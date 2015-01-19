package com.org.dms.dao.mobile.serviceprocessmng;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;

public class ServiceProcessMngDao {
	
    /**
     * 查询工单信息
     * @param repUserId 手机号码
     * @return
     * @throws Exception
     */
    public QuerySet searchServiceProcess(DBFactory factory,String repUserId) throws Exception
    {
    	QuerySet qs = null;
    	/*  StringBuffer sql= new StringBuffer();
    	  sql.append("SELECT R.WORK_ID,\n" );
    	  sql.append("       R.WORK_NO,\n" );
    	  sql.append("       R.REPAIR_USER,\n" );
    	  sql.append("       R.REPAIR_DATE,\n" );
    	  sql.append("       TO_CHAR(R.GO_DATE, 'YYYY-MM-DD HH24:MI:SS') AS GO_DATE,\n" );
    	  sql.append("       TO_CHAR(R.ARRIVE_DATE, 'YYYY-MM-DD HH24:MI:SS') AS ARRIVE_DATE,\n" );
    	  sql.append("       TO_CHAR(R.COMPLETE_DATE, 'YYYY-MM-DD HH24:MI:SS') AS COMPLETE_DATE,\n" );
    	  sql.append("       R.APPLY_USER,\n" );
    	  sql.append("       R.APPLY_MOBIL,\n" );
    	  sql.append("       R.REP_USER_TEL,\n" );
    	  sql.append("       R.REP_USER_TEL,\n" );
    	  sql.append("       R.START_LONGITUDE,\n" );
    	  sql.append("       R.START_LATITUDE,\n" );
    	  sql.append("       (SELECT COUNT(FJID) FROM FS_FILESTORE F WHERE F.YWZJ=R.WORK_ID) AS FJSL\n" );
    	  sql.append("  FROM SE_BU_WORK_ORDER R\n" );
    	  sql.append(" WHERE R.REP_USER_TEL = '"+repUserId+"'\n" );
    	  //手机端登录，只查询 已派工的 10月18日
    	  sql.append("   AND R.WORK_STATUS = "+DicConstant.PGDZT_02+" ");*/
    	StringBuffer sql1= new StringBuffer();
    	sql1.append("SELECT R.WORK_ID,\n" );
    	sql1.append("        R.WORK_NO,\n" );
    	sql1.append("        R.REPAIR_USER,\n" );
    	sql1.append("        R.REPAIR_DATE,\n" );
    	sql1.append("        TO_CHAR(R.GO_DATE, 'YYYY-MM-DD HH24:MI:SS') AS GO_DATE,\n" );
    	sql1.append("        TO_CHAR(R.ARRIVE_DATE, 'YYYY-MM-DD HH24:MI:SS') AS ARRIVE_DATE,\n" );
    	sql1.append("        TO_CHAR(R.COMPLETE_DATE, 'YYYY-MM-DD HH24:MI:SS') AS COMPLETE_DATE,\n" );
    	sql1.append("        R.APPLY_USER,\n" );
    	sql1.append("        R.APPLY_MOBIL,\n" );
    	sql1.append("        R.REP_USER_TEL,\n" );
    	sql1.append("        R.START_LONGITUDE,\n" );
    	sql1.append("        R.START_LATITUDE,\n" );
    	sql1.append("        (SELECT COUNT(FJID) FROM FS_FILESTORE F WHERE F.YWZJ = R.WORK_ID) AS FJSL\n" );
    	sql1.append("   FROM SE_BU_WORK_ORDER R\n" );
    	sql1.append("  WHERE R.WORK_STATUS = 302202\n" );
    	sql1.append("    AND EXISTS (SELECT 1\n" );
    	sql1.append("           FROM SE_BU_WORK_DISPATCH        D,\n" );
    	sql1.append("                SE_BU_WORK_DISPATCH_UER_RL L,\n" );
    	sql1.append("                SE_BU_WORK_DISPATCH_UER    U\n" );
    	sql1.append("          WHERE R.WORK_ID = D.WORK_ID\n" );
    	sql1.append("            AND D.DISPATCH_ID = L.DISPATCH_ID\n" );
    	sql1.append("            AND L.USER_ID = U.USER_ID\n" );
    	sql1.append("            AND L.IF_MAIN = "+DicConstant.SF_01+"\n" );//只有主修人才可以登录
    	sql1.append("            AND D.STATUS = "+DicConstant.YXBS_01+"\n" );
    	sql1.append("            AND U.MOBIL = '"+repUserId+"')");
    	qs = factory.select(null, sql1.toString());
    	return qs;
    }
    
