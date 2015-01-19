package com.org.dms.dao.part.storageMng.search;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

/**
 * 
 * @ClassName: DealerStockAmountQueryDao
 * @Description: 渠道库存金额汇总查询
 * @author: fuxiao
 * @date: 2015年1月5日 下午4:41:33
 */
public class DealerStockAmountQueryDao extends BaseDAO {
	
	public static final DealerStockAmountQueryDao getInstance(ActionContext ac){
		DealerStockAmountQueryDao dao = new DealerStockAmountQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * @Title: queryList
	 * @Description: 表格数据查询
	 * @param pageManager
	 * @param conditions
	 * @return
	 * @throws Exception
	 * @return: BaseResultSet
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions) throws Exception{
		BaseResultSet rs = null;
		String sql = 
					"SELECT O1.CODE ORG_CODE, O1.ONAME ORG_NAME, O1.ORG_TYPE, O2.ONAME P_ORG_NAME, T1.SUM_AMOUNT\n" +
					"  FROM (SELECT DS.ORG_ID, SUM(DS.AMOUNT * I.SALE_PRICE) SUM_AMOUNT\n" + 
					"          FROM PT_BU_DEALER_STOCK DS, PT_BA_INFO I\n" + 
					"         WHERE DS.PART_ID = I.PART_ID\n" + 
					"         GROUP BY DS.ORG_ID) T1,\n" + 
					"       TM_ORG O1,\n" + 
					"       TM_ORG O2\n" + 
					" WHERE T1.ORG_ID = O1.ORG_ID\n" + 
					"   AND O1.PID = O2.ORG_ID AND " + conditions +
					" ORDER BY O1.ORG_ID";
		rs = this.factory.select(sql, pageManager);
		rs.setFieldDic("ORG_TYPE", "ZZLB");
		return rs;
	}
	
	/**
	 * 
	 * @Title: queryDownInfo
	 * @Description: 下载数据查询
	 * @param conditions
	 * @return
	 * @throws Exception
	 * @return: QuerySet
	 */
	public QuerySet queryDownInfo(String conditions) throws Exception {
		return this.factory.select(null, 
						"SELECT O1.CODE ORG_CODE, O1.ONAME ORG_NAME, ( SELECT DIC_VALUE FROM DIC_TREE WHERE DIC_CODE = TO_CHAR(O1.ORG_TYPE) ) ORG_TYPE, O2.ONAME P_ORG_NAME, T1.SUM_AMOUNT\n" +
						"  FROM (SELECT DS.ORG_ID, SUM(DS.AMOUNT * I.SALE_PRICE) SUM_AMOUNT\n" + 
						"          FROM PT_BU_DEALER_STOCK DS, PT_BA_INFO I\n" + 
						"         WHERE DS.PART_ID = I.PART_ID\n" + 
						"         GROUP BY DS.ORG_ID) T1,\n" + 
						"       TM_ORG O1,\n" + 
						"       TM_ORG O2\n" + 
						" WHERE T1.ORG_ID = O1.ORG_ID\n" + 
						"   AND O1.PID = O2.ORG_ID AND " + conditions +
						" ORDER BY O1.ORG_ID"
					);
	}
}
	