package com.org.dms.dao.service.claimmng;


import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuClaimCheckVO;
import com.org.dms.vo.service.SeBuClaimVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class ClaimLeaderCheckDao extends BaseDAO
{
    //定义instance
    public  static final ClaimLeaderCheckDao getInstance(ActionContext atx)
    {
    	ClaimLeaderCheckDao dao = new ClaimLeaderCheckDao();
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
	       where += " AND T.WORK_ID = O.WORK_ID \n "+
	    		    " AND T.CLAIM_STATUS="+DicConstant.SPDZT_09+" \n"+  //转领导审批
	       			" ORDER BY O.WORK_NO ,T.OPERATE_USER ";
		page.setFilter(where);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.CLAIM_ID,\n" );
		sql.append("       T.CLAIM_NO,\n" ); 
		sql.append("       O.WORK_ID,\n" );
		sql.append("       O.WORK_NO,\n" );
		sql.append("       T.CLAIM_TYPE,\n" );
		sql.append("       T.CLAIM_STATUS,\n" );
		sql.append("       T.APPLY_DATE\n" );
		sql.append("  FROM SE_BU_CLAIM T, SE_BU_WORK_ORDER O\n" );
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_STATUS","SPDZT");
		bs.setFieldDic("CLAIM_TYPE", "SPDLX");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		return bs;
    }
    
    /**
     * 索赔单修改
     * @param vo
     * @return 
     * @throws Exception
     */
    public boolean claimCheckUpdae(SeBuClaimVO vo)throws Exception{
    	return factory.update(vo);
    }
    
    /**
     * 插入审核轨迹
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertCheck(SeBuClaimCheckVO vo)throws Exception{
    	return factory.insert(vo);
    }
}
