package com.org.dms.vo.service;

import com.org.framework.base.BaseVO;

public class SeBuClaimOutVO_Ext extends SeBuClaimOutVO{
    public SeBuClaimOutVO_Ext(){
    	this.addField("SE_TYPE",BaseVO.OP_STRING);
    	this.addField("CLAIM_NO",BaseVO.OP_STRING);
    	this.addField("CLAIM_STATUS",BaseVO.OP_STRING);
    }
    public void setSeType(String SeType){
		this.setInternal("SE_TYPE" ,SeType );
	}
	public String getSeType(){
		return (String)this.getInternal("SE_TYPE");
	}
	
	 public void setClaimNo(String claimNo){
		 this.setInternal("CLAIM_NO" ,claimNo );
	 }
	 public String getClaimNo(){
		 return (String)this.getInternal("CLAIM_NO");
	 }
		
	 public void setClaimStatus(String claimStatus){
		this.setInternal("CLAIM_STATUS" ,claimStatus );
	 }
	 public String getClaimStatus(){
		return (String)this.getInternal("CLAIM_STATUS");
	 }
    

}
