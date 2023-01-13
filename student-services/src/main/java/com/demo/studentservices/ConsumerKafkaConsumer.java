package com.demo.studentservices;

import java.util.HashMap;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.demo.studentservices.model.Student;

@Configuration
@EnableKafka
public class ConsumerKafkaConsumer {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootStrapServers;
	
	@Bean
	public ConsumerFactory<String, Student> consumerFactory(){
		JsonDeserializer<Student> deserializer = new JsonDeserializer<>(Student.class);
	    deserializer.setRemoveTypeHeaders(false);
	    deserializer.addTrustedPackages("com.demo.studentservices.model.Student");
	    deserializer.setUseTypeMapperForKey(true);
		
		
		HashMap<String, Object> props=new HashMap<>();
		
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
		
		return new DefaultKafkaConsumerFactory<>(props,new StringDeserializer(), deserializer);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Student> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Student> factory=new ConcurrentKafkaListenerContainerFactory();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
}
