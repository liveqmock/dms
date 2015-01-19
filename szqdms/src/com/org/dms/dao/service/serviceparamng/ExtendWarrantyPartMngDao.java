package com.org.dms.dao.service.serviceparamng;

import com.org.dms.vo.service.SeBaExtendWarrantyPartVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.mvc.context.ActionContext;

public class ExtendWarrantyPartMngDao extends BaseDAO {
	// 定义instance
	public static final ExtendWarrantyPartMngDao getInstance(ActionContext atx) {
		ExtendWarrantyPartMngDao dao = new ExtendWarrantyPartMngDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 延保策略与配件关系批量新增
	 * 
	 * @throws Exception
	 * @Author fanpeng 2014-09-01
	 */
	public boolean batchInsertExtendWarrantyPart(String partId, String warrantyId, String warrantyCode, String warrantyName,String warrantyMonth, String creatUser)
			throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO SE_BA_EXTEND_WARRANTY_PART(RELATION_ID, PART_ID, PART_CODE, PART_NAME, \n");
		sql.append(" WARRANTY_ID, WARRANTY_CODE,WARRANTY_NAME,WARRANTY_MONTH,CREATE_USER,CREATE_TIME,UPDATE_USER,UPDATE_TIME) \n");
		sql.append(" SELECT F_GETID(),I.PART_ID,I.PART_CODE,I.PART_NAME, \n");
		sql.append(" '" + warrantyId + "', '" + warrantyCode + "', '" + warrantyName + "', '" + warrantyMonth + "','" + creatUser + "', SYSDATE,'','' \n");
		sql.append(" FROM PT_BA_INFO I \n");
		sql.append(" WHERE I.PART_ID = " + partId + " \n");
		
		
		return factory.update(sql.toString(), null);
	}

	/**
	 * 单个修改延保策略与配件关系延保月份
	 * 
	 * @throws Exception
	 * @Author fanpeng 2014-09-01
	 */
	public boolean updateExtendWarrantyPart(SeBaExtendWarrantyPartVO vo)
			throws Exception {
		return factory.update(vo);
	}
	
	/**
	 * 延保策略与配件关系批量删除
	 * 
	 * @throws Exception
	 * @Author fanpeng 2014-09-01
	 */
	public boolean batchDeleteExtendWarrantyPart(String relationIds)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM SE_BA_EXTEND_WARRANTY_PART \n");
		sql.append(" WHERE RELATION_ID IN (" + relationIds + ") \n");
		return factory.update(sql.toString(), null);
	}

	/**
	 * 延保策略与配件关系查询
	 * 
	 * @throws Exception
	 * @Author fanpeng 2014-09-01
	 */
	public BaseResultSet search(PageManager page, String conditions)
			throws Exception {
		String wheres = conditions;
		wheres += " order by T.WARRANTY_CODE,T.RELATION_ID ";
		page.setFilter(wheres);

		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.RELATION_ID,\n" );
		sql.append("       T.WARRANTY_ID,\n" );
		sql.append("       T.WARRANTY_NAME,\n" );
		sql.append("       T.WARRANTY_CODE,\n" );
		sql.append("       T.PART_ID,\n" );
		sql.append("       T.PART_CODE,\n" );
		sql.append("       T.PART_NAME,\n" );
		sql.append("       T.WARRANTY_MONTH,\n" );
		sql.append("       T.CREATE_USER,\n" );
		sql.append("       T.CREATE_TIME,\n" );
		sql.append("       T.UPDATE_USER,\n" );
		sql.append("       T.UPDATE_TIME\n" );
		sql.append("  FROM SE_BA_EXTEND_WARRANTY_PART T");

		bs = factory.select(sql.toString(), page);
		bs.setFieldDateFormat("CREATE_TIME","yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("UPDATE_TIME","yyyy-MM-dd HH:mm:ss");
		return bs;
	}
	
	/**
	 * 查询配件
	 * 
	 * @throws Exception
	 * @Author fanpeng 2014-09-01
	 */
	public BaseResultSet searchPart(PageManager page, String conditions) throws Exception {
		String wheres = conditions;
		wheres += " AND T.STATUS='100201'";
		wheres += " ORDER BY T.CREATE_TIME,T.PART_ID DESC";
		page.setFilter(wheres);

		BaseResultSet bs = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT T.PART_ID,T.PART_CODE,T.PART_NAME,T.CREATE_TIME ");
		sql.append(" FROM PT_BA_INFO T ");

		bs = factory.select(sql.toString(), page);
		return bs;
	}

}
