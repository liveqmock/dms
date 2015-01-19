package com.org.dms.dao.part.storageMng.stockInMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.*;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

import java.util.Map;

/**
 * 移库入库Dao
 *
 * @user : lichuang
 * @date : 2014-07-15
 */
public class MoveStockInMngDao extends BaseDAO {
    //定义instance
    public static final MoveStockInMngDao getInstance(ActionContext atx) {
        MoveStockInMngDao dao = new MoveStockInMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    public QuerySet getArea(String position) throws Exception {
    	String sql = "SELECT AREA_ID,AREA_CODE,AREA_NAME FROM PT_BA_WAREHOUSE_POSITION WHERE POSITION_ID='"+position+"'\n";
        return factory.select(null, sql);
    }
    // 插入库存日志
    public void insetStockDtl(String saleCount,User user,String saleId,String url,String nPartId) throws Exception {
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
 	   sql.append("           '"+saleId+"',\n" );
 	   sql.append("           '"+url+"',\n" );
 	   sql.append("           '',\n" );
 	   sql.append("           '"+saleCount+"',\n" );
 	   sql.append("           '"+saleCount+"',\n" );
 	   sql.append("           '"+user.getAccount()+"',\n" );
 	   sql.append("           SYSDATE,\n" );
 	   sql.append("           '"+nPartId+"',\n" );
 	   sql.append("           "+user.getOrgId()+")");

 	   factory.update(sql.toString(), null);
    }

    /**
     * 查询入库单
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchInBill(PageManager page, User user, String conditions,String ACCOUNT) throws Exception {
    	String sql1 = "SELECT WAREHOUSE_TYPE FROM PT_BA_WAREHOUSE T1,TM_USER T2 WHERE T1.ORG_ID = T2.ORG_ID AND T2.ACCOUNT = '"+ACCOUNT+"'\n";
    	QuerySet qs = factory.select(null, sql1.toString());
    	BaseResultSet bs = null;
    	if(qs.getRowCount()>0){
    		String warehouseType=qs.getString(1, "WAREHOUSE_TYPE");
    		if(!"100101".equals(warehouseType)){
    			StringBuilder wheres = new StringBuilder(conditions);
    			wheres.append("   AND A.RECEIVE_WAREHOUSE = B.WAREHOUSE_ID\n");
    			wheres.append("   AND A.OUT_STATUS = "+DicConstant.CKDZT_02+"\n");
    	        wheres.append("   AND A.OUT_ID NOT IN (SELECT ORDER_ID FROM PT_BU_STOCK_IN WHERE IN_TYPE = "+DicConstant.RKLX_02+")\n");
    	        wheres.append("   AND A.RECEIVE_WAREHOUSE IN\n" );
    	        wheres.append("    (SELECT DISTINCT K.WAREHOUSE_ID\n" );
    	        wheres.append("       FROM PT_BA_WAREHOUSE_KEEPER K\n" );
    	        wheres.append("      WHERE K.USER_ACCOUNT ='"+user.getAccount()+"')");
    	        wheres.append("   ORDER BY A.CREATE_TIME DESC\n");
    	        page.setFilter(wheres.toString());
    			StringBuffer sql= new StringBuffer();
    			sql.append("SELECT A.OUT_ID,\n" );
    			sql.append("       A.OUT_NO,\n" );
    			sql.append("       A.WAREHOUSE_ID      FROM_WAREHOUSE_ID,\n" );
    			sql.append("       A.WAREHOUSE_CODE    FROM_WAREHOUSE_CODE,\n" );
    			sql.append("       A.WAREHOUSE_NAME    FROM_WAREHOUSE_NAME,\n" );
    			sql.append("       A.RECEIVE_WAREHOUSE WAREHOUSE_ID,\n" );
    			sql.append("       B.WAREHOUSE_CODE,\n" );
    			sql.append("       B.WAREHOUSE_NAME,\n" );
    			sql.append("       B.WAREHOUSE_TYPE,\n" );
    			sql.append("       A.MOVE_MAN,\n" );
    			sql.append("       A.CREATE_USER,\n" );
    			sql.append("       A.OUT_DATE\n" );
    			sql.append("  FROM PT_BU_STOCK_OUT A, PT_BA_WAREHOUSE B\n" );
    			bs = factory.select(sql.toString(), page);
    		}else{
    			StringBuilder wheres = new StringBuilder(conditions);
    			wheres.append("   AND A.RECEIVE_WAREHOUSE = B.WAREHOUSE_ID\n");
    	        wheres.append("   AND A.OUT_ID = C.OUT_ID\n");
    	        wheres.append("   AND A.OUT_STATUS = "+DicConstant.CKDZT_02+"\n");
    	        wheres.append("   AND C.USER_ACCOUNT ='"+user.getAccount()+"'\n");
    	        page.setFilter(wheres.toString());
    			StringBuffer sql= new StringBuffer();
    			sql.append("SELECT A.OUT_ID,\n" );
    			sql.append("       A.OUT_NO,\n" );
    			sql.append("       A.WAREHOUSE_ID      FROM_WAREHOUSE_ID,\n" );
    			sql.append("       A.WAREHOUSE_CODE    FROM_WAREHOUSE_CODE,\n" );
    			sql.append("       A.WAREHOUSE_NAME    FROM_WAREHOUSE_NAME,\n" );
    			sql.append("       A.RECEIVE_WAREHOUSE WAREHOUSE_ID,\n" );
    			sql.append("       B.WAREHOUSE_CODE,\n" );
    			sql.append("       B.WAREHOUSE_NAME,\n" );
    			sql.append("       B.WAREHOUSE_TYPE,\n" );
    			sql.append("       A.MOVE_MAN,\n" );
    			sql.append("       A.CREATE_USER,\n" );
    			sql.append("       A.OUT_DATE\n" );
    			sql.append("  FROM PT_BU_STOCK_OUT A, PT_BA_WAREHOUSE B, PT_BU_STOCK_IN_KEEPER C\n" );
    			bs = factory.select(sql.toString(), page);
    			
    		}
    		bs.setFieldUserID("CREATE_USER");
	        bs.setFieldDateFormat("OUT_DATE", "yyyy-MM-dd");
    	}
    	
        return bs;
    }

    /**
     * 查询移库出库单
     *
     * @param page
     * @param user
     * @param conditions
     * @param warehouseId 仓库ID
     * @return
     * @throws Exception
     */
    public BaseResultSet searchMove(PageManager page, User user, String conditions, String warehouseId) throws Exception {
        String wheres = conditions;
        wheres += " AND A.OUT_TYPE = " + DicConstant.CKLX_03 + "\n";
        wheres += " AND A.OUT_STATUS = " + DicConstant.CKDZT_02 + "\n";
        wheres += " AND A.RECEIVE_WAREHOUSE = " + warehouseId + "\n";
        wheres += " AND NOT EXISTS(\n";
        wheres += "     SELECT 1 FROM PT_BU_STOCK_IN B WHERE B.ORDER_ID = A.OUT_ID\n";
        wheres += " )\n";
        wheres += " AND A.STATUS = " + DicConstant.YXBS_01 + "\n";
        wheres += " AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n";
        wheres += " ORDER BY A.CREATE_TIME DESC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.OUT_ID,\n");
        sql.append("       A.OUT_NO,\n");
        sql.append("       A.WAREHOUSE_ID OUT_WAREHOUSE_ID,\n");
        sql.append("       A.WAREHOUSE_CODE OUT_WAREHOUSE_CODE,\n");
        sql.append("       A.WAREHOUSE_NAME OUT_WAREHOUSE_NAME,\n");
        sql.append("       A.CREATE_USER OUT_CREATE_USER\n");
        sql.append("  FROM PT_BU_STOCK_OUT A\n");
        bs = factory.select(sql.toString(), page);
        bs.setFieldUserID("OUT_CREATE_USER");
        return bs;
    }

