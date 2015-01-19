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
 * 旧件装箱DAO
 * @author zts
 *
 */
public class OldPartPackPJGLDao extends BaseDAO{

	 //定义instance
   public  static final OldPartPackPJGLDao getInstance(ActionContext atx)
   {
	   OldPartPackPJGLDao dao = new OldPartPackPJGLDao();
       atx.setDBFactory(dao.factory);
       return dao;
   }
   
   // 返回DBFactory
   public DBFactory getDBFactory(){
	   return factory;
   }
   
	/**
	 * 查询需要装箱的索赔配件
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public BaseResultSet searchPartOnBoxList(PageManager page, String conditions,
			User user) throws SQLException {
		StringBuffer sb  = new StringBuffer();
		
		//  拼接查询条件
		sb.append(conditions);
		sb.append("  AND T.RETURN_DATE IS NULL AND T.STATUS = '" + DicConstant.YXBS_01 +"'" + 
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
	
	/**
	 * 异步保存BoxNo
	 * @param applyId
	 * @param boxNo
	 * @return
	 * @throws SQLException
	 */
	public boolean savaAsynnBoxNo(String applyId, String boxNo, User user) throws SQLException{
		String sql = "UPDATE PT_BU_CLAIM_APPLY T SET T.BOX_NO = ?, T.UPDATE_TIME = SYSDATE, T.UPDATE_USER = ? WHERE T.APPLY_ID = ?";
		return this.factory.update(sql, new Object[]{boxNo, user.getAccount() ,applyId});
	}
	
	/**
	 * 下载导出数据
	 * 
	 * @param condition
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public QuerySet downloadExcelData(String condition,  User user) throws SQLException{
		StringBuffer sb  = new StringBuffer();
		sb.append("SELECT ROWNUM, APPLY_ID,\n"+ 
				"		  BOX_NO,\n"+
		        "		  APPLY_NO,\n" +
				"         PART_CODE,\n" + 
				"         PART_NAME,\n" + 
				"         CLAIM_COUNT,\n" + 
				"         ORG_CODE,\n" + 
				"         ORG_NAME,\n" + 
				"         SUPPLIER_CODE,\n" + 
				"         SUPPLIER_NAME,\n" + 
				"         FAULT_CONDITONS\n" + 
				"         FROM PT_BU_CLAIM_APPLY T\n where ");
		//  拼接查询条件
		sb.append(condition);
		sb.append("  AND T.RETURN_DATE IS NULL AND T.STATUS = '" + DicConstant.YXBS_01 +"'" + 
				  "  AND T.APPLY_STATUS = '"+DicConstant.PJSBSPZT_05+"'" + 
				  "  AND ( T.ORG_ID = " + user.getOrgId() + " OR "
				  + "      EXISTS ( SELECT 1 FROM SE_BA_RETURN_DEALER_RELATION SBD WHERE SBD.R_ORGID = " + user.getOrgId() + " AND SBD.D_ORGID = T.ORG_ID )"
				  + ")");
		sb.append("  ORDER BY T.APPLY_NO DESC ");
		
		// 预留分页的语句
		/*String sql = "SELECT * FROM (SELECT S.*, ROWNUM ROWNUMS FROM ( " + sb.toString() +
					 " ) S WHERE ROWNUM <= ?) Q WHERE ROWNUMS > ?";*/
		return this.factory.select(null, sb.toString());
	}
	
	/**
	 * 根据当前账户查询导入临时表中的数据
	 * 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public QuerySet searchImportDataByAccount(User user) throws SQLException{
		return this.factory.select(new Object[]{user.getAccount()}, "SELECT APPLY_ID, APPLY_NO, BOX_NO, USER_ACCOUNT, ROW_NO FROM PT_BU_CLAIM_APPLY_TMP WHERE USER_ACCOUNT = ?");
	}
	
	/**
	 * 根据ID查询数量
	 * @param applyId
	 * @return
	 * @throws SQLException
	 */
	public QuerySet searchPartDataByApplyId(int applyId) throws SQLException{
		return this.factory.select(new Object[]{applyId}, "SELECT COUNT(1) CLAIM_APPLY_COUNT FROM PT_BU_CLAIM_APPLY T WHERE T.APPLY_ID = ?");
	}
	
	/**
	 * 根据账户查询导入数据
	 * 
	 * @param page
	 * @param user
	 * @return
	 * @throws Exception
	 */
   public BaseResultSet searchImportList(PageManager page,User user)throws Exception{
   	String sql =    "SELECT TT.APPLY_ID,\n" +
		    			"       TMP.BOX_NO,\n" + 
		    			"       TT.APPLY_NO,\n" + 
		    			"       TT.PART_CODE,\n" + 
		    			"       TT.PART_NAME,\n" + 
		    			"       TT.CLAIM_COUNT,\n" + 
		    			"       TT.ORG_CODE,\n" + 
		    			"       TT.ORG_NAME,\n" + 
		    			"       TT.SUPPLIER_CODE,\n" + 
		    			"       TT.SUPPLIER_NAME,\n" + 
		    			"       TT.FAULT_CONDITONS\n" + 
		    			"  FROM PT_BU_CLAIM_APPLY_TMP TMP, PT_BU_CLAIM_APPLY TT\n" + 
		    			" WHERE TMP.APPLY_ID = TT.APPLY_ID\n" + 
		    			"   AND USER_ACCOUNT = '"+user.getAccount()+"'";
	    return factory.select(sql, page);
   }
   
   
   /**
    * 更新保存结果
    * @param user
    * @return
    * @throws SQLException
    */
   public boolean updateImportSuccessData(User user) throws SQLException{
   	return this.factory.update("UPDATE PT_BU_CLAIM_APPLY TAB "
   	    	+ " SET TAB.BOX_NO = (SELECT TMP.BOX_NO FROM PT_BU_CLAIM_APPLY_TMP TMP WHERE TMP.USER_ACCOUNT = ? AND TAB.APPLY_ID = TMP.APPLY_ID)"
   			+ " , TAB.UPDATE_TIME = SYSDATE, TAB.UPDATE_USER = ?"
   	    	+ "  WHERE EXISTS (SELECT 1 FROM PT_BU_CLAIM_APPLY_TMP TMP WHERE TMP.USER_ACCOUNT = ? AND TAB.APPLY_ID = TMP.APPLY_ID)", new Object[]{user.getAccount(),user.getAccount(),user.getAccount()});
   }
   
}