    /**
     * 更新维修人手机号（已登录人的为主）
     * @param workId
     * @param repUserId
     * @return
     */
    public void updateWorkOrder(DBFactory factory,String workId,String repUserId)throws Exception{
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE SE_BU_WORK_ORDER O SET O.REP_USER_TEL = '"+repUserId+"' WHERE O.WORK_ID = "+workId+"");
    	factory.update(sql.toString(), null);
    }
   //手机端登录（派工人手机号MOBIL）
    /*
     StringBuffer sql1= new StringBuffer();
	sql1.append("SELECT R.WORK_ID,\n" );
	sql1.append("        R.WORK_NO,\n" );
	sql1.append("        R.REPAIR_USER,\n" );
	sql1.append("        R.REPAIR_DATE,\n" );
	sql1.append("        TO_CHAR(R.GO_DATE, 'YYYY-MM-DD HH24:MI:SS') AS GO_DATE,\n" );
	sql1.append("        TO_CHAR(R.ARRIVE_DATE, 'YYYY-MM-DD HH24:MI:SS') AS ARRIVE_DATE,\n" );
	sql1.append("        TO_CHAR(R.COMPLETE_DATE, 'YYYY-MM-DD HH24:MI:SS') AS COMPLETE_DATE,\n" );
	sql1.append("        R.APPLY_USER,\n" );
	sql1.append("        R.APPLY_MOBIL,\n" );
	sql1.append("        R.REP_USER_TEL,\n" );
	sql1.append("        R.START_LONGITUDE,\n" );
	sql1.append("        R.START_LATITUDE,\n" );
	sql1.append("        (SELECT COUNT(FJID) FROM FS_FILESTORE F WHERE F.YWZJ = R.WORK_ID) AS FJSL\n" );
	sql1.append("   FROM SE_BU_WORK_ORDER R\n" );
	sql1.append("  WHERE R.WORK_STATUS = 302202\n" );
	sql1.append("    AND EXISTS (SELECT 1\n" );
	sql1.append("           FROM SE_BU_WORK_DISPATCH        D,\n" );
	sql1.append("                SE_BU_WORK_DISPATCH_UER_RL L,\n" );
	sql1.append("                SE_BU_WORK_DISPATCH_UER    U\n" );
	sql1.append("          WHERE R.WORK_ID = D.WORK_ID\n" );
	sql1.append("            AND D.DISPATCH_ID = L.DISPATCH_ID\n" );
	sql1.append("            AND L.USER_ID = U.USER_ID\n" );
	sql1.append("            AND D.STATUS = 100201\n" );
	sql1.append("            AND U.MOBIL = '2222')");
	 
     */
    /**
     * 更新工单信息
     * @param workId 工单ID
     * @param type 操作类型 1出发，2到达，3完成
     * @return
     * @throws Exception
     */
    public String updateServiceProcess(DBFactory factory,String workId,String longitude,String latitude,String ltime,String type) throws Exception
    {
    	String date = getDateToString();
    	StringBuffer sql= new StringBuffer();
    	sql.append("UPDATE SE_BU_WORK_ORDER O\n" );
    	sql.append("   SET O.UPDATE_TIME   = SYSDATE,\n" );
    	if("1".equals(type))
    	{
    		//sql.append("       O.GO_DATE       = TO_DATE('"+ltime+"','YYYY-MM-DD HH24:MI:SS'),\n" );
    		sql.append("       O.GO_DATE       = SYSDATE,\n" );
    		sql.append("       O.START_LONGITUDE       = '"+longitude+"',\n" );
    		sql.append("       O.START_LATITUDE       = '"+latitude+"',\n" );
    	}else if("2".equals(type))
    	{
    		if(ltime!=null && ltime.length()>0){
    			if(ltime.indexOf("1970") == -1){
    				sql.append("       O.ARRIVE_DATE   = TO_DATE('"+ltime+"','YYYY-MM-DD HH24:MI:SS'),\n" );
    			}else{
    				sql.append("       O.ARRIVE_DATE   = SYSDATE,\n" );
    			}
    		}else{
    			sql.append("       O.ARRIVE_DATE   = SYSDATE,\n" );
    		}
    		sql.append("       O.END_LONGITUDE       = '"+longitude+"',\n" );
    		sql.append("       O.END_LATITUDE       = '"+latitude+"',\n" );
    	}else if("3".equals(type))
    	{
    		//工单完成，将工单状态变为 工单完成
	    	//sql.append("       O.COMPLETE_DATE = TO_DATE('"+date+"','YYYY-MM-DD HH24:MI:SS'), O.WORK_STATUS="+DicConstant.PGDZT_03+" , \n" );
	    	sql.append("       O.COMPLETE_DATE = SYSDATE, O.WORK_STATUS="+DicConstant.PGDZT_03+" , \n" );
	    	//if(longitude!=null&&longitude.length()>0)
	    	StringBuffer sql1= new StringBuffer();
    		sql1.append("SELECT P.ARRIVE_DATE \n" );
    		sql1.append("  FROM SE_BU_WORK_ORDER P\n" );
    		sql1.append(" WHERE P.WORK_ID = "+workId+"\n" );
    		QuerySet qs=factory.select(null, sql1.toString());
    		String arriveDate="";
    		if(qs.getRowCount() > 0){
    			arriveDate=qs.getString(1,"ARRIVE_DATE");
    		}
    		//判断到达时间是否为空
    		if("".equals(arriveDate) || arriveDate == null){
				//if(ltime!=null&&ltime.length()>0)//调整为判断是否传递到达时间，如果有到达时间就更新到达经、纬度及时间
		    	//if(longitude!=null&& longitude.length()>0 )//如果经纬度不为空，就更新到达经、纬度及时间
		    	//{
	    		if(ltime!=null&&ltime.length()>0)
	    		{
	    			if(ltime.indexOf("1970") == -1){ //判断手机端日期是1970的，更新成sysdate ,等于-1是不存在，否则存在
	    				sql.append("       O.ARRIVE_DATE   = TO_DATE('"+ltime+"','YYYY-MM-DD HH24:MI:SS'),\n" );
	    			}else{
	    				sql.append("       O.ARRIVE_DATE   = SYSDATE,\n" );
	    			}
	    		}else{
	    			sql.append("       O.ARRIVE_DATE   = SYSDATE,\n" );
	    		}
		    	//}
    		}
    		if(longitude!=null&& longitude.length()>0 ){//如果经纬度不为空，就更新到达经、纬度及时间
    			sql.append("       O.END_LONGITUDE       = '"+longitude+"',\n" );
        		sql.append("       O.END_LATITUDE       = '"+latitude+"',\n" );
    		}
    	}
    	sql.append("       O.UPDATE_USER   = O.REPAIR_USER\n" );
    	sql.append(" WHERE O.WORK_ID = "+workId);
    	factory.update(sql.toString(), null);
    	if("3".equals(type))//如果是完成返回本地获取的完成时间
    	{
    		return date;
    	}
    	//如果是出发，到达返回手机传递过来的时间
    	return ltime;
    }
    public static String getDateToString(){
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d1 = formatter.format(date);
		if(null!=d1&&!"".equals(d1))
		return d1;
		else return null;
	}
    /**
     * 查询工单信息
     * @param repUserId 手机号码
     * @return
     * @throws Exception
     */
    public QuerySet searchServiceProcessByID(DBFactory factory,String workId) throws Exception
    {
    	  QuerySet qs = null;
     	  StringBuffer sql= new StringBuffer();
     	  sql.append("SELECT R.WORK_ID,\n" );
     	  sql.append("       R.WORK_NO,\n" );
     	  sql.append("       R.REPAIR_USER,\n" );
     	  sql.append("       R.REPAIR_DATE,\n" );
     	  sql.append("       TO_CHAR(R.GO_DATE, 'YYYY-MM-DD HH24:MI:SS') AS GO_DATE,\n" );
     	  sql.append("       TO_CHAR(R.ARRIVE_DATE, 'YYYY-MM-DD HH24:MI:SS') AS ARRIVE_DATE,\n" );
     	  sql.append("       TO_CHAR(R.COMPLETE_DATE, 'YYYY-MM-DD HH24:MI:SS') AS COMPLETE_DATE,\n" );
     	  sql.append("       R.APPLY_USER,\n" );
     	  sql.append("       R.APPLY_MOBIL,\n" );
     	  sql.append("       R.REP_USER_TEL,\n" );
     	  sql.append("       R.START_LONGITUDE,\n" );
     	  sql.append("       R.START_LATITUDE,\n" );
     	  sql.append("       (SELECT COUNT(FJID) FROM FS_FILESTORE F WHERE F.YWZJ=R.WORK_ID) AS FJSL\n" );
     	  sql.append("  FROM SE_BU_WORK_ORDER R \n" );
     	  sql.append(" WHERE R.WORK_ID = "+workId+"\n" );
     	  //手机端登录，只查询 已派工的 10月18日
     	  sql.append("   AND R.WORK_STATUS IN ("+DicConstant.PGDZT_02+","+DicConstant.PGDZT_03+") ");
     	  qs = factory.select(null, sql.toString());
     	  return qs;
    }
    
