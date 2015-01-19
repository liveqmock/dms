package com.org.dms.dao.service.basicinfomng;

import com.org.dms.vo.service.SeBaFaultPatternVO;
import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 故障模式管理类
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年7月19日 
 */
public class FaultPatternMngDao extends BaseDAO
{
    //定义instance
    public  static final FaultPatternMngDao getInstance(ActionContext atx)
    {
        FaultPatternMngDao dao = new FaultPatternMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断故障模式代码是否存在
     * @param code
     * @return
     * @throws Exception
     */
	public QuerySet checkCode(String code) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM SE_BA_FAULT_PATTERN \n");
    	sql.append(" WHERE FAULT_CODE = '" + code +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	public boolean insertFaultPattern(SeBaFaultPatternVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
	public boolean updateFaultPattern(SeBaFaultPatternVO vo) throws Exception
    {
    	return factory.update(vo);
    }

	/**
	 * 故障模式查询方法
	 * @param  page
	 * @param  code
	 * @param  conditions
	 * @return
	 * @throws Exception
	 */
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " ORDER BY CREATE_TIME DESC" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.FAULT_CODE,\n" );
    	sql.append("       T.PATTERN_ID,\n" );
    	sql.append("       T.FAULT_NAME,\n" );
    	sql.append("       T.POSITION_ID,\n" );
    	sql.append("       T.POSITION_CODE,\n" );
    	sql.append("       T.SEVERITY,\n" );
    	sql.append("       (SELECT N.NAME　FROM SE_BA_CODE N WHERE T.SEVERITY=N.CODE) AS NAME,\n" );
    	sql.append("       T.CODE_ID,\n" );
    	sql.append("       T.FAULT_PATTERN_CODE,\n" );
    	sql.append("       T.FAULT_PATTERN_NAME,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME,\n" );
    	sql.append("       T.STATUS,\n" );
    	sql.append("       T.SECRET_LEVEL,\n" );
    	sql.append("       (SELECT N.POSITION_NAME FROM SE_BA_VEHICLE_POSITION N\n" );
    	sql.append("         WHERE N.POSITION_ID = T.POSITION_ID) AS POSITION_NAME\n" );
    	sql.append("  FROM SE_BA_FAULT_PATTERN T");
       
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，将字典代码翻译为字典名称
		bs.setFieldDic("STATUS", "YXBS");
		bs.setFieldDic("SECRET_LEVEL", "SJMJ");
		//设置日期字段显示格式
		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
    
    public QuerySet download(String conditions) throws Exception {

    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT  ROWNUM,T.* FROM(");
    	sql.append("SELECT T.FAULT_CODE,\n" );
    	sql.append("       T.PATTERN_ID,\n" );
    	sql.append("       T.FAULT_NAME,\n" );
    	sql.append("       T.POSITION_ID,\n" );
    	sql.append("       T.POSITION_CODE,\n" );
    	sql.append("       T.SEVERITY,\n" );
    	sql.append("       (SELECT N.NAME　FROM SE_BA_CODE N WHERE T.SEVERITY=N.CODE) AS NAME,\n" );
    	sql.append("       T.CODE_ID,\n" );
    	sql.append("       T.FAULT_PATTERN_CODE,\n" );
    	sql.append("       T.FAULT_PATTERN_NAME,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME,\n" );
    	sql.append("       T.STATUS,\n" );
    	sql.append("       T.SECRET_LEVEL,\n" );
    	sql.append("       (SELECT N.POSITION_NAME FROM SE_BA_VEHICLE_POSITION N\n" );
    	sql.append("         WHERE N.POSITION_ID = T.POSITION_ID) AS POSITION_NAME\n" );
    	sql.append("  FROM SE_BA_FAULT_PATTERN T");       
    sql.append(" WHERE ");
    sql.append(conditions+"\n" );
    sql.append("    ORDER BY CREATE_TIME");
    sql.append("    ) T ORDER BY ROWNUM");
    	// 执行方法，不需要传递conn参数
    	return factory.select(null, sql.toString());
    }
}