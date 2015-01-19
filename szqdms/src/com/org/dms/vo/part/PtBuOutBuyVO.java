package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuOutBuyVO extends BaseVO{
    public PtBuOutBuyVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("BUY_NO",BaseVO.OP_STRING);
    	this.addField("LINK_ADDR",BaseVO.OP_STRING);
    	this.addField("CUSTOMER_NAME",BaseVO.OP_STRING);
    	this.addField("BUY_AMOUNT",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("REMARK",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("LINK_PHONE",BaseVO.OP_STRING);
    	this.addField("BUY_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("BUY_DATE", "yyyy-MM-dd");
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("BUY_COUNT",BaseVO.OP_STRING);
    	this.addField("BUY_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("CLAIM_ID",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("BUY_STATUS",BaseVO.OP_STRING);
		this.bindFieldToSequence("BUY_ID","COMMON");
    	this.setVOTableName("PT_BU_OUT_BUY");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setBuyNo(String BuyNo){
		this.setInternal("BUY_NO" ,BuyNo );
	}


	public String getBuyNo(){
		return (String)this.getInternal("BUY_NO");
	}
	public void setLinkAddr(String LinkAddr){
		this.setInternal("LINK_ADDR" ,LinkAddr );
	}


	public String getLinkAddr(){
		return (String)this.getInternal("LINK_ADDR");
	}
	public void setCustomerName(String CustomerName){
		this.setInternal("CUSTOMER_NAME" ,CustomerName );
	}


	public String getCustomerName(){
		return (String)this.getInternal("CUSTOMER_NAME");
	}
	public void setBuyAmount(String BuyAmount){
		this.setInternal("BUY_AMOUNT" ,BuyAmount );
	}


	public String getBuyAmount(){
		return (String)this.getInternal("BUY_AMOUNT");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setRemark(String Remark){
		this.setInternal("REMARK" ,Remark );
	}


	public String getRemark(){
		return (String)this.getInternal("REMARK");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setLinkPhone(String LinkPhone){
		this.setInternal("LINK_PHONE" ,LinkPhone );
	}


	public String getLinkPhone(){
		return (String)this.getInternal("LINK_PHONE");
	}
	public void setBuyDate(Date BuyDate){
		this.setInternal("BUY_DATE" ,BuyDate );
	}


	public Date getBuyDate(){
		return (Date)this.getInternal("BUY_DATE");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setBuyCount(String BuyCount){
		this.setInternal("BUY_COUNT" ,BuyCount );
	}


	public String getBuyCount(){
		return (String)this.getInternal("BUY_COUNT");
	}
	public void setBuyId(String BuyId){
		this.setInternal("BUY_ID" ,BuyId );
	}


	public String getBuyId(){
		return (String)this.getInternal("BUY_ID");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setClaimId(String ClaimId){
		this.setInternal("CLAIM_ID" ,ClaimId );
	}


	public String getClaimId(){
		return (String)this.getInternal("CLAIM_ID");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setBuyStatus(String BuyStatus){
		this.setInternal("BUY_STATUS" ,BuyStatus );
	}


	public String getBuyStatus(){
		return (String)this.getInternal("BUY_STATUS");
	}
}
