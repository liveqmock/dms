package com.org.dms.dao.service.claimmng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuClaimCheckVO;
import com.org.dms.vo.service.SeBuClaimVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 里程调整审核DAO
 * @author zts
 *
 */
public class MileageAdjustCheckDao extends BaseDAO
{
    //定义instance
    public  static final MileageAdjustCheckDao getInstance(ActionContext atx)
    {
    	MileageAdjustCheckDao dao = new MileageAdjustCheckDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 索赔单查询
     * @param page
     * @param condition
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet claimSearch(PageManager page,String condition,User user)throws Exception{
    	String where = condition;
	       where += " AND T.VEHICLE_ID = V.VEHICLE_ID\n"+
	    		    " AND T.CLAIM_STATUS ="+DicConstant.SPDZT_05+" \n "+ //人工审核通过
	    		    " AND T.MILEAGE_APPLY_STATUS ="+DicConstant.LCSQZT_01+" \n "+// 里程申请状态(已申请)
	    		  	//" AND T.OEM_COMPANY_ID="+user.getOemCompanyId()+""+  
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
		sql.append("       T.ORG_ID ORG_NAME,\n" );
		sql.append("       T.ORG_ID ORG_CODE,\n" );
		sql.append("       T.ENSURE_MILEAGE,\n" );
		sql.append("       T.MILEAGE_APPLY_REASON,\n" );
		sql.append("       V.ENGINE_NO,\n" );
		sql.append("       V.MODELS_CODE,\n" );
		sql.append("       V.CERTIFICATE,\n" );
		sql.append("       V.ENGINE_TYPE,\n" );
		sql.append("       V.USER_TYPE,\n" );
		sql.append("       V.VEHICLE_USE,\n" );
		sql.append("       V.DRIVE_FORM,\n" );
		sql.append("       V.BUY_DATE,\n" );
		sql.append("       V.GUARANTEE_NO,\n" );
		sql.append("       V.FACTORY_DATE,\n" );
		sql.append("       V.MAINTENANCE_DATE\n" );
		sql.append("  FROM SE_BU_CLAIM T, MAIN_VEHICLE V");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_STATUS","SPDZT");
		bs.setFieldDic("USER_TYPE","CLYHLX");
		bs.setFieldDic("VEHICLE_USE","CLYT");
		bs.setFieldDic("DRIVE_FORM","QDXS");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("FACTORY_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("MAINTENANCE_DATE", "yyyy-MM-dd");
		bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
		bs.setFieldOrgDeptCode("ORG_CODE");
		return bs;
    }
    
    /**
     * 里程调整审核
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateClaim(SeBuClaimVO vo)throws Exception{
    	return factory.update(vo);
    }
    
    /**
     * 审核轨迹
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertCheck(SeBuClaimCheckVO vo)throws Exception{
    	return factory.insert(vo);
    }
    
    /**
     * 获取车辆ID ，确认里程
     * @param claimId
     * @return
     * @throws Exception
     */
    public QuerySet getClaim(String claimId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.VEHICLE_ID, T.ENSURE_MILEAGE FROM SE_BU_CLAIM T WHERE T.CLAIM_ID="+claimId+" ");
    	return factory.select(null, sql.toString());
    }
    
    /**
     * 车辆表更新里程
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateVehicle(String vehicleId,String ensureMileage,User user)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE MAIN_VEHICLE V\n" );
    	sql.append("   SET  V.LRUNKM    = V.MILEAGE,\n" );
    	sql.append("        V.MILEAGE   ="+ensureMileage+",\n" );
    	sql.append("       V.UPDATE_USER = '"+user.getAccount()+"',\n" );
    	sql.append("       V.UPDATE_TIME =SYSDATE \n" );
    	sql.append(" WHERE V.VEHICLE_ID = "+vehicleId+"");
    	return factory.update(sql.toString(), null);
    }
    
    /**
     * 审核轨迹查询
     * @param page
     * @param condition
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet hisCheckSearch(PageManager page,String claimId)throws Exception{
 	   BaseResultSet bs = null;
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT C.CHECK_USER, C.CHECK_DATE, C.CHECK_RESULT, C.CHECK_REMARKS\n" );
	   sql.append("  FROM SE_BU_CLAIM_CHECK C, SE_BU_CLAIM T\n" );
 	   sql.append(" WHERE C.CLAIM_ID = T.CLAIM_ID\n" );
 	   sql.append(" AND T.CLAIM_ID="+claimId+"\n" );
 	   sql.append(" ORDER BY C.CHECK_DATE ");
 	   bs=factory.select(sql.toString(), page);
 	   bs.setFieldDic("CHECK_RESULT","SPDZT");
 	   bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd HH:mm:ss");
 	   return bs;
    }
}
