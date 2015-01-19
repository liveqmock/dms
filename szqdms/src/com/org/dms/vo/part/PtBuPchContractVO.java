package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuPchContractVO extends BaseVO{
    public PtBuPchContractVO(){
    	//设置字段信息
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("GUARANTEE_MONEY",BaseVO.OP_STRING);
    	this.addField("SIGN_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("SIGN_DATE", "yyyy-MM-dd");
    	this.addField("OPEN_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_QUALIFY",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("RECOVERY_CLAUSE",BaseVO.OP_STRING);
    	this.addField("CONTRACT_NO",BaseVO.OP_STRING);
    	this.addField("EFFECTIVE_CYCLE_BEGIN",BaseVO.OP_DATE);
		this.setFieldDateFormat("EFFECTIVE_CYCLE_BEGIN", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("INVOICE_TYPE",BaseVO.OP_STRING);
    	this.addField("EFFECTIVE_CYCLE_END",BaseVO.OP_DATE);
		this.setFieldDateFormat("EFFECTIVE_CYCLE_END", "yyyy-MM-dd");
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("BUSINESS_PERSON",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("LEGAL_PERSON",BaseVO.OP_STRING);
    	this.addField("BUSINESS_PERSON_PHONE",BaseVO.OP_STRING);
    	this.addField("ERP_NUM",BaseVO.OP_STRING);
    	this.addField("CONTRACT_STATUS",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("CONTRACT_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("LEGAL_PERSON_PHONE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("CONTRACT_NAME",BaseVO.OP_STRING);
    	this.addField("CONTRACT_TYPE",BaseVO.OP_STRING);
    	this.addField("WARRANTY_PERIOD",BaseVO.OP_STRING);
    	this.addField("TAX_RATE",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
		this.bindFieldToSequence("CONTRACT_ID","COMMON");
    	this.setVOTableName("PT_BU_PCH_CONTRACT");
}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setGuaranteeMoney(String GuaranteeMoney){
		this.setInternal("GUARANTEE_MONEY" ,GuaranteeMoney );
	}


	public String getGuaranteeMoney(){
		return (String)this.getInternal("GUARANTEE_MONEY");
	}
	public void setSignDate(Date SignDate){
		this.setInternal("SIGN_DATE" ,SignDate );
	}


	public Date getSignDate(){
		return (Date)this.getInternal("SIGN_DATE");
	}
	public void setOpenAccount(String OpenAccount){
		this.setInternal("OPEN_ACCOUNT" ,OpenAccount );
	}


	public String getOpenAccount(){
		return (String)this.getInternal("OPEN_ACCOUNT");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setSupplierQualify(String SupplierQualify){
		this.setInternal("SUPPLIER_QUALIFY" ,SupplierQualify );
	}


	public String getSupplierQualify(){
		return (String)this.getInternal("SUPPLIER_QUALIFY");
	}
	public void setSupplierId(String SupplierId){
		this.setInternal("SUPPLIER_ID" ,SupplierId );
	}


	public String getSupplierId(){
		return (String)this.getInternal("SUPPLIER_ID");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setRecoveryClause(String RecoveryClause){
		this.setInternal("RECOVERY_CLAUSE" ,RecoveryClause );
	}


	public String getRecoveryClause(){
		return (String)this.getInternal("RECOVERY_CLAUSE");
	}
	public void setContractNo(String ContractNo){
		this.setInternal("CONTRACT_NO" ,ContractNo );
	}


	public String getContractNo(){
		return (String)this.getInternal("CONTRACT_NO");
	}
	public void setEffectiveCycleBegin(Date EffectiveCycleBegin){
		this.setInternal("EFFECTIVE_CYCLE_BEGIN" ,EffectiveCycleBegin );
	}


	public Date getEffectiveCycleBegin(){
		return (Date)this.getInternal("EFFECTIVE_CYCLE_BEGIN");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setInvoiceType(String InvoiceType){
		this.setInternal("INVOICE_TYPE" ,InvoiceType );
	}


	public String getInvoiceType(){
		return (String)this.getInternal("INVOICE_TYPE");
	}
	public void setEffectiveCycleEnd(Date EffectiveCycleEnd){
		this.setInternal("EFFECTIVE_CYCLE_END" ,EffectiveCycleEnd );
	}


	public Date getEffectiveCycleEnd(){
		return (Date)this.getInternal("EFFECTIVE_CYCLE_END");
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
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}


	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
	public void setBusinessPerson(String BusinessPerson){
		this.setInternal("BUSINESS_PERSON" ,BusinessPerson );
	}


	public String getBusinessPerson(){
		return (String)this.getInternal("BUSINESS_PERSON");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setLegalPerson(String LegalPerson){
		this.setInternal("LEGAL_PERSON" ,LegalPerson );
	}


	public String getLegalPerson(){
		return (String)this.getInternal("LEGAL_PERSON");
	}
	public void setBusinessPersonPhone(String BusinessPersonPhone){
		this.setInternal("BUSINESS_PERSON_PHONE" ,BusinessPersonPhone );
	}


	public String getBusinessPersonPhone(){
		return (String)this.getInternal("BUSINESS_PERSON_PHONE");
	}
	public void setErpNum(String ErpNum){
		this.setInternal("ERP_NUM" ,ErpNum );
	}


	public String getErpNum(){
		return (String)this.getInternal("ERP_NUM");
	}
	public void setContractStatus(String ContractStatus){
		this.setInternal("CONTRACT_STATUS" ,ContractStatus );
	}


	public String getContractStatus(){
		return (String)this.getInternal("CONTRACT_STATUS");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setContractId(String ContractId){
		this.setInternal("CONTRACT_ID" ,ContractId );
	}


	public String getContractId(){
		return (String)this.getInternal("CONTRACT_ID");
	}
	public void setLegalPersonPhone(String LegalPersonPhone){
		this.setInternal("LEGAL_PERSON_PHONE" ,LegalPersonPhone );
	}


	public String getLegalPersonPhone(){
		return (String)this.getInternal("LEGAL_PERSON_PHONE");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setContractName(String ContractName){
		this.setInternal("CONTRACT_NAME" ,ContractName );
	}


	public String getContractName(){
		return (String)this.getInternal("CONTRACT_NAME");
	}
	public void setContractType(String ContractType){
		this.setInternal("CONTRACT_TYPE" ,ContractType );
	}


	public String getContractType(){
		return (String)this.getInternal("CONTRACT_TYPE");
	}
	public void setWarrantyPeriod(String WarrantyPeriod){
		this.setInternal("WARRANTY_PERIOD" ,WarrantyPeriod );
	}


	public String getWarrantyPeriod(){
		return (String)this.getInternal("WARRANTY_PERIOD");
	}
	public void setTaxRate(String TaxRate){
		this.setInternal("TAX_RATE" ,TaxRate );
	}


	public String getTaxRate(){
		return (String)this.getInternal("TAX_RATE");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
}
