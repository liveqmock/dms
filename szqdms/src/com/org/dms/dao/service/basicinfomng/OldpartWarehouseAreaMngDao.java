package com.org.dms.dao.service.basicinfomng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBaWarehouseAreaVO;
import com.org.dms.vo.service.SeBuWarehouseCentrostigmaVO;
import com.org.dms.vo.service.SeBuWarehouseUserVO;
import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 旧件库区管理类
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年8月19日 
 */
public class OldpartWarehouseAreaMngDao extends BaseDAO
{
    //定义instance
    public  static final OldpartWarehouseAreaMngDao getInstance(ActionContext atx)
    {
        OldpartWarehouseAreaMngDao dao = new OldpartWarehouseAreaMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断旧件库区代码是否存在
     * @param code
     * @return
     * @throws Exception
     */
	public QuerySet checkCode(String code) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM SE_BA_WAREHOUSE_AREA \n");
    	sql.append(" WHERE AREA_CODE = '" + code +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	public boolean insertOldpartWarehouseArea(SeBaWarehouseAreaVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	public boolean insertOldpartWarehouseUser(SeBuWarehouseUserVO vo)
			throws Exception
			{
		return factory.insert(vo);
			}
	public boolean insertOldpartWarehouseOrg(SeBuWarehouseCentrostigmaVO vo)
			throws Exception
			{
		return factory.insert(vo);
			}
	public boolean updateOldpartWarehouseOrg(SeBuWarehouseCentrostigmaVO vo)
			throws Exception
			{
		return factory.update(vo);
			}
	public boolean updateOldpartWarehouseUser(SeBuWarehouseUserVO vo)
			throws Exception
			{
		return factory.update(vo);
			}
	
	public boolean updateOldpartWarehouseArea(SeBaWarehouseAreaVO vo) throws Exception
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
    	wheres += " ORDER　BY　AREA_CODE" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;

    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.AREA_ID,\n" );
    	sql.append("       T.AREA_CODE,\n" );
    	sql.append("       T.AREA_NAME,\n" );
    	sql.append("       T.WAREHOUSE_ID,\n" );
    	sql.append("       T.WAREHOUSE_CODE,\n" );
    	sql.append("       T.WAREHOUSE_NAME,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME,\n" );
    	sql.append("       T.STATUS\n" );
    	sql.append("  FROM SE_BA_WAREHOUSE_AREA T");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，将字典代码翻译为字典名称
		bs.setFieldDic("STATUS", "YXBS");
		//bs.setFieldDic("SECRET_LEVEL", "SJMJ");
		//设置日期字段显示格式
		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
    /**
     * 查询方法
     * @param  page
     * @param  code
     * @param  conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet WarehouseSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND U.WAREHOUSE_ID = W.WAREHOUSE_ID AND T.USER_ID = U.USER_ID AND U.STATUS="+DicConstant.YXBS_01+" ORDER BY USER_ID" ;
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT U.USER_ID,\n" );
    	sql.append("       U.RELATION_ID,\n" );
    	sql.append("       U.CREATE_USER,\n" );
    	sql.append("       U.CREATE_TIME,\n" );
    	sql.append("       U.UPDATE_USER,\n" );
    	sql.append("       U.UPDATE_TIME,\n" );
    	sql.append("       T.USER_SN,\n" );
    	sql.append("       T.PERSON_NAME,\n" );
    	sql.append("       W.WAREHOUSE_ID,\n" );
    	sql.append("       W.WAREHOUSE_CODE,\n" );
    	sql.append("       W.WAREHOUSE_NAME,\n" );
    	sql.append("       W.WAREHOUSE_TYPE,\n" );
    	sql.append("       U.STATUS\n" );
    	sql.append("  FROM SE_BU_WAREHOUSE_USER U, SE_BA_WAREHOUSE W,TM_USER T");
    	
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，将字典代码翻译为字典名称
    	bs.setFieldDic("STATUS", "YXBS");
    	//bs.setFieldDic("SECRET_LEVEL", "SJMJ");
    	//设置日期字段显示格式
    	bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDic("WAREHOUSE_TYPE", "JJKCLX");
    	return bs;
    }
    /**
     * 查询方法
     * @param  page
     * @param  code
     * @param  conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet returnDealerSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND C.WAREHOUSE_ID = W.WAREHOUSE_ID AND G.ORG_ID = C.ORG_ID AND C.STATUS = "+DicConstant.YXBS_01+" ORDER BY ORG_ID" ;
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT C.ORG_ID,\n" );
    	sql.append("       C.RELATION_ID,\n" );
    	sql.append("       C.CREATE_USER,\n" );
    	sql.append("       C.CREATE_TIME,\n" );
    	sql.append("       C.UPDATE_USER,\n" );
    	sql.append("       C.UPDATE_TIME,\n" );
    	sql.append("       G.CODE,\n" );
    	sql.append("       G.ONAME,\n" );
    	sql.append("       W.WAREHOUSE_ID,\n" );
    	sql.append("       W.WAREHOUSE_CODE,\n" );
    	sql.append("       W.WAREHOUSE_NAME,\n" );
    	sql.append("       W.WAREHOUSE_TYPE,\n" );
    	sql.append("       C.STATUS\n" );
    	sql.append("  FROM SE_BU_WAREHOUSE_CENTROSTIGMA C, SE_BA_WAREHOUSE W,  TM_ORG G\n" );
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，将字典代码翻译为字典名称
    	bs.setFieldDic("STATUS", "YXBS");
    	//bs.setFieldDic("SECRET_LEVEL", "SJMJ");
    	//设置日期字段显示格式
    	bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDic("WAREHOUSE_TYPE", "JJKCLX");
    	return bs;
    }
}