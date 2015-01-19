<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
<%@page import="com.org.dms.dao.part.storageMng.barcodeMng.PosiBarcodePrint2Dao"%>
<%@page import="com.org.dms.common.TwoDimensionCode"%>
<%@page import="com.org.dms.vo.part.PtBuScanCodeBarcodeVO" %>
<%@ page import="com.sun.image.codec.jpeg.JPEGCodec,com.sun.image.codec.jpeg.JPEGImageEncoder"%>
<% 
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
response.reset();
	//ActionContext atx = ActionContext.getContext();
	User user = (User) request.getSession().getAttribute(Globals.USER_KEY);
	String POSITION_ID=Pub.val(request, "POSITION_ID");
	PosiBarcodePrint2Dao dao = new PosiBarcodePrint2Dao();
	DBFactory factory = new DBFactory();
	
	QuerySet qs = dao.queryPosition(factory,POSITION_ID);
	BufferedImage bufImg=null;
	String content="";
	 if(qs.getRowCount() > 0){ 
		String POSITION_CODE=qs.getString(1, "POSITION_CODE");//éä»¶ä»£ç 
		String POSITION_NAME = qs.getString(1, "POSITION_NAME");//éä»¶åç§°
		
        
		content="库位代码:"+POSITION_CODE+";"+"库位名称:"+POSITION_NAME;
		TwoDimensionCode handler = new TwoDimensionCode();
		bufImg=handler.encoderQRCode(content, "png", 7);
	 } 

try{
		 JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
		 encoder.encode(bufImg); 
         out.clear();
         out = pageContext.pushBody();

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