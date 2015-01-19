package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuQualityFbackDealVO extends BaseVO{
    public SeBuQualityFbackDealVO(){
    	//设置字段信息
    	this.addField("DEAL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("DEAL_ORG_ID",BaseVO.OP_STRING);
    	this.addField("DEAL_ORG_CODE",BaseVO.OP_STRING);
    	this.addField("FBACK_ID",BaseVO.OP_STRING);
    	this.addField("DEAL_REMARKS",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("DEAL_ORG_NAME",BaseVO.OP_STRING);
    	this.addField("DEAL_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("DEAL_DATE", "yyyy-MM-dd");
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("REJECT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("REJECT_DATE", "yyyy-MM-dd");
    	this.addField("DEAL_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("REJECT_REMARKS",BaseVO.OP_STRING);
    	this.addField("REJECT_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("DEAL_ID","COMMON");
    	this.setVOTableName("SE_BU_QUALITY_FBACK_DEAL");
}
	public void setDealId(String DealId){
		this.setInternal("DEAL_ID" ,DealId );
	}


	public String getDealId(){
		return (String)this.getInternal("DEAL_ID");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setDealOrgId(String DealOrgId){
		this.setInternal("DEAL_ORG_ID" ,DealOrgId );
	}


	public String getDealOrgId(){
		return (String)this.getInternal("DEAL_ORG_ID");
	}
	public void setDealOrgCode(String DealOrgCode){
		this.setInternal("DEAL_ORG_CODE" ,DealOrgCode );
	}


	public String getDealOrgCode(){
		return (String)this.getInternal("DEAL_ORG_CODE");
	}
	public void setFbackId(String FbackId){
		this.setInternal("FBACK_ID" ,FbackId );
	}


	public String getFbackId(){
		return (String)this.getInternal("FBACK_ID");
	}
	public void setDealRemarks(String DealRemarks){
		this.setInternal("DEAL_REMARKS" ,DealRemarks );
	}


	public String getDealRemarks(){
		return (String)this.getInternal("DEAL_REMARKS");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setDealOrgName(String DealOrgName){
		this.setInternal("DEAL_ORG_NAME" ,DealOrgName );
	}


	public String getDealOrgName(){
		return (String)this.getInternal("DEAL_ORG_NAME");
	}
	public void setDealDate(Date DealDate){
		this.setInternal("DEAL_DATE" ,DealDate );
	}


	public Date getDealDate(){
		return (Date)this.getInternal("DEAL_DATE");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setRejectDate(Date RejectDate){
		this.setInternal("REJECT_DATE" ,RejectDate );
	}


	public Date getRejectDate(){
		return (Date)this.getInternal("REJECT_DATE");
	}
	public void setDealUser(String DealUser){
		this.setInternal("DEAL_USER" ,DealUser );
	}


	public String getDealUser(){
		return (String)this.getInternal("DEAL_USER");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setRejectRemarks(String RejectRemarks){
		this.setInternal("REJECT_REMARKS" ,RejectRemarks );
	}


	public String getRejectRemarks(){
		return (String)this.getInternal("REJECT_REMARKS");
	}
	public void setRejectUser(String RejectUser){
		this.setInternal("REJECT_USER" ,RejectUser );
	}


	public String getRejectUser(){
		return (String)this.getInternal("REJECT_USER");
	}
}
