package com.org.dms.dao.service.claimmng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuClaimCheckVO;
import com.org.dms.vo.service.SeBuClaimVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * 重新提单审核DAO
 * @author zts
 *
 */
public class ClaimReApplyCheckDao  extends BaseDAO
{
    //定义instance
    public  static final ClaimReApplyCheckDao getInstance(ActionContext atx)
    {
    	ClaimReApplyCheckDao dao = new ClaimReApplyCheckDao();
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
    	       where += " AND T.CLAIM_STATUS ="+DicConstant.SPDZT_07+" \n "+ //索赔单状态 是审核拒绝
    	    		    " AND T.VEHICLE_ID=V.VEHICLE_ID \n" +
    	    		    " AND T.REAPPLY_STATUS = "+DicConstant.CXSQZT_01+" \n "+
    	    		   // " AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+" \n "+
    	    	        " ORDER BY  T.CLAIM_NO ";
		page.setFilter(where);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.CLAIM_ID,\n" );
		sql.append("       T.CLAIM_NO,\n" );
		sql.append("       T.ORG_ID        ORG_CODE,\n" );
		sql.append("       T.ORG_ID        ORG_NAME,\n" );
		sql.append("       T.CLAIM_STATUS,\n" );
		sql.append("       T.APPLY_DATE,\n" );
		sql.append("       T.REAPPLY_REASON,\n" );
		sql.append("       T.CHECK_OPINION,\n" );
		sql.append("       T.VIN,\n" );
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
		sql.append("  FROM SE_BU_CLAIM T ,MAIN_VEHICLE V");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_STATUS","SPDZT");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
    	bs.setFieldOrgDeptCode("ORG_CODE");
    	bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("FACTORY_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("MAINTENANCE_DATE", "yyyy-MM-dd");
		bs.setFieldDic("USER_TYPE","CLYHLX");
		bs.setFieldDic("VEHICLE_USE","CLYT");
		bs.setFieldDic("DRIVE_FORM","QDXS");
		return bs;
    }
    
    /**
     * 重新提单审核
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
