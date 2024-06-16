package net.serg;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQQueue;

import jakarta.jms.*;

public class Requestor {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();

//        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
//        ActiveMQConnection connection = (ActiveMQConnection)connectionFactory.createConnection();
//        connection.start();
        
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("Test.Queue");
        TemporaryQueue replyQueue = session.createTemporaryQueue();
        //Queue replyQueue = session.createQueue("ReplyQueue");

        MessageProducer producer = session.createProducer(queue);
        TextMessage requestMessage = session.createTextMessage("Hello");
        requestMessage.setJMSReplyTo(replyQueue);

        producer.send(requestMessage);
        System.out.println("Sent message: " + requestMessage.getText());

        MessageConsumer consumer = session.createConsumer(replyQueue);
        Message replyMsg = consumer.receive(60000);
        if (replyMsg instanceof TextMessage) {
            System.out.println("Received reply: " + ((TextMessage) replyMsg).getText());
        }

        consumer.close();
        session.close();
        //connection.destroyDestination((ActiveMQDestination) replyQueue); // delete queue
        connection.close();
    }
}