    /**
     * 获取车牌号
     * @param workId
     * @return
     * @throws Exception
     */
    public QuerySet searchLicensePlateByID(DBFactory factory,String workId)throws Exception {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT D.LICENSE_PLATE\n" );
    	sql.append("  FROM SE_BU_WORK_DISPATCH D\n" );
    	sql.append(" WHERE D.WORK_ID = "+workId+"\n" );
    	sql.append("   AND D.STATUS = 100201");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
    /**
     * 
     * @param map  工单号、派工手机号
     * @param licensePlate 车牌号
     * @param type   1出发，2 完成
     * @param longitude 经度
     * @param latitude  纬度
     * @param ltime     当前时间
     * @throws Exception
     */
    public void updateWorkGps(DBFactory factory,Map<String,String> map,String licensePlate,String type,String longitude,String latitude,String ltime)throws Exception{
    	String workNo=map.get("WORK_NO");
    	String repUserTel=map.get("REP_USER_TEL");
    	StringBuffer sql= new StringBuffer();
    	sql.append("INSERT INTO SE_BU_WORKGPS_IMP\n" );
    	sql.append("  (ID,\n" );
    	sql.append("   WORK_NO,\n" );
    	sql.append("   PHONE_LONGITUDE,\n" );
    	sql.append("   PHONE_LATITUDE,\n" );
    	sql.append("   LICENSE_PLATE,\n" );
    	sql.append("   OPERATE_STATUS,\n" );
    	sql.append("   CURREN_DATE,\n");
    	sql.append("   USER_MOBIL,\n" );
    	sql.append("   STATUS)\n" );
    	sql.append("VALUES\n" );
    	if("1".equals(type))
    	{
        	sql.append("  (F_GETID(),'"+workNo+"','"+longitude+"','"+latitude+"','"+licensePlate+"','"+type+"','"+ltime+"','"+repUserTel+"',0)");
        	factory.update(sql.toString(), null);
    	}else if("2".equals(type))
    	{
        	sql.append("  (F_GETID(),'"+workNo+"','"+longitude+"','"+latitude+"','"+licensePlate+"','"+type+"','"+ltime+"','"+repUserTel+"',0)");
        	factory.update(sql.toString(), null);
    	}
    	else if("3".equals(type))
    	{
    		StringBuffer sql1= new StringBuffer();
    		sql1.append("SELECT COUNT(1) SL \n" );
    		sql1.append("  FROM SE_BU_WORKGPS_IMP P\n" );
    		sql1.append(" WHERE P.WORK_NO = '"+workNo+"'\n" );
    		sql1.append("   AND P.OPERATE_STATUS = 2");
    		QuerySet qs=factory.select(null, sql1.toString());
    		String count="0";
    		if(qs.getRowCount() > 0){
    			count=qs.getString(1,"SL");
    		}
    		//if(ltime!=null&&ltime.length()>0)//调整为判断是否传递到达时间，如果有到达时间就更新到达经、纬度及时间
	    	//if(longitude!=null&& longitude.length()>0 )//如果经纬度不为空，就更新到达经、纬度及时间
	    	if("0".equals(count))
    		{
	    		sql.append("  (F_GETID(),'"+workNo+"','"+longitude+"','"+latitude+"','"+licensePlate+"','"+2+"','"+ltime+"','"+repUserTel+"',0)");
	    		factory.update(sql.toString(), null);
    		}
    	}
    	
    }
}
