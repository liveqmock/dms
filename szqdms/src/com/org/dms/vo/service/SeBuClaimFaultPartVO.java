package com.org.dms.vo.service;
import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseVO;
import java.util.Date;
@SuppressWarnings("serial")
public class SeBuClaimFaultPartVO extends BaseVO{
    public SeBuClaimFaultPartVO(){
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("FIRST_PART_CODE",BaseVO.OP_STRING);
    	this.addField("PART_TYPE",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("OLD_PART_COUNT",BaseVO.OP_STRING);
    	this.addField("SEVERITY",BaseVO.OP_STRING);
    	this.addField("NEW_PART_STREAM",BaseVO.OP_STRING);
    	this.addField("NEW_PART_NAME",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("OLD_PART_ID",BaseVO.OP_STRING);
    	this.addField("FIRST_PART_NAME",BaseVO.OP_STRING);
    	this.addField("CLAIM_UPRICE",BaseVO.OP_STRING);
    	this.addField("FAULT_REASON",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("BRIDGE_SUPPLIER_NO",BaseVO.OP_STRING);
    	this.addField("OLD_PART_TYPE",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("NEW_PART_CODE",BaseVO.OP_STRING);
    	this.addField("MEASURES",BaseVO.OP_STRING);
    	this.addField("BRIDGE_CODE",BaseVO.OP_STRING);
    	this.addField("CLAIM_DTL_ID",BaseVO.OP_STRING);
    	this.addField("NEW_PART_ID",BaseVO.OP_STRING);
    	this.addField("OLD_PART_NAME",BaseVO.OP_STRING);
    	this.addField("OLD_SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("REPAY_UPRICE",BaseVO.OP_STRING);
    	this.addField("NEW_PART_FROM",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("IF_RETURN",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("OLD_PART_CODE",BaseVO.OP_STRING);
    	this.addField("FIRST_PART_ID",BaseVO.OP_STRING);
    	this.addField("FAULT_PART_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("BRIDGE_TYPE",BaseVO.OP_STRING);
    	this.addField("NEW_PART_COUNT",BaseVO.OP_STRING);
    	this.addField("CLAIM_COSTS",BaseVO.OP_STRING);
    	this.addField("CLAIM_ID",BaseVO.OP_STRING);
    	this.addField("OLD_PART_STREAM",BaseVO.OP_STRING);
    	this.addField("FAULT_TYPE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("NEW_SUPPLIER_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("FAULT_PART_ID","COMMON");
		this.bindFieldToDic("MEASURES",DicConstant.CLFS);//处理方式   
		this.bindFieldToDic("FAULT_TYPE",DicConstant.GZLB);//故障类别  
		this.bindFieldToDic("NEW_PART_FROM",DicConstant.XJLY);//新件来源   
    	this.setVOTableName("SE_BU_CLAIM_FAULT_PART");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setFirstPartCode(String FirstPartCode){
		this.setInternal("FIRST_PART_CODE" ,FirstPartCode );
	}


	public String getFirstPartCode(){
		return (String)this.getInternal("FIRST_PART_CODE");
	}
	public void setPartType(String PartType){
		this.setInternal("PART_TYPE" ,PartType );
	}


	public String getPartType(){
		return (String)this.getInternal("PART_TYPE");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setOldPartCount(String OldPartCount){
		this.setInternal("OLD_PART_COUNT" ,OldPartCount );
	}


	public String getOldPartCount(){
		return (String)this.getInternal("OLD_PART_COUNT");
	}
	public void setSeverity(String Severity){
		this.setInternal("SEVERITY" ,Severity );
	}


	public String getSeverity(){
		return (String)this.getInternal("SEVERITY");
	}
	public void setNewPartStream(String NewPartStream){
		this.setInternal("NEW_PART_STREAM" ,NewPartStream );
	}


	public String getNewPartStream(){
		return (String)this.getInternal("NEW_PART_STREAM");
	}
	public void setNewPartName(String NewPartName){
		this.setInternal("NEW_PART_NAME" ,NewPartName );
	}


	public String getNewPartName(){
		return (String)this.getInternal("NEW_PART_NAME");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setOldPartId(String OldPartId){
		this.setInternal("OLD_PART_ID" ,OldPartId );
	}


	public String getOldPartId(){
		return (String)this.getInternal("OLD_PART_ID");
	}
	public void setFirstPartName(String FirstPartName){
		this.setInternal("FIRST_PART_NAME" ,FirstPartName );
	}


	public String getFirstPartName(){
		return (String)this.getInternal("FIRST_PART_NAME");
	}
	public void setClaimUprice(String ClaimUprice){
		this.setInternal("CLAIM_UPRICE" ,ClaimUprice );
	}


	public String getClaimUprice(){
		return (String)this.getInternal("CLAIM_UPRICE");
	}
	public void setFaultReason(String FaultReason){
		this.setInternal("FAULT_REASON" ,FaultReason );
	}


	public String getFaultReason(){
		return (String)this.getInternal("FAULT_REASON");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setBridgeSupplierNo(String BridgeSupplierNo){
		this.setInternal("BRIDGE_SUPPLIER_NO" ,BridgeSupplierNo );
	}


	public String getBridgeSupplierNo(){
		return (String)this.getInternal("BRIDGE_SUPPLIER_NO");
	}
	public void setOldPartType(String OldPartType){
		this.setInternal("OLD_PART_TYPE" ,OldPartType );
	}


	public String getOldPartType(){
		return (String)this.getInternal("OLD_PART_TYPE");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setNewPartCode(String NewPartCode){
		this.setInternal("NEW_PART_CODE" ,NewPartCode );
	}


	public String getNewPartCode(){
		return (String)this.getInternal("NEW_PART_CODE");
	}
	public void setMeasures(String Measures){
		this.setInternal("MEASURES" ,Measures );
	}


	public String getMeasures(){
		return (String)this.getInternal("MEASURES");
	}
	public void setBridgeCode(String BridgeCode){
		this.setInternal("BRIDGE_CODE" ,BridgeCode );
	}


	public String getBridgeCode(){
		return (String)this.getInternal("BRIDGE_CODE");
	}
	public void setClaimDtlId(String ClaimDtlId){
		this.setInternal("CLAIM_DTL_ID" ,ClaimDtlId );
	}


	public String getClaimDtlId(){
		return (String)this.getInternal("CLAIM_DTL_ID");
	}
	public void setNewPartId(String NewPartId){
		this.setInternal("NEW_PART_ID" ,NewPartId );
	}


	public String getNewPartId(){
		return (String)this.getInternal("NEW_PART_ID");
	}
	public void setOldPartName(String OldPartName){
		this.setInternal("OLD_PART_NAME" ,OldPartName );
	}


	public String getOldPartName(){
		return (String)this.getInternal("OLD_PART_NAME");
	}
	public void setOldSupplierId(String OldSupplierId){
		this.setInternal("OLD_SUPPLIER_ID" ,OldSupplierId );
	}


	public String getOldSupplierId(){
		return (String)this.getInternal("OLD_SUPPLIER_ID");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setRepayUprice(String RepayUprice){
		this.setInternal("REPAY_UPRICE" ,RepayUprice );
	}


	public String getRepayUprice(){
		return (String)this.getInternal("REPAY_UPRICE");
	}
	public void setNewPartFrom(String NewPartFrom){
		this.setInternal("NEW_PART_FROM" ,NewPartFrom );
	}


	public String getNewPartFrom(){
		return (String)this.getInternal("NEW_PART_FROM");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setIfReturn(String IfReturn){
		this.setInternal("IF_RETURN" ,IfReturn );
	}


	public String getIfReturn(){
		return (String)this.getInternal("IF_RETURN");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setOldPartCode(String OldPartCode){
		this.setInternal("OLD_PART_CODE" ,OldPartCode );
	}


	public String getOldPartCode(){
		return (String)this.getInternal("OLD_PART_CODE");
	}
	public void setFirstPartId(String FirstPartId){
		this.setInternal("FIRST_PART_ID" ,FirstPartId );
	}


	public String getFirstPartId(){
		return (String)this.getInternal("FIRST_PART_ID");
	}
	public void setFaultPartId(String FaultPartId){
		this.setInternal("FAULT_PART_ID" ,FaultPartId );
	}


	public String getFaultPartId(){
		return (String)this.getInternal("FAULT_PART_ID");
	}
	public void setBridgeType(String BridgeType){
		this.setInternal("BRIDGE_TYPE" ,BridgeType );
	}


	public String getBridgeType(){
		return (String)this.getInternal("BRIDGE_TYPE");
	}
	public void setNewPartCount(String NewPartCount){
		this.setInternal("NEW_PART_COUNT" ,NewPartCount );
	}


	public String getNewPartCount(){
		return (String)this.getInternal("NEW_PART_COUNT");
	}
	public void setClaimCosts(String ClaimCosts){
		this.setInternal("CLAIM_COSTS" ,ClaimCosts );
	}


	public String getClaimCosts(){
		return (String)this.getInternal("CLAIM_COSTS");
	}
	public void setClaimId(String ClaimId){
		this.setInternal("CLAIM_ID" ,ClaimId );
	}


	public String getClaimId(){
		return (String)this.getInternal("CLAIM_ID");
	}
	public void setOldPartStream(String OldPartStream){
		this.setInternal("OLD_PART_STREAM" ,OldPartStream );
	}


	public String getOldPartStream(){
		return (String)this.getInternal("OLD_PART_STREAM");
	}
	public void setFaultType(String FaultType){
		this.setInternal("FAULT_TYPE" ,FaultType );
	}


	public String getFaultType(){
		return (String)this.getInternal("FAULT_TYPE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setNewSupplierId(String NewSupplierId){
		this.setInternal("NEW_SUPPLIER_ID" ,NewSupplierId );
	}


	public String getNewSupplierId(){
		return (String)this.getInternal("NEW_SUPPLIER_ID");
	}
}
