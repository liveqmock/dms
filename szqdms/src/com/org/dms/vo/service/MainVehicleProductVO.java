package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class MainVehicleProductVO extends BaseVO{
    public MainVehicleProductVO(){
    	//设置字段信息
    	this.addField("VEHICLE_PRODUCT",BaseVO.OP_STRING);
    	this.addField("VIN_END",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("VEHICLE_PRO_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("VIN_START",BaseVO.OP_STRING);
		this.bindFieldToSequence("VEHICLE_PRO_ID","COMMON");
    	this.setVOTableName("MAIN_VEHICLE_PRODUCT");
}
	public void setVehicleProduct(String VehicleProduct){
		this.setInternal("VEHICLE_PRODUCT" ,VehicleProduct );
	}


	public String getVehicleProduct(){
		return (String)this.getInternal("VEHICLE_PRODUCT");
	}
	public void setVinEnd(String VinEnd){
		this.setInternal("VIN_END" ,VinEnd );
	}


	public String getVinEnd(){
		return (String)this.getInternal("VIN_END");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setVehicleProId(String VehicleProId){
		this.setInternal("VEHICLE_PRO_ID" ,VehicleProId );
	}


	public String getVehicleProId(){
		return (String)this.getInternal("VEHICLE_PRO_ID");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setVinStart(String VinStart){
		this.setInternal("VIN_START" ,VinStart );
	}


	public String getVinStart(){
		return (String)this.getInternal("VIN_START");
	}
}
