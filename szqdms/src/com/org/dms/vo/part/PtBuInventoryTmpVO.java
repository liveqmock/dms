package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuInventoryTmpVO extends BaseVO{
    public PtBuInventoryTmpVO(){
    	//�����ֶ���Ϣ
    	this.addField("DTL_ID",BaseVO.OP_STRING);
    	this.addField("PLAN_PRICE",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("ROW_NUM",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("POSITION_NAME",BaseVO.OP_STRING);
    	this.addField("AREA_CODE",BaseVO.OP_STRING);
    	this.addField("AMOUNT",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("WHOUSE_KEEPER",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("PT_BU_INVENTORY_TMP");
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
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}


	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
	public void setRowNum(String RowNum){
		this.setInternal("ROW_NUM" ,RowNum );
	}


	public String getRowNum(){
		return (String)this.getInternal("ROW_NUM");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setPositionName(String PositionName){
		this.setInternal("POSITION_NAME" ,PositionName );
	}


	public String getPositionName(){
		return (String)this.getInternal("POSITION_NAME");
	}
	public void setAreaCode(String AreaCode){
		this.setInternal("AREA_CODE" ,AreaCode );
	}


	public String getAreaCode(){
		return (String)this.getInternal("AREA_CODE");
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
	public void setTmpId(String TmpId){
		this.setInternal("TMP_ID" ,TmpId );
	}


	public String getTmpId(){
		return (String)this.getInternal("TMP_ID");
	}
	public void setUserAccount(String UserAccount){
		this.setInternal("USER_ACCOUNT" ,UserAccount );
	}


	public String getUserAccount(){
		return (String)this.getInternal("USER_ACCOUNT");
	}
	public void setWhouseKeeper(String WhouseKeeper){
		this.setInternal("WHOUSE_KEEPER" ,WhouseKeeper );
	}


	public String getWhouseKeeper(){
		return (String)this.getInternal("WHOUSE_KEEPER");
	}
}
