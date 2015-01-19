package com.org.dms.vo.part;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class FsFilestoreVO extends BaseVO{
    public FsFilestoreVO(){
    	//�����ֶ���Ϣ
    	this.addField("WJJBS",BaseVO.OP_STRING);
    	this.addField("GXR",BaseVO.OP_STRING);
    	this.addField("FJMC",BaseVO.OP_STRING);
    	this.addField("GSID",BaseVO.OP_STRING);
    	this.addField("JGID",BaseVO.OP_STRING);
    	this.addField("SJMJ",BaseVO.OP_STRING);
    	this.addField("BLWJM",BaseVO.OP_STRING);
    	this.addField("GXSJ",BaseVO.OP_DATE);
		this.setFieldDateFormat("GXSJ", "yyyy-MM-dd");
    	this.addField("FID",BaseVO.OP_STRING);
    	this.addField("FJID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("CJSJ",BaseVO.OP_DATE);
		this.setFieldDateFormat("CJSJ", "yyyy-MM-dd");
    	this.addField("FJLJ",BaseVO.OP_STRING);
    	this.addField("FJZT",BaseVO.OP_STRING);
    	this.addField("YWZJ",BaseVO.OP_STRING);
    	this.addField("CJR",BaseVO.OP_STRING);
		this.bindFieldToSequence("FJID","COMMON");
    	this.setVOTableName("FS_FILESTORE");
}
	public void setWjjbs(String Wjjbs){
		this.setInternal("WJJBS" ,Wjjbs );
	}


	public String getWjjbs(){
		return (String)this.getInternal("WJJBS");
	}
	public void setGxr(String Gxr){
		this.setInternal("GXR" ,Gxr );
	}


	public String getGxr(){
		return (String)this.getInternal("GXR");
	}
	public void setFjmc(String Fjmc){
		this.setInternal("FJMC" ,Fjmc );
	}


	public String getFjmc(){
		return (String)this.getInternal("FJMC");
	}
	public void setGsid(String Gsid){
		this.setInternal("GSID" ,Gsid );
	}


	public String getGsid(){
		return (String)this.getInternal("GSID");
	}
	public void setJgid(String Jgid){
		this.setInternal("JGID" ,Jgid );
	}


	public String getJgid(){
		return (String)this.getInternal("JGID");
	}
	public void setSjmj(String Sjmj){
		this.setInternal("SJMJ" ,Sjmj );
	}


	public String getSjmj(){
		return (String)this.getInternal("SJMJ");
	}
	public void setBlwjm(String Blwjm){
		this.setInternal("BLWJM" ,Blwjm );
	}


	public String getBlwjm(){
		return (String)this.getInternal("BLWJM");
	}
	public void setGxsj(Date Gxsj){
		this.setInternal("GXSJ" ,Gxsj );
	}


	public Date getGxsj(){
		return (Date)this.getInternal("GXSJ");
	}
	public void setFid(String Fid){
		this.setInternal("FID" ,Fid );
	}


	public String getFid(){
		return (String)this.getInternal("FID");
	}
	public void setFjid(String Fjid){
		this.setInternal("FJID" ,Fjid );
	}


	public String getFjid(){
		return (String)this.getInternal("FJID");
	}
	public void setCjsj(Date Cjsj){
		this.setInternal("CJSJ" ,Cjsj );
	}


	public Date getCjsj(){
		return (Date)this.getInternal("CJSJ");
	}
	public void setFjlj(String Fjlj){
		this.setInternal("FJLJ" ,Fjlj );
	}


	public String getFjlj(){
		return (String)this.getInternal("FJLJ");
	}
	public void setFjzt(String Fjzt){
		this.setInternal("FJZT" ,Fjzt );
	}


	public String getFjzt(){
		return (String)this.getInternal("FJZT");
	}
	public void setYwzj(String Ywzj){
		this.setInternal("YWZJ" ,Ywzj );
	}


	public String getYwzj(){
		return (String)this.getInternal("YWZJ");
	}
	public void setCjr(String Cjr){
		this.setInternal("CJR" ,Cjr );
	}


	public String getCjr(){
		return (String)this.getInternal("CJR");
	}
}
