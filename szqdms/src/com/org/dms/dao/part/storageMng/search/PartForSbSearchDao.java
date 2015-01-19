package com.org.dms.dao.part.storageMng.search;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PartForSbSearchDao extends BaseDAO{
	public  static final PartForSbSearchDao getInstance(ActionContext atx)
    {
		PartForSbSearchDao dao = new PartForSbSearchDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	public BaseResultSet partSearch(PageManager page, User user, String conditions) throws Exception
    {
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
		sql.append("SELECT T1.PART_ID,\n" );
		sql.append("       T1.PART_CODE,\n" );
		sql.append("       T1.PART_NAME,\n" );
		sql.append("       T1.ORDER_COUNT,\n" );
		sql.append("       T.ORDER_NO,\n" );
		sql.append("       T3.DIC_VALUE    IF_DELAY_ORDER,\n" );
		sql.append("       T2.DIC_VALUE    ORDER_STATUS,\n" );
		sql.append("       T.APPLY_DATE,\n" );
		sql.append("       T.DELIVERY_ADDR,\n" );
		sql.append("       T.LINK_MAN,\n" );
		sql.append("       T.PHONE,\n" );
		sql.append("       T1.REMARKS\n" );
		sql.append("  FROM PT_BU_SALE_ORDER     T,\n" );
		sql.append("       PT_BU_SALE_ORDER_DTL T1,\n" );
		sql.append("       DIC_TREE             T2,\n" );
		sql.append("       DIC_TREE             T3\n" );
		sql.append(" WHERE T.ORDER_ID = T1.ORDER_ID\n" );
		sql.append("   AND T.ORDER_TYPE = 203709\n" );
		sql.append("   AND T.IF_CHANEL_ORDER = 100102\n" );
		sql.append("   AND T.ORDER_STATUS IN (202202, 202203)\n" );
		sql.append("   AND T.IF_DELAY_ORDER = T3.ID\n" );
		sql.append("   AND T.ORDER_STATUS = T2.ID AND "+conditions+"\n");
		sql.append("	ORDER BY T1.PART_CODE");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	bs.setFieldDic("ORDER_STATUS", "DDZT");
    	bs.setFieldDic("IF_DELAY_ORDER", "SF");
    	return bs;
    }
	public QuerySet download(String conditions) throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T1.PART_ID,\n" );
		sql.append("       T1.PART_CODE,\n" );
		sql.append("       T1.PART_NAME,\n" );
		sql.append("       T1.ORDER_COUNT,\n" );
		sql.append("       T.ORDER_NO,\n" );
		sql.append("       T3.DIC_VALUE    IF_DELAY_ORDER,\n" );
		sql.append("       T2.DIC_VALUE    ORDER_STATUS,\n" );
		sql.append("       T.APPLY_DATE,\n" );
		sql.append("       T.DELIVERY_ADDR,\n" );
		sql.append("       T.LINK_MAN,\n" );
		sql.append("       T.PHONE,\n" );
		sql.append("       T1.REMARKS\n" );
		sql.append("  FROM PT_BU_SALE_ORDER     T,\n" );
		sql.append("       PT_BU_SALE_ORDER_DTL T1,\n" );
		sql.append("       DIC_TREE             T2,\n" );
		sql.append("       DIC_TREE             T3\n" );
		sql.append(" WHERE T.ORDER_ID = T1.ORDER_ID\n" );
		sql.append("   AND T.ORDER_TYPE = 203709\n" );
		sql.append("   AND T.IF_CHANEL_ORDER = 100102\n" );
		sql.append("   AND T.ORDER_STATUS IN (202202, 202203)\n" );
		sql.append("   AND T.IF_DELAY_ORDER = T3.ID\n" );
		sql.append("   AND T.ORDER_STATUS = T2.ID AND "+conditions+"");
		sql.append("	ORDER BY T1.PART_CODE");
		return factory.select(null, sql.toString());
	}

}
