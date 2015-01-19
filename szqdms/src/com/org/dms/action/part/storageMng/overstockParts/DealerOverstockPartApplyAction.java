package com.org.dms.action.part.storageMng.overstockParts;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.overstockParts.DealerOverstockPartApplyForDao;
import com.org.dms.service.part.storageMng.overstockParts.OverstockPartAuditStatus;
import com.org.dms.service.part.storageMng.overstockParts.OverstockPartAuditUnStatus;
import com.org.dms.service.part.storageMng.overstockParts.OverstockPartCommitStatus;
import com.org.dms.service.part.storageMng.overstockParts.OverstockPartConfirmStatus;
import com.org.dms.service.part.storageMng.overstockParts.OverstockPartStatus;
import com.org.dms.service.part.storageMng.overstockParts.OverstockPartUnConfirmStatus;
import com.org.dms.service.part.storageMng.overstockParts.OverstockPartUncommittedStatus;
import com.org.framework.Globals;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * ClassName: DealerOverstockPartApplyAction 
 * Function: 积压件申请单Action
 * date: 2014年9月16日 下午3:25:51
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 */
public class DealerOverstockPartApplyAction {

	// 日志类
	public Logger logger = com.org.framework.log.log.getLogger("DealerOverstockPartApplyAction");

	// 上下文对象
	public ActionContext atx = ActionContext.getContext();

	// 定义dao对象
	public DealerOverstockPartApplyForDao dao = DealerOverstockPartApplyForDao.getInstance(atx);
	
	/**
	 * 
	 * queryOverstockApply:经销商申请单列表查询
	 * @author fuxiao
	 * Date:2014年9月16日下午3:56:10
	 */
	public void queryOverstockApply(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			atx.setOutData("bs", this.dao.queryApplyForDealer(pageManager, conditions, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// 经销商申请单全部查询
	public void queryOverstockApplyAll(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			atx.setOutData("bs", this.dao.queryApplyForDealerAll(pageManager, conditions, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	// OEM申请单查询
	public void queryOverstockApplyAuditAll(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			atx.setOutData("bs", this.dao.queryApplyForAuditAll(pageManager, conditions, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * queryOverstockApplyPartInfo: 单张申请单主信息查询
	 * @author fuxiao
	 * Date:2014年9月19日上午11:20:09
	 */
	public void queryOverstockApplyById(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			String overstockId = Pub.val(requestWrapper, "overstockId");
			
			// 查询申请单主信息
			QuerySet qs = this.dao.queryApplyById(overstockId);
			
			// 遍历查询结果，并将结果保存至Map集合
			Map<String,String> map = new HashMap<String,String>();
			for(Iterator<?> iter = qs.getAttrMap().keySet().iterator();iter.hasNext();){
				String key = (String)iter.next();
				map.put(key.toUpperCase()+"_S",qs.getString(1, key));
			}
			
			// 转为JSON
			JSONObject obj = JSONObject.fromObject(map);
			atx.setOutMsg(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 
	 * queryOverstockApplyPartInfo: 根据申请单ID查询申请单配件详情
	 * @author fuxiao
	 * Date:2014年9月19日上午11:26:20
	 */
	public void queryOverstockApplyPartInfo(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			String overstockId = Pub.val(requestWrapper, "overstockId");
			atx.setOutData("bs", this.dao.queryApplyPartInfo(pageManager, conditions,overstockId,user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 
	 * applyStatusSave:根据申请单的状态，执行不同状态下的方法
	 * 所有状态实现接口OverstockPartStatus，在接口方法中实现不同状态下的业务逻辑
	 * 前台传递参数时一定要带参数status
	 * 状态类在初始化静态区域实例化，如果需要添加新的状态，请在井田区域添加实例化代码
	 * @author fuxiao
	 * Date:2014年9月18日下午11:06:23
	 */
	public void applyStatusSave(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			
			// 将前台JSON转为Map集合
			HashMap<String,String> hm = RequestUtil.getValues(requestWrapper);
			String overstockApplyStatus = hm.get("overstockApplyStatus".toUpperCase());
			OverstockPartStatus statusActive = null;
			if(overstockApplyStatus == null || "".equals(overstockApplyStatus)){
				statusActive = null;
			}else if(overstockApplyStatus.equals(DicConstant.JYJSQZT_01)){ // 未提交
				statusActive = OverstockPartUncommittedStatus.getInstance();
			}else if(overstockApplyStatus.equals(DicConstant.JYJSQZT_02)){ // 已提交
				statusActive = OverstockPartCommitStatus.getInstance();
			}else if(overstockApplyStatus.equals(DicConstant.JYJSQZT_03)){ // 确认同意
				statusActive = OverstockPartConfirmStatus.getInstance();
			}else if(overstockApplyStatus.equals(DicConstant.JYJSQZT_04)){ // 确认驳回
				statusActive = OverstockPartUnConfirmStatus.getInstance();
			}else if(overstockApplyStatus.equals(DicConstant.JYJSQZT_05)){ // 审核同意
				statusActive = OverstockPartAuditStatus.getInstance();
			}else if(overstockApplyStatus.equals(DicConstant.JYJSQZT_06)){ // 审核驳回
				statusActive = OverstockPartAuditUnStatus.getInstance();
			}
			if(statusActive != null){
				statusActive.handle(this);
			}else{
				throw new Exception("没有对应申请单处理状态实现类");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 
	 * applyConfirmQuery: 待确认申请单查询
	 * @author fuxiao
	 * Date:2014年9月19日上午10:25:58
	 */
	public void applyConfirmQuery(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			atx.setOutData("bs", this.dao.queryApplyForStatus(pageManager, conditions, user));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 
	 * applyConfirmQuery: 待确认申请单查询
	 * @author fuxiao
	 * Date:2014年9月19日上午10:25:58
	 */
	public void applyAuditQuery(){
		try {
			RequestWrapper requestWrapper = atx.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper,pageManager);
			atx.setOutData("bs", this.dao.queryApplyForStatus(pageManager, conditions, null));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
}
