package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class SeBaReturnDealerRelationVO extends BaseVO{
    public SeBaReturnDealerRelationVO(){
    	//�����ֶ���Ϣ
    	this.addField("CREATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd");
    	this.addField("D_ORGCODE",BaseVO.OP_STRING);
    	this.addField("R_ORGCODE",BaseVO.OP_STRING);
    	this.addField("RD_MILES",BaseVO.OP_STRING);
    	this.addField("UPDATE_TIME",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_TIME", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("D_ORGNAME",BaseVO.OP_STRING);
    	this.addField("R_ORGNAME",BaseVO.OP_STRING);
    	this.addField("RD_PRICE",BaseVO.OP_STRING);
    	this.addField("CREATE_USER",BaseVO.OP_STRING);
    	this.addField("R_ORGID",BaseVO.OP_STRING);
    	this.addField("D_ORGID",BaseVO.OP_STRING);
    	this.addField("UPDATE_USER",BaseVO.OP_STRING);
    	this.addField("RELATION_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
		this.bindFieldToSequence("RELATION_ID","COMMON");
    	this.setVOTableName("SE_BA_RETURN_DEALER_RELATION");
}
	public void setCreateTime(Date CreateTime){
		this.setInternal("CREATE_TIME" ,CreateTime );
	}


	public Date getCreateTime(){
		return (Date)this.getInternal("CREATE_TIME");
	}
	public void setDOrgcode(String DOrgcode){
		this.setInternal("D_ORGCODE" ,DOrgcode );
	}


	public String getDOrgcode(){
		return (String)this.getInternal("D_ORGCODE");
	}
	public void setROrgcode(String ROrgcode){
		this.setInternal("R_ORGCODE" ,ROrgcode );
	}


	public String getROrgcode(){
		return (String)this.getInternal("R_ORGCODE");
	}
	public void setRdMiles(String RdMiles){
		this.setInternal("RD_MILES" ,RdMiles );
	}


	public String getRdMiles(){
		return (String)this.getInternal("RD_MILES");
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
	public void setDOrgname(String DOrgname){
		this.setInternal("D_ORGNAME" ,DOrgname );
	}


	public String getDOrgname(){
		return (String)this.getInternal("D_ORGNAME");
	}
	public void setROrgname(String ROrgname){
		this.setInternal("R_ORGNAME" ,ROrgname );
	}


	public String getROrgname(){
		return (String)this.getInternal("R_ORGNAME");
	}
	public void setRdPrice(String RdPrice){
		this.setInternal("RD_PRICE" ,RdPrice );
	}


	public String getRdPrice(){
		return (String)this.getInternal("RD_PRICE");
	}
	public void setCreateUser(String CreateUser){
		this.setInternal("CREATE_USER" ,CreateUser );
	}


	public String getCreateUser(){
		return (String)this.getInternal("CREATE_USER");
	}
	public void setROrgid(String ROrgid){
		this.setInternal("R_ORGID" ,ROrgid );
	}


	public String getROrgid(){
		return (String)this.getInternal("R_ORGID");
	}
	public void setDOrgid(String DOrgid){
		this.setInternal("D_ORGID" ,DOrgid );
	}


	public String getDOrgid(){
		return (String)this.getInternal("D_ORGID");
	}
	public void setUpdateUser(String UpdateUser){
		this.setInternal("UPDATE_USER" ,UpdateUser );
	}


	public String getUpdateUser(){
		return (String)this.getInternal("UPDATE_USER");
	}
	public void setRelationId(String RelationId){
		this.setInternal("RELATION_ID" ,RelationId );
	}


	public String getRelationId(){
		return (String)this.getInternal("RELATION_ID");
	}
}
