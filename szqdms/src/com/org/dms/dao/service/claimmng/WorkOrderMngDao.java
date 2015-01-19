package com.org.dms.dao.service.claimmng;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuDealerStockVO;
import com.org.dms.vo.part.PtBuOutBuyDtlVO;
import com.org.dms.vo.part.PtBuOutBuyVO;
import com.org.dms.vo.part.PtBuRealSaleDtlVO;
import com.org.dms.vo.part.PtBuRealSaleVO;
import com.org.dms.vo.service.SeBuClaimCheckVO;
import com.org.dms.vo.service.SeBuClaimFaultPartVO;
import com.org.dms.vo.service.SeBuClaimOutVO;
import com.org.dms.vo.service.SeBuClaimVO;
import com.org.dms.vo.service.SeBuWorkOrderVO;
import com.org.frameImpl.Constant;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.framework.params.ParaManager;
import com.org.framework.params.UserPara.UserParaConfigureVO;
import com.org.mvc.context.ActionContext;

public class WorkOrderMngDao extends BaseDAO{
	
	//定义instance
    public  static final WorkOrderMngDao getInstance(ActionContext atx)
    {
    	WorkOrderMngDao dao = new WorkOrderMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
    /**
     * @title: searchWorkOrder 
   	 * @description: TODO( 根据工单ID和orgID 校验工单是否已转索赔单)
	 * @param page
	 * @param user
	 * @param conditions
	 * @return BaseResultSet
	 * @throws Exception
	 */
    public BaseResultSet searchWorkOrder(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	String orgId=user.getOrgId();
    	wheres += " AND T.ORG_ID = "+orgId+" \n"
    		   +  " AND T.STATUS = 100201 \n"+
    			  " ORDER BY T.WORK_ID ";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.WORK_ID,\n" );
    	sql.append("       T.WORK_NO,\n" );
    	sql.append("       T.WORK_VIN,\n" );
    	sql.append("       T.REPAIR_USER,\n" );
    	sql.append("       T.WORK_TYPE,\n" );
    	sql.append("       T.IF_OUT,\n" );
    	sql.append("       T.WORK_STATUS,\n" );
    	sql.append("       T.APPLY_USER,\n" );
    	sql.append("       T.APPLY_MOBIL,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.APPLY_ADDRESS APPLY_ADDRES,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.GO_DATE,\n" );
    	sql.append("       T.ARRIVE_DATE,\n" );
    	sql.append("       T.COMPLETE_DATE,\n" );
    	sql.append("       D.LICENSE_PLATE AS VEHICLE_NO,\n" );
    	sql.append("       C.CLAIM_ID,\n" );
    	sql.append("       C.CLAIM_TYPE,\n" );
    	sql.append("       C.SE_TYPE,\n" );
    	sql.append("       C.CLAIM_NO,\n" );
    	sql.append("       C.CLAIM_STATUS,\n" );
    	sql.append("       C.VEHICLE_ID,\n" );
    	sql.append("       C.VIN,\n" );
    	sql.append("       C.PRE_AUTHOR_ID,\n" );
    	sql.append("       C.APPLY_USER_TYPE,\n" );
    //	sql.append("       C.APPLY_ADDRES,\n" );
    	sql.append("       C.FAULT_ID,C.FAULT_CODE,\n" );
    	sql.append("       C.FAULT_FROM FAULT_FROM_CODE,\n" );
 	   	sql.append("       (SELECT S.NAME FROM SE_BA_CODE S WHERE S.CODE = C.FAULT_FROM AND S.CODE_TYPE = 302701 AND S.STATUS="+DicConstant.YXBS_01+" ) FAULT_FROM,\n" );
 	   	sql.append("       C.FAULT_ANLYSIE FAULT_ANLYSIE_CODE,\n" );
 	   	sql.append("       (SELECT S.NAME FROM SE_BA_CODE S WHERE S.CODE = C.FAULT_ANLYSIE AND S.CODE_TYPE = 302702 AND S.STATUS="+DicConstant.YXBS_01+" ) FAULT_ANLYSIE,\n" );
    	sql.append("       C.FAULT_ADDRESS,\n" );
    	sql.append("       C.FAULT_DATE,\n" );
    	sql.append("       C.ACTIVITY_ID,\n" );
    	sql.append("       C.USER_TYPE,C.USER_TYPE AS USER_TYPE_NAME,\n" );
    	sql.append("       C.VEHICLE_USE,C.VEHICLE_USE AS VEHICLE_NAME,\n" );
    	sql.append("       C.BUY_DATE,\n" );
    	sql.append("       C.IF_OVERDUE,\n" );
    	sql.append("       C.OVERDUE_DAYS,\n" );
    	sql.append("       C.MILEAGE,\n" );
    	sql.append("       C.GUARANTEE_NO,\n" );
    	sql.append("       C.APPLY_REPAIR_DATE,\n" );
    	sql.append("       C.DISPATCH_ID,\n" );
    	sql.append("       C.TRAIN_COSTS,\n" );
    	sql.append("       C.SAFE_COSTS,\n" );
    	sql.append("       C.MAINTENANCE_COSTS,\n" );
    	sql.append("       C.MAINTENANCE_DATE,\n" );
    	sql.append("       C.LICENSE_PLATE,\n" );
    	sql.append("       C.USER_NAME,\n" );
    	sql.append("       C.USER_NO,\n" );
    	sql.append("       C.LINK_MAN,\n" );
    	sql.append("       C.PHONE,\n" );
    	sql.append("       C.PROVINCE_CODE,\n" );
    	sql.append("       C.CITY_CODE,\n" );
    	sql.append("       C.COUNTY_CODE,\n" );
    	sql.append("       C.USER_ADDRESS,\n" );
    	sql.append("       C.REMARKS,\n" );
    	sql.append("       C.IF_RECOVERY,\n" );
    	sql.append("       C.IF_FIXED,\n" );
    	sql.append("       C.SE_METHOD,SERVICE_COST,\n" );
    	sql.append("       C.ENGINE_NO,C.REPAIR_ADDRESS,C.REPAIR_DATE,C.G_TIMES,\n" );
    	sql.append("       (SELECT MODELS_CODE FROM MAIN_VEHICLE V WHERE V.VEHICLE_ID=C.VEHICLE_ID ) MODEL_CODE,\n" );
    	sql.append("       (SELECT DRIVE_FORM FROM MAIN_VEHICLE V WHERE V.VEHICLE_ID=C.VEHICLE_ID ) DRIVE_FORM,\n" );
    	sql.append("       (SELECT ENGINE_TYPE FROM MAIN_VEHICLE V WHERE V.VEHICLE_ID=C.VEHICLE_ID ) ENGINE_TYPE,\n" );
    	sql.append("       (SELECT Y.ACTIVITY_CODE FROM SE_BU_ACTIVITY Y WHERE Y.ACTIVITY_ID=C.ACTIVITY_ID ) ACTIVITY_CODE,\n" );
    	sql.append("       (SELECT P.AUTHOR_NO FROM SE_BU_PRE_AUTHOR P WHERE P.AUTHOR_ID=C.PRE_AUTHOR_ID ) AUTHOR_NO,\n" );
    	sql.append("       (SELECT NVL(I.EFFECTIVE_MILEAGE,0)*2  FROM SE_BU_WORKGPS_IMP I WHERE I.WORK_NO =T.WORK_NO AND I.OPERATE_STATUS=2 AND ROWNUM =1 ) YXLC,--有效里程\n" );
    	sql.append("       (SELECT D.DISPATCH_NO FROM SE_BU_DISPATCH D WHERE D.DISPATCH_ID=C.DISPATCH_ID ) DISPATCH_NO,\n" );
 	   	sql.append("  NVL((SELECT M.OVERDUE_DAYS FROM MAIN_DEALER M WHERE M.ORG_ID="+user.getOrgId()+" AND ROWNUM=1 ),0) DEAR_OVER_DAYS\n");
    	sql.append("  FROM SE_BU_WORK_ORDER T\n" );
    	sql.append("  LEFT JOIN SE_BU_CLAIM C ON T.WORK_ID = C.WORK_ID\n" );
    	sql.append("                         AND C.CLAIM_STATUS = 301001\n" );
    	sql.append("  LEFT JOIN SE_BU_WORK_DISPATCH D ON T.WORK_ID = D.WORK_ID AND D.STATUS="+DicConstant.YXBS_01+" \n");
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("IF_OUT", "SF");
		bs.setFieldDic("WORK_STATUS", "PGDZT");
		bs.setFieldDic("WORK_TYPE", "PGLX");
		bs.setFieldDic("CLAIM_STATUS", "SPDZT");
		bs.setFieldDic("SE_TYPE", "FWLX");
		bs.setFieldDic("CLAIM_TYPE", "SPDLX");
		bs.setFieldDic("USER_TYPE", "YHLX");
		bs.setFieldDic("APPLY_USER_TYPE", "BXRLX");
		bs.setFieldDic("USER_TYPE_NAME", "CLYHLX");
		bs.setFieldDic("VEHICLE_NAME", "CLYT");
		bs.setFieldDic("DRIVE_FORM", "QDXS");
		bs.setFieldOrgDeptSimpleName("ORG_ID");
		bs.setFieldDateFormat("REPAIR_DATE", "yyyy-MM-dd HH:mm");
		bs.setFieldDateFormat("GO_DATE", "yyyy-MM-dd HH:mm");
		bs.setFieldDateFormat("ARRIVE_DATE", "yyyy-MM-dd HH:mm");
		bs.setFieldDateFormat("COMPLETE_DATE", "yyyy-MM-dd HH:mm");
		bs.setFieldDateFormat("APPLY_REPAIR_DATE", "yyyy-MM-dd HH:mm");
		bs.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd HH:mm");
		bs.setFieldDateFormat("MAINTENANCE_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
		bs.setFieldSimpleXZQH("PROVINCE_CODE");
		bs.setFieldSimpleXZQH("CITY_CODE");
		bs.setFieldSimpleXZQH("COUNTY_CODE");
    	return bs;
    }
    /**
     * @title: queryWorkOrder 
   	 * @description: TODO( 查询工单信息)
	 * @param page
	 * @param user
	 * @param conditions
	 * @return BaseResultSet
	 * @throws Exception
	 */
    public BaseResultSet bscQueryWorkOrder(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T1.ORG_ID = T.ORG_ID "
    			+ " AND T1.PID = T2.ORG_ID"
    			+ " AND T2.ORG_ID = "+user.getOrgId()+""
    			 +" ORDER BY NVL(T.GO_DATE,TO_DATE('2014-01-01 01:01','YYYY-MM-DD HH:mi')) DESC,\n"+
    	          " NVL(T.ARRIVE_DATE,TO_DATE('2014-01-01 01:01','YYYY-MM-DD HH:mi')) DESC,\n"+
    			  " NVL(T.COMPLETE_DATE,TO_DATE('2014-01-01 01:01','YYYY-MM-DD HH:mi')) DESC";
        page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.WORK_ID,\n" );
    	sql.append("       T.WORK_NO,\n" );
    	sql.append("       T.WORK_VIN,\n" );
    	sql.append("       T.REPAIR_USER,\n" );
    	sql.append("       T.REPAIR_DATE,\n" );
    	sql.append("       T.WORK_TYPE,\n" );
    	sql.append("       T.IF_OUT,\n" );
    	sql.append("       T.WORK_STATUS,\n" );
    	sql.append("       T.APPLY_USER,\n" );
    	sql.append("       T.APPLY_MOBIL,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.APPLY_ADDRESS,\n" );
    	sql.append("       T.JOBORDER_TIME,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.GO_DATE,\n" );
    	sql.append("       T.ARRIVE_DATE,\n" );
    	sql.append("       T.COMPLETE_DATE,\n" );
    	sql.append("       T.ORG_ID ORG_CODE,\n" );
    	sql.append("       D.LICENSE_PLATE,\n" );
    	sql.append("       T.ORG_ID ORG_NAME\n" );
    	sql.append(" FROM SE_BU_WORK_ORDER T\n" );
    	sql.append("  LEFT JOIN SE_BU_WORK_DISPATCH D ON T.WORK_ID = D.WORK_ID ");
    	sql.append("  AND D.STATUS ="+DicConstant.YXBS_01+" ,TM_ORG T1,TM_ORG T2");

    	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("IF_OUT", "SF");
		bs.setFieldDic("WORK_STATUS", "PGDZT");
		bs.setFieldDic("WORK_TYPE", "PGLX");
		bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
		bs.setFieldOrgDeptCode("ORG_CODE");
		bs.setFieldDateFormat("REPAIR_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("GO_DATE", "yyyy-MM-dd HH:mm");
		bs.setFieldDateFormat("JOBORDER_TIME", "yyyy-MM-dd HH:mm");
		bs.setFieldDateFormat("ARRIVE_DATE", "yyyy-MM-dd HH:mm");
		bs.setFieldDateFormat("COMPLETE_DATE", "yyyy-MM-dd HH:mm");
    	return bs;
    }
    /**
     * @title: queryWorkOrder 
     * @description: TODO( 查询工单信息)
     * @param page
     * @param user
     * @param conditions
     * @return BaseResultSet
     * @throws Exception
     */
    public BaseResultSet queryWorkOrder(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	String orgType=user.getOrgDept().getOrgType();
    	String orgId=user.getOrgId();
    	if("200007".equals(orgType))
    	{
    		wheres += " AND T.ORG_ID = "+orgId+" \n";
    	}
    	wheres += "ORDER BY NVL(T.GO_DATE,TO_DATE('2014-01-01 01:01','YYYY-MM-DD HH:mi')) DESC,\n"+
    			" NVL(T.ARRIVE_DATE,TO_DATE('2014-01-01 01:01','YYYY-MM-DD HH:mi')) DESC,\n"+
    			" NVL(T.COMPLETE_DATE,TO_DATE('2014-01-01 01:01','YYYY-MM-DD HH:mi')) DESC";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.WORK_ID,\n" );
    	sql.append("       T.WORK_NO,\n" );
    	sql.append("       T.WORK_VIN,\n" );
    	sql.append("       T.REPAIR_USER,\n" );
    	sql.append("       T.REPAIR_DATE,\n" );
    	sql.append("       T.WORK_TYPE,\n" );
    	sql.append("       T.IF_OUT,\n" );
    	sql.append("       T.WORK_STATUS,\n" );
    	sql.append("       T.APPLY_USER,\n" );
    	sql.append("       T.APPLY_MOBIL,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.APPLY_ADDRESS,\n" );
    	sql.append("       T.JOBORDER_TIME,\n" );
    	sql.append("       T.DISPATCH_TIME,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.GO_DATE,\n" );
    	sql.append("       T.ARRIVE_DATE,\n" );
    	sql.append("       T.COMPLETE_DATE,\n" );
    	sql.append("       NVL(T.STATUS,'100201')STATUS,\n" );
    	sql.append("       T.ORG_ID ORG_CODE,\n" );
    	sql.append("       D.LICENSE_PLATE,\n" );
    	sql.append("       T.ORG_ID ORG_NAME\n" );
    	sql.append(" FROM SE_BU_WORK_ORDER T\n" );
    	sql.append("  LEFT JOIN SE_BU_WORK_DISPATCH D ON T.WORK_ID = D.WORK_ID");
    	sql.append("  AND D.STATUS ="+DicConstant.YXBS_01+"");
    	
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("IF_OUT", "SF");
    	bs.setFieldDic("STATUS", "YXBS");
    	bs.setFieldDic("WORK_STATUS", "PGDZT");
    	bs.setFieldDic("WORK_TYPE", "PGLX");
    	bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
    	bs.setFieldOrgDeptCode("ORG_CODE");
    	bs.setFieldDateFormat("REPAIR_DATE", "yyyy-MM-dd");
    	bs.setFieldDateFormat("GO_DATE", "yyyy-MM-dd HH:mm");
    	bs.setFieldDateFormat("JOBORDER_TIME", "yyyy-MM-dd HH:mm");
    	bs.setFieldDateFormat("ARRIVE_DATE", "yyyy-MM-dd HH:mm");
    	bs.setFieldDateFormat("COMPLETE_DATE", "yyyy-MM-dd HH:mm");
    	return bs;
    }
    /**
     * @title: queryWorkOrder 
     * @description: TODO( 查询工单信息)
     * @param page
     * @param user
     * @param conditions
     * @return BaseResultSet
     * @throws Exception
     */
    public BaseResultSet queryDealerWorkOrder(PageManager page, User user, String conditions) throws Exception
    {
    	String wheres = conditions;
    	String orgType=user.getOrgDept().getOrgType();
    	String orgId=user.getOrgId();
    	if("200007".equals(orgType))
    	{
    		wheres += " AND T.ORG_ID = "+orgId+" \n";
    	}
    	wheres += "ORDER BY NVL(T.GO_DATE,TO_DATE('2014-01-01 01:01','YYYY-MM-DD HH:mi')) DESC,\n"+
    			" NVL(T.ARRIVE_DATE,TO_DATE('2014-01-01 01:01','YYYY-MM-DD HH:mi')) DESC,\n"+
    			" NVL(T.COMPLETE_DATE,TO_DATE('2014-01-01 01:01','YYYY-MM-DD HH:mi')) DESC";
    	page.setFilter(wheres);
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.WORK_ID,\n" );
    	sql.append("       T.WORK_NO,\n" );
    	sql.append("       T.WORK_VIN,\n" );
    	sql.append("       T.REPAIR_USER,\n" );
    	sql.append("       T.REPAIR_DATE,\n" );
    	sql.append("       T.WORK_TYPE,\n" );
    	sql.append("       T.IF_OUT,\n" );
    	sql.append("       T.WORK_STATUS,\n" );
    	sql.append("       T.APPLY_USER,\n" );
    	sql.append("       T.APPLY_MOBIL,\n" );
    	sql.append("       T.APPLY_DATE,\n" );
    	sql.append("       T.APPLY_ADDRESS,\n" );
    	sql.append("       T.JOBORDER_TIME,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.GO_DATE,\n" );
    	sql.append("       T.ARRIVE_DATE,\n" );
    	sql.append("       T.COMPLETE_DATE,\n" );
    	sql.append("       T.ORG_ID ORG_CODE,\n" );
    	sql.append("       D.LICENSE_PLATE,\n" );
    	sql.append("       T.ORG_ID ORG_NAME\n" );
    	sql.append(" FROM SE_BU_WORK_ORDER T\n" );
    	sql.append("  LEFT JOIN SE_BU_WORK_DISPATCH D ON T.WORK_ID = D.WORK_ID");
    	sql.append("  AND D.STATUS ="+DicConstant.YXBS_01+"");
    	
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("IF_OUT", "SF");
    	bs.setFieldDic("WORK_STATUS", "PGDZT");
    	bs.setFieldDic("WORK_TYPE", "PGLX");
    	bs.setFieldOrgDeptSimpleName("ORG_NAME");//简称
    	bs.setFieldOrgDeptCode("ORG_CODE");
    	bs.setFieldDateFormat("REPAIR_DATE", "yyyy-MM-dd");
    	bs.setFieldDateFormat("GO_DATE", "yyyy-MM-dd HH:mm");
    	bs.setFieldDateFormat("JOBORDER_TIME", "yyyy-MM-dd HH:mm");
    	bs.setFieldDateFormat("ARRIVE_DATE", "yyyy-MM-dd HH:mm");
    	bs.setFieldDateFormat("COMPLETE_DATE", "yyyy-MM-dd HH:mm");
    	return bs;
    }
    /**
     * @title: checkWorkOrder 
   	 * @description: TODO( 根据工单ID和orgID 校验工单是否已转索赔单)
   	 * @param workId  工单ID
   	 * @param user
   	 * @return QuerySet
   	 * @throws Exception
   	 */
   public QuerySet checkWorkOrder(String workId,User user ) throws Exception
   {
	    QuerySet qs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT COUNT(T.CLAIM_ID) AS COUCLAIM FROM SE_BU_CLAIM T WHERE T.ORG_ID="+user.getOrgId()+" AND T.WORK_ID="+workId);
		qs = factory.select(null,sql.toString());
	   	return qs;
   }
   /**
    * @title: insertClaimMantis 
    * @description: TODO() 
    * @param workId
    * @param user
    * @return  
    * @throws Exception    设定文件 
    */
   public void insertClaimMantis(String workId,User user) throws Exception
   {
	   SeBuClaimVO vo=new SeBuClaimVO(); 
	   vo.setWorkId(workId);
	   vo.setClaimStatus(DicConstant.SPDZT_01);
	   vo.setOrgId(user.getOrgId());
	   vo.setCompanyId(user.getCompanyId());
	   vo.setOemCompanyId(user.getOemCompanyId());
	   vo.setStatus(DicConstant.YXBS_01);
	   vo.setCreateTime(new Date());
	   vo.setCreateUser(user.getAccount());
	   //获取索赔单号
	   String claimNo ="";
	   claimNo=this.getClaimNo();
	   QuerySet qs = null;
	   StringBuffer sql= new StringBuffer();
	   boolean flag=true;
	   while(flag){
		   //判断单号是否存在，如果存在，取下一个单号
		   sql.append("SELECT COUNT(1) SL FROM SE_BU_CLAIM C WHERE C.CLAIM_NO = '"+claimNo+"'");
		   qs = factory.select(null, sql.toString());
		   if(qs.getRowCount() > 0 ){
			   int sl  = Integer.valueOf(qs.getString(1,"SL"));
			   if(sl >= 1){
				   claimNo=this.getClaimNo();
			   }else{
				   flag=false;
			   }
		   }
	   }
	   vo.setClaimNo(claimNo);
   	   factory.insert(vo);
   }
   
   /**
    * 生成索赔单号
    * @return String
    * @throws Exception
    */
   public String getClaimNo()throws Exception
   {
	   QuerySet qs = null;
	   String num="";
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT F_GETCLAIMNO() AS CLAIM_NO FROM DUAL");
	   qs = factory.select(null, sql.toString());
	   if(qs.getRowCount() > 0){
		   num=qs.getString(1, "CLAIM_NO");
	   }
	   return num;
	   /*	QuerySet qs = null;
	   	String date = getDateToString();
	   	String num = "SPD";
	   	if(date!=null)
	   	{
			date = date.replaceAll("-", "");
			num+=date;
		}
	   	StringBuffer sql= new StringBuffer(); 	
	   	sql.append("SELECT max(").append("CLAIM_NO").append(") as DH FROM ");
		sql.append( "SE_BU_CLAIM").append(" t");
		sql.append(" where t.").append("CLAIM_NO").append(" like '%").append(num).append("%'");
	   	qs = factory.select(null, sql.toString());
	   	if(qs.getRowCount()==0){
			 num+="0001";
		}else{
			    DataObjImpl dateObj = (DataObjImpl) qs.getDataObjs().get(0);
			 	String tem  = dateObj.getString("DH");

			if(tem==null||"null".equals(tem)){
				num+="00001";	
			}else{
				int sz = Integer.parseInt(tem.substring(tem.length()-5, tem.length()))+1;
				//如果1位数
				if(String.valueOf(sz).length()==1){
					num=tem.substring(0, tem.length()-5)+"0000"+String.valueOf(sz);
				}
				//如果2位数
				else if(String.valueOf(sz).length()==2){
					num=tem.substring(0, tem.length()-5)+"000"+String.valueOf(sz);
				}
				//如果3位数
				else if(String.valueOf(sz).length()==3){
					num=tem.substring(0, tem.length()-5)+"00"+String.valueOf(sz);
				}
				//如果4位数
				else if(String.valueOf(sz).length()==4){
					num=tem.substring(0, tem.length()-5)+"0"+String.valueOf(sz);
				}
				//如果5位数
				else{
					num=tem.substring(0, tem.length()-5)+String.valueOf(sz);
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
    * @title: searchClaimMatin 
  	 * @description: TODO( 根据工单ID和orgID 查询已转索赔单)
	 * @param page
	 * @param user
	 * @param workId
	 * @param conditions
	 * @return BaseResultSet
	 * @throws Exception
	 */
   public BaseResultSet searchClaimMatin(PageManager page, User user,String workId, String conditions) throws Exception
   {
	   	String wheres = conditions;
	   	wheres += " AND T.ORG_ID = "+user.getOrgId()+" AND T.WORK_ID="+workId+" AND T.CLAIM_STATUS=301001";
	    page.setFilter(wheres);
	   	BaseResultSet bs = null;
	   	StringBuffer sql= new StringBuffer();
	   	sql.append("SELECT T.CLAIM_NO, T.CLAIM_ID, T.CLAIM_STATUS,T.ORG_ID\n" );
	   	sql.append("  FROM SE_BU_CLAIM T\n" );
	   	bs = factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_STATUS", "SPDZT");
		bs.setFieldOrgDeptSimpleName("ORG_ID");
	   	return bs;
   }
   /**
    * @title: searchPattern 
  	 * @description: TODO( 查询故障信息)
	 * @param page
	 * @param user
	 * @param conditions
	 * @param claimId
	 * @return BaseResultSet
	 * @throws Exception
	 */
   public BaseResultSet searchPattern(PageManager page, User user, String conditions,String claimId,String cliamType,String partCode,String partName,String flag) throws Exception
   {
	   	String wheres = conditions; 	
	   	wheres += " AND T1.PATTERN_ID = T0.PATTERN_ID  AND T1.AMOUNT_ID = T3.AMOUNT_ID AND T0.STATUS=100201 \n";
	    wheres += "AND EXISTS\n" +
	    		  "    (SELECT 1\n" + 
	    		  "    		FROM PT_BA_INFO T2\n" + 
	    		  "           WHERE T2.F_POSITION_ID = T0.POSITION_ID\n" + 
	    		  "               AND EXISTS\n" + 
	    		  "              (SELECT 1\n" + 
	    		  "           FROM PT_BA_PART_SUPPLIER_RL R\n" + 
	    		  "    WHERE T2.PART_ID = R.PART_ID\n" + 
	    		  "    AND R.SE_IDENTIFY = 100201)\n" + 
	    		  "    AND T2.SE_STATUS = 100201\n" + 
	    		  "    AND NVL(T2.SE_CLPRICE, 0) > 0\n" + 
	    		  "    AND NVL(T2.SE_REPRICE, 0) > 0 \n";
	    if(!("".equals(partCode) || partCode==null)){
	    	wheres+=" AND T2.PART_CODE LIKE '%"+partCode+"%' \n";
	    }
	    if(!("".equals(partName) || partName==null)){
	    	wheres+=" AND  T2.PART_NAME LIKE '%"+partName+"%' \n";
	    }
	   	wheres +=      	  " )\n";
	   /*	if(DicConstant.SPDLX_08.equals(cliamType))	//如果是三包急件订单时选择的是三包急件的配件对应的故障
	   	{
	   		wheres += " AND EXISTS (SELECT 1  FROM SE_BU_DISPATCH_DTL D, SE_BU_CLAIM T, PT_BA_INFO I  WHERE T.DISPATCH_ID = D.DISPATCH_ID  AND D.PART_ID = I.PART_ID  AND I.F_POSITION_ID = T0.POSITION_ID AND T.CLAIM_ID = "+claimId+")";
	   	}*/
		/*if(DicConstant.SPDLX_03.equals(cliamType))//如果是服务活动时选择的项目为服务活动中的项目
	   	{
			if(flag.equals("1")){
			}else{
				wheres +=" AND ( EXISTS  (SELECT 1 FROM SE_BU_ACTIVITY_TASK_TIME P,SE_BU_CLAIM C WHERE C.ACTIVITY_ID=P.ACTIVITY_ID AND C.CLAIM_ID="+claimId+" AND P.TASK_TIME_ID=T3.AMOUNT_ID)";
				wheres +=" OR EXISTS  (SELECT 1 FROM SE_BU_ACTIVITY_PART  P, SE_BU_CLAIM C,PT_BA_INFO I WHERE C.ACTIVITY_ID=P.ACTIVITY_ID  AND P.PART_ID=I.PART_ID AND C.CLAIM_ID="+claimId+" AND I.F_POSITION_ID=T0.POSITION_ID))";
			}
			
	   	}*/
		//预授权
		/*if(DicConstant.SPDLX_08.equals(cliamType))//如果索赔单类型为预授权时，选择的项目为预授权对应的项目
	   	{
			wheres += " AND ( EXISTS (SELECT 1 FROM SE_BU_PRE_AUTHOR_PROJECT P, SE_BU_CLAIM C WHERE C.PRE_AUTHOR_ID=P.AUTHOR_ID AND C.CLAIM_ID="+claimId+" AND P.AMOUNT_ID=T3.AMOUNT_ID)";
			wheres +=" OR EXISTS  (SELECT 1 FROM SE_BU_PRE_AUTHOR_PART  P, SE_BU_CLAIM C,PT_BA_INFO I WHERE C.PRE_AUTHOR_ID=P.AUTHOR_ID  AND P.PART_ID=I.PART_ID AND C.CLAIM_ID="+claimId+" AND I.F_POSITION_ID=T0.POSITION_ID))";
	   	}*/
	    // 	wheres += " AND NOT EXISTS (SELECT 1 FROM SE_BU_CLAIM_FAULT C  WHERE C.CLAIM_ID = "+claimId+" AND C.FAULT_ID = T1.PATTERN_ID)";
	    page.setFilter(wheres);
	   	BaseResultSet bs = null;
	   	StringBuffer sql= new StringBuffer();
	   	sql.append("SELECT T1.RELATION_ID,\n" );
	   	sql.append("       T1.PATTERN_ID,\n" );
	   	sql.append("       T1.FAULT_PATTERN_CODE,\n" );
	   	sql.append("       T1.FAULT_PATTERN_NAME,\n" );
	   	sql.append("       T3.AMOUNT_SET,\n" );
		sql.append("       T3.TIME_NAME,\n" );
		sql.append("       T3.TIME_CODE,\n" );
		sql.append("       T0.POSITION_CODE,\n" );
		sql.append("       T0.POSITION_NAME\n" );
	   	sql.append("  FROM SE_BA_FAULT_TASKTIME T1, SE_BA_TASK_AMOUNT T3,SE_BA_FAULT_PATTERN  T0\n" );	
	   	bs = factory.select(sql.toString(), page);
	   	return bs;
   }
   /**
    * @title: insertClaimPattern 
    * @description: TODO() 
    * @param claimId
    * @param relationIds SE_BA_FAULT_TASKTIME表中关系ID集合
    * @param user
    * @return  
    * @throws Exception    设定文件 
    */
   public void insertClaimPattern(String claimId,String relationIds,User user,String claimType,String userType) throws Exception
   {
	   UserParaConfigureVO userVo= (UserParaConfigureVO) ParaManager.getInstance().getUserParameter("201501");
	   String jcjlxs = "";//军车激励系数
	   if(userVo != null){
		   jcjlxs=userVo.getParavalue1(); 
	   }else{
		    jcjlxs= "0.2";
	   }
	   //获取服务站星际
	   StringBuffer sql1= new StringBuffer();
	   sql1.append("SELECT M.DEALER_STAR\n" );
	   sql1.append("  FROM MAIN_DEALER M, USER_PARA_CONFIGURE R\n" );
	   sql1.append(" WHERE R.PARAKEY = TO_CHAR(M.DEALER_STAR) \n");
	   sql1.append(" AND M.ORG_ID ="+user.getOrgId()+" ");
	   QuerySet qs =factory.select(null, sql1.toString());
	   String flag="";
	   
	   if(qs.getRowCount() > 0){
		   flag=qs.getString(1, "DEALER_STAR");
	   }
	   
	   //照顾性保修、服务活动中的促销不享受工时激励 ,服务活动中的技术升级只享受星级系数
	   String dealerWorkTimeUprice=getDealerWorkTimeUprice(user,claimId);//工时费
	   String starLevelUprice=getStarLevelUprice(user,claimId,claimType);
	   String dealerEncourageUprice=getDealerEncourageUprice(user,claimId,claimType);
	   Double xjxs =Double.valueOf(starLevelUprice);
	   // 如果星级系数是0 则不按照整改的计算
	   if(!"0".equals(starLevelUprice)){
		   if(flag.equals("200301") || flag.equals("200302")){
			   xjxs = xjxs-1;   
		   }
	   }
	   //星际单价= 工时费 * 星际系数
	   Double xjdj=Double.valueOf(dealerWorkTimeUprice)* xjxs ; 
	   
	   //激励系数处理
	   Double jlxs=Double.valueOf(dealerEncourageUprice);//服务站 基础激励系数
	   if(userType.equals(DicConstant.CLYHLX_02.toString())){
		   jlxs=jlxs + Double.valueOf(jcjlxs);// 军车激励系数+服务站基础系数
	   }
	   //激励单价 =工时费 * 激励系数
	   Double jldj=Double.valueOf(dealerWorkTimeUprice) * jlxs ; 
	   //工时费=工时定额*（工时单价+星级单价+激励单价）
	   StringBuffer sql= new StringBuffer();
	   sql.append("INSERT INTO SE_BU_CLAIM_FAULT\n" );
	   sql.append("  (CLAIM_DTL_ID,\n" );
	   sql.append("   CLAIM_ID,\n" );
	   sql.append("   FAULT_ID,\n" );
	   sql.append("   FAULT_CODE,\n" );
	   sql.append("   FAULT_NAME,\n" );
	   sql.append("   WORK,\n" );
	   sql.append("   WORK_CODE,\n" );
	   sql.append("   WORK_TIME,\n" );
	   sql.append("   WORK_TIME_UPRICE,\n" );
	   sql.append("   STAR_LEVEL_UPRICE,\n" );
	   sql.append("   ENCOURAGE_UPRICE,\n" );
	   sql.append("   WORK_COSTS,\n" );
	   sql.append("   CREATE_USER,\n" );
	   sql.append("   CREATE_TIME,\n" );
	   sql.append("   STATUS)\n" );
	   sql.append("  SELECT F_GETID(),\n" );
	   sql.append("         "+claimId+",\n" );
	   sql.append("         T.PATTERN_ID,\n" );
	   sql.append("         T.FAULT_PATTERN_CODE,\n" );
	   sql.append("         T.FAULT_PATTERN_NAME,\n" );
	   sql.append("         T1.TIME_NAME,\n" );
	   sql.append("         T1.TIME_CODE,\n" );
	   sql.append("         T1.AMOUNT_SET,\n" );
	   sql.append("         '"+dealerWorkTimeUprice+"',\n" );
	   sql.append("         '"+xjdj+"'  ,\n" );
	   sql.append("         '"+jldj+"',\n" );
	   sql.append("         T1.AMOUNT_SET * ('"+dealerWorkTimeUprice+"' + '"+xjdj+"'+ '"+jldj+"'),\n" );
	   sql.append("         '"+user.getAccount()+"',\n" );
	   sql.append("         SYSDATE,\n" );
	   sql.append("         "+Constant.YXBS_01+"\n" );
	   sql.append("    FROM SE_BA_FAULT_TASKTIME T, SE_BA_TASK_AMOUNT T1\n" );
	   sql.append("   WHERE T.AMOUNT_ID = T1.AMOUNT_ID\n" );
	   sql.append("     AND T.RELATION_ID IN ("+relationIds+")");
	   factory.update(sql.toString(), null);
   }
   //获取服务站工时费
   public String getDealerWorkTimeUprice(User user,String claimId) throws SQLException
   {
	   QuerySet qs = null;
	   String uPrice="1";
	   
	   StringBuffer sql= new StringBuffer();
	   //modify by zhaojinyu 2014-09-24 需调整 按照维修时间的所属时间类型 (白天还是夜间，具体算法参考索赔单提报页面)和维护时间所在时间范围获取出工时单价
	   sql.append("SELECT CC.UNIT_PRICE FROM SE_BA_CLAIM_CODE CC WHERE CC.ORG_ID="+user.getOrgId()+" AND CC.STATUS=100201" );
	   sql.append(" AND CC.TIME_TYPE =(SELECT CASE\n" );
	   sql.append("         WHEN TO_NUMBER(TO_CHAR(M.REPAIR_DATE, 'HH24MI')) >\n" );
	   sql.append("              TO_NUMBER(REPLACE(E.PARAVALUE1, ':')) AND\n" );
	   sql.append("              TO_NUMBER(TO_CHAR(M.REPAIR_DATE, 'HH24MI')) <=\n" );
	   sql.append("              TO_NUMBER(REPLACE(E.PARAVALUE2, ':')) THEN\n" );
	   sql.append("          '302301'\n" );
	   sql.append("         ELSE\n" );
	   sql.append("          '302302'\n" );
	   sql.append("       END AS SJ_TYPE\n" );
	   sql.append("  FROM SE_BU_CLAIM M, USER_PARA_CONFIGURE E\n" );
	   sql.append(" WHERE M.CLAIM_ID = "+claimId+"\n" );
	   sql.append("   AND E.PARAKEY = '200201')");
	   sql.append("	  AND CC.START_DATE <=\n" );
	   sql.append("       (SELECT M.REPAIR_DATE\n" );
	   sql.append("          FROM SE_BU_CLAIM M\n" );
	   sql.append("         WHERE M.CLAIM_ID = "+claimId+")\n" );
	   sql.append("    AND CC.END_DATE >= (SELECT M.REPAIR_DATE\n" );
	   sql.append("                         FROM SE_BU_CLAIM M\n" );
	   sql.append("                        WHERE M.CLAIM_ID = "+claimId+")");
	   qs=factory.select(null, sql.toString());
	   if(qs.getRowCount()>0)
	   {
		   uPrice=qs.getString(1, "UNIT_PRICE");
	   }
	   return uPrice;
   }
  //获取服务站星级单价
   public String getStarLevelUprice(User user,String claimId,String claimType) throws SQLException
   {
	   String uPrice="0";
	   QuerySet qs = null;
	   if(!DicConstant.SPDLX_08.equals(claimType))
	   {
		   if(DicConstant.SPDLX_03.equals(claimType))
		   {
			   StringBuffer sql= new StringBuffer();
			   sql.append("SELECT COUNT(A.ACTIVITY_ID) AS COU\n" );
			   sql.append("  FROM SE_BU_ACTIVITY A, SE_BU_CLAIM C\n" );
			   sql.append(" WHERE A.ACTIVITY_ID = C.CLAIM_ID\n" );
			   sql.append("   AND C.CLAIM_ID = "+claimId+"\n" );
			   sql.append("   AND A.ACTIVITY_TYPE = 300502");
			   qs=factory.select(null, sql.toString());
			   if(qs.getRowCount()>0)
			   {
				   if(Integer.valueOf(qs.getString(1, "COU"))>0)
				   {
					   uPrice="0";
				   }else
				   {
					   uPrice=getStarLevelUpriceMenth(user);
				   }
				   
			   }else
			   {
				   uPrice=getStarLevelUpriceMenth(user);
			   }
			   
		   }else
		   {
			   uPrice=getStarLevelUpriceMenth(user);
		   }
	   }
	  
	   return uPrice;
   }
   public String getStarLevelUpriceMenth(User user) throws SQLException
   {
	   String uPrice="0";
	   QuerySet qs = null;
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT R.PARAVALUE1\n" );
	   sql.append("  FROM MAIN_DEALER M, USER_PARA_CONFIGURE R\n" );
	   sql.append(" WHERE R.PARAKEY = TO_CHAR(M.DEALER_STAR)\n" );
	   sql.append("   AND M.ORG_ID = "+user.getOrgId());
	   qs=factory.select(null, sql.toString());
	   if(qs.getRowCount()>0)
	   {
		   uPrice=qs.getString(1, "PARAVALUE1");
	   }
	   return uPrice;
   }
  //获取服务站激励单价
   public String getDealerEncourageUprice(User user,String claimId,String claimType) throws SQLException
   {
	   String uPrice="0";
	   QuerySet qs = null;
	   if(DicConstant.SPDLX_03.equals(claimType))
	   {
		   StringBuffer sql= new StringBuffer();
		   sql.append("SELECT COUNT(A.ACTIVITY_ID) AS COU\n" );
		   sql.append("  FROM SE_BU_ACTIVITY A, SE_BU_CLAIM C\n" );
		   sql.append(" WHERE A.ACTIVITY_ID = C.CLAIM_ID\n" );
		   sql.append("   AND C.CLAIM_ID = "+claimId+"\n" );
		   sql.append("   AND A.ACTIVITY_TYPE = 300501");
		   qs=factory.select(null, sql.toString());
		   if(qs.getRowCount()>0)
		   {
			   if(Integer.valueOf(qs.getString(1, "COU"))>0)
			   {
				   uPrice="0";
			   }else
			   {
				   uPrice=getDealerEncourageUpriceMenth(user,claimId,claimType);
			   }
			   
		   }else
		   {
			   uPrice=getDealerEncourageUpriceMenth(user,claimId,claimType);
		   }
	   }else
	   {
		   uPrice=getDealerEncourageUpriceMenth(user,claimId,claimType);
	   }
	   
	   return uPrice;
   }
   
   public String getDealerEncourageUpriceMenth(User user,String claimId,String claimType) throws SQLException
   {
	   String uPrice="0";
	   QuerySet qs = null;
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT NVL(MAX(COEFFICIENT_RADIO), 0) COEFFICIENT_RADIO\n" );
	   sql.append("  FROM (SELECT TT.COEFFICIENT_RADIO\n" );
	   sql.append("          FROM SE_BA_EXCITATION_COEFFICIENT TT, MAIN_VEHICLE MV\n" );
	   sql.append("         WHERE MV.MODELS_ID = TT.MODEL_ID\n" );
	   sql.append("           AND MV.VEHICLE_ID =\n" );
	   sql.append("               (SELECT SBC.VEHICLE_ID\n" );
	   sql.append("                  FROM SE_BU_CLAIM SBC\n" );
	   sql.append("                 WHERE SBC.CLAIM_ID = "+claimId+")\n" );
	   sql.append("           AND TT.START_DATE <= MV.BUY_DATE\n" );
	   sql.append("           AND TT.END_DATE >= MV.BUY_DATE\n" );
	   sql.append("        UNION ALL\n" );
	   sql.append("        SELECT TT.COEFFICIENT_RADIO\n" );
	   sql.append("          FROM SE_BA_EXCITATION_COEFFICIENT TT,\n" );
	   sql.append("               MAIN_VEHICLE                 MV,\n" );
	   sql.append("               SE_BA_ENGINE_TYPE            E\n" );
	   sql.append("         WHERE E.TYPE_ID = TT.ENGINE\n" );
	   sql.append("           AND MV.ENGINE_TYPE = E.TYPE_CODE\n" );
	   sql.append("           AND MV.VEHICLE_ID =\n" );
	   sql.append("               (SELECT SBC.VEHICLE_ID\n" );
	   sql.append("                  FROM SE_BU_CLAIM SBC\n" );
	   sql.append("                 WHERE SBC.CLAIM_ID = "+claimId+")\n" );
	   //modify by zhaojinyu 2014-09-24 获取激励系统增加车辆销售日期的校验
	   sql.append("           AND TT.START_DATE <= MV.BUY_DATE\n" );
	   sql.append("           AND TT.END_DATE >= MV.BUY_DATE)");
	   qs=factory.select(null, sql.toString());
	   if(qs.getRowCount()>0)
	   {
		   uPrice=qs.getString(1, "COEFFICIENT_RADIO");
	   }
	   return uPrice;
	  /* StringBuffer sql= new StringBuffer();
	   sql.append("SELECT TT.COEFFICIENT_RADIO\n" );
	   sql.append("  FROM SE_BA_EXCITATION_COEFFICIENT TT, SE_BU_CLAIM SBC, MAIN_VEHICLE MV\n" );
	   sql.append(" WHERE MV.VEHICLE_ID = SBC.VEHICLE_ID\n" );
	   sql.append("   AND (MV.ENGINE_TYPE = TT.ENGINE OR MV.MODELS_ID = TT.MODEL_ID)\n" );
	   //sql.append("   AND TT.ORG_ID = "+user.getOrgId()+"\n" );
	   sql.append("   AND SBC.CLAIM_ID = "+claimId+"\n" );
	  
	   sql.append("   AND TT.START_DATE <= MV.BUY_DATE\n" );
	   sql.append("   AND TT.END_DATE >= MV.BUY_DATE\n" );
	   sql.append(" ORDER BY TT.COEFFICIENT_RADIO DESC");*/
   }
   /**
    * @title: searchClaimPattern 
  	 * @description: TODO( 查询索赔单故障信息)
	 * @param page
	 * @param user
	 * @param conditions
	 * @return BaseResultSet
	 * @throws Exception
	 */
   public BaseResultSet searchClaimPattern(PageManager page, User user, String conditions) throws Exception
   {
	   	String wheres = conditions;
	   	wheres += "  AND STATUS="+Constant.YXBS_01;
	       page.setFilter(wheres);
	    page.setPageRows(100);
	   	BaseResultSet bs = null;
	   	StringBuffer sql= new StringBuffer();
	   	sql.append("SELECT CLAIM_DTL_ID,\n" );
	   	sql.append("       CLAIM_ID,\n" );
	   	sql.append("       FAULT_ID,\n" );
	   	sql.append("       FAULT_CODE,\n" );
	   	sql.append("       FAULT_NAME,\n" );
	   	sql.append("       WORK_TIME,\n" );
	   	sql.append("       WORK_TIME_UPRICE,\n" );
	   	sql.append("       STAR_LEVEL_UPRICE,\n" );
	   	sql.append("       ENCOURAGE_UPRICE,\n" );
	   	sql.append("       WORK_COSTS,\n" );
	   	sql.append("       CREATE_USER,\n" );
	   	sql.append("       CREATE_TIME,\n" );
	   	sql.append("       STATUS,\n" );
	   	sql.append("       WORK_MULTIPLE,\n" );
		sql.append("       (SELECT C.NAME FROM SE_BA_FAULT_PATTERN P ,SE_BA_CODE C WHERE P.PATTERN_ID=FAULT_ID AND P.SEVERITY = C.CODE_ID) AS　SEVERITY\n" );
	   	sql.append("  FROM SE_BU_CLAIM_FAULT");	 
	   	bs = factory.select(sql.toString(), page);
	   	return bs;
   }
   /**
    * @title: deleteClaimPattern 
  	 * @description: TODO( 删除索赔单故障信息)
	 * @param claimDtlId
	 * @return void
	 * @throws Exception
	 */
   public void deleteClaimPattern(String claimDtlId) throws Exception
   {
	   StringBuffer sql= new StringBuffer();
	   sql.append("DELETE  SE_BU_CLAIM_FAULT T WHERE  T.CLAIM_DTL_ID= "+claimDtlId);
	   factory.update(sql.toString(), null);
   }
   /**
    * @title: deleteClaimPatternPart 
  	 * @description: TODO( 删除索赔单故障与零件关系表)
	 * @param claimDtlId
	 * @return void
	 * @throws Exception
	 */
   public void deleteClaimPatternPart(String claimDtlId) throws Exception
   {
	   StringBuffer sql= new StringBuffer();
	   sql.append("DELETE  SE_BU_CLAIM_FAULT_PART T WHERE  T.CLAIM_DTL_ID= "+claimDtlId);
	   factory.update(sql.toString(), null);
   }
   /**
    * @title: searchOldPart  选择新件旧件，都需要加上标准件，标准件的部位代码是10091007 固定的
  	 * @description: TODO( 查询故障中的旧件，前提是在故障与零件关系表里存在并且不在已提报的索赔单中出现的)
  	 * 选择旧件时，旧件代码和旧件供应商不能在同一故障下存在，并且不能在相同故障下存在
	 * @param page
	 * @param user   
	 * @param conditions
	 * @param claimDtlId  
	 * @param selectType //1表示选择旧件,2表示选择新件  
	 * @param faultType //是否主损件 
	 * @return BaseResultSet
	 * @throws Exception
	 */
   public BaseResultSet searchOldPart(PageManager page, User user, String conditions,String claimDtlId,String selectType,String claimType,String claimId,String faultType) throws Exception
   {
	   	String wheres = conditions;
	   	if("1".equals(selectType))
	   	{
	   		wheres += " AND T1.PART_ID = T3.PART_ID AND T3.SUPPLIER_ID = T2.SUPPLIER_ID AND  NVL(T1.SE_REPRICE,0)>0  AND T3.SE_IDENTIFY ="+DicConstant.YXBS_01+" AND T2.SE_IDENTIFY ="+DicConstant.YXBS_01+" " +
		   			  " AND NOT EXISTS (SELECT 1 FROM SE_BU_CLAIM_FAULT_PART T6 WHERE T6.OLD_PART_ID = T1.PART_ID AND T6.OLD_SUPPLIER_ID = T2.SUPPLIER_ID AND T6.CLAIM_DTL_ID = "+claimDtlId+") \n" +
	   				  " AND NOT EXISTS (SELECT 1 FROM SE_BU_CLAIM_FAULT A, SE_BU_CLAIM_FAULT_PART B WHERE A.CLAIM_DTL_ID = B.CLAIM_DTL_ID AND B.OLD_PART_ID = T1.PART_ID AND B.OLD_SUPPLIER_ID = T2.SUPPLIER_ID AND A.FAULT_CODE IN (SELECT C.FAULT_CODE FROM SE_BU_CLAIM_FAULT C WHERE C.CLAIM_DTL_ID ="+claimDtlId+" ) AND A.CLAIM_ID = "+claimId+")";
	   		//如果是主损件 必须选择该故障下的配件 否则选择所有供货关系的配件信息
	   		if(DicConstant.GZLB_01.toString().equals(faultType)){
		    	wheres +=  " AND T1.F_POSITION_ID = N.POSITION_ID AND (EXISTS (SELECT 1 FROM SE_BA_FAULT_PATTERN T7 WHERE T7.POSITION_ID = T1.F_POSITION_ID AND T7.PATTERN_ID = (SELECT F.FAULT_ID FROM SE_BU_CLAIM_FAULT F WHERE F.CLAIM_DTL_ID = "+claimDtlId+") OR N.POSITION_CODE = '10091007' ))";
		    }
		   			 
	   		if(DicConstant.SPDLX_03.equals(claimType))
	   		{
	   			//查询服务活动零件是否存在记录
	   			QuerySet qs=this.counClaimActivityPart(claimId);
	   			if(qs.getColumnCount()>0)
	   			{
	   				String cou=qs.getString(1, "COU");
	   				if(Integer.valueOf(cou)>0)
	   				{
	   					wheres += " AND EXISTS(SELECT 1 FROM SE_BU_ACTIVITY_PART PT, SE_BU_CLAIM C WHERE C.ACTIVITY_ID = PT.ACTIVITY_ID AND C.CLAIM_ID = "+claimId+" AND PT.PART_ID =T1.PART_ID)";
	   				}
	   			}
	   		}
	   		//三包急件
	   		/*if(DicConstant.SPDLX_08.equals(claimType))
	   		{
	   			wheres += " AND EXISTS (SELECT 1  FROM SE_BU_DISPATCH_DTL D, SE_BU_CLAIM T   WHERE T.DISPATCH_ID = D.DISPATCH_ID  AND D.PART_ID = T1.PART_ID   AND T.CLAIM_ID = "+claimId+")";
	   		}*/
	   		//预授权
	   		if(DicConstant.SPDLX_08.equals(claimType))
	   		{
	   			//查询预授权零件是否存在记录
	   			QuerySet qsPre=this.counClaimPreAuthPart(claimId);
	   			if(qsPre.getColumnCount()>0)
	   			{
	   				String cou=qsPre.getString(1, "COU");
	   				if(Integer.valueOf(cou)>0)
	   				{
	   					wheres += " AND EXISTS(SELECT 1 FROM SE_BU_PRE_AUTHOR_PART PT, SE_BU_CLAIM C  WHERE C.PRE_AUTHOR_ID = PT.AUTHOR_ID AND C.CLAIM_ID = "+claimId+" AND PT.PART_ID = T1.PART_ID)";
	   				}
	   			}
	   		}
	   	}else if("2".equals(selectType))
	   	{
	   		wheres += " AND T1.PART_ID = T3.PART_ID  AND T3.SUPPLIER_ID = T2.SUPPLIER_ID AND NVL(T1.SE_CLPRICE,0)>0 AND T3.SE_IDENTIFY ="+DicConstant.YXBS_01+" AND T2.SE_IDENTIFY ="+DicConstant.YXBS_01+"\n";
	   		//如果是主损件 必须选择该故障下的配件  否则选择所有供货关系的配件信息
	   		if(DicConstant.GZLB_01.toString().equals(faultType)){
	   			wheres +=" AND T1.F_POSITION_ID = N.POSITION_ID  AND ( EXISTS (SELECT 1 FROM SE_BA_FAULT_PATTERN T7 WHERE T7.POSITION_ID = T1.F_POSITION_ID AND T7.PATTERN_ID = (SELECT F.FAULT_ID FROM SE_BU_CLAIM_FAULT F WHERE F.CLAIM_DTL_ID = "+claimDtlId+") OR N.POSITION_CODE = '10091007'))";
	   		}
	   				  
	   	}
	  	wheres+=" ORDER BY T1.PART_ID";
	    page.setFilter(wheres);
	   	BaseResultSet bs = null;
	   	StringBuffer sql= new StringBuffer();
	   	sql.append("SELECT T1.PART_ID,\n" );
	   	sql.append("       T1.PART_CODE,\n" );
	   	sql.append("       T1.PART_NAME,\n" );
	   	sql.append("       T1.BRIDGE_STATUS,\n" );
	   	sql.append("       T2.SUPPLIER_ID,\n" );
	   	sql.append("       T2.SUPPLIER_NAME,\n" );
		sql.append("       T2.SUPPLIER_CODE,\n" );
		sql.append("       T2.REAL_NO,\n" );
	   	sql.append("       T1.PART_TYPE,\n" );
	   	sql.append("       T1.SE_CLPRICE,\n" );//索赔价格
	   	sql.append("       T1.SE_REPRICE,\n" );//追偿价
	  	sql.append("       T1.IF_RETURN,\n" );
	  	sql.append("       NVL(T1.IF_WORK_MULTIPLE,0) IF_WORK_MULTIPLE,\n" );//是否工时倍数
	  	sql.append("       T1.F_POSITION_NAME POSITION_NAME,\n" );
	  	sql.append("       NVL(T1.VEHICLE_MAX,0) VEHICLE_MAX,\n" );//单车最大装车件数
	  	sql.append("       T3.IF_STREAM\n" );
	   	sql.append("  FROM PT_BA_INFO T1, PT_BA_PART_SUPPLIER_RL T3, PT_BA_SUPPLIER T2  \n" );
	   	if(DicConstant.GZLB_01.toString().equals(faultType)){
	   		sql.append(" ,SE_BA_VEHICLE_POSITION N \n");
	   	}
	   	bs = factory.select(sql.toString(), page);
	   	bs.setFieldDic("PART_TYPE","PJLB");
	   	return bs;
   }
   public QuerySet counClaimActivityPart(String claimId) throws Exception
   {
	    QuerySet qs = null;
	    StringBuffer sql= new StringBuffer();
	    sql.append("SELECT COUNT(PT.RELATION_ID) AS COU\n" );
	    sql.append("  FROM SE_BU_ACTIVITY_PART PT, SE_BU_CLAIM C\n" );
	    sql.append(" WHERE C.ACTIVITY_ID = PT.ACTIVITY_ID\n" );
	    sql.append("   AND C.CLAIM_ID = "+claimId);
	   	qs = factory.select(null, sql.toString());
	   	return qs;
   }
   /**
    * 查询预授权是否有配件
    * @param claimId
    * @return
    * @throws Exception
    */
   public QuerySet counClaimPreAuthPart(String claimId)throws Exception{
	   	QuerySet qs = null;
	    StringBuffer sql= new StringBuffer();
	    sql.append("SELECT COUNT(PT.REL_ID) AS COU\n" );
	    sql.append("  FROM SE_BU_PRE_AUTHOR_PART PT, SE_BU_CLAIM C\n" );
	    sql.append(" WHERE C.PRE_AUTHOR_ID = PT.AUTHOR_ID");
	    sql.append("   AND C.CLAIM_ID = "+claimId);
	   	qs = factory.select(null, sql.toString());
	   	return qs;
   }
   /**
    * 查询服务活动配件表是否存在数据。
    * @param claimId
    * @return
    * @throws Exception
    */
   public QuerySet queryPart(String claimId)throws Exception{
	   	QuerySet qs = null;
	    StringBuffer sql= new StringBuffer();
	    sql.append("SELECT 1 FROM SE_BU_ACTIVITY_PART P,SE_BU_CLAIM C WHERE P.ACTIVITY_ID = C.ACTIVITY_ID AND C.CLAIM_ID = "+claimId);
	   	qs = factory.select(null, sql.toString());
	   	return qs;
   }
   /**
    * 查询服务活动工时表是否存在数据。
    * @param claimId
    * @return
    * @throws Exception
    */
   public QuerySet queryWorkTimes(String claimId)throws Exception{
	   QuerySet qs = null;
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT 1 FROM SE_BU_ACTIVITY_TASK_TIME P,SE_BU_CLAIM C WHERE P.ACTIVITY_ID = C.ACTIVITY_ID AND C.CLAIM_ID = "+claimId);
	   qs = factory.select(null, sql.toString());
	   return qs;
   }
   
   /**
    * 修改索赔故障费用信息
    * @param ClaimDtlId
    * @param partCount
    * @return
    * @throws Exception
    */
   public boolean updateClaimDtl(String ClaimDtlId,String partCount)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("UPDATE SE_BU_CLAIM_FAULT F\n" );
	   sql.append("    SET F.WORK_MULTIPLE ="+partCount+",\n" );
	   sql.append("        F.WORK_COSTS   =\n" );
	   sql.append("        (F.WORK_TIME_UPRICE + F.STAR_LEVEL_UPRICE + F.ENCOURAGE_UPRICE) *\n" );
	   sql.append("        F.WORK_TIME * "+partCount+"\n" );
	   sql.append("  WHERE F.CLAIM_DTL_ID = "+ClaimDtlId+"");
	   return factory.update(sql.toString(), null);
   }
   
   public boolean insertFaultPart(SeBuClaimFaultPartVO vo)
           throws Exception
   {
   	return factory.insert(vo);
   }
   public boolean updateFaultPart(SeBuClaimFaultPartVO vo)
           throws Exception
   {
   	return factory.update(vo);
   }
   public BaseResultSet searchPartTabs(PageManager page, User user, String conditions,String claimDtlId) throws Exception
   {
	   	String wheres = conditions;
	   	wheres += "  AND T.CLAIM_DTL_ID="+claimDtlId;
	       page.setFilter(wheres);
	   	BaseResultSet bs = null;
	   	StringBuffer sql= new StringBuffer();
	   	sql.append("SELECT T.FAULT_PART_ID,\n" );
	   	sql.append("       T.CLAIM_DTL_ID,\n" );
	   	sql.append("       T.CLAIM_ID,\n" );
	   	sql.append("       T.FAULT_TYPE,\n" );
	   	sql.append("       T.MEASURES,\n" );
	   	sql.append("       T.FIRST_PART_ID,\n" );
	   	sql.append("       T.FIRST_PART_CODE,\n" );
	   	sql.append("       T.FIRST_PART_NAME,\n" );
	   	sql.append("       T.OLD_PART_ID,\n" );
	   	sql.append("       T.OLD_PART_CODE,\n" );
	   	sql.append("       T.OLD_PART_NAME,\n" );
	   	sql.append("       T.OLD_SUPPLIER_ID,\n" );
	   	sql.append("       (SELECT B.SUPPLIER_CODE\n" );
	   	sql.append("          FROM PT_BA_SUPPLIER B\n" );
	   	sql.append("         WHERE B.SUPPLIER_ID = T.OLD_SUPPLIER_ID) AS OLD_SUPPLIER_CODE,\n" );
	   	sql.append("       T.OLD_PART_COUNT,\n" );
	   	sql.append("       T.OLD_PART_TYPE,\n" );
	   	sql.append("       T.OLD_PART_STREAM,\n" );
	   	sql.append("       T.NEW_PART_ID,\n" );
	   	sql.append("       T.NEW_PART_CODE,\n" );
	   	sql.append("       T.NEW_PART_NAME,\n" );
	   	sql.append("       T.NEW_SUPPLIER_ID,\n" );
	   	sql.append("       (SELECT B.SUPPLIER_CODE\n" );
	   	sql.append("          FROM PT_BA_SUPPLIER B\n" );
	   	sql.append("         WHERE B.SUPPLIER_ID = T.NEW_SUPPLIER_ID) AS NEW_SUPPLIER_CODE,\n" );
	   	sql.append("       T.NEW_PART_FROM,\n" );
	   	sql.append("       T.NEW_PART_STREAM,\n" );
		sql.append("       T.NEW_PART_COUNT,\n" );
	   	sql.append("       T.CLAIM_UPRICE,\n" );
	   	sql.append("       T.CLAIM_COSTS,\n" );
	   	sql.append("       T.IF_RETURN,\n" );
	   	sql.append("       T.SEVERITY,\n" );
	   	sql.append("       T.REPAY_UPRICE,\n" );
	   	sql.append("	   T.BRIDGE_TYPE,\n" );
	   	sql.append("       T.BRIDGE_CODE,\n");
	   	sql.append("       T.PART_TYPE,\n" );
		sql.append("       	(SELECT BC.NAME   FROM SE_BA_CODE BC WHERE BC.CODE = T.SEVERITY) AS SEVERITYNAME,\n" );
	 	sql.append("         (SELECT NVL(I.VEHICLE_MAX,0)  FROM PT_BA_INFO I WHERE I.PART_ID=T.OLD_PART_ID) AS VEHICLE_MAX ,\n");//单车最大数量
	 	sql.append("         (SELECT NVL(I.IF_WORK_MULTIPLE,0)  FROM PT_BA_INFO I WHERE I.PART_ID=T.OLD_PART_ID) AS IF_WORK_MULTIPLE ,\n");//是否工时倍数
		sql.append("       T.FAULT_REASON,\n" );
	 	sql.append("       T.BRIDGE_SUPPLIER_NO\n" );
	   	sql.append("  FROM SE_BU_CLAIM_FAULT_PART T\n" );
	   	bs = factory.select(sql.toString(), page);
	   	bs.setFieldDic("MEASURES",DicConstant.CLFS);//处理方式   
	   	bs.setFieldDic("FAULT_TYPE",DicConstant.GZLB);//故障类别  
	   	bs.setFieldDic("NEW_PART_FROM",DicConstant.XJLY);//新件来源   
	 	bs.setFieldDic("PART_TYPE",DicConstant.PJLB);//配件类别
	 	bs.setFieldDic("BRIDGE_TYPE",DicConstant.QLX);//乔类型
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
		BaseResultSet bs = null;
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
		sql.append("       T.USER_TYPE AS USER_TYPE_ID,\n" );
	   	sql.append("       T.VEHICLE_USE,\n" );
		sql.append("       T.VEHICLE_USE AS VEHICLE_USE_ID,\n" );
	   	sql.append("       T.DRIVE_FORM,\n" );
	   	sql.append("       T.BUY_DATE,\n" );
	   	sql.append("       T.MILEAGE,\n" );
	   	sql.append("       T.GUARANTEE_NO,\n" );
	   	sql.append("       T.FACTORY_DATE,\n" );
	   	sql.append("       T.MAINTENANCE_DATE,\n" );
	   	sql.append("       T.G_COUNT,\n" );
	   	sql.append("       T.SALE_STATUS,\n" );
	   	sql.append("       T.LICENSE_PLATE,\n" );
	   	sql.append("       T.USER_NAME,\n" );
	   	sql.append("       T.USER_NO,\n" );
	   	sql.append("       T.LINK_MAN,\n" );
	   	sql.append("       T.PHONE,\n" );
	   	sql.append("       T.USER_ADDRESS,\n" );
	   	sql.append("       T.BLACKLISTFLAG,\n" );
	   	//追加首保费用查询
		sql.append("       (SELECT MAINTENANCE_COST FROM SE_BA_FMAINTENANCE_COST C WHERE C.DRIVE_FORM=T.DRIVE_FORM) AS MAINTENANCE_COST\n" );
	   	sql.append("  FROM MAIN_VEHICLE T\n" );
	   	sql.append(" WHERE (T.VIN LIKE '%"+vin+"'  OR T.VIN = '"+vin+"')\n" );
	   	sql.append("   AND T.ENGINE_NO = '"+engineNo+"'");
	   	bs=factory.select(sql.toString(), new PageManager());
	   	bs.setFieldDic("USER_TYPE","CLYHLX");
		bs.setFieldDic("VEHICLE_USE", "CLYT");
		bs.setFieldDic("DRIVE_FORM", "QDXS");
		bs.setFieldDateFormat("BUY_DATE","yyyy-MM-dd");
		bs.setFieldDateFormat("FACTORY_DATE","yyyy-MM-dd");
		bs.setFieldDateFormat("MAINTENANCE_DATE","yyyy-MM-dd");
	   	return bs;
   }
   /**
    * @title: deleteClaimPart 
  	 * @description: TODO( 删除索赔单故障与零件关系表)
	 * @param faultPartId
	 * @return void
	 * @throws Exception
	 */
   public void deleteClaimPart(String faultPartId) throws Exception
   {
	   StringBuffer sql= new StringBuffer();
	   sql.append("DELETE  SE_BU_CLAIM_FAULT_PART T WHERE  T.FAULT_PART_ID= "+faultPartId);
	   factory.update(sql.toString(), null);
   }
   /**
    * @title: searchActivity 
  	 * @description: TODO( 查询服务站已经登记的服务活动)
	 * @param page
	 * @param user
	 * @param conditions
	 * @param vehicleId
	 * @return BaseResultSet
	 * @throws Exception
	 */
   public BaseResultSet searchActivity(PageManager page, User user, String conditions,String vehicleId) throws Exception
   {
	   	String wheres = conditions;
	   	wheres += " AND A.ACTIVITY_ID = V.ACTIVITY_ID  AND A.ACTIVITY_ID = SA.ACTIVITY_ID  AND　 V.VEHICLE_ID="+vehicleId+" AND SA.ORG_ID="+user.getOrgId();
	    page.setFilter(wheres);
	   	BaseResultSet bs = null;
	   	StringBuffer sql= new StringBuffer();
	   	sql.append("SELECT A.ACTIVITY_ID,A.ACTIVITY_CODE,A.ACTIVITY_NAME,A.IF_CLAIM,A.IF_FIXCOSTS,V.VEHICLE_ID,A.MANAGE_TYPE,A.ACTIVITY_COSTS\n" );
	   	sql.append("  FROM SE_BU_ACTIVITY A, SE_BU_ACTIVITY_VEHICLE V,SE_BU_ACTIVITY_AREA SA");	
	   	bs = factory.select(sql.toString(), page);
	   	bs.setFieldDic("IF_FIXCOSTS", "SF");
	   	bs.setFieldDic("IF_CLAIM", "SF");
	   	return bs;
   }
   /**
    * @title: searchPreauth 
  	 * @description: TODO( 查询预授权)
	 * @param page
	 * @param user
	 * @param conditions
	 * @param vehicleId
	 * @return BaseResultSet
	 * @throws Exception
	 */
   public BaseResultSet searchPreauth(PageManager page, User user, String conditions,String vehicleId,String claimType) throws Exception
   {
	   	String wheres = conditions;
	   	wheres += "  AND　A.VEHICLE_ID="+vehicleId+" AND A.ORG_ID="+user.getOrgId();
	   	if(DicConstant.SPDLX_01.equals(claimType)){
	   		wheres += " AND A.AUTHOR_TYPE= "+DicConstant.YSQLX_04+" ";
	   	}
	    page.setFilter(wheres);
	   	BaseResultSet bs = null;
	   	StringBuffer sql= new StringBuffer();
	   	sql.append("SELECT A.AUTHOR_TYPE, A.AUTHOR_NO, A.VEHICLE_ID, A.VIN, A.AUTHOR_ID,A.AUTHOR_STATUS\n" );
	   	sql.append("  FROM SE_BU_PRE_AUTHOR A");	
	   	bs = factory.select(sql.toString(), page);
	   	bs.setFieldDic("AUTHOR_TYPE", "YSQLX");
	   	return bs;
   }
   /**
    * @title: searchPartOrder 
  	 * @description: TODO( 查询三包急件订单)
	 * @param page
	 * @param user
	 * @param conditions
	 * @param vehicleId
	 * @return BaseResultSet
	 * @throws Exception
	 */
   public BaseResultSet searchPartOrder(PageManager page, User user, String conditions,String vehicleId) throws Exception
   {
	   	String wheres = conditions;
	   	wheres += "  AND　D.VEHICLE_ID="+vehicleId+" AND D.ORG_ID="+user.getOrgId();
	     page.setFilter(wheres);
	   	BaseResultSet bs = null;
	   	StringBuffer sql= new StringBuffer();
	   	sql.append("SELECT D.DISPATCH_ID, D.DISPATCH_NO, D.VEHICLE_ID, D.VIN\n" );
	   	sql.append("  FROM SE_BU_DISPATCH D");
	 	bs = factory.select(sql.toString(), page);
	   	return bs;
   }
   /**
    * @title: searchDealerOut 
  	 * @description: TODO( 查询服务站外出费用)
	 * @param page
	 * @param user
	 * @param conditions
	 * @param vehicleId
	 * @return BaseResultSet
	 * @throws Exception
	 */
   public BaseResultSet searchDealerOut(PageManager page, User user, String conditions) throws Exception
   {
	   	String wheres = conditions;
	   	wheres += "  AND　C.STATUS=100201 AND C.ORG_ID="+user.getOrgId();
	     page.setFilter(wheres);
	   	BaseResultSet bs = null;
	   	StringBuffer sql= new StringBuffer();
	   	sql.append("SELECT C.ORG_CODE,\n" );
	   	sql.append("       C.ORG_NAME,\n" );
	   	sql.append("       C.ORG_ID,\n" );
	   	sql.append("       C.COSTS_TYPE,\n" );
	   	sql.append("       C.TRAVEL_TIMES,\n" );
	   	sql.append("       C.TRAVEL_DATE,\n" );
	   	sql.append("       C.VEHICLE_TYPE,\n" );
	   	sql.append("       C.START_MILES,\n" );
	   	sql.append("       C.END_MILES,C.COST\n" );
	   	sql.append("  FROM SE_BA_TRAVEL_COST C\n" );

	 	bs = factory.select(sql.toString(), page);
	   	return bs;
   }
   /**
    * @title: searchOther 
  	 * @description: TODO( 查询其他费用)
	 * @param page
	 * @param user
	 * @param conditions
	 * @param claimId
	 * @return BaseResultSet
	 * @throws Exception
	 */
   public BaseResultSet searchOther(PageManager page, User user, String conditions,String claimId) throws Exception
   {
	   	String wheres = conditions;
	    page.setFilter(wheres);
	   	BaseResultSet bs = null;
	   	StringBuffer sql= new StringBuffer();
	   	sql.append("SELECT O.COST_ID,\n" );
	   	sql.append("       O.COSTS_CODE,\n" );
	   	sql.append("       O.COSTS_NAME,\n" );
	   	sql.append("       C.CLAIM_ID,\n" );
	   	sql.append("       C.REL_ID,\n" );
	   	sql.append("       C.COSTS_AMOUNT\n" );
	   	sql.append("  FROM SE_BA_OTHER_COST O\n" );
	   	sql.append("  LEFT JOIN SE_BU_CLAIM_OTHER_COST C ON O.COSTS_CODE = C.COSTS_CODE\n" );
	   	sql.append("                                    AND C.CLAIM_ID = "+claimId+" \n");
	 	bs = factory.select(sql.toString(), page);
	   	return bs;
   }
   /**
    * @title: insertOther 
  	 * @description: TODO( 插入其他费用)
	 * @param costId
	 * @param claimId
	 * @param amount
	 * @throws Exception
	 */
   public void insertOther(String costId,String claimId,String amount) throws Exception
   {
	   StringBuffer sql= new StringBuffer();
	   sql.append("MERGE INTO SE_BU_CLAIM_OTHER_COST T\n" );
	   sql.append("USING (SELECT O.COST_ID, O.COSTS_CODE, O.COSTS_NAME, C.CLAIM_ID\n" );
	   sql.append("         FROM SE_BA_OTHER_COST O\n" );
	   sql.append("         LEFT JOIN SE_BU_CLAIM_OTHER_COST C ON O.COSTS_CODE = C.COSTS_CODE\n" );
	   sql.append("                                           AND C.CLAIM_ID = "+claimId+"\n" );
	   sql.append("        WHERE O.COST_ID = "+costId+") T1\n" );
	   sql.append("ON (T.CLAIM_ID = T1.CLAIM_ID AND T.COSTS_CODE = T1.COSTS_CODE)\n" );
	   sql.append("WHEN MATCHED THEN\n" );
	   sql.append("  UPDATE SET T.COSTS_AMOUNT = "+amount+"\n" );
	   sql.append("WHEN NOT MATCHED THEN\n" );
	   sql.append("  INSERT\n" );
	   sql.append("    (T.REL_ID, T.CLAIM_ID, T.COSTS_CODE, T.COSTS_NAME, T.COSTS_AMOUNT)\n" );
	   sql.append("  VALUES\n" );
	   sql.append("    (F_GETID(), "+claimId+", T1.COSTS_CODE, T1.COSTS_NAME, "+amount+")");

	   factory.update(sql.toString(), null);
   }
   public void deleteOutBuy(String buyId) throws Exception
   {
	   StringBuffer sql= new StringBuffer();
	   sql.append("DELETE FROM PT_BU_OUT_BUY B WHERE B.BUY_ID ="+buyId+"\n" );
	   factory.delete(sql.toString(), null);
   }
   /**
    * @title: countOther 
  	 * @description: TODO( 汇总其他费用之和)
	 * @param claimId
	 * @return BaseResultSet
	 * @throws Exception
	 */
   public QuerySet countOther(String claimId) throws Exception
   {
	    QuerySet qs = null;
	    StringBuffer sql= new StringBuffer();
	    sql.append("SELECT NVL(SUM(C.COSTS_AMOUNT),0) AS COSTS_AMOUNT\n" );
	    sql.append("  FROM SE_BU_CLAIM_OTHER_COST C\n" );
	    sql.append(" WHERE C.CLAIM_ID = "+claimId+"\n");
	   	qs = factory.select(null, sql.toString());
	   	return qs;
   }
   public boolean updateClaim(SeBuClaimVO vo) throws Exception
   {
   	return factory.update(vo);
   }
   public boolean insertClaimOut(SeBuClaimOutVO vo) throws Exception
   {
   	return factory.insert(vo);
   }
   public boolean updateClaimOut(SeBuClaimOutVO vo) throws Exception
   {
   	return factory.update(vo);
   }
   public boolean updateWorkOrder(SeBuWorkOrderVO vo) throws Exception
   {
   	return factory.update(vo);
   }
   
  /* public boolean updateClaimPreauth(String claimId,String preauthorId,User user)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("UPDATE SE_BU_CLAIM C\n" );
	   sql.append("   SET C.PRE_AUTHOR_ID = "+preauthorId+", C.UPDATE_USER = '"+user.getAccount()+"', C.UPDATE_TIME = SYSDATE\n" );
	   sql.append(" WHERE C.CLAIM_ID = "+claimId+"");
	   return factory.update(sql.toString(), null);
   }*/
   
  /* public boolean updateClaimActivity(String claimId,String activityId,User user)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("UPDATE SE_BU_CLAIM C\n" );
	   sql.append("   SET  C.ACTIVITY_ID =  "+activityId+", C.UPDATE_USER = '"+user.getAccount()+"', C.UPDATE_TIME = SYSDATE\n" );
	   sql.append(" WHERE C.CLAIM_ID = "+claimId+"");
	   return factory.update(sql.toString(), null);
   }*/
   /**
    * 附件查询
    * @param page 分页
    * @param user 用户
    * @return
    * @throws SQLException 
    */
   public BaseResultSet fileSearch(PageManager page,User user,String workId) throws SQLException{
   	//定义返回结果集
   	BaseResultSet bs = null;
   	StringBuffer sql= new StringBuffer();
   	sql.append("SELECT T.* FROM FS_FILESTORE T WHERE T.YWZJ = "+workId+" ORDER BY T.CJSJ");
   	bs=factory.select(sql.toString(), page);
   	bs.setFieldDateFormat("CJSJ","yyyy-MM-dd HH:mm:ss");
   	return bs;
   }
   /**
    * 附件查询
    * @param page 分页
    * @param user 用户
    * @return
    * @throws SQLException 
    */
   public BaseResultSet fileSearch1(PageManager page,User user,String workId) throws SQLException{
	   //定义返回结果集
	   BaseResultSet bs = null;
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.* FROM FS_FILESTORE T WHERE T.YWZJ = "+workId+" ORDER BY T.CJSJ");
	   bs=factory.select(sql.toString(), page);
	   bs.setFieldDateFormat("CJSJ","yyyy-MM-dd HH:mm:ss");
	   return bs;
   }
   
   /**
    * 判断故障下是否有配件，如果没有配件不能提报
    * @param claimId
    * @return
    * @throws Exception
    */
   public QuerySet getClaimFault(String claimId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT NVL((SELECT COUNT(1)\n" );
	   sql.append("             FROM SE_BU_CLAIM_FAULT T\n" );
	   sql.append("            WHERE T.CLAIM_ID = "+claimId+"),\n" );
	   sql.append("           0) - NVL((SELECT COUNT(1)\n" );
	   sql.append("                      FROM SE_BU_CLAIM_FAULT_PART P\n" );
	   sql.append("                     WHERE P.CLAIM_ID = "+claimId+"\n" );
	   sql.append("                       AND P.FAULT_TYPE = 301601),\n" );
	   sql.append("                    0) FAU_COUNT\n" );
	   sql.append("  FROM DUAL");
	   return factory.select(null, sql.toString());
   }
   
   /**
    * @title: queryClaimInfoById 
  	 * @description: TODO( 以索赔单Id查询索赔单信息)
	 * @param claimId
	 * @return BaseResultSet
	 * @throws Exception
	 */
   public QuerySet queryClaimInfoById(String claimId) throws Exception
   {
	    QuerySet qs = null;
	    StringBuffer sql= new StringBuffer();
	    sql.append("SELECT C.CLAIM_TYPE,\n" );
	    sql.append("       C.CLAIM_ID,\n" );
	    sql.append("       C.VEHICLE_ID,\n" );
	    sql.append("       C.VIN,\n" );
	    sql.append("       C.ACTIVITY_ID,\n" );
	    sql.append("       NVL(C.USER_TYPE,0) USER_TYPE,\n" );
	    sql.append("       NVL(C.OPERATE_USER,0) OPERATE_USER,\n" );
	    sql.append("       TO_CHAR(C.MAINTENANCE_DATE, 'YYYY-MM-DD') AS MAINTENANCE_DATE,\n" );
	    sql.append("       NVL(C.MAINTENANCE_COSTS,0) MAINTENANCE_COSTS,");
	    sql.append("       C.MILEAGE,\n" );
	    sql.append("       C.WORK_ID,\n" );
	    sql.append("       C.PRE_AUTHOR_ID,\n" );
	    sql.append("       NVL(C.APPLY_COUNT,0) APPLY_COUNT,\n" );
	    sql.append("       NVL(V.SAFECHECKTIMES, 0) SAFECHECKTIMES,\n" );
	    sql.append("       NVL(V.TRAINTIMES, 0) TRAINTIMES,\n" );
	    sql.append("       NVL(V.G_COUNT, 0) G_COUNT,\n" );
	    sql.append("       V.MAINTENANCE_DATE V_SBRQ\n" );
	    sql.append("  FROM SE_BU_CLAIM C, MAIN_VEHICLE V\n" );
	    sql.append(" WHERE C.CLAIM_ID ="+claimId+"\n" );
	    sql.append("   AND C.VEHICLE_ID = V.VEHICLE_ID");
	   	qs = factory.select(null, sql.toString());
	   	return qs;
   }
   public void updateActivityVehicle(User user,String activityId,String vehicleId) throws Exception
   {
	   StringBuffer sql= new StringBuffer();
	   sql.append("UPDATE SE_BU_ACTIVITY_VEHICLE E\n" );
	   sql.append("   SET E.CLAIM_USER = 100101, E.UPDATE_USER = '"+user.getAccount()+"', E.UPDATE_TIME = SYSDATE\n" );
	   sql.append(" WHERE E.ACTIVITY_ID = "+activityId+"\n" );
	   sql.append("   AND E.VEHICLE_ID = "+vehicleId);
	   factory.update(sql.toString(), null);

   }
   public void updatePreAuthorId(User user,String preAuthorId) throws Exception
   {
	   StringBuffer sql= new StringBuffer();
	   sql.append("UPDATE SE_BU_PRE_AUTHOR A\n" );
	   sql.append("   SET A.UPDATE_USER   = '"+user.getAccount()+"',\n" );
	   sql.append("       A.UPDATE_TIME   = SYSDATE,\n" );
	   sql.append("       A.IF_APPLYCLAIM = 100101\n" );
	   sql.append(" WHERE A.AUTHOR_ID = "+preAuthorId);
	   factory.update(sql.toString(), null);

   }
   /**
    * @title: updateVehicleMaintenanceDate 
  	 * @description: TODO( 以车辆ID更新车辆信息)
	 * @param user
	 * @param vehicleId 车辆Id
	 * @param map 车辆数据map
	 * @return BaseResultSet
	 * @throws Exception
	 */
   @SuppressWarnings("rawtypes")
   public boolean updateVehicleMaintenanceDate(User user,String vehicleId,Map map) throws Exception
   {
	   String flag="0";//标识更新 0不更新 1 更新
	   String maintenanceDate=(String)map.get("maintenanceDate");//首保日期 格式为YYYY—MM—DD
	   String gCount=(String)map.get("dbcs");//定保次数，存在则更新定保次数,++1
	   String safechecktimes=(String)map.get("aqjccs");//安全检查次数
	   String traintimes=(String)map.get("sqjccs");//售前检查培训次数TRAINTIMES
	  // String deunkm=(String)map.get("deunkm");//定保时里程
	  // String mileage=(String)map.get("mileage");//当前行驶里程
	   String fmaintainflag=(String)map.get("fmaintainflag");//是否已强保FMAINTAINFLAG
	   StringBuffer sql= new StringBuffer();
	   sql.append("UPDATE MAIN_VEHICLE V\n" );
	   sql.append("   SET V.UPDATE_USER='"+user.getAccount()+"',V.UPDATE_TIME = SYSDATE\n");
	   if(maintenanceDate!=null &&!"".equals(maintenanceDate))
	   {
		   sql.append("   ,V.MAINTENANCE_DATE = TO_DATE('"+maintenanceDate+"', 'YYYY-MM-DD')\n");
		   flag="1";
	   }
	   if(fmaintainflag!=null &&!"".equals(fmaintainflag))
	   {
		   sql.append("   ,V.FMAINTAINFLAG='100101'\n");
		   flag="1";
	   }
	   if(gCount!=null &&!"".equals(gCount))
	   {
		   sql.append("   ,V.G_COUNT = NVL(G_COUNT,0)+1 \n");
		   flag="1";
	   }
	   if(safechecktimes!=null &&!"".equals(safechecktimes))
	   {
		   sql.append("   ,V.SAFECHECKTIMES = NVL(SAFECHECKTIMES,0)+1 \n");
		   flag="1";
	   }
	   if(traintimes!=null &&!"".equals(traintimes))
	   {
		   sql.append("   ,V.TRAINTIMES = NVL(SAFECHECKTIMES,0)+1 \n");
		   flag="1";
	   }
	 /*  if(deunkm!=null &&!"".equals(deunkm))
	   {
		   sql.append("   ,V.DRUNKM = '"+deunkm+"' \n");
	   }
	   if(mileage!=null &&!"".equals(mileage))
	   {
		   sql.append("   ,V.LRUNKM=V.MILEAGE, V.MILEAGE='"+mileage+"' \n");
	   }*/
	   sql.append(" WHERE VEHICLE_ID = "+vehicleId);
	   
	   if("1".equals(flag)){
		   return  factory.update(sql.toString(), null);
	   }else{
		   return true;
	   }
	  
   }
   
   /**
    * 更新故障配件的祸首件信息
    * @param claimId
    * @return
    * @throws Exception
    */
   public boolean updateFaultFirstPart(String claimId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("UPDATE SE_BU_CLAIM_FAULT_PART P\n" );
	   sql.append("   SET P.FIRST_PART_ID  =\n" );
	   sql.append("       (SELECT PP.OLD_PART_ID\n" );
	   sql.append("          FROM SE_BU_CLAIM_FAULT_PART PP\n" );
	   sql.append("         WHERE PP.CLAIM_ID = "+claimId+"\n" );
	   sql.append("           AND PP.FAULT_TYPE = "+DicConstant.GZLB_01+"\n" );
	   sql.append("           AND PP.CLAIM_DTL_ID = P.CLAIM_DTL_ID),\n" );
	   sql.append("       P.FIRST_PART_CODE =\n" );
	   sql.append("       (SELECT PP.OLD_PART_CODE\n" );
	   sql.append("          FROM SE_BU_CLAIM_FAULT_PART PP\n" );
	   sql.append("         WHERE PP.CLAIM_ID ="+claimId+"\n" );
	   sql.append("           AND PP.FAULT_TYPE = "+DicConstant.GZLB_01+"\n" );
	   sql.append("           AND PP.CLAIM_DTL_ID = P.CLAIM_DTL_ID),\n" );
	   sql.append("       P.FIRST_PART_NAME =\n" );
	   sql.append("       (SELECT PP.OLD_PART_NAME\n" );
	   sql.append("          FROM SE_BU_CLAIM_FAULT_PART PP\n" );
	   sql.append("         WHERE PP.CLAIM_ID = "+claimId+"\n" );
	   sql.append("           AND PP.FAULT_TYPE = "+DicConstant.GZLB_01+"\n" );
	   sql.append("           AND PP.CLAIM_DTL_ID = P.CLAIM_DTL_ID) \n" );
	   sql.append(" WHERE P.CLAIM_ID = "+claimId+"\n" );
	   sql.append("   AND P.FAULT_TYPE = "+DicConstant.GZLB_02+"");
	   return factory.update(sql.toString(), null);
   }
   /**
    * @title: serchClaimOut 
  	 * @description: TODO( 查询索赔单外出)
	 * @param page
	 * @param user
	 * @param conditions
	 * @param claimId
	 * @return BaseResultSet
	 * @throws Exception
	 */
   public BaseResultSet searchClaimOut(PageManager page, User user, String conditions,String claimId) throws Exception
   {
	   	String wheres = conditions;
		wheres += "  AND　T.CLAIM_ID="+claimId;
	    page.setFilter(wheres);
	   	BaseResultSet bs = null;
	   	StringBuffer sql= new StringBuffer();
	   	sql.append("SELECT T.OUT_ID,\n" );
	   	sql.append("       T.CLAIM_ID,\n" );
	   	sql.append("       T.OUT_TIMES,\n" );
	   	sql.append("       T.COST_AMOUNT,\n" );
	   	sql.append("       T.OUT_COSTS,\n" );
	   	sql.append("       T.SEVEH_COSTS,\n" );
	   	sql.append("       T.MEALS_COSTS,\n" );
	   	sql.append("       T.VEHBOAT_COSTS,\n" );
	   	sql.append("       T.TRAVEL_COSTS,\n" );
	   	sql.append("       T.OTHER_COSTS,\n" );
	   	sql.append("       T.OUT_TYPE,\n" );
	   	sql.append("       T.OUT_UCOUNT,\n" );
	   	sql.append("       T.OUT_USER,\n" );
	   	sql.append("       T.GO_DATE,\n" );
	   	sql.append("       T.ARRIVE_DATE,\n" );
	   	sql.append("       T.LEAVE_DATE,\n" );
	   	sql.append("       T.MILEAGE,\n" );
	   	sql.append("       T.VEHICLE_NO,\n" );
	   	sql.append("       T.ON_WAY_DAYS,\n" );
	   	sql.append("       T.OUTDATE_TYPE,\n" );
	   	sql.append("       T.OUTDATE_TYPE OUTDATE_TYPE_NAME,\n" );
	   	sql.append("       T.REMARKS,\n" );
	   	sql.append("       T.TRAVEL_DAYS,\n" );
	   	sql.append("       T.IS_OUT_TIMES,\n" );
	   	sql.append("       T.SEC_VEH_COSTS\n" );
	   	sql.append("  FROM SE_BU_CLAIM_OUT T");
	 	bs = factory.select(sql.toString(), page);
	 	bs.setFieldDic("OUT_TYPE","WCFS");
	 	bs.setFieldDic("IS_OUT_TIMES","SF");
	 	bs.setFieldDateFormat("GO_DATE", "yyyy-MM-dd HH:mm");
		bs.setFieldDateFormat("ARRIVE_DATE", "yyyy-MM-dd HH:mm");
		bs.setFieldDateFormat("LEAVE_DATE", "yyyy-MM-dd HH:mm");
		bs.setFieldDic("OUTDATE_TYPE_NAME","WCSJ");
	   	return bs;
   }
   
   
   /**
    * 获取GPS 返回的有效里程 来回的距离
    * @param claimId
    * @return
    * @throws Exception
    */
   public BaseResultSet searchClaimOutMileage(String workNo,PageManager page)throws Exception{
	   BaseResultSet bs=null;
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT NVL(I.EFFECTIVE_MILEAGE, 0)*2 MILEAGE\n" );
	   sql.append("  FROM SE_BU_WORKGPS_IMP I\n" );
	   sql.append(" WHERE I.OPERATE_STATUS = 2\n" );
	   sql.append("   AND I.WORK_NO = '"+workNo+"'");
	   bs = factory.select(sql.toString(), page);
	   return bs;
   }
   /**
    * 索赔单提报查询
    * @param page
    * @param user
    * @param condition
    * @return
    * @throws Exception
    */
   public BaseResultSet searchClaimApply(PageManager page,User user ,String conditions)throws Exception{
	   String wheres = conditions;
   	   String orgId=user.getOrgId();
   	   		  wheres += " AND T.WORK_ID = C.WORK_ID \n"+  
   	   				  	" AND C.ORG_ID = "+orgId+" \n"
   	   				  	+"AND T.STATUS = 100201 "+
   	   				  	" AND C.STATUS ="+DicConstant.YXBS_01+" \n"+
   	   				  	" AND C.CLAIM_STATUS IN ("+DicConstant.SPDZT_04+","+DicConstant.SPDZT_06+") \n"+
   	   					" ORDER BY  C.CLAIM_NO ";
       page.setFilter(wheres);
	   BaseResultSet bs=null;
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.WORK_ID,\n" );
	   sql.append("       T.WORK_NO,\n" );
	   sql.append("       C.REPAIR_USER, --维修人、检修人\n" );
	   sql.append("       T.IF_OUT,\n" );
	   sql.append("       C.APPLY_USER, --报修人\n" );
	   sql.append("       C.APPLY_MOBIL, --报修人电话\n" );
	   sql.append("       C.ORG_ID, --服务店\n" );
	   sql.append("       C.CLAIM_ID,\n" );
	   sql.append("       C.CLAIM_TYPE,\n" );
	   sql.append("       C.SE_TYPE,\n" );
	   sql.append("       C.CLAIM_NO,\n" );
	   sql.append("       C.CLAIM_STATUS,\n" );
	   sql.append("       C.VEHICLE_ID,\n" );
	   sql.append("       C.VIN,\n" );
	   sql.append("       C.PRE_AUTHOR_ID,\n" );
	   sql.append("       C.APPLY_USER_TYPE,\n" );
	   sql.append("       C.APPLY_ADDRES,\n" );
	   sql.append("       C.FAULT_ID,\n" );
	   sql.append("       C.FAULT_CODE,\n" );
	   sql.append("       C.FAULT_FROM FAULT_FROM_CODE,\n" );
	   sql.append("       (SELECT S.NAME FROM SE_BA_CODE S WHERE S.CODE = C.FAULT_FROM AND S.CODE_TYPE = 302701 AND S.STATUS="+DicConstant.YXBS_01+" ) FAULT_FROM,\n" );
	   sql.append("       C.FAULT_ADDRESS,\n" );
	   sql.append("       C.FAULT_DATE,\n" );
	   sql.append("       C.FAULT_ANLYSIE FAULT_ANLYSIE_CODE,\n" );
	   sql.append("       (SELECT S.NAME FROM SE_BA_CODE S WHERE S.CODE = C.FAULT_ANLYSIE AND S.CODE_TYPE = 302702 AND S.STATUS="+DicConstant.YXBS_01+" ) FAULT_ANLYSIE,\n" );
	   sql.append("       C.ACTIVITY_ID,\n" );
	   sql.append("       C.USER_TYPE,\n" );
	   sql.append("       C.USER_TYPE AS USER_TYPE_NAME,\n" );
	   sql.append("       C.VEHICLE_USE,\n" );
	   sql.append("       C.VEHICLE_USE AS VEHICLE_NAME,\n" );
	   sql.append("       C.BUY_DATE,\n" );
	   sql.append("       C.IF_OVERDUE,\n" );
	   sql.append("       C.OVERDUE_DAYS,\n" );
	   sql.append("       C.MILEAGE,\n" );
	   sql.append("       C.GUARANTEE_NO,\n" );
	   sql.append("       C.APPLY_REPAIR_DATE,\n" );
	   sql.append("       C.DISPATCH_ID,\n" );
	   sql.append("       C.TRAIN_COSTS,\n" );
	   sql.append("       C.SAFE_COSTS,\n" );
	   sql.append("       C.MAINTENANCE_COSTS,\n" );
	   sql.append("       C.MAINTENANCE_DATE,\n" );
	   sql.append("       C.LICENSE_PLATE,\n" );
	   sql.append("       C.USER_NAME,\n" );
	   sql.append("       C.USER_NO,\n" );
	   sql.append("       C.LINK_MAN,\n" );
	   sql.append("       C.PHONE,\n" );
	   sql.append("       C.PROVINCE_CODE,\n" );
	   sql.append("       C.CITY_CODE,\n" );
	   sql.append("       C.COUNTY_CODE,\n" );
	   sql.append("       C.USER_ADDRESS,\n" );
	   sql.append("       C.REMARKS,\n" );
	   sql.append("       C.IF_RECOVERY,\n" );
	   sql.append("       C.IF_FIXED,\n" );
	   sql.append("       C.SE_METHOD,\n" );
	   sql.append("       C.SERVICE_COST,\n" );
	   sql.append("       C.ENGINE_NO,\n" );
	   sql.append("       C.REPAIR_ADDRESS,\n" );
	   sql.append("       C.REPAIR_DATE,\n" );
	   sql.append("       C.APPLY_COUNT,\n" );
	   sql.append("       C.REJECT_DATE,\n" );
	   sql.append("       C.APPLY_DATE,\n" );
	   sql.append("       C.G_TIMES,\n" );
	   sql.append("       (SELECT MODELS_CODE\n" );
	   sql.append("          FROM MAIN_VEHICLE V\n" );
	   sql.append("         WHERE V.VEHICLE_ID = C.VEHICLE_ID) MODEL_CODE,\n" );
	   sql.append("       (SELECT DRIVE_FORM\n" );
	   sql.append("          FROM MAIN_VEHICLE V\n" );
	   sql.append("         WHERE V.VEHICLE_ID = C.VEHICLE_ID) DRIVE_FORM,\n" );
	   sql.append("       (SELECT ENGINE_TYPE\n" );
	   sql.append("          FROM MAIN_VEHICLE V\n" );
	   sql.append("         WHERE V.VEHICLE_ID = C.VEHICLE_ID) ENGINE_TYPE,\n" );
	   sql.append("       (SELECT Y.ACTIVITY_CODE\n" );
	   sql.append("          FROM SE_BU_ACTIVITY Y\n" );
	   sql.append("         WHERE Y.ACTIVITY_ID = C.ACTIVITY_ID) ACTIVITY_CODE,\n" );
	   sql.append("       (SELECT P.AUTHOR_NO\n" );
	   sql.append("          FROM SE_BU_PRE_AUTHOR P\n" );
	   sql.append("         WHERE P.AUTHOR_ID = C.PRE_AUTHOR_ID) AUTHOR_NO,\n" );
	   sql.append("       (SELECT D.DISPATCH_NO\n" );
	   sql.append("          FROM SE_BU_DISPATCH D\n" );
	   sql.append("         WHERE D.DISPATCH_ID = C.DISPATCH_ID) DISPATCH_NO,\n" );
	   sql.append("  NVL((SELECT M.OVERDUE_DAYS FROM MAIN_DEALER M WHERE M.ORG_ID=C.ORG_ID AND ROWNUM=1 ),0) DEAR_OVER_DAYS\n");
	   sql.append("  FROM SE_BU_WORK_ORDER T ,SE_BU_CLAIM C");
	   bs=factory.select(sql.toString(), page);
	   bs.setFieldDic("IF_OUT", "SF");
	   bs.setFieldDic("CLAIM_STATUS", "SPDZT");
	   bs.setFieldDic("SE_TYPE", "FWLX");
	   bs.setFieldDic("CLAIM_TYPE", "SPDLX");
	   bs.setFieldDic("USER_TYPE", "YHLX");
	   bs.setFieldDic("APPLY_USER_TYPE", "BXRLX");
	   bs.setFieldDic("USER_TYPE_NAME", "CLYHLX");
	   bs.setFieldDic("VEHICLE_NAME", "CLYT");
	   bs.setFieldDic("DRIVE_FORM", "QDXS");
	   bs.setFieldOrgDeptSimpleName("ORG_ID");
	   bs.setFieldDateFormat("REPAIR_DATE", "yyyy-MM-dd HH:mm");
	   bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm");
	   bs.setFieldDateFormat("APPLY_REPAIR_DATE", "yyyy-MM-dd HH:mm");
	   bs.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd HH:mm");
	   bs.setFieldDateFormat("REJECT_DATE", "yyyy-MM-dd HH:mm");
	   bs.setFieldDateFormat("MAINTENANCE_DATE", "yyyy-MM-dd");
	   bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
	   bs.setFieldSimpleXZQH("PROVINCE_CODE");
	   bs.setFieldSimpleXZQH("CITY_CODE");
	   bs.setFieldSimpleXZQH("COUNTY_CODE");
	   return bs;
   }
   
   /**
    * 审核轨迹查询
    * @param page
    * @param condition
    * @param user
    * @return
    * @throws Exception
    */
   public BaseResultSet hisCheckSearch(PageManager page,String claimId)throws Exception{
	   BaseResultSet bs = null;
   	   StringBuffer sql= new StringBuffer();
   	   sql.append("SELECT C.CHECK_USER, C.CHECK_DATE, C.CHECK_RESULT, C.CHECK_REMARKS\n" );
   	   sql.append("  FROM SE_BU_CLAIM_CHECK C, SE_BU_CLAIM T\n" );
	   sql.append(" WHERE C.CLAIM_ID = T.CLAIM_ID\n" );
	   sql.append(" AND T.CLAIM_ID="+claimId+"\n" );
	   sql.append(" ORDER BY C.CHECK_DATE ");
	   bs=factory.select(sql.toString(), page);
	   bs.setFieldDic("CHECK_RESULT","SPDZT");
	   bs.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd HH:mm:ss");
	   return bs;
   }
   /**
    * 更新服务活动为未使用
    * @param activityId
    * @param user
    * @return
    * @throws Exception
    */
   public boolean updateActivity(String activityId,User user,String vehicleId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("UPDATE SE_BU_ACTIVITY_VEHICLE E\n" );
	   sql.append("   SET E.CLAIM_USER = "+DicConstant.SF_02+", E.UPDATE_USER = '"+user.getAccount()+"', E.UPDATE_TIME = SYSDATE\n" );
	   sql.append(" WHERE E.ACTIVITY_ID = "+activityId+"\n" );
	   sql.append("   AND E.VEHICLE_ID = "+vehicleId);
	   return factory.update(sql.toString(), null);
   }
   /**
    * 更新预授权为未使用
    * @param activityId
    * @param user
    * @return
    * @throws Exception
    */
   public boolean updatePreAuthor(String preAuthorId,User user)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("UPDATE SE_BU_PRE_AUTHOR T\n" );
	   sql.append("   SET T.IF_APPLYCLAIM = "+DicConstant.SF_02+", T.UPDATE_USER = '"+user.getAccount()+"', T.UPDATE_TIME = SYSDATE\n" );
	   sql.append(" WHERE T.AUTHOR_ID = "+preAuthorId+"");
	   return factory.update(sql.toString(), null);
   }
   /**
    * 更新车辆表售前、安全、定保 次数
    * @param user
    * @param vehicleId
    * @param map
    * @return
    * @throws Exception
    */
   @SuppressWarnings("rawtypes")
   public boolean updateVehicleCount(User user,String vehicleId,Map map)throws Exception{
	   String flag ="0";
	   String sbxx=(String)map.get("sbxx");//首保信息
	   String gCount=(String)map.get("dbcs");//定保次数，-1
	   String safechecktimes=(String)map.get("aqjccs");//安全检查次数 0
	   String traintimes=(String)map.get("sqjccs");//售前检查培训次数0
	   StringBuffer sql= new StringBuffer();
	   sql.append("UPDATE MAIN_VEHICLE V\n" );
	   sql.append("   SET V.UPDATE_USER='"+user.getAccount()+"',V.UPDATE_TIME = SYSDATE\n");
	   if(sbxx !=null && !"".equals(sbxx)){
		   sql.append("   ,V.MAINTENANCE_DATE = '' \n");
		   sql.append("   ,V.FMAINTAINFLAG = 100102 \n");//强保标识
		   flag="1";
	   }
	   if(gCount!=null &&!"".equals(gCount))
	   {
		   sql.append("   ,V.G_COUNT = V.G_COUNT - 1 \n");
		   flag="1";
	   }
	   if(safechecktimes!=null &&!"".equals(safechecktimes))
	   {
		   sql.append("   ,V.SAFECHECKTIMES = V.SAFECHECKTIMES - 1 \n");
		   flag="1";
	   }
	   if(traintimes!=null &&!"".equals(traintimes))
	   {
		   sql.append("   ,V.TRAINTIMES = V.TRAINTIMES - 1 \n");
		   flag="1";
	   }
	   sql.append(" WHERE VEHICLE_ID = "+vehicleId);
	   if("1".equals(flag)){
		   return factory.update(sql.toString(), null);
	   }else{
		   return true;
	   }
	   
   }
   
   /**
    * 删除外出费用
    * @param claimId 索赔单ID
    * @return
    * @throws Exception
    */
   public boolean deleteOut(String claimId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("DELETE FROM SE_BU_CLAIM_OUT T WHERE T.CLAIM_ID = "+claimId+"");
	   return factory.update(sql.toString(), null);
   }
   /**
    * 删除其它费用
    * @param claimId 索赔单ID
    * @return
    * @throws Exception
    */
   public boolean deleteotherCost(String claimId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("DELETE FROM SE_BU_CLAIM_OTHER_COST T WHERE T.CLAIM_ID ="+claimId+"");
	   return factory.update(sql.toString(), null);
   }
   /**
    * 删除故障配件
    * @param claimId 索赔单ID
    * @return
    * @throws Exception
    */
   public boolean deletePart(String claimId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("DELETE FROM SE_BU_CLAIM_FAULT_PART T WHERE T.CLAIM_ID = "+claimId+"");
	   return factory.update(sql.toString(), null);
   }
   /**
    * 删除故障
    * @param claimId 索赔单ID
    * @return
    * @throws Exception
    */
   public boolean deleteFault(String claimId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("DELETE FROM SE_BU_CLAIM_FAULT T WHERE T.CLAIM_ID = "+claimId+"");
	   return factory.update(sql.toString(), null);
   }
   /**
    * 删除索赔单 放弃提报
    * @param claimId 索赔单ID
    * @return
    * @throws Exception
    */
   public boolean deleteClaim(String claimId,User user)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("UPDATE SE_BU_CLAIM T SET T.CLAIM_STATUS ="+DicConstant.SPDZT_08+" ,T.STATUS="+DicConstant.YXBS_02+",T.UPDATE_TIME=SYSDATE,T.UPDATE_USER='"+user.getAccount()+"' WHERE T.CLAIM_ID = "+claimId+"");
	   return factory.update(sql.toString(), null);
   }
   
   /**
    * 工单放弃派工
    * @param workId
    * @param user
    * @return
    * @throws Exception
    */
   public boolean updateWork(String workId,User user)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("UPDATE SE_BU_WORK_ORDER T SET T.WORK_STATUS ="+DicConstant.PGDZT_05+" ,T.UPDATE_TIME=SYSDATE,T.UPDATE_USER='"+user.getAccount()+"' WHERE T.WORK_ID = "+workId+"");
	   return factory.update(sql.toString(), null);
   }
   
   /**
    * 获取索赔单信息
    * @param claimId
    * @return
    * @throws Exception
    */
   public QuerySet getClaimInfo(String claimId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.CLAIM_NO,\n" );
	   sql.append("       T.CLAIM_STATUS,\n" );
	   sql.append("       T.SE_TYPE,\n" );
	   sql.append("       T.WORK_ID,\n" );
	   sql.append("       T.APPLY_COUNT,\n" );
	   sql.append("       TO_CHAR(T.REJECT_DATE, 'YYYY-MM-DD HH24:Mi') REJECT_DATE,\n" );
	   sql.append("       TO_CHAR(T.APPLY_DATE, 'YYYY-MM-DD HH24:Mi') APPLY_DATE\n" );
	   sql.append("  FROM SE_BU_CLAIM T \n");
	   sql.append(" WHERE T.CLAIM_ID = "+claimId+"");
	   return factory.select(null, sql.toString());
   }
   
   /**
    * 判断索赔单是否有外出信息 如果有 修改 否则新增
    * @param claimId
    * @return
    * @throws Exception
    */
   public QuerySet getClaimOutInfo(String claimId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.OUT_ID FROM SE_BU_CLAIM_OUT T WHERE T.CLAIM_ID = "+claimId+"");
	   return factory.select(null, sql.toString());
   }
   /**
    * 获取外出GPS新消息
    * @param claimId
    * @return
    * @throws Exception
    */
   public QuerySet getClaimOutGps(String claimId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT I.PHONE_LONGITUDE,\n" );
	   sql.append("       I.PHONE_LATITUDE,\n" );
	   sql.append("       I.OPERATE_STATUS,\n" );
	   sql.append("       I.VEHICLE_LONGITUDE,\n" );
	   sql.append("       I.VEHICLE_LATITUDE,\n" );
	   sql.append("       I.VEHICLE_PHONE_MILEAGE,\n" );
	   sql.append("       I.PHONE_LOCATION,\n" );
	   sql.append("       I.VEHICLE_LOCATION,\n" );
	   sql.append("       NVL(I.EFFECTIVE_MILEAGE,0)*2 EFFECTIVE_MILEAGE\n" );//有效里程是来回的距离
	   sql.append("  FROM SE_BU_WORKGPS_IMP I, SE_BU_WORK_ORDER O, SE_BU_CLAIM C\n" );
	   sql.append(" WHERE I.WORK_NO = O.WORK_NO\n" );
	   sql.append("   AND O.WORK_ID = C.WORK_ID\n" );
	   sql.append("   AND C.CLAIM_ID ="+claimId+"\n");
	   sql.append(" ORDER BY NVL(I.EFFECTIVE_MILEAGE,0) ASC");
	   return factory.select(null, sql.toString());
   }
   
   /**
    * 获取首保费用的各个费用
    * @return
    * @throws Exception
    */
   public QuerySet getMianPara()throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT NVL(T.ENGINE, 0) ENGINE, NVL(T.GEARBOX, 0) GEARBOX\n" );
	   sql.append("  FROM SE_BA_FMAINTENANCE_PARA T");
	   return factory.select(null, sql.toString());
   }
   
   /**
    * 获取渠道商是否需要人工审核
    * @return
    * @throws Exception
    */
   public QuerySet getDealerCheck(User user)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT NVL(T.IF_AUTO_CHECK, 0) IF_AUTO_CHECK\n" );
	   sql.append("  FROM MAIN_DEALER T\n" );
	   sql.append(" WHERE T.ORG_ID = "+user.getOrgId()+"");
	   return factory.select(null, sql.toString());
   }
   
