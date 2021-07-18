package com.bosonit.batchmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class SpringReceiver {
    @RabbitListener(queues = "${listener.rabbitReceiver}")
    public void receive(String in) {
        System.out.println(" [x] Received  Spring'" + in + "'");
    }

    @RabbitListener(queues = RabbitConfiguration.QUEUE_NAME2)
    public void receive2(String in) {
        System.out.println(" [x] Received Queue2'" + in + "'");
    }
}
