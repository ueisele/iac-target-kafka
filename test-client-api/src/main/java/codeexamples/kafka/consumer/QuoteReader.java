package codeexamples.kafka.consumer;

public class QuoteReader {

    private static final String TOPIC = "quotes";
    private static final String GROUP = "test-quote-group";

    public static void main(String[] args) {
        try(SimpleConsumer simpleConsumer = new SimpleConsumer(TOPIC, GROUP)) {
            while(true) {
                System.out.println(simpleConsumer.poll());
            }
        }
    }
}
