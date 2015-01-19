package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuReturnApplyVO extends BaseVO{
    public PtBuReturnApplyVO(){
    	//ÉèÖÃ×Ö¶ÎÐÅÏ¢
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("APPLY_ORG_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("IN_STOCK_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("IN_STOCK_DATE", "yyyy-MM-dd");
    	this.addField("APPLY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd");
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("CHECK_USER",BaseVO.OP_STRING);
    	this.addField("RECEIVE_ORG_NAME",BaseVO.OP_STRING);
    	this.addField("RETURN_AMOUNT",BaseVO.OP_STRING);
    	this.addField("APPLY_ORG_CODE",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("RETURN_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("SOURCE_ORDER_NO",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("RECEIVE_ORG_CODE",BaseVO.OP_STRING);
    	this.addField("INVOICE_STATUS",BaseVO.OP_STRING);
    	this.addField("CHECK_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd");
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("APPLY_SATUS",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("RETURN_NO",BaseVO.OP_STRING);
    	this.addField("APPLY_ORG_NAME",BaseVO.OP_STRING);
    	this.addField("INVOICE_NO",BaseVO.OP_STRING);
    	this.addField("CHECK_REMARKS",BaseVO.OP_STRING);
    	this.addField("SOURCE_ORDER_ID",BaseVO.OP_STRING);
    	this.addField("CLOSE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("CLOSE_DATE", "yyyy-MM-dd");
    	this.addField("RECEIVE_ORG_ID",BaseVO.OP_STRING);
    	this.addField("RETURN_COUNT",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("OUT_STOCK_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("OUT_STOCK_DATE", "yyyy-MM-dd");
		this.bindFieldToSequence("RETURN_ID","COMMON");
    	this.setVOTableName("PT_BU_RETURN_APPLY");
}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setApplyOrgId(String ApplyOrgId){
		this.setInternal("APPLY_ORG_ID" ,ApplyOrgId );
	}


	public String getApplyOrgId(){
		return (String)this.getInternal("APPLY_ORG_ID");
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
	public void setApplyDate(Date ApplyDate){
		this.setInternal("APPLY_DATE" ,ApplyDate );
	}


	public Date getApplyDate(){
		return (Date)this.getInternal("APPLY_DATE");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setCheckUser(String CheckUser){
		this.setInternal("CHECK_USER" ,CheckUser );
	}


	public String getCheckUser(){
		return (String)this.getInternal("CHECK_USER");
	}
	public void setReceiveOrgName(String ReceiveOrgName){
		this.setInternal("RECEIVE_ORG_NAME" ,ReceiveOrgName );
	}


	public String getReceiveOrgName(){
		return (String)this.getInternal("RECEIVE_ORG_NAME");
	}
	public void setReturnAmount(String ReturnAmount){
		this.setInternal("RETURN_AMOUNT" ,ReturnAmount );
	}


	public String getReturnAmount(){
		return (String)this.getInternal("RETURN_AMOUNT");
	}
	public void setApplyOrgCode(String ApplyOrgCode){
		this.setInternal("APPLY_ORG_CODE" ,ApplyOrgCode );
	}


	public String getApplyOrgCode(){
		return (String)this.getInternal("APPLY_ORG_CODE");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setReturnId(String ReturnId){
		this.setInternal("RETURN_ID" ,ReturnId );
	}


	public String getReturnId(){
		return (String)this.getInternal("RETURN_ID");
	}
	public void setSourceOrderNo(String SourceOrderNo){
		this.setInternal("SOURCE_ORDER_NO" ,SourceOrderNo );
	}


	public String getSourceOrderNo(){
		return (String)this.getInternal("SOURCE_ORDER_NO");
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
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setReceiveOrgCode(String ReceiveOrgCode){
		this.setInternal("RECEIVE_ORG_CODE" ,ReceiveOrgCode );
	}


	public String getReceiveOrgCode(){
		return (String)this.getInternal("RECEIVE_ORG_CODE");
	}
	public void setInvoiceStatus(String InvoiceStatus){
		this.setInternal("INVOICE_STATUS" ,InvoiceStatus );
	}


	public String getInvoiceStatus(){
		return (String)this.getInternal("INVOICE_STATUS");
	}
	public void setCheckDate(Date CheckDate){
		this.setInternal("CHECK_DATE" ,CheckDate );
	}


	public Date getCheckDate(){
		return (Date)this.getInternal("CHECK_DATE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setApplySatus(String ApplySatus){
		this.setInternal("APPLY_SATUS" ,ApplySatus );
	}


	public String getApplySatus(){
		return (String)this.getInternal("APPLY_SATUS");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setReturnNo(String ReturnNo){
		this.setInternal("RETURN_NO" ,ReturnNo );
	}


	public String getReturnNo(){
		return (String)this.getInternal("RETURN_NO");
	}
	public void setApplyOrgName(String ApplyOrgName){
		this.setInternal("APPLY_ORG_NAME" ,ApplyOrgName );
	}


	public String getApplyOrgName(){
		return (String)this.getInternal("APPLY_ORG_NAME");
	}
	public void setInvoiceNo(String InvoiceNo){
		this.setInternal("INVOICE_NO" ,InvoiceNo );
	}


	public String getInvoiceNo(){
		return (String)this.getInternal("INVOICE_NO");
	}
	public void setCheckRemarks(String CheckRemarks){
		this.setInternal("CHECK_REMARKS" ,CheckRemarks );
	}


	public String getCheckRemarks(){
		return (String)this.getInternal("CHECK_REMARKS");
	}
	public void setSourceOrderId(String SourceOrderId){
		this.setInternal("SOURCE_ORDER_ID" ,SourceOrderId );
	}


	public String getSourceOrderId(){
		return (String)this.getInternal("SOURCE_ORDER_ID");
	}
	public void setCloseDate(Date CloseDate){
		this.setInternal("CLOSE_DATE" ,CloseDate );
	}


	public Date getCloseDate(){
		return (Date)this.getInternal("CLOSE_DATE");
	}
	public void setReceiveOrgId(String ReceiveOrgId){
		this.setInternal("RECEIVE_ORG_ID" ,ReceiveOrgId );
	}


	public String getReceiveOrgId(){
		return (String)this.getInternal("RECEIVE_ORG_ID");
	}
	public void setReturnCount(String ReturnCount){
		this.setInternal("RETURN_COUNT" ,ReturnCount );
	}


	public String getReturnCount(){
		return (String)this.getInternal("RETURN_COUNT");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setOutStockDate(Date OutStockDate){
		this.setInternal("OUT_STOCK_DATE" ,OutStockDate );
	}


	public Date getOutStockDate(){
		return (Date)this.getInternal("OUT_STOCK_DATE");
	}
}
