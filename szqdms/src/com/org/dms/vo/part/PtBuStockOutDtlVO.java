package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuStockOutDtlVO extends BaseVO{
    public PtBuStockOutDtlVO(){
    	//�����ֶ���Ϣ
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("ISSUE_ID",BaseVO.OP_STRING);
    	this.addField("OUT_ID",BaseVO.OP_STRING);
    	this.addField("UNIT",BaseVO.OP_STRING);
    	this.addField("OUT_AMOUNT",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("PLAN_AMOUNT",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("DETAIL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("SHOULD_COUNT",BaseVO.OP_STRING);
    	this.addField("SALE_PRICE",BaseVO.OP_STRING);
    	this.addField("KEEP_MAN",BaseVO.OP_STRING);
    	this.addField("ISSUE_NO",BaseVO.OP_STRING);
    	this.addField("PLAN_PRICE",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("SALE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("POSITION_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("DETAIL_ID","COMMON");
    	this.setVOTableName("PT_BU_STOCK_OUT_DTL");
}
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}


	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
	public void setIssueId(String IssueId){
		this.setInternal("ISSUE_ID" ,IssueId );
	}


	public String getIssueId(){
		return (String)this.getInternal("ISSUE_ID");
	}
	public void setOutId(String OutId){
		this.setInternal("OUT_ID" ,OutId );
	}


	public String getOutId(){
		return (String)this.getInternal("OUT_ID");
	}
	public void setUnit(String Unit){
		this.setInternal("UNIT" ,Unit );
	}


	public String getUnit(){
		return (String)this.getInternal("UNIT");
	}
	public void setOutAmount(String OutAmount){
		this.setInternal("OUT_AMOUNT" ,OutAmount );
	}


	public String getOutAmount(){
		return (String)this.getInternal("OUT_AMOUNT");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
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
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
	public void setDetailId(String DetailId){
		this.setInternal("DETAIL_ID" ,DetailId );
	}


	public String getDetailId(){
		return (String)this.getInternal("DETAIL_ID");
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
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setShouldCount(String ShouldCount){
		this.setInternal("SHOULD_COUNT" ,ShouldCount );
	}


	public String getShouldCount(){
		return (String)this.getInternal("SHOULD_COUNT");
	}
	public void setSalePrice(String SalePrice){
		this.setInternal("SALE_PRICE" ,SalePrice );
	}


	public String getSalePrice(){
		return (String)this.getInternal("SALE_PRICE");
	}
	public void setKeepMan(String KeepMan){
		this.setInternal("KEEP_MAN" ,KeepMan );
	}


	public String getKeepMan(){
		return (String)this.getInternal("KEEP_MAN");
	}
	public void setIssueNo(String IssueNo){
		this.setInternal("ISSUE_NO" ,IssueNo );
	}


	public String getIssueNo(){
		return (String)this.getInternal("ISSUE_NO");
	}
	public void setPlanPrice(String PlanPrice){
		this.setInternal("PLAN_PRICE" ,PlanPrice );
	}


	public String getPlanPrice(){
		return (String)this.getInternal("PLAN_PRICE");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setSaleAmount(String SaleAmount){
		this.setInternal("SALE_AMOUNT" ,SaleAmount );
	}


	public String getSaleAmount(){
		return (String)this.getInternal("SALE_AMOUNT");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setPositionId(String PositionId){
		this.setInternal("POSITION_ID" ,PositionId );
	}


	public String getPositionId(){
		return (String)this.getInternal("POSITION_ID");
	}
}
