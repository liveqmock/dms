package com.org.dms.dao.part.storageMng.search;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class DealerRaleSaleSearchMngDao extends BaseDAO{
	public static final DealerRaleSaleSearchMngDao getInstance(ActionContext atx) {

		DealerRaleSaleSearchMngDao dao = new DealerRaleSaleSearchMngDao();
		atx.setDBFactory(dao.factory);

        return dao;
    }
	
	/**
	 * 
	 * @Title: filterConditions
	 * @Description: 对查询条件进行过滤
	 * @param conditions
	 * @return
	 * @return: String
	 */
	public String filterConditions(String conditions, String partCode, User user){

        if(!"".equals(partCode)&&null!=partCode){
        	conditions +="AND T.STATUS = '" + DicConstant.YXBS_01 + "'\n" + 
    				"AND T.ORG_ID = '" + user.getOrgId() + "'\n" + 
    				"AND T.SALE_ID = T1.SALE_ID(+)\n" + 
    				"AND T.SALE_STATUS = '" + DicConstant.SXDZT_02 + "'\n"+
    				"AND EXISTS (SELECT 1 FROM PT_BU_REAL_SALE_DTL D WHERE D.SALE_ID = T.SALE_ID AND D.PART_CODE LIKE '%"+partCode+"%') ORDER BY SALE_DATE DESC";
        }else{
        	conditions +="AND T.STATUS = '" + DicConstant.YXBS_01 + "'\n" + 
        			"AND T.SALE_ID = T1.SALE_ID(+)\n" + 
    				"AND T.ORG_ID = '" + user.getOrgId() + "'\n" + 
    				"AND T.SALE_STATUS = '" + DicConstant.SXDZT_02 + "' ORDER BY SALE_DATE DESC";
        }
		return conditions;
	}
	
	
	public BaseResultSet realSalesearch(PageManager page, User user, String conditions) throws Exception {
        page.setFilter(conditions);
        // 定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.*,T1.AMOUNT FROM PT_BU_REAL_SALE T,(SELECT SALE_ID,SUM(AMOUNT) AMOUNT FROM PT_BU_REAL_SALE_DTL GROUP BY SALE_ID) T1\n");
/*        sql.append(" WHERE \n");
        sql.append(" T.OEM_COMPANY_ID = '" + user.getOemCompanyId() + "'\n" );
        sql.append("AND T.STATUS = '" + DicConstant.YXBS_01 + "'\n" );
        sql.append("AND T.COMPANY_ID = '" + user.getCompanyId() + "'\n" );
        sql.append("AND T.ORG_ID = '" + user.getOrgId() + "'\n" );
        sql.append("AND T.SALE_STATUS = '" + DicConstant.SXDZT_02 + "'\n");
        sql.append("AND EXISTS (SELECT 1 FROM PT_BU_REAL_SALE A, PT_BU_REAL_SALE_DTL B");*/
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("SALE_STATUS", "SXDZT");
        bs.setFieldDic("OUT_TYPE", "SXLX");
        bs.setFieldDic("UNIT", DicConstant.JLDW);
        bs.setFieldDateFormat("SALE_DATE", "yyyy-MM-dd");
        return bs;
	}
	
	/**
	 * 
	 * @Title: realSalesearch
	 * @Description: 实销出库查询
	 * @param page
	 * @param user
	 * @param conditions
	 * @param partCode
	 * @return
	 * @throws Exception
	 * @return: BaseResultSet
	 */
	public BaseResultSet realSalesearch(PageManager page, User user, String conditions,String partCode) throws Exception {
        conditions = this.filterConditions(conditions, partCode, user);
        return this.realSalesearch(page, user, conditions);
    }
	
	public BaseResultSet saleOrderInfoSearch(PageManager page, User user, String conditions,String SALE_ID) throws Exception
    {
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT	T.*\n" );
    	sql.append("  FROM PT_BU_REAL_SALE T\n" );
    	sql.append(" WHERE T.SALE_ID = "+SALE_ID+"");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("OUT_TYPE", "SXLX");
		bs.setFieldDic("SALE_STATUS", "SXDZT");
		bs.setFieldDateFormat("SALE_DATE", "yyyy-MM-dd");
    	return bs;
    }
	
	public BaseResultSet realSaleDtlsearch(PageManager page, User user, String saleId) throws Exception {
        String wheres ="";
        wheres += "\n 1=1 "
               +  "\n AND T.PART_ID = P.PART_ID"
               +  "\n AND T.SALE_ID = "+ saleId;
        page.setFilter(wheres);
        // 定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.DTL_ID,\n" );
        sql.append("       T.SALE_ID,\n" );
        sql.append("       T.PART_ID,\n" );
        sql.append("       P.PART_CODE,\n" );
        sql.append("       P.PART_NAME,\n" );
        sql.append("       P.UNIT,\n" );
        sql.append("       T.SALE_PRICE,\n" );
        sql.append("       T.SALE_COUNT,\n" );
        sql.append("       T.AMOUNT,\n" );
        sql.append("       P.PART_NO,\n" );
        sql.append("       P.MIN_PACK\n" );
        sql.append("  FROM PT_BU_REAL_SALE_DTL T, PT_BA_INFO P");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", DicConstant.JLDW);
        return bs;
    }

	/**
	 * 
	 * @Title: queryDownInfo
	 * @Description: excel下载查询
	 * @param conditions
	 * @param user
	 * @return
	 * @return: QuerySet
	 * @throws SQLException 
	 */
	public QuerySet queryDownInfo(String conditions, User user, String partCode) throws SQLException {
		conditions = this.filterConditions(conditions, partCode, user);
		String sql = 
				"SELECT T.SALE_NO,\n" +
						"       T.CUSTOMER_NAME,\n" + 
						"       T.LINK_PHONE,\n" + 
						"       T.LINK_ADDR,\n" + 
						"       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID = T.SALE_STATUS) SALE_STATUS,\n" + 
						"       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID = T.OUT_TYPE) OUT_TYPE,\n" + 
						"       TO_CHAR(T.SALE_DATE, 'YYYY-MM-DD HH24:MI:SS') SALE_DATE,\n" + 
						"       T1.AMOUNT\n" + 
						"  FROM PT_BU_REAL_SALE T,\n" + 
						"       (SELECT SALE_ID, SUM(AMOUNT) AMOUNT\n" + 
						"          FROM PT_BU_REAL_SALE_DTL\n" + 
						"         GROUP BY SALE_ID) T1\n" + 
						" WHERE 1 = 1\n" + 
						"   AND T.STATUS = 100201\n" + 
						"   AND T.SALE_ID = T1.SALE_ID(+)\n" + 
						"   AND T.ORG_ID = " + user.getOrgId() + "\n" + 
						"   AND T.SALE_STATUS = 205002 AND " +  conditions;
		return this.factory.select(null, sql);

	}

}
