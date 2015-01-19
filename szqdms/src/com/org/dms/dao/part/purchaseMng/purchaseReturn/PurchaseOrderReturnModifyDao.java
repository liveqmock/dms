package com.org.dms.dao.part.purchaseMng.purchaseReturn;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPchReturnDtlVO;
import com.org.dms.vo.part.PtBuPchReturnVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PurchaseOrderReturnModifyDao extends BaseDAO{
	 public  static final PurchaseOrderReturnModifyDao getInstance(ActionContext atx)
	    {
		 PurchaseOrderReturnModifyDao dao = new PurchaseOrderReturnModifyDao();
	        atx.setDBFactory(dao.factory);
	        return dao;
	    }
	 /**
	  * 
	  * @date()2014年7月26日上午11:51:22
	  * @author Administrator
	  * @to_do:采购退货单调整查询
	  * @param page
	  * @param user
	  * @param conditions
	  * @return
	  * @throws Exception
	  */
	 public BaseResultSet returnOrderSearch(PageManager page, User user, String conditions) throws Exception
	    {
	    	String wheres = conditions;
	    	wheres +="AND T.STATUS = "+DicConstant.YXBS_01+"\n" +
	    					"AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
	    					"AND T.COMPANY_ID = "+user.getCompanyId()+"\n" + 
	    					"AND T.IS_AGREED = "+DicConstant.SF_02+"\n" +
	    					"AND T.RETURN_STATUS IN( "+DicConstant.CGTHDZT_02+","+DicConstant.CGTHDZT_03+")\n" ;
	        page.setFilter(wheres);
	    	BaseResultSet bs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.RETURN_ID,\n" );
	    	sql.append("       T.RETURN_NO,\n" );
	    	sql.append("       T.PURCHASE_NO,\n" );
	    	sql.append("       T.PURCHASE_ID,\n" );
	    	sql.append("       T.IS_AGREED,\n" );
	    	sql.append("       T.ADVICE,\n" );
	    	sql.append("       T.SUPPLIER_CODE,\n" );
	    	sql.append("       T.SUPPLIER_NAME,\n" );
	    	sql.append("	   T.SUPPLIER_ID,");
	    	sql.append("       T.RETURN_TYPE,\n" );
	    	sql.append("       T.ORDER_DATE,\n" );
	    	sql.append("       T.ORDER_USER\n" );
	    	sql.append("  FROM PT_BU_PCH_RETURN T");
	    	bs = factory.select(sql.toString(), page);
			bs.setFieldDic("RETURN_TYPE", "CGTHLX");
			bs.setFieldDateFormat("ORDER_DATE", "yyyy-MM-dd");
			bs.setFieldUserID("ORDER_USER");
	    	return bs;
	    }
	 /**
	  * 
	  * @date()2014年7月30日上午9:21:15
	  * @author Administrator
	  * @to_do:采购退货单配件查询
	  * @param page
	  * @param user
	  * @param RETURN_ID
	  * @return
	  * @throws Exception
	  */
	 public BaseResultSet returnPartSearch(PageManager page, User user, String RETURN_ID) throws Exception
	    {
	    	BaseResultSet bs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.DETAIL_ID,\n" );
	    	sql.append("       T.PURCHASE_ID,\n" );
	    	sql.append("       T.PART_ID,\n" );
	    	sql.append("       T.PART_CODE,\n" );
	    	sql.append("       T.PART_NAME,\n" );
	    	sql.append("       T1.UNIT,\n" );
	    	sql.append("       T1.MIN_UNIT,\n" );
	    	sql.append("       T1.MIN_PACK,\n" );
	    	sql.append("       T.PCH_PRICE,\n" );
	    	sql.append("       T.COUNT,\n" );
	    	sql.append("       T.AMOUNT,\n" );
	    	sql.append("       T.REMARKS\n" );
	    	sql.append("  FROM PT_BU_PCH_RETURN_DTL T, PT_BA_INFO T1\n" );
	    	sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
	    	sql.append("   AND T.RETURN_ID = "+RETURN_ID+"");
	    	bs = factory.select(sql.toString(), page);
	    	bs.setFieldDic("UNIT", "JLDW");
	    	bs.setFieldDic("MIN_UNIT", "JLDW");
	    	return bs;
	    }
	 /**
	  * 
	  * @date()2014年7月30日上午9:38:48
	  * @author Administrator
	  * @to_do:调整完成
	  * @param vo
	  * @return
	  * @throws Exception
	  */
	 public boolean updateReturn(PtBuPchReturnVO vo)throws Exception {
		 return factory.update(vo);
	 }
	 /**
	  * 查询对应退货单中的每件总价，个数，计划总价。
	  * @param RETURN_ID
	  * @return
	  * @throws Exception
	  */
	 public QuerySet queryAmount(String RETURN_ID) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT SUM(T.AMOUNT) AMOUNT,SUM(T.COUNT) COUNT,SUM(T.PLAN_AMOUNT) PLAN_AMOUNT FROM PT_BU_PCH_RETURN_DTL T WHERE T.RETURN_ID ="+RETURN_ID+"\n" );
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 /**
	  * 
	  * @date()2014年7月30日上午9:38:48
	  * @author Administrator
	  * @to_do:明细保存
	  * @param vo
	  * @return
	  * @throws Exception
	  */
	 public boolean updateCount(PtBuPchReturnDtlVO vo)throws Exception {
		 return factory.update(vo);
	 }
	 /**
	  * 
	  * @date()2014年7月30日上午9:43:57
	  * @author Administrator
	  * @to_do:获取退货单、退货明细单退货数量 退货金额 计划总金额
	  * @param DETAIL_ID
	  * @return
	  * @throws Exception
	  */
	 public QuerySet geOldtPartAmount(String DETAIL_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.COUNT,\n" );
	    	sql.append("       T.AMOUNT,\n" );
	    	sql.append("       T.PLAN_AMOUNT,\n" );
	    	sql.append("       T1.PLAN_AMOUNT R_PLAN_AMOUNT,\n" );
	    	sql.append("       T1.COUNT  R_COUNT,\n" );
	    	sql.append("       T1.AMOUNT     R_AMOUNT,\n" );
	    	sql.append("       T1.RETURN_ID\n" );
	    	sql.append("  FROM PT_BU_PCH_RETURN_DTL T, PT_BU_PCH_RETURN T1\n" );
	    	sql.append(" WHERE 1 = 1\n" );
	    	sql.append("   AND T.RETURN_ID = T1.RETURN_ID\n" );
	    	sql.append("   AND DETAIL_ID = "+DETAIL_ID+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 /**
	  * 
	  * @date()2014年7月30日上午9:44:43
	  * @author Administrator
	  * @to_do:删除配件
	  * @param DETAIL_ID
	  * @return
	  * @throws Exception
	  */
	 public boolean delParts(String DETAIL_ID ) throws Exception
	    {
			StringBuffer sql= new StringBuffer();
			sql.append("DELETE FROM PT_BU_PCH_RETURN_DTL WHERE DETAIL_ID = "+DETAIL_ID+"");
	    	return factory.update(sql.toString(), null);
	    }

	 public QuerySet getOld(String RETURN_ID,String DETAIL_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.COUNT, T2.PCH_PRICE, T.AMOUNT, T.PLAN_AMOUNT, T2.PLAN_PRICE\n" );
	    	sql.append("  FROM PT_BU_PCH_RETURN_DTL      T,\n" );
	    	sql.append("       PT_BU_PCH_RETURN          T1,\n" );
	    	sql.append("       PT_BU_PCH_ORDER_SPLIT_DTL T2\n" );
	    	sql.append(" WHERE T.RETURN_ID = T1.RETURN_ID\n" );
	    	sql.append("   AND T.PURCHASE_ID = T2.SPLIT_ID\n" );
	    	sql.append("   AND T.PART_ID = T2.PART_ID");
	    	sql.append("  AND T.RETURN_ID ="+RETURN_ID+"\n" );
	    	sql.append("  AND T.DETAIL_ID ="+DETAIL_ID+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 public void updateOrderPart(String DETAIL_ID,String RETURN_ID,String COUNT,String NEW_PCH_AMOUNT,String NEW_PLAN_AMOUNT,String REMARKS,User user) throws Exception {
	    	StringBuffer sql = new StringBuffer();
	    	sql.append("UPDATE PT_BU_PCH_RETURN_DTL SET REMARKS='" + REMARKS + "',COUNT="+COUNT+",AMOUNT= "+NEW_PCH_AMOUNT+",PLAN_AMOUNT= "+NEW_PLAN_AMOUNT+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE DETAIL_ID ="+DETAIL_ID+" AND RETURN_ID ="+RETURN_ID+"\n");
	       factory.update(sql.toString(),null);
	    }
	 public void updatePurAmount(String RETURN_ID,String PLAN_AMOUNT,String NEW_PLAN_AMOUNT,String PCH_AMOUNT,String NEW_PCH_AMOUNT,String PCH_COUNT,String COUNT,User user) throws Exception {
	    	StringBuffer sql = new StringBuffer();
	    	sql.append("UPDATE PT_BU_PCH_RETURN SET AMOUNT=AMOUNT-"+PCH_AMOUNT+"+"+NEW_PCH_AMOUNT+",PLAN_AMOUNT = PLAN_AMOUNT-"+PLAN_AMOUNT+"+"+NEW_PLAN_AMOUNT+",COUNT=COUNT-"+PCH_COUNT+"+"+COUNT+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE RETURN_ID = "+RETURN_ID+"\n");
	       factory.update(sql.toString(),null);
	    }
}
