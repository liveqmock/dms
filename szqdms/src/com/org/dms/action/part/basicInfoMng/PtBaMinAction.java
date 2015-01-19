package com.org.dms.action.part.basicInfoMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.basicInfoMng.PtBaInfoDao;
import com.org.dms.dao.part.basicInfoMng.PtBaMinDao;
import com.org.dms.dao.part.basicInfoMng.PtBaPriceLogDao;
import com.org.dms.vo.part.PtBaInfoVO;
import com.org.dms.vo.part.PtBaInfoVO_Ext;
import com.org.dms.vo.part.PtBaMinVO;
import com.org.dms.vo.part.PtBaPriceLogVO;
import com.org.frameImpl.Constant;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class PtBaMinAction {
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "PtBaInfoAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PtBaMinDao dao = PtBaMinDao.getInstance(atx);   
//    private PtBaPriceLogDao logdao = PtBaPriceLogDao.getInstance(atx);
    public void insert() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取封装后的response对象
    	//ResponseWrapper response = atx.getResponse();
    	//获取当前登录user对象
    	
        User user = (User) atx.getSession().get(Globals.USER_KEY);
    	
        try
        {
        	PtBaMinVO vo = new PtBaMinVO();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
//			vo.setCreateUser(user.getAccount());
//			vo.setCreateTime(Pub.getCurrentDate());
			vo.setStatus(DicConstant.YXBS_01);		

			
	
			//通过dao，执行插入
			dao.insertPtBaMin(vo);	
			//atx.setOutMsg(logvo.getRowXml(),"配件价格日志信息新增成功！");  		
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"最小包装信息新增成功！");  		

        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);            
        }
    }
 
    
    //修改
    public void update() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
    	PtBaMinVO tempVO = new PtBaMinVO();
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			tempVO.setValue(hm);
			//extvo.setPosition_id(tempVO.getBelongAssembly());
            dao.updatePtBaMin(tempVO);
            
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"最小包装信息修改成功！");
         
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);           
        }
    }
    
    //删除
    public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String min_id = Pub.val(request, "min_id");
        try
        {
            //更新配件档案信息为无效状态
            dao.updatePtBaMinStatus(min_id, Constant.YXBS_02);         
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);          
        }
    }
    
    //查询
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
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.search(page,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}

}

