package com.org.dms.service.part.storageMng.overstockParts;

import com.org.dms.common.DicConstant;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: OverstockStrategyFactroy 
 * Function: 创建OverstockStrategyActive策略的工厂
 * date: 2014年9月22日 下午4:43:39
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 */
public class OverstockStrategyFactroy {
	
	/**
	 * 
	 * create:根据OrgType区分获取策略实现类
	 * @author fuxiao
	 * Date:2014年9月22日下午5:02:33
	 * @param inOrgType
	 * @param outOrgType
	 * @return
	 
	 */
	public static final OverstockStrategyActive create(String inOrgType,String outOrgType, ActionContext ac){
		
		OverstockStrategyActive active = null;
		// 如果参数为空，或者是空字符串，则直接返回Null
		if((inOrgType == null && outOrgType == null) 
				|| (inOrgType == null || "".equals(inOrgType))
				|| (outOrgType == null || "".equals(outOrgType))){
			return null;
		}
		// ZZLB_09 = "200005"; 配送中心 : DistributionCenter DC
		// ZZLB_10 = "200006"; 配件经销商: ServiceStation SS
		// ZZLB_11 = "200007"; 服务商: ServiceStation SS
		if(inOrgType.equals(DicConstant.ZZLB_09) && outOrgType.equals(DicConstant.ZZLB_09)){																								// DC_TO_DC
			active = DcToDcActive.getInstance(ac);
		} else if(inOrgType.equals(DicConstant.ZZLB_09) && (outOrgType.equals(DicConstant.ZZLB_10) || outOrgType.equals(DicConstant.ZZLB_11))){												// DC_TO_SS
			active = DcToSsActive.getInstance(ac);
		} else if((inOrgType.equals(DicConstant.ZZLB_10) || inOrgType.equals(DicConstant.ZZLB_11)) && outOrgType.equals(DicConstant.ZZLB_09)){												// SS_TO_DC
			active = SsToDcActive.getInstance(ac);
		} else if((inOrgType.equals(DicConstant.ZZLB_10) || inOrgType.equals(DicConstant.ZZLB_11)) && (outOrgType.equals(DicConstant.ZZLB_10) || outOrgType.equals(DicConstant.ZZLB_11))){	// SS_TO_SS 
			active = SsToSsActive.getInstance(ac);
		} else{
			active = null;
		}
		return active;
	}
}
