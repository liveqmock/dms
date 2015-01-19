package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBuPreAuthorPartVO extends BaseVO{
    public SeBuPreAuthorPartVO(){
    	//�����ֶ���Ϣ
    	this.addField("REL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("AUTHOR_ID",BaseVO.OP_STRING);
    	this.addField("UNIT_PRICE",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("QUANTITY",BaseVO.OP_STRING);
		this.bindFieldToSequence("REL_ID","COMMON");
    	this.setVOTableName("SE_BU_PRE_AUTHOR_PART");
}
	public void setRelId(String RelId){
		this.setInternal("REL_ID" ,RelId );
	}


	public String getRelId(){
		return (String)this.getInternal("REL_ID");
	}
	public void setAuthorId(String AuthorId){
		this.setInternal("AUTHOR_ID" ,AuthorId );
	}


	public String getAuthorId(){
		return (String)this.getInternal("AUTHOR_ID");
	}
	public void setUnitPrice(String UnitPrice){
		this.setInternal("UNIT_PRICE" ,UnitPrice );
	}


	public String getUnitPrice(){
		return (String)this.getInternal("UNIT_PRICE");
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
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
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
	public void setQuantity(String Quantity){
		this.setInternal("QUANTITY" ,Quantity );
	}


	public String getQuantity(){
		return (String)this.getInternal("QUANTITY");
	}
}
