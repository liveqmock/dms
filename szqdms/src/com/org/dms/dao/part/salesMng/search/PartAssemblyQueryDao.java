package com.org.dms.dao.part.salesMng.search;

import java.util.Map;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: PartAssemblyQueryDao 
 * Function: 大总成销量统计报表
 * date: 2014年11月3日 下午4:13:53
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class PartAssemblyQueryDao extends BaseDAO {
	
	public static final PartAssemblyQueryDao getInstance(ActionContext ac){
		PartAssemblyQueryDao dao = new PartAssemblyQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * queryList: 查询表单查询
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, Map<String,String> hm) throws Exception{
		StringBuilder dateStr = new StringBuilder();
		String whereStr = "";
		if(hm.get("APPLY_DATE_B") != null && !"".equals(hm.get("APPLY_DATE_B"))){
			dateStr.append(" AND APPLY_DATE >= TO_DATE('"+hm.get("APPLY_DATE_B")+"', 'YYYY-MM-DD')");
		}
		if(hm.get("APPLY_DATE_E") != null && !"".equals(hm.get("APPLY_DATE_E"))){
			dateStr.append(" AND APPLY_DATE <= TO_DATE('"+hm.get("APPLY_DATE_E")+"', 'YYYY-MM-DD')");
		}
		if(hm.get("BELONG_ASSEMBLY") != null && !"".equals(hm.get("BELONG_ASSEMBLY"))){
			whereStr = " AND I.BELONG_ASSEMBLY = '" + hm.get("BELONG_ASSEMBLY") + "'";
		}
		if(hm.get("ORG_TYPE") != null && !"".equals(hm.get("ORG_TYPE"))){
			whereStr = " AND O.ORG_TYPE = '" + hm.get("ORG_TYPE") + "'";
		}
		BaseResultSet rs = null;
		String sql = 
						"SELECT O.CODE ORG_CODE,\n" +
						"       O.ONAME ORG_NAME,\n" + 
						"       I.PART_CODE,\n" + 
						"       I.PART_NAME,\n" + 
						"       PART_COUNT,\n" + 
						"       I.BELONG_ASSEMBLY,\n" + 
						"       PART_COUNT * I.SALE_PRICE COUNT_PRICE\n" + 
						"  FROM (SELECT ORG_CODE, ORG_NAME, PART_ID, SUM(C.COUNT) PART_COUNT\n" + 
						"          FROM PT_BU_DEALER_STOCK_CHANGE C\n" + 
						"         WHERE C.APPLY_TYPE = 204102\n" + 
						"           AND EXISTS (SELECT 1\n" + 
						"                  FROM PT_BA_INFO I\n" + 
						"                 WHERE I.PART_ID = C.PART_ID\n" + 
						"                   AND I.IF_ASSEMBLY = 100101)\n" + dateStr +
						"         GROUP BY ORG_CODE, ORG_NAME, PART_ID) T1,\n" + 
						"       PT_BA_INFO I, TM_ORG O\n" + 
						" WHERE T1.PART_ID = I.PART_ID AND T1.ORG_CODE = O.CODE \n" + whereStr +
						" ORDER BY I.PART_ID";
		rs = this.factory.select(sql, pageManager);
		rs.setFieldDic("BELONG_ASSEMBLY", "PJZCLB");
		return rs;
	}
	
	/**
	 * 
	 * queryDownInfo: 查询需要下载的数据
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public QuerySet queryDownInfo(Map<String,String> hm) throws Exception {
		StringBuilder dateStr = new StringBuilder();
		String whereStr = "";
		if(hm.get("APPLY_DATE_B") != null && !"".equals(hm.get("APPLY_DATE_B"))){
			dateStr.append(" AND APPLY_DATE >= TO_DATE('"+hm.get("APPLY_DATE_B")+"', 'YYYY-MM-DD')");
		}
		if(hm.get("APPLY_DATE_E") != null && !"".equals(hm.get("APPLY_DATE_E"))){
			dateStr.append(" AND APPLY_DATE <= TO_DATE('"+hm.get("APPLY_DATE_E")+"', 'YYYY-MM-DD')");
		}
		if(hm.get("BELONG_ASSEMBLY") != null && !"".equals(hm.get("BELONG_ASSEMBLY"))){
			whereStr = " AND I.BELONG_ASSEMBLY = '" + hm.get("BELONG_ASSEMBLY") + "'";
		}
		if(hm.get("ORG_TYPE") != null && !"".equals(hm.get("ORG_TYPE"))){
			whereStr = " AND O.ORG_TYPE = '" + hm.get("ORG_TYPE") + "'";
		}
		String sql = 
						"SELECT O.CODE ORG_CODE,\n" +
						"       O.ONAME ORG_NAME,\n" + 
						"       I.PART_CODE,\n" + 
						"       I.PART_NAME,\n" + 
						"       PART_COUNT,\n" + 
						"       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = I.BELONG_ASSEMBLY) BELONG_ASSEMBLY,\n" + 
						"       PART_COUNT * I.SALE_PRICE COUNT_PRICE\n" + 
						"  FROM (SELECT ORG_CODE, ORG_NAME, PART_ID, SUM(C.COUNT) PART_COUNT\n" + 
						"          FROM PT_BU_DEALER_STOCK_CHANGE C\n" + 
						"         WHERE C.APPLY_TYPE = 204102\n" + 
						"           AND EXISTS (SELECT 1\n" + 
						"                  FROM PT_BA_INFO I\n" + 
						"                 WHERE I.PART_ID = C.PART_ID\n" + 
						"                   AND I.IF_ASSEMBLY = 100101)\n" + dateStr +
						"         GROUP BY ORG_CODE, ORG_NAME, PART_ID) T1,\n" + 
						"       PT_BA_INFO I, TM_ORG O \n" + 
						" WHERE T1.PART_ID = I.PART_ID AND T1.ORG_CODE = O.CODE \n" + whereStr +
						" ORDER BY I.PART_ID";
		return this.factory.select(null, sql);
	}
}
	