package com.org.dms.dao.part.mainTenanceApplication;

import java.util.Map;

/**
 * 
 * ClassName: 申请单类型接口
 * date: 2014年10月12日 上午11:50:42
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public interface ApplicationType {
	
	// 保存申请单
	public void save(Map<String,String> hm) throws Exception;
	
	// ED技术科审核
	public void edAudit(Map<String,String> hm) throws Exception;
	
	// ED技术科审核暂存
	public void edTempSave(Map<String, String> hm) throws Exception;
	
	// PD技术科审核暂存
	public void pdTempSave(Map<String, String> hm) throws Exception;
	
	// PD采供科审核
	public void pdAudit(Map<String,String> hm) throws Exception;
}
