package com.org.dms.dao.service.claimmng;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * 供应商索赔单查询
 * @author zts
 *
 */
public class ClaimSupplierSearchDao extends BaseDAO
{
    //定义instance
    public  static final ClaimSupplierSearchDao getInstance(ActionContext atx)
    {
    	ClaimSupplierSearchDao dao = new ClaimSupplierSearchDao();
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
	       where += " AND T.CLAIM_ID = F.CLAIM_ID \n "+ 
					" AND F.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" +
					" AND T.WORK_ID = O.WORK_ID \n"+
					" AND EXISTS (SELECT 1\n" +
					"          FROM PT_BA_SUPPLIER S\n" + 
					"         WHERE S.SUPPLIER_ID = P.OLD_SUPPLIER_ID\n" + 
					"           AND S.ORG_ID = "+user.getOrgId()+") \n"+
					//索赔单状态是自动审核通过之后的
					" AND T.CLAIM_STATUS in ("+DicConstant.SPDZT_03+","+DicConstant.SPDZT_04+","+DicConstant.SPDZT_05+","+DicConstant.SPDZT_06+","+DicConstant.SPDZT_07+","+DicConstant.SPDZT_08+","+DicConstant.SPDZT_15+") \n"+
	       			" AND P.FAULT_TYPE = 301601 \n"+
					" ORDER BY T.CLAIM_NO ";
		page.setFilter(where);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT DISTINCT T.CLAIM_ID,\n" );
		sql.append("                T.CLAIM_NO,\n" );
		sql.append("                T.CLAIM_TYPE,\n" );
		sql.append("                T.CLAIM_STATUS,\n" );
		sql.append("                T.APPLY_DATE,\n" );
		sql.append("                T.CHECKPASS_DATE,\n" );
		sql.append("                T.OLDPART_FINAL_DATE,\n" );
		sql.append("                T.SINGLE_STEP,\n" );
		sql.append("                O.WORK_ID,\n" );
		sql.append("                O.WORK_NO\n" );
		sql.append("  FROM SE_BU_CLAIM            T,\n" );
		sql.append("       SE_BU_CLAIM_FAULT      F,\n" );
		sql.append("       SE_BU_CLAIM_FAULT_PART P,\n" );
		sql.append("       SE_BU_WORK_ORDER       O \n");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_STATUS","SPDZT");
		bs.setFieldDic("CLAIM_TYPE","SPDLX");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("CHECKPASS_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("OLDPART_FINAL_DATE", "yyyy-MM-dd HH:mm:ss");
		return bs;
    }
    /**
     * 厂端工单查询下载
     * @param claimId
     * @return
     * @throws Exception
     */
    public QuerySet oemDownload(String conditions,User user)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT DISTINCT T.CLAIM_ID,\n" );
    	sql.append("                T.CLAIM_NO,\n" );
    	sql.append("       (SELECT D.DIC_VALUE FROM DIC_TREE D WHERE D.ID = T.CLAIM_TYPE)CLAIM_TYPE,\n" );
    	sql.append("       (SELECT D.DIC_VALUE FROM DIC_TREE D WHERE D.ID = T.CLAIM_STATUS)CLAIM_STATUS,\n" );
    	sql.append("                T.APPLY_DATE,\n" );
    	sql.append("                T.CHECKPASS_DATE,\n" );
    	sql.append("                T.OLDPART_FINAL_DATE,\n" );
    	sql.append("                T.SINGLE_STEP,\n" );
    	sql.append("                O.WORK_ID,\n" );
    	sql.append("                O.WORK_NO\n" );
    	sql.append("  FROM SE_BU_CLAIM            T,\n" );
    	sql.append("       SE_BU_CLAIM_FAULT      F,\n" );
    	sql.append("       SE_BU_CLAIM_FAULT_PART P,\n" );
    	sql.append("       SE_BU_WORK_ORDER       O\n" );
    	sql.append(" WHERE "+conditions+" AND T.CLAIM_ID = F.CLAIM_ID\n" );
    	sql.append("  AND F.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
    	sql.append(" AND T.WORK_ID = O.WORK_ID\n" );
    	sql.append(" AND EXISTS (SELECT 1\n" );
    	sql.append("          FROM PT_BA_SUPPLIER S\n" );
    	sql.append("         WHERE S.SUPPLIER_ID = P.OLD_SUPPLIER_ID\n" );
    	sql.append("           AND S.ORG_ID = "+user.getOrgId()+")\n" );
    	sql.append(" AND T.CLAIM_STATUS in (301003,301004,301005,301006,301007,301008,301015)\n" );
    	sql.append(" AND P.FAULT_TYPE = 301601\n" );
    	sql.append(" ORDER BY T.CLAIM_NO");
 	   return factory.select(null, sql.toString());
    }
}
