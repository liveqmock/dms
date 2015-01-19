package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaInvoiceDayVO extends BaseVO{
    public PtBaInvoiceDayVO(){
    	//�����ֶ���Ϣ
    	this.addField("DAY_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("START_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
    	this.addField("INVOICE_MONTH",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("END_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	this.addField("INVOICE_STATUS",BaseVO.OP_STRING);
    	this.addField("INVOICE_TYPE",BaseVO.OP_STRING);
    	this.addField("INVOICE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("INVOICE_DATE", "yyyy-MM-dd");
		this.bindFieldToSequence("DAY_ID","COMMON");
    	this.setVOTableName("PT_BA_INVOICE_DAY");
}
	public void setDayId(String DayId){
		this.setInternal("DAY_ID" ,DayId );
	}


	public String getDayId(){
		return (String)this.getInternal("DAY_ID");
	}
	public void setStartDate(Date StartDate){
		this.setInternal("START_DATE" ,StartDate );
	}


	public Date getStartDate(){
		return (Date)this.getInternal("START_DATE");
	}
	public void setInvoiceMonth(String InvoiceMonth){
		this.setInternal("INVOICE_MONTH" ,InvoiceMonth );
	}


	public String getInvoiceMonth(){
		return (String)this.getInternal("INVOICE_MONTH");
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
	public void setEndDate(Date EndDate){
		this.setInternal("END_DATE" ,EndDate );
	}


	public Date getEndDate(){
		return (Date)this.getInternal("END_DATE");
	}
	public void setInvoiceStatus(String InvoiceStatus){
		this.setInternal("INVOICE_STATUS" ,InvoiceStatus );
	}


	public String getInvoiceStatus(){
		return (String)this.getInternal("INVOICE_STATUS");
	}
	public void setInvoiceType(String InvoiceType){
		this.setInternal("INVOICE_TYPE" ,InvoiceType );
	}


	public String getInvoiceType(){
		return (String)this.getInternal("INVOICE_TYPE");
	}
	public void setInvoiceDate(Date InvoiceDate){
		this.setInternal("INVOICE_DATE" ,InvoiceDate );
	}


	public Date getInvoiceDate(){
		return (Date)this.getInternal("INVOICE_DATE");
	}
}
