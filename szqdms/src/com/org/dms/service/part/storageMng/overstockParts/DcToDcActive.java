package com.org.dms.service.part.storageMng.overstockParts;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.HashMap;

import com.org.framework.Globals;
import com.org.framework.common.DBFactory;
import com.org.framework.common.User;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 
 * ClassName: DcToDcActive 
 * Function: DistributionCenter To DistributionCenter
 * 配送站 申请 配送站的积压件
 * date: 2014年9月22日 下午4:39:01
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 
 */
public class DcToDcActive implements OverstockStrategyActive {
	private DBFactory factory;
	
	public void setFactory(DBFactory factory) {
		this.factory = factory;
	}

	public static final OverstockStrategyActive getInstance(ActionContext ac){
		DcToDcActive active = new DcToDcActive();
		active.setFactory(ac.getDBFactory());
		return active;
	}


	@Override
	/**
	 * 此逻辑用于配送中心申请配送中心的积压件
	 * 退货部分：被申请方维修站退库，被申请方对应配送站入库
	 * 订单部分：申请方维修站提单
	 * 库存部分：车厂出库->最后申请方入库
	 */
	public void active(ActionContext ac) throws Exception {
		
		RequestWrapper requestWrapper = ac.getRequest();
		HashMap<String,String> hm = RequestUtil.getValues(requestWrapper);	// 转换前台提交的表单JSON为Map
		String overstockId = hm.get("overstockId_f".toUpperCase());
		User user = (User) ac.getSession().get(Globals.USER_KEY);
        CallableStatement proc = null;
        try {
			proc = factory.getConnection().prepareCall("{call P_DC_TO_DC(?,?)}");
			proc.setString(1, user.getAccount());
			proc.setString(2, overstockId);
			proc.execute();
		} catch (SQLException e) {
			throw e;
		}
	}

}
