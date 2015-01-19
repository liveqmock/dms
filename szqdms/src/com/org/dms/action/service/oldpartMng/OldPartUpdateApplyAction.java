package com.org.dms.action.service.oldpartMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.oldpartMng.OldPartUpdateApplyDao;
import com.org.dms.vo.service.SeBuReturnorderDetailVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
/**
 * 旧件修改申请
 * @author zts
 *
 */
public class OldPartUpdateApplyAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "OldPartUpdateApplyAction");
    private ActionContext atx = ActionContext.getContext();
    private OldPartUpdateApplyDao dao = OldPartUpdateApplyDao.getInstance(atx);
    
    /**
     * 渠道商 待审旧件查询
     * @throws Exception
     */
    public void oldPartSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.oldPartSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }	
    
    /**
     * 申请
     * @throws Exception
     */
    public void claimOldPartUpdate()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
      //  User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuReturnorderDetailVO vo = new SeBuReturnorderDetailVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			vo.setUpdateStatus(DicConstant.JJXGSQZT_01);
			dao.udpateDetail(vo);
			atx.setOutMsg(vo.getRowXml(),"申请成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    
    /**
     * 车厂端 旧件修改查询
     * @throws Exception
     */
    public void oldPartOemSearch()throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.oldPartOemSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    /**
     * 车厂端 旧件修改保存
     * @throws Exception
     */
    public void oldPartOemUpdate()throws Exception{
    	RequestWrapper request = atx.getRequest();
        try
        {
        	SeBuReturnorderDetailVO vo = new SeBuReturnorderDetailVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			vo.setUpdateStatus(DicConstant.JJXGSQZT_01);
			dao.udpateDetail(vo);
			atx.setOutMsg(vo.getRowXml(),"保存成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 车厂端 旧件修改审核 查询
     * @throws Exception
     */
    public void oldPartCheckSearch()throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.oldPartCheckSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    
    /**
     * 车厂端 旧件修改审核
     * @throws Exception
     */
    public void oldPartUpdateCheck()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	String status=Pub.val(request, "status");
        try
        {
        	SeBuReturnorderDetailVO vo = new SeBuReturnorderDetailVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String detailId=hm.get("DETAIL_ID");
			String opinion=hm.get("UPDATE_OPINION");
			vo.setDetailId(detailId);
			vo.setUpdateOpinion(opinion);
			vo.setUpdateStatus(status);
			dao.udpateDetail(vo);
			if(status.equals(DicConstant.JJXGSQZT_02.toString())){
            	atx.setOutMsg("1","审核通过！");
            }else{
            	atx.setOutMsg("1","审核驳回！");
            }
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 渠道商 旧件修改查询
     * @throws Exception
     */
    public void oldPartUpdateSearch()throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.oldPartUpdateSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    
    /**
     * 车厂端  旧件修改查询
     * @throws Exception
     */
    public  void oldPartUpdateOemSearch()throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.oldPartUpdateOemSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
}
