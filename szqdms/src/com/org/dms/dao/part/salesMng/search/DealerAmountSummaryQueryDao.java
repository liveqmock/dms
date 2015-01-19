package com.org.dms.dao.part.salesMng.search;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Map;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: DealerAmountSummaryQueryDao 
 * Function: 配送中心库存金额汇总表
 * date: 2014年11月7日 下午2:41:21
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class DealerAmountSummaryQueryDao extends BaseDAO {
	
	public static final DealerAmountSummaryQueryDao getInstance(ActionContext ac){
		DealerAmountSummaryQueryDao dao = new DealerAmountSummaryQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * inserByProcedure: 根据开始日期，结束日期, org_id 调用存储过程，将数据写入查询表中
	 * @author fuxiao
	 * Date:2014年11月5日
	 *
	 */
	public void inserByProcedure(Map<String,String> hm)throws Exception{
        CallableStatement proc = null;
        try {
			proc = factory.getConnection().prepareCall("{call P_DEALER_AMOUNT_SUMMARY(TO_DATE(?, 'YYYY-MM-DD'), TO_DATE(?, 'YYYY-MM-DD'), ?)}");
			proc.setString(1, hm.get("beginDate".toUpperCase()));
			proc.setString(2, hm.get("endDate".toUpperCase()));
			proc.setString(3, hm.get("ORG_ID".toUpperCase()));
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
		String sql = "SELECT COUNT(1) FROM PT_PU_DEALER_AMOUNT_SUMMARY T WHERE T.BEGIN_DATE = TO_DATE(?, 'YYYY-MM-DD') AND T.END_DATE = TO_DATE(?, 'YYYY-MM-DD') AND T.ORG_ID = ?";
		return Integer.parseInt(this.factory.select(sql, new Object[]{hm.get("beginDate".toUpperCase()), hm.get("endDate".toUpperCase()), hm.get("ORG_ID".toUpperCase())})[0][0]) > 0;
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
				"SELECT INIT_INVENTORY, CHECK_ORDER_AMOUNT, SALES_RETURN_AMOUNT, SELL_RETURN_AMOUNT,\n" +
						"       PRICE_DIFFERENCE_IN, PRICE_DIFFERENCE_OUT, SALES_AMOUNT, SELL_AMOUNT "+
						"FROM PT_PU_DEALER_AMOUNT_SUMMARY\n" + 
						"WHERE TRUNC(BEGIN_DATE) = TO_DATE(?, 'YYYY-MM-DD') AND TRUNC(END_DATE) = TO_DATE(?, 'YYYY-MM-DD') AND ORG_ID = ?";
		return this.factory.select(new Object[]{hm.get("beginDate".toUpperCase()), hm.get("endDate".toUpperCase()), hm.get("ORG_ID".toUpperCase())}, sql);
	}
	
	/**
	 * 
	 * deleteDateByDate: 删除已写入的数据
	 * @author fuxiao
	 * Date:2014年11月27日
	 *
	 */
	public void deleteDateByDate(Map<String, String> hm) throws Exception {
		String sql = "DELETE FROM PT_PU_DEALER_AMOUNT_SUMMARY T " + 
						" WHERE TRUNC(T.BEGIN_DATE) = TO_DATE(?, 'YYYY-MM-DD') " + 
						" AND TRUNC(T.END_DATE) = TO_DATE(?, 'YYYY-MM-DD')" + 
						" AND T.ORG_ID = ?";
		this.factory.update(sql, new Object[]{
				hm.get("beginDate".toUpperCase()), 
				hm.get("endDate".toUpperCase()),
				hm.get("ORG_ID".toUpperCase())
		});
		
	}
	
}
	