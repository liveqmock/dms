package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class MainProblemFeedbackVO extends BaseVO{
    public MainProblemFeedbackVO(){
    	//设置字段信息
    	this.addField("FEEDBACK_USER",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("PROBLEM_CODE",BaseVO.OP_STRING);
    	this.addField("PROBLEM_DESCRIBE",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("APPLY_USER",BaseVO.OP_STRING);
    	this.addField("PROBLEM_NAME",BaseVO.OP_STRING);
    	this.addField("APPLY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("PROBLEM_STATUS",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("FEEDBACK_REMARKS",BaseVO.OP_STRING);
    	this.addField("PROBLEM_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("FEEDBACK_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("FEEDBACK_DATE", "yyyy-MM-dd");
		this.bindFieldToSequence("PROBLEM_ID","COMMON");
    	this.setVOTableName("MAIN_PROBLEM_FEEDBACK");
		//设置字典类型定义
		this.bindFieldToDic("PROBLEM_STATUS","WTHFZT");//问题回复状态
}
	public void setFeedbackUser(String FeedbackUser){
		this.setInternal("FEEDBACK_USER" ,FeedbackUser );
	}


	public String getFeedbackUser(){
		return (String)this.getInternal("FEEDBACK_USER");
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
	public void setProblemCode(String ProblemCode){
		this.setInternal("PROBLEM_CODE" ,ProblemCode );
	}


	public String getProblemCode(){
		return (String)this.getInternal("PROBLEM_CODE");
	}
	public void setProblemDescribe(String ProblemDescribe){
		this.setInternal("PROBLEM_DESCRIBE" ,ProblemDescribe );
	}


	public String getProblemDescribe(){
		return (String)this.getInternal("PROBLEM_DESCRIBE");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setApplyUser(String ApplyUser){
		this.setInternal("APPLY_USER" ,ApplyUser );
	}


	public String getApplyUser(){
		return (String)this.getInternal("APPLY_USER");
	}
	public void setProblemName(String ProblemName){
		this.setInternal("PROBLEM_NAME" ,ProblemName );
	}


	public String getProblemName(){
		return (String)this.getInternal("PROBLEM_NAME");
	}
	public void setApplyDate(Date ApplyDate){
		this.setInternal("APPLY_DATE" ,ApplyDate );
	}


	public Date getApplyDate(){
		return (Date)this.getInternal("APPLY_DATE");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setProblemStatus(String ProblemStatus){
		this.setInternal("PROBLEM_STATUS" ,ProblemStatus );
	}


	public String getProblemStatus(){
		return (String)this.getInternal("PROBLEM_STATUS");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setFeedbackRemarks(String FeedbackRemarks){
		this.setInternal("FEEDBACK_REMARKS" ,FeedbackRemarks );
	}


	public String getFeedbackRemarks(){
		return (String)this.getInternal("FEEDBACK_REMARKS");
	}
	public void setProblemId(String ProblemId){
		this.setInternal("PROBLEM_ID" ,ProblemId );
	}


	public String getProblemId(){
		return (String)this.getInternal("PROBLEM_ID");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setFeedbackDate(Date FeedbackDate){
		this.setInternal("FEEDBACK_DATE" ,FeedbackDate );
	}


	public Date getFeedbackDate(){
		return (Date)this.getInternal("FEEDBACK_DATE");
	}
}
