package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuRecoveryVO extends BaseVO{
    public SeBuRecoveryVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("UNKNOWN_TOTAL_COST",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("RECOVERY_NO",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("RECOVERY_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("ADJUST_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("ADJUST_TIME", "yyyy-MM-dd");
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("RECOVERY_PRICE",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("RECOVERY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("RECOVERY_DATE", "yyyy-MM-dd");
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("OLDPART_MANAGE_COST",BaseVO.OP_STRING);
    	this.addField("TOTAL_COST",BaseVO.OP_STRING);
    	this.addField("ADJUST_USER",BaseVO.OP_STRING);
    	this.addField("ADJUST_COST",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("CLAIM_MANAGE_COST",BaseVO.OP_STRING);
    	this.addField("CLAIM_TOTAL_COST",BaseVO.OP_STRING);
		this.bindFieldToSequence("RECOVERY_ID","COMMON");
    	this.setVOTableName("SE_BU_RECOVERY");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setUnknownTotalCost(String UnknownTotalCost){
		this.setInternal("UNKNOWN_TOTAL_COST" ,UnknownTotalCost );
	}


	public String getUnknownTotalCost(){
		return (String)this.getInternal("UNKNOWN_TOTAL_COST");
	}
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}


	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
	public void setRecoveryNo(String RecoveryNo){
		this.setInternal("RECOVERY_NO" ,RecoveryNo );
	}


	public String getRecoveryNo(){
		return (String)this.getInternal("RECOVERY_NO");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setRecoveryId(String RecoveryId){
		this.setInternal("RECOVERY_ID" ,RecoveryId );
	}


	public String getRecoveryId(){
		return (String)this.getInternal("RECOVERY_ID");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setAdjustTime(Date AdjustTime){
		this.setInternal("ADJUST_TIME" ,AdjustTime );
	}


	public Date getAdjustTime(){
		return (Date)this.getInternal("ADJUST_TIME");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setSupplierId(String SupplierId){
		this.setInternal("SUPPLIER_ID" ,SupplierId );
	}


	public String getSupplierId(){
		return (String)this.getInternal("SUPPLIER_ID");
	}
	public void setRecoveryPrice(String RecoveryPrice){
		this.setInternal("RECOVERY_PRICE" ,RecoveryPrice );
	}


	public String getRecoveryPrice(){
		return (String)this.getInternal("RECOVERY_PRICE");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setRecoveryDate(Date RecoveryDate){
		this.setInternal("RECOVERY_DATE" ,RecoveryDate );
	}


	public Date getRecoveryDate(){
		return (Date)this.getInternal("RECOVERY_DATE");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setOldpartManageCost(String OldpartManageCost){
		this.setInternal("OLDPART_MANAGE_COST" ,OldpartManageCost );
	}


	public String getOldpartManageCost(){
		return (String)this.getInternal("OLDPART_MANAGE_COST");
	}
	public void setTotalCost(String TotalCost){
		this.setInternal("TOTAL_COST" ,TotalCost );
	}


	public String getTotalCost(){
		return (String)this.getInternal("TOTAL_COST");
	}
	public void setAdjustUser(String AdjustUser){
		this.setInternal("ADJUST_USER" ,AdjustUser );
	}


	public String getAdjustUser(){
		return (String)this.getInternal("ADJUST_USER");
	}
	public void setAdjustCost(String AdjustCost){
		this.setInternal("ADJUST_COST" ,AdjustCost );
	}


	public String getAdjustCost(){
		return (String)this.getInternal("ADJUST_COST");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setClaimManageCost(String ClaimManageCost){
		this.setInternal("CLAIM_MANAGE_COST" ,ClaimManageCost );
	}


	public String getClaimManageCost(){
		return (String)this.getInternal("CLAIM_MANAGE_COST");
	}
	public void setClaimTotalCost(String ClaimTotalCost){
		this.setInternal("CLAIM_TOTAL_COST" ,ClaimTotalCost );
	}


	public String getClaimTotalCost(){
		return (String)this.getInternal("CLAIM_TOTAL_COST");
	}
}
