package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.vo.part.PtBaMinVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.mvc.context.ActionContext;

public class PtBaMinDao extends BaseDAO {
	// 定义instance
	public static final PtBaMinDao getInstance(ActionContext atx) {
		PtBaMinDao dao = new PtBaMinDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}
	
	//插入信息
	public boolean insertPtBaMin(PtBaMinVO vo) throws Exception {
		return factory.insert(vo);
	}
	
	
	//修改信息
	public boolean updatePtBaMin(PtBaMinVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	
	//删除信息
	public boolean updatePtBaMinStatus(String min_id, String status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_MIN \n");
    	sql.append(" SET STATUS = " + status + " \n");
    	sql.append(" WHERE MIN_ID = " + min_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	
	//查询
	public BaseResultSet search(PageManager page,String conditions) throws Exception
    {
    	String wheres = conditions;  
//    	wheres += " ORDER BY CREATE_TIME DESC,PART_ID DESC";
    	  	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("MIN_ID,\n" );
    	sql.append("MIN_PACK,\n" );
    	sql.append("MIN_UNIT,\n" );
    	sql.append("STATUS\n" );
    	sql.append("FROM PT_BA_MIN");
    	

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
        bs.setFieldDic("STATUS", "YXBS");   //状态
        
//        bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:MM:SS");
//		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:MM:SS");
         
    	return bs;
    }
	
}
