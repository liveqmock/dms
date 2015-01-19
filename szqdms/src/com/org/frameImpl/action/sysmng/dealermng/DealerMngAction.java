package com.org.frameImpl.action.sysmng.dealermng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.frameImpl.dao.sysmng.dealermng.DealerMngDao;
import com.org.frameImpl.vo.MainDealerVO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.framework.Globals;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.log.LogManager;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * 渠道管理类
 * @author andy
 *
 */
public class DealerMngAction
{
    private Logger logger = com.org.framework.log.log.getLogger(
            "DealerMngAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private DealerMngDao dao = DealerMngDao.getInstance(atx);
    
    /**
     * 更新
     * @throws Exception
     * @author andy.ten@tom.com 
     * @Time Jul 9, 2014 2:14:30 PM
     */
    public void update()
        throws Exception
    { 
    	RequestWrapper request = atx.getRequest();
        User user = (User) request.getSession().get(Globals.USER_KEY);
        MainDealerVO tempVO = new MainDealerVO();
        try
        {
            HashMap<String,String> hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			//设置更新人、更新时间
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
            dao.updateDealer(tempVO);
            
            atx.setOutMsg(tempVO.getRowXml(), "操作成功！");
            
            LogManager.writeUserLog("", "", Globals.OPERATION_TYPE_UPDATE,
                                    LogManager.RESULT_SUCCESS,
                                    "渠道信息变更：" + "["+tempVO.getDealerCode()+"]:"+tempVO.getDealerName(), user);
            
        } 
        catch (Exception e)
        {
            logger.error(e);
            atx.setException(e);
        }
    }
    
   
    /**
     * 机构管理：查询机构信息
     * @throws Exception
     */
    public void search()
    		throws Exception
	{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) request.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.search(page, user, conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
            atx.setException(e);
		}
	}
	/**
	 * 渠道管理导出方法。
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
    public void oemDownload()throws Exception{
		//获取封装后的request对象
		RequestWrapper request = atx.getRequest();
		ResponseWrapper response= atx.getResponse();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();
			hBean.setName("DEALER_CODE");
			hBean.setTitle("渠道商代码");
			header.add(0,hBean);
			hBean = new HeaderBean();
			hBean.setName("DEALER_NAME");
			hBean.setTitle("渠道商名称");
			header.add(1,hBean);
			hBean = new HeaderBean();
			hBean.setName("DUTY_TEL");
			hBean.setTitle("电话");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("FAX");
			hBean.setTitle("传真");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("ADDRESS");
			hBean.setTitle("地址");
			header.add(4,hBean);
			hBean = new HeaderBean();
			hBean.setName("DEALER_STAR");
			hBean.setTitle("星级");
			header.add(5,hBean);
			hBean = new HeaderBean();
			hBean.setName("STATION_NAME");
			hBean.setTitle("站长姓名");
			header.add(6,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("STATION_TEL");
			hBean.setTitle("站长电话");
			header.add(7,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("CODE");
			hBean.setTitle("所属办事处代码");
			header.add(8,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("ONAME");
			hBean.setTitle("所属办事处名称");
			header.add(9,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("SNAME");
			hBean.setTitle("服务商简称");
			header.add(10,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("IF_FST");
			hBean.setTitle("是否法士特站");
			header.add(11,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("IF_WC");
			hBean.setTitle("是否潍柴站");
			header.add(12,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("IF_KMS");
			hBean.setTitle("是否康明斯站");
			header.add(13,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("OPEN_BANK");
			hBean.setTitle("开会银行");
			header.add(14,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("BANK_ACCOUNT");
			hBean.setTitle("开户账号");
			header.add(15,hBean);
			QuerySet qs = dao.oemDownload(conditions);
			ExportManager.exportFile(response.getHttpResponse(), "渠道信息导出", header, qs);
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
}
