<%@page import="java.awt.image.BufferedImage"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="javax.imageio.ImageIO"%>
<% 

	BufferedImage bufImg=(BufferedImage)request.getAttribute("bufImg");
try{
		 response.setHeader("Pragma","No-cache");
		 response.setHeader("Cache-Control","no-cache");
		 response.setDateHeader("Expires", 0);
		 ImageIO.write(bufImg, "png", response.getOutputStream());
		 out.clear();   
		 out = pageContext.pushBody();  

	}catch(Exception e)
	{
		e.printStackTrace();  
	}
%>