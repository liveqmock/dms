package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuOrderShipDtlVO extends BaseVO{
    public PtBuOrderShipDtlVO(){
    	//�����ֶ���Ϣ
    	this.addField("DTL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("ORDER_ID",BaseVO.OP_STRING);
    	this.addField("SHIP_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("DTL_ID","COMMON");
    	this.setVOTableName("PT_BU_ORDER_SHIP_DTL");
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
	public void setShipId(String ShipId){
		this.setInternal("SHIP_ID" ,ShipId );
	}


	public String getShipId(){
		return (String)this.getInternal("SHIP_ID");
	}
}
