package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuAccountLogVO extends BaseVO{
    public PtBuAccountLogVO(){
    	//ÉèÖÃ×Ö¶ÎÐÅÏ¢
    	this.addField("EXT_DOC_NO",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("ACCOUNT_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("INVOICE_NO",BaseVO.OP_STRING);
    	this.addField("IN_DATE",BaseVO.OP_DATE);
    	this.addField("REMIT_ID",BaseVO.OP_STRING);
    	this.addField("AMOUNT",BaseVO.OP_STRING);
    	this.addField("LOG_TYPE",BaseVO.OP_STRING);
    	this.addField("SOURCEACCOUNT_TYPE",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("LOG_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("TAX_INVOICE_NO",BaseVO.OP_STRING);
		this.bindFieldToSequence("LOG_ID","COMMON");
    	this.setVOTableName("PT_BU_ACCOUNT_LOG");
}
    public void setInDate(Date inDate){
		this.setInternal("IN_DATE" ,inDate);
	}


	public Date getInDate(){
		return (Date)this.getInternal("IN_DATE");
	}
	public void setRemitId(String remitId){
		this.setInternal("REMIT_ID" ,remitId );
	}


	public String getRemitId(){
		return (String)this.getInternal("REMIT_ID");
	}
	public void setExtDocNo(String ExtDocNo){
		this.setInternal("EXT_DOC_NO" ,ExtDocNo );
	}


	public String getExtDocNo(){
		return (String)this.getInternal("EXT_DOC_NO");
	}
	
	public void setSourceaccountType(String sourceaccountType){
		this.setInternal("SOURCEACCOUNT_TYPE" ,sourceaccountType );
	}


	public String getSourceaccountType(){
		return (String)this.getInternal("SOURCEACCOUNT_TYPE");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setAccountId(String AccountId){
		this.setInternal("ACCOUNT_ID" ,AccountId );
	}


	public String getAccountId(){
		return (String)this.getInternal("ACCOUNT_ID");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setInvoiceNo(String InvoiceNo){
		this.setInternal("INVOICE_NO" ,InvoiceNo );
	}


	public String getInvoiceNo(){
		return (String)this.getInternal("INVOICE_NO");
	}
	public void setAmount(String Amount){
		this.setInternal("AMOUNT" ,Amount );
	}


	public String getAmount(){
		return (String)this.getInternal("AMOUNT");
	}
	public void setLogType(String LogType){
		this.setInternal("LOG_TYPE" ,LogType );
	}


	public String getLogType(){
		return (String)this.getInternal("LOG_TYPE");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setLogId(String LogId){
		this.setInternal("LOG_ID" ,LogId );
	}


	public String getLogId(){
		return (String)this.getInternal("LOG_ID");
	}
	public void setTaxInvoiceNo(String TaxInvoiceNo){
		this.setInternal("TAX_INVOICE_NO" ,TaxInvoiceNo );
	}


	public String getTaxInvoiceNo(){
		return (String)this.getInternal("TAX_INVOICE_NO");
	}
}
