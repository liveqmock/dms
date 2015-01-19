package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuPchReturnVO extends BaseVO{
    public PtBuPchReturnVO(){
    	//�����ֶ���Ϣ
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("IS_AGREED",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("PURCHASE_ID",BaseVO.OP_STRING);
    	this.addField("PLAN_AMOUNT",BaseVO.OP_STRING);
    	this.addField("INVOICE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("INVOICE_DATE", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("AMOUNT",BaseVO.OP_STRING);
    	this.addField("RETURN_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("SETTLE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("SETTLE_DATE", "yyyy-MM-dd");
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("INVOICE_STATUS",BaseVO.OP_STRING);
    	this.addField("ORDER_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("ORDER_DATE", "yyyy-MM-dd");
    	this.addField("INVOICE_REMARKS",BaseVO.OP_STRING);
    	this.addField("SIGN_STAUTS",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("RETURN_TYPE",BaseVO.OP_STRING);
    	this.addField("ADVICE",BaseVO.OP_STRING);
    	this.addField("SETTLE_REMARKS",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("RETURN_NO",BaseVO.OP_STRING);
    	this.addField("INVOICE_NO",BaseVO.OP_STRING);
    	this.addField("PURCHASE_NO",BaseVO.OP_STRING);
    	this.addField("CLOSE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("CLOSE_DATE", "yyyy-MM-dd");
    	this.addField("SETTLE_STATUS",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("COUNT",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("RETURN_STATUS",BaseVO.OP_STRING);
    	this.addField("ORDER_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("RETURN_ID","COMMON");
    	this.setVOTableName("PT_BU_PCH_RETURN");
}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setIsAgreed(String IsAgreed){
		this.setInternal("IS_AGREED" ,IsAgreed );
	}


	public String getIsAgreed(){
		return (String)this.getInternal("IS_AGREED");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setSupplierId(String SupplierId){
		this.setInternal("SUPPLIER_ID" ,SupplierId );
	}


	public String getSupplierId(){
		return (String)this.getInternal("SUPPLIER_ID");
	}
	public void setPurchaseId(String PurchaseId){
		this.setInternal("PURCHASE_ID" ,PurchaseId );
	}


	public String getPurchaseId(){
		return (String)this.getInternal("PURCHASE_ID");
	}
	public void setPlanAmount(String PlanAmount){
		this.setInternal("PLAN_AMOUNT" ,PlanAmount );
	}


	public String getPlanAmount(){
		return (String)this.getInternal("PLAN_AMOUNT");
	}
	public void setInvoiceDate(Date InvoiceDate){
		this.setInternal("INVOICE_DATE" ,InvoiceDate );
	}


	public Date getInvoiceDate(){
		return (Date)this.getInternal("INVOICE_DATE");
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
	public void setReturnId(String ReturnId){
		this.setInternal("RETURN_ID" ,ReturnId );
	}


	public String getReturnId(){
		return (String)this.getInternal("RETURN_ID");
	}
	public void setSettleDate(Date SettleDate){
		this.setInternal("SETTLE_DATE" ,SettleDate );
	}


	public Date getSettleDate(){
		return (Date)this.getInternal("SETTLE_DATE");
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
	public void setOrderDate(Date OrderDate){
		this.setInternal("ORDER_DATE" ,OrderDate );
	}


	public Date getOrderDate(){
		return (Date)this.getInternal("ORDER_DATE");
	}
	public void setInvoiceRemarks(String InvoiceRemarks){
		this.setInternal("INVOICE_REMARKS" ,InvoiceRemarks );
	}


	public String getInvoiceRemarks(){
		return (String)this.getInternal("INVOICE_REMARKS");
	}
	public void setSignStauts(String SignStauts){
		this.setInternal("SIGN_STAUTS" ,SignStauts );
	}


	public String getSignStauts(){
		return (String)this.getInternal("SIGN_STAUTS");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setReturnType(String ReturnType){
		this.setInternal("RETURN_TYPE" ,ReturnType );
	}


	public String getReturnType(){
		return (String)this.getInternal("RETURN_TYPE");
	}
	public void setAdvice(String Advice){
		this.setInternal("ADVICE" ,Advice );
	}


	public String getAdvice(){
		return (String)this.getInternal("ADVICE");
	}
	public void setSettleRemarks(String SettleRemarks){
		this.setInternal("SETTLE_REMARKS" ,SettleRemarks );
	}


	public String getSettleRemarks(){
		return (String)this.getInternal("SETTLE_REMARKS");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setReturnNo(String ReturnNo){
		this.setInternal("RETURN_NO" ,ReturnNo );
	}


	public String getReturnNo(){
		return (String)this.getInternal("RETURN_NO");
	}
	public void setInvoiceNo(String InvoiceNo){
		this.setInternal("INVOICE_NO" ,InvoiceNo );
	}


	public String getInvoiceNo(){
		return (String)this.getInternal("INVOICE_NO");
	}
	public void setPurchaseNo(String PurchaseNo){
		this.setInternal("PURCHASE_NO" ,PurchaseNo );
	}


	public String getPurchaseNo(){
		return (String)this.getInternal("PURCHASE_NO");
	}
	public void setCloseDate(Date CloseDate){
		this.setInternal("CLOSE_DATE" ,CloseDate );
	}


	public Date getCloseDate(){
		return (Date)this.getInternal("CLOSE_DATE");
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
	public void setCount(String Count){
		this.setInternal("COUNT" ,Count );
	}


	public String getCount(){
		return (String)this.getInternal("COUNT");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
	public void setReturnStatus(String ReturnStatus){
		this.setInternal("RETURN_STATUS" ,ReturnStatus );
	}


	public String getReturnStatus(){
		return (String)this.getInternal("RETURN_STATUS");
	}
	public void setOrderUser(String OrderUser){
		this.setInternal("ORDER_USER" ,OrderUser );
	}


	public String getOrderUser(){
		return (String)this.getInternal("ORDER_USER");
	}
}
