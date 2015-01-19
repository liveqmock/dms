package com.org.dms.dao.part.basicInfoMng;

import java.sql.Date;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBaWarehouseKeeperVO;
import com.org.frameImpl.Constant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class WarehouseKeeperDao extends BaseDAO {
	// 定义instance
	public static final WarehouseKeeperDao getInstance(ActionContext atx) {
		WarehouseKeeperDao dao = new WarehouseKeeperDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 库管员属性批量新增
	 * 
	 * @throws Exception
	 * @Author suoxiuli 2014-07-15
	 */
	public boolean batchInsertWarehouseKeeper(String partIds,
			String userAccount, String userName, String createUser,
			String status, String warehouseId, String warehouseCode,
			String warehouseName)
			throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO PT_BA_WAREHOUSE_KEEPER(KEEPER_ID, PART_ID, PART_CODE, \n");
		sql.append(" PART_NAME, USER_ACCOUNT, USER_NAME, CREATE_USER, CREATE_TIME, \n");
		sql.append(" STATUS, WAREHOUSE_ID, WAREHOUSE_CODE, WAREHOUSE_NAME) \n");
		sql.append(" SELECT F_GETID(), I.PART_ID, I.PART_CODE, I.PART_NAME, \n");
		sql.append(" '" + userAccount + "', '" + userName + "', '" + createUser + "', sysdate, '" + status + "', \n");
		sql.append(" '" + warehouseId + "', '" + warehouseCode + "', '" + warehouseName + "' \n");
		sql.append(" FROM PT_BA_INFO I \n");
		sql.append(" WHERE I.PART_ID IN (" + partIds + ") \n");
		
		
		return factory.update(sql.toString(), null);
	}

	/**
	 * 库管员属性单个修改
	 * 
	 * @throws Exception
	 * @Author suoxiuli 2014-07-15
	 */
	public boolean updateWarehouseKeeper(PtBaWarehouseKeeperVO vo)
			throws Exception {
		return factory.update(vo);
	}
	public boolean insert(PtBaWarehouseKeeperVO vo)
			throws Exception {
		return factory.insert(vo);
	}
	/**
	 * 库管员属性批量修改
	 * 
	 * @throws Exception
	 * @Author suoxiuli 2014-07-15
	 */
	public boolean batchUpdateWarehouseKeeper(String keeperIds,
			String userAccount, String userName, String updateUser)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" update pt_ba_warehouse_keeper \n");
		sql.append(" set USER_ACCOUNT = '" + userAccount + "', \n");
		sql.append(" USER_NAME = '" + userName + "', \n");
		sql.append(" UPDATE_USER = '" + updateUser + "', \n");
		sql.append(" UPDATE_TIME = sysdate  \n");
		sql.append(" where keeper_id in (" + keeperIds + ") \n");
		return factory.update(sql.toString(), null);
	}

	/**
	 * 库管员属性查询
	 * 
	 * @throws Exception
	 * @Author suoxiuli 2014-07-15
	 */
	public BaseResultSet search(PageManager page, String conditions)
			throws Exception {
		String wheres = conditions;
		wheres += " order by create_time, part_id desc ";
		page.setFilter(wheres);

		BaseResultSet bs = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT KEEPER_ID, USER_ACCOUNT, USER_NAME, PART_ID, PART_NAME, PART_CODE,\n");
		sql.append(" CREATE_USER, CREATE_TIME, STATUS, WAREHOUSE_ID, WAREHOUSE_CODE, WAREHOUSE_NAME \n");
		sql.append(" FROM PT_BA_WAREHOUSE_KEEPER \n");

		bs = factory.select(sql.toString(), page);
		bs.setFieldDic("STATUS", "YXBS");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
		return bs;
	}
	
	/**
	 * 批量搜索没有库管员的配件
	 * 
	 * @throws Exception
	 * @Author suoxiuli 2014-07-15
	 */
	public BaseResultSet searchNewPart(PageManager page, String conditions, String status) throws Exception {
		String wheres = conditions;
//		wheres += " AND T.STATUS="+status;
		wheres += " AND t.part_status in ("+DicConstant.PJZT_01+","+DicConstant.PJZT_03+")";
//		wheres += " AND  NOT EXISTS";
//		wheres += " (SELECT 1 FROM PT_BA_WAREHOUSE_KEEPER K WHERE K.PART_ID=T.PART_ID AND K.STATUS='100201') ";
		wheres += " ORDER BY T.CREATE_TIME DESC";
		page.setFilter(wheres);

		BaseResultSet bs = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT PART_ID,PART_CODE,PART_NAME,CREATE_USER,CREATE_TIME ");
		sql.append(" FROM PT_BA_INFO T ");

		bs = factory.select(sql.toString(), page);
		bs.setFieldDic("STATUS", "YXBS");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
		return bs;
	}

	/**
	 * 更新库管员属性的有效状态
	 * 
	 * @throws Exception
	 * @Author suoxiuli 2014-07-15
	 */
	public boolean updateWarehouseKeeperStatus(String keeperIds, String updateUser, String status)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" update pt_ba_warehouse_keeper \n");
		sql.append(" set status = '" + status + "', \n");
		sql.append(" update_user = '" + updateUser + "', \n");
		sql.append(" update_time = sysdate \n");
		sql.append(" where keeper_id in (" + keeperIds + ") \n");
		return factory.update(sql.toString(), null);
	}
	
	/**
	 * 批量所有的配件代码
	 * 
	 * @throws Exception
	 * @Author suoxiuli 2014-08-30
	 */
	public BaseResultSet searchAllPartCode(PageManager page, String conditions, String status) throws Exception {
		String wheres = conditions;
		wheres += " AND STATUS="+status+" ";
		wheres += " ORDER BY CREATE_TIME,PART_ID DESC";
		page.setFilter(wheres);

		BaseResultSet bs = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT PART_ID,PART_CODE,PART_NAME,CREATE_USER,CREATE_TIME ");
		sql.append(" FROM PT_BA_INFO ");

		bs = factory.select(sql.toString(), page);
		bs.setFieldDic("STATUS", "YXBS");
		bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
		return bs;
	}
	public QuerySet checkUnique(String warehouseId,String partIds) throws Exception {

//        StringBuffer sql = new StringBuffer();
//        sql.append("SELECT wm_concat(PART_CODE),POSITION_CODE FROM PT_BA_WAREHOUSE_PART_RL WHERE WAREHOUSE_ID = "+warehouseId+" AND PART_ID IN ("+partIds+") GROUP BY POSITION_CODE");
//        //执行方法，不需要传递conn参数
		
//		StringBuffer sql= new StringBuffer();
//		sql.append("SELECT wm_concat(DISTINCT T.PART_CODE) PART_CODE,T.POSITION_CODE\n" );
//		sql.append("FROM PT_BA_WAREHOUSE_PART_RL T,PT_BA_WAREHOUSE_POSITION T1,PT_BA_WAREHOUSE_AREA T2\n" );
//		sql.append("WHERE T.POSITION_ID = T1.POSITION_ID\n" );
//		sql.append("AND T2.WAREHOUSE_ID = "+warehouseId+"\n" );
//		sql.append("AND T.PART_ID IN ("+partIds+")\n" );
//		sql.append("AND T1.POSITION_ID = T1.POSITION_ID\n" );
//		sql.append("GROUP BY T.POSITION_CODE");
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT wm_concat(PART_CODE) PART_CODES, WAREHOUSE_NAME\n" );
		sql.append("  FROM PT_BA_WAREHOUSE_KEEPER\n" );
		sql.append(" WHERE WAREHOUSE_ID = "+warehouseId+"\n" );
		sql.append("   AND PART_ID IN ("+partIds+")\n" );
		sql.append(" GROUP BY WAREHOUSE_NAME");

        return factory.select(null,sql.toString());
    }
	public QuerySet keeperDownload(String conditions) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.PART_CODE,\n" );
		sql.append("       T.PART_NAME,\n" );
		sql.append("       T.WAREHOUSE_CODE,\n" );
		sql.append("       T.WAREHOUSE_NAME,\n" );
		sql.append("       T.USER_ACCOUNT,\n" );
		sql.append("       T.USER_NAME\n" );
		sql.append("  FROM PT_BA_WAREHOUSE_KEEPER T WHERE "+conditions+" AND T.STATUS = "+DicConstant.YXBS_01+"\n" );
		sql.append(" ORDER BY T.USER_ACCOUNT, T.PART_CODE");
    	return factory.select(null, sql.toString());
    }
	public BaseResultSet searchImport(PageManager page, User user,String conditions) throws Exception {
    	String wheres = conditions + " AND USER_ACCOUNT='"+user.getAccount()+"' \n";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT A.* \n");
        sql.append("  FROM PT_BA_WAREHOUSE_KEEPER_TMP A");
        bs = factory.select(sql.toString(), page);
        return bs;
    }
	public QuerySet expData(String pConditions,User user) throws Exception {

    	String wheres = " WHERE TMP_NO IN ("+ pConditions + ") AND USER_ACCOUNT='"+user.getAccount()+"'\n";
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT *\n" );
    	sql.append("  FROM PT_BA_WAREHOUSE_KEEPER_TMP");
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }
	
	public QuerySet getTmp(String tmpNo,User user) throws Exception {

		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T1.PART_ID,\n" );
		sql.append("       T1.PART_CODE,\n" );
		sql.append("       T1.PART_NAME,\n" );
		sql.append("       T2.WAREHOUSE_ID,\n" );
		sql.append("       T.WAREHOUSE_CODE,\n" );
		sql.append("       T.WAREHOUSE_NAME,\n" );
		sql.append("       T.KEEP_MAN_ACOUNT,\n" );
		sql.append("       T.KEEP_MAN_NAME\n" );
		sql.append("  FROM PT_BA_WAREHOUSE_KEEPER_TMP T, PT_BA_INFO T1, PT_BA_WAREHOUSE T2\n" );
		sql.append(" WHERE T.PART_CODE = T1.PART_CODE\n" );
		sql.append("   AND T.WAREHOUSE_CODE = T2.WAREHOUSE_CODE\n");
		if(!"".equals(tmpNo)&&tmpNo!=null){
			sql.append("   AND T.TMP_NO NOT IN ("+tmpNo+") ");
		}
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString());
    }
	public QuerySet checkIn(String PART_ID,String WAREHOUSE_ID) throws Exception {

		StringBuffer sql= new StringBuffer();
		sql.append("SELECT KEEPER_ID FROM PT_BA_WAREHOUSE_KEEPER WHERE PART_ID = "+PART_ID+" AND WAREHOUSE_ID = "+WAREHOUSE_ID+"");
        return factory.select(null, sql.toString());
    }
}
