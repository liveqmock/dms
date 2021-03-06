package com.org.dms.vo.part;

import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuChangeShipAddressVO extends BaseVO{
    public PtBuChangeShipAddressVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("ADDRESS_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("PHONE",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PROVINCE_CODE",BaseVO.OP_STRING);
    	this.addField("COUNTRY_NAME",BaseVO.OP_STRING);
    	this.addField("ZIP_CODE",BaseVO.OP_STRING);
    	this.addField("CITY_CODE",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("ADDRESS",BaseVO.OP_STRING);
    	this.addField("ORDER_NO",BaseVO.OP_STRING);
    	this.addField("COUNTRY_CODE",BaseVO.OP_STRING);
    	this.addField("CHECK_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CHECK_TIME", "yyyy-MM-dd");
    	this.addField("CHECK_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("PROVINCE_NAME",BaseVO.OP_STRING);
    	this.addField("TELEPHONE",BaseVO.OP_STRING);
    	this.addField("LINK_MAN",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("ORDER_ID",BaseVO.OP_STRING);
    	this.addField("ADD_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("CHANGE_STATUS",BaseVO.OP_STRING);
    	this.addField("CITY_NAME",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("ADD_ID","COMMON");
    	this.setVOTableName("PT_BU_CHANGE_SHIP_ADDRESS");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setAddressId(String AddressId){
		this.setInternal("ADDRESS_ID" ,AddressId );
	}


	public String getAddressId(){
		return (String)this.getInternal("ADDRESS_ID");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
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
	public void setZipCode(String ZipCode){
		this.setInternal("ZIP_CODE" ,ZipCode );
	}


	public String getZipCode(){
		return (String)this.getInternal("ZIP_CODE");
	}
	public void setCityCode(String CityCode){
		this.setInternal("CITY_CODE" ,CityCode );
	}


	public String getCityCode(){
		return (String)this.getInternal("CITY_CODE");
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
	public void setOrderNo(String OrderNo){
		this.setInternal("ORDER_NO" ,OrderNo );
	}


	public String getOrderNo(){
		return (String)this.getInternal("ORDER_NO");
	}
	public void setCountryCode(String CountryCode){
		this.setInternal("COUNTRY_CODE" ,CountryCode );
	}


	public String getCountryCode(){
		return (String)this.getInternal("COUNTRY_CODE");
	}
	public void setCheckTime(Date CheckTime){
		this.setInternal("CHECK_TIME" ,CheckTime );
	}


	public Date getCheckTime(){
		return (Date)this.getInternal("CHECK_TIME");
	}
	public void setCheckUser(String CheckUser){
		this.setInternal("CHECK_USER" ,CheckUser );
	}


	public String getCheckUser(){
		return (String)this.getInternal("CHECK_USER");
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
	public void setTelephone(String Telephone){
		this.setInternal("TELEPHONE" ,Telephone );
	}


	public String getTelephone(){
		return (String)this.getInternal("TELEPHONE");
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
	public void setOrderId(String OrderId){
		this.setInternal("ORDER_ID" ,OrderId );
	}


	public String getOrderId(){
		return (String)this.getInternal("ORDER_ID");
	}
	public void setAddId(String AddId){
		this.setInternal("ADD_ID" ,AddId );
	}


	public String getAddId(){
		return (String)this.getInternal("ADD_ID");
	}
	public void setChangeStatus(String ChangeStatus){
		this.setInternal("CHANGE_STATUS" ,ChangeStatus );
	}


	public String getChangeStatus(){
		return (String)this.getInternal("CHANGE_STATUS");
	}
	public void setCityName(String CityName){
		this.setInternal("CITY_NAME" ,CityName );
	}


	public String getCityName(){
		return (String)this.getInternal("CITY_NAME");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
}
