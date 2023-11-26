import cc.mallet.pipe.*;
import cc.mallet.pipe.iterator.*;
import cc.mallet.topics.*;
import cc.mallet.types.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

public class AnaliseTextual {

    public static void main(String[] args) throws Exception {
        ArrayList<Pipe> pipeList = new ArrayList<>();
        pipeList.add(new CharSequenceLowercase());
        pipeList.add(new CharSequence2TokenSequence(Pattern.compile("\\p{L}[\\p{L}\\p{P}]+\\p{L}")));
        pipeList.add(new TokenSequenceRemoveStopwords(new File("stoplists/pt.txt"), "UTF-8", false, false, false));
        pipeList.add(new TokenSequence2FeatureSequence());

        InstanceList instances = new InstanceList(new SerialPipes(pipeList));
        File directory = new File("exemplos");
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

        if (files != null) {
            for (File file : files) {
                Reader fileReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
                instances.addThruPipe(new CsvIterator(fileReader, Pattern.compile("^(.*)$"), 1, -1, -1)); 
            }
        }

        int numTopics = 3;
        ParallelTopicModel model = new ParallelTopicModel(numTopics);
        model.addInstances(instances);
        model.setNumThreads(2);
        model.setNumIterations(1000);
        model.estimate();

        Graph<String, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        for (File file : files) {
            TopicAndAuthors topicAndAuthors = extractTopicAndAuthorsFromFile(file);
            String topicNode = "Tópico " + topicAndAuthors.topic;
            graph.addVertex(topicNode);

            for (String author : topicAndAuthors.authors) {
                graph.addVertex(author);
                graph.addEdge(author, topicNode);
            }
        }

        System.out.println("Grafo de Tópicos e Coautores:");
        System.out.println(graph.toString());
    }

    static class TopicAndAuthors {
        String topic;
        Set<String> authors;

        TopicAndAuthors(String topic, Set<String> authors) {
            this.topic = topic;
            this.authors = authors;
        }
    }

    private static TopicAndAuthors extractTopicAndAuthorsFromFile(File file) {
        String topic = null;
        Set<String> authors = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            List<String> lines = br.lines().collect(Collectors.toList());
            if (!lines.isEmpty()) {
                // O tópico é a primeira linha do arquivo
                topic = lines.get(0);

                // Procura pela última vírgula para os autores
                for (int i = lines.size() - 1; i >= 0; i--) {
                    String line = lines.get(i);
                    if (line.contains(",")) {
                        int lastCommaIndex = line.lastIndexOf(',');
                        if (lastCommaIndex != -1 && lastCommaIndex + 1 < line.length()) {
                            String authorLine = line.substring(lastCommaIndex + 1).trim();
                            String[] potentialAuthors = authorLine.split(",");
                            for (String author : potentialAuthors) {
                                author = author.trim();
                                if (!author.isEmpty()) {
                                    authors.add(author);
                                }
                            }
                            break; // Sair do loop após encontrar os autores
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TopicAndAuthors(topic, authors);
    }


}
