package com.org.dms.vo.part;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaWarehousePositionVO_Ext extends PtBaWarehousePositionVO{
    public PtBaWarehousePositionVO_Ext(){
    	//设置字段信息
    	this.addField("WAREHOUSE_NAME",BaseVO.OP_STRING);
    	this.addField("WAREHOUSE_CODE",BaseVO.OP_STRING);
    	this.bindFieldToDic("POSITION_STATUS", DicConstant.YXBS);
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
}
