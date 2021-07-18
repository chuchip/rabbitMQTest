package com.bosonit.batchmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Configuration
public class RabbitConfiguration {
    static final String TOPIC_EXCHANGE_NAME = "spring-boot-exchange";
    @Value("${listener.route}")
    public String ROUTING_KEY ;
    private static final boolean IS_DURABLE_QUEUE = false;
    @Value("${listener.manual}")
    public  String QUEUE_NAME;
    public final static String QUEUE_NAME2= "ManualQueue2";

    @Bean
    Queue manualQueue() {
        return new Queue(QUEUE_NAME, IS_DURABLE_QUEUE);
    }

    @Bean
    Queue manualQueue2() {
        return new Queue(QUEUE_NAME2, IS_DURABLE_QUEUE);
    }

    @Bean
    Queue queueSpring(@Value("${listener.rabbitReceiver}") String springQueue)
    {
        return new Queue(springQueue, IS_DURABLE_QUEUE);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue manualQueue, TopicExchange exchange) {
        return BindingBuilder.bind(manualQueue).to(exchange).with(ROUTING_KEY);
    }
    @Bean
    Binding binding2(Queue manualQueue2, TopicExchange exchange) {
        return BindingBuilder.bind(manualQueue2).to(exchange).with(ROUTING_KEY);
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
    MessageListenerAdapter listenerAdapter(ManualReceiver receiver) {
        return new MessageListenerAdapter( receiver);
    }
    /**
     * Establecer profile conecction para poder definir nuestra propia configuracion.
     * En otro caso cogera los valores del fichero de application.properties
     * @return
     */
    @Profile("connection")
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost", 5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        System.out.println("Creating connection factory");

        return connectionFactory;
    }
}

@Component
class ManualReceiver implements MessageListener
{

    @Override
    public void onMessage(Message message) {
        System.out.println("Received <" + new String(message.getBody()) + ">\n\n Propiedades: "+message.getMessageProperties());
    }


}