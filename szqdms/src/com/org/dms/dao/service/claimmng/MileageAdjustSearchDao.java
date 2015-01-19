package com.org.dms.dao.service.claimmng;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class MileageAdjustSearchDao extends BaseDAO
{
    //定义instance
    public  static final MileageAdjustSearchDao getInstance(ActionContext atx)
    {
    	MileageAdjustSearchDao dao = new MileageAdjustSearchDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 里程调整查询dealer
     * @param page
     * @param condition
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet claimSearch(PageManager page,String condition,User user)throws Exception{
    	String where = condition;
	       where += " AND T.ORG_ID = "+user.getOrgId()+"\n"+
	    		    " AND T.MILEAGE_APPLY_STATUS > 0 \n "+ 
	    	        " ORDER BY  T.CLAIM_NO ";
		page.setFilter(where);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.CLAIM_ID,\n" );
		sql.append("       T.CLAIM_NO,\n" );
		sql.append("       T.VIN,\n" );
		sql.append("       T.CLAIM_STATUS,\n" );
		sql.append("       T.APPLY_DATE,\n" );
		sql.append("       T.MILEAGE,\n" );
		sql.append("       T.ENSURE_MILEAGE,");
		sql.append("       T.MILEAGE_APPLY_STATUS,");
		sql.append("       T.MILEAGE_APPLY_REASON,");
		sql.append("       T.VEHICLE_ID");
		sql.append("  FROM SE_BU_CLAIM T");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_STATUS","SPDZT");
		bs.setFieldDic("MILEAGE_APPLY_STATUS","LCSQZT");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		return bs;
    }

    /**
     * 里程调整查询OEM
     * @param page
     * @param condition
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet claimOemSearch(PageManager page,String condition,User user)throws Exception{
    	String where = condition;
	       where += " AND T.MILEAGE_APPLY_STATUS > "+DicConstant.LCSQZT_01+" \n "+ 
	    	        " ORDER BY  T.CLAIM_NO ";
		page.setFilter(where);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.CLAIM_ID,\n" );
		sql.append("       T.CLAIM_NO,\n" );
		sql.append("       T.VIN,\n" );
		sql.append("       T.ORG_ID ORG_CODE,\n" );
		sql.append("       T.ORG_ID ORG_NAME,\n" );
		sql.append("       T.CLAIM_STATUS,\n" );
		sql.append("       T.APPLY_DATE,\n" );
		sql.append("       T.MILEAGE,\n" );
		sql.append("       T.ENSURE_MILEAGE,");
		sql.append("       T.MILEAGE_APPLY_STATUS,");
		sql.append("       T.MILEAGE_APPLY_REASON,");
		sql.append("       T.VEHICLE_ID");
		sql.append("  FROM SE_BU_CLAIM T");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_STATUS","SPDZT");
		bs.setFieldDic("MILEAGE_APPLY_STATUS","LCSQZT");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
		bs.setFieldOrgDeptCode("ORG_CODE");
		return bs;
    }
}
