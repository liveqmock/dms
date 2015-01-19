package com.org.dms.action.service.oldpartMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.oldpartMng.OldPartCheckDao;
import com.org.dms.vo.service.SeBuReturnOrderVO;
import com.org.dms.vo.service.SeBuReturnorderCheckVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
/**
 * 旧件回运审核ACTION
 * @author zts
 *
 */
public class OldPartCheckAction {
	 private Logger logger = com.org.framework.log.log.getLogger(
		        "OldPartCheckAction");
     private ActionContext atx = ActionContext.getContext();
     private OldPartCheckDao dao = OldPartCheckDao.getInstance(atx);
		    
     /**
      * 回运单审核查询
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
      * 旧件审核通过
      * @throws Exception
      */
     public void oldPartCheckPass()throws Exception{
    	 //获取封装后的request对象
     	 RequestWrapper request = atx.getRequest();
     	 User user = (User) atx.getSession().get(Globals.USER_KEY);
	     SeBuReturnOrderVO vo = new SeBuReturnOrderVO();
	     SeBuReturnorderCheckVO checkVo=new SeBuReturnorderCheckVO();
         try
         {
        	 HashMap<String,String> hm;
 			 hm = RequestUtil.getValues(request);
     		 vo.setOrderId(hm.get("ORDER_ID"));
 			 vo.setUpdateUser(user.getAccount());
 			 vo.setUpdateTime(Pub.getCurrentDate());
 			 vo.setOrderStatus(DicConstant.HYDZT_03);
 			 //旧件回运审核通过
             dao.updateOldPart(vo);
             //审核记录表
             checkVo.setOrderId(hm.get("ORDER_ID"));
             checkVo.setCheckDate(Pub.getCurrentDate());
         	 checkVo.setCheckOpinion(hm.get("CHECK_OPINION"));
             checkVo.setCreateUser(user.getAccount());// 设置创建人
             checkVo.setCreateTime(Pub.getCurrentDate());// 创建时间
             checkVo.setOrgId(user.getOrgId());
             checkVo.setCompanyId(user.getCompanyId());
             dao.insertOldPartCheck(checkVo);
             //返回更新结果和成功信息
             atx.setOutMsg("","审核通过！");
         }
         catch (Exception e)
         {
         	atx.setException(e);
             logger.error(e);
         }
     }
     /**
      * 旧件审核驳回
      * @throws Exception
      */
     public void oldPartCheckReject() throws Exception {
     	 //获取封装后的request对象
     	 RequestWrapper request = atx.getRequest();
     	 User user = (User) atx.getSession().get(Globals.USER_KEY);
     	 SeBuReturnOrderVO vo = new SeBuReturnOrderVO();
	     SeBuReturnorderCheckVO checkVo=new SeBuReturnorderCheckVO();
         try
         {
         	HashMap<String,String> hm;
 			hm = RequestUtil.getValues(request);
 			vo.setOrderId(hm.get("ORDER_ID"));
 			vo.setUpdateUser(user.getAccount());
 			vo.setUpdateTime(Pub.getCurrentDate());
 			vo.setOrderStatus(DicConstant.HYDZT_04);
 			//旧件回运审核驳回
            dao.updateOldPart(vo);
            //审核记录表
            checkVo.setOrderId(hm.get("ORDER_ID"));
            checkVo.setCheckDate(Pub.getCurrentDate());
        	checkVo.setCheckOpinion(hm.get("CHECK_OPINION"));
            checkVo.setCreateUser(user.getAccount());// 设置创建人
            checkVo.setCreateTime(Pub.getCurrentDate());// 创建时间
            checkVo.setOrgId(user.getOrgId());
            checkVo.setCompanyId(user.getCompanyId());
            dao.insertOldPartCheck(checkVo);
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
