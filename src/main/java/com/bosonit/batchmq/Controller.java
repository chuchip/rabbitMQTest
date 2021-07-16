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
    List<String> colas=new ArrayList<>();
    @Autowired
    ConnectionFactory connectionFactory;
    public Controller()
    {
        colas.add(RabbitConfiguration.QUEUE_NAME);
    }
    @GetMapping("/{n1}/{ruta}/{msg}")
    public void sendMsgTopic( @PathVariable String n1, @PathVariable String ruta, @PathVariable String msg)
    {
        System.out.println("Creando cola para topic: "+n1);
        if (!colas.contains(n1))
        {
            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
            container.setConnectionFactory(connectionFactory);
            container.setQueueNames(n1);
        }

        rabbitTemplate.convertAndSend(n1, ruta, msg);
    }

    @GetMapping("/{ruta}/{msg}")
    public void sendMsg(@PathVariable  String ruta, @PathVariable String msg)
    {
        rabbitTemplate.convertAndSend(RabbitConfiguration.TOPIC_EXCHANGE_NAME, ruta, msg);
    }
}
