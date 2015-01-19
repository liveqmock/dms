package com.org.dms.dao.service.oldpartMng;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuReturnOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 旧件回运DAO
 * @author zts
 *
 */
public class OldPartReturnDao extends BaseDAO{
	 //定义instance
	  public  static final OldPartReturnDao getInstance(ActionContext atx)
	  {
		  OldPartReturnDao dao = new OldPartReturnDao();
	      atx.setDBFactory(dao.factory);
	      return dao;
	  }
	  
	  /**
	   * 回运单回运查询
	   * @param page
	   * @param conditions
	   * @param user
	   * @return
	   * @throws SQLException
	   */
	  public BaseResultSet oldPartSearch(PageManager page ,String conditions,User user) throws SQLException{
		   String wheres = conditions;
		          wheres +=" AND O.ORDER_STATUS IN ("+DicConstant.HYDZT_03+") \n "+
				           " AND O.STATUS= "+DicConstant.YXBS_01+" ";
						/*   " AND EXISTS (SELECT 1\n" +
						   "          FROM SE_BA_RETURN_DEALER_RELATION R\n" + 
						   "         WHERE R.R_ORGID = "+user.getOrgId()+"\n" + 
						   "           AND R.D_ORGID = O.ORG_ID)";*/
		   page.setFilter(wheres);
		   BaseResultSet bs = null;
		   StringBuffer sql= new StringBuffer();
		   sql.append("SELECT O.ORDER_ID,\n" );
		   sql.append("       O.ORDER_NO,\n" );
		   sql.append("       O.PRODUCE_DATE,\n" );
		   sql.append("       O.RETURN_DATE,\n" );
		   sql.append("       O.TRANS_TYPE,\n" );
		   sql.append("       O.ORG_ID ORG_CODE,\n" );
		   sql.append("       O.ORG_ID ORG_NAME,\n" );
		   sql.append("       O.AMOUNT,\n" );
		   sql.append("       O.REMARKS,\n" );
		   sql.append("       NVL((SELECT NVL(SUM(D.OUGHT_COUNT), 0)\n" );
		   sql.append("             FROM SE_BU_RETURNORDER_DETAIL D\n" );
		   sql.append("            WHERE D.ORDER_ID = O.ORDER_ID\n" );
		   sql.append("            GROUP BY D.ORDER_ID),\n" );
		   sql.append("           0) AS PARTCOUNT,\n" );
		   sql.append("       NVL((SELECT NVL(COUNT(A.CLAIM_ID), 0)\n" );
		   sql.append("             FROM (SELECT DISTINCT D.ORDER_ID, D.CLAIM_ID\n" );
		   sql.append("                     FROM SE_BU_RETURNORDER_DETAIL D) A\n" );
		   sql.append("            WHERE A.ORDER_ID = O.ORDER_ID\n" );
		   sql.append("            GROUP BY A.ORDER_ID),\n" );
		   sql.append("           0) AS CLAIMCOUNT\n" );
		   sql.append("  FROM SE_BU_RETURN_ORDER O");
		   bs=factory.select(sql.toString(), page);
		   bs.setFieldDic("TRANS_TYPE","HYDYSFS");
		   bs.setFieldDateFormat("PRODUCE_DATE", "yyyy-MM");
		   bs.setFieldDateFormat("RETURN_DATE", "yyyy-MM-dd");
		   bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
		   bs.setFieldOrgDeptCode("ORG_CODE");
		   return bs;
	  }
	  /**
	   * 通过回运单ID查询回运单
	   * @param orderId
	   * @return
	   * @throws Exception
	   */
	  public BaseResultSet oldPartByOrderId(String orderId) throws Exception
	  {
		  StringBuffer sql= new StringBuffer();
		  sql.append("SELECT O.ORDER_ID,\n" );
		  sql.append("       O.ORDER_NO,\n" );
		  sql.append("       O.PRODUCE_DATE,\n" );
		  sql.append("       O.RETURN_DATE,\n" );
		  sql.append("       O.TRANS_TYPE,\n" );
		  sql.append("       O.ORG_ID ORG_CODE,\n" );
		  sql.append("       O.ORG_ID ORG_NAME,\n" );
		  sql.append("       O.AMOUNT,\n" );
		  sql.append("       O.REMARKS\n" );
		  sql.append("  FROM SE_BU_RETURN_ORDER O");
		  sql.append(" WHERE O.ORDER_ID="+orderId+"");
		  return factory.select(sql.toString(), new PageManager());
	   }
	  
	  
	  /***
	    * 查询已回运的清单
	    * @param page
	    * @param orderId
	    * @return
	    * @throws Exception
	    */
	   public BaseResultSet returnPartSearch(PageManager page,String orderId,String conditions)throws Exception{
		   page.setFilter("");
		   BaseResultSet bs = null;
		   StringBuffer sql= new StringBuffer();
		   sql.append("SELECT D.DETAIL_ID,\n" );
		   sql.append("       D.PART_ID,\n" );
		   sql.append("       D.PART_CODE,\n" );
		   sql.append("       D.PART_NAME,\n" );
		   sql.append("       D.PART_AMOUNT,\n" );
		   sql.append("       D.CLAIM_NO,\n" );
		   sql.append("       D.VEHICLE_ID,\n" );
		   sql.append("       D.VIN,\n" );
		   sql.append("       D.MILEAGE,\n" );
		   sql.append("       NVL(D.OUGHT_COUNT,0) OUGHT_COUNT,\n" );
		   sql.append("       NVL(D.MISS_COUNT,0) ACTUL_COUNT,\n" );
		   sql.append("       D.BOX_NO,\n" );
		   sql.append("       D.PROSUPPLY_ID,\n" );
		   sql.append("       D.PROSUPPLY_CODE,\n" );
		   sql.append("       D.PROSUPPLY_NAME,\n" );
		   sql.append("       D.DUTYSUPPLY_ID,\n" );
		   sql.append("       D.DUTYSUPPLY_CODE,\n" );
		   sql.append("       D.DUTYSUPPLY_NAME,\n" );
		   sql.append("       D.BROKEN_REASON,\n" );
		   sql.append("       D.REMARKS\n" );
		   sql.append("  FROM SE_BU_RETURNORDER_DETAIL D");
		   sql.append(" WHERE "+conditions+" AND D.ORDER_ID = "+orderId+"");
		   bs=factory.select(sql.toString(), page);
		   return bs;
	   }
	  /**
	   * 旧件回运
	   * @param vo
	   * @return
	   * @throws Exception
	   */
	   public boolean updateOldPartReturn(SeBuReturnOrderVO vo) throws Exception{
		   return factory.update(vo);
	   }
	   
