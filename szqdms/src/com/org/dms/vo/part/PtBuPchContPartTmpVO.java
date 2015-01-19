package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuPchContPartTmpVO extends BaseVO{
    public PtBuPchContPartTmpVO(){
    	//�����ֶ���Ϣ
    	this.addField("TMP_NO",BaseVO.OP_STRING);
    	this.addField("MIN_PACK_UNIT",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("DELIVER_CYCLE",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("UNIT_PRICE",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("MIN_PACK_COUNT",BaseVO.OP_STRING);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("PT_BU_PCH_CONT_PART_TMP");
}
	public void setTmpNo(String TmpNo){
		this.setInternal("TMP_NO" ,TmpNo );
	}


	public String getTmpNo(){
		return (String)this.getInternal("TMP_NO");
	}
	public void setMinPackUnit(String MinPackUnit){
		this.setInternal("MIN_PACK_UNIT" ,MinPackUnit );
	}


	public String getMinPackUnit(){
		return (String)this.getInternal("MIN_PACK_UNIT");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setDeliverCycle(String DeliverCycle){
		this.setInternal("DELIVER_CYCLE" ,DeliverCycle );
	}


	public String getDeliverCycle(){
		return (String)this.getInternal("DELIVER_CYCLE");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setUnitPrice(String UnitPrice){
		this.setInternal("UNIT_PRICE" ,UnitPrice );
	}


	public String getUnitPrice(){
		return (String)this.getInternal("UNIT_PRICE");
	}
	public void setTmpId(String TmpId){
		this.setInternal("TMP_ID" ,TmpId );
	}


	public String getTmpId(){
		return (String)this.getInternal("TMP_ID");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setMinPackCount(String MinPackCount){
		this.setInternal("MIN_PACK_COUNT" ,MinPackCount );
	}


	public String getMinPackCount(){
		return (String)this.getInternal("MIN_PACK_COUNT");
	}
	public void setUserAccount(String UserAccount){
		this.setInternal("USER_ACCOUNT" ,UserAccount );
	}


	public String getUserAccount(){
		return (String)this.getInternal("USER_ACCOUNT");
	}
}
