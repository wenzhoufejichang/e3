package e3_solr_service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzm.pojo.Message;
import com.hzm.pojo.SolrPoje;

import e3_solr_dao.mapper.MysqlToSolr;
import e3_solr_interface.SolrInterface;

@Service
@Transactional
public class SolrInterfaceImpl implements SolrInterface {

	@Resource(name = "mysqlToSolr")
	private MysqlToSolr m;
	// @Resource(name = "httpSolrServer")
	@Autowired
	private SolrServer ss;

	/**
	 * 
	 * 
	 * 将数据库里的所有上架的商品添加到solr集群中
	 */
	@Override
	public Message getFromMysql() {
		// TODO Auto-generated method stub
		Message mm = new Message();

		try {

			List<SolrPoje> list = m.getFromMysql();
			for (SolrPoje sp : list) {

				SolrInputDocument sd = new SolrInputDocument();
				sd.setField("id", sp.getId());
				sd.setField("item_title", sp.getTitle());
				sd.setField("item_price", sp.getPrice());
				sd.setField("item_image", sp.getImage());
				sd.setField("item_sell_point", sp.getSell_point());
				sd.setField("item_cid", sp.getCid());
				sd.setField("item_cid_name", sp.getName());
				ss.add(sd);
			}
			ss.commit();

			mm.setStatus(200);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mm.setStatus(400);
		}
		return mm;
	}

}
