package com.org.dms.dao.part.storageMng.search;

import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class OutBillModSearchMngDao extends BaseDAO{
	public  static final OutBillModSearchMngDao getInstance(ActionContext atx)
    {
		OutBillModSearchMngDao dao = new OutBillModSearchMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

	public BaseResultSet outBillModSearch(PageManager page, User user,String conditions) throws Exception {
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T1.ORDER_NO,\n" );
        sql.append("       T1.ORG_CODE,\n" );
        sql.append("       T1.ORG_NAME,\n" );
        sql.append("       T2.PART_CODE,\n" );
        sql.append("       T2.PART_NAME,\n" );
        sql.append("       T.OLD_COUNT,\n" );
        sql.append("       T.MOD_COUNT,\n" );
        sql.append("       T.DEF_COUNT,\n" );
        sql.append("       T.MOD_MAN,\n" );
        sql.append("       TO_CHAR(T.MOD_DATE,'YYYY-MM-DD HH24:MI:SS') MOD_DATE,\n" );
        sql.append("       T.KEEP_MAN\n" );
        sql.append("  FROM PT_BU_STOCK_OUT_MOD_LOG T, PT_BU_SALE_ORDER T1, PT_BA_INFO T2\n" );
        sql.append(" WHERE T.ORDER_ID = T1.ORDER_ID\n" );
        sql.append("   AND T.PART_ID = T2.PART_ID AND "+conditions+"\n" );
        sql.append("   ORDER BY T2.PART_CODE,T.MOD_DATE DESC");
        bs = factory.select(sql.toString(), page);
        bs.setFieldUserID("KEEP_MAN");
        bs.setFieldUserID("MOD_MAN");
        return bs;
    }
	
	
	public QuerySet download(String conditions) throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T1.ORDER_NO,\n" );
		sql.append("       T1.ORG_CODE,\n" );
		sql.append("       T1.ORG_NAME,\n" );
		sql.append("       T2.PART_CODE,\n" );
		sql.append("       T2.PART_NAME,\n" );
		sql.append("       T.OLD_COUNT,\n" );
		sql.append("       T.MOD_COUNT,\n" );
		sql.append("       T.DEF_COUNT,\n" );
		sql.append("       T3.PERSON_NAME MOD_MAN,\n" );
		sql.append("       TO_CHAR(T.MOD_DATE, 'YYYY-MM-DD HH24:MI:SS') MOD_DATE,\n" );
		sql.append("       T4.PERSON_NAME KEEP_MAN\n" );
		sql.append("  FROM PT_BU_STOCK_OUT_MOD_LOG T,\n" );
		sql.append("       PT_BU_SALE_ORDER        T1,\n" );
		sql.append("       PT_BA_INFO              T2,\n" );
		sql.append("       TM_USER                 T3,\n" );
		sql.append("       TM_USER                 T4\n" );
		sql.append(" WHERE "+conditions+" AND T.ORDER_ID = T1.ORDER_ID\n" );
		sql.append("   AND T.PART_ID = T2.PART_ID\n" );
		sql.append("   AND T.MOD_MAN = T3.ACCOUNT\n" );
		sql.append("   AND T.KEEP_MAN = T4.ACCOUNT\n" );
		sql.append(" ORDER BY T2.PART_CODE, T.MOD_DATE DESC");
		return factory.select(null, sql.toString());
	}
}
