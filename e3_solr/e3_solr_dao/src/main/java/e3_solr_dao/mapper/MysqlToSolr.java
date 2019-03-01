package e3_solr_dao.mapper;

import java.util.List;

import com.hzm.pojo.SolrPoje;

public interface MysqlToSolr {
	public List<SolrPoje> getFromMysql();

	public SolrPoje getFromMysqlByid(Long id);

}
