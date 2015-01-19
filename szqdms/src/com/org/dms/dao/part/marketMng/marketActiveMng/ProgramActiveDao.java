package com.org.dms.dao.part.marketMng.marketActiveMng;

import com.org.dms.vo.part.PtBuProActiveDealerVO;
import com.org.dms.vo.part.PtBuProActiveVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;


public class ProgramActiveDao extends BaseDAO {

	//定义instance
    public  static final ProgramActiveDao getInstance(ActionContext atx)
    {
    	ProgramActiveDao dao = new ProgramActiveDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
  //***************************** PT_BU_PRO_ACTIVE表信息操作 Start *******************************************
    
    /**
     * 判断活动方案制定代码是否存在
     * @throws Exception
     * @Author suoxiuli 2014-09-17
     */
	/*public QuerySet checkActiveCode(String activeCode) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM PT_BU_PRO_ACTIVE \n");
    	sql.append(" WHERE ACTIVE_CODE = '" + activeCode +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }*/
    
    public QuerySet newActiveCodeSearch(String currentMonth) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
		sql.append("select active_code\n" );
		sql.append("  from (select active_code, create_time\n" );
		sql.append("          from PT_BU_PRO_ACTIVE\n" );
		sql.append("         order by create_time desc)\n" );
		sql.append(" where rownum = 1\n" );
		sql.append("   and create_time >= to_date('"+currentMonth+"', 'YYYY-MM-DD')");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
    /**
	 * 活动方案制定新增
	 * @throws Exception
     * @Author suoxiuli 2014-09-17
	 */
    public boolean insertProActive(PtBuProActiveVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
    /**
	 * 活动方案制定修改
	 * @throws Exception
     * @Author suoxiuli 2014-09-17
	 */
	public boolean updateProActive(PtBuProActiveVO vo) throws Exception
    {
    	return factory.update(vo);
    }

	/**
	 * 活动方案制定查询
	 * @throws Exception
     * @Author suoxiuli 2014-09-17
	 */
    public BaseResultSet search(PageManager page, String conditions, String orgId) throws Exception
    {
    	String wheres = conditions;
    	if (orgId != null && orgId != "" && !orgId.equals("10000001")) {
    		wheres += " AND B.ORG_ID=" +orgId;
    	}
    	wheres += " AND A.ACTIVE_ID=B.ACTIVE_ID";
		wheres += " ORDER BY A.CREATE_TIME DESC ";
		page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("select DISTINCT A.ACTIVE_ID,\n" );
    	sql.append("       A.ACTIVE_CODE,\n" );
    	sql.append("       A.ACTIVE_NAME,\n" );
    	sql.append("       A.ACTIVE_STATUS,\n" );
    	sql.append("       A.START_DATE,\n" );
    	sql.append("       A.END_DATE,\n" );
    	sql.append("       A.ACTIVE_CONTENT,\n" );
    	sql.append("       A.REMARKS,\n" );
    	sql.append("       A.COMPANY_ID,\n" );
    	sql.append("       A.ORG_ID,\n" );
    	sql.append("       A.CREATE_USER,\n" );
    	sql.append("       A.CREATE_TIME,\n" );
    	sql.append("       A.UPDATE_USER,\n" );
    	sql.append("       A.UPDATE_TIME,\n" );
    	sql.append("       A.STATUS,\n" );
    	sql.append("       A.OEM_COMPANY_ID,\n" );
    	sql.append("       A.SECRET_LEVEL\n" );
    	sql.append("  from PT_BU_PRO_ACTIVE A,\n" );
    	sql.append("PT_BU_PRO_ACTIVE_DEALER B");

    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("STATUS","YXBS");
    	bs.setFieldDic("ACTIVE_STATUS","HDLCZT");
    	bs.setFieldUserID("CREATE_USER");
    	bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
    	bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }

    /**
     * 活动方案信息的删除
     * @throws Exception
     * @Author suoxiuli 2014-09-17
     */
    public boolean updateProActiveStatus(String activeId, String updateUser) throws Exception
    {
    	//1、先删除明细表(PT_BU_PRO_ACTIVE_DEALER)里面的信息
    	String sql1 = "delete from PT_BU_PRO_ACTIVE_DEALER where active_id = "+ activeId;
    	factory.delete(sql1, null);
    	
    	//2、再更新主表(PT_BU_PRO_ACTIVE)的有效状态为"无效"
    	StringBuffer sql = new StringBuffer();
    	sql.append(" update PT_BU_PRO_ACTIVE \n");
    	sql.append(" set status = 100202, \n");
    	sql.append(" update_user = '" + updateUser + "', \n");
    	sql.append(" update_time = sysdate \n");
    	sql.append(" where active_id = '" + activeId + "' \n");
    	return factory.update(sql.toString(), null);
    }
    
    /**
     * 活动方案下发
     * @throws Exception
     * @Author suoxiuli 2014-09-17
     */
    public boolean proActiveIssue(String activeId, String activeStatus, String issueUser) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" update PT_BU_PRO_ACTIVE \n");
    	sql.append(" SET ACTIVE_STATUS = "+ activeStatus +", \n");
    	sql.append(" ISSUE_USER = '"+ issueUser +"', \n");
    	sql.append(" ISSUE_TIME = sysdate \n");
    	sql.append(" WHERE ACTIVE_ID = '" + activeId + "' \n");
    	return factory.update(sql.toString(), null);
    }
    
