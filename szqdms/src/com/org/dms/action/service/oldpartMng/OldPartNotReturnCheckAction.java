package com.org.dms.action.service.oldpartMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.oldpartMng.OldPartNotReturnCheckDao;
import com.org.dms.vo.service.SeBuReturnorderNotVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
/**
 * 旧件不回运审核ACTION
 * @author zts
 *
 */
public class OldPartNotReturnCheckAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "OldPartNotReturnCheckAction");
    private ActionContext atx = ActionContext.getContext();
    private OldPartNotReturnCheckDao dao = OldPartNotReturnCheckDao.getInstance(atx);
    
    /**
     * 查询
     * @throws Exception
     */
    public void oldPartNotReturnSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.oldPartNotReturnSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    /**
     * 查询旧件明细
     * @throws Exception
     */
    public void searchCheckParts() throws Exception{
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String notbackId = Pub.val(request, "nId");//不回运单ID
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.searchCheckParts(page,conditions,user,notbackId);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 旧件不回运审核通过
     * @throws Exception
     */
    public void oldPartNotReturnPass()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	HashMap<String, String> hm;
        hm = RequestUtil.getValues(request);
        String applyMonth=hm.get("APPLY_MONTH");
        String orgIds=hm.get("ORG_ID");
        try
        {
        	QuerySet qs = dao.queryNotbackIds(orgIds,applyMonth);
        	for(int i=0;i<qs.getRowCount();i++){
        		SeBuReturnorderNotVO vo = new SeBuReturnorderNotVO();
        		String notbackId = qs.getString(i+1, "NOTBACK_ID");
        		vo.setNotbackId(notbackId);
    			vo.setApplyStatus(DicConstant.BHYSQZT_02);//不回运状态通过
    			vo.setUpdateTime(Pub.getCurrentDate());
    			vo.setUpdateUser(user.getAccount());
    			dao.updateNotReturn(vo);
        	}
            atx.setOutMsg("","审核通过！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 旧件不回运审核驳回
     * @throws Exception
     */
    public void oldPartNotReturnReject()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	HashMap<String, String> hm;
    	hm = RequestUtil.getValues(request);
    	String applyMonth=hm.get("APPLY_MONTH");
    	String orgIds=hm.get("ORG_ID");
    	try
    	{
    		QuerySet qs = dao.queryNotbackIds(orgIds,applyMonth);
    		for(int i=0;i<qs.getRowCount();i++){
    			SeBuReturnorderNotVO vo = new SeBuReturnorderNotVO();
    			String notbackId = qs.getString(i+1, "NOTBACK_ID");
    			vo.setNotbackId(notbackId);
    			vo.setApplyStatus(DicConstant.BHYSQZT_03);//不回运状态驳回
    			vo.setUpdateTime(Pub.getCurrentDate());
    			vo.setUpdateUser(user.getAccount());
    			dao.updateNotReturn(vo);
    		}
    		atx.setOutMsg("","审核驳回！");
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
}
