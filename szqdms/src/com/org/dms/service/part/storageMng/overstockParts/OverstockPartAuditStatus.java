package com.org.dms.service.part.storageMng.overstockParts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.org.dms.action.part.storageMng.overstockParts.DealerOverstockPartApplyAction;
import com.org.dms.common.DicConstant;
import com.org.framework.Globals;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * ClassName: OverstockPartAuditStatus 
 * Function: 审核同意状态
 * date: 2014年9月21日 下午4:25:37
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public class OverstockPartAuditStatus implements OverstockPartStatus{
	
	
	private OverstockPartAuditStatus(){ }
	
	public static OverstockPartStatus getInstance(){
		return new OverstockPartAuditStatus();
	}
	
	/**
	 * 状态逻辑处理
	 */
	public void handle(DealerOverstockPartApplyAction action){
		
		// 结果保存状态：保存每次提交到数据库的状态，如果状态为false则表示保存失败，则需要报异常。
		boolean saveStatus = false;
		try {
			RequestWrapper requestWrapper = action.atx.getRequest();
			HashMap<String,String> hm = RequestUtil.getValues(requestWrapper);	// 转换前台提交的表单JSON为Map
			String overstockId = hm.get("overstockId_f".toUpperCase());
			String checkRemark = hm.get("checkRemark_f".toUpperCase());
			User user = (User)action.atx.getSession().get(Globals.USER_KEY);
			
			// 构建参数
			List<String> args = new ArrayList<String>();
			args.add(DicConstant.JYJSQZT_05); // 审核通过
			args.add(user.getAccount()); // 更新人
			args.add(overstockId); // 主键ID
			saveStatus = action.dao.updateApplyStatusById(args.toArray(), null, null, checkRemark);
			if(!saveStatus){
				throw new Exception("DealerOverstockPartApplyForDao.updateApplyStatusById,更新失败");
			}
			
			// 获取申请方，出货方类型
			QuerySet qs = action.dao.queryApplyForOrgType(overstockId);
			String inOrgType = qs.getString(1, "ORG_TYPE");
			String outOrgType = qs.getString(1, "OUT_ORG_TYPE");
			
			// 根据类型生成对应策略
			OverstockStrategyActive active = OverstockStrategyFactroy.create(inOrgType, outOrgType, action.atx);
			
			if(active == null){
				throw new Exception("审核没有对应的策略方法：OverstockStrategyActive");
			}
			
			// 调用对应的策略方法
			active.active(action.atx);;
		} catch (Exception e) {
			e.printStackTrace();
			action.logger.error(e);
			action.atx.setException(e);
		}
	}
	
	
	
}
