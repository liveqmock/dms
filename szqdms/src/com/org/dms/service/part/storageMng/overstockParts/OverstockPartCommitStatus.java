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
 * ClassName: OverstockPartUncommittedStatus 
 * Function: 积压件提交状态
 * date: 2014年9月18日 下午4:44:59
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public class OverstockPartCommitStatus implements OverstockPartStatus{
	
	
	private OverstockPartCommitStatus(){ }
	
	/**
	 *  
	 * getInstance:实例化未提交状态类
	 * 使用此方法实例化状态对象,对象引用Action实例类
	 * @author fuxiao
	 * Date:2014年9月18日下午4:48:38
	 * @param ac
	 * @return
	 */
	public static OverstockPartStatus getInstance(){
		return new OverstockPartCommitStatus();
	}
	
	public void handle(DealerOverstockPartApplyAction action){
		
		// 结果保存状态：保存每次提交到数据库的状态，如果状态为false则表示保存失败，则需要报异常。
		boolean saveStatus = false;
		try {
			RequestWrapper requestWrapper = action.atx.getRequest();
			HashMap<String,String> hm = RequestUtil.getValues(requestWrapper);	// 转换前台提交的表单JSON为Map
			String overstockId = hm.get("overstockId".toUpperCase());
			User user = (User)action.atx.getSession().get(Globals.USER_KEY);
			
			// 构建参数
			List<String> args = new ArrayList<String>();
			args.add(DicConstant.JYJSQZT_02); // "306202" 已提交
			args.add(user.getAccount()); // 更新人
			args.add(overstockId); // 主键ID
			saveStatus = action.dao.updateApplyStatusById(args.toArray(),null,null,null);
			if(!saveStatus){
				throw new Exception("DealerOverstockPartApplyForDao.updateApplyStatusById,更新失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			action.logger.error(e);
			action.atx.setException(e);
		}
	}
	
	
	
}
