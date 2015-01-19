package com.org.dms.vo.service;
import com.org.framework.base.BaseVO;
import java.util.Date;
public class MainDealerStarTmpVO extends BaseVO{
    public MainDealerStarTmpVO(){
    	//设置字段信息
    	this.addField("ROW_NO",BaseVO.OP_STRING);
    	this.addField("DEALER_STAR",BaseVO.OP_STRING);
    	this.addField("DEALER_CODE",BaseVO.OP_STRING);
    	this.addField("TMP_ID",BaseVO.OP_STRING|BaseVO.TP_PK);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
		this.bindFieldToSequence("TMP_ID","COMMON");
    	this.setVOTableName("MAIN_DEALER_STAR_TMP");
}
	public void setRowNo(String RowNo){
		this.setInternal("ROW_NO" ,RowNo );
	}


	public String getRowNo(){
		return (String)this.getInternal("ROW_NO");
	}
	public void setDealerStar(String DealerStar){
		this.setInternal("DEALER_STAR" ,DealerStar );
	}


	public String getDealerStar(){
		return (String)this.getInternal("DEALER_STAR");
	}
	public void setDealerCode(String DealerCode){
		this.setInternal("DEALER_CODE" ,DealerCode );
	}


	public String getDealerCode(){
		return (String)this.getInternal("DEALER_CODE");
	}
	public void setTmpId(String TmpId){
		this.setInternal("TMP_ID" ,TmpId );
	}


	public String getTmpId(){
		return (String)this.getInternal("TMP_ID");
	}
	public void setUserAccount(String UserAccount){
		this.setInternal("USER_ACCOUNT" ,UserAccount );
	}


	public String getUserAccount(){
		return (String)this.getInternal("USER_ACCOUNT");
	}
}