    /**
     * 查询入库单配件
     *
     * @param page
     * @param user
     * @param conditions
     * @param IN_ID      入库单ID
     * @param ORDER_ID   移库出库单ID
     * @return
     * @throws Exception
     */
    public BaseResultSet searchInBillPart(PageManager page, User user, String conditions, String outId, String warehouseType,String warehouseId,String account) throws Exception {
        StringBuilder wheres = new StringBuilder(conditions);
        wheres.append(" ORDER BY T.PART_CODE ASC\n");
        page.setFilter(wheres.toString());
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT * FROM (SELECT T1.PART_ID,\n" );
        sql.append("       T1.PART_CODE,\n" );
        sql.append("       T1.PART_NAME,\n" );
        sql.append("       T1.UNIT,\n" );
        sql.append("       T1.MIN_PACK,\n" );
        sql.append("       T1.MIN_UNIT,\n" );
        sql.append("       T1.SUPPLIER_ID,\n" );
        sql.append("       T1.SUPPLIER_CODE,\n" );
        sql.append("       T1.SUPPLIER_NAME,\n" );
        sql.append("       T1.AVAILABLE_AMOUNT,\n" );
        sql.append("       T1.OUT_AMOUNT,\n" );
        sql.append("       WM_CONCAT(T2.POSITION_ID) POSITION_IDS,\n" );
        sql.append("       WM_CONCAT(T2.POSITION_CODE) POSITION_CODES,\n" );
        sql.append("       WM_CONCAT(T2.POSITION_NAME) POSITION_NAMES\n" );
        sql.append("  FROM (SELECT A.PART_ID,\n" );
        sql.append("               A.PART_CODE,\n" );
        sql.append("               A.PART_NAME,\n" );
        sql.append("               B.UNIT,\n" );
        sql.append("               B.MIN_PACK,\n" );
        sql.append("               B.MIN_UNIT,\n" );
        sql.append("               A.SUPPLIER_ID,\n" );
        sql.append("               A.SUPPLIER_CODE,\n" );
        sql.append("               A.SUPPLIER_NAME,\n" );
        sql.append("               NVL(C.AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT,\n" );
        sql.append("               SUM(A.OUT_AMOUNT) OUT_AMOUNT\n" );
        sql.append("          FROM PT_BU_STOCK_OUT_DTL A,\n" );
        sql.append("               PT_BA_INFO B,\n" );
        sql.append("               (SELECT * FROM PT_BU_STOCK WHERE WAREHOUSE_ID = "+warehouseId+") C\n" );
        if("100101".equals(warehouseType)){
        	 sql.append("               ,PT_BA_WAREHOUSE_KEEPER D\n" );
        }
        sql.append("         WHERE A.PART_ID = B.PART_ID\n" );
        sql.append("           AND A.PART_ID = C.PART_ID(+)\n" );
        sql.append("           AND A.SUPPLIER_ID = C.SUPPLIER_ID(+)\n" );
        if("100101".equals(warehouseType)){
	        sql.append("           AND A.PART_ID = D.PART_ID\n" );
	        sql.append("           AND D.USER_ACCOUNT = '"+account+"'\n" );
        }
        sql.append("           AND A.OUT_ID = "+outId+"\n" );
        sql.append("         GROUP BY A.PART_ID,\n" );
        sql.append("                  A.PART_CODE,\n" );
        sql.append("                  A.PART_NAME,\n" );
        sql.append("                  B.UNIT,\n" );
        sql.append("                  B.MIN_PACK,\n" );
        sql.append("                  B.MIN_UNIT,\n" );
        sql.append("                  A.SUPPLIER_ID,\n" );
        sql.append("                  A.SUPPLIER_CODE,\n" );
        sql.append("                  A.SUPPLIER_NAME,\n" );
        sql.append("                  C.AVAILABLE_AMOUNT) T1\n" );
        sql.append("  LEFT JOIN PT_BA_WAREHOUSE_PART_RL T2\n" );
        sql.append("    ON (T1.PART_ID = T2.PART_ID AND T2.STATUS='"+DicConstant.YXBS_01+"'\n" );
        sql.append("   AND T2.POSITION_ID IN (SELECT S.POSITION_ID\n" );
        sql.append("                            FROM PT_BA_WAREHOUSE_POSITION S,\n" );
        sql.append("                                 PT_BA_WAREHOUSE_AREA     M,\n" );
        sql.append("                                 PT_BA_WAREHOUSE          N\n" );
        sql.append("                           WHERE S.AREA_ID = M.AREA_ID\n" );
        sql.append("                             AND M.WAREHOUSE_ID = N.WAREHOUSE_ID\n" );
        sql.append("                             AND N.WAREHOUSE_ID = "+warehouseId+"))\n" );
        sql.append(" GROUP BY T1.PART_ID,\n" );
        sql.append("          T1.PART_CODE,\n" );
        sql.append("          T1.PART_NAME,\n" );
        sql.append("          T1.UNIT,\n" );
        sql.append("          T1.MIN_PACK,\n" );
        sql.append("          T1.MIN_UNIT,\n" );
        sql.append("          T1.SUPPLIER_ID,\n" );
        sql.append("          T1.SUPPLIER_CODE,\n" );
        sql.append("          T1.SUPPLIER_NAME,\n" );
        sql.append("          T1.AVAILABLE_AMOUNT,\n" );
        sql.append("          T1.OUT_AMOUNT)T\n");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", DicConstant.JLDW);
        bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);
        return bs;
    }

