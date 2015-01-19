package com.org.dms.dao.part.storageMng.shipMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuDealerStockChangeVO;
import com.org.dms.vo.part.PtBuDealerStockVO;
import com.org.dms.vo.part.PtBuOrderShipCarrierVO;
import com.org.dms.vo.part.PtBuOrderShipVO;
import com.org.dms.vo.part.PtBuSaleOrderDtlVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.dms.vo.part.PtBuShipVanBoxRlVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 发运单出库Dao
 *
 * @user : lichuang
 * @date : 2014-08-01
 */
public class ShipOutDao extends BaseDAO {
    //定义instance
    public static final ShipOutDao getInstance(ActionContext atx) {
        ShipOutDao dao = new ShipOutDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 查询发运单
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchShip(PageManager page, User user, String conditions) throws Exception {
        String wheres = conditions;
        wheres += "  AND A.SHIP_STATUS = " + DicConstant.FYDZT_03 + "\n";
        wheres += "  AND A.STATUS = " + DicConstant.YXBS_01 + "\n";
        wheres += "  AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n";
        wheres += " ORDER BY A.CREATE_TIME DESC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.SHIP_ID,\n");
        sql.append("       A.SHIP_NO,\n");
        sql.append("       A.SHIP_STATUS,\n");
        sql.append("       A.CARRIER_ID,\n");
        sql.append("       A.CARRIER_CODE,\n");
        sql.append("       A.CARRIER_NAME,\n");
        sql.append("       A.LICENSE_PLATE,\n");
        sql.append("       A.DRIVER_NAME,\n");
        sql.append("       A.DRIVER_PHONE,\n");
        sql.append("       A.CARRIER_REMARKS,\n");
        sql.append("       A.LINK_MAN,\n");
        sql.append("       A.PHONE,\n");
        sql.append("       A.REMARKS,\n");
        sql.append("       A.CREATE_USER,\n");
        sql.append("       A.CREATE_TIME\n");
        sql.append("  FROM PT_BU_ORDER_SHIP A\n");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldUserID("CREATE_USER");
        bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
        return bs;
    }

