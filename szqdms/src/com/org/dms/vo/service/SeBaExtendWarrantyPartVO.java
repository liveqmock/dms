package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBaExtendWarrantyPartVO extends BaseVO{
    public SeBaExtendWarrantyPartVO(){
    	//设置字段信息
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("WARRANTY_MONTH",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	this.addField("WARRANTY_CODE",BaseVO.OP_STRING);
    	this.addField("WARRANTY_ID",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("RELATION_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("WARRANTY_NAME",BaseVO.OP_STRING);
		this.bindFieldToSequence("RELATION_ID","COMMON");
    	this.setVOTableName("SE_BA_EXTEND_WARRANTY_PART");
}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setWarrantyMonth(String WarrantyMonth){
		this.setInternal("WARRANTY_MONTH" ,WarrantyMonth );
	}


	public String getWarrantyMonth(){
		return (String)this.getInternal("WARRANTY_MONTH");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setWarrantyCode(String WarrantyCode){
		this.setInternal("WARRANTY_CODE" ,WarrantyCode );
	}


	public String getWarrantyCode(){
		return (String)this.getInternal("WARRANTY_CODE");
	}
	public void setWarrantyId(String WarrantyId){
		this.setInternal("WARRANTY_ID" ,WarrantyId );
	}


	public String getWarrantyId(){
		return (String)this.getInternal("WARRANTY_ID");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setRelationId(String RelationId){
		this.setInternal("RELATION_ID" ,RelationId );
	}


	public String getRelationId(){
		return (String)this.getInternal("RELATION_ID");
	}
	public void setWarrantyName(String WarrantyName){
		this.setInternal("WARRANTY_NAME" ,WarrantyName );
	}


	public String getWarrantyName(){
		return (String)this.getInternal("WARRANTY_NAME");
	}
}
