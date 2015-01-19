package com.org.dms.dao.service.oldpartMng;

import java.util.Map;
import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuClaimFaultPartVO;
import com.org.dms.vo.service.SeBuReturnorderDetailVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * 索赔单修改DAO
 * @author zts
 *
 */
public class ClaimUpdateDao extends BaseDAO{

	 //定义instance
	public  static final ClaimUpdateDao getInstance(ActionContext atx)
	{
		ClaimUpdateDao dao = new ClaimUpdateDao();	
		atx.setDBFactory(dao.factory);
		return dao;
	}
	/**
	 * 索赔单修改查询
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 */
	/*public BaseResultSet claimUpdateSearch(PageManager page,String conditions,User user)throws Exception{
		String wheres = conditions;
			   wheres+=" AND  C.VEHICLE_ID = V.VEHICLE_ID \n"+
					   " AND C.CLAIM_STATUS = "+DicConstant.SPDZT_05+" \n"+
					   " AND C.OEM_COMPANY_ID ="+user.getOemCompanyId()+" \n"+
					   " order by C.CLAIM_NO \n";
		page.setFilter(wheres);
		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT C.CLAIM_ID,\n" );
		sql.append("       C.ORG_ID ORG_CODE,\n" );
		sql.append("       C.ORG_ID ORG_NAME,\n" );
		sql.append("       C.VEHICLE_ID,\n" );
		sql.append("       C.VIN,\n" );
		sql.append("       C.CLAIM_NO,\n" );
		sql.append("       C.CLAIM_TYPE,\n" );
		sql.append("       C.APPLY_DATE,\n" );
		sql.append("       C.REMARKS,\n" );
		sql.append("       V.ENGINE_NO,\n" );
		sql.append("       V.MODELS_CODE,\n" );
		sql.append("       V.CERTIFICATE,\n" );
		sql.append("       V.ENGINE_TYPE,\n" );
		sql.append("       V.USER_TYPE,\n" );
		sql.append("       V.VEHICLE_USE,\n" );
		sql.append("       V.DRIVE_FORM\n" );
		sql.append("  FROM SE_BU_CLAIM C, MAIN_VEHICLE V\n" );
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_TYPE", "SPDLX");
		bs.setFieldDic("USER_TYPE", "CLYHLX");
		bs.setFieldDic("VEHICLE_USE", "CLYT");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
		bs.setFieldOrgDeptCode("ORG_CODE");
		return bs;
	}*/
	
	/** 
	 * 旧件查询
	 * @param page
	 * @param claimId
	 * @return
	 * @throws Exception
	 */
	/*public BaseResultSet oldPartSearch(PageManager page,String claimId) throws Exception{
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT D.DETAIL_ID,\n" );
		sql.append("       D.PART_ID,\n" );
		sql.append("       D.PART_CODE,\n" );
		sql.append("       D.PART_NAME,\n" );
		sql.append("       D.OUGHT_COUNT,\n" );
		sql.append("       D.PROSUPPLY_ID,\n" );
		sql.append("       D.PROSUPPLY_CODE,\n" );
		sql.append("       D.PROSUPPLY_NAME,\n" );
		sql.append("       D.DUTYSUPPLY_ID,\n" );
		sql.append("       D.DUTYSUPPLY_CODE,\n" );
		sql.append("       D.DUTYSUPPLY_NAME,\n" );
		sql.append("       D.IS_MAIN,\n" );
		sql.append("       D.IS_MAIN IS_MAIN_CODE,\n" );
		sql.append("       D.CLAIM_DTL_ID,\n" );
		sql.append("       P.PART_TYPE\n" );//配件类型
		sql.append("  FROM SE_BU_RETURNORDER_DETAIL D, SE_BU_RETURN_ORDER O\n" );
		sql.append("  , PT_BA_INFO P \n");
		sql.append(" WHERE D.ORDER_ID = O.ORDER_ID\n" );
		sql.append("   AND D.PART_ID = P.PART_ID\n" );
		sql.append("   AND D.CLAIM_ID = "+claimId+"\n" );
	//	sql.append("   AND O.ORDER_STATUS = 1");//回运单状态
		bs= factory.select(sql.toString(), page);
		bs.setFieldDic("IS_MAIN", "GZLB");
		bs.setFieldDic("PART_TYPE","PJLB");
		return bs;

	}*/
	