    /**
     * 新增入库单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertInBill(PtBuStockInVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 新增入库单明细
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertInBillDtl(PtBuStockInDtlVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 新增库存
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertStock(PtBuStockVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 新增库存明细
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertStockDtl(PtBuStockDtlVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 修改入库单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateInBill(PtBuStockInVO vo)
            throws Exception {
        return factory.update(vo);
    }

    /**
     * 修改入库单明细
     *
     * @param map
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateInBillDtl(Map<String, String> map, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_IN_DTL A\n");
        sql.append("   SET A.IN_AMOUNT = A.IN_AMOUNT + " + map.get("CUR_IN_AMOUNT") + ", A.REMARKS = '" + map.get("REMARK") + "'\n");
        sql.append(" WHERE A.IN_ID = " + map.get("IN_ID") + "\n");
        sql.append("   AND A.PART_ID = " + map.get("PART_ID") + "\n");
        sql.append("   AND A.SUPPLIER_ID = " + map.get("SUPPLIER_ID") + "\n");

        return factory.update(sql.toString(), null);
    }

    /**
     * 修改库存
     *
     * @param stockId     库存ID
     * @param curInAmount 本次入库数量
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateStock(String stockId, String curInAmount, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK A\n");
        sql.append("   SET A.AMOUNT = A.AMOUNT + " + curInAmount + ",\n");
        sql.append("       A.AVAILABLE_AMOUNT = A.AVAILABLE_AMOUNT + " + curInAmount + ",\n");
        sql.append("       UPDATE_USER = '" + user.getAccount() + "',\n");
        sql.append("       UPDATE_TIME = SYSDATE\n");
        sql.append(" WHERE A.STOCK_ID = " + stockId + "\n");

        return factory.update(sql.toString(), null);
    }

    /**
     * 修改库存明细
     *
     * @param stockDtlId  库存明细ID
     * @param curInAmount 当前入库数量
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateStockDtl(String stockDtlId, String curInAmount, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_DTL A\n");
        sql.append("   SET A.AMOUNT = A.AMOUNT + " + curInAmount + ",A.AVAILABLE_AMOUNT = A.AVAILABLE_AMOUNT + " + curInAmount + ",UPDATE_USER = '" + user.getAccount() + "',UPDATE_TIME = SYSDATE\n");
        sql.append(" WHERE A.DTL_ID = " + stockDtlId + "\n");

        return factory.update(sql.toString(), null);
    }

    /**
     * 校验库存是否存在
     *
     * @param WAREHOUSE_ID 仓库ID
     * @param PART_ID      配件ID
     * @param SUPPLIER_ID  供应商ID
     * @param user
     * @return
     * @throws Exception
     */
    public String checkStockIsExist(String WAREHOUSE_ID, String PART_ID, String SUPPLIER_ID, User user) throws Exception {
        String stockId = "";
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.STOCK_ID\n");
        sql.append("  FROM PT_BU_STOCK A\n");
        sql.append(" WHERE A.WAREHOUSE_ID = " + WAREHOUSE_ID + "\n");
        sql.append("   AND A.PART_ID = " + PART_ID + "\n");
        sql.append("   AND A.SUPPLIER_ID = " + SUPPLIER_ID + "\n");
        sql.append("   AND A.STATUS = " + DicConstant.YXBS_01 + "\n");
        sql.append("   AND A.OEM_COMPANY_ID = " + user.getOemCompanyId() + "\n");

        QuerySet qs = factory.select(null, sql.toString());
        if (qs.getRowCount() > 0) {
            stockId = qs.getString(1, "STOCK_ID");
        }
        return stockId;
    }

