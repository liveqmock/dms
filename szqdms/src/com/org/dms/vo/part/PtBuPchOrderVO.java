package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuPchOrderVO extends BaseVO{
    public PtBuPchOrderVO(){
    	//�����ֶ���Ϣ
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("APPLY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("ORDER_NO",BaseVO.OP_STRING);
    	this.addField("PURCHASE_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PLAN_AMOUNT",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("PURCHASE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("ORDER_STATUS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("APPLY_USER",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("SALE_ORDER_ID",BaseVO.OP_STRING);
    	this.addField("PURCHASE_TYPE",BaseVO.OP_STRING);
    	this.addField("SELECT_MONTH",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("ORDER_TYPE",BaseVO.OP_STRING);
    	this.addField("PURCHASE_COUNT",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("PURCHASE_CATEGORY",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
		this.bindFieldToSequence("PURCHASE_ID","COMMON");
		this.bindFieldToDic("PURCHASE_CATEGORY", "CGLB");
		this.bindFieldToDic("PURCHASE_TYPE", "CGDDLX");
		this.bindFieldToUserid("APPLY_USER");
    	this.setVOTableName("PT_BU_PCH_ORDER");
}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
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
	public void setPurchaseAmount(String PurchaseAmount){
		this.setInternal("PURCHASE_AMOUNT" ,PurchaseAmount );
	}


	public String getPurchaseAmount(){
		return (String)this.getInternal("PURCHASE_AMOUNT");
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
	public void setSaleOrderId(String SaleOrderId){
		this.setInternal("SALE_ORDER_ID" ,SaleOrderId );
	}


	public String getSaleOrderId(){
		return (String)this.getInternal("SALE_ORDER_ID");
	}
	public void setPurchaseType(String PurchaseType){
		this.setInternal("PURCHASE_TYPE" ,PurchaseType );
	}


	public String getPurchaseType(){
		return (String)this.getInternal("PURCHASE_TYPE");
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
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
}
