package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
@SuppressWarnings("serial")
public class SeBaDriveVinVO extends BaseVO{
    public SeBaDriveVinVO(){
    	this.addField("RL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("VIN",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("DRIVE_TYPE",BaseVO.OP_STRING);
		this.bindFieldToSequence("RE_ID","COMMON");
    	this.setVOTableName("SE_BA_DRIVE_VIN");
    	this.bindFieldToDic("DRIVE_TYPE","QDXS");//驱动形式
}
	public void setRlId(String RlId){
		this.setInternal("RL_ID" ,RlId );
	}


	public String getRlId(){
		return (String)this.getInternal("RL_ID");
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
	public void setVin(String Vin){
		this.setInternal("VIN" ,Vin );
	}


	public String getVin(){
		return (String)this.getInternal("VIN");
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
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setDriveType(String DriveType){
		this.setInternal("DRIVE_TYPE" ,DriveType );
	}


	public String getDriveType(){
		return (String)this.getInternal("DRIVE_TYPE");
	}
}