	/**
	 * 旧件选择查询 
	 * @param page
	 * @param conditions
	 * @param claimDtlId
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet claimUpdateOldPartSearch(PageManager page,String conditions)throws Exception {
		BaseResultSet bs=null;
		String wheres = conditions;
   		wheres += " AND T1.PART_ID = T3.PART_ID\n" +
   				  " AND T1.IF_RETURN = 100101\n" + 
   				  " AND T3.STATUS = 100201\n" + 
   				  " AND T3.SE_IDENTIFY ="+DicConstant.YXBS_01+" \n"+
   				  " AND T2.SE_IDENTIFY ="+DicConstant.YXBS_01+" \n"+
   				  " AND T3.SUPPLIER_ID = T2.SUPPLIER_ID\n" + 
   				  " ORDER BY T1.PART_ID";
	    page.setFilter(wheres);
	    StringBuffer sql= new StringBuffer();
	    sql.append("SELECT T1.PART_ID,\n" );
	    sql.append("       T1.PART_CODE,\n" );
	    sql.append("       T1.PART_NAME,\n" );
	    sql.append("       T2.SUPPLIER_ID,\n" );
	    sql.append("       T2.SUPPLIER_NAME,\n" );
	    sql.append("       T2.SUPPLIER_CODE\n" );
	    sql.append("  FROM PT_BA_INFO T1, PT_BA_PART_SUPPLIER_RL T3, PT_BA_SUPPLIER T2");
	   	bs = factory.select(sql.toString(), page);
		return bs;
	}
	/**
	 * 获取索赔单中的配件价格
	 * @param claimId
	 * @param claimDtlId
	 * @param partId
	 * @return
	 * @throws Exception
	 */
/*	public QuerySet getPartPrice(String claimId,String claimDtlId,String partId)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT S.CLAIM_COSTS\n" );
		sql.append("  FROM SE_BU_CLAIM_FAULT_PART S\n" );
		sql.append(" WHERE S.CLAIM_DTL_ID = "+claimDtlId+"\n" );
		sql.append("   AND S.CLAIM_ID = "+claimId+"\n" );
		sql.append("   AND S.OLD_PART_ID = "+partId+"");
		return factory.select(null, sql.toString());
	}*/
	/**
	 * 获取配件代码和名称
	 * @param partId
	 * @return
	 * @throws Exception
	 */
	/*public QuerySet getPartInfo(String partId)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.PART_CODE, T.PART_NAME FROM PT_BA_INFO T WHERE T.PART_ID ="+partId+"");
		return factory.select(null, sql.toString()); 
	}*/
	
	/**
	 * 获取供应商代码和名称
	 * @param partId
	 * @return
	 * @throws Exception
	 */
	/*public QuerySet getSuppInfo(String supplierId)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.SUPPLIER_NAME, T.SUPPLIER_CODE\n" );
		sql.append("  FROM PT_BA_SUPPLIER T\n" );
		sql.append(" WHERE T.SUPPLIER_ID = "+supplierId+"");
		return factory.select(null, sql.toString()); 
	}
	*/
	/**
	 * 获取索赔单故障零件主键
	 * @param claimId
	 * @param claimDtlId
	 * @param partId
	 * @return
	 * @throws Exception
	 */
	/*public QuerySet getFaultPartId(String claimId,String claimDtlId,String partId)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT S.FAULT_PART_ID\n" );
		sql.append("  FROM SE_BU_CLAIM_FAULT_PART S\n" );
		sql.append(" WHERE S.CLAIM_DTL_ID = "+claimDtlId+"\n" );
		sql.append("   AND S.CLAIM_ID = "+claimId+"\n" );
		sql.append("   AND S.OLD_PART_ID = "+partId+"");
		return factory.select(null, sql.toString());
	}*/
	/**
	 *修改旧件回运明细表
	 * @param params
	 * @return
	 * @throws Exception
	 */
	/*public boolean returnDetailUpdate(SeBuReturnorderDetailVO vo)throws Exception{
		return factory.update(vo);
	}*/
	/**
	 * 修改索赔单配件表
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	/*public boolean claimFaultPartUpdate(SeBuClaimFaultPartVO vo)throws Exception{
		return factory.update(vo);
	}*/
	
	/**
	 * 如果是主损件的话，要更新对应的主损件信息
	 * @param params
	 * @return
	 * @throws Exception
	 */
	/*public boolean claimFaultPartAllUpdate(Map<String,String> params)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE SE_BU_CLAIM_FAULT_PART T\n" );
		sql.append("   SET T.FIRST_PART_ID = "+params.get("newPartId")+", T.FIRST_PART_CODE ='"+params.get("partCode")+"' , T.FIRST_PART_NAME = '"+params.get("partName")+"'\n" );
		sql.append(" WHERE T.CLAIM_DTL_ID = "+params.get("claimDtlId")+"\n" );
		sql.append("   AND T.FAULT_PART_ID != "+params.get("faultPartId")+"");
		return factory.update(sql.toString(), null);
	}*/
	/**
	 * 如果是主损件的话，要更旧件回运表的主损件和责任供应商
	 * @param params
	 * @return
	 * @throws Exception
	 */
/*	public boolean returnDetailAllUpdate(Map<String,String> params)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE SE_BU_RETURNORDER_DETAIL D\n" );
		sql.append("   SET D.DUTYSUPPLY_ID = "+params.get("newSupplierId")+", D.DUTYSUPPLY_CODE = '"+params.get("supplierCode")+"', D.DUTYSUPPLY_NAME = '"+params.get("supplierName")+"'\n" );
		sql.append(" WHERE D.CLAIM_DTL_ID = "+params.get("claimDtlId")+"");
		return factory.update(sql.toString(), null);
	}*/
}
