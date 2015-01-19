package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaPriceLogVO extends BaseVO{
    public PtBaPriceLogVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("ORIGINAL_PRICE",BaseVO.OP_STRING);
    	this.addField("NOW_PRICE",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("WAREHOUSE_CODE",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("USABLE_STOCK",BaseVO.OP_STRING);
    	this.addField("MODIFY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("MODIFY_DATE", "yyyy-MM-dd");
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("LOG_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("PRICE_TYPE",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("IN_STRANS_STOCK",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("MODIFY_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("LOG_ID","COMMON");
    	this.setVOTableName("PT_BA_PRICE_LOG");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setOriginalPrice(String OriginalPrice){
		this.setInternal("ORIGINAL_PRICE" ,OriginalPrice );
	}


	public String getOriginalPrice(){
		return (String)this.getInternal("ORIGINAL_PRICE");
	}
	public void setNowPrice(String NowPrice){
		this.setInternal("NOW_PRICE" ,NowPrice );
	}


	public String getNowPrice(){
		return (String)this.getInternal("NOW_PRICE");
	}
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}


	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setWarehouseCode(String WarehouseCode){
		this.setInternal("WAREHOUSE_CODE" ,WarehouseCode );
	}


	public String getWarehouseCode(){
		return (String)this.getInternal("WAREHOUSE_CODE");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
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
	public void setSupplierId(String SupplierId){
		this.setInternal("SUPPLIER_ID" ,SupplierId );
	}


	public String getSupplierId(){
		return (String)this.getInternal("SUPPLIER_ID");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setUsableStock(String UsableStock){
		this.setInternal("USABLE_STOCK" ,UsableStock );
	}


	public String getUsableStock(){
		return (String)this.getInternal("USABLE_STOCK");
	}
	public void setModifyDate(Date ModifyDate){
		this.setInternal("MODIFY_DATE" ,ModifyDate );
	}


	public Date getModifyDate(){
		return (Date)this.getInternal("MODIFY_DATE");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setLogId(String LogId){
		this.setInternal("LOG_ID" ,LogId );
	}


	public String getLogId(){
		return (String)this.getInternal("LOG_ID");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setPriceType(String PriceType){
		this.setInternal("PRICE_TYPE" ,PriceType );
	}


	public String getPriceType(){
		return (String)this.getInternal("PRICE_TYPE");
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
	public void setInStransStock(String InStransStock){
		this.setInternal("IN_STRANS_STOCK" ,InStransStock );
	}


	public String getInStransStock(){
		return (String)this.getInternal("IN_STRANS_STOCK");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setModifyUser(String ModifyUser){
		this.setInternal("MODIFY_USER" ,ModifyUser );
	}


	public String getModifyUser(){
		return (String)this.getInternal("MODIFY_USER");
	}
}
