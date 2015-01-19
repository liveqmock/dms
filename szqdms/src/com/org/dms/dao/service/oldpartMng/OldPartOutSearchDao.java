package com.org.dms.dao.service.oldpartMng;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * 旧件出库查询
 * @author zts
 *
 */
public class OldPartOutSearchDao extends BaseDAO{

	 //定义instance
	public  static final OldPartOutSearchDao getInstance(ActionContext atx)
	{
		OldPartOutSearchDao dao = new OldPartOutSearchDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	   * 旧件出库查询
	   * @param page
	   * @param conditions
	   * @param user
	   * @return
	   * @throws Exception
	   */
	  	public BaseResultSet oldPartOutSearch(PageManager page,String conditions,User user)throws Exception{
	  		String wheres = conditions;
	  		wheres += " AND T.OEM_COMPANY_ID="+user.getOemCompanyId()+" \n"+
	  				  " ORDER BY T.PART_CODE \n";
			page.setFilter(wheres);
			BaseResultSet bs=null;
			StringBuffer sql= new StringBuffer();
			sql.append("SELECT T.OUT_ID,\n" );
			sql.append("       T.STORAGE_ID,\n" );
			sql.append("       T.PART_ID,\n" );
			sql.append("       T.PART_CODE,\n" );
			sql.append("       T.PART_NAME,\n" );
			sql.append("       T.SUPPLIER_ID,\n" );
			sql.append("       T.SUPPLIER_NAME,\n" );
			sql.append("       T.SUPPLIER_CODE,\n" );
			sql.append("       T.OUT_TYPE,\n" );
			sql.append("       T.OUT_AMOUNT,\n" );
			sql.append("       T.OUT_DATE,\n" );
			sql.append("       T.REMARKS\n" );
			sql.append("  FROM SE_BU_RETURN_OUT T");
			bs=factory.select(sql.toString(), page);
			bs.setFieldDic("OUT_TYPE","JJCKLX");
			bs.setFieldDateFormat("OUT_DATE","yyyy-MM-dd");
			return bs;
	  	}
}
