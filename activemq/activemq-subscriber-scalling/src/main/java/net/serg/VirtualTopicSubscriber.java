package net.serg;

import org.apache.activemq.ActiveMQConnectionFactory;

import jakarta.jms.*;

public class VirtualTopicSubscriber {
    private final String queueName;

    public VirtualTopicSubscriber(String queueName) {
        this.queueName = queueName;
    }

    public void start() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(queueName);

        MessageConsumer consumer = session.createConsumer(queue);

        consumer.setMessageListener(message -> {
            if (message instanceof TextMessage) {
                try {
                    System.out.println(queueName + " message received:" + ((TextMessage) message).getText());
                } catch (JMSException e) {
                    System.out.println("Error reading message. " + e.getMessage());
                }
            }
        });
    }
}