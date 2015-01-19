package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaTransportAddressVO extends BaseVO{
    public PtBaTransportAddressVO(){
    	//发运地址管理VO
    	this.addField("ADDRESS_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ADDR_TYPE",BaseVO.OP_STRING);
    	this.addField("PHONE",BaseVO.OP_STRING);
    	this.addField("MOBILE",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PROVINCE_CODE",BaseVO.OP_STRING);
    	this.addField("COUNTRY_NAME",BaseVO.OP_STRING);
    	this.addField("FAX",BaseVO.OP_STRING);
    	this.addField("CITY_CODE",BaseVO.OP_STRING);
    	this.addField("ZIP_CODE",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("ADDRESS",BaseVO.OP_STRING);
    	this.addField("COUNTRY_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("PROVINCE_NAME",BaseVO.OP_STRING);
    	this.addField("LINK_MAN",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("CITY_NAME",BaseVO.OP_STRING);
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("E_MAIL",BaseVO.OP_STRING);
		this.bindFieldToSequence("ADDRESS_ID","COMMON");
		//设置字典类型定义
		this.bindFieldToDic("ADDR_TYPE","JHDDLX");//地址类型
		this.bindFieldToDic("STATUS","YXBS");//有效标识   
    	this.setVOTableName("PT_BA_TRANSPORT_ADDRESS");
}
	public void setAddressId(String AddressId){
		this.setInternal("ADDRESS_ID" ,AddressId );
	}


	public String getAddressId(){
		return (String)this.getInternal("ADDRESS_ID");
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
	public void setOrgName(String OrgName){
		this.setInternal("ORG_NAME" ,OrgName );
	}


	public String getOrgName(){
		return (String)this.getInternal("ORG_NAME");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setAddrType(String AddrType){
		this.setInternal("ADDR_TYPE" ,AddrType );
	}


	public String getAddrType(){
		return (String)this.getInternal("ADDR_TYPE");
	}
	public void setPhone(String Phone){
		this.setInternal("PHONE" ,Phone );
	}


	public String getPhone(){
		return (String)this.getInternal("PHONE");
	}
	public void setMobile(String Mobile){
		this.setInternal("MOBILE" ,Mobile );
	}


	public String getMobile(){
		return (String)this.getInternal("MOBILE");
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
	public void setProvinceCode(String ProvinceCode){
		this.setInternal("PROVINCE_CODE" ,ProvinceCode );
	}


	public String getProvinceCode(){
		return (String)this.getInternal("PROVINCE_CODE");
	}
	public void setCountryName(String CountryName){
		this.setInternal("COUNTRY_NAME" ,CountryName );
	}


	public String getCountryName(){
		return (String)this.getInternal("COUNTRY_NAME");
	}
	public void setFax(String Fax){
		this.setInternal("FAX" ,Fax );
	}


	public String getFax(){
		return (String)this.getInternal("FAX");
	}
	public void setCityCode(String CityCode){
		this.setInternal("CITY_CODE" ,CityCode );
	}


	public String getCityCode(){
		return (String)this.getInternal("CITY_CODE");
	}
	public void setZipCode(String ZipCode){
		this.setInternal("ZIP_CODE" ,ZipCode );
	}


	public String getZipCode(){
		return (String)this.getInternal("ZIP_CODE");
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
	public void setCityName(String CityName){
		this.setInternal("CITY_NAME" ,CityName );
	}


	public String getCityName(){
		return (String)this.getInternal("CITY_NAME");
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
	public void setEMail(String EMail){
		this.setInternal("E_MAIL" ,EMail );
	}


	public String getEMail(){
		return (String)this.getInternal("E_MAIL");
	}
}
