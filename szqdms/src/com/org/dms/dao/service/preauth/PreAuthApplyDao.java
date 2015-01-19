package com.org.dms.dao.service.preauth;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuPreAuthorPartVO;
import com.org.dms.vo.service.SeBuPreAuthorProjectVO;
import com.org.dms.vo.service.SeBuPreAuthorVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 预授权提报DAO
 * @author zts
 *
 */
public class PreAuthApplyDao extends BaseDAO
{
    //定义instance
    public  static final PreAuthApplyDao getInstance(ActionContext atx)
    {
    	PreAuthApplyDao dao = new PreAuthApplyDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 预授权查询
     * @param page 分页
     * @param user 用户
     * @param conditions 前台条件
     * @return nnj                                                                                                                                                                                                                                jjj
     * @throws SQLException 
     */
    public BaseResultSet preAuthsearch(PageManager page, User user, String conditions) throws SQLException{
    
    	String wheres = conditions;
    	wheres += " AND AU.VEHICLE_ID = VH.VEHICLE_ID \n"
    		   +  " AND AU.AUTHOR_STATUS IN("+DicConstant.YSQZT_01+","+DicConstant.YSQZT_03+") \n"
    		   +  " AND AU.STATUS="+DicConstant.YXBS_01+" AND AU.ORG_ID='"+user.getOrgId()+"' \n" 
    		   +  " ORDER BY AU.AUTHOR_NO \n" ;
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
    	sql.append("       AU.REMARKS,\n" );
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
        bs=factory.select(sql.toString(), page);
        bs.setFieldDateFormat("REPORT_DATE", "yyyy-MM-dd HH:mm:ss");
        bs.setFieldDateFormat("GO_DATE", "yyyy-MM-dd HH:mm:ss");
        bs.setFieldDateFormat("ARRIVE_DATE", "yyyy-MM-dd HH:mm:ss");
        bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("FACTORY_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("MAINTENANCE_DATE", "yyyy-MM-dd");
        bs.setFieldDic("AUTHOR_STATUS","YSQZT");
        bs.setFieldDic("AUTHOR_TYPE","YSQLX");
        bs.setFieldDic("VEHICLE_USE","CLYT");
        bs.setFieldDic("USER_TYPE","CLYHLX");
        bs.setFieldDic("DRIVE_FORM","QDXS");
    	return bs;
    }
    /**
     * VIN校验
     * @param vin 
     * @param engineNo 发动机号
     * @return
     * @throws Exception
     */
    public BaseResultSet vinCheckSearch(String vin,String engineNo) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.VEHICLE_ID,\n" );
    	sql.append("       T.VIN,\n" );
    	sql.append("       T.ENGINE_NO,\n" );
    	sql.append("       T.VEHICLE_STATUS,\n" );
    	sql.append("       T.MODELS_ID,\n" );
    	sql.append("       T.MODELS_CODE,\n" );
    	sql.append("       T.CERTIFICATE,\n" );
    	sql.append("       T.ENGINE_TYPE,\n" );
    	sql.append("       T.USER_TYPE,\n" );
    	sql.append("       T.VEHICLE_USE,\n" );
    	sql.append("       T.DRIVE_FORM,\n" );
    	sql.append("       T.BUY_DATE,\n" );
    	sql.append("       T.MILEAGE,\n" );
    	sql.append("       T.GUARANTEE_NO,\n" );
    	sql.append("       T.FACTORY_DATE,\n" );
    	sql.append("       T.MAINTENANCE_DATE,\n" );
    	sql.append("       T.SALE_STATUS,\n" );
    	sql.append("       T.LICENSE_PLATE,\n" );
    	sql.append("       T.USER_NAME,\n" );
    	sql.append("       T.USER_NO,\n" );
    	sql.append("       T.LINK_MAN,\n" );
    	sql.append("       T.PHONE,\n" );
    	sql.append("       T.BLACKLISTFLAG,\n" );
    	sql.append("       T.USER_ADDRESS\n" );
    	sql.append("  FROM MAIN_VEHICLE T\n" );
    	sql.append(" WHERE (T.VIN LIKE '%"+vin+"'  OR T.VIN = '"+vin+"')\n" );
    	sql.append("   AND T.ENGINE_NO = '"+engineNo+"'");
    	return factory.select(sql.toString(), new PageManager());
    }
    /**
     * 新增预授权
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertPreAuth(SeBuPreAuthorVO vo)throws Exception
    {
    	return factory.insert(vo);
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
     * 获取预授权审核角色
     * @return
     * @throws Exception
     */
    public QuerySet getRole()throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT P.ROLE_ID,M.LOCATION \n" );
    	sql.append("  FROM EAP_MENU M, TR_ROLE_MENU_MAP P\n" );
    	sql.append(" WHERE M.TITLE = '预授权审核'\n" );
    	sql.append("   AND P.MENU_NAME = M.NAME");
    	return factory.select(null, sql.toString());
    }
    /**
     * 生成预授权单号
     * @param vo
     * @return
     * @throws Exception
     */
    public String getPreAuthNo()throws Exception
    {
       QuerySet qs = null;
  	   String num="";
  	   StringBuffer sql= new StringBuffer();
  	   sql.append("SELECT F_GETPREAUTH() AS AUTHOR_NO FROM DUAL");
  	   qs = factory.select(null, sql.toString());
  	   if(qs.getRowCount() > 0){
  		   num=qs.getString(1, "AUTHOR_NO");
  	   }
  	   return num;
    	/*QuerySet qs = null;
    	String date = getDateToString();
    	String num = "YSQ";
    	if(date!=null){
			date = date.replaceAll("-", "");
			num+=date;
		}
    	StringBuffer sql = new StringBuffer();
    	//sql.append("SELECT max(").append(numberName).append(") as DH FROM ");//变的字段
		sql.append("SELECT max(").append("AUTHOR_NO").append(") as DH FROM ");
		sql.append( "SE_BU_PRE_AUTHOR").append(" t");
		sql.append(" where t.").append("AUTHOR_NO").append(" like '%").append(num).append("%'");
		qs = factory.select(null, sql.toString());
		if(qs.getRowCount()==0){
			 num+="0001";
		}else{
			    DataObjImpl dateObj = (DataObjImpl) qs.getDataObjs().get(0);
			 	String tem  = dateObj.getString("DH");

			if(tem==null||"null".equals(tem)){
				num+="0001";	
			}else{
				int sz = Integer.parseInt(tem.substring(tem.length()-4, tem.length()))+1;
				//如果1位数
				if(String.valueOf(sz).length()==1){
					num=tem.substring(0, tem.length()-4)+"000"+String.valueOf(sz);
				}
				//如果2位数
				else if(String.valueOf(sz).length()==2){
					num=tem.substring(0, tem.length()-4)+"00"+String.valueOf(sz);
				}
				//如果3位数
				else if(String.valueOf(sz).length()==3){
					num=tem.substring(0, tem.length()-4)+"0"+String.valueOf(sz);
				}
				//如果4位数
				else{
				num=tem.substring(0, tem.length()-4)+String.valueOf(sz);
				}
			}
		 }*/
    }
    public static String getDateToString(){
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String d1 = formatter.format(date);
		if(null!=d1&&!"".equals(d1))
		return d1;
		else return null;
	}	
    
