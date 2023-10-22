import cc.mallet.topics.*;
import cc.mallet.types.*;
import java.io.*;

public class AnaliseTextual {

    public static void main(String[] args) throws Exception {
        // Lê o arquivo mallet
        InstanceList instances = InstanceList.load(new File("exemplos.mallet"));

        // Imprime o alfabeto de dados para depuração
        System.out.println("Alfabeto de dados: " + instances.getDataAlphabet());

        // Cria e estima um modelo LDA
        int numTopics = 3;
        ParallelTopicModel model = new ParallelTopicModel(numTopics);
        model.addInstances(instances);
        model.setNumThreads(2);
        model.setNumIterations(1000);
        model.estimate();

        // Imprime as palavras-chave para cada tópico
        Object[][] topicWords = model.getTopWords(10);
        for (int topic = 0; topic < numTopics; topic++) {
            System.out.println("Tópico " + topic + ":");
            for (int word = 0; word < 10; word++) {
                System.out.print((String) topicWords[topic][word] + "  ");
            }
            System.out.println();
        }
    }
}
