package com.org.dms.action.part.storageMng.logisticInfo;

import java.util.*;

import com.org.dms.dao.part.storageMng.logisticInfo.TransCostsBalanceDao;
import com.org.dms.vo.part.PtBaDriverVO;
import com.org.dms.vo.part.PtBuTransCostsBalanceVO;
import com.org.dms.common.DicConstant;

import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import org.apache.log4j.Logger;

import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * (配件发运单)运费调整结算action
 */
public class TransCostsBalanceAction
{
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "TransCostsBalanceAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private TransCostsBalanceDao dao = TransCostsBalanceDao.getInstance(atx);

    /**
     * (配件发运单)新增运费调整结算
     * @throws Exception
     * @Author suoxiuli 2014-08-22
     */
    public void transSettle() throws Exception
    {
        RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String actulCosts = Pub.val(request, "actulCosts");
        String remarks = Pub.val(request, "remarks");
        try
        {
        	PtBuTransCostsBalanceVO vo = new PtBuTransCostsBalanceVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
        	//1、发运单的发运状态调整为已结算
			String shipId = hm.get("SHIP_ID");
			
        	//2、往运费调整结算表里面添加数据
			vo.setValue(hm);
			vo.setActulCosts(actulCosts);
			vo.setBalanceStatus(DicConstant.YSJSZT_02);
			vo.setBalanceTime(Pub.getCurrentDate());
			vo.setRemarks(remarks);
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			vo.setStatus(DicConstant.YXBS_01);
			
			dao.insertTransCostsBala(vo, shipId);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"发运单结算成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * (配件发运单)运费调整结算查询
     * @throws Exception
     * Author suoxiuli 2014-08-22
     */
    public void search() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.search(page,conditions, user.getAccount());
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
}