package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
@SuppressWarnings("serial")
public class PtBuMoveOutPartTmpVO extends BaseVO{
    public PtBuMoveOutPartTmpVO(){
    	this.addField("ROW_NUM",BaseVO.OP_STRING);
    	this.addField("POSITION_CODE",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("MOVE_COUNT",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("PT_BU_MOVE_OUT_PART_TMP");
}
	public void setRowNum(String RowNum){
		this.setInternal("ROW_NUM" ,RowNum );
	}


	public String getRowNum(){
		return (String)this.getInternal("ROW_NUM");
	}
	public void setPositionCode(String PositionCode){
		this.setInternal("POSITION_CODE" ,PositionCode );
	}


	public String getPositionCode(){
		return (String)this.getInternal("POSITION_CODE");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
	public void setMoveCount(String MoveCount){
		this.setInternal("MOVE_COUNT" ,MoveCount );
	}


	public String getMoveCount(){
		return (String)this.getInternal("MOVE_COUNT");
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
}
