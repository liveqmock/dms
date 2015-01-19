package com.org.dms.action.service.basicinfomng;

import java.util.*;

import com.org.dms.dao.service.basicinfomng.DealerStarMngDao;
import com.org.dms.vo.service.MainDealerExtendsVO;
import com.org.dms.vo.service.MainDealerStarLogVO;
import com.org.dms.vo.service.MainDealerVO;

import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExcelErrors;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;

import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * @Title: szqdms
 * @description: 服务商星级评定方法
 * @throws Exception 设定文件
 * @auther fanpeng
 * @date 2014年8月25日 
 */
public class DealerStarMngAction
{
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private DealerStarMngDao dao = DealerStarMngDao.getInstance(atx);

    /**
     * 修改服务商星级
     * @throws Exception
     */
    public void update() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
    	User user = (User) atx.getSession().get(Globals.USER_KEY);

    	MainDealerVO tempVO = new MainDealerVO();
    	MainDealerStarLogVO tempStarLogVO = new MainDealerStarLogVO();
    	MainDealerExtendsVO extendsTempVO = new MainDealerExtendsVO();
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值		
			tempVO.setValue(hm);
			extendsTempVO.setValue(hm);
			
			//获取日志表信息
			String oldDealerStar=hm.get("OLD_DEALER_STAR");
			String newDealerStar=hm.get("DEALER_STAR");
			String dealerCode=hm.get("DEALER_CODE");
			String dealerName=hm.get("DEALER_NAME");
			
			tempStarLogVO.setOldStar(oldDealerStar);
			tempStarLogVO.setNewStar(newDealerStar);
			tempStarLogVO.setDealerCode(dealerCode);
			tempStarLogVO.setDealerName(dealerName);
			tempStarLogVO.setModifyTime(Pub.getCurrentDate());
			tempStarLogVO.setModifyUser(user.getPersonName());	
			
			//通过dao，执行日志表插入
            dao.insertDealStarLog(tempStarLogVO);
			
