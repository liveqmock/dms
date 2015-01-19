package com.org.dms.dao.service.basicinfomng;

import com.org.dms.vo.service.SeBaDriveVinVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class VehicleDriveMngDao extends BaseDAO{
	
 public  static final VehicleDriveMngDao getInstance(ActionContext atx)
    {
	 	VehicleDriveMngDao dao = new VehicleDriveMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	  wheres +=" ORDER BY RL_ID ";
    	page.setFilter(wheres);
    	  //定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql1= new StringBuffer();
    	sql1.append("SELECT RL_ID,\n" );
    	sql1.append("       VIN,\n" );
    	sql1.append("       DRIVE_TYPE,\n" );
    	sql1.append("       CREATE_USER,\n" );
    	sql1.append("       CREATE_TIME,\n" );
    	sql1.append("       UPDATE_USER,\n" );
    	sql1.append("       UPDATE_TIME,\n" );
    	sql1.append("       STATUS\n" );
    	sql1.append("  FROM SE_BA_DRIVE_VIN");
    	bs = factory.select(sql1.toString(), page);
        bs.setFieldDic("STATUS","YXBS");
        bs.setFieldDic("DRIVE_TYPE","QDXS");
        bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
        bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
     return bs;
     }
    public boolean update(SeBaDriveVinVO vo) throws Exception {
		return factory.update(vo);
	}
    public boolean insert(SeBaDriveVinVO vo) throws Exception {
		return factory.insert(vo);
	}
    public QuerySet check(String vin) throws Exception {
		QuerySet qs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("  SELECT COUNT(1) NUMS FROM  SE_BA_DRIVE_VIN ");
		sql.append("  WHERE VIN='");
		sql.append(vin);
		sql.append("' AND STATUS=100201");
		qs = factory.select(null, sql.toString());
		return qs;
	}

}
