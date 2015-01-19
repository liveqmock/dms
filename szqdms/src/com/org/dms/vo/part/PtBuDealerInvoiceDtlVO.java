package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuDealerInvoiceDtlVO extends BaseVO{
    public PtBuDealerInvoiceDtlVO(){
    	//�����ֶ���Ϣ
    	this.addField("DTL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("ORDER_ID",BaseVO.OP_STRING);
    	this.addField("SUM_ID",BaseVO.OP_STRING);
    	this.addField("TYPE",BaseVO.OP_STRING);
		this.bindFieldToSequence("DTL_ID","COMMON");
    	this.setVOTableName("PT_BU_DEALER_INVOICE_DTL");
}
	public void setDtlId(String DtlId){
		this.setInternal("DTL_ID" ,DtlId );
	}


	public String getDtlId(){
		return (String)this.getInternal("DTL_ID");
	}
	public void setOrderId(String OrderId){
		this.setInternal("ORDER_ID" ,OrderId );
	}


	public String getOrderId(){
		return (String)this.getInternal("ORDER_ID");
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
