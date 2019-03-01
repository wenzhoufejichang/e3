package e3_solr_dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.hzm.pojo.EasyUiPager;
import com.hzm.pojo.SolrPoje;

public interface SolrToWebDao {
	public EasyUiPager<SolrPoje> formSolr(SolrQuery sq);
}
