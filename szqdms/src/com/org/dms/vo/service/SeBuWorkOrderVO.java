package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuWorkOrderVO extends BaseVO{
    public SeBuWorkOrderVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("APPLY_ADDRESS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("APPLY_MOBIL",BaseVO.OP_STRING);
    	this.addField("WORK_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("APPLY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("COMPLETE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("COMPLETE_DATE", "yyyy-MM-dd");
    	this.addField("APPLY_REMARKS",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("REJECTION_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("REJECTION_DATE", "yyyy-MM-dd");
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("START_LONGITUDE",BaseVO.OP_STRING);
    	this.addField("JOBORDER_OPERATOR",BaseVO.OP_STRING);
    	this.addField("WORK_TYPE",BaseVO.OP_STRING);
    	this.addField("ARRIVE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("ARRIVE_DATE", "yyyy-MM-dd");
    	this.addField("WORK_STATUS",BaseVO.OP_STRING);
    	this.addField("START_LATITUDE",BaseVO.OP_STRING);
    	this.addField("JOBORDER_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("JOBORDER_TIME", "yyyy-MM-dd");
    	this.addField("END_LATITUDE",BaseVO.OP_STRING);
    	this.addField("END_LONGITUDE",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("APPLY_USER",BaseVO.OP_STRING);
    	this.addField("WORK_NO",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("DISPATCH_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("DISPATCH_TIME", "yyyy-MM-dd");
    	this.addField("IF_OUT",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("REPAIR_USER",BaseVO.OP_STRING);
    	this.addField("WORK_VIN",BaseVO.OP_STRING);
    	this.addField("REPAIR_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("REPAIR_DATE", "yyyy-MM-dd");
    	this.addField("REP_USER_TEL",BaseVO.OP_STRING);
    	this.addField("CALL_NUMBER",BaseVO.OP_STRING);
    	this.addField("GO_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("GO_DATE", "yyyy-MM-dd");
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("WORK_ID","COMMON");
    	this.setVOTableName("SE_BU_WORK_ORDER");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setApplyAddress(String ApplyAddress){
		this.setInternal("APPLY_ADDRESS" ,ApplyAddress );
	}


	public String getApplyAddress(){
		return (String)this.getInternal("APPLY_ADDRESS");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setApplyMobil(String ApplyMobil){
		this.setInternal("APPLY_MOBIL" ,ApplyMobil );
	}


	public String getApplyMobil(){
		return (String)this.getInternal("APPLY_MOBIL");
	}
	public void setWorkId(String WorkId){
		this.setInternal("WORK_ID" ,WorkId );
	}


	public String getWorkId(){
		return (String)this.getInternal("WORK_ID");
	}
	public void setApplyDate(Date ApplyDate){
		this.setInternal("APPLY_DATE" ,ApplyDate );
	}


	public Date getApplyDate(){
		return (Date)this.getInternal("APPLY_DATE");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setCompleteDate(Date CompleteDate){
		this.setInternal("COMPLETE_DATE" ,CompleteDate );
	}


	public Date getCompleteDate(){
		return (Date)this.getInternal("COMPLETE_DATE");
	}
	public void setApplyRemarks(String ApplyRemarks){
		this.setInternal("APPLY_REMARKS" ,ApplyRemarks );
	}


	public String getApplyRemarks(){
		return (String)this.getInternal("APPLY_REMARKS");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setRejectionDate(Date RejectionDate){
		this.setInternal("REJECTION_DATE" ,RejectionDate );
	}


	public Date getRejectionDate(){
		return (Date)this.getInternal("REJECTION_DATE");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setStartLongitude(String StartLongitude){
		this.setInternal("START_LONGITUDE" ,StartLongitude );
	}


	public String getStartLongitude(){
		return (String)this.getInternal("START_LONGITUDE");
	}
	public void setJoborderOperator(String JoborderOperator){
		this.setInternal("JOBORDER_OPERATOR" ,JoborderOperator );
	}


	public String getJoborderOperator(){
		return (String)this.getInternal("JOBORDER_OPERATOR");
	}
	public void setWorkType(String WorkType){
		this.setInternal("WORK_TYPE" ,WorkType );
	}


	public String getWorkType(){
		return (String)this.getInternal("WORK_TYPE");
	}
	public void setArriveDate(Date ArriveDate){
		this.setInternal("ARRIVE_DATE" ,ArriveDate );
	}


	public Date getArriveDate(){
		return (Date)this.getInternal("ARRIVE_DATE");
	}
	public void setWorkStatus(String WorkStatus){
		this.setInternal("WORK_STATUS" ,WorkStatus );
	}


	public String getWorkStatus(){
		return (String)this.getInternal("WORK_STATUS");
	}
	public void setStartLatitude(String StartLatitude){
		this.setInternal("START_LATITUDE" ,StartLatitude );
	}


	public String getStartLatitude(){
		return (String)this.getInternal("START_LATITUDE");
	}
	public void setJoborderTime(Date JoborderTime){
		this.setInternal("JOBORDER_TIME" ,JoborderTime );
	}


	public Date getJoborderTime(){
		return (Date)this.getInternal("JOBORDER_TIME");
	}
	public void setEndLatitude(String EndLatitude){
		this.setInternal("END_LATITUDE" ,EndLatitude );
	}


	public String getEndLatitude(){
		return (String)this.getInternal("END_LATITUDE");
	}
	public void setEndLongitude(String EndLongitude){
		this.setInternal("END_LONGITUDE" ,EndLongitude );
	}


	public String getEndLongitude(){
		return (String)this.getInternal("END_LONGITUDE");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setApplyUser(String ApplyUser){
		this.setInternal("APPLY_USER" ,ApplyUser );
	}


	public String getApplyUser(){
		return (String)this.getInternal("APPLY_USER");
	}
	public void setWorkNo(String WorkNo){
		this.setInternal("WORK_NO" ,WorkNo );
	}


	public String getWorkNo(){
		return (String)this.getInternal("WORK_NO");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setDispatchTime(Date DispatchTime){
		this.setInternal("DISPATCH_TIME" ,DispatchTime );
	}


	public Date getDispatchTime(){
		return (Date)this.getInternal("DISPATCH_TIME");
	}
	public void setIfOut(String IfOut){
		this.setInternal("IF_OUT" ,IfOut );
	}


	public String getIfOut(){
		return (String)this.getInternal("IF_OUT");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setRepairUser(String RepairUser){
		this.setInternal("REPAIR_USER" ,RepairUser );
	}


	public String getRepairUser(){
		return (String)this.getInternal("REPAIR_USER");
	}
	public void setWorkVin(String WorkVin){
		this.setInternal("WORK_VIN" ,WorkVin );
	}


	public String getWorkVin(){
		return (String)this.getInternal("WORK_VIN");
	}
	public void setRepairDate(Date RepairDate){
		this.setInternal("REPAIR_DATE" ,RepairDate );
	}


	public Date getRepairDate(){
		return (Date)this.getInternal("REPAIR_DATE");
	}
	public void setRepUserTel(String RepUserTel){
		this.setInternal("REP_USER_TEL" ,RepUserTel );
	}


	public String getRepUserTel(){
		return (String)this.getInternal("REP_USER_TEL");
	}
	public void setCallNumber(String CallNumber){
		this.setInternal("CALL_NUMBER" ,CallNumber );
	}


	public String getCallNumber(){
		return (String)this.getInternal("CALL_NUMBER");
	}
	public void setGoDate(Date GoDate){
		this.setInternal("GO_DATE" ,GoDate );
	}


	public Date getGoDate(){
		return (Date)this.getInternal("GO_DATE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
}
