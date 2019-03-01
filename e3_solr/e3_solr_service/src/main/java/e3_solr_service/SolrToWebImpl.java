package e3_solr_service;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hzm.pojo.EasyUiPager;
import com.hzm.pojo.SolrPoje;

import e3_solr_dao.SolrToWebDao;
import e3_solr_interface.SolrToWeb;

@Service
@Transactional
public class SolrToWebImpl implements SolrToWeb {
	@Resource(name = "solrToWebDaoImpl")
	private SolrToWebDao sw;

	/**
	 * 
	 * 
	 * 从solr索引库中搜索关键词并将关键词标注红色返回到表现层展示
	 * 
	 * 
	 * 
	 * @param keyword 关键词
	 * 
	 * @param  start  
	 *    
	 * 
	 */
	@Override
	public EasyUiPager<SolrPoje> formSolr(String keyword, Integer start, Integer rows) {
		// TODO Auto-generated method stub

		SolrQuery sq = new SolrQuery();

		sq.set("df", "item_keywords");

		sq.setStart(start);
		sq.setRows(rows);
		sq.setQuery(keyword);

		sq.setHighlight(true);
		sq.addHighlightField("item_title");
		sq.setHighlightSimplePre("<em style='color:red'>");
		sq.setHighlightSimplePost("</em>");
		EasyUiPager<SolrPoje> formSolr = sw.formSolr(sq);
		return formSolr;
	}

}
