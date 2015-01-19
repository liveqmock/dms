package com.org.dms.dao.service.basicinfomng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBaInfoVO;
import com.org.dms.vo.part.PtBaSupplierVO;
import com.org.dms.vo.service.PtBaPartSupplierRlVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class SupplierClaimDateDao extends BaseDAO {
	//定义instance
    public  static final SupplierClaimDateDao getInstance(ActionContext atx)
    {
    	SupplierClaimDateDao dao = new SupplierClaimDateDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    
    /**
     * 供应商配件查询
     * @param page  加上供应商售后有效标识
     * @param conditions
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet supplierPartSearch(PageManager page,String conditions,User user)throws Exception{
    	BaseResultSet bs=null;
    	String wheres= conditions;
    	       wheres+=" AND S.SUPPLIER_ID = R.SUPPLIER_ID \n"+
    	    		   " AND R.PART_ID = P.PART_ID\n"+
    	    		   " AND R.SE_IDENTIFY ="+DicConstant.YXBS_01+"\n"+
    	    		   " AND S.SE_IDENTIFY ="+DicConstant.YXBS_01+"\n"+
    	    		   " ORDER BY R.RELATION_ID";
    	page.setFilter(wheres);
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT R.RELATION_ID,\n" );
    	sql.append("       NVL(R.CLAIM_RATE,0) CLAIM_RATE,\n" );
    	sql.append("       S.SUPPLIER_ID,\n" );
    	sql.append("       S.SUPPLIER_NAME,\n" );
    	sql.append("       S.SUPPLIER_CODE,\n" );
    	sql.append("       P.PART_ID,\n" );
    	sql.append("       P.PART_CODE,\n" );
    	sql.append("       P.PART_NAME\n" );
    	sql.append("  FROM PT_BA_SUPPLIER S, PT_BA_PART_SUPPLIER_RL R, PT_BA_INFO P\n" );
        bs=factory.select(sql.toString(), page);
        return bs;
    }
    
    /**
     * 
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateSpR(PtBaPartSupplierRlVO vo)throws Exception{
    	return factory.update(vo);
    }
    
    
    /**
     * 旧件管理费系数查询
     * @param page  售后供应商有效标识
     * @param conditions
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet oldPartManageSearch(PageManager page,String conditions,User user)throws Exception{
    	BaseResultSet bs=null;
    	String wheres= conditions;
    	       wheres+=" AND P.SE_STATUS = 100201 \n"+ 
    	    		   " ORDER BY P.PART_ID";
    	page.setFilter(wheres);
    	StringBuffer sql= new StringBuffer();
    	sql.append(" SELECT P.PART_ID,\n" );
    	sql.append("       	NVL(P.OLD_MANAGE_FEE, 0) OLD_MANAGE_FEE,\n");
    	sql.append("       	P.PART_CODE,\n" );
    	sql.append("      	P.PART_NAME\n" );
    	sql.append("  FROM  PT_BA_INFO P\n" );
        bs=factory.select(sql.toString(), page);
        return bs;
    }
    
    /**
     * 旧件管理系数设置
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateOldManageFee(PtBaInfoVO vo)throws Exception{
    	return factory.update(vo);
    }
    
    /**
     * 供应商审核权查询
     * @param page
     * @param conditions
     * @param user
     * @return
     * @throws Exception
     */
    public BaseResultSet suppClaimCheckSearch(PageManager page,String conditions,User user)throws Exception{
    	BaseResultSet bs=null;
    	String wheres= conditions;
    	       wheres+=" ORDER BY S.SUPPLIER_ID \n";
    	page.setFilter(wheres);
    	StringBuffer sql1= new StringBuffer();
    	sql1.append("SELECT S.SUPPLIER_ID, S.SUPPLIER_NAME, S.SUPPLIER_CODE, S.IF_CLAIM_CHECK\n" );
    	sql1.append("  FROM PT_BA_SUPPLIER S");
    	bs=factory.select(sql1.toString(), page);
    	bs.setFieldDic("IF_CLAIM_CHECK", "SF");
    	return bs;
    }
    
    /**
     * 供应商审核权设置
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateSupplier(PtBaSupplierVO vo)throws Exception{
    	return factory.update(vo);
    }
    
    /**
     * 配件导出
     * @return
     * @throws Exception
     */
    public QuerySet partDownload(String conditions,User user)throws Exception{
    	StringBuffer sql= new StringBuffer();
		sql.append(" SELECT P.PART_ID,\n" );
		sql.append("       	NVL(P.OLD_MANAGE_FEE, 0) OLD_MANAGE_FEE,\n");
		sql.append("       	P.PART_CODE,\n" );
		sql.append("      	P.PART_NAME\n" );
		sql.append("  FROM  PT_BA_INFO P\n" );
		sql.append(" WHERE "+conditions+" AND  P.SE_STATUS = 100201  \n");
		sql.append(" ORDER BY P.PART_ID ");
	    return factory.select(null, sql.toString());
    }
    
    public BaseResultSet partImpSearch(PageManager page,String conditions,User user)throws Exception{
    	BaseResultSet bs = null;
 	   	StringBuffer sql= new StringBuffer();
 	   	sql.append("SELECT D.* \n" );
 	   	sql.append("  FROM SE_BA_OLDPARTMANAGE_TMP D");
 	   	sql.append(" WHERE "+conditions+" AND D.USER_ACCOUNT ='"+user.getAccount()+"'");
 	   	bs=factory.select(sql.toString(), page);
 	   	return bs;
    }
    
    /**
     * 导出错误数据
     * @param conditions
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet expData(String rowNum,User user)throws Exception{
    	StringBuffer sql= new StringBuffer();
		sql.append(" SELECT NVL(P.OLD_MANAGE_FEE, 0) OLD_MANAGE_FEE,\n");
		sql.append("       	P.PART_CODE,\n" );
		sql.append("      	P.PART_NAME\n" );
		sql.append("  FROM  SE_BA_OLDPARTMANAGE_TMP P\n" );
		sql.append(" WHERE  "+rowNum+" AND  P.USER_ACCOUNT ='"+user.getAccount()+"'\n");
	    return factory.select(null, sql.toString());
    }
    
    /**
     * 更新旧件管理费系数
     * @param rowNum
     * @param user
     * @return
     * @throws Exception
     */
    public boolean updateOldPart(String rowNum,User user)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("MERGE INTO PT_BA_INFO I\n" );
    	sql.append("USING (SELECT T.PART_CODE, T.PART_NAME, T.OLD_MANAGE_FEE\n" );
    	sql.append("         FROM SE_BA_OLDPARTMANAGE_TMP T\n" );
    	sql.append("        WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("          AND "+rowNum+") T1 \n" );
    	sql.append("ON (I.PART_CODE = T1.PART_CODE AND I.PART_NAME = T1.PART_NAME)\n" );
    	sql.append("WHEN MATCHED THEN\n" );
    	sql.append("  UPDATE SET I.OLD_MANAGE_FEE = T1.OLD_MANAGE_FEE");
    	return factory.update(sql.toString(), null);
    }
}
