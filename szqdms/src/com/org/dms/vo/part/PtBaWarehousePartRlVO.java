package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaWarehousePartRlVO extends BaseVO{
    public PtBaWarehousePartRlVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("POSITION_NAME",BaseVO.OP_STRING);
    	this.addField("POSITION_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("IF_DEFAULT",BaseVO.OP_STRING);
    	this.addField("POSITION_ID",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("KEEP_MAN",BaseVO.OP_STRING);
    	this.addField("RELATION_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
		this.bindFieldToSequence("RELATION_ID","COMMON");
    	this.setVOTableName("PT_BA_WAREHOUSE_PART_RL");
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
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setKeepMan(String KeepMan){
		this.setInternal("KEEP_MAN" ,KeepMan );
	}


	public String getKeepMan(){
		return (String)this.getInternal("KEEP_MAN");
	}
	public void setPositionName(String PositionName){
		this.setInternal("POSITION_NAME" ,PositionName );
	}


	public String getPositionName(){
		return (String)this.getInternal("POSITION_NAME");
	}
	public void setPositionCode(String PositionCode){
		this.setInternal("POSITION_CODE" ,PositionCode );
	}


	public String getPositionCode(){
		return (String)this.getInternal("POSITION_CODE");
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
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
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
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setIfDefault(String IfDefault){
		this.setInternal("IF_DEFAULT" ,IfDefault );
	}


	public String getIfDefault(){
		return (String)this.getInternal("IF_DEFAULT");
	}
	public void setPositionId(String PositionId){
		this.setInternal("POSITION_ID" ,PositionId );
	}


	public String getPositionId(){
		return (String)this.getInternal("POSITION_ID");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setRelationId(String RelationId){
		this.setInternal("RELATION_ID" ,RelationId );
	}


	public String getRelationId(){
		return (String)this.getInternal("RELATION_ID");
	}
}
