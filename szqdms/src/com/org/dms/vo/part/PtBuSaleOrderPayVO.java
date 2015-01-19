package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuSaleOrderPayVO extends BaseVO{
    public PtBuSaleOrderPayVO(){
    	//�����ֶ���Ϣ
    	this.addField("PAY_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("ORDER_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("ACCOUNT_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("PAY_AMOUNT",BaseVO.OP_STRING);
    	this.addField("ACCOUNT_TYPE",BaseVO.OP_STRING);
		this.bindFieldToSequence("PAY_ID","COMMON");
		this.bindFieldToDic("ACCOUNT_TYPE", "ZJZHLX");
    	this.setVOTableName("PT_BU_SALE_ORDER_PAY");
}
	public void setPayId(String PayId){
		this.setInternal("PAY_ID" ,PayId );
	}


	public String getPayId(){
		return (String)this.getInternal("PAY_ID");
	}
	public void setOrderId(String OrderId){
		this.setInternal("ORDER_ID" ,OrderId );
	}


	public String getOrderId(){
		return (String)this.getInternal("ORDER_ID");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
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
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setPayAmount(String PayAmount){
		this.setInternal("PAY_AMOUNT" ,PayAmount );
	}


	public String getPayAmount(){
		return (String)this.getInternal("PAY_AMOUNT");
	}
	public void setAccountType(String AccountType){
		this.setInternal("ACCOUNT_TYPE" ,AccountType );
	}


	public String getAccountType(){
		return (String)this.getInternal("ACCOUNT_TYPE");
	}
}
