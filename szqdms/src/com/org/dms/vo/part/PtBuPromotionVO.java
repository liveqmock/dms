package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuPromotionVO extends BaseVO{
    public PtBuPromotionVO(){
    	//�����ֶ���Ϣ
    	this.addField("PUBLISHER",BaseVO.OP_STRING);
    	this.addField("PROM_CODE",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("IF_TRANS_FREE",BaseVO.OP_STRING);
    	this.addField("PROM_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("END_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	this.addField("START_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("PROM_TYPE",BaseVO.OP_STRING);
    	this.addField("PUBLISH_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("PUBLISH_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("PROM_STATUS",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("PROM_NAME",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("ANNOUNCEMENT_NO",BaseVO.OP_STRING);
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.bindFieldToDic("PROM_STATUS", "CXHDZT");
    	this.bindFieldToDic("PROM_STATUS", "CXHDZT");
    	this.bindFieldToDic("PROM_TYPE", "CXHDLX");
		this.bindFieldToDic("IF_TRANS_FREE", "SF");
		this.bindFieldToSequence("PROM_ID","COMMON");
    	this.setVOTableName("PT_BU_PROMOTION");
}
	public void setPublisher(String Publisher){
		this.setInternal("PUBLISHER" ,Publisher );
	}


	public String getPublisher(){
		return (String)this.getInternal("PUBLISHER");
	}
	public void setPromCode(String PromCode){
		this.setInternal("PROM_CODE" ,PromCode );
	}


	public String getPromCode(){
		return (String)this.getInternal("PROM_CODE");
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
	public void setIfTransFree(String IfTransFree){
		this.setInternal("IF_TRANS_FREE" ,IfTransFree );
	}


	public String getIfTransFree(){
		return (String)this.getInternal("IF_TRANS_FREE");
	}
	public void setPromId(String PromId){
		this.setInternal("PROM_ID" ,PromId );
	}


	public String getPromId(){
		return (String)this.getInternal("PROM_ID");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setEndDate(Date EndDate){
		this.setInternal("END_DATE" ,EndDate );
	}


	public Date getEndDate(){
		return (Date)this.getInternal("END_DATE");
	}
	public void setStartDate(Date StartDate){
		this.setInternal("START_DATE" ,StartDate );
	}


	public Date getStartDate(){
		return (Date)this.getInternal("START_DATE");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setPromType(String PromType){
		this.setInternal("PROM_TYPE" ,PromType );
	}


	public String getPromType(){
		return (String)this.getInternal("PROM_TYPE");
	}
	public void setPublishTime(Date PublishTime){
		this.setInternal("PUBLISH_TIME" ,PublishTime );
	}


	public Date getPublishTime(){
		return (Date)this.getInternal("PUBLISH_TIME");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setPromStatus(String PromStatus){
		this.setInternal("PROM_STATUS" ,PromStatus );
	}


	public String getPromStatus(){
		return (String)this.getInternal("PROM_STATUS");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setPromName(String PromName){
		this.setInternal("PROM_NAME" ,PromName );
	}


	public String getPromName(){
		return (String)this.getInternal("PROM_NAME");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setAnnouncementNo(String AnnouncementNo){
		this.setInternal("ANNOUNCEMENT_NO" ,AnnouncementNo );
	}


	public String getAnnouncementNo(){
		return (String)this.getInternal("ANNOUNCEMENT_NO");
	}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
}
