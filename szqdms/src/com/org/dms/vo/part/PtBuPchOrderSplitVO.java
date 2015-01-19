package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuPchOrderSplitVO extends BaseVO{
    public PtBuPchOrderSplitVO(){
    	//�����ֶ���Ϣ
    	this.addField("FIRST_SHIP_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("FIRST_SHIP_DATE", "yyyy-MM-dd");
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("PACC_NO",BaseVO.OP_STRING);
    	this.addField("ACCEPT_COUNT",BaseVO.OP_STRING);
    	this.addField("SHIP_COUNT",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("APPLY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("ORDER_NO",BaseVO.OP_STRING);
    	this.addField("PURCHASE_ID",BaseVO.OP_STRING);
    	this.addField("REPUIREMENT_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("REPUIREMENT_TIME", "yyyy-MM-dd");
    	this.addField("SPLIT_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PLAN_AMOUNT",BaseVO.OP_STRING);
    	this.addField("INVOICE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("INVOICE_DATE", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("DELIVERY_CYCLE",BaseVO.OP_STRING);
    	this.addField("PURCHASE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("SETTLE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("SETTLE_DATE", "yyyy-MM-dd");
    	this.addField("ORDER_STATUS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("CONFIRM_ADVISE",BaseVO.OP_STRING);
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("APPLY_USER",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("PURCHASE_TYPE",BaseVO.OP_STRING);
    	this.addField("LAST_SHIP_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("LAST_SHIP_DATE", "yyyy-MM-dd");
    	this.addField("INVOICE_STATUS",BaseVO.OP_STRING);
    	this.addField("INVOICE_REMARKS",BaseVO.OP_STRING);
    	this.addField("SELECT_MONTH",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("ORDER_TYPE",BaseVO.OP_STRING);
    	this.addField("SETTLE_REMARKS",BaseVO.OP_STRING);
    	this.addField("PLAN_DISTRIBUTION",BaseVO.OP_STRING);
    	this.addField("PURCHASE_COUNT",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("INVOICE_NO",BaseVO.OP_STRING);
    	this.addField("CLOSE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("CLOSE_DATE", "yyyy-MM-dd");
    	this.addField("IF_ON_TIME",BaseVO.OP_STRING);
    	this.addField("COMPLETE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("COMPLETE_DATE", "yyyy-MM-dd");
    	this.addField("SETTLE_STATUS",BaseVO.OP_STRING);
    	this.addField("SPLIT_NO",BaseVO.OP_STRING);
    	this.addField("SHIP_TIMES",BaseVO.OP_STRING);
    	this.addField("PURCHASE_CATEGORY",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("STORAGE_COUNT",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("SUPPLIER_DATE", "yyyy-MM-dd");
		this.bindFieldToSequence("SPLIT_ID","COMMON");
    	this.setVOTableName("PT_BU_PCH_ORDER_SPLIT");
}
	public void setFirstShipDate(Date FirstShipDate){
		this.setInternal("FIRST_SHIP_DATE" ,FirstShipDate );
	}


	public Date getFirstShipDate(){
		return (Date)this.getInternal("FIRST_SHIP_DATE");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setPaccNo(String PaccNo){
		this.setInternal("PACC_NO" ,PaccNo );
	}


	public String getPaccNo(){
		return (String)this.getInternal("PACC_NO");
	}
	public void setAcceptCount(String AcceptCount){
		this.setInternal("ACCEPT_COUNT" ,AcceptCount );
	}


	public String getAcceptCount(){
		return (String)this.getInternal("ACCEPT_COUNT");
	}
	public void setShipCount(String ShipCount){
		this.setInternal("SHIP_COUNT" ,ShipCount );
	}


	public String getShipCount(){
		return (String)this.getInternal("SHIP_COUNT");
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
	public void setSupplierId(String SupplierId){
		this.setInternal("SUPPLIER_ID" ,SupplierId );
	}


	public String getSupplierId(){
		return (String)this.getInternal("SUPPLIER_ID");
	}
	public void setOrderNo(String OrderNo){
		this.setInternal("ORDER_NO" ,OrderNo );
	}


	public String getOrderNo(){
		return (String)this.getInternal("ORDER_NO");
	}
	public void setPurchaseId(String PurchaseId){
		this.setInternal("PURCHASE_ID" ,PurchaseId );
	}


	public String getPurchaseId(){
		return (String)this.getInternal("PURCHASE_ID");
	}
	public void setRepuirementTime(Date RepuirementTime){
		this.setInternal("REPUIREMENT_TIME" ,RepuirementTime );
	}


	public Date getRepuirementTime(){
		return (Date)this.getInternal("REPUIREMENT_TIME");
	}
	public void setSplitId(String SplitId){
		this.setInternal("SPLIT_ID" ,SplitId );
	}


	public String getSplitId(){
		return (String)this.getInternal("SPLIT_ID");
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
	public void setDeliveryCycle(String DeliveryCycle){
		this.setInternal("DELIVERY_CYCLE" ,DeliveryCycle );
	}


	public String getDeliveryCycle(){
		return (String)this.getInternal("DELIVERY_CYCLE");
	}
	public void setPurchaseAmount(String PurchaseAmount){
		this.setInternal("PURCHASE_AMOUNT" ,PurchaseAmount );
	}


	public String getPurchaseAmount(){
		return (String)this.getInternal("PURCHASE_AMOUNT");
	}
	public void setSettleDate(Date SettleDate){
		this.setInternal("SETTLE_DATE" ,SettleDate );
	}


	public Date getSettleDate(){
		return (Date)this.getInternal("SETTLE_DATE");
	}
	public void setOrderStatus(String OrderStatus){
		this.setInternal("ORDER_STATUS" ,OrderStatus );
	}


	public String getOrderStatus(){
		return (String)this.getInternal("ORDER_STATUS");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setConfirmAdvise(String ConfirmAdvise){
		this.setInternal("CONFIRM_ADVISE" ,ConfirmAdvise );
	}


	public String getConfirmAdvise(){
		return (String)this.getInternal("CONFIRM_ADVISE");
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
	public void setApplyUser(String ApplyUser){
		this.setInternal("APPLY_USER" ,ApplyUser );
	}


	public String getApplyUser(){
		return (String)this.getInternal("APPLY_USER");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setPurchaseType(String PurchaseType){
		this.setInternal("PURCHASE_TYPE" ,PurchaseType );
	}


	public String getPurchaseType(){
		return (String)this.getInternal("PURCHASE_TYPE");
	}
	public void setLastShipDate(Date LastShipDate){
		this.setInternal("LAST_SHIP_DATE" ,LastShipDate );
	}


	public Date getLastShipDate(){
		return (Date)this.getInternal("LAST_SHIP_DATE");
	}
	public void setInvoiceStatus(String InvoiceStatus){
		this.setInternal("INVOICE_STATUS" ,InvoiceStatus );
	}


	public String getInvoiceStatus(){
		return (String)this.getInternal("INVOICE_STATUS");
	}
	public void setInvoiceRemarks(String InvoiceRemarks){
		this.setInternal("INVOICE_REMARKS" ,InvoiceRemarks );
	}


	public String getInvoiceRemarks(){
		return (String)this.getInternal("INVOICE_REMARKS");
	}
	public void setSelectMonth(String SelectMonth){
		this.setInternal("SELECT_MONTH" ,SelectMonth );
	}


	public String getSelectMonth(){
		return (String)this.getInternal("SELECT_MONTH");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setOrderType(String OrderType){
		this.setInternal("ORDER_TYPE" ,OrderType );
	}


	public String getOrderType(){
		return (String)this.getInternal("ORDER_TYPE");
	}
	public void setSettleRemarks(String SettleRemarks){
		this.setInternal("SETTLE_REMARKS" ,SettleRemarks );
	}


	public String getSettleRemarks(){
		return (String)this.getInternal("SETTLE_REMARKS");
	}
	public void setPlanDistribution(String PlanDistribution){
		this.setInternal("PLAN_DISTRIBUTION" ,PlanDistribution );
	}


	public String getPlanDistribution(){
		return (String)this.getInternal("PLAN_DISTRIBUTION");
	}
	public void setPurchaseCount(String PurchaseCount){
		this.setInternal("PURCHASE_COUNT" ,PurchaseCount );
	}


	public String getPurchaseCount(){
		return (String)this.getInternal("PURCHASE_COUNT");
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
	public void setCloseDate(Date CloseDate){
		this.setInternal("CLOSE_DATE" ,CloseDate );
	}


	public Date getCloseDate(){
		return (Date)this.getInternal("CLOSE_DATE");
	}
	public void setIfOnTime(String IfOnTime){
		this.setInternal("IF_ON_TIME" ,IfOnTime );
	}


	public String getIfOnTime(){
		return (String)this.getInternal("IF_ON_TIME");
	}
	public void setCompleteDate(Date CompleteDate){
		this.setInternal("COMPLETE_DATE" ,CompleteDate );
	}


	public Date getCompleteDate(){
		return (Date)this.getInternal("COMPLETE_DATE");
	}
	public void setSettleStatus(String SettleStatus){
		this.setInternal("SETTLE_STATUS" ,SettleStatus );
	}


	public String getSettleStatus(){
		return (String)this.getInternal("SETTLE_STATUS");
	}
	public void setSplitNo(String SplitNo){
		this.setInternal("SPLIT_NO" ,SplitNo );
	}


	public String getSplitNo(){
		return (String)this.getInternal("SPLIT_NO");
	}
	public void setShipTimes(String ShipTimes){
		this.setInternal("SHIP_TIMES" ,ShipTimes );
	}


	public String getShipTimes(){
		return (String)this.getInternal("SHIP_TIMES");
	}
	public void setPurchaseCategory(String PurchaseCategory){
		this.setInternal("PURCHASE_CATEGORY" ,PurchaseCategory );
	}


	public String getPurchaseCategory(){
		return (String)this.getInternal("PURCHASE_CATEGORY");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setStorageCount(String StorageCount){
		this.setInternal("STORAGE_COUNT" ,StorageCount );
	}


	public String getStorageCount(){
		return (String)this.getInternal("STORAGE_COUNT");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
	public void setSupplierDate(Date SupplierDate){
		this.setInternal("SUPPLIER_DATE" ,SupplierDate );
	}


	public Date getSupplierDate(){
		return (Date)this.getInternal("SUPPLIER_DATE");
	}
}
