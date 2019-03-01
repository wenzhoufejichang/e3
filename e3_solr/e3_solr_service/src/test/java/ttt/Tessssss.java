package ttt;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hzm.pojo.Message;

import e3_solr_interface.SolrInterface;

public class Tessssss {

	//@Test
	public void t1() {

		ApplicationContext a = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext_*.xml");
		SolrInterface bean = a.getBean(SolrInterface.class);
		Message message = bean.getFromMysql();

		System.out.println(message.getStatus());

	}
}
