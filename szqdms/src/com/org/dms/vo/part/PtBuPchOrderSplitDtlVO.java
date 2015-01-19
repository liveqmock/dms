package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuPchOrderSplitDtlVO extends BaseVO{
    public PtBuPchOrderSplitDtlVO(){
    	//�����ֶ���Ϣ
    	this.addField("PCH_PRICE",BaseVO.OP_STRING);
    	this.addField("ACCEPT_COUNT",BaseVO.OP_STRING);
    	this.addField("SHIP_COUNT",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("PCH_COUNT",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("PCH_AMOUNT",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("SPLIT_ID",BaseVO.OP_STRING);
    	this.addField("PLAN_AMOUNT",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("PLAN_PRICE",BaseVO.OP_STRING);
    	this.addField("DETAIL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("ADJUST_REMARKS",BaseVO.OP_STRING);
    	this.addField("STORAGE_COUNT",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("DISTRIBUTION_NO",BaseVO.OP_STRING);
		this.bindFieldToSequence("DETAIL_ID","COMMON");
    	this.setVOTableName("PT_BU_PCH_ORDER_SPLIT_DTL");
}
    public void setDistributionNo(String distributionNo){
		this.setInternal("DISTRIBUTION_NO" ,distributionNo );
	}


	public String getDistributionNo(){
		return (String)this.getInternal("DISTRIBUTION_NO");
	}
	public void setPchPrice(String PchPrice){
		this.setInternal("PCH_PRICE" ,PchPrice );
	}


	public String getPchPrice(){
		return (String)this.getInternal("PCH_PRICE");
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
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setPchCount(String PchCount){
		this.setInternal("PCH_COUNT" ,PchCount );
	}


	public String getPchCount(){
		return (String)this.getInternal("PCH_COUNT");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
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
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setPchAmount(String PchAmount){
		this.setInternal("PCH_AMOUNT" ,PchAmount );
	}


	public String getPchAmount(){
		return (String)this.getInternal("PCH_AMOUNT");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
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
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setPlanPrice(String PlanPrice){
		this.setInternal("PLAN_PRICE" ,PlanPrice );
	}


	public String getPlanPrice(){
		return (String)this.getInternal("PLAN_PRICE");
	}
	public void setDetailId(String DetailId){
		this.setInternal("DETAIL_ID" ,DetailId );
	}


	public String getDetailId(){
		return (String)this.getInternal("DETAIL_ID");
	}
	public void setAdjustRemarks(String AdjustRemarks){
		this.setInternal("ADJUST_REMARKS" ,AdjustRemarks );
	}


	public String getAdjustRemarks(){
		return (String)this.getInternal("ADJUST_REMARKS");
	}
	public void setStorageCount(String StorageCount){
		this.setInternal("STORAGE_COUNT" ,StorageCount );
	}


	public String getStorageCount(){
		return (String)this.getInternal("STORAGE_COUNT");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}


	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
}
