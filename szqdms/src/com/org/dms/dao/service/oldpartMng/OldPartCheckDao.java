package com.org.dms.dao.service.oldpartMng;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuReturnOrderVO;
import com.org.dms.vo.service.SeBuReturnorderCheckVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
/**
 * 旧件回运审核DAO
 * @author zts
 *
 */
public class OldPartCheckDao extends BaseDAO{

	 //定义instance
  public  static final OldPartCheckDao getInstance(ActionContext atx)
  {
	  OldPartCheckDao dao = new OldPartCheckDao();
      atx.setDBFactory(dao.factory);
      return dao;
  }
  /**
   * 审核查询
   * @param page
   * @param conditions
   * @param user
   * @return
   * @throws SQLException
   */
  public BaseResultSet oldPartSearch(PageManager page ,String conditions,User user) throws SQLException{
	   String wheres = conditions;
	   wheres +=" AND O.ORDER_STATUS IN ("+DicConstant.HYDZT_02+") \n "+
			    " AND O.STATUS= "+DicConstant.YXBS_01+" ";
	   page.setFilter(wheres);
	   BaseResultSet bs = null;
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT O.ORDER_ID,\n" );
	   sql.append("       O.ORDER_NO,\n" );
	   sql.append("       O.PRODUCE_DATE,\n" );
	   sql.append("       O.RETURN_DATE,\n" );
	   sql.append("       O.TRANS_TYPE,\n" );
	   sql.append("       O.ORG_ID ORG_CODE,\n" );
	   sql.append("       O.ORG_ID ORG_NAME,\n" );
	   sql.append("       O.AMOUNT,\n" );
	   sql.append("       O.REMARKS,\n" );
	   sql.append("       NVL((SELECT NVL(SUM(D.OUGHT_COUNT), 0)\n" );
	   sql.append("             FROM SE_BU_RETURNORDER_DETAIL D\n" );
	   sql.append("            WHERE D.ORDER_ID = O.ORDER_ID\n" );
	   sql.append("            GROUP BY D.ORDER_ID),\n" );
	   sql.append("           0) AS PARTCOUNT,\n" );
	   sql.append("       NVL((SELECT NVL(COUNT(A.CLAIM_ID), 0)\n" );
	   sql.append("             FROM (SELECT DISTINCT D.ORDER_ID, D.CLAIM_ID\n" );
	   sql.append("                     FROM SE_BU_RETURNORDER_DETAIL D) A\n" );
	   sql.append("            WHERE A.ORDER_ID = O.ORDER_ID\n" );
	   sql.append("            GROUP BY A.ORDER_ID),\n" );
	   sql.append("           0) AS CLAIMCOUNT\n" );
	   sql.append("  FROM SE_BU_RETURN_ORDER O");
	   bs=factory.select(sql.toString(), page);
	   bs.setFieldDic("TRANS_TYPE","HYDYSFS");
	   bs.setFieldDateFormat("PRODUCE_DATE", "yyyy-MM");
	   bs.setFieldDateFormat("RETURN_DATE", "yyyy-MM-dd");
	   bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
	   bs.setFieldOrgDeptCode("ORG_CODE");
	   return bs;
  }
  /**
   * 审核通过
   * @param vo
   * @return
   * @throws Exception
   */
  public boolean updateOldPart(SeBuReturnOrderVO vo)throws Exception
  {
  	return factory.update(vo);
  }
  /**
   * 旧件审核轨迹
   * @param vo
   * @return
   * @throws Exception
   */
  public boolean insertOldPartCheck(SeBuReturnorderCheckVO vo)throws Exception
  {
  	return factory.insert(vo);
  }
}
