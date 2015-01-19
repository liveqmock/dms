package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuProActiveVO extends BaseVO{
    public PtBuProActiveVO(){
    	//活动方案制定VO
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("ACTIVE_CONTENT",BaseVO.OP_STRING);
    	this.addField("ACTIVE_STATUS",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("END_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("START_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("ACTIVE_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("ACTIVE_NAME",BaseVO.OP_STRING);
    	this.addField("ISSUE_USER",BaseVO.OP_STRING);
    	this.addField("ISSUE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("ISSUE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("ACTIVE_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
		this.bindFieldToSequence("ACTIVE_ID","COMMON");
		//设置字典类型定义
		this.bindFieldToDic("STATUS","YXBS");//有效标识
		this.bindFieldToDic("ACTIVE_STATUS","HDLCZT");//活动流程状态
    	this.setVOTableName("PT_BU_PRO_ACTIVE");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setActiveContent(String ActiveContent){
		this.setInternal("ACTIVE_CONTENT" ,ActiveContent );
	}


	public String getActiveContent(){
		return (String)this.getInternal("ACTIVE_CONTENT");
	}
	public void setActiveStatus(String ActiveStatus){
		this.setInternal("ACTIVE_STATUS" ,ActiveStatus );
	}


	public String getActiveStatus(){
		return (String)this.getInternal("ACTIVE_STATUS");
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
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setIssueTime(Date IssueTime){
		this.setInternal("ISSUE_TIME" ,IssueTime );
	}


	public Date getIssueTime(){
		return (Date)this.getInternal("ISSUE_TIME");
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
	public void setStartDate(Date StartDate){
		this.setInternal("START_DATE" ,StartDate );
	}


	public Date getStartDate(){
		return (Date)this.getInternal("START_DATE");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setIssueUser(String IssueUser){
		this.setInternal("ISSUE_USER" ,IssueUser );
	}


	public String getIssueUser(){
		return (String)this.getInternal("ISSUE_USER");
	}
	public void setActiveCode(String ActiveCode){
		this.setInternal("ACTIVE_CODE" ,ActiveCode );
	}


	public String getActiveCode(){
		return (String)this.getInternal("ACTIVE_CODE");
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
