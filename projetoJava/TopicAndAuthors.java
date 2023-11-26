public class TopicAndAuthors {
    private String topic;
    private Set<String> authors;

    public TopicAndAuthors(String topic, Set<String> authors) {
        this.topic = topic;
        this.authors = authors;
    }

    // Métodos getters
    public String getTopic() {
        return topic;
    }

    public Set<String> getAuthors() {
        return authors;
    }

    @Override
    public String toString() {
        return "Topic: " + topic + ", Authors: " + authors;
    }
}
