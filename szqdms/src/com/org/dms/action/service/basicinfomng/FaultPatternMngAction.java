package com.org.dms.action.service.basicinfomng;

import java.util.*;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.basicinfomng.FaultPatternMngDao;
import com.org.dms.vo.service.SeBaFaultPatternExtendsVO;
import com.org.dms.vo.service.SeBaFaultPatternVO;

import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;

import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * @Title: szqdms
 * @description: 故障模式方法
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年7月19日 
 */
public class FaultPatternMngAction
{
	private Logger logger = com.org.framework.log.log
			.getLogger("FaultPatternMngAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private FaultPatternMngDao dao = FaultPatternMngDao.getInstance(atx);

    /**
     * 新增故障模式
     * @throws Exception
     */
    public void insert() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBaFaultPatternVO vo = new SeBaFaultPatternVO();
        	SeBaFaultPatternExtendsVO extendsVo = new SeBaFaultPatternExtendsVO();//继承后的VO
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			extendsVo.setValue(hm);
			
			//判断故障模式代码是否已存在
			QuerySet qs = dao.checkCode(vo.getFaultCode());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("故障模式代码已存在，保存失败！");
				}
			}
			//设置通用字段
			vo.setCreateUser(user.getPersonName());
			vo.setCreateTime(Pub.getCurrentDate());
			
			//继承后的VO设置通用字段
			extendsVo.setCreateUser(user.getPersonName());
			extendsVo.setCreateTime(vo.getCreateTime());
			
			//通过dao，执行插入
			dao.insertFaultPattern(vo);
			//对于页面回显，若直接修改新增的数据行，必须将主键带过来
			extendsVo.setPatternId(vo.getPatternId());
			
			//返回插入结果和成功信息
			atx.setOutMsg(extendsVo.getRowXml(),"故障模式信息新增成功！");
           
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }

    /**
     * 更新故障模式
     * @throws Exception
     */
    public void update() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	SeBaFaultPatternVO tempVO = new SeBaFaultPatternVO();
    	SeBaFaultPatternExtendsVO extendsTempVO = new SeBaFaultPatternExtendsVO();
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			tempVO.setValue(hm);
			extendsTempVO.setValue(hm);
			
			tempVO.setUpdateTime(Pub.getCurrentDate());
			tempVO.setUpdateUser(user.getPersonName());
			//继承后的VO
			extendsTempVO.setUpdateUser(user.getPersonName());
			extendsTempVO.setUpdateTime(tempVO.getUpdateTime());
			
			//通过dao，执行更新
            dao.updateFaultPattern(tempVO);
            //返回更新结果和成功信息
            atx.setOutMsg(extendsTempVO.getRowXml(),"故障模式信息修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }

    /**
     * 删除故障模式
     * @throws Exception
     */
    //删除只更新有效标识，更新人，更新时间，所以目前与update方法一致
    public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String patternId = Pub.val(request, "patternId");
        
        SeBaFaultPatternVO tempVO = new SeBaFaultPatternVO();
		tempVO.setPatternId(patternId);
		tempVO.setUpdateTime(Pub.getCurrentDate());
		tempVO.setUpdateUser(user.getPersonName());
		tempVO.setStatus(DicConstant.YXBS_02);
        try
        {
                dao.updateFaultPattern(tempVO);
                //返回更新结果和成功信息
                atx.setOutMsg("","故障模式信息删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }

    /**
     * 查询基础代码
     * @throws Exception
     */
    public void search() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.search(page,user,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}
    /**
	 * 导出表数据
	 * 
	 * @throws Exception
	 */
	public void download() throws Exception {

		// 定义request对象
		ResponseWrapper response = atx.getResponse();
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request, page);
		try {
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(0,hBean);
			hBean = new HeaderBean();
			hBean.setName("FAULT_CODE");
			hBean.setTitle("故障模式代码");
			header.add(1, hBean);

			hBean = new HeaderBean();
			hBean.setName("FAULT_NAME");
			hBean.setTitle("故障模式名称");
			header.add(2, hBean);
			hBean = new HeaderBean();
			hBean.setName("POSITION_CODE");
			hBean.setTitle("车辆代码");
			header.add(3, hBean);
			hBean = new HeaderBean();
			hBean.setName("POSITION_NAME");
			hBean.setTitle("车辆部位");
			header.add(4, hBean);
			hBean = new HeaderBean();
			hBean.setName("FAULT_PATTERN_NAME");
			hBean.setTitle("故障类别");
			header.add(5, hBean);
			hBean = new HeaderBean();
			hBean.setName("NAME");
			hBean.setTitle("严重程度");
			header.add(6, hBean);


		/*	
			  hBean = new HeaderBean(); 
			  hBean.setName("STATUS_NA");
			  hBean.setTitle("状态"); 
			  header.add(6, hBean);
			*/

			QuerySet querySet = dao.download(conditions);
			ExportManager.exportFile(response.getHttpResponse(), "故障模式",
					header, querySet);
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		}
	}
}