	   /**
	    * 下载回运单
	    * @param produceDate
	    * @return
	    * @throws Exception
	    */
	   public QuerySet download() throws Exception{
		   QuerySet qs = null;
		   StringBuffer sql= new StringBuffer();
		   sql.append("SELECT ROWNUM,\n" );
		   sql.append("       D.CLAIM_NO,\n" );
		   sql.append("       F.FAULT_CODE,\n" );
		   sql.append("       D.VIN,\n" );
		   sql.append("       '' AS BOX_NO,\n" );
		   sql.append("       D.ORDER_NO,\n" );
		   sql.append("       D.PART_CODE,\n" );
		   sql.append("       D.PART_NAME,\n" );
		   sql.append("       D.PROSUPPLY_CODE,\n" );
		   sql.append("       D.PROSUPPLY_NAME\n" );
		   sql.append("  FROM SE_BU_RETURNORDER_DETAIL D,\n" );
		   sql.append("       SE_BU_CLAIM_FAULT        F,\n" );
		   sql.append("       SE_BU_RETURN_ORDER       R\n" );
		   sql.append(" WHERE R.ORDER_ID = D.ORDER_ID\n" );
		   sql.append("   AND D.CLAIM_DTL_ID = F.CLAIM_DTL_ID\n" );
		   sql.append("   AND R.ORDER_STATUS = "+DicConstant.HYDZT_03+"");//审核通过的
		   qs = factory.select(null, sql.toString());
		   return qs;
	   }
	   
