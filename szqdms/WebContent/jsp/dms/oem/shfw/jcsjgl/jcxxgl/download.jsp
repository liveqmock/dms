<%@ page language="java" import="java.io.*" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'download.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	</head>

	<%
		request.setCharacterEncoding("utf-8");
		String filename = request.getParameter("filename");
		filename = new String(filename.getBytes("UTF-8"), "UTF-8");
		OutputStream o = response.getOutputStream();
		byte b[] = new byte[500];
		/** * 得到文件的当前路径 * @param args */
		String serverpath = request.getRealPath("\\pldrmb\\");
		File fileLoad = new File(serverpath + "\\"+filename);
		System.out.print("++++++++"+fileLoad);
		response.setContentType("application/octet-stream");
		response.setHeader("content-disposition", "attachment; filename="+
				 new String(filename.getBytes("gb2312"),"ISO8859-1"));
		long fileLength = fileLoad.length();
		String length1 = String.valueOf(fileLength);
		response.setHeader("Content_Length", length1);
		FileInputStream in = new FileInputStream(fileLoad);
		int n;
		while ((n = in.read(b)) != -1) {
			o.write(b, 0, n);
		}
		in.close();
		out.clear();
		out = pageContext.pushBody();
	%>

	<body>
		<br>
	</body>
</html>
