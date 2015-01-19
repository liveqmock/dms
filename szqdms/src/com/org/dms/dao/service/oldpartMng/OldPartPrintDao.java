package com.org.dms.dao.service.oldpartMng;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.DBFactory;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 旧件标签、箱号打印 DAO
 * @author zts
 *
 */
public class OldPartPrintDao extends BaseDAO{

	 //定义instance
    public  static final OldPartPrintDao getInstance(ActionContext atx)
    {
    	OldPartPrintDao dao = new OldPartPrintDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    /**
     * 查询打印的旧件
     * @param page
     * @param conditions
     * @return
     * @throws SQLException
     */
    public BaseResultSet oldPartPrintSearch(PageManager page, String conditions,User user,String ifPrint) throws SQLException{
    	String wheres = conditions;
    	wheres +=" AND C.CLAIM_ID = F.CLAIM_ID\n" +
    			 " AND F.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" + 
    			 " AND P.OLD_SUPPLIER_ID = S.SUPPLIER_ID\n"+
    			 " AND C.CLAIM_STATUS="+DicConstant.SPDZT_05+" \n"+//索赔单人工审核通过
    			 " AND P.MEASURES="+DicConstant.CLFS_02+" \n"+// 处理方式
				 " AND EXISTS (SELECT 1\n" +
				 "       FROM PT_BA_INFO I\n" + 
				 "      WHERE I.PART_ID = P.OLD_PART_ID\n" + 
				 "        AND I.IF_RETURN = 100101) \n"+     //配件是否需要回运
    			 " AND C.ORG_ID="+user.getOrgId()+" \n";
    	
    	if(DicConstant.SF_01.toString().equals(ifPrint)){
    		wheres += " AND P.IF_PRINT = "+DicConstant.SF_01+"";
    	}
    	if(DicConstant.SF_02.toString().equals(ifPrint)){
    		wheres +=" AND NVL(P.IF_PRINT,0) <> "+DicConstant.SF_01+" ";
    	}
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT C.CLAIM_ID,\n" );
    	sql.append("       C.CLAIM_NO, --索赔单号\n" );
    	sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID =C.CLAIM_TYPE)CLAIM_TYPE, --索赔类型\n" );
    	sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID = (SELECT AU.AUTHOR_TYPE FROM SE_BU_PRE_AUTHOR AU WHERE AU.AUTHOR_ID = C.PRE_AUTHOR_ID))AUTHOR_TYPE, --预授权类型\n" );
    	sql.append("       C.VIN, --VIN\n" );
    	sql.append("       TO_CHAR(C.FAULT_DATE,'yyyy-MM-dd') FAULT_DATE, --故障日期\n" );
    	sql.append("       C.VEHICLE_ID,\n" );
    	sql.append("       C.MILEAGE, --行驶里程\n" );
    	sql.append("       C.REMARKS, --备注\n" );
    	sql.append("       F.FAULT_CODE, --故障代码\n" );
    	sql.append("       F.FAULT_NAME, --故障名称\n" );
    	sql.append("       P.FAULT_PART_ID, --故障零件ID\n" );
    	sql.append("       P.IF_PRINT, --是否已打印\n" );
    	sql.append("       P.OLD_PART_ID, --旧件ID\n" );
    	sql.append("       P.OLD_PART_CODE, --旧件代码\n" );
    	sql.append("	   (select A.SL from\n" );
    	sql.append("       (SELECT COUNT(1) SL,SP.OLD_PART_ID,MV.VEHICLE_ID\n" );
    	sql.append("        FROM MAIN_VEHICLE MV, SE_BU_CLAIM SC, SE_BU_CLAIM_FAULT_PART SP\n" );
    	sql.append("       WHERE SC.VEHICLE_ID = MV.VEHICLE_ID\n" );
    	sql.append("         AND SP.CLAIM_ID = SC.CLAIM_ID\n" );
    	sql.append("       GROUP BY  MV.VEHICLE_ID,SP.OLD_PART_ID) A\n" );
    	sql.append("       WHERE A.OLD_PART_ID = P.OLD_PART_ID\n" );
    	sql.append("        AND A.VEHICLE_ID = C.VEHICLE_ID  ) WXCS,");
    	sql.append("       P.OLD_PART_NAME, --旧件名称\n" );
    	sql.append("       S.SUPPLIER_NAME, --供应商NAME\n" );
    	sql.append("       S.SUPPLIER_CODE, --供应商CODE\n" );
    	sql.append("       P.CLAIM_COSTS, --结算材料费\n" );
    	sql.append("       (nvl(P.REPAY_UPRICE, 0) * nvl(P.OLD_PART_COUNT, 0)) REPAY_COSTS, --追偿材料费\n" );
    	sql.append("       P.OLD_PART_COUNT, --数量\n" );
    	sql.append("       P.FAULT_REASON, -- 故障原因\n" );
    	sql.append("       P.FIRST_PART_ID, -- 主损件\n");
    	sql.append("       (SELECT O.SNAME FROM TM_ORG O WHERE O.ORG_ID="+user.getOrgId()+") AS ORG_NAME, -- 服务商名称\n" );
    	sql.append("       (SELECT O.CODE FROM TM_ORG O WHERE O.ORG_ID="+user.getOrgId()+") AS ORG_CODE, -- 服务商代码\n" );
    	sql.append("(SELECT R.SUPPLIER_NAME\n" );
    	sql.append("         FROM PT_BA_SUPPLIER R\n" );
    	sql.append("        WHERE R.SUPPLIER_ID =\n" );
    	sql.append("              (DECODE(P.FAULT_TYPE,\n" );
    	sql.append("                      "+DicConstant.GZLB_01+",\n" );
    	sql.append("                      P.OLD_SUPPLIER_ID,\n" );
    	sql.append("                      (SELECT P1.OLD_SUPPLIER_ID\n" );
    	sql.append("                         FROM SE_BU_CLAIM_FAULT_PART P1\n" );
    	sql.append("                        WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
    	sql.append("                          AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
    	sql.append("                          AND P1.FAULT_TYPE = "+DicConstant.GZLB_01+")))) AS MAIN_SUPP_NAME, --责任厂商名称\n" );
    	sql.append("      (SELECT R.SUPPLIER_CODE\n" );
    	sql.append("         FROM PT_BA_SUPPLIER R\n" );
    	sql.append("        WHERE R.SUPPLIER_ID =\n" );
    	sql.append("              (DECODE(P.FAULT_TYPE,\n" );
    	sql.append("                      "+DicConstant.GZLB_01+",\n" );
    	sql.append("                      P.OLD_SUPPLIER_ID,\n" );
    	sql.append("                      (SELECT P1.OLD_SUPPLIER_ID\n" );
    	sql.append("                         FROM SE_BU_CLAIM_FAULT_PART P1\n" );
    	sql.append("                        WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
    	sql.append("                          AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
    	sql.append("                          AND P1.FAULT_TYPE = "+DicConstant.GZLB_01+")))) AS MAIN_SUPP_CODE --责任厂商CODE\n");
    	sql.append("  FROM SE_BU_CLAIM            C, --索赔单\n" );
    	sql.append("       SE_BU_CLAIM_FAULT      F,--故障表\n" );
    	sql.append("       SE_BU_CLAIM_FAULT_PART P, --配件\n" );
    	sql.append("       PT_BA_SUPPLIER         S--供应商\n" );
        bs=factory.select(sql.toString(), page);
        bs.setFieldDic("IF_PRINT","SF");
    	return bs;
    }
    /**
     * 查询应返的旧件
     * @param page
     * @param conditions
     * @return
     * @throws SQLException
     */
    public BaseResultSet oldPartOemSearch(PageManager page, String conditions,User user,String rorgCode) throws SQLException{
    	String wheres = conditions;
    	wheres +=" AND C.CLAIM_ID = F.CLAIM_ID\n" +
    			"  AND F.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n";
		if("0"==rorgCode||rorgCode.equals("0")){
    	}else{
    	wheres += "AND SR.D_ORGID = C.ORG_ID\n"
    			+ "AND SR.R_ORGCODE = '"+rorgCode+"'\n";
    	};
    	wheres +=" AND P.OLD_SUPPLIER_ID = S.SUPPLIER_ID\n"+
    			"  AND C.CLAIM_STATUS="+DicConstant.SPDZT_05+" \n"+//索赔单人工审核通过
    			"  AND P.MEASURES="+DicConstant.CLFS_02+" \n"+// 处理方式
    			"  AND EXISTS (SELECT 1\n" +
    			"       FROM PT_BA_INFO I\n" + 
    			"      WHERE I.PART_ID = P.OLD_PART_ID\n" + 
    			"        AND I.IF_RETURN = 100101) \n";    //配件是否需要回运
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT C.CLAIM_ID,\n" );
    	sql.append("       C.CLAIM_NO, --索赔单号\n" );
    	sql.append("       C.CLAIM_TYPE, --索赔类型\n" );
    	sql.append("       C.VIN, --VIN\n" );
    	sql.append("       C.VEHICLE_ID,\n" );
    	sql.append("       C.MILEAGE, --行驶里程\n" );
    	sql.append("       C.REMARKS, --备注\n" );
    	sql.append("       F.FAULT_CODE, --故障代码\n" );
    	sql.append("       F.FAULT_NAME, --故障名称\n" );
    	sql.append("       P.FAULT_PART_ID, --故障零件ID\n" );
    	sql.append("       P.IF_PRINT, --是否已打印\n" );
    	sql.append("       P.OLD_PART_ID, --旧件ID\n" );
    	sql.append("       P.OLD_PART_CODE, --旧件代码\n" );
    	sql.append("(select A.SL from\n" );
    	sql.append("       (SELECT COUNT(1) SL,SP.OLD_PART_ID,MV.VEHICLE_ID\n" );
    	sql.append("        FROM MAIN_VEHICLE MV, SE_BU_CLAIM SC, SE_BU_CLAIM_FAULT_PART SP\n" );
    	sql.append("       WHERE SC.VEHICLE_ID = MV.VEHICLE_ID\n" );
    	sql.append("         AND SP.CLAIM_ID = SC.CLAIM_ID\n" );
    	sql.append("       GROUP BY  MV.VEHICLE_ID,SP.OLD_PART_ID) A\n" );
    	sql.append("       WHERE A.OLD_PART_ID = P.OLD_PART_ID\n" );
    	sql.append("        AND A.VEHICLE_ID = C.VEHICLE_ID  ) WXCS,\n" );
    	sql.append("        P.OLD_PART_NAME, --旧件名称");
    	sql.append("       S.SUPPLIER_NAME, --供应商NAME\n" );
    	sql.append("       S.SUPPLIER_CODE, --供应商CODE\n" );
    	sql.append("       P.CLAIM_COSTS, --结算材料费\n" );
    	sql.append("       (nvl(P.REPAY_UPRICE, 0) * nvl(P.OLD_PART_COUNT, 0)) REPAY_COSTS, --追偿材料费\n" );
    	sql.append("       P.OLD_PART_COUNT, --数量\n" );
    	sql.append("       P.FAULT_REASON, -- 故障原因\n" );
    	sql.append("       P.FIRST_PART_ID, -- 主损件\n" );
    	sql.append("(SELECT R.SUPPLIER_NAME\n" );
    	sql.append("         FROM PT_BA_SUPPLIER R\n" );
    	sql.append("        WHERE R.SUPPLIER_ID =\n" );
    	sql.append("              (DECODE(P.FAULT_TYPE,\n" );
    	sql.append("                      301601,\n" );
    	sql.append("                      P.OLD_SUPPLIER_ID,\n" );
    	sql.append("                      (SELECT P1.OLD_SUPPLIER_ID\n" );
    	sql.append("                         FROM SE_BU_CLAIM_FAULT_PART P1\n" );
    	sql.append("                        WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
    	sql.append("                          AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
    	sql.append("                          AND P1.FAULT_TYPE = 301601)))) AS MAIN_SUPP_NAME, --责任厂商名称\n" );
    	sql.append("      (SELECT R.SUPPLIER_CODE\n" );
    	sql.append("         FROM PT_BA_SUPPLIER R\n" );
    	sql.append("        WHERE R.SUPPLIER_ID =\n" );
    	sql.append("              (DECODE(P.FAULT_TYPE,\n" );
    	sql.append("                      301601,\n" );
    	sql.append("                      P.OLD_SUPPLIER_ID,\n" );
    	sql.append("                      (SELECT P1.OLD_SUPPLIER_ID\n" );
    	sql.append("                         FROM SE_BU_CLAIM_FAULT_PART P1\n" );
    	sql.append("                        WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
    	sql.append("                          AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
    	sql.append("                          AND P1.FAULT_TYPE = 301601)))) AS MAIN_SUPP_CODE --责任厂商CODE\n" );
    	sql.append("  FROM SE_BU_CLAIM            C, --索赔单\n" );
    	sql.append("       SE_BU_CLAIM_FAULT      F,--故障表\n" );
    	sql.append("       SE_BU_CLAIM_FAULT_PART P, --配件\n" );
    	sql.append("       PT_BA_SUPPLIER         S--供应商\n");
    	if("0"==rorgCode||rorgCode.equals("0")){
    	}else{
    		sql.append("       ,SE_BA_RETURN_DEALER_RELATION SR--旧件集中点与服务商关系表\n");
    	}
    	bs=factory.select(sql.toString(), page);
    	return bs;
    }
    
    
    /**
     * 
     * @param claimId 索赔单ID
     * @param faultPartId 配件ID
     * @return
     * @throws Exception
     */
    public QuerySet queryPartBarCode(DBFactory fac,String claimId,String faultPartId,User user) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT C.CLAIM_ID,\n" );
    	sql.append("       C.CLAIM_NO, --索赔单号\n" );
    	sql.append("       C.CLAIM_TYPE, --索赔类型\n" );
    	sql.append("       C.VIN, --VIN\n" );
    	sql.append("       C.MILEAGE, --行驶里程\n" );
    	sql.append("	   F.FAULT_CODE,--故障代码\n" );
    	sql.append("       F.FAULT_NAME,--故障名称\n");
    	sql.append("       P.FAULT_PART_ID, --故障零件ID\n" );
    	sql.append("       P.OLD_PART_ID, --旧件ID\n" );
    	sql.append("       P.OLD_PART_CODE, --旧件代码\n" );
    	sql.append("       P.OLD_PART_NAME, --旧件名称\n" );
    	sql.append("       S.SUPPLIER_NAME,--供应商NAME\n" );
    	sql.append("       S.SUPPLIER_CODE,--供应商CODE\n" );
    	sql.append("       P.CLAIM_COSTS, --索赔材料费\n" );
    	sql.append("       P.OLD_PART_COUNT, --数量\n" );
    	sql.append("       P.FAULT_REASON, -- 故障原因\n" );
    	sql.append("       P.FIRST_PART_ID, -- 主损件\n" );
    	sql.append("       (SELECT O.CODE FROM TM_ORG O WHERE O.ORG_ID="+user.getOrgId()+") as ORG_CODE, -- 服务商名称\n" );
    	sql.append("       (SELECT O.SNAME FROM TM_ORG O WHERE O.ORG_ID="+user.getOrgId()+") as ORG_NAME,-- 服务商代码\n" );
    	sql.append("(SELECT R.SUPPLIER_NAME\n" );
    	sql.append("         FROM PT_BA_SUPPLIER R\n" );
    	sql.append("        WHERE R.SUPPLIER_ID =\n" );
    	sql.append("              (DECODE(P.FAULT_TYPE,\n" );
    	sql.append("                      "+DicConstant.GZLB_01+",\n" );
    	sql.append("                      P.OLD_SUPPLIER_ID,\n" );
    	sql.append("                      (SELECT P1.OLD_SUPPLIER_ID\n" );
    	sql.append("                         FROM SE_BU_CLAIM_FAULT_PART P1\n" );
    	sql.append("                        WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
    	sql.append("                          AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
    	sql.append("                          AND P1.FAULT_TYPE = "+DicConstant.GZLB_01+")))) AS MAIN_SUPP_NAME, --责任厂商名称\n" );
    	sql.append("      (SELECT R.SUPPLIER_CODE\n" );
    	sql.append("         FROM PT_BA_SUPPLIER R\n" );
    	sql.append("        WHERE R.SUPPLIER_ID =\n" );
    	sql.append("              (DECODE(P.FAULT_TYPE,\n" );
    	sql.append("                      "+DicConstant.GZLB_01+",\n" );
    	sql.append("                      P.OLD_SUPPLIER_ID,\n" );
    	sql.append("                      (SELECT P1.OLD_SUPPLIER_ID\n" );
    	sql.append("                         FROM SE_BU_CLAIM_FAULT_PART P1\n" );
    	sql.append("                        WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
    	sql.append("                          AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
    	sql.append("                          AND P1.FAULT_TYPE = "+DicConstant.GZLB_01+")))) AS MAIN_SUPP_CODE --责任厂商CODE\n");
    	sql.append("  FROM SE_BU_CLAIM            C, --索赔单\n" );
    	sql.append("       SE_BU_CLAIM_FAULT      F,--故障表\n" );
    	sql.append("       SE_BU_CLAIM_FAULT_PART P, --配件\n" );
    	sql.append("       PT_BA_SUPPLIER         S--供应商\n" );
    	sql.append("WHERE C.CLAIM_ID = F.CLAIM_ID\n" );
    	sql.append("   AND F.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n");
    	sql.append("   AND P.OLD_SUPPLIER_ID = S.SUPPLIER_ID\n");
    	sql.append("  AND C.CLAIM_ID = "+claimId+"\n" );
    	sql.append("  AND P.FAULT_PART_ID = "+faultPartId+"");
    	qs = fac.select(null, sql.toString());
    	return qs;
    }
    /**
     * 箱号打印查询
     * @param page
     * @param conditions
     * @param user
     * @return
     * @throws SQLException
     */
    public BaseResultSet oldPartBoxNoSearch(PageManager page, String conditions,User user) throws SQLException{
    	String wheres = conditions;
    	wheres += " AND D.ORDER_ID=O.ORDER_ID \n"+
    			  " AND O.ORDER_STATUS ="+DicConstant.HYDZT_03+""+	//	旧件审核通过
    			  " AND O.ORG_ID="+user.getOrgId()+"";
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
 	    StringBuffer sql= new StringBuffer();
 	    sql.append("SELECT D.DETAIL_ID,\n" );
 	    sql.append("       D.PART_ID,\n" );
 	    sql.append("       D.PART_CODE,\n" );
 	    sql.append("       D.PART_NAME,\n" );
 	    sql.append("       D.PART_AMOUNT,\n" );
 	    sql.append("       D.CLAIM_NO,\n" );
 	    sql.append("       D.VEHICLE_ID,\n" );
 	    sql.append("       D.VIN,\n" );
 	    sql.append("       D.MILEAGE,\n" );
 	    sql.append("       D.OUGHT_COUNT,\n" );
 	    sql.append("       D.BOX_NO,\n" );
 	    sql.append("       D.PROSUPPLY_ID,\n" );
 	    sql.append("       D.PROSUPPLY_CODE,\n" );
 	    sql.append("       D.PROSUPPLY_NAME,\n" );
 	    sql.append("       D.DUTYSUPPLY_ID,\n" );
 	    sql.append("       D.DUTYSUPPLY_CODE,\n" );
 	    sql.append("       D.DUTYSUPPLY_NAME,\n" );
 	    sql.append("       D.BROKEN_REASON,\n" );
 	    sql.append("       D.REMARKS\n" );
 	    sql.append("  FROM SE_BU_RETURNORDER_DETAIL D ,SE_BU_RETURN_ORDER O ");
        bs=factory.select(sql.toString(), page);
    	return bs;
    }
    
    /**
     * 下载打印数据
     * @param produceDate
     * @return
     * @throws Exception
     */
    public QuerySet download(String boxNo,User user) throws Exception{
 	    QuerySet qs = null;
 	    StringBuffer sql= new StringBuffer();
 	    sql.append("SELECT D.DETAIL_ID,\n" );
	    sql.append("       D.PART_ID,\n" );
	    sql.append("       D.PART_CODE,\n" );
	    sql.append("       D.PART_NAME,\n" );
	    sql.append("       D.PART_AMOUNT,\n" );
	    sql.append("       D.CLAIM_NO,\n" );
	    sql.append("       D.VEHICLE_ID,\n" );
	    sql.append("       D.VIN,\n" );
	    sql.append("       D.MILEAGE,\n" );
	    sql.append("       D.OUGHT_COUNT,\n" );
	    sql.append("       D.BOX_NO,\n" );
	    sql.append("       D.PROSUPPLY_ID,\n" );
	    sql.append("       D.PROSUPPLY_CODE,\n" );
	    sql.append("       D.PROSUPPLY_NAME,\n" );
	    sql.append("       D.DUTYSUPPLY_ID,\n" );
	    sql.append("       D.DUTYSUPPLY_CODE,\n" );
	    sql.append("       D.DUTYSUPPLY_NAME,\n" );
	    sql.append("       D.BROKEN_REASON,\n" );
	    sql.append("       D.REMARKS\n" );
	    sql.append("  FROM SE_BU_RETURNORDER_DETAIL D ,SE_BU_RETURN_ORDER O ");
	    sql.append("     where D.ORDER_ID=O.ORDER_ID\n");
	    sql.append("    AND O.ORDER_STATUS ="+DicConstant.HYDZT_03+" \n");
	    sql.append("      AND O.ORG_ID="+user.getOrgId()+" \n");
	    qs = factory.select(null, sql.toString());
 	    return qs;
    }
    /**
     * 下载打印数据
     * @param produceDate
     * @return
     * @throws Exception
     */
    public QuerySet partDownload(String conditions) throws Exception{
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT C.CLAIM_ID,\n" );
    	sql.append("       C.CLAIM_NO, --索赔单号\n" );
    	sql.append("       C.CLAIM_TYPE, --索赔类型\n" );
    	sql.append("       C.VIN, --VIN\n" );
    	sql.append("       C.VEHICLE_ID,\n" );
    	sql.append("       C.MILEAGE, --行驶里程\n" );
    	sql.append("       C.REMARKS, --备注\n" );
    	sql.append("       F.FAULT_CODE, --故障代码\n" );
    	sql.append("       F.FAULT_NAME, --故障名称\n" );
    	sql.append("       P.FAULT_PART_ID, --故障零件ID\n" );
    	sql.append("       P.IF_PRINT, --是否已打印\n" );
    	sql.append("       P.OLD_PART_ID, --旧件ID\n" );
    	sql.append("       P.OLD_PART_CODE, --旧件代码\n" );
    	sql.append("(SELECT A.SL FROM\n" );
    	sql.append("       (SELECT COUNT(1) SL,SP.OLD_PART_ID,MV.VEHICLE_ID\n" );
    	sql.append("        FROM MAIN_VEHICLE MV, SE_BU_CLAIM SC, SE_BU_CLAIM_FAULT_PART SP\n" );
    	sql.append("       WHERE SC.VEHICLE_ID = MV.VEHICLE_ID\n" );
    	sql.append("         AND SP.CLAIM_ID = SC.CLAIM_ID\n" );
    	sql.append("       GROUP BY  MV.VEHICLE_ID,SP.OLD_PART_ID) A\n" );
    	sql.append("       WHERE A.OLD_PART_ID = P.OLD_PART_ID\n" );
    	sql.append("        AND A.VEHICLE_ID = C.VEHICLE_ID  ) WXCS,\n" );
    	sql.append("        P.OLD_PART_NAME, --旧件名称\n" );
    	sql.append("       S.SUPPLIER_NAME, --供应商NAME\n" );
    	sql.append("       S.SUPPLIER_CODE, --供应商CODE\n" );
    	sql.append("       P.CLAIM_COSTS, --结算材料费\n" );
    	sql.append("       (NVL(P.REPAY_UPRICE, 0) * NVL(P.OLD_PART_COUNT, 0)) REPAY_COSTS, --追偿材料费\n" );
    	sql.append("       P.OLD_PART_COUNT, --数量\n" );
    	sql.append("       P.FAULT_REASON, -- 故障原因\n" );
    	sql.append("       P.FIRST_PART_ID, -- 主损件\n" );
    	sql.append("(SELECT R.SUPPLIER_NAME\n" );
    	sql.append("         FROM PT_BA_SUPPLIER R\n" );
    	sql.append("        WHERE R.SUPPLIER_ID =\n" );
    	sql.append("              (DECODE(P.FAULT_TYPE,\n" );
    	sql.append("                      301601,\n" );
    	sql.append("                      P.OLD_SUPPLIER_ID,\n" );
    	sql.append("                      (SELECT P1.OLD_SUPPLIER_ID\n" );
    	sql.append("                         FROM SE_BU_CLAIM_FAULT_PART P1\n" );
    	sql.append("                        WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
    	sql.append("                          AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
    	sql.append("                          AND P1.FAULT_TYPE = 301601)))) AS MAIN_SUPP_NAME, --责任厂商名称\n" );
    	sql.append("      (SELECT R.SUPPLIER_CODE\n" );
    	sql.append("         FROM PT_BA_SUPPLIER R\n" );
    	sql.append("        WHERE R.SUPPLIER_ID =\n" );
    	sql.append("              (DECODE(P.FAULT_TYPE,\n" );
    	sql.append("                      301601,\n" );
    	sql.append("                      P.OLD_SUPPLIER_ID,\n" );
    	sql.append("                      (SELECT P1.OLD_SUPPLIER_ID\n" );
    	sql.append("                         FROM SE_BU_CLAIM_FAULT_PART P1\n" );
    	sql.append("                        WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
    	sql.append("                          AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
    	sql.append("                          AND P1.FAULT_TYPE = 301601)))) AS MAIN_SUPP_CODE --责任厂商CODE\n" );
    	sql.append("  FROM SE_BU_CLAIM            C, --索赔单\n" );
    	sql.append("       SE_BU_CLAIM_FAULT      F,--故障表\n" );
    	sql.append("       SE_BU_CLAIM_FAULT_PART P, --配件\n" );
    	sql.append("       PT_BA_SUPPLIER         S--供应商\n" );
    	sql.append(" WHERE  "+conditions+"  AND C.CLAIM_ID = F.CLAIM_ID\n" );
    	sql.append("  AND F.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
    	sql.append(" AND P.OLD_SUPPLIER_ID = S.SUPPLIER_ID\n" );
    	sql.append("  AND C.CLAIM_STATUS=301005\n" );
    	sql.append("  AND P.MEASURES=300602\n" );
    	sql.append("  AND EXISTS (SELECT 1\n" );
    	sql.append("       FROM PT_BA_INFO I\n" );
    	sql.append("      WHERE I.PART_ID = P.OLD_PART_ID\n" );
    	sql.append("        AND I.IF_RETURN = 100101)");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    /**
     * 下载打印数据
     * @param produceDate
     * @return
     * @throws Exception
     */
    public QuerySet partDownload(String conditions,User user) throws Exception{
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT C.CLAIM_ID,\n" );
    	sql.append("       C.CLAIM_NO, --索赔单号\n" );
    	sql.append("       C.CLAIM_TYPE, --索赔类型\n" );
    	sql.append("       C.VIN, --VIN\n" );
    	sql.append("       C.MILEAGE, --行驶里程\n" );
    	sql.append(" 	   F.FAULT_CODE,--故障代码\n" );
    	sql.append("       F.FAULT_NAME,--故障名称\n" );
    	sql.append("       P.FAULT_PART_ID, --故障零件ID\n" );
    	sql.append("       P.OLD_PART_ID, --旧件ID\n" );
    	sql.append("       P.OLD_PART_CODE, --旧件代码\n" );
    	sql.append("       P.OLD_PART_NAME, --旧件名称\n" );
    	sql.append("       S.SUPPLIER_NAME,--供应商NAME\n" );
    	sql.append("       S.SUPPLIER_CODE,--供应商CODE\n" );
    	sql.append("       P.CLAIM_COSTS, --结算材料费\n" );
    	sql.append("       (nvl(P.REPAY_UPRICE,0) * nvl(P.OLD_PART_COUNT,0)) REPAY_COSTS, --追偿材料费\n" );
    	sql.append("       P.OLD_PART_COUNT, --数量\n" );
    	sql.append("       P.FAULT_REASON, -- 故障原因\n" );
    	sql.append("       P.FIRST_PART_ID, -- 主损件\n" );
    	sql.append("       (SELECT O.CODE FROM TM_ORG O WHERE O.ORG_ID="+user.getOrgId()+") AS ORG_NAME, -- 服务商名称\n" );
    	sql.append("       (SELECT O.SNAME FROM TM_ORG O WHERE O.ORG_ID="+user.getOrgId()+") AS ORG_CODE, -- 服务商代码\n" );
    	sql.append("(SELECT R.SUPPLIER_NAME\n" );
    	sql.append("         FROM PT_BA_SUPPLIER R\n" );
    	sql.append("        WHERE R.SUPPLIER_ID =\n" );
    	sql.append("              (DECODE(P.FAULT_TYPE,\n" );
    	sql.append("                      301601,\n" );
    	sql.append("                      P.OLD_SUPPLIER_ID,\n" );
    	sql.append("                      (SELECT P1.OLD_SUPPLIER_ID\n" );
    	sql.append("                         FROM SE_BU_CLAIM_FAULT_PART P1\n" );
    	sql.append("                        WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
    	sql.append("                          AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
    	sql.append("                          AND P1.FAULT_TYPE = 301601)))) AS MAIN_SUPP_NAME, --责任厂商名称\n" );
    	sql.append("      (SELECT R.SUPPLIER_CODE\n" );
    	sql.append("         FROM PT_BA_SUPPLIER R\n" );
    	sql.append("        WHERE R.SUPPLIER_ID =\n" );
    	sql.append("              (DECODE(P.FAULT_TYPE,\n" );
    	sql.append("                      301601,\n" );
    	sql.append("                      P.OLD_SUPPLIER_ID,\n" );
    	sql.append("                      (SELECT P1.OLD_SUPPLIER_ID\n" );
    	sql.append("                         FROM SE_BU_CLAIM_FAULT_PART P1\n" );
    	sql.append("                        WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
    	sql.append("                          AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
    	sql.append("                          AND P1.FAULT_TYPE = 301601)))) AS MAIN_SUPP_CODE --责任厂商CODE\n" );
    	sql.append("  FROM SE_BU_CLAIM            C, --索赔单\n" );
    	sql.append("       SE_BU_CLAIM_FAULT      F,--故障表\n" );
    	sql.append("       SE_BU_CLAIM_FAULT_PART P, --配件\n" );
    	sql.append("       PT_BA_SUPPLIER         S--供应商\n" );
    	sql.append(" WHERE  "+conditions+"\n" );
    	sql.append(" AND C.CLAIM_ID = F.CLAIM_ID\n" );
    	sql.append(" AND F.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
    	sql.append(" AND P.OLD_SUPPLIER_ID = S.SUPPLIER_ID\n" );
    	sql.append(" AND C.CLAIM_STATUS=301005\n" );
    	sql.append(" AND P.MEASURES=300602\n" );
    	sql.append(" AND EXISTS (SELECT 1\n" );
    	sql.append("       FROM PT_BA_INFO I\n" );
    	sql.append("      WHERE I.PART_ID = P.OLD_PART_ID\n" );
    	sql.append("        AND I.IF_RETURN = 100101)\n" );
    	sql.append(" AND C.ORG_ID="+user.getOrgId()+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    
    /**
     * 修改已打印
     * @param faultPartId
     * @return
     * @throws Exception
     */
    public boolean updateFaultPart(String faultPartId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE SE_BU_CLAIM_FAULT_PART P\n" );
    	sql.append("   SET P.IF_PRINT = 100101\n" );
    	sql.append(" WHERE P.FAULT_PART_ID="+faultPartId+"");
    	return factory.update(sql.toString(), null);
    }
}
