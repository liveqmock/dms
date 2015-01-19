package com.org.frameImpl.vo;
import com.org.frameImpl.Constant;
import com.org.framework.base.BaseVO;

import java.util.Date;
public class TmUserVO extends BaseVO{
    /** 
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = -468900839375425918L;
	public TmUserVO(){
    	//设置字段信息
    	this.addField("SECRET_LEVEL",BaseVO.OP_STRING);
    	this.addField("MAILFROM",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ACCOUNT",BaseVO.OP_STRING);
    	this.addField("PERSON_NAME_EN",BaseVO.OP_STRING);
    	this.addField("PERSON_KIND",BaseVO.OP_STRING);
    	this.addField("PERSON_TYPE",BaseVO.OP_STRING);
    	this.addField("DES",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("SEX",BaseVO.OP_STRING);
    	this.addField("PASSWORD",BaseVO.OP_STRING);
    	this.addField("USERTEMPLATE",BaseVO.OP_STRING);
    	this.addField("CERTCODE",BaseVO.OP_STRING);
    	this.addField("MAILNAME",BaseVO.OP_STRING);
    	this.addField("BIRTHDATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("BIRTHDATE", "yyyy-MM-dd");
    	this.addField("USER_ID",BaseVO.OP_STRING|BaseVO.getTpPk());
    	this.addField("PERSON_NAME",BaseVO.OP_STRING);
    	this.addField("MAILPSW",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("CONTACT_WAY",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
    	this.addField("IDCARD",BaseVO.OP_STRING);
    	this.addField("SMTP",BaseVO.OP_STRING);
    	this.addField("USER_SN",BaseVO.OP_STRING);
    	this.addField("USER_AUTH",BaseVO.OP_STRING);
    	this.addField("AUTH_MAC",BaseVO.OP_STRING);
    	this.addField("ORG_CODE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("LEVEL_NAME",BaseVO.OP_STRING);
    	this.addField("UPDATEPWD_TIME",BaseVO.OP_DATE);
		this.bindFieldToSequence("USER_ID","USER_S");
		//设置字典类型定义
		this.bindFieldToDic("SEX",Constant.XB);//用户性别   
		this.bindFieldToDic("PERSON_KIND",Constant.YHLX);//用户类型
		this.bindFieldToDic("SECRET_LEVEL",Constant.SJMJ);//数据密级
		this.bindFieldToDic("STATUS",Constant.YXBS);//有效标识   
		this.bindFieldToDic("PERSON_TYPE",Constant.JGJB);//用户类别
		this.bindFieldToDic("USER_AUTH",Constant.YHQX);//用户权限
		this.setFieldDateFormat("UPDATEPWD_TIME", "yyyy-MM-dd");
  		//设置组织机构
  		this.bindFieldToOrgDept("ORG_ID");//所属部门代码
    	this.setVOTableName("TM_USER");
}
	public void setSecretLevel(String SecretLevel){
		this.setInternal("SECRET_LEVEL" ,SecretLevel );
	}


	public String getSecretLevel(){
		return (String)this.getInternal("SECRET_LEVEL");
	}
	public void setMailfrom(String Mailfrom){
		this.setInternal("MAILFROM" ,Mailfrom );
	}


	public String getMailfrom(){
		return (String)this.getInternal("MAILFROM");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}

	public void setAuthMac(String AuthMac){
		this.setInternal("AUTH_MAC" ,AuthMac );
	}


	public String getAuthMac(){
		return (String)this.getInternal("AUTH_MAC");
	}
	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setUpdatepwdTime(Date UpdatepwdTime){
		this.setInternal("UPDATEPWD_TIME" ,UpdatepwdTime );
	}


	public Date getUpdatepwdTime(){
		return (Date)this.getInternal("UPDATEPWD_TIME");
	}
	
	public void setAccount(String Account){
		this.setInternal("ACCOUNT" ,Account );
	}


	public String getAccount(){
		return (String)this.getInternal("ACCOUNT");
	}
	public void setPersonNameEn(String PersonNameEn){
		this.setInternal("PERSON_NAME_EN" ,PersonNameEn );
	}


	public String getPersonNameEn(){
		return (String)this.getInternal("PERSON_NAME_EN");
	}
	public void setPersonKind(String PersonKind){
		this.setInternal("PERSON_KIND" ,PersonKind );
	}

	
	public String getPersonKind(){
		return (String)this.getInternal("PERSON_KIND");
	}
	
	public void setUserAuth(String UserAuth){
		this.setInternal("USER_AUTH" ,UserAuth );
	}


	public String getUserAuth(){
		return (String)this.getInternal("USER_AUTH");
	}
	
	public void setPersonType(String PersonType){
		this.setInternal("PERSON_TYPE" ,PersonType );
	}


	public String getPersonType(){
		return (String)this.getInternal("PERSON_TYPE");
	}
	public void setDes(String Des){
		this.setInternal("DES" ,Des );
	}


	public String getDes(){
		return (String)this.getInternal("DES");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setSex(String Sex){
		this.setInternal("SEX" ,Sex );
	}


	public String getSex(){
		return (String)this.getInternal("SEX");
	}
	public void setPassword(String Password){
		this.setInternal("PASSWORD" ,Password );
	}


	public String getPassword(){
		return (String)this.getInternal("PASSWORD");
	}
	public void setUsertemplate(String Usertemplate){
		this.setInternal("USERTEMPLATE" ,Usertemplate );
	}


	public String getUsertemplate(){
		return (String)this.getInternal("USERTEMPLATE");
	}
	public void setCertcode(String Certcode){
		this.setInternal("CERTCODE" ,Certcode );
	}


	public String getCertcode(){
		return (String)this.getInternal("CERTCODE");
	}
	public void setMailname(String Mailname){
		this.setInternal("MAILNAME" ,Mailname );
	}


	public String getMailname(){
		return (String)this.getInternal("MAILNAME");
	}
	public void setBirthdate(Date Birthdate){
		this.setInternal("BIRTHDATE" ,Birthdate );
	}


	public Date getBirthdate(){
		return (Date)this.getInternal("BIRTHDATE");
	}
	public void setUserId(String UserId){
		this.setInternal("USER_ID" ,UserId );
	}

	public String getUserId(){
		return (String)this.getInternal("USER_ID");
	}
	
	public String getPersonName(){
		return (String)this.getInternal("PERSON_NAME");
	}
	public void setMailpsw(String Mailpsw){
		this.setInternal("MAILPSW" ,Mailpsw );
	}


	public String getMailpsw(){
		return (String)this.getInternal("MAILPSW");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}

	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setContactWay(String ContactWay){
		this.setInternal("CONTACT_WAY" ,ContactWay );
	}

	public String getContactWay(){
		return (String)this.getInternal("CONTACT_WAY");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}

	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
	public void setIdcard(String Idcard){
		this.setInternal("IDCARD" ,Idcard );
	}

	public String getIdcard(){
		return (String)this.getInternal("IDCARD");
	}
	public void setSmtp(String Smtp){
		this.setInternal("SMTP" ,Smtp );
	}

	public String getSmtp(){
		return (String)this.getInternal("SMTP");
	}
	public void setUserSn(String UserSn){
		this.setInternal("USER_SN" ,UserSn );
	}

	public String getUserSn(){
		return (String)this.getInternal("USER_SN");
	}
	public void setOrgCode(String OrgCode){
		this.setInternal("ORG_CODE" ,OrgCode );
	}

	public String getOrgCode(){
		return (String)this.getInternal("ORG_CODE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}

	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setLevelName(String LevelName){
		this.setInternal("LEVEL_NAME" ,LevelName );
	}

	public String getLevelName(){
		return (String)this.getInternal("LEVEL_NAME");
	}
}
