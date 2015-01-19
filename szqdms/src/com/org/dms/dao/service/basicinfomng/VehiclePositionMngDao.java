package com.org.dms.dao.service.basicinfomng;

import com.org.dms.vo.service.SeBaVehiclePositionVO;
import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 车辆部位管理类
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年09月05日 
 */
public class VehiclePositionMngDao extends BaseDAO
{
    //定义instance
    public  static final VehiclePositionMngDao getInstance(ActionContext atx)
    {
        VehiclePositionMngDao dao = new VehiclePositionMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断车辆部位代码是否存在
     * @param code
     * @return
     * @throws Exception
     */
	public QuerySet checkCode(String code) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM SE_BA_VEHICLE_POSITION \n");
    	sql.append(" WHERE POSITION_CODE = '" + code +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	public boolean insertVehiclePosition(SeBaVehiclePositionVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean updateVehiclePosition(SeBaVehiclePositionVO vo) throws Exception
    {
    	return factory.update(vo);
    }

	/**
	 * 查询方法
	 * @param  page
	 * @param  code
	 * @param  conditions
	 * @return
	 * @throws Exception
	 */
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " ORDER　BY　POSITION_LEVEL,POSITION_CODE" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT POSITION_ID,\n" );
        sql.append("       POSITION_CODE,\n" );
        sql.append("       POSITION_NAME,\n" );
        sql.append("       POSITION_LEVEL,\n" );
        sql.append("       P_ID,\n" );
        sql.append("       P_NAME,\n" );
        sql.append("       P_CODE,\n" );
        sql.append("       STATUS,\n" );
        sql.append("       CREATE_USER,\n" );
        sql.append("       CREATE_TIME,\n" );
        sql.append("       UPDATE_USER,\n" );
        sql.append("       UPDATE_TIME\n" );
        sql.append("  FROM SE_BA_VEHICLE_POSITION");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，将字典代码翻译为字典名称
		bs.setFieldDic("STATUS", "YXBS");
		//设置日期字段显示格式
		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
    
    //下载(导出)
    public QuerySet download(String conditions, User user) throws Exception
    {	    	
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT ROWNUM,\n" );
        sql.append("       T.POSITION_ID,\n" );
        sql.append("       T.POSITION_CODE,\n" );
        sql.append("       T.POSITION_NAME,\n" );
        sql.append("       T.POSITION_LEVEL,\n" );
        sql.append("       T.P_ID,\n" );
        sql.append("       T.P_NAME,\n" );
        sql.append("       T.P_CODE,\n" );
        sql.append("       T.STATUS,\n" );
        sql.append("       T.CREATE_USER,\n" );
        sql.append("       T.CREATE_TIME,\n" );
        sql.append("       T.UPDATE_USER,\n" );
        sql.append("       T.UPDATE_TIME,\n" );
        sql.append("       M.DIC_VALUE\n" );
        sql.append("  FROM SE_BA_VEHICLE_POSITION T, DIC_TREE M");
		sql.append(" where "+conditions+"\n" );
		sql.append(" AND TO_CHAR(T.STATUS)=M.DIC_CODE\n" );

    	return factory.select(null, sql.toString());
    }
}