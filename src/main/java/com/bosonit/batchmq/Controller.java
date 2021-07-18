package com.bosonit.batchmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @GetMapping("/exchange/{ruta}/{msg}")
    public String sendMsgTopic( @PathVariable String ruta, @PathVariable String msg)
    {
        rabbitTemplate.convertAndSend(RabbitConfiguration.TOPIC_EXCHANGE_NAME, ruta, msg);
        return String.format("Send message %s a exchange %s a traves de la ruta: %s",msg,RabbitConfiguration.TOPIC_EXCHANGE_NAME, ruta);

    }

    @GetMapping("/{ruta}/{msg}")
    public String sendMsg(@PathVariable  String ruta, @PathVariable String msg)
    {
        rabbitTemplate.convertAndSend(ruta, msg);
        return String.format("Send message %s a ruta: %s",msg,ruta);
    }
}
