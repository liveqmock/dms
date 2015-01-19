package com.org.dms.dao.part.salesMng.search;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Map;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: AmountSummaryQueryDao 
 * Function: 配件公司库存金额汇总表
 * date: 2014年11月5日 上午1:36:54
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class AmountSummaryQueryDao extends BaseDAO {
	
	public static final AmountSummaryQueryDao getInstance(ActionContext ac){
		AmountSummaryQueryDao dao = new AmountSummaryQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * inserByProcedure: 根据开始日期，结束日期调用存储过程，将数据写入查询表中
	 * @author fuxiao
	 * Date:2014年11月5日
	 *
	 */
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
        
	}
	
	/**
	 * 
	 * queryDataCountByDate: 根据开始日期，结束日期查询是否存在数据：> 0 存在， 其他情况则不存在
	 * @author fuxiao
	 * Date:2014年11月5日
	 *
	 */
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
	}
	
	/**
	 * 
	 * deleteDateByDate: 删除已写入的数据
	 * @author fuxiao
	 * Date:2014年11月27日
	 *
	 */
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
		
	}
	
	/**
	 * 
	 * queryInfoByDate: 根据开始日期，结束日期查询数据
	 * @author fuxiao
	 * Date:2014年11月5日
	 *
	 */
	public QuerySet queryInfoByDate(Map<String, String> hm) throws Exception {
		String sql = 
				"SELECT INIT_INVENTORY, PURCHASE_SQ, PURCHASE_OTHER, XSTJ_IN, CGTJ_OUT, MOVE_JPDBFK, MOVE_IN, MOVE_BBK, \n" +
						"       PRICING_AMOUNT, PRICING_AMOUNT_DESC, INVEN_RECON_AMOUNT, INVEN_RECON_AMOUNT_DESC, SALES_AMOUNT, MOVE_XCK,\n" + 
						"       MOVE_DBFK, MOVE_ZSK, MOVE_ZGK, MOVE_JPK, OTHER_CLAIM,OTHER_COMPANY,\n" + 
						"       OTHER_DEPARTMENT, OTHER_OTHER,QT_IN\n" + 
						"FROM PT_PU_AMOUNT_SUMMARY\n" + 
						"WHERE TRUNC(BEGIN_DATE) = TO_DATE(?, 'YYYY-MM-DD') AND TRUNC(END_DATE) = TO_DATE(?, 'YYYY-MM-DD') AND WAREHOUSE_CODE = ?";
		return this.factory.select(new Object[]{hm.get("beginDate".toUpperCase()), hm.get("endDate".toUpperCase()), hm.get("code".toUpperCase())}, sql);
	}
	
}
	