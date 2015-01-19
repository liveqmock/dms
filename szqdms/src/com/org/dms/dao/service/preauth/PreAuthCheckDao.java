/**
 * @throws Exception
 */
package com.org.dms.dao.service.preauth;

import java.sql.SQLException;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuPreAuthorCheckVO;
import com.org.dms.vo.service.SeBuPreAuthorVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 预授权审核DAO
 * @author zts
 *
 */
public class PreAuthCheckDao extends BaseDAO{
	//定义instance
    public  static final PreAuthCheckDao getInstance(ActionContext atx)
    {
    	PreAuthCheckDao dao = new PreAuthCheckDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    /**
     * 预授权审核查询
     * @param page 分页
     * @param user 用户
     * @param conditions 前台条件
     * @return nnj                                                                                                                                                                                                                                jjj
     * @throws SQLException 
     */
    public BaseResultSet preAuthCheckSearch(PageManager page, User user, String conditions) throws SQLException{
    	String wheres = conditions;
    	wheres += " AND AU.VEHICLE_ID = VH.VEHICLE_ID \n" +
    			  " AND AU.AUTHOR_STATUS IN("+DicConstant.YSQZT_02+") \n" +
    			  " AND AU.OEM_COMPANY_ID="+user.getOemCompanyId()+" \n" +
    			  " AND AU.STATUS="+DicConstant.YXBS_01+" \n" +
				  " AND EXISTS (\n " +
				  "    SELECT 1  FROM SE_BA_PREAUTHOR_USER U ,SE_BA_PREAUTHOR_LEVEL L\n" + 
				  "           WHERE U.LEVEL_CODE = L.LEVEL_CODE\n" + 
				  "           AND AU.LEVEL_CODE = L.LEVEL_CODE\n" + 
				  "           AND U.USER_CODE='"+user.getAccount()+"'\n" + 
				  " ) \n";
		 if(DicConstant.JGJB_04.equals(user.getOrgDept().getLevelCode())){
	     	wheres+= " AND O.ORG_ID = AU.ORG_ID \n"+
	     			 " AND O.PID= O1.ORG_ID AND O1.ORG_ID="+user.getOrgId()+" \n";
	     }
    	wheres += " ORDER BY NVL(AU.REPORT_DATE,TO_DATE('2014-01-01','YYYY-MM-DD')) DESC " ;
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT AU.AUTHOR_ID,\n" );
    	sql.append("       AU.AUTHOR_NO,\n" );
    	sql.append("       AU.AUTHOR_STATUS,\n" );
    	sql.append("       AU.REPORT_DATE,\n" );
    	sql.append("       AU.AUTHOR_TYPE,\n" );
    	sql.append("       AU.MILEAGE,\n" );
    	sql.append("       AU.LICENSE_PLATE,\n" );
    	sql.append("       AU.USER_NAME,\n" );
    	sql.append("       AU.USER_NO,\n" );
    	sql.append("       AU.LINK_MAN,\n" );
    	sql.append("       AU.PHONE,\n" );
    	sql.append("       AU.USER_ADDRESS,\n" );
    	sql.append("       AU.REMARKS PREAUTH_REMARKS,\n" );
    	sql.append("       AU.ORG_ID ORG_CODE,\n" );
    	sql.append("       AU.ORG_ID ORG_NAME,\n" );
    	sql.append("       AU.TELEPHONE,\n" );
    	sql.append("       AU.FAULTLOCATION,\n" );
    	sql.append("       AU.GO_DATE,\n" );
    	sql.append("       AU.ARRIVE_DATE,\n" );
    	sql.append("       AU.OUT_UCOUNT,\n" );
    	sql.append("       AU.OUT_USER,\n" );
    	sql.append("       AU.TRAILER_COST,\n" );
    	sql.append("       AU.GPS_LICENSE_PLATE,\n" );
    	sql.append("       AU.AMOUNT,\n" );
    	sql.append("       VH.VEHICLE_ID,\n" );
    	sql.append("       VH.VIN,\n" );
    	sql.append("       VH.ENGINE_NO,\n" );
    	sql.append("       VH.VEHICLE_STATUS,\n" );
    	sql.append("       VH.MODELS_ID,\n" );
    	sql.append("       VH.MODELS_CODE,\n" );
    	sql.append("       VH.CERTIFICATE,\n" );
    	sql.append("       VH.ENGINE_TYPE,\n" );
    	sql.append("       VH.VEHICLE_USE,\n" );
    	sql.append("       VH.DRIVE_FORM,\n" );
    	sql.append("       VH.BUY_DATE,\n" );
    	sql.append("       VH.GUARANTEE_NO,\n" );
    	sql.append("       VH.FACTORY_DATE,\n" );
    	sql.append("       VH.MAINTENANCE_DATE,\n" );
    	sql.append("       VH.USER_TYPE");
        sql.append("  FROM SE_BU_PRE_AUTHOR AU, MAIN_VEHICLE VH \n" );
        if(DicConstant.JGJB_04.equals(user.getOrgDept().getLevelCode())){
        	sql.append(" ,TM_ORG O ,TM_ORG O1 ");
        }
        bs=factory.select(sql.toString(), page);
        bs.setFieldDateFormat("REPORT_DATE", "yyyy-MM-dd HH:mm:ss");
        bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("FACTORY_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("MAINTENANCE_DATE", "yyyy-MM-dd");
        bs.setFieldDic("AUTHOR_STATUS","YSQZT");
        bs.setFieldDic("AUTHOR_TYPE","YSQLX");
        bs.setFieldDic("AUTHOR_TYPE","YSQLX");
        bs.setFieldDic("VEHICLE_USE","CLYT");
        bs.setFieldDic("USER_TYPE","CLYHLX");
        bs.setFieldDic("DRIVE_FORM","QDXS");
    	bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
    	bs.setFieldOrgDeptCode("ORG_CODE");
    	return bs;
    }
    
    /**
     * 修改预授权
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updatePreAuth(SeBuPreAuthorVO vo)throws Exception
    {
    	return factory.update(vo);
    }
    /**
     * 预授权审核轨迹
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertPreAuthCheck(SeBuPreAuthorCheckVO vo)throws Exception
    {
    	return factory.insert(vo);
    }
    
    /**
     * 获取预授权审核规则
     * @param authorId
     * @return
     * @throws Exception
     */
    public QuerySet getAuthorRule(String authorId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT A.LEVEL_CODE PRE_LEVEL_CODE, R.LEVEL_CODE RULE_LEVEL_CODE\n" );
    	sql.append("  FROM SE_BU_PRE_AUTHOR A, SE_BA_PREAUTHOR_RULE R\n" );
    	sql.append(" WHERE A.RULE_ID = R.RULE_ID\n" );
    	sql.append("   AND A.AUTHOR_ID = "+authorId+"");
    	return factory.select(null, sql.toString());
    }
    
}
