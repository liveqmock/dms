package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBaSupplierVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PtBaSupplierDao extends BaseDAO {
	// 定义instance
	public static final PtBaSupplierDao getInstance(ActionContext atx) {
		PtBaSupplierDao dao = new PtBaSupplierDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}
	//插入信息
	public boolean insertPtBaSupplier(PtBaSupplierVO vo) throws Exception {
		return factory.insert(vo);
	}
	//判断供应商代码是否存在
	public QuerySet checkCode(String supplier_code) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM PT_BA_SUPPLIER \n");
    	sql.append(" WHERE SUPPLIER_CODE = '" + supplier_code +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	//修改信息
	public boolean updatePtBaSupplier(PtBaSupplierVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	
	//删除供应商(服务)信息
	public boolean updatePtBaSupplierSeIdentify(String supplier_id, String identify) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_SUPPLIER \n");
    	sql.append(" SET SE_IDENTIFY = " + identify + " \n");
    	sql.append(" WHERE SUPPLIER_ID = " + supplier_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	
	//删除供应商(配件)信息
	public boolean updatePtBaSupplierPartIdentify(String supplier_id, String identify) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_SUPPLIER \n");
    	sql.append(" SET PART_IDENTIFY = " + identify + " \n");
    	sql.append(" WHERE SUPPLIER_ID = " + supplier_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	
	//查询
	public BaseResultSet search(PageManager page,User user,String conditions) throws Exception
    {
//    	String wheres = conditions;  
//    	wheres += "  ORDER BY CREATE_TIME DESC";
//        page.setFilter(wheres);
//    	BaseResultSet bs = null;
//    	StringBuffer sql= new StringBuffer();  
//    	sql.append("SELECT\n" );
//    	sql.append("PART_IDENTIFY,\n" );
//    	sql.append("SE_IDENTIFY,\n" );
//    	sql.append("SUPPLIER_ID,\n" );
//    	sql.append("SUPPLIER_NAME,\n" );
//    	sql.append("SUPPLIER_CODE,\n" );
//    	sql.append("PART_ACCESS,\n" );
//    	sql.append("PART_ACCESS_TEL,\n" );
//    	sql.append("ADDRESS,\n" );
//    	sql.append("EXCLUSIVE_TYPE,\n" );
//    	sql.append("ERP_NO,\n" );
//    	sql.append("WARRANTY_MONEY,\n" );
//    	sql.append("WARRANTY_DAYS,\n" );
//    	sql.append("TAX_TYPE,\n" );
//    	sql.append("TAX_RATE,\n" );
//    	sql.append("OPEN_BANK,\n" );
//    	sql.append("ACCOUNT,\n" );
//    	sql.append("TAX_NO,\n" );
//    	sql.append("LEGAL_PERSON,\n" );
//    	sql.append("FAX,\n" );
//    	sql.append("SE_ACCESS,\n" );
//    	sql.append("SE_ACCESS_TEL,\n" );
//    	sql.append("IF_ARMY,\n" );
//    	sql.append("SUPPLIER_STATUS,\n" );
//    	sql.append("REMARKS,\n" );
//    	sql.append("COMPANY_ID,\n" );
//    	sql.append("ORG_ID,\n" );
//    	sql.append("CREATE_USER,\n" );
//    	sql.append("CREATE_TIME,\n" );
//    	sql.append("UPDATE_USER,\n" );
//    	sql.append("UPDATE_TIME,\n" );
//    	sql.append("STATUS,\n" );
//    	sql.append("OEM_COMPANY_ID,\n" );
//    	sql.append("SECRET_LEVEL,\n" );
//    	sql.append("ACTUL_SUPPLIER,\n" );
//    	sql.append("REAL_NO,\n" );
//    	sql.append("IF_CLAIM_CHECK\n" );
//    	sql.append("FROM PT_BA_SUPPLIER");
//    	bs = factory.select(sql.toString(), page);
		String wheres = conditions;
    	wheres +="ORDER BY CREATE_TIME DESC";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();  
    	sql.append("SELECT\n" );
    	sql.append("PART_IDENTIFY,\n" );
    	sql.append("SE_IDENTIFY,\n" );
    	sql.append("SUPPLIER_ID,\n" );
    	sql.append("SUPPLIER_NAME,\n" );
    	sql.append("SUPPLIER_CODE,\n" );
    	sql.append("PART_ACCESS,\n" );
    	sql.append("PART_ACCESS_TEL,\n" );
    	sql.append("ADDRESS,\n" );
    	sql.append("EXCLUSIVE_TYPE,\n" );
    	sql.append("ERP_NO,\n" );
    	sql.append("WARRANTY_MONEY,\n" );
    	sql.append("WARRANTY_DAYS,\n" );
    	sql.append("TAX_TYPE,\n" );
    	sql.append("TAX_RATE,\n" );
    	sql.append("OPEN_BANK,\n" );
    	sql.append("ACCOUNT,\n" );
    	sql.append("TAX_NO,\n" );
    	sql.append("LEGAL_PERSON,\n" );
    	sql.append("FAX,\n" );
    	sql.append("SE_ACCESS,\n" );
    	sql.append("SE_ACCESS_TEL,\n" );
    	sql.append("IF_ARMY,\n" );
    	sql.append("SUPPLIER_STATUS,\n" );
    	sql.append("REMARKS,\n" );
    	sql.append("COMPANY_ID,\n" );
    	sql.append("ORG_ID,\n" );
    	sql.append("CREATE_USER,\n" );
    	sql.append("CREATE_TIME,\n" );
    	sql.append("UPDATE_USER,\n" );
    	sql.append("UPDATE_TIME,\n" );
    	sql.append("STATUS,\n" );
    	sql.append("OEM_COMPANY_ID,\n" );
    	sql.append("SECRET_LEVEL,\n" );
    	sql.append("ACTUL_SUPPLIER,\n" );
    	sql.append("REAL_NO,\n" );
    	sql.append("IF_CLAIM_CHECK\n" );
    	sql.append("FROM PT_BA_SUPPLIER");
    	bs = factory.select(sql.toString(), page);
         bs.setFieldDic("STATUS", "YXBS");   		
         bs.setFieldDic("PART_IDENTIFY", "YXBS");   
         bs.setFieldDic("SE_IDENTIFY", "YXBS");     
    	 bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
 		 bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
         
    	return bs;
    }
	public BaseResultSet partSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="ORDER BY SUPPLIER_ID DESC";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT * FROM PT_BA_SUPPLIER");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("STATUS", "YXBS");   		
        bs.setFieldDic("PART_IDENTIFY", "YXBS");   
        bs.setFieldDic("SE_IDENTIFY", "YXBS");     
        bs.setFieldDic("IF_ARMY","SF");
        bs.setFieldDic("TAX_TYPE", "FPLX");
   	 	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
	/**
     * 供应商信息查询
     * @throws Exception
     * @Author suoxiuli 2014-10-29
     */
	public BaseResultSet supplierInfoQuery(PageManager page,String conditions) throws Exception
    {
    	String wheres = conditions;  
    	wheres += " AND A.STATUS = 100201";
    	wheres += " AND (B.STATUS = 100201 or B.STATUS is null)";
    	wheres += " AND (EFFECTIVE_CYCLE_BEGIN < sysdate or EFFECTIVE_CYCLE_BEGIN is null)";
    	wheres += " AND (EFFECTIVE_CYCLE_END > sysdate or EFFECTIVE_CYCLE_END is null)";
    	wheres += " AND A.SUPPLIER_CODE = B.SUPPLIER_CODE(+)";
    	wheres += " ORDER BY  B.EFFECTIVE_CYCLE_BEGIN, A.SUPPLIER_ID DESC";
        page.setFilter(wheres);
        
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.SUPPLIER_ID,\n" );
    	sql.append("       A.SUPPLIER_CODE,\n" );
    	sql.append("       A.SUPPLIER_NAME,\n" );
    	sql.append("       B.INVOICE_TYPE,\n" );
    	sql.append("       B.TAX_RATE,\n" );
    	sql.append("       A.ERP_NO,\n" );
    	sql.append("       A.STATUS,\n" );
    	sql.append("       B.SUPPLIER_QUALIFY,\n" );
    	sql.append("       B.LEGAL_PERSON,\n" );
    	sql.append("       B.LEGAL_PERSON_PHONE,\n" );
    	sql.append("       B.BUSINESS_PERSON,\n" );
    	sql.append("       B.BUSINESS_PERSON_PHONE,\n" );
    	sql.append("       B.OPEN_ACCOUNT,\n" );
    	sql.append("       (B.EFFECTIVE_CYCLE_END - B.EFFECTIVE_CYCLE_BEGIN) as EFFECT_DATE,\n" );
    	sql.append("       B.EFFECTIVE_CYCLE_BEGIN,\n" );
    	sql.append("       B.EFFECTIVE_CYCLE_END,\n" );
    	sql.append("       B.GUARANTEE_MONEY,\n" );
    	sql.append("       B.WARRANTY_PERIOD,\n" );
    	sql.append("       B.RECOVERY_CLAUSE,\n" );
    	sql.append("       B.REMARKS\n" );
    	sql.append("  FROM PT_BA_SUPPLIER A, PT_BU_PCH_CONTRACT B\n" );
 
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
             
        bs.setFieldDic("STATUS", "YXBS");   //状态
        bs.setFieldDic("INVOICE_TYPE", "FPLX");   //发票类型
         
    	return bs;
    }
	
	/**
     * 供应商信息查询导出
     * @throws Exception
     * @Author suoxiuli 2014-10-29
     */
    public QuerySet download(String conditions,PageManager page) throws Exception {

    	String wheres = " WHERE "+ conditions; 
    	wheres += " AND A.STATUS = 100201";
    	wheres += " AND (B.STATUS = 100201 or B.STATUS is null)";
    	wheres += " AND (EFFECTIVE_CYCLE_BEGIN < sysdate or EFFECTIVE_CYCLE_BEGIN is null)";
    	wheres += " AND (EFFECTIVE_CYCLE_END > sysdate or EFFECTIVE_CYCLE_END is null)";
    	wheres += " AND A.SUPPLIER_CODE = B.SUPPLIER_CODE(+)";
    	wheres += " ORDER BY  B.EFFECTIVE_CYCLE_BEGIN, A.SUPPLIER_ID DESC";
        
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROWNUM,\n" );
    	sql.append("       A.SUPPLIER_ID,\n" );
    	sql.append("       A.SUPPLIER_CODE,\n" );
    	sql.append("       A.SUPPLIER_NAME,\n" );
    	sql.append("       (SELECT DIC_VALUE FROM DIC_TREE T WHERE T.ID = B.INVOICE_TYPE) INVOICE_TYPE,\n" );
    	sql.append("       (SELECT DIC_VALUE FROM DIC_TREE T WHERE T.ID = A.STATUS) STATUS,\n" );
    	sql.append("       B.TAX_RATE,\n" );
    	sql.append("       A.ERP_NO,\n" );
    	sql.append("       B.SUPPLIER_QUALIFY,\n" );
    	sql.append("       B.LEGAL_PERSON,\n" );
    	sql.append("       B.LEGAL_PERSON_PHONE,\n" );
    	sql.append("       B.BUSINESS_PERSON,\n" );
    	sql.append("       B.BUSINESS_PERSON_PHONE,\n" );
    	sql.append("       B.OPEN_ACCOUNT,\n" );
    	sql.append("       (B.EFFECTIVE_CYCLE_END - B.EFFECTIVE_CYCLE_BEGIN) as EFFECT_DATE,\n" );
    	sql.append("       B.EFFECTIVE_CYCLE_BEGIN,\n" );
    	sql.append("       B.EFFECTIVE_CYCLE_END,\n" );
    	sql.append("       B.GUARANTEE_MONEY,\n" );
    	sql.append("       B.WARRANTY_PERIOD,\n" );
    	sql.append("       B.RECOVERY_CLAUSE,\n" );
    	sql.append("       B.REMARKS\n" );
    	sql.append("  FROM PT_BA_SUPPLIER A, PT_BU_PCH_CONTRACT B\n" );

        //执行方法，不需要传递conn参数
    	return factory.select(null, sql.toString()+wheres);
    }
}
