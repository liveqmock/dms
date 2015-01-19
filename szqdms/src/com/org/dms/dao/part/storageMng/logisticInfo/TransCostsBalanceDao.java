package com.org.dms.dao.part.storageMng.logisticInfo;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuTransCostsBalanceVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

public class TransCostsBalanceDao extends BaseDAO
{
    //定义instance
    public  static final TransCostsBalanceDao getInstance(ActionContext atx)
    {
    	TransCostsBalanceDao dao = new TransCostsBalanceDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断(配件发运单)运费调整结算是否存在
     * @throws Exception
     * @Author suoxiuli 2014-08-25
     */
	/*public QuerySet checkTransCostsBala(String shipId) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select count(1) nums \n");
    	sql.append(" from pt_bu_trans_costs_balance \n");
    	sql.append(" where ship_id = '" + shipId +"'");
    	sql.append(" and status = '" + DicConstant.YXBS_01 +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }*/
	
    /**
	 * (配件发运单)运费调整结算新增
	 * @throws Exception
     * @Author suoxiuli 2014-08-25
	 */
    public boolean insertTransCostsBala(PtBuTransCostsBalanceVO vo, String shipId)
            throws Exception
    {
    	//1、发运单的发运状态调整为已结算
    	BaseResultSet bs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BU_ORDER_SHIP \n");
    	sql.append(" set SHIP_STATUS = '" + DicConstant.FYDZT_07 + "' \n");
    	sql.append(" WHERE SHIP_ID = '" + shipId + "' \n");
    	factory.update(sql.toString(), null);

    	//2、往运费调整结算表里面添加数据
    	return factory.insert(vo);
    }

	/**
	 * 查询配件回执确认后的发运单
	 * @throws Exception
     * @Author suoxiuli 2014-08-22
	 */
    public BaseResultSet search(PageManager page, String conditions, String userAccount) throws Exception
    {
    	String wheres = conditions;
		wheres += " AND A.STATUS = 100201";
    	wheres += "   AND A.SHIP_STATUS = "+ DicConstant.FYDZT_06;
    	wheres += "   AND A.SHIP_ID = B.SHIP_ID";
    	wheres += "   AND C.STATUS = 100201";
    	wheres += "   AND B.ORDER_ID = C.ORDER_ID";
    	wheres += "   AND D.STATUS = 100201";
    	wheres += "   AND C.PROVINCE_CODE = D.PROVINCE_CODE";
    	wheres += "   AND C.CITY_CODE = D.CITY_CODE";
    	wheres += "   AND C.COUNTRY_CODE = D.COUNTRY_CODE";
    	wheres += " GROUP BY A.SHIP_ID,";
    	wheres += "          A.SHIP_NO,";
    	wheres += "          A.CARRIER_ID,";
    	wheres += "          A.CARRIER_CODE,";
    	wheres += "          A.CARRIER_NAME,";
    	wheres += "          A.RECEIVE_TIME,";
    	wheres += "          '未结算',";
    	wheres += "          A.CONFIRM_USER,";
    	wheres += "          A.CONFIRM_TIME,";
    	wheres += "          A.STATUS";
    	wheres += "  ORDER BY A.CONFIRM_TIME, A.SHIP_ID DESC";
        page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.SHIP_ID,\n" );
    	sql.append("       A.SHIP_NO,\n" );
    	sql.append("       A.CARRIER_ID,\n" );
    	sql.append("       A.CARRIER_CODE,\n" );
    	sql.append("       A.CARRIER_NAME,\n" );
    	sql.append("       A.RECEIVE_TIME,\n" );
    	sql.append("       '未结算' AS BALANCE_STATUS,\n" );
    	sql.append("       A.CONFIRM_USER,\n" );
    	sql.append("       A.CONFIRM_TIME,\n" );
    	sql.append("       A.STATUS,\n" );
    	sql.append("       SUM(D.TRANS_MILES * D.UNIT_PRICE) AS PLAN_COSTS\n" );
    	sql.append("  FROM PT_BU_ORDER_SHIP     A,\n" );
    	sql.append("       PT_BU_ORDER_SHIP_DTL B,\n" );
    	sql.append("       PT_BU_SALE_ORDER     C,\n" );
    	sql.append("       PT_BA_TRANS_MILES    D\n" );

    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("STATUS","YXBS");
    	bs.setFieldDic("SHIP_STATUS","FYDZT");
    	//bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	bs.setFieldDateFormat("RECEIVE_TIME", "yyyy-MM-dd");
    	bs.setFieldUserID("CONFIRM_USER");
    	bs.setFieldDateFormat("CONFIRM_TIME", "yyyy-MM-dd");
    	
    	return bs;
    }
}

