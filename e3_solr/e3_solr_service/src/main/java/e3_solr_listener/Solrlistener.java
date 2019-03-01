package e3_solr_listener;

import java.io.IOException;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.hzm.pojo.SolrPoje;

import e3_solr_dao.mapper.MysqlToSolr;

/**
 * 
 * 
 * 监听商品维护层发送的消息队列并对该商品添加到solr索引库
 */
public class Solrlistener implements MessageListener {
	@Resource(name = "mysqlToSolr")
	private MysqlToSolr ms;
	@Autowired
	private SolrServer ss;

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub

		try {
			TextMessage tm = (TextMessage) message;
			String id = tm.getText();
			SolrPoje poje = ms.getFromMysqlByid(Long.parseLong(id));
			SolrInputDocument sd = new SolrInputDocument();
			sd.setField("id", poje.getId());
			sd.setField("item_title", poje.getTitle());
			sd.setField("item_price", poje.getPrice());
			sd.setField("item_image", poje.getImage());
			sd.setField("item_sell_point", poje.getSell_point());
			sd.setField("item_cid", poje.getCid());
			sd.setField("item_cid_name", poje.getName());
			ss.add(sd);
			ss.commit();
		} catch (JMSException | SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
