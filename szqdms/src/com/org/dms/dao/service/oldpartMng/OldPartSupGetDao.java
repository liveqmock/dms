package com.org.dms.dao.service.oldpartMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuReturnStorageVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 旧件供应商认领DAO
 * @author zts
 *
 */
public class OldPartSupGetDao extends BaseDAO{
 
	public static final OldPartSupGetDao getInstance(ActionContext atx){
		OldPartSupGetDao dao=new OldPartSupGetDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 认领查询
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet oldPartSupGetSearch(PageManager page,String conditions,User user)throws Exception{
		String wheres = conditions;
		wheres += " AND T.SURPLUS_AMOUNT > 0 \n"+
				  " AND EXISTS (SELECT 1\n" +
				  "          FROM PT_BA_SUPPLIER S\n" + 
				  "         WHERE S.SUPPLIER_ID = T.SUPPLIER_ID\n" + 
				  "           AND S.ORG_ID = "+user.getOrgId()+") \n"+
				  " ORDER BY T.PART_CODE ";
		page.setFilter(wheres);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.STORAGE_ID,\n" );
		sql.append("       T.PART_ID,\n" );
		sql.append("       T.PART_CODE,\n" );
		sql.append("       T.PART_NAME,\n" );
		sql.append("       T.SURPLUS_AMOUNT,\n" );
		sql.append("       T.SUM_AMOUNT,\n" );
		sql.append("       T.OUT_AMOUNT\n" );
		sql.append("  FROM SE_BU_RETURN_STORAGE T");
		bs=factory.select(sql.toString(), page);
		return bs;
	}
	
	/**
	 * 更新库存表
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updateStorage(SeBuReturnStorageVO vo)throws Exception{
		return factory.update(vo);
	}
	
	/**
	 * 插入出库记录
	 * @param getAmount 出库数量
	 * @param user     
	 * @return
	 * @throws Exception
	 */
	public boolean insertOut(String getAmount ,User user,String outType,String id,String remarks)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("INSERT INTO SE_BU_RETURN_OUT\n" );
		sql.append("  (OUT_ID,\n" );
		sql.append("   STORAGE_ID,\n" );
		sql.append("   PART_ID,\n" );
		sql.append("   PART_CODE,\n" );
		sql.append("   PART_NAME,\n" );
		sql.append("   SUPPLIER_ID,\n" );
		sql.append("   SUPPLIER_NAME,\n" );
		sql.append("   SUPPLIER_CODE,\n" );
		sql.append("   OUT_TYPE,\n" );
		sql.append("   OUT_AMOUNT,\n" );
		sql.append("   OUT_DATE,\n" );
		sql.append("   COMPANY_ID,\n" );
		sql.append("   ORG_ID,\n" );
		sql.append("   CREATE_USER,\n" );
		sql.append("   CREATE_TIME,\n" );
		sql.append("   STATUS,\n" );
		sql.append("   REMARKS,\n" );
		sql.append("   OEM_COMPANY_ID)\n" );
		sql.append("  SELECT F_GETID(),\n" );
		sql.append("         T.STORAGE_ID,\n" );
		sql.append("         T.PART_ID,\n" );
		sql.append("         T.PART_CODE,\n" );
		sql.append("         T.PART_NAME,\n" );
		sql.append("         T.SUPPLIER_ID,\n" );
		sql.append("         T.SUPPLIER_NAME,\n" );
		sql.append("         T.SUPPLIER_CODE,\n" );
		sql.append("         "+outType+",\n" );
		sql.append("         "+getAmount+",\n" );
		sql.append("         SYSDATE,\n" );
		sql.append("         "+user.getCompanyId()+",\n" );
		sql.append("         "+user.getOrgId()+",\n" );
		sql.append("         '"+user.getAccount()+"',\n" );
		sql.append("         SYSDATE,\n" );
		sql.append("         "+DicConstant.YXBS_01+",\n" );
		sql.append("         '"+remarks+"',\n" );
		sql.append("         "+user.getOemCompanyId()+"\n" );
		sql.append("    FROM SE_BU_RETURN_STORAGE T WHERE T.STORAGE_ID="+id+"");
		return factory.update(sql.toString(), null);
	}
}
