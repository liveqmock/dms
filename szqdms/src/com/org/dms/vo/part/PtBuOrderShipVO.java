package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuOrderShipVO extends BaseVO{
    public PtBuOrderShipVO(){
    	//设置字段信息
    	this.addField("DRIVER_PHONE",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("CARRIER_NAME",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PHONE",BaseVO.OP_STRING);
    	this.addField("SHIP_NO",BaseVO.OP_STRING);
    	this.addField("DRIVER_NAME",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("IF_ARMY",BaseVO.OP_STRING);
    	this.addField("SHIP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("LINK_MAN",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("VEHICLE_ID",BaseVO.OP_STRING);
    	this.addField("SHIP_STATUS",BaseVO.OP_STRING);
    	this.addField("SHIP_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("SHIP_DATE", "yyyy-MM-dd");
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("LICENSE_PLATE",BaseVO.OP_STRING);
    	this.addField("CARRIER_ID",BaseVO.OP_STRING);
    	this.addField("CARRIER_REMARKS",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("RECEIVE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("CONFIRM_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CONFIRM_TIME", "yyyy-MM-dd");
    	this.addField("RECEIVE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("RECEIVE_TIME", "yyyy-MM-dd");
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("CONFIRM_USER",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CARRIER_CODE",BaseVO.OP_STRING);
    	this.addField("DRIVER_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("SHIP_ID","COMMON");
    	this.setVOTableName("PT_BU_ORDER_SHIP");
}
	public void setDriverPhone(String DriverPhone){
		this.setInternal("DRIVER_PHONE" ,DriverPhone );
	}


	public String getDriverPhone(){
		return (String)this.getInternal("DRIVER_PHONE");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setCarrierName(String CarrierName){
		this.setInternal("CARRIER_NAME" ,CarrierName );
	}


	public String getCarrierName(){
		return (String)this.getInternal("CARRIER_NAME");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setPhone(String Phone){
		this.setInternal("PHONE" ,Phone );
	}


	public String getPhone(){
		return (String)this.getInternal("PHONE");
	}
	public void setShipNo(String ShipNo){
		this.setInternal("SHIP_NO" ,ShipNo );
	}


	public String getShipNo(){
		return (String)this.getInternal("SHIP_NO");
	}
	public void setDriverName(String DriverName){
		this.setInternal("DRIVER_NAME" ,DriverName );
	}


	public String getDriverName(){
		return (String)this.getInternal("DRIVER_NAME");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setIfArmy(String IfArmy){
		this.setInternal("IF_ARMY" ,IfArmy );
	}


	public String getIfArmy(){
		return (String)this.getInternal("IF_ARMY");
	}
	public void setShipId(String ShipId){
		this.setInternal("SHIP_ID" ,ShipId );
	}


	public String getShipId(){
		return (String)this.getInternal("SHIP_ID");
	}
	public void setLinkMan(String LinkMan){
		this.setInternal("LINK_MAN" ,LinkMan );
	}


	public String getLinkMan(){
		return (String)this.getInternal("LINK_MAN");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setVehicleId(String VehicleId){
		this.setInternal("VEHICLE_ID" ,VehicleId );
	}


	public String getVehicleId(){
		return (String)this.getInternal("VEHICLE_ID");
	}
	public void setShipStatus(String ShipStatus){
		this.setInternal("SHIP_STATUS" ,ShipStatus );
	}


	public String getShipStatus(){
		return (String)this.getInternal("SHIP_STATUS");
	}
	public void setShipDate(Date ShipDate){
		this.setInternal("SHIP_DATE" ,ShipDate );
	}


	public Date getShipDate(){
		return (Date)this.getInternal("SHIP_DATE");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setLicensePlate(String LicensePlate){
		this.setInternal("LICENSE_PLATE" ,LicensePlate );
	}


	public String getLicensePlate(){
		return (String)this.getInternal("LICENSE_PLATE");
	}
	public void setCarrierId(String CarrierId){
		this.setInternal("CARRIER_ID" ,CarrierId );
	}


	public String getCarrierId(){
		return (String)this.getInternal("CARRIER_ID");
	}
	public void setCarrierRemarks(String CarrierRemarks){
		this.setInternal("CARRIER_REMARKS" ,CarrierRemarks );
	}


	public String getCarrierRemarks(){
		return (String)this.getInternal("CARRIER_REMARKS");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setReceiveUser(String ReceiveUser){
		this.setInternal("RECEIVE_USER" ,ReceiveUser );
	}


	public String getReceiveUser(){
		return (String)this.getInternal("RECEIVE_USER");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setConfirmTime(Date ConfirmTime){
		this.setInternal("CONFIRM_TIME" ,ConfirmTime );
	}


	public Date getConfirmTime(){
		return (Date)this.getInternal("CONFIRM_TIME");
	}
	public void setReceiveTime(Date ReceiveTime){
		this.setInternal("RECEIVE_TIME" ,ReceiveTime );
	}


	public Date getReceiveTime(){
		return (Date)this.getInternal("RECEIVE_TIME");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setConfirmUser(String ConfirmUser){
		this.setInternal("CONFIRM_USER" ,ConfirmUser );
	}


	public String getConfirmUser(){
		return (String)this.getInternal("CONFIRM_USER");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setCarrierCode(String CarrierCode){
		this.setInternal("CARRIER_CODE" ,CarrierCode );
	}


	public String getCarrierCode(){
		return (String)this.getInternal("CARRIER_CODE");
	}
	public void setDriverId(String DriverId){
		this.setInternal("DRIVER_ID" ,DriverId );
	}


	public String getDriverId(){
		return (String)this.getInternal("DRIVER_ID");
	}
}
