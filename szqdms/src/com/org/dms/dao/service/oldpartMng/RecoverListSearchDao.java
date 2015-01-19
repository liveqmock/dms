package com.org.dms.dao.service.oldpartMng;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuRecoveryVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class RecoverListSearchDao extends BaseDAO{

	 //定义instance
    public  static final RecoverListSearchDao getInstance(ActionContext atx)
    {
    	RecoverListSearchDao dao = new RecoverListSearchDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    /**
     * 追偿清单查询
     * searchRecoverList
     */
    public BaseResultSet searchRecoverList(PageManager page, String conditions,User user) throws SQLException{
    	BaseResultSet bs=null;
    	String wheres = conditions;
    	wheres +=" AND T.RECOVERY_ID = D.RECOVERY_ID AND D.CLAIM_ID = C.CLAIM_ID \n"+ 
    			 //" AND T.STATUS = "+DicConstant.YXBS_01+" \n"+
   			     " ORDER BY T.RECOVERY_ID";
    	page.setFilter(wheres);
    	StringBuffer sql1= new StringBuffer();
    	sql1.append("SELECT T.RECOVERY_ID,\n" );
    	sql1.append("       T.SUPPLIER_ID,\n" );
    	sql1.append("       T.SUPPLIER_NAME,\n" );
    	sql1.append("       T.SUPPLIER_CODE,\n" );
    	sql1.append("       T.RECOVERY_DATE,\n" );
    	sql1.append("       D.CLAIM_ID,\n" );
    	sql1.append("       C.CLAIM_NO,\n" );
    	sql1.append("       NVL(D.CLAIM_COST, 0) CLAIM_COST,\n" );
    	sql1.append("       NVL(D.UNKNOWN_COST, 0) UNKNOWN_COST,\n" );
    	sql1.append("       NVL(D.WORK_COSTS, 0) WORK_COSTS,\n" );
    	sql1.append("       NVL(D.MAINTENANCE_COSTS, 0) MAINTENANCE_COSTS,\n" );
    	sql1.append("       NVL(D.SEVEH_COSTS, 0) SEVEH_COSTS,\n" );
    	sql1.append("       NVL(D.TRAVEL_COSTS, 0) TRAVEL_COSTS,\n" );
    	sql1.append("       NVL(D.MEALS_COSTS, 0) MEALS_COSTS,\n" );
    	sql1.append("       NVL(D.MATERIAL_COSTS, 0) MATERIAL_COSTS,\n" );
    	sql1.append("       NVL(D.OTHER_COSTS, 0) OTHER_COSTS\n" );
    	//sql1.append("       NVL(D.OLDPART_MANAGE_COST, 0) OLDPART_MANAGE_COST\n" );
    	sql1.append("  FROM SE_BU_RECOVERY T, SE_BU_RECOVERY_DTL D, SE_BU_CLAIM C\n" );
    	bs=factory.select(sql1.toString(), page);
    	bs.setFieldDateFormat("RECOVERY_DATE", "yyyy-MM-dd");
    	return bs;
        /*page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.RECOVERY_ID,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.RECOVERY_DATE,\n" );
    	sql.append("       T.ADJUST_TIME,\n" );
    	sql.append("       T.ADJUST_USER,\n" );
    	sql.append("       NVL(T.TOTAL_COST, 0) TOTAL_COST,\n" );
    	sql.append("       NVL(T.CLAIM_TOTAL_COST, 0) CLAIM_TOTAL_COST,\n" );
    	sql.append("       NVL(T.CLAIM_MANAGE_COST, 0) CLAIM_MANAGE_COST,\n" );
    	sql.append("       NVL(T.OLDPART_MANAGE_COST, 0) OLDPART_MANAGE_COST,\n" );
    	sql.append("       NVL(T.ADJUST_COST, 0) ADJUST_COST,\n" );
    	sql.append("       NVL(T.UNKNOWN_TOTAL_COST, 0) UNKNOWN_TOTAL_COST\n" );
    	sql.append("  FROM SE_BU_RECOVERY T\n" );
        bs=factory.select(sql.toString(), page);
        bs.setFieldDateFormat("RECOVERY_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("ADJUST_TIME", "yyyy-MM-dd");
    	return bs;*/
    }
    
    /**
     * 追偿清单调整查询
     * @param page
     * @param conditions
     * @param user
     * @return
     * @throws SQLException
     */
    public BaseResultSet searchRecoverAdjust(PageManager page, String conditions,User user) throws SQLException{
    	String wheres = conditions;
    	wheres += //" AND T.STATUS = "+DicConstant.YXBS_01+"\n"+ 
    			 " AND TO_CHAR(T.RECOVERY_DATE, 'YYYY-MM') = TO_CHAR(SYSDATE, 'YYYY-MM') \n"+
   			     " ORDER BY T.RECOVERY_ID";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.RECOVERY_ID,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.RECOVERY_DATE,\n" );
    	sql.append("       T.ADJUST_TIME,\n" );
    	sql.append("       T.ADJUST_USER,\n" );
    	sql.append("       NVL(T.IF_ADJUST,0) IF_ADJUST,\n" );
    	sql.append("       NVL(T.TOTAL_COST, 0) TOTAL_COST,\n" );
    	sql.append("       NVL(T.CLAIM_TOTAL_COST, 0) CLAIM_TOTAL_COST,\n" );
    	sql.append("       NVL(T.CLAIM_MANAGE_COST, 0) CLAIM_MANAGE_COST,\n" );
    	sql.append("       NVL(T.OLDPART_MANAGE_COST, 0) OLDPART_MANAGE_COST,\n" );
    	sql.append("       NVL(T.ADJUST_COST, 0) ADJUST_COST,\n" );
    	sql.append("       NVL(T.UNKNOWN_TOTAL_COST, 0) UNKNOWN_TOTAL_COST\n" );
    	sql.append("  FROM SE_BU_RECOVERY T\n" );
        bs=factory.select(sql.toString(), page);
        bs.setFieldDateFormat("RECOVERY_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("ADJUST_TIME", "yyyy-MM-dd");
    	return bs;
    }
    
    /**
     * 追偿清单调整
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateRecovery(SeBuRecoveryVO vo)throws Exception{
    	return factory.update(vo);
    }
    
    /**
     * 查询追偿未知明细
     * @return
     * @throws Exception
     */
    public BaseResultSet searchRecoveryDetail(PageManager page,User user,String recoveryId)throws Exception{
    	BaseResultSet bs=null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SUPPLIER_NAME,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       NVL(D.CLAIM_COST, 0) CLAIM_COST,\n" );
    	sql.append("       NVL(D.UNKNOWN_COST, 0) UNKNOWN_COST,\n" );
    	sql.append("       C.CLAIM_NO\n" );
    	sql.append("  FROM SE_BU_RECOVERY_DTL D, SE_BU_RECOVERY T, SE_BU_CLAIM C\n" );
    	sql.append(" WHERE D.RECOVERY_ID = T.RECOVERY_ID\n" );
    	sql.append("   AND D.CLAIM_ID = C.CLAIM_ID\n" );
    	sql.append("   AND NVL(UNKNOWN_COST,0) > 0 ");
    	sql.append("   AND T.RECOVERY_ID = "+recoveryId+"");
    	bs=factory.select(sql.toString(), page);
    	return bs;
    }
    /**
     * 供应商追偿清单查询
     * @param page
     * @param conditions
     * @param user
     * @return
     * @throws SQLException
     */
    public BaseResultSet searchSupRecoverList(PageManager page, String conditions,User user) throws SQLException{
    	String wheres = conditions;
    	wheres +="  AND T.IF_ADJUST = "+DicConstant.SF_01+" \n"+
    			 " AND EXISTS (SELECT 1\n" +
				 "          FROM PT_BA_SUPPLIER S\n" + 
				 "         WHERE S.SUPPLIER_ID = T.SUPPLIER_ID\n" + 
				 "           AND S.ORG_ID = "+user.getOrgId()+") \n"+
   			     " ORDER BY T.RECOVERY_ID";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.RECOVERY_ID,\n" );
    	sql.append("       T.RECOVERY_DATE,\n" );
    	sql.append("       T.ADJUST_TIME,\n" );
    	sql.append("       T.ADJUST_USER,\n" );
    	sql.append("       NVL(T.TOTAL_COST, 0) TOTAL_COST,\n" );
    	sql.append("       NVL(T.CLAIM_TOTAL_COST, 0) CLAIM_TOTAL_COST,\n" );
    	sql.append("       NVL(T.CLAIM_MANAGE_COST, 0) CLAIM_MANAGE_COST,\n" );
    	sql.append("       NVL(T.OLDPART_MANAGE_COST, 0) OLDPART_MANAGE_COST,\n" );
    	sql.append("       NVL(T.ADJUST_COST, 0) ADJUST_COST,\n" );
    	sql.append("       NVL(T.UNKNOWN_TOTAL_COST, 0) UNKNOWN_TOTAL_COST\n" );
    	sql.append("  FROM SE_BU_RECOVERY T\n" );
        bs=factory.select(sql.toString(), page);
        bs.setFieldDateFormat("RECOVERY_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("ADJUST_TIME", "yyyy-MM-dd");
    	return bs;
    }
    /**
     * 追偿清单查询导出
     * @param condition
     * @return
     * @throws Exception
     */
    public QuerySet download(String condition) throws Exception {
    	StringBuffer sql1= new StringBuffer();
    	sql1.append("SELECT T.RECOVERY_ID,\n" );
    	sql1.append("       T.SUPPLIER_ID,\n" );
    	sql1.append("       T.SUPPLIER_NAME,\n" );
    	sql1.append("       T.SUPPLIER_CODE,\n" );
    	sql1.append("       T.RECOVERY_DATE,\n" );
    	sql1.append("       D.CLAIM_ID,\n" );
    	sql1.append("       C.CLAIM_NO,\n" );
    	sql1.append("       NVL(D.CLAIM_COST, 0) CLAIM_COST,\n" );
    	sql1.append("       NVL(D.UNKNOWN_COST, 0) UNKNOWN_COST,\n" );
    	sql1.append("       NVL(D.WORK_COSTS, 0) WORK_COSTS,\n" );
    	sql1.append("       NVL(D.MAINTENANCE_COSTS, 0) MAINTENANCE_COSTS,\n" );
    	sql1.append("       NVL(D.SEVEH_COSTS, 0) SEVEH_COSTS,\n" );
    	sql1.append("       NVL(D.TRAVEL_COSTS, 0) TRAVEL_COSTS,\n" );
    	sql1.append("       NVL(D.MEALS_COSTS, 0) MEALS_COSTS,\n" );
    	sql1.append("       NVL(D.MATERIAL_COSTS, 0) MATERIAL_COSTS,\n" );
    	sql1.append("       NVL(D.OTHER_COSTS, 0) OTHER_COSTS\n" );
    	//sql1.append("       NVL(D.OLDPART_MANAGE_COST, 0) OLDPART_MANAGE_COST\n" );
    	sql1.append("  FROM SE_BU_RECOVERY T, SE_BU_RECOVERY_DTL D, SE_BU_CLAIM C\n" );
    	sql1.append(" WHERE "+condition+"  AND T.RECOVERY_ID = D.RECOVERY_ID AND D.CLAIM_ID = C.CLAIM_ID \n");
    	//sql1.append("   AND T.STATUS =  "+DicConstant.YXBS_01+"\n" );
    	sql1.append(" ORDER BY T.RECOVERY_ID");
    	return factory.select(null, sql1.toString());
    }
    
    /*public QuerySet download() throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT S.SUPPLIER_CODE,\n" );
    	sql.append("       S.SUPPLIER_NAME,\n" );
    	sql.append("       S.RECOVERY_PRICE,\n" );
    	sql.append("       S.RECOVERY_ID,\n" );
    	sql.append("       S.SUPPLIER_ID,\n" );
    	sql.append("       S.RECOVERY_NO,\n" );
    	sql.append("       S.RECOVERY_DATE,\n" );
    	sql.append("       S.COMPANY_ID,\n" );
    	sql.append("       S.CREATE_USER,\n" );
    	sql.append("       S.CREATE_TIME,\n" );
    	sql.append("       S.UPDATE_USER,\n" );
    	sql.append("       S.UPDATE_TIME,\n" );
    	sql.append("       S.STATUS,\n" );
    	sql.append("       S.OEM_COMPANY_ID,\n" );
    	sql.append("       C.CLAIM_ID,\n" );
    	sql.append("       C.CLAIM_NO,\n" );
    	sql.append("       C.OLDPART_FINAL_DATE,\n" );
    	sql.append("       C.FINAL_USER\n" );
    	sql.append("  FROM SE_BU_RECOVERY         S,\n" );
    	sql.append("       SE_BU_RECOVERY_DTL     D,\n" );
    	sql.append("       SE_BU_CLAIM            C\n" );
    	sql.append(" WHERE S.RECOVERY_ID = D.RECOVERY_ID\n" );
    	sql.append("   AND D.CLAIM_ID = C.CLAIM_ID");
        return factory.select(null, sql.toString());
    }*/
    /**
     *  供应商追偿清单查询导出
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public QuerySet downloadSup(User user,String conditions) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.RECOVERY_ID,\n" );
    	sql.append("       TO_CHAR(T.RECOVERY_DATE,'YYYY-MM-DD') RECOVERY_DATE,\n" );
    	sql.append("       T.ADJUST_TIME,\n" );
    	sql.append("       T.ADJUST_USER,\n" );
    	sql.append("       NVL(T.TOTAL_COST, 0) TOTAL_COST,\n" );
    	sql.append("       NVL(T.CLAIM_TOTAL_COST, 0) CLAIM_TOTAL_COST,\n" );
    	sql.append("       NVL(T.CLAIM_MANAGE_COST, 0) CLAIM_MANAGE_COST,\n" );
    	sql.append("       NVL(T.OLDPART_MANAGE_COST, 0) OLDPART_MANAGE_COST,\n" );
    	sql.append("       NVL(T.ADJUST_COST, 0) ADJUST_COST,\n" );
    	sql.append("       NVL(T.UNKNOWN_TOTAL_COST, 0) UNKNOWN_TOTAL_COST\n" );
    	sql.append("  FROM SE_BU_RECOVERY T\n" );
    	sql.append(" WHERE "+conditions+"\n" );
    	//sql.append("   AND T.STATUS = "+DicConstant.YXBS_01+"\n" );s
    	sql.append(" AND T.IF_ADJUST= "+DicConstant.SF_01+" \n");
    	sql.append(" AND EXISTS (SELECT 1\n" );
    	sql.append("               FROM PT_BA_SUPPLIER S\n" );
    	sql.append("              WHERE S.SUPPLIER_ID = T.SUPPLIER_ID\n" );
    	sql.append("                AND S.ORG_ID = "+user.getOrgId()+") \n");
    	sql.append(" ORDER BY T.RECOVERY_ID");
    	return factory.select(null, sql.toString());
    }
    
    
    /**
     * 追偿费用明细
     * @param recoveryId
     * @return
     * @throws Exception
     */
    public BaseResultSet searchRecoveryClaimDetail(PageManager page,String recoveryId)throws Exception{
    	BaseResultSet bs=null;
    	StringBuffer sql1= new StringBuffer();
    	sql1.append("SELECT T.RECOVERY_ID,\n" );
    	sql1.append("       T.SUPPLIER_ID,\n" );
    	sql1.append("       T.SUPPLIER_NAME,\n" );
    	sql1.append("       T.SUPPLIER_CODE,\n" );
    	sql1.append("       T.RECOVERY_DATE,\n" );
    	sql1.append("       D.CLAIM_ID,\n" );
    	sql1.append("       C.CLAIM_NO,\n" );
    	sql1.append("       NVL(D.CLAIM_COST, 0) CLAIM_COST,\n" );
    	sql1.append("       NVL(D.UNKNOWN_COST, 0) UNKNOWN_COST,\n" );
    	sql1.append("       NVL(D.WORK_COSTS, 0) WORK_COSTS,\n" );
    	sql1.append("       NVL(D.MAINTENANCE_COSTS, 0) MAINTENANCE_COSTS,\n" );
    	sql1.append("       NVL(D.SEVEH_COSTS, 0) SEVEH_COSTS,\n" );
    	sql1.append("       NVL(D.TRAVEL_COSTS, 0) TRAVEL_COSTS,\n" );
    	sql1.append("       NVL(D.MEALS_COSTS, 0) MEALS_COSTS,\n" );
    	sql1.append("       NVL(D.MATERIAL_COSTS, 0) MATERIAL_COSTS,\n" );
    	sql1.append("       NVL(D.OTHER_COSTS, 0) OTHER_COSTS,\n" );
    	sql1.append("       NVL(D.OLDPART_MANAGE_COST, 0) OLDPART_MANAGE_COST\n" );
    	sql1.append("  FROM SE_BU_RECOVERY T, SE_BU_RECOVERY_DTL D, SE_BU_CLAIM C\n" );
    	sql1.append(" WHERE T.RECOVERY_ID = D.RECOVERY_ID\n" );
    	sql1.append("   AND D.CLAIM_ID = C.CLAIM_ID\n" );
    	sql1.append("   AND T.RECOVERY_ID = "+recoveryId+"\n" );
    	sql1.append(" ORDER BY T.RECOVERY_ID");
    	bs=factory.select(sql1.toString(), page);
    	return bs;
    }
    
    /**
     * 下载
     * @param recoveryId
     * @return
     * @throws Exception
     */
    public QuerySet downloadClaim(String recoveryId)throws Exception{
    	StringBuffer sql1= new StringBuffer();
    	sql1.append("SELECT T.RECOVERY_ID,\n" );
    	sql1.append("       T.SUPPLIER_ID,\n" );
    	sql1.append("       T.SUPPLIER_NAME,\n" );
    	sql1.append("       T.SUPPLIER_CODE,\n" );
    	sql1.append("       T.RECOVERY_DATE,\n" );
    	sql1.append("       D.CLAIM_ID,\n" );
    	sql1.append("       C.CLAIM_NO,\n" );
    	sql1.append("       NVL(D.CLAIM_COST, 0) CLAIM_COST,\n" );
    	sql1.append("       NVL(D.UNKNOWN_COST, 0) UNKNOWN_COST,\n" );
    	sql1.append("       NVL(D.WORK_COSTS, 0) WORK_COSTS,\n" );
    	sql1.append("       NVL(D.MAINTENANCE_COSTS, 0) MAINTENANCE_COSTS,\n" );
    	sql1.append("       NVL(D.SEVEH_COSTS, 0) SEVEH_COSTS,\n" );
    	sql1.append("       NVL(D.TRAVEL_COSTS, 0) TRAVEL_COSTS,\n" );
    	sql1.append("       NVL(D.MEALS_COSTS, 0) MEALS_COSTS,\n" );
    	sql1.append("       NVL(D.MATERIAL_COSTS, 0) MATERIAL_COSTS,\n" );
    	sql1.append("       NVL(D.OTHER_COSTS, 0) OTHER_COSTS,\n" );
    	sql1.append("       NVL(D.OLDPART_MANAGE_COST, 0) OLDPART_MANAGE_COST\n" );
    	sql1.append("  FROM SE_BU_RECOVERY T, SE_BU_RECOVERY_DTL D, SE_BU_CLAIM C\n" );
    	sql1.append(" WHERE T.RECOVERY_ID = D.RECOVERY_ID\n" );
    	sql1.append("   AND D.CLAIM_ID = C.CLAIM_ID\n" );
    	sql1.append("   AND T.RECOVERY_ID = "+recoveryId+"\n" );
    	sql1.append(" ORDER BY T.RECOVERY_ID");
    	return factory.select(null, sql1.toString());
    }
    
    
    /**
     * 供应商追偿清单查询明细
     * @return
     * @throws Exception
     */
    public BaseResultSet searchSuppRecoverDetailList(PageManager page, String conditions,User user)throws Exception{
    	String wheres = conditions;
    	   wheres +=" AND T.RECOVERY_ID = D.RECOVERY_ID\n" +
					" AND D.CLAIM_ID = C.CLAIM_ID\n" + 
					//" AND T.STATUS = "+DicConstant.YXBS_01+"\n" + 
					//" AND T.IF_ADJUST ="+DicConstant.SF_01+" \n"+
					" AND EXISTS (SELECT 1\n" + 
					"           FROM PT_BA_SUPPLIER S\n" + 
					"          WHERE S.SUPPLIER_ID = T.SUPPLIER_ID\n" + 
					"            AND S.ORG_ID = "+user.getOrgId()+")\n" + 
					"  ORDER BY T.RECOVERY_ID";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.RECOVERY_ID,\n" );
    	sql.append("       D.DTL_ID,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.RECOVERY_DATE,\n" );
    	sql.append("       D.CLAIM_ID,\n" );
    	sql.append("       C.CLAIM_NO,\n" );
    	sql.append("       NVL(D.CLAIM_COST, 0) CLAIM_COST,\n" );
    	sql.append("       NVL(D.UNKNOWN_COST, 0) UNKNOWN_COST,\n" );
    	sql.append("       NVL(D.WORK_COSTS, 0)WORK_COSTS,\n" );
    	sql.append("       NVL(D.MAINTENANCE_COSTS, 0) MAINTENANCE_COSTS,\n" );
    	sql.append("       NVL(D.SEVEH_COSTS, 0) SEVEH_COSTS,\n" );
    	sql.append("       NVL(D.TRAVEL_COSTS, 0) TRAVEL_COSTS,\n" );
    	sql.append("       NVL(D.MEALS_COSTS, 0) MEALS_COSTS,\n" );
    	sql.append("       NVL(D.MATERIAL_COSTS, 0) MATERIAL_COSTS,\n" );
    	sql.append("       NVL(D.OTHER_COSTS, 0) OTHER_COSTS　\n" );
    	sql.append("  FROM SE_BU_RECOVERY T, SE_BU_RECOVERY_DTL D, SE_BU_CLAIM C");

    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("RECOVERY_DATE", "yyyy-MM-dd");
    	return bs;
    }
    
    /**
     * 供应商追偿清单查询明细
     * @return
     * @throws Exception
     */
    public QuerySet downloadSuppRecoverDetailList(String conditions,User user)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.RECOVERY_ID,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.RECOVERY_DATE,\n" );
    	sql.append("       D.CLAIM_ID,\n" );
    	sql.append("       C.CLAIM_NO,\n" );
    	sql.append("       NVL(D.CLAIM_COST, 0) CLAIM_COST,\n" );
    	sql.append("       NVL(D.UNKNOWN_COST, 0) UNKNOWN_COST,\n" );
    	sql.append("       NVL(D.WORK_COSTS, 0) WORK_COSTS,\n" );
    	sql.append("       NVL(D.MAINTENANCE_COSTS, 0) MAINTENANCE_COSTS,\n" );
    	sql.append("       NVL(D.SEVEH_COSTS, 0) SEVEH_COSTS,\n" );
    	sql.append("       NVL(D.TRAVEL_COSTS, 0) TRAVEL_COSTS,\n" );
    	sql.append("       NVL(D.MEALS_COSTS, 0) MEALS_COSTS,\n" );
    	sql.append("       NVL(D.MATERIAL_COSTS, 0) MATERIAL_COSTS,\n" );
    	sql.append("       NVL(D.OTHER_COSTS, 0) OTHER_COSTS\n" );
    	//sql.append("       NVL(D.OLDPART_MANAGE_COST, 0) OLDPART_MANAGE_COST\n" );
    	sql.append("  FROM SE_BU_RECOVERY T, SE_BU_RECOVERY_DTL D, SE_BU_CLAIM C\n");
    	sql.append(" WHERE  "+conditions+" AND T.RECOVERY_ID = D.RECOVERY_ID\n");
    	sql.append(" AND D.CLAIM_ID = C.CLAIM_ID\n");
    	//sql.append(" AND T.STATUS = "+DicConstant.YXBS_01+"\n");
    	sql.append(" AND T.IF_ADJUST = "+DicConstant.SF_01+" \n");
    	sql.append(" AND EXISTS (SELECT 1\n");
    	sql.append(" FROM PT_BA_SUPPLIER S\n");
    	sql.append(" WHERE S.SUPPLIER_ID = T.SUPPLIER_ID\n");
    	sql.append(" AND S.ORG_ID = "+user.getOrgId()+")\n");
    	sql.append(" ORDER BY T.RECOVERY_ID\n");
    	return factory.select(null, sql.toString());
    }

    /**
     * 查询调整数据
     * @return
     * @throws Exception
     */
    public QuerySet queryAdjust()throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT COUNT(1) SL\n" );
    	sql.append("  FROM SE_BU_RECOVERY T\n" );
    	sql.append(" WHERE TO_CHAR(T.RECOVERY_DATE, 'YYYY-MM') = TO_CHAR(SYSDATE, 'YYYY-MM')\n");
    	sql.append("   AND NVL(T.IF_ADJUST,0) <> "+DicConstant.SF_01+"  ");
    	return factory.select(null, sql.toString());
    }
    
    /**
     * 调整完成 
     * @return
     * @throws Exception
     */
    public boolean updateIfAdjust(User user)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE SE_BU_RECOVERY T\n" );
    	sql.append("   SET T.IF_ADJUST = "+DicConstant.SF_01+", T.UPDATE_TIME = SYSDATE, T.UPDATE_USER = '"+user.getAccount()+"'\n" );
    	sql.append(" WHERE TO_CHAR(T.RECOVERY_DATE, 'YYYY-MM') = TO_CHAR(SYSDATE, 'YYYY-MM')");
    	return factory.update(sql.toString(), null);
    }
}
