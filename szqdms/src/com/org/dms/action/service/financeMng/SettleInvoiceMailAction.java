package com.org.dms.action.service.financeMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.financeMng.SettleInvoiceMailDao;
import com.org.dms.vo.service.SeBuClaimSettleVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
/**
 * 结算单邮寄ACTION
 * @author zts
 *
 */
public class SettleInvoiceMailAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "SettleInvoiceMailAction");
	private ActionContext atx = ActionContext.getContext();
	private SettleInvoiceMailDao dao = SettleInvoiceMailDao.getInstance(atx);


	/**
	 * 结算单邮寄查询
	 * @throws Exception
	 */
	public void settleInvoiceSearch()throws Exception{
		RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.settleInvoiceSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
	}
	
	/**
	 * 结算发票邮寄
	 * @throws Exception
	 */
	public void settleInvoiceMail()throws Exception{
		//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuClaimSettleVO vo=new SeBuClaimSettleVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			vo.setSettleStatus(DicConstant.JSZT_04);
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			vo.setOrgId(user.getOrgId());
			vo.setCompanyId(user.getCompanyId());
			dao.settleUpdate(vo);
			atx.setOutMsg("","邮寄成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
	}
}
