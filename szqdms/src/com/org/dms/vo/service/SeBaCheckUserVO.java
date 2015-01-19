package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBaCheckUserVO extends BaseVO{
    public SeBaCheckUserVO(){
    	//�����ֶ���Ϣ
    	this.addField("USER_NAME",BaseVO.OP_STRING);
    	this.addField("CU_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("IF_DISTRIB",BaseVO.OP_STRING);
    	this.addField("USER_TYPE",BaseVO.OP_STRING);
    	this.addField("COUNTS",BaseVO.OP_STRING);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("SEQ_NO",BaseVO.OP_STRING);
		this.bindFieldToSequence("CU_ID","COMMON");
    	this.setVOTableName("SE_BA_CHECK_USER");
}
	public void setUserName(String UserName){
		this.setInternal("USER_NAME" ,UserName );
	}


	public String getUserName(){
		return (String)this.getInternal("USER_NAME");
	}
	public void setCuId(String CuId){
		this.setInternal("CU_ID" ,CuId );
	}


	public String getCuId(){
		return (String)this.getInternal("CU_ID");
	}
	public void setIfDistrib(String IfDistrib){
		this.setInternal("IF_DISTRIB" ,IfDistrib );
	}


	public String getIfDistrib(){
		return (String)this.getInternal("IF_DISTRIB");
	}
	public void setUserType(String UserType){
		this.setInternal("USER_TYPE" ,UserType );
	}


	public String getUserType(){
		return (String)this.getInternal("USER_TYPE");
	}
	public void setCounts(String Counts){
		this.setInternal("COUNTS" ,Counts );
	}


	public String getCounts(){
		return (String)this.getInternal("COUNTS");
	}
	public void setUserAccount(String UserAccount){
		this.setInternal("USER_ACCOUNT" ,UserAccount );
	}


	public String getUserAccount(){
		return (String)this.getInternal("USER_ACCOUNT");
	}
	public void setSeqNo(String SeqNo){
		this.setInternal("SEQ_NO" ,SeqNo );
	}


	public String getSeqNo(){
		return (String)this.getInternal("SEQ_NO");
	}
}
