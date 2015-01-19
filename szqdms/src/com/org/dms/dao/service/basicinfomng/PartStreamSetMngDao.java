package com.org.dms.dao.service.basicinfomng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.PtBaPartSupplierRlVO;
import com.org.framework.base.*;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * @Title: szqdms
 * @description: 配件流水号设置管理类
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年7月24日 
 */
public class PartStreamSetMngDao extends BaseDAO
{
    //定义instance
    public  static final PartStreamSetMngDao getInstance(ActionContext atx)
    {
        PartStreamSetMngDao dao = new PartStreamSetMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	
	public boolean setStream(PtBaPartSupplierRlVO vo) throws Exception
    {
    	return factory.update(vo);
    }

	/**
	 * 配件流水号设置查询方法
	 * @param  page   供应商加上售后有效
	 * @param  relationid
	 * @param  conditions
	 * @return
	 * @throws Exception
	 */
    public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND RL.SUPPLIER_ID = S.SUPPLIER_ID" ;
    	wheres += " AND RL.PART_ID = P.PART_ID" ;
    	wheres += " AND RL.SE_IDENTIFY ="+DicConstant.YXBS_01+"" ;
    	wheres += " AND S.SE_IDENTIFY ="+DicConstant.YXBS_01+"" ;
    	wheres += " ORDER BY RL.SUPPLIER_ID" ;
    	
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT RL.RELATION_ID,\n" );
    	sql.append("       RL.SUPPLIER_ID,\n" );
    	sql.append("       RL.PART_ID,\n" );
    	sql.append("       RL.IF_STREAM,\n" );
    	//sql.append("       S.SUPPLIER_ID,\n" );
    	sql.append("       S.SUPPLIER_CODE,\n" );
    	sql.append("       S.SUPPLIER_NAME,\n" );
    	//sql.append("       P.PART_ID,\n" );
    	sql.append("       P.PART_CODE,\n" );
    	sql.append("       P.PART_NAME\n" );
    	sql.append("  FROM PT_BA_PART_SUPPLIER_RL RL, PT_BA_SUPPLIER S, PT_BA_INFO P\n" );
    	
    	/*sql的两种写法
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.RELATION_ID,\n" );
    	sql.append("       T.PART_ID,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.IF_STREAM,\n" );
    	sql.append("       (SELECT N.PART_NAME　FROM PT_BA_INFO N\n" );
    	sql.append("         WHERE T.PART_ID = N.PART_ID) AS PART_NAME,\n" );
    	sql.append("       (SELECT N.PART_CODE　FROM PT_BA_INFO N\n" );
    	sql.append("         WHERE T.PART_ID = N.PART_ID) AS PART_CODE,\n" );
    	sql.append("       (SELECT M.SUPPLIER_NAME　FROM PT_BA_SUPPLIER M\n" );
    	sql.append("         WHERE T.SUPPLIER_ID = M.SUPPLIER_ID) AS SUPPLIER_NAME,\n" );
    	sql.append("       (SELECT M.SUPPLIER_CODE　FROM PT_BA_SUPPLIER M\n" );
    	sql.append("         WHERE T.SUPPLIER_ID = M.SUPPLIER_ID) AS SUPPLIER_CODE\n" );
    	sql.append("  FROM PT_BA_PART_SUPPLIER_RL T");
    	*/
    	
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("IF_STREAM", "SF");
    	return bs;
    }
    
	/**
	 * 配件流水号批量设置
	 * 
	 * @throws Exception
	 */
	public boolean batchSet(String relationIds,String ifStream) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE PT_BA_PART_SUPPLIER_RL \n");
		sql.append(" SET IF_STREAM = '" + ifStream + "' \n");
		sql.append(" WHERE RELATION_ID IN (" + relationIds + ") \n");
		return factory.update(sql.toString(), null);
	}
	
    //下载(导出) 供应商加上售后有效
    public QuerySet download(String conditions, User user) throws Exception
    {	    	
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT ROWNUM,\n" );
    	sql.append("       RL.RELATION_ID,\n" );
    	sql.append("       RL.SUPPLIER_ID,\n" );
    	sql.append("       RL.PART_ID,\n" );
    	sql.append("       RL.IF_STREAM,\n" );
    	sql.append("       S.SUPPLIER_CODE,\n" );
    	sql.append("       S.SUPPLIER_NAME,\n" );
    	sql.append("       P.PART_CODE,\n" );
    	sql.append("       P.PART_NAME,\n" );
    	sql.append("       A.DIC_VALUE\n" );
    	sql.append("  FROM PT_BA_PART_SUPPLIER_RL RL, PT_BA_SUPPLIER S, PT_BA_INFO P, DIC_TREE A\n" );
		sql.append(" where "+conditions+"\n" );
		sql.append(" AND RL.SUPPLIER_ID = S.SUPPLIER_ID\n" );
		sql.append(" AND RL.PART_ID = P.PART_ID\n" );
		sql.append(" AND RL.SE_IDENTIFY ="+DicConstant.YXBS_01+"\n" );
		sql.append(" AND RL.SE_IDENTIFY ="+DicConstant.YXBS_01+"\n" );
		sql.append(" AND TO_CHAR(RL.IF_STREAM) = A.DIC_CODE\n" );

    	return factory.select(null, sql.toString());
    }
}