    /**
     * 活动方案信息的提报
     * @throws Exception
     * @Author suoxiuli 2014-09-17
     */
    public boolean proActiveReport(String typeId, String updateUser, String status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" update PT_BU_PRO_ACTIVE \n");
    	sql.append(" set status = '" + status + "', \n");
    	sql.append(" update_user = '" + updateUser + "', \n");
    	sql.append(" update_time = sysdate \n");
    	sql.append(" where type_id = '" + typeId + "' \n");
    	return factory.update(sql.toString(), null);
    }
    
  //***************************** PT_BU_PRO_ACTIVE表信息操作 Start *******************************************
    
    /**
	 * 渠道商信息查询
	 * @throws Exception
     * @Author suoxiuli 2014-09-17
	 */
    public BaseResultSet dealerSearch(PageManager page, String conditions, String activeId, String bscCode) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND STATUS = 100201";
    	//销司科室、办事处、配送中心、配件经销商、服务站
    	wheres += " AND (ORG_TYPE = 200005 OR ORG_TYPE = 200006 OR ORG_TYPE=200007)";
    	wheres += " AND org_id not in (select org_id from pt_bu_pro_active_dealer where active_id="+activeId+") ";
		wheres += " order by ORG_ID desc ";
		page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("select ORG_ID,\n" );
    	sql.append("       CODE,\n" );
    	sql.append("       ONAME\n" );
    	sql.append("  from TM_ORG " );

    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    
    //***************************** PT_BU_PRO_ACTIVE_DEALER表信息操作 Start *******************************************
    /**
	 * 新增活动方案渠道商
	 * @throws Exception
     * @Author suoxiuli 2014-09-17
	 */
    public boolean insertDealers(PtBuProActiveDealerVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
    
    /**
     * 删除活动方案渠道商信息
     * @throws Exception
     * @Author suoxiuli 2014-09-17
     */
    public void deleteProActiveDealers(String activeDealersId)
            throws Exception
    {
    	String sql = "delete from PT_BU_PRO_ACTIVE_DEALER where DTL_ID in ("+ activeDealersId +")";
    	factory.delete(sql, null);
    }
    

    /**
	 * 活动方案对应经销商查询
	 * @throws Exception
     * @Author suoxiuli 2014-09-17
	 */
    public BaseResultSet proActiveDealerSearch(PageManager page, String conditions, String activeId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND B.STATUS = 100201";
    	wheres += " AND A.ACTIVE_ID = "+ activeId;
    	wheres += " AND A.ACTIVE_ID = B.ACTIVE_ID ";
    	wheres += " AND C.STATUS = 100201";
    	wheres += " AND B.ORG_ID = C.ORG_ID ";
		wheres += " ORDER BY A.CREATE_TIME DESC ";
		page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("select A.ACTIVE_ID,\n" );
    	sql.append("       A.ACTIVE_CODE,\n" );
    	sql.append("       A.ACTIVE_NAME,\n" );
    	sql.append("       A.ACTIVE_STATUS,\n" );
    	sql.append("       A.START_DATE,\n" );
    	sql.append("       A.END_DATE,\n" );
    	sql.append("       A.ACTIVE_CONTENT,\n" );
    	sql.append("       B.DTL_ID,\n" );
    	sql.append("       B.ORG_ID,\n" );
    	sql.append("       B.CREATE_USER,\n" );
    	sql.append("       B.CREATE_TIME,\n" );
    	sql.append("       B.STATUS,\n" );
    	sql.append("       C.CODE,\n" );
    	sql.append("       C.ONAME\n" );
    	sql.append("  from PT_BU_PRO_ACTIVE A,\n" );
    	sql.append("       PT_BU_PRO_ACTIVE_DEALER B,\n" );
    	sql.append("       TM_ORG C " );

    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("STATUS","YXBS");
    	bs.setFieldUserID("CREATE_USER");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
    
    
  //***************************** PT_BU_PRO_ACTIVE_DEALER表信息操作 End*********************************************
}
