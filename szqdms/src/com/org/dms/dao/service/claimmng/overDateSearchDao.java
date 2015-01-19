package com.org.dms.dao.service.claimmng;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 超单日期查询DAO
 * @author 	zts
 *
 */
public class overDateSearchDao extends BaseDAO
{
    //定义instance
    public  static final overDateSearchDao getInstance(ActionContext atx)
    {
    	overDateSearchDao dao = new overDateSearchDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    /**
     * 超单日期查询
     * @param page
     * @return
     * @throws Exception
     */
    public BaseResultSet claimSearch(PageManager page,User user)throws Exception{
    	BaseResultSet bs=null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT D.OVERDUE_DAYS,\n" );
    	sql.append("       C.CLAIM_NO,\n" );
    	sql.append("       C.CLAIM_TYPE,\n" );
    	sql.append("       C.FAULT_DATE,\n" );
    	sql.append("       C.APPLY_DATE,\n" );
    	sql.append("       C.OVERDUE_DAYS CLAIM_OVERDUE_DAYS\n" );
    	sql.append("  FROM MAIN_DEALER D, SE_BU_CLAIM C\n" );
    	sql.append("WHERE D.ORG_ID = C.ORG_ID\n" );
    	sql.append("  AND C.ORG_ID = "+user.getOrgId()+"\n" );
    	sql.append("  AND C.IF_OVERDUE="+DicConstant.SF_01+"");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDic("CLAIM_TYPE", "SPDLX");
    	bs.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd");
    	bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
}
