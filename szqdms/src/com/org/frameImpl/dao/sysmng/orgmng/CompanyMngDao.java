package com.org.frameImpl.dao.sysmng.orgmng;

import com.org.dms.common.DicConstant;
import com.org.frameImpl.Constant;
import com.org.frameImpl.vo.TmCompanyVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 公司管理类
 * @author andy
 *
 */
public class CompanyMngDao extends BaseDAO
{
	//定义instance
    public  static final CompanyMngDao getInstance(ActionContext atx)
    {
    	CompanyMngDao dao = new CompanyMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
   /**
    * 查询公司信息
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
    		wheres +=" AND COMPANY_TYPE ="+DicConstant.GSLX_02+"\n";
    	}
    	wheres += " ORDER BY CODE ";
    	page.setFilter(wheres);
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COMPANY_ID,CODE,CNAME,SNAME,CONTACT,ADDRESS,LEGAL_PERSON,INVOICE_NAME,COMPANY_TYPE,STATUS \n");
    	sql.append(" FROM TM_COMPANY \n");
    	BaseResultSet bs = null;
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("COMPANY_TYPE", DicConstant.GSLX);
    	bs.setFieldDic("STATUS", DicConstant.YXBS);
    	return bs;
    }
    
    /**
     * 校验公司代码是否已存在
     * @param orgCode
     * @return
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 10, 2014 9:46:11 AM
     */
    public QuerySet checkCompanyCode(String orgCode) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM TM_COMPANY \n");
    	sql.append(" WHERE CODE = '" + orgCode +"' \n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    
    public boolean insertCompany(TmCompanyVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean updateCompany(TmCompanyVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	
	public boolean delete(String companyId) throws Exception
	{
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE TM_COMPANY \n");
		sql.append(" SET　STATUS="+Constant.YXBS_02+"\n");
		sql.append(" WHERE COMPANY_ID = " + companyId);
		return factory.update(sql.toString(), null);
	}
}