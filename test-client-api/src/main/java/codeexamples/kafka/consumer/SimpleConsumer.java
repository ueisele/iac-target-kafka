package codeexamples.kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.singletonList;

public class SimpleConsumer implements AutoCloseable {

    private final Consumer<String, String> consumer;

    public SimpleConsumer(String topic, String group) {
        this(consumer(topic, group));
    }

    SimpleConsumer(Consumer<String, String> consumer) {
        this.consumer = consumer;
    }

    public Map<String, String> poll() {
        ConsumerRecords<String, String> consumerRecords = consumer.poll(500);
        Map<String, String> results = new LinkedHashMap<>();
        for(ConsumerRecord<String, String> consumerRecord : consumerRecords) {
            results.put(consumerRecord.key(), consumerRecord.value());
        }
        return results;
    }

    private static Consumer<String, String> consumer(String topic, String group) {
        Consumer<String, String> consumer =new KafkaConsumer<>(consumerProperties(group));
        consumer.subscribe(singletonList(topic));
        return consumer;
    }

    private static Map<String, Object> consumerProperties(String group) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka.codeexamples.kafka:9092");
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, group);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, group);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 10_000);
        return properties;
    }

    @Override
    public void close() {
        consumer.close();
    }
}
