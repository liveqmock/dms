package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class MainBulletinVO extends BaseVO{
    public MainBulletinVO(){
    	//设置字段信息
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("SEND_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("SEND_DATE", "yyyy-MM-dd");
    	this.addField("USER_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("BULLETIN_RANGE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CONTENT",BaseVO.OP_STRING);
    	this.addField("BULLETIN_STATUS",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("TYPE_ID",BaseVO.OP_STRING);
    	this.addField("BULLETIN_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("TITLE",BaseVO.OP_STRING);
		this.bindFieldToSequence("BULLETIN_ID","COMMON");
    	this.setVOTableName("MAIN_BULLETIN");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setSendDate(Date SendDate){
		this.setInternal("SEND_DATE" ,SendDate );
	}


	public Date getSendDate(){
		return (Date)this.getInternal("SEND_DATE");
	}
	public void setUserId(String UserId){
		this.setInternal("USER_ID" ,UserId );
	}


	public String getUserId(){
		return (String)this.getInternal("USER_ID");
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
	public void setBulletinRange(String BulletinRange){
		this.setInternal("BULLETIN_RANGE" ,BulletinRange );
	}


	public String getBulletinRange(){
		return (String)this.getInternal("BULLETIN_RANGE");
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
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setContent(String Content){
		this.setInternal("CONTENT" ,Content );
	}


	public String getContent(){
		return (String)this.getInternal("CONTENT");
	}
	public void setBulletinStatus(String BulletinStatus){
		this.setInternal("BULLETIN_STATUS" ,BulletinStatus );
	}


	public String getBulletinStatus(){
		return (String)this.getInternal("BULLETIN_STATUS");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setTypeId(String TypeId){
		this.setInternal("TYPE_ID" ,TypeId );
	}


	public String getTypeId(){
		return (String)this.getInternal("TYPE_ID");
	}
	public void setBulletinId(String BulletinId){
		this.setInternal("BULLETIN_ID" ,BulletinId );
	}


	public String getBulletinId(){
		return (String)this.getInternal("BULLETIN_ID");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setTitle(String Title){
		this.setInternal("TITLE" ,Title );
	}


	public String getTitle(){
		return (String)this.getInternal("TITLE");
	}
}
