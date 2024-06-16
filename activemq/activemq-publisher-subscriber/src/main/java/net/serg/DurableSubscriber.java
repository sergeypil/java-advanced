package net.serg;

import org.apache.activemq.ActiveMQConnectionFactory;

import jakarta.jms.*;

public class DurableSubscriber {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        
        Connection connection = connectionFactory.createConnection();
        connection.setClientID("durable");
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("Test.Topic");
        
        MessageConsumer consumer = session.createDurableSubscriber(topic, "TestSubscriber");
        Message message = consumer.receive();
        
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("Received message " + textMessage.getText());
        }
        session.close();
        connection.close();
    }
}