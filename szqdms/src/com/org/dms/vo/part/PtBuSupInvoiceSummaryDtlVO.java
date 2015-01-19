package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuSupInvoiceSummaryDtlVO extends BaseVO{
    public PtBuSupInvoiceSummaryDtlVO(){
    	//�����ֶ���Ϣ
    	this.addField("DETAIL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("ORDER_ID",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("SUM_ID",BaseVO.OP_STRING);
    	this.addField("TYPE",BaseVO.OP_STRING);
		this.bindFieldToSequence("DETAIL_ID","COMMON");
    	this.setVOTableName("PT_BU_SUP_INVOICE_SUMMARY_DTL");
}
	public void setDetailId(String DetailId){
		this.setInternal("DETAIL_ID" ,DetailId );
	}


	public String getDetailId(){
		return (String)this.getInternal("DETAIL_ID");
	}
	public void setOrderId(String OrderId){
		this.setInternal("ORDER_ID" ,OrderId );
	}


	public String getOrderId(){
		return (String)this.getInternal("ORDER_ID");
	}
	public void setSupplierId(String SupplierId){
		this.setInternal("SUPPLIER_ID" ,SupplierId );
	}


	public String getSupplierId(){
		return (String)this.getInternal("SUPPLIER_ID");
	}
	public void setSumId(String SumId){
		this.setInternal("SUM_ID" ,SumId );
	}


	public String getSumId(){
		return (String)this.getInternal("SUM_ID");
	}
	public void setType(String Type){
		this.setInternal("TYPE" ,Type );
	}


	public String getType(){
		return (String)this.getInternal("TYPE");
	}
}
