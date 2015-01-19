package com.org.dms.dao.part.salesMng.search;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPartContChkVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class ContractPartCheckMngDao extends BaseDAO{
	public  static final ContractPartCheckMngDao getInstance(ActionContext atx)
    {
		ContractPartCheckMngDao dao = new ContractPartCheckMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	public BaseResultSet partSearch(PageManager page, User user, String conditions) throws Exception
    {
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.CHECK_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T.IF_IN,\n" );
    	sql.append("       T.SUP_NAMES,\n" );
    	sql.append("       T.UNIT_PRICES\n" );
    	sql.append("  FROM PT_BU_PART_CONT_CHK T WHERE 1=1 AND "+conditions+" ORDER BY T.PART_CODE");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("IF_IN", "SF");
    	return bs;
    }
	public BaseResultSet searchImport(PageManager page,User user,String conditions)throws Exception{
	 	   
	 	   String wheres = conditions;
	 	   page.setFilter(wheres);
	 	   wheres +="D.USER_ACCOUNT ='"+user.getAccount()+"'  ORDER BY D.PART_CODE";
	 	   BaseResultSet bs = null;
	 	   StringBuffer sql= new StringBuffer();
	 	   sql.append("SELECT D.* \n" );
	 	   sql.append("  FROM PT_BU_PART_CONT_CHK_TMP D");
	 	   bs=factory.select(sql.toString(), page);
	 	   return bs;
    }
	public QuerySet getPart(User user) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT PART_CODE,PART_NAME FROM PT_BU_PART_CONT_CHK_TMP A WHERE USER_ACCOUNT = '"+user.getAccount()+"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet getCon(String PART_CODE) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT TO_CHAR(wm_concat(T2.SUPPLIER_NAME)) SUPPLIER_NAMES,\n" );
    	sql.append("       TO_CHAR(wm_concat(NVL(T1.UNIT_PRICE,0))) UNIT_PRICES\n" );
    	sql.append("  FROM PT_BU_PCH_CONTRACT_DTL T1,\n" );
    	sql.append("       PT_BU_PCH_CONTRACT     T2\n" );
    	sql.append(" WHERE T1.CONTRACT_ID = T2.CONTRACT_ID\n" );
    	sql.append("   AND T1.PART_CODE = '"+PART_CODE+"' AND T2.CONTRACT_STATUS <>"+DicConstant.CGHTZT_08+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public boolean insertVo(PtBuPartContChkVO vo)
            throws Exception {
        return factory.insert(vo);
    }
	public QuerySet download(String conditions) throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.CHECK_ID,\n" );
		sql.append("       T.PART_CODE,\n" );
		sql.append("       T.PART_NAME,\n" );
		sql.append("       T1.DIC_VALUE IF_IN,\n" );
		sql.append("       T.SUP_NAMES,\n" );
		sql.append("       T.UNIT_PRICES\n" );
		sql.append("  FROM PT_BU_PART_CONT_CHK T,DIC_TREE T1\n" );
		sql.append("  WHERE T.IF_IN = T1.ID AND "+conditions+" ORDER BY T.PART_CODE");
		return factory.select(null, sql.toString());
	}

}
