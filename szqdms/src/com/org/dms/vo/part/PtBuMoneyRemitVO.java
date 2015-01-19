package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuMoneyRemitVO extends BaseVO{
    public PtBuMoneyRemitVO(){
    	//设置字段信息
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("DRAFT_NO",BaseVO.OP_STRING);
    	this.addField("BILL_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("AMOUNT_TYPE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("END_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
    	this.addField("REMIT_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("BILL_BANK",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("VOUCHER_NO",BaseVO.OP_STRING);
    	this.addField("FILIING_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("FILIING_DATE", "yyyy-MM-dd");
    	this.addField("DRAFT_CREATE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("DRAFT_CREATE_DATE", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("IN_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("IN_DATE", "yyyy-MM-dd");
    	this.addField("ACCEPT_BANK",BaseVO.OP_STRING);
    	this.addField("REMIT_STATUS",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("DRAFT_AMOUNT",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("REMARK",BaseVO.OP_STRING);
    	this.addField("BILL_AMOUNT",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("REMIT_ID","COMMON");
    	this.setVOTableName("PT_BU_MONEY_REMIT");
}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setDraftNo(String DraftNo){
		this.setInternal("DRAFT_NO" ,DraftNo );
	}


	public String getDraftNo(){
		return (String)this.getInternal("DRAFT_NO");
	}
	public void setBillAccount(String BillAccount){
		this.setInternal("BILL_ACCOUNT" ,BillAccount );
	}


	public String getBillAccount(){
		return (String)this.getInternal("BILL_ACCOUNT");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setAmountType(String AmountType){
		this.setInternal("AMOUNT_TYPE" ,AmountType );
	}


	public String getAmountType(){
		return (String)this.getInternal("AMOUNT_TYPE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setEndDate(Date EndDate){
		this.setInternal("END_DATE" ,EndDate );
	}


	public Date getEndDate(){
		return (Date)this.getInternal("END_DATE");
	}
	public void setRemitId(String RemitId){
		this.setInternal("REMIT_ID" ,RemitId );
	}


	public String getRemitId(){
		return (String)this.getInternal("REMIT_ID");
	}
	public void setBillBank(String BillBank){
		this.setInternal("BILL_BANK" ,BillBank );
	}


	public String getBillBank(){
		return (String)this.getInternal("BILL_BANK");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setVoucherNo(String VoucherNo){
		this.setInternal("VOUCHER_NO" ,VoucherNo );
	}


	public String getVoucherNo(){
		return (String)this.getInternal("VOUCHER_NO");
	}
	public void setFiliingDate(Date FiliingDate){
		this.setInternal("FILIING_DATE" ,FiliingDate );
	}


	public Date getFiliingDate(){
		return (Date)this.getInternal("FILIING_DATE");
	}
	public void setDraftCreateDate(Date DraftCreateDate){
		this.setInternal("DRAFT_CREATE_DATE" ,DraftCreateDate );
	}


	public Date getDraftCreateDate(){
		return (Date)this.getInternal("DRAFT_CREATE_DATE");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setInDate(Date InDate){
		this.setInternal("IN_DATE" ,InDate );
	}


	public Date getInDate(){
		return (Date)this.getInternal("IN_DATE");
	}
	public void setAcceptBank(String AcceptBank){
		this.setInternal("ACCEPT_BANK" ,AcceptBank );
	}


	public String getAcceptBank(){
		return (String)this.getInternal("ACCEPT_BANK");
	}
	public void setRemitStatus(String RemitStatus){
		this.setInternal("REMIT_STATUS" ,RemitStatus );
	}


	public String getRemitStatus(){
		return (String)this.getInternal("REMIT_STATUS");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setDraftAmount(String DraftAmount){
		this.setInternal("DRAFT_AMOUNT" ,DraftAmount );
	}


	public String getDraftAmount(){
		return (String)this.getInternal("DRAFT_AMOUNT");
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
	public void setBillAmount(String BillAmount){
		this.setInternal("BILL_AMOUNT" ,BillAmount );
	}


	public String getBillAmount(){
		return (String)this.getInternal("BILL_AMOUNT");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
}