   /**
    * 获取当前序号、有效最大序号 (民车的审核人)
    * @return
    * @throws Exception
    */
   public QuerySet getUserNow()throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT (SELECT MAX(TO_NUMBER(T.SEQ_NO)) SEQ_NO_MAX\n" );
	   sql.append("          FROM SE_BA_CHECK_USER T\n" );
	   sql.append("         WHERE T.IF_DISTRIB = 100101 AND T.USER_TYPE = "+DicConstant.CLYHLX_01+" ) SEQ_NO_MAX,\n" );
	   sql.append("       TO_NUMBER(N.SEQ_NO) SEQ_NO_CUR\n" );
	   sql.append("  FROM SE_BA_CHECK_USER_NOW N");
	   return factory.select(null, sql.toString());
   }
   /**
    * 获取当前序号、有效最大序号 (军车的审核人)
    * @return
    * @throws Exception
    */
   public QuerySet getUserMilitaryNow()throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT (SELECT MAX(TO_NUMBER(T.SEQ_NO)) SEQ_NO_MAX\n" );
	   sql.append("          FROM SE_BA_CHECK_USER T\n" );
	   sql.append("         WHERE T.IF_DISTRIB = 100101 AND T.USER_TYPE = "+DicConstant.CLYHLX_02+" ) SEQ_NO_MAX,\n" );
	   sql.append("       TO_NUMBER(N.SEQ_NO) SEQ_NO_CUR\n" );
	   sql.append("  FROM SE_BA_CHECK_MILITARY_NOW N");
	   return factory.select(null, sql.toString());
   }
   
