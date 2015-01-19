package com.org.dms.dao.part.basicInfoMng;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.org.dms.vo.part.PtBaInfoVO;
import com.org.dms.vo.part.PtBaPriceLogVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

public class PtBaPriceLogDao extends BaseDAO {
	// 定义instance
	public static final PtBaPriceLogDao getInstance(ActionContext atx) {
		PtBaPriceLogDao dao = new PtBaPriceLogDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}
	//插入信息
	public boolean insertPtBaPriceLog(PtBaPriceLogVO vo) throws Exception {
		return factory.update(vo);
	}
	//更新档案价格信息
	public boolean updatePtBaInfoPrice(String part_id,String sale_price,String army_price,String plan_price,String pch_price,String retail_price) throws Exception
    {
		StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_INFO \n");
    	sql.append(" SET SALE_PRICE = " + sale_price + ", ARMY_PRICE="+army_price+", PLAN_PRICE="+plan_price+",PCH_PRICE="+pch_price+",RETAIL_PRICE="+retail_price+"  \n");
    	sql.append(" WHERE PART_ID = " + part_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	//更新档案销售价格信息
	public boolean updatePtBaInfo(String part_id,String price) throws Exception
    {
		StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_INFO \n");
    	sql.append(" SET SALE_PRICE = " + price + " \n");
    	sql.append(" WHERE PART_ID = " + part_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	//更新档案计划价格信息
	public boolean updatePtBaInfoPlan(String part_id,String price) throws Exception
    {
		StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_INFO \n");
    	sql.append(" SET PLAN_PRICE = " + price + " \n");
    	sql.append(" WHERE PART_ID = " + part_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	//更新档案计划价格信息
	public boolean updatePtBaInfoArmy(String part_id,String price) throws Exception
    {
		StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_INFO \n");
    	sql.append(" SET ARMY_PRICE = " + price + " \n");
    	sql.append(" WHERE PART_ID = " + part_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	//修改信息
	public boolean updatePtBaPriceLog(PtBaPriceLogVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	//删除信息
	public boolean updatePtBaPriceLogStatus(String log_id, String status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_PRICE_LOG \n");
    	sql.append(" SET STATUS = " + status + " \n");
    	sql.append(" WHERE LOG_ID = " + log_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	
	//查询
	public BaseResultSet search(PageManager page,String conditions) throws Exception
    {
    	String wheres = conditions;  
    	wheres += " ORDER BY PART_ID, PART_CODE, PART_NAME, UPDATE_TIME DESC";
    	
    	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	/*StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("SUPPLIER_ID,\n" );
    	sql.append("SUPPLIER_CODE,\n" );
    	sql.append("SUPPLIER_NAME,\n" );
    	sql.append("LOG_ID,\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("NOW_PRICE,\n" );
    	sql.append("ORIGINAL_PRICE,\n" );
    	sql.append("PRICE_TYPE,\n" );
    	sql.append("USABLE_STOCK,\n" );
    	sql.append("MODIFY_USER,\n" );
    	sql.append("MODIFY_DATE,\n" );
    	sql.append("COMPANY_ID,\n" );
    	sql.append("ORG_ID,\n" );
    	sql.append("CREATE_USER,\n" );
    	sql.append("CREATE_TIME,\n" );
    	sql.append("UPDATE_USER,\n" );
    	sql.append("UPDATE_TIME,\n" );
    	sql.append("STATUS,\n" );
    	sql.append("OEM_COMPANY_ID,\n" );
    	sql.append("SECRET_LEVEL\n" );
    	sql.append("FROM PT_BA_PRICE_LOG");*/

    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT PART_ID,\n" );
    	sql.append("       PART_CODE,\n" );
    	sql.append("       PART_NAME,\n" );
    	sql.append("       NOW_PRICE,\n" );
    	sql.append("       ORIGINAL_PRICE,\n" );
    	sql.append("       PRICE_TYPE,\n" );
    	sql.append("       USABLE_STOCK,\n" );
    	sql.append("       MODIFY_USER,\n" );
    	sql.append("       MODIFY_DATE,\n" );
    	sql.append("       UPDATE_USER,\n" );
    	sql.append("       UPDATE_TIME\n" );
    	sql.append("  FROM PT_BA_PRICE_LOG\n" );

    	
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);   	
    	bs.setFieldDic("PRICE_TYPE", "PJJGLX");   //配件价格类型
    	    	
        //bs.setFieldDic("STATUS", "YXBS");   //状态
        //bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:MM:SS");
		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
	//调价记录导出
	public QuerySet searchPriceReportDownload(String conditions) throws Exception
    {
    	String wheres = " WHERE "+conditions;  
    	wheres += " AND A.PART_ID = B.PART_ID(+)";
    	wheres += " ORDER BY A.PART_CODE DESC";
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("A.LOG_ID,\n" );
    	sql.append("A.PART_ID,\n" );
    	sql.append("A.PART_CODE,\n" );
    	sql.append("A.PART_NAME,\n" );
    	sql.append("A.NOW_PRICE,\n" );
    	sql.append("A.ORIGINAL_PRICE,\n" );
    	sql.append("ABS(NVL(A.NOW_PRICE,0)-NVL(ORIGINAL_PRICE,0)) AS JGCY ,\n" );										//价格差异
    	sql.append("(SELECT DIC_VALUE FROM DIC_TREE WHERE ID = A.PRICE_TYPE) PRICE_TYPE,\n" );
    	sql.append("A.UPDATE_USER,\n" );
    	sql.append("A.UPDATE_TIME,\n" );
    	sql.append("B.WAREHOUSE_NAME,\n" );
    	sql.append("B.AMOUNT, \n" );
    	sql.append("ABS(NVL(A.NOW_PRICE,0)*NVL(B.AMOUNT,0)-NVL(ORIGINAL_PRICE,0)*NVL(B.AMOUNT,0))   AS CYJE  \n" );		//差异金额
    	sql.append("FROM PT_BA_PRICE_LOG A,PT_BU_STOCK B\n" );
    	
    	
    	//执行方法，不需要传递conn参数
    	return factory.select(null,sql.toString()+wheres);   	
    }
	//调价轨迹报表查询
	public BaseResultSet searchPriceReport(PageManager page,String conditions) throws Exception
    {
    	String wheres = conditions;  
    	wheres += " AND A.PART_ID = B.PART_ID(+)";
    	wheres += " ORDER BY A.PART_CODE DESC";
    	
    	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("A.LOG_ID,\n" );
    	sql.append("A.PART_ID,\n" );
    	sql.append("A.PART_CODE,\n" );
    	sql.append("A.PART_NAME,\n" );
    	sql.append("A.NOW_PRICE,\n" );
    	sql.append("A.ORIGINAL_PRICE,\n" );
    	sql.append("ABS(NVL(A.NOW_PRICE,0)-NVL(ORIGINAL_PRICE,0)) AS JGCY ,\n" );										//价格差异
    	sql.append("A.PRICE_TYPE,\n" );
    	sql.append("A.UPDATE_USER,\n" );
    	sql.append("A.UPDATE_TIME,\n" );
    	sql.append("B.WAREHOUSE_NAME,\n" );
    	sql.append("B.AMOUNT, \n" );
    	sql.append("ABS(NVL(A.NOW_PRICE,0)*NVL(B.AMOUNT,0)-NVL(ORIGINAL_PRICE,0)*NVL(B.AMOUNT,0))   AS CYJE  \n" );		//差异金额
    	sql.append("FROM PT_BA_PRICE_LOG A,PT_BU_STOCK B\n" );
    	
    	
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);   	
    	bs.setFieldDic("PRICE_TYPE", "PJJGLX");   //配件价格类型
    	    	
		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
	//调价轨迹报表查询
	public BaseResultSet searchPriceReportSum(PageManager page,String conditions) throws Exception
    {
		String wheres = conditions;  
    	wheres += " AND A.PART_ID = B.PART_ID(+)";
    	wheres += " ORDER BY A.PART_CODE DESC";
    	
    	page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("SUM(ABS(NVL(A.NOW_PRICE,0)*NVL(B.AMOUNT,0)-NVL(ORIGINAL_PRICE,0)*NVL(B.AMOUNT,0)))    AS CYJEZH  \n" );		//差异金额总和
    	sql.append("FROM PT_BA_PRICE_LOG A,PT_BU_STOCK B\n" );
    	
    	bs = factory.select(sql.toString(), page);   
    	return bs;
    }
	
	//查询军品价格
	public BaseResultSet searchArmy(PageManager page,String conditions) throws Exception
    {
    	String wheres = conditions;  
    	wheres += " AND PRICE_TYPE=2 or PRICE_TYPE=0 ORDER BY UPDATE_TIME DESC";
    	
    	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("LOG_ID,\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("NOW_PRICE,\n" );
    	sql.append("ORIGINAL_PRICE,\n" );
    	sql.append("PRICE_TYPE,\n" );
    	sql.append("USABLE_STOCK,\n" );
    	sql.append("MODIFY_USER,\n" );
    	sql.append("MODIFY_DATE,\n" );
    	sql.append("COMPANY_ID,\n" );
    	sql.append("ORG_ID,\n" );
    	sql.append("CREATE_USER,\n" );
    	sql.append("CREATE_TIME,\n" );
    	sql.append("UPDATE_USER,\n" );
    	sql.append("UPDATE_TIME,\n" );
    	sql.append("STATUS,\n" );
    	sql.append("OEM_COMPANY_ID,\n" );
    	sql.append("SECRET_LEVEL\n" );
    	sql.append("FROM PT_BA_PRICE_LOG");


    	
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
        bs.setFieldDic("STATUS", "YXBS");   //状态
        bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss"); 
    	return bs;
    }	
	//查询计划价格
	public BaseResultSet searchPlan(PageManager page,String conditions) throws Exception
    {
    	String wheres = conditions;  
    	wheres += " AND PRICE_TYPE=3 or PRICE_TYPE=0 ORDER BY UPDATE_TIME DESC";
    	
    	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("LOG_ID,\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("NOW_PRICE,\n" );
    	sql.append("ORIGINAL_PRICE,\n" );
    	sql.append("PRICE_TYPE,\n" );
    	sql.append("USABLE_STOCK,\n" );
    	sql.append("MODIFY_USER,\n" );
    	sql.append("MODIFY_DATE,\n" );
    	sql.append("COMPANY_ID,\n" );
    	sql.append("ORG_ID,\n" );
    	sql.append("CREATE_USER,\n" );
    	sql.append("CREATE_TIME,\n" );
    	sql.append("UPDATE_USER,\n" );
    	sql.append("UPDATE_TIME,\n" );
    	sql.append("STATUS,\n" );
    	sql.append("OEM_COMPANY_ID,\n" );
    	sql.append("SECRET_LEVEL\n" );
    	sql.append("FROM PT_BA_PRICE_LOG");


    	
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
        bs.setFieldDic("STATUS", "YXBS");   //状态
        bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
         
    	return bs;
    }	
}
