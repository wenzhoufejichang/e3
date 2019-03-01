package e3_solr_interface;

import com.hzm.pojo.EasyUiPager;
import com.hzm.pojo.SolrPoje;

public interface SolrToWeb {

	
	public EasyUiPager<SolrPoje> formSolr(String keyword,Integer start,Integer rows);
}
