package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class MainDealerStarLogVO extends BaseVO{
    public MainDealerStarLogVO(){
    	//设置字段信息
    	this.addField("DEALER_NAME",BaseVO.OP_STRING);
    	this.addField("LOG_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("MODIFY_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("MODIFY_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("DEALER_CODE",BaseVO.OP_STRING);
    	this.addField("NEW_STAR",BaseVO.OP_STRING);
    	this.addField("OLD_STAR",BaseVO.OP_STRING);
    	this.addField("MODIFY_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("LOG_ID","COMMON");
    	this.setVOTableName("MAIN_DEALER_STAR_LOG");
}
	public void setDealerName(String DealerName){
		this.setInternal("DEALER_NAME" ,DealerName );
	}


	public String getDealerName(){
		return (String)this.getInternal("DEALER_NAME");
	}
	public void setLogId(String LogId){
		this.setInternal("LOG_ID" ,LogId );
	}


	public String getLogId(){
		return (String)this.getInternal("LOG_ID");
	}
	public void setModifyTime(Date ModifyTime){
		this.setInternal("MODIFY_TIME" ,ModifyTime );
	}


	public Date getModifyTime(){
		return (Date)this.getInternal("MODIFY_TIME");
	}
	public void setDealerCode(String DealerCode){
		this.setInternal("DEALER_CODE" ,DealerCode );
	}


	public String getDealerCode(){
		return (String)this.getInternal("DEALER_CODE");
	}
	public void setNewStar(String NewStar){
		this.setInternal("NEW_STAR" ,NewStar );
	}


	public String getNewStar(){
		return (String)this.getInternal("NEW_STAR");
	}
	public void setOldStar(String OldStar){
		this.setInternal("OLD_STAR" ,OldStar );
	}


	public String getOldStar(){
		return (String)this.getInternal("OLD_STAR");
	}
	public void setModifyUser(String ModifyUser){
		this.setInternal("MODIFY_USER" ,ModifyUser );
	}


	public String getModifyUser(){
		return (String)this.getInternal("MODIFY_USER");
	}
}
