package com.org.dms.dao.service.financeMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.MainDealerVO;
import com.org.dms.vo.service.SeBuManuallyVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class DealerSettleSetDao extends BaseDAO {
	 //定义instance
	public  static final DealerSettleSetDao getInstance(ActionContext atx)
	{
		DealerSettleSetDao dao = new DealerSettleSetDao();	
		atx.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 渠道商查询
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet mainDealerSearch(PageManager page,String conditions,User user)throws Exception{
		BaseResultSet bs=null;
		String wheres=conditions;
			   wheres+=" ORDER BY T.DEALER_ID ";
		page.setFilter(wheres);
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.DEALER_ID,\n" );
		sql.append("       T.DEALER_NAME,\n" );
		sql.append("       T.DEALER_SHORTNAME,\n" );
		sql.append("       T.DEALER_CODE,\n" );
		sql.append("       T.SETTLE_TYPE,\n" );
		sql.append("       T.SETTLE_START_DATE,\n" );
		sql.append("       T.SETTLE_END_DATE,\n" );
		sql.append("       T.MAKE_START_DATE,\n" );
		sql.append("       T.MAKE_END_DATE,\n" );
		sql.append("       T.INVOICE_TYPE,\n" );
		sql.append("       T.IF_MAKE_AMOUNT\n" );
		sql.append("  FROM MAIN_DEALER T ");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("INVOICE_TYPE","FPLX");
		bs.setFieldDic("SETTLE_TYPE","JSLX");
		bs.setFieldDic("IF_MAKE_AMOUNT","SF");
		bs.setFieldDateFormat("MAKE_START_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("MAKE_END_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("SETTLE_START_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("SETTLE_END_DATE", "yyyy-MM-dd");
		return bs;
	}
	
	public BaseResultSet manuallySearch(PageManager page,String conditions,User user)throws Exception{
		BaseResultSet bs=null;
		String wheres=conditions;
		wheres+=" AND STATUS = 100201"
			   +" AND MANUALLY_STATUS IN (308001,308004) "
			   + "AND CREATE_USER = '"+user.getAccount()+"' "
			   +"ORDER BY MANUALLY_ID DESC ";
		page.setFilter(wheres);
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT MANUALLY_ID,\n" );
		sql.append("       ORG_ID,\n" );
		sql.append("       ORG_CODE,\n" );
		sql.append("       ORG_NAME,\n" );
		sql.append("       PASS_DATE,\n" );
		sql.append("       MANUALLY_COSTS,\n" );
		sql.append("       REMARKS,\n" );
		sql.append("       MANUALLY_TYPE,\n" );
		sql.append("       MANUALLY_STATUS,\n" );
		sql.append("       CREATE_USER,\n" );
		sql.append("       CREATE_TIME,\n" );
		sql.append("       UPDATE_USER,\n" );
		sql.append("       UPDATE_TIME,\n" );
		sql.append("       STATUS,\n" );
		sql.append("       SECRET_LEVEL,\n" );
		sql.append("       MANUALLY_WAY,\n" );
		sql.append("       CHECK_REMARKS,\n" );
		sql.append("       CLAIM_NO\n" );
		sql.append("  FROM SE_BU_MANUALLY");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("MANUALLY_TYPE","SGRZLX");
		bs.setFieldDic("MANUALLY_WAY","JSLX");
		bs.setFieldDic("MANUALLY_STATUS","SGZZT");
		bs.setFieldDateFormat("MAKE_START_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("MAKE_END_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("SETTLE_START_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("SETTLE_END_DATE", "yyyy-MM-dd");
		return bs;
	}
	public BaseResultSet manuallyApplySearch(PageManager page,String conditions,User user)throws Exception{
		BaseResultSet bs=null;
		String wheres=conditions;
		wheres+=" AND STATUS = 100201"
				+ "AND ORG_ID = '"+user.getOrgId()+"' "
				+"ORDER BY MANUALLY_ID DESC ";
		page.setFilter(wheres);
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT MANUALLY_ID,\n" );
		sql.append("       ORG_ID,\n" );
		sql.append("       ORG_CODE,\n" );
		sql.append("       ORG_NAME,\n" );
		sql.append("       PASS_DATE,\n" );
		sql.append("       MANUALLY_COSTS,\n" );
		sql.append("       REMARKS,\n" );
		sql.append("       MANUALLY_TYPE,\n" );
		sql.append("       MANUALLY_STATUS,\n" );
		sql.append("       CREATE_USER,\n" );
		sql.append("       CREATE_TIME,\n" );
		sql.append("       UPDATE_USER,\n" );
		sql.append("       UPDATE_TIME,\n" );
		sql.append("       STATUS,\n" );
		sql.append("       SECRET_LEVEL,\n" );
		sql.append("       MANUALLY_WAY,\n" );
		sql.append("       CHECK_REMARKS,\n" );
		sql.append("       CLAIM_NO\n" );
		sql.append("  FROM SE_BU_MANUALLY");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("MANUALLY_TYPE","SGRZLX");
		bs.setFieldDic("MANUALLY_WAY","JSLX");
		bs.setFieldDic("MANUALLY_STATUS","SGZZT");
		bs.setFieldDateFormat("MAKE_START_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("MAKE_END_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("SETTLE_START_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("SETTLE_END_DATE", "yyyy-MM-dd");
		return bs;
	}
	public BaseResultSet manuallyCheckSearch1(PageManager page,String conditions,User user)throws Exception{
		BaseResultSet bs=null;
		String wheres=conditions;
		wheres+=" AND STATUS = 100201 "
				+"AND MANUALLY_STATUS IN  (308003,308004) "
				+"ORDER BY MANUALLY_ID DESC ";
		page.setFilter(wheres);
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT MANUALLY_ID,\n" );
		sql.append("       ORG_ID,\n" );
		sql.append("       ORG_CODE,\n" );
		sql.append("       ORG_NAME,\n" );
		sql.append("       PASS_DATE,\n" );
		sql.append("       MANUALLY_COSTS,\n" );
		sql.append("       REMARKS,\n" );
		sql.append("       MANUALLY_TYPE,\n" );
		sql.append("       MANUALLY_STATUS,\n" );
		sql.append("       CREATE_USER,\n" );
		sql.append("       CREATE_TIME,\n" );
		sql.append("       UPDATE_USER,\n" );
		sql.append("       UPDATE_TIME,\n" );
		sql.append("       STATUS,\n" );
		sql.append("       SECRET_LEVEL,\n" );
		sql.append("       MANUALLY_WAY,\n" );
		sql.append("       CLAIM_NO\n" );
		sql.append("  FROM SE_BU_MANUALLY");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("MANUALLY_TYPE","SGRZLX");
		bs.setFieldDic("MANUALLY_WAY","JSLX");
		bs.setFieldDic("MANUALLY_STATUS","SGZZT");
		bs.setFieldDateFormat("MAKE_START_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("MAKE_END_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("SETTLE_START_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("SETTLE_END_DATE", "yyyy-MM-dd");
		return bs;
	}
	
	
	public BaseResultSet manuallyCheckSearch(PageManager page,String conditions,User user)throws Exception{
		BaseResultSet bs=null;
		String wheres=conditions;
		wheres+=" AND STATUS = 100201"
				+" AND MANUALLY_STATUS = 308002 "
				+"ORDER BY MANUALLY_ID DESC ";
		page.setFilter(wheres);
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT MANUALLY_ID,\n" );
		sql.append("       ORG_ID,\n" );
		sql.append("       ORG_CODE,\n" );
		sql.append("       ORG_NAME,\n" );
		sql.append("       PASS_DATE,\n" );
		sql.append("       MANUALLY_COSTS,\n" );
		sql.append("       REMARKS,\n" );
		sql.append("       MANUALLY_TYPE,\n" );
		sql.append("       MANUALLY_STATUS,\n" );
		sql.append("       CREATE_USER,\n" );
		sql.append("       CREATE_TIME,\n" );
		sql.append("       UPDATE_USER,\n" );
		sql.append("       UPDATE_TIME,\n" );
		sql.append("       STATUS,\n" );
		sql.append("       SECRET_LEVEL,\n" );
		sql.append("       MANUALLY_WAY,\n" );
		sql.append("       CLAIM_NO\n" );
		sql.append("  FROM SE_BU_MANUALLY");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("MANUALLY_TYPE","SGRZLX");
		bs.setFieldDic("MANUALLY_WAY","JSLX");
		bs.setFieldDic("MANUALLY_STATUS","SGZZT");
		bs.setFieldDateFormat("MAKE_START_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("MAKE_END_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("SETTLE_START_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("SETTLE_END_DATE", "yyyy-MM-dd");
		return bs;
	}
	
	/**
	 * 渠道商结算信息设置
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean dealerUpdate(MainDealerVO vo)throws Exception{
		return factory.update(vo);
	}
	public boolean updateManually(SeBuManuallyVO vo)throws Exception{
		return factory.update(vo);
	}
	public boolean insertManually(SeBuManuallyVO vo)throws Exception{
		return factory.insert(vo);
	}
	public QuerySet queryOrg(String orgCode)throws Exception{
	    StringBuffer sql= new StringBuffer();
	    sql.append("SELECT G.ORG_ID, G.ONAME FROM TM_ORG G WHERE G.CODE = '"+orgCode+"'\n");
	    return factory.select(null, sql.toString());
	}
	public QuerySet queryClaim(String claim,String orgId)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT C.CLAIM_ID FROM SE_BU_CLAIM C WHERE C.CLAIM_NO ='"+claim+"' AND C.ORG_ID = "+orgId+" AND C.CLAIM_STATUS = 301015\n");
		return factory.select(null, sql.toString());
	}
	public QuerySet querySettleType(String manuallyId)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT MD.DEALER_CODE,\n" );
		sql.append("       MD.SETTLE_TYPE,\n" );
		sql.append("       MD.SETTLE_START_DATE,\n" );
		sql.append("       MD.SETTLE_END_DATE\n" );
		sql.append("  FROM MAIN_DEALER MD\n" );
		sql.append(" WHERE MD.DEALER_CODE =\n" );
		sql.append("       (SELECT M.ORG_CODE\n" );
		sql.append("          FROM SE_BU_MANUALLY M\n" );
		sql.append("         WHERE M.MANUALLY_ID = "+manuallyId+")\n" );
		sql.append("   AND MD.SETTLE_START_DATE <= SYSDATE\n" );
		sql.append("   AND MD.SETTLE_END_DATE >= SYSDATE");
		return factory.select(null, sql.toString());
	}
	public QuerySet queryManuallyType(String manuallyId,String sType)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT M.ORG_CODE,M.MANUALLY_WAY FROM SE_BU_MANUALLY M WHERE M.MANUALLY_ID ="+manuallyId+" AND M.MANUALLY_WAY ='"+sType+"'");
		return factory.select(null, sql.toString());
	}
}
