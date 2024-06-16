package net.serg;

import org.apache.activemq.ActiveMQConnectionFactory;

import jakarta.jms.*;

public class Publisher {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("VirtualTopic.TEST");

        MessageProducer producer = session.createProducer(topic);
        TextMessage message = session.createTextMessage("Hello, Virtual Topic!");

        producer.send(message);

        System.out.println("Message sent: " + message.getText());

        connection.close();
    }
}