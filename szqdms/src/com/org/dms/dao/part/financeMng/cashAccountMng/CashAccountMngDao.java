package com.org.dms.dao.part.financeMng.cashAccountMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuAccountLogVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class CashAccountMngDao extends BaseDAO{
    public  static final CashAccountMngDao getInstance(ActionContext atx)
    {
        CashAccountMngDao dao = new CashAccountMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    public QuerySet searchDtl (String orgCode,User user) throws Exception {
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.CASH_ACCOUNT_ID,\n" );
        sql.append("       A.CASH_ACCOUNT_TYPE,\n" );
        sql.append("       A.CASH_AVAILABLE_AMOUNT,\n" );
        sql.append("       A.CASH_BALANCE_AMOUNT,\n" );
        sql.append("       A.CASH_OCCUPY_AMOUNT,\n" );
        sql.append("       A.ORG_ID,\n" );
        sql.append("       A.ORG_NAME,\n" );
        sql.append("       A.ORG_CODE,\n" );
        sql.append("       B.ACCEPT_ACCOUNT_ID,\n" );
        sql.append("       B.ACCEPT_ACCOUNT_TYPE,\n" );
        sql.append("       B.ACCEPT_AVAILABLE_AMOUNT,\n" );
        sql.append("       B.ACCEPT_BALANCE_AMOUNT,\n" );
        sql.append("       B.ACCEPT_OCCUPY_AMOUNT,\n" );
        sql.append("       C.MATERIAL_ACCOUNT_ID,\n" );
        sql.append("       C.MATERIAL_ACCOUNT_TYPE,\n" );
        sql.append("       C.MATERIAL_AVAILABLE_AMOUNT,\n" );
        sql.append("       C.MATERIAL_BALANCE_AMOUNT,\n" );
        sql.append("       C.MATERIAL_OCCUPY_AMOUNT,\n" );
        sql.append("       D.CREDIT_ACCOUNT_ID,\n" );
        sql.append("       D.CREDIT_ACCOUNT_TYPE,\n" );
        sql.append("       D.CREDIT_AVAILABLE_AMOUNT,\n" );
        sql.append("       D.CREDIT_BALANCE_AMOUNT,\n" );
        sql.append("       D.CREDIT_OCCUPY_AMOUNT\n," );
        sql.append("       E.REBATE_ACCOUNT_ID,\n" );
        sql.append("       E.REBATE_ACCOUNT_TYPE,\n" );
        sql.append("       E.REBATE_AVAILABLE_AMOUNT,\n" );
        sql.append("       E.REBATE_BALANCE_AMOUNT,\n" );
        sql.append("       E.REBATE_OCCUPY_AMOUNT\n" );
        sql.append("  FROM (SELECT T1.ACCOUNT_ID       CASH_ACCOUNT_ID,\n" );
        sql.append("               T1.ACCOUNT_TYPE     CASH_ACCOUNT_TYPE,\n" );
        sql.append("               T1.AVAILABLE_AMOUNT CASH_AVAILABLE_AMOUNT,\n" );
        sql.append("               T1.BALANCE_AMOUNT   CASH_BALANCE_AMOUNT,\n" );
        sql.append("               T1.OCCUPY_AMOUNT    CASH_OCCUPY_AMOUNT,\n" );
        sql.append("               T1.ORG_NAME,\n" );
        sql.append("               T1.ORG_CODE,\n" );
        sql.append("               T1.ORG_ID,\n" );
        sql.append("               T1.OEM_COMPANY_ID,\n" );
        sql.append("               T1.STATUS\n" );
        sql.append("          FROM PT_BU_ACCOUNT T1\n" );
        sql.append("         WHERE T1.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_01+") A,\n" );
        sql.append("       (SELECT T2.ACCOUNT_ID       ACCEPT_ACCOUNT_ID,\n" );
        sql.append("               T2.ACCOUNT_TYPE     ACCEPT_ACCOUNT_TYPE,\n" );
        sql.append("               T2.AVAILABLE_AMOUNT ACCEPT_AVAILABLE_AMOUNT,\n" );
        sql.append("               T2.BALANCE_AMOUNT   ACCEPT_BALANCE_AMOUNT,\n" );
        sql.append("               T2.OCCUPY_AMOUNT    ACCEPT_OCCUPY_AMOUNT,\n" );
        sql.append("               T2.ORG_ID\n" );
        sql.append("          FROM PT_BU_ACCOUNT T2\n" );
        sql.append("         WHERE T2.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_02+") B,\n" );
        sql.append("       (SELECT T3.ACCOUNT_ID       MATERIAL_ACCOUNT_ID,\n" );
        sql.append("               T3.ACCOUNT_TYPE     MATERIAL_ACCOUNT_TYPE,\n" );
        sql.append("               T3.AVAILABLE_AMOUNT MATERIAL_AVAILABLE_AMOUNT,\n" );
        sql.append("               T3.BALANCE_AMOUNT   MATERIAL_BALANCE_AMOUNT,\n" );
        sql.append("               T3.OCCUPY_AMOUNT    MATERIAL_OCCUPY_AMOUNT,\n" );
        sql.append("               T3.ORG_ID\n" );
        sql.append("          FROM PT_BU_ACCOUNT T3\n" );
        sql.append("         WHERE T3.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_03+") C,\n" );
        sql.append("       (SELECT T4.ACCOUNT_ID       CREDIT_ACCOUNT_ID,\n" );
        sql.append("               T4.ACCOUNT_TYPE     CREDIT_ACCOUNT_TYPE,\n" );
        sql.append("               T4.AVAILABLE_AMOUNT CREDIT_AVAILABLE_AMOUNT,\n" );
        sql.append("               T4.BALANCE_AMOUNT   CREDIT_BALANCE_AMOUNT,\n" );
        sql.append("               T4.OCCUPY_AMOUNT    CREDIT_OCCUPY_AMOUNT,\n" );
        sql.append("               T4.ORG_ID\n" );
        sql.append("          FROM PT_BU_ACCOUNT T4\n" );
        sql.append("         WHERE T4.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+") D,\n" );
        sql.append("       (SELECT T5.ACCOUNT_ID       REBATE_ACCOUNT_ID,\n" );
        sql.append("               T5.ACCOUNT_TYPE     REBATE_ACCOUNT_TYPE,\n" );
        sql.append("               T5.AVAILABLE_AMOUNT REBATE_AVAILABLE_AMOUNT,\n" );
        sql.append("               T5.BALANCE_AMOUNT   REBATE_BALANCE_AMOUNT,\n" );
        sql.append("               T5.OCCUPY_AMOUNT    REBATE_OCCUPY_AMOUNT,\n" );
        sql.append("               T5.ORG_ID\n" );
        sql.append("          FROM PT_BU_ACCOUNT T5\n" );
        sql.append("         WHERE T5.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_05+") E");
        sql.append("         WHERE A.STATUS = "+DicConstant.YXBS_01+"\n");
        sql.append("        AND  A.ORG_ID = B.ORG_ID(+)  ");
        sql.append("        AND  A.ORG_ID = C.ORG_ID(+)  ");
        sql.append("       AND  A.ORG_ID = D.ORG_ID(+)  ");
        sql.append("       AND  A.ORG_ID = E.ORG_ID(+)  ");
        sql.append("       AND  A.ORG_CODE = '" + orgCode + "'  ");
        sql.append("       AND A.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n");
        return factory.select(null, sql.toString());
    }

    /**
     * 渠道商账户是否存在(导入验证)
     * @param orgCode
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet accountIdCheck (User user,String accountType) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT ORG_CODE\n" );
    	sql.append("  FROM PT_BU_ACCOUNT_TMP\n" );
    	sql.append(" WHERE USER_ACCOUNT='"+user.getAccount()+"' AND ORG_CODE NOT IN\n" );
    	sql.append("       (SELECT ORG_CODE FROM PT_BU_ACCOUNT WHERE ACCOUNT_TYPE = '" + accountType + "')");
        return factory.select(null, sql.toString());
    }

    /**
     * 导入金额验证(导入验证)
     * @param orgCode
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet searchAmount (User user) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT ORG_CODE, AMOUNT FROM PT_BU_ACCOUNT_TMP WHERE USER_ACCOUNT = '" + user.getAccount() + "'");
        return factory.select(null, sql.toString());
    }

    /**
     * 配件账户临时表(PT_BU_ACCOUNT_TMP)查询
     *
     * @param pPageManager 查询分页对象
     * @param pConditions sql条件(默认：1=1)
     * @return BaseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchImport(PageManager pPageManager,String conditions, User user) throws Exception {

    	String wheres = conditions + " AND USER_ACCOUNT='"+user.getAccount()+"'";
    	pPageManager.setFilter(wheres);
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     ORG_CODE, \n");
        sql.append("     AMOUNT \n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_ACCOUNT_TMP \n");
        //执行方法，不需要传递conn参数
        return factory.select(sql.toString(),pPageManager);
    }

    /**
     * 配件预测明细临时表(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet expData(String pConditions,User user) throws Exception {

    	String wheres = " WHERE TMP_NO IN ("+ pConditions + ") AND USER_ACCOUNT='"+user.getAccount()+"'\n";
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT ORG_CODE, AMOUNT\n" );
    	sql.append("  FROM PT_BU_ACCOUNT_TMP");
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }

    /**
     * 
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet cashSearch(PageManager page, User user, String conditions) throws Exception
    {
        String wheres = conditions;
        wheres +="AND A.STATUS = "+DicConstant.YXBS_01+"\n" +
                        "AND  A.ORG_ID = B.ORG_ID(+)  "+
                        "AND  A.ORG_ID = C.ORG_ID(+)  "+
                        "AND  A.ORG_ID = D.ORG_ID(+)  "+
                        "AND  A.ORG_ID = E.ORG_ID(+)  "+
                        "AND A.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.CASH_ACCOUNT_ID,\n" );
        sql.append("       A.CASH_ACCOUNT_TYPE,\n" );
        sql.append("       A.CASH_AVAILABLE_AMOUNT,\n" );
        sql.append("       A.CASH_BALANCE_AMOUNT,\n" );
        sql.append("       A.CASH_OCCUPY_AMOUNT,\n" );
        sql.append("       A.ORG_ID,\n" );
        sql.append("       A.ORG_NAME,\n" );
        sql.append("       A.ORG_CODE,\n" );
        sql.append("       B.ACCEPT_ACCOUNT_ID,\n" );
        sql.append("       B.ACCEPT_ACCOUNT_TYPE,\n" );
        sql.append("       B.ACCEPT_AVAILABLE_AMOUNT,\n" );
        sql.append("       B.ACCEPT_BALANCE_AMOUNT,\n" );
        sql.append("       B.ACCEPT_OCCUPY_AMOUNT,\n" );
        sql.append("       C.MATERIAL_ACCOUNT_ID,\n" );
        sql.append("       C.MATERIAL_ACCOUNT_TYPE,\n" );
        sql.append("       C.MATERIAL_AVAILABLE_AMOUNT,\n" );
        sql.append("       C.MATERIAL_BALANCE_AMOUNT,\n" );
        sql.append("       C.MATERIAL_OCCUPY_AMOUNT,\n" );
        sql.append("       D.CREDIT_ACCOUNT_ID,\n" );
        sql.append("       D.CREDIT_ACCOUNT_TYPE,\n" );
        sql.append("       D.CREDIT_AVAILABLE_AMOUNT,\n" );
        sql.append("       D.CREDIT_BALANCE_AMOUNT,\n" );
        sql.append("       D.CREDIT_OCCUPY_AMOUNT\n," );
        sql.append("       E.REBATE_ACCOUNT_ID,\n" );
        sql.append("       E.REBATE_ACCOUNT_TYPE,\n" );
        sql.append("       E.REBATE_AVAILABLE_AMOUNT,\n" );
        sql.append("       E.REBATE_BALANCE_AMOUNT,\n" );
        sql.append("       E.REBATE_OCCUPY_AMOUNT\n" );
        sql.append("  FROM (SELECT T1.ACCOUNT_ID       CASH_ACCOUNT_ID,\n" );
        sql.append("               T1.ACCOUNT_TYPE     CASH_ACCOUNT_TYPE,\n" );
        sql.append("               T1.AVAILABLE_AMOUNT CASH_AVAILABLE_AMOUNT,\n" );
        sql.append("               T1.BALANCE_AMOUNT   CASH_BALANCE_AMOUNT,\n" );
        sql.append("               T1.OCCUPY_AMOUNT    CASH_OCCUPY_AMOUNT,\n" );
        sql.append("               O.ONAME ORG_NAME,\n" );
        sql.append("               T1.ORG_CODE,\n" );
        sql.append("               T1.ORG_ID,\n" );
        sql.append("               T1.OEM_COMPANY_ID,\n" );
        sql.append("               T1.STATUS\n" );
        sql.append("          FROM PT_BU_ACCOUNT T1,TM_ORG O \n" );
        sql.append("         WHERE T1.ORG_ID = O.ORG_ID AND T1.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_01+") A,\n" );
        sql.append("       (SELECT T2.ACCOUNT_ID       ACCEPT_ACCOUNT_ID,\n" );
        sql.append("               T2.ACCOUNT_TYPE     ACCEPT_ACCOUNT_TYPE,\n" );
        sql.append("               T2.AVAILABLE_AMOUNT ACCEPT_AVAILABLE_AMOUNT,\n" );
        sql.append("               T2.BALANCE_AMOUNT   ACCEPT_BALANCE_AMOUNT,\n" );
        sql.append("               T2.OCCUPY_AMOUNT    ACCEPT_OCCUPY_AMOUNT,\n" );
        sql.append("               T2.ORG_ID\n" );
        sql.append("          FROM PT_BU_ACCOUNT T2\n" );
        sql.append("         WHERE T2.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_02+") B,\n" );
        sql.append("       (SELECT T3.ACCOUNT_ID       MATERIAL_ACCOUNT_ID,\n" );
        sql.append("               T3.ACCOUNT_TYPE     MATERIAL_ACCOUNT_TYPE,\n" );
        sql.append("               T3.AVAILABLE_AMOUNT MATERIAL_AVAILABLE_AMOUNT,\n" );
        sql.append("               T3.BALANCE_AMOUNT   MATERIAL_BALANCE_AMOUNT,\n" );
        sql.append("               T3.OCCUPY_AMOUNT    MATERIAL_OCCUPY_AMOUNT,\n" );
        sql.append("               T3.ORG_ID\n" );
        sql.append("          FROM PT_BU_ACCOUNT T3\n" );
        sql.append("         WHERE T3.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_03+") C,\n" );
        sql.append("       (SELECT T4.ACCOUNT_ID       CREDIT_ACCOUNT_ID,\n" );
        sql.append("               T4.ACCOUNT_TYPE     CREDIT_ACCOUNT_TYPE,\n" );
        sql.append("               T4.AVAILABLE_AMOUNT CREDIT_AVAILABLE_AMOUNT,\n" );
        sql.append("               T4.BALANCE_AMOUNT   CREDIT_BALANCE_AMOUNT,\n" );
        sql.append("               T4.OCCUPY_AMOUNT    CREDIT_OCCUPY_AMOUNT,\n" );
        sql.append("               T4.ORG_ID\n" );
        sql.append("          FROM PT_BU_ACCOUNT T4\n" );
        sql.append("         WHERE T4.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+") D,\n" );
        sql.append("       (SELECT T5.ACCOUNT_ID       REBATE_ACCOUNT_ID,\n" );
        sql.append("               T5.ACCOUNT_TYPE     REBATE_ACCOUNT_TYPE,\n" );
        sql.append("               T5.AVAILABLE_AMOUNT REBATE_AVAILABLE_AMOUNT,\n" );
        sql.append("               T5.BALANCE_AMOUNT   REBATE_BALANCE_AMOUNT,\n" );
        sql.append("               T5.OCCUPY_AMOUNT    REBATE_OCCUPY_AMOUNT,\n" );
        sql.append("               T5.ORG_ID\n" );
        sql.append("          FROM PT_BU_ACCOUNT T5\n" );
        sql.append("         WHERE T5.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_05+") E");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("CASH_ACCOUNT_TYPE", "ZJZHLX");
        bs.setFieldDic("ACCEPT_ACCOUNT_TYPE", "ZJZHLX");
        bs.setFieldDic("MATERIAL_ACCOUNT_TYPE", "ZJZHLX");
        bs.setFieldDic("CREDIT_ACCOUNT_TYPE", "ZJZHLX");
        bs.setFieldDic("REBATE_ACCOUNT_TYPE", "ZJZHLX");
        return bs;
    }
    /**
     * 
     * @date()2014年8月5日下午9:50:58
     * @author Administrator
     * @to_do:更改渠道账户余额
     * @param ACCOUNT_ID
     * @param AMOUNT
     * @param REMARKS
     * @return
     * @throws Exception
     */
    public boolean addAccount(String ACCOUNT_ID,String AMOUNT ) throws Exception
    {
        StringBuffer sql= new StringBuffer();
        sql.append("UPDATE PT_BU_ACCOUNT SET AVAILABLE_AMOUNT = AVAILABLE_AMOUNT+"+AMOUNT+", BALANCE_AMOUNT=BALANCE_AMOUNT+"+AMOUNT+"\n" );
        sql.append("WHERE ACCOUNT_ID ="+ACCOUNT_ID+"");
        return factory.update(sql.toString(), null);
    }
    public boolean subAccount(String ACCOUNT_ID,String AMOUNT ) throws Exception
    {
        StringBuffer sql= new StringBuffer();
        sql.append("UPDATE PT_BU_ACCOUNT SET AVAILABLE_AMOUNT = AVAILABLE_AMOUNT-"+AMOUNT+", BALANCE_AMOUNT=BALANCE_AMOUNT-"+AMOUNT+"\n" );
        sql.append("WHERE ACCOUNT_ID ="+ACCOUNT_ID+"");
        return factory.update(sql.toString(), null);
    }
    /**
     * 
     * @date()2014年8月5日下午9:52:31
     * @author Administrator
     * @to_do:插入资金异动
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertLog(PtBuAccountLogVO vo)
            throws Exception
    {
        return factory.insert(vo);
    }
    
    public BaseResultSet occupationDetail(PageManager page, User user, String conditions,String ACCOUNT_ID) throws Exception
    {
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.OCCUPY_FUNDS, T1.ORDER_NO, T1.ORDER_TYPE, T1.ORG_CODE, T1.ORG_NAME\n" );
        sql.append("  FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS T, PT_BU_SALE_ORDER T1\n" );
        sql.append(" WHERE 1 = 1\n" );
        sql.append("   AND T.ORDER_ID = T1.ORDER_ID\n" );
        sql.append("   AND T.STATUS = "+DicConstant.YXBS_01+"");
        sql.append("   AND T.ACCOUNT_ID = "+ACCOUNT_ID+"");
        sql.append("   ORDER BY ORDER_NO ");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("ORDER_TYPE", "DDLX");
        return bs;
    }

    /**
     * 配件账户金额导入(材料费,返利)
     * @param accountType 账户类型
     * @param user 用户
     * @return
     * @throws Exception
     */
    public boolean updateAccount(String accountType,User user ,String tmpNo) throws Exception {
        StringBuilder sql= new StringBuilder();
        sql.append("UPDATE PT_BU_ACCOUNT A\n" );
        sql.append("   SET (A.AVAILABLE_AMOUNT, A.BALANCE_AMOUNT) =\n" );
        sql.append("       (SELECT A.AVAILABLE_AMOUNT + B.AMOUNT, A.BALANCE_AMOUNT + B.AMOUNT\n" );
        sql.append("          FROM PT_BU_ACCOUNT_TMP B\n" );
        sql.append("         WHERE B.ORG_CODE = A.ORG_CODE "+tmpNo+")\n" );
        sql.append(" WHERE A.ACCOUNT_TYPE = '" + accountType + "'\n" );
        sql.append("   AND A.ORG_CODE IN\n" );
        sql.append("       (SELECT B.ORG_CODE FROM PT_BU_ACCOUNT_TMP B WHERE B.USER_ACCOUNT = '" + user.getAccount() + "'"+tmpNo+")");

        return factory.update(sql.toString(), null);
    }

    /**
     * 导入时插入配件账户资金异动(材料费,返利)
     * @param accountType 账户类型
     * @param user 用户
     * @return
     * @throws Exception
     */
    public boolean insertAccountLog(String accountType,User user ,String tmpNo) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("INSERT INTO PT_BU_ACCOUNT_LOG\n" );
        sql.append("  (LOG_ID,\n" );
        sql.append("   ACCOUNT_ID,\n" );
        sql.append("   LOG_TYPE,\n" );
        sql.append("   AMOUNT,\n" );
        sql.append("   REMARKS,\n" );
        sql.append("   ORG_ID,\n" );
        sql.append("   CREATE_USER,\n" );
        sql.append("   CREATE_TIME,IN_DATE)\n" );
        sql.append("  (SELECT F_GETID(),\n" );
        sql.append("          A.ACCOUNT_ID,\n" );
        sql.append("          '"+DicConstant.ZJYDLX_01+"',\n" );
        sql.append("          B.AMOUNT,\n" );
        sql.append("          '批量导入',\n" );
        sql.append("          A.ORG_ID,\n" );
        sql.append("          '"+user.getAccount()+"',\n" );
        sql.append("          SYSDATE,SYSDATE\n" );
        sql.append("     FROM PT_BU_ACCOUNT A, PT_BU_ACCOUNT_TMP B\n" );
        sql.append("    WHERE A.ACCOUNT_TYPE = '"+accountType+"'\n" );
        sql.append("      AND B.ORG_CODE = A.ORG_CODE\n" );
        sql.append("      AND B.USER_ACCOUNT = '"+user.getAccount()+"'"+tmpNo+")");


        return factory.update(sql.toString(), null);
    }
}
