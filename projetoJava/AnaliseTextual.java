import cc.mallet.pipe.*;
import cc.mallet.pipe.iterator.*;
import cc.mallet.topics.*;
import cc.mallet.types.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class AnaliseTextual {

    public static void main(String[] args) throws Exception {
        System.out.println("Estou aqui");
        // Pipes: lowercase, tokenize, remove stopwords, map to features
        ArrayList<Pipe> pipeList = new ArrayList<Pipe>();
        pipeList.add(new CharSequenceLowercase());
        pipeList.add(new CharSequence2TokenSequence(Pattern.compile("\\p{L}[\\p{L}\\p{P}]+\\p{L}")));
        pipeList.add(new TokenSequenceRemoveStopwords(new File("stoplists/pt.txt"), "UTF-8", false, false, false));
        pipeList.add(new TokenSequence2FeatureSequence());

        InstanceList instances = new InstanceList(new SerialPipes(pipeList));

        // Directory containing the text files
        File directory = new File("exemplos");
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));
        
        if (files != null) {
            for (File file : files) {
                // Read and process each text file in the directory
                Reader fileReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
                instances.addThruPipe(new CsvIterator(fileReader, Pattern.compile("^(.*)$"), 1, -1, -1)); // assuming one document per line
            }
        }

        // The rest of your existing code to create and estimate the LDA model...
        int numTopics = 3;
        ParallelTopicModel model = new ParallelTopicModel(numTopics);
        model.addInstances(instances);
        model.setNumThreads(2);
        model.setNumIterations(1000);
        model.estimate();

        // Print the keywords for each topic...
        Object[][] topicWords = model.getTopWords(10);
        for (int topic = 0; topic < numTopics; topic++) {
            System.out.println("Tópico " + topic + ":");
            for (int word = 0; word < 10; word++) {
                System.out.print((String) topicWords[topic][word] + "  ");
            }
            System.out.println();
        }

        System.out.println("fim");


        Node a = new Node("A");
	    Node b = new Node("B");
	    Node c = new Node("C");
	    Node d = new Node("D");
	    Node e = new Node("E");
        


        ArrayList<Node> list = new ArrayList<Node>();
	    list.add(a);
	    list.add(b);
	    list.add(c);
	    list.add(d);
	    list.add(e);

        Graph g = new Graph(list);
        
        g.addEdge(a, e);
	    g.addEdge(a, d);
	    g.addEdge(d, e);
	    g.addEdge(b, e);
	    g.addEdge(b, c);

        System.out.println("Adjacency List: ");
	    g.printAdjList();
	    
    }
}
