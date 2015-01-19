<%@ page language="java" import="java.io.*" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    try {
        	//设置页面不缓存
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);
            request.setCharacterEncoding("utf-8");
            String filename = request.getParameter("filename");
            filename = new String(filename.getBytes("ISO-8859-1"), "UTF-8");
            OutputStream o = response.getOutputStream();
            byte b[] = new byte[500];
            /** * 得到文件的当前路径 * @param args */
            String serverpath = request.getRealPath("pldrmb");
            org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
            log.info("111filePath11:"+serverpath+ "/" + filename);
            File fileLoad = new File(serverpath + "/" + filename);
	   
            System.out.print("++++++++" + fileLoad);
            response.reset();
            response.setContentType("application/octet-stream");
            response.setHeader("content-disposition", "attachment;filename=" +
                    new String(filename.getBytes("gb2312"), "ISO8859-1"));
            long fileLength = fileLoad.length();
            System.out.println(fileLength);
            String length1 = String.valueOf(fileLength);
            //response.setHeader("Content_Length", length1);
            FileInputStream in = new FileInputStream(fileLoad);
            int n;
            while ((n = in.read(b)) != -1) {
                o.write(b, 0, n);
            }
            //in.close();
            out.flush();
            out.clear();
            out = pageContext.pushBody();
        }catch (Exception e){
            e.printStackTrace();
        }
%>
<html>
    <head>
    </head>
    <body>
        <br>
    </body>
</html>
