package com.org.dms.vo.part;

import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuSaleOrderDtlVO extends BaseVO{
    public PtBuSaleOrderDtlVO(){
    	//�����ֶ���Ϣ
    	this.addField("PLAN_PRODUCE_NO",BaseVO.OP_STRING);
    	this.addField("SIGN_COUNT",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("PLAN_UNIT",BaseVO.OP_STRING);
    	this.addField("UNIT_PRICE",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("PART_NO",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("AMOUNT",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("AUDIT_COUNT",BaseVO.OP_STRING);
    	this.addField("DTL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PLAN_PRICE",BaseVO.OP_STRING);
    	this.addField("ORDER_ID",BaseVO.OP_STRING);
    	this.addField("ORDER_COUNT",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("IF_SUPPLIER",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("DELIVERY_COUNT",BaseVO.OP_STRING);
    	this.addField("FILL_STATUS",BaseVO.OP_STRING);
		this.bindFieldToSequence("DTL_ID","COMMON");
    	this.setVOTableName("PT_BU_SALE_ORDER_DTL");
}
	public void setPlanProduceNo(String PlanProduceNo){
		this.setInternal("PLAN_PRODUCE_NO" ,PlanProduceNo );
	}


	public String getPlanProduceNo(){
		return (String)this.getInternal("PLAN_PRODUCE_NO");
	}
	
	public void setFillStatus(String FillStatus){
		this.setInternal("FILL_STATUS" ,FillStatus );
	}


	public String getFillStatus(){
		return (String)this.getInternal("FILL_STATUS");
	}
	
	public void setSignCount(String SignCount){
		this.setInternal("SIGN_COUNT" ,SignCount );
	}


	public String getSignCount(){
		return (String)this.getInternal("SIGN_COUNT");
	}
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}


	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
	public void setPlanUnit(String PlanUnit){
		this.setInternal("PLAN_UNIT" ,PlanUnit );
	}


	public String getPlanUnit(){
		return (String)this.getInternal("PLAN_UNIT");
	}
	public void setUnitPrice(String UnitPrice){
		this.setInternal("UNIT_PRICE" ,UnitPrice );
	}


	public String getUnitPrice(){
		return (String)this.getInternal("UNIT_PRICE");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
	public void setPartNo(String PartNo){
		this.setInternal("PART_NO" ,PartNo );
	}


	public String getPartNo(){
		return (String)this.getInternal("PART_NO");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setAmount(String Amount){
		this.setInternal("AMOUNT" ,Amount );
	}


	public String getAmount(){
		return (String)this.getInternal("AMOUNT");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setSupplierId(String SupplierId){
		this.setInternal("SUPPLIER_ID" ,SupplierId );
	}


	public String getSupplierId(){
		return (String)this.getInternal("SUPPLIER_ID");
	}
	public void setAuditCount(String AuditCount){
		this.setInternal("AUDIT_COUNT" ,AuditCount );
	}


	public String getAuditCount(){
		return (String)this.getInternal("AUDIT_COUNT");
	}
	public void setDtlId(String DtlId){
		this.setInternal("DTL_ID" ,DtlId );
	}


	public String getDtlId(){
		return (String)this.getInternal("DTL_ID");
	}
	public void setPlanPrice(String PlanPrice){
		this.setInternal("PLAN_PRICE" ,PlanPrice );
	}


	public String getPlanPrice(){
		return (String)this.getInternal("PLAN_PRICE");
	}
	public void setOrderId(String OrderId){
		this.setInternal("ORDER_ID" ,OrderId );
	}


	public String getOrderId(){
		return (String)this.getInternal("ORDER_ID");
	}
	public void setOrderCount(String OrderCount){
		this.setInternal("ORDER_COUNT" ,OrderCount );
	}


	public String getOrderCount(){
		return (String)this.getInternal("ORDER_COUNT");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setIfSupplier(String IfSupplier){
		this.setInternal("IF_SUPPLIER" ,IfSupplier );
	}


	public String getIfSupplier(){
		return (String)this.getInternal("IF_SUPPLIER");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setDeliveryCount(String DeliveryCount){
		this.setInternal("DELIVERY_COUNT" ,DeliveryCount );
	}


	public String getDeliveryCount(){
		return (String)this.getInternal("DELIVERY_COUNT");
	}
}
