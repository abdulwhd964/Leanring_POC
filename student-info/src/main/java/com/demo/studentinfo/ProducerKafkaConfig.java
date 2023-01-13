package com.demo.studentinfo;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.demo.studentinfo.model.Student;

@Configuration
public class ProducerKafkaConfig {
	
	@Bean
	public NewTopic getNewTopic() {
		return TopicBuilder.name("student-info-mongo-data").build();
	}
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootStrapServers;
	
	public Map<String, Object> producerConfig(){
		HashMap<String, Object> props=new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
		props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		
		return props;
		
	}
	@Bean
	public ProducerFactory<String, Student> producerFactory(){
		return new DefaultKafkaProducerFactory<>(producerConfig());
	}
	@Bean
	public KafkaTemplate<String, Student> kafkaTemplate(ProducerFactory<String, Student>producerFactory)
	{
		return new KafkaTemplate<>(producerFactory);
	}
}
