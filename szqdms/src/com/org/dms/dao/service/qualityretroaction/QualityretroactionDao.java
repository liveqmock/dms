package com.org.dms.dao.service.qualityretroaction;


import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuQualityFbackDealVO;
import com.org.dms.vo.service.SeBuQualityFbackPartVO;
import com.org.dms.vo.service.SeBuQualityFbackVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;
public class QualityretroactionDao extends BaseDAO
{
    //定义instance
    public  static final QualityretroactionDao getInstance(ActionContext atx)
    {
    	
    	QualityretroactionDao dao = new QualityretroactionDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	//查询
    public BaseResultSet qualityretroactionSearch(PageManager page, User user, String conditions,String orgId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.STATUS="+DicConstant.YXBS_01+""
    			+ " AND T.FBACK_STATUS ="+DicConstant.ZLFKZT_01+""
    			+ " AND T.ORG_ID = "+orgId+""
    			+ " ORDER BY T.FBACK_ID";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.FBACK_ID,\n" );
    	sql.append("       T.COM_NAME,\n" );
    	sql.append("       T.WRITE_DATE,\n" );
    	sql.append("       T.NAME,\n" );
    	sql.append("       T.TEL,\n" );
    	sql.append("       T.FAX,\n" );
    	sql.append("       T.ENGINE_NO,\n" );
    	sql.append("       T.VEHICLE_ID,\n" );
    	sql.append("       T.VIN,\n" );
    	sql.append("       T.MODELS_CODE,\n" );
    	sql.append("       T.ENGINE_TYPE,\n" );
    	sql.append("       T.ENGINE_BOOK_NO,\n" );
    	sql.append("       T.BUY_DATE,\n" );
    	sql.append("       T.FAULT_DATE,\n" );
    	sql.append("       T.FAULT_MIELES,\n" );
    	sql.append("       T.CUS_COM_NAME,\n" );
    	sql.append("       T.CUS_BUY_COUNT,\n" );
    	sql.append("       T.CUS_LINK_MAN,\n" );
    	sql.append("       T.CUS_TEL,\n" );
    	sql.append("       T.CUS_FAX,\n" );
    	sql.append("       T.DRI_NAME,\n" );
    	sql.append("       T.DRI_TEL,\n" );
    	sql.append("       T.DRI_STATUS,\n" );
    	sql.append("       T.VEHICLE_USE_TYPE,\n" );
    	sql.append("       T.DAILY_WORK,\n" );
    	sql.append("       T.FAULT_ADDRESS,\n" );
    	sql.append("       T.DAILY_ROAD,\n" );
    	sql.append("       T.MAINTENANCE_STATUS,\n" );
    	sql.append("       T.VEHICLE_STATUS,\n" );
    	sql.append("       T.FBACK_APPROACE,\n" );
    	sql.append("       T.AMOUNT,\n" );
    	sql.append("       T.FBACK_STATUS,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.COMPANY_ID,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME,\n" );
    	sql.append("       T.STATUS,\n" );
    	sql.append("       T.OEM_COMPANY_ID,\n" );
    	sql.append("       T.SECRET_LEVEL\n" );
    	sql.append("  FROM SE_BU_QUALITY_FBACK T");
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDateFormat("WRITE_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd");
		bs.setFieldDic("FBACK_STATUS","ZLFKZT");
		bs.setFieldDic("STATUS","YXBS");
    	return bs;
    }
  //下端质量反馈查询
    public BaseResultSet qualityretroactionDelSearch(PageManager page, String orgId, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.STATUS="+DicConstant.YXBS_01+""
    			+ " AND T.ORG_ID = "+orgId+""
    			+ " AND T.FBACK_STATUS IN( "+DicConstant.ZLFKZT_02+","+DicConstant.ZLFKZT_03+","+DicConstant.ZLFKZT_04+","+DicConstant.ZLFKZT_05+")"
    			+ " ORDER BY T.FBACK_ID";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.FBACK_ID,\n" );
    	sql.append("       T.COM_NAME,\n" );
    	sql.append("       T.WRITE_DATE,\n" );
    	sql.append("       T.NAME,\n" );
    	sql.append("       T.TEL,\n" );
    	sql.append("       T.FAX,\n" );
    	sql.append("       T.ENGINE_NO,\n" );
    	sql.append("       T.VEHICLE_ID,\n" );
    	sql.append("       T.VIN,\n" );
    	sql.append("       T.MODELS_CODE,\n" );
    	sql.append("       T.ENGINE_TYPE,\n" );
    	sql.append("       T.ENGINE_BOOK_NO,\n" );
    	sql.append("       T.BUY_DATE,\n" );
    	sql.append("       T.FAULT_DATE,\n" );
    	sql.append("       T.FAULT_MIELES,\n" );
    	sql.append("       T.CUS_COM_NAME,\n" );
    	sql.append("       T.CUS_BUY_COUNT,\n" );
    	sql.append("       T.CUS_LINK_MAN,\n" );
    	sql.append("       T.CUS_TEL,\n" );
    	sql.append("       T.CUS_FAX,\n" );
    	sql.append("       T.DRI_NAME,\n" );
    	sql.append("       T.DRI_TEL,\n" );
    	sql.append("       T.DRI_STATUS,\n" );
    	sql.append("       T.VEHICLE_USE_TYPE,\n" );
    	sql.append("       T.DAILY_WORK,\n" );
    	sql.append("       T.FAULT_ADDRESS,\n" );
    	sql.append("       T.DAILY_ROAD,\n" );
    	sql.append("       T.MAINTENANCE_STATUS,\n" );
    	sql.append("       T.VEHICLE_STATUS,\n" );
    	sql.append("       T.FBACK_APPROACE,\n" );
    	sql.append("       T.AMOUNT,\n" );
    	sql.append("       T.FBACK_STATUS,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.COMPANY_ID,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME,\n" );
    	sql.append("       T.STATUS,\n" );
    	sql.append("       T.OEM_COMPANY_ID,\n" );
    	sql.append("       T.SECRET_LEVEL\n" );
    	sql.append("  FROM SE_BU_QUALITY_FBACK T");
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDateFormat("WRITE_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd");
		bs.setFieldDic("STATUS","YXBS");
		bs.setFieldDic("FBACK_STATUS","ZLFKZT");
    	return bs;
    }
  //上端质量反馈查询
    public BaseResultSet qualityretroactionOemSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String orgLevel =user.getOrgDept().getLevelCode();
    	String wheres = conditions;
    	wheres += " AND T.STATUS="+DicConstant.YXBS_01+" "
    			+ " AND TG.ORG_ID = T.ORG_ID "
    			+ " AND T.VEHICLE_ID = MV.VEHICLE_ID";
		if(orgLevel.equals(DicConstant.JGJB_04)){
    		wheres+=" AND TG.PID = G.ORG_ID"
    			  + " AND G.ORG_ID = "+user.getOrgId()+"";
    	}
		wheres+= " AND T.FBACK_STATUS IN( "+DicConstant.ZLFKZT_02+","+DicConstant.ZLFKZT_03+","+DicConstant.ZLFKZT_04+","+DicConstant.ZLFKZT_05+")"
    			+ " ORDER BY T.FBACK_ID";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.FBACK_ID,\n" );
    	sql.append("       T.COM_NAME,\n" );
    	sql.append("       T.WRITE_DATE,\n" );
    	sql.append("       T.NAME,\n" );
    	sql.append("       T.TEL,\n" );
    	sql.append("       T.FAX,\n" );
    	sql.append("       T.ENGINE_NO,\n" );
    	sql.append("       T.VEHICLE_ID,\n" );
    	sql.append("       T.VIN,\n" );
    	sql.append("       T.MODELS_CODE,\n" );
    	sql.append("       T.ENGINE_TYPE,\n" );
    	sql.append("       T.ENGINE_BOOK_NO,\n" );
    	sql.append("       T.BUY_DATE,\n" );
    	sql.append("       T.FAULT_DATE,\n" );
    	sql.append("       T.FAULT_MIELES,\n" );
    	sql.append("       T.CUS_COM_NAME,\n" );
    	sql.append("       T.CUS_BUY_COUNT,\n" );
    	sql.append("       T.CUS_LINK_MAN,\n" );
    	sql.append("       T.CUS_TEL,\n" );
    	sql.append("       T.CUS_FAX,\n" );
    	sql.append("       T.DRI_NAME,\n" );
    	sql.append("       T.DRI_TEL,\n" );
    	sql.append("       T.DRI_STATUS,\n" );
    	sql.append("       T.VEHICLE_USE_TYPE,\n" );
    	sql.append("       T.DAILY_WORK,\n" );
    	sql.append("       T.FAULT_ADDRESS,\n" );
    	sql.append("       T.DAILY_ROAD,\n" );
    	sql.append("       T.MAINTENANCE_STATUS,\n" );
    	sql.append("       T.VEHICLE_STATUS,\n" );
    	sql.append("       T.FBACK_APPROACE,\n" );
    	sql.append("       T.AMOUNT,\n" );
    	sql.append("       T.FBACK_STATUS,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.COMPANY_ID,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME,\n" );
    	sql.append("       T.STATUS,\n" );
    	sql.append("       TG.ONAME,\n" );
    	sql.append("       TG.CODE,\n" );
    	sql.append("       T.OEM_COMPANY_ID,\n" );
    	sql.append("       T.SECRET_LEVEL\n" );
    	sql.append("  FROM SE_BU_QUALITY_FBACK T, TM_ORG TG, MAIN_VEHICLE MV");
    	if(orgLevel.equals(DicConstant.JGJB_04)){
    		sql.append(" ,TM_ORG G");
    	}
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDateFormat("WRITE_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd");
		bs.setFieldDic("STATUS","YXBS");
		bs.setFieldDic("FBACK_STATUS","ZLFKZT");
    	return bs;
    }
    //上端质量反馈处理
    public BaseResultSet qualityretroactionDisposeSearch(PageManager page, User user, String conditions) throws Exception
    {
    	String orgLevel =user.getOrgDept().getLevelCode();
    	String wheres = conditions;
    	wheres += " AND T.STATUS="+DicConstant.YXBS_01+" "
    			+ " AND TG.ORG_ID = T.ORG_ID ";
    	if(orgLevel.equals(DicConstant.JGJB_04)){
    		wheres+=" AND TG.PID = G.ORG_ID"
    			  + " AND G.ORG_ID = "+user.getOrgId()+"";
    	}	
    	wheres+=  " AND T.VEHICLE_ID = MV.VEHICLE_ID"
    			+ " AND T.FBACK_STATUS IN( "+DicConstant.ZLFKZT_02+","+DicConstant.ZLFKZT_04+")"
    			+ " ORDER BY T.FBACK_ID";
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.FBACK_ID,\n" );
    	sql.append("       T.COM_NAME,\n" );
    	sql.append("       T.WRITE_DATE,\n" );
    	sql.append("       T.NAME,\n" );
    	sql.append("       T.TEL,\n" );
    	sql.append("       T.FAX,\n" );
    	sql.append("       T.ENGINE_NO,\n" );
    	sql.append("       T.VEHICLE_ID,\n" );
    	sql.append("       T.VIN,\n" );
    	sql.append("       T.MODELS_CODE,\n" );
    	sql.append("       T.ENGINE_TYPE,\n" );
    	sql.append("       T.ENGINE_BOOK_NO,\n" );
    	sql.append("       T.BUY_DATE,\n" );
    	sql.append("       T.FAULT_DATE,\n" );
    	sql.append("       T.FAULT_MIELES,\n" );
    	sql.append("       T.CUS_COM_NAME,\n" );
    	sql.append("       T.CUS_BUY_COUNT,\n" );
    	sql.append("       T.CUS_LINK_MAN,\n" );
    	sql.append("       T.CUS_TEL,\n" );
    	sql.append("       T.CUS_FAX,\n" );
    	sql.append("       T.DRI_NAME,\n" );
    	sql.append("       T.DRI_TEL,\n" );
    	sql.append("       T.DRI_STATUS,\n" );
    	sql.append("       T.VEHICLE_USE_TYPE,\n" );
    	sql.append("       T.DAILY_WORK,\n" );
    	sql.append("       T.FAULT_ADDRESS,\n" );
    	sql.append("       T.DAILY_ROAD,\n" );
    	sql.append("       T.MAINTENANCE_STATUS,\n" );
    	sql.append("       T.VEHICLE_STATUS,\n" );
    	sql.append("       T.FBACK_APPROACE,\n" );
    	sql.append("       T.AMOUNT,\n" );
    	sql.append("       T.FBACK_STATUS,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.COMPANY_ID,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME,\n" );
    	sql.append("       T.STATUS,\n" );
    	sql.append("       TG.ONAME,\n" );
    	sql.append("       TG.CODE,\n" );
    	sql.append("       T.OEM_COMPANY_ID,\n" );
    	sql.append("       T.SECRET_LEVEL\n" );
    	sql.append("  FROM SE_BU_QUALITY_FBACK T, TM_ORG TG,MAIN_VEHICLE MV");
    	if(orgLevel.equals(DicConstant.JGJB_04)){
    		sql.append("  ,TM_ORG G");
    	}
    	
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDateFormat("WRITE_DATE", "yyyy-MM-dd");
    	bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
    	bs.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd");
    	bs.setFieldDic("STATUS","YXBS");
    	bs.setFieldDic("FBACK_STATUS","ZLFKZT");
    	return bs;
    }
  //质量反馈关闭查询
    public BaseResultSet qualityretroactionCloseSearch(PageManager page, String orgId, String conditions) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND T.STATUS="+DicConstant.YXBS_01+""
    			+ " AND T.VEHICLE_ID = MV.VEHICLE_ID"
    			+ " AND T.ORG_ID = "+orgId+""
    			+ " AND T.FBACK_STATUS = "+DicConstant.ZLFKZT_03+""
    			+ " ORDER BY T.FBACK_ID";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.FBACK_ID,\n" );
    	sql.append("       T.COM_NAME,\n" );
    	sql.append("       T.WRITE_DATE,\n" );
    	sql.append("       T.NAME,\n" );
    	sql.append("       T.TEL,\n" );
    	sql.append("       T.FAX,\n" );
    	sql.append("       T.ENGINE_NO,\n" );
    	sql.append("       T.VEHICLE_ID,\n" );
    	sql.append("       T.VIN,\n" );
    	sql.append("       T.MODELS_CODE,\n" );
    	sql.append("       T.ENGINE_TYPE,\n" );
    	sql.append("       T.ENGINE_BOOK_NO,\n" );
    	sql.append("       T.BUY_DATE,\n" );
    	sql.append("       T.FAULT_DATE,\n" );
    	sql.append("       T.FAULT_MIELES,\n" );
    	sql.append("       T.CUS_COM_NAME,\n" );
    	sql.append("       T.CUS_BUY_COUNT,\n" );
    	sql.append("       T.CUS_LINK_MAN,\n" );
    	sql.append("       T.CUS_TEL,\n" );
    	sql.append("       T.CUS_FAX,\n" );
    	sql.append("       T.DRI_NAME,\n" );
    	sql.append("       T.DRI_TEL,\n" );
    	sql.append("       T.DRI_STATUS,\n" );
    	sql.append("       T.VEHICLE_USE_TYPE,\n" );
    	sql.append("       T.DAILY_WORK,\n" );
    	sql.append("       T.FAULT_ADDRESS,\n" );
    	sql.append("       T.DAILY_ROAD,\n" );
    	sql.append("       T.MAINTENANCE_STATUS,\n" );
    	sql.append("       T.VEHICLE_STATUS,\n" );
    	sql.append("       T.FBACK_APPROACE,\n" );
    	sql.append("       T.AMOUNT,\n" );
    	sql.append("       T.FBACK_STATUS,\n" );
    	sql.append("       T.REMARKS,\n" );
    	sql.append("       T.COMPANY_ID,\n" );
    	sql.append("       T.ORG_ID,\n" );
    	sql.append("       T.CREATE_USER,\n" );
    	sql.append("       T.CREATE_TIME,\n" );
    	sql.append("       T.UPDATE_USER,\n" );
    	sql.append("       T.UPDATE_TIME,\n" );
    	sql.append("       T.STATUS,\n" );
    	sql.append("       T.OEM_COMPANY_ID,\n" );
    	sql.append("       T.SECRET_LEVEL\n" );
    	sql.append("  FROM SE_BU_QUALITY_FBACK T, MAIN_VEHICLE MV");
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
		bs.setFieldDateFormat("WRITE_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
		bs.setFieldDateFormat("FAULT_DATE", "yyyy-MM-dd");
		bs.setFieldDic("STATUS","YXBS");
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
    	sql.append("       T.USER_ADDRESS\n" );
    	sql.append("  FROM MAIN_VEHICLE T\n" );
    	sql.append(" WHERE (T.VIN LIKE '%"+vin+"'  OR T.VIN = '"+vin+"')\n" );
    	sql.append("   AND T.ENGINE_NO = '"+engineNo+"'");
    	return factory.select(sql.toString(), new PageManager());
    }
    //下端质量反馈处理意见查询
    public BaseResultSet searchDealRemarks(String fbackId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT TD.FBACK_ID,\n" );
    	sql.append("       TD.DEAL_REMARKS,\n" );
    	sql.append("       TD.CREATE_TIME\n" );
    	sql.append("  FROM SE_BU_QUALITY_FBACK_DEAL TD\n" );
    	sql.append(" WHERE TD.FBACK_ID = "+fbackId+"\n" );
    	sql.append(" ORDER BY TD.DEAL_ID");
    	return factory.select(sql.toString(), new PageManager());
    }
    //上端质量反馈驳回意见查询
    public BaseResultSet searchRejectRemarks(String fbackId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT TD.FBACK_ID,\n" );
    	sql.append("       TD.REJECT_REMARKS,\n" );
    	sql.append("       TD.REJECT_DATE\n" );
    	sql.append("  FROM SE_BU_QUALITY_FBACK_DEAL TD\n" );
    	sql.append(" WHERE TD.FBACK_ID = "+fbackId+"\n" );
    	sql.append(" ORDER BY TD.DEAL_ID");
    	return factory.select(sql.toString(), new PageManager());
    }
    public boolean qualityRetroactionInsert(SeBuQualityFbackVO vo)throws Exception
    {
    	String id=SequenceUtil.getCommonSerivalNumber(factory);
    	vo.setFbackId(id);
    	return factory.insert(vo);
    }
    public boolean insertParts(SeBuQualityFbackPartVO vo)throws Exception
    {
    	return factory.insert(vo);
    }
    public boolean qualityRetroactionUpdate(SeBuQualityFbackVO vo)throws Exception
    {
    	return factory.update(vo);
    }
    public boolean qualityRetroactionDispose(SeBuQualityFbackDealVO vo)throws Exception
    {
    	return factory.insert(vo);
    }
    public BaseResultSet searchQualityParts(PageManager page, User user, String conditions,String fbackId) throws Exception
    {
    	String wheres = conditions;
    	wheres += " AND  TF.FBACK_ID = T.FBACK_ID\n"
    			+ " AND PT.PART_ID = T.PART_ID\n"
    			+ " AND TF.FBACK_ID = "+fbackId+"\n"
    		    + " ORDER BY T.RELATION_ID ";
        page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.RELATION_ID,\n" );
    	sql.append("       T.FBACK_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_ID,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T.SUPPLIER_ID,\n" );
    	sql.append("       T.SUPPLIER_NAME,\n" );
    	sql.append("       T.SUPPLIER_CODE,\n" );
    	sql.append("       T.UNIT_PRICE,\n" );
    	sql.append("       T.COUNT,\n" );
    	sql.append("       (T.COUNT*PT.SE_CLPRICE) AMOUNT \n");
    	sql.append("  FROM SE_BU_QUALITY_FBACK_PART T,SE_BU_QUALITY_FBACK TF,PT_BA_INFO PT\n" );
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    public BaseResultSet searchParts(PageManager page, User user, String conditions,String fbackId) throws Exception
    {
    	String wheres = conditions;
    	wheres +="AND PR.PART_ID = T.PART_ID\n"
    		   //+ "AND T.SE_STATUS ="+DicConstant.YXBS_01+"\n"
    		   +"AND PR.SUPPLIER_ID = PS.SUPPLIER_ID\n "
    		   +"AND PR.SE_IDENTIFY ="+DicConstant.YXBS_01+"\n "
    		   +"AND PS.SE_IDENTIFY ="+DicConstant.YXBS_01+"\n "
    		   +"AND NOT EXISTS (SELECT B.PART_ID   FROM SE_BU_QUALITY_FBACK_PART B WHERE T.PART_ID = B.PART_ID AND B.FBACK_ID = "+fbackId+")\n "
    		   +"ORDER BY T.PART_ID ";
    	page.setFilter(wheres);
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_ID,\n" );
    	sql.append("       T.PART_CODE,\n" );
    	sql.append("       T.PART_NAME,\n" );
    	sql.append("       T.PART_NO,\n" );
    	sql.append("       T.SE_CLPRICE,\n" );
    	sql.append("       T.UNIT,\n" );
    	sql.append("       T.PART_TYPE,\n" );
    	sql.append("       T.SALE_PRICE,\n" );
    	sql.append("       PS.SUPPLIER_ID,\n" );
    	sql.append("       PS.SUPPLIER_NAME,\n" );
    	sql.append("       PS.SUPPLIER_CODE\n" );
    	sql.append("  FROM PT_BA_SUPPLIER PS, PT_BA_PART_SUPPLIER_RL PR, PT_BA_INFO T\n" );
    	//执行方法，不需要传递conn参数
    	bs = factory.select(sql.toString(), page);
    	return bs;
    }
    public boolean deletePartByMxids(String mxids) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" DELETE SE_BU_QUALITY_FBACK_PART A WHERE A.RELATION_ID IN ("+mxids+")   \n");
    	return factory.delete(sql.toString(), null);
    }
    public boolean qualityretroactionDelete(String fbackId) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" UPDATE  SE_BU_QUALITY_FBACK T SET T.STATUS ="+DicConstant.YXBS_02+" WHERE T.FBACK_ID ="+fbackId+"  \n");
    	return factory.delete(sql.toString(), null);
    }
    public boolean insertRejectRemarks(SeBuQualityFbackDealVO vo) throws Exception
    {
    	return factory.insert(vo);
    }
    public boolean qualityretroactionReport(SeBuQualityFbackVO vo) throws Exception
    {
    	return factory.update(vo);
    }
    public QuerySet queryPartsCount(String fbackId) throws Exception
    {
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_ID FROM SE_BU_QUALITY_FBACK_PART T WHERE T.FBACK_ID ="+fbackId+"");
    	return factory.select(null, sql.toString());
    }
}