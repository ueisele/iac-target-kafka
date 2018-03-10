package codeexamples.kafka.producer;

public class Quote {

    private final String title;

    private final String text;

    public Quote(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
