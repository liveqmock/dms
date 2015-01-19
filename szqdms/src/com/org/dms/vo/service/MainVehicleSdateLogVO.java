package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class MainVehicleSdateLogVO extends BaseVO{
    public MainVehicleSdateLogVO(){
    	//设置字段信息
    	this.addField("VIN",BaseVO.OP_STRING);
    	this.addField("OLD_SDATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("OLD_SDATE", "yyyy-MM-dd");
    	this.addField("APPLY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	this.addField("CHECK_USER",BaseVO.OP_STRING);
    	this.addField("CHECK_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
    	this.addField("USER_TYPE",BaseVO.OP_STRING);
    	this.addField("APPLY_REASON",BaseVO.OP_STRING);
    	this.addField("LOG_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("APPLY_COMPANY",BaseVO.OP_STRING);
    	this.addField("MODELS_CODE",BaseVO.OP_STRING);
    	this.addField("CHECK_REMARKS",BaseVO.OP_STRING);
    	this.addField("CHECK_STATUS",BaseVO.OP_STRING);
    	this.addField("NEW_SDATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("NEW_SDATE", "yyyy-MM-dd");
		this.bindFieldToSequence("LOG_ID","COMMON");
    	this.setVOTableName("MAIN_VEHICLE_SDATE_LOG");
    	
		//设置字典类型定义
		this.bindFieldToDic("CHECK_STATUS","CLXSRQZT");//销售日期申请状态
		this.bindFieldToDic("USER_TYPE","CLYHLX");//车辆用户类型
}
	public void setVin(String Vin){
		this.setInternal("VIN" ,Vin );
	}


	public String getVin(){
		return (String)this.getInternal("VIN");
	}
	public void setOldSdate(Date OldSdate){
		this.setInternal("OLD_SDATE" ,OldSdate );
	}


	public Date getOldSdate(){
		return (Date)this.getInternal("OLD_SDATE");
	}
	public void setApplyDate(Date ApplyDate){
		this.setInternal("APPLY_DATE" ,ApplyDate );
	}


	public Date getApplyDate(){
		return (Date)this.getInternal("APPLY_DATE");
	}
	public void setCheckUser(String CheckUser){
		this.setInternal("CHECK_USER" ,CheckUser );
	}


	public String getCheckUser(){
		return (String)this.getInternal("CHECK_USER");
	}
	public void setCheckDate(Date CheckDate){
		this.setInternal("CHECK_DATE" ,CheckDate );
	}


	public Date getCheckDate(){
		return (Date)this.getInternal("CHECK_DATE");
	}
	public void setUserType(String UserType){
		this.setInternal("USER_TYPE" ,UserType );
	}


	public String getUserType(){
		return (String)this.getInternal("USER_TYPE");
	}
	public void setApplyReason(String ApplyReason){
		this.setInternal("APPLY_REASON" ,ApplyReason );
	}


	public String getApplyReason(){
		return (String)this.getInternal("APPLY_REASON");
	}
	public void setLogId(String LogId){
		this.setInternal("LOG_ID" ,LogId );
	}


	public String getLogId(){
		return (String)this.getInternal("LOG_ID");
	}
	public void setApplyCompany(String ApplyCompany){
		this.setInternal("APPLY_COMPANY" ,ApplyCompany );
	}


	public String getApplyCompany(){
		return (String)this.getInternal("APPLY_COMPANY");
	}
	public void setModelsCode(String ModelsCode){
		this.setInternal("MODELS_CODE" ,ModelsCode );
	}


	public String getModelsCode(){
		return (String)this.getInternal("MODELS_CODE");
	}
	public void setCheckRemarks(String CheckRemarks){
		this.setInternal("CHECK_REMARKS" ,CheckRemarks );
	}


	public String getCheckRemarks(){
		return (String)this.getInternal("CHECK_REMARKS");
	}
	public void setCheckStatus(String CheckStatus){
		this.setInternal("CHECK_STATUS" ,CheckStatus );
	}


	public String getCheckStatus(){
		return (String)this.getInternal("CHECK_STATUS");
	}
	public void setNewSdate(Date NewSdate){
		this.setInternal("NEW_SDATE" ,NewSdate );
	}


	public Date getNewSdate(){
		return (Date)this.getInternal("NEW_SDATE");
	}
}
