package net.serg;

import org.apache.activemq.ActiveMQConnectionFactory;

import jakarta.jms.*;

public class Publisher {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory =
            new ActiveMQConnectionFactory(
                "tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(
            false,
            Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("Test.Topic");
        MessageProducer producer = session.createProducer(topic);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        TextMessage message = session.createTextMessage("Hello World!");

        producer.send(message);
        System.out.println("Sent message " + message.getText());
        session.close();
        connection.close();
    }
}