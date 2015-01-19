package com.org.dms.action.part.purchaseMng.purchaseOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.purchaseMng.purchaseOrder.SupplierPartPhotoApplyDao;
import com.org.dms.vo.part.PtBaSupplierVO;
import com.org.dms.vo.part.PtBuSupPartPhotoVO;
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

public class SupplierPartPhotoApplyMngAciton {
private Logger logger = com.org.framework.log.log.getLogger("Logger");
//上下文对象
private ActionContext atx = ActionContext.getContext();
//定义dao对象
private SupplierPartPhotoApplyDao dao = SupplierPartPhotoApplyDao.getInstance(atx);

	
	/**
	 * 
	 * @date()2014年7月16日下午7:34:50
	 * @author sxd
	 * @to_do:查询本供应商的所有配件信息。
	 * @throws Exception
	 */
	public void search() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.search(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	/**
	 * 
	 * @date()2014年7月16日下午7:34:50
	 * @author sxd
	 * @to_do:查询本供应商的所有配件信息。
	 * @throws Exception
	 */
	public void queryDtl() throws Exception
    {
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		try
		{
			
			BaseResultSet bs = dao.queryDtl(page,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	/**
	 * 
	 * @date()2014年7月16日下午7:34:50
	 * @author sxd
	 * @to_do:查询所有供应商
	 * @throws Exception
	 */
	public void supplierSearch() throws Exception
	{
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.photosSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	/**
	 * 
	 * @date()2014年7月16日下午7:34:50
	 * @author sxd
	 * @to_do:查询供应商配件对应的图片
	 * @throws Exception
	 */
	public void detaileSearch() throws Exception
	{
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		page.setPageRows(900);
		try
		{
			String supId = Pub.val(request, "supId");
			BaseResultSet bs = dao.detaileSearch(page,supId,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	/**
	 * 
	 * @date()2014年7月16日下午7:34:50
	 * @author sxd
	 * @to_do:查询未审核配件
	 * @throws Exception
	 */
	public void detaileSearchCheck() throws Exception
	{
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		page.setPageRows(900);
		try
		{
			String supId = Pub.val(request, "supId");
			BaseResultSet bs = dao.detaileSearchCheck(page,supId,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	/**
	 * 
	 * @date()2014年7月16日下午7:34:50
	 * @author sxd
	 * @to_do:查询未审核配件
	 * @throws Exception
	 */
	public void detaileSearchChecked() throws Exception
	{
		RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		page.setPageRows(900);
		try
		{
			String supId = Pub.val(request, "supId");
			BaseResultSet bs = dao.detaileSearchChecked(page,supId,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
	 /**
     * 
     * @date()2014年7月5日 下午4:29:39
     * TODO:供应商配件(附件)查询
     * @throws Exception
     */
    public void appendixSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		RequestUtil.getConditionsWhere(request,page);
		String relationId = Pub.val(request, "relationId");
		try
		{
			BaseResultSet bs = dao.appendixSearch(page,user,relationId);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * 
     * @date()2014年7月5日 下午4:29:39
     * TODO:审核通过配件图片。
     * @throws Exception
     */
    public void passPartPhoto() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String photoIds=hm.get("PHOTOID");
            String[] photoIdArr = photoIds.split(",");
    		for(int i=0;i<photoIdArr.length;i++){
    			PtBuSupPartPhotoVO vo=new PtBuSupPartPhotoVO();
    			vo.setPhotoId(photoIdArr[i]);
    			vo.setApplyStatus(DicConstant.GYSPJZPSC_03);
    			dao.updatePhoto(vo);
    			atx.setOutMsg("","审核通过！");
    		}
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 
     * @date()2014年7月5日 下午4:29:39
     * TODO:审核驳回配件图片。
     * @throws Exception
     */
    public void rejectPartPhoto() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		HashMap<String, String> hm;
    		//将request流转换为hashmap结构体
    		hm = RequestUtil.getValues(request);
    		String photoIds=hm.get("PHOTOID");
    		String[] photoIdArr = photoIds.split(",");
    		for(int i=0;i<photoIdArr.length;i++){
    			PtBuSupPartPhotoVO vo=new PtBuSupPartPhotoVO();
    			vo.setPhotoId(photoIdArr[i]);
    			vo.setApplyStatus(DicConstant.GYSPJZPSC_04);
    			dao.updatePhoto(vo);
    			atx.setOutMsg("","审核驳回！");
    		}
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 
     * @date()2014年7月5日 下午4:29:39
     * TODO:提报供应商配件照片，以供厂端查看。
     * @throws Exception
     */
    public void partPhotoReport() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	RequestUtil.getConditionsWhere(request,page);
    	String partId = Pub.val(request, "partId");
    	String relationId = Pub.val(request, "relationId");
    	try
    	{
    		QuerySet qs=dao.photosCheck(relationId);
    		QuerySet qs1=dao.checkPhotos(relationId);
    		String photoId=null;
    		if(qs1.getRowCount()>0){
    			 photoId=qs1.getString(1, "PHOTO_ID");
    		}
    		PtBuSupPartPhotoVO vo=new PtBuSupPartPhotoVO();
    		if(qs1.getRowCount()>0){
    			vo.setPhotoId(photoId);
    			vo.setUpdateTime(Pub.getCurrentDate());
    			vo.setUpdateUser(user.getAccount());
    			vo.setOrgId(user.getOrgId());
    			vo.setApplyTime(Pub.getCurrentDate());
    			vo.setApplyStatus(DicConstant.GYSPJZPSC_02);
    			dao.updatePhoto(vo);
    			atx.setOutMsg("","照片提交成功！");
    		}else{
	    		if(qs.getRowCount()>0){
	    			dao.insertPhotos(partId,user);
	    			atx.setOutMsg("","照片提交成功！");
	    		}else{
	    			atx.setOutMsg("1","");
	    		}
    		}
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 
     * @date()2014年7月5日 下午4:29:39
     * TODO:维护旧件认领人。
     * @throws Exception
     */
    public void getNameUpdate() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
    	RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		String supplierId=null;
    		HashMap<String, String> hm;
    		//将request流转换为hashmap结构体
    		hm = RequestUtil.getValues(request);
    		String getName=hm.get("GET_NAME");
    		String getNo=hm.get("GET_NO");
    		String getPhone=hm.get("GET_PHONE");
    		QuerySet qs=dao.querySupplerId(user);
    		if(qs.getRowCount()>0){
    			supplierId=qs.getString(1, "SUPPLIER_ID");
    		}
    		PtBaSupplierVO vo =new PtBaSupplierVO();
    		vo.setSupplierId(supplierId);
    		vo.setGetName(getName);
    		vo.setGetNo(getNo);
    		vo.setGetPhone(getPhone);
    		dao.updateSupplier(vo);
    		atx.setOutMsg("","旧件认领人员维护成功。");
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 
     * @date()2014年7月5日 下午4:29:39
     * TODO:删除
     * @throws Exception
     */
    public void deletePhoto() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		String sId=Pub.val(request, "supId");
    		dao.deletePhoto(sId,user);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    public void download()throws Exception{
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
    		hBean.setName("SUPPLIER_CODE");
    		hBean.setTitle("供应商代码");
    		header.add(0,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_NAME");
    		hBean.setTitle("供应商名称");
    		header.add(1,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("GET_NAME");
    		hBean.setTitle("认领人姓名");
    		header.add(2,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("GET_NO");
    		hBean.setTitle("认领人身份证");
    		header.add(3,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("GET_PHONE");
    		hBean.setTitle("认领人联系电话");
    		header.add(4,hBean);
    		
    		QuerySet qs = dao.download(conditions);
    		ExportManager.exportFile(response.getHttpResponse(), "陕重汽服务旧件各厂家认领人员名单", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
}
