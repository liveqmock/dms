package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBaOtherCostVO extends BaseVO{
    public SeBaOtherCostVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("COSTS_NAME",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("COST_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("COSTS_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("COST_ID","COMMON");
    	this.setVOTableName("SE_BA_OTHER_COST");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setCostsName(String CostsName){
		this.setInternal("COSTS_NAME" ,CostsName );
	}


	public String getCostsName(){
		return (String)this.getInternal("COSTS_NAME");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
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
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setCostId(String CostId){
		this.setInternal("COST_ID" ,CostId );
	}


	public String getCostId(){
		return (String)this.getInternal("COST_ID");
	}
	public void setCostsCode(String CostsCode){
		this.setInternal("COSTS_CODE" ,CostsCode );
	}


	public String getCostsCode(){
		return (String)this.getInternal("COSTS_CODE");
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
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
}
