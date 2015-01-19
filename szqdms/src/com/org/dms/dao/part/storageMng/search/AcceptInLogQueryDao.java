package com.org.dms.dao.part.storageMng.search;

import java.sql.SQLException;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: AcceptInLogQueryDao 
 * Function: 入库验收日志查询
 * date: 2014年11月15日 下午10:32:05
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class AcceptInLogQueryDao extends BaseDAO {
	
	public static final AcceptInLogQueryDao getInstance(ActionContext ac){
		AcceptInLogQueryDao dao = new AcceptInLogQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * queryList: 表格数据查询
	 * @author fuxiao
	 * Date:2014年11月15日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions, User user) throws Exception{
		BaseResultSet rs = null;
		String sql = 
						"SELECT\n" +
						"        TID, I.PART_ID, I.PART_CODE, I.PART_NAME,\n" + 
						"        SPLIT_ID, SPLIT_NO, I.UNIT,\n" + 
						"        SUPPLIER_ID, SUPPLIER_NAME, SUPPLIER_CODE,\n" + 
						"        CHECK_COUNT, CHECK_TIME, I.PLAN_PRICE,\n" + 
						"        I.PLAN_PRICE * CHECK_COUNT PLAN_AMOUNT,\n" + 
						"        (\n" + 
						"         SELECT D.DISTRIBUTION_NO\n" + 
						"           FROM PT_BU_PCH_ORDER_SPLIT_DTL D\n" + 
						"          WHERE D.SPLIT_ID = L.SPLIT_ID\n" + 
						"            AND D.PART_ID = L.PART_ID\n" + 
						"        ) DISTRIBUTION_NO\n" + 
						"  FROM PT_BU_IN_ACCEPT_LOG L, PT_BA_INFO I\n" + 
						" WHERE L.PART_ID = I.PART_ID" +
						" 		AND " + conditions + " ORDER BY L.TID";	
		rs =  this.factory.select(sql, pageManager);
		rs.setFieldDic("UNIT", "JLDW");	// 计量单位
		rs.setFieldDateFormat("CHECK_TIME", "yyyy-MM-dd HH:mm:ss");
		return rs;
	}
	
	/**
	 * 
	 * queryList:新增配件代码配件名称查询条件
	 * @author fuxiao
	 * Date:2014年11月14日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions, User user, boolean isSub) throws Exception{
		
		return this.queryList(pageManager, this.subStrCondition(conditions), user);
	}
	
	// 转换查询sql
	public String subStrCondition(String conditions){
		// 转为图号的查询条件
		if(conditions.indexOf("DISTRIBUTION_NO") != -1){
			String tempStr = conditions.substring(conditions.indexOf("DISTRIBUTION_NO"), conditions.indexOf("'", conditions.indexOf("DISTRIBUTION_NO") + 22) + 1);
			conditions = conditions.replace(" AND " + tempStr, "");
			conditions += " AND EXISTS (\n" +
					"    SELECT 1 FROM PT_BU_PCH_ORDER_SPLIT_DTL D " +
					"      WHERE D.SPLIT_ID = L.SPLIT_ID AND D.PART_ID = L.PART_ID " + " AND " + tempStr + " ) ";
		}
		return conditions;
	}
	
	/**
	 * 
	 * queryStockInfoById:根据InId查询入库单主信息
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public QuerySet downQuery(String conditions) throws SQLException{
		String sql = 
				"SELECT\n" +
				"        TID, I.PART_ID, I.PART_CODE, I.PART_NAME,\n" + 
				"        SPLIT_ID, SPLIT_NO, (SELECT DIC_VALUE FROM DIC_TREE T WHERE T.ID = I.UNIT) UNIT,\n" + 
				"        SUPPLIER_ID, SUPPLIER_NAME, SUPPLIER_CODE,\n" + 
				"        CHECK_COUNT, TO_CHAR(CHECK_TIME, 'YYYY-MM-DD HH24:MI:SS') CHECK_TIME, I.PLAN_PRICE,\n" + 
				"        I.PLAN_PRICE * CHECK_COUNT PLAN_AMOUNT,\n" + 
				"        (\n" + 
				"         SELECT D.DISTRIBUTION_NO\n" + 
				"           FROM PT_BU_PCH_ORDER_SPLIT_DTL D\n" + 
				"          WHERE D.SPLIT_ID = L.SPLIT_ID\n" + 
				"            AND D.PART_ID = L.PART_ID\n" + 
				"        ) DISTRIBUTION_NO\n" + 
				"  FROM PT_BU_IN_ACCEPT_LOG L, PT_BA_INFO I\n" + 
				" WHERE L.PART_ID = I.PART_ID" +
				" 		AND " + this.subStrCondition(conditions) + " ORDER BY L.TID";	
		return this.factory.select(null, sql);
	}
}
