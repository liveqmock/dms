package com.org.frameImpl.vo;
import com.org.frameImpl.Constant;
import com.org.framework.base.BaseVO;

import java.util.Date;
public class UserParaConfigureVO extends BaseVO{
	private static final long serialVersionUID = 4493000013128795197L;

	public UserParaConfigureVO(){
    	//设置字段信息
    	this.addField("TYPENAME",BaseVO.OP_STRING);
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("PARAVALUE4",BaseVO.OP_STRING);
    	this.addField("PARAVALUE3",BaseVO.OP_STRING);
    	this.addField("SN",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("PARAVALUE2",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("PARAVALUE1",BaseVO.OP_STRING);
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("MEMO",BaseVO.OP_STRING);
    	this.addField("PARAKEY",BaseVO.OP_STRING);
    	this.addField("PARANAME",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("BUS_TYPE",BaseVO.OP_STRING);
    	this.addField("APPTYPE",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
		this.bindFieldToSequence("SN","COMMON");
		//绑定字典
		this.bindFieldToDic("STATUS", Constant.YXBS);
		this.bindFieldToDic("BUS_TYPE", Constant.YWLX);
    	this.setVOTableName("USER_PARA_CONFIGURE");
}
	public void setTypename(String Typename){
		this.setInternal("TYPENAME" ,Typename );
	}


	public String getTypename(){
		return (String)this.getInternal("TYPENAME");
	}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setParavalue4(String Paravalue4){
		this.setInternal("PARAVALUE4" ,Paravalue4 );
	}


	public String getParavalue4(){
		return (String)this.getInternal("PARAVALUE4");
	}
	public void setParavalue3(String Paravalue3){
		this.setInternal("PARAVALUE3" ,Paravalue3 );
	}


	public String getParavalue3(){
		return (String)this.getInternal("PARAVALUE3");
	}
	public void setSn(String Sn){
		this.setInternal("SN" ,Sn );
	}


	public String getSn(){
		return (String)this.getInternal("SN");
	}
	public void setParavalue2(String Paravalue2){
		this.setInternal("PARAVALUE2" ,Paravalue2 );
	}


	public String getParavalue2(){
		return (String)this.getInternal("PARAVALUE2");
	}
	public void setUpdateTime(Date UpdateTime){
		this.setInternal("UPDATE_TIME" ,UpdateTime );
	}


	public Date getUpdateTime(){
		return (Date)this.getInternal("UPDATE_TIME");
	}
	public void setParavalue1(String Paravalue1){
		this.setInternal("PARAVALUE1" ,Paravalue1 );
	}


	public String getParavalue1(){
		return (String)this.getInternal("PARAVALUE1");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setMemo(String Memo){
		this.setInternal("MEMO" ,Memo );
	}


	public String getMemo(){
		return (String)this.getInternal("MEMO");
	}
	public void setParakey(String Parakey){
		this.setInternal("PARAKEY" ,Parakey );
	}


	public String getParakey(){
		return (String)this.getInternal("PARAKEY");
	}
	public void setParaname(String Paraname){
		this.setInternal("PARANAME" ,Paraname );
	}


	public String getParaname(){
		return (String)this.getInternal("PARANAME");
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
	public void setApptype(String Apptype){
		this.setInternal("APPTYPE" ,Apptype );
	}


	public String getApptype(){
		return (String)this.getInternal("APPTYPE");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
}
