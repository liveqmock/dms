package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuStockOutModLogVO extends BaseVO{
    public PtBuStockOutModLogVO(){
    	//�����ֶ���Ϣ
    	this.addField("DEF_COUNT",BaseVO.OP_STRING);
    	this.addField("MOD_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("MOD_DATE", "yyyy-MM-dd");
    	this.addField("CREATE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_DATE", "yyyy-MM-dd");
    	this.addField("ORDER_ID",BaseVO.OP_STRING);
    	this.addField("MOD_MAN",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("OLD_COUNT",BaseVO.OP_STRING);
    	this.addField("OUT_ID",BaseVO.OP_STRING);
    	this.addField("KEEP_MAN",BaseVO.OP_STRING);
    	this.addField("MOD_COUNT",BaseVO.OP_STRING);
    	this.addField("LOG_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PART_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("LOG_ID","COMMON");
    	this.setVOTableName("PT_BU_STOCK_OUT_MOD_LOG");
}
	public void setDefCount(String DefCount){
		this.setInternal("DEF_COUNT" ,DefCount );
	}


	public String getDefCount(){
		return (String)this.getInternal("DEF_COUNT");
	}
	public void setModDate(Date ModDate){
		this.setInternal("MOD_DATE" ,ModDate );
	}


	public Date getModDate(){
		return (Date)this.getInternal("MOD_DATE");
	}
	public void setCreateDate(Date CreateDate){
		this.setInternal("CREATE_DATE" ,CreateDate );
	}


	public Date getCreateDate(){
		return (Date)this.getInternal("CREATE_DATE");
	}
	public void setOrderId(String OrderId){
		this.setInternal("ORDER_ID" ,OrderId );
	}


	public String getOrderId(){
		return (String)this.getInternal("ORDER_ID");
	}
	public void setModMan(String ModMan){
		this.setInternal("MOD_MAN" ,ModMan );
	}


	public String getModMan(){
		return (String)this.getInternal("MOD_MAN");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setOldCount(String OldCount){
		this.setInternal("OLD_COUNT" ,OldCount );
	}


	public String getOldCount(){
		return (String)this.getInternal("OLD_COUNT");
	}
	public void setOutId(String OutId){
		this.setInternal("OUT_ID" ,OutId );
	}


	public String getOutId(){
		return (String)this.getInternal("OUT_ID");
	}
	public void setKeepMan(String KeepMan){
		this.setInternal("KEEP_MAN" ,KeepMan );
	}


	public String getKeepMan(){
		return (String)this.getInternal("KEEP_MAN");
	}
	public void setModCount(String ModCount){
		this.setInternal("MOD_COUNT" ,ModCount );
	}


	public String getModCount(){
		return (String)this.getInternal("MOD_COUNT");
	}
	public void setLogId(String LogId){
		this.setInternal("LOG_ID" ,LogId );
	}


	public String getLogId(){
		return (String)this.getInternal("LOG_ID");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
}
