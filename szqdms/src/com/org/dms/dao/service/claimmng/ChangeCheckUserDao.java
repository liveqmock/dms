package com.org.dms.dao.service.claimmng;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class ChangeCheckUserDao extends BaseDAO
{
    //定义instance
    public  static final ChangeCheckUserDao getInstance(ActionContext atx)
    {
    	ChangeCheckUserDao dao = new ChangeCheckUserDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    /**
     * 索赔单转让查询
     * @param page
     * @param conditions
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet checkUserSearch(PageManager page,String conditions,User user)throws Exception{
    	BaseResultSet bs=null;
    	String wheres= conditions;
    	       wheres+=" AND U.USER_ACCOUNT = T.OPERATE_USER \n"+
    	    		   " AND T.STATUS = 100201\n"+
    	    		   " AND T.CLAIM_STATUS = 301003\n"+
    	    		   " AND U.USER_TYPE =300101 \n"+
    	    		   " AND T.REJECT_DATE IS NULL\n"+
    	    		   //" AND T.CLAIM_TYPE IN (301401, 301403, 301408)\n"+
    	    		   " AND T.CLAIM_TYPE <> 301409 \n"+
					   " AND NVL(T.STOCK_MEET, 0) <> 100102\n" +
					   " AND (SELECT COUNT(1)\n" + 
					   "         FROM SE_BU_CLAIM_FAULT_PART P\n" + 
					   "        WHERE P.CLAIM_ID = T.CLAIM_ID\n" + 
					   "          AND P.FAULT_TYPE = 301601\n" + 
					   "          AND P.SUPPLIER_OPTION_STATUS IS NULL) = 0 \n"+
    	    		   " GROUP BY T.OPERATE_USER, U.USER_NAME \n"+
    	    		   " ORDER BY T.OPERATE_USER";
    	page.setFilter(wheres);
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT COUNT(T.CLAIM_ID) CLAIM_COUNT,\n" );
        sql.append("       MIN(T.APPLY_DATE) APPLY_DATE,--最早提报日期\n" );
        sql.append("       T.OPERATE_USER,\n" );
        sql.append("       U.USER_NAME\n" );
        sql.append("  FROM SE_BU_CLAIM T, SE_BA_CHECK_USER U\n" );
        bs=factory.select(sql.toString(), page);
        return bs;
    }
    
    /**
     * 获取索赔单ID
     * @param account
     * @return
     * @throws Exception
     */
    public QuerySet getClaimInfo(String account)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.CLAIM_ID\n" );
    	sql.append("  FROM SE_BU_CLAIM T\n" );
    	sql.append(" WHERE T.STATUS = 100201\n" );
    	sql.append("   AND T.CLAIM_STATUS = 301003\n" );
    	sql.append("   AND T.REJECT_DATE IS NULL\n" );
    	//sql.append("   AND T.CLAIM_TYPE IN (301401, 301403,301408) \n");
    	sql.append("   AND T.CLAIM_TYPE <> 301409 \n");
    	sql.append("   AND NVL(T.STOCK_MEET, 0) <> 100102\n" );
    	sql.append("   AND (SELECT COUNT(1)\n" );
    	sql.append("        FROM SE_BU_CLAIM_FAULT_PART P\n" );
    	sql.append("       WHERE P.CLAIM_ID = T.CLAIM_ID\n" );
    	sql.append("         AND P.FAULT_TYPE = 301601\n" );
    	sql.append("         AND P.SUPPLIER_OPTION_STATUS IS NULL) = 0");
    	sql.append("   AND T.OPERATE_USER='"+account+"'");
    	return factory.select(null, sql.toString());
    }    
    /**
     * 获取有效审核
     * @param account
     * @return
     * @throws Exception
     */
    public QuerySet getCheckUserInfo(String account)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SEQ_NO,T.USER_ACCOUNT\n" );
    	sql.append("  FROM SE_BA_CHECK_USER T\n" );
    	sql.append(" WHERE T.IF_DISTRIB = 100101\n" );
    	sql.append("   AND T.USER_ACCOUNT <> '"+account+"'\n");
    	sql.append("   AND T.USER_TYPE = "+DicConstant.CLYHLX_01+"\n");
    	sql.append(" ORDER BY T.SEQ_NO");
    	return factory.select(null, sql.toString());
    }  
    
    /**
     * 更新索赔单审核人
     * @param claimId
     * @param account
     * @return
     * @throws Exception
     */
    public boolean updateClaim(String claimId,String account)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE SE_BU_CLAIM T SET T.OPERATE_USER = '"+account+"' WHERE T.CLAIM_ID = "+claimId+"");
    	return factory.update(sql.toString(), null);
    }
}
