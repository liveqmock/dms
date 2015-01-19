package com.org.dms.dao.service.guaranteesHurryPart;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuDispatchDtlVO;
import com.org.dms.vo.service.SeBuDispatchVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.common.dataset.DataObjImpl;
import com.org.mvc.context.ActionContext;

/**
 * 三包急件提报DAO
 * @author Administrator
 *
 */
public class GuaranteesHurryPartDao extends BaseDAO
{
    //定义instance
    public  static final GuaranteesHurryPartDao getInstance(ActionContext atx)
    {
    	GuaranteesHurryPartDao dao = new GuaranteesHurryPartDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * @param page 分页
     * @param user 用户
     * @param conditions 前台条件
     * @throws SQLException 
     */
    public BaseResultSet dispatchSearch(PageManager page, User user, String conditions,String orgId) throws SQLException{
    
    	String wheres = conditions;
    	wheres   +="AND T.STATUS ="+DicConstant.YXBS_01+"\n"
    			 + "AND T.VEHICLE_ID = MV.VEHICLE_ID\n"
    			 + "AND T.DISPATCH_STATUS IN("+DicConstant.SBJJSQZT_01+","+DicConstant.SBJJSQZT_03+") "
    			 + "AND T.ORG_ID="+orgId+""
    			 + "ORDER BY T.DISPATCH_NO";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DISPATCH_ID,\n" );
    	sql.append("       T.DISPATCH_NO,\n" );
    	sql.append("       T.VEHICLE_ID,\n" );
    	sql.append("       T.VIN,\n" );
    	sql.append("       T.BUY_DATE,\n" );
    	sql.append("       T.MILEAGE,\n" );
    	sql.append("       T.USER_NAME,\n" );
    	sql.append("       T.LINK_MAN,\n" );
    	sql.append("       T.PHONE,\n" );
    	sql.append("       T.USER_ADDRESS,\n" );
    	sql.append("       T.RECEIPT_USER,\n" );
    	sql.append("       T.RECEIPT_PHONE,\n" );
    	sql.append("       T.RECEIPT_ADDRESS,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.DISPATCH_STATUS,\n" );
    	sql.append("       T.COMPANY_ID,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME,\n" );
    	sql.append("       T.STATUS,\n" );
    	sql.append("       T.OEM_COMPANY_ID,\n" );
    	sql.append("       T.SECRET_LEVEL,\n" );
    	sql.append("       T.FAULT_DATE,\n" );
    	sql.append("       T.CHECK_REARKS,\n" );
    	sql.append("       T.FAULT_ANALYSE FAULT_ANALYSE_CODE,\n" );
    	sql.append("       (SELECT S.NAME FROM SE_BA_CODE S WHERE S.CODE = T.FAULT_ANALYSE AND S.CODE_TYPE = 302702 ) FAULT_ANALYSE,\n" );
    	sql.append("       MV.ENGINE_NO,\n" );
    	sql.append("       MV.MODELS_CODE,\n" );
    	sql.append("       MV.BLACKLISTFLAG\n" );
    	sql.append("  FROM SE_BU_DISPATCH T, MAIN_VEHICLE MV");
        bs=factory.select(sql.toString(), page);
        bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("APPLY_DATE","yyyy-MM-dd HH:mm:ss");
        bs.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd");
        bs.setFieldDic("DISPATCH_STATUS", "SBJJSQZT");
        bs.setFieldDic("USERNAME", "CLYHLX");
        bs.setFieldDic("VEHICLENAME", "CLYT");
    	return bs;
    }
    public BaseResultSet dispatchDelSearch(PageManager page, User user, String conditions,String orgId) throws SQLException{
    	
    	String wheres = conditions;
    	wheres   +="AND T.STATUS ="+DicConstant.YXBS_01+"\n"
    			+ "AND T.VEHICLE_ID = MV.VEHICLE_ID\n"
    			+ "AND T.ORG_ID="+orgId+"";
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DISPATCH_ID,\n" );
    	sql.append("       T.DISPATCH_NO,\n" );
    	sql.append("       T.VEHICLE_ID,\n" );
    	sql.append("       T.VIN,\n" );
    	sql.append("       T.BUY_DATE,\n" );
    	sql.append("       T.MILEAGE,\n" );
    	sql.append("       T.USER_NAME,\n" );
    	sql.append("       T.LINK_MAN,\n" );
    	sql.append("       T.PHONE,\n" );
    	sql.append("       T.USER_ADDRESS,\n" );
    	sql.append("       T.RECEIPT_USER,\n" );
    	sql.append("       T.RECEIPT_PHONE,\n" );
    	sql.append("       T.RECEIPT_ADDRESS,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.DISPATCH_STATUS,\n" );
    	sql.append("       T.FAULT_ANALYSE,\n" );
    	sql.append("       T.COMPANY_ID,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME,\n" );
    	sql.append("       T.CHECK_REARKS,\n" );
    	sql.append("       T.STATUS,\n" );
    	sql.append("       T.OEM_COMPANY_ID,\n" );
    	sql.append("       T.SECRET_LEVEL,\n" );
    	sql.append("       T.FAULT_DATE,\n" );
    	sql.append("       MV.ENGINE_NO,\n" );
    	sql.append("       MV.MODELS_CODE\n" );
    	sql.append("  FROM SE_BU_DISPATCH T, MAIN_VEHICLE MV");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
    	bs.setFieldDateFormat("APPLY_DATE","yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd");
    	bs.setFieldDic("DISPATCH_STATUS", "SBJJSQZT");
    	bs.setFieldDic("USERNAME", "CLYHLX");
    	bs.setFieldDic("VEHICLENAME", "CLYT");
    	return bs;
    }
    public BaseResultSet dispatchOemSearch(PageManager page, User user, String conditions) throws SQLException{
    	
    	String wheres = conditions;
    	wheres   +="AND T.STATUS ="+DicConstant.YXBS_01+"\n"
    			+ "AND T.DISPATCH_STATUS="+DicConstant.SBJJSQZT_02+""
    			+ "AND T.VEHICLE_ID = MV.VEHICLE_ID\n";
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.DISPATCH_ID,\n" );
    	sql.append("       T.DISPATCH_NO,\n" );
    	sql.append("       T.VEHICLE_ID,\n" );
    	sql.append("       T.VIN,\n" );
    	sql.append("       T.BUY_DATE,\n" );
    	sql.append("       T.MILEAGE,\n" );
    	sql.append("       T.USER_NAME,\n" );
    	sql.append("       T.LINK_MAN,\n" );
    	sql.append("       T.PHONE,\n" );
    	sql.append("       T.USER_ADDRESS,\n" );
    	sql.append("       T.RECEIPT_USER,\n" );
    	sql.append("       T.RECEIPT_PHONE,\n" );
    	sql.append("       T.RECEIPT_ADDRESS,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.DISPATCH_STATUS,\n" );
    	sql.append("       T.COMPANY_ID,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.FAULT_ANALYSE,\n" );
    	sql.append("       T.UPDATE_TIME,\n" );
    	sql.append("       T.STATUS,\n" );
    	sql.append("       T.CHECK_REARKS,\n" );
    	sql.append("       T.OEM_COMPANY_ID,\n" );
    	sql.append("       T.SECRET_LEVEL,\n" );
    	sql.append("       T.FAULT_DATE,\n" );
    	sql.append("       MV.ENGINE_NO,\n" );
    	sql.append("       MV.MODELS_CODE\n" );
    	sql.append("  FROM SE_BU_DISPATCH T, MAIN_VEHICLE MV");
    	bs=factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
    	bs.setFieldDateFormat("APPLY_DATE","yyyy-MM-dd HH:mm:ss");
    	bs.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd");
    	bs.setFieldDic("DISPATCH_STATUS", "SBJJSQZT");
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
        bs.setFieldDateFormat("G_DATE", "yyyy-MM-dd");
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
    	sql.append("       T.VEHICLE_USE,\n" );
    	sql.append("       T.VEHICLE_USE VEHICLENAME,\n" );
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
    /**
  	 * @title: searchServiceParts
  	 * 查询服务活动配件
  	 * @date 2014年7月2日09:14:52
  	 */
    public BaseResultSet searchHurryParts(PageManager page, User user, String conditions,String dispatchId) throws Exception
      {
      	String wheres = conditions;
      	wheres += " AND T.DISPATCH_ID = SD.DISPATCH_ID\n "
      			+ " AND PT.PART_ID = T.PART_ID\n"
      			+ " AND SD.DISPATCH_ID = "+dispatchId+"\n"
      		  +"ORDER BY T.DTL_ID ";
          page.setFilter(wheres);
      	//定义返回结果集
      	BaseResultSet bs = null;
      	StringBuffer sql= new StringBuffer();
      	sql.append("SELECT T.DTL_ID,\n" );
      	sql.append("       T.DISPATCH_ID,\n" );
      	sql.append("       T.PART_ID,\n" );
      	sql.append("       T.PART_CODE,\n" );
      	sql.append("       T.PART_NAME,\n" );
      	sql.append("       T.COUNT,\n" );
      	sql.append("       T.CLAIM_PRICE,\n" );
      	sql.append("       T.CREATE_USER,\n" );
      	sql.append("       T.CREATE_TIME,\n" );
      	sql.append("       T.UPDATE_USER,\n" );
      	sql.append("       T.UPDATE_TIME,\n" );
      	sql.append("       T.STATUS, \n");
      	sql.append("       T.REMARKS, \n");
      	sql.append("       (T.COUNT*PT.SE_CLPRICE) AMOUNT \n");
    	sql.append("       FROM SE_BU_DISPATCH_DTL T,SE_BU_DISPATCH SD,PT_BA_INFO PT\n" );
      	//执行方法，不需要传递conn参数
      	bs = factory.select(sql.toString(), page);
      	return bs;
      }
    /**三包急件新增
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean hurryPartInsert(SeBuDispatchVO vo)throws Exception
    {
    	return factory.insert(vo);
    }
    
    /**三包急件修改
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean hurryPartUpdate(SeBuDispatchVO vo)throws Exception
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
     * 三包急件号
     * @param vo
     * @return
     * @throws Exception
     */
    public String getdispatchNo()throws Exception
    {
    	QuerySet qs = null;
    	String date = getDateToString();
    	String num = "SBJJ";
    	if(date!=null){
			date = date.replaceAll("-", "");
			num+=date;
		}
    	StringBuffer sql = new StringBuffer();
		sql.append("SELECT max(").append("DISPATCH_NO").append(") as DH FROM ");
		sql.append( "SE_BU_DISPATCH").append(" t");
		sql.append(" where t.").append("DISPATCH_NO").append(" like '%").append(num).append("%'");
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
    /**
     * 删除三包急件
     * @return
     * @throws Exception
     */
    public boolean dispatchPartDelete(String dispatchId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("DELETE FROM SE_BU_DISPATCH T\n" );
    	sql.append("WHERE T.DISPATCH_ID ="+dispatchId+"");
    	return factory.delete(sql.toString(), null);
    }
    public BaseResultSet searchParts(PageManager page, User user, String conditions,String dispatchId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND NOT EXISTS (SELECT B.PART_ID   FROM SE_BU_DISPATCH_DTL B WHERE T.PART_ID = B.PART_ID AND B.DISPATCH_ID = "+dispatchId+") "
    		   +"ORDER BY T.PART_ID ";
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T.PART_NO,\n" );
    	sql.append("       T.UNIT,\n" );
    	sql.append("       T.PART_TYPE,\n" );
    	sql.append("       T.ATTRIBUTE,\n" );
    	sql.append("       T.SE_CLPRICE,\n" );
    	sql.append("       T.MIN_PACK,\n" );
    	sql.append("       T.MIN_UNIT,\n" );
    	sql.append("       T.IF_DIRECT,\n" );
    	sql.append("       T.IF_OUT,\n" );
    	sql.append("       T.IF_BOOK,\n" );
    	sql.append("       T.IF_RETURN,\n" );
    	sql.append("       T.IF_ASSEMBLY,\n" );
    	sql.append("       T.F_POSITION_ID BELONG_ASSEMBLY,\n" );
    	sql.append("       T.IF_SCAN,\n" );
    	sql.append("       T.IF_SUPLY,\n" );
    	sql.append("       T.PART_STATUS,\n" );
    	sql.append("       T.IF_STREAM,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.PCH_PRICE,\n" );
    	sql.append("       T.SALE_PRICE,\n" );
    	sql.append("       T.ARMY_PRICE,\n" );
    	sql.append("       T.PLAN_PRICE,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME,\n" );
    	sql.append("       T.STATUS\n" );
    	sql.append("  FROM PT_BA_INFO T");
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    public boolean insertParts(SeBuDispatchDtlVO vo)
            throws Exception {
        return factory.insert(vo);
    }
    public boolean deletePartByMxids(String mxids) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" DELETE SE_BU_DISPATCH_DTL A WHERE A.DTL_ID IN ("+mxids+")   \n");
    	return factory.delete(sql.toString(), null);
    }
    public QuerySet queryPartsCount(String dispatchId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_ID FROM SE_BU_DISPATCH_DTL T WHERE T.DISPATCH_ID ="+dispatchId+"");
    	return factory.select(null, sql.toString());
    }
    public QuerySet queryAmount(String dispatchId,String orgId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT NVL(S.AVAILABLE_AMOUNT, 0),S.PART_ID,S.PART_CODE,S.PART_NAME\n" );
    	sql.append("  FROM PT_BU_DEALER_STOCK S, SE_BU_DISPATCH_DTL D\n" );
    	sql.append(" WHERE D.PART_ID = S.PART_ID\n" );
    	sql.append("   AND S.ORG_ID = "+orgId+"\n" );
    	sql.append("   AND D.DISPATCH_ID ="+dispatchId+"");
    	return factory.select(null, sql.toString());
    }
    public QuerySet queryPSAmount(String dispatchId,String orgId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT S.AVAILABLE_AMOUNT PSKC,S.PART_ID,S.PART_CODE,S.PART_NAME\n" );
    	sql.append("   FROM PT_BU_DEALER_STOCK S,\n" );
    	sql.append("        SE_BU_DISPATCH_DTL D,\n" );
    	sql.append("        TM_ORG             TG,\n" );
    	sql.append("        PT_BA_SERVICE_DC   DC\n" );
    	sql.append("  WHERE DC.ORG_ID = TG.ORG_ID\n" );
    	sql.append("    AND DC.DC_ID = S.ORG_ID\n" );
    	sql.append("    AND S.PART_ID = D.PART_ID\n" );
    	sql.append("    AND D.DISPATCH_ID = "+dispatchId+"\n" );
    	sql.append("    AND TG.ORG_ID = "+orgId+"");
    	return factory.select(null, sql.toString());
    }
}
