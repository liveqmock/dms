package com.org.dms.dao.part.storageMng.search;

import java.util.Map;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PartAcceptReferenceMngDao extends BaseDAO{
	public  static final PartAcceptReferenceMngDao getInstance(ActionContext atx)
    {
		PartAcceptReferenceMngDao dao = new PartAcceptReferenceMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public BaseResultSet acceptSearch(PageManager page, User user,String warehouseId,String warehousetype,String conditions) throws Exception {
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT X.*,\n" );
        sql.append("       NVL(Y.UPPER_LIMIT, 0) UPPER_LIMIT,\n" );
        sql.append("       NVL(X.ORDER_COUNT - X.AVAILABLE_AMOUNT + Y.UPPER_LIMIT,0) AS WILL_ACCEPT_COUNT\n" );
        sql.append("  FROM (SELECT NVL(T.AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT,\n" );
        sql.append("               T.PART_ID,\n" );
        sql.append("               T.PART_CODE,\n" );
        sql.append("               T.PART_NAME,\n" );
        sql.append("               NVL(T1.ORDER_COUNT, 0) ORDER_COUNT,\n" );
        sql.append("               T.WAREHOUSE_ID\n" );
        sql.append("          FROM PT_BU_STOCK T,\n" );
        sql.append("               (SELECT SUM(ORDER_COUNT) ORDER_COUNT, PART_ID\n" );
        sql.append("                  FROM PT_BU_SALE_ORDER_DTL A, PT_BU_SALE_ORDER B\n" );
        sql.append("                 WHERE A.ORDER_ID = B.ORDER_ID\n" );
        sql.append("                   AND B.IF_CHANEL_ORDER = 100102\n" );
        if(warehousetype.equals("100101")){
        	sql.append("                   AND B.ORDER_TYPE NOT IN (203708,203710)\n" );
        }else if(warehousetype.equals("100102")){
        	sql.append("                   AND B.ORDER_TYPE = 203708\n" );
        }else{
        	sql.append("                   AND B.ORDER_TYPE = 203710\n" );
        }
        sql.append("                   AND B.IF_DELAY_ORDER = 100101\n" );
        sql.append("                   AND B.ORDER_STATUS = 202202\n" );
        sql.append("                 GROUP BY PART_ID) T1\n" );
        sql.append("         WHERE T.PART_ID = T1.PART_ID AND T.WAREHOUSE_ID = "+warehouseId+" AND "+conditions+") X\n" );
        sql.append("  LEFT JOIN PT_BA_STOCK_SAFESTOCKS Y\n" );
        sql.append("    ON X.WAREHOUSE_ID = Y.STOCK_ID\n" );
        sql.append("   AND X.PART_ID = Y.PART_ID");
        bs = factory.select(sql.toString(), page);
        return bs;
    }
	
	public QuerySet download(String warehouseId,String warehousetype,String conditions) throws Exception {
		StringBuffer sql= new StringBuffer();
        sql.append("SELECT X.*,\n" );
        sql.append("       NVL(Y.UPPER_LIMIT, 0) UPPER_LIMIT,\n" );
        sql.append("       NVL(X.ORDER_COUNT - X.AVAILABLE_AMOUNT + Y.UPPER_LIMIT,0) AS WILL_ACCEPT_COUNT\n" );
        sql.append("  FROM (SELECT NVL(T.AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT,\n" );
        sql.append("               T.PART_ID,\n" );
        sql.append("               T.PART_CODE,\n" );
        sql.append("               T.PART_NAME,\n" );
        sql.append("               NVL(T1.ORDER_COUNT, 0) ORDER_COUNT,\n" );
        sql.append("               T.WAREHOUSE_ID\n" );
        sql.append("          FROM PT_BU_STOCK T,\n" );
        sql.append("               (SELECT SUM(ORDER_COUNT) ORDER_COUNT, PART_ID\n" );
        sql.append("                  FROM PT_BU_SALE_ORDER_DTL A, PT_BU_SALE_ORDER B\n" );
        sql.append("                 WHERE A.ORDER_ID = B.ORDER_ID\n" );
        sql.append("                   AND B.IF_CHANEL_ORDER = 100102\n" );
        if(warehousetype.equals("100101")){
        	sql.append("                   AND B.ORDER_TYPE NOT IN (203708,203710)\n" );
        }else if(warehousetype.equals("100102")){
        	sql.append("                   AND B.ORDER_TYPE = 203708\n" );
        }else{
        	sql.append("                   AND B.ORDER_TYPE = 203710\n" );
        }
        sql.append("                   AND B.IF_DELAY_ORDER = 100101\n" );
        sql.append("                   AND B.ORDER_STATUS = 202202\n" );
        sql.append("                 GROUP BY PART_ID) T1\n" );
        sql.append("         WHERE T.PART_ID = T1.PART_ID AND T.WAREHOUSE_ID = "+warehouseId+" AND "+conditions+") X\n" );
        sql.append("  LEFT JOIN PT_BA_STOCK_SAFESTOCKS Y\n" );
        sql.append("    ON X.WAREHOUSE_ID = Y.STOCK_ID\n" );
        sql.append("   AND X.PART_ID = Y.PART_ID");
		return factory.select(null, sql.toString());
	}

}
