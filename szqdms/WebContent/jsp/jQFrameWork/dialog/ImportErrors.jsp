<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.org.framework.fileimport.ExcelErrors"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	List list = null;
	String bType,bRow,bCol;
	try
	{
		list = (List)request.getAttribute("itemslist");
		bType = (String)request.getAttribute("bType");
		bRow = (String)request.getAttribute("bRow");
		bCol = (String)request.getAttribute("bCol"); 
		System.out.println("错误列表-----------"+list.size());
	}catch(Exception e)
	{e.printStackTrace();}
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=request.getContextPath() %>/themes/default/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/themes/css/core.css" rel="stylesheet" type="text/css" />
<title>成功列表</title>
</head>
<body>
	<%
		if(list != null)
		{
	%>	<div class="page">
		<div class="pageContent">
		<div  style="width:100%;height:300px;overflow:auto;">
		<table class="dlist" style="width:100%;">
			<thead>
				<tr>
		           <th>序号</th>
		           <th>错误列表</th>
	        	</tr>
			</thead>
			<tbody>
			
	<%		
			int len = list.size();
			for(int i=0;i<len;i++)
			{
				ExcelErrors s = new ExcelErrors();
				out.println("<tr style='height:20px;line-height:20px;'>");
				s = (ExcelErrors)list.get(i);
				out.println("<td style='width:120px;'><div>"+"第["+s.getRowNum()+"]行"+"</div></td>");
				out.println("<td><div>"+s.getErrorDesc()+"</div></td>");
				out.println("</tr>");
				//只输出前1000个错误
				if(i>1000)
					break;
			}
	%>
			</tbody>
		</table>
	<%	
		}
	%>
		</div>
		<table class="table_edit" align=center width="95%" id="role">
				<tr>
			 		<td align="center">
			 		<input type="button" value="导出错误数据" id="btn-exportError1" class="normal_btn"/>
					<input type="button" value="返回" onclick="history.back();" class="normal_btn"/>
					</td>
				</tr>
		</table>
		</div>
		</div>
</body>
</html>