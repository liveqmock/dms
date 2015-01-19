/**
 * @throws Exception
 */
package com.org.dms.action.service.preauthMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.preauth.PreAuthCheckDao;
import com.org.dms.vo.service.SeBuPreAuthorCheckVO;
import com.org.dms.vo.service.SeBuPreAuthorVO;
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
 * 预授权审核ACTION
 * @author zts
 *
 */
public class PreAuthCheckAction {
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "PreAuthCheckAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PreAuthCheckDao dao = PreAuthCheckDao.getInstance(atx);
    
    /**
     * 预授权审核查询
     * @throws Exception
     */
    public void preAuthCheckSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.preAuthCheckSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    
    /**
     * 预授权审核通过
     * @throws Exception
     */
    public void preAuthCheckPass() throws Exception {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
        SeBuPreAuthorVO vo = new SeBuPreAuthorVO();
        SeBuPreAuthorCheckVO checkVo=new SeBuPreAuthorCheckVO();
        try
        {
        	HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String authorId=hm.get("AUTHOR_ID");
    		vo.setAuthorId(authorId);
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			
			//判断当前审核人是否是最后一个人，如果是最后一人，将审核通过，否则将转下个人
			QuerySet qs=dao.getAuthorRule(authorId);
			String preLevelCode="";
			String ruleLevelCode="";
			if(qs.getRowCount() > 0){
				preLevelCode =qs.getString(1,"PRE_LEVEL_CODE");
				ruleLevelCode =qs.getString(1,"RULE_LEVEL_CODE");
				
				String str = ruleLevelCode ; //预授权审核规则所有级别"01,02,03"
				String s1 =preLevelCode; //预授权里的审核级别 "01"
				String s2 = "";       
				int n = str.indexOf(s1) + s1.length(); //n 是当前s1的位置
				int m = str.indexOf(",", n+1) != -1 ? str.indexOf(",", n+1): str.length();
				if(n != str.length()){ //判断是否先等，如果不相等，继续审核，否则审核通过
					s2 = str.substring(n+1, m);
				}else{
					s2 = s1;
				}
				if(s2.equals(s1)){
					vo.setAuthorStatus(DicConstant.YSQZT_04);
				}else {
					vo.setAuthorStatus(DicConstant.YSQZT_02);
				}
				vo.setLevelCode(s2);
			}
			//预授权审核通过
            dao.updatePreAuth(vo);
            //审核记录表
            checkVo.setAuthorId(hm.get("AUTHOR_ID"));
            checkVo.setCheckUser(user.getAccount());
            checkVo.setCheckDate(Pub.getCurrentDate());
            checkVo.setCheckType(DicConstant.SHLX_01);//审核类型、人工审核
            checkVo.setCheckResult(DicConstant.SHJG_01);//审核结果 、审核通过
        	checkVo.setRemaks(hm.get("REMARKS"));
            checkVo.setCreateUser(user.getAccount());// 设置创建人
            checkVo.setCreateTime(Pub.getCurrentDate());// 创建时间
            checkVo.setOrgId(user.getOrgId());
            checkVo.setCompanyId(user.getCompanyId());
            dao.insertPreAuthCheck(checkVo);
            //返回更新结果和成功信息
            atx.setOutMsg("","审核通过.");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 预授权审核驳回
     * @throws Exception
     */
    public void preAuthCheckReject() throws Exception {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
        SeBuPreAuthorVO vo = new SeBuPreAuthorVO();
        SeBuPreAuthorCheckVO checkVo=new SeBuPreAuthorCheckVO();
        try
        {
        	HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
    		vo.setAuthorId(hm.get("AUTHOR_ID"));
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			vo.setAuthorStatus(DicConstant.YSQZT_03);
			vo.setReportDate(Pub.getCurrentDate());
			vo.setLevelCode("");//清空当前审核级别
			vo.setRuleId("");   //清空审核规则ID
			//预授权审核通过
             dao.updatePreAuth(vo);
            //审核记录表
            checkVo.setAuthorId(hm.get("AUTHOR_ID"));
            checkVo.setCheckUser(user.getAccount());
            checkVo.setCheckDate(Pub.getCurrentDate());
            checkVo.setCheckType(DicConstant.SHLX_01);//审核类型、人工审核
            checkVo.setCheckResult(DicConstant.SHJG_02);//审核结果 、审核通过
        	checkVo.setRemaks(hm.get("REMARKS"));
            checkVo.setCreateUser(user.getAccount());// 设置创建人
            checkVo.setCreateTime(Pub.getCurrentDate());// 创建时间
            checkVo.setOrgId(user.getOrgId());
            checkVo.setCompanyId(user.getCompanyId());
            dao.insertPreAuthCheck(checkVo);
            //返回更新结果和成功信息
            atx.setOutMsg("","审核驳回！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
}
