package com.org.dms.dao.service.claimmng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuClaimVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * 里程调整申请DAO
 * @author zts
 *
 */
public class MileageAdjustDao extends BaseDAO
{
    //定义instance
    public  static final MileageAdjustDao getInstance(ActionContext atx)
    {
    	MileageAdjustDao dao = new MileageAdjustDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    /**
     * 索赔单查询  索赔单状态为 人工审核通过的
     * @param page
     * @param condition
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet claimSearch(PageManager page,String condition,User user)throws Exception{
    	String where = condition;
	       where += " AND T.VEHICLE_ID = V.VEHICLE_ID\n"+
	    		   	" AND T.ORG_ID = "+user.getOrgId()+"\n"+
	    		    " AND T.CLAIM_STATUS = "+DicConstant.SPDZT_05+" \n "+ //人工审核通过
	    		   	//里程调整状态不是已申请、审核通过
	    		    " AND NVL(T.MILEAGE_APPLY_STATUS,0) NOT IN ("+DicConstant.LCSQZT_01+","+DicConstant.LCSQZT_03+") \n "+ 
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
		return bs;
    }
    
    /**
     * 索赔单修改
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateClaim(SeBuClaimVO vo)throws Exception{
    	return factory.update(vo);
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
