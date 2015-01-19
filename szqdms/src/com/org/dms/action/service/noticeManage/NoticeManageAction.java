package com.org.dms.action.service.noticeManage;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.noticeManage.NoticeManageDao;
import com.org.dms.vo.service.MainBulletinPermissionVO;
import com.org.dms.vo.service.MainBulletinRangeVO;
import com.org.dms.vo.service.MainBulletinTypeVO;
import com.org.dms.vo.service.MainBulletinVO;
import com.org.framework.Globals;
import com.org.framework.alertmsg.AlertInfoVO;
import com.org.framework.alertmsg.AlertManager;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 通知通告action
 */
public class NoticeManageAction
{
    private Logger logger = com.org.framework.log.log.getLogger("NoticeManageAction");
    private ActionContext atx = ActionContext.getContext();
    private NoticeManageDao dao = NoticeManageDao.getInstance(atx);

    /**
     * @title: noticeManageSearch
     * @description: TODO(查询通知通告类别)
     * @date:二〇一四年八月八日 17:28:47
     * @throws Exception    设定文件
     */
    public void noticeManageSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.noticeManageSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * @title: noticeSignSearch
     * @description: TODO(查询通知通告类别)
     * @date:2014年9月1日
     * @throws Exception    设定文件
     */
    public void noticeSignSearch() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String userId= user.getUserId();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.noticeSignSearch(page,user,conditions,userId);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * @title: noticeSignSearch
     * @description: TODO(查询通知通告类别)
     * @date:2014年9月1日
     * @throws Exception    设定文件
     */
    public void noticeSignSearch1() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String userId= user.getUserId();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.noticeSignSearch1(page,user,conditions,userId);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * @title: noticeSignedSearch
     * @description: TODO(查询通知通告签收情况)
     * @date:2014年9月1日
     * @throws Exception    设定文件
     */
    public void noticeSignedSearch() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String userId= user.getUserId();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.noticeSignedSearch(page,user,conditions,userId);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * @title: noticeSearch
     * @description: TODO(查询通知通告)
     * @date:2014年9月1日
     * @throws Exception    设定文件
     */
    public void noticeSearch() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String userId= user.getUserId();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.noticeSearch(page,user,conditions,userId);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    //已添加渠道商查询
    public void noticeRangeSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String bulletinId=Pub.val(request, "bulletinId");
			BaseResultSet bs = dao.noticeRangeSearch(page,user,conditions,bulletinId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    //未添加渠道商查询
    public void searchOrgDealrs() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String bulletinId=Pub.val(request, "bulletinId");
			String bulletin_range=Pub.val(request, "bulletin_range");
			String bscCode=Pub.val(request, "bscCode");
			BaseResultSet bs = dao.searchOrgDealrs(page,user,conditions,bulletinId,bulletin_range,bscCode);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * @title: noticeContentSearch
     * @description: TODO(查询通知通告类别)
     * @date:2014年8月11日 11:01:05
     */
    public void noticeContentSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String userId= user.getUserId();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.noticeContentSearch(page,user,conditions,userId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * @title: noticeArchiveSearch
     * @description: TODO(查询通知通告归档)
     * @date:2014年8月11日 11:01:05
     */
    public void noticeArchiveSearch() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String userId= user.getUserId();
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.noticeArchiveSearch(page,user,conditions,userId);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    //新增通知通告类别
    public void insert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	MainBulletinTypeVO vo = new MainBulletinTypeVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			vo.setCompanyId(user.getCompanyId());
			vo.setStatus(DicConstant.YXBS_01);
			vo.setOemCompanyId(user.getOemCompanyId());
			dao.insert(vo);
			atx.setOutMsg(vo.getRowXml(),"通知通告新增成功！");
			String typeId=vo.getTypeId();
			String userId=user.getUserId();
			MainBulletinPermissionVO vo1 = new MainBulletinPermissionVO();
            vo1.setUserId(userId);
            vo1.setTypeId(typeId);
            vo1.setOrgId(user.getOrgId());
            vo1.setOemCompanyId(user.getOemCompanyId());
            vo1.setCompanyId(user.getCompanyId());
            vo1.setCreateTime(Pub.getCurrentDate());
            vo1.setCreateUser(user.getAccount());
            dao.insertUser(vo1);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    //新增通知通告内容
    public void insertContent() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		MainBulletinVO vo = new MainBulletinVO();
    		HashMap<String,String> hm;
    		hm = RequestUtil.getValues(request);
    		vo.setValue(hm);
    		vo.setCreateUser(user.getAccount());
    		vo.setBulletinRange(String.valueOf(hm.get("BULLETIN_RANGE")));
    		vo.setCreateTime(Pub.getCurrentDate());
    		vo.setCompanyId(user.getCompanyId());
    		vo.setBulletinStatus(DicConstant.TGFBZT_01);
    		vo.setStatus(DicConstant.YXBS_01);
    		vo.setOemCompanyId(user.getOemCompanyId());
    		dao.insert(vo);
    		atx.setOutMsg(vo.getRowXml(),"通知通告内容新增成功！");
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    public void insertDealers() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String CreateUser=user.getAccount();
        try
        {
			String bulletinId=Pub.val(request, "bulletinId");
			String mxids=Pub.val(request, "mxids");
			dao.insertOrgs(CreateUser,mxids,bulletinId);
			atx.setOutMsg("服务活动渠道商新增成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 新增全部
     * @throws Exception
     */
    public void insertAllDealers() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String CreateUser=user.getAccount();
    	try
    	{
    		String bulletinId=Pub.val(request, "bulletinId");
    		String bulletin_range=Pub.val(request, "bulletin_range");
    		dao.insertOrgs1(CreateUser,bulletin_range,bulletinId);
    		atx.setOutMsg("服务活动渠道商新增成功！");
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 删除渠道商
     * @throws Exception      
     */
    public void deleteDealer() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
       
        try
        {
        	String mxids=Pub.val(request, "mxid");
			 dao.deleteDealer(mxids);
			 atx.setOutMsg("","服务活动渠道商删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 查询附件
     * @throws Exception
     */
    public void fileSearch() throws Exception {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
		PageManager page = new PageManager();
		RequestUtil.getConditionsWhere(request,page);
		try
		{
			String bulletinId=request.getParamValue("bulletinId");
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.fileSearch(page,user,bulletinId);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    /**
     * 删除通知通告类别
     * @throws Exception
     */
    public void noticeManagedelete() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        MainBulletinTypeVO vo = new MainBulletinTypeVO();
        String typeId = Pub.val(request, "typeId");
        try
        {
        	vo.setTypeId(typeId);
        	vo.setStatus(DicConstant.YXBS_02);
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
            dao.update(vo);
            atx.setOutMsg(vo.getRowXml(),"通知通告删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 删除通知通告内容
     * @throws Exception
     */
    public void noticePublishdelete() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	String bulletinId = Pub.val(request, "bulletinId");
    	try
    	{
    		dao.deleteBulletin(bulletinId);
    		atx.setOutMsg("","通知通告删除成功！");
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 通告类别修改保存
     * @throws Exception
     */
    public void update() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	MainBulletinTypeVO vo = new MainBulletinTypeVO();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			vo.setUpdateUser(user.getAccount());// 设置更新人
			vo.setUpdateTime(Pub.getCurrentDate());// 更新时间
			vo.setOrgId(user.getOrgId());
			vo.setOemCompanyId(user.getOemCompanyId());
			vo.setCompanyId(user.getCompanyId());
			//通过dao，执行插入
			dao.update(vo);
			//返回插入结果和成功信息
			//voExt.setAuthorId(vo.getAuthorId());
			atx.setOutMsg(vo.getRowXml(),"通告类别修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 修改通告内容保存
     * @throws Exception
     */
    public void updateContent() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		MainBulletinVO vo = new MainBulletinVO();
    		HashMap<String,String> hm;
    		//将request流转换为hashmap结构体
    		hm = RequestUtil.getValues(request);
    		//将hashmap映射到vo对象中,完成匹配赋值
    		vo.setValue(hm);
    		vo.setBulletinRange(String.valueOf(hm.get("BULLETIN_RANGE")));
    		vo.setUpdateUser(user.getAccount());// 设置更新人
    		vo.setUpdateTime(Pub.getCurrentDate());// 更新时间
    		vo.setOrgId(user.getOrgId());
    		vo.setOemCompanyId(user.getOemCompanyId());
    		vo.setCompanyId(user.getCompanyId());
    		//通过dao，执行插入
    		dao.updateContent(vo);
    		//返回插入结果和成功信息
    		//voExt.setAuthorId(vo.getAuthorId());
    		atx.setOutMsg(vo.getRowXml(),"通告内容修改成功！");
    	}
    	catch (Exception e)
    	{
    		//设置失败异常处理
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * @title: searchUsers
     * @description: TODO(查询未添加的用户)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther sunxuedong
     */
    public void searchUsers() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{	
			String typeId=Pub.val(request, "typeId");
			BaseResultSet bs = dao.searchUsers(page,user,conditions,typeId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * @title: searchUsers
     * @description: TODO(查询已添加的用户)
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther sunxuedong
     */
    public void searchNoticeUser() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{	
    		String typeId=Pub.val(request, "typeId");
    		BaseResultSet bs = dao.searchNoticeUser(page,user,conditions,typeId);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 新增用户
     *
     * @throws Exception
     */
    public void insertUsers() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String userId=hm.get("USER_ID");
            String typeId=Pub.val(request, "typeId");
            String[] userIdArr = userId.split(",");
            for (int i = 0; i < userIdArr.length; i++) {
            	MainBulletinPermissionVO vo = new MainBulletinPermissionVO();
                vo.setUserId(userIdArr[i]);
                vo.setTypeId(typeId);
                vo.setOrgId(user.getOrgId());
                vo.setOemCompanyId(user.getOemCompanyId());
                vo.setCompanyId(user.getCompanyId());
                vo.setCreateTime(Pub.getCurrentDate());
                vo.setCreateUser(user.getAccount());
                dao.insertUser(vo);
            }
            //返回插入结果和成功信息
            atx.setOutMsg("", "操作人员新增成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 删除通知通告用户
     * @throws Exception
     */
    public void deleteNoticeUser() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
       
        try
        {
        	String mxids=Pub.val(request, "mxids");
			 dao.deleteNoticeUserByMxids(mxids);
			 atx.setOutMsg("","通知通告用户删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 发布通知通告用户
     * @throws Exception
     */
    public void noticeManagePublish() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	MainBulletinVO vo = new MainBulletinVO();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String userId = user.getUserId();
    	String orgId = user.getOrgId(); 
    	String orgName=user.getOrgDept().getSname();
    	try
    	{
    		String bulletinId=Pub.val(request, "bulletinId");
    		vo.setBulletinId(bulletinId);
    		vo.setOrgId(orgId);
    		vo.setUserId(userId);
    		vo.setSendDate(Pub.getCurrentDate());//发布时间
    		vo.setBulletinStatus(DicConstant.TGFBZT_02);
    		dao.updateBulletinStatus(vo);
    		atx.setOutMsg("","通知通告用户发布成功！");
    		
    		QuerySet qs=dao.getRange(bulletinId);
            if(qs.getRowCount() > 0){
            	for (int i=0;i<qs.getRowCount() ;i++){
	            	String oId=qs.getString(i+1,"ORG_ID");
	            	String typeName=qs.getString(i+1,"TYPE_NAME");
	            	String bId=qs.getString(i+1,"BULLETIN_ID");
	            	QuerySet qs1 =dao.queryUserId(oId);
	            		if(qs1.getRowCount()>0){
	            			AlertInfoVO infoVO=new AlertInfoVO();
	    	            	infoVO.setAlertTitle(orgName+"有一条通知通告！");//标题
	    	            	infoVO.setDesr("通知通告类别:"+typeName);//提醒内容
	    	            	infoVO.setOverrun("0");//提醒周期
	    	            	infoVO.setAlertOrg(oId);
	    	            	infoVO.setBusType(DicConstant.YWLX_02);//业务类型（配件、售后）
	    	            	infoVO.setBusPk(bId);//业务主键
	    	            	infoVO.setAlertType(DicConstant.TXLX_01);//提醒类型
	    	            	infoVO.setCreateUser(user.getAccount());//发送人（当前登录人）
	    	            	infoVO.setCreateOrgid(user.getOrgId());//发送部门（当前登录部门）
	    		            AlertManager.alertInsert(atx.getDBFactory(),infoVO);
	            		}
	            	}
	            }
	    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 删除附件
     * @throws Exception
     */
    public void attaDelete() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        String fjid = Pub.val(request, "fjid");
        try
        {
        	//删除预授权项目
            dao.preAuthAttaDelete(fjid);
            //返回更新结果和成功信息
            atx.setOutMsg("","删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     *	通告签收
     * @throws Exception
     */
    public void noticeSign() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		MainBulletinRangeVO vo = new MainBulletinRangeVO();
    		String rangeId=Pub.val(request, "rangeId");
    		vo.setRangeId(rangeId);
    		vo.setStatus(DicConstant.TGQSZT_02);
    		vo.setSignUserId(user.getUserId());
    		vo.setSignDate(Pub.getCurrentDate());
    		vo.setUpdateBy(user.getAccount());
    		vo.setUpdateDate(Pub.getCurrentDate());
    		vo.setOrgId(user.getOrgId());
    		dao.updateStatus(vo);
    		atx.setOutMsg("1","通告签收成功！");
    	}
    	catch (Exception e)
    	{
    		//设置失败异常处理
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     *	通告归档
     * @throws Exception
     */
    public void noticeArchive() throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		MainBulletinVO vo = new MainBulletinVO();
    		String bulletinId=Pub.val(request, "bulletinId");
    		vo.setBulletinId(bulletinId);
    		vo.setBulletinStatus(DicConstant.TGFBZT_03);
    		vo.setUpdateTime(Pub.getCurrentDate());
    		vo.setUpdateUser(user.getAccount());
    		dao.updateBulletinStatus(vo);
    		atx.setOutMsg("1","通告归档成功！");
    	}
    	catch (Exception e)
    	{
    		//设置失败异常处理
    		atx.setException(e);
    		logger.error(e);
    	}
    }
}