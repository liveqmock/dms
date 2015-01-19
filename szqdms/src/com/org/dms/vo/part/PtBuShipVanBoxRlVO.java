package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuShipVanBoxRlVO extends BaseVO{
    public PtBuShipVanBoxRlVO(){
    	//�����ֶ���Ϣ
    	this.addField("SHIP_ID",BaseVO.OP_STRING);
    	this.addField("VEHICLE_ID",BaseVO.OP_STRING);
    	this.addField("RL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("BOX_NO",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("RL_ID","COMMON");
    	this.setVOTableName("PT_BU_SHIP_VAN_BOX_RL");
}
	public void setShipId(String ShipId){
		this.setInternal("SHIP_ID" ,ShipId );
	}


	public String getShipId(){
		return (String)this.getInternal("SHIP_ID");
	}
	public void setVehicleId(String VehicleId){
		this.setInternal("VEHICLE_ID" ,VehicleId );
	}


	public String getVehicleId(){
		return (String)this.getInternal("VEHICLE_ID");
	}
	public void setRlId(String RlId){
		this.setInternal("RL_ID" ,RlId );
	}


	public String getRlId(){
		return (String)this.getInternal("RL_ID");
	}
	public void setBoxNo(String BoxNo){
		this.setInternal("BOX_NO" ,BoxNo );
	}


	public String getBoxNo(){
		return (String)this.getInternal("BOX_NO");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
}
