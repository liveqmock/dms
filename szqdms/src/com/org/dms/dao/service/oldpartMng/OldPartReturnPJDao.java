package com.org.dms.dao.service.oldpartMng;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 旧件回运DAO
 * @author fuxiao
 *
 */
public class OldPartReturnPJDao extends BaseDAO{
	 //定义instance
	  public  static final OldPartReturnPJDao getInstance(ActionContext atx)
	  {
		  OldPartReturnPJDao dao = new OldPartReturnPJDao();
	      atx.setDBFactory(dao.factory);
	      return dao;
	  }
	  
	  /**
	   * 配件管理-旧件回运数据查询
	   * @param page
	   * @param conditions
	   * @param user
	   * @return
	   * @throws SQLException
	   */
	  public BaseResultSet searchReturnBoxList(PageManager page, String conditions, User user) throws SQLException {
			StringBuffer sb  = new StringBuffer();
			
			//  拼接查询条件
			sb.append(conditions);
			sb.append("  AND T.BOX_NO IS NOT NULL AND T.RETURN_DATE IS NULL");
			sb.append("  AND T.STATUS = '" + DicConstant.YXBS_01 +"'" + 
					  "  AND T.APPLY_STATUS = '"+DicConstant.PJSBSPZT_05+"'" + 
					  "  AND ( T.ORG_ID = " + user.getOrgId() + " OR "
					  + "      EXISTS ( SELECT 1 FROM SE_BA_RETURN_DEALER_RELATION SBD WHERE SBD.R_ORGID = " + user.getOrgId() + " AND SBD.D_ORGID = T.ORG_ID )"
					  + ")");
			sb.append("  ORDER BY T.APPLY_NO DESC ");
			page.setFilter(sb.toString());
			
			// 生成查询SQL
			sb.delete(0, sb.length());
			sb.append("SELECT APPLY_ID,\n"+ 
					"		  BOX_NO,\n"+
					"         RETURN_DATE,\n"+
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
	   * 更新回运时间
	   * 
	   * @param applyIds
	   * @param user
	   * @return
	   * @throws SQLException 
	   */
	  public boolean updatePartReturnDate(String applyIds, User user) throws SQLException{
		  return this.factory.update("UPDATE PT_BU_CLAIM_APPLY T SET T.RETURN_DATE = SYSDATE,T.UPDATE_TIME = SYSDATE,T.UPDATE_USER = ? WHERE T.APPLY_ID IN (" + applyIds + ")", new Object[]{user.getAccount()});
	  }
}
