package com.org.dms.dao.service.oldpartMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuReturnStorageVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * 旧件出库DAO
 * @author zts
 *
 */
public class OldPartOutDao extends BaseDAO{

	 //定义instance
	public  static final OldPartOutDao getInstance(ActionContext atx)
	{
		OldPartOutDao dao = new OldPartOutDao();
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
  		wheres += " AND T.SURPLUS_AMOUNT > 0 \n"+
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
		sql.append("       T.SURPLUS_AMOUNT,\n" );
		sql.append("       T.SUM_AMOUNT,\n" );
		sql.append("       T.OUT_AMOUNT,\n" );
		sql.append("	   T.WAREHOUSE_ID,\n" );
		sql.append("       T.WAREHOUSE_CODE,\n" );
		sql.append("       T.WAREHOUSE_NAME");
		sql.append("  FROM SE_BU_RETURN_STORAGE T");
		bs=factory.select(sql.toString(), page);
		return bs;
  	}
  	/**
  	 * 旧件库存更新
  	 * @param vo
  	 * @return
  	 * @throws Exception
  	 */
  	public boolean updateStorage(SeBuReturnStorageVO vo)throws Exception{
  		return factory.update(vo);
  	}
    /**
     * 旧件出库
     * @param getAmount  出库数量
     * @param user  用户
     * @param outType 出库类型
     * @param id 库存ID
     * @param remarks 备注
     * @return
     * @throws Exception
     */
  	public boolean insertOut(String getAmount,User user,String outType,String id,String remarks)throws Exception{
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
  //查询经销商对应某一月的所有索赔单
  	public QuerySet checkStatus(String supplierCode,String partCode) throws Exception{
  		StringBuffer sql= new StringBuffer();
  		sql.append("SELECT 1\n" );
  		sql.append("  FROM PT_BU_SUP_PART_PHOTO T\n" );
  		sql.append(" WHERE T.SUPPLIER_CODE ='"+supplierCode+"'\n" );
  		sql.append("   AND T.PART_CODE ='"+partCode+"'\n" );
  		sql.append("   AND T.APPLY_STATUS ='"+DicConstant.GYSPJZPSC_03+"'");
  		return factory.select(null, sql.toString());
  	}
}
