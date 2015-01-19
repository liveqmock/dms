package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaSupplierVO extends BaseVO{
    public PtBaSupplierVO(){
    	//ÉèÖÃ×Ö¶ÎÐÅÏ¢
    	this.addField("PART_ACCESS_TEL",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("PART_IDENTIFY",BaseVO.OP_STRING);
    	this.addField("GET_NAME",BaseVO.OP_STRING);
    	this.addField("GUARANTEE_MONEY",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("OPEN_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_QUALIFY",BaseVO.OP_STRING);
    	this.addField("IF_CLAIM_CHECK",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("TAX_TYPE",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("SE_ACCESS_TEL",BaseVO.OP_STRING);
    	this.addField("ACTUL_SUPPLIER",BaseVO.OP_STRING);
    	this.addField("RECOVERY_CLAUSE",BaseVO.OP_STRING);
    	this.addField("IF_ARMY",BaseVO.OP_STRING);
    	this.addField("WARRANTY_DAYS",BaseVO.OP_STRING);
    	this.addField("ACCOUNT",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("GET_PHONE",BaseVO.OP_STRING);
    	this.addField("ERP_NO",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("BUSINESS_PERSON",BaseVO.OP_STRING);
    	this.addField("TAX_NO",BaseVO.OP_STRING);
    	this.addField("LEGAL_PERSON",BaseVO.OP_STRING);
    	this.addField("BUSINESS_PERSON_PHONE",BaseVO.OP_STRING);
    	this.addField("PART_ACCESS",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("WARRANTY_MONEY",BaseVO.OP_STRING);
    	this.addField("LEGAL_PERSON_PHONE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("OPEN_BANK",BaseVO.OP_STRING);
    	this.addField("EXCLUSIVE_TYPE",BaseVO.OP_STRING);
    	this.addField("WARRANTY_PERIOD",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_STATUS",BaseVO.OP_STRING);
    	this.addField("FAX_NO",BaseVO.OP_STRING);
    	this.addField("GET_NO",BaseVO.OP_STRING);
    	this.addField("ADDRESS",BaseVO.OP_STRING);
    	this.addField("TAX_RATE",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("REAL_NO",BaseVO.OP_STRING);
    	this.addField("SE_ACCESS",BaseVO.OP_STRING);
    	this.addField("FAX",BaseVO.OP_STRING);
    	this.addField("SE_IDENTIFY",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
		this.bindFieldToSequence("SUPPLIER_ID","COMMON");
    	this.setVOTableName("PT_BA_SUPPLIER");
}
	public void setPartAccessTel(String PartAccessTel){
		this.setInternal("PART_ACCESS_TEL" ,PartAccessTel );
	}


	public String getPartAccessTel(){
		return (String)this.getInternal("PART_ACCESS_TEL");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setPartIdentify(String PartIdentify){
		this.setInternal("PART_IDENTIFY" ,PartIdentify );
	}


	public String getPartIdentify(){
		return (String)this.getInternal("PART_IDENTIFY");
	}
	public void setGetName(String GetName){
		this.setInternal("GET_NAME" ,GetName );
	}


	public String getGetName(){
		return (String)this.getInternal("GET_NAME");
	}
	public void setGuaranteeMoney(String GuaranteeMoney){
		this.setInternal("GUARANTEE_MONEY" ,GuaranteeMoney );
	}


	public String getGuaranteeMoney(){
		return (String)this.getInternal("GUARANTEE_MONEY");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setOpenAccount(String OpenAccount){
		this.setInternal("OPEN_ACCOUNT" ,OpenAccount );
	}


	public String getOpenAccount(){
		return (String)this.getInternal("OPEN_ACCOUNT");
	}
	public void setSupplierQualify(String SupplierQualify){
		this.setInternal("SUPPLIER_QUALIFY" ,SupplierQualify );
	}


	public String getSupplierQualify(){
		return (String)this.getInternal("SUPPLIER_QUALIFY");
	}
	public void setIfClaimCheck(String IfClaimCheck){
		this.setInternal("IF_CLAIM_CHECK" ,IfClaimCheck );
	}


	public String getIfClaimCheck(){
		return (String)this.getInternal("IF_CLAIM_CHECK");
	}
	public void setSupplierId(String SupplierId){
		this.setInternal("SUPPLIER_ID" ,SupplierId );
	}


	public String getSupplierId(){
		return (String)this.getInternal("SUPPLIER_ID");
	}
	public void setTaxType(String TaxType){
		this.setInternal("TAX_TYPE" ,TaxType );
	}


	public String getTaxType(){
		return (String)this.getInternal("TAX_TYPE");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setSeAccessTel(String SeAccessTel){
		this.setInternal("SE_ACCESS_TEL" ,SeAccessTel );
	}


	public String getSeAccessTel(){
		return (String)this.getInternal("SE_ACCESS_TEL");
	}
	public void setActulSupplier(String ActulSupplier){
		this.setInternal("ACTUL_SUPPLIER" ,ActulSupplier );
	}


	public String getActulSupplier(){
		return (String)this.getInternal("ACTUL_SUPPLIER");
	}
	public void setRecoveryClause(String RecoveryClause){
		this.setInternal("RECOVERY_CLAUSE" ,RecoveryClause );
	}


	public String getRecoveryClause(){
		return (String)this.getInternal("RECOVERY_CLAUSE");
	}
	public void setIfArmy(String IfArmy){
		this.setInternal("IF_ARMY" ,IfArmy );
	}


	public String getIfArmy(){
		return (String)this.getInternal("IF_ARMY");
	}
	public void setWarrantyDays(String WarrantyDays){
		this.setInternal("WARRANTY_DAYS" ,WarrantyDays );
	}


	public String getWarrantyDays(){
		return (String)this.getInternal("WARRANTY_DAYS");
	}
	public void setAccount(String Account){
		this.setInternal("ACCOUNT" ,Account );
	}


	public String getAccount(){
		return (String)this.getInternal("ACCOUNT");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setGetPhone(String GetPhone){
		this.setInternal("GET_PHONE" ,GetPhone );
	}


	public String getGetPhone(){
		return (String)this.getInternal("GET_PHONE");
	}
	public void setErpNo(String ErpNo){
		this.setInternal("ERP_NO" ,ErpNo );
	}


	public String getErpNo(){
		return (String)this.getInternal("ERP_NO");
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
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setBusinessPerson(String BusinessPerson){
		this.setInternal("BUSINESS_PERSON" ,BusinessPerson );
	}


	public String getBusinessPerson(){
		return (String)this.getInternal("BUSINESS_PERSON");
	}
	public void setTaxNo(String TaxNo){
		this.setInternal("TAX_NO" ,TaxNo );
	}


	public String getTaxNo(){
		return (String)this.getInternal("TAX_NO");
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
	public void setPartAccess(String PartAccess){
		this.setInternal("PART_ACCESS" ,PartAccess );
	}


	public String getPartAccess(){
		return (String)this.getInternal("PART_ACCESS");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setWarrantyMoney(String WarrantyMoney){
		this.setInternal("WARRANTY_MONEY" ,WarrantyMoney );
	}


	public String getWarrantyMoney(){
		return (String)this.getInternal("WARRANTY_MONEY");
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
	public void setOpenBank(String OpenBank){
		this.setInternal("OPEN_BANK" ,OpenBank );
	}


	public String getOpenBank(){
		return (String)this.getInternal("OPEN_BANK");
	}
	public void setExclusiveType(String ExclusiveType){
		this.setInternal("EXCLUSIVE_TYPE" ,ExclusiveType );
	}


	public String getExclusiveType(){
		return (String)this.getInternal("EXCLUSIVE_TYPE");
	}
	public void setWarrantyPeriod(String WarrantyPeriod){
		this.setInternal("WARRANTY_PERIOD" ,WarrantyPeriod );
	}


	public String getWarrantyPeriod(){
		return (String)this.getInternal("WARRANTY_PERIOD");
	}
	public void setSupplierStatus(String SupplierStatus){
		this.setInternal("SUPPLIER_STATUS" ,SupplierStatus );
	}


	public String getSupplierStatus(){
		return (String)this.getInternal("SUPPLIER_STATUS");
	}
	public void setFaxNo(String FaxNo){
		this.setInternal("FAX_NO" ,FaxNo );
	}


	public String getFaxNo(){
		return (String)this.getInternal("FAX_NO");
	}
	public void setGetNo(String GetNo){
		this.setInternal("GET_NO" ,GetNo );
	}


	public String getGetNo(){
		return (String)this.getInternal("GET_NO");
	}
	public void setAddress(String Address){
		this.setInternal("ADDRESS" ,Address );
	}


	public String getAddress(){
		return (String)this.getInternal("ADDRESS");
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
	public void setRealNo(String RealNo){
		this.setInternal("REAL_NO" ,RealNo );
	}


	public String getRealNo(){
		return (String)this.getInternal("REAL_NO");
	}
	public void setSeAccess(String SeAccess){
		this.setInternal("SE_ACCESS" ,SeAccess );
	}


	public String getSeAccess(){
		return (String)this.getInternal("SE_ACCESS");
	}
	public void setFax(String Fax){
		this.setInternal("FAX" ,Fax );
	}


	public String getFax(){
		return (String)this.getInternal("FAX");
	}
	public void setSeIdentify(String SeIdentify){
		this.setInternal("SE_IDENTIFY" ,SeIdentify );
	}


	public String getSeIdentify(){
		return (String)this.getInternal("SE_IDENTIFY");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
}
