package com.org.dms.dao.service.milesPolicy;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.MainVehicleVO;
import com.org.dms.vo.service.SeBuMillionGuaranteeVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.common.dataset.DataObjImpl;
import com.org.mvc.context.ActionContext;

/**
 * 万里定保单提报DAO
 * @author Administrator
 *
 */
public class MilesPolicyReportDao extends BaseDAO
{
    //定义instance
    public  static final MilesPolicyReportDao getInstance(ActionContext atx)
    {
    	MilesPolicyReportDao dao = new MilesPolicyReportDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * @param page 分页
     * @param user 用户
     * @param conditions 前台条件
     * @throws SQLException 
     */
    public BaseResultSet milesPolicyReportSearch(PageManager page, User user, String conditions,String orgId) throws SQLException{
    
    	String wheres = conditions;
    	wheres   +="AND SG.STATUS ="+DicConstant.YXBS_01+"\n"
    			 + "AND SG.VEHICLE_ID = MV.VEHICLE_ID\n"
    			 + "AND SG.G_STATUS = "+DicConstant.DBDZT_01+""
    			 + "AND SG.ORG_ID="+orgId+"";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT SG.G_ID,\n" );
    	sql.append("       SG.G_NO,\n" );
    	sql.append("       SG.VEHICLE_ID,\n" );
    	sql.append("       SG.VIN,\n" );
    	sql.append("       SG.G_STATUS,\n" );
    	sql.append("       SG.USER_TYPE USERNAME,\n" );
    	sql.append("       SG.APPLY_ADDRES,\n" );
    	sql.append("       SG.VEHICLE_USE VEHICLENAME,\n" );
    	sql.append("       SG.BUY_DATE,\n" );
    	sql.append("       SG.MILEAGE,\n" );
    	sql.append("       SG.GUARANTEE_NO,\n" );
    	sql.append("       SG.APPLY_DATE,\n" );
    	sql.append("       SG.LICENSE_PLATE,\n" );
    	sql.append("       SG.USER_NAME,\n" );
    	sql.append("       SG.USER_NO,\n" );
    	sql.append("       SG.LINK_MAN,\n" );
    	sql.append("       SG.PHONE,\n" );
    	sql.append("       SG.USER_ADDRESS,\n" );
    	sql.append("       SG.REMARKS,\n" );
    	sql.append("       SG.OUT_AMOUNT,\n" );
    	sql.append("       SG.COMPANY_ID,\n" );
    	sql.append("       SG.ORG_ID,\n" );
    	sql.append("       SG.CREATE_USER,\n" );
    	sql.append("       SG.CREATE_TIME,\n" );
    	sql.append("       SG.UPDATE_USER,\n" );
    	sql.append("       SG.UPDATE_TIME,\n" );
    	sql.append("       SG.STATUS,\n" );
    	sql.append("       SG.OEM_COMPANY_ID,\n" );
    	sql.append("       SG.SECRET_LEVEL,\n" );
    	sql.append("       SG.ENGINE_NO,\n" );
    	sql.append("       SG.G_DATE,\n" );
    	sql.append("       MV.CERTIFICATE,\n" );
    	sql.append("       MV.ENGINE_TYPE,\n" );
    	sql.append("       MV.MODELS_CODE,\n" );
    	sql.append("       MV.DRIVE_FORM,\n" );
    	sql.append("       MV.DRIVE_FORM DRIVENAME\n" );
    	sql.append("  FROM SE_BU_MILLION_GUARANTEE SG, MAIN_VEHICLE MV");
        bs=factory.select(sql.toString(), page);
        bs.setFieldDateFormat("G_DATE", "yyyy-MM-dd HH:mm:ss");
        bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
        
        bs.setFieldDic("G_STATUS", "DBDZT");
        bs.setFieldDic("USERNAME", "CLYHLX");
        bs.setFieldDic("VEHICLENAME", "CLYT");
        bs.setFieldDic("DRIVENAME", "QDXS");
    	return bs;
    }
    /**
     * @param page 分页
     * @param user 用户
     * @param conditions 前台条件
     * @throws SQLException 
     */
    public BaseResultSet milesPolicyDelSearch(PageManager page, User user, String conditions,String orgId) throws SQLException{
    	String wheres = conditions;
    	wheres   +="AND SG.STATUS ="+DicConstant.YXBS_01+"\n"
    			 + "AND SG.VEHICLE_ID = MV.VEHICLE_ID\n"
    			 + "AND SG.ORG_ID="+orgId+"";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT SG.G_ID,\n" );
    	sql.append("       SG.G_NO,\n" );
    	sql.append("       SG.VEHICLE_ID,\n" );
    	sql.append("       SG.VIN,\n" );
    	sql.append("       SG.G_STATUS,\n" );
    	sql.append("       SG.USER_TYPE USERNAME,\n" );
    	sql.append("       SG.APPLY_ADDRES,\n" );
    	sql.append("       SG.VEHICLE_USE VEHICLENAME,\n" );
    	sql.append("       SG.BUY_DATE,\n" );
    	sql.append("       SG.MILEAGE,\n" );
    	sql.append("       NVL(SG.G_COUNT, 0) G_COUNT,\n" );
    	sql.append("       SG.GUARANTEE_NO,\n" );
    	sql.append("       SG.APPLY_DATE,\n" );
    	sql.append("       SG.LICENSE_PLATE,\n" );
    	sql.append("       SG.USER_NAME,\n" );
    	sql.append("       SG.USER_NO,\n" );
    	sql.append("       SG.LINK_MAN,\n" );
    	sql.append("       SG.PHONE,\n" );
    	sql.append("       SG.USER_ADDRESS,\n" );
    	sql.append("       SG.REMARKS,\n" );
    	sql.append("       SG.OUT_AMOUNT,\n" );
    	sql.append("       SG.COMPANY_ID,\n" );
    	sql.append("       SG.ORG_ID,\n" );
    	sql.append("       SG.CREATE_USER,\n" );
    	sql.append("       SG.CREATE_TIME,\n" );
    	sql.append("       SG.UPDATE_USER,\n" );
    	sql.append("       SG.UPDATE_TIME,\n" );
    	sql.append("       SG.STATUS,\n" );
    	sql.append("       SG.OEM_COMPANY_ID,\n" );
    	sql.append("       SG.SECRET_LEVEL,\n" );
    	sql.append("       SG.ENGINE_NO,\n" );
    	sql.append("       SG.G_DATE,\n" );
    	sql.append("       MV.CERTIFICATE,\n" );
    	sql.append("       MV.ENGINE_TYPE,\n" );
    	sql.append("       MV.MODELS_CODE,\n" );
    	sql.append("       MV.DRIVE_FORM\n" );
    	sql.append("  FROM SE_BU_MILLION_GUARANTEE SG, MAIN_VEHICLE MV");
        bs=factory.select(sql.toString(), page);
        bs.setFieldDateFormat("G_DATE", "yyyy-MM-dd HH:mm:ss");
        bs.setFieldDic("G_STATUS", "DBDZT");
        bs.setFieldDic("USERNAME", "CLYHLX");
        bs.setFieldDic("VEHICLENAME", "CLYT");
    	return bs;
    }
    /**
     * @param page 分页
     * @param user 用户
     * @param conditions 前台条件
     * @throws SQLException 
     */
    public BaseResultSet milesPolicyOemSearch(PageManager page, User user, String conditions,String orgId) throws SQLException{
    	String wheres = conditions;
    	wheres   +="AND SG.STATUS ="+DicConstant.YXBS_01+"\n"
    			 + "AND SG.G_STATUS="+DicConstant.DBDZT_02+"\n"
    			 + "AND SG.VEHICLE_ID = MV.VEHICLE_ID\n"
    			 + "AND SG.ORG_ID = TG.ORG_ID";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT SG.G_ID,\n" );
    	sql.append("       SG.G_NO,\n" );
    	sql.append("       SG.VEHICLE_ID,\n" );
    	sql.append("       SG.VIN,\n" );
    	sql.append("       SG.G_STATUS,\n" );
    	sql.append("       SG.USER_TYPE USERNAME,\n" );
    	sql.append("       SG.APPLY_ADDRES,\n" );
    	sql.append("       SG.VEHICLE_USE VEHICLENAME,\n" );
    	sql.append("       SG.BUY_DATE,\n" );
    	sql.append("       SG.MILEAGE,\n" );
    	sql.append("       NVL(SG.G_COUNT, 0) G_COUNT,\n" );
    	sql.append("       SG.GUARANTEE_NO,\n" );
    	sql.append("       SG.APPLY_DATE,\n" );
    	sql.append("       SG.LICENSE_PLATE,\n" );
    	sql.append("       SG.USER_NAME,\n" );
    	sql.append("       SG.USER_NO,\n" );
    	sql.append("       SG.LINK_MAN,\n" );
    	sql.append("       SG.PHONE,\n" );
    	sql.append("       SG.USER_ADDRESS,\n" );
    	sql.append("       SG.REMARKS,\n" );
    	sql.append("       SG.OUT_AMOUNT,\n" );
    	sql.append("       SG.COMPANY_ID,\n" );
    	sql.append("       SG.ORG_ID,\n" );
    	sql.append("       SG.CREATE_USER,\n" );
    	sql.append("       SG.CREATE_TIME,\n" );
    	sql.append("       SG.UPDATE_USER,\n" );
    	sql.append("       SG.UPDATE_TIME,\n" );
    	sql.append("       SG.STATUS,\n" );
    	sql.append("       SG.OEM_COMPANY_ID,\n" );
    	sql.append("       SG.SECRET_LEVEL,\n" );
    	sql.append("       SG.ENGINE_NO,\n" );
    	sql.append("       SG.G_DATE,\n" );
    	sql.append("       MV.CERTIFICATE,\n" );
    	sql.append("       MV.ENGINE_TYPE,\n" );
    	sql.append("       MV.MODELS_CODE,\n" );
    	sql.append("       MV.DRIVE_FORM,\n" );
    	sql.append("       TG.ONAME,\n" );
    	sql.append("       TG.CODE\n" );
    	sql.append("  FROM SE_BU_MILLION_GUARANTEE SG, MAIN_VEHICLE MV,TM_ORG TG");
        bs=factory.select(sql.toString(), page);
        bs.setFieldDateFormat("G_DATE","yyyy-MM-dd HH:mm:ss");
        bs.setFieldDic("G_STATUS", "DBDZT");
        bs.setFieldDic("USERNAME", "CLYHLX");
        bs.setFieldDic("VEHICLENAME", "CLYT");
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
    	sql.append("       T.USER_TYPE USERNAME,\n" );
    	sql.append("       T.USER_TYPE,\n" );
    	sql.append("       T.VEHICLE_USE VEHICLEUSENAME,\n" );
    	sql.append("       T.VEHICLE_USE, \n" );
    	sql.append("       T.DRIVE_FORM,\n" );
    	sql.append("       T.DRIVE_FORM DRIVENAME,\n" );
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
    	sql.append("       T.USER_ADDRESS,\n" );
    	sql.append("       T.BLACKLISTFLAG\n" );
    	sql.append("  FROM MAIN_VEHICLE T\n" );
    	sql.append(" WHERE (T.VIN LIKE '%"+vin+"'  OR T.VIN = '"+vin+"')\n" );
    	sql.append("   AND T.ENGINE_NO = '"+engineNo+"'");
    	return factory.select(sql.toString(), new PageManager());
    }
    /**定保单新增
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean milesPolicyInsert(SeBuMillionGuaranteeVO vo)throws Exception
    {
    	return factory.insert(vo);
    }
    
    /**定保单修改
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean milesPolicyUpdate(SeBuMillionGuaranteeVO vo)throws Exception
    {
    	return factory.update(vo);
    }
    public boolean vehicleUpdate(MainVehicleVO vo)throws Exception
    {
    	return factory.update(vo);
    }
    public QuerySet searchVehicleMiles(String vehicleId) throws SQLException {
		QuerySet qs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.MILEAGE FROM MAIN_VEHICLE T WHERE T.VEHICLE_ID  = "+vehicleId+"");
		qs = factory.select(null,sql.toString());
		return qs;
	}
    /**
     * 生成万里定保单单号
     * @param vo
     * @return
     * @throws Exception
     */
    public String getGNo()throws Exception
    {
    	QuerySet qs = null;
    	String date = getDateToString();
    	String num = "WLDB";
    	if(date!=null){
			date = date.replaceAll("-", "");
			num+=date;
		}
    	StringBuffer sql = new StringBuffer();
		sql.append("SELECT max(").append("G_NO").append(") as DH FROM ");
		sql.append( "SE_BU_MILLION_GUARANTEE").append(" t");
		sql.append(" where t.").append("G_NO").append(" like '%").append(num).append("%'");
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
		 }
		return num;
    }
    public static String getDateToString(){
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String d1 = formatter.format(date);
		if(null!=d1&&!"".equals(d1))
		return d1;
		else return null;
	}
    //查询车辆表中G_COUNT的值
    public QuerySet checkGCount(String gId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
		sql.append("SELECT NVL(MV.G_COUNT,0) G_COUNT,MV.VEHICLE_ID,MV.MILEAGE FROM SE_BU_MILLION_GUARANTEE SG,MAIN_VEHICLE MV WHERE MV.VEHICLE_ID = SG.VEHICLE_ID AND SG.G_ID ="+gId+"");
    	return factory.select(null, sql.toString());
    }
    //查询万里定保表中的里程数
    public QuerySet mileageSearch(String gId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.MILEAGE FROM SE_BU_MILLION_GUARANTEE T WHERE T.G_ID ="+gId+"");
    	return factory.select(null, sql.toString());
    }
    /**
     * 删除定保单
     * @param authorId
     * @return
     * @throws Exception
     */
    public boolean milesPolicyDelete(String gId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("DELETE FROM SE_BU_MILLION_GUARANTEE T\n" );
    	sql.append("WHERE T.G_ID ="+gId+"");
    	return factory.delete(sql.toString(), null);
    }
}
