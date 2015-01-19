package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaDriverVO extends BaseVO{
    public PtBaDriverVO(){
    	//司机信息管理VO
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("CARRIER_NAME",BaseVO.OP_STRING);
    	this.addField("CARRIER_CODE",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("ADDRESS",BaseVO.OP_STRING);
    	this.addField("DRIVER_NO",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("PHONE",BaseVO.OP_STRING);
    	this.addField("DRIVER_NAME",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("SEX",BaseVO.OP_STRING);
    	this.addField("EMAIL",BaseVO.OP_STRING);
    	this.addField("DRIVER_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("FIXED_LINE",BaseVO.OP_STRING);
    	this.addField("CARRIER_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("DRIVER_ID","COMMON");
		//设置字典类型定义
		this.bindFieldToDic("STATUS","YXBS");//有效标识
		this.bindFieldToDic("SEX","XB");//性别
    	this.setVOTableName("PT_BA_DRIVER");
}
    public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setCarrierName(String CarrierName){
		this.setInternal("CARRIER_NAME" ,CarrierName );
	}


	public String getCarrierName(){
		return (String)this.getInternal("CARRIER_NAME");
	}
	public void setCarrierCode(String CarrierCode){
		this.setInternal("CARRIER_CODE" ,CarrierCode );
	}


	public String getCarrierCode(){
		return (String)this.getInternal("CARRIER_CODE");
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
	public void setAddress(String Address){
		this.setInternal("ADDRESS" ,Address );
	}


	public String getAddress(){
		return (String)this.getInternal("ADDRESS");
	}
	public void setDriverNo(String DriverNo){
		this.setInternal("DRIVER_NO" ,DriverNo );
	}


	public String getDriverNo(){
		return (String)this.getInternal("DRIVER_NO");
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
	public void setPhone(String Phone){
		this.setInternal("PHONE" ,Phone );
	}


	public String getPhone(){
		return (String)this.getInternal("PHONE");
	}
	public void setDriverName(String DriverName){
		this.setInternal("DRIVER_NAME" ,DriverName );
	}


	public String getDriverName(){
		return (String)this.getInternal("DRIVER_NAME");
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
	public void setSex(String Sex){
		this.setInternal("SEX" ,Sex );
	}


	public String getSex(){
		return (String)this.getInternal("SEX");
	}
	public void setEmail(String Email){
		this.setInternal("EMAIL" ,Email );
	}


	public String getEmail(){
		return (String)this.getInternal("EMAIL");
	}
	public void setDriverId(String DriverId){
		this.setInternal("DRIVER_ID" ,DriverId );
	}


	public String getDriverId(){
		return (String)this.getInternal("DRIVER_ID");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setFixedLine(String FixedLine){
		this.setInternal("FIXED_LINE" ,FixedLine );
	}


	public String getFixedLine(){
		return (String)this.getInternal("FIXED_LINE");
	}
	public void setCarrierId(String CarrierId){
		this.setInternal("CARRIER_ID" ,CarrierId );
	}


	public String getCarrierId(){
		return (String)this.getInternal("CARRIER_ID");
	}
}
