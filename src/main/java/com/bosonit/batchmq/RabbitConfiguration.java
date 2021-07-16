package com.bosonit.batchmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class RabbitConfiguration {
    static final String TOPIC_EXCHANGE_NAME = "spring-boot-exchange2";
    @Value("${listener.key}")
    public String ROUTING_KEY ;
    private static final boolean IS_DURABLE_QUEUE = false;
    public static final String QUEUE_NAME = "spring-boot";
    static final String RECEIVE_METHOD_NAME = "receiveMessage";

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, IS_DURABLE_QUEUE);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        System.out.println(String.format("\n\n-----------------------------\nListener. Routing key %s",ROUTING_KEY));
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(QUEUE_NAME);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter( receiver, RECEIVE_METHOD_NAME);
    }
}

@Component
class Receiver implements MessageListener
{

    @Override
    public void onMessage(Message message) {
        System.out.println("Received <" + new String(message.getBody()) + ">\n\n Propiedades: "+message.getMessageProperties());
    }


}