package ttt;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ActivityMqTest {
	//@Test
	public void tt() {
		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;
		try {
			ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.1.128:61616");
			connection = cf.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("11");
			producer = session.createProducer(queue);
			TextMessage textMessage = session.createTextMessage("你好!1");
			producer.send(textMessage);
			producer.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (producer != null) {
				try {
					producer.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	//@Test
	public void tt4() {
		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;
		try {
			ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.1.128:61616");
			connection = cf.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic("te");
			producer = session.createProducer(topic);
			TextMessage textMessage = session.createTextMessage("你好!1");
			producer.send(textMessage);
			producer.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (producer != null) {
				try {
					producer.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	//@Test
	public void tt2() {
		Connection connection = null;
		Session session = null;
		MessageConsumer c = null;
		try {
			ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.1.128:61616");
			connection = cf.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("test");
			c = session.createConsumer(queue);
			c.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
					// TODO Auto-generated method stub
					try {
						TextMessage TextMessage = (TextMessage) message;
						String text = TextMessage.getText();
						System.out.println(text);

					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			System.in.read();
			connection.close();
			session.close();
			c.close();
		} catch (JMSException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//@Test
	public void tt3() {
		Connection connection = null;
		Session session = null;
		MessageConsumer c = null;
		try {
			ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.1.128:61616");
			connection = cf.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic("te");
			c = session.createConsumer(topic);
			c.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
					// TODO Auto-generated method stub
					try {
						TextMessage TextMessage = (TextMessage) message;
						String text = TextMessage.getText();
						System.out.println(text);

					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			System.in.read();
			connection.close();
			session.close();
			c.close();
		} catch (JMSException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
