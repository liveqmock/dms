package com.org.dms.dao.part.purchaseMng.purchaseOrder;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPchOrderDtlVO;
import com.org.dms.vo.part.PtBuPchOrderShippingVO;
import com.org.dms.vo.part.PtBuPchOrderSplitDtlVO;
import com.org.dms.vo.part.PtBuPchOrderSplitVO;
import com.org.dms.vo.part.PtBuPchOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.common.dataset.DataObjImpl;
import com.org.mvc.context.ActionContext;

public class ArmyPurchaseOrderDao extends BaseDAO{
	//定义instance
    public  static final ArmyPurchaseOrderDao getInstance(ActionContext atx)
    {
    	ArmyPurchaseOrderDao dao = new ArmyPurchaseOrderDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    /**
     * 
     * @date()2014年7月15日上午11:43:07
     * @author Administrator
     * @to_do:采购订单维护界面查询
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet orderSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND T.STATUS = "+DicConstant.YXBS_01+"\n" +
					"AND T.PURCHASE_ID = T1.PURCHASE_ID(+)"+"\n"+
					"AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
					"AND T.COMPANY_ID = "+user.getCompanyId()+"\n" + 
					"AND T.ORG_ID = "+user.getOrgId()+"\n" + 
					"AND T.ORDER_STATUS = "+DicConstant.CGDDZT_01+"\n" + 
					"AND T.APPLY_USER = '"+user.getAccount()+"'\n" + 
					"AND T.PURCHASE_TYPE = "+DicConstant.CGDDLX_04+"\n";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PURCHASE_ID,\n" );
    	sql.append("       T.ORDER_NO,\n" );
    	sql.append("       T.PURCHASE_TYPE,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("	   T.SUPPLIER_ID,");
    	sql.append("       T.SELECT_MONTH,\n" );
    	sql.append("       T1.COUNT,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.APPLY_USER\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER T,(SELECT COUNT(1) COUNT,PURCHASE_ID FROM PT_BU_PCH_ORDER_DTL GROUP BY PURCHASE_ID) T1  \n");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("PURCHASE_TYPE", "CGDDLX");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
		bs.setFieldUserID("APPLY_USER");
    	return bs;
    }
    /**
     * 
     * @date()2014年7月15日上午11:44:38
     * @author Administrator
     * @to_do:订单以选择配件查询
     * @param page
     * @param user
     * @param PURCHASE_ID
     * @return
     * @throws Exception
     */
    public BaseResultSet orderPartSearch(PageManager page, User user, String PURCHASE_ID) throws Exception
    {
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DETAIL_ID,\n" );
    	sql.append("       T.PURCHASE_ID,\n" );
    	sql.append("       T.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T1.UNIT,\n" );
    	sql.append("       T1.MIN_UNIT,\n");
    	sql.append("       T1.MIN_PACK,\n" );
    	sql.append("       T.PCH_COUNT,\n" );
    	sql.append("       T.PCH_PRICE,\n" );
    	sql.append("       T.PCH_AMOUNT,\n" );
    	sql.append("       T.DELIVERY_CYCLE,\n" );
    	sql.append("       T.REMARKS\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_DTL T, PT_BA_INFO T1\n" );
    	sql.append(" WHERE T.PART_ID = T1.PART_ID");
    	sql.append("	AND T.PURCHASE_ID = "+PURCHASE_ID+"");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("UNIT", "JLDW");
    	bs.setFieldDic("MIN_UNIT", "JLDW");
    	return bs;
    }
    /**
     * 
     * @date()2014年7月15日上午11:44:55
     * @author Administrator
     * @to_do:待选择配件查询
     * @param page
     * @param user
     * @param conditions
     * @param PURCHASE_ID
     * @return
     * @throws Exception
     */
    public BaseResultSet searchPart(PageManager page, User user, String conditions,String SUPPLIER_ID, String PURCHASE_ID) throws Exception {
        String wheres = conditions;
        wheres +="AND T.PART_ID = T1.PART_ID\n" +
				"AND T.PART_ID = T2.PART_ID\n" + 
				"AND T.PART_TYPE != "+DicConstant.PJLB_02+"\n" + 
				"AND T.PART_STATUS = "+DicConstant.PJZT_01+"\n" + 
				"AND T1.SUPPLIER_ID = "+SUPPLIER_ID+" AND T1.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" + 
				"AND T2.USER_ACCOUNT = T3.APPLY_USER\n" + 
				"AND T1.PCH_PRICE > 0\n" +
				"AND T3.PURCHASE_ID = "+PURCHASE_ID+"\n" + 
				"AND NOT EXISTS (SELECT 1\n" + 
				"       FROM PT_BU_PCH_ORDER_DTL T2\n" + 
				"      WHERE T2.PURCHASE_ID = "+PURCHASE_ID+"\n" + 
				"        AND T.PART_ID = T2.PART_ID) "+
		        "AND EXISTS\n" +
		        "       (SELECT 1\n"+
		        "                FROM PT_BU_PCH_CONTRACT A, PT_BU_PCH_CONTRACT_DTL B\n" +
		        "               WHERE A.CONTRACT_ID = B.CONTRACT_ID\n" +
		        "                 AND T3.SUPPLIER_CODE = A.SUPPLIER_CODE\n"+
		        "                 AND T.PART_CODE = B.PART_CODE\n"+ 
		        "                 AND A.CONTRACT_STATUS IN (200109, 200110)\n"+
		        "                 AND A.STATUS = 100201)"+
		        " ORDER BY T.PART_CODE";
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.PART_ID,\n" );
        sql.append("       T.PART_CODE,\n" );
        sql.append("       T.PART_NAME,\n" );
        sql.append("       T.UNIT,\n" );
        sql.append("       T.MIN_PACK,\n" );
        sql.append("       T1.APPLY_CYCLE,\n" );
        sql.append("       T1.PCH_PRICE\n" );
        sql.append("  FROM PT_BA_INFO T, PT_BA_PART_SUPPLIER_RL T1, PT_BA_PCH_ATTRIBUTE T2,PT_BU_PCH_ORDER T3");
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", "JLDW");
        return bs;
    }
    /**
     * 
     * @date()2014年7月15日上午11:45:16
     * @author Administrator
     * @to_do:订单新增
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertPurchaseOrder(PtBuPchOrderVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
    /**
     * 
     * @date()2014年7月15日上午11:45:28
     * @author Administrator
     * @to_do:订单修改
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updatePurchaseOrder(PtBuPchOrderVO vo)
    		throws Exception
    {
    	return factory.update(vo);
    }
    /**
     * 
     * @date()2014年7月15日上午11:45:41
     * @author Administrator
     * @to_do:订单配件新增
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertPart(PtBuPchOrderDtlVO vo)
            throws Exception {
        return factory.insert(vo);
    }
    /**
     * 
     * @date()2014年7月15日上午11:50:27
     * @author Administrator
     * @to_do:配件订单删除
     * @param DETAIL_ID
     * @return
     * @throws Exception
     */
    public boolean delParts(String DETAIL_ID ) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("DELETE FROM PT_BU_PCH_ORDER_DTL WHERE DETAIL_ID = "+DETAIL_ID+"");
    	return factory.update(sql.toString(), null);
    }
    
