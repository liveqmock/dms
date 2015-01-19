package com.org.dms.dao.part.storageMng.search;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: IssueOrderQueryDao 
 * Function: 发料单查询
 * date: 2014年10月24日 下午4:28:41
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class IssueOrderQueryDao extends BaseDAO {
	
	public static final IssueOrderQueryDao getInstance(ActionContext ac){
		IssueOrderQueryDao dao = new IssueOrderQueryDao();
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
	public BaseResultSet queryList(PageManager pageManager, String conditions, User user) throws Exception{
		BaseResultSet rs = null;
		String sql = 
				"SELECT\n" +
				"      IO.ISSUE_ID,\n" + 
				"      IO.ISSUE_NO,\n" + 
				"      IO.ISSUE_STATUS,\n" + 
				"      IO.ORDER_ID,\n" + 
				"      IO.ORDER_NO,\n" + 
				"      SO.ORDER_TYPE,\n" + 
				"      SO.ORDER_STATUS,\n" + 
				"      SO.ORG_CODE,\n" + 
				"      SO.ORG_NAME,\n" + 
				"      SO.PALN_YMONTH,\n" + 
				"      SO.SALEUSER_NAME,\n" + 
				"      IO.USER_NAME,\n" + 
				"      IO.PRINT_STATUS,\n" + 
				"      IO.PRINT_DATE,\n" + 
				"      IO.PRINT_MAN\n" + 
				"  FROM PT_BU_ISSUE_ORDER IO, PT_BU_SALE_ORDER SO"+
				"  WHERE IO.ORDER_ID = SO.ORDER_ID AND IO.STATUS = '"+ DicConstant.YXBS_01 +"'" +
				"		AND "+ conditions;
		rs =  this.factory.select(sql, pageManager);
		rs.setFieldDic("ORDER_TYPE", "DDLX");		// 订单类型
		rs.setFieldDic("ISSUE_STATUS", "FLDFLZT");		// 订单类型
		rs.setFieldDic("ORDER_STATUS", "DDZT");		// 订单状态
		rs.setFieldDic("PRINT_STATUS", "DYZT");	// 是否打印
		rs.setFieldDateFormat("PALN_YMONTH", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldDateFormat("PRINT_DATE", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldUserID("PRINT_MAN");
		return rs;
	}
	
	/**
	 * 
	 * queryList: 添加配件信息查询
	 * @author fuxiao
	 * Date:2014年11月14日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions, User user,boolean isSub) throws Exception{
		
		// 判断用户是否为军品用户
/*		if(this.checkUserIsAM(user)){
			conditions += "AND SO.ORDER_TYPE = "+DicConstant.DDLX_08;
		} else {
			
			conditions += "AND SO.ORDER_TYPE <> "+DicConstant.DDLX_08;
		}*/
		
		if(conditions.indexOf("PART_CODE") != -1 || conditions.indexOf("PART_NAME") != -1){
			conditions += " AND EXISTS (\n" +
							"    SELECT 1 FROM PT_BU_ISSUE_ORDER_DTL D WHERE D.ISSUE_ID = IO.ISSUE_ID";
			if(conditions.indexOf("PART_NAME") != -1){
				String partNameSql = conditions.substring(conditions.indexOf("PART_NAME"), conditions.indexOf("'", conditions.indexOf("PART_NAME") + 15) + 1);
				conditions = conditions.replace(" AND "+partNameSql, "");
				conditions +=" AND " + partNameSql;
			}
			if(conditions.indexOf("PART_CODE") != -1){
				String partNameSql = conditions.substring(conditions.indexOf("PART_CODE"), conditions.indexOf("'", conditions.indexOf("PART_CODE") + 15) + 1);
				conditions = conditions.replace(" AND "+partNameSql, "");
				conditions +=" AND " + partNameSql;
			}
			conditions += " ) ";
		}
		
		return this.queryList(pageManager, conditions, user);
	}
	
	/**
	 * 
	 * checkUserIsAM: 判断用户的org是否属于军品，true：是，false: 不是
	 * @author fuxiao
	 * Date:2014年11月14日
	 *
	 */
	public boolean checkUserIsAM(User user) throws SQLException{
		String res = this.factory.select("SELECT F_IS_AM("+user.getOrgId()+") FROM DUAL")[0][0];
		return res.equals("1");
	}
	
	/**
	 * 
	 * queryStockInfoById:根据outId查询出单主信息
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public QuerySet queryIssueOrderInfoById(String issueId) throws SQLException{
		String sql = 
					"SELECT\n" +
					"      IO.ISSUE_ID,\n" + 
					"      IO.ISSUE_NO,\n" + 
					"      IO.ISSUE_STATUS,\n" + 
					"      IO.ORDER_ID,\n" + 
					"      IO.ORDER_NO,\n" + 
					"      SO.ORDER_TYPE,\n" + 
					"      SO.ORDER_STATUS,\n" + 
					"      SO.ORG_CODE,\n" + 
					"      SO.ORG_NAME,\n" + 
					"      SO.PALN_YMONTH,\n" + 
					"      SO.SALEUSER_NAME,\n" + 
					"      IO.USER_NAME,\n" + 
					"      IO.PRINT_STATUS,\n" + 
					"      IO.PRINT_DATE,\n" + 
					"      (SELECT U.PERSON_NAME FROM TM_USER U WHERE U.ACCOUNT = IO.PRINT_MAN) PRINT_MAN\n" + 
					"  FROM PT_BU_ISSUE_ORDER IO, PT_BU_SALE_ORDER SO\n" + 
					" WHERE IO.ORDER_ID = SO.ORDER_ID AND IO.ISSUE_ID = ?";

		return this.factory.select(new Object[]{ issueId }, sql);
	}
	
	/**
	 * 
	 * queryStockInfoById:查询出库单详细信息
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryIssueOrderDetailsById(PageManager pageManage, String conditions) throws Exception {
		pageManage.setFilter(conditions);
		String sql = 
						"SELECT ISSUE_ID,\n" +
						"       ISSUE_NO,\n" + 
						"       ORDER_ID,\n" + 
						"       PART_ID,\n" + 
						"       PART_CODE,\n" + 
						"       PART_NAME,\n" + 
						"       SUPPLIER_ID,\n" + 
						"       SUPPLIER_NAME,\n" + 
						"       SUPPLIER_CODE,\n" + 
						"       POSITION_ID,\n" + 
						"       POSITION_CODE,\n" + 
						"       POSITION_NAME,\n" + 
						"       SHOULD_COUNT,\n" + 
						"       REAL_COUNT,\n" + 
						"       UNIT,\n" + 
						"       REMARK,\n" + 
						"       COMPANY_ID,\n" + 
						"       ORG_ID,\n" + 
						"       STATUS,\n" + 
						"       OEM_COMPANY_ID,\n" + 
						"       SECRET_LEVEL\n" + 
						"  FROM PT_BU_ISSUE_ORDER_DTL";
		return this.factory.select(sql, pageManage);
		
	}

}
