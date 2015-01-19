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
 * Function: 积压件未提交状态
 * date: 2014年9月18日 下午4:44:59
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public class OverstockPartUncommittedStatus implements OverstockPartStatus{
	
	private OverstockPartUncommittedStatus(){ }
	
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
		return new OverstockPartUncommittedStatus();
	}
	
	/**
	 * 具体状态保存的方法:未提交状态保存方法
	 */
	public void handle(DealerOverstockPartApplyAction action){
		
		boolean saveStatus = false;
		try {
			RequestWrapper requestWrapper = action.atx.getRequest();
			HashMap<String,String> hm = RequestUtil.getValues(requestWrapper);	// 转换前台提交的表单JSON为Map
			
			String[] stockIdsArray = hm.get("stockIds_f".toUpperCase()).split(",");
			String[] partsCountArray = hm.get("partsCount_f".toUpperCase()).split(",");
			
			// 如果为修改动作则会有主表ID
			String overstockId = hm.get("overstockId_f".toUpperCase()) == null ? "" : hm.get("overstockId_f".toUpperCase());
			
			// 如果为修改动作，则会从前台传回来NO
			String overstockNo = hm.get("overstockNo_f".toUpperCase()) == null ? "" : hm.get("overstockNo_f".toUpperCase());
			
			User user = (User)action.atx.getSession().get(Globals.USER_KEY);
			List<String> args = new ArrayList<String>();
			
			// 第一步：写入主表，构建申请单主数据记录参数
			if(overstockNo.equals("")){
				overstockNo = action.dao.getOverstockNO(user); 	// 1.获取一个新的申请单单号
				args.add(overstockNo);
				args.add(DicConstant.JYJSQZT_01); 				// 2.申请单状态：JYJSQZT_01 = "306201" 未提交
				args.add(user.getPersonName()); 				// 3.申请人
				args.add(user.getOrgId()); 						// 4.申请人的渠道ID
				args.add(user.getOrgCode()); 					// 5.申请人的渠道Code
				args.add(user.getOrgId()); 						//   通过ORGID查询出name
				args.add(DicConstant.YXBS_01); 					// 6.表示数据有效
				args.add(user.getAccount()); 					// 7.数据创建者
				
				// 注：这里比较特殊，因为前台限制了申请单只能选择同一家渠道的配件，所以这里可以用一个stockId获取对应的渠道信息
				args.add(stockIdsArray[0]); 					// 8.根据一个配件存储ID，获取存储的经销商的渠道ID，渠道CODE，渠道名称. 
				saveStatus = action.dao.addOverstockApply(args.toArray());
				if(!saveStatus){
					new Exception("DealerOverstockPartApplyForDao.addOverstockApply():"+args.toArray().toString()+",保存失败");
				}
			}
			
			
			// 第二步：构建申请表子表配件信息
			if(!overstockId.equals("")){
				action.dao.deleteOverstockPartInfoByOverstockId(overstockId);
			}
			int i = 0;
			for(String stockId : stockIdsArray){
				args.clear();
				args.add(partsCountArray[i++]); 				// 1.添加配件申请个数
				args.add(user.getAccount());  					// 2.添加创建人ID
				args.add(overstockNo);		  					// 3.添加申请单单号,根据此单号可查出主表ID
				args.add(stockId);			  					// 4.添加配件存储表主键ID，根据此ID可以获取需要添加的配件信息：配件ID，配件名称，配件Code
				saveStatus = action.dao.addOverstockPartInfo(args.toArray());
				if(!saveStatus){
					new Exception("DealerOverstockPartApplyForDao.addOverstockPartInfo():"+args.toArray().toString()+",保存失败");
				}
			}
			
			// 第三步，更新申请单总金额
			saveStatus = action.dao.updateApplyAmountByNo(overstockNo);
			if(!saveStatus){
				new Exception("DealerOverstockPartApplyForDao.addOverstockPartInfo():"+args.toArray().toString()+",保存失败");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			action.logger.error(e);
			action.atx.setException(e);
		}
	}
	
	
	
}