    /**
     * 项目查询
     * @param page 分页
     * @param user 用户
     * @return
     * @throws SQLException 
     */
    public BaseResultSet itemSearch(PageManager page, User user, String authorId) throws SQLException{
    
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT F.PROJECT_ID, F.PROJECT_TYPE, F.TIME_CODE, F.TIME_NAME,F.REMARKS,F.AMOUNT_ID,F.AMOUNT_SET \n" );
    	sql.append("  FROM SE_BU_PRE_AUTHOR_PROJECT F, SE_BU_PRE_AUTHOR A\n" );
    	sql.append(" WHERE F.AUTHOR_ID = A.AUTHOR_ID AND A.AUTHOR_ID="+authorId+"");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDic("PROJECT_TYPE", "XMLX");
    	return bs;
    }
    /**
     * 附件查询
     * @param page 分页
     * @param user 用户
     * @return
     * @throws SQLException 
     */
    public BaseResultSet fileSearch(PageManager page,User user,String authorId) throws SQLException{
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.* FROM FS_FILESTORE T WHERE T.YWZJ = "+authorId+"  ORDER BY T.CJSJ");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("CJSJ","yyyy-MM-dd HH:mm:ss");
    	return bs;
    }
    /**
     * 轨迹查询
     * @param page 分页
     * @param user 用户
     * @return
     * @throws SQLException 
     */
    public BaseResultSet authorHisSearch(PageManager page,User user,String authorId) throws SQLException{
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT F.CHECK_ID,\n" );
    	sql.append("       F.CHECK_USER,\n" );
    	sql.append("       U.PERSON_NAME,\n" );
    	sql.append("       F.CHECK_DATE,\n" );
    	sql.append("       F.CHECK_TYPE,\n" );
    	sql.append("       F.CHECK_RESULT,\n" );
    	sql.append("       F.REMAKS\n" );
    	sql.append("  FROM SE_BU_PRE_AUTHOR_CHECK F, SE_BU_PRE_AUTHOR A, TM_USER U\n" );
    	sql.append(" WHERE F.AUTHOR_ID = A.AUTHOR_ID\n" );
    	sql.append("   AND F.CHECK_USER = U.ACCOUNT AND A.AUTHOR_ID="+authorId+"");
    	sql.append(" ORDER BY F.CHECK_DATE ");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
    	bs.setFieldDic("CHECK_TYPE","SHLX");
    	bs.setFieldDic("CHECK_RESULT","SHJG");
    	return bs;
    }
    
