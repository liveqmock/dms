package com.org.dms.dao.part.purchaseMng.purchaseContract;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPchContractCheckVO;
import com.org.dms.vo.part.PtBuPchContractVO;
import com.org.frameImpl.Constant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class ContractAppendixAduitManageDao extends BaseDAO{
	//定义instance
    public  static final ContractAppendixAduitManageDao getInstance(ActionContext atx)
    {
    	ContractAppendixAduitManageDao dao = new ContractAppendixAduitManageDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    public BaseResultSet contractSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+" AND T.CONTRACT_STATUS="+DicConstant.CGHTZT_03+" AND T.STATUS = "+Constant.YXBS_01+"ORDER BY T.CREATE_TIME";
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
		bs.setFieldDic("CONTRACT_TYPE", "HTLX");
		bs.setFieldDic("INVOICE_TYPE", "FPLX");
		bs.setFieldDateFormat("EFFECTIVE_CYCLE_END", "yyyy-MM-dd");
		bs.setFieldDateFormat("EFFECTIVE_CYCLE_BEGIN", "yyyy-MM-dd");
    	return bs;
    }
    
    public BaseResultSet contractPartSearch(PageManager page, User user, String CONTRACT_ID) throws Exception
    {
//    	String wheres = conditions;
//        page.setFilter(wheres);
    	//定义返回结果集
    	page.setPageRows(200);
    	BaseResultSet bs = null;
//    	StringBuffer sql= new StringBuffer();
//    	sql.append("SELECT T.DETAIL_ID,\n" );
//    	sql.append("       T.PART_ID,\n" );
//    	sql.append("       T.PART_CODE,\n" );
//    	sql.append("       T.PART_NAME,\n" );
//    	sql.append("       T.UNIT_PRICE,\n" );
//    	sql.append("       T.DELIVERY_CYCLE,\n" );
//    	sql.append("       T.MIN_PACK_UNIT,\n" );
//    	sql.append("       T.MIN_PACK_COUNT,\n" );
//    	sql.append("       T.REMARKS\n" );
//    	sql.append("  FROM PT_BU_PCH_CONTRACT_DTL T\n" );
//    	sql.append(" WHERE T.CONTRACT_ID = "+CONTRACT_ID+"\n" );
//    	sql.append(" ORDER BY T.DETAIL_ID");
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
    	sql.append("       T.FJLJ,\n" );
    	sql.append("       T.BLWJM,\n" );
    	sql.append("       T.FID\n" );
    	sql.append("  FROM FS_FILESTORE T\n" );
    	sql.append(" WHERE T.YWZJ = '"+DETAIL_ID+"'\n" );
    	sql.append(" ORDER BY T.FJID");
    	bs = factory.select(sql.toString(), page);
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

}
