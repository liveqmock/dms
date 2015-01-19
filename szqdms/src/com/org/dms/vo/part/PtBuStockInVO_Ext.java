package com.org.dms.vo.part;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseVO;

import java.util.Date;

@SuppressWarnings("serial")
public class PtBuStockInVO_Ext extends BaseVO{
    public PtBuStockInVO_Ext(){
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("WAREHOUSE_NAME",BaseVO.OP_STRING);
    	this.addField("WAREHOUSE_ID",BaseVO.OP_STRING);
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("PRINT_STATUS",BaseVO.OP_STRING);
    	this.addField("WAREHOUSE_CODE",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("IN_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("PRINT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("PRINT_DATE", "yyyy-MM-dd");
    	this.addField("KEEPER_MAN",BaseVO.OP_STRING);
    	this.addField("IN_STATUS",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("IN_TYPE",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("ORDER_NO",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("ORDER_ID",BaseVO.OP_STRING);
    	this.addField("BUYER",BaseVO.OP_STRING);
    	this.addField("PRINT_MAN",BaseVO.OP_STRING);
    	this.addField("IN_NO",BaseVO.OP_STRING);
    	this.addField("IN_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("IN_DATE", "yyyy-MM-dd");
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("IN_ID","COMMON");
    	this.setVOTableName("PT_BU_STOCK_IN");
        this.addField("PLAN_AMOUNT",BaseVO.OP_STRING);
        this.addField("PURCHASE_AMOUNT",BaseVO.OP_STRING);
        this.addField("OUT_WAREHOUSE_ID",BaseVO.OP_STRING);
        this.addField("OUT_WAREHOUSE_CODE",BaseVO.OP_STRING);
        this.addField("OUT_WAREHOUSE_NAME",BaseVO.OP_STRING);
        this.addField("APPLY_ORG_NAME",BaseVO.OP_STRING);
        this.addField("CHECK_USER_SV",BaseVO.OP_STRING);
        this.addField("OUT_CREATE_USER",BaseVO.OP_STRING);
        this.addField("WAREHOUSE_TYPE",BaseVO.OP_STRING);
        this.bindFieldToDic("IN_TYPE", DicConstant.RKLX);
        this.bindFieldToUserid("BUYER");
        this.bindFieldToUserid("OUT_CREATE_USER");
        this.bindFieldToDic("PRINT_STATUS", DicConstant.DYZT);
    }
    public void setWarehouseType(String WarehouseType){
		this.setInternal("WAREHOUSE_TYPE" ,WarehouseType );
	}


	public String getWarehouseType(){
		return (String)this.getInternal("WAREHOUSE_TYPE");
	}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}


	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
	}
	public void setWarehouseName(String WarehouseName){
		this.setInternal("WAREHOUSE_NAME" ,WarehouseName );
	}


	public String getWarehouseName(){
		return (String)this.getInternal("WAREHOUSE_NAME");
	}
	public void setWarehouseId(String WarehouseId){
		this.setInternal("WAREHOUSE_ID" ,WarehouseId );
	}


	public String getWarehouseId(){
		return (String)this.getInternal("WAREHOUSE_ID");
	}
	public void setOrgName(String OrgName){
		this.setInternal("ORG_NAME" ,OrgName );
	}


	public String getOrgName(){
		return (String)this.getInternal("ORG_NAME");
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
	public void setPrintStatus(String PrintStatus){
		this.setInternal("PRINT_STATUS" ,PrintStatus );
	}


	public String getPrintStatus(){
		return (String)this.getInternal("PRINT_STATUS");
	}
	public void setWarehouseCode(String WarehouseCode){
		this.setInternal("WAREHOUSE_CODE" ,WarehouseCode );
	}


	public String getWarehouseCode(){
		return (String)this.getInternal("WAREHOUSE_CODE");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
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
	public void setInId(String InId){
		this.setInternal("IN_ID" ,InId );
	}


	public String getInId(){
		return (String)this.getInternal("IN_ID");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setPrintDate(Date PrintDate){
		this.setInternal("PRINT_DATE" ,PrintDate );
	}


	public Date getPrintDate(){
		return (Date)this.getInternal("PRINT_DATE");
	}
	public void setKeeperMan(String KeeperMan){
		this.setInternal("KEEPER_MAN" ,KeeperMan );
	}


	public String getKeeperMan(){
		return (String)this.getInternal("KEEPER_MAN");
	}
	public void setInStatus(String InStatus){
		this.setInternal("IN_STATUS" ,InStatus );
	}


	public String getInStatus(){
		return (String)this.getInternal("IN_STATUS");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setInType(String InType){
		this.setInternal("IN_TYPE" ,InType );
	}


	public String getInType(){
		return (String)this.getInternal("IN_TYPE");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setOrderNo(String OrderNo){
		this.setInternal("ORDER_NO" ,OrderNo );
	}


	public String getOrderNo(){
		return (String)this.getInternal("ORDER_NO");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setOrderId(String OrderId){
		this.setInternal("ORDER_ID" ,OrderId );
	}


	public String getOrderId(){
		return (String)this.getInternal("ORDER_ID");
	}
	public void setBuyer(String Buyer){
		this.setInternal("BUYER" ,Buyer );
	}


	public String getBuyer(){
		return (String)this.getInternal("BUYER");
	}
	public void setPrintMan(String PrintMan){
		this.setInternal("PRINT_MAN" ,PrintMan );
	}


	public String getPrintMan(){
		return (String)this.getInternal("PRINT_MAN");
	}
	public void setInNo(String InNo){
		this.setInternal("IN_NO" ,InNo );
	}


	public String getInNo(){
		return (String)this.getInternal("IN_NO");
	}
	public void setInDate(Date InDate){
		this.setInternal("IN_DATE" ,InDate );
	}


	public Date getInDate(){
		return (Date)this.getInternal("IN_DATE");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}


	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
}
