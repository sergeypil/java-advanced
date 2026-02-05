package net.serg;

import org.apache.activemq.ActiveMQConnectionFactory;

import jakarta.jms.*;

public class NonDurableSubscriber {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("Test.Topic");

        MessageConsumer consumer = session.createConsumer(topic); // For Non-Durable Subscriber
        Message message = consumer.receive();

        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("Received message " + textMessage.getText());
        }
        session.close();
        connection.close();
    }
}   