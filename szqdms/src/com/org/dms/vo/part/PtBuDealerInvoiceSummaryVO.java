package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuDealerInvoiceSummaryVO extends BaseVO{
    public PtBuDealerInvoiceSummaryVO(){
    	//ÉèÖÃ×Ö¶ÎÐÅÏ¢
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("SIGN_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("SIGN_DATE", "yyyy-MM-dd");
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("RECIVE_USER",BaseVO.OP_STRING);
    	this.addField("END_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	this.addField("RETURN_AMOUNT",BaseVO.OP_STRING);
    	this.addField("EXPRESS_COMPANY",BaseVO.OP_STRING);
    	this.addField("PLAN_AMOUNT",BaseVO.OP_STRING);
    	this.addField("INVOICE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("INVOICE_DATE", "yyyy-MM-dd");
    	this.addField("INVOICE_MONTH",BaseVO.OP_STRING);
    	this.addField("SALE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("SUM_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("EXPRESS_REMARKS",BaseVO.OP_STRING);
    	this.addField("INVOICE_STATUS",BaseVO.OP_STRING);
    	this.addField("INVOICE_REMARKS",BaseVO.OP_STRING);
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("EXPRESS_STATUS",BaseVO.OP_STRING);
    	this.addField("SENDER",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("START_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("INVOICE_NO",BaseVO.OP_STRING);
    	this.addField("SIGN_USER",BaseVO.OP_STRING);
    	this.addField("RETURN_COUNT",BaseVO.OP_STRING);
    	this.addField("EXPRESS_NO",BaseVO.OP_STRING);
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("ADDRESS",BaseVO.OP_STRING);
    	this.addField("INVOICE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("TEL",BaseVO.OP_STRING);
    	this.addField("SALE_COUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("SUM_ID","COMMON");
    	this.setVOTableName("PT_BU_DEALER_INVOICE_SUMMARY");
}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setSignDate(Date SignDate){
		this.setInternal("SIGN_DATE" ,SignDate );
	}


	public Date getSignDate(){
		return (Date)this.getInternal("SIGN_DATE");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setReciveUser(String ReciveUser){
		this.setInternal("RECIVE_USER" ,ReciveUser );
	}


	public String getReciveUser(){
		return (String)this.getInternal("RECIVE_USER");
	}
	public void setEndDate(Date EndDate){
		this.setInternal("END_DATE" ,EndDate );
	}


	public Date getEndDate(){
		return (Date)this.getInternal("END_DATE");
	}
	public void setReturnAmount(String ReturnAmount){
		this.setInternal("RETURN_AMOUNT" ,ReturnAmount );
	}


	public String getReturnAmount(){
		return (String)this.getInternal("RETURN_AMOUNT");
	}
	public void setExpressCompany(String ExpressCompany){
		this.setInternal("EXPRESS_COMPANY" ,ExpressCompany );
	}


	public String getExpressCompany(){
		return (String)this.getInternal("EXPRESS_COMPANY");
	}
	public void setPlanAmount(String PlanAmount){
		this.setInternal("PLAN_AMOUNT" ,PlanAmount );
	}


	public String getPlanAmount(){
		return (String)this.getInternal("PLAN_AMOUNT");
	}
	public void setInvoiceDate(Date InvoiceDate){
		this.setInternal("INVOICE_DATE" ,InvoiceDate );
	}


	public Date getInvoiceDate(){
		return (Date)this.getInternal("INVOICE_DATE");
	}
	public void setInvoiceMonth(String InvoiceMonth){
		this.setInternal("INVOICE_MONTH" ,InvoiceMonth );
	}


	public String getInvoiceMonth(){
		return (String)this.getInternal("INVOICE_MONTH");
	}
	public void setSaleAmount(String SaleAmount){
		this.setInternal("SALE_AMOUNT" ,SaleAmount );
	}


	public String getSaleAmount(){
		return (String)this.getInternal("SALE_AMOUNT");
	}
	public void setSumId(String SumId){
		this.setInternal("SUM_ID" ,SumId );
	}


	public String getSumId(){
		return (String)this.getInternal("SUM_ID");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setExpressRemarks(String ExpressRemarks){
		this.setInternal("EXPRESS_REMARKS" ,ExpressRemarks );
	}


	public String getExpressRemarks(){
		return (String)this.getInternal("EXPRESS_REMARKS");
	}
	public void setInvoiceStatus(String InvoiceStatus){
		this.setInternal("INVOICE_STATUS" ,InvoiceStatus );
	}


	public String getInvoiceStatus(){
		return (String)this.getInternal("INVOICE_STATUS");
	}
	public void setInvoiceRemarks(String InvoiceRemarks){
		this.setInternal("INVOICE_REMARKS" ,InvoiceRemarks );
	}


	public String getInvoiceRemarks(){
		return (String)this.getInternal("INVOICE_REMARKS");
	}
	public void setOrgName(String OrgName){
		this.setInternal("ORG_NAME" ,OrgName );
	}


	public String getOrgName(){
		return (String)this.getInternal("ORG_NAME");
	}
	public void setExpressStatus(String ExpressStatus){
		this.setInternal("EXPRESS_STATUS" ,ExpressStatus );
	}


	public String getExpressStatus(){
		return (String)this.getInternal("EXPRESS_STATUS");
	}
	public void setSender(String Sender){
		this.setInternal("SENDER" ,Sender );
	}


	public String getSender(){
		return (String)this.getInternal("SENDER");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setStartDate(Date StartDate){
		this.setInternal("START_DATE" ,StartDate );
	}


	public Date getStartDate(){
		return (Date)this.getInternal("START_DATE");
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
	public void setSignUser(String SignUser){
		this.setInternal("SIGN_USER" ,SignUser );
	}


	public String getSignUser(){
		return (String)this.getInternal("SIGN_USER");
	}
	public void setReturnCount(String ReturnCount){
		this.setInternal("RETURN_COUNT" ,ReturnCount );
	}


	public String getReturnCount(){
		return (String)this.getInternal("RETURN_COUNT");
	}
	public void setExpressNo(String ExpressNo){
		this.setInternal("EXPRESS_NO" ,ExpressNo );
	}


	public String getExpressNo(){
		return (String)this.getInternal("EXPRESS_NO");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}


	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
	}
	public void setAddress(String Address){
		this.setInternal("ADDRESS" ,Address );
	}


	public String getAddress(){
		return (String)this.getInternal("ADDRESS");
	}
	public void setInvoiceAmount(String InvoiceAmount){
		this.setInternal("INVOICE_AMOUNT" ,InvoiceAmount );
	}


	public String getInvoiceAmount(){
		return (String)this.getInternal("INVOICE_AMOUNT");
	}
	public void setTel(String Tel){
		this.setInternal("TEL" ,Tel );
	}


	public String getTel(){
		return (String)this.getInternal("TEL");
	}
	public void setSaleCount(String SaleCount){
		this.setInternal("SALE_COUNT" ,SaleCount );
	}


	public String getSaleCount(){
		return (String)this.getInternal("SALE_COUNT");
	}
}