    /**
     * 删除项目
     * @param authorId
     * @return
     * @throws Exception
     */
    public boolean itemDelete(String authorId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("DELETE FROM SE_BU_PRE_AUTHOR_PROJECT T\n" );
    	sql.append("WHERE T.AUTHOR_ID ="+authorId+"");
    	return factory.delete(sql.toString(), null);
    }
    
    /**
     * 删除审核轨迹
     * @param authorId
     * @return
     * @throws Exception
     */
    public boolean authorHisDelete(String authorId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("DELETE FROM SE_BU_PRE_AUTHOR_CHECK T\n" );
    	sql.append("WHERE T.AUTHOR_ID ="+authorId+"");
    	return factory.delete(sql.toString(), null);
    }
    /**
     * 删除预授权
     * @param authorId
     * @return
     * @throws Exception
     */
    public boolean preAuthDelete(String authorId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("DELETE FROM SE_BU_PRE_AUTHOR T\n" );
    	sql.append("WHERE T.AUTHOR_ID ="+authorId+"");
    	return factory.delete(sql.toString(), null);
    }
    
    /**
     * 新增项目
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertItem(SeBuPreAuthorProjectVO vo)throws Exception
    {
    	return factory.insert(vo);
    }
    /**
     * 修改项目
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updateItem(SeBuPreAuthorProjectVO vo) throws Exception
    {
    	return factory.update(vo);
    }
    
    /**
     * 删除预授权项目
     * @param authorId
     * @return
     * @throws Exception
     */
    public boolean preAuthItemDelete(String projectId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("DELETE FROM SE_BU_PRE_AUTHOR_PROJECT T\n" );
    	sql.append("WHERE T.PROJECT_ID ="+projectId+"");
    	return factory.delete(sql.toString(), null);
    }
    /**
     * 删除预授权项目
     * @param authorId
     * @return
     * @throws Exception
     */
    public boolean preAuthAttaDelete(String fjid) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("DELETE FROM FS_FILESTORE T\n" );
    	sql.append("WHERE T.FJID ="+fjid+"");
    	return factory.delete(sql.toString(), null);
    }
    /**
     * 判断是否有项目
     * @throws Exception
     */
	public QuerySet checkReport(String authorId) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.AUTHOR_ID \n" );
    	sql.append("  FROM SE_BU_PRE_AUTHOR T, SE_BU_PRE_AUTHOR_PROJECT P\n" );
    	sql.append(" WHERE T.AUTHOR_ID = P.AUTHOR_ID\n" );
    	sql.append("   AND T.AUTHOR_ID = "+authorId+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	/**
     * 判断是否有附件
     * @throws Exception
     */
	public QuerySet checkReportAtta(String authorId) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.AUTHOR_ID \n" );
    	sql.append("  FROM SE_BU_PRE_AUTHOR T, FS_FILESTORE P\n" );
    	sql.append(" WHERE T.AUTHOR_ID = P.YWZJ \n" );
    	sql.append("   AND T.AUTHOR_ID = "+authorId+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
	/**
	 * 判断该预授权是否有该项目
	 * @param amountId  工时ID
	 * @param authorId  预授权ID
	 * @return
	 */
	public QuerySet getAmount(String amountId,String authorId)throws Exception{
		QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT COUNT(T.PROJECT_ID) COU \n" );
    	sql.append("  FROM SE_BU_PRE_AUTHOR_PROJECT T\n" );
    	sql.append(" WHERE T.AUTHOR_ID = "+authorId+"\n" );
    	sql.append("   AND T.AMOUNT_ID = "+amountId+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
	}
	
	/**
	 * 判断该预授权是否有该项目
	 * @param amountId  工时ID
	 * @param authorId  预授权ID
	 * @param projectId  项目ID
	 * @return
	 */
	public QuerySet getAmountUpdate(String amountId,String authorId,String projectId)throws Exception{
		QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT COUNT(T.PROJECT_ID) COU \n" );
    	sql.append("  FROM SE_BU_PRE_AUTHOR_PROJECT T\n" );
    	sql.append(" WHERE T.AUTHOR_ID = "+authorId+"\n" );
    	sql.append("   AND T.AMOUNT_ID = "+amountId+"\n");
    	sql.append("   AND T.PROJECT_ID != "+projectId+"");
    	qs = factory.select(null, sql.toString());
    	return qs;
	}
	
	/**
	 * 预授权配件查询
	 * @param page
	 * @param user
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet authPartSearch(PageManager page,User user,String authorId,String condition)throws Exception{
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.REL_ID,\n" );
		sql.append("       T.PART_ID,\n" );
		sql.append("       T.PART_CODE,\n" );
		sql.append("       T.PART_NAME,\n" );
		sql.append("       T.QUANTITY,\n" );
		sql.append("       T.UNIT_PRICE\n" );
		sql.append("  FROM SE_BU_PRE_AUTHOR_PART T\n" );
		sql.append(" WHERE "+condition+" AND T.AUTHOR_ID ="+authorId+"");
		bs=factory.select(sql.toString(), page);
		return bs;
	}
	
	/**
	 * 配件查询
	 * @param page
	 * @param user
	 * @param authorId
	 * @param condition
	 * @throws Exception
	 */
	public BaseResultSet searchPart(PageManager page,User user,String authorId,String condition)throws Exception{
		BaseResultSet bs=null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.PART_ID, T.PART_CODE, T.PART_NAME, NVL(T.SE_CLPRICE,0) SE_CLPRICE \n" );
		sql.append("  FROM PT_BA_INFO T\n" );
		sql.append(" WHERE  "+condition+" AND T.SE_STATUS = "+DicConstant.YXBS_01+"\n" );
		sql.append("   AND NOT EXISTS (SELECT 1\n" );
		sql.append("          FROM SE_BU_PRE_AUTHOR_PART P\n" );
		sql.append("         WHERE T.PART_ID = P.PART_ID\n" );
		sql.append("           AND P.AUTHOR_ID = "+authorId+")");
		sql.append(" ORDER BY T.PART_CODE ");
		bs=factory.select(sql.toString(), page);
		return bs;
	}
	
	/**
	 * 插入预授权配件表
	 * @param partIds
	 * @param authorId
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean inertAuthPart(SeBuPreAuthorPartVO vo)throws Exception{
		/*StringBuffer sql= new StringBuffer();
		sql.append("INSERT INTO SE_BU_PRE_AUTHOR_PART\n" );
		sql.append("  (REL_ID,\n" );
		sql.append("   AUTHOR_ID,\n" );
		sql.append("   PART_ID,\n" );
		sql.append("   PART_CODE,\n" );
		sql.append("   PART_NAME,\n" );
		sql.append("   UNIT_PRICE,\n");
		sql.append("   CREATE_USER,\n" );
		sql.append("   CREATE_TIME)\n" );
		sql.append("  SELECT F_GETID(), "+authorId+", T.PART_ID, T.PART_CODE, T.PART_NAME,T.SE_CLPRICE,'"+user.getAccount()+"', SYSDATE\n" );
		sql.append("    FROM PT_BA_INFO T\n" );
		sql.append("   WHERE T.PART_ID IN ("+partIds+")");*/
		return factory.insert(vo);
	}
	
	/**
	 * 删除预授权配件
	 * @param relIds 预授权配件表ID
	 * @return
	 * @throws Exception
	 */
	public boolean deleteAuthPart(String relIds)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("DELETE FROM SE_BU_PRE_AUTHOR_PART T WHERE T.REL_ID IN ("+relIds+")");
		return factory.update(sql.toString(), null);
	}
	
	/**
	 * 预授权明细查询
	 * @param page
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet preAuthDetailSearch(PageManager page,User user,String authorId)throws Exception{
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
    	sql.append("       AU.IF_APPLYCLAIM,\n" );
    	sql.append("       AU.USER_NAME,\n" );
    	sql.append("       AU.USER_NO,\n" );
    	sql.append("       AU.LINK_MAN,\n" );
    	sql.append("       AU.PHONE,\n" );
    	sql.append("       AU.USER_ADDRESS,\n" );
    	sql.append("       AU.REMARKS,\n" );
    	sql.append("       AU.TELEPHONE,\n" );
    	sql.append("       AU.FAULTLOCATION,\n" );
    	sql.append("       AU.GO_DATE,\n" );
    	sql.append("       AU.ARRIVE_DATE,\n" );
    	sql.append("       AU.OUT_UCOUNT,\n" );
    	sql.append("       AU.OUT_USER,\n" );
    	sql.append("       AU.TRAILER_COST,\n" );
    	sql.append("       AU.GPS_LICENSE_PLATE,\n" );
    	sql.append("       AU.AMOUNT,\n");
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
        sql.append(" WHERE  AU.VEHICLE_ID = VH.VEHICLE_ID \n");
        sql.append(" AND  AU.AUTHOR_ID ="+authorId+" \n");
        bs=factory.select(sql.toString(), page);
        bs.setFieldDateFormat("REPORT_DATE", "yyyy-MM-dd HH:mm:ss");
        bs.setFieldDateFormat("GO_DATE", "yyyy-MM-dd HH:mm:ss");
        bs.setFieldDateFormat("ARRIVE_DATE", "yyyy-MM-dd HH:mm:ss");
        bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("FACTORY_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("MAINTENANCE_DATE", "yyyy-MM-dd");
        bs.setFieldDic("AUTHOR_STATUS","YSQZT");
        bs.setFieldDic("AUTHOR_TYPE","YSQLX");
        bs.setFieldDic("AUTHOR_TYPE","YSQLX");
        bs.setFieldDic("VEHICLE_USE","CLYT");
        bs.setFieldDic("USER_TYPE","CLYHLX");
        bs.setFieldDic("IF_APPLYCLAIM","SF");
        bs.setFieldDic("DRIVE_FORM","QDXS");
    	return bs;
	}
	
	
	
	
	/**
	 * 获取预授权规则明细
	 * @param authorStatus
	 * @return
	 * @throws Exception
	 */
	public QuerySet getPreAuthRule(String authorType)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT R.RULE_ID,\n" );
		sql.append("       R.LEVEL_CODE, --审核编码\n" );
		sql.append("       D.DTL_ID, --明细ID\n" );
		sql.append("       D.PREAUTHOR_OBJECT, --授权项\n" );
		sql.append("       D.PREAUTHOR_RELATION, --授权关系\n" );
		sql.append("       D.COMPAR_CHARACTER, --比较符\n" );
		sql.append("       D.VALUE, --值\n" );
		sql.append("       D.SEQUENCE --检查顺序\n" );
		sql.append("  FROM SE_BA_PREAUTHOR_RULE R, SE_BA_PREAUTHOR_RULE_DTL D\n" );
		sql.append(" WHERE R.RULE_ID = D.RULE_ID\n" );
		sql.append("   AND D.VALUE = '"+authorType+"'");
		return factory.select(null, sql.toString());
	}
	
	/**
	 * 获取该规则下的明细
	 * @param ruleId
	 * @return
	 * @throws Exception
	 */
	public QuerySet getPreAuthRuleDetail(String ruleId,String authorType)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT  D.COMPAR_CHARACTER ,D.VALUE \n" );
		sql.append("  FROM SE_BA_PREAUTHOR_RULE_DTL D\n" );
		sql.append(" WHERE D.RULE_ID = "+ruleId+"\n" );
		sql.append("   AND D.VALUE <> '"+authorType+"'");
		return factory.select(null, sql.toString());
	}
	/**
	 * 判断是否符合
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public QuerySet getIfRule(String ruleId,String authorType)throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT COUNT(1) SL \n" );
		sql.append("  FROM SE_BA_PREAUTHOR_RULE_DTL D\n" );
		sql.append(" WHERE D.RULE_ID = "+ruleId+"\n" );
		sql.append("   AND D.VALUE <> '"+authorType+"'");
		return factory.select(null, sql.toString());
	}
}
