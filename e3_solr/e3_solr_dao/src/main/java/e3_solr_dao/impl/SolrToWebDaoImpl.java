package e3_solr_dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hzm.pojo.EasyUiPager;
import com.hzm.pojo.SolrPoje;

import e3_solr_dao.SolrToWebDao;

@Repository
public class SolrToWebDaoImpl implements SolrToWebDao {

	@Autowired
	private SolrServer ss;

	@Override
	public EasyUiPager<SolrPoje> formSolr(SolrQuery sq) {
		// TODO Auto-generated method stub
		List<SolrPoje> solrp = new ArrayList<>();
		EasyUiPager<SolrPoje> ep = new EasyUiPager<>();
		try {
			QueryResponse query = ss.query(sq);
			SolrDocumentList list = query.getResults();
			Map<String, Map<String, List<String>>> map = query.getHighlighting();
			for (SolrDocument solrDocument : list) {
				SolrPoje sp = new SolrPoje();
				sp.setId(Long.parseLong((String) solrDocument.get("id")));
				sp.setImage((String) solrDocument.get("item_image"));
				sp.setName((String) solrDocument.get("item_cid_name"));
				sp.setCid(((Long) solrDocument.get("item_cid")).intValue());
				sp.setPrice((Long) solrDocument.get("item_price"));
				sp.setSell_point((String) solrDocument.get("item_sell_point"));

				Map<String, List<String>> map2 = map.get(String.valueOf(sp.getId()));
				List<String> list2 = map2.get("item_title");
				if (list2 != null) {
					sp.setTitle(list2.get(0));
				} else {
					sp.setTitle((String) solrDocument.get("item_title"));
				}
				solrp.add(sp);
			}
			ep.setRows(solrp);
			ep.setTotal(list.getNumFound());
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// SolrDocument{id=1145177,
		// item_title=三星 G3586V 白色 联通4G手机,
		// item_price=82900,
		// item_image=http://image.e3mall.cn/jd/cddc143c8614435282be89015b130ce4.jpg,
		// item_sell_point=下单即送手机魔法盒！,
		// item_cid=560, item_cid_name=手机, _version_=1622543667740803072},
		return ep;
	}

}
