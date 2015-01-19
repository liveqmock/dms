package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBaExtendWarrantyVO extends BaseVO{
    public SeBaExtendWarrantyVO(){
    	//设置字段信息
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("WARRANTY_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("WARRANTY_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("WARRANTY_NAME",BaseVO.OP_STRING);
		this.bindFieldToSequence("WARRANTY_ID","COMMON");
    	this.setVOTableName("SE_BA_EXTEND_WARRANTY");
		//设置字典类型定义
		this.bindFieldToDic("STATUS","YXBS");//有效标识
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
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
	public void setWarrantyId(String WarrantyId){
		this.setInternal("WARRANTY_ID" ,WarrantyId );
	}


	public String getWarrantyId(){
		return (String)this.getInternal("WARRANTY_ID");
	}
	public void setWarrantyCode(String WarrantyCode){
		this.setInternal("WARRANTY_CODE" ,WarrantyCode );
	}


	public String getWarrantyCode(){
		return (String)this.getInternal("WARRANTY_CODE");
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
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setWarrantyName(String WarrantyName){
		this.setInternal("WARRANTY_NAME" ,WarrantyName );
	}


	public String getWarrantyName(){
		return (String)this.getInternal("WARRANTY_NAME");
	}
}
