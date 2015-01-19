package com.org.dms.action.service.oldpartMng;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.TwoDimensionCode;
import com.org.dms.dao.service.oldpartMng.OldPartPrintDao;
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
 * 旧件打印、箱号打印ACTION
 * @author zts
 *
 */
public class OldPartPrintAction {
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "OldPartPrintAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private OldPartPrintDao dao = OldPartPrintDao.getInstance(atx);
    private final String tmPrctureUrl = "/jsp/dms/dealer/service/oldpart/showPicture.jsp";//二维码图片
    private final String boxPrctureUrl = "/jsp/dms/dealer/service/oldpart/showBoxPicture.jsp";//箱号打印    
    /**
     * 旧件标签打印查询
     * @throws Exception
     */
    public void oldPartPrintSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request,page);
		String ifPrint = Pub.val(request, "ifPrint");
		try
		{
			BaseResultSet bs = dao.oldPartPrintSearch(page,conditions,user,ifPrint);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * 厂端应返旧件查询
     * @throws Exception
     */
    public void oldPartOemSearch() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		String rorgCode = Pub.val(request, "rorgCode");
    		BaseResultSet bs = dao.oldPartOemSearch(page,conditions,user,rorgCode);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     *二维码图片
     */
    public void partPrintPicture(){
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
		String claimId=Pub.val(request, "claimId");
		String faultPartId=Pub.val(request, "faultPartId");
		try
		{
			QuerySet qs = dao.queryPartBarCode(null,claimId,faultPartId,user);
			BufferedImage bufImg=null;
			String content="";
			if(qs.getRowCount() > 0){
				String partCode=qs.getString(1, "OLD_PART_CODE");//配件代码
				String partName=qs.getString(1, "OLD_PART_NAME");//配件名称
				//String partCount=qs.getString(1, "OLD_PART_COUNT");//数量
				String partCount="1";//数量
				//String partFactory=qs.getString(1, "SUPPLIER_NAME");//旧件厂商
				String supplierCode=qs.getString(1, "SUPPLIER_CODE");//旧件厂商代码
				String faultReason=qs.getString(1, "FAULT_REASON");//质损原因
				//String mainFactory=qs.getString(1, "MAIN_SUPP_NAME");//责任厂商
				String mainSuppCode=qs.getString(1, "MAIN_SUPP_CODE");//责任厂商代码
				String claimNo=qs.getString(1, "CLAIM_NO");//索赔单号
				String faultName=qs.getString(1, "FAULT_NAME");//故障名称
				String faultCode=qs.getString(1, "FAULT_CODE");//故障代码
				String vin=qs.getString(1, "VIN");//底盘号
				String orgName=qs.getString(1, "ORG_NAME");//渠道商名称
				String orgCode=qs.getString(1, "ORG_CODE");//渠道商代码
				content="渠道商代码:"+orgCode+"\r\n"+"渠道商名称:"+orgName+"\r\n"+"索赔单号:"+claimNo+"\r\n"+"底盘号:"+vin+"\r\n"+"故障代码:"+faultCode+"\r\n"+"故障名称:"+faultName+"\r\n"+"配件代码:"+partCode+"\r\n"+"配件名称:"+partName+"\r\n"+"配件数量:"+partCount+"\r\n"+"生产厂商:"+supplierCode+"\r\n"+"质损原因:"+faultReason+"\r\n"+"责任厂商:"+mainSuppCode;
				TwoDimensionCode handler = new TwoDimensionCode();
				bufImg=handler.encoderQRCode(content, "png", 20);
			}
			atx.getSession().set("bufImg", bufImg);
			atx.setOutObject("noresponse", "true");
			atx.setRedirect(tmPrctureUrl);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 箱号打印查询
     * @throws Exception
     */
    public void oldPartBoxNoSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.oldPartBoxNoSearch(page,conditions,user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * 下载打印数据
     * @throws Exception
     */
    public void download()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
        String boxNo = Pub.val(request, "boxNo");
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
    		header.add(1,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OUGHT_COUNT");
    		hBean.setTitle("配件数量");
    		header.add(2,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PROSUPPLY_CODE");
    		hBean.setTitle("生产供应商代码");
    		header.add(3,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("PROSUPPLY_NAME");
    		hBean.setTitle("生产供应商名称");
    		header.add(4,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("DUTYSUPPLY_CODE");
    		hBean.setTitle("责任供应商代码");
    		header.add(5,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("DUTYSUPPLY_NAME");
    		hBean.setTitle("责任供应商名称");
    		header.add(6,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("BROKEN_REASON");
    		hBean.setTitle("质损原因");
    		header.add(7,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("REMARKS");
    		hBean.setTitle("备注");
    		header.add(8,hBean);
    		QuerySet qs = dao.download(boxNo,user);
    		ExportManager.exportFile(response.getHttpResponse(),boxNo+"号箱配件信息", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 下载应返件
     * @throws Exception
     */
    public void partDownload()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		List<HeaderBean> header = new ArrayList<HeaderBean>();
    		HeaderBean hBean = null;
    		hBean = new HeaderBean();
    		hBean.setName("CLAIM_NO");
    		hBean.setTitle("索赔单号");
    		header.add(0,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("VIN");
    		hBean.setTitle("VIN");
    		header.add(1,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MILEAGE");
    		hBean.setTitle("里程");
    		header.add(2,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("WXCS");
    		hBean.setTitle("维修次数");
    		header.add(3,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OLD_PART_CODE");
    		hBean.setTitle("配件代码");
    		header.add(4,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OLD_PART_NAME");
    		hBean.setTitle("配件名称");
    		header.add(5,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_CODE");
    		hBean.setTitle("生产供应商代码");
    		header.add(6,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SUPPLIER_NAME");
    		hBean.setTitle("生产供应商名称");
    		header.add(7,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MAIN_SUPP_CODE");
    		hBean.setTitle("责任供应商代码");
    		header.add(8,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("MAIN_SUPP_NAME");
    		hBean.setTitle("责任供应商名称");
    		header.add(9,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("OLD_PART_COUNT");
    		hBean.setTitle("配件数量");
    		header.add(10,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("REPAY_COSTS");
    		hBean.setTitle("追偿材料费");
    		header.add(11,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("CLAIM_COSTS");
    		hBean.setTitle("结算材料费");
    		header.add(12,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("FAULT_REASON");
    		hBean.setTitle("质损原因");
    		header.add(13,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("REMARKS");
    		hBean.setTitle("备注");
    		header.add(14,hBean);
    		QuerySet qs = dao.partDownload(conditions);
    		ExportManager.exportFile(response.getHttpResponse(), "应返旧件", header, qs);
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     *二维码图片
     */
    public void boxNoPrintPicture(){
    	RequestWrapper request = atx.getRequest();
		try
		{
			String boxNo = Pub.val(request,"BOX_NO");
			String name= new String(boxNo.getBytes("ISO-8859-1"),"UTF-8");
			BufferedImage bufImg=null;
			String content="";
			content=name;
			TwoDimensionCode handler = new TwoDimensionCode();
			bufImg=handler.encoderQRCode(content, "png", 10);
			atx.setOutData("bufImg", bufImg);
			atx.setForword(boxPrctureUrl);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    public void dealerDownload()throws Exception{
		//获取封装后的request对象
		RequestWrapper request = atx.getRequest();
		ResponseWrapper response= atx.getResponse();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();
			hBean.setName("CLAIM_NO");
			hBean.setTitle("索赔单号");
			header.add(0,hBean);
			hBean = new HeaderBean();
			hBean.setName("OLD_PART_CODE");
			hBean.setTitle("配件代码");
			header.add(1,hBean);
			hBean = new HeaderBean();
			hBean.setName("OLD_PART_NAME");
			hBean.setTitle("配件名称");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("SUPPLIER_CODE");
			hBean.setTitle("供应商代码");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("SUPPLIER_NAME");
			hBean.setTitle("供应商名称");
			header.add(4,hBean);
			hBean = new HeaderBean();
			hBean.setName("OLD_PART_COUNT");
			hBean.setTitle("数量");
			header.add(5,hBean);
			QuerySet qs = dao.partDownload(conditions,user);
			ExportManager.exportFile(response.getHttpResponse(), "陕西重汽产品保修服务旧件综合管理表", header, qs);
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
    /***
     * 修改打印
     * @throws Exception
     */
    public void oldPartPrintUpdate()throws Exception{
    	RequestWrapper request = atx.getRequest();
        String faultPartId = Pub.val(request, "faultPartId");
        try
        {
        	dao.updateFaultPart(faultPartId);
            atx.setOutMsg("","");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
}
