package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import com.org.dms.vo.service.MainDealerVO;
public class MainDealerExtendsVO extends MainDealerVO{
    public MainDealerExtendsVO(){
    	//设置字段信息
    	this.addField("PARANAME",BaseVO.OP_STRING);
}
	public void setParaName(String ParaName){
		this.setInternal("PARANAME" ,ParaName );
	}

	public String getParaName(){
		return (String)this.getInternal("PARANAME");
	}

}
