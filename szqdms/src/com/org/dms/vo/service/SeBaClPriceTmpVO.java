package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBaClPriceTmpVO extends BaseVO{
    public SeBaClPriceTmpVO(){
    	//�����ֶ���Ϣ
    	this.addField("SE_CLPRICE",BaseVO.OP_STRING);
    	this.addField("ROW_NUM",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("SE_BA_CL_PRICE_TMP");
}
	public void setSeClprice(String SeClprice){
		this.setInternal("SE_CLPRICE" ,SeClprice );
	}


	public String getSeClprice(){
		return (String)this.getInternal("SE_CLPRICE");
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
