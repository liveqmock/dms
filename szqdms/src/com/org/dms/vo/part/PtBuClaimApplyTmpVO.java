package com.org.dms.vo.part;

import com.org.framework.base.BaseVO;

/**
 * 
 * ClassName: PtBuClaimApplyTmpVO 
 * Function: 配件管理：旧件回运 
 * date: 2014年10月28日 下午5:17:33
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 *
 */
public class PtBuClaimApplyTmpVO extends BaseVO{
	
	private static final long serialVersionUID = 1L;
	public PtBuClaimApplyTmpVO(){
    	this.addField("APPLY_ID",BaseVO.OP_STRING);
    	this.addField("APPLY_NO",BaseVO.OP_STRING);
    	this.addField("BOX_NO",BaseVO.OP_STRING);
    	this.addField("USER_ACCOUNT",BaseVO.OP_STRING);
    	this.addField("ROW_NO",BaseVO.OP_NUMBER);
    	this.setVOTableName("PT_BU_CLAIM_APPLY_TMP");
	}
	
	public String getApplyId() {
		return (String)this.getInternal("APPLY_ID");
	}
	public void setApplyId(String applyId) {
		this.setInternal("APPLY_ID" ,applyId );
	}
	public String getApplyNo() {
		return (String)this.getInternal("APPLY_NO");
	}
	public void setApplyNo(String applyNo) {
		this.setInternal("APPLY_NO" ,applyNo );
	}
	public String getBoxNo() {
		return (String)this.getInternal("BOX_NO");
	}
	public void setBoxNo(String boxNo) {
		this.setInternal("BOX_NO" , boxNo);
	}
	public String getUserAccount() {
		return (String)this.getInternal("USER_ACCOUNT");
	}
	public void setUserAccount(String userAccount) {
		this.setInternal("USER_ACCOUNT" , userAccount);
	}
	public int getRowNo() {
		return (Integer)this.getInternal("ROW_NO");
	}
	public void setRowNo(int rowNo) {
		this.setInternal("ROW_NO" , rowNo);
	}
	
	
}
