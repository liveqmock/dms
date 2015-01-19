package com.org.dms.action.part.basicInfoMng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.basicInfoMng.WarehouseKeeperDao;
import com.org.dms.vo.part.PtBaWarehouseKeeperVO;
import com.org.dms.vo.part.PtBuPchOrderDtlVO;
import com.org.dms.vo.part.PtBuPchOrderVO;
import com.org.frameImpl.Constant;
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
 * 用户管理action
 */
public class WarehouseKeeperAction
{
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "WarehouseKeeperAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private WarehouseKeeperDao dao = WarehouseKeeperDao.getInstance(atx);

    /**
     * 新增库管员属性
     * @throws Exception
     * @Author suoxiuli 2014-07-15
     */
    public void batchInsert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String partIds = Pub.val(request, "partIds");
        
        try
        {
        	HashMap<String,String> hm;
 			hm = RequestUtil.getValues(request);
 			String userAccount = hm.get("USER_ACCOUNT");
 			String userName = hm.get("PERSON_NAME");
 			String warehouseId = hm.get("WAREHOUSE_ID");
			String warehouseCode = hm.get("WAREHOUSE_CODE");
			String warehouseName = hm.get("WAREHOUSE_NAME");
			QuerySet check = dao.checkUnique(warehouseId,partIds);
 	 			if(check.getRowCount()>0){
 	 				throw new Exception("配件"+check.getString(1, "PART_CODES")+"在仓库"+check.getString(1, "WAREHOUSE_NAME")+"库位已存在");
 	 			}
			dao.batchInsertWarehouseKeeper(partIds, userAccount, userName, user
					.getPersonName(), DicConstant.YXBS_01, warehouseId,
					warehouseCode, warehouseName);
            
            atx.setOutMsg("","库管员属性批量新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 更新库管员属性
     * @throws Exception
     * @Author suoxiuli 2014-07-15
     */
    public void update() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBaWarehouseKeeperVO tempVO = new PtBaWarehouseKeeperVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			
			//设置通用字段
			tempVO.setUpdateUser(user.getPersonName());
			tempVO.setUpdateTime(Pub.getCurrentDate());
			
            dao.updateWarehouseKeeper(tempVO);
            atx.setOutMsg(tempVO.getRowXml(),"库管员属性修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 更新库管员属性
     * @throws Exception
     * @Author suoxiuli 2014-07-15
     */
    public void batchUpdate() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String keeperIds = Pub.val(request, "keeperIds");
        
        try
        {
        	
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			
			String userAccount = hm.get("USER_ACCOUNT");
			String userName = hm.get("PERSON_NAME");
			String updateUser = user.getPersonName();
            dao.batchUpdateWarehouseKeeper(keeperIds,userAccount,userName,updateUser);
            atx.setOutMsg("","库管员属性批量修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除库管员属性
     * @throws Exception
     * @Author suoxiuli 2014-07-19
     */
    public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String keeperIds = Pub.val(request, "keeperIds");
        try
        {
            //更新直发类型为无效状态
            boolean b = dao.updateWarehouseKeeperStatus(keeperIds, user.getAccount(), Constant.YXBS_02);
            atx.setOutMsg("","库管员属性删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 发运库管员属性
     * @throws Exception
     * Author suoxiuli 2014-07-15
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
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 批量搜索没有库管员的配件
     * @throws Exception
     * Author suoxiuli 2014-07-15
     */
    public void searchNewPart() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			BaseResultSet bs = dao.searchNewPart(page, conditions, DicConstant.YXBS_01);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    
    /**
     * 搜索所有的配件代码
     * @throws Exception
     * Author suoxiuli 2014-08-30
     */
    public void partCodeSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			BaseResultSet bs = dao.searchAllPartCode(page, conditions, DicConstant.YXBS_01);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
    }
    //keeperDownload
    public void keeperDownload()throws Exception{
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
			hBean.setName("PART_CODE");
			hBean.setTitle("配件代码");
			header.add(0,hBean);
			hBean = new HeaderBean();
			hBean.setName("PART_NAME");
			hBean.setTitle("配件名称");
			hBean.setWidth(50);
			header.add(1,hBean); 
			hBean = new HeaderBean();
			hBean.setName("WAREHOUSE_CODE");
			hBean.setTitle("仓库代码");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("WAREHOUSE_NAME");
			hBean.setTitle("仓库名称");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("USER_ACCOUNT");
			hBean.setTitle("库管员帐号");
			header.add(4,hBean);
			hBean = new HeaderBean();
			hBean.setName("USER_NAME");
			hBean.setTitle("库管员名称");
			header.add(5,hBean);
			QuerySet qs = dao.keeperDownload(conditions);
			ExportManager.exportFile(response.getHttpResponse(), "库管员属性", header, qs);
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
    //searchImport
    public void searchImport() throws Exception {
        RequestWrapper request = atx.getRequest();
        PageManager pageManager = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request, pageManager);
        PageManager page = new PageManager();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            BaseResultSet bs = dao.searchImport(page, user,conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    //expData
    public void expData()throws Exception{
    	RequestWrapper request = atx.getRequest();
        ResponseWrapper response = atx.getResponse();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String tmpNo = Pub.val(request, "seqs");
        try {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();
			hBean.setName("PART_CODE");
			hBean.setTitle("配件代码");
			header.add(0,hBean);
			hBean = new HeaderBean();
			hBean.setName("PART_NAME");
			hBean.setTitle("配件名称");
			hBean.setWidth(50);
			header.add(1,hBean); 
			hBean = new HeaderBean();
			hBean.setName("WAREHOUSE_CODE");
			hBean.setTitle("仓库代码");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("WAREHOUSE_NAME");
			hBean.setTitle("仓库名称");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("USER_ACCOUNT");
			hBean.setTitle("库管员帐号");
			header.add(4,hBean);
			hBean = new HeaderBean();
			hBean.setName("USER_NAME");
			hBean.setTitle("库管员名称");
			header.add(5,hBean);
            QuerySet qs = dao.expData(tmpNo,user);
            ExportManager.exportFile(response.getHttpResponse(), "库管员属性", header, qs);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    public void confirmImport()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String tmpNo = Pub.val(request, "tmpNo");
 		try
 		{
 			QuerySet getTmp = dao.getTmp(tmpNo,user);
 			for(int i = 0;i<getTmp.getRowCount();i++){
 				String PART_CODE = getTmp.getString(i+1, "PART_CODE");
 				String PART_NAME = getTmp.getString(i+1, "PART_NAME");
 				String WAREHOUSE_CODE = getTmp.getString(i+1, "WAREHOUSE_CODE");
 				String WAREHOUSE_NAME = getTmp.getString(i+1, "WAREHOUSE_NAME");
 				String USER_ACCOUNT = getTmp.getString(i+1, "KEEP_MAN_ACOUNT");
 				String USER_NAME = getTmp.getString(i+1, "KEEP_MAN_NAME");
 				String PART_ID = getTmp.getString(i+1, "PART_ID");
 				String WAREHOUSE_ID = getTmp.getString(i+1, "WAREHOUSE_ID");
 				QuerySet checkIn = dao.checkIn(PART_ID,WAREHOUSE_ID);
 				PtBaWarehouseKeeperVO vo = new PtBaWarehouseKeeperVO();
 				if(checkIn.getRowCount()>0){
 					vo.setKeeperId(checkIn.getString(1, "KEEPER_ID"));
 					vo.setPartCode(PART_CODE);
 					vo.setPartId(PART_ID);
 					vo.setPartName(PART_NAME);
 					vo.setWarehouseId(WAREHOUSE_ID);
 					vo.setWarehouseCode(WAREHOUSE_CODE);
 					vo.setWarehouseName(WAREHOUSE_NAME);
 					vo.setUserAccount(USER_ACCOUNT);
 					vo.setUserName(USER_NAME);
 					vo.setUpdateUser(user.getAccount());
 					vo.setUpdateTime(Pub.getCurrentDate());
 					dao.updateWarehouseKeeper(vo);
 				}else{
 					vo.setPartCode(PART_CODE);
 					vo.setPartId(PART_ID);
 					vo.setPartName(PART_NAME);
 					vo.setWarehouseId(WAREHOUSE_ID);
 					vo.setWarehouseCode(WAREHOUSE_CODE);
 					vo.setWarehouseName(WAREHOUSE_NAME);
 					vo.setUserAccount(USER_ACCOUNT);
 					vo.setUserName(USER_NAME);
 					vo.setCreateUser(user.getAccount());
 					vo.setCreateTime(Pub.getCurrentDate());
 					vo.setStatus(DicConstant.YXBS_01);
 					dao.insert(vo);
 				}
 			}
 			atx.setOutMsg("", "导入成功！");
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }

}