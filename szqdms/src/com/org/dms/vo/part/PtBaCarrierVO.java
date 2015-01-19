package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaCarrierVO extends BaseVO{
    public PtBaCarrierVO(){
    	//承运商信息管理VO   
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("CITY_CODE",BaseVO.OP_STRING);
    	this.addField("CARRIER_NAME",BaseVO.OP_STRING);
    	this.addField("CARRIER_CODE",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("ADDRESS",BaseVO.OP_STRING);
    	this.addField("COUNTRY_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("PROVINCE_NAME",BaseVO.OP_STRING);
    	this.addField("IF_ARMY",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("LINK_MAN",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("PHONE",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CITY_NAME",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("EMAIL",BaseVO.OP_STRING);
    	this.addField("PROVINCE_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("COUNTRY_NAME",BaseVO.OP_STRING);
    	this.addField("FIXED_LINE",BaseVO.OP_STRING);
    	this.addField("CARRIER_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
		this.bindFieldToSequence("CARRIER_ID","COMMON");
		//设置字典类型定义
		this.bindFieldToDic("SF","IF_ARMY");//是否军品承运商
		this.bindFieldToDic("STATUS","YXBS");//有效标识
    	this.setVOTableName("PT_BA_CARRIER");
}
    public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setCityCode(String CityCode){
		this.setInternal("CITY_CODE" ,CityCode );
	}


	public String getCityCode(){
		return (String)this.getInternal("CITY_CODE");
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
	public void setAddress(String Address){
		this.setInternal("ADDRESS" ,Address );
	}


	public String getAddress(){
		return (String)this.getInternal("ADDRESS");
	}
	public void setCountryCode(String CountryCode){
		this.setInternal("COUNTRY_CODE" ,CountryCode );
	}


	public String getCountryCode(){
		return (String)this.getInternal("COUNTRY_CODE");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setProvinceName(String ProvinceName){
		this.setInternal("PROVINCE_NAME" ,ProvinceName );
	}


	public String getProvinceName(){
		return (String)this.getInternal("PROVINCE_NAME");
	}
	public void setIfArmy(String IfArmy){
		this.setInternal("IF_ARMY" ,IfArmy );
	}


	public String getIfArmy(){
		return (String)this.getInternal("IF_ARMY");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setLinkMan(String LinkMan){
		this.setInternal("LINK_MAN" ,LinkMan );
	}


	public String getLinkMan(){
		return (String)this.getInternal("LINK_MAN");
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
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setCityName(String CityName){
		this.setInternal("CITY_NAME" ,CityName );
	}


	public String getCityName(){
		return (String)this.getInternal("CITY_NAME");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setEmail(String Email){
		this.setInternal("EMAIL" ,Email );
	}


	public String getEmail(){
		return (String)this.getInternal("EMAIL");
	}
	public void setProvinceCode(String ProvinceCode){
		this.setInternal("PROVINCE_CODE" ,ProvinceCode );
	}


	public String getProvinceCode(){
		return (String)this.getInternal("PROVINCE_CODE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setCountryName(String CountryName){
		this.setInternal("COUNTRY_NAME" ,CountryName );
	}


	public String getCountryName(){
		return (String)this.getInternal("COUNTRY_NAME");
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
