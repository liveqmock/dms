package com.org.dms.dao.part.salesMng.search;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Map;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: PartSalesReturnStaticQueryDao 
 * Function: 配件销售回款统计表
 * date: 2014-12-10 
 * @author suoxiuli
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class PartSalesReturnStaticQueryDao extends BaseDAO {
	
	public static final PartSalesReturnStaticQueryDao getInstance(ActionContext ac){
		PartSalesReturnStaticQueryDao dao = new PartSalesReturnStaticQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * inserByProcedure: 根据开始日期，结束日期调用存储过程，将数据写入查询表中
	 * @author fuxiao
	 * Date:2014年11月5日
	 *
	 
	public void inserByProcedure(Map<String,String> hm)throws Exception{
        CallableStatement proc = null;
        try {
			proc = factory.getConnection().prepareCall("{call P_AMOUNT_SUMMARY(TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS'), ?)}");
			proc.setString(1, hm.get("beginDate".toUpperCase()));
			proc.setString(2, hm.get("endDate".toUpperCase()) + " 23:59:59");
			proc.setString(3, hm.get("CODE".toUpperCase()));
			proc.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
        
	}*/
	
	/**
	 * 
	 * queryDataCountByDate: 根据开始日期，结束日期查询是否存在数据：> 0 存在， 其他情况则不存在
	 * @author fuxiao
	 * Date:2014年11月5日
	 *
	 
	public boolean queryDataCountByDate(Map<String, String> hm) throws Exception {
		String sql = "SELECT COUNT(1) FROM PT_PU_AMOUNT_SUMMARY T " + 
						" WHERE T.BEGIN_DATE = TO_DATE(?, 'YYYY-MM-DD') " + 
						" AND T.END_DATE = TO_DATE(?, 'YYYY-MM-DD')" + 
						" AND T.WAREHOUSE_CODE = ?";
		return Integer.parseInt(this.factory.select(sql, new Object[]{
															hm.get("beginDate".toUpperCase()), 
															hm.get("endDate".toUpperCase()),
															hm.get("code".toUpperCase())
															})[0][0]) > 0;
	}*/
	
	/**
	 * 
	 * deleteDateByDate: 删除已写入的数据
	 * @author fuxiao
	 * Date:2014年11月27日
	 *
	 
	public void deleteDateByDate(Map<String, String> hm) throws Exception {
		String sql = "DELETE FROM PT_PU_AMOUNT_SUMMARY T " + 
						" WHERE TRUNC(T.BEGIN_DATE) = TO_DATE(?, 'YYYY-MM-DD') " + 
						" AND TRUNC(T.END_DATE) = TO_DATE(?, 'YYYY-MM-DD')" + 
						" AND T.WAREHOUSE_CODE = ?";
		this.factory.update(sql, new Object[]{
				hm.get("beginDate".toUpperCase()), 
				hm.get("endDate".toUpperCase()),
				hm.get("code".toUpperCase())	
		});
		
	}*/
	
	/**
	 * 配件销售回款统计表查询
	 * @throws Exception
     * @Author suoxiuli 2014-12-10
	 */
	public BaseResultSet queryInfoByDate(PageManager page, Map<String, String> hm) throws Exception {
		
		int year = Integer.parseInt(hm.get("YEAR"));
		String startTime = year+"" + "-" + hm.get("MONTH");
		String yearGrowthStatiTime = (year-1)+"" + "-" + hm.get("MONTH");
		
		String wheres = " A.STATUS = 100201";
		wheres += "   AND (A.STATISTIC_MONTH = '"+startTime+"' OR A.STATISTIC_MONTH = '"+yearGrowthStatiTime+"')";
        page.setFilter(wheres);
        
        BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT A.STATISTIC_ID,\n" );
		sql.append("       A.BSC_ID,\n" );
		sql.append("       A.BSC_CODE,\n" );
		sql.append("       A.BSC_NAME,\n" );
		sql.append("       A.DC_ID,\n" );
		sql.append("       A.DC_CODE,\n" );
		sql.append("       A.DC_NAME,\n" );
		sql.append("       A.STATISTIC_MONTH,\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN A.STATISTIC_MONTH = '"+startTime+"' THEN\n" );
		sql.append("          A.OUTSTAND_ACCOUNT\n" );
		sql.append("       END RETURN_ACCOUT_14,\n" );
		sql.append("\n" );
		sql.append("       (((CASE\n" );
		sql.append("         WHEN A.STATISTIC_MONTH = '"+startTime+"' THEN\n" );
		sql.append("          A.OUTSTAND_ACCOUNT\n" );
		sql.append("       END) - (CASE\n" );
		sql.append("         WHEN A.STATISTIC_MONTH = '"+yearGrowthStatiTime+"' THEN\n" );
		sql.append("          A.OUTSTAND_ACCOUNT\n" );
		sql.append("         ELSE\n" );
		sql.append("          0\n" );
		sql.append("       END)) / (CASE\n" );
		sql.append("         WHEN (CASE\n" );
		sql.append("                WHEN A.STATISTIC_MONTH = '"+yearGrowthStatiTime+"' THEN\n" );
		sql.append("                 A.OUTSTAND_ACCOUNT\n" );
		sql.append("                ELSE\n" );
		sql.append("                 0\n" );
		sql.append("              END) = 0 THEN\n" );
		sql.append("          1\n" );
		sql.append("         ELSE\n" );
		sql.append("          CASE\n" );
		sql.append("            WHEN A.STATISTIC_MONTH = '"+yearGrowthStatiTime+"' THEN\n" );
		sql.append("             A.OUTSTAND_ACCOUNT\n" );
		sql.append("          END\n" );
		sql.append("       END)) * 100 || '%' AS RETURN_ACCOUT_YEAR_GROWTH,\n" );
		sql.append("\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN A.STATISTIC_MONTH = '"+startTime+"' THEN\n" );
		sql.append("          A.SALES_ACCOUNT\n" );
		sql.append("       END SALES_ACCOUNT_14,\n" );
		sql.append("\n" );
		sql.append("       (((CASE\n" );
		sql.append("         WHEN A.STATISTIC_MONTH = '"+startTime+"' THEN\n" );
		sql.append("          A.SALES_ACCOUNT\n" );
		sql.append("       END) - (CASE\n" );
		sql.append("         WHEN A.STATISTIC_MONTH = '"+yearGrowthStatiTime+"' THEN\n" );
		sql.append("          A.SALES_ACCOUNT\n" );
		sql.append("         ELSE\n" );
		sql.append("          0\n" );
		sql.append("       END)) / (CASE\n" );
		sql.append("         WHEN (CASE\n" );
		sql.append("                WHEN A.STATISTIC_MONTH = '"+yearGrowthStatiTime+"' THEN\n" );
		sql.append("                 A.SALES_ACCOUNT\n" );
		sql.append("                ELSE\n" );
		sql.append("                 0\n" );
		sql.append("              END) = 0 THEN\n" );
		sql.append("          1\n" );
		sql.append("         ELSE\n" );
		sql.append("          CASE\n" );
		sql.append("            WHEN A.STATISTIC_MONTH = '"+yearGrowthStatiTime+"' THEN\n" );
		sql.append("             A.SALES_ACCOUNT\n" );
		sql.append("          END\n" );
		sql.append("       END)) * 100 || '%' AS SALES_ACCOUNT_YEAR_GROWTH,\n" );
		sql.append("\n" );
		sql.append("       A.OIL_ACCOUT,\n" );
		sql.append("       A.OUT_LX_ACCOUT,\n" );
		sql.append("       A.OUT_LHQ_ACCOUT,\n" );
		sql.append("       A.CQJ_ACCOUT,\n" );
		sql.append("       A.QDZC_ACCOUT,\n" );
		sql.append("       A.OTHER_OUT_ACCOUT,\n" );
		sql.append("       A.OTHER_ACCOUT,\n" );
		sql.append("       A.STATISTIC_USER,\n" );
		sql.append("       A.STATISTIC_TIME,\n" );
		sql.append("       A.STATUS\n" );
		sql.append("\n" );
		sql.append("  FROM PT_BU_PART_SALES_RETURN_STATIC A\n" );
		
		bs = factory.select(sql.toString(), page);
    	return bs;
	}
	
	/**
	 * 某个配送中心的配件销售回款统计查询
	 * @throws Exception
     * @Author suoxiuli 2014-12-23
	 */
	public QuerySet queryDCInfoByDate(String statisticId) throws Exception {

		StringBuffer sql= new StringBuffer();
		sql.append("SELECT STATISTIC_ID,\n" );
		sql.append("       BSC_ID,\n" );
		sql.append("       BSC_CODE,\n" );
		sql.append("       BSC_NAME,\n" );
		sql.append("       DC_ID,\n" );
		sql.append("       DC_CODE,\n" );
		sql.append("       DC_NAME,\n" );
		sql.append("       STATISTIC_MONTH,\n" );
		sql.append("       OUTSTAND_ACCOUNT,\n" );
		sql.append("       SALES_ACCOUNT,\n" );
		sql.append("       OIL_ACCOUT,\n" );
		sql.append("       OUT_LX_ACCOUT,\n" );
		sql.append("       OUT_LHQ_ACCOUT,\n" );
		sql.append("       CQJ_ACCOUT,\n" );
		sql.append("       QDZC_ACCOUT,\n" );
		sql.append("       OTHER_OUT_ACCOUT,\n" );
		sql.append("       OTHER_ACCOUT,\n" );
		sql.append("       STATISTIC_USER,\n" );
		sql.append("       STATISTIC_TIME,\n" );
		sql.append("       STATUS\n" );
		sql.append("  FROM PT_BU_PART_SALES_RETURN_STATIC \n" );
		sql.append("  WHERE STATISTIC_ID = "+statisticId);
		
    	return this.factory.select(null, sql.toString());
	}

	/**
	 * 配件销售回款统计表导出
	 * @throws Exception
     * @Author suoxiuli 2014-12-30
	 */
	public QuerySet reportExportExcel(PageManager page, Map<String, String> hm) throws Exception {
		
		int year = Integer.parseInt(hm.get("YEAR"));
		String startTime = year+"" + "-" + hm.get("MONTH");
		String yearGrowthStatiTime = (year-1)+"" + "-" + hm.get("MONTH");
		
		String wheres = " A.STATUS = 100201";
		wheres += "   AND (A.STATISTIC_MONTH = '"+startTime+"' OR A.STATISTIC_MONTH = '"+yearGrowthStatiTime+"')";
        page.setFilter(wheres);
        
        BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT ROWNUM,\n" );
		sql.append("       A.STATISTIC_ID,\n" );
		sql.append("       A.BSC_ID,\n" );
		sql.append("       A.BSC_CODE,\n" );
		sql.append("       A.BSC_NAME,\n" );
		sql.append("       A.DC_ID,\n" );
		sql.append("       A.DC_CODE,\n" );
		sql.append("       A.DC_NAME,\n" );
		sql.append("       A.STATISTIC_MONTH,\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN A.STATISTIC_MONTH = '"+startTime+"' THEN\n" );
		sql.append("          A.OUTSTAND_ACCOUNT\n" );
		sql.append("       END RETURN_ACCOUT_14,\n" );
		sql.append("\n" );
		sql.append("       (((CASE\n" );
		sql.append("         WHEN A.STATISTIC_MONTH = '"+startTime+"' THEN\n" );
		sql.append("          A.OUTSTAND_ACCOUNT\n" );
		sql.append("       END) - (CASE\n" );
		sql.append("         WHEN A.STATISTIC_MONTH = '"+yearGrowthStatiTime+"' THEN\n" );
		sql.append("          A.OUTSTAND_ACCOUNT\n" );
		sql.append("         ELSE\n" );
		sql.append("          0\n" );
		sql.append("       END)) / (CASE\n" );
		sql.append("         WHEN (CASE\n" );
		sql.append("                WHEN A.STATISTIC_MONTH = '"+yearGrowthStatiTime+"' THEN\n" );
		sql.append("                 A.OUTSTAND_ACCOUNT\n" );
		sql.append("                ELSE\n" );
		sql.append("                 0\n" );
		sql.append("              END) = 0 THEN\n" );
		sql.append("          1\n" );
		sql.append("         ELSE\n" );
		sql.append("          CASE\n" );
		sql.append("            WHEN A.STATISTIC_MONTH = '"+yearGrowthStatiTime+"' THEN\n" );
		sql.append("             A.OUTSTAND_ACCOUNT\n" );
		sql.append("          END\n" );
		sql.append("       END)) * 100 || '%' AS RETURN_ACCOUT_YEAR_GROWTH,\n" );
		sql.append("\n" );
		sql.append("       CASE\n" );
		sql.append("         WHEN A.STATISTIC_MONTH = '"+startTime+"' THEN\n" );
		sql.append("          A.SALES_ACCOUNT\n" );
		sql.append("       END SALES_ACCOUNT_14,\n" );
		sql.append("\n" );
		sql.append("       (((CASE\n" );
		sql.append("         WHEN A.STATISTIC_MONTH = '"+startTime+"' THEN\n" );
		sql.append("          A.SALES_ACCOUNT\n" );
		sql.append("       END) - (CASE\n" );
		sql.append("         WHEN A.STATISTIC_MONTH = '"+yearGrowthStatiTime+"' THEN\n" );
		sql.append("          A.SALES_ACCOUNT\n" );
		sql.append("         ELSE\n" );
		sql.append("          0\n" );
		sql.append("       END)) / (CASE\n" );
		sql.append("         WHEN (CASE\n" );
		sql.append("                WHEN A.STATISTIC_MONTH = '"+yearGrowthStatiTime+"' THEN\n" );
		sql.append("                 A.SALES_ACCOUNT\n" );
		sql.append("                ELSE\n" );
		sql.append("                 0\n" );
		sql.append("              END) = 0 THEN\n" );
		sql.append("          1\n" );
		sql.append("         ELSE\n" );
		sql.append("          CASE\n" );
		sql.append("            WHEN A.STATISTIC_MONTH = '"+yearGrowthStatiTime+"' THEN\n" );
		sql.append("             A.SALES_ACCOUNT\n" );
		sql.append("          END\n" );
		sql.append("       END)) * 100 || '%' AS SALES_ACCOUNT_YEAR_GROWTH,\n" );
		sql.append("\n" );
		sql.append("       A.OIL_ACCOUT,\n" );
		sql.append("       A.OUT_LX_ACCOUT,\n" );
		sql.append("       A.OUT_LHQ_ACCOUT,\n" );
		sql.append("       A.CQJ_ACCOUT,\n" );
		sql.append("       A.QDZC_ACCOUT,\n" );
		sql.append("       A.OTHER_OUT_ACCOUT,\n" );
		sql.append("       A.OTHER_ACCOUT,\n" );
		sql.append("       A.STATISTIC_USER,\n" );
		sql.append("       A.STATISTIC_TIME,\n" );
		sql.append("       A.STATUS\n" );
		sql.append("\n" );
		sql.append("  FROM PT_BU_PART_SALES_RETURN_STATIC A\n" );
		sql.append("  WHERE A.STATUS = 100201\n" );
		sql.append("    AND (A.STATISTIC_MONTH = '"+startTime+"' OR A.STATISTIC_MONTH = '"+yearGrowthStatiTime+"')");
		
    	return this.factory.select(null, sql.toString());
	}
}
	