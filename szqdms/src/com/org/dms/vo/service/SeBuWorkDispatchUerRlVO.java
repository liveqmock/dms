package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuWorkDispatchUerRlVO extends BaseVO{
    public SeBuWorkDispatchUerRlVO(){
    	//�����ֶ���Ϣ
    	this.addField("IF_MAIN",BaseVO.OP_STRING);
    	this.addField("RL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("USER_ID",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("DISPATCH_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("RL_ID","COMMON");
    	this.setVOTableName("SE_BU_WORK_DISPATCH_UER_RL");
}
	public void setIfMain(String IfMain){
		this.setInternal("IF_MAIN" ,IfMain );
	}


	public String getIfMain(){
		return (String)this.getInternal("IF_MAIN");
	}
	public void setRlId(String RlId){
		this.setInternal("RL_ID" ,RlId );
	}


	public String getRlId(){
		return (String)this.getInternal("RL_ID");
	}
	public void setUserId(String UserId){
		this.setInternal("USER_ID" ,UserId );
	}


	public String getUserId(){
		return (String)this.getInternal("USER_ID");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setDispatchId(String DispatchId){
		this.setInternal("DISPATCH_ID" ,DispatchId );
	}


	public String getDispatchId(){
		return (String)this.getInternal("DISPATCH_ID");
	}
}
