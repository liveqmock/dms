package com.org.dms.dao.service.claimmng;


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
 * 供应商审核DAO
 * @author zts
 *
 */
public class ClaimSupplierCheckDao extends BaseDAO
{
    //定义instance
    public  static final ClaimSupplierCheckDao getInstance(ActionContext atx)
    {
    	ClaimSupplierCheckDao dao = new ClaimSupplierCheckDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 索赔单查询  索赔单状态自动审核通过
     * @param page
     * @param condition
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet claimSearch(PageManager page,String condition,User user)throws Exception{
    	String where = condition;
	       where += " AND T.CLAIM_ID = F.CLAIM_ID \n "+ 
					" AND F.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" +
	    		    " AND T.WORK_ID = O.WORK_ID \n"+
					" AND EXISTS (SELECT 1\n" +
					"          FROM PT_BA_SUPPLIER S\n" + 
					"         WHERE S.SUPPLIER_ID = P.OLD_SUPPLIER_ID\n" + 
					"           AND S.ORG_ID = "+user.getOrgId()+") \n"+
					" AND T.CLAIM_STATUS = "+DicConstant.SPDZT_03+" \n"+ //索赔单状态自动审核通过
					" AND NVL(T.STOCK_MEET,0) <> "+DicConstant.YXBS_02+" \n"+ //办事处审核通过的单子
					" AND P.SUPPLIER_OPTION_STATUS IS NULL \n"+ //没审核的单子
					" AND P.FAULT_TYPE="+DicConstant.GZLB_01+" \n"+ //主损件
	       			" ORDER BY T.CLAIM_NO ";
		page.setFilter(where);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT DISTINCT T.CLAIM_ID,\n" );
		sql.append("                T.CLAIM_NO,\n" );
		sql.append("                T.CLAIM_TYPE,\n" );
		sql.append("                T.CLAIM_STATUS,\n" );
		sql.append("                T.APPLY_DATE,\n" );
		sql.append("                T.APPLY_COUNT,\n" );
		sql.append("                O.WORK_ID,\n" );
		sql.append("                O.WORK_NO\n" );
		sql.append("  FROM SE_BU_CLAIM            T,\n" );
		sql.append("       SE_BU_CLAIM_FAULT      F,\n" );
		sql.append("       SE_BU_CLAIM_FAULT_PART P,\n" );
		sql.append("       SE_BU_WORK_ORDER       O");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_STATUS","SPDZT");
		bs.setFieldDic("CLAIM_TYPE","SPDLX");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
    	bs.setFieldOrgDeptCode("ORG_CODE");
		return bs;
    }
    
    /**
     * 查询故障信息
     * @param page
     * @param claimId
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet searchClaimPattern(PageManager page,String claimId,User user)throws Exception{
    	BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT CLAIM_DTL_ID,\n" );
		sql.append("       CLAIM_ID,\n" );
		sql.append("       FAULT_ID,\n" );
		sql.append("       FAULT_CODE,\n" );
		sql.append("       FAULT_NAME,\n" );
		sql.append("       WORK_TIME,\n" );
		sql.append("       WORK_TIME_UPRICE,\n" );
		sql.append("       STAR_LEVEL_UPRICE,\n" );
		sql.append("       ENCOURAGE_UPRICE,\n" );
		sql.append("       WORK_COSTS,\n" );
		sql.append("       WORK_MULTIPLE\n" );
		sql.append("  FROM SE_BU_CLAIM_FAULT A\n" );
		sql.append(" WHERE EXISTS\n" );
		sql.append(" (SELECT 1\n" );
		sql.append("          FROM SE_BU_CLAIM C, SE_BU_CLAIM_FAULT F, SE_BU_CLAIM_FAULT_PART P\n" );
		sql.append("         WHERE C.CLAIM_ID = F.CLAIM_ID\n" );
		sql.append("           AND F.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
		sql.append("           AND P.FAULT_TYPE = "+DicConstant.GZLB_01+"\n" );
		sql.append("           AND EXISTS (SELECT 1\n" );
		sql.append("                  FROM PT_BA_SUPPLIER S\n" );
		sql.append("                 WHERE S.SUPPLIER_ID = P.OLD_SUPPLIER_ID\n" );
		sql.append("                   AND S.ORG_ID = "+user.getOrgId()+")\n" );
		sql.append("           AND C.CLAIM_ID = "+claimId+"\n" );
		sql.append("           AND A.CLAIM_DTL_ID = F.CLAIM_DTL_ID)");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("FAULT_TYPE", "GZLB");
		return bs;
    }
    
    /**
     * 配件信息查询
     * @param page
     * @param claimId
     * @param user
     * @return
     */
    public BaseResultSet searchPart(PageManager page,String claimDtlId,User user)throws Exception{
		BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.FAULT_TYPE,\n" );
    	sql.append("       T.MEASURES,\n" );
    	sql.append("       T.OLD_PART_CODE,\n" );
    	sql.append("       T.OLD_PART_NAME,\n" );
    	sql.append("       T.OLD_PART_COUNT,\n" );
    	sql.append("       O.SUPPLIER_NAME   OLD_SUPPLIER_NAME,\n" );
    	sql.append("       O.SUPPLIER_CODE   OLD_SUPPLIER_CODE,\n" );
    	sql.append("       T.OLD_PART_STREAM,\n" );
    	sql.append("       T.NEW_PART_CODE,\n" );
    	sql.append("       T.NEW_PART_NAME,\n" );
    	sql.append("       T.NEW_PART_COUNT,\n" );
    	sql.append("       N.SUPPLIER_NAME   NEW_SUPPLIER_NAME,\n" );
    	sql.append("       N.SUPPLIER_CODE   NEW_SUPPLIER_CODE,\n" );
    	sql.append("       T.NEW_PART_STREAM,\n" );
    	sql.append("       T.REPAY_UPRICE,\n" );
    	sql.append("       T.CLAIM_UPRICE,\n" );
    	sql.append("       T.BRIDGE_CODE,\n" );
    	sql.append("       T.FAULT_REASON\n" );
    	sql.append("  FROM SE_BU_CLAIM_FAULT_PART T, PT_BA_SUPPLIER O, PT_BA_SUPPLIER N\n" );
    	sql.append(" WHERE T.CLAIM_DTL_ID ="+claimDtlId+"\n" );
    	sql.append("   AND T.OLD_SUPPLIER_ID = O.SUPPLIER_ID(+) \n" );
    	sql.append("   AND T.NEW_SUPPLIER_ID = N.SUPPLIER_ID(+) ");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDic("FAULT_TYPE", "GZLB");
    	bs.setFieldDic("MEASURES", "CLFS");
		return bs;
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
     * 供应商是否审核权
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet getSupplierCheck(User user)throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT NVL(S.IF_CLAIM_CHECK,0) IF_CLAIM_CHECK FROM PT_BA_SUPPLIER S WHERE S.ORG_ID = "+user.getOrgId()+"");
    	return factory.select(null, sql.toString());
    }
    
    /**
     * 更新意见
     * @param user
     * @param claimId
     * @return
     * @throws Exception
     */
    public boolean updateFaultPart(User user ,String claimId,String gysyjzt)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE SE_BU_CLAIM_FAULT_PART T\n" );
    	sql.append("   SET T.SUPPLIER_OPTION_STATUS = "+gysyjzt+"\n" );
    	sql.append(" WHERE T.CLAIM_ID = "+claimId+"\n" );
    	sql.append("   AND T.MEASURES IN ("+DicConstant.CLFS_01+", "+DicConstant.CLFS_02+")\n" );
    	sql.append("   AND T.FAULT_TYPE = "+DicConstant.GZLB_01+"");
    	sql.append("   AND EXISTS (SELECT 1\n" );
    	sql.append("        FROM PT_BA_SUPPLIER S\n" );
    	sql.append("         WHERE S.SUPPLIER_ID = T.OLD_SUPPLIER_ID\n" );
    	sql.append("           AND S.ORG_ID = "+user.getOrgId()+")");
    	return factory.update(sql.toString(), null);
    }
    
