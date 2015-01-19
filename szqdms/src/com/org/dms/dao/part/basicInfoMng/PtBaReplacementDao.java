package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.vo.part.PtBaReplacementVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.mvc.context.ActionContext;

public class PtBaReplacementDao extends BaseDAO {
	// 定义instance
	public static final PtBaReplacementDao getInstance(ActionContext atx) {
		PtBaReplacementDao dao = new PtBaReplacementDao();
		atx.setDBFactory(dao.factory);
		return dao;
	}
	//插入信息
	public boolean insertPtBaReplacement(PtBaReplacementVO vo) throws Exception {
		return factory.insert(vo);
	}
	//修改信息
	public boolean updatePtBaReplacement(PtBaReplacementVO vo) throws Exception
    {
    	return factory.update(vo);
    }
	//删除信息
	public boolean updatePtBaReplacementStatus(String replace_id, String status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE PT_BA_REPLACEMENT \n");
    	sql.append(" SET STATUS = " + status + " \n");
    	sql.append(" WHERE REPLACE_ID = " + replace_id + " \n");
    	return factory.update(sql.toString(), null);
    }
	
	//查询
	public BaseResultSet search(PageManager page,String conditions) throws Exception
    {
    	String wheres = conditions;  
    	wheres += " AND STATUS=100201";
    	wheres += " ORDER BY CREATE_TIME DESC,REPLACE_ID DESC";
    	
    	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
  
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT\n" );
    	sql.append("REPLACE_ID,\n" );
    	sql.append("PART_ID,\n" );
    	sql.append("PART_NAME,\n" );
    	sql.append("PART_CODE,\n" );
    	sql.append("RPART_ID,\n" );
    	sql.append("RPART_NAME,\n" );
    	sql.append("RPART_CODE,\n" );
    	sql.append("COMPANY_ID,\n" );
    	sql.append("ORG_ID,\n" );
    	sql.append("CREATE_USER,\n" );
    	sql.append("CREATE_TIME,\n" );
    	sql.append("UPDATE_USER,\n" );
    	sql.append("UPDATE_TIME,\n" );
    	sql.append("REMARKS,\n" );
    	sql.append("STATUS,\n" );
    	sql.append("OEM_COMPANY_ID,\n" );
    	sql.append("SECRET_LEVEL\n" );
    	sql.append("FROM PT_BA_REPLACEMENT");

  
 
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	//绑定字典方法，对于配件名称字典（PART_NAME），将字典代码翻译为字典名称	  
    	
    	
    	
//        bs.setFieldOrgDeptSimpleName("ORG_ID")//绑定组织机构
        bs.setFieldOrgCompanySimpleName("COMPANY_ID");	//所属公司
        bs.setFieldOrgDeptSimpleName("ORG_ID");			//所属机构
        												//RPART_ID 替换配件主键
        												//OEM_COMPANY_ID
        												//SECRET_LEVEL 秘级
             
        bs.setFieldDic("STATUS", "YXBS");   //配件状态
        bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
         
    	return bs;
    }
	//查询配件
	public BaseResultSet partSearch(PageManager page,String conditions) throws Exception {
    	String wheres = conditions;  
    	wheres += " AND STATUS=100201 AND PART_STATUS NOT IN(200602)";
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
    	sql.append("PCH_PRICE,\n" );
    	sql.append("STATUS\n" );
    	sql.append("FROM PT_BA_INFO");

    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page); 
    	return bs;
    }
	//批量新增
	public boolean batchInsert(String partIds,PtBaReplacementVO vo)
			throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" INSERT INTO PT_BA_REPLACEMENT  \n");
		sql.append(" (REPLACE_ID,  \n");
		sql.append(" PART_ID,  \n");
		sql.append(" PART_CODE,  \n");
		sql.append(" PART_NAME,  \n");
		sql.append(" RPART_ID,  \n");
		sql.append(" RPART_CODE,  \n");
		sql.append(" RPART_NAME,  \n");
		sql.append(" REMARKS,  \n");
		sql.append(" STATUS)  \n");
		
		sql.append(" SELECT f_getid(),  \n");
		sql.append(" '"+vo.getPartId()+"' ,\n");
		sql.append(" '"+vo.getPartCode()+"' ,\n");
		sql.append(" '"+vo.getPartName()+"' ,\n");
		sql.append(" PART_ID ,\n");
		sql.append(" PART_CODE ,\n");
		sql.append(" PART_NAME ,\n");
		sql.append(" '"+vo.getRemarks()+"' ,\n");
		sql.append(" '"+vo.getStatus()+"' \n");
		sql.append(" from PT_BA_INFO  \n");
		sql.append(" WHERE PART_ID IN (" + partIds + ") \n");
		
		return factory.update(sql.toString(), null);
	}
	
}
