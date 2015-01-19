package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBaTaskAmountTmpVO extends BaseVO{
    public SeBaTaskAmountTmpVO(){
    	//�����ֶ���Ϣ
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("TIME_NAME",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("POSITION_NAME",BaseVO.OP_STRING);
    	this.addField("POSITION_CODE",BaseVO.OP_STRING);
    	this.addField("AMOUNT_SET",BaseVO.OP_STRING);
    	this.addField("USER_TYPE",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("MODELS_ID",BaseVO.OP_STRING);
    	this.addField("MODELS_CODE",BaseVO.OP_STRING);
    	this.addField("STATUS_NA",BaseVO.OP_STRING);
    	this.addField("ROW_NUM",BaseVO.OP_STRING);
    	this.addField("TIME_CODE",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("POSITION_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("SE_BA_TASK_AMOUNT_TMP");
}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setTimeName(String TimeName){
		this.setInternal("TIME_NAME" ,TimeName );
	}


	public String getTimeName(){
		return (String)this.getInternal("TIME_NAME");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setPositionName(String PositionName){
		this.setInternal("POSITION_NAME" ,PositionName );
	}


	public String getPositionName(){
		return (String)this.getInternal("POSITION_NAME");
	}
	public void setPositionCode(String PositionCode){
		this.setInternal("POSITION_CODE" ,PositionCode );
	}


	public String getPositionCode(){
		return (String)this.getInternal("POSITION_CODE");
	}
	public void setAmountSet(String AmountSet){
		this.setInternal("AMOUNT_SET" ,AmountSet );
	}


	public String getAmountSet(){
		return (String)this.getInternal("AMOUNT_SET");
	}
	public void setUserType(String UserType){
		this.setInternal("USER_TYPE" ,UserType );
	}


	public String getUserType(){
		return (String)this.getInternal("USER_TYPE");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setUserAccount(String UserAccount){
		this.setInternal("USER_ACCOUNT" ,UserAccount );
	}


	public String getUserAccount(){
		return (String)this.getInternal("USER_ACCOUNT");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setModelsId(String ModelsId){
		this.setInternal("MODELS_ID" ,ModelsId );
	}


	public String getModelsId(){
		return (String)this.getInternal("MODELS_ID");
	}
	public void setModelsCode(String ModelsCode){
		this.setInternal("MODELS_CODE" ,ModelsCode );
	}


	public String getModelsCode(){
		return (String)this.getInternal("MODELS_CODE");
	}
	public void setStatusNa(String StatusNa){
		this.setInternal("STATUS_NA" ,StatusNa );
	}


	public String getStatusNa(){
		return (String)this.getInternal("STATUS_NA");
	}
	public void setRowNum(String RowNum){
		this.setInternal("ROW_NUM" ,RowNum );
	}


	public String getRowNum(){
		return (String)this.getInternal("ROW_NUM");
	}
	public void setTimeCode(String TimeCode){
		this.setInternal("TIME_CODE" ,TimeCode );
	}


	public String getTimeCode(){
		return (String)this.getInternal("TIME_CODE");
	}
	public void setTmpId(String TmpId){
		this.setInternal("TMP_ID" ,TmpId );
	}


	public String getTmpId(){
		return (String)this.getInternal("TMP_ID");
	}
	public void setPositionId(String PositionId){
		this.setInternal("POSITION_ID" ,PositionId );
	}


	public String getPositionId(){
		return (String)this.getInternal("POSITION_ID");
	}
}
