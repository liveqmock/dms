package com.org.dms.dao.part.storageMng.enterStorage;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuDealerStockChangeVO;
import com.org.dms.vo.part.PtBuDealerStockVO;
import com.org.dms.vo.part.PtBuOrderShipVO;
import com.org.dms.vo.part.PtBuSaleOrderDtlVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class InStorageDao extends BaseDAO
{

	/**
	 * 定义instance
	 * 
	 * @param atx
	 * @return
	 */
	public static final InStorageDao getInstance(ActionContext atx) {
		InStorageDao dao = new InStorageDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}

	
	public QuerySet getIfAmry(String orgId) throws Exception {

        //定义返回结果集
        StringBuffer sql= new StringBuffer();
        sql.append("select is_am from tm_org where org_id = "+orgId+"");
        return factory.select(null,sql.toString());
    }
	/**
	 * 
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */

	public BaseResultSet ordersearch(PageManager page, User user, String conditions,String ifArmy) throws Exception {
		String wheres = conditions;
			   
		page.setFilter(wheres);
		// 定义返回结果集
		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.ORDER_ID,\n" );
		sql.append("       T.ORDER_NO,\n" );
		sql.append("       T.ORDER_TYPE,\n" );
		sql.append("       T.ORG_ID,\n" );
		sql.append("       T.ORDER_AMOUNT,\n" );
		sql.append("       T.REAL_AMOUNT,\n" );
		sql.append("       T.WAREHOUSE_ID,\n" );
		sql.append("       T.WAREHOUSE_CODE,\n" );
		sql.append("       T.WAREHOUSE_NAME,\n" );
		sql.append("       T.APPLY_DATE,\n" );
		sql.append("       T.ORDER_STATUS,\n" );
		sql.append("       T.SHIP_ID,\n" );
		sql.append("       T.SHIP_NO,\n" );
		sql.append("       T.CARRIER_NAME,\n" );
		sql.append("       T.LINK_MAN,\n" );
		sql.append("       T.PHONE,\n" );
		sql.append("       T.SHIP_DATE\n" );
		sql.append("  FROM (SELECT T1.ORDER_ID,\n" );
		sql.append("               T1.ORDER_NO,\n" );
		sql.append("               T1.ORDER_TYPE,\n" );
		sql.append("               T1.ORG_ID,\n" );
		sql.append("               T1.ORDER_AMOUNT,\n" );
		sql.append("               T1.REAL_AMOUNT,\n" );
		sql.append("               T1.WAREHOUSE_ID,\n" );
		sql.append("               T1.WAREHOUSE_CODE,\n" );
		sql.append("               T1.WAREHOUSE_NAME,\n" );
		sql.append("               T1.APPLY_DATE,\n" );
		sql.append("               T1.ORDER_STATUS,\n" );
		sql.append("               T2.SHIP_ID,\n" );
		sql.append("               T2.SHIP_NO,\n" );
		sql.append("               T2.CARRIER_NAME,\n" );
		sql.append("               T2.LINK_MAN,\n" );
		sql.append("               T2.PHONE,\n" );
		sql.append("               T2.SHIP_DATE\n" );
		sql.append("          FROM (SELECT A.ORDER_ID,\n" );
		sql.append("                       A.ORDER_NO,\n" );
		sql.append("                       A.ORDER_TYPE,\n" );
		sql.append("                       A.ORG_ID,\n" );
		sql.append("                       A.ORDER_AMOUNT,\n" );
		sql.append("                       A.REAL_AMOUNT,\n" );
		sql.append("                       A.WAREHOUSE_ID,\n" );
		sql.append("                       A.WAREHOUSE_CODE,\n" );
		sql.append("                       A.WAREHOUSE_NAME,\n" );
		sql.append("                       A.APPLY_DATE,\n" );
		sql.append("                       A.ORDER_STATUS\n" );
		sql.append("                  FROM PT_BU_SALE_ORDER A\n" );
		sql.append("                 WHERE 1=1\n" );
		if(ifArmy.equals(DicConstant.SF_01)){
			sql.append("                   AND A.ORDER_TYPE = "+DicConstant.DDLX_08+"\n" );
		}else{
			sql.append("                   AND A.ORDER_TYPE <> "+DicConstant.DDLX_08+"\n" );
		}
		sql.append("                   AND A.STATUS = "+DicConstant.YXBS_01+"\n" );
		if (DicConstant.ZZLB_09.equals(user.getOrgDept().getOrgType())) {
			// 配送中心
			sql.append("                   AND A.ORDER_TYPE NOT IN ('"+DicConstant.DDLX_09+"','"+DicConstant.DDLX_10+"')\n" );
		}
		
//		if (DicConstant.ZZLB_10.equals(user.getOrgDept().getOrgType())) {
//			// 服务站
//		}
		
//		sql.append("                   AND A.ORDER_STATUS = "+DicConstant.DDZT_03+"\n" );
		sql.append("                   AND A.ORDER_STATUS = "+DicConstant.DDZT_06+"\n" );
		sql.append("                   AND A.SHIP_STATUS = "+DicConstant.DDFYZT_06+"\n" );
		sql.append("                   AND A.ORG_ID = "+user.getOrgId()+"\n" );
		sql.append("                ) T1\n" );
		sql.append("          LEFT JOIN (SELECT C.SHIP_ID,\n" );
		sql.append("                           C.SHIP_NO,\n" );
		sql.append("                           C.CARRIER_NAME,\n" );
		sql.append("                           C.SHIP_DATE,\n" );
		sql.append("                           C.LINK_MAN,\n" );
		sql.append("                           C.PHONE,\n" );
		sql.append("                           D.ORDER_ID\n" );
		sql.append("                      FROM PT_BU_ORDER_SHIP C, PT_BU_ORDER_SHIP_DTL D\n" );
		sql.append("                     WHERE C.SHIP_ID = D.SHIP_ID\n" );
		sql.append("                       AND C.SHIP_STATUS = "+DicConstant.FYDZT_04+") T2\n" );
		sql.append("            ON T1.ORDER_ID = T2.ORDER_ID) T");
		bs = factory.select(sql.toString(), page);
		bs.setFieldDic("order_type", "DDLX");
		bs.setFieldDateFormat("ship_date", "yyyy-MM-dd");
		bs.setFieldDateFormat("apply_date", "yyyy-MM-dd");
		return bs;
	}
	
	/**
	 * 查询订单
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public QuerySet orderDtl(User user,String orderId) throws Exception {
		String wheres = "\n  WHERE PBO.ORDER_STATUS <>" + DicConstant.DDZT_05 
			   +  "\n  AND PBO.SHIP_STATUS  =" + DicConstant.DDFYZT_06
			   +  "\n  AND PBO.STATUS ="+ DicConstant.YXBS_01
			   +  "\n  AND PBOD.PART_ID = PBI.PART_ID  "
			   +  "\n  AND PBO.ORDER_ID = PBOD.ORDER_ID "
			   +  "\n  AND PBO.ORG_ID ="+user.getOrgId()
			   +  "\n  AND PBO.ORDER_ID ="+orderId
			   +  "\n  AND PBOD.DELIVERY_COUNT >0";
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT PBOD.DTL_ID,\n" );
		sql.append("       PBOD.PART_ID,\n" );
		sql.append("       PBOD.PART_CODE,\n" );
		sql.append("       PBOD.PART_NAME,\n" );
		sql.append("       NVL(PBOD.DELIVERY_COUNT,0) SIGN_COUNT,\n" );
		sql.append("       PBOD.SUPPLIER_NAME,\n" );
		sql.append("       PBOD.SUPPLIER_ID,\n" );
		sql.append("       PBOD.SUPPLIER_CODE\n" );
		sql.append("  FROM PT_BU_SALE_ORDER     PBO,\n" );
		sql.append("       PT_BU_SALE_ORDER_DTL PBOD,\n" );
		sql.append("       PT_BA_INFO PBI");
		return factory.select(null,sql.toString()+wheres);
	}
	/**
	 * 查询订单
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet orderXxsearch(PageManager page, User user, String conditions,String orderId) throws Exception {
		String wheres = conditions;
		wheres += "\n  AND pbo.order_status <>" + DicConstant.DDZT_05 
			   +  "\n  AND pbo.SHIP_STATUS  =" + DicConstant.DDFYZT_06
			   +  "\n  AND pbo.STATUS ="+ DicConstant.YXBS_01
			   +  "\n  AND pbod.part_id = pbi.part_id  "
			   +  "\n  AND pbo.order_id = pbod.order_id "
			   //+  "\n  AND PBO.COMPANY_ID ="+user.getCompanyId() 
			   +  "\n  AND PBO.org_id ="+user.getOrgId()
			   +  "\n  AND PBO.order_id ="+orderId
			   +  "\n  AND pbod.delivery_count >0";
		page.setFilter(wheres);
		// 定义返回结果集
		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("select pbo.order_id,\n" );
		sql.append("       pbo.order_no,\n" );
		sql.append("       pbo.apply_date,\n" );
		sql.append("       pbod.dtl_id,\n" );
		sql.append("       pbo.order_status,\n" );
		sql.append("       pbod.part_id,\n" );
		sql.append("       pbod.part_code,\n" );
		sql.append("       pbod.part_name,\n" );
		sql.append("       pbod.part_no,\n" );
		sql.append("       pbod.order_count,\n" );
		sql.append("       pbod.unit_price,\n" );
		sql.append("       pbod.amount,\n" );
		sql.append("       pbod.delivery_count,\n" );
		sql.append("       nvl(pbod.delivery_count,0) sign_count,\n" );
		sql.append("       nvl(pbod.audit_count,0) audit_count,\n" );
		sql.append("       pbi.unit,\n" );
		sql.append("       pbi.min_pack,\n" );
		sql.append("       pbi.min_unit,\n" );
		sql.append("       pbod.if_supplier,\n" );
		sql.append("       pbod.supplier_name,\n" );
		sql.append("       pbod.supplier_id,\n" );
		sql.append("       pbod.supplier_code\n" );
		sql.append("  from pt_bu_sale_order     pbo,\n" );
		sql.append("       pt_bu_sale_order_dtl pbod,\n" );
		sql.append("       pt_ba_info pbi");
		bs = factory.select(sql.toString(), page);
		bs.setFieldDic("unit", "JLDW");
		bs.setFieldDic("min_unit", "JLDW");
		return bs;
	}
	
	public QuerySet orderChek(String order_id) throws Exception {

		QuerySet qs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("select count(*) from pt_bu_sale_order_dtl t where order_id ="+order_id);
		qs = factory.select(null,sql.toString());
		return qs;
	}
	
	public QuerySet queryShipOrder(String shipId) throws Exception {
		QuerySet qs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT 1 FROM PT_BU_ORDER_SHIP_DTL A,PT_BU_ORDER_SHIP B,PT_BU_SALE_ORDER C\n" );
		sql.append("WHERE A.SHIP_ID = B.SHIP_ID AND A.ORDER_ID = C.ORDER_ID AND B.SHIP_ID = '"+shipId+"'\n" );
		sql.append("AND C.SHIP_STATUS<>"+DicConstant.DDFYZT_07+"\n");
		qs = factory.select(null,sql.toString());
		return qs;
	}
	
	/**
     * 更新签收数量
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateInStorageOrder(PtBuSaleOrderDtlVO vo) throws Exception {
        return factory.update(vo);
    }
    /**
     * 更新库存表
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateDealerStock(PtBuDealerStockVO vo) throws Exception {
        return factory.update(vo);
    }
    
    public boolean updateShipOrder(PtBuOrderShipVO vo) throws Exception {
        return factory.update(vo);
    }
    /**
     * 更新订单状态和回执单
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateInStorageOrderStauts(PtBuSaleOrderVO vo) throws Exception {
        return factory.update(vo);
    }
    //
    public QuerySet dealerstock(String part_id,String supplierId,User user) throws Exception {

		QuerySet qs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("select  STOCK_ID,nvl(AMOUNT,0),nvl(AVAILABLE_AMOUNT,0) from pt_bu_dealer_stock t where t.part_id ="+part_id+" and t.supplier_id = "+supplierId+" and t.org_id ="+user.getOrgId());
		qs = factory.select(null,sql.toString());
		return qs;
	}
    
    /**
     * 查询订单是否有原单
     *
     * @param SHIP_ID 发运单ID
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet selectSourceOrder(String orderId) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT DIR_SOURCE_ORDER_ID\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER\n" );
    	sql.append(" WHERE ORDER_ID = '"+orderId+"'\n" );
    	sql.append("   AND DIR_SOURCE_ORDER_ID IS NOT NULL");

        return factory.select(null, sql.toString());
    }

    /**
	 * 库存新增
	 */
	public boolean orderStockInsert(PtBuDealerStockVO vo)
            throws Exception {
		String StockId=SequenceUtil.getCommonSerivalNumber(factory);
		vo.setStockId(StockId);
        return factory.insert(vo);
    }
	/**
	 * 库存异动(轨迹)新增
	 */
	public boolean orderStockChangeInsert(PtBuDealerStockChangeVO vo)
            throws Exception {
		String ChangeId=SequenceUtil.getCommonSerivalNumber(factory);
		vo.setChangeId(ChangeId);;
        return factory.insert(vo);
    }
}
