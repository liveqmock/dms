package com.org.dms.dao.service.claimmng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuClaimVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * 重新提单申请
 * @author zts
 *
 */
public class ClaimReApplyDao extends BaseDAO
{
    //定义instance
    public  static final ClaimReApplyDao getInstance(ActionContext atx)
    {
    	ClaimReApplyDao dao = new ClaimReApplyDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    /**
     * 索赔单查询 审核拒绝
     * @param page
     * @param condition
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet claimSearch(PageManager page,String condition,User user)throws Exception{
    	String where = condition;
    	       where += " AND T.ORG_ID = "+user.getOrgId()+"\n" +
    	    		    " AND T.CLAIM_STATUS ="+DicConstant.SPDZT_07+" \n "+ //索赔单状态 是审核拒绝
    	    		    //索赔单重新申请状态 不是已申请、审核通过
    	    		    //" AND NVL(T.REAPPLY_STATUS,0) NOT IN ("+DicConstant.CXSQZT_01+","+DicConstant.CXSQZT_03+") \n "+ 
    	    	        " ORDER BY  T.CLAIM_NO ";
		page.setFilter(where);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.CLAIM_ID,\n" );
		sql.append("       T.CLAIM_NO,\n" );
		sql.append("       T.CLAIM_STATUS,\n" );
		sql.append("       T.APPLY_DATE,\n" );
		sql.append("       T.RECHECK_DATE,\n");
		sql.append("       T.REAPPLY_STATUS,\n");
		sql.append("       T.CHECK_OPINION\n" );
		sql.append("  FROM SE_BU_CLAIM T");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_STATUS","SPDZT");
		bs.setFieldDic("REAPPLY_STATUS","CXSQZT");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("RECHECK_DATE", "yyyy-MM-dd HH:mm:ss");
		return bs;
    }

    /**
     * 审核查询
     * @param page
     * @param condition
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet checkSearch(PageManager page,String condition,User user)throws Exception{
    	String where = condition;
	       where += " AND C.CLAIM_ID = T.CLAIM_ID\n" +
	    	        " ORDER BY  T.CHECK_DATE ";
		page.setFilter(where);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT C.CLAIM_NO,\n" );
		sql.append("       T.CHECK_USER,\n" );
		sql.append("       T.CHECK_DATE,\n" );
		sql.append("       T.CHECK_RESULT,\n" );
		sql.append("       T.CHECK_REMARKS\n" );
		sql.append("  FROM SE_BU_CLAIM C, SE_BU_CLAIM_CHECK T");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("CHECK_RESULT","SPDZT");
		bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd HH:mm:ss");
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
}
