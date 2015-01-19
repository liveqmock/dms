package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuPchOrderShippingVO extends BaseVO{
    public PtBuPchOrderShippingVO(){
    	//设置字段信息
    	this.addField("SHIPPING_AMOUNT",BaseVO.OP_STRING);
    	this.addField("SHIPPING_MONEY",BaseVO.OP_STRING);
    	this.addField("DETAIL_ID",BaseVO.OP_STRING);
    	this.addField("SHIP_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("SHIP_DATE", "yyyy-MM-dd");
    	this.addField("PURCHASE_ID",BaseVO.OP_STRING);
    	this.addField("SHIPPING_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
		this.bindFieldToSequence("SHIPPING_ID","COMMON");
    	this.setVOTableName("PT_BU_PCH_ORDER_SHIPPING");
}
	public void setShippingAmount(String ShippingAmount){
		this.setInternal("SHIPPING_AMOUNT" ,ShippingAmount );
	}


	public String getShippingAmount(){
		return (String)this.getInternal("SHIPPING_AMOUNT");
	}
	public void setShippingMoney(String ShippingMoney){
		this.setInternal("SHIPPING_MONEY" ,ShippingMoney );
	}


	public String getShippingMoney(){
		return (String)this.getInternal("SHIPPING_MONEY");
	}
	public void setDetailId(String DetailId){
		this.setInternal("DETAIL_ID" ,DetailId );
	}


	public String getDetailId(){
		return (String)this.getInternal("DETAIL_ID");
	}
	public void setShipDate(Date ShipDate){
		this.setInternal("SHIP_DATE" ,ShipDate );
	}


	public Date getShipDate(){
		return (Date)this.getInternal("SHIP_DATE");
	}
	public void setPurchaseId(String PurchaseId){
		this.setInternal("PURCHASE_ID" ,PurchaseId );
	}


	public String getPurchaseId(){
		return (String)this.getInternal("PURCHASE_ID");
	}
	public void setShippingId(String ShippingId){
		this.setInternal("SHIPPING_ID" ,ShippingId );
	}


	public String getShippingId(){
		return (String)this.getInternal("SHIPPING_ID");
	}
}
