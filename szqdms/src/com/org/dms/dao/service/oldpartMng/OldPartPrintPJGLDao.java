package com.org.dms.dao.service.oldpartMng;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.DBFactory;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 配件管理->旧件标签打印Dao
 * 
 * @author fuxiao
 * @version 1.0
 * @time 2014-09-03
 *
 */
public class OldPartPrintPJGLDao extends BaseDAO {

	public DBFactory getFactory(){
		return factory;
	}
	/**
	 * 获取Dao
	 * 
	 * @param atx
	 * @return
	 */
	public static final OldPartPrintPJGLDao getInstance(ActionContext atx) {
		OldPartPrintPJGLDao dao = new OldPartPrintPJGLDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 查询需要打印的索赔配件
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public BaseResultSet searchPartList(PageManager page, String conditions,
			User user) throws SQLException {
		StringBuffer sb  = new StringBuffer();
		
		//  拼接查询条件
		sb.append(conditions);
		sb.append("  AND T.STATUS = '" + DicConstant.YXBS_01 +"'\n" + 
				  "  AND T.APPLY_STATUS = '"+DicConstant.PJSBSPZT_04+"'\n" + 
				  "  AND T.ORG_ID =" + user.getOrgId() + "\n ");
		sb.append("  ORDER BY T.APPLY_NO DESC ");
		page.setFilter(sb.toString());
		
		// 生成查询SQL
		sb.delete(0, sb.length());
		sb.append("SELECT APPLY_ID,\n"+ 
		        "		  APPLY_NO,\n" +
				"         PART_CODE,\n" + 
				"         PART_NAME,\n" + 
				"         CLAIM_COUNT,\n" + 
				"         ORG_CODE,\n" + 
				"         ORG_NAME,\n" + 
				"         SUPPLIER_CODE,\n" + 
				"         SUPPLIER_NAME,\n" + 
				"         FAULT_CONDITONS\n" + 
				"         FROM PT_BU_CLAIM_APPLY T\n");
		
		return this.factory.select(sb.toString(), page);
	}
	
	/**
	 * 根据ID查询详细信息
	 * 
	 * @param applyId
	 * @return
	 * @throws SQLException 
	 */
	public QuerySet queryPartBarCode(String applyId) throws SQLException{

		String sqlString = "SELECT APPLY_NO,\n" +
						"       PART_CODE,\n" + 
						"       PART_NAME,\n" + 
						"       CLAIM_COUNT,\n" + 
						"       ORG_CODE,\n" + 
						"       ORG_NAME,\n" + 
						"       SUPPLIER_CODE,\n" + 
						"       SUPPLIER_NAME,\n" + 
						"       FAULT_CONDITONS\n" + 
						"  FROM PT_BU_CLAIM_APPLY T\n" + 
						" WHERE T.APPLY_ID = ?";
		return this.factory.select(new Object[]{applyId}, sqlString);
	}

}
