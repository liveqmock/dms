package com.org.dms.dao.service.reportForms;

import com.org.dms.common.DicConstant;
import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 售后报表——售后质量信息
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年10月29日 
 */
public class ServiceQualityMngDao extends BaseDAO
{
    //定义instance
    public  static final ServiceQualityMngDao getInstance(ActionContext atx)
    {
        ServiceQualityMngDao dao = new ServiceQualityMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

	/**
	 * 报表查询
	 * @param  page
	 * @param  code
	 * @param  conditions
	 * @return
	 * @throws Exception
	 */
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += "AND T.CLAIM_ID = M.CLAIM_ID\n" ;
    	wheres += "AND T.OLD_SUPPLIER_ID = S.SUPPLIER_ID\n" ;
    	wheres += "AND M.ORG_ID = N.ORG_ID\n" ;
    	wheres += "AND M.VEHICLE_ID = V.VEHICLE_ID\n" ;
    	wheres += "AND T.FAULT_TYPE = '"+DicConstant.GZLB_01+"'\n" ;//故障类别为主损件
    	wheres += "ORDER BY S.SUPPLIER_ID, N.ORG_ID" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT S.SUPPLIER_NAME,\n" );
    	sql.append("       N.ONAME,\n" );
    	sql.append("       M.CLAIM_NO,\n" );
    	sql.append("       M.VIN,\n" );
    	sql.append("       V.MODELS_CODE,\n" );
    	sql.append("       M.BUY_DATE,\n" );
    	sql.append("       M.FAULT_DATE,\n" );
    	sql.append("       M.MILEAGE,\n" );
    	sql.append("       T.OLD_PART_NAME,\n" );
    	sql.append("       T.OLD_PART_CODE,\n" );
    	sql.append("       T.MEASURES,\n" );
    	sql.append("       T.OLD_PART_COUNT * T.REPAY_UPRICE AS PRICE,\n" );
    	sql.append("       M.OLDPART_FINAL_DATE\n" );
    	sql.append("  FROM SE_BU_CLAIM_FAULT_PART T,\n" );
    	sql.append("       SE_BU_CLAIM            M,\n" );
    	sql.append("       TM_ORG                 N,\n" );
    	sql.append("       PT_BA_SUPPLIER         S,\n" );
    	sql.append("       MAIN_VEHICLE           V\n" );
    	
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	
    	//绑定字典方法，将字典代码翻译为字典名称
		bs.setFieldDic("MEASURES", "CLFS");//处理措施

		//设置日期字段显示格式
		bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd");
    	return bs;
    }
    
    //下载(导出)
    public QuerySet download(String conditions, User user) throws Exception
    {	    	

    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROWNUM,\n" );
    	sql.append("       S.SUPPLIER_NAME,\n" );
    	sql.append("       N.ONAME,\n" );
    	sql.append("       M.CLAIM_NO,\n" );
    	sql.append("       M.VIN,\n" );
    	sql.append("       V.MODELS_CODE,\n" );
    	sql.append("       TO_CHAR(M.BUY_DATE,'yyyy-MM-dd') AS BUY_DATE,\n" );
    	sql.append("       TO_CHAR(M.FAULT_DATE,'yyyy-MM-dd') AS FAULT_DATE,\n" );
    	sql.append("       M.MILEAGE,\n" );
    	sql.append("       T.OLD_PART_NAME,\n" );
    	sql.append("       T.OLD_PART_CODE,\n" );
    	sql.append("       D.DIC_VALUE,\n" );
    	sql.append("       T.OLD_PART_COUNT * T.REPAY_UPRICE AS PRICE,\n" );
    	sql.append("       M.OLDPART_FINAL_DATE\n" );
    	sql.append("  FROM SE_BU_CLAIM_FAULT_PART T,\n" );
    	sql.append("       SE_BU_CLAIM            M,\n" );
    	sql.append("       TM_ORG                 N,\n" );
    	sql.append("       PT_BA_SUPPLIER         S,\n" );
    	sql.append("       MAIN_VEHICLE           V,\n" );
    	sql.append("       DIC_TREE               D\n" );
		sql.append(" where "+conditions+"\n" );
		sql.append(" AND T.CLAIM_ID=M.CLAIM_ID\n" );
		sql.append(" AND T.OLD_SUPPLIER_ID = S.SUPPLIER_ID\n" );
		sql.append(" AND M.ORG_ID = N.ORG_ID\n" );
		sql.append(" AND M.VEHICLE_ID = V.VEHICLE_ID\n" );
		sql.append(" AND T.FAULT_TYPE = '"+DicConstant.GZLB_01+"'\n" );
		sql.append(" AND TO_CHAR(T.MEASURES) = D.DIC_CODE\n" );
		//sql.append(" ORDER BY S.SUPPLIER_ID, N.ORG_ID\n" );

    	return factory.select(null, sql.toString());
    }
}