			//通过dao，执行更新
            dao.updateDealStar(tempVO);
            //返回更新结果和成功信息
            atx.setOutMsg(extendsTempVO.getRowXml(),"服务商星级修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
        }
    }

    /**
     * 查询服务商星级
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
     * 查询服务商星级评定轨迹
     * @throws Exception
     */
    public void searchLog() throws Exception
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
			BaseResultSet bs = dao.searchLog(page,user,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}
    
    /**
	 * 
	 * @date()2014年10月10日
	 * @author fanpeng
	 * @to_do:服务站星级评定临时表导入校验方法：临时表数据校验
	 * @param user
	 * @param bParams
	 * @return
	 * @throws Exception
	 */
    public List<ExcelErrors> checkData()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	ExcelErrors errors = null;
		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		QuerySet qs = dao.searchDealerStarTmp(user);//查询此用户下的所有服务站星级临时表信息
		if(qs.getRowCount()>0){
			for(int i=0;i<qs.getRowCount();i++){
				errors=new ExcelErrors();
				String rowNo = qs.getString(i+1, "ROW_NO"); 		//行号
				String dealerCode = qs.getString(i+1,"DEALER_CODE");//服务站代码
	    		String dealerStar = qs.getString(i+1,"DEALER_STAR");//服务站星级代码
	    		
	    		//服务站代码
				if(null==dealerCode || "".equals(dealerCode)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNo));
					errors.setErrorDesc("服务站代码不能为空!");
                    errorList.add(errors);
                }
	    		
				//星级代码
				if(null==dealerStar || "".equals(dealerStar)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNo));
					errors.setErrorDesc("星级代码不能为空!");
                    errorList.add(errors);
                }
				
	    		//判断服务站代码是否存在
	    		QuerySet qs1 = dao.checkDealerCode(dealerCode);
				if(qs1.getRowCount() > 0)
				{
					String n = qs1.getString(1, 1);
					if(Integer.parseInt(n) == 0)
					{
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNo));
						errors.setErrorDesc("服务站代码:"+dealerCode+"不存在!");
	                    errorList.add(errors);
					}
				}
						
	    		//判断星级代码是否存在
	    		QuerySet qs2 = dao.checkDealerStar(dealerStar);
				if(qs2.getRowCount() > 0)
				{
					String n = qs2.getString(1, 1);
					if(Integer.parseInt(n) == 0)
					{
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNo));
						errors.setErrorDesc("星级代码:"+dealerStar+"不存在!");
	                    errorList.add(errors);
					}
				}
			}
		
		//判断服务站代码是否存在重复数据
		QuerySet qs3 = dao.checkMultipleDealerCode(user, ""); 
		if(qs3.getRowCount() > 0)
		{
			for(int j=0; j<qs3.getRowCount(); j++){
				String dealerCode = qs3.getString(j+1, "DEALER_CODE"); 
				
				String errorStr = "";
				QuerySet qs4 = dao.checkMultipleDealerCode(user, dealerCode);
				for(int k=0; k<qs4.getRowCount(); k++){
					String rowNo = qs4.getString(k+1, "ROW_NO");
					errorStr = errorStr + rowNo + ",";
				}
				
				errorStr = errorStr.substring(0, errorStr.length()-1);//删除最后一个","号
				errorStr ="第"+ errorStr + "行服务站代码："+dealerCode+"重复！";
				
				//添加错误描述
				errors=new ExcelErrors();
				errors.setErrorDesc(errorStr);
				errorList.add(errors);
			}
		}
		
		//判断序号(行号)是否存在重复数据
		QuerySet qs5 = dao.checkMultipleRowNo(user);
		if(qs5.getRowCount()>0){
			for(int i=0;i<qs5.getRowCount();i++){
				errors=new ExcelErrors();
				String rowNo=qs5.getString(i+1,"ROW_NO");
				errors.setRowNum(Integer.parseInt(rowNo));
				errors.setErrorDesc("序号(行号)代码:"+rowNo+"导入数据重复！");
				errorList.add(errors);
			}
		}
		}
		
		if(errorList!=null && errorList.size()>0){
			return errorList;
		}else{
			return null;
		}
    }
    
    /**
     * 服务站星级评定临时表查询（显示临时表数据在页面弹出框中）
     * @throws Exception
     * Author fanpeng 2014-10-17
     */
    public void searchImport() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchSuccess(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			atx.setException(e);
		}
	}
	/**
     * 导入确定按钮：把临时表的数据更新到主表
     * @throws Exception
     */
    public void dealerStarImport()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
 		try
 		{
 			//更新
 		    QuerySet qs = dao.searchDealerStarTmpImport(user);
 		    if(qs.getRowCount() > 0 ){
 		    	for(int i=0;i<qs.getRowCount();i++){
 		    		String dealerId = qs.getString(i+1,"DEALER_ID");     //服务站主键
 					String dealerCode = qs.getString(i+1,"DEALER_CODE"); //服务站代码
 					String dealerName = qs.getString(i+1,"DEALER_NAME"); //服务站名称
 					String oldDealerStar = qs.getString(i+1,"OLD_STAR"); //渠道原星级代码
 					String newDealerStar = qs.getString(i+1,"NEW_STAR"); //渠道新星级代码
 		    		String createUser = qs.getString(i+1,"USER_ACCOUNT");//创建人
 		    		
 		    		//主表VO
 		    		MainDealerVO tempVO=new MainDealerVO();
 		    		//日志表VO
 		    		MainDealerStarLogVO tempLogVO = new MainDealerStarLogVO();
 		    		
 		    		tempVO.setDealerId(dealerId);
 		    		tempVO.setDealerCode(dealerCode);
 		    		tempVO.setDealerName(dealerName);
 		    		tempVO.setDealerStar(newDealerStar);
 		    		
 		    		tempLogVO.setDealerCode(dealerCode);
 		    		tempLogVO.setDealerName(dealerName);
 		    		tempLogVO.setOldStar(oldDealerStar);
 		    		tempLogVO.setNewStar(newDealerStar);
 		    		tempLogVO.setModifyUser(createUser);
 		    		tempLogVO.setModifyTime(Pub.getCurrentDate());
 		    				    		
 					//通过dao，执行日志表插入
 		            dao.insertDealStarLog(tempLogVO);
 					//通过dao，执行更新
 		            dao.updateDealStar(tempVO);
 		    	}
 		    }
 		    
 			atx.setOutMsg("", "导入成功！");
 		}
 		catch (Exception e)
 		{
 			atx.setException(e);
 		}
    }
    
    /**
     * 下载（导出）
     * @throws Exception
     */
    public void download()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
	    	HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(0,hBean);
	    	hBean = new HeaderBean();
    		hBean.setName("ONAME");
    		hBean.setTitle("所属办事处");
    		header.add(1,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("DEALER_CODE");
    		hBean.setTitle("服务商代码");
    		header.add(2,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("DEALER_NAME");
    		hBean.setTitle("服务商名称");
    		header.add(3,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PARANAME");
    		hBean.setTitle("星级");
    		header.add(4,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("DEALER_STAR");
    		hBean.setTitle("星级代码");
    		header.add(5,hBean);
    		QuerySet qs = dao.download(conditions,user);
    		ExportManager.exportFile(response.getHttpResponse(), "服务站星级评定明细", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
        }
    }
}