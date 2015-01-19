<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.org.framework.common.DBFactory"%>
<%@page import="java.awt.image.BufferedImage"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="com.org.mvc.context.RequestWrapper"%>
<%@page import="com.org.mvc.context.ResponseWrapper"%>
<%@page import="com.org.framework.util.Pub"%>
<%@page import="com.org.framework.common.User"%>
<%@page import="com.org.framework.Globals"%>
<%@page import="com.org.mvc.context.ActionContext"%>
<%@page import="com.org.framework.common.QuerySet"%>
<%@page import="com.org.dms.dao.service.oldpartMng.OldPartPrint2Dao"%>
<%@page import="com.org.dms.common.TwoDimensionCode"%>
<%@ page import="com.sun.image.codec.jpeg.JPEGCodec,com.sun.image.codec.jpeg.JPEGImageEncoder"%>
<% 
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
response.reset();
	//ActionContext atx = ActionContext.getContext();
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String claimId=Pub.val(request, "claimId");
	String faultPartId=Pub.val(request, "faultPartId");
	OldPartPrint2Dao dao = new OldPartPrint2Dao();
	DBFactory factory = new DBFactory();
	QuerySet qs = dao.queryPartBarCode(factory,claimId,faultPartId,user);
	BufferedImage bufImg=null;
	String content="";
	if(qs.getRowCount() > 0){
		String partCode=qs.getString(1, "OLD_PART_CODE");//配件代码
		String partName=qs.getString(1, "OLD_PART_NAME");//配件名称
		//String partCount=qs.getString(1, "OLD_PART_COUNT");//数量
		String partCount="1";//数量
		//String partFactory=qs.getString(1, "SUPPLIER_NAME");//旧件厂商
		String supplierCode=qs.getString(1, "SUPPLIER_CODE");//旧件厂商代码
		//String faultReason=qs.getString(1, "FAULT_REASON");//质损原因
		//String mainFactory=qs.getString(1, "MAIN_SUPP_NAME");//责任厂商
		String mainSuppCode=qs.getString(1, "MAIN_SUPP_CODE");//责任厂商代码
		String claimNo=qs.getString(1, "CLAIM_NO");//索赔单号
		String faultName=qs.getString(1, "FAULT_NAME");//故障名称
		String faultCode=qs.getString(1, "FAULT_CODE");//故障代码
		String vin=qs.getString(1, "VIN");//底盘号
		String orgName=qs.getString(1, "ORG_NAME");//渠道商名称
		String orgCode=qs.getString(1, "ORG_CODE");//渠道商代码
		//+"质损原因:"+faultReason+"\r\n"
		content="渠道商代码:"+orgCode+"\r\n"+"渠道商名称:"+orgName+"\r\n"+"索赔单号:"+claimNo+"\r\n"+"底盘号:"+vin+"\r\n"+"故障代码:"+faultCode+"\r\n"+"故障名称:"+faultName+"\r\n"+"配件代码:"+partCode+"\r\n"+"配件名称:"+partName+"\r\n"+"配件数量:"+partCount+"\r\n"+"生产厂商:"+supplierCode+"\r\n"+"责任厂商:"+mainSuppCode;
		TwoDimensionCode handler = new TwoDimensionCode();
		bufImg=handler.encoderQRCode(content, "png", 20);
	}

try{
	//response.reset(); 
		 //ImageIO.write(bufImg, "png", response.getOutputStream());
		 JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
		 encoder.encode(bufImg); 
         out.clear();
         out = pageContext.pushBody();
		 //out.clear();   
		 //out = pageContext.pushBody();  

	}catch(Exception e)
	{
		e.printStackTrace();  
	}finally{
		if(factory != null)
			factory.getConnection().close();
		factory.setConnection(null);
		factory.setFactory(null);
		factory = null;
	}
%>