package com.org.dms.dao.part.purchaseMng.purchaseContract;

import java.util.Map;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class ContractInfoSearchDao extends BaseDAO{
	 public  static final ContractInfoSearchDao getInstance(ActionContext atx)
	    {
		 ContractInfoSearchDao dao = new ContractInfoSearchDao();
	        atx.setDBFactory(dao.factory);
	        return dao;
	    }
	 public BaseResultSet contractSearch(PageManager page, User user, Map<String, String> hm,String orgCode) throws Exception
	    {
	    	//定义返回结果集
	    	BaseResultSet bs = null;
	    	String sql ="SELECT T.CONTRACT_ID,\n" +
	    					"       T.CONTRACT_NO,\n" + 
	    					"       T.CONTRACT_NAME,\n" + 
	    					"       T.CONTRACT_TYPE,\n" + 
	    					"       T.SUPPLIER_NAME,\n" + 
	    					"       T.SUPPLIER_CODE,\n" + 
	    					"       T.SUPPLIER_QUALIFY,\n" + 
	    					"       T.LEGAL_PERSON,\n" + 
	    					"       T.LEGAL_PERSON_PHONE,\n" + 
	    					"       T.BUSINESS_PERSON,\n" + 
	    					"       T.BUSINESS_PERSON_PHONE,\n" + 
	    					"       T.GUARANTEE_MONEY,\n" + 
	    					"       T.WARRANTY_PERIOD,\n" + 
	    					"       T.OPEN_ACCOUNT,\n" + 
	    					"       T.TAX_RATE,\n" + 
	    					"       T.RECOVERY_CLAUSE,\n" + 
	    					"       T.EFFECTIVE_CYCLE_BEGIN,\n" + 
	    					"       T.EFFECTIVE_CYCLE_END,\n" + 
	    					"       T.INVOICE_TYPE,\n" + 
	    					"		T.SIGN_DATE,"+
	    					"       T1.SUPPLIER_STATUS,\n" + 
	    					"       T.CONTRACT_STATUS,\n" + 
	    					"       T.CREATE_USER,\n" + 
	    					"       T.REMARKS,\n" + 
	    					"       T.CREATE_TIME\n" + 
	    					"  FROM PT_BU_PCH_CONTRACT T, PT_BA_SUPPLIER T1\n" + 
	    					" WHERE T.SUPPLIER_CODE = T1.SUPPLIER_CODE(+)\n" + 
	    					"   AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
	    					"   AND T.STATUS = "+DicConstant.YXBS_01+"\n" + 
//	    					"   AND T1.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n"+
	    					"   AND T.CONTRACT_STATUS <> "+DicConstant.CGHTZT_01+"\n";
	    	if(hm.get("CONTRACT_NO") != null && !"".equals(hm.get("CONTRACT_NO"))){
	    		sql += " AND T.CONTRACT_NO LIKE '%" +hm.get("CONTRACT_NO")+ "%'";
	    	}
	    	if(hm.get("SUPPLIER_NAME") != null && !"".equals(hm.get("SUPPLIER_NAME"))){
	    		sql += " AND T.SUPPLIER_NAME LIKE '%" +hm.get("SUPPLIER_NAME")+ "%'";
	    	}
	    	if((hm.get("PART_NAME") != null && !"".equals(hm.get("PART_NAME"))) || ( hm.get("PART_CODE") != null && !"".equals(hm.get("PART_CODE")) )){
	    		sql += " AND EXISTS( " +
	    			   "       SELECT 1 FROM PT_BU_PCH_CONTRACT_DTL D WHERE D.CONTRACT_ID = T.CONTRACT_ID ";
	    		
	    		if(hm.get("PART_NAME") != null && !"".equals(hm.get("PART_NAME"))){
	    			sql += " AND D.PART_NAME LIKE '%" +hm.get("PART_NAME")+ "%'";
	    		}
	    		
	    		if(hm.get("PART_CODE") != null && !"".equals(hm.get("PART_CODE"))){
	    			sql += " AND D.PART_CODE LIKE '%" +hm.get("PART_CODE")+ "%'";
	    		}
	    		
	    		sql += "   )";
	    	}
	    	if(hm.get("CREATE_TIME_B") != null && !"".equals(hm.get("CREATE_TIME_B"))){
	    		sql += " AND T.CREATE_TIME >= TO_DATE('" +hm.get("CREATE_TIME_B")+ "', 'YYYY-MM-DD')";
	    	}
	    	if(hm.get("CREATE_TIME_E") != null && !"".equals(hm.get("CREATE_TIME_E"))){
	    		sql += " AND T.CREATE_TIME <= TO_DATE('" +hm.get("CREATE_TIME_E")+ " 23:59:59', 'YYYY-MM-DD HH24:MI:SS')";
	    	}
	    	
	    	if(hm.get("T.CONTRACT_STATUS") != null && !"".equals(hm.get("T.CONTRACT_STATUS"))){
	    		sql += " AND T.CONTRACT_STATUS = " +hm.get("T.CONTRACT_STATUS");
	    	}
	    	
	    	if(hm.get("SUPPLIER_CODE") != null && !"".equals(hm.get("SUPPLIER_CODE"))){
	    		sql += " AND T.SUPPLIER_CODE = '" +hm.get("SUPPLIER_CODE")+"'";
	    	}
	    	if("XS10905".equals(orgCode)){
	    		sql += " AND T.CREATE_USER = '" +user.getAccount()+"'";
	    	}
	    	sql += " ORDER BY T.CREATE_TIME";
	    	bs = factory.select(sql.toString(), page);
			bs.setFieldDic("CONTRACT_TYPE", "HTLX");
			bs.setFieldDic("SUPPLIER_STATUS", "HTLX");
			bs.setFieldDic("CONTRACT_STATUS", "CGHTZT");
			bs.setFieldDateFormat("EFFECTIVE_CYCLE_BEGIN", "yyyy-MM-dd");
			bs.setFieldDateFormat("EFFECTIVE_CYCLE_END", "yyyy-MM-dd");
			bs.setFieldDateFormat("SIGN_DATE", "yyyy-MM-dd");
			bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
			bs.setFieldUserID("CREATE_USER");
	    	return bs;
	    }
	 public BaseResultSet contractPartSearch(PageManager page, User user, String CONTRACT_ID) throws Exception
	    {
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
	    	sql.append("  FROM PT_BU_PCH_CONTRACT_DTL T");
	    	sql.append("	WHERE T.CONTRACT_ID = "+CONTRACT_ID+"");
	    	sql.append("   ORDER BY T.DETAIL_ID");
	    	bs = factory.select(sql.toString(), page);
	    	return bs;
	    }
	 public BaseResultSet contractAppendixSearch(PageManager page, User user, String CONTRACT_ID) throws Exception
	    {
	    	//定义返回结果集
	    	BaseResultSet bs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.FJID,T.FJMC FILE_NAME,T.YWZJ,T.CJR CREATE_USER,TO_CHAR(T.CJSJ,'YYYY-MM-DD') CREATE_TIME FROM FS_FILESTORE T WHERE T.YWZJ = '"+CONTRACT_ID+"'");
	    	bs = factory.select(sql.toString(), page);
	    	return bs;
	    }
	 public BaseResultSet contractTrackSearch(PageManager page, User user, String CONTRACT_ID) throws Exception
	    {
	    	//定义返回结果集
	    	BaseResultSet bs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.CHECK_STATUS, T.CHECK_USER, T.CHECK_DATE, T1.CONTRACT_STATUS,T.CHECK_REMARKS\n" );
	    	sql.append("  FROM PT_BU_PCH_CONTRACT_CHECK T, PT_BU_PCH_CONTRACT T1\n" );
	    	sql.append(" WHERE T.CONTRACT_ID = T1.CONTRACT_ID AND T1.CONTRACT_ID = "+CONTRACT_ID+"\n" );
	    	sql.append(" ORDER BY T.CHECK_ID");
	    	bs = factory.select(sql.toString(), page);
	    	bs.setFieldDic("CHECK_STATUS", "CGHTZT");
	    	bs.setFieldDic("CONTRACT_STATUS", "CGHTZT");
	    	bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
	    	return bs;
	    }

	 public BaseResultSet contractDtlSearch(PageManager page, User user, String CONTRACT_ID) throws Exception
	    {
	    	//定义返回结果集
	    	page.setPageRows(200);
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
	    	sql.append("       T.REMARKS,\n" );
	    	sql.append("       WM_CONCAT(S.FID) FID,\n" );
	    	sql.append("       WM_CONCAT(S.FJMC) FJMC,\n" );
	    	sql.append("       WM_CONCAT(S.WJJBS) WJJBS,\n" );
	    	sql.append("       WM_CONCAT(S.BLWJM) BLWJM\n" );
	    	sql.append("  FROM PT_BU_PCH_CONTRACT_DTL T, FS_FILESTORE S\n" );
	    	sql.append(" WHERE T.CONTRACT_ID = "+CONTRACT_ID+"\n" );
	    	sql.append("   AND T.DETAIL_ID = S.YWZJ(+)\n" );
	    	sql.append(" GROUP BY T.DETAIL_ID,\n" );
	    	sql.append("          T.PART_ID,\n" );
	    	sql.append("          T.PART_CODE,\n" );
	    	sql.append("          T.PART_NAME,\n" );
	    	sql.append("          T.UNIT_PRICE,\n" );
	    	sql.append("          T.DELIVERY_CYCLE,\n" );
	    	sql.append("          T.MIN_PACK_UNIT,\n" );
	    	sql.append("          T.MIN_PACK_COUNT,\n" );
	    	sql.append("          T.REMARKS\n" );
	    	sql.append(" ORDER BY T.DETAIL_ID");
	    	bs = factory.select(sql.toString(), page);
	    	return bs;
	    }
	 	public QuerySet download(String CONTRACT_ID,String conditions) throws Exception{
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
	    	sql.append("  FROM PT_BU_PCH_CONTRACT_DTL T");
	    	sql.append("	WHERE T.CONTRACT_ID = "+CONTRACT_ID+" AND "+conditions+"");
	    	sql.append("   ORDER BY T.DETAIL_ID");
	    	return factory.select(null, sql.toString());
	    }
}
