package codeexamples.kafka.producer;

import java.io.IOException;
import java.util.Set;

public class RandomQuoteProducer {

    private static final String TOPIC = "quotes";

    public static void main(String[] args) throws IOException {
        RandomQuoteReader randomQuoteReader = new RandomQuoteReader();
        try(SimpleProducer simpleProducer = new SimpleProducer(TOPIC)) {
            while(true) {
                Set<Quote> quotes = randomQuoteReader.randomQuotes(10);
                for(Quote quote : quotes) {
                    simpleProducer.sendSync(quote.getTitle(), quote.getText());
                }
            }
        }
    }
}
