package com.my.springboot.kafka;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class SimpleKafkaProducer {
	private void execMsgSend() {
        Properties props = new Properties();
        props.put("metadata.broker.list", "192.168.31.194:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "0");
         
        ProducerConfig config = new ProducerConfig(props); 
         
        System.out.println("set config info(" + config + ") ok.");
         
        Producer<String, String> procuder = new Producer<>(config);
         
        String topic = "test";
        for (int i = 1; i <= 10; i++) {
            String value = "value1_" + i;
            KeyedMessage<String, String> msg = new KeyedMessage<String, String>(topic, value);
            procuder.send(msg);
        }
        System.out.println("send message over.");
             
        procuder.close();
    }
     
    /**
     * @param args
     */
    public static void main(String[] args) {
        SimpleKafkaProducer simpleProducer = new SimpleKafkaProducer();
        simpleProducer.execMsgSend();
    }
}
