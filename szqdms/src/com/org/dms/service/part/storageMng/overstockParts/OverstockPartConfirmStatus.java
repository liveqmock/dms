package com.org.dms.service.part.storageMng.overstockParts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.org.dms.action.part.storageMng.overstockParts.DealerOverstockPartApplyAction;
import com.org.dms.common.DicConstant;
import com.org.framework.Globals;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * ClassName: OverstockPartConfirmStatus 
 * Function: 积压件确认同意状态
 * date: 2014年9月21日 下午3:32:10
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 */
public class OverstockPartConfirmStatus implements OverstockPartStatus{
	
	
	private OverstockPartConfirmStatus(){ }
	
	public static OverstockPartStatus getInstance(){
		return new OverstockPartConfirmStatus();
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
			String overstockId = hm.get("overstockId".toUpperCase());
			String confirmRemark = hm.get("confirmRemark_f".toUpperCase());
			User user = (User)action.atx.getSession().get(Globals.USER_KEY);
			
			// 构建参数
			List<String> args = new ArrayList<String>();
			args.add(DicConstant.JYJSQZT_03); // 确认通过待审核
			args.add(user.getAccount()); // 更新人
			args.add(overstockId); // 主键ID
			saveStatus = action.dao.updateApplyStatusById(args.toArray(), null, confirmRemark, null);
			if(!saveStatus){
				throw new Exception("DealerOverstockPartApplyForDao.updateApplyStatusById,更新失败");
			}
			
			// 锁定配件库存状态 : 库存数量 不变；占用数量 = 占用数量 + 申请数量  可用数量 = 可用数量 - 申请数量
			action.dao.chagePageCount(overstockId, user.getAccount());
		} catch (Exception e) {
			e.printStackTrace();
			action.logger.error(e);
			action.atx.setException(e);
		}
	}
	
	
	
}
