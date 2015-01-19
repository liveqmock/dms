package com.org.dms.dao.part.basicInfoMng;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.mvc.context.ActionContext;

/**
 * 
 * @ClassName: PartInfoQueryDao
 * @Description: 配件信息查询Dao
 * @author: fuxiao
 * @date: 2014年12月9日 下午3:07:31
 */
public class PartInfoQueryDao extends BaseDAO {

	public static final PartInfoQueryDao getInstance(ActionContext ac) {
		PartInfoQueryDao dao = new PartInfoQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 
	 * @Title: queryList
	 * @Description: 表格内容查询
	 * @param pageManager
	 * @param conditions
	 * @param user
	 * @param isQueryArea
	 * @return
	 * @throws Exception
	 * @return: BaseResultSet
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions)
			throws Exception {
		BaseResultSet rs = null;
		String sql = "SELECT\n" + "       T.PART_ID,\n"
				+ "       T.PART_CODE,\n" + "       T.PART_NAME,\n"
				+ "       T.UNIT,\n"
				+ "       T.PART_TYPE,\n" + "       T.ATTRIBUTE,\n"
				+ "       T.MIN_PACK,\n" + "       T.MIN_UNIT,\n"
				+ "       T.BELONG_ASSEMBLY,\n" + "       T.PART_STATUS,\n"
				+ "       T.SALE_PRICE,\n" + "       T.ARMY_PRICE,\n"
				+ "       T.PLAN_PRICE,\n" + "       T.RETAIL_PRICE\n"
				+ "  FROM PT_BA_INFO T" + " WHERE " + conditions
				+ " ORDER BY T.PART_ID";
		rs = this.factory.select(sql, pageManager);
		rs.setFieldDic("PART_TYPE", "PJLB"); // 配件类型
		rs.setFieldDic("UNIT", "JLDW"); // 计量单位
		rs.setFieldDic("MIN_UNIT", "JLDW"); // 最小包装单位
		return rs;
	}

}
