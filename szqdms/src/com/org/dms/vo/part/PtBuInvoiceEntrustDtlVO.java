package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuInvoiceEntrustDtlVO extends BaseVO{
    public PtBuInvoiceEntrustDtlVO(){
    	//�����ֶ���Ϣ
    	this.addField("DTL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("COLUMN_10",BaseVO.OP_STRING);
    	this.addField("PRO_NAME",BaseVO.OP_STRING);
    	this.addField("IN_INVOICE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("ENTRUST_ID",BaseVO.OP_STRING);
    	this.addField("PRO_UNIT",BaseVO.OP_STRING);
    	this.addField("IN_INVOICE_PRICE",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("PRO_COUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("DTL_ID","COMMON");
    	this.setVOTableName("PT_BU_INVOICE_ENTRUST_DTL");
}
	public void setDtlId(String DtlId){
		this.setInternal("DTL_ID" ,DtlId );
	}


	public String getDtlId(){
		return (String)this.getInternal("DTL_ID");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setColumn10(String Column10){
		this.setInternal("COLUMN_10" ,Column10 );
	}


	public String getColumn10(){
		return (String)this.getInternal("COLUMN_10");
	}
	public void setProName(String ProName){
		this.setInternal("PRO_NAME" ,ProName );
	}


	public String getProName(){
		return (String)this.getInternal("PRO_NAME");
	}
	public void setInInvoiceAmount(String InInvoiceAmount){
		this.setInternal("IN_INVOICE_AMOUNT" ,InInvoiceAmount );
	}


	public String getInInvoiceAmount(){
		return (String)this.getInternal("IN_INVOICE_AMOUNT");
	}
	public void setEntrustId(String EntrustId){
		this.setInternal("ENTRUST_ID" ,EntrustId );
	}


	public String getEntrustId(){
		return (String)this.getInternal("ENTRUST_ID");
	}
	public void setProUnit(String ProUnit){
		this.setInternal("PRO_UNIT" ,ProUnit );
	}


	public String getProUnit(){
		return (String)this.getInternal("PRO_UNIT");
	}
	public void setInInvoicePrice(String InInvoicePrice){
		this.setInternal("IN_INVOICE_PRICE" ,InInvoicePrice );
	}


	public String getInInvoicePrice(){
		return (String)this.getInternal("IN_INVOICE_PRICE");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setProCount(String ProCount){
		this.setInternal("PRO_COUNT" ,ProCount );
	}


	public String getProCount(){
		return (String)this.getInternal("PRO_COUNT");
	}
}
