package com.org.dms.dao.service.financeMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuClaimSettleVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * 结算单调整DAO
 * @author zts
 *
 */
public class StatementAdjustDao extends BaseDAO{

	 //定义instance
	public  static final StatementAdjustDao getInstance(ActionContext atx)
	{
		StatementAdjustDao dao = new StatementAdjustDao();	
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 结算单查询
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet settleSearch(PageManager page,String conditions,User user,String ifmake)throws Exception{
	    String wheres = conditions;
	    if(null=="-1"||ifmake.equals("-1")){
	    	wheres += " AND T.SETTLE_STATUS='"+DicConstant.JSZT_01+"'\n"+  
	    			//" AND TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =  (SELECT TO_CHAR(SYSDATE, 'YYYY-MM') FROM DUAL)\n "+
		    		 " AND T.STATUS ="+DicConstant.YXBS_01+" \n"+
				     " ORDER BY T.ORG_ID";
	    }else {
	    	wheres += " AND T.SETTLE_STATUS='"+DicConstant.JSZT_01+"'\n"+  
	    			//" AND TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =  (SELECT TO_CHAR(SYSDATE, 'YYYY-MM') FROM DUAL)\n "+
		    		 " AND T.STATUS ="+DicConstant.YXBS_01+" \n"
		    		+" AND EXISTS ( SELECT 1 FROM MAIN_DEALER D WHERE D.IF_MAKE_AMOUNT = "+ifmake+" AND D.ORG_ID = T.ORG_ID)"+
				     " ORDER BY T.ORG_ID";
	    }
	    
	    page.setFilter(wheres);
	    BaseResultSet bs = null;
	    StringBuffer sql= new StringBuffer();
	    sql.append("SELECT T.SETTLE_ID,\n" );
	    sql.append("       T.SETTLE_NO,\n" );
	    sql.append("       T.ORG_ID,\n" );
	    sql.append("       T.ORG_CODE,\n" );
	    sql.append("       T.ORG_NAME,\n" );
	    sql.append("       T.SETTLE_DATE,\n" );
	    sql.append("       T.SETTLE_TYPE,\n" );
	    sql.append("       T.ADJUST_TIME,\n" );
	    sql.append("       T.ADJUST_USER,\n" );
	    sql.append("       T.POLICY_ADJUST_TIME,\n" );
	    sql.append("       T.POLICY_ADJUST_USER,\n" );
	    sql.append("       T.OTHERS_ADJUST_TIME,\n" );
	    sql.append("       T.OTHERS_ADJUST_USER,\n" );
	    sql.append("       NVL(T.COSTS, 0) COSTS,\n" );
	    sql.append("       NVL(T.RE_COSTS, 0) RE_COSTS,\n" );
	    sql.append("       NVL(T.POLICY_SUP, 0) POLICY_SUP,\n" );
	    sql.append("       NVL(T.CASH_GIFT, 0) CASH_GIFT,\n" );
	    sql.append("       NVL(T.CAR_AWARD, 0) CAR_AWARD,\n" );
	    sql.append("       NVL(T.AP_COSTS, 0) AP_COSTS,\n" );
	    sql.append("       NVL(T.OTHERS, 0) OTHERS,\n" );
	    sql.append("       NVL(T.SUMMARY, 0) SUMMARY,\n" );
	    sql.append("       NVL(T.MANUALLY_COST,0)MANUALLY_COST,\n" );
	    sql.append("       NVL(T.PART_MATERIAL_COSTS, 0) PART_MATERIAL_COSTS,\n" );
	    sql.append("       NVL(T.MATERIALCOST_REDUCE, 0) MATERIALCOST_REDUCE,\n" );
	    sql.append("       T.REMARKS,\n" );
	    sql.append("       T.SETTLE_STATUS\n" );
	    sql.append("  FROM SE_BU_CLAIM_SETTLE T\n");
	    bs=factory.select(sql.toString(), page);
	    bs.setFieldDateFormat("ADJUST_TIME","yyyy-MM-dd");
	    bs.setFieldDateFormat("POLICY_ADJUST_TIME","yyyy-MM-dd");
	    bs.setFieldDateFormat("OTHERS_ADJUST_TIME","yyyy-MM-dd");
	    bs.setFieldDateFormat("SETTLE_DATE","yyyy-MM-dd");
	    bs.setFieldDic("SETTLE_TYPE","JSLX");
	    return bs;
	}
	
	/**
	 * 结算单调整
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updateSettle(SeBuClaimSettleVO vo)throws Exception{
		return factory.update(vo);
	}
	
	/**
	 * 结算单其他费用调整
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updateOtherFeetsSettle(SeBuClaimSettleVO vo,User user)throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append(" UPDATE SE_BU_CLAIM_SETTLE T \n");
		sb.append("   SET T.OTHERS = ?, T.SUMMARY = (T.SUMMARY - T.OTHERS + ?) ,T.OTHERS_ADJUST_TIME=SYSDATE ,T.OTHERS_ADJUST_USER='"+user.getAccount()+"' \n");
		sb.append(" WHERE T.SETTLE_ID = ? ");
		return factory.update(sb.toString(),new Object[]{vo.getOthers(), vo.getOthers(), vo.getSettleId()});
	}
	
	/**
	 * 结算单政策支持费用调整
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updatePolicyFeetsSettle(SeBuClaimSettleVO vo,User user)throws Exception{
		StringBuilder sb = new StringBuilder();
		sb.append(" UPDATE SE_BU_CLAIM_SETTLE T \n");
		sb.append("   SET T.POLICY_SUP = ?, T.SUMMARY = (T.SUMMARY - T.POLICY_SUP + ?) ,T.POLICY_ADJUST_USER='"+user.getAccount()+"',T.POLICY_ADJUST_TIME=SYSDATE \n");
		sb.append(" WHERE T.SETTLE_ID = ? ");
		return factory.update(sb.toString(),new Object[]{vo.getPolicySup(), vo.getPolicySup(), vo.getSettleId()});
	}
	
	/**
	 * 下载调整数据
	 * @return
	 * @throws Exception
	 */
	public QuerySet download(String conditions,User user,String ifmakeAmount)throws Exception{
	    StringBuffer sql= new StringBuffer();
	    sql.append("SELECT ROWNUM,\n" );
	    sql.append("       T.SETTLE_ID,\n" );
	    sql.append("       T.SETTLE_NO,\n" );
	    sql.append("       DECODE(T.SETTLE_TYPE,"+DicConstant.JSLX_01+",'服务费','材料费') SETTLE_TYPE,\n" );
	    sql.append("       T.ORG_ID,\n" );
	    sql.append("       T.ORG_CODE,\n" );
	    sql.append("       T.ORG_NAME,\n" );
	    sql.append("       T.MATERIALCOST_REDUCE CLFCJ,\n" );
	    sql.append("       TO_CHAR(T.SETTLE_DATE,'YYYY-MM-DD') SETTLE_DATE,\n" );
	    sql.append("       NVL(T.COSTS, 0) COSTS,\n" );
	    sql.append("       NVL(T.RE_COSTS, 0) RE_COSTS,\n" );
	    sql.append("       NVL(T.POLICY_SUP, 0) POLICY_SUP,\n" );
	    sql.append("       NVL(T.CASH_GIFT, 0) CASH_GIFT,\n" );
	    sql.append("       NVL(T.CAR_AWARD, 0) CAR_AWARD,\n" );
	    sql.append("       NVL(T.AP_COSTS, 0) AP_COSTS,\n" );
	    sql.append("       NVL(T.OTHERS, 0) OTHERS,\n" );
	    sql.append("       NVL(T.MANUALLY_COST, 0) MANUALLY_COST,\n" );
	    sql.append("       NVL(T.PART_MATERIAL_COSTS, 0) PART_MATERIAL_COSTS,\n" );
	    sql.append("       NVL(T.MATERIALCOST_REDUCE, 0) MATERIALCOST_REDUCE,\n" );
	    sql.append("       T.REMARKS,\n" );
	    sql.append("       T.SETTLE_STATUS\n" );
	    sql.append("  FROM SE_BU_CLAIM_SETTLE T\n");
	    sql.append(" where "+conditions+" --AND  TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =  (SELECT TO_CHAR(SYSDATE, 'YYYY-MM') FROM DUAL)\n");
	    //sql.append(" AND T.OEM_COMPANY_ID="+user.getOemCompanyId()+"\n");
	    sql.append(" AND T.SETTLE_STATUS='"+DicConstant.JSZT_01+"' \n");
	    sql.append(" AND T.STATUS ="+DicConstant.YXBS_01+" \n");
	    if(DicConstant.SF_01.toString().equals(ifmakeAmount)){
	    	 sql.append(" AND EXISTS (SELECT 1 FROM MAIN_DEALER M WHERE M.ORG_ID=T.ORG_ID AND M.IF_MAKE_AMOUNT = "+ifmakeAmount+")\n");
	    }
	    return factory.select(null, sql.toString());
	}
	
	/**
	 * 导入数据查询
	 * @throws Exception
	 */
	public BaseResultSet searchImport(PageManager page,User user,String conditions)throws Exception{
		page.setFilter("");
		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.TMP_ID,\n" );
		sql.append("       T.USER_ACCOUNT,\n" );
		sql.append("       T.SETTLE_NO,\n" );
		sql.append("       T.ORG_CODE,\n" );
		sql.append("       T.ORG_NAME,\n" );
		sql.append("       T.SETTLE_DATE,\n" );
		sql.append("       T.SE_COSTS,\n" );
		sql.append("       T.SE_RE_COSTS,\n" );
		sql.append("       T.SE_POLICY_SUP,\n" );
		sql.append("       T.MATERIALCOST_REDUCE,\n" );
		sql.append("       T.PART_MATERIAL_COSTS,\n" );
		sql.append("       T.SE_CASH_GIFT,\n" );
		sql.append("       T.SE_CAR_AWARD,\n" );
		sql.append("       T.SE_AP_COSTS,\n" );
		sql.append("       T.SE_OTHERS,\n" );
		sql.append("       T.SE_REMARKS\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE_TMP T\n" );
		sql.append(" WHERE "+conditions+" AND T.USER_ACCOUNT = '"+user.getAccount()+"'");
		bs=factory.select(sql.toString(), page);
		return bs;
	}
	
	/**
	 * 获取结算单临时表数据
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public QuerySet getSettleTmp1(User user,String rowNo)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.TMP_ID,\n" );
		sql.append("       T.USER_ACCOUNT,\n" );
		sql.append("       T1.SETTLE_ID,\n" );
		sql.append("       NVL(T1.COSTS,0) SE_COSTS,\n" );
		sql.append("       T.SETTLE_NO,\n" );
		sql.append("       T.ORG_CODE,\n" );
		sql.append("       NVL(T.PART_MATERIAL_COSTS,0) PART_MATERIAL_COSTS,\n" );
		sql.append("       NVL(T.MATERIALCOST_REDUCE,0) MATERIALCOST_REDUCE,\n" );
		sql.append("       T.ORG_NAME,\n" );
		sql.append("       T.SETTLE_DATE,\n" );
		sql.append("       NVL(T.SE_RE_COSTS,0) SE_RE_COSTS,\n" );
		sql.append("       NVL(T.SE_POLICY_SUP,0) SE_POLICY_SUP,\n" );
		sql.append("       NVL(T.SE_CASH_GIFT,0) SE_CASH_GIFT,\n" );
		sql.append("       NVL(T.SE_CAR_AWARD,0) SE_CAR_AWARD,\n" );
		sql.append("       NVL(T.SE_AP_COSTS,0) SE_AP_COSTS,\n" );
		sql.append("       NVL(T1.MANUALLY_COST,0) MANUALLY_COST,\n" );
		sql.append("       NVL(T.SE_OTHERS,0) SE_OTHERS,\n" );
		sql.append("       T.SE_REMARKS\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE_TMP T ,SE_BU_CLAIM_SETTLE T1\n" );
		sql.append(" WHERE T.SETTLE_NO=T1.SETTLE_NO AND T.USER_ACCOUNT = '"+user.getAccount()+"' "+rowNo+" ");
		return factory.select(null, sql.toString());
	}
	/**
	 * 获取结算单临时表数据
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public QuerySet getSettleTmp(User user)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.TMP_ID,\n" );
		sql.append("       T.USER_ACCOUNT,\n" );
		sql.append("       T1.SETTLE_ID,\n" );
		sql.append("       NVL(T1.COSTS,0) SE_COSTS,\n" );
		sql.append("       T.SETTLE_NO,\n" );
		sql.append("       T.ORG_CODE,\n" );
		sql.append("       T.ORG_NAME,\n" );
		sql.append("       T.SETTLE_DATE,\n" );
		sql.append("       NVL(T.SE_RE_COSTS,0) SE_RE_COSTS,\n" );
		sql.append("       NVL(T.SE_POLICY_SUP,0) SE_POLICY_SUP,\n" );
		sql.append("       NVL(T.SE_CASH_GIFT,0) SE_CASH_GIFT,\n" );
		sql.append("       NVL(T.SE_CAR_AWARD,0) SE_CAR_AWARD,\n" );
		sql.append("       NVL(T.SE_AP_COSTS,0) SE_AP_COSTS,\n" );
		sql.append("       NVL(T.SE_OTHERS,0) SE_OTHERS,\n" );
		sql.append("       T.SE_REMARKS\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE_TMP T ,SE_BU_CLAIM_SETTLE T1\n" );
		sql.append(" WHERE T.SETTLE_NO=T1.SETTLE_NO AND T.USER_ACCOUNT = '"+user.getAccount()+"'  ");
		return factory.select(null, sql.toString());
	}
	
	/**
	 * 结算单号不能为空
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public QuerySet checkList1(User user) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.TMP_ID,\n" );
		sql.append("       T.ROW_NUM,\n" );
		sql.append("       T.USER_ACCOUNT,\n" );
		sql.append("       T.SETTLE_NO,\n" );
		sql.append("       T.ORG_CODE,\n" );
		sql.append("       T.ORG_NAME,\n" );
		sql.append("       T.SETTLE_DATE,\n" );
		sql.append("       T.SE_COSTS,\n" );
		sql.append("       T.SE_RE_COSTS,\n" );
		sql.append("       T.SE_POLICY_SUP,\n" );
		sql.append("       T.SE_CASH_GIFT,\n" );
		sql.append("       T.SE_CAR_AWARD,\n" );
		sql.append("       T.SE_AP_COSTS,\n" );
		sql.append("       T.SE_OTHERS,\n" );
		sql.append("       T.SE_REMARKS\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE_TMP T\n" );
		sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'");
		return factory.select(null, sql.toString());
	}
	
	/**
	 * 单号为当前月份单号
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public QuerySet checkList2(User user)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TM.ROW_NUM, TM.SETTLE_NO\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE_TMP TM\n" );
		sql.append(" WHERE TM.USER_ACCOUNT = '"+user.getAccount()+"' AND NOT EXISTS\n" );
		sql.append(" (SELECT 1\n" );
		sql.append("          FROM SE_BU_CLAIM_SETTLE T\n" );
		sql.append("         WHERE T.SETTLE_NO = TM.SETTLE_NO\n" );
		sql.append("           AND TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("               (SELECT TO_CHAR(SYSDATE, 'YYYY-MM') FROM DUAL) AND T.SETTLE_STATUS='"+DicConstant.JSZT_01+"')");
		return factory.select(null, sql.toString());
	}
	
	/**
	 * 更改状态
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean updateSettleStatus(User user)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE SE_BU_CLAIM_SETTLE T1\n" );
		sql.append("   SET T1.SETTLE_STATUS = '"+DicConstant.JSZT_02+"', T1.UPDATE_USER = '"+user.getAccount()+"', T1.UPDATE_TIME = SYSDATE \n" );
		sql.append(" WHERE T1.SETTLE_ID IN\n" );
		sql.append("       (SELECT T.SETTLE_ID\n" );
		sql.append("          FROM SE_BU_CLAIM_SETTLE T\n" );
		sql.append("         WHERE  TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') = \n" );
		sql.append("               (SELECT TO_CHAR(SYSDATE, 'YYYY-MM') FROM DUAL)  AND T.SETTLE_STATUS='"+DicConstant.JSZT_01+"' )");
		return factory.update(sql.toString(), null);
	}
	public QuerySet querySettleId(User user)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.SETTLE_ID\n" );
		sql.append("          FROM SE_BU_CLAIM_SETTLE T\n" );
		sql.append("         WHERE  TO_CHAR(T.SETTLE_DATE, 'YYYY-MM') =\n" );
		sql.append("               (SELECT TO_CHAR(SYSDATE, 'YYYY-MM') FROM DUAL)  AND T.SETTLE_STATUS='"+DicConstant.JSZT_01+"'");
		return factory.select(null, sql.toString());
	}
	public QuerySet expData(String conditions,User user) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.TMP_ID,\n" );
		sql.append("       T.USER_ACCOUNT,\n" );
		sql.append("       T.SETTLE_NO,\n" );
		sql.append("       T.ORG_CODE,\n" );
		sql.append("       T.ORG_NAME,\n" );
		sql.append("       T.SETTLE_DATE,\n" );
		sql.append("       T.SE_COSTS,\n" );
		sql.append("       T.SE_RE_COSTS,\n" );
		sql.append("       T.PART_MATERIAL_COSTS,\n" );
		sql.append("       T.MATERIALCOST_REDUCE,\n" );
		sql.append("       T.SE_POLICY_SUP,\n" );
		sql.append("       T.SE_CASH_GIFT,\n" );
		sql.append("       T.SE_CAR_AWARD,\n" );
		sql.append("       T.SE_AP_COSTS,\n" );
		sql.append("       T.SE_OTHERS,\n" );
		sql.append("       T.SE_REMARKS\n" );
		sql.append("  FROM SE_BU_CLAIM_SETTLE_TMP T\n" );
		sql.append(" WHERE T.ROW_NUM IN ("+conditions+") AND T.USER_ACCOUNT = '"+user.getAccount()+"'");
		return factory.select(null, sql.toString());
	}
}
