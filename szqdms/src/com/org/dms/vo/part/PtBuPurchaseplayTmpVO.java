package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuPurchaseplayTmpVO extends BaseVO{
    public PtBuPurchaseplayTmpVO(){
    	//ÉèÖÃ×Ö¶ÎÐÅÏ¢
    	this.addField("PART_NO",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("AVAILABLE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("PLAN_PRICE",BaseVO.OP_STRING);
    	this.addField("LOWER_LIMIT",BaseVO.OP_STRING);
    	this.addField("APPLY_CYCLE",BaseVO.OP_STRING);
    	this.addField("IF_SB",BaseVO.OP_STRING);
    	this.addField("ALL_PRICE",BaseVO.OP_STRING);
    	this.addField("ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("MIN_PACK",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("REMARK",BaseVO.OP_STRING);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("UNIT",BaseVO.OP_STRING);
    	this.addField("IF_SUPLY",BaseVO.OP_STRING);
    	this.addField("PCH_COUNT",BaseVO.OP_STRING);
    	this.addField("POSITION_NAME",BaseVO.OP_STRING);
    	this.addField("MIN_UNIT",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("ORDER_COUNT",BaseVO.OP_STRING);
    	this.addField("ROWSPAN",BaseVO.OP_STRING);
    	this.addField("UPPER_LIMIT",BaseVO.OP_STRING);
    	this.addField("PLAN_TYPE",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
		this.bindFieldToSequence("ID","COMMON");
    	this.setVOTableName("PT_BU_PURCHASEPLAY_TMP");
}
	public void setPartNo(String PartNo){
		this.setInternal("PART_NO" ,PartNo );
	}


	public String getPartNo(){
		return (String)this.getInternal("PART_NO");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setAvailableAmount(String AvailableAmount){
		this.setInternal("AVAILABLE_AMOUNT" ,AvailableAmount );
	}


	public String getAvailableAmount(){
		return (String)this.getInternal("AVAILABLE_AMOUNT");
	}
	public void setSupplierId(String SupplierId){
		this.setInternal("SUPPLIER_ID" ,SupplierId );
	}


	public String getSupplierId(){
		return (String)this.getInternal("SUPPLIER_ID");
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
	public void setLowerLimit(String LowerLimit){
		this.setInternal("LOWER_LIMIT" ,LowerLimit );
	}


	public String getLowerLimit(){
		return (String)this.getInternal("LOWER_LIMIT");
	}
	public void setApplyCycle(String ApplyCycle){
		this.setInternal("APPLY_CYCLE" ,ApplyCycle );
	}


	public String getApplyCycle(){
		return (String)this.getInternal("APPLY_CYCLE");
	}
	public void setIfSb(String IfSb){
		this.setInternal("IF_SB" ,IfSb );
	}


	public String getIfSb(){
		return (String)this.getInternal("IF_SB");
	}
	public void setAllPrice(String AllPrice){
		this.setInternal("ALL_PRICE" ,AllPrice );
	}


	public String getAllPrice(){
		return (String)this.getInternal("ALL_PRICE");
	}
	public void setId(String Id){
		this.setInternal("ID" ,Id );
	}


	public String getId(){
		return (String)this.getInternal("ID");
	}
	public void setMinPack(String MinPack){
		this.setInternal("MIN_PACK" ,MinPack );
	}


	public String getMinPack(){
		return (String)this.getInternal("MIN_PACK");
	}
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}


	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
	public void setRemark(String Remark){
		this.setInternal("REMARK" ,Remark );
	}


	public String getRemark(){
		return (String)this.getInternal("REMARK");
	}
	public void setUserAccount(String UserAccount){
		this.setInternal("USER_ACCOUNT" ,UserAccount );
	}


	public String getUserAccount(){
		return (String)this.getInternal("USER_ACCOUNT");
	}
	public void setUnit(String Unit){
		this.setInternal("UNIT" ,Unit );
	}


	public String getUnit(){
		return (String)this.getInternal("UNIT");
	}
	public void setIfSuply(String IfSuply){
		this.setInternal("IF_SUPLY" ,IfSuply );
	}


	public String getIfSuply(){
		return (String)this.getInternal("IF_SUPLY");
	}
	public void setPchCount(String PchCount){
		this.setInternal("PCH_COUNT" ,PchCount );
	}


	public String getPchCount(){
		return (String)this.getInternal("PCH_COUNT");
	}
	public void setPositionName(String PositionName){
		this.setInternal("POSITION_NAME" ,PositionName );
	}


	public String getPositionName(){
		return (String)this.getInternal("POSITION_NAME");
	}
	public void setMinUnit(String MinUnit){
		this.setInternal("MIN_UNIT" ,MinUnit );
	}


	public String getMinUnit(){
		return (String)this.getInternal("MIN_UNIT");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setOrderCount(String OrderCount){
		this.setInternal("ORDER_COUNT" ,OrderCount );
	}


	public String getOrderCount(){
		return (String)this.getInternal("ORDER_COUNT");
	}
	public void setRowspan(String Rowspan){
		this.setInternal("ROWSPAN" ,Rowspan );
	}


	public String getRowspan(){
		return (String)this.getInternal("ROWSPAN");
	}
	public void setUpperLimit(String UpperLimit){
		this.setInternal("UPPER_LIMIT" ,UpperLimit );
	}


	public String getUpperLimit(){
		return (String)this.getInternal("UPPER_LIMIT");
	}
	public void setPlanType(String PlanType){
		this.setInternal("PLAN_TYPE" ,PlanType );
	}


	public String getPlanType(){
		return (String)this.getInternal("PLAN_TYPE");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
}
