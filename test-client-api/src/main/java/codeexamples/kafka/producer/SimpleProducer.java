package codeexamples.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.lang.String.format;

public class SimpleProducer implements AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleProducer.class);

    private final String topic;

    private final Producer<String, String> producer;

    public SimpleProducer(String topic) {
        this(topic, producer());
    }

    SimpleProducer(String topic, Producer<String, String> producer) {
        this.topic = topic;
        this.producer = producer;
    }

    public void sendSync(String key, String value) {
        try {
            producer.send(new ProducerRecord<>(topic, key, value)).get();
        } catch (InterruptedException e) {
            Thread.interrupted();
            return;
        } catch (ExecutionException e) {
            if(e.getCause() instanceof RuntimeException) {
                throw (RuntimeException) e.getCause();
            }
            throw new RuntimeException(e.getCause());
        }
    }

    public Future<RecordMetadata> send(String key, String value) {
        return producer.send(new ProducerRecord<>(topic, key, value), (metadata, exception) -> {
            LOG.info(format("Send key '%s' at partition %d and offset %d.", key, metadata.partition(), metadata.offset()));
        });
    }

    private static Producer<String, String> producer() {
        return new KafkaProducer<>(producerProperties());
    }

    private static Map<String, Object> producerProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka.codeexamples.kafka:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        properties.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE);
        properties.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 2000);
        //properties.put(ProducerConfig.LINGER_MS_CONFIG, 50L);
        return properties;
    }

    @Override
    public void close() {
        producer.close();
    }
}
