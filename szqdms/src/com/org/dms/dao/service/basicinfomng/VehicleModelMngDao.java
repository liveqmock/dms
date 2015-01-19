package com.org.dms.dao.service.basicinfomng;

import com.org.dms.vo.service.MainModelsVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class VehicleModelMngDao extends BaseDAO{
	  //定义instance
    public  static final VehicleModelMngDao getInstance(ActionContext atx)
    {
    	VehicleModelMngDao dao = new VehicleModelMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	  wheres +=" ORDER BY MODELS_ID ";
    	page.setFilter(wheres);
    	  //定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append("SELECT M.MODELS_ID,\n" );
    	sql.append("       M.MODELS_CODE,\n" );
    	sql.append("       M.MODELS_NAME,\n" );
    	sql.append("       M.STATUS,\n" );
    	sql.append("       M.CREATE_USER,\n" );
    	sql.append("       M.CREATE_TIME,\n" );
    	sql.append("       M.UPDATE_USER,\n" );
    	sql.append("       M.UPDATE_TIME\n" );
    	sql.append("  FROM MAIN_MODELS M");
    	bs = factory.select(sql.toString(), page);
        bs.setFieldDic("STATUS","YXBS");
        bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
        bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
     return bs;
     }
    public boolean update(MainModelsVO vo) throws Exception {
		return factory.update(vo);
	}
    public boolean insert(MainModelsVO vo) throws Exception {
		return factory.insert(vo);
	}
    public QuerySet check(String modelCode) throws Exception {
		QuerySet qs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT COUNT(1) NUMS FROM  MAIN_MODELS ");
		sql.append("  WHERE MODELS_CODE='");
		sql.append(modelCode);
		sql.append("' AND STATUS=100201");
		qs = factory.select(null, sql.toString());
		return qs;
	}
}
