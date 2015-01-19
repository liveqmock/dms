package com.org.dms.dao.part.salesMng.orderMng;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.Calendar;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.vo.part.PtBuSaleOrderCheckVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * @Description:订单审核Dao
 * @Date: 2014-08-06
 * @Author gouwentan
 */
public class PartOrderCheckDao extends BaseDAO{
    public static final PartOrderCheckDao getInstance(ActionContext atx) {
        PartOrderCheckDao dao = new PartOrderCheckDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    /**
     * 是否是军品订单
     */
    public QuerySet isAmCheck(User user) throws Exception {

        StringBuilder sql= new StringBuilder();
        sql.append("SELECT 1\n" );
        sql.append("  FROM TM_ORG\n" );
        sql.append(" WHERE ORG_ID ='" + user.getOrgId() + "'\n" );
        sql.append("   AND IS_AM = '" + DicConstant.SF_01 + "'");
    return factory.select(null, sql.toString());
    }
    /**
     * 订单查询
     */
    public BaseResultSet partOrderCheckSearch(PageManager page, User user, String conditions,boolean flag) throws Exception
    {
        String wheres = conditions;
        if (flag) {
            // ------ 军品订单审核
            wheres+=" AND T.ORDER_TYPE='"+DicConstant.DDLX_08+"'";
        } else {
            // ------ 非军品订单审核
        	wheres+=" AND T.ORDER_TYPE NOT IN ("+DicConstant.DDLX_08+","+DicConstant.DDLX_09+","+DicConstant.DDLX_10+")\n";
            wheres += " AND T.ORG_ID IN (SELECT ORG_ID FROM TM_ORG WHERE PID IN (SELECT ORG_ID FROM PT_BA_ORDER_CHECK WHERE USER_ACCOUNT='"+user.getAccount()+"'))";
        }
        wheres +="AND T.ORDER_STATUS = "+DicConstant.DDZT_02+" AND T.IF_CHANEL_ORDER ="+DicConstant.SF_02+"\n";
        wheres += " ORDER BY T.APPLY_DATE ASC\n";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.ORDER_ID,subStr(T.ORDER_NO,-1) AS DELAY_COUNT,T.ORDER_NO,T.ORDER_TYPE,T.ORG_ID,T.ORG_CODE,T.ORG_NAME,T.WAREHOUSE_ID,\n");
    	sql.append("T.WAREHOUSE_CODE,T.WAREHOUSE_NAME,T.EXPECT_DATE,T.ORDER_AMOUNT,T.APPLY_DATE,T.CREATE_USER,\n");
    	sql.append("T.REMARKS,T.IF_DELAY_ORDER,T.PROM_ID,T.IF_CREDIT,T.IF_TRANS,T.PROVINCE_CODE,T.PROVINCE_NAME,T.CITY_CODE,T.CITY_NAME,T.COUNTRY_CODE,T.COUNTRY_NAME,T.CUSTORM_NAME,T.ARMY_CONT_NO,\n");
    	sql.append("T.TRANS_TYPE,T.ZIP_CODE,\n" );
//    	sql.append("CASE\n" );
//    	sql.append("WHEN T.COUNTRY_NAME IS NULL THEN\n" );
//    	sql.append("T.CITY_NAME || T.DELIVERY_ADDR\n" );
//    	sql.append("ELSE\n" );
//    	sql.append("T.COUNTRY_NAME || T.DELIVERY_ADDR\n" );
//    	sql.append("END AS DELIVERY_ADDR,\n" );
    	sql.append("T.DELIVERY_ADDR,\n" );
    	sql.append("T.LINK_MAN,T.PHONE,T.DIRECT_TYPE_NAME,T.SUPPLIER_NAME,T.VIN,T.PROM_NAME,\n");
    	sql.append("NVL(ROUND(((SELECT SUM(CASE\n" );
    	sql.append("                            WHEN T1.AVAILABLE_AMOUNT / DECODE(T3.ORDER_COUNT,'0','1',T3.ORDER_COUNT) > 1 THEN\n" );
    	sql.append("                             1\n" );
    	sql.append("                            ELSE\n" );
    	sql.append("                             T1.AVAILABLE_AMOUNT / DECODE(T3.ORDER_COUNT,'0','1',T3.ORDER_COUNT)\n" );
    	sql.append("                          END)\n" );
    	sql.append("                 FROM PT_BU_STOCK          T1,\n" );
    	sql.append("                      PT_BU_SALE_ORDER     T2,\n" );
    	sql.append("                      PT_BU_SALE_ORDER_DTL T3\n" );
    	sql.append("                WHERE T1.PART_ID = T3.PART_ID\n" );
    	sql.append("                  AND T2.WAREHOUSE_ID = T1.WAREHOUSE_ID\n" );
    	sql.append("                  AND T2.ORDER_ID = T3.ORDER_ID\n" );
    	sql.append("                  AND T3.ORDER_ID = T.ORDER_ID) /\n" );
    	sql.append("             (SELECT COUNT(1)\n" );
    	sql.append("                 FROM PT_BU_SALE_ORDER_DTL\n" );
    	sql.append("                WHERE ORDER_ID = T.ORDER_ID)) * 100,\n" );
    	sql.append("             2),0) AS RATE,\n" );
    	sql.append("       NVL(ROUND(((SELECT SUM(CASE\n" );
    	sql.append("                            WHEN NVL(T1.AVAILABLE_AMOUNT, 0) = '0' THEN\n" );
    	sql.append("                             0\n" );
    	sql.append("                            ELSE\n" );
    	sql.append("                             1\n" );
    	sql.append("                          END)\n" );
    	sql.append("                 FROM PT_BU_STOCK          T1,\n" );
    	sql.append("                      PT_BU_SALE_ORDER     T2,\n" );
    	sql.append("                      PT_BU_SALE_ORDER_DTL T3\n" );
    	sql.append("                WHERE T1.PART_ID = T3.PART_ID\n" );
    	sql.append("                  AND T2.WAREHOUSE_ID = T1.WAREHOUSE_ID\n" );
    	sql.append("                  AND T2.ORDER_ID = T3.ORDER_ID\n" );
    	sql.append("                  AND T3.ORDER_ID = T.ORDER_ID) /\n" );
    	sql.append("             (SELECT COUNT(1)\n" );
    	sql.append("                 FROM PT_BU_SALE_ORDER_DTL\n" );
    	sql.append("                WHERE ORDER_ID = T.ORDER_ID)) * 100,\n" );
    	sql.append("             2),0) AS RATE1");
    	sql.append(" FROM (SELECT A.ORDER_ID,\n" );
    	sql.append("       A.ORDER_NO,\n" );
    	sql.append("       A.ORDER_TYPE,\n" );
    	sql.append("       A.ORG_ID,\n" );
    	sql.append("       A.ORG_CODE,\n" );
    	sql.append("       A.ORG_NAME,\n" );
    	sql.append("       A.WAREHOUSE_ID,\n" );
    	sql.append("       A.WAREHOUSE_CODE,\n" );
    	sql.append("       A.WAREHOUSE_NAME,\n" );
    	sql.append("       A.EXPECT_DATE,\n" );
    	sql.append("       A.ORDER_AMOUNT,\n" );
    	sql.append("       A.APPLY_DATE,\n" );
    	sql.append("       A.CREATE_USER,\n" );
    	sql.append("       A.REMARKS,\n" );
    	sql.append("       A.IF_DELAY_ORDER,\n" );
    	sql.append("       A.IF_CHANEL_ORDER,\n" );
    	sql.append("       A.PROM_ID,\n" );
    	sql.append("       A.IF_CREDIT,\n" );
    	sql.append("       A.IF_TRANS,\n" );
    	sql.append("       A.PROVINCE_CODE,\n" );
    	sql.append("       A.PROVINCE_NAME,\n" );
    	sql.append("       A.CITY_CODE,\n" );
    	sql.append("       A.CITY_NAME,\n" );
    	sql.append("       A.COUNTRY_CODE,\n" );
    	sql.append("       A.COUNTRY_NAME,\n" );
    	sql.append("       A.TRANS_TYPE,\n" );
    	sql.append("       A.ZIP_CODE,\n" );
    	sql.append("       A.DELIVERY_ADDR,\n" );
    	sql.append("       A.LINK_MAN,\n" );
    	sql.append("       A.PHONE,\n" );
    	sql.append("       A.ORDER_STATUS,\n" );
    	sql.append("       A.DIRECT_TYPE_NAME,\n" );
    	sql.append("       A.SUPPLIER_NAME,\n" );
    	sql.append("       A.VIN,\n" );
    	sql.append("       A.ARMY_CONT_NO,\n" );
    	sql.append("       A.CUSTORM_NAME,\n" );
    	sql.append("       B.PROM_NAME\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER A LEFT JOIN PT_BU_PROMOTION B ON A.PROM_ID = B.PROM_ID)T\n" );

        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("ORDER_TYPE", "DDLX");
        bs.setFieldDic("ORDER_STATUS", "DDZT");
        bs.setFieldDic("TRANS_TYPE", "FYFS");
        bs.setFieldDic("IF_CREDIT", "SF");
        bs.setFieldDic("IF_TRANS", "SF");
        bs.setFieldDateFormat("EXPECT_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
        bs.setFieldDic("IF_DELAY_ORDER", DicConstant.SF);
        // 提报人
        bs.setFieldUserID("CREATE_USER");
        return bs;
    }
    /**
     * 资金账户可用余额查询
     */
    public BaseResultSet accountSearch(PageManager page, String orgId, String conditions) throws Exception
    {
        String wheres = conditions;
        wheres += " AND T.ACCOUNT_TYPE IN ("+DicConstant.ZJZHLX_01+", "+DicConstant.ZJZHLX_02+", "+DicConstant.ZJZHLX_03+")\n";
        wheres += " AND ORG_ID = "+orgId+"AND T.STATUS ="+DicConstant.YXBS_01+"\n";
        wheres += " UNION SELECT 2 TYPE, NVL(SUM(AVAILABLE_AMOUNT),0) AVAILABLE_AMOUNT FROM PT_BU_ACCOUNT T\n";
        wheres += " WHERE T.ACCOUNT_TYPE = "+DicConstant.ZJZHLX_04+" AND ORG_ID = "+orgId+"AND T.STATUS ="+DicConstant.YXBS_01+"\n";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT 1 TYPE, NVL(SUM(T.AVAILABLE_AMOUNT),0) AVAILABLE_AMOUNT\n" );
        sql.append("  FROM PT_BU_ACCOUNT T\n" );
        bs = factory.select(sql.toString(), page);
        return bs;
    }
    /**
     * 订单资金使用查询
     */
    public BaseResultSet orderFundsListSearch(PageManager page, User user, String conditions,String orderId) throws Exception
    {
        String wheres = conditions;
        wheres += " AND A.ACCOUNT_ID = B.ACCOUNT_ID AND B.ACCOUNT_TYPE= C.ID AND C.DIC_NAME_CODE='"+DicConstant.ZJZHLX+"' AND A.ORDER_ID = "+orderId+"\n";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.PAY_ID,A.ACCOUNT_ID, C.DIC_VALUE ACCOUNT_TYPE, B.AVAILABLE_AMOUNT, A.PAY_AMOUNT\n" );
        sql.append("  FROM PT_BU_SALE_ORDER_PAY A, PT_BU_ACCOUNT B,DIC_TREE C\n" );
        bs = factory.select(sql.toString(), page);
        return bs;
    }
    /**
     * 订单审核记录查询
     */
    public BaseResultSet orderCheckListSearch(PageManager page, User user, String conditions,String orderId) throws Exception
    {
        String wheres = conditions;
        wheres += " AND A.CHECK_ORG = B.ORG_ID AND A.CHECK_RESULT = C.ID AND C.DIC_NAME_CODE ='"+DicConstant.DDZT+"' AND A.ORDER_ID="+orderId+" ORDER BY A.CHECK_DATE DESC\n";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.CHECK_DATE,\n" );
        sql.append("       C.DIC_VALUE  CHECK_RESULT,\n" );
        sql.append("       B.ONAME      CHECK_ORG,\n" );
        sql.append("       A.CHECK_USER,\n" );
        sql.append("       A.REMARKS\n" );
        sql.append("  FROM PT_BU_SALE_ORDER_CHECK A, TM_ORG B, DIC_TREE C\n" );
        bs = factory.select(sql.toString(), page);
        bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
        return bs;
    }
    
    /**
     * 订单订购配件列表查询
     */
    public BaseResultSet partOrderDetailSearch(PageManager page, User user, String conditions,String orderId) throws Exception
    {
        String wheres = conditions;
        wheres += " ORDER BY PART_CODE ASC\n";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.DTL_ID,\n" );
        sql.append("       T.PART_ID,\n" );
        sql.append("       T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T.UNIT,\n" );
        sql.append("       T.MIN_PACK,\n" );
        sql.append("       T.MIN_UNIT,\n" );
        sql.append("       T.UNIT_PRICE,\n" );
        sql.append("       T.IF_SUPPLIER,\n" );
        sql.append("       T.SUPPLIER_ID,\n" );
        sql.append("       T.SUPPLIER_CODE,\n" );
        sql.append("       T.SUPPLIER_NAME,\n" );
        sql.append("       T.ORDER_COUNT,\n" );
        sql.append("       T.REMARKS,\n" );
        sql.append("       T.WAREHOUSE_ID,\n" );
        sql.append("       T.AMOUNT,\n" );
        sql.append("       T.OCCUPY_AMOUNT,\n" );
        sql.append("       T.AVAILABLE_AMOUNT,\n" );
        sql.append("       T.USER_ACCOUNT\n" );
        sql.append("  FROM (SELECT D.DTL_ID,\n" );
        sql.append("               D.PART_ID,\n" );
        sql.append("               D.PART_CODE,\n" );
        sql.append("               D.PART_NAME,\n" );
        sql.append("               D.UNIT,\n" );
        sql.append("               D.MIN_PACK,\n" );
        sql.append("               D.MIN_UNIT,\n" );
        sql.append("               D.UNIT_PRICE,\n" );
        sql.append("               D.IF_SUPPLIER,\n" );
        sql.append("               D.SUPPLIER_ID,\n" );
        sql.append("               D.SUPPLIER_CODE,\n" );
        sql.append("               D.SUPPLIER_NAME,\n" );
        sql.append("               D.ORDER_COUNT,\n" );
        sql.append("               D.REMARKS,\n" );
        sql.append("               D.WAREHOUSE_ID,\n" );
        sql.append("               P.USER_ACCOUNT,\n" );
        sql.append("               NVL(E.AMOUNT, 0) AMOUNT,\n" );
        sql.append("               NVL(E.OCCUPY_AMOUNT, 0) OCCUPY_AMOUNT,\n" );
        sql.append("               NVL(E.AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT\n" );
        sql.append("          FROM (SELECT A.DTL_ID,\n" );
        sql.append("                       A.PART_ID,\n" );
        sql.append("                       A.PART_CODE,\n" );
        sql.append("                       A.PART_NAME,\n" );
        sql.append("                       C.UNIT,\n" );
        sql.append("                       C.MIN_PACK,\n" );
        sql.append("                       C.MIN_UNIT,\n" );
        sql.append("                       A.UNIT_PRICE,\n" );
        sql.append("                       A.IF_SUPPLIER,\n" );
        sql.append("                       A.SUPPLIER_ID,\n" );
        sql.append("                       A.SUPPLIER_CODE,\n" );
        sql.append("                       A.SUPPLIER_NAME,\n" );
        sql.append("                       A.ORDER_COUNT,\n" );
        sql.append("                       A.REMARKS,\n" );
        sql.append("                       B.WAREHOUSE_ID\n" );
        sql.append("                  FROM PT_BU_SALE_ORDER_DTL A,\n" );
        sql.append("                       PT_BU_SALE_ORDER     B,\n" );
        sql.append("                       PT_BA_INFO           C\n" );
        sql.append("                 WHERE 1 = 1\n" );
        sql.append("                   AND A.ORDER_ID = B.ORDER_ID\n" );
        sql.append("                   AND A.PART_ID = C.PART_ID\n" );
        sql.append("                   AND A.ORDER_ID = "+orderId+") D\n" );
        sql.append("		  LEFT JOIN PT_BA_WAREHOUSE_KEEPER P\n" );
        sql.append("            ON D.PART_ID = P.PART_ID\n" );
        sql.append("           AND D.WAREHOUSE_ID = P.WAREHOUSE_ID\n");
        sql.append("          LEFT JOIN PT_BU_STOCK E\n" );
        sql.append("            ON D.PART_ID = E.PART_ID\n" );
        sql.append("           AND D.SUPPLIER_ID = E.SUPPLIER_ID\n" );
        sql.append("           AND E.STOCK_STATUS='"+DicConstant.KCZT_01+"'\n" );
        sql.append("           AND D.WAREHOUSE_ID = E.WAREHOUSE_ID) T");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", "JLDW");
        bs.setFieldDic("MIN_UNIT", "JLDW");
        bs.setFieldDic("IF_SUPPLIER", "SF");
        bs.setFieldUserID("USER_ACCOUNT");
        return bs;
    }
    
    /**
     * 订单审核驳回
     */
    public boolean partOrderCheckApproveBack(PtBuSaleOrderVO vo)
            throws Exception {
        return factory.update(vo);
    }
    /**
     * 订单审核驳回
     */
    public boolean partOrderCheckPass(PtBuSaleOrderVO vo)
            throws Exception {
        return factory.update(vo);
    }
    /**
     * 订单审核日志
     */
    public boolean partOrderCheckLogInsert(PtBuSaleOrderCheckVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 订单审核验证(是否存在三包急件订单(状态：提报)的配件)
     *
     * @param orderId 订单主键
     * @return QuerySet 结果集
     * @throws Exception
     */
    public QuerySet orderCheck(String orderId) throws Exception {
    	
    	
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT PART_CODE\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_DTL\n" );
    	sql.append(" WHERE ORDER_ID = "+orderId+"\n" );
    	sql.append("   AND PART_ID || nvl(SUPPLIER_ID, 0) IN\n" );
    	sql.append("       (SELECT A.PART_ID || nvl(A.SUPPLIER_ID, 0)\n" );
    	sql.append("          FROM PT_BU_SALE_ORDER_DTL A, PT_BU_SALE_ORDER B, pt_bu_stock c\n" );
    	sql.append("         WHERE A.ORDER_ID = B.ORDER_ID\n" );
    	sql.append("                   AND B.ORDER_TYPE = " + DicConstant.DDLX_09 + "\n" );
    	sql.append("                   AND B.ORDER_STATUS = " + DicConstant.DDZT_02 + "\n" );
    	sql.append("                   AND B.IF_DELAY_ORDER = " + DicConstant.SF_01 + "\n" );
    	sql.append("           AND b.dir_SOURCE_ORDER_ID IS NOT NULL\n" );
    	sql.append("           and a.part_id = c.part_id(+)\n" );
    	sql.append("           and c.warehouse_id =\n" );
    	sql.append("               (SELECT WAREHOUSE_ID\n" );
    	sql.append("                  FROM PT_BA_WAREHOUSE\n" );
    	sql.append("                 WHERE WAREHOUSE_TYPE = '100101')\n" );
    	sql.append("           and c.available_amount > 0)");

    	
//    	StringBuffer sql= new StringBuffer();
//    	sql.append("SELECT D.PART_CODE\n" );
//    	sql.append("  FROM PT_BU_SALE_ORDER_DTL D\n" );
//    	sql.append(" WHERE EXISTS (SELECT 1\n" );
//    	sql.append("          FROM PT_BU_SALE_ORDER_DTL A\n" );
//    	sql.append("         WHERE EXISTS (SELECT 1\n" );
//    	sql.append("                  FROM PT_BU_SALE_ORDER B\n" );
//    	sql.append("                 WHERE A.ORDER_ID = B.ORDER_ID\n" );
//    	sql.append("                   AND B.DIR_SOURCE_ORDER_ID IS NULL\n" );
//    	sql.append("                   AND B.ORDER_TYPE = " + DicConstant.DDLX_09 + "\n" );
//    	sql.append("                   AND B.ORDER_STATUS = " + DicConstant.DDZT_02 + "\n" );
//    	
////    	sql.append("                   AND B.IF_CHANEL_ORDER = " + DicConstant.SF_02 + "\n" );
//    	
//    	sql.append("                   AND B.IF_DELAY_ORDER = " + DicConstant.SF_01 + ")\n" );
//    	sql.append("           AND EXISTS (SELECT PART_ID FROM PT_BU_STOCK C WHERE AVAILABLE_AMOUNT > 0 " +
//    			"			AND C.PART_ID = A.PART_ID AND C.SUPPLIER_ID = A.SUPPLIER_ID " +
//    			"			AND C.WAREHOUSE_ID = (SELECT WAREHOUSE_ID FROM PT_BA_WAREHOUSE " +
//    			"			WHERE WAREHOUSE_TYPE='100101'))\n" );
//    	sql.append("           AND D.PART_ID = A.PART_ID\n" );
//    	sql.append("           AND D.SUPPLIER_ID = A.SUPPLIER_ID)\n" );
//    	sql.append("   AND ORDER_ID = "+orderId+"\n");
//        StringBuilder sql= new StringBuilder();
//        sql.append(" SELECT 1\n" );
//        sql.append("     FROM PT_BU_SALE_ORDER_DTL A, PT_BU_SALE_ORDER B\n" );
//        sql.append(" WHERE A.ORDER_ID = B.ORDER_ID AND B.DIR_SOURCE_ORDER_ID IS NULL\n" );
//        sql.append("     AND B.ORDER_TYPE = " + DicConstant.DDLX_09 + "\n" );
//        sql.append("     AND B.ORDER_STATUS = " + DicConstant.DDZT_02 + "\n" );
//        sql.append("     AND B.IF_DELAY_ORDER = " + DicConstant.SF_01 + "\n" );
        return factory.select(null, sql.toString());
    }
    
    /**
     * 查询原单
     */
    public QuerySet sourceOrderId(String orderId) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT NVL(DIR_SOURCE_ORDER_ID,0) DIR_SOURCE_ORDER_ID,NVL(DIR_SOURCE_ORDER_NO,0) DIR_SOURCE_ORDER_NO FROM PT_BU_SALE_ORDER WHERE ORDER_ID= '" + orderId + "'");
        return factory.select(null, sql.toString());
    }

    /**
     * 删除直营店订单和订单明细
     */
    public void deleteOrder(String orderId) throws Exception {

    	StringBuilder sql= new StringBuilder();
    	sql.append("DELETE FROM PT_BU_SALE_ORDER WHERE ORDER_ID='" + orderId + "'");
        factory.exec(sql.toString());

        StringBuilder sql1= new StringBuilder();
    	sql1.append("DELETE FROM PT_BU_SALE_ORDER_DTL WHERE ORDER_ID='" + orderId + "'");
        factory.exec(sql1.toString());
    }

    /**
     * 直发订单审核，存在供货关系中的配件。
     */
    public QuerySet partSupplierCheck(String dtlId) throws Exception {
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT PART_CODE\n" );
    	sql.append("  FROM PT_BU_SALE_ORDER_DTL B\n" );
    	sql.append(" WHERE B.DTL_ID IN ("+dtlId+")\n" );
    	sql.append("   AND B.PART_ID NOT IN\n" );
    	sql.append("       (SELECT PART_ID\n" );
    	sql.append("          FROM PT_BA_PART_SUPPLIER_RL\n" );
    	sql.append("         WHERE PART_ID IN\n" );
    	sql.append("               (SELECT PART_ID FROM PT_BU_SALE_ORDER_DTL WHERE DTL_ID IN ("+dtlId+")))");
        return factory.select(null, sql.toString());
    }

    /**
     * 订单审核明细更新审核数量
     */
    public boolean orderAuditCountUpdate(String auditCount,String dtlId,String orderId,User user,String orderType)
            throws Exception {
        StringBuffer sql = new StringBuffer();
            sql.append("UPDATE PT_BU_SALE_ORDER_DTL SET AUDIT_COUNT ="+auditCount+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE DTL_ID="+dtlId+" AND ORDER_ID ="+orderId+"\n");
        return factory.update(sql.toString(), null);
    }

    /**
     * 订单审核明细更新审核数量(服务站)
     */
    public boolean orderAuditCountUpdateServer(String auditCount,String dtlId,String orderId,User user) throws Exception {

        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE PT_BU_SALE_ORDER_DTL SET AUDIT_COUNT ="+auditCount+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE PART_ID=(SELECT PART_ID FROM PT_BU_SALE_ORDER_DTL WHERE DTL_ID="+dtlId+") AND ORDER_ID ="+orderId+"\n");
        return factory.update(sql.toString(), null);
    }
    /**
     * 资金占用释放
     */
    public void orderReleaseFreez(String orderId,User user)
            throws Exception {
        QuerySet qs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT A.OFUNDS_ID,A.ACCOUNT_ID,A.ACCOUNT_TYPE,A.OCCUPY_FUNDS FROM PT_BU_SALE_ORDER_OCCUPY_FUNDS A \n" );
        sql.append("  WHERE STATUS = "+DicConstant.YXBS_01+"\n" );
        sql.append("   AND A.ORDER_ID = "+orderId+"\n");
        qs = factory.select(null, sql.toString());
        if(qs.getRowCount()>0){
            for(int i=0;i<qs.getRowCount();i++){
                String ofundsId = qs.getString(i+1, "OFUNDS_ID");
                String accountId = qs.getString(i+1, "ACCOUNT_ID");
                String accountType = qs.getString(i+1, "ACCOUNT_TYPE");
                String occupyFunds = qs.getString(i+1, "OCCUPY_FUNDS");
                StringBuffer sql1 = new StringBuffer();
                sql1.append("UPDATE PT_BU_SALE_ORDER_OCCUPY_FUNDS SET STATUS ="+DicConstant.YXBS_02+",UPDATE_USER='"+user.getAccount()+"',\n");
                sql1.append("UPDATE_TIME=SYSDATE WHERE OFUNDS_ID = "+ofundsId+"\n");
                factory.update(sql1.toString(), null);
                StringBuffer sql2 = new StringBuffer();
                sql2.append("UPDATE PT_BU_ACCOUNT SET OCCUPY_AMOUNT =OCCUPY_AMOUNT -"+occupyFunds+",AVAILABLE_AMOUNT= AVAILABLE_AMOUNT+"+occupyFunds+",\n");
                sql2.append(" UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE ACCOUNT_ID="+accountId+" AND ACCOUNT_TYPE="+accountType+"\n");
                factory.update(sql2.toString(), null);
            }
        }
    }
    /**
     * 资金库存占用
     */
    public synchronized void orderFreez(String orderId,String flag)
            throws Exception {
        CallableStatement proc1 = null;
        CallableStatement proc2 = null;
        Object[] objs = new Object[1];
        objs[0] = orderId;
        int[] types = new int[1];
        types[0] = Types.VARCHAR;
        //资金库存同时冻结
        if("1".equals(flag)){
            proc1 = factory.getConnection().prepareCall("{call P_PART_FREEZ_FUNDS(?,?)}");
            proc1.setString(1, orderId);
            proc1.registerOutParameter(2, Types.VARCHAR);
            proc1.execute();
            proc2 = factory.getConnection().prepareCall("{call P_PART_FREEZ_STORE(?,?)}");
            proc2.setString(1, orderId);
            proc2.registerOutParameter(2, Types.VARCHAR);
            proc2.execute();
            if(!"".equals(proc1.getString(2))&&proc1.getString(2)!=null){
                throw new Exception(proc1.getString(2));
            }
            if(!"".equals(proc2.getString(2))&&proc2.getString(2)!=null){
                throw new Exception(proc2.getString(2));
            }
        }
        //只冻结资金
        if("2".equals(flag)){
            proc1 = factory.getConnection().prepareCall("{call P_PART_FREEZ_FUNDS(?,?)}");
            proc1.setString(1, orderId);
            proc1.registerOutParameter(2, Types.VARCHAR);
            proc1.execute();
            if(!"".equals(proc1.getString(2))&&proc1.getString(2)!=null){
                throw new Exception(proc1.getString(2));
            }
        }
        //只冻结库存
        if("3".equals(flag)){
            proc2 = factory.getConnection().prepareCall("{call P_PART_FREEZ_STORE(?,?)}");
            proc2.setString(1, orderId);
            proc2.registerOutParameter(2, Types.VARCHAR);
            proc2.execute();
            if(!"".equals(proc2.getString(2))&&proc2.getString(2)!=null){
                throw new Exception(proc2.getString(2));
            }
        }
    }
    
    /**
     * 延期订单生成
     */
    public void delayOrderInfoInsert(String orderId,User user)
            throws Exception {
        String orderNo = PartOddNumberUtil.getDelaySaleOrderNo(factory, orderId);
        String delayOrderId = SequenceUtil.getCommonSerivalNumber(factory);
        StringBuffer sql= new StringBuffer();
        sql.append("INSERT INTO PT_BU_SALE_ORDER (ORDER_ID,ORDER_NO,ORDER_TYPE,ORDER_STATUS,ORG_CODE,\n" );
        sql.append("   ORG_NAME,WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME,EXPECT_DATE,IF_CREDIT,\n" );
        sql.append("   IF_TRANS,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,\n" );
        sql.append("   ORG_ID,CREATE_USER,CREATE_TIME,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,IF_DELAY_ORDER,\n" );
        sql.append("   SOURCE_ORDER_ID,SOURCE_ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE,DIRECT_TYPE_ID,DIRECT_TYPE_CODE,DIRECT_TYPE_NAME,VIN,PLAN_PRODUCE_NO,BELONG_ASSEMBLY,ARMY_CONT_NO,CUSTORM_NAME)\n" );
        sql.append("  SELECT "+delayOrderId+",'"+orderNo+"',ORDER_TYPE,"+DicConstant.DDZT_02+",ORG_CODE,\n" );
        sql.append("   ORG_NAME,WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME,EXPECT_DATE,IF_CREDIT,\n" );
        sql.append("   IF_TRANS,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,\n" );
        sql.append("   ORG_ID,'"+user.getAccount()+"',SYSDATE,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,"+DicConstant.SF_01+",\n" );
        sql.append("   ORDER_ID,ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE,DIRECT_TYPE_ID,DIRECT_TYPE_CODE,DIRECT_TYPE_NAME,VIN,PLAN_PRODUCE_NO,BELONG_ASSEMBLY,ARMY_CONT_NO,CUSTORM_NAME\n" );
        sql.append("    FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "+orderId+"\n" );
        //插入延期订单主信息
        factory.update(sql.toString(), null);
        
        StringBuffer sql1= new StringBuffer();
        sql1.append("INSERT INTO PT_BU_SALE_ORDER_DTL(DTL_ID,ORDER_ID,PART_ID,PART_CODE,PART_NAME,PART_NO,ORDER_COUNT,\n" );
        sql1.append("CREATE_USER,CREATE_TIME,IF_SUPPLIER,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,UNIT_PRICE,AMOUNT,PLAN_PRICE,REMARKS)\n" );
        sql1.append("SELECT F_GETID(),"+delayOrderId+",PART_ID,PART_CODE,PART_NAME,PART_NO,NVL(ORDER_COUNT,0)-NVL(AUDIT_COUNT,0),'"+user.getAccount()+"',\n" );
        sql1.append("SYSDATE,IF_SUPPLIER,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,UNIT_PRICE,\n" );
        sql1.append("(NVL(ORDER_COUNT,0)-NVL(AUDIT_COUNT,0))*UNIT_PRICE,PLAN_PRICE,REMARKS FROM PT_BU_SALE_ORDER_DTL\n" );
        sql1.append("WHERE ORDER_ID ="+orderId+" AND NVL(ORDER_COUNT,0)-NVL(AUDIT_COUNT,0)>0");
        //插入延期订单明细信息
        factory.update(sql1.toString(), null);
        StringBuffer sql2= new StringBuffer();
        sql2.append("INSERT INTO PT_BU_SALE_ORDER_PAY(PAY_ID,ORDER_ID,ACCOUNT_ID,PAY_AMOUNT,CREATE_USER,CREATE_TIME,ACCOUNT_TYPE)\n" );
        sql2.append("SELECT F_GETID(),"+delayOrderId+",ACCOUNT_ID,PAY_AMOUNT,'"+user.getAccount()+"',SYSDATE,ACCOUNT_TYPE FROM\n" );
        sql2.append(" (SELECT T.ACCOUNT_ID,NVL(T.PAY_AMOUNT,0)-NVL(B.OCCUPY_FUNDS,0) PAY_AMOUNT,T.ACCOUNT_TYPE\n" );
        sql2.append("FROM (SELECT A.ORDER_ID,A.ACCOUNT_ID,A.ACCOUNT_TYPE,A.PAY_AMOUNT FROM PT_BU_SALE_ORDER_PAY A\n" );
        sql2.append(" WHERE ORDER_ID ="+orderId+")T LEFT JOIN PT_BU_SALE_ORDER_OCCUPY_FUNDS B\n");
        sql2.append(" ON T.ORDER_ID = B.ORDER_ID AND T.ACCOUNT_ID = B.ACCOUNT_ID  AND T.ACCOUNT_TYPE =B.ACCOUNT_TYPE\n");
        sql2.append(" AND B.STATUS ="+DicConstant.YXBS_01+" ) T WHERE PAY_AMOUNT>0\n");
        //插入延期订单付款信息
        factory.update(sql2.toString(), null);
        
        QuerySet qs1 = null;
        StringBuffer sql6= new StringBuffer();
        sql6.append("SELECT SUM(NVL(ORDER_COUNT,0)* NVL(UNIT_PRICE,0)) ORDER_AMOUNT\n" );
        sql6.append("  FROM PT_BU_SALE_ORDER_DTL A\n" );
        sql6.append(" WHERE A.ORDER_ID = "+delayOrderId+"\n");
        //查询延期订单付款信息
        qs1 = factory.select(null, sql6.toString());
        if(qs1.getRowCount()>0){
            String orderAmount = qs1.getString(1, "ORDER_AMOUNT");
            StringBuffer sql7 = new StringBuffer();
            sql7.append("UPDATE PT_BU_SALE_ORDER SET ORDER_AMOUNT="+orderAmount+" WHERE ORDER_ID = "+delayOrderId+"\n");
            //更新延期订单总金额
            factory.update(sql7.toString(), null);
        }
        QuerySet qs = null;
        StringBuffer sql3= new StringBuffer();
        sql3.append("SELECT A.ACCOUNT_ID,A.ACCOUNT_TYPE,A.PAY_AMOUNT, B.AVAILABLE_AMOUNT\n" );
        sql3.append("  FROM PT_BU_SALE_ORDER_PAY A, PT_BU_ACCOUNT B\n" );
        sql3.append(" WHERE A.ACCOUNT_ID = B.ACCOUNT_ID\n" );
        sql3.append("   AND A.ORDER_ID = "+delayOrderId+"\n");
        //查询延期订单付款信息
        qs = factory.select(null, sql3.toString());
        if(qs.getRowCount()>0){
            for(int i=0;i<qs.getRowCount();i++){
                String accountId  = qs.getString(i+1, "ACCOUNT_ID");
                String payAmount  = qs.getString(i+1, "PAY_AMOUNT");
                String accountType  = qs.getString(i+1, "ACCOUNT_TYPE");
                //更新资金账户
                StringBuffer sql4= new StringBuffer();
                sql4.append("UPDATE PT_BU_ACCOUNT SET OCCUPY_AMOUNT = OCCUPY_AMOUNT+"+payAmount+" ,AVAILABLE_AMOUNT = AVAILABLE_AMOUNT-"+payAmount+" WHERE ACCOUNT_ID =+"+accountId+"\n");
                factory.update(sql4.toString(), null);
                //插入延期订单资金占用
                StringBuffer sql5= new StringBuffer();
                sql5.append("INSERT INTO PT_BU_SALE_ORDER_OCCUPY_FUNDS\n" );
                sql5.append("  (OFUNDS_ID,\n" );
                sql5.append("   ORDER_ID,\n" );
                sql5.append("   ACCOUNT_ID,\n" );
                sql5.append("   ACCOUNT_TYPE,\n" );
                sql5.append("   OCCUPY_FUNDS,\n" );
                sql5.append("   CREATE_USER,\n" );
                sql5.append("   CREATE_TIME,\n" );
                sql5.append("   STATUS)\n" );
                sql5.append("VALUES\n" );
                sql5.append("  (F_GETID(),"+delayOrderId+","+accountId+","+accountType+","+payAmount+",'"+user.getAccount()+"',SYSDATE,"+DicConstant.YXBS_01+")\n");
                factory.update(sql5.toString(), null);
            }
        }
    }

    /**
     * 延期订单生成(直营订单)
     */
    public void dbDelayOrderInfoInsert(String orderId,User user,String sourceOrderIdString) throws Exception {

    	// 服务站中心单号
        String sourceOrderNo = PartOddNumberUtil.getDelaySaleOrderNo(factory, sourceOrderIdString);
        // 服务站订单ID
        String delaySourceOrderId = SequenceUtil.getCommonSerivalNumber(factory);
        StringBuffer sql= new StringBuffer();
        sql.append("INSERT INTO PT_BU_SALE_ORDER (ORDER_ID,ORDER_NO,ORDER_TYPE,ORDER_STATUS,ORG_CODE,\n" );
        sql.append("   ORG_NAME,WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME,EXPECT_DATE,IF_CREDIT,\n" );
        sql.append("   IF_TRANS,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,\n" );
        sql.append("   ORG_ID,CREATE_USER,CREATE_TIME,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,IF_DELAY_ORDER,\n" );
        sql.append("   SOURCE_ORDER_ID,SOURCE_ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE\n" );
        sql.append("   ,SB_VIN,SB_ENGINE_NO,SB_MODELS_CODE,SB_BUY_DATE,SB_MILEAGE,SB_USER_NAME,SB_LINK_MAN,SB_PHONE,SB_USER_ADDRESS,SB_FAULT_DATE,SB_FAULT_ANALYSE_CODE,SB_FAULT_ANALYSE_NAME)\n" );
        sql.append("  SELECT "+delaySourceOrderId+",'"+sourceOrderNo+"',ORDER_TYPE,"+DicConstant.DDZT_02+",ORG_CODE,\n" );
        sql.append("   ORG_NAME,WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME,EXPECT_DATE,IF_CREDIT,\n" );
        sql.append("   IF_TRANS,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,\n" );
        sql.append("   ORG_ID,'"+user.getAccount()+"',SYSDATE,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,"+DicConstant.SF_01+",\n" );
        sql.append("   ORDER_ID,ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE\n" );
        sql.append("   ,SB_VIN,SB_ENGINE_NO,SB_MODELS_CODE,SB_BUY_DATE,SB_MILEAGE,SB_USER_NAME,SB_LINK_MAN,SB_PHONE,SB_USER_ADDRESS,SB_FAULT_DATE,SB_FAULT_ANALYSE_CODE,SB_FAULT_ANALYSE_NAME\n" );
        sql.append("    FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "+sourceOrderIdString+"\n" );
        //插入延期订单主信息
        factory.update(sql.toString(), null);

        StringBuffer sql1= new StringBuffer();
        sql1.append("INSERT INTO PT_BU_SALE_ORDER_DTL(DTL_ID,ORDER_ID,PART_ID,PART_CODE,PART_NAME,PART_NO,ORDER_COUNT,\n" );
        sql1.append("CREATE_USER,CREATE_TIME,IF_SUPPLIER,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,UNIT_PRICE,AMOUNT,PLAN_PRICE,REMARKS)\n" );
        sql1.append("SELECT F_GETID(),"+delaySourceOrderId+",PART_ID,PART_CODE,PART_NAME,PART_NO,ORDER_COUNT - AUDIT_COUNT,'"+user.getAccount()+"',\n" );
        sql1.append("SYSDATE,IF_SUPPLIER,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,UNIT_PRICE,\n" );
        sql1.append("(ORDER_COUNT - AUDIT_COUNT)*UNIT_PRICE,PLAN_PRICE,REMARKS FROM PT_BU_SALE_ORDER_DTL\n" );
        sql1.append("WHERE ORDER_ID ="+sourceOrderIdString+" AND ORDER_COUNT - AUDIT_COUNT>0");
        //插入延期订单明细信息
        factory.update(sql1.toString(), null);

        QuerySet qs1 = null;
        StringBuffer sql6= new StringBuffer();
        sql6.append("SELECT NVL(SUM(ORDER_COUNT * UNIT_PRICE),0) ORDER_AMOUNT\n" );
        sql6.append("  FROM PT_BU_SALE_ORDER_DTL A\n" );
        sql6.append(" WHERE A.ORDER_ID = "+delaySourceOrderId+"\n");
        // 查询延期订单详细金额汇总
        qs1 = factory.select(null, sql6.toString());

        if(qs1.getRowCount()>0){
            String orderAmount = qs1.getString(1, "ORDER_AMOUNT");
            StringBuffer sql7 = new StringBuffer();
            sql7.append("UPDATE PT_BU_SALE_ORDER SET ORDER_AMOUNT="+orderAmount+" WHERE ORDER_ID = "+delaySourceOrderId+"\n");
            //更新延期订单总金额
            factory.update(sql7.toString(), null);
        }
        
    	// 配送中心单号
        String orderNo = PartOddNumberUtil.getDelaySaleOrderNo(factory, orderId);
        String delayOrderId = SequenceUtil.getCommonSerivalNumber(factory);
        StringBuffer sql00= new StringBuffer();
        sql00.append("INSERT INTO PT_BU_SALE_ORDER (ORDER_ID,ORDER_NO,ORDER_TYPE,ORDER_STATUS,ORG_CODE,\n" );
        sql00.append("   ORG_NAME,WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME,EXPECT_DATE,IF_CREDIT,\n" );
        sql00.append("   IF_TRANS,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,\n" );
        sql00.append("   ORG_ID,CREATE_USER,CREATE_TIME,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,IF_DELAY_ORDER,\n" );
        sql00.append("   SOURCE_ORDER_ID,SOURCE_ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE,DIR_SOURCE_ORDER_ID,DIR_SOURCE_ORDER_NO\n" );
        sql00.append("   ,SB_VIN,SB_ENGINE_NO,SB_MODELS_CODE,SB_BUY_DATE,SB_MILEAGE,SB_USER_NAME,SB_LINK_MAN,SB_PHONE,SB_USER_ADDRESS,SB_FAULT_DATE,SB_FAULT_ANALYSE_CODE,SB_FAULT_ANALYSE_NAME)\n" );
        sql00.append("  SELECT "+delayOrderId+",'"+orderNo+"',ORDER_TYPE,"+DicConstant.DDZT_02+",ORG_CODE,\n" );
        sql00.append("   ORG_NAME,WAREHOUSE_ID,WAREHOUSE_CODE,WAREHOUSE_NAME,EXPECT_DATE,IF_CREDIT,\n" );
        sql00.append("   IF_TRANS,TRANS_TYPE,DELIVERY_ADDR,LINK_MAN,PHONE,ZIP_CODE,REMARKS,COMPANY_ID,\n" );
        sql00.append("   ORG_ID,'"+user.getAccount()+"',SYSDATE,STATUS,OEM_COMPANY_ID,IF_CHANEL_ORDER,"+DicConstant.SF_01+",\n" );
        sql00.append("   ORDER_ID,ORDER_NO,ADDR_TYPE,PROVINCE_CODE,PROVINCE_NAME,CITY_CODE,CITY_NAME,COUNTRY_CODE,COUNTRY_NAME,PROM_ID,APPLY_DATE,"+delaySourceOrderId+",'"+sourceOrderNo+"'\n" );
        sql00.append("   ,SB_VIN,SB_ENGINE_NO,SB_MODELS_CODE,SB_BUY_DATE,SB_MILEAGE,SB_USER_NAME,SB_LINK_MAN,SB_PHONE,SB_USER_ADDRESS,SB_FAULT_DATE,SB_FAULT_ANALYSE_CODE,SB_FAULT_ANALYSE_NAME\n" );
        sql00.append("    FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "+orderId+"\n" );
        //插入延期订单主信息
        factory.update(sql00.toString(), null);

        StringBuffer sql11= new StringBuffer();
        sql11.append("INSERT INTO PT_BU_SALE_ORDER_DTL(DTL_ID,ORDER_ID,PART_ID,PART_CODE,PART_NAME,PART_NO,ORDER_COUNT,\n" );
        sql11.append("CREATE_USER,CREATE_TIME,IF_SUPPLIER,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,UNIT_PRICE,AMOUNT,PLAN_PRICE,REMARKS)\n" );
        sql11.append("SELECT F_GETID(),"+delayOrderId+",PART_ID,PART_CODE,PART_NAME,PART_NO,ORDER_COUNT - AUDIT_COUNT,'"+user.getAccount()+"',\n" );
        sql11.append("SYSDATE,IF_SUPPLIER,SUPPLIER_ID,SUPPLIER_CODE,SUPPLIER_NAME,UNIT_PRICE,REMARKS,\n" );
        sql11.append("(ORDER_COUNT - AUDIT_COUNT)*UNIT_PRICE,PLAN_PRICE FROM PT_BU_SALE_ORDER_DTL\n" );
        sql11.append("WHERE ORDER_ID ="+orderId+" AND ORDER_COUNT - AUDIT_COUNT>0");
        //插入延期订单明细信息
        factory.update(sql11.toString(), null);
        
        StringBuffer sql2= new StringBuffer();
        sql2.append("INSERT INTO PT_BU_SALE_ORDER_PAY(PAY_ID,ORDER_ID,ACCOUNT_ID,PAY_AMOUNT,CREATE_USER,CREATE_TIME,ACCOUNT_TYPE)\n" );
        sql2.append("SELECT F_GETID(),"+delayOrderId+",ACCOUNT_ID,PAY_AMOUNT,'"+user.getAccount()+"',SYSDATE,ACCOUNT_TYPE FROM\n" );
        sql2.append(" (SELECT T.ACCOUNT_ID,NVL(T.PAY_AMOUNT,0) - NVL(B.OCCUPY_FUNDS,0) PAY_AMOUNT,T.ACCOUNT_TYPE\n" );
        sql2.append("FROM (SELECT A.ORDER_ID,A.ACCOUNT_ID,A.ACCOUNT_TYPE,A.PAY_AMOUNT FROM PT_BU_SALE_ORDER_PAY A\n" );
        sql2.append(" WHERE ORDER_ID ="+orderId+")T LEFT JOIN PT_BU_SALE_ORDER_OCCUPY_FUNDS B\n");
        sql2.append(" ON T.ORDER_ID = B.ORDER_ID AND T.ACCOUNT_ID = B.ACCOUNT_ID  AND T.ACCOUNT_TYPE =B.ACCOUNT_TYPE\n");
        sql2.append(" AND B.STATUS ="+DicConstant.YXBS_01+" ) T WHERE PAY_AMOUNT>0\n");
        //插入延期订单付款信息
        factory.update(sql2.toString(), null);
        
        QuerySet qs11 = null;
        StringBuffer sql61= new StringBuffer();
        sql61.append("SELECT NVL(SUM(ORDER_COUNT * UNIT_PRICE),0) ORDER_AMOUNT\n" );
        sql61.append("  FROM PT_BU_SALE_ORDER_DTL A\n" );
        sql61.append(" WHERE A.ORDER_ID = "+delayOrderId+"\n");
        // 查询延期订单详细金额汇总
        qs11 = factory.select(null, sql61.toString());

        if(qs11.getRowCount()>0){
            String orderAmount = qs11.getString(1, "ORDER_AMOUNT");
            StringBuffer sql7 = new StringBuffer();
            sql7.append("UPDATE PT_BU_SALE_ORDER SET ORDER_AMOUNT="+orderAmount+" WHERE ORDER_ID = "+delayOrderId+"\n");
            //更新延期订单总金额
            factory.update(sql7.toString(), null);
        }
        QuerySet qs = null;
        StringBuffer sql3= new StringBuffer();
        sql3.append("SELECT A.ACCOUNT_ID,A.ACCOUNT_TYPE,A.PAY_AMOUNT, B.AVAILABLE_AMOUNT\n" );
        sql3.append("  FROM PT_BU_SALE_ORDER_PAY A, PT_BU_ACCOUNT B\n" );
        sql3.append(" WHERE A.ACCOUNT_ID = B.ACCOUNT_ID\n" );
        sql3.append("   AND A.ORDER_ID = "+delayOrderId+"\n");
        //查询延期订单付款信息
        qs = factory.select(null, sql3.toString());
        if(qs.getRowCount()>0){
            for(int i=0;i<qs.getRowCount();i++){
                String accountId  = qs.getString(i+1, "ACCOUNT_ID");
                String payAmount  = qs.getString(i+1, "PAY_AMOUNT");
                String accountType  = qs.getString(i+1, "ACCOUNT_TYPE");
                //更新资金账户
                StringBuffer sql4= new StringBuffer();
                sql4.append("UPDATE PT_BU_ACCOUNT SET OCCUPY_AMOUNT = OCCUPY_AMOUNT+"+payAmount+" ,AVAILABLE_AMOUNT = AVAILABLE_AMOUNT - "+payAmount+" WHERE ACCOUNT_ID =+"+accountId+"\n");
                factory.update(sql4.toString(), null);
                //插入延期订单资金占用
                StringBuffer sql5= new StringBuffer();
                sql5.append("INSERT INTO PT_BU_SALE_ORDER_OCCUPY_FUNDS\n" );
                sql5.append("  (OFUNDS_ID,\n" );
                sql5.append("   ORDER_ID,\n" );
                sql5.append("   ACCOUNT_ID,\n" );
                sql5.append("   ACCOUNT_TYPE,\n" );
                sql5.append("   OCCUPY_FUNDS,\n" );
                sql5.append("   CREATE_USER,\n" );
                sql5.append("   CREATE_TIME,\n" );
                sql5.append("   STATUS)\n" );
                sql5.append("VALUES\n" );
                sql5.append("  (F_GETID(),"+delayOrderId+","+accountId+","+accountType+","+payAmount+",'"+user.getAccount()+"',SYSDATE,"+DicConstant.YXBS_01+")\n");
                factory.update(sql5.toString(), null);
            }
        }
    }

    /**
     * 直发采购订单生成
     */
    public void createPchOrder(String orderId,User user)
            throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT SUPPLIER_ID,SUPPLIER_CODE FROM PT_BU_SALE_ORDER WHERE ORDER_ID = "+orderId+"\n");
        //查询直发订单供应商
        QuerySet qs = factory.select(null, sql.toString());
        String supplierId = "";
        String supplierCode = "";
        if(qs.getRowCount()>0){
            supplierId = qs.getString(1, "SUPPLIER_ID");
            supplierCode = qs.getString(1, "SUPPLIER_CODE");
        }
        //生成直发采购订单号
        String orderNo = PartOddNumberUtil.getPurchaseOrderNo(factory,DicConstant.CGDDLX_05,supplierCode);
        //生成直发采购订单主键
        String pchOrderId = SequenceUtil.getCommonSerivalNumber(factory);
        Calendar calendar = Calendar.getInstance();
        int year_ = calendar.get(calendar.YEAR);
        String year = year_+"";
        int month_ = calendar.get(calendar.MONTH)+1;
        String month = month_+"";
        StringBuffer sql1 = new StringBuffer();
        sql1.append("INSERT INTO PT_BU_PCH_ORDER\n" );
        sql1.append("(SALE_ORDER_ID,PURCHASE_ID,SELECT_MONTH,PURCHASE_TYPE,ORDER_STATUS,CREATE_USER,CREATE_TIME,COMPANY_ID,ORG_ID,OEM_COMPANY_ID,STATUS,APPLY_USER,APPLY_DATE)\n" );
        sql1.append("SELECT "+orderId+","+pchOrderId+",'"+year+"-"+month+"',"+DicConstant.CGDDLX_05+"\n" );
        sql1.append(","+DicConstant.CGDDZT_01+",'"+user.getAccount()+"',SYSDATE,"+user.getCompanyId()+","+user.getOrgId()+","+user.getCompanyId()+","+DicConstant.YXBS_01+"\n" );
        sql1.append(",'"+user.getAccount()+"',SYSDATE FROM PT_BU_SALE_ORDER WHERE ORDER_ID ="+orderId+"\n");
        //插入直发采购订单主信息
        factory.update(sql1.toString(), null);
        
        StringBuffer sql2= new StringBuffer();
        sql2.append("INSERT INTO PT_BU_PCH_ORDER_DTL(DETAIL_ID,PURCHASE_ID,PART_ID,PART_CODE,PART_NAME,PCH_COUNT)\n" );
        sql2.append("SELECT F_GETID(),"+pchOrderId+",A.PART_ID,A.PART_CODE,A.PART_NAME,A.AUDIT_COUNT\n" );
        sql2.append("FROM PT_BU_SALE_ORDER_DTL A,PT_BA_INFO B\n" );
        sql2.append("WHERE A.PART_ID = B.PART_ID\n" );
        sql2.append("AND A.ORDER_ID ="+orderId+"\n");
        //插入直发采购订单明细信息
        factory.update(sql2.toString(), null);
        
//        StringBuffer sql3 = new StringBuffer();
//        sql3.append("SELECT SUM(PCH_COUNT) PURCHASE_COUNT,SUM(PCH_AMOUNT) PURCHASE_AMOUNT,SUM(PLAN_AMOUNT) PLAN_AMOUNT FROM PT_BU_PCH_ORDER_DTL WHERE PURCHASE_ID = "+pchOrderId+"\n");
//        //查询直发采购订单采购数量、采购金额、采购计划金额信息
//        QuerySet qs1 = factory.select(null, sql3.toString());
//        String pchCount = "";
//        String pchAmount = "";
//        String planAmount = "";
//        if(qs1.getRowCount()>0){
//            pchCount = qs1.getString(1, "PURCHASE_COUNT");
//            pchAmount = qs1.getString(1, "PURCHASE_AMOUNT");
//            planAmount = qs1.getString(1, "PLAN_AMOUNT");
//        }
//        StringBuffer sql4 = new StringBuffer();
//        sql4.append("UPDATE PT_BU_PCH_ORDER\n" );
//        sql4.append("SET PURCHASE_COUNT="+pchCount+",PURCHASE_AMOUNT="+pchAmount+",PLAN_AMOUNT="+planAmount+"\n" );
//        sql4.append(" WHERE PURCHASE_ID = "+pchOrderId+"\n" );
//        //更新直发采购订单采购数量、采购金额、采购计划金额信息
//        factory.update(sql4.toString(), null);
    }
    
    
    public QuerySet download(String orderId)throws Exception{
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.DTL_ID,\n" );
        sql.append("       T.PART_ID,\n" );
        sql.append("       T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T.UNIT,\n" );
        sql.append("       T.MINI,\n" );
        sql.append("       T.MIN_PACK,\n" );
        sql.append("       T.MIN_UNIT,\n" );
        sql.append("       T.UNIT_PRICE,\n" );
        sql.append("       T.IF_SUPPLIER,\n" );
        sql.append("       T.SUPPLIER_ID,\n" );
        sql.append("       T.SUPPLIER_CODE,\n" );
        sql.append("       T.SUPPLIER_NAME,\n" );
        sql.append("       T.ORDER_COUNT,\n" );
        sql.append("       T.WAREHOUSE_ID,\n" );
        sql.append("       T.AMOUNT,\n" );
        sql.append("       T.OCCUPY_AMOUNT,\n" );
        sql.append("       T.AVAILABLE_AMOUNT,\n" );
        sql.append("       T.PERSON_NAME \n" );
        sql.append("  FROM (SELECT D.DTL_ID,\n" );
        sql.append("               D.PART_ID,\n" );
        sql.append("               D.PART_CODE,\n" );
        sql.append("               D.PART_NAME,\n" );
        sql.append("               D.UNIT,\n" );
        sql.append("               D.MINI,\n" );
        sql.append("               D.MIN_PACK,\n" );
        sql.append("               D.MIN_UNIT,\n" );
        sql.append("               D.UNIT_PRICE,\n" );
        sql.append("               D.IF_SUPPLIER,\n" );
        sql.append("               D.SUPPLIER_ID,\n" );
        sql.append("               D.SUPPLIER_CODE,\n" );
        sql.append("               D.SUPPLIER_NAME,\n" );
        sql.append("               D.ORDER_COUNT,\n" );
        sql.append("               D.WAREHOUSE_ID,\n" );
        sql.append("               D.PERSON_NAME,\n" );
        sql.append("               NVL(E.AMOUNT, 0) AMOUNT,\n" );
        sql.append("               NVL(E.OCCUPY_AMOUNT, 0) OCCUPY_AMOUNT,\n" );
        sql.append("               NVL(E.AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT\n" );
        sql.append("          FROM (SELECT A.DTL_ID,\n" );
        sql.append("                       A.PART_ID,\n" );
        sql.append("                       A.PART_CODE,\n" );
        sql.append("                       A.PART_NAME,\n" );
        sql.append("                       DT.DIC_VALUE UNIT,\n" );
        sql.append("                       C.MIN_PACK||'/'||DT2.DIC_VALUE MINI,\n" );
        sql.append("                       C.MIN_PACK,\n" );
        sql.append("                       C.MIN_UNIT,\n" );
        sql.append("                       A.UNIT_PRICE,\n" );
        sql.append("                       A.IF_SUPPLIER,\n" );
        sql.append("                       A.SUPPLIER_ID,\n" );
        sql.append("                       A.SUPPLIER_CODE,\n" );
        sql.append("                       A.SUPPLIER_NAME,\n" );
        sql.append("                       A.ORDER_COUNT,\n" );
        sql.append("                       B.WAREHOUSE_ID,\n" );
        sql.append("                       U.PERSON_NAME\n" );
        sql.append("                  FROM PT_BU_SALE_ORDER_DTL A,\n" );
        sql.append("                       PT_BU_SALE_ORDER     B,\n" );
        sql.append("                       PT_BA_INFO           C,\n" );
        sql.append("                       DIC_TREE           DT,\n" );
        sql.append("                       DIC_TREE           DT2,\n" );
        sql.append("                       PT_BA_WAREHOUSE_KEEPER   P,\n" );
        sql.append("                       TM_USER   U\n" );
        sql.append("                 WHERE 1 = 1\n" );
        sql.append("                   AND A.ORDER_ID = B.ORDER_ID\n" );
        sql.append("                   AND A.PART_ID = C.PART_ID\n" );
        sql.append("                   AND A.PART_ID = P.PART_ID\n" );
        sql.append("                   AND B.WAREHOUSE_ID = P.WAREHOUSE_ID\n" );
        sql.append("                   AND P.USER_ACCOUNT = U.ACCOUNT\n" );
        sql.append("                   AND C.UNIT = DT.ID\n" );
        sql.append("                   AND C.MIN_UNIT = DT2.ID\n" );
        sql.append("                   AND A.ORDER_ID = "+orderId+") D\n" );
        sql.append("          LEFT JOIN PT_BU_STOCK E\n" );
        sql.append("            ON D.PART_ID = E.PART_ID\n" );
        sql.append("           AND D.SUPPLIER_ID = E.SUPPLIER_ID\n" );
        sql.append("           AND D.WAREHOUSE_ID = E.WAREHOUSE_ID) T");
            return factory.select(null, sql.toString());
        }
    
}
