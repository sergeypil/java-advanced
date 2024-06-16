package net.serg;

import jakarta.jms.*;

public class SubscribersRunner {
    public static void main(String[] args) {
        VirtualTopicSubscriber subscriberA = new VirtualTopicSubscriber("Consumer.A.VirtualTopic.TEST");
        VirtualTopicSubscriber subscriberB = new VirtualTopicSubscriber("Consumer.B.VirtualTopic.TEST");
        VirtualTopicSubscriber subscriberC = new VirtualTopicSubscriber("Consumer.C.VirtualTopic.TEST");

        try {
            subscriberA.start();
            subscriberB.start();
            subscriberC.start();
        } catch (JMSException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}