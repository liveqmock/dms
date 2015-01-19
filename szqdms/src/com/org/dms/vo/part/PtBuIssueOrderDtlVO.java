package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuIssueOrderDtlVO extends BaseVO{
    public PtBuIssueOrderDtlVO(){
    	//设置字段信息
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("POSITION_CODE",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("PART_CODE",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_ID",BaseVO.OP_STRING);
    	this.addField("ISSUE_NO",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("PART_NAME",BaseVO.OP_STRING);
    	this.addField("POSITION_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_NAME",BaseVO.OP_STRING);
    	this.addField("REMARK",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("REAL_COUNT",BaseVO.OP_STRING);
    	this.addField("SHOULD_COUNT",BaseVO.OP_STRING);
    	this.addField("UNIT",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("ISSUE_ID",BaseVO.OP_STRING);
    	this.addField("POSITION_NAME",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("PART_ID",BaseVO.OP_STRING);
    	this.addField("DTL_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("ORDER_ID",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("SUPPLIER_CODE",BaseVO.OP_STRING);
		this.bindFieldToSequence("DTL_ID","COMMON");
    	this.setVOTableName("PT_BU_ISSUE_ORDER_DTL");
}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setPositionCode(String PositionCode){
		this.setInternal("POSITION_CODE" ,PositionCode );
	}


	public String getPositionCode(){
		return (String)this.getInternal("POSITION_CODE");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setPartCode(String PartCode){
		this.setInternal("PART_CODE" ,PartCode );
	}


	public String getPartCode(){
		return (String)this.getInternal("PART_CODE");
	}
	public void setSupplierId(String SupplierId){
		this.setInternal("SUPPLIER_ID" ,SupplierId );
	}


	public String getSupplierId(){
		return (String)this.getInternal("SUPPLIER_ID");
	}
	public void setIssueNo(String IssueNo){
		this.setInternal("ISSUE_NO" ,IssueNo );
	}


	public String getIssueNo(){
		return (String)this.getInternal("ISSUE_NO");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setPartName(String PartName){
		this.setInternal("PART_NAME" ,PartName );
	}


	public String getPartName(){
		return (String)this.getInternal("PART_NAME");
	}
	public void setPositionId(String PositionId){
		this.setInternal("POSITION_ID" ,PositionId );
	}


	public String getPositionId(){
		return (String)this.getInternal("POSITION_ID");
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
	public void setSupplierName(String SupplierName){
		this.setInternal("SUPPLIER_NAME" ,SupplierName );
	}


	public String getSupplierName(){
		return (String)this.getInternal("SUPPLIER_NAME");
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
	public void setRealCount(String RealCount){
		this.setInternal("REAL_COUNT" ,RealCount );
	}


	public String getRealCount(){
		return (String)this.getInternal("REAL_COUNT");
	}
	public void setShouldCount(String ShouldCount){
		this.setInternal("SHOULD_COUNT" ,ShouldCount );
	}


	public String getShouldCount(){
		return (String)this.getInternal("SHOULD_COUNT");
	}
	public void setUnit(String Unit){
		this.setInternal("UNIT" ,Unit );
	}


	public String getUnit(){
		return (String)this.getInternal("UNIT");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setIssueId(String IssueId){
		this.setInternal("ISSUE_ID" ,IssueId );
	}


	public String getIssueId(){
		return (String)this.getInternal("ISSUE_ID");
	}
	public void setPositionName(String PositionName){
		this.setInternal("POSITION_NAME" ,PositionName );
	}


	public String getPositionName(){
		return (String)this.getInternal("POSITION_NAME");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setPartId(String PartId){
		this.setInternal("PART_ID" ,PartId );
	}


	public String getPartId(){
		return (String)this.getInternal("PART_ID");
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
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setSupplierCode(String SupplierCode){
		this.setInternal("SUPPLIER_CODE" ,SupplierCode );
	}


	public String getSupplierCode(){
		return (String)this.getInternal("SUPPLIER_CODE");
	}
}
