package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBuDealerPrintLogVO extends BaseVO{
    public PtBuDealerPrintLogVO(){
    	//�����ֶ���Ϣ
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("PRINT_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PRINT_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("PRINT_DATE", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ORDER_ID",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("PRINT_TYPE",BaseVO.OP_STRING);
    	this.addField("PRINT_MAN",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("PRINT_ID","COMMON");
    	this.setVOTableName("PT_BU_DEALER_PRINT_LOG");
}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
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
	public void setPrintId(String PrintId){
		this.setInternal("PRINT_ID" ,PrintId );
	}


	public String getPrintId(){
		return (String)this.getInternal("PRINT_ID");
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
	public void setPrintType(String PrintType){
		this.setInternal("PRINT_TYPE" ,PrintType );
	}


	public String getPrintType(){
		return (String)this.getInternal("PRINT_TYPE");
	}
	public void setPrintMan(String PrintMan){
		this.setInternal("PRINT_MAN" ,PrintMan );
	}


	public String getPrintMan(){
		return (String)this.getInternal("PRINT_MAN");
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
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
}
