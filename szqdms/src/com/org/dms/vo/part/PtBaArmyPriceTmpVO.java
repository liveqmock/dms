package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaArmyPriceTmpVO extends BaseVO{
    public PtBaArmyPriceTmpVO(){
    	//�����ֶ���Ϣ
    	this.addField("TMP_NO",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("ARMY_PRICE",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("PT_BA_ARMY_PRICE_TMP");
}
	public void setTmpNo(String TmpNo){
		this.setInternal("TMP_NO" ,TmpNo );
	}


	public String getTmpNo(){
		return (String)this.getInternal("TMP_NO");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setArmyPrice(String ArmyPrice){
		this.setInternal("ARMY_PRICE" ,ArmyPrice );
	}


	public String getArmyPrice(){
		return (String)this.getInternal("ARMY_PRICE");
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
