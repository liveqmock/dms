package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuRealSaleVO extends BaseVO{
    public PtBuRealSaleVO(){
    	//设置字段信息
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("CLAIM_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("SALE_NO",BaseVO.OP_STRING);
    	this.addField("PRINT_STATUS",BaseVO.OP_STRING);
    	this.addField("SALE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("SALE_DATE", "yyyy-MM-dd");
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("SALE_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("LINK_PHONE",BaseVO.OP_STRING);
    	this.addField("OUT_TYPE",BaseVO.OP_STRING);
    	this.addField("PRINT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("PRINT_DATE", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("CUSTOMER_NAME",BaseVO.OP_STRING);
    	this.addField("SALE_STATUS",BaseVO.OP_STRING);
    	this.addField("LINK_ADDR",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("SALE_AMOUNT",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("REMARK",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("SALE_COUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("SALE_ID","COMMON");
    	this.setVOTableName("PT_BU_REAL_SALE");
}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setClaimId(String ClaimId){
		this.setInternal("CLAIM_ID" ,ClaimId );
	}


	public String getClaimId(){
		return (String)this.getInternal("CLAIM_ID");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setSaleNo(String SaleNo){
		this.setInternal("SALE_NO" ,SaleNo );
	}


	public String getSaleNo(){
		return (String)this.getInternal("SALE_NO");
	}
	public void setPrintStatus(String PrintStatus){
		this.setInternal("PRINT_STATUS" ,PrintStatus );
	}


	public String getPrintStatus(){
		return (String)this.getInternal("PRINT_STATUS");
	}
	public void setSaleDate(Date SaleDate){
		this.setInternal("SALE_DATE" ,SaleDate );
	}


	public Date getSaleDate(){
		return (Date)this.getInternal("SALE_DATE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setSaleId(String SaleId){
		this.setInternal("SALE_ID" ,SaleId );
	}


	public String getSaleId(){
		return (String)this.getInternal("SALE_ID");
	}
	public void setLinkPhone(String LinkPhone){
		this.setInternal("LINK_PHONE" ,LinkPhone );
	}


	public String getLinkPhone(){
		return (String)this.getInternal("LINK_PHONE");
	}
	public void setOutType(String OutType){
		this.setInternal("OUT_TYPE" ,OutType );
	}


	public String getOutType(){
		return (String)this.getInternal("OUT_TYPE");
	}
	public void setPrintDate(Date PrintDate){
		this.setInternal("PRINT_DATE" ,PrintDate );
	}


	public Date getPrintDate(){
		return (Date)this.getInternal("PRINT_DATE");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setCustomerName(String CustomerName){
		this.setInternal("CUSTOMER_NAME" ,CustomerName );
	}


	public String getCustomerName(){
		return (String)this.getInternal("CUSTOMER_NAME");
	}
	public void setSaleStatus(String SaleStatus){
		this.setInternal("SALE_STATUS" ,SaleStatus );
	}


	public String getSaleStatus(){
		return (String)this.getInternal("SALE_STATUS");
	}
	public void setLinkAddr(String LinkAddr){
		this.setInternal("LINK_ADDR" ,LinkAddr );
	}


	public String getLinkAddr(){
		return (String)this.getInternal("LINK_ADDR");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setSaleAmount(String SaleAmount){
		this.setInternal("SALE_AMOUNT" ,SaleAmount );
	}


	public String getSaleAmount(){
		return (String)this.getInternal("SALE_AMOUNT");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setRemark(String Remark){
		this.setInternal("REMARK" ,Remark );
	}


	public String getRemark(){
		return (String)this.getInternal("REMARK");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setSaleCount(String SaleCount){
		this.setInternal("SALE_COUNT" ,SaleCount );
	}


	public String getSaleCount(){
		return (String)this.getInternal("SALE_COUNT");
	}
}
