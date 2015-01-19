package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBaPreauthorRuleDtlVO extends BaseVO{
    public SeBaPreauthorRuleDtlVO(){
    	//�����ֶ���Ϣ
    	this.addField("PREAUTHOR_RELATION",BaseVO.OP_STRING);
    	this.addField("SEQUENCE",BaseVO.OP_STRING);
    	this.addField("DTL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("RULE_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("COMPAR_CHARACTER",BaseVO.OP_STRING);
    	this.addField("VALUE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("PREAUTHOR_OBJECT",BaseVO.OP_STRING);
		this.bindFieldToSequence("DTL_ID","COMMON");
    	this.setVOTableName("SE_BA_PREAUTHOR_RULE_DTL");
}
	public void setPreauthorRelation(String PreauthorRelation){
		this.setInternal("PREAUTHOR_RELATION" ,PreauthorRelation );
	}


	public String getPreauthorRelation(){
		return (String)this.getInternal("PREAUTHOR_RELATION");
	}
	public void setSequence(String Sequence){
		this.setInternal("SEQUENCE" ,Sequence );
	}


	public String getSequence(){
		return (String)this.getInternal("SEQUENCE");
	}
	public void setDtlId(String DtlId){
		this.setInternal("DTL_ID" ,DtlId );
	}


	public String getDtlId(){
		return (String)this.getInternal("DTL_ID");
	}
	public void setRuleId(String RuleId){
		this.setInternal("RULE_ID" ,RuleId );
	}


	public String getRuleId(){
		return (String)this.getInternal("RULE_ID");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setComparCharacter(String ComparCharacter){
		this.setInternal("COMPAR_CHARACTER" ,ComparCharacter );
	}


	public String getComparCharacter(){
		return (String)this.getInternal("COMPAR_CHARACTER");
	}
	public void setValue(String Value){
		this.setInternal("VALUE" ,Value );
	}


	public String getValue(){
		return (String)this.getInternal("VALUE");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setPreauthorObject(String PreauthorObject){
		this.setInternal("PREAUTHOR_OBJECT" ,PreauthorObject );
	}


	public String getPreauthorObject(){
		return (String)this.getInternal("PREAUTHOR_OBJECT");
	}
}
