package com.org.dms.dao.service.reportForms;

import com.org.dms.common.DicConstant;
import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 售后报表——未知标识明细表
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年10月30日 
 */
public class UnknownDetailMngDao extends BaseDAO
{
    //定义instance
    public  static final UnknownDetailMngDao getInstance(ActionContext atx)
    {
        UnknownDetailMngDao dao = new UnknownDetailMngDao();
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
    	wheres += "AND T.ORG_ID = M.ORG_ID\n" ;
    	wheres += "AND T.CLAIM_ID = N.CLAIM_ID\n" ;
    	wheres += "AND T.CLAIM_ID = F.CLAIM_ID\n" ;
    	wheres += "AND N.OLD_SUPPLIER_ID = S.SUPPLIER_ID\n" ;
    	wheres += "AND S.SUPPLIER_CODE = '9XXXXXX'\n" ;				//未知供应商代码
    	wheres += "AND N.FAULT_TYPE = '"+DicConstant.GZLB_01+"'\n" ;//查询主损件
    	wheres += "ORDER BY M.ORG_ID,T.CLAIM_NO" ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
    	StringBuffer sql= new StringBuffer();
		sql.append("SELECT M.ONAME,\n" );
		sql.append("       T.CLAIM_NO,\n" );
		sql.append("       N.OLD_PART_NAME,\n" );
		sql.append("       N.OLD_PART_CODE,\n" );
		sql.append("       F.WORK_COSTS,\n" );
		sql.append("       F.SEVEH_COSTS,\n" );
		sql.append("       F.TRAVEL_COSTS,\n" );
		sql.append("       F.MEALS_COSTS,\n" );
		sql.append("       F.MATERIAL_COSTS,\n" );
		sql.append("       F.OTHER_COSTS,\n" );
		sql.append("       F.WORK_COSTS+F.SEVEH_COSTS+F.TRAVEL_COSTS+F.MEALS_COSTS+F.MATERIAL_COSTS+F.OTHER_COSTS  AS  TOTAL_COST\n" );
		sql.append("FROM   SE_BU_CLAIM            T,\n" );
		sql.append("       TM_ORG                 M,\n" );
		sql.append("       SE_BU_CLAIM_FAULT_PART N,\n" );
		sql.append("       SE_BU_RECOVERY_DTL     F,\n" );
		sql.append("       PT_BA_SUPPLIER         S\n" );
    	
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	
    	return bs;
    }    
    
    //下载(导出)
    public QuerySet download(String conditions, User user) throws Exception
    {	    			
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT ROWNUM,\n" );
		sql.append("       M.ONAME,\n" );
		sql.append("       T.CLAIM_NO,\n" );
		sql.append("       N.OLD_PART_NAME,\n" );
		sql.append("       N.OLD_PART_CODE,\n" );
		sql.append("       F.WORK_COSTS,\n" );
		sql.append("       F.SEVEH_COSTS,\n" );
		sql.append("       F.TRAVEL_COSTS,\n" );
		sql.append("       F.MEALS_COSTS,\n" );
		sql.append("       F.MATERIAL_COSTS,\n" );
		sql.append("       F.OTHER_COSTS,\n" );		
		sql.append("       F.WORK_COSTS+F.SEVEH_COSTS+F.TRAVEL_COSTS+F.MEALS_COSTS+F.MATERIAL_COSTS+F.OTHER_COSTS  AS  TOTAL_COST\n" );
		sql.append("FROM   SE_BU_CLAIM            T,\n" );
		sql.append("       TM_ORG                 M,\n" );
		sql.append("       SE_BU_CLAIM_FAULT_PART N,\n" );
		sql.append("       SE_BU_RECOVERY_DTL     F,\n" );
		sql.append("       PT_BA_SUPPLIER         S\n" );
		sql.append("WHERE  "+conditions+"\n" );
		sql.append(" AND   T.ORG_ID = M.ORG_ID\n" );
		sql.append(" AND   T.CLAIM_ID = N.CLAIM_ID\n" );
		sql.append(" AND   T.CLAIM_ID = F.CLAIM_ID\n" );
		sql.append(" AND   N.OLD_SUPPLIER_ID = S.SUPPLIER_ID\n" );
		sql.append(" AND   S.SUPPLIER_CODE = '9XXXXXX'\n" );			  //未知供应商代码
		sql.append(" AND   N.FAULT_TYPE = '"+DicConstant.GZLB_01+"'\n" ); //查询主损件
		//sql.append(" ORDER BY M.ORG_ID,T.CLAIM_NO");

    	return factory.select(null, sql.toString());
    }
}