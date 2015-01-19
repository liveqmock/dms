package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBaInfoVO;
import com.org.dms.vo.part.PtBaPartSupplierRlVO;
import com.org.dms.vo.part.PtBaPriceLogVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class PtBaPartSupplierRlDao extends BaseDAO {
	// 定义instance
	public static final PtBaPartSupplierRlDao getInstance(ActionContext atx) {
		PtBaPartSupplierRlDao dao = new PtBaPartSupplierRlDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}
	//插入信息
	public boolean insertPtBaPartSupplierRl(PtBaPartSupplierRlVO vo) throws Exception {
		return factory.insert(vo);
	}
	//插入采购价格轨迹信息
	public boolean insertPchPriceLog(PtBaPriceLogVO vo) throws Exception {
		return factory.insert(vo);
	}
	
	public boolean updateBaInfoPchPrice(PtBaInfoVO vo) throws Exception {
		return factory.update(vo);
	}
	//判断配件供应商关系是否存在
	public QuerySet checkSupplier(String part_id,String supplier_id) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" SELECT COUNT(1) NUMS \n");
    	sql.append(" FROM PT_BA_PART_SUPPLIER_RL \n");
    	sql.append(" WHERE PART_ID = '" + part_id +"' AND SUPPLIER_ID = '" + supplier_id +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	//修改信息
	public boolean updatePtBaPartSupplierRl(PtBaPartSupplierRlVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	//删除信息
	public boolean updatePtBaPartSupplierRlStatus(String relation_id, String status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_PART_SUPPLIER_RL \n");
    	sql.append(" SET STATUS = " + status + " \n");
    	sql.append(" WHERE RELATION_ID = " + relation_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	//删除供货关系(配件)
	public boolean updatePtBaPartSupplierRlPartIdentify(String relation_id, String identify) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_PART_SUPPLIER_RL \n");
    	sql.append(" SET PART_IDENTIFY = " + identify + " \n");
    	sql.append(" WHERE RELATION_ID = " + relation_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	//删除供货关系(服务)
	public boolean updatePtBaPartSupplierRlSeIdentify(String relation_id, String identify) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_PART_SUPPLIER_RL \n");
    	sql.append(" SET SE_IDENTIFY = " + identify + " \n");
    	sql.append(" WHERE RELATION_ID = " + relation_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	//修改采购价格
	public boolean updatePchPrice(String relation_id, String pch_price) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_PART_SUPPLIER_RL \n");
    	sql.append(" SET PCH_PRICE = " + pch_price + " \n");
    	sql.append(" WHERE RELATION_ID = " + relation_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	
	//查询
	public BaseResultSet search(PageManager page,String conditions) throws Exception
    {
    	String wheres = conditions;  
    	
    	
    	wheres += "  and  rl.part_id = p.part_id";
    	wheres += "  and  rl.supplier_id = s.supplier_id";
    	wheres += "  and  p.part_status  not in(200602)";
//    	wheres += "  and  s.part_identify = 100201";
    	wheres += "  order by rl.relation_id desc";
    	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("select rl.relation_id, p.part_id, p.part_code, p.part_name, p.part_status, p.se_status,\n" );
    	sql.append("s.supplier_id,s.supplier_code, s.supplier_name,\n" );
    	sql.append("rl.if_stream, rl.create_user,rl.create_time,rl.update_user,rl.update_time,\n" );
    	sql.append("rl.status,rl.apply_cycle, rl.pch_price, rl.plan_price, rl.part_identify, rl.se_identify\n" );
    	sql.append("  from pt_ba_part_supplier_rl rl,\n" );
    	sql.append("       pt_ba_info p,\n" );
    	sql.append("       pt_ba_supplier s\n" );
    	
    	bs = factory.select(sql.toString(), page);
        bs.setFieldDic("STATUS", "YXBS");    
        bs.setFieldDic("PART_STATUS", "PJZT");   //配件状态
        bs.setFieldDic("SE_STATUS", "YXBS");   	 //服务状态
        bs.setFieldDic("PART_IDENTIFY", "YXBS");          
        bs.setFieldDic("SE_IDENTIFY", "YXBS");           
        bs.setFieldDic("IF_STREAM", "SF");   //是否需要流水号
        
        bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
	    bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
         
    	return bs;
    }
	//查询供货(服务)
	public BaseResultSet searchService(PageManager page,String conditions) throws Exception
    {
    	String wheres = conditions;  
    	
    	
    	wheres += "  and  rl.part_id = p.part_id";
    	wheres += "  and  rl.supplier_id = s.supplier_id";
//    	wheres += "  and  p.part_status  not in(200602)";
//    	wheres += "  and  s.part_identify = 100201";
    	wheres += "  order by rl.create_time desc";
    	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("select rl.relation_id, p.part_id, p.part_code, p.part_name, p.part_status, p.se_status,\n" );
    	sql.append("s.supplier_id,s.supplier_code, s.supplier_name,\n" );
    	sql.append("rl.if_stream, rl.create_user,rl.create_time,rl.update_user,rl.update_time,\n" );
    	sql.append("rl.status,rl.apply_cycle, rl.pch_price, rl.plan_price, rl.part_identify, rl.se_identify\n" );
    	sql.append("  from pt_ba_part_supplier_rl rl,\n" );
    	sql.append("       pt_ba_info p,\n" );
    	sql.append("       pt_ba_supplier s\n" );
    	
    	bs = factory.select(sql.toString(), page);
        bs.setFieldDic("STATUS", "YXBS");    
        bs.setFieldDic("PART_STATUS", "PJZT");   //配件状态
        bs.setFieldDic("SE_STATUS", "YXBS");   	 //服务状态
        bs.setFieldDic("PART_IDENTIFY", "YXBS");          
        bs.setFieldDic("SE_IDENTIFY", "YXBS");           
        bs.setFieldDic("IF_STREAM", "SF");   //是否需要流水号
        
        bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
	    bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
         
    	return bs;
    }
	//查询配件采购价格
	public BaseResultSet searchPrice(PageManager page,String conditions,String if_null) throws Exception
    {
    	String wheres = conditions;  
    	wheres += "  and  rl.part_id =p.part_id";
    	wheres += "  and  rl.supplier_id=s.supplier_id";
    	wheres += "  and  rl.part_identify = 100201";
    	wheres += "  and  p.part_status not in(200602)";
    	wheres += "  and  s.part_identify = 100201";
    	
    	
    	if(if_null.equals(DicConstant.SF_01)){
    		wheres += "  and  rl.pch_price is null";
    	}
    	if(if_null.equals(DicConstant.SF_02)){
    		wheres += "  and  rl.pch_price is not null";
    	}
    	wheres += "  order by rl.part_id desc";
    	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("select rl.relation_id, rl.if_stream, rl.create_user,rl.create_time,rl.update_user,rl.update_time,\n" );
    	sql.append("rl.status,rl.apply_cycle, rl.pch_price, rl.plan_price,rl.pch_user,rl.pch_time,\n" );
    	sql.append("p.part_id, p.part_code, p.part_name,\n" );
    	sql.append("s.supplier_id,s.supplier_code, s.supplier_name\n" );
    	sql.append("  from pt_ba_part_supplier_rl rl,\n" );
    	sql.append("       pt_ba_info p,\n" );
    	sql.append("       pt_ba_supplier s\n" );
    	
    	bs = factory.select(sql.toString(), page);
        bs.setFieldDic("STATUS", "YXBS");   //状态        
        bs.setFieldDic("IF_STREAM", "SF");   //是否需要流水号
        bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
	    bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
         
    	return bs;
    }
	

	//查询配件
	public BaseResultSet partsupplierrlSearch(PageManager page,String conditions) throws Exception {
    	String wheres = conditions;  
    	
    	wheres +="ORDER BY T.PART_CODE DESC";
    	page.setFilter(wheres);
//    	wheres += " AND A.PART_STATUS NOT IN(200602)";
//    	wheres +=("AND EXISTS (SELECT 1 FROM PT_BU_PCH_CONTRACT T,PT_BU_PCH_CONTRACT_DTL T1 \n" );
//    	wheres +=("WHERE T.CONTRACT_ID = T1.CONTRACT_ID AND T1.PART_CODE = A.PART_CODE \n");
//    	wheres +=("AND T.CONTRACT_STATUS IN ("+DicConstant.CGHTZT_09+","+DicConstant.CGHTZT_10+"))\n");
//    	wheres += " ORDER BY PART_CODE,CREATE_TIME DESC";
//    	  	
//        page.setFilter(wheres);
//    	//定义返回结果集
//    	BaseResultSet bs = null;
//    	StringBuffer sql= new StringBuffer();
//    	sql.append("SELECT\n" );
//    	sql.append("A.PART_ID,\n" );
//    	sql.append("A.PART_CODE,\n" );
//    	sql.append("A.PART_NAME,\n" );
//    	sql.append("A.PART_TYPE,\n" );
//    	sql.append("A.UNIT,\n" );
//    	sql.append("A.PART_STATUS\n" );
//    	sql.append("FROM PT_BA_INFO A");
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.*\n" );
    	sql.append("  FROM (SELECT A.PART_ID,\n" );
    	sql.append("               A.PART_CODE,\n" );
    	sql.append("               A.PART_NAME,\n" );
    	sql.append("               A.PART_TYPE,\n" );
    	sql.append("               A.UNIT,\n" );
    	sql.append("               A.PART_STATUS\n" );
    	sql.append("          FROM PT_BA_INFO A\n" );
    	sql.append("         WHERE 1 = 1\n" );
    	sql.append("           AND A.PART_STATUS NOT IN (200602)\n" );
    	sql.append("           AND A.IF_ASSEMBLY = 100102\n" );
    	sql.append("           AND EXISTS\n" );
    	sql.append("         (SELECT 1\n" );
    	sql.append("                  FROM PT_BU_PCH_CONTRACT T, PT_BU_PCH_CONTRACT_DTL T1\n" );
    	sql.append("                 WHERE T.CONTRACT_ID = T1.CONTRACT_ID\n" );
    	sql.append("                   AND T1.PART_CODE = A.PART_CODE\n" );
    	sql.append("                   AND T.CONTRACT_STATUS IN (200109, 200110))\n" );
    	sql.append("        UNION ALL\n" );
    	sql.append("        SELECT A.PART_ID,\n" );
    	sql.append("               A.PART_CODE,\n" );
    	sql.append("               A.PART_NAME,\n" );
    	sql.append("               A.PART_TYPE,\n" );
    	sql.append("               A.UNIT,\n" );
    	sql.append("               A.PART_STATUS\n" );
    	sql.append("          FROM PT_BA_INFO A\n" );
    	sql.append("         WHERE 1 = 1\n" );
    	sql.append("           AND A.PART_STATUS NOT IN (200602)\n" );
    	sql.append("           AND A.IF_ASSEMBLY = 100101) T");
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page); 
    	bs.setFieldDic("UNIT", "JLDW");
        bs.setFieldDic("PART_TYPE", "PJLB");     
		bs.setFieldDic("PART_STATUS", "PJZT");   //配件状态
    	return bs;
    }
	//查询配件(服务)
	public BaseResultSet partsupplierrlFwSearch(PageManager page,String conditions) throws Exception {
    	String wheres = conditions;  
    	wheres += " AND SE_STATUS=100201 ";
    	wheres += " ORDER BY PART_CODE,CREATE_TIME DESC";
    	  	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("PART_TYPE,\n" );
    	sql.append("UNIT,\n" );
    	sql.append("PART_STATUS,\n" );
    	sql.append("SE_STATUS\n" );
    	sql.append("FROM PT_BA_INFO");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page); 
    	bs.setFieldDic("UNIT", "JLDW");
        bs.setFieldDic("PART_TYPE", "PJLB");     
		bs.setFieldDic("PART_STATUS", "PJZT");   //配件状态
		bs.setFieldDic("SE_STATUS", "YXBS");   //服务状态
    	return bs;
    }
	 //配件采购价格导出
    public QuerySet pchPriceDownload(String conditions, String if_null) throws Exception {	
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("select rownum,\n" );
    	sql.append("rl.relation_id, rl.if_stream, rl.create_user,rl.create_time,rl.update_user,rl.update_time,\n" );
    	sql.append("rl.status,rl.apply_cycle, rl.pch_price, rl.plan_price,rl.pch_user,rl.pch_time,\n" );
    	sql.append("p.part_id, p.part_code, p.part_name,\n" );
    	sql.append("s.supplier_id,s.supplier_code, s.supplier_name\n" );
    	sql.append("  from pt_ba_part_supplier_rl rl,\n" );
    	sql.append("       pt_ba_info p,\n" );
    	sql.append("       pt_ba_supplier s\n" );
    	sql.append(" where "+conditions+"\n" );
    	sql.append("  and  rl.part_id =p.part_id \n" );
    	sql.append("  and  rl.supplier_id=s.supplier_id \n" );
    	sql.append("  and  rl.part_identify = 100201 \n" );
    	sql.append("  and  p.part_status not in(200602) \n" );
    	sql.append("  and  s.part_identify = 100201 \n" );
    	if(if_null.equals(DicConstant.SF_01)){
    		sql.append("  and  rl.plan_price is null \n" );
    	}
    	if(if_null.equals(DicConstant.SF_02)){
    		sql.append("  and  rl.plan_price is not null\n" );
    	}

        return factory.select(null, sql.toString());
    }
  //配件采购价格错误信息查询
	public QuerySet expPchPriceTmpErrorData(String pConditions,User user) throws Exception {
		String wheres = " WHERE ROW_NUM IN ("+ pConditions + ") \n";
    	wheres += " AND USER_ACCOUNT='"+user.getAccount()+"' \n";
    	wheres += " ORDER BY ROW_NUM \n";
    	
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT ROW_NUM,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("SUPPLIER_CODE,\n" );
    	sql.append("SUPPLIER_NAME,\n" );
    	sql.append("PCH_PRICE\n" );
    	sql.append("FROM PT_BA_PCH_TMP");

    	return factory.select(null, sql.toString()+wheres);
    }
	public BaseResultSet searchImport(PageManager pPageManager, String pConditions,User user) throws Exception {

    	String wheres = pConditions + " AND USER_ACCOUNT='"+user.getAccount()+"' \n";
        pPageManager.setFilter(wheres);
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     PART_CODE, \n");
        sql.append("     SUPPLIER_CODE, \n");
        sql.append("     APPLY_CYCLE,\n");
        sql.append("     PART_STATUS\n");
        sql.append(" FROM \n");
        sql.append("     PT_BA_PART_SUPPLIER_RL_TMP \n");
        //执行方法，不需要传递conn参数
        return factory.select(sql.toString(),pPageManager);
    }
	public QuerySet expData(String pConditions,User user) throws Exception {

    	String wheres = " WHERE TMP_NO IN ("+ pConditions + ") AND USER_ACCOUNT='"+user.getAccount()+"'\n";
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT \n");
        sql.append("     PART_CODE, \n");
        sql.append("     SUPPLIER_CODE, \n");
        sql.append("     APPLY_CYCLE,\n");
        sql.append("     PART_STATUS\n");
    	sql.append("  FROM PT_BA_PART_SUPPLIER_RL_TMP");
        //执行方法，不需要传递conn参数
        return factory.select(null, sql.toString()+wheres);
    }
	public boolean updateForecastDtl(String pForcastId,User user,String tmpNo) throws Exception {


        // 新增配件预测明细表sql
		StringBuffer sql= new StringBuffer();
		sql.append("INSERT INTO PT_BA_PART_SUPPLIER_RL\n" );
		sql.append("  (RELATION_ID,\n" );
		sql.append("   PART_ID,\n" );
		sql.append("   SUPPLIER_ID,\n" );
		sql.append("   COMPANY_ID,\n" );
		sql.append("   ORG_ID,\n" );
		sql.append("   CREATE_USER,\n" );
		sql.append("   CREATE_TIME,\n" );
		sql.append("   STATUS,\n" );
		sql.append("   OEM_COMPANY_ID,\n" );
		sql.append("   APPLY_CYCLE,\n" );
		sql.append("   PART_IDENTIFY)\n" );
		sql.append("  (SELECT F_GETID(),\n" );
		sql.append("         T1.PART_ID,\n" );
		sql.append("         T2.SUPPLIER_ID,\n" );
		sql.append("         '"+user.getCompanyId()+"',\n" );
		sql.append("         '"+user.getOrgId()+"',\n" );
		sql.append("         '"+user.getAccount()+"',\n" );
		sql.append("         SYSDATE,\n" );
		sql.append("         '"+DicConstant.YXBS_01+"',\n" );
		sql.append("         '"+user.getOemCompanyId()+"',\n" );
		sql.append("         T3.PARAVALUE2,\n" );
		sql.append("         '"+DicConstant.YXBS_01+"'\n" );
		sql.append("    FROM PT_BA_PART_SUPPLIER_RL_TMP T,\n" );
		sql.append("         PT_BA_INFO                 T1,\n" );
		sql.append("         PT_BA_SUPPLIER             T2,\n" );
		sql.append("         USER_PARA_CONFIGURE        T3\n" );
		sql.append("   WHERE T.PART_CODE = T1.PART_CODE\n" );
		sql.append("     AND T.SUPPLIER_CODE = T2.SUPPLIER_CODE\n" );
		sql.append("     AND T.APPLY_CYCLE = T3.PARAVALUE1 AND  T.USER_ACCOUNT='" + user.getAccount() + "' "+tmpNo+")");
        return factory.exec(sql.toString());
    }
	
	
	public QuerySet download(String conditions,PageManager page)throws Exception{
//    	String wheres = "WHERE "+ conditions;
//    	wheres+=" AND RL.PART_ID = P.PART_ID\n";
//    	wheres+=" AND RL.SUPPLIER_ID = T2.SUPPLIER_ID\n" ;
//    	wheres+=" AND RL.APPLY_CYCLE = T3.PARAVALUE2\n" ;
//    	wheres+=" AND RL.PART_IDENTIFY = T4.ID\n" ;
//    	StringBuffer sql= new StringBuffer();
//    	sql.append("SELECT P.PART_CODE,\n" );
//    	sql.append("       T2.SUPPLIER_CODE,\n" );
//    	sql.append("       T3.PARAVALUE1    APPLY_CYCLE,\n" );
//    	sql.append("       T4.DIC_VALUE     PART_STATUS\n" );
//    	sql.append("  FROM PT_BA_PART_SUPPLIER_RL RL,\n" );
//    	sql.append("       PT_BA_INFO             P,\n" );
//    	sql.append("       PT_BA_SUPPLIER         T2,\n" );
//    	sql.append("       USER_PARA_CONFIGURE    T3,\n" );
//    	sql.append("       DIC_TREE               T4\n");
		
    	String wheres = "WHERE "+conditions;  
    	
    	
    	wheres += "  and  rl.part_id = p.part_id";
    	wheres += "  and  rl.supplier_id = s.supplier_id";
    	wheres += "  and  p.part_status  not in(200602)";
    	wheres += "  and  p.part_status = d.id";
//    	wheres += "  and  s.part_identify = 100201";
    	wheres += "  order by rl.relation_id desc";
    	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("select rl.relation_id, p.part_id, p.part_code, p.part_name, d.dic_value part_status, p.se_status,\n" );
    	sql.append("s.supplier_id,s.supplier_code, s.supplier_name,\n" );
    	sql.append("rl.if_stream, rl.create_user,rl.create_time,rl.update_user,rl.update_time,\n" );
    	sql.append("rl.status,rl.apply_cycle, rl.pch_price, rl.plan_price, rl.part_identify, rl.se_identify\n" );
    	sql.append("  from pt_ba_part_supplier_rl rl,\n" );
    	sql.append("       pt_ba_info p,\n" );
    	sql.append("       pt_ba_supplier s,dic_tree d\n" );
		    return factory.select(null, sql.toString()+wheres);
		}
	
	
	public QuerySet getTmp(String tmp,User user) throws Exception {	
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T1.PART_ID,\n" );
		sql.append("       T2.SUPPLIER_ID,\n" );
		sql.append("       T3.PARAVALUE2  APPLY_CYCLE,\n" );
		sql.append("       T4.ID          PART_STATUS\n" );
		sql.append("  FROM PT_BA_PART_SUPPLIER_RL_TMP T,\n" );
		sql.append("       PT_BA_INFO                 T1,\n" );
		sql.append("       PT_BA_SUPPLIER             T2,\n" );
		sql.append("       USER_PARA_CONFIGURE        T3,\n" );
		sql.append("       DIC_TREE                   T4\n" );
		sql.append(" WHERE T.PART_CODE = T1.PART_CODE\n" );
		sql.append("   AND T.SUPPLIER_CODE = T2.SUPPLIER_CODE\n" );
		sql.append("   AND T.APPLY_CYCLE = T3.PARAVALUE1\n" );
		sql.append("   AND T4.DIC_NAME_VALUE='有效标识'\n" );
		sql.append("   AND T.PART_STATUS = T4.DIC_VALUE\n" );
		sql.append("   AND T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		if (!"".equals(tmp)&&tmp!=null) {
     		sql.append(" AND T.TMP_NO NOT IN ("+tmp+") \n");
     	}
        return factory.select(null, sql.toString());
    }
	
	
	public QuerySet checkIn(String partId,String supplierId) throws Exception {	
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.RELATION_ID\n" );
		sql.append("FROM PT_BA_PART_SUPPLIER_RL T\n" );
		sql.append("WHERE T.PART_ID = "+partId+"\n" );
		sql.append("AND T.SUPPLIER_ID = "+supplierId+" ");
        return factory.select(null, sql.toString());
    }
	
	public QuerySet getSupId(String supplierCode) throws Exception {
    	
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT SUPPLIER_ID\n" );
    	sql.append("FROM PT_BA_SUPPLIER \n");
    	sql.append("WHERE SUPPLIER_CODE = '"+supplierCode+"'\n");
    	return factory.select(null, sql.toString());
    }
	//服务标识批量修改
	public boolean batchUpdateService(String relationIds,String se_identify)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" update PT_BA_PART_SUPPLIER_RL \n");
		sql.append(" set SE_IDENTIFY = '" + se_identify + "'\n");
		sql.append(" where RELATION_ID in (" + relationIds + ") \n");
		return factory.update(sql.toString(), null);
	}
    
}
