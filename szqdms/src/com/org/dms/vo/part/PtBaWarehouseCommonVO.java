package com.org.dms.vo.part;

import com.org.framework.base.BaseVO;
public class PtBaWarehouseCommonVO extends BaseVO{
    public PtBaWarehouseCommonVO(){
    	//设置字段信息
    	this.addField("FLAG",BaseVO.OP_STRING);
    }
	
	public void setFlag(String Flag){
		this.setInternal("FLAG" ,Flag );
	}


	public String getFlag(){
		return (String)this.getInternal("FLAG");
	}
}
