package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBaStrategyVO extends BaseVO{
    public SeBaStrategyVO(){
    	//设置字段信息
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("RULE_ID",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("END_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	this.addField("BEGIN_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("BEGIN_DATE", "yyyy-MM-dd");
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("STRATEGY_NAME",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("STRATEGY_CODE",BaseVO.OP_STRING);
    	this.addField("STRATEGY_STATUS",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("RULE_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("STRATEGY_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
		this.bindFieldToSequence("STRATEGY_ID","COMMON");
    	this.setVOTableName("SE_BA_STRATEGY");
    	
		//设置字典类型定义
		this.bindFieldToDic("STATUS","YXBS");//有效标识
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setRuleId(String RuleId){
		this.setInternal("RULE_ID" ,RuleId );
	}


	public String getRuleId(){
		return (String)this.getInternal("RULE_ID");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setEndDate(Date EndDate){
		this.setInternal("END_DATE" ,EndDate );
	}


	public Date getEndDate(){
		return (Date)this.getInternal("END_DATE");
	}
	public void setBeginDate(Date BeginDate){
		this.setInternal("BEGIN_DATE" ,BeginDate );
	}


	public Date getBeginDate(){
		return (Date)this.getInternal("BEGIN_DATE");
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
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setStrategyName(String StrategyName){
		this.setInternal("STRATEGY_NAME" ,StrategyName );
	}


	public String getStrategyName(){
		return (String)this.getInternal("STRATEGY_NAME");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setStrategyCode(String StrategyCode){
		this.setInternal("STRATEGY_CODE" ,StrategyCode );
	}


	public String getStrategyCode(){
		return (String)this.getInternal("STRATEGY_CODE");
	}
	public void setStrategyStatus(String StrategyStatus){
		this.setInternal("STRATEGY_STATUS" ,StrategyStatus );
	}


	public String getStrategyStatus(){
		return (String)this.getInternal("STRATEGY_STATUS");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setRuleCode(String RuleCode){
		this.setInternal("RULE_CODE" ,RuleCode );
	}


	public String getRuleCode(){
		return (String)this.getInternal("RULE_CODE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setStrategyId(String StrategyId){
		this.setInternal("STRATEGY_ID" ,StrategyId );
	}


	public String getStrategyId(){
		return (String)this.getInternal("STRATEGY_ID");
	}
}
