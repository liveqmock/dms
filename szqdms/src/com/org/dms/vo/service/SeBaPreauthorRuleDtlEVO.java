package com.org.dms.vo.service;

import com.org.framework.base.BaseVO;

public class SeBaPreauthorRuleDtlEVO extends SeBaPreauthorRuleDtlVO{
	 public SeBaPreauthorRuleDtlEVO(){
		 this.addField("PREAUTHOR_OBJECT_CODE",BaseVO.OP_STRING);
	 }
	 
	 public void setPreauthorObjectCode(String PreauthorObjectCode){
			this.setInternal("PREAUTHOR_OBJECT_CODE" ,PreauthorObjectCode );
		}


		public String getPreauthorObjectCode(){
			return (String)this.getInternal("PREAUTHOR_OBJECT_CODE");
		}
}
