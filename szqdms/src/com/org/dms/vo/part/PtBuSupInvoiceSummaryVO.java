package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuSupInvoiceSummaryVO extends BaseVO{
    public PtBuSupInvoiceSummaryVO(){
    	//ÉèÖÃ×Ö¶ÎÐÅÏ¢
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("ACCOUNT_TYPE",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("UN_INVOICE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("RETURN_AMOUNT",BaseVO.OP_STRING);
    	this.addField("PLAN_AMOUNT",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("INVOICE_MONTH",BaseVO.OP_STRING);
    	this.addField("IN_COUNT",BaseVO.OP_STRING);
    	this.addField("SUM_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("INVOICE_STATUS",BaseVO.OP_STRING);
    	this.addField("ADUIT_REMARKS",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("INVOICE_NO",BaseVO.OP_STRING);
    	this.addField("IN_AMOUNT",BaseVO.OP_STRING);
    	this.addField("SETTLE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("RETURN_COUNT",BaseVO.OP_STRING);
    	this.addField("SETTLE_STATUS",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("INVOICE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("HAS_INVOICE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("UNSETTLE_AMOUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("SUM_ID","COMMON");
    	this.setVOTableName("PT_BU_SUP_INVOICE_SUMMARY");
}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setAccountType(String AccountType){
		this.setInternal("ACCOUNT_TYPE" ,AccountType );
	}


	public String getAccountType(){
		return (String)this.getInternal("ACCOUNT_TYPE");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setUnInvoiceAmount(String UnInvoiceAmount){
		this.setInternal("UN_INVOICE_AMOUNT" ,UnInvoiceAmount );
	}


	public String getUnInvoiceAmount(){
		return (String)this.getInternal("UN_INVOICE_AMOUNT");
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
	public void setReturnAmount(String ReturnAmount){
		this.setInternal("RETURN_AMOUNT" ,ReturnAmount );
	}


	public String getReturnAmount(){
		return (String)this.getInternal("RETURN_AMOUNT");
	}
	public void setPlanAmount(String PlanAmount){
		this.setInternal("PLAN_AMOUNT" ,PlanAmount );
	}


	public String getPlanAmount(){
		return (String)this.getInternal("PLAN_AMOUNT");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setInvoiceMonth(String InvoiceMonth){
		this.setInternal("INVOICE_MONTH" ,InvoiceMonth );
	}


	public String getInvoiceMonth(){
		return (String)this.getInternal("INVOICE_MONTH");
	}
	public void setInCount(String InCount){
		this.setInternal("IN_COUNT" ,InCount );
	}


	public String getInCount(){
		return (String)this.getInternal("IN_COUNT");
	}
	public void setSumId(String SumId){
		this.setInternal("SUM_ID" ,SumId );
	}


	public String getSumId(){
		return (String)this.getInternal("SUM_ID");
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
	public void setInvoiceStatus(String InvoiceStatus){
		this.setInternal("INVOICE_STATUS" ,InvoiceStatus );
	}


	public String getInvoiceStatus(){
		return (String)this.getInternal("INVOICE_STATUS");
	}
	public void setAduitRemarks(String AduitRemarks){
		this.setInternal("ADUIT_REMARKS" ,AduitRemarks );
	}


	public String getAduitRemarks(){
		return (String)this.getInternal("ADUIT_REMARKS");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setInvoiceNo(String InvoiceNo){
		this.setInternal("INVOICE_NO" ,InvoiceNo );
	}


	public String getInvoiceNo(){
		return (String)this.getInternal("INVOICE_NO");
	}
	public void setInAmount(String InAmount){
		this.setInternal("IN_AMOUNT" ,InAmount );
	}


	public String getInAmount(){
		return (String)this.getInternal("IN_AMOUNT");
	}
	public void setSettleAmount(String SettleAmount){
		this.setInternal("SETTLE_AMOUNT" ,SettleAmount );
	}


	public String getSettleAmount(){
		return (String)this.getInternal("SETTLE_AMOUNT");
	}
	public void setReturnCount(String ReturnCount){
		this.setInternal("RETURN_COUNT" ,ReturnCount );
	}


	public String getReturnCount(){
		return (String)this.getInternal("RETURN_COUNT");
	}
	public void setSettleStatus(String SettleStatus){
		this.setInternal("SETTLE_STATUS" ,SettleStatus );
	}


	public String getSettleStatus(){
		return (String)this.getInternal("SETTLE_STATUS");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setInvoiceAmount(String InvoiceAmount){
		this.setInternal("INVOICE_AMOUNT" ,InvoiceAmount );
	}


	public String getInvoiceAmount(){
		return (String)this.getInternal("INVOICE_AMOUNT");
	}
	public void setHasInvoiceAmount(String HasInvoiceAmount){
		this.setInternal("HAS_INVOICE_AMOUNT" ,HasInvoiceAmount );
	}


	public String getHasInvoiceAmount(){
		return (String)this.getInternal("HAS_INVOICE_AMOUNT");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
	public void setUnsettleAmount(String UnsettleAmount){
		this.setInternal("UNSETTLE_AMOUNT" ,UnsettleAmount );
	}


	public String getUnsettleAmount(){
		return (String)this.getInternal("UNSETTLE_AMOUNT");
	}
}
