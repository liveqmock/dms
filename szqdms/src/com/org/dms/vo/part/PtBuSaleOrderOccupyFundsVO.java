package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuSaleOrderOccupyFundsVO extends BaseVO{
    public PtBuSaleOrderOccupyFundsVO(){
    	//设置字段信息
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ACCOUNT_TYPE",BaseVO.OP_STRING);
    	this.addField("ORDER_ID",BaseVO.OP_STRING);
    	this.addField("ACCOUNT_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("REPAY_AMOUNT",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("OFUNDS_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("OCCUPY_FUNDS",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
		this.bindFieldToSequence("OFUNDS_ID","COMMON");
    	this.setVOTableName("PT_BU_SALE_ORDER_OCCUPY_FUNDS");
}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setAccountType(String AccountType){
		this.setInternal("ACCOUNT_TYPE" ,AccountType );
	}


	public String getAccountType(){
		return (String)this.getInternal("ACCOUNT_TYPE");
	}
	public void setOrderId(String OrderId){
		this.setInternal("ORDER_ID" ,OrderId );
	}


	public String getOrderId(){
		return (String)this.getInternal("ORDER_ID");
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
	public void setRepayAmount(String RepayAmount){
		this.setInternal("REPAY_AMOUNT" ,RepayAmount );
	}


	public String getRepayAmount(){
		return (String)this.getInternal("REPAY_AMOUNT");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setOfundsId(String OfundsId){
		this.setInternal("OFUNDS_ID" ,OfundsId );
	}


	public String getOfundsId(){
		return (String)this.getInternal("OFUNDS_ID");
	}
	public void setOccupyFunds(String OccupyFunds){
		this.setInternal("OCCUPY_FUNDS" ,OccupyFunds );
	}


	public String getOccupyFunds(){
		return (String)this.getInternal("OCCUPY_FUNDS");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
}
