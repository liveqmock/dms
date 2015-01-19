package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBaInfoVO;
import com.org.dms.vo.part.PtBaPartSupplierRlVO;
import com.org.dms.vo.part.PtBaPriceLogVO;
import com.org.dms.vo.part.PtBaTransportAddressVO;
import com.org.dms.vo.part.PtBuInventoryDtlVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PtBaInfoDao extends BaseDAO {
	// 定义instance
	public static final PtBaInfoDao getInstance(ActionContext atx) {
		PtBaInfoDao dao = new PtBaInfoDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}
	//判断配件代码是否存在
	public QuerySet checkPart_code(String part_code) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM PT_BA_INFO \n");
    	sql.append(" WHERE PART_CODE = '" + part_code +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	//插入信息
	public boolean insertPtBaInfo(PtBaInfoVO vo) throws Exception {
		return factory.insert(vo);
	}
	//插入价格轨迹
	public boolean insertPtBaPriceLog(PtBaPriceLogVO vo) throws Exception {
		return factory.insert(vo);
	}
	//插入军品价格轨迹信息
	public boolean insertArmyPriceLog(PtBaPriceLogVO vo) throws Exception {
		return factory.insert(vo);
	}
	
	//修改信息
	public boolean updatePtBaInfo(PtBaInfoVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	
	public QuerySet selectDealerStock(String partId) throws Exception
    {
    	String sql = "select * from pt_bu_dealer_stock where part_id ="+partId+" and amount >0";
    	QuerySet qs = factory.select(null, sql);
    	return qs;
    }
	
	public QuerySet selectStock(String partId) throws Exception
    {
		String sql = "select * from pt_bu_stock where part_id = "+partId+" and amount>0";
		QuerySet qs = factory.select(null, sql);
    	return qs;
    }
	//更新档案价格信息
//	public boolean updatePtBaInfoPrice(String part_id,String sale_price,String plan_price,String retail_price) throws Exception
//    {
//		StringBuffer sql = new StringBuffer();
//    	sql.append(" UPDATE PT_BA_INFO \n");
//    	sql.append(" SET SALE_PRICE = " + sale_price + ",  PLAN_PRICE="+plan_price+",RETAIL_PRICE="+retail_price+"  \n");
//    	sql.append(" WHERE PART_ID = " + part_id + " \n");
//    	return factory.update(sql.toString(), null);
//    }
	public boolean updatePtBaInfoPrice(PtBaInfoVO vo) throws Exception
    {
		return factory.update(vo);
    }
	public QuerySet queryStock(String partId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT S.WAREHOUSE_CODE,\n" );
    	sql.append("       S.AMOUNT, --库存数\n" );
    	sql.append("       T.OUT_AMOUNT\n" );
    	sql.append("  FROM (SELECT PART_ID, WAREHOUSE_ID, WAREHOUSE_CODE, AMOUNT\n" );
    	sql.append("          FROM PT_BU_STOCK\n" );
//    	sql.append("         WHERE WAREHOUSE_CODE IN ('K000001',\n" );
//    	sql.append("                                  'K000002',\n" );
//    	sql.append("                                  'K000003',\n" );
//    	sql.append("                                  'K000004',\n" );
//    	sql.append("                                  'K000005',\n" );
//    	sql.append("                                  'K000006',\n" );
//    	sql.append("                                  'K000007') AND PART_ID ="+partId+") S\n" );
    	sql.append("       WHERE PART_ID ="+partId+") S\n");
    	sql.append("  LEFT JOIN (SELECT D.PART_ID, O.WAREHOUSE_ID, SUM(D.OUT_AMOUNT) OUT_AMOUNT --在途数\n" );
    	sql.append("               FROM PT_BU_STOCK_OUT O, PT_BU_STOCK_OUT_DTL D\n" );
    	sql.append("              WHERE O.OUT_ID = D.OUT_ID\n" );
    	sql.append("                AND O.OUT_STATUS = 201602 --已出库\n" );
    	sql.append("                AND O.ORDER_ID IN\n" );
    	sql.append("                    (SELECT S.ORDER_ID\n" );
    	sql.append("                       FROM PT_BU_SALE_ORDER S\n" );
    	sql.append("                      WHERE S.ORDER_STATUS = 202203 --审核通过\n" );
    	sql.append("                        AND S.SHIP_STATUS >= 204803 --已发料\n" );
    	sql.append("                        AND S.CLOSE_DATE IS NULL)\n" );
    	sql.append("              GROUP BY D.PART_ID, O.WAREHOUSE_ID) T\n" );
    	sql.append("    ON (S.PART_ID = T.PART_ID AND S.WAREHOUSE_ID = T.WAREHOUSE_ID)");

    	return factory.select(null, sql.toString());
    }
	
	//修改零售价格
	public boolean updateRetailPrice(String part_id, String retail_price) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_INFO \n");
    	sql.append(" SET RETAIL_PRICE = " + retail_price + " \n");
    	sql.append(" WHERE PART_ID = " + part_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	//修改军品价格
	public boolean updateArmyPrice(String part_id, String army_price) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_INFO \n");
    	sql.append(" SET ARMY_PRICE = " + army_price + " \n");
    	sql.append(" WHERE PART_ID = " + part_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	//修改销售价格
	public boolean updateSalePrice(String part_id, String sale_price) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_INFO \n");
    	sql.append(" SET SALE_PRICE = " + sale_price + " \n");
    	sql.append(" WHERE PART_ID = " + part_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	//修改服务索赔价格
	public boolean updateSeClPrice(String part_id, String se_clprice) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_INFO \n");
    	sql.append(" SET SE_CLPRICE = " + se_clprice + " \n");
    	sql.append(" WHERE PART_ID = " + part_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	//修改服务追偿价格
	public boolean updateSeRePrice(String part_id, String se_reprice) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_INFO \n");
    	sql.append(" SET SE_REPRICE = " + se_reprice + " \n");
    	sql.append(" WHERE PART_ID = " + part_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	//修改计划价格
	public boolean updatePlanPrice(String part_id, String plan_price) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_INFO \n");
    	sql.append(" SET PLAN_PRICE = " + plan_price + " \n");
    	sql.append(" WHERE PART_ID = " + part_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	//删除配件信息
	public boolean updatePtBaInfoStatus(String partid, String part_status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_INFO \n");
    	sql.append(" SET PART_STATUS = " + part_status + " \n");
    	sql.append(" WHERE PART_ID = " + partid + " \n");
    	return factory.update(sql.toString(), null);
    }
	//删除配件服务信息
	public boolean updatePtBaInfoServiceStatus(String partid, String se_status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_INFO \n");
    	sql.append(" SET SE_STATUS = " + se_status + " \n");
    	sql.append(" WHERE PART_ID = " + partid + " \n");
    	return factory.update(sql.toString(), null);
    }
	//服务查询
	public BaseResultSet searchService(PageManager page,String conditions) throws Exception
    {
		String wheres = conditions;  
		wheres += " ORDER BY CREATE_TIME DESC,PART_ID DESC";
		
		
		page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
		
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT\n" );
		sql.append("PART_ID,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("PART_TYPE,\n" );
		sql.append("IF_RETURN,\n" );
		sql.append("ATTRIBUTE,\n" );
		sql.append("PART_NO,\n" );
		sql.append("POSITION_ID,\n" );
		sql.append("POSITION_CODE,\n" );
		sql.append("POSITION_NAME,\n" );
		sql.append("F_POSITION_ID,\n" );
		sql.append("F_POSITION_CODE,\n" );
		sql.append("F_POSITION_NAME,\n" );
		sql.append("IF_OUT,\n" );
		sql.append("UNIT,\n" );
		sql.append("IF_SCAN,\n" );
		sql.append("IF_WORK_MULTIPLE,\n" );
		sql.append("PART_STATUS,\n" );
		sql.append("SE_STATUS,\n" );
		sql.append("REMARKS,\n" );
		sql.append("UPDATE_USER,\n" );
		sql.append("BRIDGE_STATUS,\n" );
		sql.append("UPDATE_TIME\n" );
		sql.append("FROM PT_BA_INFO");

		
		
		
		//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//翻译字典值	  
    	bs.setFieldDic("UNIT", "JLDW");			
        bs.setFieldDic("PART_TYPE", "PJLB");        
        bs.setFieldDic("ATTRIBUTE", "PJSX");        
        bs.setFieldDic("BRIDGE_STATUS", "SF");        
        //bs.setFieldDic("MIN_UNIT", "JLDW");         
        
        bs.setFieldDic("IF_DIRECT", "SF");   
        bs.setFieldDic("IF_OUT", "SF");   
        bs.setFieldDic("IF_BOOK", "SF");   
        bs.setFieldDic("IF_RETURN", "SF");   //是否回运
        bs.setFieldDic("IF_OIL", "SF");   //是否油品
        bs.setFieldDic("IF_WORK_MULTIPLE", "SF");   //是否工时倍数
        
        
        bs.setFieldDic("IF_SCAN", "SF");   	//是否扫码件
        
        bs.setFieldDic("PART_STATUS", "PJZT");   //配件状态
        bs.setFieldDic("SE_STATUS", "YXBS");   	 //服务状态
        
        //bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:MM:SS");
		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
		//bs.setFieldDateFormat("PRICE_TIME", "yyyy-MM-dd HH:MM:SS");
         
    	return bs;
    }
	//配件查询
	public BaseResultSet search(PageManager page,String conditions) throws Exception
    {
    	String wheres = conditions;  
//    	wheres += " AND STATUS=100201 AND PART_STATUS NOT IN(200602)";
//    	wheres += " AND STATUS=100201";
    	wheres += " ORDER BY CREATE_TIME DESC,PART_ID DESC";
    	
	
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("SPE_NAME,\n" );
    	sql.append("POSITION_ID,\n" );
    	sql.append("POSITION_NAME,\n" );
    	sql.append("POSITION_CODE,\n" );
    	sql.append("F_POSITION_ID,\n" );
    	sql.append("F_POSITION_CODE,\n" );
    	sql.append("F_POSITION_NAME,\n" );
    	sql.append("PRICE_USER,\n" );
    	sql.append("REBATE_PARAVALUE1,\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("PART_NO,\n" );
    	sql.append("UNIT,\n" );
    	sql.append("PART_TYPE,\n" );
    	sql.append("ATTRIBUTE,\n" );
    	sql.append("MIN_PACK,\n" );
    	sql.append("MIN_UNIT,\n" );
    	sql.append("IF_DIRECT,\n" );
    	sql.append("IF_OUT,\n" );
    	sql.append("IF_BOOK,\n" );
    	sql.append("IF_RETURN,\n" );
    	sql.append("IF_ASSEMBLY,\n" );
    	sql.append("BELONG_ASSEMBLY,\n" );
    	sql.append("IF_SCAN,\n" );
    	sql.append("IF_SUPLY,\n" );
    	sql.append("PART_STATUS,\n" );
    	sql.append("IF_STREAM,\n" );
    	sql.append("REMARKS,\n" );
    	sql.append("SALE_PRICE,\n" );
    	sql.append("ARMY_PRICE,\n" );
    	sql.append("PLAN_PRICE,\n" );
    	sql.append("COMPANY_ID,\n" );
    	sql.append("ORG_ID,\n" );
    	sql.append("CREATE_USER,\n" );
    	sql.append("CREATE_TIME,\n" );
    	sql.append("UPDATE_USER,\n" );
    	sql.append("UPDATE_TIME,\n" );
    	sql.append("STATUS,\n" );
    	sql.append("PCH_PRICE,\n" );
    	sql.append("SE_STATUS,\n" );
    	sql.append("IF_SELF,\n" );
    	sql.append("IF_OIL,\n" );
    	sql.append("SE_UNIT,\n" );
    	sql.append("SE_CLPRICE,\n" );
    	sql.append("SE_REPRICE,\n" );
    	sql.append("F_PART_ID,\n" );
    	sql.append("F_PART_CODE,\n" );
    	sql.append("F_PART_NAME,\n" );
    	sql.append("IF_OUT_BUY,\n" );
    	sql.append("REBATE_TYPE,\n" );
    	sql.append("WEIGHT,\n" );
    	sql.append("P_SPECI,\n" );
    	sql.append("SPE_TYPE,\n" );
    	sql.append("OLD_MANAGE_FEE,\n" );
    	sql.append("RETAIL_PRICE,\n" );
    	sql.append("VEHICLE_MAX,\n" );
    	sql.append("DIRECT_TYPE_ID,\n" );
    	sql.append("DIRECT_TYPE_CODE,\n" );
    	sql.append("DIRECT_TYPE_NAME,\n" );
    	sql.append("PRICE_TIME\n" );
    	sql.append("FROM PT_BA_INFO");

    	

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，对于配件名称字典（PART_NAME），将字典代码翻译为字典名称	  
    	bs.setFieldDic("UNIT", "JLDW");
        bs.setFieldDic("PART_TYPE", "PJLB");        
        bs.setFieldDic("ATTRIBUTE", "PJSX");        
        bs.setFieldDic("MIN_UNIT", "JLDW");         
        
        bs.setFieldDic("IF_DIRECT", "SF");   
        bs.setFieldDic("IF_OUT", "SF");   
        bs.setFieldDic("IF_BOOK", "SF");   
        bs.setFieldDic("IF_RETURN", "SF");   //是否回运
        bs.setFieldDic("IF_OIL", "SF");   //是否油品
        bs.setFieldDic("IF_OUT_BUY", "SF");   //是否外购
//        bs.setFieldOrgDeptSimpleName("ORG_ID")//绑定组织机构
        
        bs.setFieldDic("IF_ASSEMBLY", "SF");   //是否大总成        
        bs.setFieldDic("BELONG_ASSEMBLY", "PJZCLB");   //所属总成
        
        bs.setFieldDic("IF_SCAN", "SF");   	//是否扫码件
        bs.setFieldDic("IF_SUPLY", "SF");   //是否指定供应商
        bs.setFieldDic("IF_STREAM", "SF");   //是否需要流水号
        
        bs.setFieldDic("PART_STATUS", "PJZT");   //配件状态
        bs.setFieldDic("STATUS", "YXBS");   	//状态
        
        bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("PRICE_TIME", "yyyy-MM-dd HH:mm:ss");
         
    	return bs;
    }
	//查询
	public BaseResultSet searchPlanPrice(PageManager page,String conditions) throws Exception
    {
    	String wheres = conditions;  
    	wheres += " AND A.PART_ID(+)=B.PART_ID";
    	wheres += " AND A.PART_STATUS NOT IN("+DicConstant.PJZT_02+")";
    	wheres += " ORDER BY A.CREATE_TIME DESC,A.PART_ID DESC";
    	  	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.PART_ID,\n" );
    	sql.append("       A.PART_CODE,\n" );
    	sql.append("       A.PART_NAME,\n" );
    	sql.append("       A.PART_STATUS,\n" );
    	sql.append("       A.PLAN_PRICE,\n" );
    	sql.append("       B.CKJG,\n" );
    	sql.append("       A.CREATE_TIME,\n" );
    	sql.append("       A.STATUS\n" );
    	sql.append("FROM PT_BA_INFO A,(SELECT PART_ID, MAX(PCH_PRICE) AS CKJG  FROM PT_BA_PART_SUPPLIER_RL group by PART_ID) B\n" );

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
	//服务索赔价格查询
	public BaseResultSet searchSeClPrice(PageManager page,String conditions) throws Exception {
    	String wheres = conditions;  
    	wheres += " AND SE_STATUS = "+DicConstant.YXBS_01+"";
    	wheres += " ORDER BY PART_ID DESC";
    	  	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("SE_CLPRICE\n" );
    	sql.append("FROM PT_BA_INFO");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page); 
    	return bs;
    }
	//服务追偿价格查询
	public BaseResultSet searchSeRePrice(PageManager page,String conditions) throws Exception {
    	String wheres = conditions;  
    	wheres += " AND SE_STATUS = "+DicConstant.YXBS_01+"";
    	wheres += " ORDER BY PART_ID DESC";
    	  	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("SE_REPRICE\n" );
    	sql.append("FROM PT_BA_INFO");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page); 
    	return bs;
    }
	//军品价格查询
	public BaseResultSet searchRetailPrice(PageManager page,String conditions) throws Exception {
    	String wheres = conditions;  
    	wheres += " AND PART_STATUS NOT IN("+DicConstant.PJZT_02+")";
    	wheres += " ORDER BY PART_ID DESC";
    	  	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("RETAIL_PRICE\n" );
    	sql.append("FROM PT_BA_INFO");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page); 
    	return bs;
    }
	//零售价格查询
	public BaseResultSet searchArmyPrice(PageManager page,String conditions) throws Exception {
    	String wheres = conditions;  
    	wheres += " AND PART_STATUS NOT IN("+DicConstant.PJZT_02+")";
    	wheres += " ORDER BY PART_ID DESC";
    	  	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("ARMY_PRICE\n" );
    	sql.append("FROM PT_BA_INFO");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page); 
    	return bs;
    }
	//销售价格查询
	public BaseResultSet searchSalePrice(PageManager page,String conditions) throws Exception {
    	String wheres = conditions;  
    	wheres += " AND PART_STATUS NOT IN("+DicConstant.PJZT_02+")";
    	wheres += " ORDER BY PART_ID DESC";
    	  	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("SALE_PRICE\n" );
    	sql.append("FROM PT_BA_INFO");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page); 
    	return bs;
    }
	//配件计划、经销、零售价格查询
	public BaseResultSet selectThirdPrice(PageManager page,String conditions) throws Exception {
    	String wheres = conditions;  
    	wheres += " AND PART_STATUS NOT IN(200602)";
    	wheres += " ORDER BY PART_ID DESC";
    	  	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("PLAN_PRICE,\n" );
    	sql.append("SALE_PRICE,\n" );
    	sql.append("RETAIL_PRICE,\n" );
    	sql.append("ARMY_PRICE\n" );
    	sql.append("FROM PT_BA_INFO");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page); 
    	return bs;
    }
	//配件计划、经销、零售三种价格查询
	public QuerySet downloadThirdPrice(String conditions) throws Exception {
    	  	
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROWNUM,\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("PLAN_PRICE,\n" );
    	sql.append("SALE_PRICE,\n" );
    	sql.append("RETAIL_PRICE,\n" );
    	sql.append("ARMY_PRICE\n" );
    	sql.append("FROM PT_BA_INFO");
    	sql.append(" WHERE "+conditions+"\n" );
    	sql.append(" AND PART_STATUS NOT IN("+DicConstant.PJZT_02+")");

    	//执行方法，不需要传递conn参数
    	return factory.select(null, sql.toString());
    }
	
	
	//配件计划、经销、零售三种价格错误信息查询
	public QuerySet downloadThirdPriceError(String pConditions,User user) throws Exception {
		String wheres = " WHERE ROW_NUM IN ("+ pConditions + ") \n";
    	wheres += " AND USER_ACCOUNT='"+user.getAccount()+"' \n";
    	wheres += " ORDER BY ROW_NUM \n";
    	
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT ROW_NUM,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("PLAN_PRICE,\n" );
    	sql.append("SALE_PRICE,\n" );
    	sql.append("RETAIL_PRICE\n" );
    	sql.append("FROM PT_BA_PRICE_TMP");

    	return factory.select(null, sql.toString()+wheres);
    }
	//配件计划、经销、零售、采购价格查询
	public BaseResultSet selectPrice(PageManager page,String conditions) throws Exception {
    	String wheres = conditions;  
    	wheres += " AND A.PART_ID(+)=B.PART_ID AND A.SUPPLIER_ID=C.SUPPLIER_ID AND B.PART_STATUS NOT IN(200602)";
//    	wheres += " ORDER BY CREATE_TIME DESC";
    	  	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("B.PART_ID,\n" );
    	sql.append("B.PART_CODE,\n" );
    	sql.append("B.PART_NAME,\n" );
    	sql.append("B.SALE_PRICE,\n" );
    	sql.append("B.PLAN_PRICE,\n" );
    	sql.append("B.RETAIL_PRICE,\n" );
    	sql.append("C.SUPPLIER_ID,\n" );
    	sql.append("C.SUPPLIER_CODE,\n" );
    	sql.append("C.SUPPLIER_NAME,\n" );
    	sql.append("A.RELATION_ID,\n" );
    	sql.append("A.PCH_PRICE\n" );
    	sql.append("FROM PT_BA_PART_SUPPLIER_RL A,PT_BA_INFO B,PT_BA_SUPPLIER C\n" );
//    	sql.append("WHERE A.PART_ID=B.PART_ID(+) AND A.SUPPLIER_ID=C.SUPPLIER_ID\n" );
//    	sql.append("AND B.STATUS=100201");


    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page); 
    	return bs;
    }
	
	
	
	
	//配件计划、经销、零售、采购价格查询
	public QuerySet downloadPrice(String conditions) throws Exception {
//    	String wheres = conditions;  
//    	wheres += " AND A.PART_ID=B.PART_ID(+) AND A.SUPPLIER_ID=C.SUPPLIER_ID AND B.STATUS=100201";
//    	wheres += " ORDER BY CREATE_TIME DESC";
    	  	
//        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROWNUM,\n" );
    	sql.append("B.PART_ID,\n" );
    	sql.append("B.PART_CODE,\n" );
    	sql.append("B.PART_NAME,\n" );
    	sql.append("B.SALE_PRICE,\n" );
    	sql.append("B.PLAN_PRICE,\n" );
    	sql.append("B.RETAIL_PRICE,\n" );
    	sql.append("C.SUPPLIER_ID,\n" );
    	sql.append("C.SUPPLIER_CODE,\n" );
    	sql.append("C.SUPPLIER_NAME,\n" );
    	sql.append("A.RELATION_ID,\n" );
    	sql.append("A.PCH_PRICE\n" );
    	sql.append("FROM PT_BA_PART_SUPPLIER_RL A,PT_BA_INFO B,PT_BA_SUPPLIER C\n" );
    	sql.append(" WHERE "+conditions+"\n" );
    	sql.append(" AND A.PART_ID=B.PART_ID(+) AND A.SUPPLIER_ID=C.SUPPLIER_ID AND B.PART_STATUS NOT IN(200602)");
    	
    	
//    	sql.append("WHERE A.PART_ID=B.PART_ID(+) AND A.SUPPLIER_ID=C.SUPPLIER_ID\n" );
//    	sql.append("AND B.STATUS=100201");


    	//执行方法，不需要传递conn参数
    	return factory.select(null, sql.toString());
//    	bs = factory.select(sql.toString(), page); 
//    	return bs;
    }
	//修改采购价格
//	public boolean updatePchPrice(String relation_id, String pch_price) throws Exception
//    {
//    	StringBuffer sql = new StringBuffer();
//    	sql.append(" UPDATE PT_BA_PART_SUPPLIER_RL \n");
//    	sql.append(" SET PCH_PRICE = " + pch_price + " \n");
//    	sql.append(" WHERE RELATION_ID = " + relation_id + " \n");
//    	return factory.update(sql.toString(), null);
//    }
	public boolean updatePchPrice(PtBaPartSupplierRlVO vo) throws Exception
    {
		return factory.update(vo);
    }
	
	//查询配件
	public BaseResultSet partSearch(PageManager page,String conditions) throws Exception {
    	String wheres = conditions;  
    	wheres += " AND PART_STATUS NOT IN(200602)";
    	wheres += " ORDER BY CREATE_TIME DESC";
    	  	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
//    	sql.append("PLAN_PRICE,\n" );
//    	sql.append("PCH_PRICE,\n" );
    	sql.append("STATUS\n" );
    	sql.append("FROM PT_BA_INFO");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page); 
    	return bs;
    }
	//查询总成
	public BaseResultSet positionSearch(PageManager page,String conditions,String p_id) throws Exception {
    	String wheres = conditions;  
    	wheres += " and p_id = '"+p_id+"' ";
    	wheres += " ORDER BY POSITION_CODE DESC";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("POSITION_ID,\n" );
    	sql.append("POSITION_CODE,\n" );
    	sql.append("POSITION_NAME,\n" );
    	sql.append("P_ID,\n" );
    	sql.append("P_NAME,\n" );
    	sql.append("P_CODE,\n" );
    	sql.append("STATUS\n" );
    	sql.append("FROM SE_BA_VEHICLE_POSITION");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page); 
    	return bs;
    }
	
	/**
	 * 获取配件档案临时表数据
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public QuerySet searchPtBaInfoTmp(User user, String errorInfoRowNum)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT\n" );
		sql.append("PART_ID,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("PART_NO,\n" );
		sql.append("UNIT,\n" );
		sql.append("PART_TYPE,\n" );
		sql.append("BELONG_ASSEMBLY,\n" );
		sql.append("USER_ACCOUNT,\n" );
		sql.append("ATTRIBUTE,\n" );
		sql.append("IF_ASSEMBLY,\n" );
		sql.append("IF_OUT,\n" );
		sql.append("IF_SUPLY,\n" );
		sql.append("IF_OIL,\n" );
		sql.append("IF_SCAN,\n" );
		sql.append("PART_STATUS,\n" );
		sql.append("POSITION_NAME,\n" );
		sql.append("F_POSITION_NAME,\n" );
		sql.append("REBATE_TYPE,\n" );
		sql.append("DIRECT_TYPE_NAME,\n" );
		sql.append("F_PART_CODE,\n" );
		sql.append("F_PART_NAME,\n" );
		sql.append("MIN_PACK,\n" );
		sql.append("MIN_UNIT,\n" );
		sql.append("SPE_NAME,\n" );
		sql.append("ROW_NUM\n" );
		sql.append("FROM PT_BA_INFO_TMP");

		sql.append(" WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		
		if (!errorInfoRowNum.equals("")) {
			sql.append(" AND ROW_NUM not in ( "+errorInfoRowNum+")\n" );
		}
		return factory.select(null, sql.toString());
	}
	
	/**
	 * 获取配件采购价格临时表数据
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public QuerySet searchPtBaPchPriceTrueTmp(User user, String errorInfoRowNum)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT\n" );
		sql.append("ROW_NUM,\n" );
		sql.append("TMP_ID,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("SUPPLIER_CODE,\n" );
		sql.append("SUPPLIER_NAME,\n" );
		sql.append("PCH_PRICE,\n" );
		sql.append("USER_ACCOUNT\n" );
		sql.append("FROM  PT_BA_PCH_TMP");

		sql.append(" WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		if (!errorInfoRowNum.equals("")) {
			sql.append(" AND ROW_NUM not in ( "+errorInfoRowNum+")\n" );
		}
		return factory.select(null, sql.toString());
	}
	/**
	 * 获取配件价格临时表数据
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public QuerySet searchPtBaPriceTmp(User user, String errorInfoRowNum)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT\n" );
		sql.append("TMP_ID,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("ROW_NUM,\n" );
		sql.append("PCH_PRICE,\n" );
		sql.append("PLAN_PRICE,\n" );
		sql.append("SALE_PRICE,\n" );
		sql.append("RETAIL_PRICE,\n" );
		sql.append("SUPPLIER_CODE,\n" );
		sql.append("SUPPLIER_NAME,\n" );
		sql.append("USER_ACCOUNT\n" );
		sql.append("FROM  PT_BA_PRICE_TMP");

		sql.append(" WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		if (!errorInfoRowNum.equals("")) {
			sql.append(" AND ROW_NUM not in ( "+errorInfoRowNum+")\n" );
		}
		return factory.select(null, sql.toString());
	}
	/**
	 * 获取配件采购价格临时表数据
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public QuerySet searchPtBaPchPriceTmp(User user, String errorInfoRowNum)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT\n" );
		sql.append("TMP_ID,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("ROW_NUM,\n" );
		sql.append("PCH_PRICE,\n" );
		sql.append("PLAN_PRICE,\n" );
		sql.append("SALE_PRICE,\n" );
		sql.append("RETAIL_PRICE,\n" );
		sql.append("SUPPLIER_CODE,\n" );
		sql.append("SUPPLIER_NAME,\n" );
		sql.append("USER_ACCOUNT\n" );
		sql.append("FROM  PT_BA_PRICE_TMP");

		sql.append(" WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		if (!errorInfoRowNum.equals("")) {
			sql.append(" AND ROW_NUM not in ( "+errorInfoRowNum+")\n" );
		}
		return factory.select(null, sql.toString());
	}
	 /**
     * 查询配件档案临时表信息（校验临时表数据:2、重复数据校验）
     * @throws Exception
     * @Author yhw 2014-10-24
     */
    public QuerySet searchPtBaInfoTmpRepeatData(User user,String partCode)throws Exception{
		
    	StringBuffer sql= new StringBuffer();
    	if (!partCode.equals("")) {
	    	sql.append("SELECT A.ROW_NUM, A.PART_CODE\n" );
	    	sql.append("  FROM PT_BA_INFO_TMP A,\n" );
	    	sql.append("       (" );
    	}
    	sql.append("SELECT PART_CODE, COUNT(PART_CODE)\n" );
    	sql.append("          FROM PT_BA_INFO_TMP\n" );
    	sql.append("         WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("         GROUP BY PART_CODE\n" );
    	sql.append("        HAVING COUNT(PART_CODE) > 1" );
    	if (!partCode.equals("")) {
	    	sql.append(") B\n" );
	    	sql.append(" WHERE A.PART_CODE = B.PART_CODE\n" );
	    	sql.append("   AND A.PART_CODE = B.PART_CODE\n" );
	    	sql.append("   AND A.PART_CODE = '"+partCode+"'\n" );
	    	sql.append("   AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	    	sql.append("   ORDER BY A.ROW_NUM\n" );
    	}
		return factory.select(null, sql.toString());
	}
    
    /**
     * 查询配件价格临时表信息（校验临时表数据:2、重复数据校验）
     * @throws Exception
     * @Author yhw 2014-10-24
     */
    public QuerySet searchPtBaPriceTmpRepeatData(User user,String partCode)throws Exception{
		
    	StringBuffer sql= new StringBuffer();
    	if (!partCode.equals("")) {
	    	sql.append("SELECT A.ROW_NUM, A.PART_CODE\n" );
	    	sql.append("  FROM PT_BA_PRICE_TMP A,\n" );
	    	sql.append("       (" );
    	}
    	sql.append("SELECT PART_CODE, COUNT(PART_CODE)\n" );
    	sql.append("          FROM PT_BA_PRICE_TMP\n" );
    	sql.append("         WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("         GROUP BY PART_CODE\n" );
    	sql.append("        HAVING COUNT(PART_CODE) > 1" );
    	if (!partCode.equals("")) {
	    	sql.append(") B\n" );
	    	sql.append(" WHERE A.PART_CODE = B.PART_CODE\n" );
	    	sql.append("   AND A.PART_CODE = '"+partCode+"'\n" );
	    	sql.append("   AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	    	sql.append("   ORDER BY A.ROW_NUM\n" );
    	}
		return factory.select(null, sql.toString());
	}
    /**
     * 查询配件采购价格临时表信息（校验临时表数据:2、重复数据校验）
     * @throws Exception
     * @Author yhw 2014-10-24
     */
    public QuerySet searchPtBaPchPriceTmpRepeatData(User user,String partCode, String supplierCode)throws Exception{
		
    	StringBuffer sql= new StringBuffer();
    	if (!partCode.equals("")) {
	    	sql.append("SELECT A.ROW_NUM, A.PART_CODE, A.SUPPLIER_CODE\n" );
	    	sql.append("  FROM PT_BA_PCH_TMP A,\n" );
	    	sql.append("       (" );
    	}
    	sql.append("SELECT PART_CODE, SUPPLIER_CODE, COUNT(PART_CODE)\n" );
    	sql.append("          FROM PT_BA_PCH_TMP\n" );
    	sql.append("         WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("         GROUP BY PART_CODE, SUPPLIER_CODE\n" );
    	sql.append("        HAVING COUNT(PART_CODE) > 1" );
    	if (!partCode.equals("")) {
	    	sql.append(") B\n" );
	    	sql.append(" WHERE A.PART_CODE = B.PART_CODE\n" );
	    	sql.append("   AND A.SUPPLIER_CODE = B.SUPPLIER_CODE\n" );
	    	sql.append("   AND A.PART_CODE = '"+partCode+"'\n" );
	    	sql.append("   AND A.SUPPLIER_CODE = '"+supplierCode+"'\n" );
	    	sql.append("   AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	    	sql.append("   ORDER BY A.ROW_NUM\n" );
    	}
		return factory.select(null, sql.toString());
	}
	//将零时表正确的数据显示到页面
	public BaseResultSet searchPtBaInfoTmpImport(PageManager page, String conditions, User user)throws Exception{
		
		String wheres = conditions;
    	wheres += " AND USER_ACCOUNT = '"+user.getAccount()+"'";
    	wheres += " order by PART_ID desc ";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
		
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT\n" );
		sql.append("PART_ID,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("PART_NO,\n" );
		sql.append("UNIT,\n" );
		sql.append("PART_TYPE,\n" );
		sql.append("BELONG_ASSEMBLY,\n" );
		sql.append("USER_ACCOUNT,\n" );
		sql.append("ATTRIBUTE,\n" );
		sql.append("IF_ASSEMBLY,\n" );
		sql.append("IF_OUT,\n" );
		sql.append("IF_SUPLY,\n" );
		sql.append("IF_OIL,\n" );
		sql.append("IF_SCAN,\n" );
		sql.append("PART_STATUS,\n" );
		sql.append("POSITION_NAME,\n" );
		sql.append("F_POSITION_NAME,\n" );
		sql.append("REBATE_TYPE,\n" );
		sql.append("DIRECT_TYPE_NAME,\n" );
		sql.append("F_PART_CODE,\n" );
		sql.append("F_PART_NAME,\n" );
		sql.append("MIN_PACK,\n" );
		sql.append("MIN_UNIT,\n" );
		sql.append("SPE_NAME\n" );
		sql.append("FROM PT_BA_INFO_TMP");
		
		bs=factory.select(sql.toString(), page);
		return bs;
	}
	
	//yhw将档案价格零时表正确的数据显示到页面
	public BaseResultSet searchPtBaPriceTmpImport(PageManager page, String conditions, User user)throws Exception{
		String wheres = conditions;
    	wheres += " AND USER_ACCOUNT = '"+user.getAccount()+"'";
    	wheres += " order by PART_CODE desc ";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
		
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT\n" );
		sql.append("TMP_ID,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("ROW_NUM,\n" );
		sql.append("PCH_PRICE,\n" );
		sql.append("PLAN_PRICE,\n" );
		sql.append("SALE_PRICE,\n" );
		sql.append("RETAIL_PRICE,\n" );
		sql.append("SUPPLIER_CODE,\n" );
		sql.append("SUPPLIER_NAME,\n" );
		sql.append("USER_ACCOUNT\n" );
		sql.append("FROM  PT_BA_PRICE_TMP");

	
		bs=factory.select(sql.toString(), page);
		return bs;
	}
	//yhw将配件采购价格零时表正确的数据显示到页面
	public BaseResultSet searchPtBaPchPriceTmpImport(PageManager page, String conditions, User user)throws Exception{
		String wheres = conditions;
    	wheres += " AND USER_ACCOUNT = '"+user.getAccount()+"'";
    	wheres += " order by PART_CODE desc ";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
		
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT\n" );
		sql.append("ROW_NUM,\n" );
		sql.append("TMP_ID,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("SUPPLIER_CODE,\n" );
		sql.append("SUPPLIER_NAME,\n" );
		sql.append("PCH_PRICE,\n" );
		sql.append("USER_ACCOUNT\n" );
		sql.append("FROM  PT_BA_PCH_TMP");

	
		bs=factory.select(sql.toString(), page);
		return bs;
	}
	//----------------------------下面是点击确定按钮，需要调用的方法----------------------------
    /**
     * 判断表PT_BA_INFO 配件CODE是否存在
     * @throws Exception
     * @Author yhw 2014-07-11
     */
	public QuerySet checkPtBaInfoCode(String partCode) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT PART_ID,PART_CODE \n");
    	sql.append(" FROM PT_BA_INFO \n");
    	sql.append(" WHERE PART_CODE = '" + partCode +"'\n");
    	//sql.append(" AND PART_STATUS NOT IN(200602)\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	 /**
     * 获取dic_tree表选字典值(字典名获取字典码)
     * @throws Exception
     * @Author yhw 2014-07-11
     */
	public String getDicTree(String dic_value,String dic_name_value) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT DIC_CODE,DIC_VALUE,DIC_NAME_VALUE \n");
    	sql.append(" FROM DIC_TREE \n");
    	sql.append(" WHERE DIC_VALUE = '" + dic_value +"'\n");
    	sql.append(" AND DIC_NAME_VALUE = '" + dic_name_value +"'\n");
    	qs = factory.select(null, sql.toString());
    	return qs.getString(1, "DIC_CODE");
    }
	/**
     * 获取表选字典码
     * @throws Exception
     * @Author yhw 2014-07-11
     */
	public QuerySet getBxCode(String position_name) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT POSITION_ID,POSITION_CODE,POSITION_NAME \n");
    	sql.append(" FROM SE_BA_VEHICLE_POSITION \n");
    	sql.append(" WHERE POSITION_NAME = '" + position_name +"'\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	/**
     * 获取直发类型字典码
     * @throws Exception
     * @Author yhw 2014-07-11
     */
	public QuerySet getZfCode(String type_name) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT TYPE_ID,TYPE_CODE,TYPE_NAME \n");
    	sql.append(" FROM PT_BA_DIRECT_TYPE \n");
    	sql.append(" WHERE TYPE_NAME = '" + type_name +"'\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	
	 /**
     * 导入新增：（临时表的数据有两种：配件档案不存在--则新增，否则--则更新）
     * @throws Exception
     * @Author yhw 2014-08-30
     */
	
	public boolean insertImportPtBaInfoFromTmp(PtBaInfoVO vo) throws Exception
	{
		return factory.insert(vo);
	}
//	public boolean insertImportPtBaInfoFromTmp(User user,String PART_CODE,String PART_NAME,String UNIT_CODE,String PART_TYPE_CODE,String IF_ASSEMBLY_CODE,String IF_OUT_CODE,String IF_SUPLY_CODE,String IF_OIL_CODE,String IF_SCAN_CODE,String PART_STATUS_CODE,String POSITION_ID,String POSITION_CODE,String POSITION_NAME,String F_POSITION_ID,String F_POSITION_CODE,String F_POSITION_NAME,String PART_NO,String REBATE_TYPE,String ATTRIBUTE_CODE,String DIRECT_TYPE_ID,String DIRECT_TYPE_CODE,String DIRECT_TYPE_NAME,String BELONG_ASSEMBLY_CODE,String SPE_TYPE)
//		throws Exception{
//		
//		StringBuffer sql = new StringBuffer();
//		sql.append("insert into PT_BA_INFO\n" );
//		sql.append("(PART_ID,\n" );
//		sql.append("PART_CODE,\n" );
//		sql.append("PART_NAME,\n" );
//		sql.append("UNIT,\n" );
//		sql.append("PART_TYPE,\n" );
//		sql.append("IF_ASSEMBLY,\n" );
//		sql.append("IF_OUT,\n" );
//		sql.append("IF_SUPLY,\n" );
//		sql.append("IF_OIL,\n" );
//		sql.append("IF_SCAN,\n" );
//		sql.append("PART_STATUS,\n" );
//		sql.append("POSITION_ID,\n" );
//		sql.append("POSITION_CODE,\n" );
//		sql.append("POSITION_NAME,\n" );
//		sql.append("F_POSITION_ID,\n" );
//		sql.append("F_POSITION_CODE,\n" );
//		sql.append("F_POSITION_NAME,\n" );
//		sql.append("PART_NO,\n" );
//		sql.append("REBATE_TYPE,\n" );
//		sql.append("ATTRIBUTE,\n" );
//		sql.append("DIRECT_TYPE_ID,\n" );
//		sql.append("DIRECT_TYPE_CODE,\n" );
//		sql.append("DIRECT_TYPE_NAME,\n" );
//		sql.append("BELONG_ASSEMBLY,\n" );
//		sql.append("SPE_TYPE,");
//		sql.append("    STATUS)\n" );
//
//
//		
//		
//		
//		
//		sql.append("   select f_getid(),\n" );
//		sql.append("          A.WAREHOUSE_ID,\n" );
//		sql.append("          A.WAREHOUSE_CODE,\n" );
//		sql.append("          A.WAREHOUSE_NAME,\n" );
//		sql.append("          A.PART_ID,\n" );
//		sql.append("          A.PART_CODE,\n" );
//		sql.append("          A.PART_NAME,\n" );
//		sql.append("          B.LOWER_LIMIT,\n" );
//		sql.append("          B.UPPER_LIMIT,\n" );
//		sql.append("          '"+ user.getAccount() +"',\n" );
//		sql.append("          sysdate,\n" );
//		sql.append("          100201\n" );
//		sql.append("     from PT_BU_STOCK A, PT_BA_STOCK_SAFESTOCKS_TMP B\n" );
//		sql.append("    WHERE A.WAREHOUSE_CODE = B.STOCK_CODE\n" );
//		sql.append("      AND A.PART_CODE = B.PART_CODE\n" );
//		sql.append("      AND B.STOCK_CODE ='"+ stockCode +"'\n" );
//		sql.append("      AND B.PART_CODE ='"+ partCode +"'");
//
//    	return factory.update(sql.toString(), null);
//	}
	
	/**
     * 导入更新：（临时表的数据有两种：配件档案不存在--则新增，否则--则更新）
     * @throws Exception
     * @Author yhw 2014-10-27
     */
	public boolean updateImportPtBaInfoFromTmp(PtBaInfoVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	/**
     * 导入更新：（临时表的数据有两种：配件代码及各价格一致则不更新，不一致则更新）
     * @throws Exception
     * @Author yhw 2014-10-27
     */
	public boolean updateImportPtBaPriceFromTmp(PtBaInfoVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	/**
     * 导入更新：（临时表的数据有两种：配件供货关系代码及各价格一致则不更新，不一致则更新）
     * @throws Exception
     * @Author yhw 2014-10-27
     */
	public boolean updateImportPtBaPartSupplierRlFromTmp(PtBaPartSupplierRlVO vo) throws Exception
    {
    	return factory.update(vo);
    }
//	public boolean updateImportPtBaInfoFromTmp(User user,String PART_CODE,String PART_NAME,String UNIT_CODE,String PART_TYPE_CODE,String IF_ASSEMBLY_CODE,String IF_OUT_CODE,String IF_SUPLY_CODE,String IF_OIL_CODE,String IF_SCAN_CODE,String PART_STATUS_CODE,String POSITION_ID,String POSITION_CODE,String POSITION_NAME,String F_POSITION_ID,String F_POSITION_CODE,String F_POSITION_NAME,String PART_NO,String REBATE_TYPE,String ATTRIBUTE_CODE,String DIRECT_TYPE_ID,String DIRECT_TYPE_CODE,String DIRECT_TYPE_NAME,String BELONG_ASSEMBLY_CODE,String SPE_TYPE)
//		throws Exception{
//		
//		StringBuffer sql = new StringBuffer();
//		sql.append(" UPDATE PT_BA_INFO \n");
//    	sql.append(" SET PART_NAME = " + PART_NAME + " ,\n");
//    	sql.append(" UNIT = " + UNIT_CODE + " ,\n");
//    	sql.append(" PART_TYPE = " + PART_TYPE_CODE + " ,\n");
//    	sql.append(" IF_ASSEMBLY = " + IF_ASSEMBLY_CODE + " ,\n");
//    	sql.append(" IF_OUT = " + IF_OUT_CODE + " ,\n");
//    	sql.append(" IF_SUPLY = " + IF_SUPLY_CODE + " ,\n");
//    	sql.append(" IF_OIL = " + IF_OIL_CODE + " ,\n");	
//    	sql.append(" IF_SCAN = " + IF_SCAN_CODE + " ,\n");	
//    	sql.append(" PART_STATUS = " + PART_STATUS_CODE + " ,\n");	
//    	sql.append(" POSITION_ID = " + POSITION_ID + " ,\n");	
//    	sql.append(" POSITION_CODE = " + POSITION_CODE + " ,\n");	
//    	sql.append(" POSITION_NAME = " + POSITION_NAME + " ,\n");	
//    	sql.append(" F_POSITION_ID = " + F_POSITION_ID + " ,\n");	
//    	sql.append(" F_POSITION_CODE = " + F_POSITION_CODE + " ,\n");	
//    	sql.append(" F_POSITION_NAME = " + F_POSITION_NAME + " ,\n");	
//    	
//    	
//    	
//    	sql.append(" PART_NO = " + PART_NO + " ,\n");	
//    	sql.append(" REBATE_TYPE = " + REBATE_TYPE + " ,\n");	
//    	sql.append(" ATTRIBUTE = " + ATTRIBUTE_CODE + " ,\n");	
//    	sql.append(" DIRECT_TYPE_ID = " + DIRECT_TYPE_ID + " ,\n");	
//    	sql.append(" DIRECT_TYPE_CODE = " + DIRECT_TYPE_CODE + " ,\n");	
//    	sql.append(" DIRECT_TYPE_NAME = " + DIRECT_TYPE_NAME + " ,\n");	
//    	sql.append(" BELONG_ASSEMBLY = " + BELONG_ASSEMBLY_CODE + " ,\n");	
//    	sql.append(" SPE_TYPE = " + SPE_TYPE + " \n");	 	
//    	
//    	
//    	sql.append(" WHERE PART_CODE = " + PART_CODE + " \n");
//    	sql.append(" AND UPDATE_USER = " + user.getAccount() + " \n");
//    	return factory.update(sql.toString(), null);
//	}
	
	//----------------------------下面是点击价格确定按钮，需要调用的方法----------------------------
	
	 /**
     * 根据CODE获得ID
     * @throws Exception
     * @Author yhw 2014-07-11
     */
	public QuerySet getTableId(String tableId, String tableCode, String table, String code) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT "+tableId+"\n");
    	sql.append(" FROM  "+table+"\n");
    	sql.append(" WHERE "+tableCode+" = '" + code +"'\n");
//    	sql.append(" AND PART_STATUS NOT IN(200602)\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	//根据ID获得价格
	public QuerySet getPrice(String part_code) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT PLAN_PRICE,SALE_PRICE,RETAIL_PRICE \n");
    	sql.append(" FROM  PT_BA_INFO \n");
    	sql.append(" WHERE PART_CODE = '" + part_code +"'\n");
    	sql.append(" AND PART_STATUS NOT IN(200602)\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    /**
     * 判断表PT_BA_INFO 配件CODE是否存在
     * @throws Exception
     * @Author yhw 2014-07-11
     */
	public QuerySet checkPtBaPriceCode(String part_code, String plan_price, String sale_price, String retail_price) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT PART_ID,PART_CODE \n");
    	sql.append(" FROM PT_BA_INFO \n");
    	sql.append(" WHERE PART_CODE = '" + part_code +"'\n");
    	sql.append(" AND PLAN_PRICE = '" + plan_price +"'\n");
    	sql.append(" AND SALE_PRICE = '" + sale_price +"'\n");
    	sql.append(" AND RETAIL_PRICE = '" + retail_price +"'\n");
    	sql.append(" AND PART_STATUS NOT IN(200602)\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	 /**
     * 判断表PT_BA_PART_SUPPLIER_RL 配件CODE,SUPPLIER_CODE,PCH_PRICE是否存在
     * @throws Exception
     * @Author yhw 2014-07-11
     */
	public QuerySet checkPtBaPartSupplierRlPriceCode(String part_id, String supplier_id, String pch_price) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT RELATION_ID,PART_ID,SUPPLIER_ID \n");
    	sql.append(" FROM PT_BA_PART_SUPPLIER_RL \n");
    	sql.append(" WHERE PART_ID = '" + part_id +"'\n");
    	sql.append(" AND SUPPLIER_ID = '" + supplier_id +"'\n");
    	sql.append(" AND PCH_PRICE = '" + pch_price +"'\n");
//    	sql.append(" AND PART_STATUS NOT IN(200602)\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	 /**
     * 判断表PT_BA_PART_SUPPLIER_RL 配件CODE,SUPPLIER_CODE,PCH_PRICE是否存在
     * @throws Exception
     * @Author yhw 2014-07-11
     */
	public QuerySet getPartSupplierRlId(String part_id, String supplier_id) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT RELATION_ID \n");
    	sql.append(" FROM PT_BA_PART_SUPPLIER_RL \n");
    	sql.append(" WHERE PART_ID = '" + part_id +"'\n");
    	sql.append(" AND SUPPLIER_ID = '" + supplier_id +"'\n");
//    	sql.append(" AND PART_STATUS NOT IN(200602)\n");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	public String getPchPrice(String part_id, String supplier_id) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT PCH_PRICE \n");
    	sql.append(" FROM PT_BA_PART_SUPPLIER_RL \n");
    	sql.append(" WHERE PART_ID = '" + part_id +"'\n");
    	sql.append(" AND SUPPLIER_ID = '" + supplier_id +"'\n");
//    	sql.append(" AND PART_STATUS NOT IN(200602)\n");
    	qs = factory.select(null, sql.toString());
    	return qs.getString(1, "PCH_PRICE");
    }
	
	
	
	/**
	 * 配件档案明细信息新增
	 * @throws Exception
     * @Author yhw 2014-07-25
	 */
	public boolean insertPtBaInfoImport(PtBaInfoVO vo) throws Exception
    {
		return factory.insert(vo);
    }
	
	 //配件档案表导出
    public QuerySet download(String conditions) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROWNUM,\n" );
    	sql.append("A.SPE_NAME,\n" );
    	sql.append("A.POSITION_ID,\n" );
    	sql.append("A.POSITION_NAME,\n" );
    	sql.append("A.POSITION_CODE,\n" );
    	sql.append("A.F_POSITION_ID,\n" );
    	sql.append("A.F_POSITION_CODE,\n" );
    	sql.append("A.F_POSITION_NAME,\n" );
    	sql.append("A.PRICE_USER,\n" );
    	sql.append("A.REBATE_PARAVALUE1,\n" );
    	sql.append("A.PART_ID,\n" );
    	sql.append("A.PART_CODE,\n" );
    	sql.append("A.PART_NAME,\n" );
    	sql.append("A.PART_NO,\n" );
    	sql.append("A.UNIT,\n" );
    	sql.append("A.PART_TYPE,\n" );
    	sql.append("A.ATTRIBUTE,\n" );
    	sql.append("A.MIN_PACK,\n" );
    	sql.append("A.MIN_UNIT,\n" );
    	sql.append("A.IF_DIRECT,\n" );
    	sql.append("A.IF_OUT,\n" );
    	sql.append("A.IF_BOOK,\n" );
    	sql.append("A.IF_RETURN,\n" );
    	sql.append("A.IF_ASSEMBLY,\n" );
    	sql.append("A.BELONG_ASSEMBLY,\n" );
    	sql.append("A.IF_SCAN,\n" );
    	sql.append("A.IF_SUPLY,\n" );
    	sql.append("A.PART_STATUS,\n" );
    	sql.append("A.IF_STREAM,\n" );
    	sql.append("A.REMARKS,\n" );
    	sql.append("A.SALE_PRICE,\n" );
    	sql.append("A.ARMY_PRICE,\n" );
    	sql.append("A.PLAN_PRICE,\n" );
    	sql.append("A.COMPANY_ID,\n" );
    	sql.append("A.ORG_ID,\n" );
    	sql.append("A.CREATE_USER,\n" );
    	sql.append("A.CREATE_TIME,\n" );
    	sql.append("A.UPDATE_USER,\n" );
    	sql.append("A.UPDATE_TIME,\n" );
    	sql.append("A.STATUS,\n" );
    	sql.append("A.PCH_PRICE,\n" );
    	sql.append("A.SE_STATUS,\n" );
    	sql.append("A.IF_SELF,\n" );
    	sql.append("A.IF_OIL,\n" );
    	sql.append("A.SE_UNIT,\n" );
    	sql.append("A.SE_CLPRICE,\n" );
    	sql.append("A.SE_REPRICE,\n" );
    	sql.append("A.F_PART_ID,\n" );
    	sql.append("A.F_PART_CODE,\n" );
    	sql.append("A.F_PART_NAME,\n" );
    	sql.append("A.IF_OUT_BUY,\n" );
    	sql.append("A.REBATE_TYPE,\n" );
    	sql.append("A.WEIGHT,\n" );
    	sql.append("A.P_SPECI,\n" );
    	sql.append("A.SPE_NAME,\n" );
    	sql.append("A.OLD_MANAGE_FEE,\n" );
    	sql.append("A.RETAIL_PRICE,\n" );
    	sql.append("A.VEHICLE_MAX,\n" );
    	sql.append("A.DIRECT_TYPE_ID,\n" );
    	sql.append("A.DIRECT_TYPE_CODE,\n" );
    	sql.append("A.DIRECT_TYPE_NAME,\n" );
    	sql.append("A.PRICE_TIME,");
    	
    	sql.append("       B.DIC_VALUE 		IF_ASSEMBLY_NAME,\n");
    	sql.append("       C.DIC_VALUE 		IF_OUT_NAME,\n");
    	sql.append("       D.DIC_VALUE 		IF_SUPLY_NAME,\n");
    	sql.append("       E.DIC_VALUE 		IF_OIL_NAME,\n");
    	sql.append("       F.DIC_VALUE 		IF_SCAN_NAME,\n");
    	sql.append("       G.DIC_VALUE 		PART_TYPE_NAME,\n");
    	sql.append("       K.DIC_VALUE 		PART_STATUS_NAME,\n");
    	sql.append("       L.DIC_VALUE 		UNIT_NAME,\n");
    	sql.append("       M.DIC_VALUE 		BELONG_ASSEMBLY_NAME,\n");
    	sql.append("       N.DIC_VALUE 		MIN_UNIT_NAME,\n");
    	sql.append("       P.DIC_VALUE 		ATTRIBUTE_NAME\n");
        	
    	sql.append("FROM PT_BA_INFO  A,");
    	sql.append("       DIC_TREE  B,\n");
    	sql.append("       DIC_TREE  C,\n");
    	sql.append("       DIC_TREE  D,\n");
    	sql.append("       DIC_TREE  E,\n");
    	sql.append("       DIC_TREE  F,\n");
    	sql.append("       DIC_TREE  G,\n");
    	sql.append("       DIC_TREE  K,\n");
    	sql.append("       DIC_TREE  L,\n");
    	sql.append("       DIC_TREE  M,\n");
    	sql.append("       DIC_TREE  N,\n");
    	sql.append("       DIC_TREE  P\n");
        	
    	sql.append(" where "+conditions+"\n" );
    	sql.append("   AND A.IF_ASSEMBLY=B.ID(+)\n");
    	sql.append("   AND A.IF_OUT=C.ID(+)\n");
    	sql.append("   AND A.IF_SUPLY=D.ID(+)\n");
    	sql.append("   AND A.IF_OIL=E.ID(+)\n");
    	sql.append("   AND A.IF_SCAN=F.ID(+)\n");
    	sql.append("   AND A.PART_TYPE=G.ID(+)\n");
    	sql.append("   AND A.PART_STATUS=K.ID(+)\n");
    	sql.append("   AND A.UNIT=L.ID(+)\n");
    	sql.append("   AND A.BELONG_ASSEMBLY=M.ID(+)\n");
    	sql.append("   AND A.MIN_UNIT=N.ID(+)\n");
    	sql.append("   AND A.ATTRIBUTE=P.ID(+)\n");
    	
    	
    	
    	
//    	sql.append(" AND STATUS=100201 AND PART_STATUS NOT IN(200602)\n" );
//    	sql.append(" ORDER BY PART_CODE " );

        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString());
    }
    
    //配件档案表导出(服务)
    public QuerySet downloadService(String conditions) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROWNUM,\n" );
    	sql.append("A.PART_ID,\n" );
    	sql.append("A.PART_CODE,\n" );
    	sql.append("A.PART_NAME,\n" );
    	sql.append("A.UNIT,\n" );
    	sql.append("A.PART_TYPE,\n" );
    	sql.append("A.IF_OUT,\n" );
    	sql.append("A.PART_STATUS,\n" );
    	sql.append("A.SE_STATUS,\n" );
    	sql.append("A.POSITION_ID,\n" );
    	sql.append("A.POSITION_NAME,\n" );
    	sql.append("A.POSITION_CODE,\n" );
    	sql.append("A.F_POSITION_ID,\n" );
    	sql.append("A.F_POSITION_CODE,\n" );
    	sql.append("A.F_POSITION_NAME,\n" );
    	sql.append("A.PART_NO,\n" );
    	sql.append("A.F_PART_ID,\n" );
    	sql.append("A.F_PART_CODE,\n" );
    	sql.append("A.F_PART_NAME,\n" );
    	sql.append("A.IF_WORK_HOURS_TIMES,\n" );
    	sql.append("A.CREATE_USER,\n" );
    	sql.append("A.CREATE_TIME,\n" );
    	sql.append("A.UPDATE_USER,\n" );
    	sql.append("A.UPDATE_TIME,\n" );
    	
    	
    	sql.append("       B.DIC_VALUE 		PART_TYPE_NAME,\n");
    	sql.append("       C.DIC_VALUE 		IF_OUT_NAME,\n");
    	sql.append("       D.DIC_VALUE 		PART_STATUS_NAME,\n");
    	sql.append("       E.DIC_VALUE 		SE_STATUS_NAME,\n");
    	sql.append("       F.DIC_VALUE 		IF_WORK_HOURS_TIMES_NAME,\n");
    	sql.append("       G.DIC_VALUE 		UNIT_NAME\n");
    	
        	
    	sql.append("FROM PT_BA_INFO  A,");
    	sql.append("       DIC_TREE  B,\n");
    	sql.append("       DIC_TREE  C,\n");
    	sql.append("       DIC_TREE  D,\n");
    	sql.append("       DIC_TREE  E,\n");
    	sql.append("       DIC_TREE  F,\n");
    	sql.append("       DIC_TREE  G\n");
        	
    	sql.append(" where "+conditions+"\n" );
    	sql.append("   AND A.PART_TYPE=B.ID(+)\n");
    	sql.append("   AND A.IF_OUT=C.ID(+)\n");
    	sql.append("   AND A.PART_STATUS=D.ID(+)\n");
    	sql.append("   AND A.SE_STATUS=E.ID(+)\n");
    	sql.append("   AND A.IF_WORK_HOURS_TIMES=F.ID(+)\n");
    	sql.append("   AND A.UNIT=G.ID(+)\n");
    	
    	
        return factory.select(null, sql.toString());
    }
    
    //配件档案错误信息导出
    public QuerySet expPtBaInfoTmpErrorData(String pConditions,User user) throws Exception {

    	String wheres = " WHERE ROW_NUM IN ("+ pConditions + ") \n";
    	wheres += " AND USER_ACCOUNT='"+user.getAccount()+"' \n";
    	wheres += " ORDER BY ROW_NUM \n";
    	
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT ROW_NUM,\n" );
		sql.append("PART_ID,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("PART_NO,\n" );
		sql.append("UNIT,\n" );
		sql.append("PART_TYPE,\n" );
		sql.append("BELONG_ASSEMBLY,\n" );
		sql.append("USER_ACCOUNT,\n" );
		sql.append("ATTRIBUTE,\n" );
		sql.append("IF_ASSEMBLY,\n" );
		sql.append("IF_OUT,\n" );
		sql.append("IF_SUPLY,\n" );
		sql.append("IF_OIL,\n" );
		sql.append("IF_SCAN,\n" );
		sql.append("PART_STATUS,\n" );
		sql.append("POSITION_NAME,\n" );
		sql.append("F_POSITION_NAME,\n" );
		sql.append("REBATE_TYPE,\n" );
		sql.append("DIRECT_TYPE_NAME,\n" );
		sql.append("F_PART_CODE,\n" );
		sql.append("F_PART_NAME,\n" );
		sql.append("MIN_PACK,\n" );
		sql.append("MIN_UNIT,\n" );
		sql.append("SPE_NAME\n" );
		sql.append("FROM PT_BA_INFO_TMP");
		
    	
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }
    
    //配件计划、销售、零售价格错误信息导出
    public QuerySet expThirdPriceTmpErrorData(String pConditions,User user) throws Exception {

    	String wheres = " WHERE ROW_NUM IN ("+ pConditions + ") \n";
    	wheres += " AND USER_ACCOUNT='"+user.getAccount()+"' \n";
    	wheres += " ORDER BY ROW_NUM \n";
    	
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT ROW_NUM,\n" );
    	sql.append("TMP_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("PLAN_PRICE,\n" );
    	sql.append("SALE_PRICE,\n" );
    	sql.append("RETAIL_PRICE,\n" );
    	sql.append("USER_ACCOUNT\n" );
    	sql.append("FROM PT_BA_PRICE_TMP");

		
    	
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }
    
  //配件基础信息导出
    public QuerySet downloadReport(String conditions) throws Exception {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROWNUM,\n" );
    	sql.append("A.PART_ID,\n" );
    	sql.append("A.PART_CODE,\n" );
    	sql.append("A.PART_NAME,\n" );
    	sql.append("A.UNIT,\n" );
    	sql.append("A.IF_DIRECT,\n" );
    	sql.append("A.IF_OUT,\n" );
    	sql.append("A.PART_STATUS,\n" );
    	sql.append("A.PLAN_PRICE,\n" );
    	sql.append("A.SALE_PRICE,\n" );
    	sql.append("A.RETAIL_PRICE,\n" );
    	sql.append("A.IF_OIL,\n" );
    	sql.append("A.IF_OUT_BUY,\n" );
    	sql.append("A.REBATE_TYPE,\n" );
    	sql.append("A.WEIGHT,\n" );
    	sql.append("A.P_SPECI,\n" );
    	
    	
    	sql.append("       B.DIC_VALUE 		UNIT_NAME,\n");
    	sql.append("       C.DIC_VALUE 		IF_DIRECT_NAME,\n");
    	sql.append("       D.DIC_VALUE 		IF_OUT_NAME,\n");
    	sql.append("       E.DIC_VALUE 		PART_STATUS_NAME,\n");
    	sql.append("       F.DIC_VALUE 		IF_OIL_NAME,\n");
    	sql.append("       G.DIC_VALUE 		IF_OUT_BUY_NAME\n");
    	
        	
    	sql.append("FROM PT_BA_INFO  A,");
    	sql.append("       DIC_TREE  B,\n");
    	sql.append("       DIC_TREE  C,\n");
    	sql.append("       DIC_TREE  D,\n");
    	sql.append("       DIC_TREE  E,\n");
    	sql.append("       DIC_TREE  F,\n");
    	sql.append("       DIC_TREE  G\n");
    	
        	
    	sql.append(" where "+conditions+"\n" );
    	sql.append("   AND A.UNIT=B.ID(+)\n");
    	sql.append("   AND A.IF_DIRECT=C.ID(+)\n");
    	sql.append("   AND A.IF_OUT=D.ID(+)\n");
    	sql.append("   AND A.PART_STATUS=E.ID(+)\n");
    	sql.append("   AND A.IF_OIL=F.ID(+)\n");
    	sql.append("   AND A.IF_OUT_BUY=G.ID(+)\n");
    	

        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString());
    }
    
    
    //配件价格导出
    public QuerySet priceDownload(String conditions) throws Exception {
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROWNUM,\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("SALE_PRICE,\n" );
    	sql.append("ARMY_PRICE,\n" );
    	sql.append("PLAN_PRICE,\n" );
    	sql.append("STATUS,\n" );
    	sql.append("SE_CLPRICE,\n" );
    	sql.append("SE_REPRICE,\n" );
    	sql.append("RETAIL_PRICE,\n" );
    	sql.append("PCH_PRICE\n" );
    	sql.append("FROM PT_BA_INFO");
    	sql.append(" where "+conditions+"\n" );
    	sql.append(" AND PART_STATUS NOT IN(200602)\n" );
//    	sql.append(" ORDER BY PART_CODE " );

        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString());
    }
    public BaseResultSet searchArmyPriceImport(PageManager pPageManager, String pConditions,User user) throws Exception {

    	String wheres = pConditions + " AND USER_ACCOUNT='"+user.getAccount()+"' \n";
        pPageManager.setFilter(wheres);
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     PART_CODE, \n");
        sql.append("     PART_NAME, \n");
        sql.append("     ARMY_PRICE\n");
        sql.append(" FROM \n");
        sql.append("     PT_BA_ARMY_PRICE_TMP \n");
        //执行方法，不需要传递conn参数
        return factory.select(sql.toString(),pPageManager);
    }
    public QuerySet expArmyPriceData(String pConditions,User user) throws Exception {

    	String wheres = " WHERE TMP_NO IN ("+ pConditions + ") AND USER_ACCOUNT='"+user.getAccount()+"'\n";
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT \n");
        sql.append("     PART_CODE, \n");
        sql.append("     PART_NAME, \n");
        sql.append("     ARMY_PRICE\n");
    	sql.append("  FROM PT_BA_ARMY_PRICE_TMP");
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }
    public QuerySet getArmyPriceTmp(String tmp,User user) throws Exception {	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T1.PART_ID,T1.PART_CODE,T1.PART_NAME, T.ARMY_PRICE NEW_PRICE, T1.ARMY_PRICE OLD_PRICE\n" );
    	sql.append("  FROM PT_BA_ARMY_PRICE_TMP T, PT_BA_INFO T1\n" );
    	sql.append(" WHERE T.PART_CODE = T1.PART_CODE");
		sql.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		if (!"".equals(tmp)&&tmp!=null) {
     		sql.append(" AND T.TMP_NO NOT IN ("+tmp+") \n");
     	}
        return factory.select(null, sql.toString());
    }
	public boolean updatePartInfo(PtBaInfoVO vo) throws Exception
    {
    	return factory.update(vo);
    }
}
