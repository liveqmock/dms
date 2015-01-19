package com.org.dms.dao.service.serviceparamng;

import com.org.dms.vo.service.SeBaExtendWarrantyVO;
import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 延保策略管理类
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年8月29日 
 */
public class ExtendWarrantyMngDao extends BaseDAO
{
    //定义instance
    public  static final ExtendWarrantyMngDao getInstance(ActionContext atx)
    {
        ExtendWarrantyMngDao dao = new ExtendWarrantyMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断延保策略代码是否存在
     * @throws Exception
     */
	public QuerySet checkWarrantyCode(String warrantyCode) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM SE_BA_EXTEND_WARRANTY \n");
    	sql.append(" WHERE WARRANTY_CODE = '" + warrantyCode +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	public boolean insertExtendWarranty(SeBaExtendWarrantyVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean updateExtendWarranty(SeBaExtendWarrantyVO vo) throws Exception
    {
    	return factory.update(vo);
    }

	/**
	 * 查询方法
	 * @throws Exception
	 */
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " ORDER　BY　CREATE_TIME DESC" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT WARRANTY_ID,\n" );
        sql.append("       WARRANTY_NAME,\n" );
        sql.append("       WARRANTY_CODE,\n" );
        sql.append("       REMARKS,\n" );
        sql.append("       STATUS,\n" );
        sql.append("       CREATE_USER,\n" );
        sql.append("       CREATE_TIME,\n" );
        sql.append("       UPDATE_USER,\n" );
        sql.append("       UPDATE_TIME\n" );
        sql.append("  FROM SE_BA_EXTEND_WARRANTY");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，将字典代码翻译为字典名称
		bs.setFieldDic("STATUS", "YXBS");
		//设置日期字段显示格式
		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
    
	/**
	 * 服务商车辆延保查询方法
	 * @throws Exception
	 */
    public BaseResultSet searchDealer(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND C.WARRANTY_ID = B.WARRANTY_ID AND C.WARRANTY_ID = A.WARRANTY_ID" ;
    	wheres += " ORDER　BY　A.VIN" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.WARRANTY_ID,\n" );
        sql.append("       A.WARRANTY_NAME,\n" );
        sql.append("       A.VIN,\n" );
        sql.append("       B.PART_CODE,\n" );
        sql.append("       B.PART_NAME,\n" );
        sql.append("       B.WARRANTY_MONTH,\n" );
        sql.append("       C.REMARKS\n" );
        sql.append("  FROM SE_BA_EXTEND_WARRANTY_VEHICLE A, SE_BA_EXTEND_WARRANTY_PART B, SE_BA_EXTEND_WARRANTY C");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
}