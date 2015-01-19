package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.vo.part.PtBaOrderCheckVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;
import com.org.dms.common.DicConstant;

public class OrderCheckDao extends BaseDAO
{
    //定义instance
    public  static final OrderCheckDao getInstance(ActionContext atx)
    {
    	OrderCheckDao dao = new OrderCheckDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断订单审核员账号是否存在
     * @throws Exception
     * @Author suoxiuli 2014-07-28
     */
	public QuerySet checkUserAccount(String orgCode) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select count(1) nums \n");
    	sql.append(" from pt_ba_order_check \n");
    	sql.append(" where org_code = '" + orgCode +"'");
    	sql.append(" and status = '" + DicConstant.YXBS_01 +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
    /**
	 * 订单审核员属性新增
	 * @throws Exception
     * @Author suoxiuli 2014-07-28
	 */
    public boolean insertOrderCheck(PtBaOrderCheckVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
    /**
	 * 订单审核员属性修改
	 * @throws Exception
     * @Author suoxiuli 2014-07-28
	 */
	public boolean updateOrderCheck(PtBaOrderCheckVO vo) throws Exception
    {
    	return factory.update(vo);
    }

	/**
	 * 订单审核员属性查询
	 * @throws Exception
     * @Author suoxiuli 2014-07-28
	 */
    public BaseResultSet search(PageManager page, String conditions) throws Exception
    {
    	String wheres = conditions;
		wheres += " order by create_time desc ";
		page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" select checker_id,user_account,user_name,org_name,org_code,status,secret_level,create_user,create_time ");
    	sql.append(" from pt_ba_order_check ");

    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("STATUS","YXBS");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }

}
