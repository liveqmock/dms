package com.org.frameImpl.dao.sysmng.orgmng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBaCarrierVO;
import com.org.dms.vo.part.PtBaSupplierVO;
import com.org.dms.vo.part.PtBuAccountVO;
import com.org.frameImpl.Constant;
import com.org.frameImpl.vo.MainDealerVO;
import com.org.frameImpl.vo.TmOrgVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 机构管理类
 * @author andy
 *
 */
public class OrgMngDao extends BaseDAO
{
	//定义instance
    public  static final OrgMngDao getInstance(ActionContext atx)
    {
    	OrgMngDao dao = new OrgMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
   /**
    * 查询机构信息
    * @param page
    * @param user
    * @param condition
    * @return
    * @throws Exception
    */
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	boolean flag;
    	String account = user.getAccount();
		if(account.indexOf("ADMIN")==0||account.indexOf("SUPERMAN")==0)
			flag = false;
		else
			flag = true;
    	if(flag){
    		wheres +=" AND ORG_TYPE IN("+DicConstant.ZZLB_09+","+DicConstant.ZZLB_10+","+DicConstant.ZZLB_11+","+DicConstant.ZZLB_12+","+DicConstant.ZZLB_13+")\n";
    	}
    	//增加按当前公司、当前组织过滤条件
    	//wheres += " AND OEM_COMPANY_ID="+user.getCompanyId()+"";
    	wheres += " ORDER BY ORG_ID ASC ";
    	page.setFilter(wheres);
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT ORG_ID,CODE,ONAME,SNAME,DIVISION_CODE,PID,LEVEL_CODE,COMPANY_ID,OEM_COMPANY_ID,ORG_TYPE,STATUS,BUS_TYPE,ERP_ID,BUS_STATUS,IS_DS,IS_IC,IS_AM \n");
    	sql.append(" FROM TM_ORG \n");
    	BaseResultSet bs = null;
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldOrgCompanySimpleName("COMPANY_ID");
    	bs.setFieldOrgCompanySimpleName("OEM_COMPANY_ID");
    	bs.setFieldOrgDept("PID");
    	bs.setFieldDic("ORG_TYPE", "ZZLB");
    	bs.setFieldDic("BUS_TYPE", "YWLX");
    	bs.setFieldDic("LEVEL_CODE", "JGJB");
    	bs.setFieldDic("STATUS", "YXBS");
    	bs.setFieldDic("IS_DS", "SF");
    	bs.setFieldDic("IS_IC", "SF");
    	bs.setFieldDic("IS_AM", "SF");
    	bs.setFieldSimpleXZQH("DIVISION_CODE");
    	bs.setFieldDic("BUS_STATUS", "ZZYWZT");
    	return bs;
    }
    
    /**
     * 校验机构代码是否已存在
     * @param orgCode
     * @return
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 10, 2014 9:46:11 AM
     */
    public QuerySet checkOrgCode(String orgCode) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM TM_ORG \n");
    	sql.append(" WHERE CODE = '" + orgCode +"' \n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    
    public boolean insertOrg(TmOrgVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean updateOrg(TmOrgVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	
	public boolean updateMainDealer(String orgId,String pId,String oname,String sname) throws Exception
    {
		String sql = "UPDATE MAIN_DEALER SET BELONG_OFFICE ="+pId+" , DEALER_NAME='"+oname+"', DEALER_SHORTNAME='"+sname+"' WHERE ORG_ID ="+orgId+"\n";
    	return factory.update(sql, null);
    }
	
	public boolean updateSupp(String orgId,String oname,String sname)throws Exception{
		String sql = "UPDATE PT_BA_SUPPLIER SET SUPPLIER_NAME='"+oname+"' WHERE ORG_ID ="+orgId+"\n";
    	return factory.update(sql, null);
	}
	public boolean updateCys(String orgId,String oname,String sname)throws Exception{
		String sql = "UPDATE PT_BA_CARRIER SET CARRIER_NAME='"+oname+"' WHERE ORG_ID ="+orgId+"\n";
    	return factory.update(sql, null);
	}
	/**
	 * 校验机构下是否有用户
	 * @param orgCode
	 * @return
	 * @throws Exception
	 * @author andy.ten@tom.com 
	 * @Time Jul 10, 2014 9:46:37 AM
	 */
	public String[][] checkOrgUser(String orgId) throws Exception
    {
    	String[][] qs = null;
    	StringBuffer sql = new StringBuffer();
  
    	sql.append(" SELECT COUNT(1) \n");
    	sql.append(" FROM TM_USER \n");
    	sql.append(" WHERE ORG_ID = " + orgId +" AND STATUS="+Constant.YXBS_01+" \n");
    	qs = factory.select(sql.toString());
    	return qs;
    }
	
	/**
	 * 校验该机构下是否存在子部门
	 * @param orgId
	 * @return
	 * @throws Exception
	 * @author andy.ten@tom.com 
	 * @Time Jul 10, 2014 9:51:09 AM
	 */
	public String[][] checkChildOrg(String orgId) throws Exception
    {
    	String[][] qs = null;
    	StringBuffer sql = new StringBuffer();
  
    	sql.append(" SELECT COUNT(1) \n");
    	sql.append(" FROM TM_ORG \n");
    	sql.append(" WHERE PID = " + orgId +" AND STATUS="+Constant.YXBS_01+" \n");
    	qs = factory.select(sql.toString());
    	return qs;
    }
	
	public boolean delete(String orgId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE TM_ORG \n");
		sql.append(" SET　STATUS="+Constant.YXBS_02+"\n");
		sql.append(" WHERE ORG_ID = " + orgId);
		StringBuffer sql2 = new StringBuffer();//删除组织表，同时删除main_dealer表
		sql2.append(" UPDATE MAIN_DEALER \n");
		sql2.append(" SET　STATUS="+Constant.YXBS_02+"\n");
		sql2.append(" WHERE ORG_ID = " + orgId);
		factory.update(sql2.toString(), null);
		return factory.update(sql.toString(), null);
	}
	
	public boolean insertSupplier(PtBaSupplierVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean updateSupplier(PtBaSupplierVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	
	public boolean insertCarrier(PtBaCarrierVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean updateCarrier(PtBaCarrierVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	
	public boolean insertBuAccount(PtBuAccountVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean insertDealer(MainDealerVO vo)
            throws Exception
    {
		vo.setDealerId(SequenceUtil.getCommonSerivalNumber(factory));
    	return factory.insert(vo);
    }
	
	public boolean updateDealer(MainDealerVO vo) throws Exception
    {
    	return factory.update(vo);
    }
}