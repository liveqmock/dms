package com.org.dms.dao.channel.channelInfo;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 账户异动查询Dao
 *
 * 账户异动查询
 * @author zhengyao
 * @date 2014-10-30
 * @version 1.0
 */
public class AccountChangeQueryDao extends BaseDAO {
	
    /**
     * 定义instance
     *
     * @param pActionContext
     * @return warehouseMngDao 账户异动查询Dao
     */
    public static final AccountChangeQueryDao getInstance(ActionContext pActionContext) {

    	AccountChangeQueryDao dao = new AccountChangeQueryDao();
        pActionContext.setDBFactory(dao.factory);
        return dao;
    }
	
	/**
	 * 
	 * queryList: 查询表单查询
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions, User user) throws Exception{
		BaseResultSet rs = null;
		pageManager.setFilter(conditions + " AND A.ACCOUNT_ID=B.ACCOUNT_ID AND B.ORG_ID = '"+user.getOrgId()+"' ORDER BY A.LOG_TYPE ");
		StringBuilder sql= new StringBuilder();
		sql.append("SELECT B.ACCOUNT_TYPE, A.LOG_TYPE,A.SOURCEACCOUNT_TYPE,A.IN_DATE, A.AMOUNT, A.CREATE_TIME, A.CREATE_USER\n" );
		sql.append("  FROM PT_BU_ACCOUNT_LOG A,PT_BU_ACCOUNT B\n" );

		rs = this.factory.select(sql.toString(), pageManager);
		// 账户类型
		rs.setFieldDic("ACCOUNT_TYPE", DicConstant.ZJZHLX);
		rs.setFieldDic("SOURCEACCOUNT_TYPE", DicConstant.ZJZHLX);
		// 操作类型
		rs.setFieldDic("LOG_TYPE", DicConstant.ZJYDLX);
		// 创建时间
		rs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldDateFormat("IN_DATE", "yyyy-MM-dd");
		// 创建人
		rs.setFieldUserID("CREATE_USER");
		return rs;
	}

	/**
     * 订单查询(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet downloadOem(String conditions,User user) throws Exception {
    	
    	String where = " WHERE "+conditions + " AND A.ACCOUNT_ID = B.ACCOUNT_ID AND B.ORG_ID = C.ORG_ID AND C.PID = D.ORG_ID AND A.CREATE_USER = E.ACCOUNT ORDER BY A.CREATE_TIME DESC\n";
		StringBuilder sql= new StringBuilder();
		sql.append("SELECT A.LOG_ID,\n" );
		sql.append("       D.CODE         AGENCY_CODE,\n" );
		sql.append("       D.ONAME        AGENCY_NAME,\n" );
		sql.append("       C.CODE         ORG_CODE,\n" );
		sql.append("       C.ONAME        ORG_NAME,\n" );
		sql.append("       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = B.ACCOUNT_TYPE) ACCOUNT_TYPE,\n" );
		sql.append("       A.ACCOUNT_ID,\n" );
		sql.append("       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = A.LOG_TYPE) LOG_TYPE,\n" );
		sql.append("       A.AMOUNT,\n" );
		sql.append("       (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = A.SOURCEACCOUNT_TYPE) SOURCEACCOUNT_TYPE,\n" );
		sql.append("       TO_CHAR(A.IN_DATE,'YYYY-MM-DD') IN_DATE,\n" );
		sql.append("       A.REMARKS,\n" );
		sql.append("       (SELECT PERSON_NAME FROM TM_USER WHERE ACCOUNT=A.CREATE_USER) CREATE_USER,\n" );
		sql.append("       TO_CHAR(A.CREATE_TIME,'YYYY-MM-DD HH24:MI:SS') CREATE_TIME\n" );
		sql.append("  FROM PT_BU_ACCOUNT_LOG A, PT_BU_ACCOUNT B, TM_ORG C, TM_ORG D, TM_USER E \n" );

        return factory.select(null, sql.toString()+where);
    }
	/**
	 * 
	 * queryList: 查询表单查询
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryListOem(PageManager pageManager, String conditions, User user) throws Exception{
		BaseResultSet rs = null;
		pageManager.setFilter(conditions + " AND A.ACCOUNT_ID = B.ACCOUNT_ID AND B.ORG_ID = C.ORG_ID AND C.PID = D.ORG_ID AND A.CREATE_USER = E.ACCOUNT ORDER BY A.CREATE_TIME DESC\n");
		StringBuilder sql= new StringBuilder();
		sql.append("SELECT A.LOG_ID,\n" );
		sql.append("       D.CODE         AGENCY_CODE,\n" );
		sql.append("       D.ONAME        AGENCY_NAME,\n" );
		sql.append("       C.CODE         ORG_CODE,\n" );
		sql.append("       C.ONAME        ORG_NAME,\n" );
		sql.append("       B.ACCOUNT_TYPE,\n" );
		sql.append("       A.ACCOUNT_ID,\n" );
		sql.append("       A.LOG_TYPE,\n" );
		sql.append("       A.AMOUNT,\n" );
		sql.append("       A.REMARKS,\n" );
		sql.append("       A.CREATE_USER,\n" );
		sql.append("       A.CREATE_TIME,\n" );
		sql.append("       A.SOURCEACCOUNT_TYPE,\n" );
		sql.append("       A.IN_DATE\n" );
		sql.append("  FROM PT_BU_ACCOUNT_LOG A, PT_BU_ACCOUNT B, TM_ORG C, TM_ORG D,TM_USER E\n" );
		rs = this.factory.select(sql.toString(), pageManager);
		// 账户类型
		rs.setFieldDic("ACCOUNT_TYPE", DicConstant.ZJZHLX);
		rs.setFieldDic("SOURCEACCOUNT_TYPE", DicConstant.ZJZHLX);
		// 操作类型
		rs.setFieldDic("LOG_TYPE", DicConstant.ZJYDLX);
		// 创建时间
		rs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldDateFormat("IN_DATE", "yyyy-MM-dd");
		// 创建人
		rs.setFieldUserID("CREATE_USER");
		return rs;
	}
}
