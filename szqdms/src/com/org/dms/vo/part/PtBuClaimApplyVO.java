package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuClaimApplyVO extends BaseVO{
    public PtBuClaimApplyVO(){
    	//ÉèÖÃ×Ö¶ÎÐÅÏ¢
    	this.addField("OUT_COUNT",BaseVO.OP_STRING);
    	this.addField("SOURCE_OUT_ID",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("OUT_ORDER_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("IN_STOCK_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("IN_STOCK_DATE", "yyyy-MM-dd");
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("APPLY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	this.addField("PHONE",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("SOURCE_IN_NO",BaseVO.OP_STRING);
    	this.addField("SETTLE_ACCOUNT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("SETTLE_ACCOUNT_DATE", "yyyy-MM-dd");
    	this.addField("LINK_MAN",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("IN_ORDER_ID",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("AMOUNT",BaseVO.OP_STRING);
    	this.addField("APPLY_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("IN_COUNT",BaseVO.OP_STRING);
    	this.addField("OUT_ORDER_NO",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("APPLY_USER",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("UNIT",BaseVO.OP_STRING);
    	this.addField("CLAIM_COUNT",BaseVO.OP_STRING);
    	this.addField("ADUIT_REMARKS",BaseVO.OP_STRING);
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("SETTLE_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("RETURN_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("RETURN_DATE", "yyyy-MM-dd");
    	this.addField("SOURCE_OUT_NO",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("APPLY_NO",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("SOURCE_IN_ID",BaseVO.OP_STRING);
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("SALE_PRICE",BaseVO.OP_STRING);
    	this.addField("FAULT_CONDITONS",BaseVO.OP_STRING);
    	this.addField("APPLY_STATUS",BaseVO.OP_STRING);
    	this.addField("CUSTOMER_NAME",BaseVO.OP_STRING);
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("BOX_NO",BaseVO.OP_STRING);
    	this.addField("IN_ORDER_NO",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
    	this.addField("OUT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("OUT_DATE", "yyyy-MM-dd");
		this.bindFieldToSequence("APPLY_ID","COMMON");
    	this.setVOTableName("PT_BU_CLAIM_APPLY");
}
	public void setOutCount(String OutCount){
		this.setInternal("OUT_COUNT" ,OutCount );
	}


	public String getOutCount(){
		return (String)this.getInternal("OUT_COUNT");
	}
	public void setSourceOutId(String SourceOutId){
		this.setInternal("SOURCE_OUT_ID" ,SourceOutId );
	}


	public String getSourceOutId(){
		return (String)this.getInternal("SOURCE_OUT_ID");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setOutOrderId(String OutOrderId){
		this.setInternal("OUT_ORDER_ID" ,OutOrderId );
	}


	public String getOutOrderId(){
		return (String)this.getInternal("OUT_ORDER_ID");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setInStockDate(Date InStockDate){
		this.setInternal("IN_STOCK_DATE" ,InStockDate );
	}


	public Date getInStockDate(){
		return (Date)this.getInternal("IN_STOCK_DATE");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setApplyDate(Date ApplyDate){
		this.setInternal("APPLY_DATE" ,ApplyDate );
	}


	public Date getApplyDate(){
		return (Date)this.getInternal("APPLY_DATE");
	}
	public void setPhone(String Phone){
		this.setInternal("PHONE" ,Phone );
	}


	public String getPhone(){
		return (String)this.getInternal("PHONE");
	}
	public void setSupplierId(String SupplierId){
		this.setInternal("SUPPLIER_ID" ,SupplierId );
	}


	public String getSupplierId(){
		return (String)this.getInternal("SUPPLIER_ID");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setSourceInNo(String SourceInNo){
		this.setInternal("SOURCE_IN_NO" ,SourceInNo );
	}


	public String getSourceInNo(){
		return (String)this.getInternal("SOURCE_IN_NO");
	}
	public void setSettleAccountDate(Date SettleAccountDate){
		this.setInternal("SETTLE_ACCOUNT_DATE" ,SettleAccountDate );
	}


	public Date getSettleAccountDate(){
		return (Date)this.getInternal("SETTLE_ACCOUNT_DATE");
	}
	public void setLinkMan(String LinkMan){
		this.setInternal("LINK_MAN" ,LinkMan );
	}


	public String getLinkMan(){
		return (String)this.getInternal("LINK_MAN");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setInOrderId(String InOrderId){
		this.setInternal("IN_ORDER_ID" ,InOrderId );
	}


	public String getInOrderId(){
		return (String)this.getInternal("IN_ORDER_ID");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setAmount(String Amount){
		this.setInternal("AMOUNT" ,Amount );
	}


	public String getAmount(){
		return (String)this.getInternal("AMOUNT");
	}
	public void setApplyId(String ApplyId){
		this.setInternal("APPLY_ID" ,ApplyId );
	}


	public String getApplyId(){
		return (String)this.getInternal("APPLY_ID");
	}
	public void setInCount(String InCount){
		this.setInternal("IN_COUNT" ,InCount );
	}


	public String getInCount(){
		return (String)this.getInternal("IN_COUNT");
	}
	public void setOutOrderNo(String OutOrderNo){
		this.setInternal("OUT_ORDER_NO" ,OutOrderNo );
	}


	public String getOutOrderNo(){
		return (String)this.getInternal("OUT_ORDER_NO");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
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
	public void setApplyUser(String ApplyUser){
		this.setInternal("APPLY_USER" ,ApplyUser );
	}


	public String getApplyUser(){
		return (String)this.getInternal("APPLY_USER");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setUnit(String Unit){
		this.setInternal("UNIT" ,Unit );
	}


	public String getUnit(){
		return (String)this.getInternal("UNIT");
	}
	public void setClaimCount(String ClaimCount){
		this.setInternal("CLAIM_COUNT" ,ClaimCount );
	}


	public String getClaimCount(){
		return (String)this.getInternal("CLAIM_COUNT");
	}
	public void setAduitRemarks(String AduitRemarks){
		this.setInternal("ADUIT_REMARKS" ,AduitRemarks );
	}


	public String getAduitRemarks(){
		return (String)this.getInternal("ADUIT_REMARKS");
	}
	public void setOrgName(String OrgName){
		this.setInternal("ORG_NAME" ,OrgName );
	}


	public String getOrgName(){
		return (String)this.getInternal("ORG_NAME");
	}
	public void setSettleAccount(String SettleAccount){
		this.setInternal("SETTLE_ACCOUNT" ,SettleAccount );
	}


	public String getSettleAccount(){
		return (String)this.getInternal("SETTLE_ACCOUNT");
	}
	public void setReturnDate(Date ReturnDate){
		this.setInternal("RETURN_DATE" ,ReturnDate );
	}


	public Date getReturnDate(){
		return (Date)this.getInternal("RETURN_DATE");
	}
	public void setSourceOutNo(String SourceOutNo){
		this.setInternal("SOURCE_OUT_NO" ,SourceOutNo );
	}


	public String getSourceOutNo(){
		return (String)this.getInternal("SOURCE_OUT_NO");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setApplyNo(String ApplyNo){
		this.setInternal("APPLY_NO" ,ApplyNo );
	}


	public String getApplyNo(){
		return (String)this.getInternal("APPLY_NO");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setSourceInId(String SourceInId){
		this.setInternal("SOURCE_IN_ID" ,SourceInId );
	}


	public String getSourceInId(){
		return (String)this.getInternal("SOURCE_IN_ID");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
	}
	public void setSalePrice(String SalePrice){
		this.setInternal("SALE_PRICE" ,SalePrice );
	}


	public String getSalePrice(){
		return (String)this.getInternal("SALE_PRICE");
	}
	public void setFaultConditons(String FaultConditons){
		this.setInternal("FAULT_CONDITONS" ,FaultConditons );
	}


	public String getFaultConditons(){
		return (String)this.getInternal("FAULT_CONDITONS");
	}
	public void setApplyStatus(String ApplyStatus){
		this.setInternal("APPLY_STATUS" ,ApplyStatus );
	}


	public String getApplyStatus(){
		return (String)this.getInternal("APPLY_STATUS");
	}
	public void setCustomerName(String CustomerName){
		this.setInternal("CUSTOMER_NAME" ,CustomerName );
	}


	public String getCustomerName(){
		return (String)this.getInternal("CUSTOMER_NAME");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}


	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setBoxNo(String BoxNo){
		this.setInternal("BOX_NO" ,BoxNo );
	}


	public String getBoxNo(){
		return (String)this.getInternal("BOX_NO");
	}
	public void setInOrderNo(String InOrderNo){
		this.setInternal("IN_ORDER_NO" ,InOrderNo );
	}


	public String getInOrderNo(){
		return (String)this.getInternal("IN_ORDER_NO");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
	public void setOutDate(Date OutDate){
		this.setInternal("OUT_DATE" ,OutDate );
	}


	public Date getOutDate(){
		return (Date)this.getInternal("OUT_DATE");
	}
}
