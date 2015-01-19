package com.org.dms.action.service.claimmng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.claimmng.CheckUserDao;
import com.org.dms.vo.service.SeBaCheckUserVO;
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
 * 审核员维护
 * @author zts
 *
 */
public class CheckUserAction {
	private Logger logger = com.org.framework.log.log.getLogger("CheckUserAction");
	private ActionContext atx = ActionContext.getContext();
	private CheckUserDao dao = CheckUserDao.getInstance(atx);
	 
	/**
	 * 审核员查询
	 * @throws Exception
	 */
	public void checkUserSearch()throws Exception {
		RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.checkUserSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	
	/**
	 * 新增审核查询
	 * @throws Exception
	 */
	public void searchUser()throws Exception{
		RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchUser(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	} 
	
	/**
	 * 新增审核员
	 * @throws Exception
	 */
	public void checkUserSave()throws Exception{
		RequestWrapper request = atx.getRequest();
        try {
        	QuerySet qs=dao.maxSeqNo();
        	int maxSeqNo=0;
        	if(qs.getRowCount()> 0){
        		maxSeqNo=Integer.parseInt(qs.getString(1, 1));
        	}
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            String accounts = hm.get("ACCOUNT");//审核员帐号
            String userNames = hm.get("USER_NAME");//审核员名称
            String []account=accounts.split(",");
            String []userName=userNames.split(",");
            for(int i=0;i< account.length;i++){
            	SeBaCheckUserVO vo=new SeBaCheckUserVO();
            	vo.setSeqNo(String.valueOf((maxSeqNo+1)));
            	vo.setUserAccount(account[i]);
            	vo.setUserName(userName[i]);
            	vo.setIfDistrib(DicConstant.SF_01);
            	vo.setUserType(DicConstant.CLYHLX_01);//维护民用审核员
            	dao.insertUser(vo);
            	maxSeqNo++;
            }
            atx.setOutMsg("", "保存成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
	}
	
	/**
	 * 删除
	 * @throws Exception
	 */
	public void deleteCheckUser()throws Exception{
		//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        String cuId = Pub.val(request, "cuId");
        try
        {
            dao.userDelete(cuId);
            atx.setOutMsg("","删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
	}
	
	/**
	 * 修改审核员审核分配索赔单
	 * @throws Exception
	 */
	public void checkUserUpdate()throws Exception{
		RequestWrapper request = atx.getRequest();
        try {
        	SeBaCheckUserVO vo=new SeBaCheckUserVO();
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            vo.setValue(hm);
            dao.updateCheckUser(vo);
            vo.bindFieldToDic("IF_DISTRIB","SF");
            atx.setOutMsg(vo.getRowXml(),"保存成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
	}
	
	/**
	 * 审核数量
	 * @throws Exception
	 */
	public void checkCountSearch()throws Exception{
		RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.checkCountSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
}
