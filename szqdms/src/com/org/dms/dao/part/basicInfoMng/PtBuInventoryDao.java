package com.org.dms.dao.part.basicInfoMng;


import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.vo.part.PtBaInfoVO;
import com.org.dms.vo.part.PtBaStockSafestocksVO;
import com.org.dms.vo.part.PtBuInventoryDtlVO;
import com.org.dms.vo.part.PtBuInventoryVO;
import com.org.dms.vo.part.PtBuStockDtlVO;
import com.org.dms.vo.part.PtBuStockVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PtBuInventoryDao extends BaseDAO {
	// 定义instance
	public static final PtBuInventoryDao getInstance(ActionContext atx) {
		PtBuInventoryDao dao = new PtBuInventoryDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}
	//判断配件代码是否存在
	public QuerySet checkInventory_no(String inventory_no) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM PT_BU_INVENTORY \n");
    	sql.append(" WHERE INVENTORY_NO = '" + inventory_no +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	//插入信息
	public boolean insertPtBuInventory(PtBuInventoryVO vo) throws Exception {
		return factory.insert(vo);
	}	
	//插入全部信息
	public boolean insertAll(String inventory_id,String warehouse_id,String inventory_no) throws Exception {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" INSERT INTO PT_BU_INVENTORY_DTL \n");
		sql.append(" (DTL_ID, \n");
		sql.append(" INVENTORY_ID, \n");
		sql.append(" PART_ID, \n");
		sql.append(" PART_CODE, \n");
		sql.append(" PART_NAME, \n");
		sql.append(" SUPPLIER_ID, \n");
		sql.append(" SUPPLIER_NAME, \n");
		sql.append(" SUPPLIER_CODE, \n");
		sql.append(" AREA_ID, \n");
		sql.append(" AREA_CODE, \n");
		sql.append(" AREA_NAME, \n");
		sql.append(" POSITION_ID, \n");
		sql.append(" POSITION_CODE, \n");
		sql.append(" POSITION_NAME, \n");
		sql.append(" WHOUSE_KEEPER, \n");
		sql.append(" PAPER_COUNT, \n");
		sql.append(" PLAN_PRICE, \n");
		sql.append(" INVENTORY_NO, \n");
		sql.append(" STOCK_DTL_ID) \n");
		
		sql.append(" SELECT f_getid(), \n");
		sql.append("         '"+inventory_id+"',\n" );
		sql.append("         B.PART_ID,\n" );
		sql.append("         B.PART_CODE,\n" );
		sql.append("         B.PART_NAME,\n" );
		sql.append("         B.SUPPLIER_ID,\n" );
		sql.append("         B.SUPPLIER_NAME,\n" );
		sql.append("         B.SUPPLIER_CODE,\n" );
		sql.append("         B.AREA_ID,\n" );
		sql.append("         B.AREA_CODE,\n" );
		sql.append("         B.AREA_NAME,\n" );
		sql.append("         B.POSITION_ID,\n" );
		sql.append("         B.POSITION_CODE,\n" );
		sql.append("         B.POSITION_NAME,\n" );
		sql.append("         D.USER_NAME,\n" );
		sql.append("         B.AMOUNT,\n" );
		sql.append("         C.PLAN_PRICE,\n" );
		sql.append("         '"+inventory_no+"',\n" );
		sql.append("         B.DTL_ID\n" );
		
		sql.append(" FROM   PT_BU_STOCK A, PT_BU_STOCK_DTL B, PT_BA_INFO C, PT_BA_WAREHOUSE_KEEPER D  \n" );
		sql.append(" WHERE  A.STOCK_ID = B.STOCK_ID  \n" );
		sql.append("   AND  B.PART_ID = C.PART_ID  \n" );
		sql.append("   AND  B.PART_ID = D.PART_ID  \n" );
		sql.append("   AND  A.WAREHOUSE_ID = '"+warehouse_id+"'  \n" );
		sql.append("   AND  D.WAREHOUSE_ID = '"+warehouse_id+"'  \n" );
		
		
//		sql.append(" INSERT INTO PT_BU_INVENTORY_DTL(DTL_ID,INVENTORY_ID,PART_ID,PART_CODE,PART_NAME,SUPPLIER_ID,SUPPLIER_NAME,SUPPLIER_CODE,AREA_ID,AREA_CODE,AREA_NAME,POSITION_ID,POSITION_CODE,POSITION_NAME,WHOUSE_KEEPER,PLAN_PRICE,INVENTORY_NO,STOCK_DTL_ID) \n");
//		sql.append(" SELECT f_getid(),'"+inventory_id+"',B.PART_ID,B.PART_CODE,B.PART_NAME,B.SUPPLIER_ID,B.SUPPLIER_NAME,B.SUPPLIER_CODE,B.AREA_ID,B.AREA_CODE,B.AREA_NAME,B.POSITION_ID,B.POSITION_CODE,B.POSITION_NAME,D.USER_NAME,C.PLAN_PRICE,'"+inventory_no+"',B.DTL_ID \n");
//		sql.append("  from PT_BU_STOCK A, PT_BU_STOCK_DTL B, PT_BA_INFO C, PT_BA_WAREHOUSE_KEEPER D  WHERE A.STOCK_ID = B.STOCK_ID AND B.PART_ID = C.PART_ID AND  B.PART_ID = D.PART_ID AND A.WAREHOUSE_ID = '"+warehouse_id+"'  \n" );
//		
		return factory.update(sql.toString(), null);
	}	
	
	//库存盘点零时表信息导入正式表
	public boolean updateImportPtBuDtlFromTmp(PtBuInventoryDtlVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	
	//修改信息
	public boolean updatePtBuInventory(PtBuInventoryVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	//盘点状态修改
	public boolean checkInventoryStatus(String inventory_id,String inventory_status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BU_INVENTORY \n");
    	sql.append(" SET INVENTORY_STATUS=" + inventory_status + " \n");
    	sql.append(" WHERE inventory_id = " + inventory_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	//审核通过
	public boolean checkUpdatePass(String inventory_id,String check_remarks,String inventory_status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BU_INVENTORY \n");
    	sql.append(" SET CHECK_REMARKS = '" + check_remarks + "' , INVENTORY_STATUS=" + inventory_status + " \n");
    	sql.append(" WHERE inventory_id = " + inventory_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	//审核驳回
	public boolean checkUpdateNoPass(String inventory_id,String check_remarks,String inventory_status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BU_INVENTORY \n");
    	sql.append(" SET CHECK_REMARKS = '" + check_remarks + "' , INVENTORY_STATUS=" + inventory_status + " \n");
    	sql.append(" WHERE inventory_id = " + inventory_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	//删除信息
	public boolean updatePtBuInventoryStatus(String inventory_id, String status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BU_INVENTORY \n");
    	sql.append(" SET STATUS = " + status + " \n");
    	sql.append(" WHERE inventory_id = " + inventory_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	//删除库存盘点明细信息
	public boolean dtlDelete(String DTL_ID) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" DELETE FROM PT_BU_INVENTORY_DTL \n");
    	sql.append(" WHERE DTL_ID = " + DTL_ID + " \n");
    	return factory.update(sql.toString(), null);
    }
	//批量删除库存盘点明细信息
	public boolean batchDelete(String DTL_ID) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" DELETE FROM PT_BU_INVENTORY_DTL \n");
    	sql.append(" WHERE DTL_ID IN (" + DTL_ID + ") \n");
    	return factory.update(sql.toString(), null);
    }
	
	//查询
	public BaseResultSet search(PageManager page,String conditions) throws Exception
    {
    	String wheres = conditions;  
    	wheres += " AND (INVENTORY_STATUS=201901 OR  INVENTORY_STATUS IS NULL)";
    	wheres += " AND STATUS=100201";
    	wheres += " ORDER BY CREATE_TIME DESC";
    	
    	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();

    	sql.append("\n" );
    	sql.append("SELECT\n" );
    	sql.append(" INVENTORY_ID,\n" );
    	sql.append(" INVENTORY_NO,\n" );
    	sql.append(" WAREHOUSE_ID,\n" );
    	sql.append(" WAREHOUSE_CODE,\n" );
    	sql.append(" WAREHOUSE_NAME,\n" );
    	sql.append(" INVENTORY_USER,\n" );
    	sql.append(" INVENTORY_STATUS,\n" );
    	sql.append(" INVENTORY_TYPE,\n" );
    	sql.append(" INVENTORY_DATE,\n" );
    	sql.append(" REMARKS,\n" );
    	sql.append(" COMPANY_ID,\n" );
    	sql.append(" ORG_ID,\n" );
    	sql.append(" CREATE_USER,\n" );
    	sql.append(" CREATE_TIME,\n" );
    	sql.append(" UPDATE_USER,\n" );
    	sql.append(" UPDATE_TIME,\n" );
    	sql.append(" STATUS,\n" );
    	sql.append(" OEM_COMPANY_ID,\n" );
    	sql.append(" SECRET_LEVEL\n" );
    	sql.append(" FROM PT_BU_INVENTORY");


    	
	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，对于配件名称字典（PART_NAME），将字典代码翻译为字典名称	  
    	bs.setFieldDic("INVENTORY_TYPE", "PDLX");   		//盘点类型
        bs.setFieldDic("INVENTORY_STATUS", "PDZT");         //盘点状态
   
        bs.setFieldDateFormat("INVENTORY_DATE", "yyyy-MM-dd");		//盘点日期
    	return bs;
    }
	//库存盘点财务确认查询
	public BaseResultSet searchSeale(PageManager page,String conditions) throws Exception
    {
    	String wheres = conditions;  
    	wheres += " AND INVENTORY_STATUS=201904";
    	wheres += " AND STATUS=100201";
    	wheres += " ORDER BY CREATE_TIME DESC";
    	
    	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();

    	sql.append("\n" );
    	sql.append("SELECT\n" );
    	sql.append(" INVENTORY_ID,\n" );
    	sql.append(" INVENTORY_NO,\n" );
    	sql.append(" WAREHOUSE_ID,\n" );
    	sql.append(" WAREHOUSE_CODE,\n" );
    	sql.append(" WAREHOUSE_NAME,\n" );
    	sql.append(" INVENTORY_USER,\n" );
    	sql.append(" INVENTORY_STATUS,\n" );
    	sql.append(" INVENTORY_TYPE,\n" );
    	sql.append(" INVENTORY_DATE,\n" );
    	sql.append(" REMARKS,\n" );
    	sql.append(" COMPANY_ID,\n" );
    	sql.append(" ORG_ID,\n" );
    	sql.append(" CREATE_USER,\n" );
    	sql.append(" CREATE_TIME,\n" );
    	sql.append(" UPDATE_USER,\n" );
    	sql.append(" UPDATE_TIME,\n" );
    	sql.append(" STATUS,\n" );
    	sql.append(" CHECK_REMARKS,\n" );
    	sql.append(" OEM_COMPANY_ID,\n" );
    	sql.append(" SECRET_LEVEL\n" );
    	sql.append(" FROM PT_BU_INVENTORY");


    	
	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，对于配件名称字典（PART_NAME），将字典代码翻译为字典名称	  
    	bs.setFieldDic("INVENTORY_TYPE", "PDLX");   		//盘点类型
        bs.setFieldDic("INVENTORY_STATUS", "PDZT");         //盘点状态
   
        bs.setFieldDateFormat("INVENTORY_DATE", "yyyy-MM-dd");		//盘点日期
    	return bs;
    }
	//库存盘点结果封存查询
	public BaseResultSet searchConfirm(PageManager page,String conditions) throws Exception
    {
    	String wheres = conditions;  
    	wheres += " AND INVENTORY_STATUS IN ("+DicConstant.PDZT_05+","+DicConstant.PDZT_06+")";
//    	wheres += " AND STATUS=100201";
    	wheres += " ORDER BY CREATE_TIME DESC";
    	
    	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();

    	sql.append("\n" );
    	sql.append("SELECT\n" );
    	sql.append(" INVENTORY_ID,\n" );
    	sql.append(" INVENTORY_NO,\n" );
    	sql.append(" WAREHOUSE_ID,\n" );
    	sql.append(" WAREHOUSE_CODE,\n" );
    	sql.append(" WAREHOUSE_NAME,\n" );
    	sql.append(" INVENTORY_USER,\n" );
    	sql.append(" INVENTORY_STATUS,\n" );
    	sql.append(" INVENTORY_TYPE,\n" );
    	sql.append(" INVENTORY_DATE,\n" );
    	sql.append(" REMARKS,\n" );
    	sql.append(" COMPANY_ID,\n" );
    	sql.append(" ORG_ID,\n" );
    	sql.append(" CREATE_USER,\n" );
    	sql.append(" CREATE_TIME,\n" );
    	sql.append(" UPDATE_USER,\n" );
    	sql.append(" UPDATE_TIME,\n" );
    	sql.append(" STATUS,\n" );
    	sql.append(" OEM_COMPANY_ID,\n" );
    	sql.append(" SECRET_LEVEL\n" );
    	sql.append(" FROM PT_BU_INVENTORY");


    	
	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，对于配件名称字典（PART_NAME），将字典代码翻译为字典名称	  
    	bs.setFieldDic("INVENTORY_TYPE", "PDLX");   		//盘点类型
        bs.setFieldDic("INVENTORY_STATUS", "PDZT");         //盘点状态
   
        bs.setFieldDateFormat("INVENTORY_DATE", "yyyy-MM-dd");		//盘点日期
    	return bs;
    }
	//库存盘点结果查询
	public BaseResultSet searchResult(PageManager page,String conditions) throws Exception
    {
    	String wheres = conditions;  
    	wheres += " AND INVENTORY_STATUS=201903";
    	wheres += " AND STATUS=100201";
    	wheres += " ORDER BY INVENTORY_ID DESC";
    	
    	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();

    	sql.append("\n" );
    	sql.append("SELECT\n" );
    	sql.append(" INVENTORY_ID,\n" );
    	sql.append(" INVENTORY_NO,\n" );
    	sql.append(" WAREHOUSE_ID,\n" );
    	sql.append(" WAREHOUSE_CODE,\n" );
    	sql.append(" WAREHOUSE_NAME,\n" );
    	sql.append(" INVENTORY_USER,\n" );
    	sql.append(" INVENTORY_STATUS,\n" );
    	sql.append(" INVENTORY_TYPE,\n" );
    	sql.append(" INVENTORY_DATE,\n" );
    	sql.append(" REMARKS,\n" );
    	sql.append(" COMPANY_ID,\n" );
    	sql.append(" ORG_ID,\n" );
    	sql.append(" CREATE_USER,\n" );
    	sql.append(" CREATE_TIME,\n" );
    	sql.append(" UPDATE_USER,\n" );
    	sql.append(" UPDATE_TIME,\n" );
    	sql.append(" STATUS,\n" );
    	sql.append(" CHECK_REMARKS,\n" );
    	sql.append(" OEM_COMPANY_ID,\n" );
    	sql.append(" SECRET_LEVEL\n" );
    	sql.append(" FROM PT_BU_INVENTORY");


    	
	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，对于配件名称字典（PART_NAME），将字典代码翻译为字典名称	  
    	bs.setFieldDic("INVENTORY_TYPE", "PDLX");   		//盘点类型
        bs.setFieldDic("INVENTORY_STATUS", "PDZT");         //盘点状态
   
        bs.setFieldDateFormat("INVENTORY_DATE", "yyyy-MM-dd");		//盘点日期
    	return bs;
    }
	//查询
	public BaseResultSet searchExp(PageManager page,String conditions) throws Exception
    {
    	String wheres = conditions;  
    	wheres += " AND INVENTORY_STATUS=201902";
    	wheres += " AND STATUS=100201";
    	wheres += " ORDER BY INVENTORY_ID DESC";
    	
    	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();

    	sql.append("\n" );
    	sql.append("SELECT\n" );
    	sql.append(" INVENTORY_ID,\n" );
    	sql.append(" INVENTORY_NO,\n" );
    	sql.append(" WAREHOUSE_ID,\n" );
    	sql.append(" WAREHOUSE_CODE,\n" );
    	sql.append(" WAREHOUSE_NAME,\n" );
    	sql.append(" INVENTORY_USER,\n" );
    	sql.append(" INVENTORY_STATUS,\n" );
    	sql.append(" INVENTORY_TYPE,\n" );
    	sql.append(" INVENTORY_DATE,\n" );
    	sql.append(" REMARKS,\n" );
    	sql.append(" COMPANY_ID,\n" );
    	sql.append(" ORG_ID,\n" );
    	sql.append(" CREATE_USER,\n" );
    	sql.append(" CREATE_TIME,\n" );
    	sql.append(" UPDATE_USER,\n" );
    	sql.append(" UPDATE_TIME,\n" );
    	sql.append(" STATUS,\n" );
    	sql.append(" OEM_COMPANY_ID,\n" );
    	sql.append(" SECRET_LEVEL\n" );
    	sql.append(" FROM PT_BU_INVENTORY");


    	
	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，对于配件名称字典（PART_NAME），将字典代码翻译为字典名称	  
    	bs.setFieldDic("INVENTORY_TYPE", "PDLX");   		//盘点类型
        bs.setFieldDic("INVENTORY_STATUS", "PDZT");         //盘点状态
   
        bs.setFieldDateFormat("INVENTORY_DATE", "yyyy-MM-dd");		//盘点日期
    	return bs;
    }
	//查询配件
	public BaseResultSet partList(PageManager page,String conditions,String inventory_id) throws Exception {
    	String wheres = conditions;  
    	wheres +=("   AND T.PART_ID = T1.PART_ID\n" );
    	wheres +=("   AND INVENTORY_ID = '"+inventory_id+"'\n" );
    	wheres +=(" ORDER BY T.PART_CODE DESC");
    	  	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
//    	
//    	StringBuffer sql= new StringBuffer();
//    	sql.append("SELECT\n" );
//    	sql.append("DTL_ID,\n" );
//    	sql.append("INVENTORY_ID,\n" );
//    	sql.append("PART_ID,\n" );
//    	sql.append("PART_CODE,\n" );
//    	sql.append("PART_NAME,\n" );
//    	sql.append("SUPPLIER_ID,\n" );
//    	sql.append("SUPPLIER_NAME,\n" );
//    	sql.append("SUPPLIER_CODE,\n" );
//    	sql.append("AREA_ID,\n" );
//    	sql.append("AREA_CODE,\n" );
//    	sql.append("AREA_NAME,\n" );
//    	sql.append("POSITION_ID,\n" );
//    	sql.append("POSITION_CODE,\n" );
//    	sql.append("POSITION_NAME,\n" );
//    	sql.append("WHOUSE_KEEPER,\n" );
//    	sql.append("PLAN_PRICE,\n" );
//    	sql.append("PAPER_COUNT,\n" );					//账面数量
//    	sql.append("MATERIAL_COUNT,\n" );				//实盘数量
//    	sql.append("ABS(NVL(PAPER_COUNT,0)-NVL(MATERIAL_COUNT,0)) AS CYSL ,\n" );		//差异数量
//    	
//    	
//    	sql.append("AMOUNT,\n" );
//    	sql.append("INVENTORY_RESULT,\n" );
//    	sql.append("REMARKS,\n" );
//    	sql.append("PLAN_PRICE*PAPER_COUNT  AS  PAPER_AMOUNT,\n" );					//账面金额
//    	
//    	
//    	sql.append("PLAN_PRICE*MATERIAL_COUNT  AS  MATERIAL_AMOUNT,\n" );				//实盘金额
//    	
//    	
//    	sql.append("ABS(NVL(PAPER_AMOUNT,0)-NVL(MATERIAL_AMOUNT,0)) AS CYJE ,\n" );		//差异金额
//    	
//    	
//    	sql.append("INVENTORY_NO,\n" );
//    	sql.append("STOCK_DTL_ID\n" );
//    	sql.append("FROM PT_BU_INVENTORY_DTL");

    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DTL_ID,\n" );
    	sql.append("       T.INVENTORY_ID,\n" );
    	sql.append("       T.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.AREA_ID,\n" );
    	sql.append("       T.AREA_CODE,\n" );
    	sql.append("       T.AREA_NAME,\n" );
    	sql.append("       T.POSITION_ID,\n" );
    	sql.append("       T.POSITION_CODE,\n" );
    	sql.append("       T.POSITION_NAME,\n" );
    	sql.append("       T.WHOUSE_KEEPER,\n" );
    	sql.append("       T1.PLAN_PRICE,\n" );
    	sql.append("       T.PAPER_COUNT,\n" );
    	sql.append("       T.MATERIAL_COUNT,\n" );
    	sql.append("       ABS(NVL(PAPER_COUNT, 0) - NVL(MATERIAL_COUNT, 0)) AS CYSL,\n" );
    	sql.append("       T.AMOUNT,\n" );
    	sql.append("       T.INVENTORY_RESULT,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T1.PLAN_PRICE * PAPER_COUNT AS PAPER_AMOUNT,\n" );
    	sql.append("       T1.PLAN_PRICE * MATERIAL_COUNT AS MATERIAL_AMOUNT,\n" );
    	sql.append("       ABS(NVL(T1.PLAN_PRICE * PAPER_COUNT, 0) -\n" );
    	sql.append("           NVL(T1.PLAN_PRICE * MATERIAL_COUNT, 0)) AS CYJE,\n" );
    	sql.append("       INVENTORY_NO,\n" );
    	sql.append("       STOCK_DTL_ID\n" );
    	sql.append("  FROM PT_BU_INVENTORY_DTL T, PT_BA_INFO T1\n" );
    	bs = factory.select(sql.toString(), page); 
    	bs.setFieldDic("INVENTORY_RESULT", "PDJG");        
    	return bs;
    }
	//查询配件
	public BaseResultSet partSearch(PageManager page,String conditions,String warehouse_id) throws Exception {
    	String wheres = conditions;  
    	
/*    	wheres += " and  a.stock_id = b.stock_id ";
    	wheres += " and  b.part_id = c.part_id ";
    	wheres += " and  b.part_id = d.part_id ";
    	wheres += " and  a.warehouse_id = '"+warehouse_id+"' ";
    	wheres += " and  d.warehouse_id = '"+warehouse_id+"' ";
//    	wheres += " and  not exists(select d.stock_dtl_id from pt_bu_inventory_dtl d where d.stock_dtl_id=b.dtl_id) ";
*/    	
    	wheres +="AND A.STOCK_ID = B.STOCK_ID\n" +
    					"AND B.PART_ID = C.PART_ID\n" + 
    					"AND B.PART_ID = D.PART_ID\n" + 
    					"AND A.WAREHOUSE_ID = '"+warehouse_id+"'\n" + 
    					"AND D.WAREHOUSE_ID = '"+warehouse_id+"'\n" + 
    					"AND NOT EXISTS (SELECT 1\n" + 
    					"       FROM PT_BU_INVENTORY T, PT_BU_INVENTORY_DTL T1\n" + 
    					"      WHERE T.INVENTORY_ID = T1.INVENTORY_ID\n" + 
     					"        AND T.WAREHOUSE_ID = A.WAREHOUSE_ID\n" + 
    					"        AND T1.POSITION_ID = B.POSITION_ID\n" + 
    					"        AND T1.PART_ID = B.PART_ID\n" + 
    					"        AND NVL(T.INVENTORY_STATUS，0) < "+DicConstant.PDZT_04+")";

    	  	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("select b.dtl_id,\n" );
    	sql.append("       b.part_id,\n" );
    	sql.append("       b.part_code,\n" );
    	sql.append("       b.part_name,\n" );
    	sql.append("       c.if_suply,\n" );
    	sql.append("       c.plan_price,\n" );
    	sql.append("       c.plan_price*b.amount as CCJE,\n" );
    	sql.append("       d.user_name,\n" );
    	sql.append("       b.supplier_id,\n" );
    	sql.append("       b.supplier_code,\n" );
    	sql.append("       b.supplier_name,\n" );
    	sql.append("       b.area_id,\n" );
    	sql.append("       b.area_code,\n" );
    	sql.append("       b.area_name,\n" );
    	sql.append("       b.position_id,\n" );
    	sql.append("       b.position_name,\n" );
    	sql.append("       b.amount\n" );
    	sql.append("  from pt_bu_stock a, pt_bu_stock_dtl b, pt_ba_info c,pt_ba_warehouse_keeper d  \n" );
//    	sql.append("  from pt_bu_stock a, pt_bu_stock_dtl b, pt_ba_info c where a.stock_id = b.stock_id and b.part_id = c.part_id and a.warehouse_id = '"+warehouse_id+"'  and  not exists(select * from pt_bu_inventory_dtl d where d.stock_dtl_id=b.dtl_id)\n" );

    	bs = factory.select(sql.toString(), page); 
    	return bs;
    }
	
	 //配件信息导出
    public QuerySet checkDownload(String conditions,String warehouse_id) throws Exception {	
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("select rownum,\n" );
    	sql.append("       b.dtl_id,\n" );
    	sql.append("       b.part_id,\n" );
    	sql.append("       b.part_code,\n" );
    	sql.append("       b.part_name,\n" );
    	sql.append("       c.if_suply,\n" );
    	sql.append("       c.plan_price,\n" );
    	sql.append("       c.plan_price*b.amount as CCJE,\n" );
    	sql.append("       d.user_name,\n" );
    	sql.append("       b.supplier_id,\n" );
    	sql.append("       b.supplier_code,\n" );
    	sql.append("       b.supplier_name,\n" );
    	sql.append("       b.area_id,\n" );
    	sql.append("       b.area_code,\n" );
    	sql.append("       b.area_name,\n" );
    	sql.append("       b.position_id,\n" );
    	sql.append("       b.position_name,\n" );
    	sql.append("       b.amount\n" );
    	sql.append("  from pt_bu_stock a, pt_bu_stock_dtl b, pt_ba_info c,pt_ba_warehouse_keeper d  \n" );
    	sql.append(" where "+conditions+"\n" );
    	sql.append("  and  a.stock_id = b.stock_id \n" );
    	sql.append("  and  b.part_id = c.part_id \n" );
    	sql.append("  and  b.part_id = d.part_id \n" );
    	sql.append("  and  a.warehouse_id = '"+warehouse_id+"' \n" );
    	sql.append("  and  d.warehouse_id = '"+warehouse_id+"' \n" );
    	

        return factory.select(null, sql.toString());
    }
    
	//批量新增
	public boolean batchInsertPchAttribute(String dtl_id,String inventory_id,String warehouse_id,String inventory_no)
			throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO PT_BU_INVENTORY_DTL \n");
		sql.append(" (DTL_ID, \n");
		sql.append(" INVENTORY_ID, \n");
		sql.append(" PART_ID, \n");
		sql.append(" PART_CODE, \n");
		sql.append(" PART_NAME, \n");
		sql.append(" SUPPLIER_ID, \n");
		sql.append(" SUPPLIER_NAME, \n");
		sql.append(" SUPPLIER_CODE, \n");
		sql.append(" AREA_ID, \n");
		sql.append(" AREA_CODE, \n");
		sql.append(" AREA_NAME, \n");
		sql.append(" POSITION_ID, \n");
		sql.append(" POSITION_CODE, \n");
		sql.append(" POSITION_NAME, \n");
		sql.append(" WHOUSE_KEEPER, \n");
		sql.append(" PAPER_COUNT, \n");
		sql.append(" PLAN_PRICE, \n");
		sql.append(" INVENTORY_NO, \n");
		sql.append(" STOCK_DTL_ID) \n");
		
		sql.append(" SELECT f_getid(), \n");
		sql.append("         '"+inventory_id+"',\n" );
		sql.append("         B.PART_ID,\n" );
		sql.append("         B.PART_CODE,\n" );
		sql.append("         B.PART_NAME,\n" );
		sql.append("         B.SUPPLIER_ID,\n" );
		sql.append("         B.SUPPLIER_NAME,\n" );
		sql.append("         B.SUPPLIER_CODE,\n" );
		sql.append("         B.AREA_ID,\n" );
		sql.append("         B.AREA_CODE,\n" );
		sql.append("         B.AREA_NAME,\n" );
		sql.append("         B.POSITION_ID,\n" );
		sql.append("         B.POSITION_CODE,\n" );
		sql.append("         B.POSITION_NAME,\n" );
		sql.append("         D.USER_NAME,\n" );
		sql.append("         B.AMOUNT,\n" );
		sql.append("         C.PLAN_PRICE,\n" );
		sql.append("         '"+inventory_no+"',\n" );
		sql.append("         B.DTL_ID\n" );
		
		sql.append(" FROM   PT_BU_STOCK A, PT_BU_STOCK_DTL B, PT_BA_INFO C, PT_BA_WAREHOUSE_KEEPER D  \n" );
		sql.append(" WHERE  A.STOCK_ID = B.STOCK_ID  \n" );
		sql.append("   AND  B.PART_ID = C.PART_ID  \n" );
		sql.append("   AND  B.PART_ID = D.PART_ID  \n" );
		sql.append("   AND  A.WAREHOUSE_ID = '"+warehouse_id+"'  \n" );
		sql.append("   AND  D.WAREHOUSE_ID = '"+warehouse_id+"'  \n" );
		sql.append("   AND  B.DTL_ID IN (" + dtl_id + ")  \n" );
		     
		     
		
//		sql.append(" INSERT INTO PT_BU_INVENTORY_DTL(DTL_ID,INVENTORY_ID,PART_ID,PART_CODE,PART_NAME,SUPPLIER_ID,SUPPLIER_NAME,SUPPLIER_CODE,AREA_ID,AREA_CODE,AREA_NAME,POSITION_ID,POSITION_CODE,POSITION_NAME,WHOUSE_KEEPER,PAPER_COUNT,PLAN_PRICE,INVENTORY_NO, STOCK_DTL_ID) \n");
//		sql.append(" SELECT f_getid(),'"+inventory_id+"',B.PART_ID,B.PART_CODE,B.PART_NAME,B.SUPPLIER_ID,B.SUPPLIER_NAME,B.SUPPLIER_CODE,B.AREA_ID,B.AREA_CODE,B.AREA_NAME,B.POSITION_ID,B.POSITION_CODE,B.POSITION_NAME,D.USER_NAME,B.AMOUNT,C.PLAN_PRICE,'"+inventory_no+"', B.DTL_ID \n");
//		sql.append("  from PT_BU_STOCK A, PT_BU_STOCK_DTL B, PT_BA_INFO C, PT_BA_WAREHOUSE_KEEPER D  WHERE A.STOCK_ID = B.STOCK_ID AND B.PART_ID = C.PART_ID  AND  B.PART_ID = D.PART_ID  AND A.WAREHOUSE_ID = '"+warehouse_id+"'  AND D.WAREHOUSE_ID = '"+warehouse_id+"'  AND B.DTL_ID IN (" + dtl_id + ") \n" );
//		
		
		
		return factory.update(sql.toString(), null);
	}
	//批量修改
	public boolean batchUpdate(String dtlId, String materialCount)
	throws Exception {

	StringBuffer sql = new StringBuffer();
	sql.append(" UPDATE PT_BU_INVENTORY_DTL \n");
	sql.append(" SET MATERIAL_COUNT = " + materialCount + " \n");
	sql.append(" WHERE DTL_ID = " + dtlId + " \n");
	return factory.update(sql.toString(), null);
}
	
	 /**
     * 生成盘点编号
     *
     * @param WAREHOUSE_CODE 盘点仓库代码
     * @return
     * @throws Exception
     */
    public String createInventory_No(String WAREHOUSE_CODE) throws Exception {
        return PartOddNumberUtil.getCheckSetUpNo(factory, WAREHOUSE_CODE);
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
	 * 获取库存盘点临时表数据
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public QuerySet searchTmpDtl(User user, String errorInfoRowNum,String id)throws Exception{
		
//		StringBuffer sql= new StringBuffer();
//		sql.append("SELECT\n" );
//		sql.append("TMP_ID,\n" );
//		sql.append("ROW_NUM,\n" );
//		sql.append("PAPER_AMOUNT,\n" );
//		sql.append("MATERIAL_AMOUNT,\n" );
//		sql.append("REMARKS,\n" );
//		sql.append("INVENTORY_ID,\n" );
//		sql.append("DTL_ID,\n" );
//		sql.append("INVENTORY_NO,\n" );
//		sql.append("PART_NAME,\n" );
//		sql.append("USER_ACCOUNT,\n" );
//		sql.append("PART_CODE,\n" );
//		sql.append("SUPPLIER_NAME,\n" );
//		sql.append("AREA_CODE,\n" );
//		sql.append("POSITION_NAME,\n" );
//		sql.append("WHOUSE_KEEPER,\n" );
//		sql.append("PAPER_COUNT,\n" );
//		sql.append("MATERIAL_COUNT,\n" );
//		sql.append("PLAN_PRICE,\n" );
//		sql.append("AMOUNT,\n" );
//		sql.append("INVENTORY_RESULT\n" );
//		sql.append("FROM PT_BU_INVENTORY_DTL_TMP");
//		sql.append(" WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
//		if (!errorInfoRowNum.equals("")) {
//			sql.append(" AND ROW_NUM not in ( "+errorInfoRowNum+")\n" );
//		}
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.DTL_ID, T1.MATERIAL_COUNT, T1.REMARKS,T.PAPER_COUNT\n" );
		sql.append("  FROM PT_BU_INVENTORY_DTL T, PT_BU_INVENTORY_DTL_TMP T1\n" );
		sql.append(" WHERE T.PART_CODE = T1.PART_CODE\n" );
		sql.append("   AND T.POSITION_CODE = T1.POSITION_CODE\n" );
		sql.append("   AND T1.USER_ACCOUNT = 'SUPERMAN'\n" );
		sql.append("   AND T.INVENTORY_ID = "+id+"\n" );
		if(!"".equals(errorInfoRowNum)&&errorInfoRowNum!=null){
			sql.append("   AND T1.ROW_NUM NOT IN ("+errorInfoRowNum+")\n" );
		}
		sql.append("   ORDER BY T1.PART_CODE");
		return factory.select(null, sql.toString());
	}
	//去库存盘点明细零时表重复数据
	public QuerySet searchTmpRepeatData(User user,String dtlId)throws Exception{
		
    	StringBuffer sql= new StringBuffer();
    	if (!dtlId.equals("")) {
	    	sql.append("SELECT A.ROW_NUM, A.DTL_ID\n" );
	    	sql.append("  FROM PT_BU_INVENTORY_DTL_TMP A,\n" );
	    	sql.append("       (" );
    	}
    	sql.append("SELECT DTL_ID,  COUNT(DTL_ID)\n" );
    	sql.append("          FROM PT_BU_INVENTORY_DTL_TMP\n" );
    	sql.append("         WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("         GROUP BY DTL_ID\n" );
    	sql.append("        HAVING COUNT(DTL_ID) > 1" );
    	if (!dtlId.equals("")) {
	    	sql.append(") B\n" );
	    	sql.append(" WHERE A.DTL_ID = B.DTL_ID\n" );
	    	sql.append("   AND A.DTL_ID = '"+dtlId+"'\n" );
	    	sql.append("   AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	    	sql.append("   ORDER BY A.ROW_NUM\n" );
    	}
		return factory.select(null, sql.toString());
	}
	
	//去盘点零时表重复数据
	public QuerySet searchCheckTmpRepeatData(User user,String dtlId)throws Exception{
		
    	StringBuffer sql= new StringBuffer();
    	if (!dtlId.equals("")) {
	    	sql.append("SELECT A.ROW_NUM, A.DTL_ID\n" );
	    	sql.append("  FROM PT_BU_INVENTORY_TMP A,\n" );
	    	sql.append("       (" );
    	}
    	sql.append("SELECT DTL_ID,  COUNT(DTL_ID)\n" );
    	sql.append("          FROM PT_BU_INVENTORY_TMP\n" );
    	sql.append("         WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("         GROUP BY DTL_ID\n" );
    	sql.append("        HAVING COUNT(DTL_ID) > 1" );
    	if (!dtlId.equals("")) {
	    	sql.append(") B\n" );
	    	sql.append(" WHERE A.DTL_ID = B.DTL_ID\n" );
	    	sql.append("   AND A.DTL_ID = '"+dtlId+"'\n" );
	    	sql.append("   AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	    	sql.append("   ORDER BY A.ROW_NUM\n" );
    	}
		return factory.select(null, sql.toString());
	}
	
	
	//将零时表正确的数据显示到页面
	public BaseResultSet searchTmpImport(PageManager page, String conditions, User user,String ID)throws Exception{
		String wheres = conditions;
//    	wheres += " AND USER_ACCOUNT = '"+user.getAccount()+"'";
//    	wheres += " order by DTL_ID desc ";
//    	page.setFilter(wheres);
    	BaseResultSet bs = null;
    	
//		StringBuffer sql= new StringBuffer();
//		sql.append("SELECT\n" );
//		sql.append("TMP_ID,\n" );
//		sql.append("ROW_NUM,\n" );
//		sql.append("PAPER_AMOUNT,\n" );
//		sql.append("MATERIAL_AMOUNT,\n" );
//		sql.append("REMARKS,\n" );
//		sql.append("INVENTORY_ID,\n" );
//		sql.append("DTL_ID,\n" );
//		sql.append("INVENTORY_NO,\n" );
//		sql.append("PART_NAME,\n" );
//		sql.append("USER_ACCOUNT,\n" );
//		sql.append("PART_CODE,\n" );
//		sql.append("SUPPLIER_NAME,\n" );
//		sql.append("AREA_CODE,\n" );
//		sql.append("POSITION_NAME,\n" );
//		sql.append("WHOUSE_KEEPER,\n" );
//		sql.append("PAPER_COUNT,\n" );
//		sql.append("MATERIAL_COUNT,\n" );
//		sql.append("PLAN_PRICE,\n" );
//		sql.append("AMOUNT,\n" );
//		sql.append("INVENTORY_RESULT\n" );
//		sql.append("FROM PT_BU_INVENTORY_DTL_TMP");
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.TMP_ID,\n" );
    	sql.append("       T.ROW_NUM,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.POSITION_CODE,\n" );
    	sql.append("       T1.USER_NAME WHOUSE_KEEPER,\n" );
    	sql.append("       T2.PAPER_COUNT,\n" );
    	sql.append("       T.MATERIAL_COUNT,\n" );
    	sql.append("       T3.PLAN_PRICE\n" );
    	sql.append("  FROM PT_BU_INVENTORY_DTL_TMP T,\n" );
    	sql.append("       PT_BA_WAREHOUSE_KEEPER  T1,\n" );
    	sql.append("       PT_BU_INVENTORY_DTL     T2,\n" );
    	sql.append("       PT_BA_INFO              T3,\n" );
    	sql.append("       PT_BU_INVENTORY         T4\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND T.PART_CODE = T1.PART_CODE\n" );
    	sql.append("   AND T.PART_CODE = T2.PART_CODE\n" );
    	sql.append("   AND T.POSITION_CODE = T2.POSITION_CODE\n" );
    	sql.append("   AND T1.WAREHOUSE_ID = T4.WAREHOUSE_ID\n" );
    	sql.append("   AND T2.INVENTORY_ID = T4.INVENTORY_ID\n" );
    	sql.append("   AND T.PART_CODE = T3.PART_CODE\n" );
    	sql.append("   AND T2.INVENTORY_ID = "+ID+"\n" );
    	sql.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append(" order by T.PART_CODE desc");
		bs=factory.select(sql.toString(), page);
		return bs;
	}
	
	//根据DTL_ID获得PAPER_COUNT
	public QuerySet getDate(String dtl_id) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT PAPER_COUNT \n");
    	sql.append(" FROM PT_BU_INVENTORY_DTL \n");
    	sql.append(" WHERE DTL_ID = '" + dtl_id +"'\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	//根据INVENTORY_ID获得WAREHOUSE_ID和INVENTORY_NO
	public QuerySet getInventoryDate(String inventory_id) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT INVENTORY_ID,INVENTORY_NO,WAREHOUSE_ID \n");
    	sql.append(" FROM PT_BU_INVENTORY \n");
    	sql.append(" WHERE INVENTORY_ID = '" + inventory_id +"'\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	//根据DTL_ID获得PAPER_COUNT
	public QuerySet getDtlId(String dtl_id) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT DTL_ID \n");
    	sql.append(" FROM PT_BU_STOCK_DTL \n");
    	sql.append(" WHERE DTL_ID = '" + dtl_id +"'\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	//库存明细错误信息导出
	public QuerySet expTmpErrorData(String pConditions,User user) throws Exception {
		String wheres = " WHERE ROW_NUM IN ("+ pConditions + ") \n";
    	wheres += " AND USER_ACCOUNT='"+user.getAccount()+"' \n";
    	wheres += " ORDER BY ROW_NUM \n";
    	
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT ROW_NUM,\n" );
    	sql.append("DTL_ID,\n" );
    	sql.append("INVENTORY_NO,\n" );
    	sql.append("WHOUSE_KEEPER,\n" );
    	sql.append("AREA_CODE,\n" );
    	sql.append("POSITION_CODE,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("SUPPLIER_CODE,\n" );
    	sql.append("PLAN_PRICE,\n" );
    	sql.append("PAPER_COUNT,\n" );
    	sql.append("MATERIAL_COUNT,\n" );
    	sql.append("REMARKS\n" );
    	
    	sql.append("FROM PT_BU_INVENTORY_DTL_TMP");

    	return factory.select(null, sql.toString()+wheres);
    }
	 //库存盘点表导出
  public QuerySet download(String inventory_id) throws Exception {
  	StringBuffer sql= new StringBuffer();
  	sql.append("SELECT ROWNUM,\n" );
  	sql.append("PAPER_AMOUNT,\n" );					//账面金额
  	sql.append("MATERIAL_AMOUNT,\n" );				//实物金额
  	sql.append("INVENTORY_NO,\n" );
  	sql.append("STOCK_DTL_ID,\n" );
  	sql.append("DTL_ID,\n" );
  	sql.append("INVENTORY_ID,\n" );
  	sql.append("PART_ID,\n" );
  	sql.append("PART_CODE,\n" );
  	sql.append("PART_NAME,\n" );
  	sql.append("SUPPLIER_ID,\n" );
  	sql.append("SUPPLIER_NAME,\n" );
  	sql.append("SUPPLIER_CODE,\n" );
  	sql.append("AREA_ID,\n" );
  	sql.append("AREA_CODE,\n" );
  	sql.append("AREA_NAME,\n" );
  	sql.append("POSITION_ID,\n" );
  	sql.append("POSITION_CODE,\n" );
  	sql.append("POSITION_NAME,\n" );
  	sql.append("WHOUSE_KEEPER,\n" );
  	sql.append("PAPER_COUNT,\n" );					//账面数量
  	sql.append("MATERIAL_COUNT,\n" );				//实物数量	
  	sql.append("PLAN_PRICE,\n" );
  	sql.append("AMOUNT,\n" );
  	sql.append("INVENTORY_RESULT,\n" );
  	sql.append("REMARKS\n" );
  	sql.append("FROM PT_BU_INVENTORY_DTL");
  	sql.append(" WHERE INVENTORY_ID ='"+inventory_id+"' ORDER BY PART_CODE\n");

      //执行方法，不需要传递conn参数
      return factory.select(null, sql.toString());
  }
		
	/**
	 * 库存明细信息新增
	 * @throws Exception
     * @Author suoxiuli 2014-07-25
	 */
	public boolean insertSafeStockImport(PtBuInventoryDtlVO vo) throws Exception
    {
		return factory.insert(vo);
    }
	
	
	//获取零时表信息
	public QuerySet searchTmp(User user, String errorInfoRowNum)throws Exception{
		
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT\n" );
		sql.append("AMOUNT,\n" );
		sql.append("TMP_ID,\n" );
		sql.append("DTL_ID,\n" );
		sql.append("USER_ACCOUNT,\n" );
		sql.append("ROW_NUM,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("SUPPLIER_NAME,\n" );
		sql.append("AREA_CODE,\n" );
		sql.append("POSITION_NAME,\n" );
		sql.append("WHOUSE_KEEPER,\n" );
		sql.append("PLAN_PRICE\n" );
		sql.append("FROM PT_BU_INVENTORY_TMP");

		sql.append(" WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		if (!errorInfoRowNum.equals("")) {
			sql.append(" AND ROW_NUM not in ( "+errorInfoRowNum+")\n" );
		}
		return factory.select(null, sql.toString());
	}
	//将零时表正确的数据显示到页面
	public BaseResultSet searchCheckTmpImport(PageManager page, String conditions, User user)throws Exception{
		String wheres = conditions;
    	wheres += " AND USER_ACCOUNT = '"+user.getAccount()+"'";
    	wheres += " order by DTL_ID desc ";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
    	
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT\n" );
		sql.append("AMOUNT,\n" );
		sql.append("TMP_ID,\n" );
		sql.append("DTL_ID,\n" );
		sql.append("USER_ACCOUNT,\n" );
		sql.append("ROW_NUM,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("SUPPLIER_NAME,\n" );
		sql.append("AREA_CODE,\n" );
		sql.append("POSITION_NAME,\n" );
		sql.append("WHOUSE_KEEPER,\n" );
		sql.append("PLAN_PRICE\n" );
		sql.append("FROM PT_BU_INVENTORY_TMP");
		
		bs=factory.select(sql.toString(), page);
		return bs;
	}
	//配件采购价格错误信息查询
	public QuerySet expCheckTmpErrorData(String pConditions,User user) throws Exception {
		String wheres = " WHERE ROW_NUM IN ("+ pConditions + ") \n";
    	wheres += " AND USER_ACCOUNT='"+user.getAccount()+"' \n";
    	wheres += " ORDER BY ROW_NUM \n";
    	
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT ROW_NUM,\n" );
    	sql.append("AMOUNT,\n" );
		sql.append("TMP_ID,\n" );
		sql.append("DTL_ID,\n" );
		sql.append("USER_ACCOUNT,\n" );
		sql.append("ROW_NUM,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("SUPPLIER_NAME,\n" );
		sql.append("AREA_CODE,\n" );
		sql.append("POSITION_NAME,\n" );
		sql.append("WHOUSE_KEEPER,\n" );
		sql.append("PLAN_PRICE\n" );
    	sql.append("FROM PT_BU_INVENTORY_TMP");

    	return factory.select(null, sql.toString()+wheres);
    }
	//库存盘点状态调整,从配件库存盘点设置明细表中获取库存明细主键
	public QuerySet getStockDtlId(String inventory_id)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT\n" );
		sql.append("STOCK_DTL_ID,\n" );
		sql.append("MATERIAL_COUNT\n" );
		sql.append("FROM PT_BU_INVENTORY_DTL");
		sql.append(" WHERE INVENTORY_ID = '" + inventory_id +"'\n");
		return factory.select(null, sql.toString());
	}
	//库存盘点状态调整,根据库存明细主键获得库存主键
	public String getStockId(String dtl_id) throws Exception
    {
		QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT STOCK_ID \n");
    	sql.append(" FROM PT_BU_STOCK_DTL \n");
    	sql.append(" WHERE DTL_ID = '" + dtl_id +"'\n");
    	qs = factory.select(null, sql.toString());
    	return qs.getString(1, "STOCK_ID");
    }
	//库存盘点状态调整,根据库存主键获得库存占用数量
	public String getAvailableAmount(String stock_id) throws Exception
    {
		QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT OCCUPY_AMOUNT \n");
    	sql.append(" FROM PT_BU_STOCK \n");
    	sql.append(" WHERE STOCK_ID = '" + stock_id +"'\n");
    	qs = factory.select(null, sql.toString());
    	return qs.getString(1, "OCCUPY_AMOUNT");
    }
	//根据实际盘点数量修改库存明细账面数量
	public boolean updateStockDtlAmont(PtBuStockDtlVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	//根据实际盘点数量修改库存账面数量
	public boolean updateStockAmont(PtBuStockVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	public boolean updatePaperCount(String inventory_id) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_INVENTORY_DTL T\n" );
		sql.append("   SET T.PAPER_COUNT =\n" );
		sql.append("       (SELECT T1.AMOUNT\n" );
		sql.append("          FROM PT_BU_STOCK_DTL T1, PT_BU_STOCK T2, PT_BU_INVENTORY T3\n" );
		sql.append("         WHERE T.PART_ID = T1.PART_ID\n" );
		sql.append("           AND T.POSITION_ID = T1.POSITION_ID\n" );
		sql.append("           AND T1.STOCK_ID = T2.STOCK_ID\n" );
		sql.append("           AND T2.WAREHOUSE_ID = T3.WAREHOUSE_ID\n" );
		sql.append("           AND T.INVENTORY_ID = T3.INVENTORY_ID)\n" );
		sql.append(" WHERE T.INVENTORY_ID = "+inventory_id+"");
    	return factory.update(sql.toString(), null);
    }
	public boolean lockStock(String inventory_id) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_STOCK T\n" );
		sql.append("   SET T.STOCK_STATUS = "+DicConstant.KCZT_03+"\n" );
		sql.append(" WHERE 1=1 AND EXISTS\n" );
		sql.append("       (SELECT 1\n" );
		sql.append("          FROM PT_BU_INVENTORY T2, PT_BU_INVENTORY_DTL T3\n" );
		sql.append("         WHERE T2.INVENTORY_ID = T3.INVENTORY_ID\n" );
		sql.append("           AND T.WAREHOUSE_ID = T2.WAREHOUSE_ID\n" );
		sql.append("           AND T.PART_ID = T3.PART_ID\n" );
		sql.append("           AND T2.INVENTORY_ID = "+inventory_id+")");
    	return factory.update(sql.toString(), null);
    }
	public boolean unlockStock(String inventory_id) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_STOCK T\n" );
		sql.append("   SET T.STOCK_STATUS = "+DicConstant.KCZT_01+"\n" );
		sql.append(" WHERE 1=1 AND EXISTS\n" );
		sql.append("       (SELECT 1\n" );
		sql.append("          FROM PT_BU_INVENTORY T2, PT_BU_INVENTORY_DTL T3\n" );
		sql.append("         WHERE T2.INVENTORY_ID = T3.INVENTORY_ID\n" );
		sql.append("           AND T.WAREHOUSE_ID = T2.WAREHOUSE_ID\n" );
		sql.append("           AND T.PART_ID = T3.PART_ID\n" );
		sql.append("           AND T2.INVENTORY_ID = "+inventory_id+")");
    	return factory.update(sql.toString(), null);
    }
	public boolean updateAmount(String inventory_id) throws Exception
    {
		StringBuffer sql= new StringBuffer();
		sql.append("UPDATE PT_BU_INVENTORY_DTL T\n" );
		sql.append("   SET T.PLAN_PRICE     =\n" );
		sql.append("       (SELECT PLAN_PRICE FROM PT_BA_INFO T1 WHERE T.PART_ID = T1.PART_ID),\n" );
		sql.append("       T.PAPER_AMOUNT    = T.PAPER_COUNT *\n" );
		sql.append("                           (SELECT PLAN_PRICE\n" );
		sql.append("                              FROM PT_BA_INFO T1\n" );
		sql.append("                             WHERE T.PART_ID = T1.PART_ID),\n" );
		sql.append("       T.MATERIAL_AMOUNT = T.MATERIAL_COUNT *\n" );
		sql.append("                           (SELECT PLAN_PRICE\n" );
		sql.append("                              FROM PT_BA_INFO T1\n" );
		sql.append("                             WHERE T.PART_ID = T1.PART_ID)\n" );
		sql.append(" WHERE T.INVENTORY_ID = "+inventory_id+"");
    	return factory.update(sql.toString(), null);
    }
	public QuerySet modDownload(String inventory_id,String conditions) throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.DTL_ID,\n" );
		sql.append("       T.INVENTORY_ID,\n" );
		sql.append("       T.PART_ID,\n" );
		sql.append("       T.PART_CODE,\n" );
		sql.append("       T.PART_NAME,\n" );
		sql.append("       T.SUPPLIER_ID,\n" );
		sql.append("       T.SUPPLIER_NAME,\n" );
		sql.append("       T.SUPPLIER_CODE,\n" );
		sql.append("       T.AREA_ID,\n" );
		sql.append("       T.AREA_CODE,\n" );
		sql.append("       T.AREA_NAME,\n" );
		sql.append("       T.POSITION_ID,\n" );
		sql.append("       T.POSITION_CODE,\n" );
		sql.append("       T.POSITION_NAME,\n" );
		sql.append("       T.WHOUSE_KEEPER,\n" );
		sql.append("       T1.PLAN_PRICE,\n" );
		sql.append("       T.PAPER_COUNT,\n" );
		sql.append("       T.MATERIAL_COUNT,\n" );
		sql.append("       ABS(NVL(PAPER_COUNT, 0) - NVL(MATERIAL_COUNT, 0)) AS CYSL,\n" );
		sql.append("       T.AMOUNT,\n" );
		sql.append("       T2.DIC_VALUE INVENTORY_RESULT,\n" );
		sql.append("       T.REMARKS,\n" );
		sql.append("       T1.PLAN_PRICE * PAPER_COUNT AS PAPER_AMOUNT,\n" );
		sql.append("       T1.PLAN_PRICE * MATERIAL_COUNT AS MATERIAL_AMOUNT,\n" );
		sql.append("       ABS(NVL(T1.PLAN_PRICE * PAPER_COUNT, 0) -\n" );
		sql.append("           NVL(T1.PLAN_PRICE * MATERIAL_COUNT, 0)) AS CYJE,\n" );
		sql.append("       INVENTORY_NO,\n" );
		sql.append("       STOCK_DTL_ID\n" );
		sql.append("  FROM PT_BU_INVENTORY_DTL T, PT_BA_INFO T1, DIC_TREE T2\n" );
		sql.append(" WHERE "+conditions+"\n" );
		sql.append("   AND T.PART_ID = T1.PART_ID\n" );
		sql.append("   AND T.INVENTORY_RESULT = T2.ID\n" );
		sql.append("   AND INVENTORY_ID = '"+inventory_id+"'\n" );
		sql.append(" ORDER BY T.PART_CODE DESC");

	      //执行方法，不需要传递conn参数
	      return factory.select(null, sql.toString());
	  }
	
	public QuerySet getNo(String inventory_id) throws Exception {
	  	StringBuffer sql= new StringBuffer();

	  	sql.append("SELECT INVENTORY_NO FROM PT_BU_INVENTORY WHERE INVENTORY_ID = "+inventory_id+"");

	      return factory.select(null, sql.toString());
	  }
	
	public BaseResultSet partCountSearch(PageManager page,String conditions,String inventory_id) throws Exception {
    	String wheres = conditions;  
    	wheres +=("   AND T.PART_ID = T1.PART_ID\n" );
    	wheres +=("   AND INVENTORY_ID = '"+inventory_id+"'\n" );
    	wheres +=(" ORDER BY T.PART_CODE DESC");
    	  	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;

    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DTL_ID,\n" );
    	sql.append("       T.INVENTORY_ID,\n" );
    	sql.append("       T.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.AREA_ID,\n" );
    	sql.append("       T.AREA_CODE,\n" );
    	sql.append("       T.AREA_NAME,\n" );
    	sql.append("       T.POSITION_ID,\n" );
    	sql.append("       T.POSITION_CODE,\n" );
    	sql.append("       T.POSITION_NAME,\n" );
    	sql.append("       T.WHOUSE_KEEPER,\n" );
    	sql.append("       T1.PLAN_PRICE,\n" );
    	sql.append("       T.PAPER_COUNT,\n" );
    	sql.append("       NVL(T.MATERIAL_COUNT,0) MATERIAL_COUNT,\n" );
    	sql.append("       ABS(NVL(PAPER_COUNT, 0) - NVL(MATERIAL_COUNT, 0)) AS CYSL,\n" );
    	sql.append("       T.AMOUNT,\n" );
    	sql.append("       T.INVENTORY_RESULT,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T1.PLAN_PRICE * PAPER_COUNT AS PAPER_AMOUNT,\n" );
    	sql.append("       T1.PLAN_PRICE * MATERIAL_COUNT AS MATERIAL_AMOUNT,\n" );
    	sql.append("       ABS(NVL(T1.PLAN_PRICE * PAPER_COUNT, 0) -\n" );
    	sql.append("           NVL(T1.PLAN_PRICE * MATERIAL_COUNT, 0)) AS CYJE,\n" );
    	sql.append("       INVENTORY_NO,\n" );
    	sql.append("       STOCK_DTL_ID\n" );
    	sql.append("  FROM PT_BU_INVENTORY_DTL T, PT_BA_INFO T1\n" );
    	bs = factory.select(sql.toString(), page); 
    	bs.setFieldDic("INVENTORY_RESULT", "PDJG");        
    	return bs;
    }
	
	public QuerySet dtlDownload(String inventory_id) throws Exception {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.DTL_ID,\n" );
		sql.append("       T.INVENTORY_ID,\n" );
		sql.append("       T.PART_ID,\n" );
		sql.append("       T.PART_CODE,\n" );
		sql.append("       T.PART_NAME,\n" );
		sql.append("       T.SUPPLIER_ID,\n" );
		sql.append("       T.SUPPLIER_NAME,\n" );
		sql.append("       T.SUPPLIER_CODE,\n" );
		sql.append("       T.AREA_ID,\n" );
		sql.append("       T.AREA_CODE,\n" );
		sql.append("       T.AREA_NAME,\n" );
		sql.append("       T.POSITION_ID,\n" );
		sql.append("       T.POSITION_CODE,\n" );
		sql.append("       T.POSITION_NAME,\n" );
		sql.append("       T.WHOUSE_KEEPER,\n" );
		sql.append("       T.PLAN_PRICE,\n" );
		sql.append("       T.PAPER_COUNT,\n" );
		sql.append("       T.MATERIAL_COUNT,\n" );
		sql.append("       ABS(NVL(PAPER_COUNT, 0) - NVL(MATERIAL_COUNT, 0)) AS CYSL,\n" );
		sql.append("       T.AMOUNT,\n" );
		sql.append("       T2.DIC_VALUE INVENTORY_RESULT,\n" );
		sql.append("       T.REMARKS,\n" );
		sql.append("       T.PLAN_PRICE * PAPER_COUNT AS PAPER_AMOUNT,\n" );
		sql.append("       T.PLAN_PRICE * MATERIAL_COUNT AS MATERIAL_AMOUNT,\n" );
		sql.append("       ABS(NVL(T.PLAN_PRICE * PAPER_COUNT, 0) -\n" );
		sql.append("           NVL(T.PLAN_PRICE * MATERIAL_COUNT, 0)) AS CYJE,\n" );
		sql.append("       INVENTORY_NO,\n" );
		sql.append("       STOCK_DTL_ID\n" );
		sql.append("  FROM PT_BU_INVENTORY_DTL T, DIC_TREE T2\n" );
		sql.append(" WHERE 1=1\n" );
		sql.append("   AND T.INVENTORY_RESULT = T2.ID\n" );
		sql.append("   AND INVENTORY_ID = '"+inventory_id+"'\n" );
		sql.append(" ORDER BY T.PART_CODE DESC");
	      //执行方法，不需要传递conn参数
	      return factory.select(null, sql.toString());
	  }
}
