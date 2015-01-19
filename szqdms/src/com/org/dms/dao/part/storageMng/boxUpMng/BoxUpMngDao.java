package com.org.dms.dao.part.storageMng.boxUpMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuBoxUpVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * 装箱清单维护Dao
 *
 * @user : lichuang
 * @date : 2014-07-21
 */
public class BoxUpMngDao extends BaseDAO {
    //定义instance
    public static final BoxUpMngDao getInstance(ActionContext atx) {
        BoxUpMngDao dao = new BoxUpMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 查询销售订单
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchSaleOrder(PageManager page, User user, String conditions) throws Exception {
        String wheres = conditions;
        wheres += " AND A.ORDER_ID = B.ORDER_ID\n";
        wheres += " AND B.CHECK_USER = C.ACCOUNT\n";
        wheres += " AND A.SHIP_STATUS = " + DicConstant.DDFYZT_03 + "\n";
        wheres += " AND A.STATUS = " + DicConstant.YXBS_01 + "\n";
        wheres += " AND A.OEM_COMPANY_ID=" + user.getOemCompanyId() + "\n";
        wheres += " AND A.ORDER_ID = D.ORDER_ID\n";
        wheres += " AND D.REAL_COUNT > 0\n";
        wheres += " ORDER BY A.APPLY_DATE DESC\n";
        page.setFilter(wheres);
        //定义返回结果集
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
        sql.append("  FROM PT_BU_SALE_ORDER A, (SELECT ORDER_ID, CHECK_USER, MAX(CHECK_DATE) CHECK_DATE FROM PT_BU_SALE_ORDER_CHECK WHERE CHECK_RESULT = 202203 GROUP BY ORDER_ID, CHECK_USER) B, TM_USER C,\n");
        sql.append("  (SELECT SUM(NVL(REAL_COUNT,0)) REAL_COUNT,ORDER_ID FROM PT_BU_ISSUE_ORDER_DTL GROUP BY ORDER_ID)D");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("ORDER_TYPE", DicConstant.DDLX);
        bs.setFieldUserID("CHECK_USER");
        bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
        bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd HH:mm:ss");
        return bs;
    }

    /**
     * 查询销售订单配件
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchSaleOrderPart(PageManager page, User user, String conditions, String ORDER_ID) throws Exception {
        String wheres = conditions;
        wheres += " AND A.PART_ID = B.PART_ID\n";
        wheres += " AND A.PART_ID = C.PART_ID(+)\n";
       // wheres += " AND NVL(A.REAL_COUNT,0) >0\n";
        wheres += " ORDER BY A.ISSUE_NO,A.PART_CODE ASC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.PART_ID,\n");
        sql.append("       A.PART_CODE,\n");
        sql.append("       A.PART_NAME,\n");
        sql.append("       B.UNIT,\n");
        sql.append("       B.MIN_PACK,\n");
        sql.append("       A.SUPPLIER_ID,\n");
        sql.append("       A.SUPPLIER_CODE,\n");
        sql.append("       DECODE(A.SUPPLIER_CODE,'9XXXXXX','',A.SUPPLIER_NAME) SUPPLIER_NAME,\n");
        sql.append("       A.ISSUE_ID,\n");
        sql.append("       A.ISSUE_NO,\n");
        sql.append("       A.SHOULD_COUNT,\n");
        sql.append("       NVL(A.REAL_COUNT,0)REAL_COUNT,\n");
        sql.append("       C.BOX_NO,\n");
        sql.append("       C.COUNT,\n");
        sql.append("       C.REMARKS\n");
        sql.append("  FROM (SELECT PART_ID,PART_CODE,PART_NAME,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,ISSUE_ID,ISSUE_NO,SUM(SHOULD_COUNT) SHOULD_COUNT,SUM(REAL_COUNT) REAL_COUNT FROM PT_BU_ISSUE_ORDER_DTL WHERE ORDER_ID = " + ORDER_ID + " GROUP BY PART_ID,PART_CODE,PART_NAME,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,ISSUE_ID,ISSUE_NO) A,\n");
        sql.append("       PT_BA_INFO B,\n");
        sql.append("       (SELECT D.PART_ID,wm_concat(D.BOX_NO) BOX_NO,wm_concat(D.COUNT) COUNT,D.REMARKS FROM PT_BU_BOX_UP D WHERE ORDER_ID = "+ORDER_ID+" GROUP BY D.PART_ID,D.REMARKS) C\n");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", DicConstant.JLDW);
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
     * 校验装箱清单是否存在
     *
     * @param ISSUE_ID 发料单ID
     * @param PART_ID  配件ID
     * @param user
     * @return
     * @throws Exception
     */
    public String checkBoxUpIsExist(String ISSUE_ID, String PART_ID, User user) throws Exception {
        String UP_ID = "";
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.UP_ID\n");
        sql.append("  FROM PT_BU_BOX_UP A\n");
        sql.append(" WHERE A.ISSUE_ID = " + ISSUE_ID + "\n");
        sql.append("   AND A.PART_ID = " + PART_ID + "\n");

        QuerySet qs = factory.select(null, sql.toString());
        if (qs.getRowCount() > 0) {
            UP_ID = qs.getString(1, "UP_ID");
        }
        return UP_ID;
    }

    /**
     * 校验装箱清单是否存在
     *
     * @param ORDER_ID 销售订单ID
     * @return
     * @throws Exception
     */
    public Boolean checkBoxUpIsExist(String ORDER_ID) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.UP_ID\n");
        sql.append("  FROM PT_BU_BOX_UP A\n");
        sql.append(" WHERE A.ORDER_ID = " + ORDER_ID + "\n");

        QuerySet qs = factory.select(null, sql.toString());
        if (qs.getRowCount() > 0) {
            return true;
        }
        return false;
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

    /**
     * 新增装箱单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertBoxUp(PtBuBoxUpVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 修改装箱单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateBoxUp(PtBuBoxUpVO vo)
            throws Exception {
        return factory.update(vo);
    }

    /**
     * 修改销售订单
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateSaleOrder(PtBuSaleOrderVO vo)
            throws Exception {
        return factory.update(vo);
    }

    /**
     * 修改发运单对应的订单的明细的发运数量
     *
     * @param SHIP_ID 发运单ID
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateSaleOrderDtl(String orderId, User user)
            throws Exception {
        StringBuilder sql= new StringBuilder();
        sql.append("UPDATE PT_BU_SALE_ORDER_DTL A\n" );
        sql.append("   SET A.DELIVERY_COUNT =\n" );
        sql.append("       (SELECT SUM(NVL(B.OUT_AMOUNT,0)) COUNT\n" );
        sql.append("          FROM PT_BU_STOCK_OUT_DTL B\n" );
        sql.append("         WHERE B.OUT_ID=(SELECT OUT_ID FROM PT_BU_STOCK_OUT WHERE ORDER_ID= A.ORDER_ID)\n" );
        sql.append("           AND B.PART_ID = A.PART_ID),\n" );
        sql.append("       A.UPDATE_USER    = '"+user.getAccount()+"',\n" );
        sql.append("       A.UPDATE_TIME    = SYSDATE\n" );
        sql.append(" WHERE 1 = 1\n" );
        sql.append("   AND A.ORDER_ID = '" + orderId + "'\n");
        return factory.update(sql.toString(), null);
    }
    
    
    public boolean updateSaleOrderAmount(String orderId, User user)
            throws Exception {
        StringBuilder sql= new StringBuilder();
        sql.append("UPDATE PT_BU_SALE_ORDER A\n" );
        sql.append("   SET A.REAL_AMOUNT =\n" );
        sql.append("(SELECT SUM(NVL(T.UNIT_PRICE,0) * NVL(T1.OUT_AMOUNT,0))\n" );
        sql.append("  FROM PT_BU_SALE_ORDER_DTL T, PT_BU_STOCK_OUT_DTL T1\n" );
        sql.append(" WHERE T1.OUT_ID=(SELECT OUT_ID FROM PT_BU_STOCK_OUT WHERE ORDER_ID= T.ORDER_ID)\n" );
        sql.append("   AND T.PART_ID = T1.PART_ID\n" );
        sql.append("   AND T.ORDER_ID = "+orderId+"),");
        sql.append("       A.UPDATE_USER    = '"+user.getAccount()+"',\n" );
        sql.append("       A.UPDATE_TIME    = SYSDATE\n" );
        sql.append(" WHERE 1 = 1\n" );
        sql.append("   AND A.ORDER_ID = '" + orderId + "'\n");
        return factory.update(sql.toString(), null);
    }

    /**
     * 查询原订单
     *
     * @param orderId 订单ID
     * @return 结果集
     * @throws Exception
     */
    public QuerySet getserverOrderId(String orderId)throws Exception {

        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append(" SELECT NVL(DIR_SOURCE_ORDER_ID,0) DIR_SOURCE_ORDER_ID FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "+orderId+"\n" );
        qs = factory.select(null,sql.toString());
        return qs;
    }

    /**
     * 导出装箱单
     *
     * @param ORDER_ID
     * @return
     * @throws Exception
     */
    public QuerySet exportBoxUp(String ORDER_ID) throws Exception {

        //定义返回结果集
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.*,ROWNUM FROM(\n");
        sql.append("SELECT A.PART_ID,\n");
        sql.append("       A.PART_CODE,\n");
        sql.append("       A.PART_NAME,\n");
        sql.append("       D.DIC_VALUE UNIT,\n");
        sql.append("       B.MIN_PACK,\n");
        sql.append("       A.SUPPLIER_ID,\n");
        sql.append("       A.SUPPLIER_CODE,\n");
        sql.append("       DECODE(A.SUPPLIER_CODE,'9XXXXXX','',A.SUPPLIER_NAME) SUPPLIER_NAME,\n");
        sql.append("       A.ISSUE_ID,\n");
        sql.append("       A.ISSUE_NO,\n");
        sql.append("       A.SHOULD_COUNT,\n");
        sql.append("       A.REAL_COUNT,\n");
        sql.append("       C.BOX_NO,\n");
        sql.append("       C.COUNT,\n");
        sql.append("       C.REMARKS\n");
        sql.append("  FROM (SELECT PART_ID,PART_CODE,PART_NAME,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,ISSUE_ID,ISSUE_NO,SUM(SHOULD_COUNT) SHOULD_COUNT,SUM(REAL_COUNT) REAL_COUNT FROM PT_BU_ISSUE_ORDER_DTL WHERE ORDER_ID = " + ORDER_ID + " GROUP BY PART_ID,PART_CODE,PART_NAME,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,ISSUE_ID,ISSUE_NO) A,\n");
        sql.append("       PT_BA_INFO B,\n");
        sql.append("       (SELECT * FROM PT_BU_BOX_UP WHERE ORDER_ID = " + ORDER_ID + ") C,\n");
        sql.append("       DIC_TREE D\n");
        sql.append("  WHERE A.PART_ID = B.PART_ID\n");
        sql.append("    AND A.PART_ID = C.PART_ID(+)\n");
        sql.append("    AND B.UNIT = D.ID\n");
        sql.append("  ORDER BY C.BOX_NO ASC) T");
        return factory.select(null, sql.toString());
    }
    
    public QuerySet getOrgName(String ORDER_ID) throws Exception {

        //定义返回结果集
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ORG_NAME FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "+ORDER_ID+"");
        return factory.select(null, sql.toString());
    }

    /**
     * 查询临时表数据
     *
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet checkList1(User user) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT * FROM PT_BU_BOX_UP_TMP A WHERE A.USER_ACCOUNT = '" + user.getAccount() + "'\n");
        return factory.select(null, sql.toString());
    }

    /**
     * @param user
     * @param ORDER_ID 销售订单ID
     * @return
     * @throws Exception
     */
    public QuerySet checkList2(User user, String ORDER_ID) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT A.ROW_NO\n");
        sql.append("  FROM PT_BU_BOX_UP_TMP A\n");
        sql.append(" WHERE A.USER_ACCOUNT = '" + user.getAccount() + "'\n");
        sql.append("  AND NOT EXISTS (SELECT 1\n");
        sql.append("          FROM PT_BU_ISSUE_ORDER_DTL B\n");
        sql.append("         WHERE B.ORDER_ID = " + ORDER_ID + "\n");
        sql.append("           AND B.ISSUE_NO = A.ISSUE_NO\n");
        sql.append("           AND B.PART_CODE = A.PART_CODE)\n");

        return factory.select(null, sql.toString());
    }

    /**
     * 导入验证成功查询
     *
     * @param page
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet searchImport(PageManager page, User user,String conditions) throws Exception {
    	String wheres = conditions + " AND USER_ACCOUNT='"+user.getAccount()+"' \n";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT A.* \n");
        sql.append("  FROM PT_BU_BOX_UP_TMP A");
        bs = factory.select(sql.toString(), page);
        return bs;
    }

    /**
     * 根据临时表插入正式表
     *
     * @param user
     * @param orderId 销售订单ID
     * @throws Exception
     */
    public void insertBoxUpFromTmp(User user, String orderId,String tmpNo) throws Exception {

        StringBuilder sql = new StringBuilder();
        sql.append("MERGE INTO PT_BU_BOX_UP A\n");
        sql.append("USING (SELECT C.ORDER_ID,\n");
        sql.append("              D.ORDER_NO,\n");
        sql.append("              C.ISSUE_ID,\n");
        sql.append("              C.ISSUE_NO,\n");
        sql.append("              C.PART_ID,\n");
        sql.append("              C.PART_CODE,\n");
        sql.append("              C.PART_NAME,\n");
        sql.append("              B.BOX_NO,\n");
        sql.append("              B.COUNT,\n");
        sql.append("              B.REMARKS\n");
        sql.append("         FROM PT_BU_BOX_UP_TMP B, PT_BU_ISSUE_ORDER_DTL C, PT_BU_SALE_ORDER D\n");
        sql.append("        WHERE B.ISSUE_NO = C.ISSUE_NO\n");
        sql.append("          AND B.PART_CODE = C.PART_CODE\n");
        sql.append("          AND B.USER_ACCOUNT = '" + user.getAccount() + "'\n");
        sql.append("          AND C.ORDER_ID = " + orderId + "\n");
        sql.append("          AND C.ORDER_ID = D.ORDER_ID AND B.TMP_NO NOT IN ("+tmpNo+")) E\n");
        sql.append("ON (A.PART_ID = E.PART_ID AND A.ISSUE_ID = E.ISSUE_ID)\n");
        sql.append("WHEN MATCHED THEN\n");
        sql.append("  UPDATE\n");
        sql.append("     SET A.BOX_NO      = E.BOX_NO,\n");
        sql.append("         A.COUNT       = E.COUNT,\n");
        sql.append("         A.REMARKS     = E.REMARKS,\n");
        sql.append("         A.UPDATE_USER = '" + user.getAccount() + "',\n");
        sql.append("         A.UPDATE_TIME = SYSDATE\n");
        sql.append("WHEN NOT MATCHED THEN\n");
        sql.append("  INSERT\n");
        sql.append("    (UP_ID,\n");
        sql.append("     ORDER_ID,\n");
        sql.append("     ORDER_NO,\n");
        sql.append("     ISSUE_ID,\n");
        sql.append("     ISSUE_NO,\n");
        sql.append("     PART_ID,\n");
        sql.append("     PART_CODE,\n");
        sql.append("     PART_NAME,\n");
        sql.append("     BOX_NO,\n");
        sql.append("     COUNT,\n");
        sql.append("     REMARKS,\n");
        sql.append("     ORG_ID,\n");
        sql.append("     COMPANY_ID,\n");
        sql.append("     OEM_COMPANY_ID,\n");
        sql.append("     STATUS,\n");
        sql.append("     CREATE_USER,\n");
        sql.append("     CREATE_TIME)\n");
        sql.append("  VALUES\n");
        sql.append("    (F_GETID(),\n");
        sql.append("     E.ORDER_ID,\n");
        sql.append("     E.ORDER_NO,\n");
        sql.append("     E.ISSUE_ID,\n");
        sql.append("     E.ISSUE_NO,\n");
        sql.append("     E.PART_ID,\n");
        sql.append("     E.PART_CODE,\n");
        sql.append("     E.PART_NAME,\n");
        sql.append("     E.BOX_NO,\n");
        sql.append("     E.COUNT,\n");
        sql.append("     E.REMARKS,\n");
        sql.append("     " + user.getOrgId() + ",\n");
        sql.append("     " + user.getCompanyId() + ",\n");
        sql.append("     " + user.getOemCompanyId() + ",\n");
        sql.append("     " + DicConstant.YXBS_01 + ",\n");
        sql.append("     '" + user.getAccount() + "',\n");
        sql.append("     SYSDATE)\n");

        factory.update(sql.toString(), null);
    }
    public QuerySet checkExists(String partId,String issueId,String orderId) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT T.UP_ID FROM PT_BU_BOX_UP T\n" );
        sql.append("WHERE T.ORDER_ID = "+orderId+"\n" );
        sql.append("AND T.ISSUE_ID = "+issueId+"\n" );
        sql.append("AND T.PART_ID = "+partId+"\n" );
        return factory.select(null, sql.toString());
    }
    public boolean deleteBox(String partId,String issueId,String orderId) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("DELETE FROM PT_BU_BOX_UP WHERE PART_ID = "+partId+" AND ISSUE_ID = "+issueId+" AND ORDER_ID = "+orderId+"");
    	return factory.update(sql.toString(), null);
    }
    
    public boolean deleteBoxImpot(String orderId) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("DELETE FROM PT_BU_BOX_UP WHERE  ORDER_ID = "+orderId+"");
    	return factory.update(sql.toString(), null);
    }
    
    
    public QuerySet exportData(String ORDER_ID,String tmpNo) throws Exception {

        //定义返回结果集
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT T.*,ROWNUM FROM(\n");
        sql.append("SELECT A.PART_ID,\n");
        sql.append("       A.PART_CODE,\n");
        sql.append("       A.PART_NAME,\n");
        sql.append("       D.DIC_VALUE UNIT,\n");
        sql.append("       B.MIN_PACK,\n");
        sql.append("       A.SUPPLIER_ID,\n");
        sql.append("       A.SUPPLIER_CODE,\n");
        sql.append("       DECODE(A.SUPPLIER_CODE,'9XXXXXX','',A.SUPPLIER_NAME) SUPPLIER_NAME,\n");
        sql.append("       A.ISSUE_ID,\n");
        sql.append("       A.ISSUE_NO,\n");
        sql.append("       A.SHOULD_COUNT,\n");
        sql.append("       A.REAL_COUNT,\n");
        sql.append("       C.BOX_NO,\n");
        sql.append("       C.COUNT,\n");
        sql.append("       C.REMARKS\n");
        sql.append("  FROM (SELECT PART_ID,PART_CODE,PART_NAME,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,ISSUE_ID,ISSUE_NO,SUM(SHOULD_COUNT) SHOULD_COUNT,SUM(REAL_COUNT) REAL_COUNT FROM PT_BU_ISSUE_ORDER_DTL WHERE ORDER_ID = " + ORDER_ID + " GROUP BY PART_ID,PART_CODE,PART_NAME,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,ISSUE_ID,ISSUE_NO) A,\n");
        sql.append("       PT_BA_INFO B,\n");
        sql.append("       (SELECT * FROM PT_BU_BOX_UP WHERE ORDER_ID = " + ORDER_ID + ") C,\n");
        sql.append("       DIC_TREE D\n");
        sql.append("  WHERE A.PART_ID = B.PART_ID\n");
        sql.append("    AND A.PART_ID = C.PART_ID(+)\n");
        sql.append("    AND B.UNIT = D.ID\n");
        sql.append("    AND B.PART_CODE IN(SELECT PART_CODE FROM PT_BU_BOX_UP_TMP WHERE TMP_NO IN  ("+tmpNo+") )\n");
        sql.append("  ORDER BY C.BOX_NO ASC) T");
        return factory.select(null, sql.toString());
    }
    
    
    public QuerySet getTmp(String tmpNo,User user) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T3.ORDER_ID,\n" );
    	sql.append("       T3.ORDER_NO,\n" );
    	sql.append("       T2.ISSUE_ID,\n" );
    	sql.append("       T2.ISSUE_NO,\n" );
    	sql.append("       T1.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T.COUNT,\n" );
    	sql.append("       T.BOX_NO,\n" );
    	sql.append("       T.REMARKS\n" );
    	sql.append("  FROM PT_BU_BOX_UP_TMP      T,\n" );
    	sql.append("       PT_BU_ISSUE_ORDER_DTL T1,\n" );
    	sql.append("       PT_BU_ISSUE_ORDER     T2,\n" );
    	sql.append("       PT_BU_SALE_ORDER      T3\n" );
    	sql.append(" WHERE T.PART_CODE = T1.PART_CODE\n" );
    	sql.append("   AND T.ISSUE_NO = T2.ISSUE_NO\n" );
    	sql.append("   AND T1.ISSUE_ID = T2.ISSUE_ID\n" );
    	sql.append("   AND T2.ORDER_ID = T3.ORDER_ID\n" );
    	if(!"".equals(tmpNo)&&tmpNo!=null){
    		sql.append("   AND T.TMP_NO NOT IN ("+tmpNo+")\n" );
    	}
    	sql.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'");
        return factory.select(null, sql.toString());
    }
    
    public QuerySet getDevCount(String ORDER_ID) throws Exception {

        //定义返回结果集
        StringBuilder sql = new StringBuilder();
        return factory.select(null, sql.toString());
    }
    
    public QuerySet check(String orderId) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT 1\n" );
    	sql.append("  FROM (SELECT T.REAL_COUNT, SUM(T1.COUNT) BOX_COUNT, T.PART_ID\n" );
    	sql.append("          FROM PT_BU_ISSUE_ORDER_DTL T, PT_BU_BOX_UP T1\n" );
    	sql.append("         WHERE T.ORDER_ID = T1.ORDER_ID\n" );
    	sql.append("           AND T.PART_ID = T1.PART_ID\n" );
    	sql.append("           AND T.ORDER_ID = "+orderId+"\n" );
    	sql.append("         GROUP BY T.REAL_COUNT, T.PART_ID) A\n" );
    	sql.append(" WHERE A.REAL_COUNT != A.BOX_COUNT");
        return factory.select(null, sql.toString());
    }
    
}