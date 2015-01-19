package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuAccountTransferVO extends BaseVO{
    public PtBuAccountTransferVO(){
    	//�����ֶ���Ϣ
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("APPLY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("DEALER_CODE",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("TRANSFER_STATUS",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("OUT_TYPE",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("AMOUNT",BaseVO.OP_STRING);
    	this.addField("TRANSFER_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("IN_TYPE",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("TRANSFER_ID","COMMON");
		this.bindFieldToDic("IN_TYPE", "ZJZHLX");
		this.bindFieldToDic("OUT_TYPE", "ZJZHLX");
		this.bindFieldToDic("TRANSFER_STATUS", "ZZZT");
    	this.setVOTableName("PT_BU_ACCOUNT_TRANSFER");
}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setOrgName(String OrgName){
		this.setInternal("ORG_NAME" ,OrgName );
	}


	public String getOrgName(){
		return (String)this.getInternal("ORG_NAME");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setApplyDate(Date ApplyDate){
		this.setInternal("APPLY_DATE" ,ApplyDate );
	}


	public Date getApplyDate(){
		return (Date)this.getInternal("APPLY_DATE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setDealerCode(String DealerCode){
		this.setInternal("DEALER_CODE" ,DealerCode );
	}


	public String getDealerCode(){
		return (String)this.getInternal("DEALER_CODE");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setTransferStatus(String TransferStatus){
		this.setInternal("TRANSFER_STATUS" ,TransferStatus );
	}


	public String getTransferStatus(){
		return (String)this.getInternal("TRANSFER_STATUS");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setOutType(String OutType){
		this.setInternal("OUT_TYPE" ,OutType );
	}


	public String getOutType(){
		return (String)this.getInternal("OUT_TYPE");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setAmount(String Amount){
		this.setInternal("AMOUNT" ,Amount );
	}


	public String getAmount(){
		return (String)this.getInternal("AMOUNT");
	}
	public void setTransferId(String TransferId){
		this.setInternal("TRANSFER_ID" ,TransferId );
	}


	public String getTransferId(){
		return (String)this.getInternal("TRANSFER_ID");
	}
	public void setInType(String InType){
		this.setInternal("IN_TYPE" ,InType );
	}


	public String getInType(){
		return (String)this.getInternal("IN_TYPE");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
}
