<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
	List list = null;
	String bType="",bParams="";
	try
	{
		bType = (String)request.getAttribute("bType");
		bParams = (String)request.getAttribute("bParams");
		list = (List)request.getAttribute("itemslist");
	}catch(Exception e)
	{}
	
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=request.getContextPath() %>/themes/default/style.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/themes/css/core.css" rel="stylesheet" type="text/css" />
<title>成功列表</title>
<script type="text/javascript">
function doConfirm()
{
	parent.doImportConfirm("<%=bType%>","<%=bParams%>");
	try{
		var dialog = parent.$("body").data("importXls");
		parent.$.pdialog.close(dialog);
	}catch(e){}
}
</script>
</head>
<body>
	<%
		if(list != null)
		{
	%>	
			<div class="page">
			<div class="pageContent">
			<div  style="width:100%;height:300px;overflow:auto;">
			<table class="dlist" style="width:100%;">
				<thead>
					<tr>
					<%
						int columns = 0;
						if(list != null && list.size()>0)
						{
							String[] row1 = (String[])list.get(0);
							
							for(int n=0;n<row1.length;n++)
							{
								if(!"".equals(row1[n]))
								{
									out.println("<th>"+row1[n]+"</th>");
									columns ++;
								}
							}
						}
					%>
					</tr>
				</thead>
				<tbody>
				<%		
					String[] obj;
					for(int i=1;i<list.size();i++)
					{
						out.println("<tr>");
						obj = (String[])list.get(i);
						for(int j=0;j<columns;j++)
						{
							out.println("<td>");
							out.println("<div>"+obj[j]+"</div>");
							out.println("</td>");
						}
						out.println("</tr>");
					}
				%>
				</tbody>
			</table>
			</div>
			<table border=0 align="center">
			 	<tr><td align=center>
			 	<input type="button" value="确定" onclick="javascript:doConfirm()" />
			 	<input type="button" value="返回" onclick="javascript:history.go(-1);" />
			 	</td></tr>
	    	</table>
			</div>
			</div>
	<%	
		}
	%>
</body>
</html>