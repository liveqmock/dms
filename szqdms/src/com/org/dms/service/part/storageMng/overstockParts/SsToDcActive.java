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
 * ClassName: SsToDcActive 
 * Function: Service Statiion To Distribution Center
 * 服务站 申请 配送站积压件
 * date: 2014年9月22日 下午4:41:13
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 */
public class SsToDcActive implements OverstockStrategyActive {
	private DBFactory factory;
	
	public void setFactory(DBFactory factory) {
		this.factory = factory;
	}

	public static final OverstockStrategyActive getInstance(ActionContext ac){
		SsToDcActive active = new SsToDcActive();
		active.setFactory(ac.getDBFactory());
		return active;
	}
	
	@Override
	public void active(ActionContext ac) throws Exception {
		RequestWrapper requestWrapper = ac.getRequest();
		HashMap<String,String> hm = RequestUtil.getValues(requestWrapper);	// 转换前台提交的表单JSON为Map
		String overstockId = hm.get("overstockId_f".toUpperCase());
		User user = (User) ac.getSession().get(Globals.USER_KEY);
        CallableStatement proc = null;
        try {
			proc = factory.getConnection().prepareCall("{call P_SS_TO_DC(?,?)}");
			proc.setString(1, user.getAccount());
			proc.setString(2, overstockId);
			proc.execute();
		} catch (SQLException e) {
			throw e;
		}
	}

}
