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

public class ContractOperationManageDao extends BaseDAO{
	public  static final ContractOperationManageDao getInstance(ActionContext atx)
    {
		ContractOperationManageDao dao = new ContractOperationManageDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	/**
	 * 
	 * @date()2014年7月3日 下午7:04:44
	 * @user()Sonia
	 * TODO:更改合同状态(已签订，已关闭，已归档)
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updateContract(PtBuPchContractVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	/**
	 * 
	 * @date()2014年7月4日 上午10:02:52
	 * @user()Sonia
	 * TODO:插入合同生命轨迹
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean insertContractTrack(PtBuPchContractCheckVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	/**
	 * 
	 * @date()2014年7月3日 下午7:02:30
	 * @user()Sonia
	 * TODO:合同签订页面查询
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet signContractSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+" AND T.CONTRACT_STATUS="+DicConstant.CGHTZT_06+" AND T.STATUS = "+Constant.YXBS_01+"ORDER BY T.CREATE_TIME";
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
	/**
	 * 
	 * @date()2014年7月3日 下午7:11:55
	 * @user()Sonia
	 * TODO:合同归档查询
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet fileContractSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.SUPPLIER_CODE = T1.SUPPLIER_CODE(+) AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+" AND T.CONTRACT_STATUS="+DicConstant.CGHTZT_10+" AND T.STATUS = "+Constant.YXBS_01+"ORDER BY T.CREATE_TIME";
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
    	sql.append("       T.EFFECTIVE_CYCLE_END,\n" );
    	sql.append("       T.EFFECTIVE_CYCLE_BEGIN,\n" );
    	sql.append("       T1.ERP_NO,\n" );
    	sql.append("       T.INVOICE_TYPE,\n" );
    	sql.append("       T.REMARKS\n" );
    	sql.append("  FROM PT_BU_PCH_CONTRACT T,");
    	sql.append("       (SELECT ERP_NO,SUPPLIER_CODE FROM PT_BA_SUPPLIER WHERE PART_IDENTIFY ="+DicConstant.YXBS_01+") T1");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("CONTRACT_TYPE", "HTLX");
		bs.setFieldDic("INVOICE_TYPE", "FPLX");
		bs.setFieldDateFormat("EFFECTIVE_CYCLE_END", "yyyy-MM-dd");
		bs.setFieldDateFormat("EFFECTIVE_CYCLE_BEGIN", "yyyy-MM-dd");
    	return bs;
    }
	
	public BaseResultSet closeContractSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += "AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+" AND T.CONTRACT_STATUS IN("+DicConstant.CGHTZT_07+","+DicConstant.CGHTZT_09+","+DicConstant.CGHTZT_10+") AND T.STATUS = "+Constant.YXBS_01+"ORDER BY T.CREATE_TIME";
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

	public BaseResultSet contractInfo(PageManager page, User user, String conditions,String CONTRACT_ID) throws Exception
    {
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
    	sql.append("       T.ERP_NUM,\n" );
    	sql.append("       T.REMARKS\n" );
    	sql.append("  FROM PT_BU_PCH_CONTRACT T \n");
    	sql.append("       WHERE 1=1 \n ");
    	sql.append("AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n");
    	sql.append("AND T.STATUS = "+Constant.YXBS_01+"\n");
    	sql.append("AND T.CONTRACT_ID = "+CONTRACT_ID+"\n");
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
    	sql.append("       T1.FID,\n" );
    	sql.append("       T1.FJMC,\n" );
    	sql.append("       T1.WJJBS,\n" );
    	sql.append("       T1.BLWJM\n" );
    	sql.append("  FROM PT_BU_PCH_CONTRACT_DTL T, FS_FILESTORE T1\n" );
    	sql.append(" WHERE T.CONTRACT_ID = "+CONTRACT_ID+"\n" );
    	sql.append("   AND T.DETAIL_ID = T1.YWZJ(+)\n" );
    	sql.append(" ORDER BY T.DETAIL_ID");
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
}
