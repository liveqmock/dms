<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.awt.image.BufferedImage"%>
<%@ page import="com.org.dms.common.TwoDimensionCode"%>
<%@ page import="com.sun.image.codec.jpeg.JPEGCodec,com.sun.image.codec.jpeg.JPEGImageEncoder"%>
<%@page import="com.org.framework.common.DBFactory"%>
<%@page import="com.org.dms.dao.service.oldpartMng.OldPartPrintPJGLDao"%>
<%@page import="com.org.framework.common.QuerySet" %>
<%
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
	response.reset();
	
	String applyId = request.getParameter("applyId");
	OldPartPrintPJGLDao dao = new OldPartPrintPJGLDao();
	DBFactory factory = dao.getFactory();
	String content="";
	try
	{
		QuerySet qs = dao.queryPartBarCode(applyId );
		if(qs.getRowCount() > 0){
			String orgName=qs.getString(1, "ORG_NAME");							// 渠道商名称
			String orgCode=qs.getString(1, "ORG_CODE");							// 渠道商代码
			String applyNo = qs.getString(1, "APPLY_NO");						// 申请单号码
			String partCode=qs.getString(1, "PART_CODE");						// 配件代码
			String partName=qs.getString(1, "PART_NAME");
			partName = (partName == null) ? "" : partName.replace(",", "，");						// 配件名称
			String claimCount = qs.getString(1, "CLAIM_COUNT");					// 索赔数量
			String supplierCode = qs.getString(1, "SUPPLIER_CODE");				// 供应商代码
			String supplierName = qs.getString(1, "SUPPLIER_NAME");				// 供应商名称
			
			String faultReason=qs.getString(1, "FAULT_CONDITONS");
			faultReason = (faultReason == null) ? "" : faultReason.replace(",", "，");				// 故障情况
			content = "渠道商代码:" + orgCode + "\r\n" + 
					  "渠道商名称:" + orgName + "\r\n" +
					  "申请单号:"   + applyNo + "\r\n" +
					  "配件代码:"   + partCode + "\r\n" +
					  "配件名称:" + partName + "\r\n" + 
					  "索赔数量:" + claimCount + "\r\n" + 
					  "供应商代码:" + supplierCode + "\r\n" + 
					  "供应商名称:" + supplierName + "\r\n" + 
					  "故障情况:" + faultReason; 
			System.out.println(content);
		}
	} catch(Exception e){
		e.printStackTrace();
	} finally {
		if(factory != null){
			factory.getConnection().close();
		}
		factory.setFactory(null);
	}


	System.out.println("配件旧件标签打印内容：" + content);
	TwoDimensionCode handler = new TwoDimensionCode();
	BufferedImage bufImg = handler.encoderQRCode(content, "png", 20);
	try{
		 JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
		 encoder.encode(bufImg); 
         out.clear();
         out = pageContext.pushBody();
	}catch(Exception e){
		e.printStackTrace();  
	}finally{
		
	}
%>