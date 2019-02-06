package com.iswAcademy.Voucherz;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


@SpringBootApplication
//@EnableDiscoveryClient
public class VoucherzApplication implements CommandLineRunner {

    public static final String EXCHANGE_NAME = "regExchange";
    public static final String QUEUE_NAME = "regQueue";
    public static final String ROUTING_KEY = "registration.key";


//    public static final String EXCHANGE_NAME_Login = "loginExchange";
//    public static final String QUEUE_NAME_login = "loginQueue";
//    public static final String ROUTING_KEY_login = "login.key";
//
//    public static final String EXCHANGE_NAME_Update = "updateExchange";
//    public static final String QUEUE_NAME_Update = "updateQueue";
//    public static final String ROUTING_KEY_Update = "update.key";

    public static void main(String[] args) {
        SpringApplication.run(VoucherzApplication.class, args);

    }

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange regExchange(){
	    return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue regQueue(){
	    return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding declareBindingreg(){
	    return BindingBuilder.bind(regQueue()).to(regExchange()).with(ROUTING_KEY);
    }

//    @Bean
//    public TopicExchange loginExchange(){
//        return new TopicExchange(EXCHANGE_NAME_Login);
//    }
//
//    @Bean
//    public Queue loginQueue(){
//        return new Queue(QUEUE_NAME_login);
//    }
//
//    @Bean
//    public Binding declareBindinglogin(){
//        return BindingBuilder.bind(loginQueue()).to(loginExchange()).with(ROUTING_KEY_login);
//    }
//
//
//    @Bean
//    public TopicExchange updateExchange(){
//        return new TopicExchange(EXCHANGE_NAME_Update);
//    }
//
//    @Bean
//    public Queue updateQueue(){
//        return new Queue(QUEUE_NAME_Update);
//    }
//
//    @Bean
//    public Binding declareBindingupdate(){
//        return BindingBuilder.bind(updateQueue()).to(updateExchange()).with(ROUTING_KEY_Update);
//    }






    @Override
	public void run(String... args) throws Exception {

	}
}

