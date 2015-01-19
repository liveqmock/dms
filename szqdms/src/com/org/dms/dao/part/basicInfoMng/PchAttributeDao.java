package com.org.dms.dao.part.basicInfoMng;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;
import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBaPchAttributeVO;

public class PchAttributeDao extends BaseDAO {
	// 定义instance
	public static final PchAttributeDao getInstance(ActionContext atx) {
		PchAttributeDao dao = new PchAttributeDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 采购员属性批量新增
	 * 
	 * @throws Exception
	 * @Author suoxiuli 2014-07-22
	 */
	public boolean batchInsertPchAttribute(String partIds,
			String userAccount, String userName, String createUser, String status)
			throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into pt_ba_pch_attribute(pchattribute_id, part_id, part_code, \n");
		sql.append(" part_name, user_account, user_name, create_user, create_time, status) \n");
		sql.append(" select f_getid(),i.part_id,i.part_code,i.part_name, \n");
		sql.append(" '" + userAccount + "', '" + userName + "', '" + createUser + "', sysdate, '" + status + "' \n");
		sql.append(" from pt_ba_info i \n");
		sql.append(" where i.part_id in (" + partIds + ") \n");
		
		
		return factory.update(sql.toString(), null);
	}

	/**
	 * 采购员属性单个修改
	 * 
	 * @throws Exception
	 * @Author suoxiuli 2014-07-22
	 */
	public boolean updatePchAttribute(PtBaPchAttributeVO vo)
			throws Exception {
		return factory.update(vo);
		//return factory.insert(vo);
	}

	/**
	 * 采购员属性批量修改
	 * 
	 * @throws Exception
	 * @Author suoxiuli 2014-07-22
	 */
	public boolean batchUpdatePchAttribute(String pchAttributeIds,
			String userAccount, String userName, String updateUser)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" update pt_ba_pch_attribute \n");
		sql.append(" set user_account = '" + userAccount + "', \n");
		sql.append(" user_name = '" + userName + "', \n");
		sql.append(" update_user = '" + updateUser + "', \n");
		sql.append(" update_time = sysdate  \n");
		sql.append(" where pchattribute_id in (" + pchAttributeIds + ") \n");
		return factory.update(sql.toString(), null);
	}

	/**
	 * 查询所有有效采购员属性
	 * 
	 * @throws Exception
	 * @Author suoxiuli 2014-07-22
	 */
	public BaseResultSet search(PageManager page, String conditions)
			throws Exception {
		String wheres = conditions;
		//wheres += " and status="+DicConstant.YXBS_01+"  ";
		wheres += " order by create_time, part_id desc ";
		page.setFilter(wheres);

		BaseResultSet bs = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select pchattribute_id,user_account,user_name,part_id,");
		sql.append(" part_name,part_code,create_user,create_time,status ");
		sql.append(" from pt_ba_pch_attribute ");

		bs = factory.select(sql.toString(), page);
		bs.setFieldDic("STATUS", "YXBS");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
		return bs;
	}
	
	/**
	 * 批量搜索没有采购员的配件
	 * 
	 * @throws Exception
	 * @Author suoxiuli 2014-07-22
	 */
	public BaseResultSet searchNewPart(PageManager page, String conditions, String status, String partStatus) throws Exception {
		String wheres = conditions;
		wheres += " and t.status="+status;
		wheres += " and t.part_status="+partStatus;
		wheres += " and  not exists (select 1 from pt_ba_pch_attribute k where k.part_id=t.part_id and k.status='100201') ";
		wheres += " order by t.create_time desc ";
		page.setFilter(wheres);

		BaseResultSet bs = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select part_id,part_code,part_name,create_user,create_time ");
		sql.append(" from pt_ba_info t ");

		bs = factory.select(sql.toString(), page);
		bs.setFieldDic("STATUS", "YXBS");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
		return bs;
	}

	/**
	 * 更新采购员属性的有效状态
	 * 
	 * @throws Exception
	 * @Author suoxiuli 2014-07-22
	 */
	public boolean updatePchAttributeStatus(String pchAttributeIds, String updateUser, String status)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" update pt_ba_pch_attribute \n");
		sql.append(" set status = '" + status + "', \n");
		sql.append(" update_user = '" + updateUser + "', \n");
		sql.append(" update_time = sysdate \n");
		sql.append(" where pchattribute_id in (" + pchAttributeIds + ") \n");
		return factory.update(sql.toString(), null);
	}
	public QuerySet checkUnique(String partIds,String userAccout)
            throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT to_char(wm_concat( T1.PART_CODE)) CODES FROM PT_BA_PCH_ATTRIBUTE T,PT_BA_INFO T1\n" );
		sql.append("WHERE T.PART_ID = T1.PART_ID\n" );
		sql.append("AND T.PART_ID IN ("+partIds+")\n" );
		sql.append("AND T.USER_ACCOUNT = '"+userAccout+"'");

        return factory.select(null, sql.toString());
    }
}
