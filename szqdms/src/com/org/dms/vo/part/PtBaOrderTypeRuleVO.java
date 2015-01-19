package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class PtBaOrderTypeRuleVO extends BaseVO{
    public PtBaOrderTypeRuleVO(){
    	//�����ֶ���Ϣ
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("DC_STARTDATE",BaseVO.OP_STRING);
    	this.addField("SE_STARTDATE",BaseVO.OP_STRING);
    	this.addField("IF_STORAGE",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("IF_APPLYTIMES",BaseVO.OP_STRING);
    	this.addField("IF_CHANEL",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("SE_ENDDATE",BaseVO.OP_STRING);
    	this.addField("IF_SUMMARY",BaseVO.OP_STRING);
    	this.addField("IF_OWNPICK",BaseVO.OP_STRING);
    	this.addField("DC_ENDDATE",BaseVO.OP_STRING);
    	this.addField("IF_APPLYDATE",BaseVO.OP_STRING);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("IF_FREE",BaseVO.OP_STRING);
    	this.addField("FREE_TIMES",BaseVO.OP_STRING);
    	this.addField("TYPERULE_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("TYPE_CODE",BaseVO.OP_STRING);
    	this.addField("IF_FUNDS",BaseVO.OP_STRING);
    	this.addField("ORDER_TYPE",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("APPLY_TIMES",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("IF_CHOOSEADDR",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("FIRST_LETTER",BaseVO.OP_STRING);
		this.bindFieldToSequence("TYPERULE_ID","COMMON");
    	this.setVOTableName("PT_BA_ORDER_TYPE_RULE");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setDcStartdate(String DcStartdate){
		this.setInternal("DC_STARTDATE" ,DcStartdate );
	}


	public String getDcStartdate(){
		return (String)this.getInternal("DC_STARTDATE");
	}
	public void setSeStartdate(String SeStartdate){
		this.setInternal("SE_STARTDATE" ,SeStartdate );
	}


	public String getSeStartdate(){
		return (String)this.getInternal("SE_STARTDATE");
	}
	public void setIfStorage(String IfStorage){
		this.setInternal("IF_STORAGE" ,IfStorage );
	}


	public String getIfStorage(){
		return (String)this.getInternal("IF_STORAGE");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setIfApplytimes(String IfApplytimes){
		this.setInternal("IF_APPLYTIMES" ,IfApplytimes );
	}


	public String getIfApplytimes(){
		return (String)this.getInternal("IF_APPLYTIMES");
	}
	public void setIfChanel(String IfChanel){
		this.setInternal("IF_CHANEL" ,IfChanel );
	}


	public String getIfChanel(){
		return (String)this.getInternal("IF_CHANEL");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setSeEnddate(String SeEnddate){
		this.setInternal("SE_ENDDATE" ,SeEnddate );
	}


	public String getSeEnddate(){
		return (String)this.getInternal("SE_ENDDATE");
	}
	public void setIfSummary(String IfSummary){
		this.setInternal("IF_SUMMARY" ,IfSummary );
	}


	public String getIfSummary(){
		return (String)this.getInternal("IF_SUMMARY");
	}
	public void setIfOwnpick(String IfOwnpick){
		this.setInternal("IF_OWNPICK" ,IfOwnpick );
	}


	public String getIfOwnpick(){
		return (String)this.getInternal("IF_OWNPICK");
	}
	public void setDcEnddate(String DcEnddate){
		this.setInternal("DC_ENDDATE" ,DcEnddate );
	}


	public String getDcEnddate(){
		return (String)this.getInternal("DC_ENDDATE");
	}
	public void setIfApplydate(String IfApplydate){
		this.setInternal("IF_APPLYDATE" ,IfApplydate );
	}


	public String getIfApplydate(){
		return (String)this.getInternal("IF_APPLYDATE");
	}
	public void setOemCompanyId(String OemCompanyId){
		this.setInternal("OEM_COMPANY_ID" ,OemCompanyId );
	}


	public String getOemCompanyId(){
		return (String)this.getInternal("OEM_COMPANY_ID");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setIfFree(String IfFree){
		this.setInternal("IF_FREE" ,IfFree );
	}


	public String getIfFree(){
		return (String)this.getInternal("IF_FREE");
	}
	public void setFreeTimes(String FreeTimes){
		this.setInternal("FREE_TIMES" ,FreeTimes );
	}


	public String getFreeTimes(){
		return (String)this.getInternal("FREE_TIMES");
	}
	public void setTyperuleId(String TyperuleId){
		this.setInternal("TYPERULE_ID" ,TyperuleId );
	}


	public String getTyperuleId(){
		return (String)this.getInternal("TYPERULE_ID");
	}
	public void setTypeCode(String TypeCode){
		this.setInternal("TYPE_CODE" ,TypeCode );
	}


	public String getTypeCode(){
		return (String)this.getInternal("TYPE_CODE");
	}
	public void setIfFunds(String IfFunds){
		this.setInternal("IF_FUNDS" ,IfFunds );
	}


	public String getIfFunds(){
		return (String)this.getInternal("IF_FUNDS");
	}
	public void setOrderType(String OrderType){
		this.setInternal("ORDER_TYPE" ,OrderType );
	}


	public String getOrderType(){
		return (String)this.getInternal("ORDER_TYPE");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setApplyTimes(String ApplyTimes){
		this.setInternal("APPLY_TIMES" ,ApplyTimes );
	}


	public String getApplyTimes(){
		return (String)this.getInternal("APPLY_TIMES");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setIfChooseaddr(String IfChooseaddr){
		this.setInternal("IF_CHOOSEADDR" ,IfChooseaddr );
	}


	public String getIfChooseaddr(){
		return (String)this.getInternal("IF_CHOOSEADDR");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setFirstLetter(String FirstLetter){
		this.setInternal("FIRST_LETTER" ,FirstLetter );
	}


	public String getFirstLetter(){
		return (String)this.getInternal("FIRST_LETTER");
	}
}
