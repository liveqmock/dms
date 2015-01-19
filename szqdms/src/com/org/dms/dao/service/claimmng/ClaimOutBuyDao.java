package com.org.dms.dao.service.claimmng;
import java.sql.SQLException;
import java.util.Map;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuDealerStockVO;
import com.org.dms.vo.part.PtBuOutBuyVO;
import com.org.dms.vo.service.SeBuClaimCheckVO;
import com.org.dms.vo.service.SeBuClaimVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 索赔单查询DAO
 * @author Administrator
 *
 */
public class ClaimOutBuyDao extends BaseDAO
{
    //定义instance
    public  static final ClaimOutBuyDao getInstance(ActionContext atx)
    {
    	ClaimOutBuyDao dao = new ClaimOutBuyDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 外采单审核
     * @param page 分页
     * @param user 用户
     * @param conditions 前台条件
     * @throws SQLException 
     */
    public BaseResultSet claimOutSearch(PageManager page, User user, String conditions,String claimNo) throws SQLException{
    
    	String wheres = conditions;
    	if(null==claimNo||claimNo.equals("")){
    		wheres   +=" AND T.ORG_ID = B.ORG_ID\n"
       			 +"  AND T.PID = G.ORG_ID"
       			 + " AND G.ORG_ID = "+user.getOrgId()+""
       			 +"  AND B.STATUS="+DicConstant.YXBS_01+"\n"
       			 + " AND B.CLAIM_ID IN (SELECT SC.CLAIM_ID FROM SE_BU_CLAIM SC WHERE SC.CLAIM_ID = B.CLAIM_ID AND SC.CLAIM_STATUS = 301003)"
       			 + " AND B.BUY_STATUS="+DicConstant.SF_02+""
       			 +"  AND B.BUY_COUNT>0";
    	}else{
    		wheres   +=" AND T.ORG_ID = B.ORG_ID\n"
          			 +"  AND T.PID = G.ORG_ID"
          			 + " AND G.ORG_ID = "+user.getOrgId()+""
          			 +"  AND B.STATUS="+DicConstant.YXBS_01+"\n"
          			 + " AND B.CLAIM_ID IN (SELECT SC.CLAIM_ID FROM SE_BU_CLAIM SC WHERE SC.CLAIM_ID = B.CLAIM_ID AND SC.CLAIM_STATUS = 301003)"
          			 + " AND B.BUY_STATUS="+DicConstant.SF_02+""
          			 + " AND B.CLAIM_ID IN ( SELECT SC.CLAIM_ID FROM SE_BU_CLAIM SC WHERE SC.CLAIM_NO LIKE '%"+claimNo+"%')"
          			 +"  AND B.BUY_COUNT>0";
    	}
    	
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT B.BUY_ID,\n" );
    	sql.append("       B.BUY_NO,\n" );
    	sql.append("       B.CUSTOMER_NAME,\n" );
    	sql.append("       B.LINK_PHONE,\n" );
    	sql.append("       B.LINK_ADDR,\n" );
    	sql.append("       B.BUY_DATE,\n" );
    	sql.append("       B.BUY_COUNT,\n" );
    	sql.append("       B.BUY_AMOUNT,\n" );
    	sql.append("       B.REMARK,\n" );
    	sql.append("       B.BUY_STATUS,\n" );
    	sql.append("       B.STATUS,\n" );
    	sql.append("       B.ORG_ID,\n" );
    	sql.append("       B.CLAIM_ID,\n" );
    	sql.append("       (SELECT C.WORK_ID FROM SE_BU_CLAIM C WHERE C.CLAIM_ID = B.CLAIM_ID) WORK_ID,\n" );
    	sql.append("       (SELECT W.WORK_NO FROM SE_BU_CLAIM C,SE_BU_WORK_ORDER W WHERE W.WORK_ID = C.WORK_ID AND C.CLAIM_ID = B.CLAIM_ID) WORK_NO,\n" );
    	sql.append("       (SELECT C.CLAIM_NO\n" );
    	sql.append("          FROM SE_BU_CLAIM C\n" );
    	sql.append("         WHERE C.CLAIM_ID = B.CLAIM_ID)CLAIM_NO,\n" );
    	sql.append("       T.CODE,\n" );
    	sql.append("       T.ONAME\n" );
    	sql.append("  FROM PT_BU_OUT_BUY B, TM_ORG T, TM_ORG G");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
    	bs.setFieldDic("CLAIM_STATUS", "SPDZT");
    	bs.setFieldDic("STATUS", "YXBS");
    	bs.setFieldDic("BUY_STATUS", "SF");
        bs.setFieldOrgDeptSimpleName("ORG_NAME");
		bs.setFieldOrgDeptCode("ORG_CODE");
    	return bs;
    }
    /**
     * 外采单审核
     * @param page 分页
     * @param user 用户
     * @param conditions 前台条件
     * @throws SQLException 
     */
    public BaseResultSet claimOutSearchInfo(PageManager page, User user, String conditions,String claimNo) throws SQLException{
    	
    	String wheres = conditions;
    	if(null==claimNo||claimNo.equals("")){
    		wheres   +="AND T.ORG_ID = B.ORG_ID\n"
        			+"  AND T.PID = G.ORG_ID"
        			+ " AND G.ORG_ID = "+user.getOrgId()+""
        			+ " AND B.STATUS ="+DicConstant.YXBS_01+""
        			+"  AND B.BUY_COUNT>0";
    	}else{
    		wheres   +="AND T.ORG_ID = B.ORG_ID\n"
        			+"  AND T.PID = G.ORG_ID"
        			+ " AND G.ORG_ID = "+user.getOrgId()+""
        			+ " AND B.STATUS ="+DicConstant.YXBS_01+""
        			+ " AND B.CLAIM_ID IN ( SELECT SC.CLAIM_ID FROM SE_BU_CLAIM SC WHERE SC.CLAIM_NO LIKE '%"+claimNo+"%')"
        			+"  AND B.BUY_COUNT>0";
    	}
    	
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT B.BUY_ID,\n" );
    	sql.append("       B.BUY_NO,\n" );
    	sql.append("       B.CUSTOMER_NAME,\n" );
    	sql.append("       B.LINK_PHONE,\n" );
    	sql.append("       B.LINK_ADDR,\n" );
    	sql.append("       B.BUY_DATE,\n" );
    	sql.append("       B.BUY_COUNT,\n" );
    	sql.append("       B.BUY_AMOUNT,\n" );
    	sql.append("       B.REMARK,\n" );
    	sql.append("       B.BUY_STATUS,\n" );
    	sql.append("       B.STATUS,\n" );
    	sql.append("       B.ORG_ID,\n" );
    	sql.append("       B.CLAIM_ID,\n" );
    	sql.append("       (SELECT C.CLAIM_NO\n" );
    	sql.append("          FROM SE_BU_CLAIM C\n" );
    	sql.append("         WHERE C.CLAIM_ID = B.CLAIM_ID)CLAIM_NO,\n" );
    	sql.append("       T.CODE,\n" );
    	sql.append("       T.ONAME\n" );
    	sql.append("  FROM PT_BU_OUT_BUY B, TM_ORG T, TM_ORG G");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
    	bs.setFieldDic("CLAIM_STATUS", "SPDZT");
    	bs.setFieldOrgDeptSimpleName("ORG_NAME");
    	bs.setFieldOrgDeptCode("ORG_CODE");
    	bs.setFieldDic("STATUS", "YXBS");
    	bs.setFieldDic("BUY_STATUS", "SF");
    	return bs;
    }
    /**
     * 外采单审核
     * @param page 分页
     * @param user 用户
     * @param conditions 前台条件
     * @throws SQLException 
     */
    public BaseResultSet claimOutSearchInfoPart(PageManager page, User user, String conditions,String claimNo) throws SQLException{
    	
    	String wheres = conditions;
    	if(null==claimNo||claimNo.equals("")){
    		wheres   +="AND T.ORG_ID = B.ORG_ID\n"
    				+"  AND T.ORG_ID = DC.ORG_ID"
    				+ " AND DC.DC_ID = "+user.getOrgId()+""
    				+ " AND B.STATUS ="+DicConstant.YXBS_01+""
    				+"  AND B.BUY_COUNT>0";
    	}else{
    		wheres   +="AND T.ORG_ID = B.ORG_ID\n"
    				+"  AND T.ORG_ID = DC.ORG_ID"
    				+ " AND DC.DC_ID = "+user.getOrgId()+""
    				+ " AND B.STATUS ="+DicConstant.YXBS_01+""
    				+ " AND B.CLAIM_ID IN ( SELECT SC.CLAIM_ID FROM SE_BU_CLAIM SC WHERE SC.CLAIM_NO LIKE '%"+claimNo+"%')"
    				+"  AND B.BUY_COUNT>0";
    	}
    	
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT B.BUY_ID,\n" );
    	sql.append("       B.BUY_NO,\n" );
    	sql.append("       B.CUSTOMER_NAME,\n" );
    	sql.append("       B.LINK_PHONE,\n" );
    	sql.append("       B.LINK_ADDR,\n" );
    	sql.append("       B.BUY_DATE,\n" );
    	sql.append("       B.BUY_COUNT,\n" );
    	sql.append("       B.BUY_AMOUNT,\n" );
    	sql.append("       B.REMARK,\n" );
    	sql.append("       B.BUY_STATUS,\n" );
    	sql.append("       B.STATUS,\n" );
    	sql.append("       B.ORG_ID,\n" );
    	sql.append("       B.CLAIM_ID,\n" );
    	sql.append("       (SELECT C.CLAIM_NO FROM SE_BU_CLAIM C WHERE C.CLAIM_ID = B.CLAIM_ID) CLAIM_NO,\n" );
    	sql.append("       T.CODE,\n" );
    	sql.append("       T.ONAME\n" );
    	sql.append("  FROM PT_BU_OUT_BUY B, TM_ORG T,  PT_BA_SERVICE_DC DC");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
    	bs.setFieldDic("CLAIM_STATUS", "SPDZT");
    	bs.setFieldOrgDeptSimpleName("ORG_NAME");
    	bs.setFieldOrgDeptCode("ORG_CODE");
    	bs.setFieldDic("STATUS", "YXBS");
    	bs.setFieldDic("BUY_STATUS", "SF");
    	return bs;
    }
    /**
     * 外采单审核
     * @param page 分页
     * @param user 用户
     * @param conditions 前台条件
     * @throws SQLException 
     */
    public BaseResultSet claimDealerOutSearchInfo(PageManager page, User user, String conditions,String claimNo) throws SQLException{
    	
    	String wheres = conditions;
    	if(null==claimNo||claimNo.equals("")){
        	wheres   +="AND B.ORG_ID = "+user.getOrgId()+""
        			+"  AND B.BUY_COUNT>0"
        			+ " AND B.STATUS ="+DicConstant.YXBS_01+"";
    	}else{
        	wheres   +="AND B.ORG_ID = "+user.getOrgId()+""
        			+"  AND B.BUY_COUNT>0"
        			+ " AND B.STATUS ="+DicConstant.YXBS_01+""
        			+ " AND B.CLAIM_ID IN ( SELECT SC.CLAIM_ID FROM SE_BU_CLAIM SC WHERE SC.CLAIM_NO LIKE '%"+claimNo+"%')";
    	}

    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT B.BUY_ID,\n" );
    	sql.append("       B.BUY_NO,\n" );
    	sql.append("       B.CUSTOMER_NAME,\n" );
    	sql.append("       B.LINK_PHONE,\n" );
    	sql.append("       B.LINK_ADDR,\n" );
    	sql.append("       B.BUY_DATE,\n" );
    	sql.append("       B.BUY_COUNT,\n" );
    	sql.append("       B.BUY_AMOUNT,\n" );
    	sql.append("       B.REMARK,\n" );
    	sql.append("       B.BUY_STATUS,\n" );
    	sql.append("       B.STATUS,\n" );
    	sql.append("       B.ORG_ID,\n" );
    	sql.append("       B.CLAIM_ID,\n" );
    	sql.append("       (SELECT C.CLAIM_NO\n" );
    	sql.append("          FROM SE_BU_CLAIM C\n" );
    	sql.append("         WHERE C.CLAIM_ID = B.CLAIM_ID)CLAIM_NO\n" );
    	sql.append("  FROM PT_BU_OUT_BUY B");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
    	bs.setFieldDic("STATUS", "YXBS");
    	bs.setFieldDic("BUY_STATUS", "SF");
    	return bs;
    }
    /**
     * 外采单查询（厂端）
     * @param page 分页
     * @param user 用户
     * @param conditions 前台条件
     * @throws SQLException 
     */
    public BaseResultSet claimOemOutSearchInfo(PageManager page, User user, String conditions,String claimNo) throws SQLException{
    	
    	String wheres = conditions;
    	if(null==claimNo||claimNo.equals("")){
    		wheres   +=" AND T.ORG_ID = B.ORG_ID\n"
       			 +"  AND T.PID = G.ORG_ID"
       			 + " AND B.STATUS ="+DicConstant.YXBS_01+""
       			 +"  AND B.BUY_COUNT>0";
    	}else{
    	wheres   +=" AND T.ORG_ID = B.ORG_ID\n"
    			 +"  AND T.PID = G.ORG_ID"
    			 + " AND B.STATUS ="+DicConstant.YXBS_01+""
    			 + " AND B.CLAIM_ID IN ( SELECT SC.CLAIM_ID FROM SE_BU_CLAIM SC WHERE SC.CLAIM_NO LIKE '%"+claimNo+"%')"
    			 +"  AND B.BUY_COUNT>0";
    	}
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT B.BUY_ID,\n" );
    	sql.append("       B.BUY_NO,\n" );
    	sql.append("       B.CUSTOMER_NAME,\n" );
    	sql.append("       B.LINK_PHONE,\n" );
    	sql.append("       B.LINK_ADDR,\n" );
    	sql.append("       B.BUY_DATE,\n" );
    	sql.append("       B.BUY_COUNT,\n" );
    	sql.append("       B.BUY_AMOUNT,\n" );
    	sql.append("       B.REMARK,\n" );
    	sql.append("       B.BUY_STATUS,\n" );
    	sql.append("       B.STATUS,\n" );
    	sql.append("       B.ORG_ID,\n" );
    	sql.append("       B.CLAIM_ID,\n" );
    	sql.append("       (SELECT C.CLAIM_NO\n" );
    	sql.append("          FROM SE_BU_CLAIM C\n" );
    	sql.append("         WHERE C.CLAIM_ID = B.CLAIM_ID) CLAIM_NO,\n" );
    	sql.append("       T.CODE,\n" );
    	sql.append("       T.ONAME,\n" );
    	sql.append("       G.CODE          BSCCODE,\n" );
    	sql.append("       G.ONAME         BSCNAME\n" );
    	sql.append("  FROM PT_BU_OUT_BUY B, TM_ORG T, TM_ORG G");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
    	bs.setFieldDic("CLAIM_STATUS", "SPDZT");
    	bs.setFieldOrgDeptSimpleName("ORG_NAME");
    	bs.setFieldOrgDeptCode("ORG_CODE");
    	bs.setFieldDic("STATUS", "YXBS");
    	bs.setFieldDic("BUY_STATUS", "SF");
    	return bs;
    }
    /**
     * 外采件查询
     * @param page 分页
     * @param user 用户
     * @param conditions 前台条件
     * @throws SQLException 
     */
    public BaseResultSet searchOutParts(PageManager page, User user, String conditions,String buyId) throws SQLException{
    	
    	String wheres = conditions;
    	wheres   +=" AND B.BUY_ID = D.BUY_ID\n"
    			+ "  AND B.BUY_ID ="+buyId+"\n"
    			 + " ORDER BY D.DTL_ID";
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT D.DTL_ID,\n" );
    	sql.append("       D.BUY_ID,\n" );
    	sql.append("       D.PART_ID,\n" );
    	sql.append("       D.PART_CODE,\n" );
    	sql.append("       D.PART_NAME,\n" );
    	sql.append("       D.UNIT,\n" );
    	sql.append("       D.SUPPLIER_ID,\n" );
    	sql.append("       D.SUPPLIER_CODE,\n" );
    	sql.append("       D.SUPPLIER_NAME,\n" );
    	sql.append("       D.BUY_PRICE,\n" );
    	sql.append("       D.BUY_COUNT,\n" );
    	sql.append("       D.AMOUNT,\n" );
    	sql.append("       D.CREATE_USER,\n" );
    	sql.append("       D.CREATE_TIME\n" );
    	sql.append("  FROM PT_BU_OUT_BUY B, PT_BU_OUT_BUY_DTL D");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("REJECT_DATE", "yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDic("UNIT", "JLDW");
    	return bs;
    }
    public QuerySet queryClaimId(String buyId)throws Exception{
 	   StringBuffer sql= new StringBuffer();
 	   sql.append("SELECT T.CLAIM_ID FROM PT_BU_OUT_BUY T WHERE T.BUY_ID = "+buyId+"");
 	   return factory.select(null, sql.toString());
    }
    public QuerySet queryOpUser(String claimId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT C.OPERATE_USER FROM SE_BU_CLAIM C WHERE C.CLAIM_ID =  "+claimId+"");
    	return factory.select(null, sql.toString());
    }
    public QuerySet queryClaimType(String claimId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT C.CLAIM_TYPE, C.CLAIM_ID\n" );
    	sql.append("  FROM SE_BU_CLAIM C\n" );
    	sql.append(" WHERE EXISTS (SELECT 1\n" );
    	sql.append("          FROM SE_BU_CLAIM_FAULT_PART SP, PT_BA_INFO P\n" );
    	sql.append("         WHERE C.CLAIM_ID = SP.CLAIM_ID\n" );
    	sql.append("           AND SP.OLD_PART_ID = P.PART_ID\n" );
    	sql.append("           AND P.IF_RETURN = 100101\n" );
    	sql.append("           AND SP.MEASURES = 300602)\n" );
    	sql.append("   AND C.CLAIM_ID = "+claimId+"");

    	return factory.select(null, sql.toString());
    }
    public QuerySet queryClaimType1(String claimId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT C.CLAIM_TYPE, C.CLAIM_ID\n" );
    	sql.append("  FROM SE_BU_CLAIM C\n" );
    	sql.append(" WHERE C.CLAIM_ID = "+claimId+"\n" );
    	return factory.select(null, sql.toString());
    }
    /**
     * 修改外采单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateBuyStatus(PtBuOutBuyVO vo) throws Exception {
 	   return factory.update(vo);
    }
    /**
     * 修改索赔单状态
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateClaim(SeBuClaimVO vo) throws Exception {
    	return factory.update(vo);
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
    /**
     * 更新不需要回运旧件的审核通过数量
     * @param claimId
     * @return
     * @throws Exception
     */
    public boolean updateOught(String claimId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE SE_BU_CLAIM_FAULT_PART P\n" );
    	sql.append("   SET P.OUGHT_COUNT = P.OLD_PART_COUNT\n" );
    	sql.append(" WHERE P.CLAIM_ID = "+claimId+"\n" );
    	sql.append("   AND EXISTS (SELECT 1\n" );
    	sql.append("          FROM PT_BA_INFO I\n" );
    	sql.append("         WHERE P.OLD_PART_ID = I.PART_ID\n" );
    	sql.append("           AND I.IF_RETURN = 100102)");
    	return factory.update(sql.toString(), null);
    }
    /**
     * 更新维修和加装的审核数量
     * @param claimId
     * @return
     * @throws Exception
     */
    public boolean updateOught1(String claimId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE SE_BU_CLAIM_FAULT_PART P\n" );
    	sql.append("   SET P.OUGHT_COUNT = DECODE(P.OLD_PART_COUNT,\n" );
    	sql.append("                              '',\n" );
    	sql.append("                              P.NEW_PART_COUNT,\n" );
    	sql.append("                              P.OLD_PART_COUNT)\n" );
    	sql.append(" WHERE P.CLAIM_ID = "+claimId+"\n" );
    	sql.append("   AND P.MEASURES IN (300601, 300603)");
    	return factory.update(sql.toString(), null);
    }
    public void insetStockDtl(Integer saleCount,User user,String orgId,String saleId,String url,String nPartId) throws Exception
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
 	   sql.append("           '-"+saleCount+"',\n" );//占用
 	   sql.append("           '-"+saleCount+"',\n" );//总数
 	   sql.append("           '',\n" );//可用
 	   sql.append("           '"+user.getAccount()+"',\n" );
 	   sql.append("           SYSDATE,\n" );
 	   sql.append("           '"+nPartId+"',\n" );
 	   sql.append("           '"+orgId+"')");

 	   factory.update(sql.toString(), null);
    }
    public void insetStockDtl1(Integer saleCount,User user,String orgId,String saleId,String url,String nPartId) throws Exception
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
    	sql.append("           '-"+saleCount+"',\n" );//占用
    	sql.append("           '',\n" );//总数
    	sql.append("           '"+saleCount+"',\n" );//可用
    	sql.append("           '"+user.getAccount()+"',\n" );
    	sql.append("           SYSDATE,\n" );
    	sql.append("           '"+nPartId+"',\n" );
    	sql.append("           '"+orgId+"')");
    	
    	factory.update(sql.toString(), null);
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
    public QuerySet download(String conditions,User user,String claimNo) throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT B.BUY_ID,\n" );
    	sql.append("       B.BUY_NO,\n" );
    	sql.append("       B.CUSTOMER_NAME,\n" );
    	sql.append("       B.LINK_PHONE,\n" );
    	sql.append("       B.LINK_ADDR,\n" );
    	sql.append("       B.BUY_DATE,\n" );
    	sql.append("       B.BUY_COUNT,\n" );
    	sql.append("       B.BUY_AMOUNT,\n" );
    	sql.append("       B.REMARK,\n" );
    	sql.append("       (SELECT T.DIC_VALUE FROM DIC_TREE T WHERE T.ID = B.BUY_STATUS)BUY_STATUS,\n" );
    	sql.append("       (SELECT T.DIC_VALUE FROM DIC_TREE T WHERE T.ID = B.STATUS)STATUS,\n" );
    	sql.append("       B.ORG_ID,\n" );
    	sql.append("       (SELECT G.ONAME FROM TM_ORG G WHERE G.ORG_ID = B.ORG_ID) ONAME,\n" );
    	sql.append("       (SELECT G.CODE FROM TM_ORG G WHERE G.ORG_ID = B.ORG_ID) CODE,\n" );
    	sql.append("       B.CLAIM_ID,\n" );
    	sql.append("       (SELECT C.CLAIM_NO FROM SE_BU_CLAIM C WHERE C.CLAIM_ID = B.CLAIM_ID) CLAIM_NO,\n" );
    	sql.append("       T.CODE,\n" );
    	sql.append("       T.ONAME\n" );
    	sql.append("  FROM PT_BU_OUT_BUY B, TM_ORG T, PT_BA_SERVICE_DC DC\n" );
    	sql.append(" WHERE "+conditions+"\n" );
    	sql.append("   AND T.ORG_ID = B.ORG_ID\n" );
    	sql.append("   AND T.ORG_ID = DC.ORG_ID\n" );
    	sql.append("   AND DC.DC_ID = "+user.getOrgId()+"\n" );
    	sql.append("   AND B.STATUS = 100201\n" );
    	sql.append("   AND B.BUY_COUNT > 0");
    	if(claimNo==null||claimNo.equals("")){
    	}else{
    		sql.append("  AND B.CLAIM_ID IN (SELECT C.CLAIM_ID FROM SE_BU_CLAIM C WHERE C.CLAIM_NO LIKE '%"+claimNo+"%')");
    	}
    	sql.append(" ORDER BY B.BUY_ID");
    	
    	return factory.select(null, sql.toString());
    }
    public QuerySet download1(String conditions,String claimNo) throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT B.BUY_ID,\n" );
    	sql.append("       B.BUY_NO,\n" );
    	sql.append("       B.CUSTOMER_NAME,\n" );
    	sql.append("       B.LINK_PHONE,\n" );
    	sql.append("       B.LINK_ADDR,\n" );
    	sql.append("       B.BUY_DATE,\n" );
    	sql.append("       B.BUY_COUNT,\n" );
    	sql.append("       B.BUY_AMOUNT,\n" );
    	sql.append("       B.REMARK,\n" );
    	sql.append("       (SELECT T.DIC_VALUE FROM DIC_TREE T WHERE T.ID = B.STATUS)STATUS,\n" );
    	sql.append("       (SELECT T.DIC_VALUE FROM DIC_TREE T WHERE T.ID = B.BUY_STATUS)BUY_STATUS,\n" );
    	sql.append("       B.ORG_ID,\n" );
    	sql.append("       B.CLAIM_ID,\n" );
    	sql.append("       (SELECT C.CLAIM_NO FROM SE_BU_CLAIM C WHERE C.CLAIM_ID = B.CLAIM_ID) CLAIM_NO,\n" );
    	sql.append("       T.CODE,\n" );
    	sql.append("       T.ONAME,\n" );
    	sql.append("       G.CODE BSCCODE,\n" );
    	sql.append("       G.ONAME BSCNAME\n" );
    	sql.append("  FROM PT_BU_OUT_BUY B, TM_ORG T, TM_ORG G\n" );
    	sql.append(" WHERE "+conditions+"\n" );
    	sql.append("   AND T.ORG_ID = B.ORG_ID\n" );
    	sql.append("   AND T.PID = G.ORG_ID\n" );
    	sql.append("   AND B.STATUS = 100201\n" );
    	sql.append("   AND B.BUY_COUNT > 0");
    	if(claimNo==null||claimNo.equals("")){
    	}else{
    		sql.append("  AND B.CLAIM_ID IN (SELECT C.CLAIM_ID FROM SE_BU_CLAIM C WHERE C.CLAIM_NO LIKE '%"+claimNo+"%')");
    	}
    	sql.append(" ORDER BY B.BUY_ID");
    	
