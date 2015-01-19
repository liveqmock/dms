package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuSupPartPhotoVO extends BaseVO{
    public PtBuSupPartPhotoVO(){
    	//设置字段信息
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("APPLY_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("APPLY_TIME", "yyyy-MM-dd");
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("REATION_ID",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("PHOTO_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("APPLY_STATUS",BaseVO.OP_STRING);
		this.bindFieldToSequence("PHOTO_ID","COMMON");
    	this.setVOTableName("PT_BU_SUP_PART_PHOTO");
}
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}


	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setApplyTime(Date ApplyTime){
		this.setInternal("APPLY_TIME" ,ApplyTime );
	}


	public Date getApplyTime(){
		return (Date)this.getInternal("APPLY_TIME");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setSupplierId(String SupplierId){
		this.setInternal("SUPPLIER_ID" ,SupplierId );
	}


	public String getSupplierId(){
		return (String)this.getInternal("SUPPLIER_ID");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setReationId(String ReationId){
		this.setInternal("REATION_ID" ,ReationId );
	}


	public String getReationId(){
		return (String)this.getInternal("REATION_ID");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setPhotoId(String PhotoId){
		this.setInternal("PHOTO_ID" ,PhotoId );
	}


	public String getPhotoId(){
		return (String)this.getInternal("PHOTO_ID");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setApplyStatus(String ApplyStatus){
		this.setInternal("APPLY_STATUS" ,ApplyStatus );
	}


	public String getApplyStatus(){
		return (String)this.getInternal("APPLY_STATUS");
	}
}

