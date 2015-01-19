package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuOrderShipCarrierVO extends BaseVO{
    public PtBuOrderShipCarrierVO(){
    	//ÉèÖÃ×Ö¶ÎÐÅÏ¢
    	this.addField("DRIVER_PHONE",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PHONE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("DRIVER_NAME",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("NO_PACKED",BaseVO.OP_STRING);
    	this.addField("LINK_MAN",BaseVO.OP_STRING);
    	this.addField("SHIP_ID",BaseVO.OP_STRING);
    	this.addField("WOOD_BOX",BaseVO.OP_STRING);
    	this.addField("VEHICLE_ID",BaseVO.OP_STRING);
    	this.addField("DEL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PAPER_BOX",BaseVO.OP_STRING);
    	this.addField("OTHER_PACKED",BaseVO.OP_STRING);
    	this.addField("SHIP_VEL_REMARKS",BaseVO.OP_STRING);
    	this.addField("DRIVER_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("LICENSE_PLATE",BaseVO.OP_STRING);
    	this.addField("RECEIPT_NO",BaseVO.OP_STRING);
    	this.addField("EXPECT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("EXPECT_DATE", "yyyy-MM-dd");
    	this.addField("PLASTIC_PACKED",BaseVO.OP_STRING);
		this.bindFieldToSequence("DEL_ID","COMMON");
    	this.setVOTableName("PT_BU_ORDER_SHIP_CARRIER");
}
	public void setDriverPhone(String DriverPhone){
		this.setInternal("DRIVER_PHONE" ,DriverPhone );
	}


	public String getDriverPhone(){
		return (String)this.getInternal("DRIVER_PHONE");
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
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setDriverName(String DriverName){
		this.setInternal("DRIVER_NAME" ,DriverName );
	}


	public String getDriverName(){
		return (String)this.getInternal("DRIVER_NAME");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setNoPacked(String NoPacked){
		this.setInternal("NO_PACKED" ,NoPacked );
	}


	public String getNoPacked(){
		return (String)this.getInternal("NO_PACKED");
	}
	public void setLinkMan(String LinkMan){
		this.setInternal("LINK_MAN" ,LinkMan );
	}


	public String getLinkMan(){
		return (String)this.getInternal("LINK_MAN");
	}
	public void setShipId(String ShipId){
		this.setInternal("SHIP_ID" ,ShipId );
	}


	public String getShipId(){
		return (String)this.getInternal("SHIP_ID");
	}
	public void setWoodBox(String WoodBox){
		this.setInternal("WOOD_BOX" ,WoodBox );
	}


	public String getWoodBox(){
		return (String)this.getInternal("WOOD_BOX");
	}
	public void setVehicleId(String VehicleId){
		this.setInternal("VEHICLE_ID" ,VehicleId );
	}


	public String getVehicleId(){
		return (String)this.getInternal("VEHICLE_ID");
	}
	public void setDelId(String DelId){
		this.setInternal("DEL_ID" ,DelId );
	}


	public String getDelId(){
		return (String)this.getInternal("DEL_ID");
	}
	public void setPaperBox(String PaperBox){
		this.setInternal("PAPER_BOX" ,PaperBox );
	}


	public String getPaperBox(){
		return (String)this.getInternal("PAPER_BOX");
	}
	public void setOtherPacked(String OtherPacked){
		this.setInternal("OTHER_PACKED" ,OtherPacked );
	}


	public String getOtherPacked(){
		return (String)this.getInternal("OTHER_PACKED");
	}
	public void setShipVelRemarks(String ShipVelRemarks){
		this.setInternal("SHIP_VEL_REMARKS" ,ShipVelRemarks );
	}


	public String getShipVelRemarks(){
		return (String)this.getInternal("SHIP_VEL_REMARKS");
	}
	public void setDriverId(String DriverId){
		this.setInternal("DRIVER_ID" ,DriverId );
	}


	public String getDriverId(){
		return (String)this.getInternal("DRIVER_ID");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setLicensePlate(String LicensePlate){
		this.setInternal("LICENSE_PLATE" ,LicensePlate );
	}


	public String getLicensePlate(){
		return (String)this.getInternal("LICENSE_PLATE");
	}
	public void setReceiptNo(String ReceiptNo){
		this.setInternal("RECEIPT_NO" ,ReceiptNo );
	}


	public String getReceiptNo(){
		return (String)this.getInternal("RECEIPT_NO");
	}
	public void setExpectDate(Date ExpectDate){
		this.setInternal("EXPECT_DATE" ,ExpectDate );
	}


	public Date getExpectDate(){
		return (Date)this.getInternal("EXPECT_DATE");
	}
	public void setPlasticPacked(String PlasticPacked){
		this.setInternal("PLASTIC_PACKED" ,PlasticPacked );
	}


	public String getPlasticPacked(){
		return (String)this.getInternal("PLASTIC_PACKED");
	}
}
