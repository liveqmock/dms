package com.org.dms.vo.part;

import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuAccountVO_Ext extends BaseVO{
    public PtBuAccountVO_Ext(){
    	//�����ֶ���Ϣ
    	
    	this.addField("CASH_AVAILABLE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("ACCEPT_AVAILABLE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("MATERIAL_AVAILABLE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("CREDIT_AVAILABLE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("REBATE_AVAILABLE_AMOUNT",BaseVO.OP_STRING);
    	
    	this.addField("CASH_ACCOUNT_ID",BaseVO.OP_STRING);
    	this.addField("ACCEPT_ACCOUNT_ID",BaseVO.OP_STRING);
    	this.addField("MATERIAL_ACCOUNT_ID",BaseVO.OP_STRING);
    	this.addField("CREDIT_ACCOUNT_ID",BaseVO.OP_STRING);
    	this.addField("REBATE_ACCOUNT_ID",BaseVO.OP_STRING);
}
    
    
    
    
    
	public void setRebateAccountId(String rebateAccountId){
		this.setInternal("REBATE_ACCOUNT_ID" ,rebateAccountId );
	}
	
	public String getRebateAccountId(){
		return (String)this.getInternal("REBATE_ACCOUNT_ID");
	}
    
	public void setCreditAccountId(String creditAccountId){
		this.setInternal("CREDIT_ACCOUNT_ID" ,creditAccountId );
	}
	
	public String getCreditAccountId(){
		return (String)this.getInternal("CREDIT_ACCOUNT_ID");
	}
	
	public void setMaterialAccountId(String materialAccountId){
		this.setInternal("MATERIAL_ACCOUNT_ID" ,materialAccountId );
	}
	
	public String getMaterialAccountId(){
		return (String)this.getInternal("MATERIAL_ACCOUNT_ID");
	}

	public void setAcceptAccountId(String acceptAccountId){
		this.setInternal("ACCEPT_ACCOUNT_ID" ,acceptAccountId );
	}
	
	public String getAcceptAccountId(){
		return (String)this.getInternal("ACCEPT_ACCOUNT_ID");
	}
	
	public void setCashAccountId(String cashAccountId){
		this.setInternal("CASH_ACCOUNT_ID" ,cashAccountId );
	}
	
	public String getCashAccountId(){
		return (String)this.getInternal("CASH_ACCOUNT_ID");
	}
	
	public void setCashAvailbleAmount(String cashAvailbleAmount){
		this.setInternal("CASH_AVAILABLE_AMOUNT" ,cashAvailbleAmount );
	}
	
	public String getCashAvailbleAmount(){
		return (String)this.getInternal("CASH_AVAILABLE_AMOUNT");
	}

	public void setAcceptAvailableAmount(String acceptAvailableAmount){
		this.setInternal("ACCEPT_AVAILABLE_AMOUNT" ,acceptAvailableAmount );
	}
	
	public String getAcceptAvailableAmount(){
		return (String)this.getInternal("ACCEPT_AVAILABLE_AMOUNT");
	}

	public void setMaterialAvailableAmount(String materialAvailableAmount){
		this.setInternal("MATERIAL_AVAILABLE_AMOUNT" ,materialAvailableAmount );
	}
	
	public String getMaterialAvailableAmount(){
		return (String)this.getInternal("MATERIAL_AVAILABLE_AMOUNT");
	}


	public void setCreditAvailableAmount(String creditAvailableAmount){
		this.setInternal("CREDIT_AVAILABLE_AMOUNT" ,creditAvailableAmount );
	}


	public String getCreditAvailableAmount(){
		return (String)this.getInternal("CREDIT_AVAILABLE_AMOUNT");
	}

	public String getRebateAvailableAmount(){
		return (String)this.getInternal("REBATE_AVAILABLE_AMOUNT");
	}

	public void setRebateAvailableAmount(String rebateAvailableAmount){
		this.setInternal("REBATE_AVAILABLE_AMOUNT" ,rebateAvailableAmount );
	}

}
