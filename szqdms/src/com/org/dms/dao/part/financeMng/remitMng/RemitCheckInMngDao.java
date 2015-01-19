package com.org.dms.dao.part.financeMng.remitMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuMoneyRemitVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class RemitCheckInMngDao extends BaseDAO{
    public  static final RemitCheckInMngDao getInstance(ActionContext atx)
    {
        RemitCheckInMngDao dao = new RemitCheckInMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet getIsDs(User user) throws Exception {

        StringBuffer sql= new StringBuffer();
        sql.append("SELECT NVL(T.IS_DS,0) AS IS_DS\n" );
        sql.append("  FROM TM_ORG T");
        sql.append(" WHERE T.ORG_ID='" + user.getOrgId() + "'");
        return factory.select( null,sql.toString());
    }

    public BaseResultSet remitSearch(PageManager page, User user, String conditions) throws Exception
    {
        String wheres = conditions;
        wheres +="AND T.STATUS = "+DicConstant.YXBS_01+"\n" +
                        "AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
                        "AND T.COMPANY_ID = "+user.getCompanyId()+"\n" + 
                        "AND T.ORG_ID = "+user.getOrgId()+"\n" + 
                        "AND T.REMIT_STATUS = "+DicConstant.DKZT_01+"\n";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.REMIT_ID,\n" );
        sql.append("       T.FILIING_DATE,\n" );
        sql.append("       T.AMOUNT_TYPE,\n" );
        sql.append("       T.DRAFT_NO,\n" );
        sql.append("       T.BILL_AMOUNT,\n" );
        sql.append("       T.REMARK\n" );
        sql.append("  FROM PT_BU_MONEY_REMIT T");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("AMOUNT_TYPE", "ZJZHLX");
        bs.setFieldDateFormat("FILIING_DATE", "yyyy-MM-dd");
        return bs;
    }
    /**
     * 
     * @date()2014年7月31日下午5:35:49
     * @author Administrator
     * @to_do:新增打款信息
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertRemit(PtBuMoneyRemitVO vo)
            throws Exception
    {
        return factory.insert(vo);
    }
    /**
     * 
     * @date()2014年7月31日下午5:35:59
     * @author Administrator
     * @to_do:修改打款信息
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateRemit(PtBuMoneyRemitVO vo)
            throws Exception
    {
        return factory.update(vo);
    }
    /**
     * 
     * @date()2014年7月31日下午5:36:11
     * @author Administrator
     * @to_do:删除打款信息
     * @param REMIT_ID
     * @return
     * @throws Exception
     */
    public boolean deletedRemit(String REMIT_ID ) throws Exception
    {
        StringBuffer sql= new StringBuffer();
        sql.append("DELETE FROM PT_BU_MONEY_REMIT WHERE REMIT_ID = "+REMIT_ID+"");
        return factory.update(sql.toString(), null);
    }

}
