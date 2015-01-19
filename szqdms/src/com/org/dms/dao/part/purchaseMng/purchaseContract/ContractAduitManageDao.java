package com.org.dms.dao.part.purchaseMng.purchaseContract;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPchContractCheckVO;
import com.org.dms.vo.part.PtBuPchContractVO;
import com.org.frameImpl.Constant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class ContractAduitManageDao extends BaseDAO{
	public  static final ContractAduitManageDao getInstance(ActionContext atx)
    {
		ContractAduitManageDao dao = new ContractAduitManageDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public BaseResultSet contractSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	//wheres += " AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+" AND T.CONTRACT_STATUS="+DicConstant.CGHTZT_04+" AND T.STATUS = "+Constant.YXBS_01+" ORDER BY T.CREATE_TIME";
        wheres +="AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" +
        				"  AND T.CONTRACT_STATUS = "+DicConstant.CGHTZT_04+"\n" + 
        				"  AND T.STATUS = "+Constant.YXBS_01+"\n" + 
        				"  AND NOT EXISTS (SELECT *\n" + 
        				"         FROM PT_BU_PCH_CONTRACT_CHECK T1\n" + 
        				"        WHERE T1.CHECK_USER = '"+user.getAccount()+"'\n" + 
        				"          AND T1.CONTRACT_ID = T.CONTRACT_ID AND T1.CHECK_STATUS IN ("+DicConstant.CGHTZT_06+","+DicConstant.CGHTZT_07+"))\n" + 
        				"ORDER BY T.CREATE_TIME";


    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.CONTRACT_ID,\n" );
    	sql.append("       T.CONTRACT_NO,\n" );
    	sql.append("       T.CONTRACT_NAME,\n" );
    	sql.append("       T.CONTRACT_TYPE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_QUALIFY,\n" );
    	sql.append("       T.LEGAL_PERSON,\n" );
    	sql.append("       T.LEGAL_PERSON_PHONE,\n" );
    	sql.append("       T.BUSINESS_PERSON,\n" );
    	sql.append("       T.BUSINESS_PERSON_PHONE,\n" );
    	sql.append("       T.GUARANTEE_MONEY,\n" );
    	sql.append("       T.WARRANTY_PERIOD,\n" );
    	sql.append("       T.OPEN_ACCOUNT,\n" );
    	sql.append("       T.TAX_RATE,\n" );
    	sql.append("       T.RECOVERY_CLAUSE,\n" );
    	sql.append("       T.EFFECTIVE_CYCLE_END,\n" );
    	sql.append("       T.EFFECTIVE_CYCLE_BEGIN,\n" );
    	sql.append("       T.INVOICE_TYPE,\n" );
    	sql.append("       T.REMARKS\n" );
    	sql.append("  FROM PT_BU_PCH_CONTRACT T");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("INVOICE_TYPE", "FPLX");
		bs.setFieldDic("CONTRACT_TYPE", "HTLX");
		bs.setFieldDateFormat("EFFECTIVE_CYCLE_END", "yyyy-MM-dd");
		bs.setFieldDateFormat("EFFECTIVE_CYCLE_BEGIN", "yyyy-MM-dd");
    	return bs;
    }
	
	public boolean updateContract(PtBuPchContractVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	
	public boolean insertContractTrack(PtBuPchContractCheckVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public QuerySet getPassNum(String CONTRACT_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.NUM / B.NUM RATE\n" );
    	sql.append("  FROM (SELECT COUNT(1) NUM\n" );
    	sql.append("          FROM PT_BU_PCH_CONTRACT_CHECK T\n" );
    	sql.append("         WHERE T.CONTRACT_ID = "+CONTRACT_ID+" AND T.CHECK_STATUS = "+DicConstant.CGHTZT_06+") A,\n" );
    	sql.append("       (SELECT COUNT(1) NUM FROM PT_BA_AGREE_REVIEW T1) B\n" );
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public QuerySet getAduitNum() throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT COUNT(1) NUM FROM PT_BA_AGREE_REVIEW");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }


}
