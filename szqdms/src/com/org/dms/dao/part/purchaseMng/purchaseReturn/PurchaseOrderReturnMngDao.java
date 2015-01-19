package com.org.dms.dao.part.purchaseMng.purchaseReturn;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPchReturnDtlVO;
import com.org.dms.vo.part.PtBuPchReturnVO;
import com.org.dms.vo.part.PtBuStockDtlVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.common.dataset.DataObjImpl;
import com.org.mvc.context.ActionContext;

public class PurchaseOrderReturnMngDao extends BaseDAO{
	 public  static final PurchaseOrderReturnMngDao getInstance(ActionContext atx)
	    {
		 PurchaseOrderReturnMngDao dao = new PurchaseOrderReturnMngDao();
	        atx.setDBFactory(dao.factory);
	        return dao;
	    }
	 /**
	  * 
	  * @date()2014年7月22日下午5:12:54
	  * @author Administrator
	  * @to_do:采购退货单查询
	  * @param page
	  * @param user
	  * @param conditions
	  * @return
	  * @throws Exception
	  */
	 public BaseResultSet returnOrderSearch(PageManager page, User user, String conditions) throws Exception
	    {
	    	String wheres = conditions;
	    	wheres +="AND T.STATUS = "+DicConstant.YXBS_01+"\n" +
	    					"AND T.RETURN_ID = T1.RETURN_ID(+)"+"\n"+
	    					"AND T.CREATE_USER = '"+user.getAccount()+"'"+"\n"+
	    					"AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
	    					"AND T.COMPANY_ID = "+user.getCompanyId()+"\n" + 
	    					"AND T.ORG_ID = "+user.getOrgId()+"\n" + 
	    					"AND T.RETURN_STATUS = "+DicConstant.CGTHDZT_01+"\n" ;
	        page.setFilter(wheres);
	    	BaseResultSet bs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.RETURN_ID,\n" );
	    	sql.append("       T.RETURN_NO,\n" );
	    	sql.append("       (SELECT T.WAREHOUSE_ID FROM PT_BA_WAREHOUSE T WHERE T.WAREHOUSE_TYPE = 100101 AND T.WAREHOUSE_STATUS = "+DicConstant.YXBS_01+") WAREHOUSE_ID,\n" );
	    	sql.append("       T.SUPPLIER_CODE,\n" );
	    	sql.append("       T.SUPPLIER_NAME,\n" );
	    	sql.append("	   T.SUPPLIER_ID,");
	    	sql.append("       T.RETURN_TYPE,\n" );
	    	sql.append("       T.ORDER_DATE,\n" );
	    	sql.append("       T.ORDER_USER,\n" );
	    	sql.append("       T1.COUNT\n" );
	    	sql.append("  FROM PT_BU_PCH_RETURN T,(SELECT COUNT(1) COUNT,RETURN_ID FROM PT_BU_PCH_RETURN_DTL GROUP BY RETURN_ID) T1\n");
	    	bs = factory.select(sql.toString(), page);
			bs.setFieldDic("RETURN_TYPE", "CGTHLX");
			bs.setFieldDateFormat("ORDER_DATE", "yyyy-MM-dd");
			bs.setFieldUserID("ORDER_USER");
	    	return bs;
	    }
	 
