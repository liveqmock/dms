package com.org.dms.dao.service.oldpartMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuReturnorderNotVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 旧件不回运审核DAO
 * @author zts
 *
 */
public class OldPartNotReturnCheckDao extends BaseDAO{

	 //定义instance
	public  static final OldPartNotReturnCheckDao getInstance(ActionContext atx)
	{
		OldPartNotReturnCheckDao dao = new OldPartNotReturnCheckDao();
	    atx.setDBFactory(dao.factory);
	    return dao;
	}
	

	/**
	 * 不回运申请
	 * @param page
	 * @param condition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet oldPartNotReturnSearch(PageManager page,String conditions,User user)throws Exception{
		String wheres = conditions;
    	wheres += " ORDER BY T.APPLY_STATUS,T.ORG_ID \n" ;
        page.setFilter(wheres);
        BaseResultSet bs = null;
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT T.NOTBACK_ID,\n" );
        sql.append("       T.ORG_ID     ORG_NAME,\n" );
        sql.append("       T.ORG_ID     ORG_CODE,\n" );
        sql.append("       T.CLAIM_ID,\n" );
        sql.append("       T.CLAIM_TYPE,\n" );
        sql.append("       T.CLAIM_NO,\n" );
        sql.append("       T.APPLY_USER,\n" );
        sql.append("       T.APPLY_DATE,\n" );
        sql.append("       T.APPLY_STATUS,\n" );
        sql.append("       T.REMARKS\n" );
        sql.append("  FROM SE_BU_RETURNORDER_NOT T\n" );
	    bs=factory.select(sql.toString(), page);
	    bs.setFieldDic("APPLY_STATUS","BHYSQZT");
	    bs.setFieldDateFormat("APPLY_DATE","yyyy-MM-dd");
	    bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
    	bs.setFieldOrgDeptCode("ORG_CODE");
		return bs;
	}
	/**
	 * 不回运申请
	 * @param page
	 * @param condition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet searchCheckParts(PageManager page,String conditions,User user,String notbackId)throws Exception{
		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.DTL_ID,\n" );
		sql.append("       T.NOTBACK_ID,\n" );
		sql.append("       T.PART_ID,\n" );
		sql.append("       T.PART_CODE,\n" );
		sql.append("       T.PART_NAME,\n" );
		sql.append("       T.SUPPLIER_ID,\n" );
		sql.append("       T.DTL_COUNT,\n" );
		sql.append("       T.CREATE_USER,\n" );
		sql.append("       T.CREATE_TIME,\n" );
		sql.append("       T.UPDATE_USER,\n" );
		sql.append("       T.UPDATE_TIME,\n" );
		sql.append("       T.FAULT_CODE,\n" );
		sql.append("       N.CLAIM_NO,\n" );
		sql.append("       S.SUPPLIER_NAME,\n" );
		sql.append("       S.SUPPLIER_CODE\n" );
		sql.append("    FROM SE_BU_RETURNORDER_NOT_DTL T,SE_BU_RETURNORDER_NOT N,PT_BA_SUPPLIER S\n" );
		sql.append(" WHERE T.NOTBACK_ID = "+notbackId+"");
		sql.append(" AND S.SUPPLIER_ID = T.SUPPLIER_ID");
		sql.append(" AND N.NOTBACK_ID = T.NOTBACK_ID");
		sql.append(" ORDER BY T.PART_ID");
		bs=factory.select(sql.toString(), page);
		return bs;
	}
	//查询经销商对应某一月的所有索赔单
	public QuerySet queryNotbackIds(String orgIds,String applyMonth) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.NOTBACK_ID\n" );
		sql.append("  FROM SE_BU_RETURNORDER_NOT T\n" );
		sql.append(" WHERE 1 = 1\n" );
		sql.append("   AND APPLY_MONTH = '"+applyMonth+"'\n" );
		if(!orgIds.equals("anull")){
			sql.append("   AND T.ORG_ID in ("+orgIds+")\n" );
		}
		sql.append("   AND T.APPLY_STATUS = "+DicConstant.BHYSQZT_01+"\n" );
		sql.append(" ORDER BY T.CLAIM_NO");
		return factory.select(null, sql.toString());
	}
	/**
	 * 更新不回运表状态
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updateNotReturn(SeBuReturnorderNotVO vo)throws Exception{
		return factory.update(vo);
	}
}
