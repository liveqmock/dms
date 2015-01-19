package com.org.dms.dao.service.claimmng;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class ClaimReApplySearchDao extends BaseDAO
{
    //定义instance
    public  static final ClaimReApplySearchDao getInstance(ActionContext atx)
    {
    	ClaimReApplySearchDao dao = new ClaimReApplySearchDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * dealer 重新提单查询
     * @throws Exception
     */
    public BaseResultSet claimSearch(PageManager page,String conditions,User user)throws Exception{
    	String where = conditions;
		       where += " AND T.VEHICLE_ID=V.VEHICLE_ID \n" +
		    		    " AND T.REAPPLY_STATUS > 0 \n "+
		    		    " AND T.ORG_ID = "+user.getOrgId()+" \n "+
		    	        " ORDER BY  T.CLAIM_NO ";
		page.setFilter(where);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.CLAIM_ID,\n" );
		sql.append("       T.CLAIM_NO,\n" );
		sql.append("       T.CLAIM_STATUS,\n" );
		sql.append("       T.APPLY_DATE,\n" );
		sql.append("       T.CHECK_OPINION,\n" );
		sql.append("       T.REAPPLY_STATUS,\n" );
		sql.append("       T.RECHECK_DATE,\n" );
		sql.append("       T.VIN\n" );
		sql.append("  FROM SE_BU_CLAIM T ,MAIN_VEHICLE V");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_STATUS","SPDZT");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("RECHECK_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDic("REAPPLY_STATUS","CXSQZT");
		return bs;
    }
    
    /**
     * oem 重新提单查询
     * @throws Exception
     */
    public BaseResultSet claimOemSearch(PageManager page,String conditions,User user)throws Exception{
    	String where = conditions;
		       where += " AND T.VEHICLE_ID=V.VEHICLE_ID \n" +
		    		    " AND T.REAPPLY_STATUS > "+DicConstant.CXSQZT_01+" \n "+
		    	        " ORDER BY  T.CLAIM_NO ";
		page.setFilter(where);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.CLAIM_ID,\n" );
		sql.append("       T.CLAIM_NO,\n" );
		sql.append("       T.ORG_ID ORG_CODE,\n" );
		sql.append("       T.ORG_ID ORG_NAME,\n" );
		sql.append("       T.CLAIM_STATUS,\n" );
		sql.append("       T.APPLY_DATE,\n" );
		sql.append("       T.CHECK_OPINION,\n" );
		sql.append("       T.REAPPLY_STATUS,\n" );
		sql.append("       T.RECHECK_DATE,\n" );
		sql.append("       T.VIN\n" );
		sql.append("  FROM SE_BU_CLAIM T ,MAIN_VEHICLE V");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_STATUS","SPDZT");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("RECHECK_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDic("REAPPLY_STATUS","CXSQZT");
		bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
    	bs.setFieldOrgDeptCode("ORG_CODE");
		return bs;
    }
}