	   /**
	    * 索赔单号、故障代码、配件代码、供应商代码、回运单号 数据一致性
	    * @param user
	    * @return
	    * @throws Exception
	    */
	   public QuerySet checkList1(User user)throws Exception{
		   StringBuffer sql= new StringBuffer();
		   sql.append("SELECT T.ROW_NUM,\n" );
		   sql.append("       T.CLAIM_NO,\n" );
		   sql.append("       T.PROSUPPLY_CODE,\n" );
		   sql.append("       T.FAULT_CODE,\n" );
		   sql.append("       T.ORDER_NO,\n" );
		   sql.append("       T.PART_CODE\n" );
		   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP T\n" );
		   sql.append(" WHERE NOT EXISTS (SELECT 1\n" );
		   sql.append("          FROM SE_BU_RETURNORDER_DETAIL D,\n" );
		   sql.append("               SE_BU_CLAIM              C,\n" );
		   sql.append("               SE_BU_CLAIM_FAULT        F,\n" );
		   sql.append("               PT_BA_SUPPLIER           S,\n" );
		   sql.append("               PT_BA_INFO               I,\n" );
		   sql.append("               SE_BU_RETURN_ORDER       O\n" );
		   sql.append("         WHERE T.CLAIM_NO = C.CLAIM_NO\n" );
		   sql.append("           AND T.FAULT_CODE = F.FAULT_CODE\n" );
		   sql.append("           AND C.CLAIM_ID = F.CLAIM_ID\n" );
		   sql.append("           AND T.PROSUPPLY_CODE = S.SUPPLIER_CODE\n" );
		   sql.append("           AND I.PART_CODE = T.PART_CODE\n" );
		   sql.append("           AND T.ORDER_NO = O.ORDER_NO\n" );
		   sql.append("           AND T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		   sql.append("           AND D.ORDER_ID = O.ORDER_ID\n" );
		   sql.append("           AND D.PART_ID = I.PART_ID\n" );
		   sql.append("           AND D.CLAIM_DTL_ID = F.CLAIM_DTL_ID\n" );
		   sql.append("   		  AND O.ORDER_STATUS = "+DicConstant.HYDZT_03+"\n");//审核通过的
		   sql.append("           AND D.PROSUPPLY_ID = S.SUPPLIER_ID)");
		   return factory.select(null, sql.toString());
	   }
	   /**
	    * 校验箱号不能为空
	    * @param user
	    * @return
	    * @throws Exception
	    */
	   public QuerySet checkList2(User user)throws Exception{
		   StringBuffer sql= new StringBuffer();
		   sql.append("SELECT T.ROW_NUM\n" );
		   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP T\n" );
		   sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		   sql.append("   AND T.BOX_NO IS NULL\n");
		   sql.append(" ORDER BY T.ROW_NUM");
		   return factory.select(null, sql.toString());
	   }
	   /**
	    * 校验导入的数据是否重复
	    * @param user
	    * @return
	    * @throws Exception
	    */
	   public QuerySet checkList3(User user)throws Exception{
		   StringBuffer sql= new StringBuffer();
		   sql.append("SELECT P.CLAIM_NO,\n" );
		   sql.append("       P.FAULT_CODE,\n" );
		   sql.append("       P.PART_CODE,\n" );
		   sql.append("       P.PROSUPPLY_CODE,\n" );
		   sql.append("       P.ORDER_NO,\n" );
		   sql.append("       COUNT(P.TMP_ID)\n" );
		   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP P\n" );
		   sql.append(" WHERE P.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		   sql.append(" GROUP BY P.CLAIM_NO,\n" );
		   sql.append("          P.FAULT_CODE,\n" );
		   sql.append("          P.PART_CODE,\n" );
		   sql.append("          P.PROSUPPLY_CODE,\n" );
		   sql.append("          P.ORDER_NO\n" );
		   sql.append("HAVING COUNT(P.TMP_ID) > 1");
		   return factory.select(null, sql.toString());
	   }
	   /**
	    * 导入查询
	    * @param page
	    * @param user
	    * @return
	    * @throws Exception
	    */
	   public BaseResultSet searchImport(PageManager page,User user)throws Exception{
		   page.setFilter("");
		   BaseResultSet bs = null;
		   StringBuffer sql= new StringBuffer();
		   sql.append("SELECT D.* \n" );
		   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP D");
		   sql.append(" WHERE D.USER_ACCOUNT ='"+user.getAccount()+"'");
		   bs=factory.select(sql.toString(), page);
		   return bs;
	   }
	   
	   /**
	    * 旧件集中点更新装箱字段
	    * @param user
	    * @return
	    * @throws Exception
	    */
	   public boolean updateOrderDetail(User user)throws Exception{
		   StringBuffer sql= new StringBuffer();
		   sql.append("MERGE INTO SE_BU_RETURNORDER_DETAIL D\n" );
		   sql.append("USING (SELECT C.CLAIM_ID,\n" );
		   sql.append("              F.CLAIM_DTL_ID,\n" );
		   sql.append("              O.ORDER_ID,\n" );
		   sql.append("              I.PART_ID,\n" );
		   sql.append("              S.SUPPLIER_ID,\n" );
		   sql.append("              T.BOX_NO,\n" );
		   sql.append("              T.PROSUPPLY_CODE\n" );
		   sql.append("         FROM SE_BU_RETURNORDER_DTL_TMP T,\n" );
		   sql.append("              SE_BU_CLAIM               C,\n" );
		   sql.append("              SE_BU_CLAIM_FAULT         F,\n" );
		   sql.append("              PT_BA_SUPPLIER            S,\n" );
		   sql.append("              PT_BA_INFO                I,\n" );
		   sql.append("              SE_BU_RETURN_ORDER        O\n" );
		   sql.append("        WHERE T.CLAIM_NO = C.CLAIM_NO\n" );
		   sql.append("          AND T.FAULT_CODE = F.FAULT_CODE\n" );
		   sql.append("          AND C.CLAIM_ID = F.CLAIM_ID\n" );
		   sql.append("          AND T.PROSUPPLY_CODE = S.SUPPLIER_CODE\n" );
		   sql.append("          AND I.PART_CODE = T.PART_CODE\n" );
		   sql.append("          AND T.ORDER_NO = O.ORDER_NO\n" );
		   sql.append("          AND T.USER_ACCOUNT = '"+user.getAccount()+"') T1\n" );
		   sql.append("ON (D.ORDER_ID = T1.ORDER_ID AND D.CLAIM_ID = T1.CLAIM_ID AND D.PART_ID = T1.PART_ID AND D.PROSUPPLY_ID = T1.SUPPLIER_ID AND D.CLAIM_DTL_ID = T1.CLAIM_DTL_ID)\n" );
		   sql.append("WHEN MATCHED THEN\n" );
		   sql.append("  UPDATE SET D.BOX_NO = T1.BOX_NO");
		   return factory.update(sql.toString(), null);
	   }
}
