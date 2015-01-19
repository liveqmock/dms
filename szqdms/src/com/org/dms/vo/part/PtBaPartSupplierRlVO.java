package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaPartSupplierRlVO extends BaseVO{
    public PtBaPartSupplierRlVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("PART_IDENTIFY",BaseVO.OP_STRING);
    	this.addField("CLAIM_RATE",BaseVO.OP_STRING);
    	this.addField("PCH_USER",BaseVO.OP_STRING);
    	this.addField("PCH_PRICE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("SE_IDENTIFY",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("PLAN_PRICE",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("IF_STREAM",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("APPLY_CYCLE",BaseVO.OP_STRING);
    	this.addField("PCH_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("PCH_TIME", "yyyy-MM-dd");
    	this.addField("RELATION_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
		this.bindFieldToSequence("RELATION_ID","COMMON");
		
		//设置字典类型定义
		this.bindFieldToDic("PART_IDENTIFY", "YXBS"); 
		this.bindFieldToDic("SE_IDENTIFY", "YXBS"); 
		this.bindFieldToDic("STATUS","YXBS");
    	this.setVOTableName("PT_BA_PART_SUPPLIER_RL");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
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
	public void setPartIdentify(String PartIdentify){
		this.setInternal("PART_IDENTIFY" ,PartIdentify );
	}


	public String getPartIdentify(){
		return (String)this.getInternal("PART_IDENTIFY");
	}
	public void setClaimRate(String ClaimRate){
		this.setInternal("CLAIM_RATE" ,ClaimRate );
	}


	public String getClaimRate(){
		return (String)this.getInternal("CLAIM_RATE");
	}
	public void setPchUser(String PchUser){
		this.setInternal("PCH_USER" ,PchUser );
	}


	public String getPchUser(){
		return (String)this.getInternal("PCH_USER");
	}
	public void setPchPrice(String PchPrice){
		this.setInternal("PCH_PRICE" ,PchPrice );
	}


	public String getPchPrice(){
		return (String)this.getInternal("PCH_PRICE");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setSupplierId(String SupplierId){
		this.setInternal("SUPPLIER_ID" ,SupplierId );
	}


	public String getSupplierId(){
		return (String)this.getInternal("SUPPLIER_ID");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setSeIdentify(String SeIdentify){
		this.setInternal("SE_IDENTIFY" ,SeIdentify );
	}


	public String getSeIdentify(){
		return (String)this.getInternal("SE_IDENTIFY");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setPlanPrice(String PlanPrice){
		this.setInternal("PLAN_PRICE" ,PlanPrice );
	}


	public String getPlanPrice(){
		return (String)this.getInternal("PLAN_PRICE");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setIfStream(String IfStream){
		this.setInternal("IF_STREAM" ,IfStream );
	}


	public String getIfStream(){
		return (String)this.getInternal("IF_STREAM");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setApplyCycle(String ApplyCycle){
		this.setInternal("APPLY_CYCLE" ,ApplyCycle );
	}


	public String getApplyCycle(){
		return (String)this.getInternal("APPLY_CYCLE");
	}
	public void setPchTime(Date PchTime){
		this.setInternal("PCH_TIME" ,PchTime );
	}


	public Date getPchTime(){
		return (Date)this.getInternal("PCH_TIME");
	}
	public void setRelationId(String RelationId){
		this.setInternal("RELATION_ID" ,RelationId );
	}


	public String getRelationId(){
		return (String)this.getInternal("RELATION_ID");
	}
}
