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
<%@page import="com.org.dms.dao.part.storageMng.barcodeMng.PartBarcodePrint2Dao"%>
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
	String partId=Pub.val(request, "PART_ID");
	String supplierId=Pub.val(request, "SUPPLIER_ID");
	DBFactory factory = new DBFactory();
	PartBarcodePrint2Dao dao = new PartBarcodePrint2Dao();
	try{
	QuerySet qs = dao.queryPartInfo(factory,partId,supplierId);
		BufferedImage bufImg=null;
		String content="";
		 if(qs.getRowCount() > 0){ 
			String partCode=qs.getString(1, "PART_CODE");//éä»¶ä»£ç 
			String partName = qs.getString(1, "PART_NAME");//éä»¶åç§°
			String supplierCode = qs.getString(1, "SUPPLIER_CODE");//ä¾åºåä»£ç 
			String supplierName = qs.getString(1, "SUPPLIER_NAME");//ä¾åºååç§°
			String MIN_PACK = qs.getString(1,"MIN_PACK");//æå°åè£æ°
			String barcode= dao.createBarcode(factory);
	        PtBuScanCodeBarcodeVO barcodeVO = new PtBuScanCodeBarcodeVO();
	        barcodeVO.setBarcode(barcode);
	        barcodeVO.setPartId(partId);
	        barcodeVO.setPartCode(partCode);
	        barcodeVO.setPartName(partName);
	        barcodeVO.setSupplierId(supplierId);
	        barcodeVO.setSupplierCode(supplierCode);
	        barcodeVO.setSupplierName(supplierName);
	        barcodeVO.setCounts(MIN_PACK);
	        dao.insertPartBarcode(factory,barcodeVO);
	        factory.getConnection().commit();
/* 			content="唯一码:"+barcode+""+"配件代码 :"+partCode+""+"配件名称:"+partName+""+"供应商名称:"+supplierName; */

			String name = "";
			if(partName.length()>20){
				name = partName.substring(0, 20);
			}else{
				name = partName;
			}
			String context="";
            context+="唯一码:"+barcode;
            context+="配件代码:"+partCode;
            context+="配件名称:"+name;
            context+="供应商名称:"+supplierName;
			TwoDimensionCode handler = new TwoDimensionCode();
			bufImg=handler.encoderQRCode(context, "png", 10);
		 } 
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