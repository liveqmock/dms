package com.org.dms.vo.part;

import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuSaleOrderDtlExtendVO extends BaseVO{
    public PtBuSaleOrderDtlExtendVO(){
    	//�����ֶ���Ϣ
    	
    	this.addField("REMARKS",BaseVO.OP_STRING);
    	this.addField("DTL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("ORDER_COUNT",BaseVO.OP_STRING);
    	this.addField("ORDER_AMOUNT",BaseVO.OP_STRING);
    	this.addField("AMOUNT",BaseVO.OP_STRING);
    	this.addField("UNIT_PRICE",BaseVO.OP_STRING);
		this.bindFieldToSequence("DTL_ID","COMMON");
    	this.setVOTableName("PT_BU_SALE_ORDER_DTL");
}
	public void setUnitPrice(String unitPrice){
		this.setInternal("UNIT_PRICE" ,unitPrice );
	}
	
	public String getUnitPrice(){
		return (String)this.getInternal("UNIT_PRICE");
	}

	public void setAmount(String amount){
		this.setInternal("AMOUNT" ,amount );
	}
	
	public String getAmount(){
		return (String)this.getInternal("AMOUNT");
	}

	public void setOrderAmount(String orderAmount){
		this.setInternal("ORDER_AMOUNT" ,orderAmount );
	}
	
	public String getOrderAmount(){
		return (String)this.getInternal("ORDER_AMOUNT");
	}


	public void setRemarks(String Remarks){
		this.setInternal("REMARKS" ,Remarks );
	}


	public String getRemarks(){
		return (String)this.getInternal("REMARKS");
	}

	public String getDtlId(){
		return (String)this.getInternal("DTL_ID");
	}

	public void setDtlId(String dtlId){
		this.setInternal("DTL_ID" ,dtlId );
	}

	public void setOrderCount(String orderCount){
		this.setInternal("ORDER_COUNT" ,orderCount );
	}

	public String getOrderCount(){
		return (String)this.getInternal("ORDER_COUNT");
	}
}
