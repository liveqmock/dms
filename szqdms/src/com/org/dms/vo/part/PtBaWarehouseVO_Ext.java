package com.org.dms.vo.part;

import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaWarehouseVO_Ext extends PtBaWarehouseVO{
    public PtBaWarehouseVO_Ext(){
    	//设置字段信息
    	this.addField("PARAVALUE1",BaseVO.OP_STRING);
    	this.addField("PARAKEY",BaseVO.OP_STRING);
    	this.addField("FLAG",BaseVO.OP_STRING);
    	this.addField("WAREHOUSE_ID",BaseVO.OP_STRING);
}
	public void setWarehouseId(String WarehouseId){
		this.setInternal("WAREHOUSE_ID" ,WarehouseId );
	}


	public String getWarehouseId(){
		return (String)this.getInternal("WAREHOUSE_ID");
	}

	public void setParaValue1(String ParaValue1){
		this.setInternal("PARAVALUE1" ,ParaValue1 );
	}


	public String getParaValue1(){
		return (String)this.getInternal("PARAVALUE1");
	}

	public void setParaKey(String ParaKey){
		this.setInternal("PARAKEY" ,ParaKey );
	}


	public String getParaKey(){
		return (String)this.getInternal("PARAKEY");
	}
	
	public void setFlag(String Flag){
		this.setInternal("FLAG" ,Flag );
	}


	public String getFlag(){
		return (String)this.getInternal("FLAG");
	}
}