    /**
     * 查询发运单明细
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchShipDtl(PageManager page, User user, String conditions, String shipId) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);

//        wheres.append("   AND A.ORDER_ID = B.ORDER_ID\n");
//        wheres.append("   AND A.ORDER_ID = C.ORDER_ID\n");
//        wheres.append("   AND B.PART_ID = C.PART_ID\n");
//        wheres.append("   AND A.ORDER_ID = D.ORDER_ID\n");
        wheres.append("   AND A.ORDER_ID = T.ORDER_ID\n");
//        wheres.append("   AND A.ORDER_ID = C.ORDER_ID\n");
//        wheres.append("   AND B.PART_ID = C.PART_ID\n");
        wheres.append("   AND A.ORDER_ID = D.ORDER_ID\n");
        wheres.append("   AND D.SHIP_ID = " + shipId + "\n");
        wheres.append("   AND A.STATUS = " + DicConstant.YXBS_01 + "\n");
        wheres.append("   AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n");
//        wheres.append(" GROUP BY A.ORDER_ID,\n");
//        wheres.append("          A.ORDER_NO,\n");
//        wheres.append("          A.ORDER_TYPE,\n");
//        wheres.append("          A.ORG_NAME,\n");
//        wheres.append("          A.DELIVERY_ADDR,\n");
//        wheres.append("          A.LINK_MAN,\n");
//        wheres.append("          A.PHONE,\n");
//        wheres.append("          A.CREATE_TIME,\n");
//        wheres.append("          D.DTL_ID\n");
        wheres.append(" ORDER BY A.CREATE_TIME DESC\n");

        page.setFilter(wheres.toString());
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.ORDER_ID,\n");
        sql.append("       A.ORDER_NO,\n");
        sql.append("       A.ORDER_TYPE,\n");
        sql.append("       A.ORG_NAME,\n");
        sql.append("       T.COUNT,\n");
        sql.append("       T.AMOUNT,\n");
        sql.append("       A.DELIVERY_ADDR,\n");
        sql.append("       A.LINK_MAN,\n");
        sql.append("       A.PHONE,\n");
        sql.append("       D.DTL_ID\n");
        sql.append("  FROM PT_BU_SALE_ORDER A,\n");
        sql.append(" (SELECT B.ORDER_ID,\n");
        sql.append("     SUM(NVL(C.COUNT, 0)) COUNT,\n");
        sql.append("     SUM(NVL(C.COUNT, 0) * B.UNIT_PRICE) AMOUNT\n");
        sql.append("  FROM PT_BU_SALE_ORDER_DTL B, PT_BU_BOX_UP C\n");
        sql.append(" WHERE B.PART_ID = C.PART_ID\n");
        sql.append("   AND B.ORDER_ID = C.ORDER_ID\n");
        sql.append("  GROUP BY B.ORDER_ID) T,\n");
        sql.append("  PT_BU_ORDER_SHIP_DTL D\n");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("ORDER_TYPE", DicConstant.DDLX);
        return bs;
    }

    /**
     * 修改发运单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateShip(PtBuOrderShipVO vo)
            throws Exception {
        return factory.update(vo);
    }

    /**
     * 修改发运单对应的订单的发运状态为已发运
     *
     * @param SHIP_ID 发运单ID
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateSaleOrder(String SHIP_ID, User user)
            throws Exception {
        StringBuilder sql= new StringBuilder();
        sql.append("UPDATE PT_BU_SALE_ORDER A\n" );
        sql.append("   SET A.SHIP_STATUS = "+DicConstant.DDFYZT_06+", A.UPDATE_USER = '"+user.getAccount()+"',A.DELIVERY_DATE=SYSDATE, A.UPDATE_TIME = SYSDATE\n" );
        sql.append(" WHERE 1 = 1\n" );
        sql.append("   AND EXISTS (SELECT 1\n" );
        sql.append("          FROM PT_BU_ORDER_SHIP_DTL B\n" );
        sql.append("         WHERE B.ORDER_ID = A.ORDER_ID\n" );
        sql.append("           AND B.SHIP_ID = "+SHIP_ID+")\n");

        return factory.update(sql.toString(), null);
    }

    /**
     * 修改发运单对应的订单的明细的发运数量
     *
     * @param SHIP_ID 发运单ID
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateSaleOrderDtl(String SHIP_ID, User user)
            throws Exception {
        StringBuilder sql= new StringBuilder();
        sql.append("UPDATE PT_BU_SALE_ORDER_DTL A\n" );
        sql.append("   SET A.DELIVERY_COUNT =\n" );
        sql.append("       (SELECT SUM(B.COUNT) COUNT\n" );
        sql.append("          FROM PT_BU_BOX_UP B\n" );
        sql.append("         WHERE B.ORDER_ID = A.ORDER_ID\n" );
        sql.append("           AND B.PART_ID = A.PART_ID),\n" );
        sql.append("       A.UPDATE_USER    = '"+user.getAccount()+"',\n" );
        sql.append("       A.UPDATE_TIME    = SYSDATE\n" );
        sql.append(" WHERE 1 = 1\n" );
        sql.append("   AND EXISTS (SELECT 1\n" );
        sql.append("          FROM PT_BU_ORDER_SHIP_DTL C\n" );
        sql.append("         WHERE C.SHIP_ID = "+SHIP_ID+"\n" );
        sql.append("           AND C.ORDER_ID = A.ORDER_ID)\n");

        return factory.update(sql.toString(), null);
    }
    /**
     * 修改发运单对应的订单的原订单明细的发运数量
     *
     * @param SHIP_ID 发运单ID
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateSaleOrderAmount(String SHIP_ID, User user)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_SALE_ORDER A\n" );
    	sql.append("   SET A.REAL_AMOUNT =\n" );
    	sql.append("       (SELECT SUM(B.COUNT * C.UNIT_PRICE) COUNT\n" );
    	sql.append("          FROM PT_BU_BOX_UP B, PT_BU_SALE_ORDER_DTL C\n" );
    	sql.append("         WHERE B.ORDER_ID = A.ORDER_ID\n" );
    	sql.append("           AND B.ORDER_ID = C.ORDER_ID\n" );
    	sql.append("           AND B.PART_ID = C.PART_ID),\n" );
    	sql.append("       A.UPDATE_USER    = '"+user.getAccount()+"',\n" );
    	sql.append("       A.UPDATE_TIME    = SYSDATE\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND EXISTS (SELECT 1\n" );
    	sql.append("          FROM PT_BU_ORDER_SHIP_DTL C\n" );
    	sql.append("         WHERE C.SHIP_ID = "+SHIP_ID+"\n" );
    	sql.append("           AND C.ORDER_ID = A.ORDER_ID)");
        return factory.update(sql.toString(), null);
    }
    /**
     * 修改发运单对应的订单的原订单明细的发运数量
     *
     * @param SHIP_ID 发运单ID
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateSourceSaleOrderDtl(String orderId, String dirSourceOrderId) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("UPDATE PT_BU_SALE_ORDER_DTL A\n" );
    	sql.append("   SET A.DELIVERY_COUNT =\n" );
    	sql.append("       (SELECT DELIVERY_COUNT\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER_DTL B\n" );
    	sql.append("         WHERE B.ORDER_ID = '"+orderId+"'\n" );
    	sql.append("           AND A.DTL_ID = B.DTL_ID)\n" );
    	sql.append(" WHERE A.ORDER_ID = '"+dirSourceOrderId+"'");
        return factory.update(sql.toString(), null);
    }

	public QuerySet getOrderId(String SHIP_ID) throws Exception {
		StringBuilder sql= new StringBuilder();
		sql.append("SELECT ORDER_ID,DIR_SOURCE_ORDER_ID\n" );
		sql.append("  FROM PT_BU_SALE_ORDER\n" );
		sql.append(" WHERE ORDER_ID IN (SELECT ORDER_ID FROM PT_BU_ORDER_SHIP_DTL WHERE SHIP_ID = "+SHIP_ID+")\n" );
		sql.append("   AND NVL(DIR_SOURCE_ORDER_ID, 0) <> 0");
		return factory.select(null, sql.toString());
	}

	public QuerySet getSaleInfo(String orderId) throws Exception {
		QuerySet qs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT A.DIR_SOURCE_ORDER_ID,A.ORG_ID,A.ORG_CODE,A.DIR_SOURCE_ORDER_NO,A.ORDER_NO,A.CUSTORM_NAME, A.DELIVERY_ADDR, A.PHONE, NVL(B.AMOUNT,0)AMOUNT, NVL(B.COUNT,0)COUNT\n");
		sql.append("  FROM PT_BU_SALE_ORDER A,\n");
		sql.append("(SELECT SUM(T2.REAL_COUNT * T.UNIT_PRICE) AMOUNT,SUM(T2.REAL_COUNT) COUNT,T.ORDER_ID\n");
		sql.append("FROM PT_BU_SALE_ORDER_DTL T,PT_BU_ISSUE_ORDER T1,PT_BU_ISSUE_ORDER_DTL T2\n");
		sql.append("WHERE 1 = 1 AND T.ORDER_ID = T1.ORDER_ID AND T1.ISSUE_ID = T2.ISSUE_ID\n");
		sql.append("GROUP BY T.ORDER_ID) B");
		sql.append(" WHERE A.ORDER_ID = B.ORDER_ID\n");
		sql.append(" AND A.ORDER_ID = " + orderId + "");
		qs = factory.select(null, sql.toString());
		return qs;
	}

	public QuerySet getSaleDtlInfo(String orderId) throws Exception {
		QuerySet qs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.PART_ID,\n");
		sql.append("       T.PART_CODE,\n");
		sql.append("       T.PART_NAME,\n");
		sql.append("       T.SUPPLIER_ID,\n");
		sql.append("       T.SUPPLIER_CODE,\n");
		sql.append("       T.SUPPLIER_NAME,\n");
		sql.append("       NVL(T.DELIVERY_COUNT,0) DELIVERY_COUNT,\n");
		sql.append("       T.DTL_ID\n");
		sql.append("  FROM PT_BU_SALE_ORDER_DTL T\n");
		sql.append("  WHERE T.ORDER_ID = " + orderId + " AND NVL(T.DELIVERY_COUNT,0) >0");
		qs = factory.select(null, sql.toString());
		return qs;
	}

	public boolean updateInStorageOrder(PtBuSaleOrderDtlVO vo) throws Exception {
		return factory.update(vo);
	}

	public QuerySet dealerstock(String part_id, String supplierId, String orgId)
			throws Exception {

		QuerySet qs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  STOCK_ID,NVL(AMOUNT,0) AMOUNT,NVL(AVAILABLE_AMOUNT,0) AVAILABLE_AMOUNT FROM PT_BU_DEALER_STOCK T WHERE T.PART_ID ="
				+ part_id
				+ " AND T.SUPPLIER_ID = "
				+ supplierId
				+ " AND T.ORG_ID =" + orgId);
		qs = factory.select(null, sql.toString());
		return qs;
	}

	public boolean updateDealerStock(PtBuDealerStockVO vo) throws Exception {
		return factory.update(vo);
	}

	public boolean orderStockInsert(PtBuDealerStockVO vo) throws Exception {
//		String StockId = SequenceUtil.getCommonSerivalNumber(factory);
//		vo.setStockId(StockId);
		return factory.insert(vo);
	}

	public boolean orderStockChangeInsert(PtBuDealerStockChangeVO vo)
			throws Exception {
		String ChangeId = SequenceUtil.getCommonSerivalNumber(factory);
		vo.setChangeId(ChangeId);
		;
		return factory.insert(vo);
	}

	public boolean updateSourceSaleOrder(PtBuSaleOrderVO pPtBuSaleOrderVO,
			User pUser) throws Exception {

		// 修改销售订单表sql
		// 发运单状态-已发运
		String sql = " UPDATE \n" + "     PT_BU_SALE_ORDER \n" + " SET \n"
				+ "     SHIP_STATUS = " + DicConstant.DDFYZT_06 + ",\n"
				+ "     UPDATE_USER='" + pUser.getAccount() + "',\n"
				//+ "     ORDER_STATUS = " + DicConstant.DDZT_06 + ",\n"
				+ "     DELIVERY_DATE=sysdate,\n"
				+ "     UPDATE_TIME=sysdate \n" + " WHERE \n"
				+ "     ORDER_ID='" + pPtBuSaleOrderVO.getOrderId() + "'\n";

		return factory.update(sql, null);
	}

	public QuerySet getSourceOrderDtl(String sourceOrderId, String PART_ID)
			throws Exception {

		QuerySet qs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.DTL_ID FROM PT_BU_SALE_ORDER_DTL T WHERE T.ORDER_ID = "
				+ sourceOrderId + " AND T.PART_ID = " + PART_ID + "");
		qs = factory.select(null, sql.toString());
		return qs;
	}

	public boolean updateSourceSaleOrderDtl(PtBuSaleOrderDtlVO vo)
			throws Exception {
		return factory.update(vo);
	}

    /**
     * 校验发运单是否上传附件
     *
     * @param SHIP_ID  发运单ID
     * @param user
     * @return
     * @throws Exception
     */
    public Boolean checkAttIsUploaded(String SHIP_ID, User user) throws Exception {
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT 1\n" );
        sql.append("  FROM FS_FILESTORE\n" );
        sql.append(" WHERE 1 = 1\n" );
        sql.append("   AND FJZT = '1'\n" );
        sql.append("   AND YWZJ = "+SHIP_ID+"\n");

        QuerySet qs = factory.select(null, sql.toString());
        if (qs.getRowCount() > 0) {
           return true;
        }
        return false;
    }

