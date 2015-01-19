package com.org.dms.dao.service.basicinfomng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBaStrategyVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * @Description:三包策略管理
 * @Date: 2014-09-02
 * @author fanpeng
 * @remark 
 */
public class StrategyMngDao extends BaseDAO
{
    //定义instance
    public  static final StrategyMngDao getInstance(ActionContext atx)
    {
        StrategyMngDao dao = new StrategyMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    /**
     * 判断三包策略代码是否存在
     * @param code
     * @return
     * @throws Exception
     */
	public QuerySet checkCode(String code) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM SE_BA_STRATEGY \n");
    	sql.append(" WHERE STRATEGY_CODE = '" + code +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    
    //新增三包策略
	public boolean insertStrategy(SeBaStrategyVO vo)
            throws Exception
    {
		//String activityId=SequenceUtil.getCommonSerivalNumber(factory);
		//vo.setActivityId(activityId);
    	return factory.insert(vo);
    }

	//新增三包车型
	public boolean insertModels(String modelsIds,String createUser,String strategyId)
	            throws Exception
	    {
		StringBuffer sql= new StringBuffer();
		sql.append("INSERT INTO SE_BA_STRATEGY_MODELS\n" );
		sql.append("  (RELATION_ID,\n" );
		sql.append("   MODELS_ID,\n" );
		sql.append("   STRATEGY_ID,\n" );
		sql.append("   CREATE_USER,\n" );
		sql.append("   CREATE_TIME,\n" );
		sql.append("   STATUS)\n" );
		sql.append("  SELECT F_GETID(),\n" );
		sql.append("         B.MODELS_ID,\n" );
		sql.append("         '"+strategyId+"',\n" );
		sql.append("         '"+createUser+"',\n" );
		sql.append("         SYSDATE,\n" );
		sql.append("         '"+DicConstant.YXBS_01+"'\n" );
		sql.append("    FROM MAIN_MODELS B\n" );
		sql.append("   WHERE B.MODELS_ID IN ("+modelsIds+")");
		return factory.update(sql.toString(),null);
	    }
	
	//新增三包省份
	public boolean insertProvince(String provinceIds,String createUser,String strategyId)
	            throws Exception
	    {
		StringBuffer sql= new StringBuffer();
		sql.append("INSERT INTO SE_BA_STRATEGY_PROVINCE\n" );
		sql.append("  (RELATION_ID,\n" );
		sql.append("   PROVINCE_ID,\n" );
		sql.append("   STRATEGY_ID,\n" );
		sql.append("   CREATE_USER,\n" );
		sql.append("   CREATE_TIME,\n" );
		sql.append("   STATUS)\n" );
		sql.append("  SELECT F_GETID(),\n" );
		sql.append("         B.DM,\n" );
		sql.append("         '"+strategyId+"',\n" );
		sql.append("         '"+createUser+"',\n" );
		sql.append("         SYSDATE,\n" );
		sql.append("         '"+DicConstant.YXBS_01+"'\n" );
		sql.append("    FROM TM_DIVISION B\n" );
		sql.append("   WHERE B.DM IN ("+provinceIds+")");
		return factory.update(sql.toString(),null);
	    }
	
	//修改三包策略
	public boolean updateStrategy(SeBaStrategyVO tempVO) throws Exception
    {
    	return factory.update(tempVO);
    }

	/**
	 * @title: search三包策略
	 * @date 2014年09月02日
	 */
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " ORDER BY STRATEGY_ID DESC";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT STRATEGY_ID,\n" );
    	sql.append("       STRATEGY_NAME,\n" );
    	sql.append("       STRATEGY_CODE,\n" );
    	sql.append("       BEGIN_DATE,\n" );
    	sql.append("       END_DATE,\n" );
    	sql.append("       RULE_ID,\n" );
    	sql.append("       RULE_CODE,\n" );
    	sql.append("       REMARKS,\n" );
    	sql.append("       CREATE_USER,\n" );
    	sql.append("       CREATE_TIME,\n" );
    	sql.append("       UPDATE_USER,\n" );
    	sql.append("       UPDATE_TIME,\n" );
    	sql.append("       STATUS\n" );
    	sql.append("  FROM SE_BA_STRATEGY T");
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDateFormat("BEGIN_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
		bs.setFieldDic("STATUS","YXBS");
    	return bs;
    }
    /**
	 * @title: searchStrategyModels
	 * 查询三包策略车型
	 * author:fanpeng
	 * @date 2014年09月03日
	 */
    public BaseResultSet searchStrategyModels(PageManager page, User user, String conditions,String strategyId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.STATUS='"+DicConstant.YXBS_01+"'";
    	wheres += " AND T.MODELS_ID = B.MODELS_ID AND T.STRATEGY_ID = M.STRATEGY_ID  AND T.STRATEGY_ID = '"+strategyId+"' "
    		   +"ORDER BY T.RELATION_ID DESC";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.RELATION_ID,\n" );
    	sql.append("       T.STRATEGY_ID,\n" );
    	sql.append("       T.MODELS_ID,\n" );
    	sql.append("       M.STRATEGY_CODE,\n" );
    	sql.append("       M.STRATEGY_NAME,\n" );
    	sql.append("       B.MODELS_NAME,\n" );
    	sql.append("       B.MODELS_CODE,\n" );
    	sql.append("       T.STATUS,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME\n" );
    	sql.append("  FROM SE_BA_STRATEGY_MODELS T, SE_BA_STRATEGY M,\n" );
    	sql.append("       MAIN_MODELS B\n" );
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    
    /**
	 * @title: searchStrategyProvince
	 * 查询三包策略省份
	 * author:fanpeng
	 * @date 2014年09月03日
	 */
    public BaseResultSet searchStrategyProvince(PageManager page, User user, String conditions,String strategyId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.STATUS='"+DicConstant.YXBS_01+"'";
    	wheres += " AND T.PROVINCE_ID = B.DM AND T.STRATEGY_ID = M.STRATEGY_ID  AND T.STRATEGY_ID = '"+strategyId+"' "
    		   +"ORDER BY T.RELATION_ID DESC";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.RELATION_ID,\n" );
    	sql.append("       T.STRATEGY_ID,\n" );
    	sql.append("       T.PROVINCE_ID,\n" );
    	sql.append("       M.STRATEGY_CODE,\n" );
    	sql.append("       M.STRATEGY_NAME,\n" );
    	sql.append("       B.MC,\n" );
    	sql.append("       T.STATUS,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME\n" );
    	sql.append("  FROM SE_BA_STRATEGY_PROVINCE T, SE_BA_STRATEGY M,\n" );
    	sql.append("       TM_DIVISION B\n" );
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    
    /**
	 * @title: searchModels
	 * 查询所有未绑定三包策略的车型
	 * author:fanpeng
	 * @date 2014年09月03日
	 */
    public BaseResultSet searchModels(PageManager page, User user, String conditions,String strategyId) throws Exception
    {
    	String wheres = conditions;
		wheres += " AND A.STATUS='"+DicConstant.YXBS_01+"'";
    	wheres += "AND NOT EXISTS (SELECT B.MODELS_ID FROM SE_BA_STRATEGY_MODELS B  WHERE A.MODELS_ID = B.MODELS_ID  AND B.STRATEGY_ID = "+strategyId+" AND B.STATUS='"+DicConstant.YXBS_01+"') "
    	       +"ORDER BY A.MODELS_CODE";
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.MODELS_ID,\n" );
    	sql.append("       A.MODELS_CODE,\n" );
    	sql.append("       A.MODELS_NAME,\n" );
    	sql.append("       A.MODELS_STATUS,\n" );
    	sql.append("       A.CREATE_USER,\n" );
    	sql.append("       A.CREATE_TIME,\n" );
    	sql.append("       A.UPDATE_USER,\n" );
    	sql.append("       A.UPDATE_TIME\n" );
    	sql.append("  FROM MAIN_MODELS A\n" );
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    
    /**
	 * @title: searchProvince
	 * 查询所有未绑定三包策略的省份
	 * author:fanpeng
	 * @date 2014年09月04日
	 */
    public BaseResultSet searchProvince(PageManager page, User user, String conditions,String strategyId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND A.LX='20'";
    	wheres += "AND NOT EXISTS (SELECT B.PROVINCE_ID FROM SE_BA_STRATEGY_PROVINCE B  WHERE A.DM = B.PROVINCE_ID  AND B.STRATEGY_ID = "+strategyId+" AND B.STATUS='"+DicConstant.YXBS_01+"') "
    	       +"ORDER BY A.DM";
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.DM,\n" );
    	sql.append("       A.MC,\n" );
    	sql.append("       A.JC,\n" );
    	sql.append("       A.LX\n" );
    	sql.append("  FROM TM_DIVISION A\n" );
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
       
       /**
        * @title: deleteModels
        * 删除三包策略车型
        * @return
        * @throws Exception    设定文件 
        * @author:fanpeng
        * @return boolean    返回类型 
        * @date 2014年09月03日
        */
       public boolean deleteModels(String relationIds, String userName) throws Exception
       {
       	StringBuffer sql = new StringBuffer();
       	sql.append(" UPDATE SE_BA_STRATEGY_MODELS A SET A.UPDATE_USER = '"+userName+"', A.UPDATE_TIME = SYSDATE, A.STATUS = '"+DicConstant.YXBS_02+"' WHERE A.RELATION_ID IN ("+relationIds+") \n");
       	return factory.delete(sql.toString(), null);
       }
       
       /**
        * @title: deleteProvince
        * 删除三包策略省份
        * @return
        * @throws Exception    设定文件 
        * @author:fanpeng
        * @return boolean    返回类型 
        * @date 2014年09月03日
        */
       public boolean deleteProvince(String relationIds, String userName) throws Exception
       {
       	StringBuffer sql = new StringBuffer();
       	sql.append(" UPDATE SE_BA_STRATEGY_PROVINCE A SET A.UPDATE_USER = '"+userName+"', A.UPDATE_TIME = SYSDATE, A.STATUS = '"+DicConstant.YXBS_02+"' WHERE A.RELATION_ID IN ("+relationIds+") \n");
       	return factory.delete(sql.toString(), null);
       }  
}