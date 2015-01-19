package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
public class SeBaFaultPatternExtendsVO extends SeBaFaultPatternVO{
    public SeBaFaultPatternExtendsVO(){
    	//设置字段信息 车辆部位名称和严重程度名称
    	this.addField("POSITION_NAME",BaseVO.OP_STRING);
    	this.addField("NAME",BaseVO.OP_STRING);	
    	
    	//设置字典类型定义
		this.bindFieldToDic("STATUS","YXBS");//有效标识
		this.bindFieldToDic("SECRET_LEVEL","SJMJ");//数据密级
}
	public void setPositionName(String PositionName){
		this.setInternal("POSITION_NAME" ,PositionName );
	}
	public String getpositionName(){
		return (String)this.getInternal("POSITION_NAME");
	}
	
	public void setName(String Name){
		this.setInternal("NAME" ,Name );
	}
	public String getName(){
		return (String)this.getInternal("NAME");
	}
	
}