    public QuerySet checkPart(String PURCHASE_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT 1 FROM PT_BU_PCH_ORDER_DTL WHERE PURCHASE_ID = "+PURCHASE_ID+"\n" );
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    /**
     * 
     * @date()2014年7月15日上午11:45:58
     * @author Administrator
     * @to_do:订单删除时同时删除对应配件信息
     * @param PURCHASE_ID
     * @return
     * @throws Exception
     */
    public boolean purchaseOrderDelete(String PURCHASE_ID ) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("DELETE FROM PT_BU_PCH_ORDER WHERE PURCHASE_ID = "+PURCHASE_ID+"");
    	return factory.update(sql.toString(), null);
    }
    /**
     * 
     * @date()2014年7月15日上午11:46:25
     * @author Administrator
     * @to_do:订单删除时同时删除对应配件信息
     * @param PURCHASE_ID
     * @return
     * @throws Exception
     */
    public boolean purchaseOrderPartDelete(String PURCHASE_ID ) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("DELETE FROM PT_BU_PCH_ORDER_DTL WHERE PURCHASE_ID = "+PURCHASE_ID+"");
    	return factory.update(sql.toString(), null);
    }
    
    /**
     * 
     * @date()2014年7月14日上午11:45:45
     * @author Administrator
     * @to_do:生成采购订单号
     * @return
     * @throws Exception
     */
    public String getPurchaseOrderNo(String num)throws Exception
    {
    	QuerySet qs = null;
    	String date = getDateToString();
    	if(date!=null){
			date = date.replaceAll("-", "");
			num+=date;
		}
    	StringBuffer sql = new StringBuffer();
		sql.append("SELECT max(").append("ORDER_NO").append(") as CG FROM ");
		sql.append( "PT_BU_PCH_ORDER").append(" t");
		sql.append(" where t.").append("ORDER_NO").append(" like '%").append(num).append("%'");
		qs = factory.select(null, sql.toString());
		if(qs.getRowCount()==0){
			 num+="0001";
		}else{
			    DataObjImpl dateObj = (DataObjImpl) qs.getDataObjs().get(0);
			 	String tem  = dateObj.getString("CG");

			if(tem==null||"null".equals(tem)){
				num+="0001";	
			}else{
				int sz = Integer.parseInt(tem.substring(tem.length()-4, tem.length()))+1;
				//如果1位数
				if(String.valueOf(sz).length()==1){
					num=tem.substring(0, tem.length()-4)+"000"+String.valueOf(sz);
				}
				//如果2位数
				else if(String.valueOf(sz).length()==2){
					num=tem.substring(0, tem.length()-4)+"00"+String.valueOf(sz);
				}
				//如果3位数
				else if(String.valueOf(sz).length()==3){
					num=tem.substring(0, tem.length()-4)+"0"+String.valueOf(sz);
				}
				//如果4位数
				else{
				num=tem.substring(0, tem.length()-4)+String.valueOf(sz);
				}
			}
		 }
		return num;
    }
    public static String getDateToString(){
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String d1 = formatter.format(date);
		if(null!=d1&&!"".equals(d1))
		return d1;
		else return null;
	}	
    /**
     * 
     * @date()2014年7月15日上午11:42:26
     * @author Administrator
     * @to_do:获取每个采购周期对应订单信息
     * @param PURCHASE_ID
     * @return
     * @throws Exception
     */
    public QuerySet getDelivery(String PURCHASE_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT DISTINCT (T.DELIVERY_CYCLE) DELIVERY_CYCLE,\n" );
    	sql.append("                T1.SELECT_MONTH,\n" );
    	sql.append("                T1.ORDER_NO,\n" );
    	sql.append("                T1.PURCHASE_TYPE,\n" );
    	sql.append("                T1.SUPPLIER_ID,\n" );
    	sql.append("                T1.SUPPLIER_NAME,\n" );
    	sql.append("                T1.SUPPLIER_CODE,\n" );
    	sql.append("                T1.ORDER_TYPE,\n" );
    	sql.append("                T1.ORDER_STATUS\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_DTL T, PT_BU_PCH_ORDER T1\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND T.PURCHASE_ID = T1.PURCHASE_ID\n" );
    	sql.append("   AND T.PURCHASE_ID = "+PURCHASE_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    
    /**
     * 
     * @date()2014年7月15日上午11:42:26
     * @author Administrator
     * @to_do:获取每个采购周期对应订单信息
     * @param PURCHASE_ID
     * @return
     * @throws Exception
     */
    public QuerySet getPlanPrice(String PART_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT NVL(PLAN_PRICE,0) PLAN_PRICE FROM PT_BA_INFO WHERE PART_ID = "+PART_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    /**
     * 
     * @date()2014年7月15日上午11:41:59
     * @author Administrator
     * @to_do:生成采购订单拆分单号
     * @param ORDER_NO
     * @return
     * @throws Exception
     */
    public String getSplitNo(String ORDER_NO)throws Exception
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
     * 
     * @date()2014年7月15日下午12:13:47
     * @author Administrator
     * @to_do:插入拆分单主表信息
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertSplitOrder(PtBuPchOrderSplitVO vo)
            throws Exception {
        return factory.insert(vo);
    }
    public boolean updatePurchaseOrderSplit(PtBuPchOrderSplitVO vo)
    		throws Exception
    {
    	return factory.update(vo);
    }
    /**
     * 
     * @date()2014年7月15日下午12:14:21
     * @author Administrator
     * @to_do:查询拆分单对应采购配件信息
     * @param PURCHASE_ID
     * @param DELIVERY_CYCLE
     * @return
     * @throws Exception
     */
    public QuerySet getSplitPart(String PURCHASE_ID,String DELIVERY_CYCLE) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_ID,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PCH_COUNT,\n" );
    	sql.append("       T.PCH_PRICE,\n" );
    	sql.append("       T.PCH_AMOUNT,\n" );
    	sql.append("       T.DELIVERY_CYCLE,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T1.IF_SUPLY\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_DTL T,PT_BA_INFO T1\n" );
    	sql.append(" WHERE T.DELIVERY_CYCLE = '"+DELIVERY_CYCLE+"'\n" );
    	sql.append(" AND T.PART_ID = T1.PART_ID");
    	sql.append("   AND T.PURCHASE_ID = "+PURCHASE_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    /**
     * 
     * @date()2014年7月15日下午12:14:42
     * @author Administrator
     * @to_do:将采购拆分单对平配件信息插入采购拆分单明细表
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertSplitOrderPart(PtBuPchOrderSplitDtlVO vo)
            throws Exception {
        return factory.insert(vo);
    }
    
    public QuerySet checkIfSQ(String SUPPLIER_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT 1\n" );
    	sql.append("  FROM PT_BA_SUPPLIER\n" );
    	sql.append(" WHERE SUPPLIER_CODE LIKE '%SQGH%'\n" );
    	sql.append("   AND SUPPLIER_ID = "+SUPPLIER_ID+" AND PART_IDENTIFY ="+DicConstant.YXBS_01+"\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    /**
     * 
     * @date()2014年7月21日下午2:30:39
     * @author Administrator
     * @to_do:导入数据
     * @param page
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet searchImport(PageManager page,User user)throws Exception{
 	   page.setFilter("");
 	   BaseResultSet bs = null;
 	   StringBuffer sql= new StringBuffer();
 	   sql.append("SELECT D.* \n" );
 	   sql.append("  FROM PT_BU_PCH_ORDER_PART_TMP D");
 	   sql.append(" WHERE D.USER_ACCOUNT ='"+user.getAccount()+"'");
 	   bs=factory.select(sql.toString(), page);
 	   return bs;
    }
    
    public QuerySet geOldtPartAmount(String DETAIL_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PCH_COUNT,\n" );
    	sql.append("       T.PCH_AMOUNT,\n" );
    	sql.append("       T.PLAN_AMOUNT,\n" );
    	sql.append("       T1.PURCHASE_AMOUNT P_PCH_AMOUNT,\n" );
    	sql.append("       T1.PURCHASE_COUNT  P_PURCHASE_COUNT,\n" );
    	sql.append("       T1.PLAN_AMOUNT     P_PLAN_AMOUNT,\n" );
    	sql.append("       T1.PURCHASE_ID\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_DTL T, PT_BU_PCH_ORDER T1\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND T.PURCHASE_ID = T1.PURCHASE_ID\n" );
    	sql.append("   AND DETAIL_ID = "+DETAIL_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    public QuerySet getTmpSup(String sup_no) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SUPPLIER_ID, T.SUPPLIER_NAME, T.SUPPLIER_CODE\n" );
    	sql.append("  FROM PT_BA_SUPPLIER T\n" );
    	sql.append(" WHERE T.SUPPLIER_CODE = '"+sup_no+"' AND T.PART_IDENTIFY ="+DicConstant.YXBS_01+"\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    public QuerySet checkPch(String PURCHASE_TYPE,String SUPPLIER_CODE,User user) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT 1\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER\n" );
    	sql.append(" WHERE PURCHASE_TYPE ="+PURCHASE_TYPE+"\n" );
    	sql.append("   AND SUPPLIER_CODE ='"+SUPPLIER_CODE+"'\n" );
    	sql.append("   AND APPLY_USER ='"+user.getAccount()+"'\n");
    	sql.append("   AND ORDER_STATUS ="+DicConstant.CGDDZT_01+"\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    public QuerySet getDic(String purchaseType) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT DIC_VALUE\n" );
    	sql.append("FROM DIC_TREE WHERE ID ="+purchaseType+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    public void updateOrderPart(String DETAIL_ID,String PURCHASE_ID,String COUNT,String REMARKS,User user) throws Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("UPDATE PT_BU_PCH_ORDER_DTL SET REMARKS='" + REMARKS + "',PCH_COUNT="+COUNT+",PCH_AMOUNT= PCH_PRICE*"+COUNT+",PLAN_AMOUNT= PLAN_PRICE*"+COUNT+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE DETAIL_ID ="+DETAIL_ID+" AND PURCHASE_ID ="+PURCHASE_ID+"\n");
       factory.update(sql.toString(),null);
    }
    public QuerySet getOld(String PURCHASE_ID,String DETAIL_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PCH_COUNT, T.PCH_PRICE, T.PCH_AMOUNT, T.PLAN_AMOUNT, NVL(T.PLAN_PRICE,0) PLAN_PRICE\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_DTL T\n" );
    	sql.append("  WHERE T.PURCHASE_ID ="+PURCHASE_ID+"\n" );
    	sql.append("  AND T.DETAIL_ID ="+DETAIL_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    public void updatePurAmount(String PURCHASE_ID,String PLAN_AMOUNT,String NEW_PLAN_AMOUNT,String PCH_AMOUNT,String NEW_PCH_AMOUNT,String PCH_COUNT,String COUNT,User user) throws Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("UPDATE PT_BU_PCH_ORDER SET PURCHASE_AMOUNT=PURCHASE_AMOUNT-"+PCH_AMOUNT+"+"+NEW_PCH_AMOUNT+",PLAN_AMOUNT = PLAN_AMOUNT-"+PLAN_AMOUNT+"+"+NEW_PLAN_AMOUNT+",PURCHASE_COUNT=PURCHASE_COUNT-"+PCH_COUNT+"+"+COUNT+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE PURCHASE_ID = "+PURCHASE_ID+"\n");
       factory.update(sql.toString(),null);
    }
    public QuerySet getPlanPrice(String PART_ID,String PURCHASE_ID) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT NVL(T.PLAN_PRICE,0) PLAN_PRICE FROM PT_BA_PART_SUPPLIER_RL T, PT_BU_PCH_ORDER T1\n" );
    	sql.append("WHERE T1.SUPPLIER_ID = T.SUPPLIER_ID AND T.PART_IDENTIFY = "+DicConstant.YXBS_01+" AND T.PART_ID = "+PART_ID+" AND T1.PURCHASE_ID = "+PURCHASE_ID+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    public boolean inserShipLog(PtBuPchOrderShippingVO vo)
            throws Exception {
        return factory.insert(vo);
    }
    public QuerySet getDeliveryCyclNum(String DELIVERY_CYCLE) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT paravalue2 from user_para_configure where apptype ='3001' and paravalue1='"+DELIVERY_CYCLE+"'\n" );
    	qs = factory.select(null, sql.toString());
    	return qs;
    }

    public boolean deleteSplitDtl(String PURCHASE_ID ) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("DELETE FROM PT_BU_PCH_ORDER_SPLIT_DTL WHERE SPLIT_ID IN(SELECT SPLIT_ID FROM PT_BU_PCH_ORDER_SPLIT WHERE PURCHASE_ID = "+PURCHASE_ID+") ");
    	return factory.update(sql.toString(), null);
    }
    public boolean deleteSplit(String PURCHASE_ID ) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("DELETE FROM PT_BU_PCH_ORDER_SPLIT WHERE PURCHASE_ID = "+PURCHASE_ID+"");
    	return factory.update(sql.toString(), null);
    }
    public QuerySet dtlDownload(String PURCHASE_ID) throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T2.DIC_VALUE AS UNIT,\n" );
    	sql.append("       T1.MIN_PACK || '/' || T2.DIC_VALUE AS MIN_UNIT,\n" );
    	sql.append("       T.PCH_PRICE,\n" );
    	sql.append("       T.DELIVERY_CYCLE,\n" );
    	sql.append("       T.PCH_COUNT,\n" );
    	sql.append("       T.PCH_AMOUNT,\n" );
    	sql.append("       T.REMARKS\n" );
    	sql.append("  FROM PT_BU_PCH_ORDER_DTL T, PT_BA_INFO T1, DIC_TREE T2\n" );
    	sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
    	sql.append("   AND T1.UNIT = T2.ID AND T.PURCHASE_ID = "+PURCHASE_ID+"");
    	return factory.select(null, sql.toString());
    }
}
