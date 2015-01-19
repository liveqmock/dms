package com.org.dms.dao.part.purchaseMng.purchaseContract;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuForcastVO;
import com.org.dms.vo.part.PtBuPchContractCheckVO;
import com.org.dms.vo.part.PtBuPchContractDtlVO;
import com.org.dms.vo.part.PtBuPchContractVO;
import com.org.frameImpl.Constant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PurchaseContractManageDao extends BaseDAO
{
	//定义instance
    public  static final PurchaseContractManageDao getInstance(ActionContext atx)
    {
    	PurchaseContractManageDao dao = new PurchaseContractManageDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    /**
     * 合同明细临时表(导出)
     *
     * @pConditions 查询条件
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet expData(String pConditions,User user) throws Exception {

    	String wheres = " WHERE TMP_NO IN ("+ pConditions + ") AND USER_ACCOUNT='"+user.getAccount()+"'\n";
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT \n");
        sql.append("     PART_CODE, \n");
        sql.append("     PART_NAME, \n");
        sql.append("     UNIT_PRICE, \n");
        sql.append("     DELIVER_CYCLE, \n");
        sql.append("     MIN_PACK_UNIT, \n");
        sql.append("     MIN_PACK_COUNT, \n");
        sql.append("     REMARKS \n");
    	sql.append("  FROM PT_BU_PCH_CONT_PART_TMP");
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }

    /**
     * 合同明细表(PT_BU_PCH_CONT_PART_TMP)导入更新
     *
     * @param pForcastId 合同ID
     * @param pUser
     * @return true:成功;false:失败;
     * @throws Exception
     */
    public boolean updateForecastDtl(String pForcastId,User pUser,String tmpNo) throws Exception {


        // 新增配件预测明细表sql
        StringBuffer sql = new StringBuffer();
        sql.append(" INSERT INTO PT_BU_PCH_CONTRACT_DTL \n");
        sql.append("         (DETAIL_ID,\n");
        sql.append("         CONTRACT_ID,\n");
        sql.append("         PART_CODE,\n");
        sql.append("         PART_NAME,\n");
        sql.append("         UNIT_PRICE,\n");
        sql.append("         DELIVERY_CYCLE,\n");
        sql.append("         MIN_PACK_UNIT,\n");
        sql.append("         MIN_PACK_COUNT,\n");
        sql.append("         REMARKS,\n");
        sql.append("         ORG_ID,\n");
        sql.append("         CREATE_USER,\n");
        sql.append("         CREATE_TIME,\n");
        sql.append("         STATUS) \n");
        sql.append("     (SELECT \n");
        sql.append("         F_GETID()," + pForcastId + ",PART_CODE,\n");
        sql.append("         PART_NAME,\n");
        sql.append("         UNIT_PRICE,\n");
        sql.append("         DELIVER_CYCLE, \n");
        sql.append("         MIN_PACK_UNIT,\n");
        sql.append("         MIN_PACK_COUNT,\n");
        sql.append("         REMARKS,\n");
        sql.append("         '"+pUser.getOrgId()+"',\n");
        sql.append("         '"+pUser.getAccount()+"',\n");
        sql.append("         sysdate,\n");
        sql.append("         '"+DicConstant.YXBS_01+"'\n");
        sql.append("     FROM \n");
        sql.append("         PT_BU_PCH_CONT_PART_TMP\n");
        sql.append("     WHERE  \n");
        sql.append("       USER_ACCOUNT='" + pUser.getAccount() + "' "+tmpNo+")");
        return factory.exec(sql.toString());
    }

    /**
     * 合同明细临时表(pt_bu_forcast_dtl_tmp)查询
     *
     * @param pPageManager 查询分页对象
     * @param pConditions sql条件(默认：1=1)
     * @return BaseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchImport(PageManager pPageManager, String pConditions,User user) throws Exception {

    	String wheres = pConditions + " AND USER_ACCOUNT='"+user.getAccount()+"' \n";
        pPageManager.setFilter(wheres);
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     PART_CODE, \n");
        sql.append("     PART_NAME, \n");
        sql.append("     UNIT_PRICE, \n");
        sql.append("     DELIVER_CYCLE, \n");
        sql.append("     MIN_PACK_UNIT, \n");
        sql.append("     MIN_PACK_COUNT, \n");
        sql.append("     REMARKS \n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_PCH_CONT_PART_TMP \n");
        //执行方法，不需要传递conn参数
        return factory.select(sql.toString(),pPageManager);
    }

    public BaseResultSet contractSearch(PageManager page, User user, String conditions,String partCode,String partName) throws Exception
    {
    	String wheres = conditions;
    	if(!"".equals(partCode)&&partCode!=null){
    		wheres += " AND T.CONTRACT_ID = T1.CONTRACT_ID(+)"+
    				"AND EXISTS (SELECT 1 FROM PT_BU_PCH_CONTRACT_DTL A WHERE A.PART_CODE LIKE '%"+partCode+"%'  AND A.CONTRACT_ID = T.CONTRACT_ID)"+
        			"AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+""
        					+ " AND T.CONTRACT_STATUS IN("+DicConstant.CGHTZT_01+","+DicConstant.CGHTZT_07+","+DicConstant.CGHTZT_05+" )"
        							+ " AND T.STATUS = "+Constant.YXBS_01+""
        									+ "ORDER BY T.CREATE_TIME";
    	}else if(!"".equals(partName)&&partName!=null){
    		wheres += " AND T.CONTRACT_ID = T1.CONTRACT_ID(+)"+
    				"AND EXISTS (SELECT 1 FROM PT_BU_PCH_CONTRACT_DTL A WHERE A.PART_NAME LIKE '%"+partName+"%'  AND A.CONTRACT_ID = T.CONTRACT_ID)"+
        			"AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+""
        					+ " AND T.CONTRACT_STATUS IN("+DicConstant.CGHTZT_01+","+DicConstant.CGHTZT_07+","+DicConstant.CGHTZT_05+" )"
        							+ " AND T.STATUS = "+Constant.YXBS_01+""
        									+ "ORDER BY T.CREATE_TIME";
    	}else if(!"".equals(partName)&&partName!=null&&!"".equals(partCode)&&partCode!=null){
    		wheres += " AND T.CONTRACT_ID = T1.CONTRACT_ID(+)"+
    				"AND EXISTS (SELECT 1 FROM PT_BU_PCH_CONTRACT_DTL A WHERE A.PART_CODE LIKE '%"+partCode+"%' A.PART_NAME LIKE '%"+partName+"%' AND A.CONTRACT_ID = T.CONTRACT_ID)"+
        			"AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+""
        					+ " AND T.CONTRACT_STATUS IN("+DicConstant.CGHTZT_01+","+DicConstant.CGHTZT_07+","+DicConstant.CGHTZT_05+" )"
        							+ " AND T.STATUS = "+Constant.YXBS_01+""
        									+ "ORDER BY T.CREATE_TIME";
    	}else{
    		wheres += " AND T.CONTRACT_ID = T1.CONTRACT_ID(+)"+
        			"AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+""
        					+ " AND T.CONTRACT_STATUS IN("+DicConstant.CGHTZT_01+","+DicConstant.CGHTZT_07+" )"
        							+ " AND T.STATUS = "+Constant.YXBS_01+""
        									+ "ORDER BY T.CREATE_TIME";
    	}
    	
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
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T1.COUNT\n" );
    	sql.append("  FROM PT_BU_PCH_CONTRACT T,(SELECT COUNT(1) COUNT,CONTRACT_ID FROM PT_BU_PCH_CONTRACT_DTL GROUP BY CONTRACT_ID) T1");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("INVOICE_TYPE", "FPLX");
		bs.setFieldDic("CONTRACT_TYPE", "HTLX");
		bs.setFieldDateFormat("EFFECTIVE_CYCLE_END", "yyyy-MM-dd");
		bs.setFieldDateFormat("EFFECTIVE_CYCLE_BEGIN", "yyyy-MM-dd");
    	return bs;
    }
    
    public BaseResultSet searchPart(PageManager page, User user, String conditions, String CONTRACT_ID) throws Exception {
        String wheres = conditions;

        wheres += " AND NOT EXISTS(SELECT 1 FROM PT_BU_PCH_CONTRACT_DTL PBCD WHERE PBCD.CONTRACT_ID = "+CONTRACT_ID+" AND PBI.PART_ID = PBCD.PART_ID)\n";
      //  wheres += " AND PBI.COMPANY_ID=" + user.getCompanyId()+"\n";
        wheres += " ORDER BY PBI.CREATE_TIME DESC\n";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT PBI.PART_ID,\n");
        sql.append("       PBI.PART_CODE,\n");
        sql.append("       PBI.PART_NAME,\n");
        sql.append("       PBI.PART_NO,\n");
        sql.append("       PBI.UNIT,\n");
        sql.append("       PBI.MIN_PACK,\n");
        sql.append("       PBI.SALE_PRICE\n");
        sql.append("  FROM PT_BA_INFO PBI\n");
        //执行方法，不需要传递conn参数
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
    	sql.append("       T.REMARKS\n" );
    	sql.append("  FROM PT_BU_PCH_CONTRACT_DTL T \n");
    	sql.append("	WHERE T.CONTRACT_ID = "+CONTRACT_ID+" \n");
    	sql.append("    ORDER BY T.CREATE_TIME");
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    
    public boolean insertContract(PtBuPchContractVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
    
    public boolean insertContractTrack(PtBuPchContractCheckVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean updateContract(PtBuPchContractVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	public boolean partInsert(PtBuPchContractDtlVO vo)
            throws Exception {
        return factory.insert(vo);
    }
	public boolean partUpdate(PtBuPchContractDtlVO vo)
            throws Exception {
        return factory.update(vo);
    }
	public boolean updateParts(String DETAIL_ID ) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("DELETE FROM PT_BU_PCH_CONTRACT_DTL WHERE DETAIL_ID = "+DETAIL_ID+"");
    	return factory.update(sql.toString(), null);
    }
	
	public QuerySet checkUnique(String PART_CODE,String CONTRACT_ID) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT 1 FROM PT_BU_PCH_CONTRACT_DTL WHERE PART_CODE = '"+PART_CODE+"' AND CONTRACT_ID = "+CONTRACT_ID+"");
        //执行方法，不需要传递conn参数
        return factory.select(null,sql.toString());
    }
	
	public QuerySet getSup(String CONTRACT_ID) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT SUPPLIER_CODE FROM PT_BU_PCH_CONTRACT WHERE CONTRACT_ID = "+CONTRACT_ID+"");
        //执行方法，不需要传递conn参数
        return factory.select(null,sql.toString());
    }
	
	public QuerySet checkOther(String PART_CODE,String supplierCode) throws Exception {

        //执行方法，不需要传递conn参数
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT 1\n" );
		sql.append("  FROM PT_BU_PCH_CONTRACT_DTL T\n" );
		sql.append(" WHERE T.PART_CODE = '"+PART_CODE+"'\n" );
		sql.append("   AND T.CONTRACT_ID IN (SELECT T1.CONTRACT_ID\n" );
		sql.append("                           FROM PT_BU_PCH_CONTRACT T1\n" );
		sql.append("                          WHERE T1.EFFECTIVE_CYCLE_BEGIN <= SYSDATE\n" );
		sql.append("                            AND T1.EFFECTIVE_CYCLE_END >= SYSDATE\n" );
		sql.append("                            AND T1.SUPPLIER_CODE = '"+supplierCode+"')");

        return factory.select(null,sql.toString());
    }
	
	public boolean deleteAllParts(String CONTRACT_ID ) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("DELETE FROM PT_BU_PCH_CONTRACT_DTL WHERE CONTRACT_ID = "+CONTRACT_ID+"");
    	return factory.update(sql.toString(), null);
    }

	public QuerySet checkSup(String supplierCode) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT 1 FROM PT_BA_SUPPLIER WHERE SUPPLIER_CODE = '"+supplierCode+"' AND SUPPLIER_CODE = 'SQGH001'");
        //执行方法，不需要传递conn参数
        return factory.select(null,sql.toString());
    }
	
	public QuerySet checkCNo(String CONTRACT_NO) throws Exception {

        StringBuffer sql = new StringBuffer();
        
        // begin 2014-12-31 by fuxiao: 赵冬冬提出合同号不管是否再有效期内，都不能重复，合同号可以作为唯一键 
        // sql.append("SELECT 1 FROM PT_BU_PCH_CONTRACT WHERE CONTRACT_NO = '"+CONTRACT_NO+"' AND EFFECTIVE_CYCLE_END > SYSDATE");
        sql.append("SELECT 1 FROM PT_BU_PCH_CONTRACT WHERE CONTRACT_NO = '"+CONTRACT_NO+"' AND STATUS = 100201 AND SUPPLIER_CODE!='SQGH001'");
        // end
        
        //执行方法，不需要传递conn参数
        return factory.select(null,sql.toString());
    }
	public BaseResultSet checkSupplier(PageManager page, String supplierCode, String conditions) throws Exception
    {
        String wheres = conditions;
        wheres += "AND T.TAX_TYPE = T1.ID AND T.SUPPLIER_CODE = '"+supplierCode+"'\n";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.SUPPLIER_NAME,\n" );
        sql.append("       T.SUPPLIER_CODE,\n" );
        sql.append("       T.SUPPLIER_QUALIFY,\n" );
        sql.append("       T.TAX_TYPE,\n" );
        sql.append("       T1.DIC_VALUE,\n" );
        sql.append("       T.LEGAL_PERSON,\n" );
        sql.append("       T.LEGAL_PERSON_PHONE,\n" );
        sql.append("       T.TAX_RATE,\n" );
        sql.append("       T.BUSINESS_PERSON,\n" );
        sql.append("       T.BUSINESS_PERSON_PHONE,\n" );
        sql.append("       T.OPEN_ACCOUNT,\n" );
        sql.append("       T.GUARANTEE_MONEY,\n" );
        sql.append("       T.WARRANTY_PERIOD\n" );
        sql.append("  FROM PT_BA_SUPPLIER T,DIC_TREE T1");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("TAX_TYPE", "FPLX");
        return bs;
    }
	public QuerySet checkCode(String supplierCode)throws Exception{
		String sql1 ="SELECT * FROM PT_BA_SUPPLIER WHERE SUPPLIER_CODE='"+supplierCode+"'";
		QuerySet qs1 = factory.select(null, sql1);
		return qs1;
	}
	public QuerySet checkName(String supplierCode,String supplierName) throws Exception {
		
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT 1\n" );
		sql.append("  FROM PT_BA_SUPPLIER T\n" );
		sql.append(" WHERE T.SUPPLIER_NAME = '"+supplierName+"'\n" );
		sql.append("   AND EXISTS (SELECT 1\n" );
		sql.append("          FROM PT_BA_SUPPLIER T1\n" );
		sql.append("         WHERE T.SUPPLIER_ID = T1.SUPPLIER_ID\n" );
		sql.append("           AND T1.SUPPLIER_CODE = '"+supplierCode+"')");
	        //执行方法，不需要传递conn参数
		QuerySet qs =  factory.select(null,sql.toString());
		return qs;
    }
}
