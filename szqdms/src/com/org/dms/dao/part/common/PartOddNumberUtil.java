package com.org.dms.dao.part.common;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
/**
 * @Author: gouwentan
 * @Date: 2014-08-06
 * @Description:配件相关单号生成工具类
 */
public class PartOddNumberUtil{

    /**
     * 实销单编号生成
     *
     * 配送中心编号（7位）+年月（4位）+流水号（6位）+标识位（2位）
     * 例：Q00NNLS+1407+000001+CK
     * 服务站编号（7位）+年月（4位）+流水号（4位）；
     * 例：00101172+1407+0001+CK；
     * @param factory 
     * @param orderType 订单类型
     * @return 订单编号
     */
    public static String getRealSaleOutOrderNo(DBFactory factory,String orgType,String orgCode) {

        // 订单编号
        String orderNo = "";
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("CK");
            // 1.渠道代码
            sb.append(orgCode);
            // 2.年月(4位)
            sb.append(getDate("yyMM"));
            // 4.流水号(6位)(自动增长,步长为1)
            String maxOrderNo = getMaxOrderNo(factory,"PT_BU_REAL_SALE","SALE_NO",sb.toString());
            int count = 4;
            String defult = "0001";
            if (DicConstant.ZZLB_09.equals(orgType)) {
                count = 6;
                defult = "000001";
            }
            orderNo = getOrderNo(maxOrderNo,sb.toString(),count,defult,0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderNo;
    }
    /**
     * 外采申请单号生成
     *
     * 配送中心编号（7位）+年月（4位）+流水号（6位）+标识位（2位）
     * 例：Q00NNLS+1407+000001+CK
     * 服务站编号（7位）+年月（4位）+流水号（4位）；
     * 例：00101172+1407+0001+CK；
     * @param factory 
     * @param orderType 订单类型
     * @return 订单编号
     */
    public static String getBuyOrderNo(DBFactory factory,String orgType,String orgCode) {
    	
    	// 订单编号
    	String orderNo = "";
    	try {
    		StringBuffer sb = new StringBuffer();
    		// 1.渠道代码
    		sb.append("WC");
    		sb.append(orgCode);
    		// 2.年月(4位)
    		sb.append(getDate("yyMM"));
    		// 4.流水号(6位)(自动增长,步长为1)
    		String maxOrderNo = getMaxOrderNo(factory,"PT_BU_OUT_BUY","BUY_NO",sb.toString());
    		// 服务站流水号
            int count = 4;
            String defult = "0001";
            if (DicConstant.ZZLB_09.equals(orgType)) {
                count = 6;
                defult = "000001";
            }
    		orderNo = getOrderNo(maxOrderNo,sb.toString(),count,defult,0);
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return orderNo;
    }


    /**
     * 配件索赔申请单编号生成
     *
     * SB（2位）+年份（2位）+流水号（6位）
     * 例：SB+14+000001
     * @param factory 
     * @param orgCode 渠道代码
     * @return 订单编号
     */
    public static String getClaimCyclOrderNo(DBFactory factory) {

        // 订单编号
        String orderNo = "";
        try {
            StringBuffer sb = new StringBuffer();
            // 1.订单首位字符
            sb.append("SB");
            // 2.年月(2位)
            sb.append(getDate("yy"));
            // 4.流水号(6位)(自动增长,步长为1)
            String maxOrderNo = getMaxOrderNo(factory,"PT_BU_CLAIM_APPLY","APPLY_NO",sb.toString());
            orderNo = getOrderNo(maxOrderNo,sb.toString(),6,"000001",0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderNo;
    }

    /**
     * 获取订单首位字符
     * 
     * @param factory
     * @param orderType 订单类型
     * @return
     */
    public static String getOrderFirstLetter(DBFactory factory,String orderType) {

        String orderFirstLetter = "";
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT\n" );
        sql.append("    FIRST_LETTER\n" );
        sql.append("FROM\n" );
        sql.append("    PT_BA_ORDER_TYPE_RULE\n" );
        sql.append("WHERE\n" );
        sql.append("    ORDER_TYPE = '" + orderType + "'");
        try {
            QuerySet querySet = factory.select(null, sql.toString());
            if (querySet.getRowCount() > 0) {
                orderFirstLetter = querySet.getString(1, "FIRST_LETTER");
            } else {
                return "";
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return orderFirstLetter;
    }
    /**
     * 退件申请单编号生成
     *
     * 退库订单类型（2位）+年月（4位）+渠道商编号（7位）+流水号（3位
     * 例：TK+1407+P210011+001+0
     * @param factory 
     * @param orgCode 渠道代码
     * @return 订单编号
     */
    public static String getReturnPurchaseOrderNo(DBFactory factory,String orgCode) {

        // 订单编号
        String orderNo = "";
        try {
            StringBuffer sb = new StringBuffer();
            // 1.订单首位字符
            sb.append("TJ");
            // 2.年月(4位)
            sb.append(getDate("yyMM"));
            // 3.渠道商编号(7位)
            sb.append(orgCode);
            // 4.流水号(3位)(自动增长,步长为1)
            String maxOrderNo = getMaxOrderNo(factory,"PT_BU_RETURN_APPLY","RETURN_NO",sb.toString());
            orderNo = getOrderNo(maxOrderNo,sb.toString(),3,"0010",1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderNo;
    }

    /**
     * 订单号生成
     *
     * @param maxOrderNo 本日最大订单号
     * @param indexOrderNo 订单号-流水号
     * @param count 流水号位数
     * @param defultCount 默认流水号
     * @param endCount 尾数
     * @return
     */
    private static String getOrderNo (String maxOrderNo,String indexOrderNo,int count,String defultCount,int endCount) {
        if (null != maxOrderNo && !"".equals(maxOrderNo)) {
            String commonNO = maxOrderNo.substring(0, indexOrderNo.length());
            String indexNO = maxOrderNo.substring(indexOrderNo.length(), indexOrderNo.length()+count);
            int index_res = Integer.parseInt(indexNO)+1;
            String index_res_ = index_res+"";
            StringBuffer noBuffer = new StringBuffer();
            int index_res_length = count-index_res_.length();
            for (int i = 0; i < index_res_length; i++) {
                noBuffer.append("0");
            }
            noBuffer.append(index_res_);
            if (endCount == 1) {
                noBuffer.append("0");
            }
            return commonNO  + noBuffer;
        }else{
            return indexOrderNo + defultCount;
        }
    }
    /**
     * 查询最大订单编号
     * 
     * @param factory
     * @param tableName 表名
     * @param filedName 字段名
     * @param orderNo 订单编号-流水号
     * @return 结果集
     */
    private static String getMaxOrderNo(DBFactory factory,String tableName,String filedName,String orderNo) throws SQLException {
        
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT\n" );
        sql.append("    MAX(" + filedName + ") " + filedName + "\n" );
        sql.append("FROM\n" );
        sql.append("    " + tableName + "\n" );
        sql.append("WHERE\n" );
        sql.append("    " + filedName + " LIKE '" + orderNo + "%'");
        QuerySet querySet = factory.select(null, sql.toString());
        if (querySet.getRowCount() > 0) {
            return querySet.getString(1, filedName);
        } else {
            return "";
        }
    }

    /**
     * 日期获取
     *
     * @param pFormat 日期格式
     * @return 日期字符串
     */
    private static String getDate(String pFormat){

        // 日期格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pFormat);
        // 获取当前日期
        Date date = new Date();

        return simpleDateFormat.format(date);
    }

    /**
     * 销售订单编号生成
     */
    public static String getSaleOrderNo(DBFactory factory,String firstLetter,String orgCode){
        Calendar calendar = Calendar.getInstance();
        int year_ = calendar.get(calendar.YEAR);
        String year = year_+"";
        year = year.substring(2, 4);
        int month_ = calendar.get(calendar.MONTH)+1;
        String month = month_+"";
        if (month.length()<2) {
            month = month.format("0"+month, month);
        }
        StringBuffer orderNoBuffer = new StringBuffer();
        orderNoBuffer.append(firstLetter).append(year).append(month).append(orgCode);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT NVL(MAX(T.ORDER_NO),0) ORDER_NO FROM PT_BU_SALE_ORDER T WHERE T.ORDER_NO LIKE '"+orderNoBuffer+"%'\n");
        StringBuffer orderNo = new StringBuffer();
        try {
            String maxOrderNo="";
            QuerySet qs = factory.select(null, sql.toString());
            maxOrderNo = qs.getString(1, "ORDER_NO");
            if (!"0".equals(maxOrderNo)) {
                String commonNO = maxOrderNo.substring(0, maxOrderNo.length()-4);                    
                String indexNO = maxOrderNo.substring(maxOrderNo.length()-4, maxOrderNo.length()-1);    
                int index_res = Integer.parseInt(indexNO)+1;                                        
                String index_res_ = index_res+"";                        
                StringBuffer noBuffer = new StringBuffer();
                int index_res_length = 3-index_res_.length();
                for (int i = 0; i < index_res_length; i++) {
                    noBuffer.append("0");
                }
                noBuffer.append(index_res_).append("0");
                orderNo.append(commonNO).append(noBuffer);
            }else{
                orderNo.append(firstLetter).append(year).append(month).append(orgCode).append("0010").toString();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return orderNo.toString();
    }
    
    /**
     * 延期订单编号生成
     * @throws Exception 
     */
    public static String getDelaySaleOrderNo(DBFactory factory,String orderId) throws Exception{
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ORDER_NO FROM PT_BU_SALE_ORDER T WHERE T.ORDER_ID ="+orderId+"\n");
        StringBuffer orderNo = new StringBuffer();
        try {
            String maxOrderNo="";
            QuerySet qs = factory.select(null, sql.toString());
            if(qs.getRowCount()>0){
                maxOrderNo = qs.getString(1, "ORDER_NO");
                String sql1 ="SELECT MAX(ORDER_NO) ORDER_NO FROM PT_BU_SALE_ORDER T WHERE T.ORDER_NO LIKE '%"+maxOrderNo.substring(0, maxOrderNo.length()-1)+"%'";
                QuerySet qs1 = factory.select(null, sql1.toString());
                if(qs1.getRowCount()>0){
                	maxOrderNo = qs1.getString(1, "ORDER_NO");
                }
            }
            String commonNO = maxOrderNo.substring(0, maxOrderNo.length()-1);                    
            String indexNO = maxOrderNo.substring(maxOrderNo.length()-1, maxOrderNo.length());    
            int index_res = Integer.parseInt(indexNO)+1;
            if(index_res>9){
                throw new Exception("延期次数已大于10次,不能再进行延期.");
            }
            String index_res_ = index_res+"";                        
            StringBuffer noBuffer = new StringBuffer();
            noBuffer.append(index_res_);
            orderNo.append(commonNO).append(noBuffer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderNo.toString();
    }
    /**
     * 采购订单编号生成
     */
    public static String getPurchaseOrderNo(DBFactory factory,String purchaseType,String supplierCode){
        Calendar calendar = Calendar.getInstance();
        int year_ = calendar.get(calendar.YEAR);
        String year = year_+"";
        year = year.substring(2, 4);
        int month_ = calendar.get(calendar.MONTH)+1;
        String month = month_+"";
        if (month.length()<2) {
            month = month.format("0"+month, month);
        }
        String firstLetter ="";
        if(DicConstant.CGDDLX_01.equals(purchaseType)){
            firstLetter ="YD";
        }
        if(DicConstant.CGDDLX_02.equals(purchaseType)){
            firstLetter ="QJ";
        }
        if(DicConstant.CGDDLX_03.equals(purchaseType)){
            firstLetter ="QT";
        }
        if(DicConstant.CGDDLX_04.equals(purchaseType)){
            firstLetter ="JP";
        }
        if(DicConstant.CGDDLX_05.equals(purchaseType)){
            firstLetter ="ZF";
        }
        if(DicConstant.CGDDLX_06.equals(purchaseType)){
            firstLetter ="ZC";
        }
        StringBuffer orderNoBuffer = new StringBuffer();
        orderNoBuffer.append(firstLetter).append("CL").append(year).append(month).append(supplierCode);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT MAX(T.ORDER_NO) ORDER_NO FROM PT_BU_PCH_ORDER T WHERE T.ORDER_NO LIKE '"+orderNoBuffer+"%'\n");
        StringBuffer orderNo = new StringBuffer();
        try {
            String maxOrderNo="";
            QuerySet qs = factory.select(null, sql.toString());
            if(qs.getRowCount()>0){
                maxOrderNo = qs.getString(1, "ORDER_NO");
            }
            if (null != maxOrderNo && !"".equals(maxOrderNo)) {
                String commonNO = maxOrderNo.substring(0, maxOrderNo.length()-3);                    
                String indexNO = maxOrderNo.substring(maxOrderNo.length()-3, maxOrderNo.length());    
                int index_res = Integer.parseInt(indexNO)+1;                                        
                String index_res_ = index_res+"";                        
                StringBuffer noBuffer = new StringBuffer();
                int index_res_length = 3-index_res_.length();
                for (int i = 0; i < index_res_length; i++) {
                    noBuffer.append("0");
                }
                noBuffer.append(index_res_);
                orderNo.append(commonNO).append(noBuffer);
            }else{
                orderNo.append(firstLetter).append("CL").append(year).append(month).append(supplierCode).append("001").toString();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return orderNo.toString();
    }
    /**
     * 采购订单拆分单编号生成
     */
    public static String getSplitNo(DBFactory factory,String ORDER_NO)throws Exception
    {
        QuerySet qs = null;
        String num = ORDER_NO;
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT max(").append("SPLIT_NO").append(") as CF FROM ");
        sql.append( "PT_BU_PCH_ORDER_SPLIT").append(" t");
        sql.append(" where t.").append("SPLIT_NO").append(" like '%").append(ORDER_NO).append("%'");
        qs = factory.select(null, sql.toString());
        if(qs.getString(1,"CF")==null||"".equals(qs.getString(1,"CF"))){
             num=num+"01";
        }else{
            int sz = Integer.parseInt(qs.getString(1,"CF").substring(qs.getString(1,"CF").length()-2, qs.getString(1,"CF").length()))+1;
            if(String.valueOf(sz).length()==1){
                num=num.substring(0, num.length())+"0"+String.valueOf(sz);
            }else{
                num=num.substring(0, num.length())+String.valueOf(sz);
            }
        }
        return num;
    }
    /**
     * 采购退货单编号生成
     */
    public static String getPurchaseReturnOrderNo(DBFactory factory,String supplierCode){
        Calendar calendar = Calendar.getInstance();
        int year_ = calendar.get(calendar.YEAR);
        String year = year_+"";
        year = year.substring(2, 4);
        int month_ = calendar.get(calendar.MONTH)+1;
        String month = month_+"";
        if (month.length()<2) {
            month = month.format("0"+month, month);
        }
        String firstLetter ="TK";
        StringBuffer orderNoBuffer = new StringBuffer();
        orderNoBuffer.append(firstLetter).append("CL").append(year).append(month).append(supplierCode);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT MAX(T.RETURN_NO) ORDER_NO FROM PT_BU_PCH_RETURN T WHERE T.RETURN_NO LIKE '%"+orderNoBuffer+"%'\n");
        StringBuffer orderNo = new StringBuffer();
        try {
            String maxOrderNo="";
            QuerySet qs = factory.select(null, sql.toString());
            if(qs.getRowCount()>0){
                maxOrderNo = qs.getString(1, "ORDER_NO");
            }
            if (null != maxOrderNo && !"".equals(maxOrderNo)) {
                String commonNO = maxOrderNo.substring(0, maxOrderNo.length()-3);                    
                String indexNO = maxOrderNo.substring(maxOrderNo.length()-3, maxOrderNo.length());    
                int index_res = Integer.parseInt(indexNO)+1;                                        
                String index_res_ = index_res+"";                        
                StringBuffer noBuffer = new StringBuffer();
                int index_res_length = 3-index_res_.length();
                for (int i = 0; i < index_res_length; i++) {
                    noBuffer.append("0");
                }
                noBuffer.append(index_res_);
                orderNo.append(commonNO).append(noBuffer);
            }else{
                orderNo.append(firstLetter).append("CL").append(year).append(month).append(supplierCode).append("001").toString();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return orderNo.toString();
    }
    /**
     * 生成采购入库单号
     * @param factory
     * @param splitId
     * @param splitNo
     * @return
     * @throws Exception
     */
    public static String getPchInBillNo(DBFactory factory,String splitId,String splitNo) throws Exception{
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT COUNT(A.IN_ID)+1 PRINT_COUNT\n" );
        sql.append("  FROM PT_BU_STOCK_IN A\n" );
        sql.append(" WHERE A.ORDER_ID = "+splitId+"\n" );
        sql.append("   AND A.PRINT_STATUS = "+DicConstant.DYZT_02+"\n");

        String printCount = "0";
        QuerySet qs = factory.select(null,sql.toString());
        if(qs.getRowCount()>0){
            printCount = qs.getString(1,"PRINT_COUNT");
        }

        if(Integer.parseInt(printCount)<10){
            printCount = "0"+printCount;
        }
        return splitNo + printCount;
    }

    /**
     * 生成销售退件入库单号
     * @param RETURN_NO
     * @return
     */
    public static String getSaleRetInBillNo(String RETURN_NO){
        return RETURN_NO;
    }

    /**
     * 生成移库出库和其他出库出库单号
     * @param factory
     * @param warehouseCode
     * @return
     * @throws Exception
     */
    public static String getMoveAndOtherOutBillNo(DBFactory factory,String warehouseCode) throws Exception {
        Calendar calendar = Calendar.getInstance();
        int year_ = calendar.get(calendar.YEAR);
        String year = year_+"";
        year = year.substring(2, 4);
        int month_ = calendar.get(calendar.MONTH)+1;
        String month = month_+"";
        if (month.length()<2) {
            month = month.format("0"+month, month);
        }
        StringBuffer outBillNoBuffer = new StringBuffer();
        outBillNoBuffer.append(warehouseCode).append(year).append(month);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT MAX(T.OUT_NO) OUT_NO FROM PT_BU_STOCK_OUT T WHERE T.OUT_NO LIKE '"+outBillNoBuffer+"%'\n");
        StringBuffer outBillNo = new StringBuffer();
        try {
            String maxOutNo="";
            QuerySet qs = factory.select(null, sql.toString());
            if(qs.getRowCount()>0){
                maxOutNo = qs.getString(1, "OUT_NO");
            }
            if (null != maxOutNo && !"".equals(maxOutNo)) {
                String commonNO = maxOutNo.substring(0, maxOutNo.length()-8);
                String indexNO = maxOutNo.substring(maxOutNo.length()-8, maxOutNo.length()-2);
                int index_res = Integer.parseInt(indexNO)+1;
                String index_res_ = index_res+"";
                StringBuffer noBuffer = new StringBuffer();
                int index_res_length = 6-index_res_.length();
                for (int i = 0; i < index_res_length; i++) {
                    noBuffer.append("0");
                }
                outBillNo.append(commonNO).append(noBuffer).append(index_res_).append("CK");
            }else{
                outBillNo.append(warehouseCode).append(year).append(month).append("000001").append("CK").toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outBillNo.toString();
    }
    /**
     * 生成库存盘点编号
     * @param factory
     * @param warehouseCode
     * @return
     * @throws Exception
     */
    public static String getCheckSetUpNo(DBFactory factory,String warehouseCode) throws Exception {
        Calendar calendar = Calendar.getInstance();
        int year_ = calendar.get(calendar.YEAR);
        String year = year_+"";
//        year = year.substring(2, 4);
        int month_ = calendar.get(calendar.MONTH)+1;
        String month = month_+"";
        if (month.length()<2) {
            month = month.format("0"+month, month);
        }
        int day = calendar.get(calendar.DAY_OF_MONTH)+0;
        StringBuffer outBillNoBuffer = new StringBuffer();
        outBillNoBuffer.append(warehouseCode).append(year).append(month).append(day);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT MAX(T.INVENTORY_NO) INVENTORY_NO FROM PT_BU_INVENTORY T WHERE T.INVENTORY_NO LIKE '"+outBillNoBuffer+"%'\n");
        StringBuffer outBillNo = new StringBuffer();
        try {
            String maxOutNo="";
            QuerySet qs = factory.select(null, sql.toString());
            if(qs.getRowCount()>0){
                maxOutNo = qs.getString(1, "INVENTORY_NO");
            }
            if (null != maxOutNo && !"".equals(maxOutNo)) {
                String commonNO = maxOutNo.substring(0, maxOutNo.length()-2);
                String indexNO = maxOutNo.substring(maxOutNo.length()-2);
                int index_res = Integer.parseInt(indexNO)+1;
                String index_res_ = index_res+"";
                StringBuffer noBuffer = new StringBuffer();
                int index_res_length = 2-index_res_.length();
                for (int i = 0; i < index_res_length; i++) {
                    noBuffer.append("0");
                }
//                outBillNo.append(commonNO).append(noBuffer).append(index_res_).append("CK");
                outBillNo.append(commonNO).append(noBuffer).append(index_res_);
            }else{
//                outBillNo.append(warehouseCode).append(year).append(month).append("000001").append("CK").toString();
                outBillNo.append(warehouseCode).append(year).append(month).append(day).append("01").toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outBillNo.toString();
    }
    /**
     * 生成其他入库单号
     * @param factory
     * @param warehouseCode
     * @return
     * @throws Exception
     */
    public static String getOtherInBillNo(DBFactory factory,String warehouseCode) throws Exception {
        Calendar calendar = Calendar.getInstance();
        int year_ = calendar.get(calendar.YEAR);
        String year = year_+"";
        year = year.substring(2, 4);
        int month_ = calendar.get(calendar.MONTH)+1;
        String month = month_+"";
        if (month.length()<2) {
            month = month.format("0"+month, month);
        }
        StringBuffer inBillNoBuffer = new StringBuffer();
        inBillNoBuffer.append(warehouseCode).append(year).append(month);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT MAX(T.IN_NO) IN_NO FROM PT_BU_STOCK_IN T WHERE T.IN_NO LIKE '"+inBillNoBuffer+"%'\n");
        StringBuffer inBillNo = new StringBuffer();
        try {
            String maxInNo="";
            QuerySet qs = factory.select(null, sql.toString());
            if(qs.getRowCount()>0){
                maxInNo = qs.getString(1, "IN_NO");
            }
            if (null != maxInNo && !"".equals(maxInNo)) {
                String commonNO = maxInNo.substring(0, maxInNo.length()-8);
                String indexNO = maxInNo.substring(maxInNo.length()-8, maxInNo.length()-2);
                int index_res = Integer.parseInt(indexNO)+1;
                String index_res_ = index_res+"";
                StringBuffer noBuffer = new StringBuffer();
                int index_res_length = 3-index_res_.length();
                for (int i = 0; i < index_res_length; i++) {
                    noBuffer.append("0");
                }
                inBillNo.append(commonNO).append(noBuffer).append(index_res_).append("RK");
            }else{
                inBillNo.append(warehouseCode).append(year).append(month).append("001").append("RK").toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inBillNo.toString();
    }
    /**
     * 生成发运单号
     * @param factory
     * @return
     * @throws Exception
     */
    public static String getShipNo(DBFactory factory) throws Exception {
        Calendar calendar = Calendar.getInstance();
        int year_ = calendar.get(calendar.YEAR);
        int month_ = calendar.get(calendar.MONTH)+1;
        String year = year_+"";
        year = year.substring(2, 4);
        String month = month_+"";
        if (month.length()<2) {
            month = month.format("0"+month, month);
        }
        StringBuffer shipNoBuffer = new StringBuffer();
        shipNoBuffer.append("FY").append(year).append(month);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT MAX(T.SHIP_NO) SHIP_NO FROM PT_BU_ORDER_SHIP T WHERE T.SHIP_NO LIKE '"+shipNoBuffer+"%'\n");
        StringBuffer shipNo = new StringBuffer();
        try {
            String maxShipNo="";
            QuerySet qs = factory.select(null, sql.toString());
            if(qs.getRowCount()>0){
                maxShipNo = qs.getString(1, "SHIP_NO");
            }
            if (null != maxShipNo && !"".equals(maxShipNo)) {
                String commonNO = maxShipNo.substring(0, maxShipNo.length()-6);
                String indexNO = maxShipNo.substring(maxShipNo.length()-6);
                int index_res = Integer.parseInt(indexNO)+1;
                String index_res_ = index_res+"";
                StringBuffer noBuffer = new StringBuffer();
                int index_res_length = 6-index_res_.length();
                for (int i = 0; i < index_res_length; i++) {
                    noBuffer.append("0");
                }
                shipNo.append(commonNO).append(noBuffer).append(index_res_);
            }else{
                shipNo.append("FY").append(year).append(month).append("000001").toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shipNo.toString();
    }
    
    /**
     * 采购订单编号生成
     */
    public static String getPromotionNo(DBFactory factory){
        Calendar calendar = Calendar.getInstance();
        int year_ = calendar.get(calendar.YEAR);
        String year = year_+"";
        year = year.substring(2, 4);
        int month_ = calendar.get(calendar.MONTH)+1;
        String month = month_+"";
        if (month.length()<2) {
            month = month.format("0"+month, month);
        }
        String firstLetter ="";
        StringBuffer orderNoBuffer = new StringBuffer();
        orderNoBuffer.append(firstLetter).append(year).append(month);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT MAX(T.PROM_CODE) ORDER_NO FROM PT_BU_PROMOTION T WHERE T.PROM_CODE LIKE '"+orderNoBuffer+"%'\n");
        StringBuffer orderNo = new StringBuffer();
        try {
            String maxOrderNo="";
            QuerySet qs = factory.select(null, sql.toString());
            if(qs.getRowCount()>0){
                maxOrderNo = qs.getString(1, "ORDER_NO");
            }
            if (null != maxOrderNo && !"".equals(maxOrderNo)) {
                String commonNO = maxOrderNo.substring(0, maxOrderNo.length()-3);
                String indexNO = maxOrderNo.substring(maxOrderNo.length()-3, maxOrderNo.length());
                int index_res = Integer.parseInt(indexNO)+1;
                String index_res_ = index_res+"";
                StringBuffer noBuffer = new StringBuffer();
                int index_res_length = 3-index_res_.length();
                for (int i = 0; i < index_res_length; i++) {
                    noBuffer.append("0");
                }
                noBuffer.append(index_res_);
                orderNo.append(commonNO).append(noBuffer);
            }else{
                orderNo.append(firstLetter).append(year).append(month).append("01").toString();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return orderNo.toString();
    }
    /**
     * 入库流水号生成
     */
    public static String getInContinualNo(DBFactory factory){
        Calendar calendar = Calendar.getInstance();
        int year_ = calendar.get(calendar.YEAR);
        String year = year_+"";
        year = year.substring(2, 4);
        int month_ = calendar.get(calendar.MONTH)+1;
        String month = month_+"";
        if (month.length()<2) {
            month = month.format("0"+month, month);
        }
        StringBuffer orderNoBuffer = new StringBuffer();
        orderNoBuffer.append(year).append(month);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT MAX(T.CONTINUAL_NO) ORDER_NO FROM PT_BU_STOCK_IN_CONTINUAL T WHERE T.CONTINUAL_NO LIKE '"+orderNoBuffer+"%'\n");
        StringBuffer orderNo = new StringBuffer();
        try {
            String maxOrderNo="";
            QuerySet qs = factory.select(null, sql.toString());
            if(qs.getRowCount()>0){
                maxOrderNo = qs.getString(1, "ORDER_NO");
            }
            if (null != maxOrderNo && !"".equals(maxOrderNo)) {
                String commonNO = maxOrderNo.substring(0, maxOrderNo.length()-6);                    
                String indexNO = maxOrderNo.substring(maxOrderNo.length()-6, maxOrderNo.length());    
                int index_res = Integer.parseInt(indexNO)+1;                                        
                String index_res_ = index_res+"";                        
                StringBuffer noBuffer = new StringBuffer();
                int index_res_length = 6-index_res_.length();
                for (int i = 0; i < index_res_length; i++) {
                    noBuffer.append("0");
                }
                noBuffer.append(index_res_);
                orderNo.append(commonNO).append(noBuffer);
            }else{
                orderNo.append(year).append(month).append("000001").toString();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return orderNo.toString();
    }
    /**
     * 出库流水号生成
     */
    public static String getOutContinualNo(DBFactory factory){
        Calendar calendar = Calendar.getInstance();
        int year_ = calendar.get(calendar.YEAR);
        String year = year_+"";
        year = year.substring(2, 4);
        int month_ = calendar.get(calendar.MONTH)+1;
        String month = month_+"";
        if (month.length()<2) {
            month = month.format("0"+month, month);
        }
        StringBuffer orderNoBuffer = new StringBuffer();
        orderNoBuffer.append(year).append(month);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT MAX(T.CONTINUAL_NO) ORDER_NO FROM PT_BU_STOCK_OUT_CONTINUAL T WHERE T.CONTINUAL_NO LIKE '"+orderNoBuffer+"%'\n");
        StringBuffer orderNo = new StringBuffer();
        try {
            String maxOrderNo="";
            QuerySet qs = factory.select(null, sql.toString());
            if(qs.getRowCount()>0){
                maxOrderNo = qs.getString(1, "ORDER_NO");
            }
            if (null != maxOrderNo && !"".equals(maxOrderNo)) {
                String commonNO = maxOrderNo.substring(0, maxOrderNo.length()-6);                    
                String indexNO = maxOrderNo.substring(maxOrderNo.length()-6, maxOrderNo.length());    
                int index_res = Integer.parseInt(indexNO)+1;                                        
                String index_res_ = index_res+"";                        
                StringBuffer noBuffer = new StringBuffer();
                int index_res_length = 6-index_res_.length();
                for (int i = 0; i < index_res_length; i++) {
                    noBuffer.append("0");
                }
                noBuffer.append(index_res_);
                orderNo.append(commonNO).append(noBuffer);
            }else{
                orderNo.append(year).append(month).append("000001").toString();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return orderNo.toString();
    }
    /**
     * 采购领料票号生成
     */
    public static String getReceiptNo(DBFactory factory) throws Exception {
        Calendar calendar = Calendar.getInstance();
        int year_ = calendar.get(calendar.YEAR);
        int month_ = calendar.get(calendar.MONTH)+1;
        String year = year_+"";
        year = year.substring(0, 4);
        String month = month_+"";
        if (month.length()<2) {
            month = month.format("0"+month, month);
        }
        StringBuffer shipNoBuffer = new StringBuffer();
        shipNoBuffer.append(year).append(month);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT MAX(T.REC_NO) REC_NO FROM PT_BU_PCH_RECEIVE_ORDER T WHERE T.REC_NO LIKE '"+shipNoBuffer+"%'\n");
        StringBuffer recNo = new StringBuffer();
        try {
            String maxShipNo="";
            QuerySet qs = factory.select(null, sql.toString());
            if(qs.getRowCount()>0){
                maxShipNo = qs.getString(1, "REC_NO");
            }
            if (null != maxShipNo && !"".equals(maxShipNo)) {
                String commonNO = maxShipNo.substring(0, maxShipNo.length()-6);
                String indexNO = maxShipNo.substring(maxShipNo.length()-6);
                int index_res = Integer.parseInt(indexNO)+1;
                String index_res_ = index_res+"";
                StringBuffer noBuffer = new StringBuffer();
                int index_res_length = 6-index_res_.length();
                for (int i = 0; i < index_res_length; i++) {
                    noBuffer.append("0");
                }
                recNo.append(commonNO).append(noBuffer).append(index_res_);
            }else{
            	recNo.append(year).append(month).append("0001").toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recNo.toString();
    }
    public static String getReceiptNo(DBFactory factory,String CARRIER_CODE){
        Calendar calendar = Calendar.getInstance();
        int year_ = calendar.get(calendar.YEAR);
        String year = year_+"";
        year = year.substring(2, 4);
        int month_ = calendar.get(calendar.MONTH)+1;
        String month = month_+"";
        if (month.length()<2) {
            month = month.format("0"+month, month);
        }
        String firstLetter ="";
        StringBuffer orderNoBuffer = new StringBuffer();
        orderNoBuffer.append(CARRIER_CODE).append(year).append(month);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT MAX(T.RECEIPT_NO) ORDER_NO FROM PT_BU_ORDER_SHIP_CARRIER T WHERE T.RECEIPT_NO LIKE '"+orderNoBuffer+"%'\n");
        StringBuffer orderNo = new StringBuffer();
        try {
            String maxOrderNo="";
            QuerySet qs = factory.select(null, sql.toString());
            if(qs.getRowCount()>0){
                maxOrderNo = qs.getString(1, "ORDER_NO");
            }
            if (null != maxOrderNo && !"".equals(maxOrderNo)) {
                String commonNO = maxOrderNo.substring(0, maxOrderNo.length()-3);                    
                String indexNO = maxOrderNo.substring(maxOrderNo.length()-3, maxOrderNo.length());    
                int index_res = Integer.parseInt(indexNO)+1;                                        
                String index_res_ = index_res+"";                        
                StringBuffer noBuffer = new StringBuffer();
                int index_res_length = 3-index_res_.length();
                for (int i = 0; i < index_res_length; i++) {
                    noBuffer.append("0");
                }
                noBuffer.append(index_res_);
                orderNo.append(commonNO).append(noBuffer);
            }else{
                orderNo.append(CARRIER_CODE).append(year).append(month).append("001").toString();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return orderNo.toString();
    }
    
    public static String getEntrustNo(DBFactory factory){
        Calendar calendar = Calendar.getInstance();
        int year_ = calendar.get(calendar.YEAR);
        String year = year_+"";
        year = year.substring(2, 4);
        int month_ = calendar.get(calendar.MONTH)+1;
        String month = month_+"";
        if (month.length()<2) {
            month = month.format("0"+month, month);
        }
        String firstLetter ="FPWTD";
        StringBuffer orderNoBuffer = new StringBuffer();
        orderNoBuffer.append(year).append(month);
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT MAX(T.ENTRUST_NO) ORDER_NO FROM PT_BU_INVOICE_ENTRUST T WHERE T.ENTRUST_NO LIKE '"+orderNoBuffer+"%'\n");
        StringBuffer orderNo = new StringBuffer();
        try {
            String maxOrderNo="";
            QuerySet qs = factory.select(null, sql.toString());
            if(qs.getRowCount()>0){
                maxOrderNo = qs.getString(1, "ORDER_NO");
            }
            if (null != maxOrderNo && !"".equals(maxOrderNo)) {
                String commonNO = maxOrderNo.substring(0, maxOrderNo.length()-8);                    
                String indexNO = maxOrderNo.substring(maxOrderNo.length()-8, maxOrderNo.length());    
                int index_res = Integer.parseInt(indexNO)+1;                                        
                String index_res_ = index_res+"";                        
                StringBuffer noBuffer = new StringBuffer();
                int index_res_length = 8-index_res_.length();
                for (int i = 0; i < index_res_length; i++) {
                    noBuffer.append("0");
                }
                noBuffer.append(index_res_);
                orderNo.append(commonNO).append(noBuffer);
            }else{
                orderNo.append(year).append(month).append("00000001").toString();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return orderNo.toString();
    }
}
