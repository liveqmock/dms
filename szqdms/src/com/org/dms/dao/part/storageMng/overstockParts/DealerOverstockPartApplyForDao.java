package com.org.dms.dao.part.storageMng.overstockParts;

import java.sql.CallableStatement;
import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: 积压件调拨申请Dao
 * date: 2014年9月16日 上午9:24:54
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public class DealerOverstockPartApplyForDao extends BaseDAO{

	public static final DealerOverstockPartApplyForDao getInstance(ActionContext ac){
		DealerOverstockPartApplyForDao dao = new DealerOverstockPartApplyForDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * queryApplyForDealer: 经销商积压件调拨申请单查询
	 * @author fuxiao
	 * Date:2014年9月16日下午2:16:44
	 * @return
	 * @throws SQLException
	 */
	public BaseResultSet queryApplyForDealer(PageManager page, String conditions, User user) throws SQLException{
		StringBuilder sb = new StringBuilder(conditions);
		sb.append(" AND STATUS = '"+DicConstant.YXBS_01+"' AND ORG_ID = '"+user.getOrgId()+"' AND APPLY_STATUS NOT IN ('"+DicConstant.JYJSQZT_02+"','"+DicConstant.JYJSQZT_03+"','"+DicConstant.JYJSQZT_05+"')");
		page.setFilter(sb.toString());
		String strSql =	
				"SELECT\n" +
						"  OVERSTOCK_ID,\n" + 
						"  OVERSTOCK_NO,\n" + 
						"  APPLY_STATUS,\n" + 
						"  APPLY_USER,\n" + 
						"  APPLY_DATE,\n" + 
						"  OUT_ORG_CODE,\n" + 
						"  OUT_ORG_NAME\n" + 
						"FROM PT_BU_OVERSTOCK_PARTS_APPLY";

		BaseResultSet rs = this.factory.select(strSql, page);
		rs.setFieldDic("APPLY_STATUS", "JYJSQZT");
		rs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		return rs;
	}
	
	// 经销商申请单积压件申请单查询
	public BaseResultSet queryApplyForDealerAll(PageManager page, String conditions, User user) throws SQLException{
		StringBuilder sb = new StringBuilder(conditions);
		sb.append(" AND STATUS = '"+DicConstant.YXBS_01+"' AND ORG_ID = '"+user.getOrgId()+"'");
		page.setFilter(sb.toString());
		String strSql =	
				"SELECT\n" +
						"  OVERSTOCK_ID,\n" + 
						"  OVERSTOCK_NO,\n" + 
						"  APPLY_STATUS,\n" + 
						"  APPLY_USER,\n" + 
						"  APPLY_DATE,\n" + 
						"  OUT_ORG_CODE,\n" + 
						"  OUT_ORG_NAME\n" + 
						"FROM PT_BU_OVERSTOCK_PARTS_APPLY";

		BaseResultSet rs = this.factory.select(strSql, page);
		rs.setFieldDic("APPLY_STATUS", "JYJSQZT");
		rs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		return rs;
	}
	
	// OEM积压件申请单查询
	public BaseResultSet queryApplyForAuditAll(PageManager page, String conditions, User user) throws SQLException{
		StringBuilder sb = new StringBuilder(conditions);
		sb.append(" AND STATUS = '"+DicConstant.YXBS_01+"' AND APPLY_STATUS NOT IN ('"+DicConstant.JYJSQZT_01+"')");
		page.setFilter(sb.toString());
		String strSql =	
				"SELECT\n" +
						"  OVERSTOCK_ID,\n" + 
						"  OVERSTOCK_NO,\n" + 
						"  APPLY_STATUS,\n" + 
						"  APPLY_USER,\n" + 
						"  APPLY_DATE,\n" + 
						"  OUT_ORG_CODE,\n" + 
						"  OUT_ORG_NAME\n" + 
						"FROM PT_BU_OVERSTOCK_PARTS_APPLY";

		BaseResultSet rs = this.factory.select(strSql, page);
		rs.setFieldDic("APPLY_STATUS", "JYJSQZT");
		rs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		return rs;
	}
	
	/**
	 * 
	 * queryApplyById:单张申请单查询 
	 * @author fuxiao
	 * Date:2014年9月16日下午3:10:58
	 * @param overstockId
	 * @return
	 * @throws SQLException
	 */
	public QuerySet queryApplyById(String overstockId) throws SQLException{
		return this.factory.select(new Object[]{overstockId}, 
				"SELECT OVERSTOCK_ID,OVERSTOCK_NO,APPLY_REMARK,CONFORM_REMARK,CHECK_REMARK,APPLY_STATUS_CODE,APPLY_STATUS,APPLY_USER,APPLY_DATE,AMOUNT,ORG_CODE,ORG_NAME\n" +
						"FROM (\n" + 
						"SELECT\n" + 
						"    OVERSTOCK_ID,\n" + 
						"    OVERSTOCK_NO,\n" + 
						"    APPLY_REMARK,\n" + 
						"    CONFORM_REMARK,\n" + 
						"    CHECK_REMARK,\n" + 
						"    APPLY_STATUS APPLY_STATUS_CODE,\n" + 
						"    (SELECT DIC.DIC_VALUE FROM DIC_TREE DIC WHERE DIC.ID = A.APPLY_STATUS) APPLY_STATUS,\n" + 
						"    APPLY_USER,\n" + 
						"    TO_CHAR(APPLY_DATE,'YYYY-MM-DD HH24:MI:SS') APPLY_DATE,\n" + 
						"    AMOUNT,\n" + 
						"    O.CODE ORG_CODE,\n" + 
						"    O.ONAME ORG_NAME\n" + 
						"FROM PT_BU_OVERSTOCK_PARTS_APPLY A, TM_ORG O\n" + 
						"WHERE A.ORG_ID = O.ORG_ID\n" + 
						") WHERE OVERSTOCK_ID = ?");
	}
	
	/**
	 * 
	 * queryApplyPartInfo: 根据申请单ID查询申请单中配件详情
	 * @author fuxiao
	 * Date:2014年9月16日下午3:15:17
	 * @param page
	 * @param conditions
	 * @param overstockId
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public BaseResultSet queryApplyPartInfo(PageManager page,String conditions,String overstockId, User user) throws SQLException{
		StringBuilder sb = new StringBuilder(conditions);
		sb.append(" AND OVERSTOCK_ID = "+overstockId);
		page.setFilter(sb.toString());
		String strSql = 
				"SELECT STOCK_ID,OVERSTOCK_ID,PART_CODE,PART_NAME,APPLY_COUNTS,UNIT,PART_TYPE,SALE_PRICE,ORG_NAME,ORG_CODE,AVAILABLE_AMOUNT\n" +
						"FROM (\n" + 
								"SELECT\n" +
								"  S.STOCK_ID,\n" + 
								"  D.OVERSTOCK_ID,\n" + 
								"  D.PART_CODE,\n" + 
								"  D.PART_NAME,\n" + 
								"  D.APPLY_COUNTS,\n" + 
								"  I.UNIT,\n" + 
								"  I.PART_TYPE,\n" + 
								"  I.SALE_PRICE,\n" + 
								"  O.ONAME ORG_NAME,\n" + 
								"  O.CODE ORG_CODE,\n" + 
								"  NVL(S.AVAILABLE_AMOUNT,0) AVAILABLE_AMOUNT\n" + 
								"FROM PT_BU_OVERSTOCK_PARTS_DTL D ,PT_BA_INFO I, PT_BU_DEALER_STOCK S, PT_BU_OVERSTOCK_PARTS_APPLY A, TM_ORG O\n" + 
								"WHERE D.PART_ID = I.PART_ID\n" + 
								"      AND A.OUT_ORG_ID = S.ORG_ID\n" + 
								"      AND D.OVERSTOCK_ID = A.OVERSTOCK_ID\n" + 
								"      AND D.PART_ID = S.PART_ID\n" + 
								"      AND O.ORG_ID = A.OUT_ORG_ID"+
						")";
		BaseResultSet rs =  this.factory.select(strSql, page);
		// 计量单位
		rs.setFieldDic("UNIT", "JLDW");
		// 配件类型
		rs.setFieldDic("PART_TYPE", "PJLB");
		return rs;
	}
	
	/**
	 * 
	 * getOverstockNO:根据ORGCODE获取一个一个积压件申请单单号
	 * 单号规则：NO + ORG_CODE + YYYYMMDD + 三位数字（数字表示生成申请单号的当天，该站点新建申请单的个数,数字不足3为，在左边补0）
	 * @author fuxiao
	 * Date:2014年9月18日下午9:58:59
	 * @param user
	 * @throws SQLException
	 */
	public String getOverstockNO(User user) throws SQLException{
		return this.factory.select("SELECT F_GET_OVERSTOCK_APPLY_NO('" + user.getOrgCode() + "') FROM DUAL")[0][0];
	}
	
	/**
	 * 
	 * addOverstockApply: 添加一条积压件申请单
	 * @author fuxiao
	 * Date:2014年9月18日下午10:11:22
	 * @param args
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public boolean addOverstockApply(Object[] args) throws SQLException{
		String sql =
				"INSERT INTO PT_BU_OVERSTOCK_PARTS_APPLY\n" +
						"(OVERSTOCK_ID, OVERSTOCK_NO, APPLY_STATUS, APPLY_USER,\n" + 
						" APPLY_DATE,   AMOUNT,       ORG_ID,       ORG_CODE,   ORG_NAME,\n" + 
						" OUT_ORG_ID,   OUT_ORG_CODE, OUT_ORG_NAME,\n" + 
						" STATUS, CREATE_USER, CREATE_TIME)\n" + 
						"SELECT\n" + 
						" F_GETID(),?,?,?,\n" + 
						" SYSDATE,0,?,?,(SELECT TM.ONAME FROM TM_ORG TM WHERE TM.ORG_ID = ?),\n" + 
						" S.ORG_ID,O.CODE,O.ONAME,\n" + 
						" ?,?,SYSDATE\n" + 
						"FROM PT_BU_DEALER_STOCK S,TM_ORG O\n" + 
						"WHERE S.ORG_ID = O.ORG_ID AND STOCK_ID = ?";
		
		return this.factory.update(sql, args);
	}
	
	/**
	 * 
	 * addOverstockPartInfo: 添加积压件申请表的配件详情信息
	 * @author fuxiao
	 * Date:2014年9月18日下午10:45:23
	 * @param args
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public boolean addOverstockPartInfo(Object[] args) throws SQLException{
		String sql = 
					"INSERT INTO PT_BU_OVERSTOCK_PARTS_DTL\n" +
					"  (OVERSTOCK_DTL_ID, OVERSTOCK_ID, PART_ID,     PART_CODE,\n" + 
					"   PART_NAME,        APPLY_COUNTS, CREATE_USER, CREATE_TIME)\n" + 
					"SELECT\n" + 
					"   F_GETID(),   PA.OVERSTOCK_ID, S.PART_ID, I.PART_CODE,\n" + 
					"   I.PART_NAME, ?,?,SYSDATE\n" + 
					"FROM PT_BU_DEALER_STOCK S, PT_BA_INFO I,PT_BU_OVERSTOCK_PARTS_APPLY PA\n" + 
					"WHERE S.PART_ID = I.PART_ID\n" + 
					"      AND PA.OUT_ORG_ID = S.ORG_ID\n" + 
					"      AND PA.OVERSTOCK_NO = ?\n" + 
					"      AND S.STOCK_ID = ?";
		return this.factory.update(sql, args);
	}
	
	/**
	 * 
	 * deleteOverstockPartInfoByOverstockId:根据主表ID删除子表所有信息
	 * 用于修改动作
	 * @author fuxiao
	 * Date:2014年9月20日下午4:21:36
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteOverstockPartInfoByOverstockId(String id) throws SQLException{
		return this.factory.delete("DELETE PT_BU_OVERSTOCK_PARTS_DTL D WHERE D.OVERSTOCK_ID = ?", new Object[]{id});
	}
	
	/**
	 * 
	 * updateApplyAmountByNo: 根据申请单NO更新主表中的申请单总金额
	 * @author fuxiao
	 * Date:2014年9月19日上午12:45:44
	 * @param overstockNo
	 * @return
	 * @throws SQLException
	 */
	public boolean updateApplyAmountByNo(String overstockNo) throws SQLException{
		String sql = 
				"UPDATE PT_BU_OVERSTOCK_PARTS_APPLY T\n" +
						"SET T.AMOUNT = (\n" + 
						"       SELECT\n" + 
						"             SUM(DT.APPLY_COUNTS * I.SALE_PRICE)\n" + 
						"       FROM PT_BU_OVERSTOCK_PARTS_DTL DT,PT_BA_INFO I\n" + 
						"       WHERE DT.PART_ID = I.PART_ID\n" + 
						"             AND DT.OVERSTOCK_ID = T.OVERSTOCK_ID\n" + 
						") WHERE T.OVERSTOCK_NO = ?";
		return this.factory.update(sql, new Object[]{overstockNo});
	}
	
	/**
	 * 
	 * updateApplyStatusById:根据主键ID更新申请单状态
	 * @author fuxiao
	 * Date:2014年9月19日上午9:45:01
	 * @param args
	 * @return
	 * @throws SQLException
	 */
	public boolean updateApplyStatusById(Object[] args, String applyRemark, String conformRemark, String checkRemark) throws SQLException{
		StringBuffer sql = new StringBuffer(
						"UPDATE PT_BU_OVERSTOCK_PARTS_APPLY T\n" +
						"SET T.APPLY_STATUS = ?,\n" + 
						"    T.UPDATE_USER = ?,\n" ); 
		if(applyRemark != null){
			sql.append("    T.APPLY_REMARK = '" + applyRemark + "',\n");
		}
		
		if(conformRemark != null){
			sql.append("    T.CONFORM_REMARK = '" + conformRemark + "',\n");
		}
		
		if(checkRemark != null){
			sql.append("    T.CHECK_REMARK = '" + checkRemark + "',\n");
		}
		
		sql.append("    T.UPDATE_TIME = SYSDATE\n" + 
				   " WHERE T.OVERSTOCK_ID = ?");
		return this.factory.update(sql.toString(), args);
	}
	
	/**
	 * 
	 * queryApplyForCommit:根据积压件状态查询
	 * @author fuxiao
	 * Date:2014年9月19日上午10:34:18
	 * @param page
	 * @param condition
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public BaseResultSet queryApplyForStatus(PageManager page,String condition,User user) throws SQLException{
		StringBuilder sb = new StringBuilder(condition);
		sb.append(" AND A.STATUS = '"+DicConstant.YXBS_01+"'");
		if(user != null){
			sb.append(" AND OUT_ORG_ID = '"+user.getOrgId()+"'");
		}
		String strSql = 
					"SELECT OVERSTOCK_ID,\n" +
					"       OVERSTOCK_NO,\n" + 
					"       APPLY_STATUS,\n" + 
					"       APPLY_USER,\n" + 
					"       APPLY_DATE,\n" + 
					"       AMOUNT,\n" + 
					"       A.ORG_CODE,\n" + 
					"       A.ORG_NAME,\n" + 
					"       A.OUT_ORG_CODE,\n" + 
					"       A.OUT_ORG_NAME,\n" + 
					"       A.STATUS,\n" + 
					"       OUT_ORG_ID\n" + 
					"  FROM PT_BU_OVERSTOCK_PARTS_APPLY A, TM_ORG O\n" + 
					" WHERE A.ORG_ID = O.ORG_ID AND " + sb.toString();
		BaseResultSet rs = this.factory.select(strSql, page);
		rs.setFieldDic("APPLY_STATUS", "JYJSQZT");
		rs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		return rs;
	}
	
	/**
	 * 
	 * queryApplyForOrgType: 根据申请单ID，查询请求方，出货方的OrgType
	 * 返回两列，第一列：申请方:orgType 第二列：出货方：OutOrgType
	 * @author fuxiao
	 * Date:2014年9月22日下午5:45:08
	 * @param overstockId
	 * @return
	 * @throws SQLException
	 
	 */
	public QuerySet queryApplyForOrgType(String overstockId) throws SQLException{
		String sqlStr = "SELECT\n" +
						"       (SELECT O.ORG_TYPE FROM TM_ORG O WHERE O.ORG_ID = A.ORG_ID) ORG_TYPE,\n" + 
						"       (SELECT O.ORG_TYPE FROM TM_ORG O WHERE O.ORG_ID = A.OUT_ORG_ID) OUT_ORG_TYPE\n" + 
						"FROM PT_BU_OVERSTOCK_PARTS_APPLY A WHERE A.OVERSTOCK_ID = ?";
		return this.factory.select(new Object[]{overstockId}, sqlStr);
	}
	
	/**
	 * 
	 * chagePageCount:调用存储过程，根据积压件状态修改库存数量
	 * TODO(这里描述这个方法适用条件 – 可选)
	 * @author fuxiao
	 * Date:2014年9月24日下午7:31:20
	 * @throws SQLException 
	 */
	public void chagePageCount(String overstockId, String userName) throws SQLException{
		 CallableStatement proc = null;
	        try {
				proc = factory.getConnection().prepareCall("{call P_CHAGE_PART_COUNT_BY_STATUS(?,?)}");
				proc.setString(1, overstockId);
				proc.setString(2, userName);
				proc.execute();
			} catch (SQLException e) {
				throw e;
			}
	}
}
