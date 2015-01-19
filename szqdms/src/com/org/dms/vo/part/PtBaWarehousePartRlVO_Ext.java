package com.org.dms.vo.part;

import com.org.framework.base.BaseVO;
public class PtBaWarehousePartRlVO_Ext extends PtBaWarehousePartRlVO{
    public PtBaWarehousePartRlVO_Ext(){
    	//设置字段信息
    	this.addField("WAREHOUSE_NAME",BaseVO.OP_STRING);
    	this.addField("WAREHOUSE_CODE",BaseVO.OP_STRING);
    	this.addField("AREA_CODE",BaseVO.OP_STRING);
    	this.addField("AREA_NAME",BaseVO.OP_STRING);
}
	public void setWarehouseName(String WarehouseName){
		this.setInternal("WAREHOUSE_NAME" ,WarehouseName );
	}


	public String getWarehouseName1(){
		return (String)this.getInternal("WAREHOUSE_NAME");
	}

	public void setWarehouseCode(String WarehouseCode){
		this.setInternal("WAREHOUSE_CODE" ,WarehouseCode );
	}


	public String getWarehouseCode(){
		return (String)this.getInternal("WAREHOUSE_CODE");
	}
	
	public void setAreaName(String AreaName){
		this.setInternal("AREA_NAME" ,AreaName );
	}


	public String getAreaName(){
		return (String)this.getInternal("AREA_NAME");
	}

	public void setAreaCode(String AreaCode){
		this.setInternal("AREA_CODE" ,AreaCode );
	}


	public String getAreaCode(){
		return (String)this.getInternal("AREA_CODE");
	}
}
