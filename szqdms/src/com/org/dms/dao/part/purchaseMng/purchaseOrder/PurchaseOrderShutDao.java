package com.org.dms.dao.part.purchaseMng.purchaseOrder;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PurchaseOrderShutDao extends BaseDAO{
	
	public static final PurchaseOrderShutDao getInstance(ActionContext atx)
	{
		PurchaseOrderShutDao dao = new PurchaseOrderShutDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}
	/**
	 * 
	 * @date()2014年7月18日上午9:42:05
	 * @author Administrator
	 * @to_do:合同关闭
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet orderSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +=" AND T.CREATE_USER = '"+user.getAccount()+"' AND T.SUPPLIER_ID = T1.SUPPLIER_ID AND T1.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" +
    					"   AND T.ORG_ID = "+user.getOrgId()+"\n" + 
    					"   AND T.COMPANY_ID = "+user.getCompanyId()+"\n" + 
    					"   AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
    					//"	AND EXISTS(SELECT 1 FROM PT_BU_PCH_ORDER_SPLIT_DTL T1 WHERE T.SPLIT_ID = T1.SPLIT_ID AND NVL(T1.ACCEPT_COUNT, 0) = NVL(T1.STORAGE_COUNT, 0))"+
    					"   AND T.ORDER_STATUS IN( "+DicConstant.CGDDZT_02+","+DicConstant.CGDDZT_03+","+DicConstant.CGDDZT_04+") ORDER BY T.APPLY_DATE DESC ";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SPLIT_ID,\n" );
    	sql.append("       T.PURCHASE_ID,\n" );
    	sql.append("       T.SPLIT_NO,\n" );
    	sql.append("       T.SELECT_MONTH,\n" );
    	sql.append("       T.PURCHASE_TYPE,\n" );
    	sql.append("       T.APPLY_USER,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.SUPPLIER_CODE\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT T, PT_BA_SUPPLIER T1");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("PURCHASE_TYPE", DicConstant.CGDDLX);
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
		bs.setFieldUserID("APPLY_USER");
    	return bs;
    }
	public boolean shutDownOrder(PtBuPchOrderSplitVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	 public QuerySet checkAll(String SPLIT_ID) throws Exception {
        QuerySet qs = null;
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT 1 FROM PT_BU_PCH_ORDER_SPLIT WHERE SPLIT_ID = "+SPLIT_ID+" AND NVL(ACCEPT_COUNT,0)<>NVL(STORAGE_COUNT,0)");
        qs = factory.select(null, sql.toString());
        return qs;
    }
}

