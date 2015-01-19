package com.org.dms.dao.part.storageMng.search;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class DealerOutSearchMngDao extends BaseDAO{
	public static final DealerOutSearchMngDao getInstance(ActionContext pActionContext) {

		DealerOutSearchMngDao dao = new DealerOutSearchMngDao();
        pActionContext.setDBFactory(dao.factory);

        return dao;
    }
	/**
     * 订单查询(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet download(String pConditions,User pUser) throws Exception {
    	
    	String wheres = " WHERE " + pConditions;
        // 查询销售订单(审核通过,未发料)
        wheres += " AND A.ORDER_ID = B.ORDER_ID AND A.ORDER_STATUS <> '" + DicConstant.DDZT_01 + "'\n"
        		+ " AND A.ORDER_TYPE NOT IN ('"+DicConstant.DDLX_07+"','"+DicConstant.DDLX_09+"','"+DicConstant.DDLX_10+"') \n"
                + " AND A.STATUS = '" + DicConstant.YXBS_01 + "'\n"
                + " AND A.IF_CHANEL_ORDER = '" + DicConstant.SF_01 + "'\n";
		if(pUser.getOrgDept().getOrgType().equals(DicConstant.ZZLB_09) || pUser.getOrgDept().getOrgType().equals(DicConstant.ZZLB_10)){
			wheres += " AND A.WAREHOUSE_ID = " + pUser.getOrgId() + "\n";
		}  
		
        wheres += "  GROUP BY A.ORDER_ID, A.ORDER_NO, A.ORDER_TYPE, A.ORDER_STATUS, A.ORG_CODE, A.ORG_NAME,\n"
				+ "          A.WAREHOUSE_ID, A.WAREHOUSE_CODE, A.WAREHOUSE_NAME, A.ORDER_AMOUNT, A.APPLY_DATE,A.CLOSE_DATE\n"
                + " ORDER BY A.APPLY_DATE DESC\n";
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT A.ORDER_ID,\n" );
        sql.append("       A.ORDER_NO,\n" );
        sql.append("       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = A.ORDER_TYPE) ORDER_TYPE,\n" );
        sql.append("       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = A.ORDER_STATUS) ORDER_STATUS,\n" );
        sql.append("       A.ORG_CODE,\n" );
        sql.append("       A.ORG_NAME,\n" );
        sql.append("       A.WAREHOUSE_ID,\n" );
        sql.append("       A.WAREHOUSE_CODE,\n" );
        sql.append("       A.WAREHOUSE_NAME,\n" );
        sql.append("       A.ORDER_AMOUNT,\n" );
        sql.append("       A.APPLY_DATE,\n" );
        sql.append("       A.CLOSE_DATE,\n" );
        sql.append("       SUM(NVL(B.AUDIT_COUNT, 0) * B.UNIT_PRICE) SHOULD_AMOUNT\n" );
        sql.append("  FROM PT_BU_SALE_ORDER A, PT_BU_SALE_ORDER_DTL B");

        return factory.select(null, sql.toString()+wheres);
    }
	public BaseResultSet searchSaleOrder(PageManager pPageManager, User pUser, String pConditions) throws Exception {


        String wheres = pConditions;
        // 查询销售订单(审核通过,未发料)
        wheres += " AND A.ORDER_ID = B.ORDER_ID AND A.ORDER_STATUS <> '" + DicConstant.DDZT_01 + "'\n"
        		+ " AND A.ORDER_TYPE NOT IN ('"+DicConstant.DDLX_07+"','"+DicConstant.DDLX_09+"','"+DicConstant.DDLX_10+"') \n"
                + " AND A.STATUS = '" + DicConstant.YXBS_01 + "'\n"
                + " AND A.IF_CHANEL_ORDER = '" + DicConstant.SF_01 + "'\n";
       
		if(pUser.getOrgDept().getOrgType().equals(DicConstant.ZZLB_09) || pUser.getOrgDept().getOrgType().equals(DicConstant.ZZLB_10)){
			wheres += " AND A.WAREHOUSE_ID = " + pUser.getOrgId() + "\n";
		}       
        
		wheres += "  GROUP BY A.ORDER_ID, A.ORDER_NO, A.ORDER_TYPE, A.ORDER_STATUS, A.ORG_CODE, A.ORG_NAME,\n"
				+ "          A.WAREHOUSE_ID, A.WAREHOUSE_CODE, A.WAREHOUSE_NAME, A.ORDER_AMOUNT, A.APPLY_DATE,A.CLOSE_DATE\n"
                + " ORDER BY A.APPLY_DATE DESC\n";
        pPageManager.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT A.ORDER_ID,\n" );
        sql.append("       A.ORDER_NO,\n" );
        sql.append("       A.ORDER_TYPE,\n" );
        sql.append("       A.ORDER_STATUS,\n" );
        sql.append("       A.ORG_CODE,\n" );
        sql.append("       A.ORG_NAME,\n" );
        sql.append("       A.WAREHOUSE_ID,\n" );
        sql.append("       A.WAREHOUSE_CODE,\n" );
        sql.append("       A.WAREHOUSE_NAME,\n" );
        sql.append("       A.ORDER_AMOUNT,\n" );
        sql.append("       A.APPLY_DATE,\n" );
        sql.append("       A.CLOSE_DATE,\n" );
        sql.append("       SUM(NVL(B.AUDIT_COUNT, 0) * B.UNIT_PRICE) SHOULD_AMOUNT\n" );
        sql.append("  FROM PT_BU_SALE_ORDER A, PT_BU_SALE_ORDER_DTL B");
        // 执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), pPageManager);
        bs.setFieldDic("ORDER_TYPE", DicConstant.DDLX);
        // 申请日期绑定
        bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("CLOSE_DATE", "yyyy-MM-dd");

        return bs;
    }

}