   /**
    * 获取绑定审核人序号和帐号
    * @return
    * @throws Exception
    */
   public QuerySet getUserAccount(int paras,String userType)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT U.SEQ_NO, U.USER_ACCOUNT\n" );
	   sql.append("  FROM SE_BA_CHECK_USER U\n" );
	   sql.append(" WHERE TO_NUMBER(U.SEQ_NO) > "+paras+"\n" );
	  // sql.append("   AND U.IF_DISTRIB = 100101   \n" );
	   sql.append("   AND U.IF_DISTRIB = 100101 AND U.USER_TYPE = "+userType+" \n" );
	   sql.append(" ORDER BY TO_NUMBER(U.SEQ_NO)");
	   return factory.select(null, sql.toString());
   }
   
   /**
    * 更新当前审核人序号
    * @param num
    * @return
    * @throws Exception
    */
   public boolean updateNo(String num)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("UPDATE SE_BA_CHECK_USER_NOW SET SEQ_NO="+num+"");
	   return factory.update(sql.toString(), null);
   }
   
   /**
    * 计算材料总费用
    * @param claimId
    * @return
    * @throws Exception
    */
   public QuerySet getMatericlCosts(String claimId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append(" SELECT NVL(SUM(NVL(T.NEW_PART_COUNT, 0) * NVL(T.CLAIM_UPRICE, 0)), 0) CLAIM_COST \n" );
	   sql.append("  FROM SE_BU_CLAIM_FAULT_PART T\n" );
	   sql.append(" WHERE T.CLAIM_ID = "+claimId+"");
	   return factory.select(null, sql.toString());
   }
   /**
    * 查询索赔单中旧件ID，与供应商ID
    * @return
    * @throws Exception
    */
   public QuerySet queryParts(String claimId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.NEW_PART_ID,T.NEW_PART_CODE,T.NEW_PART_NAME,T.NEW_SUPPLIER_ID,T.NEW_PART_COUNT FROM SE_BU_CLAIM_FAULT_PART T WHERE T.CLAIM_ID ="+claimId+"\n" );
	   return factory.select(null, sql.toString());
   }
   /**
    * 查询索赔单中旧件ID，与供应商ID,服务站对应的可用库存
    * @return
    * @throws Exception
    */
   public QuerySet queryStock(String oPartId,  User user)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT NVL(T.AVAILABLE_AMOUNT,0) AVAILABLE_AMOUNT, NVL(T.OCCUPY_AMOUNT,0) OCCUPY_AMOUNT, T.STOCK_ID\n" );
	   sql.append("  FROM PT_BU_DEALER_STOCK T\n" );
	   sql.append(" WHERE T.PART_ID ="+oPartId+"\n" );
	   sql.append("   AND T.ORG_ID ="+user.getOrgId()+"\n" );
	   sql.append("   AND T.STATUS = "+DicConstant.YXBS_01+"");
	   return factory.select(null, sql.toString());
   }

   /**
    * 查询用户信息
    * @param claimId
    * @return
    * @throws Exception
    */
   public QuerySet queryUsersInfos(String claimId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.USER_NAME, T.USER_ADDRESS, T.PHONE,T.CLAIM_TYPE\n" );
	   sql.append("  FROM SE_BU_CLAIM T\n" );
	   sql.append(" WHERE T.CLAIM_ID = "+claimId+"");
	   return factory.select(null, sql.toString());
   }
   /**
    * 查询可用库存是否大于0，如果有可用库存。则生成实销单主信息。
    * @param claimId
    * @return
    * @throws Exception
    */
   public QuerySet queryIfStock(String claimId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT 1\n" );
	   sql.append("  FROM SE_BU_CLAIM C, SE_BU_CLAIM_FAULT_PART P\n" );
	   sql.append(" WHERE C.CLAIM_ID = P.CLAIM_ID\n" );
	   sql.append("   AND C.CLAIM_ID ="+claimId+"\n" );
	   sql.append("   AND EXISTS\n" );
	   sql.append(" (SELECT 1 FROM PT_BU_DEALER_STOCK S\n" );
	   sql.append("         WHERE S.PART_ID = P.NEW_PART_ID\n" );
	   //sql.append("           AND S.SUPPLIER_ID = P.NEW_SUPPLIER_ID\n" );
	   sql.append("           AND C.ORG_ID = S.ORG_ID\n" );
	   sql.append("           AND S.AVAILABLE_AMOUNT >0)");
	   return factory.select(null, sql.toString());
   }
   /**
    * 查询配件对应的 单位，零售价
    * @param claimId
    * @return
    * @throws Exception
    */
   public QuerySet queryPartInfos(String oPartId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append(" SELECT T.UNIT,NVL(T.RETAIL_PRICE,0) RETAIL_PRICE FROM PT_BA_INFO T WHERE T.PART_ID="+oPartId+"");
	   return factory.select(null, sql.toString());
   }
   /**
    * 查询配件供应商 代码，名称
    * @param claimId
    * @return
    * @throws Exception
    */
   public QuerySet querySupplier(String nSupplierId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.SUPPLIER_CODE,T.SUPPLIER_NAME FROM PT_BA_SUPPLIER T WHERE T.SUPPLIER_ID ="+nSupplierId+"");
	   return factory.select(null, sql.toString());
   }
   /**
    * 判断是否油品
    * @param claimId
    * @return
    * @throws Exception
    */
   public QuerySet queryIfOil(String nPartId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT 1 FROM PT_BA_INFO T WHERE T.IF_OIL = 100101 AND T.PART_ID = "+nPartId+"");
	   return factory.select(null, sql.toString());
   }
   /**
    * 查询实销明细表实时实销数量与实销金额。
    * @param claimId
    * @return
    * @throws Exception
    */
   public QuerySet queryCount(String saleId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT NVL(R.SALE_COUNT,0) SALE_COUNT,NVL(R.SALE_AMOUNT,0) SALE_AMOUNT FROM PT_BU_REAL_SALE R WHERE R.SALE_ID = "+saleId+"");
	   return factory.select(null, sql.toString());
   }
   /**
    * 查询外销明细表实时实销数量与实销金额。
    * @param claimId
    * @return
    * @throws Exception
    */
   public QuerySet queryOutCount(String buyId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT NVL(R.BUY_COUNT,0)BUY_COUNT,NVL(R.BUY_AMOUNT,0) BUY_AMOUNT FROM PT_BU_OUT_BUY R WHERE R.BUY_ID = "+buyId+"");
	   return factory.select(null, sql.toString());
   }
   /**
    * 新增实销单
    *
    * @param vo
    * @return
    * @throws Exception
    */
   public boolean insertRealSale(PtBuRealSaleVO vo) throws Exception {
	   String saleId=SequenceUtil.getCommonSerivalNumber(factory);//按序列取出一个ID
		vo.setSaleId(saleId);
       return factory.insert(vo);
   }
   /**
    * 修改实销单
    *
    * @param vo
    * @return
    * @throws Exception
    */
   public boolean updateSale(PtBuRealSaleVO vo) throws Exception {
	   return factory.update(vo);
   }
   /**
    * 新增外采单
    *
    * @param vo
    * @return
    * @throws Exception
    */
   public boolean insertBuyOrder(PtBuOutBuyVO vo) throws Exception {
	   String buyId=SequenceUtil.getCommonSerivalNumber(factory);//按序列取出一个ID
	   vo.setBuyId(buyId);
	   return factory.insert(vo);
   }
   /**
    * 新增外采单
    *
    * @param vo
    * @return
    * @throws Exception
    */
   public boolean updateBuy(PtBuOutBuyVO vo) throws Exception {
	   return factory.update(vo);
   }
   /**
    * 新增外采单明细
    *
    * @param vo
    * @return
    * @throws Exception
    */
   public boolean insertBuyOrderDtl(PtBuOutBuyDtlVO vo) throws Exception {
	   return factory.insert(vo);
   }
   /**
    * 库存锁定数量
    */
   public boolean DealerStockUpdate(PtBuDealerStockVO vo) throws Exception {

       return factory.update(vo);
   }
   /**
    * 实销详单新增
    */
   public boolean realSaleDtlInsert(PtBuRealSaleDtlVO vo) throws Exception {

       return factory.insert(vo);
   }
   public BaseResultSet queryDtl(PageManager page, User user,String workId) throws Exception
   {
   	BaseResultSet bs = null;
   	StringBuffer sql= new StringBuffer();
   	sql.append("SELECT T.WORK_ID,\n" );
   	sql.append("       T.WORK_NO,\n" );
   	sql.append("       T.REPAIR_USER,\n" );
   	sql.append("       T.REPAIR_DATE,\n" );
   	sql.append("       T.WORK_TYPE,\n" );
   	sql.append("       T.IF_OUT,\n" );
   	sql.append("       T.WORK_STATUS,\n" );
   	sql.append("       T.APPLY_USER,\n" );
   	sql.append("       T.APPLY_MOBIL,\n" );
   	sql.append("       T.APPLY_DATE,\n" );
   	sql.append("       T.APPLY_ADDRESS,\n" );
   	sql.append("       T.APPLY_REMARKS,\n" );
   	sql.append("       T.REP_USER_TEL,\n" );
   	sql.append("       T.REJECTION_DATE,\n" );
   	sql.append("       T.GO_DATE,\n" );
   	sql.append("       T.ARRIVE_DATE,\n" );
   	sql.append("       T.COMPLETE_DATE,\n" );
   	sql.append("       T.COMPANY_ID,\n" );
   	sql.append("       T.ORG_ID,\n" );
   	sql.append("       T.CREATE_USER,\n" );
   	sql.append("       T.WORK_VIN,\n" );
   	sql.append("       T.JOBORDER_OPERATOR,\n" );
   	sql.append("       T.JOBORDER_TIME,\n" );
   	sql.append("       (SELECT I.VEHICLE_LONGITUDE FROM SE_BU_WORKGPS_IMP I WHERE I.WORK_NO =T.WORK_NO AND I.OPERATE_STATUS=1 AND ROWNUM =1 ) CLCFJD,--车辆出发经度\n" );
   	sql.append("       (SELECT I.VEHICLE_LATITUDE FROM SE_BU_WORKGPS_IMP I WHERE I.WORK_NO =T.WORK_NO AND I.OPERATE_STATUS=1 AND ROWNUM =1 ) CLCFWD,--车辆出发纬度\n" );
   	sql.append("       (SELECT I.VEHICLE_LONGITUDE FROM SE_BU_WORKGPS_IMP I WHERE I.WORK_NO =T.WORK_NO AND I.OPERATE_STATUS=2 AND ROWNUM =1 ) CLDDJD,--车辆到达经度\n" );
   	sql.append("       (SELECT I.VEHICLE_LATITUDE FROM SE_BU_WORKGPS_IMP I WHERE I.WORK_NO =T.WORK_NO AND I.OPERATE_STATUS=2 AND ROWNUM =1 ) CLDDWD,--车辆到达纬度\n" );
   	sql.append("       (SELECT I.PHONE_LONGITUDE FROM SE_BU_WORKGPS_IMP I WHERE I.WORK_NO =T.WORK_NO AND I.OPERATE_STATUS=1 AND ROWNUM =1 ) SJCFJD,--手机出发经度\n" );
   	sql.append("       (SELECT I.PHONE_LATITUDE FROM SE_BU_WORKGPS_IMP I WHERE I.WORK_NO =T.WORK_NO AND I.OPERATE_STATUS=1 AND ROWNUM =1 ) SJCFWD,--手机出发纬度\n" );
   	sql.append("       (SELECT I.PHONE_LONGITUDE FROM SE_BU_WORKGPS_IMP I WHERE I.WORK_NO =T.WORK_NO AND I.OPERATE_STATUS=2 AND ROWNUM =1 ) SJDDJD,--手机到达经度\n" );
   	sql.append("       (SELECT I.PHONE_LATITUDE FROM SE_BU_WORKGPS_IMP I WHERE I.WORK_NO =T.WORK_NO AND I.OPERATE_STATUS=2 AND ROWNUM =1 ) SJDDWD,--手机到达纬度\n" );
   	sql.append("       (SELECT I.PHONE_LOCATION FROM SE_BU_WORKGPS_IMP I WHERE I.WORK_NO =T.WORK_NO AND I.OPERATE_STATUS=1 AND ROWNUM =1 ) SJCFWZ,--手机出发位置\n" );
   	sql.append("       (SELECT I.PHONE_LOCATION FROM SE_BU_WORKGPS_IMP I WHERE I.WORK_NO =T.WORK_NO AND I.OPERATE_STATUS=2 AND ROWNUM =1 ) SJDDWZ,--手机到达位置\n" );
   	sql.append("       (SELECT I.VEHICLE_LOCATION FROM SE_BU_WORKGPS_IMP I WHERE I.WORK_NO =T.WORK_NO AND I.OPERATE_STATUS=1 AND ROWNUM =1 ) CLCFWZ,--车辆出发位置\n" );
   	sql.append("       (SELECT I.VEHICLE_LOCATION FROM SE_BU_WORKGPS_IMP I WHERE I.WORK_NO =T.WORK_NO AND I.OPERATE_STATUS=2 AND ROWNUM =1 ) CLDDWZ,--车辆到达位置\n" );
   	sql.append("       (SELECT NVL(I.EFFECTIVE_MILEAGE,0)*2  FROM SE_BU_WORKGPS_IMP I WHERE I.WORK_NO =T.WORK_NO AND I.OPERATE_STATUS=2 AND ROWNUM =1 ) YXLC,--有效里程\n" );
   	sql.append("       (SELECT I.VEHICLE_PHONE_MILEAGE FROM SE_BU_WORKGPS_IMP I WHERE I.WORK_NO =T.WORK_NO AND I.OPERATE_STATUS=1 AND ROWNUM =1 ) CLYSJJL,--车辆与手机距离\n" );
   	sql.append("       (SELECT I.LICENSE_PLATE FROM SE_BU_WORKGPS_IMP I WHERE I.WORK_NO =T.WORK_NO AND I.OPERATE_STATUS=1 AND ROWNUM =1 ) CPH,--车辆与手机距离\n" );
   	sql.append("       WU.USER_NAME,\n" );
   	sql.append("       WU.MOBIL,\n" );
   	sql.append("       T.ORG_ID ORG_CODE,\n" );
   	sql.append("       T.ORG_ID ORG_NAME \n" );
   	sql.append("      FROM SE_BU_WORK_ORDER T ,SE_BU_WORK_DISPATCH WD,SE_BU_WORK_DISPATCH_UER WU,SE_BU_WORK_DISPATCH_UER_RL WR\n" );
   	sql.append("       WHERE T.WORK_ID = WD.WORK_ID\n" );
   	sql.append("        AND WU.USER_ID = WR.USER_ID\n" );
   	sql.append("        AND WR.IF_MAIN = "+DicConstant.SF_01+"\n" );
   	sql.append("        AND WD.DISPATCH_ID = WR.DISPATCH_ID");
   	sql.append(" 	    AND T.WORK_ID = "+workId+"");
   	//执行方法，不需要传递conn参数
   	bs = factory.select(sql.toString(), page);
		bs.setFieldDateFormat("REPAIR_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("GO_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("ARRIVE_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("COMPLETE_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("REJECTION_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("JOBORDER_TIME", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
		bs.setFieldDic("WORK_TYPE","PGLX");
		bs.setFieldDic("IF_OUT","SF");
		bs.setFieldDic("IF_FIXCOSTS","SF");
		bs.setFieldDic("ACTIVITY_TYPE","HDLB");
		bs.setFieldDic("ACTIVITY_STATUS","HDZT");
		bs.setFieldDic("MANAGE_TYPE","CLFS");
		bs.setFieldDic("IN_ACCOUNT_TYPE","RZLX");
		bs.setFieldDic("IF_PERSON_CHECK","SF");
   	return bs;
   }
   public void insetStockDtl(Integer saleCount,User user,String saleId,String url,String nPartId) throws Exception
   {
	   StringBuffer sql= new StringBuffer();
	   sql.append("INSERT INTO PT_BU_DEALER_STOCK_LOG\n" );
	   sql.append("          (LOG_ID,\n" );
	   sql.append("           YWZJ,\n" );
	   sql.append("           ACTION_URL,\n" );
	   sql.append("           OAMOUNT,\n" );
	   sql.append("           AMOUNT,\n" );
	   sql.append("           AAMOUNT,\n" );
	   sql.append("           UPDATE_USER,\n" );
	   sql.append("           UPDATE_TIME,\n" );
	   sql.append("           PART_ID,\n" );
	   sql.append("           ORG_ID)\n" );
	   sql.append("        VALUES\n" );
	   sql.append("          (F_GETID(),\n" );
	   sql.append("           '"+saleId+"',\n" );
	   sql.append("           '"+url+"',\n" );
	   sql.append("           '"+saleCount+"',\n" );
	   sql.append("           '',\n" );
	   sql.append("           '-"+saleCount+"',\n" );
	   sql.append("           '"+user.getAccount()+"',\n" );
	   sql.append("           SYSDATE,\n" );
	   sql.append("           '"+nPartId+"',\n" );
	   sql.append("           '"+user.getOrgId()+"')");
	   factory.update(sql.toString(), null);
   }
   
   /**
    * 单车材料费
    * @param claimId
    * @return
    * @throws Exception
    */
   public BaseResultSet searchClaimMaxCosts(String claimId)throws Exception{
	   BaseResultSet bs=null;
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT  NVL(SUM(T.CLAIM_COSTS), 0) CLAIM_COST\n" );
	   sql.append("  FROM SE_BU_CLAIM_FAULT_PART T\n" );
	   sql.append(" WHERE T.CLAIM_ID = "+claimId+"");
	   bs=factory.select(sql.toString(),new PageManager());
	   return bs;
   }
   /**
    * 办事处工单查询导出
    * @param claimId
    * @return
    * @throws Exception
    */
   public QuerySet bscDownload(String conditions,User user)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.WORK_ID,\n" );
	   sql.append("       T.WORK_NO,\n" );
	   sql.append("       T.WORK_VIN,\n" );
	   sql.append("       T.REPAIR_USER,\n" );
	   sql.append("       T.REPAIR_DATE,\n" );
	   sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID = T.WORK_TYPE) WORK_TYPE,\n" );
	   sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID = T.IF_OUT)IF_OUT,\n" );
	   sql.append("       (SELECT TR.DIC_VALUE FROM DIC_TREE TR WHERE TR.ID = T.WORK_STATUS) WORK_STATUS,\n" );
	   sql.append("       T.APPLY_USER,\n" );
	   sql.append("       T.APPLY_MOBIL,\n" );
	   sql.append("       T.APPLY_DATE,\n" );
	   sql.append("       T.APPLY_ADDRESS,\n" );
	   sql.append("       T.JOBORDER_TIME,\n" );
	   sql.append("       T.ORG_ID,\n" );
	   sql.append("       T.GO_DATE,\n" );
	   sql.append("       T.ARRIVE_DATE,\n" );
	   sql.append("       T.COMPLETE_DATE,\n" );
	   sql.append("       (SELECT G.CODE FROM TM_ORG G WHERE G.ORG_ID =T.ORG_ID) ORG_CODE,\n" );
	   sql.append("       D.LICENSE_PLATE,\n" );
	   sql.append("       (SELECT G.ONAME FROM TM_ORG G WHERE G.ORG_ID =T.ORG_ID) ORG_NAME\n" );
	   sql.append("  FROM SE_BU_WORK_ORDER T\n" );
	   sql.append("  LEFT JOIN SE_BU_WORK_DISPATCH D\n" );
	   sql.append("    ON T.WORK_ID = D.WORK_ID\n" );
	   sql.append("   AND D.STATUS = 100201, TM_ORG T1, TM_ORG T2\n" );
	   sql.append(" WHERE  "+conditions+"\n" );
	   sql.append("   AND T1.ORG_ID = T.ORG_ID\n" );
	   sql.append("   AND T1.PID = T2.ORG_ID\n" );
	   sql.append("   AND T2.ORG_ID = "+user.getOrgId()+"\n" );
	   sql.append(" ORDER BY NVL(T.GO_DATE, TO_DATE('2014-01-01 01:01', 'YYYY-MM-DD HH:mi')) DESC,\n" );
	   sql.append("          NVL(T.ARRIVE_DATE,\n" );
	   sql.append("              TO_DATE('2014-01-01 01:01', 'YYYY-MM-DD HH:mi')) DESC,\n" );
	   sql.append("          NVL(T.COMPLETE_DATE,\n" );
	   sql.append("              TO_DATE('2014-01-01 01:01', 'YYYY-MM-DD HH:mi')) DESC");
	   return factory.select(null, sql.toString());
   }
   /**
    * 厂端工单查询下载
    * @param claimId
    * @return
    * @throws Exception
    */
   public QuerySet oemDownload(String conditions,User user)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.WORK_ID,\n" );
	   sql.append("       T.WORK_NO,\n" );
	   sql.append("       T.WORK_VIN,\n" );
	   sql.append("       T.REPAIR_USER,\n" );
	   sql.append("       T.REPAIR_DATE,\n" );
	   sql.append("       (SELECT D.DIC_VALUE FROM DIC_TREE D WHERE D.ID = T.WORK_TYPE)WORK_TYPE,\n" );
	   sql.append("       (SELECT D.DIC_VALUE FROM DIC_TREE D WHERE D.ID = T.WORK_STATUS)WORK_STATUS,\n" );
	   sql.append("       (SELECT D.DIC_VALUE FROM DIC_TREE D WHERE D.ID = T.IF_OUT)IF_OUT,\n" );
	   sql.append("       T.APPLY_USER,\n" );
	   sql.append("       T.APPLY_MOBIL,\n" );
	   sql.append("       T.APPLY_DATE,\n" );
	   sql.append("       T.APPLY_ADDRESS,\n" );
	   sql.append("       T.JOBORDER_TIME,\n" );
	   sql.append("       T.DISPATCH_TIME,\n" );
	   sql.append("       T.ORG_ID,\n" );
	   sql.append("       T.GO_DATE,\n" );
	   sql.append("       T.ARRIVE_DATE,\n" );
	   sql.append("       T.COMPLETE_DATE,\n" );
	   sql.append("       (SELECT D.DIC_VALUE FROM DIC_TREE D WHERE D.ID = T.STATUS)STATUS,\n" );
	   sql.append("       T.ORG_ID ORG_CODE,\n" );
	   sql.append("       D.LICENSE_PLATE,\n" );
	   sql.append("       T.ORG_ID ORG_NAME\n" );
	   sql.append("  FROM SE_BU_WORK_ORDER T\n" );
	   sql.append("  LEFT JOIN SE_BU_WORK_DISPATCH D\n" );
	   sql.append("    ON T.WORK_ID = D.WORK_ID\n" );
	   sql.append("   AND D.STATUS = 100201\n" );
	   sql.append(" WHERE "+conditions+"\n" );
	   sql.append(" ORDER BY NVL(T.GO_DATE, TO_DATE('2014-01-01 01:01', 'YYYY-MM-DD HH:mi')) DESC,\n" );
	   sql.append("          NVL(T.ARRIVE_DATE,TO_DATE('2014-01-01 01:01', 'YYYY-MM-DD HH:mi')) DESC,\n" );
	   sql.append("          NVL(T.COMPLETE_DATE,TO_DATE('2014-01-01 01:01', 'YYYY-MM-DD HH:mi')) DESC");
	   return factory.select(null, sql.toString());
   }
   /**
    * 审核轨迹
    * @param vo
    * @return
    * @throws Exception
    */
   public boolean insertCheck(SeBuClaimCheckVO vo)throws Exception{
   	return factory.insert(vo);
   }
   public BaseResultSet outBuyPartSearch(PageManager page, User user, String claimId) throws Exception
   {
   	//定义返回结果集
   	BaseResultSet bs = null;
   	StringBuffer sql= new StringBuffer();
   	sql.append("SELECT P.NEW_PART_CODE,\n" );
   	sql.append("       P.NEW_PART_NAME,\n" );
   	sql.append("       (SELECT SP.SUPPLIER_CODE FROM PT_BA_SUPPLIER SP WHERE SP.SUPPLIER_ID = P.NEW_SUPPLIER_ID) NEW_SUPPLIER_CODE,\n" );
   	sql.append("       (SELECT SP.SUPPLIER_NAME FROM PT_BA_SUPPLIER SP WHERE SP.SUPPLIER_ID = P.NEW_SUPPLIER_ID) NEW_SUPPLIER_NAME,\n" );
   	sql.append("      NVL(P.NEW_PART_COUNT, 0) - NVL(S.AVAILABLE_AMOUNT, 0) WCSL\n" );
   	sql.append("  FROM SE_BU_CLAIM_FAULT_PART P\n" );
   	sql.append("  LEFT JOIN PT_BU_DEALER_STOCK S");
   	sql.append("    ON (P.NEW_PART_ID = S.PART_ID AND S.ORG_ID = "+user.getOrgId()+" )\n" );
   	sql.append(" WHERE P.CLAIM_ID = "+claimId+"\n");
   	sql.append("   AND P.NEW_PART_ID IS NOT NULL");
   	sql.append("   AND P.NEW_PART_COUNT > NVL(S.AVAILABLE_AMOUNT, 0)");
   	bs = factory.select(sql.toString(), page);
   	bs.setFieldDic("UNIT", "JLDW");
   	return bs;
   }
}
