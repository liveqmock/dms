package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class MainBulletinRangeVO extends BaseVO{
    public MainBulletinRangeVO(){
    	//�����ֶ���Ϣ
    	this.addField("SIGN_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("SIGN_DATE", "yyyy-MM-dd");
    	this.addField("UPDATE_BY",BaseVO.OP_STRING);
    	this.addField("ORG_TYPE",BaseVO.OP_STRING);
    	this.addField("UPDATE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("UPDATE_DATE", "yyyy-MM-dd");
    	this.addField("CREATE_BY",BaseVO.OP_STRING);
    	this.addField("BULLETIN_ID",BaseVO.OP_STRING);
    	this.addField("RANGE_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("SIGN_USER_ID",BaseVO.OP_STRING);
    	this.addField("CREATE_DATE",BaseVO.OP_DATE);
		this.setFieldDateFormat("CREATE_DATE", "yyyy-MM-dd");
    	this.addField("STATUS",BaseVO.OP_STRING);
    	this.addField("ORG_ID",BaseVO.OP_STRING);
		this.bindFieldToSequence("RANGE_ID","COMMON");
    	this.setVOTableName("MAIN_BULLETIN_RANGE");
}
	public void setSignDate(Date SignDate){
		this.setInternal("SIGN_DATE" ,SignDate );
	}


	public Date getSignDate(){
		return (Date)this.getInternal("SIGN_DATE");
	}
	public void setUpdateBy(String UpdateBy){
		this.setInternal("UPDATE_BY" ,UpdateBy );
	}


	public String getUpdateBy(){
		return (String)this.getInternal("UPDATE_BY");
	}
	public void setOrgType(String OrgType){
		this.setInternal("ORG_TYPE" ,OrgType );
	}


	public String getOrgType(){
		return (String)this.getInternal("ORG_TYPE");
	}
	public void setUpdateDate(Date UpdateDate){
		this.setInternal("UPDATE_DATE" ,UpdateDate );
	}


	public Date getUpdateDate(){
		return (Date)this.getInternal("UPDATE_DATE");
	}
	public void setCreateBy(String CreateBy){
		this.setInternal("CREATE_BY" ,CreateBy );
	}


	public String getCreateBy(){
		return (String)this.getInternal("CREATE_BY");
	}
	public void setBulletinId(String BulletinId){
		this.setInternal("BULLETIN_ID" ,BulletinId );
	}


	public String getBulletinId(){
		return (String)this.getInternal("BULLETIN_ID");
	}
	public void setRangeId(String RangeId){
		this.setInternal("RANGE_ID" ,RangeId );
	}


	public String getRangeId(){
		return (String)this.getInternal("RANGE_ID");
	}
	public void setSignUserId(String SignUserId){
		this.setInternal("SIGN_USER_ID" ,SignUserId );
	}


	public String getSignUserId(){
		return (String)this.getInternal("SIGN_USER_ID");
	}
	public void setCreateDate(Date CreateDate){
		this.setInternal("CREATE_DATE" ,CreateDate );
	}


	public Date getCreateDate(){
		return (Date)this.getInternal("CREATE_DATE");
	}
	public void setStatus(String Status){
		this.setInternal("STATUS" ,Status );
	}


	public String getStatus(){
		return (String)this.getInternal("STATUS");
	}
	public void setOrgId(String OrgId){
		this.setInternal("ORG_ID" ,OrgId );
	}


	public String getOrgId(){
		return (String)this.getInternal("ORG_ID");
	}
}
