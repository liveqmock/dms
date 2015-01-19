<!-- 
	 Title:系统框架空工程
	 Version:1.0
     Collator：andy.ten@tom.com
     Date：2011-10
     Remark：You can copy and/or use and/or modify this program free,
             but please reserve the segment above.
             Please mail me if you have any question.
-->
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%

	String key = request.getParameter("key");
	String title = request.getParameter("file");
	if(title == null) title="downLoad_table";
	title += ".xls";
	if(key != null && !key.equals(""))
	{
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-type","application/x-msdownload");
		response.setHeader("Accept-Ranges","bytes");
		response.setHeader("Content-Disposition","attachment; filename="+title);
		String str = (String) session.getAttribute(key);
		OutputStream os = response.getOutputStream();
    	String header = "<html><head><title></title>\n" +
	        "<meta http-equiv=\"Content-Type\" content=\"text/html;charset=UTF-8\">\n" +
	        "<meta name=ProgId content=Excel.Sheet>\n" +
	        "<style>\n" +
	        "<!--\n" +
	        "a {\n" +
	        "font-size:          9pt;\n" +
	        "color:              navy;\n" +
	        "text-decoration:    none;\n" +
	        "}\n" +
	        "a:hover {\n" +
	        "font-size:          9pt;\n" +
	        "color:              darkorange;\n" +
	        "text-decoration:    underline;\n" +
	        "}\n" +
	        "-->\n" +
	        "</style>\n" +
	        "</head>\n" +
	        "<body>\n";
	    os.write(header.getBytes("UTF-8"));
	    os.write(str.getBytes("UTF-8"));
	    os.write("</body></html>".getBytes("UTF-8"));
		os.flush();
		os.close();
		session.removeAttribute(key);
		return;
	}
	else
	{
	%>
		<%@ page contentType="text/html;charset=UTF-8"%>
		<html>
		<!-- 
			 Title:系统框架空工程
			 Version:1.0
		     Collator：andy.ten@tom.com
		     Date：2011-10
		     Remark：You can copy and/or use and/or modify this program free,
		             but please reserve the segment above.
		             Please mail me if you have any question.
		-->
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title></head>
		<body bgcolor="#F0FAFF" text="#FF8040" marginwidth="0" marginheight="0" leftmargin="5" topmargin="1">
		<script type="text/JavaScript">
		<!--
			alert("下载统计表错误！");
			<%
				session.removeAttribute(key);
			%>
		-->
		</script>
		</body></html>
		<%
				return;
	}
	%>