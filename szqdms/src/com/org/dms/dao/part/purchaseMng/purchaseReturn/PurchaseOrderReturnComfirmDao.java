package com.org.dms.dao.part.purchaseMng.purchaseReturn;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPchReturnVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PurchaseOrderReturnComfirmDao extends BaseDAO{
	public  static final PurchaseOrderReturnComfirmDao getInstance(ActionContext atx)
    {
		PurchaseOrderReturnComfirmDao dao = new PurchaseOrderReturnComfirmDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	/**
	 * 
	 * @date()2014年7月26日上午11:02:39
	 * @author Administrator
	 * @to_do:退货单主表查询，通过退货单对应的供应商组织ID关联查询
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet returnOrderSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += "AND T.SUPPLIER_ID = T1.SUPPLIER_ID\n" + 
    					"AND T.STATUS = "+DicConstant.YXBS_01+"\n" +
    					"AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
    					//"AND T.COMPANY_ID = "+user.getCompanyId()+"\n" + 
    					"AND T1.ORG_ID = "+user.getOrgId()+" AND T1.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" + 
    					"AND T.RETURN_STATUS = "+DicConstant.CGTHDZT_02+"\n" ;
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.RETURN_ID,\n" );
    	sql.append("       T.RETURN_NO,\n" );
    	sql.append("       T.PURCHASE_NO,\n" );
    	sql.append("       T.PURCHASE_ID,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.RETURN_TYPE,\n" );
    	sql.append("       T.ORDER_DATE,\n" );
    	sql.append("       T.ORDER_USER\n" );
    	sql.append("  FROM PT_BU_PCH_RETURN T,PT_BA_SUPPLIER T1");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("RETURN_TYPE", "CGTHLX");
		bs.setFieldDateFormat("ORDER_DATE", "yyyy-MM-dd");
		bs.setFieldUserID("ORDER_USER");
    	return bs;
    }
	/**
	 * 
	 * @date()2014年7月26日上午11:01:58
	 * @author Administrator
	 * @to_do:退货单配查询
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
    	sql.append("	AND T.RETURN_ID = "+RETURN_ID+"");
    	sql.append("	AND T.COUNT>0");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("MIN_UNIT", "JLDW");
    	return bs;
    }
	/**
	 * 
	 * @date()2014年7月26日上午10:58:08
	 * @author Administrator
	 * @to_do:采购订单确认
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updatePurchaseOrderSplit(PtBuPchReturnVO vo)
    		throws Exception
    {
    	return factory.update(vo);
    }

}
