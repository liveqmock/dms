package com.org.frameImpl.vo;
import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseVO;

import java.util.Date;
public class TmOrgVO extends BaseVO{
	private static final long serialVersionUID = 7717188243585921383L;

	public TmOrgVO(){
    	//设置字段信息
    	this.addField("ONAME",BaseVO.OP_STRING);
    	this.addField("DIVISION_CODE",BaseVO.OP_STRING);
    	this.addField("PID",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("IS_IC",BaseVO.OP_STRING);
    	this.addField("ERP_ID",BaseVO.OP_STRING);
    	this.addField("ORG_TYPE",BaseVO.OP_STRING);
    	this.addField("COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("SNAME",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("BUS_STATUS",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("OEM_COMPANY_ID",BaseVO.OP_STRING);
    	this.addField("IS_AM",BaseVO.OP_STRING);
    	this.addField("LEVEL_CODE",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("BUS_TYPE",BaseVO.OP_STRING);
    	this.addField("IS_DS",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("NAME_EN",BaseVO.OP_STRING);
    	this.addField("CODE",BaseVO.OP_STRING);
		this.bindFieldToSequence("ORG_ID","ORG_S");
		//设置字典
        this.bindFieldToDic("STATUS", DicConstant.YXBS);
        this.bindFieldToDic("LEVEL_CODE", DicConstant.JGJB);
        this.bindFieldToSimpleOrgCompany("COMPANY_ID");
        this.bindFieldToDic("ORG_TYPE", "ZZLB");
        this.bindFieldToDic("BUS_TYPE", "YWLX");
        this.bindFieldToDic("BUS_STATUS", "ZZYWZT");
        this.bindFieldToDic("IS_DC", DicConstant.SF);
        this.bindFieldToDic("IS_IC", DicConstant.SF);
        this.bindFieldToSimpleOrgDept("PID");
        this.bindFieldToSimpleOrgDept("CODE");
        //设置行政区划
        this.bindFieldToOrgXzqh("DIVISION_CODE");
    	this.setVOTableName("TM_ORG");
}
	public void setOname(String Oname){
		this.setInternal("ONAME" ,Oname );
	}


	public String getOname(){
		return (String)this.getInternal("ONAME");
	}
	public void setDivisionCode(String DivisionCode){
		this.setInternal("DIVISION_CODE" ,DivisionCode );
	}


	public String getDivisionCode(){
		return (String)this.getInternal("DIVISION_CODE");
	}
	public void setPid(String Pid){
		this.setInternal("PID" ,Pid );
	}


	public String getPid(){
		return (String)this.getInternal("PID");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setIsIc(String IsIc){
		this.setInternal("IS_IC" ,IsIc );
	}


	public String getIsIc(){
		return (String)this.getInternal("IS_IC");
	}
	public void setErpId(String ErpId){
		this.setInternal("ERP_ID" ,ErpId );
	}


	public String getErpId(){
		return (String)this.getInternal("ERP_ID");
	}
	public void setOrgType(String OrgType){
		this.setInternal("ORG_TYPE" ,OrgType );
	}


	public String getOrgType(){
		return (String)this.getInternal("ORG_TYPE");
	}
	public void setCompanyId(String CompanyId){
		this.setInternal("COMPANY_ID" ,CompanyId );
	}


	public String getCompanyId(){
		return (String)this.getInternal("COMPANY_ID");
	}
	public void setSname(String Sname){
		this.setInternal("SNAME" ,Sname );
	}


	public String getSname(){
		return (String)this.getInternal("SNAME");
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
	public void setBusStatus(String BusStatus){
		this.setInternal("BUS_STATUS" ,BusStatus );
	}


	public String getBusStatus(){
		return (String)this.getInternal("BUS_STATUS");
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
	public void setIsAm(String IsAm){
		this.setInternal("IS_AM" ,IsAm );
	}


	public String getIsAm(){
		return (String)this.getInternal("IS_AM");
	}
	public void setLevelCode(String LevelCode){
		this.setInternal("LEVEL_CODE" ,LevelCode );
	}


	public String getLevelCode(){
		return (String)this.getInternal("LEVEL_CODE");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setBusType(String BusType){
		this.setInternal("BUS_TYPE" ,BusType );
	}


	public String getBusType(){
		return (String)this.getInternal("BUS_TYPE");
	}
	public void setIsDs(String IsDs){
		this.setInternal("IS_DS" ,IsDs );
	}


	public String getIsDs(){
		return (String)this.getInternal("IS_DS");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setNameEn(String NameEn){
		this.setInternal("NAME_EN" ,NameEn );
	}


	public String getNameEn(){
		return (String)this.getInternal("NAME_EN");
	}
	public void setCode(String Code){
		this.setInternal("CODE" ,Code );
	}


	public String getCode(){
		return (String)this.getInternal("CODE");
	}
}
