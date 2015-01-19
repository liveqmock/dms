package com.org.dms.action.part.basicInfoMng;

import java.util.HashMap;

import org.apache.log4j.Logger;
import com.org.dms.dao.part.basicInfoMng.PtBaPictureDao;
import com.org.dms.dao.part.basicInfoMng.PtBaReplacementDao;
import com.org.dms.vo.part.PtBaPictureVO;
import com.org.dms.vo.part.PtBaReplacementVO;
import com.org.frameImpl.Constant;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class PtBaReplacementAction {
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "OrgPersonAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PtBaReplacementDao dao = PtBaReplacementDao.getInstance(atx);
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
        	PtBaReplacementVO vo = new PtBaReplacementVO();
//            PtBaInfoVO_Ext evo = new PtBaInfoVO_Ext();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
//			evo.setValue(hm);
			//设置通用字段
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			
					
	
			//通过dao，执行插入
			dao.insertPtBaReplacement(vo);
			
//			evo.setUnit(vo.getUnit());	//计量单位
										//配件类型
//			voExt.bindFieldToDic("STATUS", "YXBS");	//基本表
			
//			evo.setAttribute(vo.getAttribute()); 	//配件属性ATTRIBUTEs
//			evo.setMinPack(vo.getMinPack());		//最小包装单位MIN_PACK
			//是否直发IF_DIRECT
			//是否保外IF_OUT
			//是否可订IF_BOOK
			//是否回运IF_RETURN
			//是否大总成IF_ASSEMBLY
			//所属总成BELONG_ASSEMBLY
			//是否扫码件IF_SCAN
			//是否指定供应商IF_SUPLY
			//配件状态PART_STATUS
			
			
			
			
//			evo.setPosition_name(vo.getBelongAssembly());
//			
//			evo.setPartId(vo.getBelongAssembly());	
//			evo.setPartId(vo.getBelongAssembly());	
//			evo.setPartId(vo.getBelongAssembly());	
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"配件替换件关系信息新增成功！");           
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
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBaReplacementVO tempVO = new PtBaReplacementVO();
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			tempVO.setValue(hm);
			//设置通用字段
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
            dao.updatePtBaReplacement(tempVO);
            
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"配件替换件关系信息修改成功！");
         
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
        String replace_id = Pub.val(request, "replace_id");
        try
        {
            //更新配件档案信息为无效状态
            dao.updatePtBaReplacementStatus(replace_id, Constant.YXBS_02);        
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
  //查询配件
    public void partSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.partSearch(page, conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    //批量新增
    public void batchInsert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String partIds = Pub.val(request, "partIds");
        try
        {
        	PtBaReplacementVO vo = new PtBaReplacementVO();	
        	HashMap<String,String> hm;
 			hm = RequestUtil.getValues(request);
 			vo.setValue(hm);
 			dao.batchInsert(partIds,vo);
            atx.setOutMsg(vo.getRowXml(),"批量新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    } 

}

