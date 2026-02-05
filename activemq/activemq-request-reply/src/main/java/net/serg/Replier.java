package net.serg;

import org.apache.activemq.ActiveMQConnectionFactory;

import jakarta.jms.*;

public class Replier {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("Test.Queue");

        MessageConsumer consumer = session.createConsumer(queue);
        Message requestMsg = consumer.receive(60000);
        if (requestMsg instanceof TextMessage) {
            System.out.println("Received request: " + ((TextMessage) requestMsg).getText());

            MessageProducer replyProducer = session.createProducer(null);
            TextMessage replyMessage = session.createTextMessage("Hello, back to you!");
            replyMessage.setJMSCorrelationID(requestMsg.getJMSCorrelationID());

            replyProducer.send(requestMsg.getJMSReplyTo(), replyMessage);
        }
        consumer.close();
        session.close();
        connection.close();
    }
}