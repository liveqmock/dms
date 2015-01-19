package com.org.dms.dao.service.basicinfomng;

import com.org.dms.vo.service.SeBaWarehouseSupplierVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.mvc.context.ActionContext;

public class OldpartWarehouseAreaSupplierMngDao extends BaseDAO {
	// 定义instance
	public static final OldpartWarehouseAreaSupplierMngDao getInstance(ActionContext atx) {
		OldpartWarehouseAreaSupplierMngDao dao = new OldpartWarehouseAreaSupplierMngDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 供应商旧件库区批量新增
	 * 
	 * @throws Exception
	 * @Author fanpeng 2014-08-22
	 */
	public boolean batchInsertWarehouseAreaSupplier(String supplierIds, String areaId, String areaCode, String areaName)
			throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO SE_BA_WAREHOUSE_SUPPLIER(RELATION_ID, SUPPLIER_ID, SUPPLIER_CODE, SUPPLIER_NAME, \n");
		sql.append(" AREA_ID, AREA_CODE,AREA_NAME) \n");
		sql.append(" SELECT F_GETID(),I.SUPPLIER_ID,I.SUPPLIER_CODE,I.SUPPLIER_NAME, \n");
		sql.append(" '" + areaId + "', '" + areaCode + "', '" + areaName + "' \n");
		sql.append(" FROM PT_BA_SUPPLIER I \n");
		sql.append(" WHERE I.SUPPLIER_ID IN (" + supplierIds + ") \n");
		
		
		return factory.update(sql.toString(), null);
	}

	/**
	 * 供应商旧件库区单个修改
	 * 
	 * @throws Exception
	 * @Author fanpeng 2014-08-23
	 */
	public boolean updateWarehouseAreaSupplier(SeBaWarehouseSupplierVO vo)
			throws Exception {
		return factory.update(vo);
	}

	/**
	 * 供应商旧件库区批量修改
	 * 
	 * @throws Exception
	 * @Author fanpeng 2014-08-25
	 */
	public boolean batchUpdateWarehouseAreaSupplier(String relationIds,String areaId, String areaCode, String areaName)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE SE_BA_WAREHOUSE_SUPPLIER \n");
		sql.append(" SET AREA_ID = '" + areaId + "', \n");
		sql.append(" AREA_CODE = '" + areaCode + "', \n");
		sql.append(" AREA_NAME = '" + areaName + "'  \n");
		sql.append(" WHERE RELATION_ID IN (" + relationIds + ") \n");
		return factory.update(sql.toString(), null);
	}
	
	/**
	 * 供应商旧件库区批量删除
	 * 
	 * @throws Exception
	 * @Author fanpeng 2014-08-25
	 */
	public boolean batchDeleteWarehouseAreaSupplier(String relationIds)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM SE_BA_WAREHOUSE_SUPPLIER \n");
		sql.append(" WHERE RELATION_ID IN (" + relationIds + ") \n");
		return factory.update(sql.toString(), null);
	}

	/**
	 * 供应商旧件库区查询
	 * 
	 * @throws Exception
	 * @Author fanpeng 2014-08-23
	 */
	public BaseResultSet search(PageManager page, String conditions)
			throws Exception {
		String wheres = conditions;
		wheres += " order by AREA_CODE ";
		page.setFilter(wheres);

		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.RELATION_ID,\n" );
		sql.append("       T.AREA_ID,\n" );
		sql.append("       T.AREA_CODE,\n" );
		sql.append("       T.AREA_NAME,\n" );
		sql.append("       T.SUPPLIER_ID,\n" );
		sql.append("       T.SUPPLIER_NAME,\n" );
		sql.append("       T.SUPPLIER_CODE\n" );
		sql.append("  FROM SE_BA_WAREHOUSE_SUPPLIER T");

		bs = factory.select(sql.toString(), page);
		return bs;
	}
	
	/**
	 * 批量搜索没有绑定旧件库区的供应商
	 * 
	 * @throws Exception
	 * @Author fanpeng 2014-08-23
	 */
	public BaseResultSet searchNewSupplier(PageManager page, String conditions) throws Exception {
		String wheres = conditions;
		wheres += " AND T.STATUS='100201' AND  NOT EXISTS";
		wheres += " (SELECT 1 FROM SE_BA_WAREHOUSE_SUPPLIER K WHERE K.SUPPLIER_ID=T.SUPPLIER_ID) ";
		wheres += " ORDER BY T.CREATE_TIME DESC";
		page.setFilter(wheres);

		BaseResultSet bs = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT T.SUPPLIER_ID,T.SUPPLIER_CODE,T.SUPPLIER_NAME,T.CREATE_TIME ");
		sql.append(" FROM PT_BA_SUPPLIER T ");

		bs = factory.select(sql.toString(), page);
		return bs;
	}

}
