package ttt;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class ActivityMqTest2 {

	// @Test
	public void test1() {
		ApplicationContext a = new ClassPathXmlApplicationContext("classpath:spring/applicationContext_activitymq.xml");
		JmsTemplate template = a.getBean(JmsTemplate.class);
		
		Queue q = (Queue) a.getBean("queueDestination");
		template.send(q, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				TextMessage message = session.createTextMessage("hello");

				return message;
			}
		});
	}

}
