package com.org.dms.dao.service.oldpartMng;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 旧件库存查询DAO
 * @author zts
 *
 */
public class OldPartStorageSearchDao  extends BaseDAO{
	 
	public static final OldPartStorageSearchDao getInstance(ActionContext atx){
		OldPartStorageSearchDao dao=new OldPartStorageSearchDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 汇总查询
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet oldPartStorageSearch(PageManager page,String conditions,User user)throws Exception{
		String wheres = conditions;
  		wheres +=" GROUP BY T.STORAGE_ID, T.PART_ID, T.PART_CODE, T.PART_NAME \n"+
  				 " ORDER BY T.PART_CODE \n";
		page.setFilter(wheres);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.STORAGE_ID,\n" );
		sql.append("       T.PART_ID,\n" );
		sql.append("       T.PART_CODE,\n" );
		sql.append("       T.PART_NAME,\n" );
		sql.append("       SUM(T.SURPLUS_AMOUNT) SURPLUS_AMOUNT,\n" );
		sql.append("       SUM(T.SUM_AMOUNT) SUM_AMOUNT,\n" );
		sql.append("       SUM(T.OUT_AMOUNT) OUT_AMOUNT\n" );
		sql.append("  FROM SE_BU_RETURN_STORAGE T");
		bs=factory.select(sql.toString(), page);
		return bs;
	}
	
	/**
	 * 明细查询
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet oldPartStorageDetailSearch(PageManager page,String conditions,User user)throws Exception{
		String wheres = conditions;
  		wheres += //" AND T.OEM_COMPANY_ID="+user.getOemCompanyId()+" \n"+
  				  " ORDER BY T.PART_CODE \n";
		page.setFilter(wheres);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.STORAGE_ID,\n" );
		sql.append("       T.PART_ID,\n" );
		sql.append("       T.PART_CODE,\n" );
		sql.append("       T.PART_NAME,\n" );
		sql.append("       T.SUPPLIER_ID,\n" );
		sql.append("       T.SUPPLIER_NAME,\n" );
		sql.append("       T.SUPPLIER_CODE,\n" );
		sql.append("       T.WAREHOUSE_ID,\n" );
		sql.append("       T.WAREHOUSE_CODE,\n" );
		sql.append("       T.WAREHOUSE_NAME,\n" );
		sql.append("       T.SURPLUS_AMOUNT,\n" );
		sql.append("       T.SUM_AMOUNT,\n" );
		sql.append("       T.OUT_AMOUNT\n" );
		sql.append("  FROM SE_BU_RETURN_STORAGE T\n" );
		bs=factory.select(sql.toString(), page);
		return bs;
	}
}
