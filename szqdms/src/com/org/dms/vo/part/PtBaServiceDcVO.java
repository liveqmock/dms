package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaServiceDcVO extends BaseVO{
    public PtBaServiceDcVO(){
    	//配送关系管理(配送中心--经销商、服务站之间的关系)VO
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("DC_ID",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("IF_DEFAULT",BaseVO.OP_STRING);
    	this.addField("DC_CODE",BaseVO.OP_STRING);
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("RELATION_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
		this.bindFieldToSequence("RELATION_ID","COMMON");
		
		//设置字典类型定义
		this.bindFieldToDic("IF_DEFAULT","SF");//是否默认配送中心
		this.bindFieldToDic("STATUS","YXBS");//有效标识 
    	this.setVOTableName("PT_BA_SERVICE_DC");
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
	public void setDcId(String DcId){
		this.setInternal("DC_ID" ,DcId );
	}


	public String getDcId(){
		return (String)this.getInternal("DC_ID");
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
	public void setIfDefault(String IfDefault){
		this.setInternal("IF_DEFAULT" ,IfDefault );
	}


	public String getIfDefault(){
		return (String)this.getInternal("IF_DEFAULT");
	}
	public void setDcCode(String DcCode){
		this.setInternal("DC_CODE" ,DcCode );
	}


	public String getDcCode(){
		return (String)this.getInternal("DC_CODE");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}


	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
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
