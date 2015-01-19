package com.org.dms.dao.part.storageMng.stockOutMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuDealerStockChangeVO;
import com.org.dms.vo.part.PtBuDealerStockVO;
import com.org.dms.vo.part.PtBuIssueOrderVO;
import com.org.dms.vo.part.PtBuRealSaleDtlVO;
import com.org.dms.vo.part.PtBuRealSaleVO;
import com.org.dms.vo.part.PtBuSaleOrderDtlVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.dms.vo.part.PtBuStockOutContinualVO;
import com.org.dms.vo.part.PtBuStockOutDtlVO;
import com.org.dms.vo.part.PtBuStockOutVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * 直营出库Dao
 * 
 * @user : lichuang
 * @date : 2014-07-23
 */
public class DirectStockOutMngDao extends BaseDAO {
	// 定义instance
	public static final DirectStockOutMngDao getInstance(ActionContext atx) {
		DirectStockOutMngDao dao = new DirectStockOutMngDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}

	/**
	 * 查询出库单
	 * 
	 * @param page
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet searchOutBill(PageManager page, User user,
			String conditions) throws Exception {
		StringBuilder wheres = new StringBuilder(conditions);
		wheres.append("   AND A.ORDER_ID = B.ORDER_ID\n");
		wheres.append("   AND B.ORDER_ID = C.ORDER_ID\n");
		wheres.append("   AND C.CHECK_RESULT = " + DicConstant.DDZT_03 + "\n");
		wheres.append("   AND A.OUT_STATUS = " + DicConstant.CKDZT_01 + "\n");
		wheres.append("   AND A.OUT_TYPE = " + DicConstant.CKLX_02 + "\n");
		wheres.append("   AND A.STATUS = " + DicConstant.YXBS_01 + "\n");
		wheres.append("   AND A.OEM_COMPANY_ID=" + user.getOemCompanyId()
				+ "\n");
		wheres.append("   ORDER BY A.CREATE_TIME DESC\n");
		page.setFilter(wheres.toString());

		// 定义返回结果集
		BaseResultSet bs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A.OUT_ID,\n");
		sql.append("       A.OUT_NO,\n");
		sql.append("       A.OUT_TYPE,\n");
		sql.append("       A.ORDER_ID,\n");
		sql.append("       A.ORDER_NO,\n");
		sql.append("       A.WAREHOUSE_ID,\n");
		sql.append("       A.WAREHOUSE_CODE,\n");
		sql.append("       A.WAREHOUSE_NAME,\n");
		sql.append("       B.ORG_NAME,\n");
		sql.append("       C.CHECK_USER,\n");
		sql.append("       A.REMARKS\n");
		sql.append("  FROM PT_BU_STOCK_OUT A, PT_BU_SALE_ORDER B, PT_BU_SALE_ORDER_CHECK C\n");

		// 执行方法，不需要传递conn参数
		bs = factory.select(sql.toString(), page);
		bs.setFieldDic("OUT_TYPE", DicConstant.CKLX);
		bs.setFieldUserID("CHECK_USER");
		return bs;
	}

	/**
	 * 查询销售订单
	 * 
	 * @param page
	 * @param user
	 * @param conditions
	 * @param WAREHOUSE_ID
	 *            出库仓库ID
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet searchSale(PageManager page, User user,
			String conditions, String WAREHOUSE_ID) throws Exception {
		String wheres = conditions;

		wheres += " AND A.ORDER_TYPE = " + DicConstant.DDLX_07 + "\n";
		wheres += " AND A.ORDER_ID = B.ORDER_ID\n";
		wheres += " AND B.CHECK_USER = C.ACCOUNT\n";
		wheres += " AND A.WAREHOUSE_ID = " + WAREHOUSE_ID + "\n";
		wheres += " AND A.SHIP_STATUS = " + DicConstant.DDFYZT_02 + "\n";
		wheres += " AND A.ORDER_STATUS = " + DicConstant.DDZT_03 + "\n";
		wheres += " AND B.CHECK_RESULT = " + DicConstant.DDZT_03 + "\n";
		wheres += " AND NOT EXISTS(SELECT 1 FROM PT_BU_STOCK_OUT D WHERE D.ORDER_ID = A.ORDER_ID)\n";
		wheres += " AND A.STATUS = " + DicConstant.YXBS_01 + "\n";
		wheres += " AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n";
		wheres += " ORDER BY A.APPLY_DATE DESC\n";
		page.setFilter(wheres.toString());

		// 定义返回结果集
		BaseResultSet bs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A.ORDER_ID,\n");
		sql.append("       A.ORDER_NO,\n");
		sql.append("       A.ORDER_TYPE,\n");
		sql.append("       A.ORG_CODE,\n");
		sql.append("       A.ORG_NAME,\n");
		sql.append("       A.APPLY_DATE,\n");
		sql.append("       A.WAREHOUSE_ID,\n");
		sql.append("       B.CHECK_USER,\n");
		sql.append("       B.CHECK_DATE\n");
		sql.append("  FROM PT_BU_SALE_ORDER A, PT_BU_SALE_ORDER_CHECK B, TM_USER C\n");

		// 执行方法，不需要传递conn参数
		bs = factory.select(sql.toString(), page);
		bs.setFieldDic("ORDER_TYPE", DicConstant.DDLX);
		bs.setFieldUserID("CHECK_USER");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd HH:mm:ss");
		return bs;
	}

	/**
	 * 获取主键ID
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getId() throws Exception {
		return SequenceUtil.getCommonSerivalNumber(factory);
	}

	/**
	 * 新增出库单
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean insertOutBill(PtBuStockOutVO vo) throws Exception {
		return factory.insert(vo);
	}

	/**
	 * 新增出库单明细
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean insertOutBillDtl(PtBuStockOutDtlVO vo) throws Exception {
		return factory.insert(vo);
	}

	/**
	 * 修改出库单
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updateOutBill(PtBuStockOutVO vo) throws Exception {
		return factory.update(vo);
	}

	/**
	 * 查询出库单配件
	 * 
	 * @param page
	 * @param user
	 * @param conditions
	 * @param OUT_ID
	 *            出库单ID
	 * @param ORDER_ID
	 *            销售订单ID
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet searchOutBillPart(PageManager page, User user,
			String conditions, String ISSUE_ID,String account) throws Exception {
		// 定义返回结果集
		BaseResultSet bs = null;
		StringBuffer sql = new StringBuffer();
//		sql.append("SELECT A.PART_ID,\n");
//		sql.append("       A.PART_CODE,\n");
//		sql.append("       A.PART_NAME,\n");
//		sql.append("       A.SUPPLIER_ID,\n");
//		sql.append("       A.SUPPLIER_CODE,\n");
//		sql.append("       A.SUPPLIER_NAME,\n");
//		sql.append("       C.UNIT,\n");
//		sql.append("       C.MIN_PACK,\n");
//		sql.append("       C.MIN_UNIT,\n");
//		sql.append("       A.SHOULD_COUNT,\n");
//		sql.append("       A.POSITION_ID,\n");
//		sql.append("       A.POSITION_CODE,\n");
//		sql.append("       A.POSITION_NAME,\n");
//		sql.append("       A.DTL_ID ISSUE_DTL_ID,\n");
//		sql.append("       A.ISSUE_ID,\n");
//		sql.append("       E.ISSUE_NO,\n");
//		sql.append("       E.USER_ACCOUNT\n" );
//		sql.append("  FROM PT_BU_ISSUE_ORDER_DTL A,\n");
//		sql.append("       PT_BA_INFO C,\n");
//		sql.append("       PT_BU_STOCK_DTL D,\n");
//		sql.append("       PT_BU_ISSUE_ORDER E\n" );
//		sql.append(" WHERE  1=1    AND A.ISSUE_ID = " + ISSUE_ID + "\n");
//		sql.append("   AND A.PART_ID = C.PART_ID\n");
//		sql.append("   AND A.PART_ID = D.PART_ID\n");
//		sql.append("   AND A.POSITION_ID = D.POSITION_ID\n");
//		sql.append("   AND A.SUPPLIER_ID = D.SUPPLIER_ID\n");
//		sql.append("   AND A.ISSUE_ID = E.ISSUE_ID\n");
//		sql.append("   ORDER BY A.PART_CODE ASC");
        sql.append("SELECT T.*, T1.OUT_AMOUNT\n" );
        sql.append("  FROM (SELECT A.PART_ID,\n" );
        sql.append("               A.PART_CODE,\n" );
        sql.append("               A.PART_NAME,\n" );
        sql.append("               A.SUPPLIER_ID,\n" );
        sql.append("               A.SUPPLIER_CODE,\n" );
        sql.append("               A.SUPPLIER_NAME,\n" );
        sql.append("               C.UNIT,\n" );
        sql.append("               C.MIN_PACK,\n" );
        sql.append("               C.MIN_UNIT,\n" );
        sql.append("               A.SHOULD_COUNT,\n" );
        sql.append("               A.POSITION_ID,\n" );
        sql.append("               A.POSITION_CODE,\n" );
        sql.append("               A.POSITION_NAME,\n" );
        sql.append("               A.DTL_ID ISSUE_DTL_ID,\n" );
        sql.append("               A.ISSUE_ID,\n" );
        sql.append("               D.AVAILABLE_AMOUNT,\n" );
        sql.append("               E.ISSUE_NO,\n" );
        sql.append("               E.ORDER_ID,\n" );
        sql.append("               E.USER_ACCOUNT\n" );
        sql.append("          FROM PT_BU_ISSUE_ORDER_DTL A,\n" );
        sql.append("               PT_BA_INFO            C,\n" );
        sql.append("               PT_BU_STOCK_DTL       D,\n" );
        sql.append("               PT_BU_ISSUE_ORDER     E\n" );
        sql.append("         WHERE 1 = 1\n" );
        sql.append("           AND A.ISSUE_ID = "+ISSUE_ID+"\n" );
        sql.append("           AND A.PART_ID = C.PART_ID\n" );
        sql.append("           AND A.PART_ID = D.PART_ID\n" );
        sql.append("           AND A.POSITION_ID = D.POSITION_ID\n" );
        sql.append("           AND A.SUPPLIER_ID = D.SUPPLIER_ID\n" );
        sql.append("           AND A.ISSUE_ID = E.ISSUE_ID\n" );
        sql.append("         ORDER BY A.PART_CODE ASC) T\n" );
        sql.append("  LEFT JOIN (SELECT X.ORDER_ID,Y.PART_ID,Y.OUT_AMOUNT FROM PT_BU_STOCK_OUT X,PT_BU_STOCK_OUT_DTL Y WHERE X.OUT_ID = Y.OUT_ID) T1\n" );
        sql.append("  ON T.ORDER_ID = T1.ORDER_ID AND T.PART_ID = T1.PART_ID");
		// 执行方法，不需要传递conn参数
		bs = factory.select(sql.toString(), page);
		bs.setFieldDic("UNIT", DicConstant.JLDW);
		bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);
		return bs;
	}

	/**
	 * 校验出库单明细是否存在
	 * 
	 * @param OUT_ID
	 *            出库单ID
	 * @param PART_ID
	 *            配件ID
	 * @param POSITION_ID
	 *            库位ID
	 * @param SUPPLIER_ID
	 *            供应商ID
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String checkOutBillDtlIsExist(String OUT_ID, String PART_ID,
			String POSITION_ID, String SUPPLIER_ID, User user) throws Exception {
		String DETAIL_ID = "";
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT A.DETAIL_ID\n");
		sql.append("  FROM PT_BU_STOCK_OUT_DTL A\n");
		sql.append(" WHERE A.OUT_ID = " + OUT_ID + "\n");
		sql.append("   AND A.PART_ID = " + PART_ID + "\n");
		sql.append("   AND A.POSITION_ID = " + POSITION_ID + "\n");
		sql.append("   AND A.SUPPLIER_ID = " + SUPPLIER_ID + "\n");

		QuerySet qs = factory.select(null, sql.toString());
		if (qs.getRowCount() > 0) {
			DETAIL_ID = qs.getString(1, "DETAIL_ID");
		}
		return DETAIL_ID;
	}

	/**
	 * 修改出库单明细
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updateOutBillDtl(PtBuStockOutDtlVO vo) throws Exception {
		return factory.update(vo);
	}

	/**
	 * 修改库存
	 * 
	 * @param WAREHOUSE_ID
	 *            仓库ID
	 * @param ORDER_ID
	 *            销售订单ID
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean updateStock(String WAREHOUSE_ID, String ORDER_ID, String account)
			throws Exception {
//		StringBuilder sql = new StringBuilder();
//		sql.append("UPDATE PT_BU_STOCK A\n");
//		sql.append("   SET A.AMOUNT        = A.AMOUNT -\n");
//		sql.append("                         (SELECT SUM(NVL(B.REAL_COUNT,0))\n");
//		sql.append("                            FROM PT_BU_ISSUE_ORDER_DTL B\n");
//		sql.append("                           WHERE B.ORDER_ID = " + ORDER_ID
//				+ "\n");
//		sql.append("                             AND B.PART_ID = A.PART_ID\n");
//		sql.append("                             AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
//		sql.append("                            GROUP BY PART_ID),\n");
//		sql.append("       A.OCCUPY_AMOUNT = A.OCCUPY_AMOUNT -\n");
//		sql.append("                         (SELECT SUM(NVL(B.SHOULD_COUNT,0))\n");
//		sql.append("                            FROM PT_BU_ISSUE_ORDER_DTL B\n");
//		sql.append("                           WHERE B.ORDER_ID = " + ORDER_ID
//				+ "\n");
//		sql.append("                             AND B.PART_ID = A.PART_ID\n");
//		sql.append("                             AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
//		sql.append("                            GROUP BY PART_ID),\n");
//		sql.append("       A.AVAILABLE_AMOUNT = A.AVAILABLE_AMOUNT +\n");
//		sql.append("                         (SELECT SUM(B.SHOULD_COUNT - NVL(B.REAL_COUNT,0))\n");
//		sql.append("                            FROM PT_BU_ISSUE_ORDER_DTL B\n");
//		sql.append("                           WHERE B.ORDER_ID = " + ORDER_ID
//				+ "\n");
//		sql.append("                             AND B.PART_ID = A.PART_ID\n");
//		sql.append("                             AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
//		sql.append("                            GROUP BY PART_ID),\n");
//		sql.append("       A.UPDATE_TIME = SYSDATE,\n");
//		sql.append("       A.UPDATE_USER = '" + user.getAccount() + "'\n");
//		sql.append(" WHERE A.WAREHOUSE_ID = " + WAREHOUSE_ID + "\n");
//		sql.append("   AND EXISTS (SELECT 1\n");
//		sql.append("          FROM PT_BU_ISSUE_ORDER_DTL B\n");
//		sql.append("         WHERE B.ORDER_ID = " + ORDER_ID + "\n");
//		sql.append("           AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
//		sql.append("           AND B.PART_ID = A.PART_ID)\n");
	  	
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE PT_BU_STOCK A\n" );
    	sql.append("           SET A.AMOUNT        = A.AMOUNT -\n" );
    	sql.append("                                 (SELECT SUM(NVL(B.REAL_COUNT,0))\n" );
    	sql.append("                             FROM PT_BU_ISSUE_ORDER_DTL B,PT_BU_ISSUE_ORDER C \n" );
    	sql.append("                                   WHERE B.ORDER_ID = "+ORDER_ID+"\n" );
    	sql.append("                                     AND B.PART_ID = A.PART_ID\n" );
    	sql.append("                                     AND B.SUPPLIER_ID = A.SUPPLIER_ID\n" );
    	sql.append("                               		 AND B.ISSUE_ID = C.ISSUE_ID\n" );
    	sql.append("                               		 AND C.USER_ACCOUNT = '"+account+"'\n" );
    	sql.append("                                    GROUP BY PART_ID),\n" );
    	sql.append("               A.OCCUPY_AMOUNT = A.OCCUPY_AMOUNT -\n" );
    	sql.append("                                 (SELECT SUM(NVL(B.SHOULD_COUNT,0))\n" );
    	sql.append("                                    FROM PT_BU_ISSUE_ORDER_DTL B,PT_BU_ISSUE_ORDER C\n" );
    	sql.append("                                   WHERE B.ORDER_ID = "+ORDER_ID+"\n" );
    	sql.append("                                     AND B.PART_ID = A.PART_ID\n" );
    	sql.append("                                     AND B.SUPPLIER_ID = A.SUPPLIER_ID\n" );
    	sql.append("                                   AND B.ISSUE_ID = C.ISSUE_ID\n" );
    	sql.append("                                   AND C.USER_ACCOUNT = '"+account+"'\n" );
    	sql.append("                                    GROUP BY PART_ID),\n" );
	    sql.append("       			A.AVAILABLE_AMOUNT = A.AVAILABLE_AMOUNT +\n");
	    sql.append("                         		(SELECT SUM(B.SHOULD_COUNT - NVL(B.REAL_COUNT,0))\n");
	    sql.append("                            		FROM PT_BU_ISSUE_ORDER_DTL B,PT_BU_ISSUE_ORDER C\n");
	    sql.append("                          		 WHERE B.ORDER_ID = " + ORDER_ID + "\n");
	    sql.append("                            	 AND B.PART_ID = A.PART_ID\n");
	    sql.append("                            	 AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
	    sql.append("                      			 AND B.ISSUE_ID = C.ISSUE_ID\n");
	    sql.append("                      			 AND C.USER_ACCOUNT = '"+account+"'\n");
        sql.append("                            	GROUP BY PART_ID),\n");
    	sql.append("               A.UPDATE_TIME = SYSDATE,\n" );
    	sql.append("               A.UPDATE_USER = '"+account+"'\n" );
    	sql.append("         WHERE A.WAREHOUSE_ID = "+WAREHOUSE_ID+"\n" );
    	sql.append("           AND EXISTS (SELECT 1\n" );
    	sql.append("                  FROM PT_BU_ISSUE_ORDER_DTL B, PT_BU_ISSUE_ORDER W\n" );
    	sql.append("                 WHERE B.ORDER_ID = "+ORDER_ID+"\n" );
    	sql.append("                   AND B.SUPPLIER_ID = A.SUPPLIER_ID\n" );
    	sql.append("                   AND B.PART_ID = A.PART_ID AND B.ISSUE_ID = W.ISSUE_ID AND W.USER_ACCOUNT = '"+account+"')");
		return factory.update(sql.toString(), null);
	}

	/**
	 * 修改库存明细
	 * 
	 * @param ORDER_ID
	 *            销售订单ID
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean updateStockDtl(String ORDER_ID, String account) throws Exception {
		StringBuilder sql = new StringBuilder();
//		sql.append("UPDATE PT_BU_STOCK_DTL A\n");
//		sql.append("   SET A.AMOUNT = A.AMOUNT -\n");
//		sql.append("                  (SELECT NVL(B.REAL_COUNT,0)\n");
//		sql.append("                     FROM PT_BU_ISSUE_ORDER_DTL B\n");
//		sql.append("                    WHERE B.ORDER_ID = " + ORDER_ID + "\n");
//		sql.append("                      AND B.PART_ID = A.PART_ID\n");
//		sql.append("                      AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
//		sql.append("                      AND B.POSITION_ID = A.POSITION_ID),\n");
//		sql.append("       A.OCCUPY_AMOUNT = A.OCCUPY_AMOUNT -\n");
//		sql.append("                  (SELECT B.SHOULD_COUNT\n");
//		sql.append("                     FROM PT_BU_ISSUE_ORDER_DTL B\n");
//		sql.append("                    WHERE B.ORDER_ID = " + ORDER_ID + "\n");
//		sql.append("                      AND B.PART_ID = A.PART_ID\n");
//		sql.append("                      AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
//		sql.append("                      AND B.POSITION_ID = A.POSITION_ID),\n");
//		sql.append("       A.AVAILABLE_AMOUNT = A.AVAILABLE_AMOUNT +\n");
//		sql.append("                  (SELECT B.SHOULD_COUNT - NVL(B.REAL_COUNT,0)\n");
//		sql.append("                     FROM PT_BU_ISSUE_ORDER_DTL B\n");
//		sql.append("                    WHERE B.ORDER_ID = " + ORDER_ID + "\n");
//		sql.append("                      AND B.PART_ID = A.PART_ID\n");
//		sql.append("                      AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
//		sql.append("                      AND B.POSITION_ID = A.POSITION_ID),\n");
//		sql.append("       A.UPDATE_TIME = SYSDATE,\n");
//		sql.append("       A.UPDATE_USER = '" + user.getAccount() + "'\n");
//		sql.append(" WHERE 1 = 1\n");
//		sql.append("   AND EXISTS (SELECT 1\n");
//		sql.append("          FROM PT_BU_ISSUE_ORDER_DTL B\n");
//		sql.append("         WHERE B.ORDER_ID = " + ORDER_ID + "\n");
//		sql.append("           AND B.PART_ID = A.PART_ID\n");
//		sql.append("           AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
//		sql.append("           AND B.POSITION_ID = A.POSITION_ID)\n");
		sql.append("UPDATE PT_BU_STOCK_DTL A\n" );
        sql.append("           SET A.AMOUNT = A.AMOUNT -\n" );
        sql.append("                          (SELECT NVL(B.REAL_COUNT,0)\n" );
        sql.append("                             FROM PT_BU_ISSUE_ORDER_DTL B,PT_BU_ISSUE_ORDER C\n" );
        sql.append("                            WHERE B.ORDER_ID = "+ORDER_ID+"\n" );
        sql.append("                              AND B.PART_ID = A.PART_ID\n" );
        sql.append("                              AND B.ISSUE_ID = C.ISSUE_ID\n" );
        sql.append("                              AND C.USER_ACCOUNT = '"+account+"'\n" );
        sql.append("                              AND B.SUPPLIER_ID = A.SUPPLIER_ID\n" );
        sql.append("                              AND B.POSITION_ID = A.POSITION_ID),\n" );
        sql.append("               A.OCCUPY_AMOUNT = A.OCCUPY_AMOUNT -\n" );
        sql.append("                          (SELECT B.SHOULD_COUNT\n" );
        sql.append("                             FROM PT_BU_ISSUE_ORDER_DTL B,PT_BU_ISSUE_ORDER C\n" );
        sql.append("                            WHERE B.ORDER_ID = "+ORDER_ID+"\n" );
        sql.append("                              AND B.PART_ID = A.PART_ID\n" );
        sql.append("                              AND B.ISSUE_ID = C.ISSUE_ID\n" );
        sql.append("                              AND C.USER_ACCOUNT = '"+account+"'\n" );
        sql.append("                              AND B.SUPPLIER_ID = A.SUPPLIER_ID\n" );
        sql.append("                              AND B.POSITION_ID = A.POSITION_ID),\n" );
        sql.append("       A.AVAILABLE_AMOUNT = A.AVAILABLE_AMOUNT +\n");
        sql.append("                  (SELECT B.SHOULD_COUNT - NVL(B.REAL_COUNT,0)\n");
        sql.append("                     FROM PT_BU_ISSUE_ORDER_DTL B,PT_BU_ISSUE_ORDER C\n");
        sql.append("                    WHERE B.ORDER_ID = " + ORDER_ID + "\n");
        sql.append("                      AND B.PART_ID = A.PART_ID\n");
        sql.append("                      AND B.SUPPLIER_ID = A.SUPPLIER_ID\n");
        sql.append("                      AND B.ISSUE_ID = C.ISSUE_ID\n");
        sql.append("                      AND C.USER_ACCOUNT = '"+account+"'\n");
        sql.append("                      AND B.POSITION_ID = A.POSITION_ID),\n");
        sql.append("               A.UPDATE_TIME = SYSDATE,\n" );
        sql.append("               A.UPDATE_USER = '"+account+"'\n" );
        sql.append("         WHERE 1 = 1\n" );
        sql.append("           AND EXISTS (SELECT 1\n" );
        sql.append("                  FROM PT_BU_ISSUE_ORDER_DTL B,PT_BU_ISSUE_ORDER W\n" );
        sql.append("                 WHERE B.ORDER_ID = "+ORDER_ID+"\n" );
        sql.append("                   AND B.PART_ID = A.PART_ID\n" );
        sql.append("                   AND B.SUPPLIER_ID = A.SUPPLIER_ID\n" );
        sql.append("                   AND B.POSITION_ID = A.POSITION_ID AND B.ISSUE_ID = W.ISSUE_ID AND W.USER_ACCOUNT = '"+account+"')");
		return factory.update(sql.toString(), null);
	}

	/**
	 * 更新发料单明细
	 * 
	 * @param ORDER_ID
	 *            销售订单ID
	 * @param OUT_ID
	 *            出库单ID
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean updateIssueOrderDtl(String ORDER_ID, String outCount,
			String issueId, String detailId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE PT_BU_ISSUE_ORDER_DTL A\n");
		sql.append("   SET A.REAL_COUNT = " + outCount + " WHERE ISSUE_ID = "
				+ issueId + " AND ORDER_ID=" + ORDER_ID + " AND DTL_ID ="
				+ detailId + "\n");
		return factory.update(sql.toString(), null);
	}

	/**
	 * 修改发料单
	 * 
	 * @param ORDER_ID
	 *            销售订单ID
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean updateIssueOrder(String ORDER_ID, User user)
			throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE PT_BU_ISSUE_ORDER A\n");
		sql.append("   SET A.ISSUE_STATUS = " + DicConstant.FLDFLZT_02
				+ ", A.UPDATE_USER = '" + user.getAccount()
				+ "', A.UPDATE_TIME = SYSDATE\n");
		sql.append(" WHERE A.ORDER_ID = " + ORDER_ID + "\n");
		return factory.update(sql.toString(), null);
	}

	/**
	 * 校验出库单明细是否存在
	 * 
	 * @param OUT_ID
	 *            出库单ID
	 * @return
	 * @throws Exception
	 */
	public Boolean checkOutBillDtlIsExist(String OUT_ID) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT 1\n");
		sql.append("  FROM PT_BU_STOCK_OUT_DTL A\n");
		sql.append(" WHERE A.OUT_ID = " + OUT_ID + "\n");

