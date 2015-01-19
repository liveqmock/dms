package com.org.dms.dao.service.oldpartMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuReturnorderDetailVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * 旧件修改申请
 * @author zts
 *
 */
public class OldPartUpdateApplyDao extends BaseDAO{

	 //定义instance
	public  static final OldPartUpdateApplyDao getInstance(ActionContext atx)
	{
		OldPartUpdateApplyDao dao = new OldPartUpdateApplyDao();	
		atx.setDBFactory(dao.factory);
		return dao;
	}
	
	/**
	 * 旧件查询
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet oldPartSearch(PageManager page,String conditions,User user)throws Exception{
		String wheres=conditions;
			wheres +=" AND D.CLAIM_ID = C.CLAIM_ID \n"+
		    		    " AND D.OLD_PART_STATUS = "+DicConstant.JJZT_03+" \n"+
		    		    " AND NVL(D.UPDATE_STATUS, 0) NOT IN ("+DicConstant.JJXGSQZT_01+", "+DicConstant.JJXGSQZT_02+")\n"+
		    		    " AND C.ORG_ID = "+user.getOrgId()+"\n"+
		       			" ORDER BY D.DETAIL_ID";
		page.setFilter(wheres);
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT D.DETAIL_ID,\n" );
		sql.append("       D.ORDER_NO,\n" );
		sql.append("       D.PART_ID,\n" );
		sql.append("       D.PART_CODE,\n" );
		sql.append("       D.PART_NAME,\n" );
		sql.append("       D.CLAIM_NO,\n" );
		sql.append("       D.ACTUL_COUNT,\n" );
		sql.append("       D.PROSUPPLY_ID,\n" );
		sql.append("       D.PROSUPPLY_NAME,\n" );
		sql.append("       D.PROSUPPLY_CODE,\n" );
		sql.append("       D.UPDATE_OPINION,\n" );
		sql.append("       D.UPDATE_STATUS\n" );
		sql.append("  FROM SE_BU_RETURNORDER_DETAIL D, SE_BU_CLAIM C");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("UPDATE_STATUS", "JJXGSQZT");
		return bs;
	}

	
	/**
	 * 修改旧件信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean udpateDetail(SeBuReturnorderDetailVO vo)throws Exception{
		return factory.update(vo);
	}
	
	/**
	 * 车厂端旧件修改查询
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet oldPartOemSearch(PageManager page,String conditions,User user)throws Exception{
		BaseResultSet bs=null;
		String wheres=conditions;
		wheres +=" AND D.CLAIM_ID = C.CLAIM_ID \n"+
	    		    " AND D.OLD_PART_STATUS = "+DicConstant.JJZT_03+" \n"+
	    		    " AND NVL(D.UPDATE_STATUS, 0) NOT IN ("+DicConstant.JJXGSQZT_01+", "+DicConstant.JJXGSQZT_02+")\n"+
	       			" ORDER BY D.DETAIL_ID";
		page.setFilter(wheres);
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT D.DETAIL_ID,\n" );
		sql.append("       D.ORDER_NO,\n" );
		sql.append("       D.PART_ID,\n" );
		sql.append("       C.ORG_ID ORG_NAME,\n" );
		sql.append("       C.ORG_ID ORG_CODE,\n" );
		sql.append("       D.PART_CODE,\n" );
		sql.append("       D.PART_NAME,\n" );
		sql.append("       D.CLAIM_NO,\n" );
		sql.append("       NVL(D.ACTUL_COUNT,0)ACTUL_COUNT,\n" );
		sql.append("       D.PROSUPPLY_ID,\n" );
		sql.append("       D.PROSUPPLY_NAME,\n" );
		sql.append("       D.PROSUPPLY_CODE\n" );
		sql.append("  FROM SE_BU_RETURNORDER_DETAIL D, SE_BU_CLAIM C");
		bs=factory.select(sql.toString(), page);
		bs.setFieldOrgDeptSimpleName("ORG_NAME");
		bs.setFieldOrgDeptCode("ORG_CODE");
		return bs;
	}
	
	/***
	 * 旧件修改审核查询
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet oldPartCheckSearch(PageManager page,String conditions,User user)throws Exception{
		BaseResultSet bs=null;
		String wheres=conditions;
		wheres +=" AND D.CLAIM_ID = C.CLAIM_ID \n"+
	    		 " AND D.OLD_PART_STATUS = "+DicConstant.JJZT_03+" \n"+
	    		 " AND D.UPDATE_STATUS = "+DicConstant.JJXGSQZT_01+"\n"+
	       		 " ORDER BY D.DETAIL_ID";
		page.setFilter(wheres);
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT D.DETAIL_ID,\n" );
		sql.append("       D.ORDER_NO,\n" );
		sql.append("       D.PART_ID,\n" );
		sql.append("       C.ORG_ID             ORG_NAME,\n" );
		sql.append("       C.ORG_ID             ORG_CODE,\n" );
		sql.append("       D.PART_CODE,\n" );
		sql.append("       D.PART_NAME,\n" );
		sql.append("       D.CLAIM_NO,\n" );
		sql.append("       D.ACTUL_COUNT,\n" );
		sql.append("       D.PROSUPPLY_ID,\n" );
		sql.append("       D.PROSUPPLY_NAME,\n" );
		sql.append("       D.PROSUPPLY_CODE,\n" );
		sql.append("       D.UPDATE_PART_CODE,\n" );
		sql.append("       D.UPDATE_PART_NAME,\n" );
		sql.append("       D.UPDATE_PART_COUNT,\n" );
		sql.append("       D.UPDATE_SUPPLY_CODE,\n" );
		sql.append("       D.UPDATE_SUPPLY_NAME,\n" );
		sql.append("       D.UPDATE_REMARKS \n");
		sql.append("  FROM SE_BU_RETURNORDER_DETAIL D, SE_BU_CLAIM C");
		bs=factory.select(sql.toString(), page);
		bs.setFieldOrgDeptSimpleName("ORG_NAME");
		bs.setFieldOrgDeptCode("ORG_CODE");
		return bs;
	}
	
	/**
	 * 渠道商 旧件修改查询
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet oldPartUpdateSearch(PageManager page,String conditions,User user)throws Exception{
		String wheres=conditions;
		wheres +=" AND D.CLAIM_ID = C.CLAIM_ID \n"+
	    		 " AND D.UPDATE_STATUS > 0 \n"+
	    		 " AND C.ORG_ID = "+user.getOrgId()+"\n"+
	       		 " ORDER BY D.DETAIL_ID";
	    page.setFilter(wheres);
	    BaseResultSet bs=null;
	    StringBuffer sql= new StringBuffer();
	    sql.append("SELECT D.DETAIL_ID,\n" );
	    sql.append("       D.ORDER_NO,\n" );
	    sql.append("       D.PART_ID,\n" );
		sql.append("       D.PART_CODE,\n" );
		sql.append("       D.PART_NAME,\n" );
		sql.append("       D.CLAIM_NO,\n" );
		sql.append("       D.ACTUL_COUNT,\n" );
		sql.append("       D.PROSUPPLY_ID,\n" );
		sql.append("       D.PROSUPPLY_NAME,\n" );
		sql.append("       D.PROSUPPLY_CODE,\n" );
		sql.append("       D.UPDATE_PART_CODE,\n" );
		sql.append("       D.UPDATE_PART_NAME,\n" );
		sql.append("       D.UPDATE_PART_COUNT,\n" );
		sql.append("       D.UPDATE_SUPPLY_CODE,\n" );
		sql.append("       D.UPDATE_SUPPLY_NAME,\n" );
		sql.append("       D.UPDATE_REMARKS, \n");
		sql.append("       D.UPDATE_OPINION,\n" );
		sql.append("       D.UPDATE_STATUS\n" );
		sql.append("  FROM SE_BU_RETURNORDER_DETAIL D, SE_BU_CLAIM C");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("UPDATE_STATUS", "JJXGSQZT");
		return bs;
	}
	
	/**
	 * 车厂端 旧件修改查询
	 * @param page
	 * @param conditions
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet oldPartUpdateOemSearch(PageManager page,String conditions,User user)throws Exception{
		BaseResultSet bs=null;
		String wheres=conditions;
		wheres +=" AND D.CLAIM_ID = C.CLAIM_ID \n"+
	    		 " AND D.UPDATE_STATUS = "+DicConstant.JJXGSQZT_02+"\n"+
	       		 " ORDER BY D.DETAIL_ID";
		page.setFilter(wheres);
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT D.DETAIL_ID,\n" );
		sql.append("       D.ORDER_NO,\n" );
		sql.append("       D.PART_ID,\n" );
		sql.append("       C.ORG_ID             ORG_NAME,\n" );
		sql.append("       C.ORG_ID             ORG_CODE,\n" );
		sql.append("       D.PART_CODE,\n" );
		sql.append("       D.PART_NAME,\n" );
		sql.append("       D.CLAIM_NO,\n" );
		sql.append("       D.ACTUL_COUNT,\n" );
		sql.append("       D.PROSUPPLY_ID,\n" );
		sql.append("       D.PROSUPPLY_NAME,\n" );
		sql.append("       D.PROSUPPLY_CODE,\n" );
		sql.append("       D.UPDATE_PART_CODE,\n" );
		sql.append("       D.UPDATE_PART_NAME,\n" );
		sql.append("       D.UPDATE_PART_COUNT,\n" );
		sql.append("       D.UPDATE_SUPPLY_CODE,\n" );
		sql.append("       D.UPDATE_SUPPLY_NAME,\n" );
		sql.append("       D.UPDATE_REMARKS, \n");
		sql.append("       D.UPDATE_OPINION \n");
		sql.append("  FROM SE_BU_RETURNORDER_DETAIL D, SE_BU_CLAIM C");
		bs=factory.select(sql.toString(), page);
		bs.setFieldOrgDeptSimpleName("ORG_NAME");
		bs.setFieldOrgDeptCode("ORG_CODE");
		return bs;
	}
}
