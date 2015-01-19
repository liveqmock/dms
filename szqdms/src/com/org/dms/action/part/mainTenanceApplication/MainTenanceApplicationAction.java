package com.org.dms.action.part.mainTenanceApplication;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.mainTenanceApplication.ApplicationType;
import com.org.dms.dao.part.mainTenanceApplication.ApplicationTypeFactory;
import com.org.dms.dao.part.mainTenanceApplication.MainTenanceApplicationDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
/**
 * 
 * @ClassName: MainTenanceApplicationAction
 * @Description: 配件申请维护Action
 * @author: ALONY
 * @date: 2014年12月5日 下午3:45:55
 */
public class MainTenanceApplicationAction {
	
	private Logger logger = com.org.framework.log.log.getLogger("MainTenanceApplicationAction");
	// 上下文对象
	private ActionContext atx = ActionContext.getContext();
	// 定义dao对象
	private MainTenanceApplicationDao dao = MainTenanceApplicationDao.getInstance(atx);
	
	// 申请查询
	public void applicationSelect() throws Exception{
		RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.queryApplicationList(page,conditions,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// 配件申请单导出查询
	public void partInfoApplicationQuery() throws Exception{
		RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.queryPartInfoExport(page,conditions,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// 经销商的申请单查询
	public void applicationSelectForDealer() throws Exception{
		RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.queryApplicationListForDealer(page,conditions,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// 审核方申请单查询
	public void applicationSelectForAudit() throws Exception{
		RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.queryApplicationListForAudit(page,conditions,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// ED审核
	public void applicationSelectByAudit() throws Exception{
		RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.queryApplicationListByAudit(page,conditions,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// PD审核
	public void applicationSelectByAuditForPd() throws Exception{
		RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.queryApplicationListByAuditForPD(page,conditions,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// 申请单详情
	public void applicationDetails() throws Exception{
		RequestWrapper request = atx.getRequest();
		String applicationId = Pub.val(request, "applicationId");
		try
		{
			QuerySet qs = this.dao.queryApplicationDetails(applicationId);
			
			// 遍历查询结果，并将结果保存至Map集合
			Map<String,String> map = new HashMap<String,String>();
			for(Iterator<?> iter = qs.getAttrMap().keySet().iterator();iter.hasNext();){
				String key = (String)iter.next();
				map.put(key.toUpperCase()+"_D",qs.getString(1, key));
			}
			
			// 转为JSON
			JSONObject obj = JSONObject.fromObject(map);
			atx.setOutMsg(obj.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// 申请查询-配件信息录入
	public void applicationPartInfoDetails() throws Exception{
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.queryApplicationDetailsForPartInfo(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// 申请查询-配件信息录入：审核或者修改
	public void applicationPartInfoDetailsForAuditOrEdit() throws Exception {
		RequestWrapper request = atx.getRequest();
		String applicationId = Pub.val(request, "applicationId");
		try
		{
			QuerySet qs = this.dao.queryApplicationDetailsForPartInfo(applicationId);
			
			Map<String,Map<String,String>> resultMap = new HashMap<String,Map<String,String>>();
			for(int i = 0 ; i < qs.getRowCount() ; i++){
				// 遍历查询结果，并将结果保存至Map集合
				Map<String,String> map = new HashMap<String,String>();
				for(Iterator<?> iter = qs.getAttrMap().keySet().iterator();iter.hasNext();){
					String key = (String)iter.next();
					map.put(key.toUpperCase(),qs.getString(i+1, key));
				}
				resultMap.put("row"+i, map);
			}
			// 转为JSON
			JSONObject obj = JSONObject.fromObject(resultMap);
			atx.setOutMsg(obj.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// 申请查询-驾驶室总成新编号录入CAB_ASSEMBLY
	public void applicationCABAssemblyDetails() throws Exception{
		RequestWrapper request = atx.getRequest();
		String applicationId = Pub.val(request, "applicationId");
		try
		{
			QuerySet qs = this.dao.queryApplicationDetailsForCABAssembly(applicationId);
			
			// 遍历查询结果，并将结果保存至Map集合
			Map<String,String> map = new HashMap<String,String>();
			for(Iterator<?> iter = qs.getAttrMap().keySet().iterator();iter.hasNext();){
				String key = (String)iter.next();
				map.put(key.toUpperCase(),qs.getString(1, key));
			}
			
			qs = this.dao.queryApplicationDetailsForCarBody(applicationId);
			qs.getRowCount();
			for(int i = 0; i < qs.getRowCount(); i++){
				map.put(qs.getString(i + 1, "BODY_CODE"), qs.getString(i + 1, "BODY_VALUE"));
			}
			// 转为JSON
			JSONObject obj = JSONObject.fromObject(map);
			atx.setOutMsg(obj.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// 申请查询-驾驶室本体新编号
	public void applicationCABInfoDetails() throws Exception{
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.queryApplicationDetailsForCABInfo(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// 驾驶室本体新编号: 修改或审核时详情
	public void applicationCABInfoDetailsForEdit() throws Exception{
		RequestWrapper request = atx.getRequest();
		String applicationId = Pub.val(request, "applicationId");
		try
		{
			QuerySet qs = this.dao.queryApplicationDetailsForCABInfoEdit(applicationId);
			
			Map<String,Map<String,String>> resultMap = new HashMap<String,Map<String,String>>();
			for(int i = 0 ; i < qs.getRowCount() ; i++){
				// 遍历查询结果，并将结果保存至Map集合
				Map<String,String> map = new HashMap<String,String>();
				for(Iterator<?> iter = qs.getAttrMap().keySet().iterator();iter.hasNext();){
					String key = (String)iter.next();
					map.put(key.toUpperCase(),qs.getString(i+1, key));
				}
				resultMap.put("row"+i, map);
			}
			// 转为JSON
			JSONObject obj = JSONObject.fromObject(resultMap);
			atx.setOutMsg(obj.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// 申请查询-零件编号更新（禁用）
	public void applicationPartInfoChangeDetails() throws Exception{
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.queryApplicationDetailsForPartInfoChange(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// 申请查询-零件编号更新（禁用）: 修改或审核
	public void applicationPartInfoChangeDetailsForEdit() throws Exception{
		RequestWrapper request = atx.getRequest();
		String applicationId = Pub.val(request, "applicationId");
		try
		{
			QuerySet qs = this.dao.queryApplicationDetailsForPartInfoChangeForEdit(applicationId);
			
			Map<String,Map<String,String>> resultMap = new HashMap<String,Map<String,String>>();
			for(int i = 0 ; i < qs.getRowCount() ; i++){
				// 遍历查询结果，并将结果保存至Map集合
				Map<String,String> map = new HashMap<String,String>();
				for(Iterator<?> iter = qs.getAttrMap().keySet().iterator();iter.hasNext();){
					String key = (String)iter.next();
					map.put(key.toUpperCase(),qs.getString(i+1, key));
				}
				resultMap.put("row"+i, map);
			}
			// 转为JSON
			JSONObject obj = JSONObject.fromObject(resultMap);
			atx.setOutMsg(obj.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// 申请查询-供货商新增（变更）SUPPLIER_CHANGE
	public void applicationSupplierChangeDetails() throws Exception{
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.queryApplicationDetailsForSupplierChange(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// 申请查询-供货商新增（变更）: 修改或审核
	public void applicationSupplierChangeDetailsForEdit() throws Exception{
		RequestWrapper request = atx.getRequest();
		String applicationId = Pub.val(request, "applicationId");
		try
		{
			QuerySet qs = this.dao.queryApplicationDetailsForSupplierChangeForEdit(applicationId);
			
			Map<String,Map<String,String>> resultMap = new HashMap<String,Map<String,String>>();
			for(int i = 0 ; i < qs.getRowCount() ; i++){
				// 遍历查询结果，并将结果保存至Map集合
				Map<String,String> map = new HashMap<String,String>();
				for(Iterator<?> iter = qs.getAttrMap().keySet().iterator();iter.hasNext();){
					String key = (String)iter.next();
					map.put(key.toUpperCase(),qs.getString(i+1, key));
				}
				resultMap.put("row"+i, map);
			}
			// 转为JSON
			JSONObject obj = JSONObject.fromObject(resultMap);
			atx.setOutMsg(obj.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// 申请单保存
	public void saveApplication() throws Exception {
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		RequestWrapper requestWrapper = atx.getRequest();
		HashMap<String,String> hm = RequestUtil.getValues(requestWrapper); // 将前台JSON转为Map集合
		hm.put("USER_ACCOUNT", user.getAccount());
		hm.put("ORG_CODE", user.getOrgCode());
		hm.put("ORG_ID", user.getOrgId());
		String applicationType = hm.get("APPLICATION_TYPE_A"); // 获取申请单类型
		ApplicationType typeDao = ApplicationTypeFactory.createApplicationType(applicationType, atx);
		
		// 获取申请单状态：根据状态的不同，调用不同的保存方法
		String applicationStatus = hm.get("APPLICATION_STATUS_A") == null ? "" : hm.get("APPLICATION_STATUS_A");
		String editAction = hm.get("EDIT_ACTION") == null ? "" : hm.get("EDIT_ACTION");
		if(applicationStatus.equals(DicConstant.PJWHSQZT_03) && editAction.equals("") || applicationStatus.equals(DicConstant.PJWHSQZT_04) && editAction.equals("")){ // PJWHSQZT_03 技术科审核通过  PJWHSQZT_04  技术科审核驳回
			typeDao.edAudit(hm);
		}else if(applicationStatus.equals(DicConstant.PJWHSQZT_05) && editAction.equals("") || applicationStatus.equals(DicConstant.PJWHSQZT_06) && editAction.equals("")){ // PJWHSQZT_05 采供科审核通过  PJWHSQZT_06  采供科审核驳回
			typeDao.pdAudit(hm);
		}else if(applicationStatus.equals(DicConstant.PJWHSQZT_07)){ // 技术科审核暂存
			typeDao.edTempSave(hm);
		}else if(applicationStatus.equals(DicConstant.PJWHSQZT_08)){ // 采供科审核暂存
			typeDao.pdTempSave(hm);
		}else{
			typeDao.save(hm);
		}
	}
	
	// 申请单提交
	public void submitApplication() throws Exception {
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		RequestWrapper requestWrapper = atx.getRequest();
		HashMap<String,String> hm = RequestUtil.getValues(requestWrapper); // 将前台JSON转为Map集合
		hm.put("USER_ACCOUNT", user.getAccount());
		this.dao.submitApplication(hm);
	}
	
	
	// 供应商查询
	public void suplierQuery() throws Exception {
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.querySupplierInfo(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// 检测配件是否存在 true存在 false不存在
	public void checkPartCodeExists() throws Exception {
		RequestWrapper request = atx.getRequest();
		try
		{
			String[] partCodes = Pub.val(request, "partCodes").split(",");
			boolean checkResult = false;
			String message = "";
			for(int i = 0, len = partCodes.length; i < len; i++){
				checkResult = dao.checkPartIsExist(partCodes[i]);
				if(checkResult){
					message = "配件：" + partCodes[i] + "，系统中已存在";
					break;
				}
			}
			JSONObject obj = JSONObject.fromObject("{result : '"+checkResult+"', message : '"+message+"'}");
			atx.setOutMsg(obj.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
}
