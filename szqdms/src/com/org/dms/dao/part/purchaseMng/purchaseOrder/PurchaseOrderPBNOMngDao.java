package com.org.dms.dao.part.purchaseMng.purchaseOrder;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PurchaseOrderPBNOMngDao extends BaseDAO{
	public  static final PurchaseOrderPBNOMngDao getInstance(ActionContext atx)
    {
		PurchaseOrderPBNOMngDao dao = new PurchaseOrderPBNOMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public BaseResultSet orderSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND T.STATUS = "+DicConstant.YXBS_01+" AND T.PLAN_DISTRIBUTION IS NULL \n" +
    	                "AND T.SUPPLIER_CODE LIKE 'SQGH%' \n" +
    					"AND T.PURCHASE_TYPE IN ("+DicConstant.CGDDLX_01+","+DicConstant.CGDDLX_02+","+DicConstant.CGDDLX_03+","+DicConstant.CGDDLX_06+")"+
    					"AND T.ORDER_STATUS IN ("+DicConstant.CGDDZT_03+","+DicConstant.CGDDZT_04+" )\n" + 
    					"ORDER BY T.CREATE_TIME DESC";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SPLIT_ID,\n" );
    	sql.append("       T.SPLIT_NO,\n" );
    	sql.append("       T.PURCHASE_TYPE,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("	   T.SUPPLIER_ID,");
    	sql.append("       T.SELECT_MONTH,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.APPLY_USER\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT T\n");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("PURCHASE_TYPE", "CGDDLX");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
		bs.setFieldUserID("APPLY_USER");
    	return bs;
    }
	public boolean updatePurchaseOrder(PtBuPchOrderSplitVO vo)
    		throws Exception
    {
    	return factory.update(vo);
    }

}