    /**
     * 校验库存明细(库位)是否存在
     *
     * @param POSITION_ID 库位ID
     * @param PART_ID     配件ID
     * @param SUPPLIER_ID 供应商ID
     * @param user
     * @return
     * @throws Exception
     */
    public String checkStockDtlIsExist(String POSITION_ID, String PART_ID, String SUPPLIER_ID, User user) throws Exception {
        String stockDtlId = "";
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.DTL_ID\n");
        sql.append("  FROM PT_BU_STOCK_DTL A\n");
        sql.append(" WHERE A.POSITION_ID = " + POSITION_ID + "\n");
        sql.append("   AND A.PART_ID = " + PART_ID + "\n");
        sql.append("   AND A.SUPPLIER_ID = " + SUPPLIER_ID + "\n");

        QuerySet qs = factory.select(null, sql.toString());
        if (qs.getRowCount() > 0) {
            stockDtlId = qs.getString(1, "DTL_ID");
        }
        return stockDtlId;
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
     * 删除入库单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean deleteInBill(PtBuStockInVO vo) throws Exception {
        return factory.delete(vo);
    }
    
    public boolean deleteInBillKeeper(String userAccount,String outId) throws Exception {
    	String sql = "DELETE FROM PT_BU_STOCK_IN_KEEPER WHERE USER_ACCOUNT ='"+userAccount+"' AND OUT_ID="+outId+"\n";
        return factory.update(sql, null);
    }
    public QuerySet selectInBillKeeper(String userAccount,String outId) throws Exception {
    	String sql = "SELECT * FROM PT_BU_STOCK_IN_KEEPER WHERE USER_ACCOUNT ='"+userAccount+"' AND OUT_ID="+outId+"\n";
        return factory.select(null, sql);
    }
    /**
     * 删除入库单明细
     *
     * @param inId 入库单ID
     * @return
     * @throws Exception
     */
    public boolean deleteInBillDtl(String inId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM PT_BU_STOCK_IN_DTL WHERE IN_ID = " + inId + "\n");
        return factory.delete(sql.toString(), null);
    }

    /**
     * 校验入库单明细是否存在
     *
     * @param IN_ID 入库单ID
     * @return
     * @throws Exception
     */
    public QuerySet checkInBillIsExist(String outId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT IN_ID,IN_NO\n");
        sql.append("  FROM PT_BU_STOCK_IN A\n");
        sql.append(" WHERE A.ORDER_ID = " + outId + " AND A.IN_TYPE = "+DicConstant.RKLX_02+" AND A.IN_STATUS="+DicConstant.RKDZT_01+"\n");
        return factory.select(null, sql.toString());
    }

    /**
     * 修改移库入库的已入库数量
     *
     * @param splitId      采购拆分单ID
     * @param totalInCount 本次入库数量之和
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateSplit(String splitId, int totalInCount, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_PCH_ORDER_SPLIT A\n");
        sql.append("   SET A.IN_AMOUNT = NVL(A.IN_AMOUNT,0) + " + totalInCount + ", A.UPDATE_USER = '" + user.getAccount() + "',A.UPDATE_TIME = SYSDATE\n");
        sql.append(" WHERE A.SPLIT_ID = " + splitId + "\n");

        return factory.update(sql.toString(), null);
    }

    /**
     * 修改移库入库明细的已入库数量
     *
     * @param map
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateSplitDtl(Map<String, String> map, User user)
            throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_IN_DTL A\n");
        sql.append("   SET A.IN_AMOUNT = NVL(A.IN_AMOUNT,0) + " + map.get("CUR_IN_AMOUNT")+"\n");
        sql.append(" WHERE A.DETAIL_ID = " + map.get("DETAIL_ID") + "\n");

        return factory.update(sql.toString(), null);
    }

    /**
     * 新增入库流水
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertContinual(PtBuStockInContinualVO vo)
            throws Exception {
        return factory.insert(vo);
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
    
    public void updateInBillDtl(String IN_ID) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_IN_DTL A\n");
        sql.append("   SET \n");
//        if (DicConstant.RKLX_01.equals(IN_TYPE)) {//只有采购入库更新采购单价和采购金额
//            sql.append("       A.PCH_PRICE  =\n");
//            sql.append("       (SELECT D.PCH_PRICE\n");
//            sql.append("          FROM PT_BU_STOCK_IN            B,\n");
//            sql.append("               PT_BU_PCH_ORDER_SPLIT_DTL C,\n");
//            sql.append("               PT_BU_PCH_ORDER_SPLIT 	 E,\n");
//            sql.append("               PT_BA_INFO    D\n");
//            sql.append("         WHERE B.IN_ID = " + IN_ID + "\n");
//            sql.append("           AND B.ORDER_ID = C.SPLIT_ID\n");
//            sql.append("           AND C.SPLIT_ID = E.SPLIT_ID\n");
//            sql.append("           AND E.SUPPLIER_ID = D.SUPPLIER_ID AND D.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n");
//            sql.append("           AND C.PART_ID = D.PART_ID\n");
//            sql.append("           AND C.PART_ID = A.PART_ID),\n");
//            sql.append("       A.PCH_AMOUNT =\n");
//            sql.append("       (SELECT D.PCH_PRICE * A.IN_AMOUNT\n");
//            sql.append("          FROM PT_BU_STOCK_IN            B,\n");
//            sql.append("               PT_BU_PCH_ORDER_SPLIT_DTL C,\n");
//            sql.append("               PT_BU_PCH_ORDER_SPLIT 	 E,\n");
//            sql.append("               PT_BA_PART_SUPPLIER_RL    D\n");
//            sql.append("         WHERE B.IN_ID = " + IN_ID + "\n");
//            sql.append("           AND B.ORDER_ID = C.SPLIT_ID\n");
//            sql.append("           AND C.SPLIT_ID = E.SPLIT_ID\n");
//            sql.append("           AND E.SUPPLIER_ID = D.SUPPLIER_ID AND D.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n");
//            sql.append("           AND C.PART_ID = D.PART_ID\n");
//            sql.append("           AND C.PART_ID = A.PART_ID),\n");
//        }
        sql.append("       A.PLAN_PRICE =\n");
        sql.append("       (SELECT E.PLAN_PRICE FROM PT_BA_INFO E WHERE E.PART_ID = A.PART_ID),\n");
        sql.append("       A.PLAN_AMOUNT =\n");
        sql.append("       (SELECT E.PLAN_PRICE * A.IN_AMOUNT\n");
        sql.append("          FROM PT_BA_INFO E\n");
        sql.append("         WHERE E.PART_ID = A.PART_ID),\n");
        sql.append("       A.UNIT       =\n");
        sql.append("       (SELECT E.UNIT FROM PT_BA_INFO E WHERE E.PART_ID = A.PART_ID)\n");
        sql.append(" WHERE A.IN_ID = " + IN_ID + "\n");

        factory.update(sql.toString(), null);
    }

    /**
     * 更新入库流水的计划价
     *
     * @param IN_ID 入库单ID
     * @throws Exception
     */
    public void updateInFlow(String IN_ID) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE PT_BU_STOCK_IN_CONTINUAL A\n");
        sql.append("   SET A.PLAN_PRICE =\n");
        sql.append("       (SELECT 1 FROM PT_BA_INFO B WHERE B.PART_ID = A.PART_ID)\n");
        sql.append(" WHERE A.IN_ID = " + IN_ID + "\n");
        factory.update(sql.toString(), null);
    }
}