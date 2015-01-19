package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuSaleInvoiceSummaryVO extends BaseVO{
    public PtBuSaleInvoiceSummaryVO(){
    	//�����ֶ���Ϣ
    	this.addField("SUM_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("ORDER_STATUS",BaseVO.OP_STRING);
    	this.addField("EXPRESS_REMARKS",BaseVO.OP_STRING);
    	this.addField("INVOICE_NO",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("ORG_NAME",BaseVO.OP_STRING);
    	this.addField("INVOICE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("ADDRESS",BaseVO.OP_STRING);
    	this.addField("RECIVE_USER",BaseVO.OP_STRING);
    	this.addField("ORDER_NO",BaseVO.OP_STRING);
    	this.addField("INVOICE_STATUS",BaseVO.OP_STRING);
    	this.addField("INVOICE_REMARKS",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("INVOICE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("INVOICE_DATE", "yyyy-MM-dd");
    	this.addField("ORDER_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("TEL",BaseVO.OP_STRING);
    	this.addField("EXPRESS_STATUS",BaseVO.OP_STRING);
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("EXPRESS_NO",BaseVO.OP_STRING);
		this.bindFieldToSequence("SUM_ID","COMMON");
    	this.setVOTableName("PT_BU_SALE_INVOICE_SUMMARY");
}
	public void setSumId(String SumId){
		this.setInternal("SUM_ID" ,SumId );
	}


	public String getSumId(){
		return (String)this.getInternal("SUM_ID");
	}
	public void setOrderStatus(String OrderStatus){
		this.setInternal("ORDER_STATUS" ,OrderStatus );
	}


	public String getOrderStatus(){
		return (String)this.getInternal("ORDER_STATUS");
	}
	public void setExpressRemarks(String ExpressRemarks){
		this.setInternal("EXPRESS_REMARKS" ,ExpressRemarks );
	}


	public String getExpressRemarks(){
		return (String)this.getInternal("EXPRESS_REMARKS");
	}
	public void setInvoiceNo(String InvoiceNo){
		this.setInternal("INVOICE_NO" ,InvoiceNo );
	}


	public String getInvoiceNo(){
		return (String)this.getInternal("INVOICE_NO");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setOrgName(String OrgName){
		this.setInternal("ORG_NAME" ,OrgName );
	}


	public String getOrgName(){
		return (String)this.getInternal("ORG_NAME");
	}
	public void setInvoiceAmount(String InvoiceAmount){
		this.setInternal("INVOICE_AMOUNT" ,InvoiceAmount );
	}


	public String getInvoiceAmount(){
		return (String)this.getInternal("INVOICE_AMOUNT");
	}
	public void setAddress(String Address){
		this.setInternal("ADDRESS" ,Address );
	}


	public String getAddress(){
		return (String)this.getInternal("ADDRESS");
	}
	public void setReciveUser(String ReciveUser){
		this.setInternal("RECIVE_USER" ,ReciveUser );
	}


	public String getReciveUser(){
		return (String)this.getInternal("RECIVE_USER");
	}
	public void setOrderNo(String OrderNo){
		this.setInternal("ORDER_NO" ,OrderNo );
	}


	public String getOrderNo(){
		return (String)this.getInternal("ORDER_NO");
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
	public void setInvoiceDate(Date InvoiceDate){
		this.setInternal("INVOICE_DATE" ,InvoiceDate );
	}


	public Date getInvoiceDate(){
		return (Date)this.getInternal("INVOICE_DATE");
	}
	public void setOrderId(String OrderId){
		this.setInternal("ORDER_ID" ,OrderId );
	}


	public String getOrderId(){
		return (String)this.getInternal("ORDER_ID");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setTel(String Tel){
		this.setInternal("TEL" ,Tel );
	}


	public String getTel(){
		return (String)this.getInternal("TEL");
	}
	public void setExpressStatus(String ExpressStatus){
		this.setInternal("EXPRESS_STATUS" ,ExpressStatus );
	}


	public String getExpressStatus(){
		return (String)this.getInternal("EXPRESS_STATUS");
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
	public void setExpressNo(String ExpressNo){
		this.setInternal("EXPRESS_NO" ,ExpressNo );
	}


	public String getExpressNo(){
		return (String)this.getInternal("EXPRESS_NO");
	}
}