		QuerySet qs = factory.select(null, sql.toString());
		if (qs.getRowCount() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 删除出库单
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean deleteOutBill(PtBuStockOutVO vo) throws Exception {
		return factory.delete(vo);
	}

	/**
	 * 删除出库单明细
	 * 
	 * @param outId
	 *            出库单ID
	 * @return
	 * @throws Exception
	 */
	public boolean deleteOutBillDtl(String outId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM PT_BU_STOCK_OUT_DTL WHERE OUT_ID = " + outId
				+ "\n");
		return factory.delete(sql.toString(), null);
	}

	/**
	 * 新增出库流水
	 * 
	 * @param OUT_ID
	 *            出库单ID
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean insertOutFlow(String OUT_ID, String account) throws Exception {
		StringBuilder sql = new StringBuilder();
//		sql.append("INSERT INTO PT_BU_STOCK_OUT_CONTINUAL\n");
//		sql.append("  (CONTINUAL_ID,\n");
//		sql.append("   CONTINUAL_NO,\n");
//		sql.append("   OUT_ID,\n");
//		sql.append("   OUT_NO,\n");
//		sql.append("   PART_ID,\n");
//		sql.append("   PART_CODE,\n");
//		sql.append("   PART_NAME,\n");
//		sql.append("   OUT_DATE,\n");
//		sql.append("   KEEPER_MAN,\n");
//		sql.append("   OUT_COUNT,\n");
//		sql.append("   CREATE_MAN)\n");
//		sql.append("  SELECT F_GETID(),\n");
//		sql.append("         '自动生成',\n");
//		sql.append("         A.OUT_ID,\n");
//		sql.append("         B.OUT_NO,\n");
//		sql.append("         A.PART_ID,\n");
//		sql.append("         A.PART_CODE,\n");
//		sql.append("         A.PART_NAME,\n");
//		sql.append("         SYSDATE,\n");
//		sql.append("         A.KEEP_MAN,\n");
//		sql.append("         A.OUT_AMOUNT,\n");
//		sql.append("         '" + user.getAccount() + "'\n");
//		sql.append("    FROM PT_BU_STOCK_OUT_DTL A, PT_BU_STOCK_OUT B\n");
//		sql.append("   WHERE A.OUT_ID = B.OUT_ID\n");
//		sql.append("   AND A.OUT_ID = " + OUT_ID + "\n");
		sql.append("INSERT INTO PT_BU_STOCK_OUT_CONTINUAL\n");
        sql.append("  (CONTINUAL_ID,\n");
        sql.append("   CONTINUAL_NO,\n");
        sql.append("   OUT_ID,\n");
        sql.append("   OUT_NO,\n");
        sql.append("   PART_ID,\n");
        sql.append("   PART_CODE,\n");
        sql.append("   PART_NAME,\n");
        sql.append("   OUT_DATE,\n");
        sql.append("   KEEPER_MAN,\n");
        sql.append("   OUT_COUNT,\n");
        sql.append("   CREATE_MAN)\n");
        sql.append("  SELECT F_GETID(),\n");
        sql.append("         F_GETOUT_CONTINUAL(),\n");
        sql.append("         B.OUT_ID,\n");
        sql.append("         B.OUT_NO,\n");
        sql.append("         A.PART_ID,\n");
        sql.append("         A.PART_CODE,\n");
        sql.append("         A.PART_NAME,\n");
        sql.append("         SYSDATE,\n");
        sql.append("         A.KEEP_MAN,\n");
        sql.append("         A.OUT_AMOUNT,\n");
        sql.append("         '" +account+ "'\n");
        sql.append("    FROM PT_BU_STOCK_OUT_DTL A, PT_BU_STOCK_OUT B,PT_BU_ISSUE_ORDER C,PT_BU_ISSUE_ORDER_DTL D\n");
        sql.append("   WHERE A.OUT_ID = B.OUT_ID AND C.ORDER_ID = B.ORDER_ID AND A.PART_ID = D.PART_ID AND C.USER_ACCOUNT = '"+account+"' AND C.ISSUE_ID = D.ISSUE_ID\n");
        sql.append("   AND A.OUT_ID = " + OUT_ID + "\n");

		return factory.update(sql.toString(), null);
	}

	/**
	 * 修改销售订单
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updateSaleOrder(PtBuSaleOrderVO vo) throws Exception {
		return factory.update(vo);
	}

	/**
	 * 更新出库单明细的销售单价/销售金额
	 * 
	 * @param OUT_ID
	 * @param ORDER_ID
	 */
	public void updateOutBillDtl(String OUT_ID, String ORDER_ID)
			throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE PT_BU_STOCK_OUT_DTL A\n");
		sql.append("   SET A.SALE_PRICE =\n");
		sql.append("       (SELECT B.UNIT_PRICE\n");
		sql.append("          FROM PT_BU_SALE_ORDER_DTL B\n");
		sql.append("         WHERE B.ORDER_ID = '" + ORDER_ID + "'\n");
		sql.append("           AND B.PART_ID = A.PART_ID\n");
		sql.append("           AND B.SUPPLIER_ID = A.SUPPLIER_ID),\n");
		sql.append("       A.SALE_AMOUNT =\n");
		sql.append("       (SELECT B.UNIT_PRICE * A.OUT_AMOUNT\n");
		sql.append("          FROM PT_BU_SALE_ORDER_DTL B\n");
		sql.append("         WHERE B.ORDER_ID = '" + ORDER_ID + "'\n");
		sql.append("           AND B.PART_ID = A.PART_ID\n");
		sql.append("           AND B.SUPPLIER_ID = A.SUPPLIER_ID)\n");
		sql.append(" WHERE A.OUT_ID = '" + OUT_ID + "'\n");
		factory.update(sql.toString(), null);
	}

	public BaseResultSet searchSaleOrder(PageManager page, User user,
			String conditions,String account) throws Exception {

		// 定义返回结果集
		BaseResultSet bs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT A.ORDER_ID,\n");
		sql.append("       A.ORDER_NO,\n");
		sql.append("       D.ISSUE_NO,\n");
		sql.append("       A.ORDER_TYPE,\n");
		sql.append("       A.ORG_CODE,\n");
		sql.append("       A.ORG_NAME,\n");
		sql.append("       A.APPLY_DATE,\n");
		sql.append("       A.WAREHOUSE_ID,\n");
		sql.append("       D.ISSUE_ID,\n");
		sql.append("       B.CHECK_USER,\n");
		sql.append("       B.CHECK_DATE,\n");
		sql.append("NVL(ROUND(((SELECT SUM(OUT_AMOUNT)\n" );
        sql.append("                     FROM PT_BU_STOCK_OUT_DTL\n" );
        sql.append("                    WHERE OUT_ID = (SELECT OUT_ID\n" );
        sql.append("                                      FROM PT_BU_STOCK_OUT\n" );
        sql.append("                                     WHERE ORDER_ID = A.ORDER_ID)) /\n" );
        sql.append("                 (SELECT SUM(AUDIT_COUNT)\n" );
        sql.append("                     FROM PT_BU_SALE_ORDER_DTL\n" );
        sql.append("                    WHERE ORDER_ID = A.ORDER_ID)) * 100,\n" );
        sql.append("                 2),\n" );
        sql.append("           0) RATE,\n" );
        sql.append("       NVL(ROUND(((SELECT SUM(CASE\n" );
        sql.append("                                WHEN T1.ORDER_COUNT = T2.OUT_AMOUNT THEN\n" );
        sql.append("                                 1\n" );
        sql.append("                                ELSE\n" );
        sql.append("                                 0\n" );
        sql.append("                              END)\n" );
        sql.append("                     FROM PT_BU_SALE_ORDER_DTL T1, PT_BU_STOCK_OUT_DTL T2\n" );
        sql.append("                    WHERE T1.PART_ID = T2.PART_ID\n" );
        sql.append("                      AND T1.SUPPLIER_ID = T2.SUPPLIER_ID\n" );
        sql.append("                      AND T1.ORDER_ID = A.ORDER_ID\n" );
        sql.append("                      AND T2.OUT_ID =\n" );
        sql.append("                          (SELECT OUT_ID\n" );
        sql.append("                             FROM PT_BU_STOCK_OUT\n" );
        sql.append("                            WHERE ORDER_ID = A.ORDER_ID)) /\n" );
        sql.append("                 (SELECT COUNT(ORDER_COUNT)\n" );
        sql.append("                     FROM PT_BU_SALE_ORDER_DTL\n" );
        sql.append("                    WHERE ORDER_ID = A.ORDER_ID)) * 100,\n" );
        sql.append("                 2),\n" );
        sql.append("           0) RATE1");
		sql.append("  FROM PT_BU_SALE_ORDER A, (SELECT ORDER_ID, CHECK_USER, MAX(CHECK_DATE) CHECK_DATE FROM PT_BU_SALE_ORDER_CHECK WHERE CHECK_RESULT = "+DicConstant.DDZT_03+" GROUP BY ORDER_ID, CHECK_USER) B, TM_USER C,PT_BU_ISSUE_ORDER D,PT_BA_WAREHOUSE E\n");
		sql.append(" WHERE "+conditions+" AND A.ORDER_TYPE = " + DicConstant.DDLX_07+"\n");
		sql.append(" AND A.ORDER_ID = B.ORDER_ID\n");
		sql.append(" AND B.CHECK_USER = C.ACCOUNT\n");
		sql.append(" AND B.ORDER_ID = D.ORDER_ID\n");
		sql.append(" AND A.WAREHOUSE_ID = E.WAREHOUSE_ID\n");
		sql.append(" AND E.WAREHOUSE_TYPE = '100101'\n");
		sql.append(" AND D.USER_ACCOUNT = '" + account + "'\n");
		sql.append(" AND D.PRINT_STATUS = " + DicConstant.DYZT_02 + "\n");
		sql.append(" AND D.ISSUE_STATUS = " + DicConstant.FLDFLZT_01 + "\n");
		sql.append(" AND A.SHIP_STATUS = " + DicConstant.DDFYZT_02 + "\n");
		sql.append(" AND A.ORDER_STATUS = " + DicConstant.DDZT_03 + "\n");
//		sql.append(" AND B.CHECK_RESULT = " + DicConstant.DDZT_03 + "\n");
		// sql.append(" AND NOT EXISTS(SELECT 1 FROM PT_BU_STOCK_OUT D WHERE D.ORDER_ID = A.ORDER_ID)\n"
		// );
		sql.append(" AND A.STATUS = " + DicConstant.YXBS_01 + "\n");
		sql.append(" AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n");
		sql.append(" ORDER BY A.APPLY_DATE DESC");

		// 执行方法，不需要传递conn参数
		bs = factory.select(sql.toString(), page);
		bs.setFieldDic("ORDER_TYPE", DicConstant.DDLX);
		bs.setFieldUserID("CHECK_USER");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd HH:mm:ss");
		return bs;
	}

	public QuerySet getWareInfo(String orderId) throws Exception {
		QuerySet qs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T1.ORDER_ID,T1.ORDER_NO,T3.WAREHOUSE_ID,T3.WAREHOUSE_CODE,T3.WAREHOUSE_NAME\n");
		sql.append("FROM PT_BU_ISSUE_ORDER T,PT_BU_SALE_ORDER T1,TM_USER T2,PT_BA_WAREHOUSE T3\n");
		sql.append("WHERE T.ORDER_ID = T1.ORDER_ID\n");
		sql.append("AND T.USER_ACCOUNT = T2.ACCOUNT\n");
		sql.append("AND T2.ORG_ID = T3.ORG_ID\n");
		sql.append("AND T3.WAREHOUSE_TYPE =100101\n");
		sql.append("AND T.ORDER_ID =" + orderId + "");
		qs = factory.select(null, sql.toString());
		return qs;
	}

	public QuerySet checkOut(String orderId) throws Exception {
		QuerySet qs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT OUT_ID FROM PT_BU_STOCK_OUT WHERE ORDER_ID = "
				+ orderId + "");
		qs = factory.select(null, sql.toString());
		return qs;
	}

	public QuerySet getIssue(String PART_ID, String detailId) throws Exception {
		QuerySet qs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.PART_ID,\n");
		sql.append("       T.PART_CODE,\n");
		sql.append("       T.PART_NAME,\n");
		sql.append("       T.SUPPLIER_ID,\n");
		sql.append("       T.SUPPLIER_CODE,\n");
		sql.append("       T.SUPPLIER_NAME,\n");
		sql.append("       T.POSITION_ID,\n");
		sql.append("       T1.UNIT\n");
		sql.append("  FROM PT_BU_ISSUE_ORDER_DTL T, PT_BA_INFO T1\n");
		sql.append(" WHERE T.PART_ID = T1.PART_ID");
		sql.append(" AND T.DTL_ID = " + detailId + "");
		sql.append(" AND T.PART_ID = " + PART_ID + "");
		qs = factory.select(null, sql.toString());
		return qs;
	}

	public boolean updateIssue(PtBuIssueOrderVO vo) throws Exception {
		return factory.update(vo);
	}

	public QuerySet checkIfAllOut(String orderId) throws Exception {
		QuerySet qs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  1 from PT_BU_ISSUE_ORDER WHERE ORDER_ID = "
				+ orderId + "AND  ISSUE_STATUS = " + DicConstant.FLDFLZT_01
				+ "");
		qs = factory.select(null, sql.toString());
		return qs;
	}

    /**
     * 查询订单状态
     * 
     * @param orderId 订单ID
     * @return
     * @throws Exception
     */
	public QuerySet checkOrderType(String orderId) throws Exception {
		QuerySet qs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  TRANS_TYPE FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "
				+ orderId + "");
		qs = factory.select(null, sql.toString());
		return qs;
	}

	public QuerySet checkSource(String orderId) throws Exception {
		QuerySet qs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  DIR_SOURCE_ORDER_ID from PT_BU_SALE_ORDER WHERE ORDER_ID = "
				+ orderId + "");
		qs = factory.select(null, sql.toString());
		return qs;
	}

	public QuerySet getSaleInfo(String orderId) throws Exception {
		QuerySet qs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT A.DIR_SOURCE_ORDER_ID,A.ORG_ID,A.DIR_SOURCE_ORDER_NO,A.ORDER_NO,A.CUSTORM_NAME, A.DELIVERY_ADDR, A.PHONE, NVL(B.AMOUNT,0)AMOUNT, NVL(B.COUNT,0)COUNT\n");
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
		sql.append("       T.UNIT,\n");
		sql.append("       T.SUPPLIER_ID,\n");
		sql.append("       T.SUPPLIER_CODE,\n");
		sql.append("       T.SUPPLIER_NAME,\n");
		sql.append("       T1.UNIT_PRICE SALE_PRICE,\n");
		sql.append("       T.REAL_COUNT SHOULD_COUNT,\n");
		sql.append("       T1.DTL_ID\n");
		sql.append("  FROM PT_BU_ISSUE_ORDER_DTL T, PT_BU_SALE_ORDER_DTL T1\n");
		sql.append(" WHERE T.PART_ID = T1.PART_ID");
		sql.append("  AND T.SUPPLIER_ID = T1.SUPPLIER_ID AND T.ORDER_ID = T1.ORDER_ID\n");
		sql.append("   AND T.ORDER_ID = " + orderId + "");
		qs = factory.select(null, sql.toString());
		return qs;
	}

	public boolean inserRealSale(PtBuRealSaleVO vo) throws Exception {
		return factory.update(vo);
	}

	public boolean insertRealSaleDtl(PtBuRealSaleDtlVO vo) throws Exception {
		return factory.update(vo);
	}

	public boolean updateInStorageOrder(PtBuSaleOrderDtlVO vo) throws Exception {
		return factory.update(vo);
	}

	public QuerySet dealerstock(String part_id, String supplierId, String orgId)
			throws Exception {

		QuerySet qs = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select  STOCK_ID,nvl(AMOUNT,0),nvl(AVAILABLE_AMOUNT,0) from pt_bu_dealer_stock t where t.part_id ="
				+ part_id
				+ " and t.supplier_id = "
				+ supplierId
				+ " and t.org_id =" + orgId);
		qs = factory.select(null, sql.toString());
		return qs;
	}

	public boolean updateDealerStock(PtBuDealerStockVO vo) throws Exception {
		return factory.update(vo);
	}

	public boolean orderStockInsert(PtBuDealerStockVO vo) throws Exception {
		String StockId = SequenceUtil.getCommonSerivalNumber(factory);
		vo.setStockId(StockId);
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
	
	public QuerySet checkPlanPrice(String partIds)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT to_char(wm_concat( PART_CODE)) CODES FROM PT_BA_INFO WHERE PART_ID IN ("+partIds+") AND NVL(PLAN_PRICE,0)=0\n" );
        return factory.select(null, sql.toString());
    }
	public QuerySet checkLock1(String partIds,String warehouseId)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT to_char(wm_concat( PART_CODE)) CODES FROM PT_BU_STOCK WHERE PART_ID IN ("+partIds+") AND STOCK_STATUS = "+DicConstant.KCZT_02+" AND WAREHOUSE_ID = "+warehouseId+"\n" );
        return factory.select(null, sql.toString());
    }
    public QuerySet checkLock2(String partIds,String warehouseId)
            throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT to_char(wm_concat( PART_CODE)) CODES FROM PT_BU_STOCK WHERE PART_ID IN ("+partIds+") AND STOCK_STATUS = "+DicConstant.KCZT_03+" AND WAREHOUSE_ID = "+warehouseId+"\n" );
        return factory.select(null, sql.toString());
    }
	public void insetStockDtl(String saleCount,String aAmount,String SHOULD_COUNT,User user,String saleId,String url,String nPartId) throws Exception {
	  	   StringBuffer sql= new StringBuffer();
	  	   sql.append("INSERT INTO PT_BU_DEALER_STOCK_LOG\n" );
	  	   sql.append("          (LOG_ID,\n" );
	  	   sql.append("           YWZJ,\n" );
	  	   sql.append("           ACTION_URL,\n" );
	  	   sql.append("           OAMOUNT,\n" );
	  	   sql.append("           AMOUNT,\n" );
	  	   sql.append("           AAMOUNT,\n" );
	  	   sql.append("           UPDATE_USER,\n" );
	  	   sql.append("           UPDATE_TIME,\n" );
	  	   sql.append("           PART_ID,\n" );
	  	   sql.append("           ORG_ID)\n" );
	  	   sql.append("        VALUES\n" );
	  	   sql.append("          (F_GETID(),\n" );
	  	   sql.append("           "+saleId+",\n" );
	  	   sql.append("           '"+url+"',\n" );
	  	   sql.append("           -"+SHOULD_COUNT+",\n" );
	  	   sql.append("           -"+saleCount+",\n" );
	  	   sql.append("           +"+aAmount+",\n" );
	  	   sql.append("           '"+user.getAccount()+"',\n" );
	  	   sql.append("           SYSDATE,\n" );
	  	   sql.append("           "+nPartId+",\n" );
	  	   sql.append("           "+user.getOrgId()+")");

	  	   factory.update(sql.toString(), null);
	     }
	    
	    public QuerySet getAmount(String outId,String orderId,User user)throws Exception
	    {
	        QuerySet qs = null;
	        StringBuffer sql= new StringBuffer();
	        sql.append("SELECT T.PART_ID,T.OUT_AMOUNT,NVL((T3.SHOULD_COUNT-T.OUT_AMOUNT),0) AAMONT,T3.SHOULD_COUNT\n" );
	        sql.append("FROM PT_BU_STOCK_OUT_DTL T,PT_BU_STOCK_OUT T1,PT_BU_ISSUE_ORDER T2,PT_BU_ISSUE_ORDER_DTL T3\n" );
	        sql.append("WHERE T.OUT_ID = T1.OUT_ID\n" );
	        sql.append("AND T.PART_ID = T3.PART_ID\n" );
	        sql.append("AND T1.ORDER_ID = T2.ORDER_ID\n" );
	        sql.append("AND T3.ISSUE_ID = T2.ISSUE_ID\n" );
	        sql.append("AND T2.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	        sql.append("AND T1.OUT_ID = "+outId+"");
	        sql.append("AND T1.ORDER_ID = "+orderId+"");
	        qs = factory.select(null, sql.toString());
	    return qs;
	    }
	    public QuerySet getRealAmount(String orderId) throws Exception {
	        QuerySet qs = null;
	        StringBuffer sql= new StringBuffer();
	        sql.append("SELECT SUM(NVL(T2.OUT_AMOUNT, 0) * NVL(T.UNIT_PRICE, 0)) REAL_AMOUNT\n" );
	        sql.append("  FROM PT_BU_SALE_ORDER_DTL T, PT_BU_STOCK_OUT T1, PT_BU_STOCK_OUT_DTL T2\n" );
	        sql.append(" WHERE T.ORDER_ID = T1.ORDER_ID\n" );
	        sql.append("   AND T1.OUT_ID = T2.OUT_ID\n" );
	        sql.append("   AND T.PART_ID = T2.PART_ID\n" );
	        sql.append("   AND T.ORDER_ID = "+orderId+"");
	        qs = factory.select(null, sql.toString());
	        return qs;
	    }
	    public QuerySet getDeliery(String orderId) throws Exception {
	        QuerySet qs = null;
	        StringBuffer sql= new StringBuffer();
	        sql.append("SELECT T.PART_ID, T.REAL_COUNT, T1.DTL_ID\n" );
	        sql.append("  FROM PT_BU_ISSUE_ORDER_DTL T, PT_BU_SALE_ORDER_DTL T1\n" );
	        sql.append(" WHERE T.ORDER_ID = T1.ORDER_ID\n" );
	        sql.append("   AND T.PART_ID = T1.PART_ID\n" );
	        sql.append("   AND T.ORDER_ID = "+orderId+"");
	        qs = factory.select(null, sql.toString());
	        return qs;
	    }
	    public boolean updateSaleOrderDtl(PtBuSaleOrderDtlVO vo)
	            throws Exception {
	        return factory.update(vo);
	    }
	    public QuerySet getcNo()
	            throws Exception {

	        QuerySet qs = null;
	        StringBuffer sql = new StringBuffer();
	        sql.append("SELECT F_GETOUT_CONTINUAL C_NO FROM DUAL");
	        qs = factory.select(null, sql.toString());
	        return qs;
	    }
	    public QuerySet getPlanPrice(String partId)throws Exception
	    {
	        QuerySet qs = null;
	        StringBuffer sql= new StringBuffer();
	        sql.append("SELECT NVL(PLAN_PRICE,0) PLAN_PRICE FROM PT_BA_INFO WHERE PART_ID = "+partId+"");
	        qs = factory.select(null, sql.toString());
	        return qs;
	    }
	    public QuerySet getOutConId()throws Exception
	    {
	        QuerySet qs = null;
	        StringBuffer sql= new StringBuffer();
	        sql.append("SELECT F_GETID() CON_ID FROM DUAL");
	        qs = factory.select(null, sql.toString());
	        return qs;
	    }
	    public QuerySet getOld(String OUT_ID,String partId)throws Exception
	    {
	        QuerySet qs = null;
	        StringBuffer sql= new StringBuffer();
	        sql.append("SELECT  DISTINCT NVL(OUT_COUNT,0) OUT_COUNT\n" );
	        sql.append("  FROM PT_BU_STOCK_OUT_CONTINUAL\n" );
	        sql.append(" WHERE 1 = 1\n" );
	        sql.append("   AND OUT_ID = "+OUT_ID+"\n" );
	        sql.append("   AND PART_ID = "+partId+"\n" );
	        sql.append("   AND CREATE_TIME = (SELECT MAX(CREATE_TIME)\n" );
	        sql.append("                        FROM PT_BU_STOCK_OUT_CONTINUAL\n" );
	        sql.append("                       WHERE OUT_ID = "+OUT_ID+"\n" );
	        sql.append("                         AND PART_ID = "+partId+")");
	        qs = factory.select(null, sql.toString());
	        return qs;
	    }
	    public boolean insertOutCon(PtBuStockOutContinualVO vo)
	            throws Exception {
	        return factory.insert(vo);
	    }
}