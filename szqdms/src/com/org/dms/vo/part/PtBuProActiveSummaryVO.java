package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuProActiveSummaryVO extends BaseVO{
    public PtBuProActiveSummaryVO(){
    	//活动方案总结提报VO
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("DEAL_ID",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("SUMMARY_CON",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("REAL_FEE",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("ACTIVE_CODE",BaseVO.OP_STRING);
    	this.addField("SUMMARY_STATUS",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("SUMMARY_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("ACTIVE_NAME",BaseVO.OP_STRING);
    	this.addField("SUM_REPORT_USER",BaseVO.OP_STRING);
    	this.addField("SUM_REPORT_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("SUM_REPORT_TIME", "yyyy-MM-dd");
    	this.addField("ACTIVE_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("SUMMARY_ID","COMMON");
		//设置字典类型定义
		this.bindFieldToDic("STATUS","YXBS");//有效标识
		this.bindFieldToDic("SUMMARY_STATUS","HDZXZT");//总结状态
    	this.setVOTableName("PT_BU_PRO_ACTIVE_SUMMARY");
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
	public void setSummaryCon(String SummaryCon){
		this.setInternal("SUMMARY_CON" ,SummaryCon );
	}


	public String getSummaryCon(){
		return (String)this.getInternal("SUMMARY_CON");
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
	public void setRealFee(String RealFee){
		this.setInternal("REAL_FEE" ,RealFee );
	}


	public String getRealFee(){
		return (String)this.getInternal("REAL_FEE");
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
	public void setSummaryStatus(String SummaryStatus){
		this.setInternal("SUMMARY_STATUS" ,SummaryStatus );
	}


	public String getSummaryStatus(){
		return (String)this.getInternal("SUMMARY_STATUS");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setSummaryId(String SummaryId){
		this.setInternal("SUMMARY_ID" ,SummaryId );
	}


	public String getSummaryId(){
		return (String)this.getInternal("SUMMARY_ID");
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
