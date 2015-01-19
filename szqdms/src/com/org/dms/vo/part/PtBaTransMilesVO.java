package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaTransMilesVO extends BaseVO{
    public PtBaTransMilesVO(){
    	//运费里程数维护VO
    	this.addField("BIRTHLAND_CODE",BaseVO.OP_STRING);
    	this.addField("CITY_CODE",BaseVO.OP_STRING);
    	this.addField("UNIT_PRICE",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("BIRTHLAND_NAME",BaseVO.OP_STRING);
    	this.addField("ADDRESS",BaseVO.OP_STRING);
    	this.addField("COUNTRY_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("PROVINCE_NAME",BaseVO.OP_STRING);
    	this.addField("MILES_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("TRANS_MILES",BaseVO.OP_STRING);
    	this.addField("CITY_NAME",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("PROVINCE_CODE",BaseVO.OP_STRING);
    	this.addField("COUNTRY_NAME",BaseVO.OP_STRING);
		this.bindFieldToSequence("MILES_ID","COMMON");
		//设置字典类型定义
		this.bindFieldToDic("STATUS","YXBS");//有效标识  
    	this.setVOTableName("PT_BA_TRANS_MILES");
}
    public void setBirthlandCode(String BirthlandCode){
		this.setInternal("BIRTHLAND_CODE" ,BirthlandCode );
	}


	public String getBirthlandCode(){
		return (String)this.getInternal("BIRTHLAND_CODE");
	}
	public void setCityCode(String CityCode){
		this.setInternal("CITY_CODE" ,CityCode );
	}


	public String getCityCode(){
		return (String)this.getInternal("CITY_CODE");
	}
	public void setUnitPrice(String UnitPrice){
		this.setInternal("UNIT_PRICE" ,UnitPrice );
	}


	public String getUnitPrice(){
		return (String)this.getInternal("UNIT_PRICE");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setAddress(String Address){
		this.setInternal("ADDRESS" ,Address );
	}


	public String getAddress(){
		return (String)this.getInternal("ADDRESS");
	}
	public void setBirthlandName(String BirthlandName){
		this.setInternal("BIRTHLAND_NAME" ,BirthlandName );
	}


	public String getBirthlandName(){
		return (String)this.getInternal("BIRTHLAND_NAME");
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
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setProvinceName(String ProvinceName){
		this.setInternal("PROVINCE_NAME" ,ProvinceName );
	}


	public String getProvinceName(){
		return (String)this.getInternal("PROVINCE_NAME");
	}
	public void setMilesId(String MilesId){
		this.setInternal("MILES_ID" ,MilesId );
	}


	public String getMilesId(){
		return (String)this.getInternal("MILES_ID");
	}
	public void setTransMiles(String TransMiles){
		this.setInternal("TRANS_MILES" ,TransMiles );
	}


	public String getTransMiles(){
		return (String)this.getInternal("TRANS_MILES");
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
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
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
}
