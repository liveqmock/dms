package com.org.dms.dao.part.purchaseMng.purchaseOrder;

import java.sql.SQLException;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: PchReturnOrderQueryDao 
 * Function: 采购退货单查询dao
 * date: 2014年11月22日 下午2:23:53
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class PchReturnOrderQueryDao extends BaseDAO {
	
	public static final PchReturnOrderQueryDao getInstance(ActionContext ac){
		PchReturnOrderQueryDao dao = new PchReturnOrderQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * queryList: 表格查询
	 * @author fuxiao
	 * Date:2014年11月22日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions, 
									      User user) throws Exception{
		BaseResultSet rs = null;
		String sql = 
					"SELECT\n" +
					"   R.RETURN_ID, R.RETURN_NO, R.ORDER_DATE, R.ORDER_USER,R.CLOSE_DATE,\n" + 
					"   R.RETURN_STATUS, R.SUPPLIER_CODE, R.SUPPLIER_NAME,\n" + 
					"   R.SIGN_STAUTS, R.AMOUNT, R.PLAN_AMOUNT\n" + 
					"FROM PT_BU_PCH_RETURN R\n" + 
					"WHERE R.STATUS = 100201\n" + 
					" AND R.RETURN_STATUS <> 201101 AND " + conditions +
					" ORDER BY R.RETURN_ID";
		rs =  this.factory.select(sql, pageManager);
		rs.setFieldDic("RETURN_STATUS", "CGTHDZT");					 // 采购退货单状态
		rs.setFieldDic("SIGN_STAUTS", "THDYSZT");					 // 采购退货签收状态
		rs.setFieldDateFormat("ORDER_DATE", "yyyy-MM-dd HH:mm:ss");  // 退货单创建时间
		rs.setFieldDateFormat("CLOSE_DATE", "yyyy-MM-dd HH:mm:ss");  // 退货单关闭时间
		rs.setFieldUserID("ORDER_USER");
		return rs;
	}
	
	/**
	 * 
	 * queryList: 添加配件名称，配件代码模糊查询
	 * @author fuxiao
	 * Date:2014年11月14日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions, 
									 User user, boolean isSub) throws Exception{
		
		if(conditions.indexOf("PART_CODE") != -1 || conditions.indexOf("PART_NAME") != -1){
			conditions += " AND EXISTS (\n" +
							"    SELECT 1 FROM PT_BU_PCH_RETURN_DTL D WHERE D.RETURN_ID = R.RETURN_ID";
			if(conditions.indexOf("PART_NAME") != -1){
				String partNameSql = conditions.substring(conditions.indexOf("PART_NAME"), 
														  conditions.indexOf("'", conditions.indexOf("PART_NAME") + 15) + 1);
				conditions = conditions.replace(" AND "+partNameSql, "");
				conditions +=" AND " + partNameSql;
			}
			if(conditions.indexOf("PART_CODE") != -1){
				String partNameSql = conditions.substring(conditions.indexOf("PART_CODE"), 
														  conditions.indexOf("'", conditions.indexOf("PART_CODE") + 15) + 1);
				conditions = conditions.replace(" AND "+partNameSql, "");
				conditions +=" AND " + partNameSql;
			}
			conditions += " ) ";
		}
		
		// 判断用户是否为采供科，如果为采供科则只可查询到自己创建的采供订单
		if(user.getOrgCode().equals("XS10905")){
			conditions += " AND R.CREATE_USER = '" + user.getAccount() + "'";
		}
		
		return this.queryList(pageManager, conditions, user);
	}
	
	/**
	 * 
	 * queryInfoById:根据ID查询相信主信息
	 * @author fuxiao
	 * Date:2014年11月22日
	 *
	 */
	public QuerySet queryInfoById(String id) throws SQLException{
		String sql = 
				"SELECT\n" +
				"   R.RETURN_ID, R.RETURN_NO, " + 
				"   (SELECT T.PERSON_NAME FROM TM_USER T WHERE T.ACCOUNT = R.ORDER_USER) ORDER_USER," + 
				"   TO_CHAR(R.ORDER_DATE,'YYYY-MM-DD HH24:MI:SS') ORDER_DATE,\n" + 
				"   (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = R.RETURN_STATUS) RETURN_STATUS, R.SUPPLIER_CODE, R.SUPPLIER_NAME,\n" + 
				"   (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = R.SIGN_STAUTS) SIGN_STAUTS, R.AMOUNT, R.PLAN_AMOUNT\n" + 
				"FROM PT_BU_PCH_RETURN R\n" + 
				"WHERE R.STATUS = 100201\n" + 
				" AND R.RETURN_STATUS <> 201101 AND R.RETURN_ID = ?";
		return this.factory.select(new Object[]{ id }, sql);
	}
	
	/**
	 * 
	 * queryInfoDetailsById: 根据ID查询子表详细信息
	 * @author fuxiao
	 * Date:2014年11月22日
	 *
	 */
	public BaseResultSet queryInfoDetailsById(PageManager pageManage, 
										String conditions) throws Exception {
		String sql = 
				" SELECT\n" +
				"  D.DETAIL_ID, D.RETURN_ID, D.PART_ID, I.PART_NAME, I.PART_CODE,\n" + 
				"  D.RETURN_AMOUNT, D.ADJUST_REMARKS,\n" + 
				"  NVL(D.AMOUNT,0)AMOUNT, NVL(D.COUNT,0)COUNT, D.PLAN_AMOUNT, D.SUPPLIER_ID, D.SUPPLIER_CODE, D.SUPPLIER_NAME,\n" + 
				"  I.PCH_PRICE, I.PLAN_PRICE, D.POSITION_ID\n" + 
				" FROM PT_BU_PCH_RETURN_DTL D, PT_BA_INFO I\n" + 
				"WHERE D.PART_ID = I.PART_ID\n" + 
				" AND " + conditions + " ORDER BY DETAIL_ID";
		return this.factory.select(sql, pageManage);
		
	}

}
