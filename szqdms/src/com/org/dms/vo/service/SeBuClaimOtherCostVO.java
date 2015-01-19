package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
@SuppressWarnings("serial")
public class SeBuClaimOtherCostVO extends BaseVO{
    public SeBuClaimOtherCostVO(){
    	this.addField("COSTS_NAME",BaseVO.OP_STRING);
    	this.addField("REL_ID",BaseVO.OP_STRING);
    	this.addField("CLAIM_ID",BaseVO.OP_STRING);
    	this.addField("COSTS_CODE",BaseVO.OP_STRING);
    	this.addField("COSTS_AMOUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("COST_ID","COMMON");
    	this.setVOTableName("SE_BU_CLAIM_OTHER_COST");
}
	public void setCostsName(String CostsName){
		this.setInternal("COSTS_NAME" ,CostsName );
	}


	public String getCostsName(){
		return (String)this.getInternal("COSTS_NAME");
	}
	public void setRelId(String RelId){
		this.setInternal("REL_ID" ,RelId );
	}


	public String getRelId(){
		return (String)this.getInternal("REL_ID");
	}
	public void setClaimId(String ClaimId){
		this.setInternal("CLAIM_ID" ,ClaimId );
	}


	public String getClaimId(){
		return (String)this.getInternal("CLAIM_ID");
	}
	public void setCostsCode(String CostsCode){
		this.setInternal("COSTS_CODE" ,CostsCode );
	}


	public String getCostsCode(){
		return (String)this.getInternal("COSTS_CODE");
	}
	public void setCostsAmount(String CostsAmount){
		this.setInternal("COSTS_AMOUNT" ,CostsAmount );
	}


	public String getCostsAmount(){
		return (String)this.getInternal("COSTS_AMOUNT");
	}
}
