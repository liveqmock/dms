package com.org.dms.action.part.purchaseMng.purchaseOrder;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.org.dms.dao.part.purchaseMng.purchaseOrder.PurchaseOrderDeliveryPrintDao;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

public class PurchaseOrderDeliveryPrintAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private PurchaseOrderDeliveryPrintDao dao = PurchaseOrderDeliveryPrintDao.getInstance(atx);
	    
	    public static Rectangle saleDetailRectangle = new Rectangle(520f,800f);//(518f

		public static float MARGINLEFT =0f;//31.181f;

		public static float MARGINRIGHT =0f;

		public static float MARGINTOP =0f;

		public static float MARGINBOTTOM =0f;

			// 销售信息明细表列宽数组
		
		public static float tableWidths[] = {172f,172f,172f};

			// 通用表格的总宽度
		public static float tableTotalWidth =515.91f;
		
		public static float tableTotalWidth2 = 172.0F;
		
		public static float[] tableWidths2 = { 120.0F, 52.0F };

	    
	    public void orderSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.orderSearch(page,user,conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    
	    public void printShipPdf()throws Exception{
	    	RequestWrapper request = atx.getRequest();
	    	ResponseWrapper response =  atx.getResponse();
	    	User user = (User) atx.getSession().get(Globals.USER_KEY);
	    	String SPLIT_ID = Pub.val(request, "SPLIT_ID");
	    	
	    	response.getHttpResponse().reset();
	    	response.getHttpResponse().setContentType("application/pdf");
	    	response.getHttpResponse().setHeader("content-disposition", "filename="
	    			+ new String("供应商发货清单".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

	    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
	    	DoPrint doPrint = new DoPrint();
	    	doPrint.createPdf(user,SPLIT_ID,request.getHttpRequest(),response.getHttpResponse(),out);
	    	out.close();
	    }	
	    
	    private class DoPrint extends PdfPageEventHelper {
	    	private PdfPTable table;

	    	private PdfPCell cell;

	    	private Document document;
	    	
	    	private BaseFont bfChinese;
	    	
	    	private PdfTemplate total;// 总页数
	    	private BaseFont helv;//分页信息中的字体
	    	private int pagerFontSize=11;//分页信息中的字体大小
	    	private float pagerFixX=524.41054f;
	    	private float pagerFixY=290.402f;
	    	
	    	private String pagerInfo1="第";
	    	private String pagerInfo2="页，共";
	    	private String pagerInfo3="页";

	    	public void createPdf(User user,String ISSUE_ID,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
	    	//设置字体
	    	String fontPath = "/css/simsun.ttc";
	    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
	    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
	        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
	    	
	    	 document=new Document(new Rectangle(637.79544f,354.33072f),70.866f,36.85f,36.85f,36.85f);
	    	
	    	try{
	    		PdfWriter writer=PdfWriter.getInstance(document,out);
	    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
	    		writer.setPageEvent(this);
	    		
	    		//查询发料单主信息
	    		QuerySet info = dao.queryPchOrderInfo(user, ISSUE_ID);
	    		String SUPPLIER_NAME = info.getString(1, "SUPPLIER_NAME");
	    		String SPLIT_NO = info.getString(1, "SPLIT_NO");
	    		String printDate = info.getString(1, "PRINT_DATE");
	    		String [] printDates =printDate.split("-");
	    		
	    				document.open();
	    				float[] widths={26.928f,21.25f,76.53f,119.68f,40.87f,62.67f,69.8f,69.8f,42.52f};
	    				
	    				table=new PdfPTable(widths);
	    				table.setTotalWidth(530.0784f);
	    				table.setLockedWidth(true);
	    				table.setHeaderRows(3);
	    			
	    				 cell = new PdfPCell(new Paragraph("发货方:"+SUPPLIER_NAME,chineseFont));
	    				 cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    				 cell.setBorder(PdfPCell.NO_BORDER);
	    				 cell.setColspan(5);
	    				 table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("订单号:"+SPLIT_NO,chineseFont));
	    				cell.setNoWrap(true);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				cell.setColspan(4);
	    				table.addCell(cell);
	    				
	    						 
	    				cell = new PdfPCell(new Paragraph("接收方:陕重汽配件公司",chineseFont));
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    				cell.setColspan(5);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
	    				cell.setColspan(2);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell();
	    				cell.setColspan(2);
	    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				table.addCell(cell);		
	    				
	    				cell = new PdfPCell(new Paragraph("序号",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("配件编号",chineseFont));
	    				cell.setColspan(2);
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("配件名称",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("单位",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("最小包装数",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("订购数量",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("发货数量",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("备注",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				//查询发料单明细信息
	    				QuerySet qs = dao.queryPchOrderDtl(user,ISSUE_ID);
	    				if(qs.getRowCount()>0){
	    					for(int f=0;f<qs.getRowCount();f++){
	    			
	    					String sf=f+1+"";
	    					cell = new PdfPCell(new Paragraph(sf, chineseFont));
	    					cell.setNoWrap(false);
	    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_CODE"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					cell.setColspan(2);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_NAME"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "MIN_PACK"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PCH_COUNT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "SHIPPING_AMOUNT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    			
	    					cell = new PdfPCell(new Paragraph("", chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    				}//end for
	    				}//end if
	    				document.add(table);
	    			
	    		
	    	}catch(Exception e){
	    		throw new Exception(e);
	    	}
	    	finally{
	    		document.close();
	    		out.flush();
	    	}
	    		
	    }
	    	/**
	    	 * 文档关闭时调用此方法
	    	 */
	    	public void onCloseDocument(PdfWriter writer, Document document) {
	    		total.beginText();
	    		total.setFontAndSize(helv, pagerFontSize);
	    		total.setTextMatrix(0, 0);
	    		total.showText(String.valueOf(writer.getPageNumber() - 1)+pagerInfo3);
	    		total.endText();
	    	}

	    	/**
	    	 * 页面结束时调用此方法
	    	 */
	    	public void onEndPage(PdfWriter writer, Document document) {
	    		PdfContentByte cb = writer.getDirectContent();
	    		cb.saveState();
	    		String text = pagerInfo1 + writer.getPageNumber() + pagerInfo2;
	    		float textSize = helv.getWidthPoint(text, pagerFontSize);
	    		cb.beginText();
	    		cb.setFontAndSize(helv, pagerFontSize);
	            cb.setTextMatrix(pagerFixX, pagerFixY);
	    		cb.showText(text);
	    		cb.endText();
	    		cb.addTemplate(total, pagerFixX+textSize, pagerFixY);

	    		cb.restoreState();
	    	}

	    	/**
	    	 * 文档打开时调用此方法
	    	 */
	    	public void onOpenDocument(PdfWriter writer, Document document) {
	    		total = writer.getDirectContent().createTemplate(114f, 18.8f);
	    		total.setBoundingBox(new Rectangle(-200,-200,20, 20));
	    		try {
	    			helv = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI,
	    					BaseFont.NOT_EMBEDDED);
	    			
	    			helv=bfChinese;
	    		} catch (Exception e) {
	    			throw new ExceptionConverter(e);
	    		}
	    	}

	    }

	    public void printDirShipPdf()throws Exception{
	    	RequestWrapper request = atx.getRequest();
	    	ResponseWrapper response =  atx.getResponse();
	    	User user = (User) atx.getSession().get(Globals.USER_KEY);
	    	String SPLIT_ID = Pub.val(request, "SPLIT_ID");
	    	
	    	response.getHttpResponse().reset();
	    	response.getHttpResponse().setContentType("application/pdf");
	    	response.getHttpResponse().setHeader("content-disposition", "filename="
	    			+ new String("供应商发货清单".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

	    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
	    	DoPrint1 doPrint = new DoPrint1();
	    	doPrint.createPdf(user,SPLIT_ID,request.getHttpRequest(),response.getHttpResponse(),out);
	    	out.close();
	    }	
	    
	    private class DoPrint1 extends PdfPageEventHelper {
	    	private PdfPTable table;

	    	private PdfPCell cell;

	    	private Document document;
	    	
	    	private BaseFont bfChinese;
	    	
	    	private PdfTemplate total;// 总页数
	    	private BaseFont helv;//分页信息中的字体
	    	private int pagerFontSize=11;//分页信息中的字体大小
	    	private float pagerFixX=524.41054f;
	    	private float pagerFixY=290.402f;
	    	
	    	private String pagerInfo1="第";
	    	private String pagerInfo2="页，共";
	    	private String pagerInfo3="页";

	    	public void createPdf(User user,String ISSUE_ID,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
	    	//设置字体
	    	String fontPath = "/css/simsun.ttc";
	    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
	    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
	        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
	    	
	    	 document=new Document(new Rectangle(637.79544f,354.33072f),70.866f,36.85f,36.85f,36.85f);
	    	
	    	try{
	    		PdfWriter writer=PdfWriter.getInstance(document,out);
	    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
	    		writer.setPageEvent(this);
	    		
	    		//查询发料单主信息
	    		QuerySet info = dao.queryDirPchOrderInfo(user, ISSUE_ID);
	    		String SUPPLIER_NAME = info.getString(1, "SUPPLIER_NAME");
	    		String ORG_NAME = info.getString(1, "ORG_NAME");
	    		String ORDER_NO = info.getString(1, "ORDER_NO");
	    		String SPLIT_NO = info.getString(1, "SPLIT_NO");
	    		String DELIVERY_ADDR = info.getString(1, "DELIVERY_ADDR");
	    		String printDate = info.getString(1, "PRINT_DATE");
	    		String [] printDates =printDate.split("-");
	    		
	    				document.open();
	    				float[] widths={26.928f,21.25f,76.53f,119.68f,40.87f,62.67f,69.8f,69.8f,42.52f};
	    				
	    				table=new PdfPTable(widths);
	    				table.setTotalWidth(530.0784f);
	    				table.setLockedWidth(true);
	    				table.setHeaderRows(3);
	    			
	    				 cell = new PdfPCell(new Paragraph("发货方:"+SUPPLIER_NAME,chineseFont));
	    				 cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	    				 cell.setBorder(PdfPCell.NO_BORDER);
	    				 cell.setColspan(5);
	    				 table.addCell(cell);
	    				 
	    				cell = new PdfPCell(new Paragraph("采购订单号:"+SPLIT_NO,chineseFont));
	    				cell.setNoWrap(true);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    				cell.setColspan(2);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
	    				cell.setColspan(2);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				table.addCell(cell);
	    						 
	    				cell = new PdfPCell(new Paragraph("接收方:"+ORG_NAME,chineseFont));
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    				cell.setColspan(5);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("销售订单号:"+ORDER_NO,chineseFont));
	    				cell.setNoWrap(true);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    				cell.setColspan(2);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell();
	    				cell.setColspan(2);
	    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				table.addCell(cell);	
	    				
	    				cell = new PdfPCell(new Paragraph("收货地址:"+DELIVERY_ADDR,chineseFont));
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    				cell.setColspan(9);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("序号",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("配件编号",chineseFont));
	    				cell.setColspan(2);
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("配件名称",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("单位",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("最小包装数",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("订购数量",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("发货数量",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("备注",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				//查询发料单明细信息
	    				QuerySet qs = dao.queryPchOrderDtl(user,ISSUE_ID);
	    				if(qs.getRowCount()>0){
	    					for(int f=0;f<qs.getRowCount();f++){
	    			
	    					String sf=f+1+"";
	    					cell = new PdfPCell(new Paragraph(sf, chineseFont));
	    					cell.setNoWrap(false);
	    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_CODE"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					cell.setColspan(2);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_NAME"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "MIN_PACK"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PCH_COUNT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "SHIPPING_AMOUNT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    			
	    					cell = new PdfPCell(new Paragraph("", chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    				}//end for
	    				}//end if
	    				document.add(table);
	    			
	    		
	    	}catch(Exception e){
	    		throw new Exception(e);
	    	}
	    	finally{
	    		document.close();
	    		out.flush();
	    	}
	    		
	    }
	    	/**
	    	 * 文档关闭时调用此方法
	    	 */
	    	public void onCloseDocument(PdfWriter writer, Document document) {
	    		total.beginText();
	    		total.setFontAndSize(helv, pagerFontSize);
	    		total.setTextMatrix(0, 0);
	    		total.showText(String.valueOf(writer.getPageNumber() - 1)+pagerInfo3);
	    		total.endText();
	    	}

	    	/**
	    	 * 页面结束时调用此方法
	    	 */
	    	public void onEndPage(PdfWriter writer, Document document) {
	    		PdfContentByte cb = writer.getDirectContent();
	    		cb.saveState();
	    		String text = pagerInfo1 + writer.getPageNumber() + pagerInfo2;
	    		float textSize = helv.getWidthPoint(text, pagerFontSize);
	    		cb.beginText();
	    		cb.setFontAndSize(helv, pagerFontSize);
	            cb.setTextMatrix(pagerFixX, pagerFixY);
	    		cb.showText(text);
	    		cb.endText();
	    		cb.addTemplate(total, pagerFixX+textSize, pagerFixY);

	    		cb.restoreState();
	    	}

	    	/**
	    	 * 文档打开时调用此方法
	    	 */
	    	public void onOpenDocument(PdfWriter writer, Document document) {
	    		total = writer.getDirectContent().createTemplate(114f, 18.8f);
	    		total.setBoundingBox(new Rectangle(-200,-200,20, 20));
	    		try {
	    			helv = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI,
	    					BaseFont.NOT_EMBEDDED);
	    			
	    			helv=bfChinese;
	    		} catch (Exception e) {
	    			throw new ExceptionConverter(e);
	    		}
	    	}

	    }
}
