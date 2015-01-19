package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
public class SeBaTaskAmountVO_Ext extends SeBaTaskAmountVO{
    public SeBaTaskAmountVO_Ext(){
    	//�����ֶ���Ϣ
    	this.addField("MODELS_NAME",BaseVO.OP_STRING);
       	this.addField("POSITION_NAME",BaseVO.OP_STRING);	
}
    public void setModelsName(String ModelsName){
  		this.setInternal("MODELS_NAME" ,ModelsName );
  	}


  	public String getModelsName(){
  		return (String)this.getInternal("MODELS_NAME");
  	}
  	
    public void setPositionName(String PositionName){
  		this.setInternal("POSITION_NAME" ,PositionName );
  	}


  	public String getPositionName(){
  		return (String)this.getInternal("POSITION_NAME");
  	}
}
