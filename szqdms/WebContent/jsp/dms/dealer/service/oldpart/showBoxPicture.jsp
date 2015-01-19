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
<%@page import="com.org.dms.dao.service.oldpartMng.OldPartPrintDao"%>
<%@page import="com.org.dms.common.TwoDimensionCode"%>
<%@ page import="com.sun.image.codec.jpeg.JPEGCodec,com.sun.image.codec.jpeg.JPEGImageEncoder"%>
<% 
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);
		response.reset();
		String boxNo=Pub.val(request, "boxNo");
		BufferedImage bufImg=null;
		
		String name= new String(boxNo.getBytes("ISO-8859-1"),"UTF-8");
		String content="";
		content=name;
		TwoDimensionCode handler = new TwoDimensionCode();
		bufImg=handler.encoderQRCode(content, "png", 10);

	try{
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
		encoder.encode(bufImg); 
        out.clear();
        out = pageContext.pushBody();
	}catch(Exception e)
	{
		e.printStackTrace();  
	}
%>