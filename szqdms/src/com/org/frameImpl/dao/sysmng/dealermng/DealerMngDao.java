package com.org.frameImpl.dao.sysmng.dealermng;

import com.org.frameImpl.vo.MainDealerVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 渠道管理类
 * @author andy
 *
 */
public class DealerMngDao extends BaseDAO
{
	//定义instance
    public  static final DealerMngDao getInstance(ActionContext atx)
    {
    	DealerMngDao dao = new DealerMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
   /**
    * 查询渠道信息
    * @param page
    * @param user
    * @param condition
    * @return
    * @throws Exception
    */
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	//增加按当前公司、当前组织过滤条件
    	//wheres += " AND OEM_COMPANY_ID="+user.getCompanyId()+"";
    	page.setFilter(wheres);
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT * \n");
    	sql.append(" FROM MAIN_DEALER \n");
    	BaseResultSet bs = null;
    	bs = factory.select(sql.toString(), page);
    	//bs.setFieldOrgCompanySimpleName("COMPANY_ID");
    	//bs.setFieldOrgCompanySimpleName("OEM_COMPANY_ID");
    	bs.setFieldOrgDept("ORG_ID");
    	bs.setFieldOrgDept("BELONG_OFFICE");
    	bs.setFieldDic("DEALER_TYPE", "ZZLB");
    	bs.setFieldDic("DEALER_STATUS", "ZZYWZT");
    	bs.setFieldDic("STATUS", "YXBS");
    	bs.setFieldSimpleXZQH("PROVINCE");
    	bs.setFieldSimpleXZQH("CITY");
    	bs.setFieldDic("IF_FST", "SF");
    	bs.setFieldDic("IF_KMS", "SF");
    	bs.setFieldDic("IF_AUTO_CHECK", "SF");
    	bs.setFieldUserID("UPDATE_USER");
    	bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
	
	public boolean updateDealer(MainDealerVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	/**
	 * 渠道管理导出方法。
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
    public QuerySet oemDownload(String conditions) throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT DEALER_CODE,\n" );
    	sql.append("       DEALER_NAME,\n" );
    	sql.append("       DUTY_TEL,\n" );
    	sql.append("       FAX,\n" );
    	sql.append("       ADDRESS,\n" );
    	sql.append("       DEALER_STAR,\n" );
    	sql.append("       STATION_NAME,--站长姓名\n" );
    	sql.append("       STATION_TEL,\n" );
    	sql.append("       T.CODE,\n" );
    	sql.append("       T.ONAME,\n" );
    	sql.append("       G.SNAME,\n" );
    	sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID =IF_FST)IF_FST,\n" );
    	sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID =IF_WC)IF_WC,\n" );
    	sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID =IF_KMS)IF_KMS,\n" );
    	sql.append("       OPEN_BANK,\n" );
    	sql.append("       BANK_ACCOUNT\n" );
    	sql.append("  FROM MAIN_DEALER,TM_ORG G ,TM_ORG T\n" );
    	sql.append(" WHERE "+conditions+"\n" );
    	sql.append(" AND G.CODE = DEALER_CODE\n" );
    	sql.append(" AND G.PID = T.ORG_ID(+)\n" );
    	sql.append(" ORDER BY DEALER_ID");
    	return factory.select(null, sql.toString());
    }
}