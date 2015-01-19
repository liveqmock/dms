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

public class ContractAppendixManageDao extends BaseDAO{
	//定义instance
    public  static final ContractAppendixManageDao getInstance(ActionContext atx)
    {
    	ContractAppendixManageDao dao = new ContractAppendixManageDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    public BaseResultSet contractSearch2(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND A.CONTRACT_ID = B.CONTRACT_ID\n" +
    			" AND A.OEM_COMPANY_ID = "+user.getOemCompanyId()+""+
				"AND A.CONTRACT_STATUS IN ("+DicConstant.CGHTZT_02+", "+DicConstant.CGHTZT_05+")"+
				"AND A.STATUS = "+DicConstant.YXBS_01+"\n" + 
				"ORDER BY A.CONTRACT_ID DESC";
    	
    	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.CONTRACT_ID,\n" );
    	sql.append("       A.CONTRACT_NO,\n" );
    	sql.append("       A.CONTRACT_NAME,\n" );
    	sql.append("       A.CONTRACT_TYPE,\n" );
    	sql.append("       A.SUPPLIER_NAME,\n" );
    	sql.append("       A.SUPPLIER_CODE,\n" );
    	sql.append("       A.SUPPLIER_QUALIFY,\n" );
    	sql.append("       A.LEGAL_PERSON,\n" );
    	sql.append("       A.LEGAL_PERSON_PHONE,\n" );
    	sql.append("       A.BUSINESS_PERSON,\n" );
    	sql.append("       A.BUSINESS_PERSON_PHONE,\n" );
    	sql.append("       A.GUARANTEE_MONEY,\n" );
    	sql.append("       A.WARRANTY_PERIOD,\n" );
    	sql.append("       A.OPEN_ACCOUNT,\n" );
    	sql.append("       A.TAX_RATE,\n" );
    	sql.append("       A.RECOVERY_CLAUSE,\n" );
    	sql.append("       A.EFFECTIVE_CYCLE_END,\n" );
    	sql.append("       A.EFFECTIVE_CYCLE_BEGIN,\n" );
    	sql.append("       A.INVOICE_TYPE,\n" );
    	sql.append("       A.REMARKS,\n" );
    	sql.append("       B.STATUS\n" );
    	sql.append("  FROM PT_BU_PCH_CONTRACT A,\n" );
    	sql.append("       (SELECT T.CONTRACT_ID,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN NVL(T1.STATUS, 0) > 0 THEN\n" );
    	sql.append("                  '100101'\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  '100102'\n" );
    	sql.append("               END STATUS\n" );
    	sql.append("          FROM (SELECT COUNT(1) STATUS, A.CONTRACT_ID\n" );
    	sql.append("                  FROM PT_BU_PCH_CONTRACT_DTL A,\n" );
    	sql.append("                       FS_FILESTORE           B,\n" );
    	sql.append("                       PT_BU_PCH_CONTRACT     C\n" );
    	sql.append("                 WHERE A.DETAIL_ID = B.YWZJ(+)\n" );
    	sql.append("                   AND C.CONTRACT_ID = A.CONTRACT_ID\n" );
    	sql.append("                 GROUP BY A.CONTRACT_ID) T1,\n" );
    	sql.append("               PT_BU_PCH_CONTRACT T\n" );
    	sql.append("         WHERE 1 = 1\n" );
    	sql.append("           AND T.CONTRACT_ID = T1.CONTRACT_ID(+)\n" );
    	sql.append("           AND T.STATUS = "+DicConstant.YXBS_01+") B");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("CONTRACT_TYPE", "HTLX");
		bs.setFieldDic("INVOICE_TYPE", "FPLX");
		bs.setFieldDic("STATUS", "SF");
		bs.setFieldDateFormat("EFFECTIVE_CYCLE_END", "yyyy-MM-dd");
		bs.setFieldDateFormat("EFFECTIVE_CYCLE_BEGIN", "yyyy-MM-dd");
    	return bs;
    }
    public BaseResultSet contractSearch1(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND A.CONTRACT_ID = B.CONTRACT_ID\n" +
    			" AND A.SUPPLIER_CODE = C.SUPPLIER_CODE \n"+
    			" AND C.ORG_ID = "+user.getOrgId()+"\n"+
    			" AND C.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n"+
				"AND A.CONTRACT_STATUS IN ("+DicConstant.CGHTZT_02+", "+DicConstant.CGHTZT_05+")"+
				"AND A.STATUS = "+DicConstant.YXBS_01+"\n" + 
				"ORDER BY A.CONTRACT_ID DESC";

    	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.CONTRACT_ID,\n" );
    	sql.append("       A.CONTRACT_NO,\n" );
    	sql.append("       A.CONTRACT_NAME,\n" );
    	sql.append("       A.CONTRACT_TYPE,\n" );
    	sql.append("       A.SUPPLIER_NAME,\n" );
    	sql.append("       A.SUPPLIER_CODE,\n" );
    	sql.append("       A.SUPPLIER_QUALIFY,\n" );
    	sql.append("       A.LEGAL_PERSON,\n" );
    	sql.append("       A.LEGAL_PERSON_PHONE,\n" );
    	sql.append("       A.BUSINESS_PERSON,\n" );
    	sql.append("       A.BUSINESS_PERSON_PHONE,\n" );
    	sql.append("       A.GUARANTEE_MONEY,\n" );
    	sql.append("       A.WARRANTY_PERIOD,\n" );
    	sql.append("       A.OPEN_ACCOUNT,\n" );
    	sql.append("       A.TAX_RATE,\n" );
    	sql.append("       A.RECOVERY_CLAUSE,\n" );
    	sql.append("       A.EFFECTIVE_CYCLE_END,\n" );
    	sql.append("       A.EFFECTIVE_CYCLE_BEGIN,\n" );
    	sql.append("       A.INVOICE_TYPE,\n" );
    	sql.append("       A.REMARKS,\n" );
    	sql.append("       B.STATUS\n" );
    	sql.append("  FROM PT_BU_PCH_CONTRACT A,\n" );
    	sql.append("       PT_BA_SUPPLIER C,\n" );
    	sql.append("       (SELECT T.CONTRACT_ID,\n" );
    	sql.append("               CASE\n" );
    	sql.append("                 WHEN NVL(T1.STATUS, 0) > 0 THEN\n" );
    	sql.append("                  '100101'\n" );
    	sql.append("                 ELSE\n" );
    	sql.append("                  '100102'\n" );
    	sql.append("               END STATUS\n" );
    	sql.append("          FROM (SELECT COUNT(1) STATUS, A.CONTRACT_ID\n" );
    	sql.append("                  FROM PT_BU_PCH_CONTRACT_DTL A,\n" );
    	sql.append("                       FS_FILESTORE           B,\n" );
    	sql.append("                       PT_BU_PCH_CONTRACT     C\n" );
    	sql.append("                 WHERE A.DETAIL_ID = B.YWZJ(+)\n" );
    	sql.append("                   AND C.CONTRACT_ID = A.CONTRACT_ID\n" );
    	sql.append("                 GROUP BY A.CONTRACT_ID) T1,\n" );
    	sql.append("               PT_BU_PCH_CONTRACT T\n" );
    	sql.append("         WHERE 1 = 1\n" );
    	sql.append("           AND T.CONTRACT_ID = T1.CONTRACT_ID(+)\n" );
    	sql.append("           AND T.STATUS = "+DicConstant.YXBS_01+") B");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("CONTRACT_TYPE", "HTLX");
		bs.setFieldDic("INVOICE_TYPE", "FPLX");
		bs.setFieldDic("STATUS", "SF");
		bs.setFieldDateFormat("EFFECTIVE_CYCLE_END", "yyyy-MM-dd");
		bs.setFieldDateFormat("EFFECTIVE_CYCLE_BEGIN", "yyyy-MM-dd");
    	return bs;
    }
    /**
     * 
     * @date()2014年7月3日 下午3:44:11
     * @user()Sonia
     * TODO
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet contractPartSearch(PageManager page, User user, String CONTRACT_ID, String conditions) throws Exception
    {
//    	String wheres = conditions;
//        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DETAIL_ID,\n" );
    	sql.append("       T.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T.UNIT_PRICE,\n" );
    	sql.append("       T.DELIVERY_CYCLE,\n" );
    	sql.append("       T.MIN_PACK_UNIT,\n" );
    	sql.append("       T.MIN_PACK_COUNT,\n" );
    	sql.append("       T.REMARKS\n" );
    	sql.append("  FROM PT_BU_PCH_CONTRACT_DTL T\n" );
    	sql.append(" WHERE T.CONTRACT_ID = "+CONTRACT_ID+"\n" + " AND "+ conditions );
    	sql.append(" ORDER BY T.DETAIL_ID");
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    
    /**
     * 
     * @date()2014年7月3日 下午3:44:11
     * @user()Sonia
     * TODO
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet contractAppendixSearch(PageManager page, User user, String DETAIL_ID) throws Exception
    {
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.FJID,\n" );
    	sql.append("       T.FJMC,\n" );
    	sql.append("       T.YWZJ,\n" );
    	sql.append("       T.CJR CREATE_USER,\n" );
    	sql.append("       TO_CHAR(T.CJSJ, 'YYYY-MM-DD') CREATE_TIME,\n" );
    	sql.append("       T.WJJBS,\n" );
    	sql.append("       T.BLWJM,\n" );
    	sql.append("       T.FID\n" );
    	sql.append("  FROM FS_FILESTORE T\n" );
    	sql.append(" WHERE T.YWZJ = '"+DETAIL_ID+"'\n" );
    	sql.append(" ORDER BY T.FJID");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldUserID("CREATE_USER");
    	return bs;
    }
    public boolean updateFiles(String FJID ) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("DELETE FROM FS_FILESTORE WHERE FJID = "+FJID+"");
    	return factory.update(sql.toString(), null);
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
    public QuerySet checkNew(String orgId) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ORG_TYPE FROM TM_ORG WHERE ORG_ID = "+orgId+"\n" );
    	qs = factory.select(null, sql.toString());
    	return qs;
    }

}