    	return factory.select(null, sql.toString());
    }
    public QuerySet download2(String conditions,String claimNo) throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT B.BUY_NO,\n" );
    	sql.append("       (SELECT C.CLAIM_NO FROM SE_BU_CLAIM C WHERE C.CLAIM_ID = B.CLAIM_ID) CLAIM_NO,\n" );
    	sql.append("       D.PART_CODE,\n" );
    	sql.append("       D.PART_NAME,\n" );
    	sql.append("       (SELECT T.DIC_VALUE FROM DIC_TREE T WHERE T.ID = D.UNIT) UNIT,\n" );
    	sql.append("       D.SUPPLIER_CODE,\n" );
    	sql.append("       D.SUPPLIER_NAME,\n" );
    	sql.append("       D.BUY_PRICE,\n" );
    	sql.append("       D.BUY_COUNT,\n" );
    	sql.append("       D.AMOUNT\n" );
    	sql.append("  FROM PT_BU_OUT_BUY B, TM_ORG T, TM_ORG G, PT_BU_OUT_BUY_DTL D\n" );
    	sql.append(" WHERE "+conditions+"\n" );
    	sql.append("   AND D.BUY_ID = B.BUY_ID\n" );
    	sql.append("   AND T.ORG_ID = B.ORG_ID\n" );
    	sql.append("   AND T.PID = G.ORG_ID\n" );
    	sql.append("   AND B.STATUS = 100201\n" );
    	sql.append("   AND B.BUY_COUNT > 0\n" );
    	if(claimNo==null||claimNo.equals("")){
    	}else{
    		sql.append("  AND B.CLAIM_ID IN (SELECT C.CLAIM_ID FROM SE_BU_CLAIM C WHERE C.CLAIM_NO LIKE '%"+claimNo+"%')");
    	}
    	sql.append(" ORDER BY B.BUY_ID");
    	
    	return factory.select(null, sql.toString());
    }
    public QuerySet download3(String conditions,User user,String claimNo) throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT B.BUY_NO,\n" );
    	sql.append("       (SELECT C.CLAIM_NO FROM SE_BU_CLAIM C WHERE C.CLAIM_ID = B.CLAIM_ID) CLAIM_NO,\n" );
    	sql.append("       D.PART_CODE,\n" );
    	sql.append("       D.PART_NAME,\n" );
    	sql.append("       (SELECT T.DIC_VALUE FROM DIC_TREE T WHERE T.ID = D.UNIT) UNIT,\n" );
    	sql.append("       D.SUPPLIER_CODE,\n" );
    	sql.append("       D.SUPPLIER_NAME,\n" );
    	sql.append("       D.BUY_PRICE,\n" );
    	sql.append("       D.BUY_COUNT,\n" );
    	sql.append("       D.AMOUNT\n" );
    	sql.append("  FROM PT_BU_OUT_BUY B, TM_ORG T, PT_BA_SERVICE_DC DC,PT_BU_OUT_BUY_DTL D\n" );
    	sql.append(" WHERE "+conditions+"\n" );
    	sql.append("   AND T.ORG_ID = B.ORG_ID\n" );
    	sql.append("   AND D.BUY_ID = B.BUY_ID\n" );
    	sql.append("   AND T.ORG_ID = DC.ORG_ID\n" );
    	sql.append("   AND DC.DC_ID = "+user.getOrgId()+"\n" );
    	sql.append("   AND B.STATUS = 100201\n" );
    	sql.append("   AND B.BUY_COUNT > 0");
    	if(claimNo==null||claimNo.equals("")){
    	}else{
    		sql.append("  AND B.CLAIM_ID IN (SELECT C.CLAIM_ID FROM SE_BU_CLAIM C WHERE C.CLAIM_NO LIKE '%"+claimNo+"%')");
    	}
    	sql.append(" ORDER BY B.BUY_ID");
    	
    	return factory.select(null, sql.toString());
    }
}
