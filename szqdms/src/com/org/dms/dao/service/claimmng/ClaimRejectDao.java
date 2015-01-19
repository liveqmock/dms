package com.org.dms.dao.service.claimmng;

import java.util.Map;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuDealerStockVO;
import com.org.dms.vo.service.SeBuClaimCheckVO;
import com.org.dms.vo.service.SeBuClaimVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 索赔单驳回DAO 
 * @author zts
 *
 */
public class ClaimRejectDao extends BaseDAO
{
    //定义instance
    public  static final ClaimRejectDao getInstance(ActionContext atx)
    {
    	ClaimRejectDao dao = new ClaimRejectDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    /**
     * 索赔单查询
     * @param page
     * @param condition
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet claimSearch(PageManager page,String condition,User user)throws Exception{
    	String where = condition;
	       where += " AND C.WORK_ID = O.WORK_ID \n "+ 
	    		    " AND C.CLAIM_STATUS IN ( "+DicConstant.SPDZT_05+", "+DicConstant.SPDZT_15+")\n "+ //索赔单状态初审 终审的
					//驳回当月初审的报单
	    		    " AND TO_CHAR(C.CHECKPASS_DATE,'YYYY-MM') LIKE (SELECT TO_CHAR(SYSDATE, 'YYYY-MM') FROM DUAL) \n"+
	    		    " AND NOT EXISTS (SELECT 1 FROM SE_BU_RETURNORDER_DETAIL D\n" +
					" WHERE D.CLAIM_ID = C.CLAIM_ID) \n"+
	       			" ORDER BY C.CLAIM_NO ";
		page.setFilter(where);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT O.WORK_ID,\n" );
		sql.append("       O.WORK_NO,\n" );
		sql.append("       C.CLAIM_ID,\n" );
		sql.append("       C.CLAIM_NO,\n" );
		sql.append("       C.VIN,\n" );
		sql.append("       C.APPLY_DATE,\n" );
		sql.append("       C.CLAIM_STATUS,\n" );
		sql.append("       C.CLAIM_TYPE,\n" );
		sql.append("       C.ORG_ID       ORG_NAME,\n" );
		sql.append("       C.ORG_ID       ORG_CODE \n");
		sql.append("  FROM SE_BU_CLAIM C, SE_BU_WORK_ORDER O \n");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_STATUS","SPDZT");
		bs.setFieldDic("CLAIM_TYPE","SPDLX");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
    	bs.setFieldOrgDeptCode("ORG_CODE");
		return bs;
    }
    
    /**
     * 获取索赔单信息
     * @param claimId
     * @return
     * @throws Exception
     */
    public QuerySet getClaimInfo(String claimId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT C.CLAIM_TYPE, C.VEHICLE_ID,C.ACTIVITY_ID,C.PRE_AUTHOR_ID FROM SE_BU_CLAIM C WHERE C.CLAIM_ID = "+claimId+"");
    	return factory.select(null, sql.toString());
    }
    
    /**
     * 索赔单驳回
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean claimRejectUpdae(SeBuClaimVO vo)throws Exception{
    	return factory.update(vo);
    }
    
    /**
     * 逆流程  将终审时间更新为空（特殊情况）
     * @param claimId
     * @return
     * @throws Exception
     */
    public boolean updateFinalDate(String claimId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE SE_BU_CLAIM C SET C.OLDPART_FINAL_DATE = '' , C.CHECKPASS_DATE='' WHERE C.CLAIM_ID =  "+claimId+"");
    	return factory.update(sql.toString(), null);
    }
    
    /**
     * 逆流程 将不需要回运的件 审核通过数量更新为0
     * @return
     * @throws Exception
     */
    public boolean updateOughtCount(String claimId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE SE_BU_CLAIM_FAULT_PART P\n" );
    	sql.append("   SET P.OUGHT_COUNT = 0\n" );
    	sql.append(" WHERE P.CLAIM_ID = "+claimId+"\n" );
    	sql.append("   AND EXISTS (SELECT 1\n" );
    	sql.append("          FROM PT_BA_INFO I\n" );
    	sql.append("         WHERE P.OLD_PART_ID = I.PART_ID\n" );
    	sql.append("           AND I.IF_RETURN = 100102)");
    	return factory.update(sql.toString(), null);
    }
    /**
     * 逆流成 将处理措施是加装 和维修的 审核通过数量更新为0
     * @param claimId
     * @return
     * @throws Exception
     */
    public boolean updateOughtCount1(String claimId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE SE_BU_CLAIM_FAULT_PART P\n" );
    	sql.append("   SET P.OUGHT_COUNT = 0\n" );
    	sql.append(" WHERE P.CLAIM_ID = "+claimId+"\n" );
    	sql.append("   AND P.MEASURES IN(300601,300603)");
    	return factory.update(sql.toString(), null);
    }
    
    @SuppressWarnings("rawtypes")
	public boolean updateVehicle(User user,String vehicleId,Map map)throws Exception{
    	String flag ="0";
 	   	String sbxx=(String)map.get("sbxx");//首保信息
 	   	String gCount=(String)map.get("dbcs");//定保次数，-1
	 	String safechecktimes=(String)map.get("aqjccs");//安全检查次数 0
	 	String traintimes=(String)map.get("sqjccs");//售前检查培训次数0
	 	StringBuffer sql= new StringBuffer();
	 	sql.append("UPDATE MAIN_VEHICLE V\n" );
	 	sql.append("   SET V.UPDATE_USER='"+user.getAccount()+"',V.UPDATE_TIME = SYSDATE ,V.MILEAGE = V.LRUNKM\n");
	 	if(sbxx !=null && !"".equals(sbxx)){
	 		sql.append("   ,V.MAINTENANCE_DATE = '' \n");
	 		sql.append("   ,V.FMAINTAINFLAG = 100102 \n");//强保标识
	 		flag="1";
	 	}
	 	if(gCount!=null &&!"".equals(gCount))
	 	{
	 		sql.append("   ,V.G_COUNT = V.G_COUNT - 1 \n");
	 		flag="1";
	 	}
	 	if(safechecktimes!=null &&!"".equals(safechecktimes))
	 	{
	 		sql.append("   ,V.SAFECHECKTIMES = V.SAFECHECKTIMES - 1 \n");
	 		flag="1";
	 	}
	 	if(traintimes!=null &&!"".equals(traintimes))
	 	{
	 		sql.append("   ,V.TRAINTIMES = V.TRAINTIMES - 1 \n");
	 		flag="1";
	 	}
	 	sql.append(" WHERE VEHICLE_ID= "+vehicleId+" ");
	 	if("1".equals(flag)){
	 		return factory.update(sql.toString(), null);
	 	}else{
	 		return true;
	 	}
    }
    
    /**
     * 审核轨迹
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertCheck(SeBuClaimCheckVO vo)throws Exception{
    	return factory.insert(vo);
    }
    /**
     * 查询索赔单中旧件ID，与供应商ID
     * @return
     * @throws Exception
     */
    public QuerySet queryParts(String claimId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT D.PART_ID,\n" );
    	sql.append("       D.PART_CODE,\n" );
    	sql.append("       D.PART_NAME,\n" );
    	sql.append("       R.ORG_ID,\n" );
    	sql.append("       D.SUPPLIER_ID,\n" );
    	sql.append("       D.SUPPLIER_CODE,\n" );
    	sql.append("       D.SUPPLIER_NAME,\n" );
    	sql.append("       R.SALE_ID,\n" );
    	sql.append("       D.DTL_ID,\n" );
    	sql.append("       D.SALE_COUNT\n" );
    	sql.append("  FROM PT_BU_REAL_SALE R, PT_BU_REAL_SALE_DTL D\n" );
    	sql.append(" WHERE R.SALE_ID = D.SALE_ID\n" );
    	sql.append("   AND R.CLAIM_ID = "+claimId+"");
    	sql.append("   AND R.SALE_STATUS = "+DicConstant.SXDZT_02+"");
    	sql.append("   AND R.STATUS = "+DicConstant.YXBS_01+"");
 	   return factory.select(null, sql.toString());
    }
    /**
     * 查询索赔单中旧件ID，与供应商ID,服务站对应的可用库存
     * @return
     * @throws Exception
     */
    public QuerySet queryStock(String oPartId,String oSupplierId, String orgId)throws Exception{
 	   StringBuffer sql= new StringBuffer();
 	   sql.append("SELECT NVL(T.AVAILABLE_AMOUNT,0) AVAILABLE_AMOUNT, NVL(T.OCCUPY_AMOUNT,0) OCCUPY_AMOUNT, NVL(T.AMOUNT,0) AMOUNT,T.STOCK_ID\n" );
 	   sql.append("  FROM PT_BU_DEALER_STOCK T\n" );
 	   sql.append(" WHERE T.PART_ID ="+oPartId+"\n" );
 	   sql.append("   AND T.SUPPLIER_ID ="+oSupplierId+"\n" );
 	   sql.append("   AND T.ORG_ID ="+orgId+"\n" );
 	   sql.append("   AND T.STATUS = "+DicConstant.YXBS_01+"");
 	   return factory.select(null, sql.toString());
    }
    /**
     * 库存锁定数量
     */
    public boolean DealerStockUpdate(PtBuDealerStockVO vo) throws Exception {

        return factory.update(vo);
    }
    /**
     * 将实销出库单更新为无效。
     */
    public boolean updateRsStatus(String claimId) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_REAL_SALE R\n" );
    	sql.append("   SET R.STATUS = "+DicConstant.YXBS_02+"\n" );
    	sql.append(" WHERE R.CLAIM_ID = "+claimId+"\n" );
    	sql.append("   AND R.STATUS = "+DicConstant.YXBS_01+"");
		return factory.update(sql.toString(), null);

    }
    /**
     * 将外采申请单更新为无效。
     */
    public boolean updateObStatus(String claimId) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_OUT_BUY R\n" );
    	sql.append("   SET R.STATUS = "+DicConstant.YXBS_02+"\n" );
    	sql.append(" WHERE R.CLAIM_ID = "+claimId+"\n" );
    	sql.append("   AND R.STATUS = "+DicConstant.YXBS_01+"");
    	return factory.update(sql.toString(), null);
    	
    }
    public void insetStockDtl(Integer saleCount,User user,String saleId,String url,String nPartId,String orgId) throws Exception
    {
 	   StringBuffer sql= new StringBuffer();
 	   sql.append("INSERT INTO PT_BU_DEALER_STOCK_LOG\n" );
 	   sql.append("          (LOG_ID,\n" );
 	   sql.append("           YWZJ,\n" );
 	   sql.append("           ACTION_URL,\n" );
 	   sql.append("           OAMOUNT,\n" );
 	   sql.append("           AMOUNT,\n" );
 	   sql.append("           AAMOUNT,\n" );
 	   sql.append("           UPDATE_USER,\n" );
 	   sql.append("           UPDATE_TIME,\n" );
 	   sql.append("           PART_ID,\n" );
 	   sql.append("           ORG_ID)\n" );
 	   sql.append("        VALUES\n" );
 	   sql.append("          (F_GETID(),\n" );
 	   sql.append("           '"+saleId+"',\n" );
 	   sql.append("           '"+url+"',\n" );
 	   sql.append("           '',\n" );//占用
 	   sql.append("           '"+saleCount+"',\n" );//总数
 	   sql.append("           '"+saleCount+"',\n" );//可用
 	   sql.append("           '"+user.getAccount()+"',\n" );
 	   sql.append("           SYSDATE,\n" );
 	   sql.append("           '"+nPartId+"',\n" );
 	   sql.append("           '"+orgId+"')");

 	   factory.update(sql.toString(), null);
    }
    public void insetStockChange(String dtlId,String stockId, String orgId,User user) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("INSERT INTO PT_BU_DEALER_STOCK_CHANGE(\n" );
    	sql.append("       CHANGE_ID,\n" );
    	sql.append("       STOCK_ID,\n" );
    	sql.append("       ORG_ID,\n" );
    	sql.append("       ORG_CODE,\n" );
    	sql.append("       ORG_NAME,\n" );
    	sql.append("       PART_ID,\n" );
    	sql.append("       PART_CODE,\n" );
    	sql.append("       PART_NAME,\n" );
    	sql.append("       COUNT,\n" );
    	sql.append("       APPLY_DATE,\n" );
    	sql.append("       APPLY_TYPE,\n" );
    	sql.append("       CREATE_USER,\n" );
    	sql.append("       CREATE_TIME,\n" );
    	sql.append("       STATUS,\n" );
    	sql.append("       SUPPLIER_ID,\n" );
    	sql.append("       SUPPLIER_CODE,\n" );
    	sql.append("       SUPPLIER_NAME,\n" );
    	sql.append("       STORAGE_TYPE,\n" );
    	sql.append("       OUT_NO)\n" );
    	sql.append("SELECT F_GETID(),\n" );
    	sql.append("        '"+stockId+"',--STOCK_ID\n" );
    	sql.append("        '"+orgId+"',--ORG_ID\n" );
    	sql.append("        (SELECT G.CODE FROM TM_ORG G WHERE G.ORG_ID = "+orgId+"),--ORG_CODE\n" );
    	sql.append("        (SELECT G.ONAME FROM TM_ORG G WHERE G.ORG_ID = "+orgId+"),--ORG_NAME\n" );
    	sql.append("        D.PART_ID,\n" );
    	sql.append("        D.PART_CODE,\n" );
    	sql.append("        D.PART_NAME,\n" );
    	sql.append("        D.SALE_COUNT,\n" );
    	sql.append("        SYSDATE,\n" );
    	sql.append("        '204101',\n" );
    	sql.append("        '"+user.getAccount()+"',--创建人\n" );
    	sql.append("        SYSDATE,\n" );
    	sql.append("        '100201',\n" );
    	sql.append("        D.SUPPLIER_ID,\n" );
    	sql.append("        D.SUPPLIER_CODE,\n" );
    	sql.append("        D.SUPPLIER_NAME,\n" );
    	sql.append("        '204704',\n" );
    	sql.append("        (SELECT R.SALE_NO FROM PT_BU_REAL_SALE R WHERE R.SALE_ID = D.SALE_ID)\n" );
    	sql.append(" FROM PT_BU_REAL_SALE_DTL D\n" );
    	sql.append("  WHERE D.DTL_ID ="+dtlId+"");
    	
    	factory.update(sql.toString(), null);
    }
}