	 public BaseResultSet purchaseSearch(PageManager page, User user, String conditions,String SUPPLIER_ID) throws Exception
	    {
	    	String wheres = conditions;
	    	wheres +="AND T.SUPPLIER_ID = T1.SUPPLIER_ID AND T1.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" +
	    					"   AND T.SUPPLIER_ID = "+SUPPLIER_ID+"\n" + 
	    					"   AND T.ORG_ID = "+user.getOrgId()+"\n" + 
	    					"   AND T.COMPANY_ID = "+user.getCompanyId()+"\n" + 
	    					"   AND T.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n" + 
	    					"   AND T.ORDER_STATUS IN("+DicConstant.CGDDZT_04+","+DicConstant.CGDDZT_05+")"+
							"AND EXISTS (SELECT 1\n" +
							"       FROM PT_BU_PCH_ORDER_SPLIT_DTL T2\n" + 
							"      WHERE T2.STORAGE_COUNT IS NOT NULL\n" + 
							"        AND T.SPLIT_ID = T2.SPLIT_ID)"+
	    					"   ORDER BY T.SPLIT_ID DESC";
	        page.setFilter(wheres);
	    	BaseResultSet bs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.SPLIT_ID,\n" );
	    	sql.append("       T.PURCHASE_ID,\n" );
	    	sql.append("       T.SPLIT_NO,\n" );
	    	sql.append("       T.SELECT_MONTH,\n" );
	    	sql.append("       T.PURCHASE_TYPE,\n" );
	    	sql.append("       T.APPLY_USER,\n" );
	    	sql.append("       T.APPLY_DATE,\n" );
	    	sql.append("       T.SUPPLIER_ID,\n" );
	    	sql.append("       T.SUPPLIER_NAME,\n" );
	    	sql.append("       T.SUPPLIER_CODE\n" );
	    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT T, PT_BA_SUPPLIER T1");
	    	bs = factory.select(sql.toString(), page);
			bs.setFieldDic("PURCHASE_TYPE", DicConstant.CGDDLX);
			bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
			bs.setFieldUserID("APPLY_USER");
	    	return bs;
	    }
	 /**
	  * 
	  * @date()2014年7月23日下午7:48:12
	  * @author Administrator
	  * @to_do:生成退货单号
	  * @param type
	  * @return
	  * @throws Exception
	  */
	 public String getPurchaseReturnOrderNo(String type)throws Exception
	    {
	    	QuerySet qs = null;
	    	String date = getDateToString();
	    	if(date!=null){
				date = date.replaceAll("-", "");
				type+=date;
			}
	    	StringBuffer sql = new StringBuffer();
			sql.append("SELECT max(").append("RETURN_NO").append(") as TH FROM ");
			sql.append( "PT_BU_PCH_RETURN").append(" t");
			sql.append(" where t.").append("RETURN_NO").append(" like '%").append(type).append("%'");
			qs = factory.select(null, sql.toString());
			if(qs.getRowCount()==0){
				type+="0001";
			}else{
				    DataObjImpl dateObj = (DataObjImpl) qs.getDataObjs().get(0);
				 	String tem  = dateObj.getString("TH");

				if(tem==null||"null".equals(tem)){
					type+="0001";	
				}else{
					int sz = Integer.parseInt(tem.substring(tem.length()-4, tem.length()))+1;
					//如果1位数
					if(String.valueOf(sz).length()==1){
						type=tem.substring(0, tem.length()-4)+"000"+String.valueOf(sz);
					}
					//如果2位数
					else if(String.valueOf(sz).length()==2){
						type=tem.substring(0, tem.length()-4)+"00"+String.valueOf(sz);
					}
					//如果3位数
					else if(String.valueOf(sz).length()==3){
						type=tem.substring(0, tem.length()-4)+"0"+String.valueOf(sz);
					}
					//如果4位数
					else{
						type=tem.substring(0, tem.length()-4)+String.valueOf(sz);
					}
				}
			 }
			return type;
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
	  * @date()2014年7月23日下午7:48:27
	  * @author Administrator
	  * @to_do:退货单新增
	  * @param vo
	  * @return
	  * @throws Exception
	  */
	 public boolean insertReturnOrder(PtBuPchReturnVO vo)
	            throws Exception
	    {
	    	return factory.insert(vo);
	    }
	 /**
	  * 
	  * @date()2014年7月23日下午8:49:44
	  * @author Administrator
	  * @to_do:退货单修改
	  * @param vo
	  * @return
	  * @throws Exception
	  */
	 public boolean purchaseReturnOrderUpdate(PtBuPchReturnVO vo)
	    		throws Exception
	    {
	    	return factory.update(vo);
	    }
	 /**
	  * @to_do:库存明细表修改
	  */
	 public boolean updateStockdtl(PtBuStockDtlVO vo)throws Exception{
		 return factory.update(vo);
	}
	 /**
	  * 
	  * @date()2014年7月23日下午8:49:56
	  * @author Administrator
	  * @to_do:退货单配件查询
	  * @param page
	  * @param user
	  * @param PURCHASE_ID
	  * @return
	  * @throws Exception
	  */
	 public BaseResultSet returnPartSearch(PageManager page, User user, String RETURN_ID,String SUPPLIER_ID,String WAREHOUSE_ID) throws Exception
	    {
	    	BaseResultSet bs = null;
//	    	StringBuffer sql= new StringBuffer();
//	    	sql.append("SELECT T1.PART_ID,\n" );
//	    	sql.append("       T1.PART_CODE,\n" );
//	    	sql.append("       T1.PART_NAME,\n" );
//	    	sql.append("       T1.UNIT,\n" );
//	    	sql.append("       T1.MIN_PACK,\n" );
//	    	sql.append("       T1.MIN_UNIT,\n" );
//	    	sql.append("       T1.PCH_PRICE,\n" );
//	    	sql.append("       T1.PLAN_PRICE,\n" );
//	    	sql.append("       PD.REMARKS,\n" );
//	    	sql.append("       WM_CONCAT(PD.DETAIL_ID)DETAIL_ID,\n" );
//	    	sql.append("       WM_CONCAT(NVL(PD.RETURN_AMOUNT, 0)) YRETURN_AMOUNT,\n" );
//	    	sql.append("       NVL(T1.STORAGE_COUNT, 0) STORAGE_COUNT,\n" );
//	    	sql.append("       NVL(T1.RETURN_AMOUNT, 0) RETURN_AMOUNT,\n" );
//	    	sql.append("       NVL(T1.STORAGE_COUNT, 0) - NVL(T1.RETURN_AMOUNT, 0) SL,\n" );
//	    	sql.append("       WM_CONCAT(T1.AVAILABLE_AMOUNT) AVAILABLE_AMOUNT,\n" );
//	    	sql.append("       wm_concat(T1.POSITION_ID) POSITION_IDS,\n" );
//	    	sql.append("       wm_concat(T1.POSITION_CODE) POSITION_CODES,\n" );
//	    	sql.append("       wm_concat(T1.POSITION_NAME) POSITION_NAMES\n" );
//	    	sql.append("  FROM PT_BU_PCH_RETURN_DTL PD,\n" );
//	    	sql.append("       (SELECT T1.*, T2.RETURN_AMOUNT\n" );
//	    	sql.append("          FROM (SELECT B.SUPPLIER_ID,\n" );
//	    	sql.append("                       D.PART_ID,\n" );
//	    	sql.append("                       D.PART_CODE,\n" );
//	    	sql.append("                       D.PART_NAME,\n" );
//	    	sql.append("                       D.UNIT,\n" );
//	    	sql.append("                       D.MIN_PACK,\n" );
//	    	sql.append("                       D.MIN_UNIT,\n" );
//	    	sql.append("                       D.PART_NO,\n" );
//	    	sql.append("                       E.PCH_PRICE,\n" );
//	    	sql.append("                       D.PLAN_PRICE,\n" );
//	    	sql.append("                       NVL(G.AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT,\n" );
//	    	sql.append("                       G.POSITION_ID,\n" );
//	    	sql.append("                       G.POSITION_CODE,\n" );
//	    	sql.append("                       G.POSITION_NAME,\n" );
//	    	sql.append("                       SUM(NVL(B.STORAGE_COUNT, 0)) STORAGE_COUNT\n" );
//	    	sql.append("                  FROM PT_BU_PCH_ORDER_SPLIT     A,\n" );
//	    	sql.append("                       PT_BU_PCH_ORDER_SPLIT_DTL B,\n" );
//	    	sql.append("                       PT_BA_PCH_ATTRIBUTE       C,\n" );
//	    	sql.append("                       PT_BA_INFO                D,\n" );
//	    	sql.append("                       PT_BA_PART_SUPPLIER_RL    E,\n" );
//	    	sql.append("                       PT_BA_WAREHOUSE_PART_RL   F,\n" );
//	    	sql.append("                       PT_BU_STOCK_DTL           G\n" );
//	    	sql.append("                 WHERE A.SPLIT_ID = B.SPLIT_ID\n" );
//	    	sql.append("                   AND B.PART_ID = C.PART_ID\n" );
//	    	sql.append("                   AND B.PART_ID = D.PART_ID\n" );
//	    	sql.append("                   AND B.PART_ID = E.PART_ID\n" );
//	    	sql.append("                   AND F.PART_ID = B.PART_ID\n" );
//	    	sql.append("                   AND G.PART_ID = D.PART_ID\n" );
//	    	sql.append("                   AND G.POSITION_ID = F.POSITION_ID\n" );
//	    	sql.append("                   AND F.POSITION_ID IN\n" );
//	    	sql.append("                       (SELECT S.POSITION_ID\n" );
//	    	sql.append("                          FROM PT_BA_WAREHOUSE_POSITION S,\n" );
//	    	sql.append("                               PT_BA_WAREHOUSE_AREA     M,\n" );
//	    	sql.append("                               PT_BA_WAREHOUSE          N\n" );
//	    	sql.append("                         WHERE S.AREA_ID = M.AREA_ID\n" );
//	    	sql.append("                           AND M.WAREHOUSE_ID = N.WAREHOUSE_ID\n" );
//	    	sql.append("                           AND N.WAREHOUSE_ID = "+WAREHOUSE_ID+")\n" );
//	    	sql.append("                   AND A.SUPPLIER_ID = E.SUPPLIER_ID AND E.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
//	    	sql.append("                   AND B.SUPPLIER_ID = G.SUPPLIER_ID\n" );
//	    	sql.append("                   AND A.SUPPLIER_ID = "+SUPPLIER_ID+"\n" );
//	    	sql.append("                   AND B.STORAGE_COUNT > 0\n" );
//	    	sql.append("                 GROUP BY B.SUPPLIER_ID,\n" );
//	    	sql.append("                          G.AVAILABLE_AMOUNT,\n" );
//	    	sql.append("                          D.PART_ID,\n" );
//	    	sql.append("                          D.PART_CODE,\n" );
//	    	sql.append("                          D.PART_NAME,\n" );
//	    	sql.append("                          D.PART_NO,\n" );
//	    	sql.append("                          D.UNIT,\n" );
//	    	sql.append("                          D.MIN_PACK,\n" );
//	    	sql.append("                          D.MIN_UNIT,\n" );
//	    	sql.append("                          E.PCH_PRICE,\n" );
//	    	sql.append("                          D.PLAN_PRICE,\n" );
//	    	sql.append("                          G.POSITION_ID,\n" );
//	    	sql.append("                          G.POSITION_CODE,\n" );
//	    	sql.append("                          G.POSITION_NAME) T1\n" );
//	    	sql.append("          LEFT JOIN (SELECT G.SUPPLIER_ID,\n" );
//	    	sql.append("                           G.PART_ID,\n" );
//	    	sql.append("                           SUM(NVL(G.RETURN_AMOUNT, 0)) RETURN_AMOUNT\n" );
//	    	sql.append("                      FROM PT_BU_PCH_RETURN F, PT_BU_PCH_RETURN_DTL G\n" );
//	    	sql.append("                     WHERE F.RETURN_ID = G.RETURN_ID\n" );
//	    	sql.append("                       AND F.SUPPLIER_ID = "+SUPPLIER_ID+"\n" );
//	    	sql.append("                       AND G.RETURN_AMOUNT > 0\n" );
//	    	sql.append("                       AND F.RETURN_STATUS <> 201101\n" );
//	    	sql.append("                     GROUP BY G.SUPPLIER_ID, G.PART_ID) T2\n" );
//	    	sql.append("            ON T1.PART_ID = T2.PART_ID\n" );
//	    	sql.append("           AND T1.SUPPLIER_ID = T2.SUPPLIER_ID\n" );
//	    	sql.append("           AND T1.STORAGE_COUNT - T2.RETURN_AMOUNT > 0) T1\n" );
//	    	sql.append(" WHERE PD.PART_ID = T1.PART_ID\n" );
//	    	sql.append("   AND PD.SUPPLIER_ID = T1.SUPPLIER_ID\n" );
//	    	sql.append("   AND T1.POSITION_ID = PD.POSITION_ID\n" );
//	    	sql.append("   AND PD.RETURN_ID = "+RETURN_ID+"\n" );
//	    	sql.append(" GROUP BY T1.PART_ID,\n" );
//	    	sql.append("          T1.PART_CODE,\n" );
//	    	sql.append("          T1.PART_NAME,\n" );
//	    	sql.append("          T1.UNIT,\n" );
//	    	sql.append("          T1.MIN_PACK,\n" );
//	    	sql.append("          T1.MIN_UNIT,\n" );
//	    	sql.append("          T1.PCH_PRICE,\n" );
//	    	sql.append("          T1.PLAN_PRICE,\n" );
//	    	sql.append("          PD.REMARKS,\n" );
//	    	sql.append("          T1.STORAGE_COUNT,\n" );
//	    	sql.append("          T1.RETURN_AMOUNT");
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T1.PART_ID,\n" );
	    	sql.append("       T1.PART_CODE,\n" );
	    	sql.append("       T1.PART_NAME,\n" );
	    	sql.append("       T1.UNIT,\n" );
	    	sql.append("       T1.MIN_PACK,\n" );
	    	sql.append("       T1.MIN_UNIT,\n" );
	    	sql.append("       T1.PCH_PRICE,\n" );
	    	sql.append("       T1.PLAN_PRICE,\n" );
	    	sql.append("       PD.REMARKS,\n" );
	    	sql.append("       WM_CONCAT(PD.DETAIL_ID)DETAIL_ID,\n" );
	    	sql.append("       WM_CONCAT(NVL(PD.RETURN_AMOUNT, 0)) YRETURN_AMOUNT,\n" );
	    	sql.append("       NVL(T1.RETURN_AMOUNT, 0) RETURN_AMOUNT,\n" );
	    	sql.append("       WM_CONCAT(T1.AVAILABLE_AMOUNT) AVAILABLE_AMOUNT,\n" );
	    	sql.append("       wm_concat(T1.POSITION_ID) POSITION_IDS,\n" );
	    	sql.append("       wm_concat(T1.POSITION_CODE) POSITION_CODES,\n" );
	    	sql.append("       wm_concat(T1.POSITION_NAME) POSITION_NAMES,\n" );
	    	sql.append("       TX.AVAILABLE_AMOUNT SL\n" );
	    	sql.append("  FROM PT_BU_PCH_RETURN_DTL PD,PT_BU_STOCK TX,\n" );
	    	sql.append("       (SELECT T1.*, T2.RETURN_AMOUNT\n" );
	    	sql.append("          FROM (SELECT E.SUPPLIER_ID,\n" );
	    	sql.append("                       D.PART_ID,\n" );
	    	sql.append("                       D.PART_CODE,\n" );
	    	sql.append("                       D.PART_NAME,\n" );
	    	sql.append("                       D.UNIT,\n" );
	    	sql.append("                       D.MIN_PACK,\n" );
	    	sql.append("                       D.MIN_UNIT,\n" );
	    	sql.append("                       D.PART_NO,\n" );
	    	sql.append("                       E.PCH_PRICE,\n" );
	    	sql.append("                       D.PLAN_PRICE,\n" );
	    	sql.append("                       NVL(G.AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT,\n" );
	    	sql.append("                       G.POSITION_ID,\n" );
	    	sql.append("                       G.POSITION_CODE,\n" );
	    	sql.append("                       G.POSITION_NAME\n" );
	    	sql.append("                  FROM PT_BA_PCH_ATTRIBUTE       C,\n" );
	    	sql.append("                       PT_BA_INFO                D,\n" );
	    	sql.append("                       PT_BA_PART_SUPPLIER_RL    E,\n" );
	    	sql.append("                       PT_BA_WAREHOUSE_PART_RL   F,\n" );
	    	sql.append("                       PT_BU_STOCK_DTL           G\n" );
	    	sql.append("                 WHERE D.PART_ID = C.PART_ID\n" );
	    	sql.append("                   AND D.PART_ID = E.PART_ID\n" );
	    	sql.append("                   AND F.PART_ID = D.PART_ID\n" );
	    	sql.append("                   AND G.PART_ID = D.PART_ID\n" );
	    	sql.append("                   AND G.POSITION_ID = F.POSITION_ID\n" );
	    	sql.append("                   AND F.POSITION_ID IN\n" );
	    	sql.append("                       (SELECT S.POSITION_ID\n" );
	    	sql.append("                          FROM PT_BA_WAREHOUSE_POSITION S,\n" );
	    	sql.append("                               PT_BA_WAREHOUSE_AREA     M,\n" );
	    	sql.append("                               PT_BA_WAREHOUSE          N\n" );
	    	sql.append("                         WHERE S.AREA_ID = M.AREA_ID\n" );
	    	sql.append("                           AND M.WAREHOUSE_ID = N.WAREHOUSE_ID\n" );
	    	sql.append("                           AND N.WAREHOUSE_ID = "+WAREHOUSE_ID+")\n" );
	    	sql.append("                   AND E.SUPPLIER_ID = G.SUPPLIER_ID\n" );
	    	sql.append("                 GROUP BY E.SUPPLIER_ID,\n" );
	    	sql.append("                          G.AVAILABLE_AMOUNT,\n" );
	    	sql.append("                          D.PART_ID,\n" );
	    	sql.append("                          D.PART_CODE,\n" );
	    	sql.append("                          D.PART_NAME,\n" );
	    	sql.append("                          D.PART_NO,\n" );
	    	sql.append("                          D.UNIT,\n" );
	    	sql.append("                          D.MIN_PACK,\n" );
	    	sql.append("                          D.MIN_UNIT,\n" );
	    	sql.append("                          E.PCH_PRICE,\n" );
	    	sql.append("                          D.PLAN_PRICE,\n" );
	    	sql.append("                          G.POSITION_ID,\n" );
	    	sql.append("                          G.POSITION_CODE,\n" );
	    	sql.append("                          G.POSITION_NAME) T1\n" );
	    	sql.append("          LEFT JOIN (SELECT G.SUPPLIER_ID,\n" );
	    	sql.append("                           G.PART_ID,\n" );
	    	sql.append("                           SUM(NVL(G.RETURN_AMOUNT, 0)) RETURN_AMOUNT\n" );
	    	sql.append("                      FROM PT_BU_PCH_RETURN F, PT_BU_PCH_RETURN_DTL G\n" );
	    	sql.append("                     WHERE F.RETURN_ID = G.RETURN_ID\n" );
	    	sql.append("                       AND F.SUPPLIER_ID = "+RETURN_ID+"\n" );
	    	sql.append("                       AND G.RETURN_AMOUNT > 0\n" );
	    	sql.append("                       AND F.RETURN_STATUS <> "+DicConstant.CGTHDZT_01+"\n" );
	    	sql.append("                     GROUP BY G.SUPPLIER_ID, G.PART_ID) T2\n" );
	    	sql.append("            ON T1.PART_ID = T2.PART_ID\n" );
	    	sql.append("           AND T1.SUPPLIER_ID = T2.SUPPLIER_ID) T1\n" );
	    	sql.append(" WHERE PD.PART_ID = T1.PART_ID\n" );
	    	sql.append("   AND PD.PART_ID = TX.PART_ID\n" );
	    	sql.append("   AND TX.WAREHOUSE_ID = "+WAREHOUSE_ID+"\n" );
	    	sql.append("   AND PD.RETURN_ID = "+RETURN_ID+"\n" );
	    	sql.append(" GROUP BY T1.PART_ID,\n" );
	    	sql.append("          T1.PART_CODE,\n" );
	    	sql.append("          T1.PART_NAME,\n" );
	    	sql.append("          T1.UNIT,\n" );
	    	sql.append("          T1.MIN_PACK,\n" );
	    	sql.append("          T1.MIN_UNIT,\n" );
	    	sql.append("          T1.PCH_PRICE,\n" );
	    	sql.append("          T1.PLAN_PRICE,\n" );
	    	sql.append("          PD.REMARKS,\n" );
	    	sql.append("          T1.RETURN_AMOUNT,");
	    	sql.append("          TX.AVAILABLE_AMOUNT");
	    	bs = factory.select(sql.toString(), page);
	    	bs.setFieldDic("UNIT", "JLDW");
	    	bs.setFieldDic("MIN_UNIT", "JLDW");
	    	return bs;
	    }
	 /**
	  * 
	  * @date()2014年7月28日上午10:06:32
	  * @author Administrator
	  * @to_do:待退货配件查询
	  * @param page
	  * @param user
	  * @param conditions
	  * @param SPLIT_ID
	  * @param RETURN_ID
	  * @return
	  * @throws Exception
	  */
	 public BaseResultSet searchPart(PageManager page, User user, String conditions,String SUPPLIER_ID,String RETURN_ID,String WAREHOUSE_ID) throws Exception {

	        BaseResultSet bs = null;
	        String wheres = conditions;
	        wheres +="AND T.PART_ID = T1.PART_ID\n" +
	        				"   AND T.PART_ID = T2.PART_ID\n" + 
	        				"   AND T.PART_ID = T3.PART_ID\n" + 
	        				"   AND T.AVAILABLE_AMOUNT > 0\n" + 
	        				"   AND T.WAREHOUSE_ID = "+WAREHOUSE_ID+"\n" + 
	        				"   AND T2.SUPPLIER_ID = "+SUPPLIER_ID+"\n" + 
	        				"   AND T3.USER_ACCOUNT = '"+user.getAccount()+"'\n" + 
	        				"   AND T2.PART_IDENTIFY = "+DicConstant.YXBS_01+""+
	        				" AND NOT EXISTS (SELECT 1 FROM PT_BU_PCH_RETURN_DTL PD WHERE PD.PART_ID = T1.PART_ID AND PD.RETURN_ID ="+RETURN_ID+")\n" +
	        				" ORDER BY T1.PART_ID";
	        page.setFilter(wheres);
//	        StringBuffer sql= new StringBuffer();
//	        sql.append("SELECT T1.PART_ID,\n" );
//	        sql.append("       T1.PART_CODE,\n" );
//	        sql.append("       T1.PART_NAME,\n" );
//	        sql.append("       T1.SUPPLIER_ID,\n" );
//	        sql.append("       T1.UNIT,\n" );
//	        sql.append("       T1.MIN_PACK,\n" );
//	        sql.append("       T1.MIN_UNIT,\n" );
//	        sql.append("       T1.PCH_PRICE,\n" );
//	        sql.append("       T1.PLAN_PRICE\n" );
//	        sql.append("  FROM (SELECT B.SUPPLIER_ID,\n" );
//	        sql.append("               B.PART_ID,\n" );
//	        sql.append("               B.PART_CODE,\n" );
//	        sql.append("               B.PART_NAME,\n" );
//	        sql.append("               D.UNIT,\n" );
//	        sql.append("               D.MIN_PACK,\n" );
//	        sql.append("               D.MIN_UNIT,\n" );
//	        sql.append("               E.PCH_PRICE,\n" );
//	        sql.append("               D.PLAN_PRICE,\n" );
//	        sql.append("               SUM(NVL(B.STORAGE_COUNT, 0)) STORAGE_COUNT\n" );
//	        sql.append("          FROM PT_BU_PCH_ORDER_SPLIT     A,\n" );
//	        sql.append("               PT_BU_PCH_ORDER_SPLIT_DTL B,\n" );
//	        sql.append("               PT_BA_PCH_ATTRIBUTE       C,\n" );
//	        sql.append("               PT_BA_INFO                D,\n" );
//	        sql.append("               PT_BA_PART_SUPPLIER_RL    E\n" );
//	        sql.append("         WHERE A.SPLIT_ID = B.SPLIT_ID\n" );
//	        sql.append("           AND B.PART_ID = C.PART_ID\n" );
//	        sql.append("           AND B.PART_ID = D.PART_ID\n" );
//	        sql.append("           AND B.PART_ID = E.PART_ID\n" );
//	        sql.append("           AND A.SUPPLIER_ID = E.SUPPLIER_ID AND E.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
//	        sql.append("           AND A.SUPPLIER_ID = "+SUPPLIER_ID+"\n" );
//	        sql.append("           AND B.STORAGE_COUNT > 0\n" );
//	        sql.append("         GROUP BY B.SUPPLIER_ID,\n" );
//	        sql.append("                  B.PART_ID,\n" );
//	        sql.append("                  B.PART_CODE,\n" );
//	        sql.append("                  B.PART_NAME,\n" );
//	        sql.append("                  D.UNIT,\n" );
//	        sql.append("                  D.MIN_PACK,\n" );
//	        sql.append("                  D.MIN_UNIT,\n" );
//	        sql.append("                  E.PCH_PRICE,\n" );
//	        sql.append("                  D.PLAN_PRICE) T1\n" );
//	        sql.append("  LEFT JOIN (SELECT G.SUPPLIER_ID,\n" );
//	        sql.append("                    G.PART_ID,\n" );
//	        sql.append("                    SUM(NVL(G.RETURN_AMOUNT, 0)) RETURN_AMOUNT\n" );
//	        sql.append("               FROM PT_BU_PCH_RETURN F, PT_BU_PCH_RETURN_DTL G\n" );
//	        sql.append("              WHERE F.RETURN_ID = G.RETURN_ID\n" );
//	        sql.append("                AND F.SUPPLIER_ID = "+SUPPLIER_ID+"\n" );
//	        sql.append("                AND G.RETURN_AMOUNT > 0\n" );
//	        sql.append("                AND F.RETURN_STATUS <> 201101\n" );
//	        sql.append("              GROUP BY G.SUPPLIER_ID, G.PART_ID) T2\n" );
//	        sql.append("    ON T1.PART_ID = T2.PART_ID\n" );
//	        sql.append("   AND T1.SUPPLIER_ID = T2.SUPPLIER_ID\n" );
//	        sql.append("   AND T1.STORAGE_COUNT - T2.RETURN_AMOUNT > 0");
	        StringBuffer sql= new StringBuffer();
	        sql.append("SELECT T.PART_ID,\n" );
	        sql.append("       T1.PART_CODE,\n" );
	        sql.append("       T1.PART_NAME,\n" );
	        sql.append("       T1.UNIT,\n" );
	        sql.append("       T1.MIN_UNIT,\n" );
	        sql.append("       T1.MIN_PACK,\n" );
	        sql.append("       T1.PLAN_PRICE,\n" );
	        sql.append("       T2.SUPPLIER_ID,\n" );
	        sql.append("       T2.PCH_PRICE\n" );
	        sql.append("  FROM PT_BU_STOCK            T,\n" );
	        sql.append("       PT_BA_INFO             T1,\n" );
	        sql.append("       PT_BA_PART_SUPPLIER_RL T2,\n" );
	        sql.append("       PT_BA_PCH_ATTRIBUTE    T3\n" );
//	        sql.append(" WHERE T.PART_ID = T1.PART_ID\n" );
//	        sql.append("   AND T.PART_ID = T2.PART_ID\n" );
//	        sql.append("   AND T.PART_ID = T3.PART_ID\n" );
//	        sql.append("   AND T.AVAILABLE_AMOUNT > 0\n" );
//	        sql.append("   AND T.WAREHOUSE_ID = "+WAREHOUSE_ID+"\n" );
//	        sql.append("   AND T2.SUPPLIER_ID = "+SUPPLIER_ID+"\n" );
//	        sql.append("   AND T3.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
//	        sql.append("   AND T2.PART_IDENTIFY = "+DicConstant.YXBS_01+"");
	        bs = factory.select(sql.toString(), page);
	        
	        bs.setFieldDic("UNIT", "JLDW");
	    	bs.setFieldDic("MIN_UNIT", "JLDW");
	        return bs;
	    }
		/**
		 * insertParts
		 * 新增退货明细表（附带库位信息）
		 */
		public boolean insertParts(String detailId,String positionId,User user,String retCount,String remark)throws Exception{
			StringBuffer sql= new StringBuffer();
			sql.append("INSERT INTO PT_BU_PCH_RETURN_DTL\n" );
			sql.append("  (DETAIL_ID,\n" );
			sql.append("   PART_ID,\n" );
			sql.append("   PART_CODE,\n" );
			sql.append("   PART_NAME,\n" );
			sql.append("   RETURN_ID,\n" );
			sql.append("   RETURN_AMOUNT,\n" );
			sql.append("   REMARKS,\n" );
			sql.append("   CREATE_USER,\n" );
			sql.append("   CREATE_TIME,\n" );
			sql.append("   SUPPLIER_ID,\n" );
			sql.append("   SUPPLIER_CODE,\n" );
			sql.append("   SUPPLIER_NAME,\n" );
			sql.append("   PCH_PRICE,\n" );
			sql.append("   PLAN_PRICE,\n" );
			sql.append("   POSITION_ID,\n" );
			sql.append("   POSITION_CODE,\n" );
			sql.append("   POSITION_NAME,\n" );
			sql.append("   COUNT,\n" );
			sql.append("   AMOUNT,\n" );
			sql.append("   PLAN_AMOUNT)\n" );
			sql.append("  SELECT F_GETID(),\n" );
			sql.append("         D.PART_ID,\n" );
			sql.append("         D.PART_CODE,\n" );
			sql.append("         D.PART_NAME,\n" );
			sql.append("         D.RETURN_ID,\n" );
			sql.append("         "+retCount+",\n" );
			sql.append("         '"+remark+"',\n" );
			sql.append("         '"+user.getAccount()+"',\n" );
			sql.append("         SYSDATE,\n" );
			sql.append("         D.SUPPLIER_ID,\n" );
			sql.append("         D.SUPPLIER_CODE,\n" );
			sql.append("         D.SUPPLIER_NAME,\n" );
			sql.append("         D.PCH_PRICE,\n" );
			sql.append("         D.PLAN_PRICE,\n" );
			sql.append("         "+positionId+",\n" );
			sql.append("         (SELECT T.POSITION_CODE FROM PT_BA_WAREHOUSE_POSITION T WHERE T.POSITION_ID = "+positionId+"),\n" );
			sql.append("         (SELECT T.POSITION_NAME FROM PT_BA_WAREHOUSE_POSITION T WHERE T.POSITION_ID = "+positionId+"),\n" );
			sql.append("         "+retCount+",\n" );
			sql.append("         "+retCount+"*NVL(D.PCH_PRICE,0),\n" );
			sql.append("         "+retCount+"*NVL(D.PLAN_PRICE,0)\n" );
			sql.append("    FROM PT_BU_PCH_RETURN_DTL D\n" );
			sql.append("   WHERE D.DETAIL_ID ="+detailId+"");
			return factory.update(sql.toString(),null);
		    }
		/**
		 * deleteParts
		 * 删除多余退货明细表
		 */
		public boolean deleteParts(String detailId)throws Exception{
			StringBuffer sql= new StringBuffer();
			sql.append("DELETE FROM PT_BU_PCH_RETURN_DTL T WHERE T.DETAIL_ID IN("+detailId+")" );
			return factory.update(sql.toString(),null);
		}
	 public QuerySet checkPart(String RETURN_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT 1 FROM PT_BU_PCH_RETURN_DTL WHERE RETURN_ID = "+RETURN_ID+"\n" );
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 /**
	  * 查询对应退货单中的每件总价，个数，计划总价。
	  * @param RETURN_ID
	  * @return
	  * @throws Exception
	  */
	 public QuerySet queryAmount(String RETURN_ID) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT SUM(T.AMOUNT) AMOUNT,SUM(T.COUNT) COUNT,SUM(T.PLAN_AMOUNT) PLAN_AMOUNT FROM PT_BU_PCH_RETURN_DTL T WHERE T.RETURN_ID ="+RETURN_ID+"\n" );
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 /**
	  * 查询配件ID，供应商ID
	  */
	 public QuerySet queryContent(String dtlId) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.PART_ID,T.SUPPLIER_ID FROM PT_BU_PCH_RETURN_DTL T WHERE T.DETAIL_ID =  "+dtlId+"\n" );
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 public boolean returnOrderPartDelete(String RETURN_ID ) throws Exception
	    {
			StringBuffer sql= new StringBuffer();
			sql.append("DELETE FROM PT_BU_PCH_RETURN_DTL WHERE RETURN_ID = "+RETURN_ID+"");
	    	return factory.update(sql.toString(), null);
	    }
	 public boolean returnOrderDelete(String RETURN_ID ) throws Exception
	    {
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("DELETE FROM PT_BU_PCH_RETURN WHERE RETURN_ID = "+RETURN_ID+"");
	    	return factory.update(sql.toString(), null);
	    }
	 public boolean insertPart(PtBuPchReturnDtlVO vo)
	            throws Exception {
	        return factory.insert(vo);
	    }
	 public boolean updatePart(PtBuPchReturnDtlVO vo)
			 throws Exception {
		 	return factory.update(vo);
	 }
	 public boolean updateReturnOrder(PtBuPchReturnVO vo)
	            throws Exception {
	        return factory.update(vo);
	    }
	 public QuerySet download(String purchaseId) throws Exception{
		   QuerySet qs = null;
		   StringBuffer sql= new StringBuffer();
		   sql.append("SELECT T.SPLIT_NO,T1.PART_CODE, T1.PART_NAME, 0 AS RETURN_COUNT,NULL AS REMARKS\n" );
		   sql.append("  FROM PT_BU_PCH_ORDER_SPLIT T, PT_BU_PCH_ORDER_SPLIT_DTL T1\n" );
		   sql.append(" WHERE T.SPLIT_ID = T1.SPLIT_ID\n" );
		   sql.append("   AND T.SPLIT_ID = "+purchaseId+"");
		   qs = factory.select(null, sql.toString());
		   return qs;
	   }
	 /*
	  * 查询库存明细ID
	  */
	 public QuerySet queryStockDtlId(String POSITION_ID,String SUPPLIER_ID,String PART_ID) throws Exception{
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.DTL_ID,T.OCCUPY_AMOUNT,T.AVAILABLE_AMOUNT FROM PT_BU_STOCK_DTL T WHERE T.PART_ID = "+PART_ID+" AND T.SUPPLIER_ID ="+SUPPLIER_ID+" AND T.POSITION_ID ="+POSITION_ID+" \n" );
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 /*
	  * 查询库存ID
	  */
	 public QuerySet queryStockId(String POSITION_ID,String SUPPLIER_ID,String PART_ID) throws Exception{
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.DTL_ID,T.OCCUPY_AMOUNT,T.AVAILABLE_AMOUNT FROM PT_BU_STOCK_DTL T WHERE T.PART_ID = "+PART_ID+" AND T.SUPPLIER_ID ="+SUPPLIER_ID+" AND T.POSITION_ID ="+POSITION_ID+" \n" );
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 /**
	     * 更新库存明细冻结数量(可能增加或者减少,yAmount-outAmount)
	     *
	     * @param partId 配件ID
	     * @param supplierId 供应商ID
	     * @param positionId 货位ID
	     * @param yAmount 原数量
	     * @param outAmount 出库数量
	     * @param user
	     * @return
	     * @throws Exception
	     */
	    public boolean updateLockStockDtlByOutBillDtl(String partId,String supplierId,String positionId,String yAmount,String outAmount,User user)
	            throws Exception {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE PT_BU_STOCK_DTL A\n");
			sql.append("   SET A.OCCUPY_AMOUNT    = OCCUPY_AMOUNT -("+yAmount+")+("+outAmount+"),\n");
			sql.append("       A.AVAILABLE_AMOUNT = AVAILABLE_AMOUNT +("+yAmount+")-("+outAmount+"),\n");
			sql.append("       A.UPDATE_USER      = '" + user.getAccount() + "',\n");
			sql.append("       A.UPDATE_TIME      = SYSDATE\n");
			sql.append(" WHERE A.PART_ID ="+partId+"\n");
			sql.append("       AND A.POSITION_ID ="+positionId+"\n");
			sql.append("       AND A.SUPPLIER_ID = "+supplierId+"\n");
	        return factory.update(sql.toString(), null);
	    }
	 /**
	     * 更新库存冻结数量(可能增加或者减少,yAmount-outAmount)
	     *
	      * @param partId 配件ID
	     * @param supplierId 供应商ID
	     * @param positionId 货位ID
	     * @param yAmount 原数量
	     * @param outAmount 出库数量
	     * @param user
	     * @return
	     * @throws Exception
	     */
	    public boolean updateLockStockByOutBillDtl(String partId,String supplierId,String positionId,String yAmount,String outAmount,User user)
	            throws Exception {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE PT_BU_STOCK A\n");
			sql.append("   SET A.OCCUPY_AMOUNT    = OCCUPY_AMOUNT -("+yAmount+")+"+outAmount+",\n");
			sql.append("       A.AVAILABLE_AMOUNT = AVAILABLE_AMOUNT +("+yAmount+")-("+outAmount+"),\n");
			sql.append("       A.UPDATE_USER      = '" + user.getAccount() + "',\n");
			sql.append("       A.UPDATE_TIME      = SYSDATE\n");
			sql.append(" WHERE 1 = 1\n");
			sql.append("   AND EXISTS (SELECT 1\n");
			sql.append("          FROM PT_BU_STOCK_DTL B\n");
			sql.append("         WHERE B.STOCK_ID = A.STOCK_ID \n");
			sql.append("           AND B.PART_ID = "+partId+"\n");
			sql.append("           AND B.SUPPLIER_ID ="+supplierId+"\n");
			sql.append("           AND B.POSITION_ID ="+positionId+")\n");
	        return factory.update(sql.toString(), null);
	    }
	 /**
	     * 更新库存明细表占用数量和可用数量
	     *
	     * @param stockDtlId 库存明细ID
	     * @param amount 出库数量
	     * @param user
	     * @return
	     * @throws Exception
	     */
	    public boolean updateStockDtlAva(String dtlId,String amount,User user)
	            throws Exception {
	    	StringBuffer sql1= new StringBuffer();
	    	sql1.append("UPDATE PT_BU_STOCK_DTL D\n" );
	    	sql1.append("   SET D.OCCUPY_AMOUNT    = NVL(D.OCCUPY_AMOUNT, 0) + "+amount+",\n" );
	    	sql1.append("       D.AVAILABLE_AMOUNT = NVL(D.AVAILABLE_AMOUNT, 0) - ("+amount+"),\n" );
	    	sql1.append("       D.UPDATE_USER='"+user.getAccount()+"',\n" );
	    	sql1.append("       D.UPDATE_TIME=SYSDATE\n" );
	    	sql1.append(" WHERE D.DTL_ID = "+dtlId);
	        return factory.update(sql1.toString(), null);
	    }
	    /**
	     * 更新库存主表占用数量和可用数量
	     *
	     * @param stockDtlId 库存明细ID
	     * @param amount 出库数量
	     * @param user
	     * @return
	     * @throws Exception
	     */
	    public boolean updateStockAva(String dtlId,String amount,User user)
	            throws Exception {
	    	StringBuffer sql1= new StringBuffer();
	    	sql1.append("UPDATE PT_BU_STOCK S\n" );
	    	sql1.append("   SET S.OCCUPY_AMOUNT    = NVL(OCCUPY_AMOUNT, 0) + "+amount+",\n" );
	    	sql1.append("       S.AVAILABLE_AMOUNT = NVL(S.AVAILABLE_AMOUNT, 0) - ("+amount+"),\n" );
	    	sql1.append("       S.UPDATE_USER      = '"+user.getAccount()+"',\n" );
	    	sql1.append("       S.UPDATE_TIME      = SYSDATE\n" );
	    	sql1.append(" WHERE S.STOCK_ID =\n" );
	    	sql1.append("       (SELECT D.STOCK_ID FROM PT_BU_STOCK_DTL D WHERE D.DTL_ID = "+dtlId+")");

	        return factory.update(sql1.toString(), null);
	    }
	 public QuerySet querySupplier(String SUPPLIER_ID) throws Exception{
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.SUPPLIER_NAME,T.SUPPLIER_CODE FROM PT_BA_SUPPLIER T WHERE T.SUPPLIER_ID = "+SUPPLIER_ID+" AND T.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 public QuerySet querySupplier1(String PART_ID,String POSITION_ID) throws Exception{
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
//		 sql.append("SELECT T.SUPPLIER_NAME,T.SUPPLIER_CODE FROM PT_BA_SUPPLIER T WHERE T.SUPPLIER_ID = "+SUPPLIER_ID+" AND T.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
		 sql.append("SELECT T.SUPPLIER_ID,T.SUPPLIER_CODE,T.SUPPLIER_NAME\n" );
		 sql.append("FROM PT_BU_STOCK_DTL T\n" );
		 sql.append("WHERE T.PART_ID = "+PART_ID+"\n" );
		 sql.append("AND T.POSITION_ID = "+POSITION_ID+"");
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 
	 public QuerySet geOldtPartAmount(String DETAIL_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.COUNT,\n" );
	    	sql.append("       T.AMOUNT,\n" );
	    	sql.append("       T.PLAN_AMOUNT,\n" );
	    	sql.append("       T1.PLAN_AMOUNT R_PLAN_AMOUNT,\n" );
	    	sql.append("       T1.COUNT  R_COUNT,\n" );
	    	sql.append("       T1.AMOUNT     R_AMOUNT,\n" );
	    	sql.append("       T1.RETURN_ID\n" );
	    	sql.append("  FROM PT_BU_PCH_RETURN_DTL T, PT_BU_PCH_RETURN T1\n" );
	    	sql.append(" WHERE 1 = 1\n" );
	    	sql.append("   AND T.RETURN_ID = T1.RETURN_ID\n" );
	    	sql.append("   AND DETAIL_ID = "+DETAIL_ID+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 /**
	  * 
	  * @date()2014年7月30日上午11:42:40
	  * @author Administrator
	  * @to_do:删除配件
	  * @param DETAIL_ID
	  * @return
	  * @throws Exception
	  */
	 public boolean delParts(String DETAIL_ID ) throws Exception
	    {
			StringBuffer sql= new StringBuffer();
			sql.append("DELETE FROM PT_BU_PCH_RETURN_DTL WHERE DETAIL_ID = "+DETAIL_ID+"");
	    	return factory.update(sql.toString(), null);
	    }
	 /**
	  * 
	  * @date()2014年7月30日上午11:44:42
	  * @author Administrator
	  * @to_do:获取退货单对应原采购订单ID 
	  * @param RETURN_ID
	  * @return
	  * @throws Exception
	  */
	 public QuerySet getPurchase(String RETURN_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT PURCHASE_ID FROM PT_BU_PCH_RETURN WHERE RETURN_ID = "+RETURN_ID+"");
	    	qs = factory.select(null, sql.toString());     
	    	return qs;
	    }
	 /**
	  * 
	  * @author Administrator
	  * @param user
	  * @throws Exception
	  * 校验填写的退货单号在数据库中存不存在。
	  */
	 public QuerySet checkList1(User user) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.RETURN_NO,T.ROW_NO\n" );
	    	sql.append("  FROM PT_BU_PCH_RETURN_PART_TMP T\n" );
	    	sql.append(" WHERE NOT EXISTS (SELECT 1\n" );
	    	sql.append("          FROM PT_BU_PCH_RETURN A\n" );
	    	sql.append("         WHERE T.RETURN_NO = A.RETURN_NO\n" );
	    	sql.append("           AND T.USER_ACCOUNT = '"+user.getAccount()+"')");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 /**
	  * 
	  * @author Administrator
	  * @param user
	  * @throws Exception
	  * 校验填写的配件代码在数据库中存不存在。
	  */
	 public QuerySet checkPartCode(User user) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.PART_CODE\n" );
		 sql.append("  FROM PT_BU_PCH_RETURN_PART_TMP T\n" );
		 sql.append(" WHERE NOT EXISTS (SELECT *\n" );
		 sql.append("          FROM PT_BA_INFO A\n" );
		 sql.append("         WHERE T.PART_CODE = A.PART_CODE\n" );
		 sql.append("           AND T.USER_ACCOUNT = '"+user.getAccount()+"')");
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 /**
	  * 
	  * @author Administrator
	  * @param user
	  * @throws Exception
	  * 校验填写的配件代码在数据库中存不存在。
	  */
	 public QuerySet checkPartPoCode(User user) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.PART_CODE,T.POSITION_CODE\n" );
		 sql.append("  FROM PT_BU_PCH_RETURN_PART_TMP T\n" );
		 sql.append(" WHERE NOT EXISTS (SELECT 1\n" );
		 sql.append("          FROM PT_BA_WAREHOUSE_PART_RL R\n" );
		 sql.append("         WHERE T.PART_CODE = R.PART_CODE\n" );
		 sql.append("           AND T.POSITION_CODE = R.POSITION_CODE\n" );
		 sql.append("           AND T.USER_ACCOUNT = '"+user.getAccount()+"')");
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 //查询临时表中的信息
	 public QuerySet queryTmp(User user) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT A.PART_CODE,\n" );
		 sql.append("       A.PART_NAME,\n" );
		 sql.append("       A.RETURN_COUNT,\n" );
		 sql.append("       A.REMARKS,\n" );
		 sql.append("       A.USER_ACCOUNT,\n" );
		 sql.append("       A.POSITION_CODE,\n" );
		 sql.append("       A.RETURN_NO\n" );
		 sql.append("  FROM PT_BU_PCH_RETURN_PART_TMP A \n");
		 sql.append("WHERE A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 //查询配件ID
	 public QuerySet queryPartId(String pCode) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.PART_ID, T.PART_NAME FROM PT_BA_INFO T WHERE T.PART_CODE ='"+pCode+"'\n" );
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 //查询配件ID
	 public QuerySet queryDtl(String pId,String poId,String wId) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT D.DETAIL_ID, D.RETURN_AMOUNT\n" );
		 sql.append("  FROM PT_BU_PCH_RETURN_DTL D\n" );
		 sql.append(" WHERE D.PART_ID ="+pId+"\n" );
		 sql.append("   AND D.POSITION_ID ="+poId+"\n" );
		 sql.append("   AND D.SUPPLIER_ID ="+wId+"");
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 //查询是否为指定供应商
	 public QuerySet queryIfSuply(User user,String bParams) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT PART_CODE, ROW_NO\n" );
		 sql.append("  FROM PT_BU_PCH_RETURN_PART_TMP T\n" );
		 sql.append(" WHERE NOT EXISTS (SELECT 1\n" );
		 sql.append("          FROM PT_BA_PART_SUPPLIER_RL R, PT_BA_INFO P\n" );
		 sql.append("         WHERE R.PART_ID = P.PART_ID AND R.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
		 sql.append("           AND R.SUPPLIER_ID =\n" );
		 sql.append("               (SELECT SUPPLIER_ID\n" );
		 sql.append("                  FROM PT_BU_PCH_RETURN\n" );
		 sql.append("                 WHERE RETURN_ID = "+bParams+")\n" );
		 sql.append("           AND T.PART_CODE = P.PART_CODE\n" );
		 sql.append("           AND T.USER_ACCOUNT = '"+user.getAccount()+"')");

		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 /**
	  * 
	  * @author Administrator
	  * @param user
	  * @throws Exception
	  * 校验填写的配件信息是否满足可用库存
	  */
	 public QuerySet checkAvaAmount(User user,String sId,String wId) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.PART_CODE, T.POSITION_CODE, T.AVAILABLE_AMOUNT, TMP.RETURN_COUNT\n" );
		 sql.append("  FROM (SELECT T1.AVAILABLE_AMOUNT, POSITION_CODE, PART_CODE\n" );
		 sql.append("          FROM (SELECT B.SUPPLIER_ID,\n" );
		 sql.append("                       D.PART_ID,\n" );
		 sql.append("                       D.PART_CODE,\n" );
		 sql.append("                       D.PART_NAME,\n" );
		 sql.append("                       D.UNIT,\n" );
		 sql.append("                       D.MIN_PACK,\n" );
		 sql.append("                       D.MIN_UNIT,\n" );
		 sql.append("                       D.PART_NO,\n" );
		 sql.append("                       E.PCH_PRICE,\n" );
		 sql.append("                       D.PLAN_PRICE,\n" );
		 sql.append("                       NVL(G.AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT,\n" );
		 sql.append("                       G.POSITION_ID,\n" );
		 sql.append("                       G.POSITION_CODE,\n" );
		 sql.append("                       G.POSITION_NAME,\n" );
		 sql.append("                       SUM(NVL(B.STORAGE_COUNT, 0)) STORAGE_COUNT\n" );
		 sql.append("                  FROM PT_BU_PCH_ORDER_SPLIT     A,\n" );
		 sql.append("                       PT_BU_PCH_ORDER_SPLIT_DTL B,\n" );
		 sql.append("                       PT_BA_PCH_ATTRIBUTE       C,\n" );
		 sql.append("                       PT_BA_INFO                D,\n" );
		 sql.append("                       PT_BA_PART_SUPPLIER_RL    E,\n" );
		 sql.append("                       PT_BA_WAREHOUSE_PART_RL   F,\n" );
		 sql.append("                       PT_BU_STOCK_DTL           G\n" );
		 sql.append("                 WHERE A.SPLIT_ID = B.SPLIT_ID\n" );
		 sql.append("                   AND B.PART_ID = C.PART_ID\n" );
		 sql.append("                   AND B.PART_ID = D.PART_ID\n" );
		 sql.append("                   AND B.PART_ID = E.PART_ID\n" );
		 sql.append("                   AND F.PART_ID = B.PART_ID\n" );
		 sql.append("                   AND G.PART_ID = D.PART_ID\n" );
		 sql.append("                   AND G.POSITION_ID = F.POSITION_ID\n" );
		 sql.append("                   AND F.POSITION_ID IN\n" );
		 sql.append("                       (SELECT S.POSITION_ID\n" );
		 sql.append("                          FROM PT_BA_WAREHOUSE_POSITION S,\n" );
		 sql.append("                               PT_BA_WAREHOUSE_AREA     M,\n" );
		 sql.append("                               PT_BA_WAREHOUSE          N\n" );
		 sql.append("                         WHERE S.AREA_ID = M.AREA_ID\n" );
		 sql.append("                           AND M.WAREHOUSE_ID = N.WAREHOUSE_ID\n" );
		 sql.append("                           AND N.WAREHOUSE_ID = "+wId+")\n" );
		 sql.append("                   AND A.SUPPLIER_ID = E.SUPPLIER_ID AND E.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
		 sql.append("                   AND B.SUPPLIER_ID = G.SUPPLIER_ID\n" );
		 sql.append("                   AND A.SUPPLIER_ID = "+sId+"\n" );
		 sql.append("                   AND B.STORAGE_COUNT > 0\n" );
		 sql.append("                 GROUP BY B.SUPPLIER_ID,\n" );
		 sql.append("                          G.AVAILABLE_AMOUNT,\n" );
		 sql.append("                          D.PART_ID,\n" );
		 sql.append("                          D.PART_CODE,\n" );
		 sql.append("                          D.PART_NAME,\n" );
		 sql.append("                          D.PART_NO,\n" );
		 sql.append("                          D.UNIT,\n" );
		 sql.append("                          D.MIN_PACK,\n" );
		 sql.append("                          D.MIN_UNIT,\n" );
		 sql.append("                          E.PCH_PRICE,\n" );
		 sql.append("                          D.PLAN_PRICE,\n" );
		 sql.append("                          G.POSITION_ID,\n" );
		 sql.append("                          G.POSITION_CODE,\n" );
		 sql.append("                          G.POSITION_NAME) T1\n" );
		 sql.append("          LEFT JOIN (SELECT G.SUPPLIER_ID,\n" );
		 sql.append("                           G.PART_ID,\n" );
		 sql.append("                           SUM(NVL(G.RETURN_AMOUNT, 0)) RETURN_AMOUNT\n" );
		 sql.append("                      FROM PT_BU_PCH_RETURN F, PT_BU_PCH_RETURN_DTL G\n" );
		 sql.append("                     WHERE F.RETURN_ID = G.RETURN_ID\n" );
		 sql.append("                       AND F.SUPPLIER_ID = "+sId+"\n" );
		 sql.append("                       AND G.RETURN_AMOUNT > 0\n" );
		 sql.append("                       AND F.RETURN_STATUS <> 201101\n" );
		 sql.append("                     GROUP BY G.SUPPLIER_ID, G.PART_ID) T2\n" );
		 sql.append("            ON T1.PART_ID = T2.PART_ID\n" );
		 sql.append("           AND T1.SUPPLIER_ID = T2.SUPPLIER_ID\n" );
		 sql.append("           AND T1.STORAGE_COUNT - T2.RETURN_AMOUNT > 0) T,\n" );
		 sql.append("       PT_BU_PCH_RETURN_PART_TMP TMP\n" );
		 sql.append(" WHERE T.PART_CODE = TMP.PART_CODE\n" );
		 sql.append("   AND T.POSITION_CODE = TMP.POSITION_CODE\n" );
		 sql.append("   AND TMP.RETURN_COUNT > T.AVAILABLE_AMOUNT");
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 /**
	  * 
	  * @author Administrator
	  * @param user
	  * @throws Exception
	  * TO_DO:查询配件所在的库位是否有满足的可退货数。
	  */
	 public QuerySet checkKthAmount(User user,String sId,String wId) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.PART_CODE,T.POSITION_CODE,T.SL,TMP.RETURN_COUNT\n" );
		 sql.append("  FROM (SELECT T1.*,\n" );
		 sql.append("               (NVL(T1.STORAGE_COUNT, 0) - NVL(T2.RETURN_AMOUNT, 0)) SL\n" );
		 sql.append("          FROM (SELECT B.SUPPLIER_ID,\n" );
		 sql.append("                       D.PART_ID,\n" );
		 sql.append("                       D.PART_CODE,\n" );
		 sql.append("                       D.PART_NAME,\n" );
		 sql.append("                       D.UNIT,\n" );
		 sql.append("                       D.MIN_PACK,\n" );
		 sql.append("                       D.MIN_UNIT,\n" );
		 sql.append("                       D.PART_NO,\n" );
		 sql.append("                       E.PCH_PRICE,\n" );
		 sql.append("                       D.PLAN_PRICE,\n" );
		 sql.append("                       NVL(G.AVAILABLE_AMOUNT, 0) AVAILABLE_AMOUNT,\n" );
		 sql.append("                       G.POSITION_ID,\n" );
		 sql.append("                       G.POSITION_CODE,\n" );
		 sql.append("                       G.POSITION_NAME,\n" );
		 sql.append("                       SUM(NVL(B.STORAGE_COUNT, 0)) STORAGE_COUNT\n" );
		 sql.append("                  FROM PT_BU_PCH_ORDER_SPLIT     A,\n" );
		 sql.append("                       PT_BU_PCH_ORDER_SPLIT_DTL B,\n" );
		 sql.append("                       PT_BA_PCH_ATTRIBUTE       C,\n" );
		 sql.append("                       PT_BA_INFO                D,\n" );
		 sql.append("                       PT_BA_PART_SUPPLIER_RL    E,\n" );
		 sql.append("                       PT_BA_WAREHOUSE_PART_RL   F,\n" );
		 sql.append("                       PT_BU_STOCK_DTL           G\n" );
		 sql.append("                 WHERE A.SPLIT_ID = B.SPLIT_ID\n" );
		 sql.append("                   AND B.PART_ID = C.PART_ID\n" );
		 sql.append("                   AND B.PART_ID = D.PART_ID\n" );
		 sql.append("                   AND B.PART_ID = E.PART_ID\n" );
		 sql.append("                   AND F.PART_ID = B.PART_ID\n" );
		 sql.append("                   AND G.PART_ID = D.PART_ID\n" );
		 sql.append("                   AND G.POSITION_ID = F.POSITION_ID\n" );
		 sql.append("                   AND F.POSITION_ID IN\n" );
		 sql.append("                       (SELECT S.POSITION_ID\n" );
		 sql.append("                          FROM PT_BA_WAREHOUSE_POSITION S,\n" );
		 sql.append("                               PT_BA_WAREHOUSE_AREA     M,\n" );
		 sql.append("                               PT_BA_WAREHOUSE          N\n" );
		 sql.append("                         WHERE S.AREA_ID = M.AREA_ID\n" );
		 sql.append("                           AND M.WAREHOUSE_ID = N.WAREHOUSE_ID\n" );
		 sql.append("                           AND N.WAREHOUSE_ID = "+wId+")\n" );
		 sql.append("                   AND A.SUPPLIER_ID = E.SUPPLIER_ID AND E.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
		 sql.append("                   AND B.SUPPLIER_ID = G.SUPPLIER_ID\n" );
		 sql.append("                   AND A.SUPPLIER_ID = "+sId+"\n" );
		 sql.append("                   AND B.STORAGE_COUNT > 0\n" );
		 sql.append("                 GROUP BY B.SUPPLIER_ID,\n" );
		 sql.append("                          G.AVAILABLE_AMOUNT,\n" );
		 sql.append("                          D.PART_ID,\n" );
		 sql.append("                          D.PART_CODE,\n" );
		 sql.append("                          D.PART_NAME,\n" );
		 sql.append("                          D.PART_NO,\n" );
		 sql.append("                          D.UNIT,\n" );
		 sql.append("                          D.MIN_PACK,\n" );
		 sql.append("                          D.MIN_UNIT,\n" );
		 sql.append("                          E.PCH_PRICE,\n" );
		 sql.append("                          D.PLAN_PRICE,\n" );
		 sql.append("                          G.POSITION_ID,\n" );
		 sql.append("                          G.POSITION_CODE,\n" );
		 sql.append("                          G.POSITION_NAME) T1\n" );
		 sql.append("          LEFT JOIN (SELECT G.SUPPLIER_ID,\n" );
		 sql.append("                           G.PART_ID,\n" );
		 sql.append("                           SUM(NVL(G.RETURN_AMOUNT, 0)) RETURN_AMOUNT\n" );
		 sql.append("                      FROM PT_BU_PCH_RETURN F, PT_BU_PCH_RETURN_DTL G\n" );
		 sql.append("                     WHERE F.RETURN_ID = G.RETURN_ID\n" );
		 sql.append("                       AND F.SUPPLIER_ID = "+sId+"\n" );
		 sql.append("                       AND G.RETURN_AMOUNT > 0\n" );
		 sql.append("                       AND F.RETURN_STATUS <> 201101\n" );
		 sql.append("                     GROUP BY G.SUPPLIER_ID, G.PART_ID) T2\n" );
		 sql.append("            ON T1.PART_ID = T2.PART_ID\n" );
		 sql.append("           AND T1.SUPPLIER_ID = T2.SUPPLIER_ID\n" );
		 sql.append("           AND T1.STORAGE_COUNT - T2.RETURN_AMOUNT > 0) T,\n" );
		 sql.append("       PT_BU_PCH_RETURN_PART_TMP TMP\n" );
		 sql.append(" WHERE T.PART_CODE = TMP.PART_CODE\n" );
		 sql.append("   AND T.POSITION_CODE = TMP.POSITION_CODE\n" );
		 sql.append("   AND TMP.RETURN_COUNT > T.SL");
		 sql.append("   AND TMP.USER_ACCOUNT = '"+user.getAccount()+"'");
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 /**
	  * @date()2014年7月30日下午3:26:55
	  * @author Administrator
	  * @to_do:通过退货单ID查询供应商ID
	  * @param user
	  * @param SPLIT_ID
	  * @return
	  * @throws Exception
	  */
	 public QuerySet querySid(String bParams) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.SUPPLIER_ID FROM PT_BU_PCH_RETURN T WHERE T.RETURN_ID = "+bParams+"\n" );
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 public QuerySet queryId() throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.WAREHOUSE_ID FROM PT_BA_WAREHOUSE T WHERE T.WAREHOUSE_TYPE ="+DicConstant.SF_01+" AND T.STATUS="+DicConstant.YXBS_01+"\n" );
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 /**
	  * 
	  * @author Administrator
	  * @to_do:查询退货单明细中的供应商ID,配件ID,退货单ID。
	  * @return
	  * @throws Exception
	  */
	 public QuerySet queryDelete(String detailId) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.PART_ID,T.RETURN_ID,T.SUPPLIER_ID FROM PT_BU_PCH_RETURN_DTL T WHERE T.DETAIL_ID = "+detailId+"\n" );
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 /**
	  * 
	  * @date()2014年7月30日下午3:31:28
	  * @author 校验是否重复导入
	  * @param user
	  * @param bParams
	  * @return
	  * @throws Exception
	  */
	 public QuerySet checkUnique(User user,String bParams) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT ROWNUM NUM, T.PART_CODE\n" );
	    	sql.append("  FROM PT_BU_PCH_RETURN_PART_TMP T\n" );
	    	sql.append(" WHERE EXISTS (SELECT 1\n" );
	    	sql.append("          FROM PT_BU_PCH_RETURN_DTL T1\n" );
	    	sql.append("         WHERE T.PART_CODE = T1.PART_CODE\n" );
	    	sql.append("           AND T1.RETURN_ID = "+bParams+")\n" );
	    	sql.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 /**
	  * 
	  * @date()2014年7月30日下午3:56:32
	  * @author Administrator
	  * @to_do:采购退货单导入配件查询
	  * @param page
	  * @param user
	  * @return
	  * @throws Exception
	  */
	 public BaseResultSet searchImport(PageManager page,User user,String tmpNo)throws Exception{
	 	   page.setFilter("");
	 	   BaseResultSet bs = null;
	 	   StringBuffer sql= new StringBuffer();
	 	   sql.append("SELECT D.* \n" );
	 	   sql.append("  FROM PT_BU_PCH_RETURN_PART_TMP D");
	 	   sql.append(" WHERE D.USER_ACCOUNT ='"+user.getAccount()+"' AND D.TMP_NO NOT IN ('"+tmpNo+"')");
	 	   bs=factory.select(sql.toString(), page);
	 	   return bs;
	    }
	 /**
	  * 
	  * @date()2014年7月30日下午4:17:41
	  * @author Administrator
	  * @to_do:获取临时表数据插入业务表
	  * @param user
	  * @param SUPPLIER_ID
	  * @return
	  * @throws Exception
	  */
	 public QuerySet getTmpInfo(User user,String SPLIT_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.PART_CODE,\n" );
	    	sql.append("       T.PART_NAME,\n" );
	    	sql.append("       T.REMARKS,\n" );
	    	sql.append("       T2.PART_ID,\n" );
	    	sql.append("       T.RETURN_COUNT,\n" );
	    	sql.append("       T2.PLAN_PRICE * T.RETURN_COUNT PLAN_AMOUNT,\n" );
	    	sql.append("       T2.PCH_PRICE * T.RETURN_COUNT RETURN_AMOUNT\n" );
	    	sql.append("  FROM PT_BU_PCH_RETURN_PART_TMP T,\n" );
	    	sql.append("       PT_BU_PCH_ORDER_SPLIT     T1,\n" );
	    	sql.append("       PT_BU_PCH_ORDER_SPLIT_DTL T2\n" );
	    	sql.append(" WHERE T.SPLIT_NO = T1.SPLIT_NO\n" );
	    	sql.append("   AND T2.SPLIT_ID = "+SPLIT_ID+"\n" );
	    	sql.append("   AND T1.SPLIT_ID = T2.SPLIT_ID\n" );
	    	sql.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 public QuerySet getPch(String RETURN_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT PURCHASE_ID SPLIT_ID FROM PT_BU_PCH_RETURN WHERE RETURN_ID = "+RETURN_ID+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 /*
	  * 查询对应的库位信息code,name
	  */
	 public QuerySet queryPositions(String pId) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.POSITION_ID,T.POSITION_CODE,T.POSITION_NAME FROM PT_BA_WAREHOUSE_POSITION T WHERE T.POSITION_ID = "+pId+"");
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 /*
	  * 查询对应的库位信息id,name
	  */
	 public QuerySet queryPositionId(String poCode) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.POSITION_ID,T.POSITION_CODE,T.POSITION_NAME FROM PT_BA_WAREHOUSE_POSITION T WHERE T.POSITION_CODE = '"+poCode+"'");
		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 /*
	  * 查询计划价，采购价
	  */
	 public QuerySet queryPrices(String pId,String poId,String sId,String wId) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT E.PCH_PRICE,\n" );
		 sql.append("       D.PLAN_PRICE\n" );
		 sql.append("  FROM PT_BU_PCH_ORDER_SPLIT     A,\n" );
		 sql.append("       PT_BU_PCH_ORDER_SPLIT_DTL B,\n" );
		 sql.append("       PT_BA_PCH_ATTRIBUTE       C,\n" );
		 sql.append("       PT_BA_INFO                D,\n" );
		 sql.append("       PT_BA_PART_SUPPLIER_RL    E,\n" );
		 sql.append("       PT_BA_WAREHOUSE_PART_RL   F,\n" );
		 sql.append("       PT_BU_STOCK_DTL           G\n" );
		 sql.append(" WHERE A.SPLIT_ID = B.SPLIT_ID\n" );
		 sql.append("   AND B.PART_ID = C.PART_ID\n" );
		 sql.append("   AND B.PART_ID = D.PART_ID\n" );
		 sql.append("   AND B.PART_ID = E.PART_ID\n" );
		 sql.append("   AND F.PART_ID = B.PART_ID\n" );
		 sql.append("   AND G.PART_ID = D.PART_ID\n" );
		 sql.append("   AND G.POSITION_ID = F.POSITION_ID\n" );
		 sql.append("   AND F.POSITION_ID IN\n" );
		 sql.append("       (SELECT S.POSITION_ID\n" );
		 sql.append("          FROM PT_BA_WAREHOUSE_POSITION S,\n" );
		 sql.append("               PT_BA_WAREHOUSE_AREA     M,\n" );
		 sql.append("               PT_BA_WAREHOUSE          N\n" );
		 sql.append("         WHERE S.AREA_ID = M.AREA_ID\n" );
		 sql.append("           AND M.WAREHOUSE_ID = N.WAREHOUSE_ID\n" );
		 sql.append("           AND N.WAREHOUSE_ID = "+wId+")\n" );
		 sql.append("   AND A.SUPPLIER_ID = E.SUPPLIER_ID AND E.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
		 sql.append("   AND B.SUPPLIER_ID = G.SUPPLIER_ID\n" );
		 sql.append("   AND A.SUPPLIER_ID = "+sId+"\n" );
		 sql.append("   AND D.PART_ID = "+pId+"\n" );
		 sql.append("   AND G.POSITION_ID = "+poId+"\n" );
		 sql.append("   AND B.STORAGE_COUNT > 0\n" );
		 sql.append(" GROUP BY B.SUPPLIER_ID,\n" );
		 sql.append("          G.AVAILABLE_AMOUNT,\n" );
		 sql.append("          D.PART_ID,\n" );
		 sql.append("          D.PART_CODE,\n" );
		 sql.append("          D.PART_NAME,\n" );
		 sql.append("          D.PART_NO,\n" );
		 sql.append("          D.UNIT,\n" );
		 sql.append("          D.MIN_PACK,\n" );
		 sql.append("          D.MIN_UNIT,\n" );
		 sql.append("          E.PCH_PRICE,\n" );
		 sql.append("          D.PLAN_PRICE,\n" );
		 sql.append("          G.POSITION_ID,\n" );
		 sql.append("          G.POSITION_CODE,\n" );
		 sql.append("          G.POSITION_NAME");

		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	 public QuerySet getSup(String SPLIT_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.SUPPLIER_ID, T.SUPPLIER_NAME, T.SUPPLIER_CODE\n" );
	    	sql.append("  FROM PT_BU_PCH_ORDER_SPLIT_DTL T, PT_BU_PCH_ORDER_SPLIT T1\n" );
	    	sql.append(" WHERE T.SPLIT_ID = T1.SPLIT_ID\n" );
	    	sql.append("   AND T1.SPLIT_ID ="+SPLIT_ID+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 public QuerySet checkSup(String PART_ID,String SPLIT_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.SUPPLIER_ID,T.SUPPLIER_NAME,T.SUPPLIER_CODE\n" );
	    	sql.append("FROM PT_BU_PCH_ORDER_SPLIT_DTL T\n" );
	    	sql.append("WHERE T.PART_ID = "+PART_ID+"\n" );
	    	sql.append("AND T.SPLIT_ID = "+SPLIT_ID+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 public QuerySet checkAmount(User user) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT ROWNUM NUM, T.PART_CODE\n" );
	    	sql.append("  FROM PT_BU_PCH_RETURN_PART_TMP T\n" );
	    	sql.append(" WHERE T.RETURN_COUNT = 0\n" );
	    	sql.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 public QuerySet getOld(String RETURN_ID,String DETAIL_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT T.COUNT, NVL(T2.PCH_PRICE,0)PCH_PRICE, T.AMOUNT, T.PLAN_AMOUNT, NVL(T2.PLAN_PRICE,0) PLAN_PRICE, T.RETURN_ID,T.DETAIL_ID\n" );
	    	sql.append("  FROM PT_BU_PCH_RETURN_DTL      T,\n" );
	    	sql.append("       PT_BU_PCH_RETURN          T1,\n" );
	    	sql.append("       PT_BA_PART_SUPPLIER_RL T2\n" );
	    	sql.append(" WHERE T.RETURN_ID = T1.RETURN_ID\n" );
	    	sql.append("   AND T.SUPPLIER_ID = T2.SUPPLIER_ID AND T2.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
	    	sql.append("   AND T.PART_ID = T2.PART_ID  AND T.RETURN_ID ="+RETURN_ID+"\n" );
	    	sql.append("  AND T.DETAIL_ID ="+DETAIL_ID+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 public void updateOrderPart(String DETAIL_ID,String RETURN_ID,int count,String NEW_PCH_AMOUNT,String NEW_PLAN_AMOUNT,String REMARKS,User user) throws Exception {
	    	StringBuffer sql = new StringBuffer();
	    	sql.append("UPDATE PT_BU_PCH_RETURN_DTL SET REMARKS='" + REMARKS + "',COUNT="+count+",AMOUNT= "+NEW_PCH_AMOUNT+",PLAN_AMOUNT= "+NEW_PLAN_AMOUNT+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE DETAIL_ID ="+DETAIL_ID+" AND RETURN_ID ="+RETURN_ID+"\n");
	       factory.update(sql.toString(),null);
	    }
	 public void updatePurAmount(String RETURN_ID,String PLAN_AMOUNT,String NEW_PLAN_AMOUNT,String PCH_AMOUNT,String NEW_PCH_AMOUNT,String PCH_COUNT,int count,User user) throws Exception {
	    	StringBuffer sql = new StringBuffer();
	    	sql.append("UPDATE PT_BU_PCH_RETURN SET AMOUNT=AMOUNT-"+PCH_AMOUNT+"+"+NEW_PCH_AMOUNT+",PLAN_AMOUNT = PLAN_AMOUNT-"+PLAN_AMOUNT+"+"+NEW_PLAN_AMOUNT+",COUNT=COUNT-"+PCH_COUNT+"+"+count+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE RETURN_ID = "+RETURN_ID+"\n");
	       factory.update(sql.toString(),null);
	    }
	 public QuerySet getPrice(String PART_ID,String SUPPLIIER_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT NVL(A.PCH_PRICE, 0) PCH_PRICE, NVL(A.PLAN_PRICE, 0) PLAN_PRICE,B.SUPPLIER_ID,B.SUPPLIER_NAME,B.SUPPLIER_CODE\n" );
	    	sql.append("  FROM PT_BA_PART_SUPPLIER_RL A,PT_BA_SUPPLIER B\n" );
	    	sql.append(" WHERE A.PART_ID = "+PART_ID+" AND B.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
	    	sql.append("   AND A.SUPPLIER_ID = B.SUPPLIER_ID AND A.PART_IDENTIFY = "+DicConstant.YXBS_01+"");
	    	sql.append("   AND A.SUPPLIER_ID = "+SUPPLIIER_ID+"");

	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	 /**
	  * 查询配件所在库位的库位ID
	  * @param PART_ID
	  * @param SUPPLIIER_ID
	  * @return
	  * @throws Exception
	  */
	 public QuerySet queryPid(String PART_ID,String WAREHOUSE_ID) throws Exception
	 {
		 QuerySet qs = null;
		 StringBuffer sql= new StringBuffer();
		 sql.append("SELECT T.POSITION_ID FROM PT_BU_STOCK_DTL T WHERE T.PART_ID ="+PART_ID+"\n" );
		 sql.append("AND T.POSITION_ID IN\n" );
		 sql.append("                       (SELECT S.POSITION_ID\n" );
		 sql.append("                          FROM PT_BA_WAREHOUSE_POSITION S,\n" );
		 sql.append("                               PT_BA_WAREHOUSE_AREA     M,\n" );
		 sql.append("                               PT_BA_WAREHOUSE          N\n" );
		 sql.append("                         WHERE S.AREA_ID = M.AREA_ID\n" );
		 sql.append("                           AND M.WAREHOUSE_ID = N.WAREHOUSE_ID\n" );
		 sql.append("                           AND N.WAREHOUSE_ID = "+WAREHOUSE_ID+")");

		 qs = factory.select(null, sql.toString());
		 return qs;
	 }
	  /**
	     * 解锁库存明细(根据出库单明细)
	     *
	     * @param returnId 出库单ID
	     * @param partId 配件ID
	     * @param supplierId 供应商ID
	     * @param user
	     * @return
	     * @throws Exception
	     */
	    public boolean unLockStockDtlByOutBillDtl(String returnId, String partId,String supplierId,User user)
	            throws Exception {
	        StringBuffer sql1= new StringBuffer();
	        sql1.append("MERGE INTO PT_BU_STOCK_DTL A\n" );
	        sql1.append("USING (SELECT B.PART_ID, B.SUPPLIER_ID, B.POSITION_ID, B.RETURN_AMOUNT\n" );
	        sql1.append("         FROM PT_BU_PCH_RETURN_DTL B\n" );
	        sql1.append("        WHERE B.RETURN_ID = "+returnId+"\n" );
	        sql1.append("          AND B.PART_ID = "+partId+"\n" );
	        sql1.append("          AND B.SUPPLIER_ID = "+supplierId+"\n" );
	        sql1.append("          AND B.RETURN_AMOUNT > 0) C\n" );
	        sql1.append("ON (A.PART_ID = C.PART_ID AND A.SUPPLIER_ID = C.SUPPLIER_ID AND A.POSITION_ID = C.POSITION_ID)\n" );
	        sql1.append("WHEN MATCHED THEN\n" );
	        sql1.append("  UPDATE\n" );
	        sql1.append("     SET A.OCCUPY_AMOUNT    = A.OCCUPY_AMOUNT - C.RETURN_AMOUNT,\n" );
	        sql1.append("         A.AVAILABLE_AMOUNT = A.AVAILABLE_AMOUNT + C.RETURN_AMOUNT,\n" );
	        sql1.append("         A.UPDATE_USER      = '"+user.getAccount()+"',\n" );
	        sql1.append("         A.UPDATE_TIME      = SYSDATE");

	        return factory.update(sql1.toString(), null);
	    }
	    /**
	     * 解锁库存(根据出库单明细)
	     *
	     * @param returnId 出库单ID
	     * @param partId 配件ID
	     * @param supplierId 供应商ID
	     * @param user
	     * @return
	     * @throws Exception
	     */
	    public boolean unLockStockByOutBillDtl(String returnId, String partId,String supplierId,User user)
	            throws Exception {
	    	StringBuffer sql1= new StringBuffer();
	    	sql1.append("MERGE INTO PT_BU_STOCK A\n" );
	    	sql1.append("USING (SELECT B.RETURN_AMOUNT, D.STOCK_ID\n" );
	    	sql1.append("         FROM PT_BU_PCH_RETURN_DTL B, PT_BU_STOCK_DTL D\n" );
	    	sql1.append("        WHERE B.PART_ID = D.PART_ID\n" );
	    	sql1.append("          AND B.SUPPLIER_ID = D.SUPPLIER_ID\n" );
	    	sql1.append("          AND B.POSITION_ID = D.POSITION_ID\n" );
	    	sql1.append("          AND B.RETURN_ID = "+returnId+"\n" );
	    	sql1.append("          AND B.PART_ID = "+partId+"\n" );
	    	sql1.append("          AND B.SUPPLIER_ID = "+supplierId+"\n" );
	    	sql1.append("          AND B.RETURN_AMOUNT > 0) C\n" );
	    	sql1.append("ON (A.STOCK_ID = C.STOCK_ID)\n" );
	    	sql1.append("WHEN MATCHED THEN\n" );
	    	sql1.append("  UPDATE\n" );
	    	sql1.append("     SET A.OCCUPY_AMOUNT    = A.OCCUPY_AMOUNT - C.RETURN_AMOUNT,\n" );
	    	sql1.append("         A.AVAILABLE_AMOUNT = A.AVAILABLE_AMOUNT + C.RETURN_AMOUNT,\n" );
	    	sql1.append("         A.UPDATE_USER      = '"+user.getAccount()+"',\n" );
	    	sql1.append("         A.UPDATE_TIME      = SYSDATE");
	        return factory.update(sql1.toString(), null);
	    }
	    public QuerySet checkRet(String SUPPLIER_CODE,User user) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT 1\n" );
	    	sql.append("  FROM PT_BU_PCH_RETURN\n" );
	    	sql.append(" WHERE SUPPLIER_CODE ='"+SUPPLIER_CODE+"'\n" );
	    	sql.append("   AND ORDER_USER ='"+user.getAccount()+"'\n");
	    	sql.append("   AND RETURN_STATUS ="+DicConstant.CGTHDZT_01+"\n");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
	    public QuerySet expData(String pConditions,User user) throws Exception {

	    	String wheres = " WHERE TMP_NO IN ("+ pConditions + ") AND USER_ACCOUNT='"+user.getAccount()+"'\n";
	    	StringBuilder sql= new StringBuilder();
	    	sql.append("SELECT PART_CODE, POSITION_CODE,RETURN_COUNT\n" );
	    	sql.append("  FROM PT_BU_PCH_RETURN_PART_TMP");
	        //执行方法，不需要传递conn参数
	        return factory.select(null, sql.toString()+wheres);
	    }
	    
	    public QuerySet checkZero(String RETURN_ID) throws Exception
	    {
	    	QuerySet qs = null;
	    	StringBuffer sql= new StringBuffer();
	    	sql.append("SELECT wm_concat(T.PART_CODE) PART_CODES FROM PT_BU_PCH_RETURN_DTL T\n" );
	    	sql.append("WHERE NVL(T.COUNT,0) = 0 AND T.RETURN_ID = "+RETURN_ID+"");
	    	qs = factory.select(null, sql.toString());
	    	return qs;
	    }
}
