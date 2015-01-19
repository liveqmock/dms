package com.org.dms.dao.service.oldpartMng;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuReturnStorageVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: OldPartCheckStoragePJDao
 * Function: 配件管理-旧件终审Dao层
 * date: 2014年9月9日 上午10:00:28 
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6 
 * 
 */
public class OldPartCheckStoragePJDao extends BaseDAO{
	
	/**
	 * 
	 * getInstance:获取Dao对象实例. <br/> 
	 * @author fuxiao 
	 * @param atx
	 * @return 
	 * @since JDK 1.6 
	 *
	 */
	public  static final OldPartCheckStoragePJDao getInstance(ActionContext atx)
	{
		OldPartCheckStoragePJDao dao = new OldPartCheckStoragePJDao();
	    atx.setDBFactory(dao.factory);
	    return dao;
	}
	
	/**
	 * 
	 * getCheckStoragePartList: 查询需要终审的数据集合
	 * @author fuxiao
	 * @param pageManager
	 * @param conditions
	 * @param user
	 * @return
	 * @throws SQLException
	 * @since JDK 1.6
	 */
	public BaseResultSet getCheckStoragePartList(PageManager page, String conditions, User user) throws SQLException{
		StringBuffer sb  = new StringBuffer();
		
		//  拼接查询条件
		sb.append(conditions);
		sb.append("  AND T.BOX_NO IS NOT NULL AND T.RETURN_DATE IS NOT NULL AND T.IN_STOCK_DATE IS NULL");
		sb.append("  AND T.STATUS = '" + DicConstant.YXBS_01 +"'" + 
				  "  AND T.APPLY_STATUS = '"+DicConstant.PJSBSPZT_05+"'");
		sb.append("  ORDER BY T.APPLY_NO DESC ");
		page.setFilter(sb.toString());
		
		// 生成查询SQL
		sb.delete(0, sb.length());
		sb.append("SELECT APPLY_ID,\n"+ 
				"		  BOX_NO,\n"+
				"         RETURN_DATE,\n"+
		        "		  APPLY_NO,\n" +
				"         PART_CODE,\n" + 
				"         PART_NAME,\n" + 
				"         CLAIM_COUNT,\n" + 
				"         ORG_CODE,\n" + 
				"         ORG_NAME,\n" + 
				"         SUPPLIER_CODE,\n" + 
				"         SUPPLIER_NAME,\n" + 
				"         FAULT_CONDITONS,\n" + 
				"         IN_COUNT,\n" + 
				"         ADUIT_REMARKS\n" + 
				"         FROM PT_BU_CLAIM_APPLY T\n");
		
		return this.factory.select(sb.toString(), page);
	}
	
	
	/**
	 * 
	 * getPartInfoByApplyId:(这里用一句话描述这个方法的作用). 
	 * @author fuxiao
	 * @param applyId
	 * @return
	 * @throws SQLException
	 * @since JDK 1.6
	 */
	public QuerySet getPartInfoByApplyId(String applyId) throws SQLException{
		return this.factory.select(new Object[]{ applyId }, "SELECT APPLY_ID,\n"+ 
															"		  BOX_NO,\n"+
															"         RETURN_DATE,\n"+
													        "		  APPLY_NO,\n" +
															"         PART_CODE,\n" + 
															"         PART_NAME,\n" + 
															"         CLAIM_COUNT,\n" + 
															"         ORG_CODE,\n" + 
															"         ORG_NAME,\n" + 
															"         SUPPLIER_CODE,\n" + 
															"         SUPPLIER_NAME,\n" + 
															"         FAULT_CONDITONS,\n" + 
															"         IN_COUNT,\n" + 
															"         ADUIT_REMARKS\n" + 
															"         FROM PT_BU_CLAIM_APPLY T WHERE T.APPLY_ID = ?");
	}
	
	
	/**
	 * 
	 * saveInCountByApplyId: 根据主键ApplyID更新入库数量及入库备注
	 * @author fuxiao
	 * @param applyId
	 * @param inCount
	 * @param aduitRemarks
	 * @param user
	 * @return
	 * @throws SQLException
	 * @since JDK 1.6
	 */
	public boolean saveInCountByApplyId(String applyId, int inCount, String aduitRemarks, User user) throws SQLException{
		return this.factory.update("UPDATE PT_BU_CLAIM_APPLY T SET T.IN_COUNT = ?,T.IN_STOCK_DATE = SYSDATE, T.ADUIT_REMARKS = ?, T.UPDATE_USER = ?, T.UPDATE_TIME = SYSDATE WHERE T.APPLY_ID = ?", new Object[]{
				inCount,aduitRemarks,user.getAccount(),applyId
		});
	}
	
	/**
	 * 
	 * insertReturnStorage: 新增一条旧件库存记录
	 * @author fuxiao
	 * @param vo
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 
	 */
	public boolean insertReturnStorage(SeBuReturnStorageVO vo, User user) throws Exception{
		StringBuffer sb = new StringBuffer("");
		sb.append("INSERT INTO SE_BU_RETURN_STORAGE"
				+ " (STORAGE_ID, PART_CODE, PART_NAME, SUPPLIER_NAME, SUPPLIER_CODE, SUM_AMOUNT, CREATE_USER, CREATE_TIME, STATUS, OEM_COMPANY_ID, WAREHOUSE_ID, WAREHOUSE_CODE, WAREHOUSE_NAME)"
				+ "  SELECT F_GETID(),?,?,?,?,?,?,SYSDATE,?,?,SS.AREA_ID, SS.AREA_CODE, SS.AREA_NAME FROM SE_BA_WAREHOUSE_SUPPLIER  SS WHERE SS.SUPPLIER_CODE = ? ");
		return this.factory.update(sb.toString(),new Object[]{
			vo.getPartCode(),vo.getPartName(),vo.getSupplierName(),vo.getSupplierCode(),vo.getSumAmount(),user.getAccount(),DicConstant.YXBS_01,user.getOemCompanyId(),vo.getSupplierCode()
		});
	}
	
	/**
	 * 
	 * updateReturnStorageStatus: 根据主键ApplyId更新状态
	 * @author fuxiao
	 * @return
	 * @throws Exception
	 * @since JDK 1.6
	 */
	public boolean updateReturnStorageStatusByApplyId(String applyId,String status, User user) throws Exception{
		return this.factory.update("UPDATE PT_BU_CLAIM_APPLY T SET T.STATUS = ?, T.UPDATE_USER = ?, T.UPDATE_TIME = SYSDATE WHERE T.APPLY_ID = ?", 
				new Object[]{status, user.getAccount(), applyId});
	}
	

}
