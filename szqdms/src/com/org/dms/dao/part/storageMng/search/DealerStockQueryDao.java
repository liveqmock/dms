package com.org.dms.dao.part.storageMng.search;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: DealerStockQueryDao 
 * Function:信息查询 - 仓储相关 - 经销商库存查询Dao
 * date: 2014年9月10日 下午3:10:23
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public class DealerStockQueryDao extends BaseDAO {

	public static final DealerStockQueryDao getInstance(ActionContext ac) {
		DealerStockQueryDao dao = new DealerStockQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * queryList: 库存查询
	 * @author fuxiao
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws SQLException
	 * @since JDK 1.6
	 
	 */
	public BaseResultSet queryList(PageManager page, String conditions, User user) throws SQLException{
		BaseResultSet rs = null;
		StringBuilder sb = new StringBuilder(conditions);
		
		sb.append(" AND T.STATUS = '" +DicConstant.YXBS_01+ "' AND S.PART_IDENTIFY = "+DicConstant.YXBS_01+" AND S.SUPPLIER_ID = T.SUPPLIER_ID AND T.ORG_ID = O.ORG_ID");
		page.setFilter(sb.toString());
		
		String sql = "SELECT (O.CODE) ORG_CODE,\n" +
						"       (O.ONAME) ORG_NAME,\n" + 
						"       O.ORG_TYPE,\n" + 
						"       T.PART_CODE,\n" + 
						"       T.PART_NAME,\n" + 
						"       S.SUPPLIER_CODE,\n" + 
						"       S.SUPPLIER_NAME,\n" + 
						"       T.AMOUNT,\n" + 
						"       T.OCCUPY_AMOUNT,\n" + 
						"       T.AVAILABLE_AMOUNT,\n" + 
						"       (SELECT P.SALE_PRICE FROM PT_BA_INFO P WHERE P.PART_ID = T.PART_ID) SALE_PRICE\n" + 
						"FROM PT_BU_DEALER_STOCK T, PT_BA_SUPPLIER S, TM_ORG O ";
		rs = this.factory.select(sql, page);
		rs.setFieldDic("ORG_TYPE", "ZZLB");
		return rs;
	}
	
}
