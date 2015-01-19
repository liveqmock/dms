package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBaRulePartTmpVO extends BaseVO{
    public SeBaRulePartTmpVO(){
    	//�����ֶ���Ϣ
    	this.addField("RULE_ID",BaseVO.OP_STRING);
    	this.addField("RULE_NAME",BaseVO.OP_STRING);
    	this.addField("ROW_NUM",BaseVO.OP_STRING);
    	this.addField("MILEAGE",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("RULE_CODE",BaseVO.OP_STRING);
    	this.addField("MONTHS",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("SE_BA_RULE_PART_TMP");
}
	public void setRuleId(String RuleId){
		this.setInternal("RULE_ID" ,RuleId );
	}


	public String getRuleId(){
		return (String)this.getInternal("RULE_ID");
	}
	public void setRuleName(String RuleName){
		this.setInternal("RULE_NAME" ,RuleName );
	}


	public String getRuleName(){
		return (String)this.getInternal("RULE_NAME");
	}
	public void setRowNum(String RowNum){
		this.setInternal("ROW_NUM" ,RowNum );
	}


	public String getRowNum(){
		return (String)this.getInternal("ROW_NUM");
	}
	public void setMileage(String Mileage){
		this.setInternal("MILEAGE" ,Mileage );
	}


	public String getMileage(){
		return (String)this.getInternal("MILEAGE");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setRuleCode(String RuleCode){
		this.setInternal("RULE_CODE" ,RuleCode );
	}


	public String getRuleCode(){
		return (String)this.getInternal("RULE_CODE");
	}
	public void setMonths(String Months){
		this.setInternal("MONTHS" ,Months );
	}


	public String getMonths(){
		return (String)this.getInternal("MONTHS");
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
