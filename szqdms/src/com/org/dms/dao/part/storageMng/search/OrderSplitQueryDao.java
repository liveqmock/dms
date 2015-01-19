package com.org.dms.dao.part.storageMng.search;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: OrderSplitQueryDao 
 * Function: 采购订单入库明细
 * date: 2014年10月30日 下午7:35:40
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class OrderSplitQueryDao extends BaseDAO {
	
	public static final OrderSplitQueryDao getInstance(ActionContext ac){
		OrderSplitQueryDao dao = new OrderSplitQueryDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 
	 * queryList: 查询表单查询
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public BaseResultSet queryList(PageManager pageManager, String conditions) throws Exception{
		BaseResultSet rs = null;
		String sql = 
				"SELECT\n" +
				"      BI.PART_CODE, BI.PART_NAME, BI.PART_NO, OS.SPLIT_NO, I.WAREHOUSE_CODE, I.WAREHOUSE_NAME,\n" + 
				"      I.IN_NO, I.IN_DATE, PRINT_DATE, IN_AMOUNT,\n" + 
				"      DT.PLAN_PRICE, NVL(DT.IN_AMOUNT,0)*NVL(DT.PLAN_PRICE,0) PLAN_AMOUNT, DT.PCH_PRICE,\n" + 
				"      DT.PCH_AMOUNT, OS.SUPPLIER_CODE, OS.SUPPLIER_NAME,\n" + 
				"      I.BUYER, OS.SETTLE_STATUS,  SD.DISTRIBUTION_NO \n" + 
				"  FROM PT_BU_PCH_ORDER_SPLIT OS, PT_BU_STOCK_IN I, PT_BU_STOCK_IN_DTL DT, PT_BA_INFO BI, PT_BU_PCH_ORDER_SPLIT_DTL SD\n" + 
				" WHERE " + 
				" 		OS.SPLIT_ID = I.ORDER_ID AND I.IN_ID = DT.IN_ID AND OS.SPLIT_ID = SD.SPLIT_ID AND DT.PART_ID = SD.PART_ID \n" + 
				"       AND DT.PART_ID = BI.PART_ID AND I.IN_STATUS = 201502\n" + 
				"       AND I.PRINT_STATUS = 201702 AND I.STATUS = 100201 AND NVL(IN_AMOUNT,0) > 0" +
				"		AND " + conditions + " ORDER BY I.IN_DATE DESC";
		rs = this.factory.select(sql, pageManager);
		rs.setFieldUserID("BUYER");
		rs.setFieldDic("SETTLE_STATUS", "GYSJSZT");
		rs.setFieldDateFormat("IN_DATE", "yyyy-MM-dd HH:mm:ss");
		rs.setFieldDateFormat("PRINT_DATE", "yyyy-MM-dd HH:mm:ss");
		return rs;
	}
	
	/**
	 * 
	 * queryDownInfo: 查询需要下载的数据
	 * @author fuxiao
	 * Date:2014年10月23日
	 *
	 */
	public QuerySet queryDownInfo(String conditions) throws Exception {
		return this.factory.select(null, 
									"SELECT\n" +
									"      BI.PART_CODE, BI.PART_NAME, BI.PART_NO, OS.SPLIT_NO,I.WAREHOUSE_CODE, I.WAREHOUSE_NAME,\n" + 
									"      I.IN_NO, TO_CHAR(I.IN_DATE, 'YYYY-MM-DD HH24:MI:SS') IN_DATE, TO_CHAR(PRINT_DATE, 'YYYY-MM-DD HH24:MI:SS') PRINT_DATE,IN_AMOUNT,\n" + 
									"      DT.PLAN_PRICE, DT.PLAN_AMOUNT, DT.PCH_PRICE,\n" + 
									"      DT.PCH_AMOUNT, OS.SUPPLIER_CODE, OS.SUPPLIER_NAME,\n" + 
									"      (SELECT PERSON_NAME FROM TM_USER WHERE ACCOUNT=I.BUYER) BUYER,\n" + 
									"      (SELECT DIC_VALUE FROM DIC_TREE WHERE ID = OS.SETTLE_STATUS) SETTLE_STATUS,  SD.DISTRIBUTION_NO \n" + 
									"  FROM PT_BU_PCH_ORDER_SPLIT OS, PT_BU_STOCK_IN I, PT_BU_STOCK_IN_DTL DT, PT_BA_INFO BI, PT_BU_PCH_ORDER_SPLIT_DTL SD \n" + 
									" WHERE " + 
									" 		OS.SPLIT_ID = I.ORDER_ID AND I.IN_ID = DT.IN_ID AND OS.SPLIT_ID = SD.SPLIT_ID AND DT.PART_ID = SD.PART_ID \n" + 
									"       AND DT.PART_ID = BI.PART_ID AND I.IN_STATUS = 201502 and NVL(IN_AMOUNT,0) > 0 \n" + 
									"       AND I.PRINT_STATUS = 201702 AND I.STATUS = 100201 " + " AND " + conditions + " ORDER BY I.IN_DATE DESC"
					);
	}
	
	
	
	public BaseResultSet getAmount(PageManager page, String conditions) throws Exception
    {
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT SUM(NVL(DT.IN_AMOUNT,0)*NVL(DT.PLAN_PRICE,0)) IN_AMOUNT,\n" );
        sql.append("       SUM(DT.PCH_AMOUNT) PCH_AMOUNT\n" );
        sql.append("  FROM PT_BU_PCH_ORDER_SPLIT     OS,\n" );
        sql.append("       PT_BU_STOCK_IN            I,\n" );
        sql.append("       PT_BU_STOCK_IN_DTL        DT\n" );
        sql.append(" WHERE OS.SPLIT_ID = I.ORDER_ID\n" );
        sql.append("   AND I.IN_ID = DT.IN_ID\n" );
        sql.append("   AND I.IN_STATUS = 201502\n" );
        sql.append("   AND I.PRINT_STATUS = 201702\n" );
        sql.append("   AND I.STATUS = 100201\n" );
        sql.append("   AND "+conditions+"\n");
        sql.append("   AND NVL(IN_AMOUNT, 0) > 0\n" );
        sql.append(" ORDER BY I.IN_DATE DESC");
        bs = factory.select(sql.toString(), page);
        return bs;
    }
}
	