    /**
     * 获得数量
     * @param claimId
     * @return
     * @throws Exception
     */
    public QuerySet getSl(String claimId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT (SELECT COUNT(*) ZSL\n" );
    	sql.append("          FROM SE_BU_CLAIM_FAULT T\n" );
    	sql.append("         WHERE T.CLAIM_ID = "+claimId+") -\n" );
    	sql.append("       NVL((SELECT COUNT(P.FAULT_PART_ID) TGSL\n" );
    	sql.append("             FROM SE_BU_CLAIM            C,\n" );
    	sql.append("                  SE_BU_CLAIM_FAULT      F,\n" );
    	sql.append("                  SE_BU_CLAIM_FAULT_PART P\n" );
    	sql.append("            WHERE C.CLAIM_ID = F.CLAIM_ID\n" );
    	sql.append("              AND F.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
    	sql.append("              AND C.CLAIM_ID = "+claimId+"\n" );
    	sql.append("              AND P.FAULT_TYPE = "+DicConstant.GZLB_01+"\n" );
    	sql.append("              AND P.SUPPLIER_OPTION_STATUS = "+DicConstant.GYSYJZT_01+"\n" );
    	sql.append("            GROUP BY P.FAULT_PART_ID),\n" );
    	sql.append("           0) SL\n" );
    	sql.append("  FROM DUAL");
    	return factory.select(null, sql.toString());
    }
    
    /**
     * 审核通过
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateClaim(SeBuClaimVO vo)throws Exception{
    	return factory.update(vo);
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
    	sql.append("       D.SALE_COUNT\n" );
    	sql.append("  FROM PT_BU_REAL_SALE R, PT_BU_REAL_SALE_DTL D\n" );
    	sql.append(" WHERE R.SALE_ID = D.SALE_ID\n" );
    	sql.append("   AND R.CLAIM_ID = "+claimId+"");
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
     * 将实销出库单更新为已出库
     */
    public boolean updateRsSaleStatus(String claimId) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_REAL_SALE R\n" );
    	sql.append("   SET R.SALE_STATUS = "+DicConstant.SXDZT_02+"\n" );
    	sql.append(" WHERE R.CLAIM_ID = "+claimId+"\n" );
    	sql.append("   AND R.STATUS = "+DicConstant.YXBS_01+"");
    	return factory.update(sql.toString(), null);
    	
    }
}
