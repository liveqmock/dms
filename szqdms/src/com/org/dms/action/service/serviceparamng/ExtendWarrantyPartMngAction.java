package com.org.dms.action.service.serviceparamng;

import java.util.HashMap;

import com.org.dms.dao.service.serviceparamng.ExtendWarrantyPartMngDao;
import com.org.dms.vo.service.SeBaExtendWarrantyPartVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * @Title: szqdms
 * @description: 延保策略与配件关系管理方法
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年9月1日 
 */
public class ExtendWarrantyPartMngAction
{
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private ExtendWarrantyPartMngDao dao = ExtendWarrantyPartMngDao.getInstance(atx);

    /**
     * 批量新增延保策略与配件关系
     * @throws Exception
     * @Author fanpeng 2014-09-01
     */
    public void batchInsert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
        String creatUser = user.getPersonName();
        
        try
        {
        	HashMap<String,String> hm;
 			hm = RequestUtil.getValues(request);
 			String warrantyId = hm.get("WARRANTY_ID");
 			String warrantyCode = hm.get("WARRANTY_CODE");
 			String warrantyName = hm.get("WARRANTY_NAME");
 	 			
 			String  partIds= hm.get("PART_IDS");
 			String  warrantyMonths= hm.get("WARRANTY_MONTHS");
 			String[] partId = partIds.split(",");
            String[] warrantyMonth = warrantyMonths.split(",");
            for(int i=0;i<partId.length ;i++){
            	String part=partId[i];
            	String month=warrantyMonth[i];
            	dao.batchInsertExtendWarrantyPart(part, warrantyId, warrantyCode, warrantyName, month, creatUser);
            }
            atx.setOutMsg("","延保策略与配件关系新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }

    /**
     * 单个修改延保策略与配件关系
     * @throws Exception
     * @Author fanpeng 2014-09-01
     */
    public void update() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
        SeBaExtendWarrantyPartVO tempVO = new SeBaExtendWarrantyPartVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			
			tempVO.setUpdateTime(Pub.getCurrentDate());
			tempVO.setUpdateUser(user.getPersonName());
			
            dao.updateExtendWarrantyPart(tempVO);
            atx.setOutMsg(tempVO.getRowXml(),"延保月份修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }

    /**
     * 批量删除延保策略与配件关系
     * @throws Exception
     * @Author fanpeng 2014-09-01
     */
    public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        String relationIds = Pub.val(request, "relationIds");
        try
        {
            dao.batchDeleteExtendWarrantyPart(relationIds);
            atx.setOutMsg("","延保策略与配件关系删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }

    /**
     * 查询延保策略与配件关系
     * @throws Exception
     * Author fanpeng 2014-09-01
     */
    public void search() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.search(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}
    
    /**
     * 查询配件
     * @throws Exception
     * Author fanpeng 2014-09-01
     */
    public void searchPart() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			BaseResultSet bs = dao.searchPart(page, conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
    }
    
    
}