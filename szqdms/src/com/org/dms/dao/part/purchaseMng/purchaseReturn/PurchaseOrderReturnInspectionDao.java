package com.org.dms.dao.part.purchaseMng.purchaseReturn;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPchReturnVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PurchaseOrderReturnInspectionDao extends BaseDAO{
	public  static final PurchaseOrderReturnInspectionDao getInstance(ActionContext atx)
    {
		PurchaseOrderReturnInspectionDao dao = new PurchaseOrderReturnInspectionDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	public BaseResultSet returnOrderSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += "AND T.SUPPLIER_ID = T1.SUPPLIER_ID\n" + 
    					"AND T.RETURN_ID = T2.ORDER_ID\n" + 
    					"AND T2.OUT_STATUS = "+DicConstant.CKDZT_02+"\n" +
    					"AND T.SIGN_STAUTS = "+DicConstant.THDYSZT_02+"\n" +
    					"AND T.STATUS = "+DicConstant.YXBS_01+"\n" +
    					"AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
    					//"AND T.COMPANY_ID = "+user.getCompanyId()+"\n" + 
    					"AND T1.ORG_ID = "+user.getOrgId()+" AND T1.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" + 
    					"AND T.RETURN_STATUS = "+DicConstant.CGTHDZT_04+"\n" ;
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.RETURN_ID,\n" );
    	sql.append("       T.RETURN_NO,\n" );
    	sql.append("       T.PURCHASE_NO,\n" );
    	sql.append("       T2.OUT_NO,\n" );
    	sql.append("       T.PURCHASE_ID,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.RETURN_TYPE,\n" );
    	sql.append("       T.ORDER_DATE,\n" );
    	sql.append("       T.ORDER_USER\n" );
    	sql.append("  FROM PT_BU_PCH_RETURN T,PT_BA_SUPPLIER T1,PT_BU_STOCK_OUT T2");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("RETURN_TYPE", "CGTHLX");
		bs.setFieldDateFormat("ORDER_DATE", "yyyy-MM-dd");
		bs.setFieldUserID("ORDER_USER");
    	return bs;
    }
	
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
    	sql.append("       T1.MIN_PACK,\n" );
    	sql.append("       T1.MIN_UNIT,\n" );
    	sql.append("       T.COUNT,\n" );
    	sql.append("       T.PCH_PRICE,\n" );
    	sql.append("       T.AMOUNT,\n" );
    	sql.append("       T.REMARKS\n" );
    	sql.append("  FROM PT_BU_PCH_RETURN_DTL T, PT_BA_INFO T1\n" );
    	sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("	AND T.RETURN_ID = "+RETURN_ID+"");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("MIN_UNIT", "JLDW");
    	return bs;
    }
	
	public boolean updateReturn(PtBuPchReturnVO vo)
    		throws Exception
    {
    	return factory.update(vo);
    }

}
