package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuProActiveDealVO extends BaseVO{
    public PtBuProActiveDealVO(){
    	//活动执行方案VO
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("DEAL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("DEAL_STATUS",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("DEAL_CONTENT",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("ACTIVE_CODE",BaseVO.OP_STRING);
    	this.addField("PLAN_FEE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("ACTIVE_NAME",BaseVO.OP_STRING);
    	this.addField("ACTIVE_ID",BaseVO.OP_STRING);
    	this.addField("PERSON_NUMS",BaseVO.OP_STRING);
    	this.addField("REPORT_USER",BaseVO.OP_STRING);
    	this.addField("REPORT_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("REPORT_TIME", "yyyy-MM-dd");
		this.bindFieldToSequence("DEAL_ID","COMMON");
		//设置字典类型定义
		this.bindFieldToDic("STATUS","YXBS");//有效标识
		this.bindFieldToDic("DEAL_STATUS","HDZXZT");//活动执行状态
    	this.setVOTableName("PT_BU_PRO_ACTIVE_DEAL");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setDealId(String DealId){
		this.setInternal("DEAL_ID" ,DealId );
	}


	public String getDealId(){
		return (String)this.getInternal("DEAL_ID");
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
	public void setDealStatus(String DealStatus){
		this.setInternal("DEAL_STATUS" ,DealStatus );
	}


	public String getDealStatus(){
		return (String)this.getInternal("DEAL_STATUS");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setDealContent(String DealContent){
		this.setInternal("DEAL_CONTENT" ,DealContent );
	}


	public String getDealContent(){
		return (String)this.getInternal("DEAL_CONTENT");
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
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setReportUser(String ReportUser){
		this.setInternal("REPORT_USER" ,ReportUser );
	}


	public String getReportUser(){
		return (String)this.getInternal("REPORT_USER");
	}
	public void setReportTime(Date ReportTime){
		this.setInternal("REPORT_TIME" ,ReportTime );
	}


	public Date getReportTime(){
		return (Date)this.getInternal("REPORT_TIME");
	}
	public void setPersonNums(String PersonNums){
		this.setInternal("PERSON_NUMS" ,PersonNums );
	}


	public String getPersonNums(){
		return (String)this.getInternal("PERSON_NUMS");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setActiveCode(String ActiveCode){
		this.setInternal("ACTIVE_CODE" ,ActiveCode );
	}


	public String getActiveCode(){
		return (String)this.getInternal("ACTIVE_CODE");
	}
	public void setPlanFee(String PlanFee){
		this.setInternal("PLAN_FEE" ,PlanFee );
	}


	public String getPlanFee(){
		return (String)this.getInternal("PLAN_FEE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setActiveName(String ActiveName){
		this.setInternal("ACTIVE_NAME" ,ActiveName );
	}


	public String getActiveName(){
		return (String)this.getInternal("ACTIVE_NAME");
	}
	public void setActiveId(String ActiveId){
		this.setInternal("ACTIVE_ID" ,ActiveId );
	}


	public String getActiveId(){
		return (String)this.getInternal("ACTIVE_ID");
	}
}
