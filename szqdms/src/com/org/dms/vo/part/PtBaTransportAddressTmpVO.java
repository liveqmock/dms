package com.org.dms.vo.part;

import com.org.framework.base.BaseVO;

public class PtBaTransportAddressTmpVO extends BaseVO{

	public PtBaTransportAddressTmpVO(){
    	//发运地址导入临时表VO
		this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
		this.addField("ROW_NUM",BaseVO.OP_STRING);
		
		this.addField("ORG_CODE",BaseVO.OP_STRING);
		this.addField("LINK_MAN",BaseVO.OP_STRING);
		this.addField("ORG_ID",BaseVO.OP_STRING);
		this.addField("PHONE",BaseVO.OP_STRING);
		this.addField("MOBILE",BaseVO.OP_STRING);
		this.addField("FAX",BaseVO.OP_STRING);
    	this.addField("E_MAIL",BaseVO.OP_STRING);
    	this.addField("ZIP_CODE",BaseVO.OP_STRING);
    	
    	this.addField("ADDR_TYPE",BaseVO.OP_STRING);
		this.addField("PROVINCE_CODE",BaseVO.OP_STRING);
		this.addField("CITY_CODE",BaseVO.OP_STRING);
		this.addField("COUNTRY_CODE",BaseVO.OP_STRING);
    	this.addField("ADDRESS",BaseVO.OP_STRING);
		
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("PT_BA_TRANSPORT_ADDRESS_TMP");

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
	public void setLinkMan(String LinkMan){
		this.setInternal("LINK_MAN" ,LinkMan );
	}


	public String getLinkMan(){
		return (String)this.getInternal("LINK_MAN");
	}
	public void setUserAccount(String UserAccount){
		this.setInternal("USER_ACCOUNT" ,UserAccount );
	}


	public String getUserAccount(){
		return (String)this.getInternal("USER_ACCOUNT");
	}
	public void setAddrType(String AddrType){
		this.setInternal("ADDR_TYPE" ,AddrType );
	}


	public String getAddrType(){
		return (String)this.getInternal("ADDR_TYPE");
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
	public void setMobile(String Mobile){
		this.setInternal("MOBILE" ,Mobile );
	}


	public String getMobile(){
		return (String)this.getInternal("MOBILE");
	}
	public void setRowNum(String RowNum){
		this.setInternal("ROW_NUM" ,RowNum );
	}


	public String getRowNum(){
		return (String)this.getInternal("ROW_NUM");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}


	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
	}
	public void setTmpId(String TmpId){
		this.setInternal("TMP_ID" ,TmpId );
	}


	public String getTmpId(){
		return (String)this.getInternal("TMP_ID");
	}
	public void setProvinceCode(String ProvinceCode){
		this.setInternal("PROVINCE_CODE" ,ProvinceCode );
	}


	public String getProvinceCode(){
		return (String)this.getInternal("PROVINCE_CODE");
	}
	public void setEMail(String EMail){
		this.setInternal("E_MAIL" ,EMail );
	}


	public String getEMail(){
		return (String)this.getInternal("E_MAIL");
	}
	public void setFax(String Fax){
		this.setInternal("FAX" ,Fax );
	}


	public String getFax(){
		return (String)this.getInternal("FAX");
	}
}
