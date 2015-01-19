package com.org.dms.service.part.storageMng.overstockParts;

import com.org.dms.action.part.storageMng.overstockParts.DealerOverstockPartApplyAction;

/**
 * 
 * ClassName: OverstockPartStatus 
 * Function: 积压件状态接口
 * date: 2014年9月16日 下午10:24:59
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 */
public interface OverstockPartStatus {
	public void handle(DealerOverstockPartApplyAction action);
}