    /**
     * 查询承运信息
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchCarrier(PageManager page, User user, String conditions, String shipId) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);

        wheres.append("   AND A.SHIP_ID = " + shipId + "\n");
        wheres.append("   ORDER BY LICENSE_PLATE\n");
        page.setFilter(wheres.toString());
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.*\n");
        sql.append("  FROM PT_BU_ORDER_SHIP_CARRIER A\n");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDateFormat("EXPECT_DATE", "yyyy-MM-dd");
        return bs;
    }
    public BaseResultSet searchBoxNo(PageManager page, User user, String conditions, String VEHICLE_ID,String SHIP_ID) throws Exception {

    	String wheres = conditions;
    	wheres +="ORDER BY X.BOX_NO";
    	page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT X.BOX_NO, X.ORDER_ID, X.ORDER_NO, X.SHIP_NO,X.SHIP_ID,X.PART_ID,X.PART_CODE,X.PART_NAME,X.COUNT, ROWNUM AS T_KEY\n" );
        sql.append("   FROM (SELECT  T.BOX_NO BOX_NO, T1.ORDER_ID, T.ORDER_NO, T2.SHIP_NO,T2.SHIP_ID,T.PART_ID,T.PART_CODE,T.PART_NAME,T.COUNT\n" );
        sql.append("          FROM PT_BU_BOX_UP T, PT_BU_ORDER_SHIP_DTL T1, PT_BU_ORDER_SHIP T2\n" );
        sql.append("         WHERE T.ORDER_ID = T1.ORDER_ID\n" );
        sql.append("           AND T1.SHIP_ID = T2.SHIP_ID\n" );
        sql.append("           AND NOT EXISTS (SELECT 1\n" );
        sql.append("                  FROM PT_BU_SHIP_VAN_BOX_RL A\n" );
        sql.append("                 WHERE A.BOX_NO = T.BOX_NO\n" );
        sql.append("                   AND A.SHIP_ID = T1.SHIP_ID AND A.VEHICLE_ID IN (SELECT B.VEHICLE_ID FROM  PT_BU_ORDER_SHIP_CARRIER B WHERE B.SHIP_ID = "+SHIP_ID+"))\n" );
        sql.append("           AND T1.SHIP_ID = "+SHIP_ID+") X");
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        return bs;
    }
    public boolean insertRl(PtBuShipVanBoxRlVO vo)
    		throws Exception
    {
    	return factory.insert(vo);
    }
    public QuerySet downloadOrder(String orderId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT  T.PART_CODE,T1.ORDER_NO, T.PART_NAME,T4.DIC_VALUE UNIT,T.ORDER_COUNT,T2.COUNT AUDIT_COUNT,T2.BOX_NO\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_DTL T,\n" );
    	sql.append("       PT_BU_SALE_ORDER     T1,\n" );
    	sql.append("       PT_BU_BOX_UP         T2,\n" );
    	sql.append("       PT_BA_INFO           T3,\n" );
    	sql.append("       DIC_TREE             T4\n" );
    	sql.append(" WHERE T.ORDER_ID = T1.ORDER_ID\n" );
    	sql.append("   AND T.ORDER_ID = T2.ORDER_ID\n" );
    	sql.append("   AND T.PART_ID = T2.PART_ID\n" );
    	sql.append("   AND T.PART_ID = T3.PART_ID\n" );
    	sql.append("   AND T3.UNIT = T4.ID\n" );
    	sql.append("   AND T.ORDER_ID = "+orderId+"\n" );
    	sql.append("   ORDER BY PART_CODE");
	    return factory.select(null, sql.toString());
		}
    public BaseResultSet boxDetail(PageManager page, User user, String conditions, String VEHICLE_ID,String SHIP_ID) throws Exception {

        //定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.BOX_NO,\n" );
        sql.append("       T.COUNT,\n" );
        sql.append("       T.PART_CODE,\n" );
        sql.append("       T1.ORDER_ID,\n" );
        sql.append("       T.ORDER_NO,\n" );
        sql.append("       T2.SHIP_NO,\n" );
        sql.append("       T2.SHIP_ID\n" );
        sql.append("  FROM PT_BU_BOX_UP T, PT_BU_ORDER_SHIP_DTL T1, PT_BU_ORDER_SHIP T2\n" );
        sql.append(" WHERE T.ORDER_ID = T1.ORDER_ID\n" );
        sql.append("   AND T1.SHIP_ID = T2.SHIP_ID\n" );
        sql.append("   AND EXISTS (SELECT 1\n" );
        sql.append("          FROM PT_BU_SHIP_VAN_BOX_RL A\n" );
        sql.append("         WHERE A.BOX_NO = T.BOX_NO\n" );
        sql.append("           AND A.SHIP_ID = T1.SHIP_ID\n" );
        sql.append("           AND A.VEHICLE_ID = "+VEHICLE_ID+")\n" );
        sql.append("   AND T1.SHIP_ID = "+SHIP_ID+"");
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        return bs;
    }
    
    public BaseResultSet getTransReceipt(PageManager page, User user, String conditions, String SHIP_ID,String VEHICLE_ID ) throws Exception {
        //定义返回结果集
        BaseResultSet bs = null;
        //执行方法，不需要传递conn参数
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.*,B.ORDER_NOS,B.ADDRS,C.COUNT\n" );
        sql.append("  FROM (SELECT A.DRIVER_NAME, A.DRIVER_PHONE, B.CARRIER_NAME, B.SHIP_ID,C.PERSON_NAME NAME,A.RECEIPT_NO,A.PAPER_BOX,A.WOOD_BOX,A.NO_PACKED,A.OTHER_PACKED\n" );
        sql.append("          FROM PT_BU_ORDER_SHIP_CARRIER A, PT_BU_ORDER_SHIP B,TM_USER C\n" );
        sql.append("         WHERE 1 = 1\n" );
        sql.append("           AND A.SHIP_ID = B.SHIP_ID\n" );
        sql.append("           AND B.CREATE_USER = C.ACCOUNT\n" );
        sql.append("           AND A.SHIP_ID = "+SHIP_ID+"\n" );
        sql.append("           AND A.VEHICLE_ID = "+VEHICLE_ID+") A,\n" );
        sql.append("       (SELECT wm_concat(T2.ORDER_NO) ORDER_NOS,\n" );
        sql.append("               WM_CONCAT(DISTINCT T2.DELIVERY_ADDR ||';'|| T2.PHONE) ADDRS,\n" );
        sql.append("               T.SHIP_ID\n" );
        sql.append("          FROM PT_BU_ORDER_SHIP     T,\n" );
        sql.append("               PT_BU_ORDER_SHIP_DTL T1,\n" );
        sql.append("               PT_BU_SALE_ORDER     T2\n" );
        sql.append("         WHERE 1 = 1\n" );
        sql.append("           AND T.SHIP_ID = T1.SHIP_ID\n" );
        sql.append("           AND T1.ORDER_ID = T2.ORDER_ID\n" );
        sql.append("           AND T.SHIP_ID = "+SHIP_ID+"\n" );
        sql.append("         GROUP BY T.SHIP_ID) B,\n" );
        sql.append("(SELECT count(BOX_NO) COUNT,A.SHIP_ID\n" );
        sql.append("   FROM PT_BU_SHIP_VAN_BOX_RL A\n" );
        sql.append("  WHERE 1=1\n" );
        sql.append("    AND A.SHIP_ID = "+SHIP_ID+"\n" );
        sql.append("    AND A.VEHICLE_ID = "+VEHICLE_ID+"\n" );
        sql.append("    GROUP BY A.SHIP_ID) C");
        sql.append(" WHERE A.SHIP_ID = B.SHIP_ID\n" );
        sql.append("   AND A.SHIP_ID = C.SHIP_ID");
        bs = factory.select(sql.toString(), page);
        return bs;
    }
    public QuerySet getDelId(String VEHICLE_ID,String SHIP_ID) throws Exception {
		StringBuilder sql= new StringBuilder();
		sql.append("SELECT DEL_ID FROM PT_BU_ORDER_SHIP_CARRIER WHERE VEHICLE_ID = "+VEHICLE_ID+" AND SHIP_ID="+SHIP_ID+"\n");
		return factory.select(null, sql.toString());
	}
    public boolean updateCarrier(PtBuOrderShipCarrierVO vo)
    		throws Exception
    {
    	return factory.update(vo);
    }
    public QuerySet getCarrierNo(String SHIP_ID) throws Exception {

        //定义返回结果集
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.CARRIER_CODE FROM PT_BU_ORDER_SHIP T WHERE T.SHIP_ID ="+SHIP_ID+"");

        return factory.select(null, sql.toString());
    }
    
    public QuerySet getShipInfo(User user,String SHIP_ID,String VEHICLE_ID) throws Exception {

        //定义返回结果集
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.RECEIPT_NO,\n" );
    	sql.append("       '梁亚军' SHIPER,\n" );
    	sql.append("       T6.PERSON_NAME CHECK_USER,\n" );
    	sql.append("       TO_CHAR(SYSDATE, 'YYYY-MM-DD') PRINT_DATE,\n" );
    	sql.append("       TO_CHAR(T.EXPECT_DATE, 'YYYY-MM-DD') EXPECT_DATE,\n" );
    	sql.append("       T.WOOD_BOX,\n" );
    	sql.append("       T.PAPER_BOX,\n" );
    	sql.append("       T.NO_PACKED,\n" );
    	sql.append("       T.PLASTIC_PACKED,\n" );
    	sql.append("       T.OTHER_PACKED,\n" );
    	sql.append("       (NVL(T.WOOD_BOX,0) + NVL(T.PAPER_BOX,0) + NVL(T.NO_PACKED,0) + \n" );
    	sql.append("       NVL(T.OTHER_PACKED,0)) ALL_COUNT,\n" );
    	sql.append("       T4.DELIVERY_ADDR,\n" );
    	sql.append("       T4.LINK_MAN,\n" );
    	sql.append("       T4.PHONE,\n" );
    	sql.append("       T4.ORG_NAME,\n" );
    	sql.append("       T.SHIP_VEL_REMARKS,\n" );
    	sql.append("       T5.PHONE CARRIER_PHONE\n" );
    	sql.append("  FROM PT_BU_ORDER_SHIP_CARRIER T,\n" );
    	sql.append("       PT_BU_ORDER_SHIP T1,\n" );
    	sql.append("       PT_BU_ORDER_SHIP_DTL T2,\n" );
    	sql.append("       PT_BU_SALE_ORDER_CHECK T3,\n" );
    	sql.append("       (SELECT A.DELIVERY_ADDR, A.LINK_MAN, A.PHONE, A.ORG_NAME, B.SHIP_ID\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER A, PT_BU_ORDER_SHIP_DTL B\n" );
    	sql.append("         WHERE A.ORDER_ID = B.ORDER_ID) T4,\n" );
    	sql.append("       PT_BA_CARRIER T5,\n" );
    	sql.append("       TM_USER T6\n" );
    	sql.append(" WHERE T.SHIP_ID = T1.SHIP_ID\n" );
    	sql.append("   AND T.SHIP_ID = T2.SHIP_ID\n" );
    	sql.append("   AND T2.ORDER_ID = T3.ORDER_ID\n" );
    	sql.append("   AND T.SHIP_ID = T4.SHIP_ID\n" );
    	sql.append("   AND T3.CHECK_USER = T6.ACCOUNT\n" );
    	sql.append("   AND T1.CARRIER_ID = T5.CARRIER_ID\n" );
    	sql.append("   AND T.VEHICLE_ID = "+VEHICLE_ID+"\n" );
    	sql.append("   AND T.SHIP_ID = "+SHIP_ID+"");
        return factory.select(null, sql.toString());
    }
    public BaseResultSet searchImport(PageManager page, User user,String conditions) throws Exception {
    	String wheres = conditions + " AND USER_ACCONT='"+user.getAccount()+"' \n";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT A.* \n");
        sql.append("  FROM PT_BU_VEL_BOX_RL_TMP A");
        bs = factory.select(sql.toString(), page);
        return bs;
    }
    public boolean updateForecastDtl(String VEHICLE_ID,String SHIP_ID,User pUser,String tmpNo) throws Exception {


        // 新增配件预测明细表sql
        StringBuffer sql = new StringBuffer();
        sql.append(" INSERT INTO PT_BU_SHIP_VAN_BOX_RL \n");
        sql.append("         (RL_ID,\n");
        sql.append("         VEHICLE_ID,\n");
        sql.append("         SHIP_ID,\n");
        sql.append("         BOX_NO) \n");
        sql.append("     (SELECT \n");
        sql.append("         F_GETID()," + VEHICLE_ID + "," + SHIP_ID + ",BOX_NO\n");
        sql.append("     FROM \n");
        sql.append("          PT_BU_VEL_BOX_RL_TMP\n");
        sql.append("     WHERE  \n");
        sql.append("       USER_ACCONT='" + pUser.getAccount() + "' "+tmpNo+")");
        return factory.exec(sql.toString());
    }
    public QuerySet expData(String pConditions,User user) throws Exception {

    	String wheres = " WHERE TMP_NO IN ("+ pConditions + ") AND USER_ACCONT='"+user.getAccount()+"'\n";
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT \n");
        sql.append("     BOX_NO\n");
    	sql.append("  FROM PT_BU_VEL_BOX_RL_TMP");
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }
    
    public QuerySet getOrderNo(String SHIP_ID) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT TO_CHAR('订单号:'||','||''||','||wm_concat(T1.ORDER_NO)) ORDER_NOS\n" );
    	sql.append("FROM PT_BU_ORDER_SHIP_DTL T,PT_BU_SALE_ORDER T1\n" );
    	sql.append("WHERE T.ORDER_ID = T1.ORDER_ID\n" );
    	sql.append("AND T.SHIP_ID = "+SHIP_ID+"");
		return factory.select(null, sql.toString());
	}
    public QuerySet getDAPS(String SHIP_ID) throws Exception {
//    	StringBuffer sql= new StringBuffer();
//    	sql.append("SELECT TO_CHAR('收货人地址及电话:' || ',' ||\n" );
//    	sql.append("               WM_CONCAT(DISTINCT T2.SNAME|| ';' ||T1.LINK_MAN || ';' ||T1.DELIVERY_ADDR || ';' || T1.PHONE || '。')) DAPS\n" );
//    	sql.append("  FROM PT_BU_ORDER_SHIP_DTL T, PT_BU_SALE_ORDER T1, TM_ORG T2\n" );
//    	sql.append(" WHERE T.ORDER_ID = T1.ORDER_ID\n" );
//    	sql.append("   AND T1.ORG_ID = T2.ORG_ID\n" );
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT TO_CHAR('收货单位：'||T2.SNAME||','||'地址：'||T1.DELIVERY_ADDR||','||'联系人：'||T1.LINK_MAN||','||'联系电话：'|| T1.PHONE)DAPS\n" );
    	sql.append("  FROM PT_BU_ORDER_SHIP_DTL T, PT_BU_SALE_ORDER T1, TM_ORG T2\n" );
    	sql.append(" WHERE T.ORDER_ID = T1.ORDER_ID\n" );
    	sql.append("   AND T1.ORG_ID = T2.ORG_ID\n" );
//    	StringBuffer sql= new StringBuffer();
//    	sql.append("SELECT TO_CHAR('收货单位：' || WM_CONCAT(DISTINCT T2.SNAME) || ',' || '地址：' ||\n" );
//    	sql.append("               WM_CONCAT(DISTINCT T1.DELIVERY_ADDR ||';') || ',' || '联系人：' ||\n" );
//    	sql.append("               WM_CONCAT(DISTINCT T1.LINK_MAN ||';') || ',' || '联系电话：' ||\n" );
//    	sql.append("               WM_CONCAT(DISTINCT T1.PHONE ||';')) DAPS\n" );
//    	sql.append("  FROM PT_BU_ORDER_SHIP_DTL T, PT_BU_SALE_ORDER T1, TM_ORG T2\n" );
//    	sql.append(" WHERE T.ORDER_ID = T1.ORDER_ID\n" );
//    	sql.append("   AND T1.ORG_ID = T2.ORG_ID\n" );
//    	sql.append("   AND T.SHIP_ID = 2014112801750402");
//    	sql.append("   AND T.SHIP_ID = "+SHIP_ID+"");
//    	StringBuffer sql= new StringBuffer();
//    	sql.append("SELECT CASE\n" );
//    	sql.append("         WHEN T1.COUNTRY_NAME IS NULL THEN\n" );
//    	sql.append("          '收货单位：' || T2.ONAME || ',' || '地址：' ||\n" );
//    	sql.append("\n" );
//    	sql.append("          T1.CITY_NAME || T1.DELIVERY_ADDR || ',' || '联系人：' || T1.LINK_MAN || ',' ||\n" );
//    	sql.append("          '联系电话：' || T1.PHONE\n" );
//    	sql.append("         ELSE\n" );
//    	sql.append("          '收货单位：' || T2.SNAME || ',' || '地址：' || T1.COUNTRY_NAME ||\n" );
//    	sql.append("          T1.DELIVERY_ADDR || ',' || '联系人：' || T1.LINK_MAN || ',' || '联系电话：' ||\n" );
//    	sql.append("          T1.PHONE\n" );
//    	sql.append("       END AS DAPS\n" );
//    	sql.append("  FROM PT_BU_ORDER_SHIP_DTL T, PT_BU_SALE_ORDER T1, TM_ORG T2\n" );
//    	sql.append(" WHERE T.ORDER_ID = T1.ORDER_ID\n" );
//    	sql.append("   AND T1.ORG_ID = T2.ORG_ID\n" );
    	sql.append("   AND T.SHIP_ID = "+SHIP_ID+"");
		return factory.select(null, sql.toString());
	}
    public QuerySet getCarrier(String SHIP_ID) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T1.CARRIER_NAME, T1.PHONE C_PHONE\n" );
    	sql.append("  FROM PT_BU_ORDER_SHIP T, PT_BA_CARRIER T1\n" );
    	sql.append(" WHERE T.CARRIER_ID = T1.CARRIER_ID\n" );
    	sql.append("   AND T.SHIP_ID = "+SHIP_ID+"");
		return factory.select(null, sql.toString());
	}

}