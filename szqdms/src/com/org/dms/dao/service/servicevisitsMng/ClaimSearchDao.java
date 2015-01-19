package com.org.dms.dao.service.servicevisitsMng;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * 索赔单查询
 * @author zts
 *
 */
public class ClaimSearchDao extends BaseDAO
{
    //定义instance
    public  static final ClaimSearchDao getInstance(ActionContext atx)
    {
    	ClaimSearchDao dao = new ClaimSearchDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

   /**
    * 索赔单查询
    * @param page
    * @param user
    * @param conditions
    * @return
    * @throws SQLException
    */
    public BaseResultSet claimSearch(PageManager page, User user, String conditions) throws SQLException{
    	
    	String wheres = conditions;
    	wheres   +=" AND T.WORK_ID = O.WORK_ID\n"+
    			   " AND T.CLAIM_STATUS="+DicConstant.SPDZT_05+"\n"+
    			   " ORDER BY T.CLAIM_NO\n";
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.CLAIM_ID,\n" );
    	sql.append("       T.CLAIM_NO,\n" );
    	sql.append("       T.CLAIM_TYPE,\n" );
    	sql.append("       T.CLAIM_STATUS,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       O.WORK_ID,\n" );
    	sql.append("       O.WORK_NO\n" );
    	sql.append("  FROM SE_BU_CLAIM T, SE_BU_WORK_ORDER O\n" );
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDic("CLAIM_STATUS", "SPDZT");
    	bs.setFieldDic("CLAIM_TYPE", "SPDLX");
    	return bs;
    }

    /**
	 * 索赔单下载
	 * @return
	 * @throws Exception
	 */
	public QuerySet download(String conditions,User user)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.CLAIM_ID,\n" );
		sql.append("       T.CLAIM_NO,\n" );
		sql.append("       T.CLAIM_STATUS,\n" );
		sql.append("       TO_CHAR(T.APPLY_DATE,'YYYY-DD-MM HH:MI:SS') APPLY_DATE,\n" );
		sql.append("       O.WORK_ID,\n" );
		sql.append("       O.WORK_NO,\n" );
		sql.append("       D.DIC_VALUE CLAIM_TYPE\n" );
		sql.append("  FROM SE_BU_CLAIM T, SE_BU_WORK_ORDER O,DIC_TREE D\n" );
		sql.append(" WHERE "+conditions+" \n" );
		sql.append("   AND T.WORK_ID = O.WORK_ID\n" );
		sql.append("   AND T.CLAIM_TYPE=D.ID\n" );
		sql.append("   AND T.CLAIM_STATUS="+DicConstant.SPDZT_05+"\n" );
		sql.append(" ORDER BY T.CLAIM_NO");
	    return factory.select(null, sql.toString());
	}
}
