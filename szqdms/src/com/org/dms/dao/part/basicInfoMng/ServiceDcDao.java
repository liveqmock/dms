package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.vo.part.PtBaServiceDcVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

public class ServiceDcDao extends BaseDAO
{
    //定义instance
    public  static final ServiceDcDao getInstance(ActionContext atx)
    {
    	ServiceDcDao dao = new ServiceDcDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断渠道商orgId是否存在配送中心dcId
     * @throws Exception
     * @Author suoxiuli 2014-07-30
     */
	public QuerySet checkOrgIdIsDcId(String dc_id, String orgId) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select count(1) nums \n");
    	sql.append(" from pt_ba_service_dc \n");
    	sql.append(" where dc_id = '" + dc_id +"'");
    	sql.append(" and org_id = '" + orgId +"'");
    	sql.append(" and status = '100201'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	/**
	 * 判断用户是否已经存在默认且有效的配送中心 
	 * @Title: checkOrgLinkStatus
	 * @Description: TODO
	 * @param orgid
	 * @return true 存在，false不存在
	 * @return: boolean
	 */
	public boolean checkOrgLinkStatus(String orgId) throws Exception {
		return Integer.parseInt(this.factory.select(
				"SELECT COUNT(1)\n" +
						"  FROM PT_BA_SERVICE_DC T\n" + 
						" WHERE T.ORG_ID = ?\n" + 
						"   AND T.IF_DEFAULT = 100101"
					, new Object[]{orgId})[0][0]) > 0;
	}
	
    /**
	 * 配送关系新增
	 * @throws Exception
     * @Author suoxiuli 2014-07-30
	 */
    public boolean insertServiceDc(PtBaServiceDcVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
    /**
	 * 配送关系修改
	 * @throws Exception
     * @Author suoxiuli 2014-07-30
	 */
	public boolean updateServiceDc(PtBaServiceDcVO vo) throws Exception
    {
    	return factory.update(vo);
    }

	/**
	 * 配送关系查询
	 * @throws Exception
     * @Author suoxiuli 2014-07-30
	 */
    public BaseResultSet search(PageManager page, String conditions) throws Exception
    {
    	String wheres = conditions;
        wheres += " and od.status='100201' ";
        wheres += " and os.status='100201' ";
        wheres += " and sd.dc_id=od.org_id ";
        wheres += " and sd.org_id=os.org_id ";
		wheres += " order by sd.dc_code, sd.create_time desc ";
		page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("select sd.relation_id, sd.dc_id, sd.dc_code as dc_code, od.oname as dc_name,\n" );
    	sql.append(" sd.org_id,  sd.org_code, os.oname as org_name,\n" );
    	sql.append(" sd.if_default, sd.status, sd.create_user, sd.create_time\n" );
    	sql.append(" from pt_ba_service_dc sd,\n" );
    	sql.append(" tm_org od,\n" );
    	sql.append(" tm_org os\n" );

    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("IF_DEFAULT","SF");
    	bs.setFieldDic("STATUS","YXBS");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }

    /**
     * 更新配送关系的有效状态
     * @throws Exception
     * @Author suoxiuli 2014-07-30
     */
    public boolean updateServiceDcStatus(String relationId, String updateUser, String status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" update pt_ba_service_dc \n");
    	sql.append(" set status = '" + status + "', \n");
    	sql.append(" update_user = '" + updateUser + "', \n");
    	sql.append(" update_time = sysdate \n");
    	sql.append(" where relation_id = '" + relationId + "' \n");
    	return factory.update(sql.toString(), null);
    }
    
    /**
	 * 配送中心及渠道商查询
	 * @throws Exception
     * @Author suoxiuli 2014-08-30
	 */
    public BaseResultSet serviceDcSearch(PageManager page, String conditions, String orgType) throws Exception
    {
    	String wheres = conditions;
        wheres += " and status='100201' ";
        wheres += " and org_type in ("+orgType+")";
		wheres += " order by org_id desc ";
		page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("select org_id, code, oname \n" );
    	sql.append(" from tm_org \n" );

    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("STATUS","YXBS");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
}
