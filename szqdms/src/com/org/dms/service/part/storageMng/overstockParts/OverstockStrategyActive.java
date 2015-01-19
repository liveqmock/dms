package com.org.dms.service.part.storageMng.overstockParts;

import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: OverstockStrategyActive 
 * Function: 积压件审核策略接口
 * date: 2014年9月22日 下午4:27:27
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 */
public interface OverstockStrategyActive {
	
	/**
	 * 
	 * active:共4种情况，服务站：ServiceStation(简写：SS) 配送中心：DistributionCenter(简写：DC)
	 * SS to SS,SS to DC,DC to SS,DC to DC
	 * 四中情况分别不同的动作,所以实现了四个接口
	 * @author fuxiao
	 * Date:2014年9月22日下午4:31:19
	 */
	public void active(ActionContext ac) throws Exception;
}
