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
public class OldPartPrint2Dao {

  
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
    	sql.append("       (SELECT O.CODE FROM TM_ORG O WHERE O.ORG_ID="+user.getOrgId()+") as ORG_NAME, -- 服务商名称\n" );
    	sql.append("       (SELECT O.SNAME FROM TM_ORG O WHERE O.ORG_ID="+user.getOrgId()+") as ORG_CODE,-- 服务商代码\n" );
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
}
