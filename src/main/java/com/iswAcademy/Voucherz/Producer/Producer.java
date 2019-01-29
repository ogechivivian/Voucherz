package com.iswAcademy.Voucherz.Producer;

import com.iswAcademy.Voucherz.Model.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${auth.rabbitmq.exchange}")
    private String exchange;

    @Value("${auth.rabbitmq.routingkey}")
    private String routingkey;


    public void produce(User user){
        amqpTemplate.convertAndSend(exchange, routingkey, user);
        System.out.println("user detail =" + user);
    }

}
