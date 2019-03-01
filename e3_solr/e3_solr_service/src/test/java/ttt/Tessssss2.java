package ttt;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;

public class Tessssss2 {

	// @Test
	public void t1() {
		try {

			CloudSolrServer cs = new CloudSolrServer("192.168.1.128:2182,192.168.1.128:2183,192.168.1.128:2184");
			cs.setDefaultCollection("collection2");
			// SolrQuery sq = new SolrQuery();
			// sq.set("id", "1");
			// QueryResponse query = cs.query(sq);
			// SolrDocumentList list = query.getResults();

			cs.deleteByQuery("id:1");
			cs.commit();
			// SolrInputDocument sid = new SolrInputDocument();
			// sid.setField("id", "1");
			// sid.setField("item_title", "张三");
			// cs.add(sid);
			// cs.commit();
		} catch (SolrServerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
