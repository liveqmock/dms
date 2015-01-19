package com.org.dms.action.part.storageMng.stockOutMng;

import java.awt.Color;
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
import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.storageMng.stockOutMng.DealerOutPrintMngDao;
import com.org.dms.vo.part.PtBuDealerPrintLogVO;
import com.org.dms.vo.part.PtBuRealSaleVO;
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





public class DealerOutPrintMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private DealerOutPrintMngDao dao = DealerOutPrintMngDao.getInstance(atx);
	    
	    
	    public static Rectangle saleDetailRectangle_new = new Rectangle(609.44882f,
				793.70079f);

		//表格与纸张之间的空白
		public static float MARGINLEFT_NEW = 23.364f;

		public static float MARGINRIGHT_NEW = 33.364f;

		public static float MARGINTOP_NEW = 28.364f;

		public static float MARGINBOTTOM_NEW = 51.0236f;

			//信息明细表列宽数组
		public static float tableWidths_new[] = { 26.929f,90.055f,198.079f,34.016f,34.016f,52.441f,70.866f,48.189f};

			//通用表格的总宽度
		public static float tableTotalWidth_new = 552.7559f;
	    
	    public void outSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.outSearch(page,user,conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    
	    public void printRealPdf()throws Exception{
	    	RequestWrapper request = atx.getRequest();
	    	ResponseWrapper response =  atx.getResponse();
	    	User user = (User) atx.getSession().get(Globals.USER_KEY);
	    	String ORDER_ID = Pub.val(request, "ORDER_ID");
	    	String flag = Pub.val(request, "flag");
	    	printOrder2(ORDER_ID);
	    	
	    	response.getHttpResponse().reset();
	    	response.getHttpResponse().setContentType("application/pdf");
	    	response.getHttpResponse().setHeader("content-disposition", "filename="
	    			+ new String("实销单".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

	    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
	    	DoPrint1 doPrint = new DoPrint1();
	    	doPrint.createPdf(user,ORDER_ID,request.getHttpRequest(),response.getHttpResponse(),out);
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

	    	public void createPdf(User user,String ORDER_ID,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
	    	//设置字体
	    	String fontPath = "/css/simsun.ttc";
	    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
	    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
	        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
	        Font chineseFontBig = new Font(bfChinese, 14, Font.BOLD, Color.BLACK);
	        Document document=new Document(saleDetailRectangle_new, MARGINLEFT_NEW,MARGINRIGHT_NEW, MARGINTOP_NEW, MARGINBOTTOM_NEW);
	    	try{
	    		PdfWriter writer=PdfWriter.getInstance(document,out);
	    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
	    		writer.setPageEvent(this);
	    		float  pchAmount = 0;
	    		//查询发料单主信息
	    		QuerySet info = dao.queryRealInfo(user, ORDER_ID);
				String ORG_NAME = info.getString(1, "ORG_NAME");
				String OUT_NO = info.getString(1, "OUT_NO");
				String CUST_NAME = info.getString(1, "CUST_NAME");
				String printDate = info.getString(1, "PRINT_DATE");
				String [] printDates =printDate.split("-");
	    		
	    				document.open();
	    				float[] widths={ 26.929f,90.055f,198.079f,34.016f,34.016f,52.441f,70.866f,48.189f};
	    				
	    				table=new PdfPTable(widths);
	    				table.setTotalWidth(530.0784f);
	    				table.setLockedWidth(true);
	    				table.setHeaderRows(3);
	    			
	    				 cell = new PdfPCell(new Paragraph("区域中心实销单",chineseFontBig));
	    				 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    				 cell.setBorder(PdfPCell.NO_BORDER);
	    				 cell.setColspan(8);
	    				 table.addCell(cell);
	    						 
	    				cell = new PdfPCell(new Paragraph("区域中心:"+ORG_NAME,chineseFont));
	    				cell.setColspan(4);
	    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("编号:"+OUT_NO,chineseFont));
	    				cell.setNoWrap(true);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				cell.setColspan(4);
	    				table.addCell(cell);
	    				
	    						 
	    				cell = new PdfPCell(new Paragraph("客户名称:"+CUST_NAME,chineseFont));
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    				cell.setColspan(3);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
	    				cell.setColspan(3);
	    				cell.setBorder(PdfPCell.NO_BORDER);
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
	    				
	    				cell = new PdfPCell(new Paragraph("数量",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("零售价",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("金额",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("备注",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				//查询发料单明细信息
	    				QuerySet qs = dao.queryRealDtlInfo(user,ORDER_ID);
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
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_NAME"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "SALE_COUNT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "SALE_PRICE"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "AMOUNT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    			
	    					cell = new PdfPCell(new Paragraph("", chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					pchAmount = pchAmount + Float.parseFloat(qs.getString(f+1, "AMOUNT"));
	    				}//end for
	    					
	    					double pAmount1 =  Math.floor(pchAmount *100+.5)/100;
	    					cell = new PdfPCell(new Paragraph("合计：",chineseFont));
	           				cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	           				cell.setColspan(5);
	           				table.addCell(cell);
	           				
	           				cell = new PdfPCell(new Paragraph("",chineseFont));
	           				cell.setNoWrap(true);
	           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           				cell.setColspan(1);
	           				table.addCell(cell);
	           				
	           				cell = new PdfPCell(new Paragraph(String.valueOf(pAmount1),chineseFont));
	           				cell.setNoWrap(true);
	           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           				cell.setColspan(1);
	           				table.addCell(cell);
	           				
	           				cell = new PdfPCell(new Paragraph("",chineseFont));
	           				cell.setNoWrap(true);
	           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           				cell.setColspan(1);
	           				table.addCell(cell);
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
	    		total.endText();
	    	}

	    	/**
	    	 * 页面结束时调用此方法
	    	 */
	    	public void onEndPage(PdfWriter writer, Document document) {
	    		PdfContentByte cb = writer.getDirectContent();
	    		cb.saveState();
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
	    
	    public void printRealSale(String ORDER_ID) throws Exception {
	        //获取封装后的request对象
	        RequestWrapper request = atx.getRequest();
	        //获取封装后的response对象
	        //ResponseWrapper response = atx.getResponse();
	        //获取当前登录user对象
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try {

	            PtBuRealSaleVO vo = new PtBuRealSaleVO();
	            vo.setSaleId(ORDER_ID);
	            vo.setPrintDate(Pub.getCurrentDate());
	            vo.setPrintStatus(DicConstant.DYZT_02);

	            vo.setUpdateUser(user.getAccount());
	            vo.setUpdateTime(Pub.getCurrentDate());

	            dao.updateRealSale(vo);

	            //返回插入结果和成功信息
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    public void printSalePdf()throws Exception{
	    	RequestWrapper request = atx.getRequest();
	    	ResponseWrapper response =  atx.getResponse();
	    	User user = (User) atx.getSession().get(Globals.USER_KEY);
	    	String ORDER_ID = Pub.val(request, "ORDER_ID");
	    	String flag = Pub.val(request, "flag");
	    	if("1".equals(flag)){
	    		printOrder2(ORDER_ID);
	    	}else{
	    		printOrder(ORDER_ID);
	    	}
	    	response.getHttpResponse().reset();
	    	response.getHttpResponse().setContentType("application/pdf");
	    	response.getHttpResponse().setHeader("content-disposition", "filename="
	    			+ new String("发料单".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

	    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
	    	DoPrint doPrint = new DoPrint();
	    	doPrint.createPdf(user,ORDER_ID,request.getHttpRequest(),response.getHttpResponse(),out);
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

	    	public void createPdf(User user,String ORDER_ID,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
	    	//设置字体
	    	String fontPath = "/css/simsun.ttc";
	    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
	    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
	        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
	        Font chineseFontBig = new Font(bfChinese, 14, Font.BOLD, Color.BLACK);
	    	
	        Document document=new Document(saleDetailRectangle_new, MARGINLEFT_NEW,MARGINRIGHT_NEW, MARGINTOP_NEW, MARGINBOTTOM_NEW);
	    	
	    	try{
	    		PdfWriter writer=PdfWriter.getInstance(document,out);
	    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
	    		writer.setPageEvent(this);
	    		float  pchAmount = 0;
	    		//查询发料单主信息
	    		QuerySet info = dao.querySaleInfo(user, ORDER_ID);
				String ORG_NAME = info.getString(1, "ORG_NAME");
				String OUT_NO = info.getString(1, "OUT_NO");
				String CUST_NAME = info.getString(1, "CUST_NAME");
				String printDate = info.getString(1, "PRINT_DATE");
				String [] printDates =printDate.split("-");
	    		
	    				document.open();
	    				float[] widths={ 26.929f,90.055f,198.079f,34.016f,34.016f,52.441f,70.866f,48.189f};
	    				
	    				table=new PdfPTable(widths);
	    				table.setTotalWidth(530.0784f);
	    				table.setLockedWidth(true);
	    				table.setHeaderRows(3);
	    			
	    				 cell = new PdfPCell(new Paragraph("区域中心出库单(销售)",chineseFontBig));
	    				 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    				 cell.setBorder(PdfPCell.NO_BORDER);
	    				 cell.setColspan(8);
	    				 table.addCell(cell);
	    						 
	    				cell = new PdfPCell(new Paragraph("区域中心:"+ORG_NAME,chineseFont));
	    				cell.setColspan(4);
	    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("编号:"+OUT_NO,chineseFont));
	    				cell.setNoWrap(true);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				cell.setColspan(4);
	    				table.addCell(cell);
	    				
	    						 
	    				cell = new PdfPCell(new Paragraph("客户名称:"+CUST_NAME,chineseFont));
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    				cell.setColspan(3);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
	    				cell.setColspan(3);
	    				cell.setBorder(PdfPCell.NO_BORDER);
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
	    				
	    				cell = new PdfPCell(new Paragraph("数量",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("零售价",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("金额",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("备注",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				//查询发料单明细信息
	    				QuerySet qs = dao.querySaleDtlInfo(user,ORDER_ID);
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
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_NAME"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "SALE_COUNT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "SALE_PRICE"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "AMOUNT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    			
	    					cell = new PdfPCell(new Paragraph("", chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					pchAmount = pchAmount + Float.parseFloat(qs.getString(f+1, "AMOUNT"));
	    				}//end for
	    					
	    					double pAmount1 =  Math.floor(pchAmount *100+.5)/100;
	    					cell = new PdfPCell(new Paragraph("合计：",chineseFont));
	           				cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	           				cell.setColspan(5);
	           				table.addCell(cell);
	           				
	           				cell = new PdfPCell(new Paragraph("",chineseFont));
	           				cell.setNoWrap(true);
	           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           				cell.setColspan(1);
	           				table.addCell(cell);
	           				
	           				cell = new PdfPCell(new Paragraph(String.valueOf(pAmount1),chineseFont));
	           				cell.setNoWrap(true);
	           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           				cell.setColspan(1);
	           				table.addCell(cell);
	           				
	           				cell = new PdfPCell(new Paragraph("",chineseFont));
	           				cell.setNoWrap(true);
	           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           				cell.setColspan(1);
	           				table.addCell(cell);
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
	    		total.endText();
	    	}

	    	/**
	    	 * 页面结束时调用此方法
	    	 */
	    	public void onEndPage(PdfWriter writer, Document document) {
	    		PdfContentByte cb = writer.getDirectContent();
	    		cb.saveState();
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
	    
	    
	    
	    
	    
	    
	    public void inSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.inSearch(page,user,conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    
	    public void printSaleInPdf()throws Exception{
	    	RequestWrapper request = atx.getRequest();
	    	ResponseWrapper response =  atx.getResponse();
	    	User user = (User) atx.getSession().get(Globals.USER_KEY);
	    	String ORDER_ID = Pub.val(request, "ORDER_ID");
	    	String flag = Pub.val(request, "flag");
	    	printOrder(ORDER_ID);
	    	response.getHttpResponse().reset();
	    	response.getHttpResponse().setContentType("application/pdf");
	    	response.getHttpResponse().setHeader("content-disposition", "filename="
	    			+ new String("销售订单入库单".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

	    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
	    	DoPrint2 doPrint = new DoPrint2();
	    	doPrint.createPdf(user,ORDER_ID,request.getHttpRequest(),response.getHttpResponse(),out);
	    	out.close();
	    }	
	    	
	    	
	    private class DoPrint2 extends PdfPageEventHelper {
	    	private PdfPTable table;

	    	private PdfPCell cell;

	    	private Document document;
	    	
	    	private BaseFont bfChinese;
	    	
	    	private PdfTemplate total;// 总页数
	    	private BaseFont helv;//分页信息中的字体
	    	private int pagerFontSize=11;//分页信息中的字体大小
	    	private float pagerFixX=524.41054f;
	    	private float pagerFixY=290.402f;
	    	
//	    	private String pagerInfo1="第";
//	    	private String pagerInfo2="页，共";
//	    	private String pagerInfo3="页";

	    	public void createPdf(User user,String ORDER_ID,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
	    	//设置字体
	    	String fontPath = "/css/simsun.ttc";
	    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
	    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
	        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
	        Font chineseFontBig = new Font(bfChinese, 14, Font.BOLD, Color.BLACK);
	        Document document=new Document(saleDetailRectangle_new, MARGINLEFT_NEW,MARGINRIGHT_NEW, MARGINTOP_NEW, MARGINBOTTOM_NEW);
	    	try{
	    		PdfWriter writer=PdfWriter.getInstance(document,out);
	    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
	    		writer.setPageEvent(this);
	    		float  pchAmount = 0;
	    		//查询发料单主信息
	    		QuerySet info = dao.querySaleIn(user, ORDER_ID);
				String ORG_NAME = info.getString(1, "ORG_NAME");
				String IN_NO = info.getString(1, "IN_NO");
				String printDate = info.getString(1, "PRINT_DATE");
				String [] printDates =printDate.split("-");
	    		
	    				document.open();
	    				float[] widths={ 26.929f,90.055f,198.079f,34.016f,34.016f,52.441f,70.866f,48.189f};
	    				
	    				table=new PdfPTable(widths);
	    				table.setTotalWidth(530.0784f);
	    				table.setLockedWidth(true);
	    				table.setHeaderRows(3);
	    			
	    				 cell = new PdfPCell(new Paragraph("陕重汽配件经销商入库单",chineseFontBig));
	    				 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    				 cell.setBorder(PdfPCell.NO_BORDER);
	    				 cell.setColspan(8);
	    				 table.addCell(cell);
	    						 
	    				cell = new PdfPCell(new Paragraph("经销商名称 ："+ORG_NAME,chineseFont));
	    				cell.setColspan(4);
	    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("编号:"+IN_NO,chineseFont));
	    				cell.setNoWrap(true);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				cell.setColspan(4);
	    				table.addCell(cell);
	    				
	    				
	    				cell = new PdfPCell(new Paragraph("打印日期："+printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
	    				cell.setColspan(8);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("序号",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("配件编号",chineseFont));
	    				
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
	    				
	    				cell = new PdfPCell(new Paragraph("数量",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("零售价",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("金额",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("备注",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				//查询发料单明细信息
	    				QuerySet qs = dao.querySaleInDtlInfo(user,ORDER_ID);
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
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_NAME"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "SALE_COUNT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "SALE_PRICE"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "AMOUNT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    			
	    					cell = new PdfPCell(new Paragraph("", chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					pchAmount = pchAmount + Float.parseFloat(qs.getString(f+1, "AMOUNT"));
	    				}//end for
	    					
	    					double pAmount1 =  Math.floor(pchAmount *100+.5)/100;
	    					cell = new PdfPCell(new Paragraph("合计：",chineseFont));
	           				cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	           				cell.setColspan(5);
	           				table.addCell(cell);
	           				
	           				cell = new PdfPCell(new Paragraph("",chineseFont));
	           				cell.setNoWrap(true);
	           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           				cell.setColspan(1);
	           				table.addCell(cell);
	           				
	           				cell = new PdfPCell(new Paragraph(String.valueOf(pAmount1),chineseFont));
	           				cell.setNoWrap(true);
	           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           				cell.setColspan(1);
	           				table.addCell(cell);
	           				
	           				cell = new PdfPCell(new Paragraph("",chineseFont));
	           				cell.setNoWrap(true);
	           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           				cell.setColspan(1);
	           				table.addCell(cell);
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
//	    		total.showText(String.valueOf(writer.getPageNumber() - 1)+pagerInfo3);
	    		total.endText();
	    	}

	    	/**
	    	 * 页面结束时调用此方法
	    	 */
	    	public void onEndPage(PdfWriter writer, Document document) {
	    		PdfContentByte cb = writer.getDirectContent();
	    		cb.saveState();
//	    		String text = pagerInfo1 + writer.getPageNumber() + pagerInfo2;
//	    		float textSize = helv.getWidthPoint(text, pagerFontSize);
//	    		cb.beginText();
//	    		cb.setFontAndSize(helv, pagerFontSize);
//	            cb.setTextMatrix(pagerFixX, pagerFixY);
//	    		cb.showText(text);
//	    		cb.endText();
//	    		cb.addTemplate(total, pagerFixX+textSize, pagerFixY);

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
	    
	    
	    
	    public void printSaleRetPdf()throws Exception{
	    	RequestWrapper request = atx.getRequest();
	    	ResponseWrapper response =  atx.getResponse();
	    	User user = (User) atx.getSession().get(Globals.USER_KEY);
	    	String ORDER_ID = Pub.val(request, "ORDER_ID");
	    	String flag = Pub.val(request, "flag");
	    	printOrder(ORDER_ID);
	    	response.getHttpResponse().reset();
	    	response.getHttpResponse().setContentType("application/pdf");
	    	response.getHttpResponse().setHeader("content-disposition", "filename="
	    			+ new String("销售订单退件入库单".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

	    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
	    	DoPrint3 doPrint = new DoPrint3();
	    	doPrint.createPdf(user,ORDER_ID,request.getHttpRequest(),response.getHttpResponse(),out);
	    	out.close();
	    }	
	    	
	    	
	    private class DoPrint3 extends PdfPageEventHelper {
	    	private PdfPTable table;

	    	private PdfPCell cell;

	    	private Document document;
	    	
	    	private BaseFont bfChinese;
	    	
	    	private PdfTemplate total;// 总页数
	    	private BaseFont helv;//分页信息中的字体
	    	private int pagerFontSize=11;//分页信息中的字体大小
	    	private float pagerFixX=524.41054f;
	    	private float pagerFixY=290.402f;
	    	
	    	public void createPdf(User user,String ORDER_ID,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
	    	//设置字体
	    	String fontPath = "/css/simsun.ttc";
	    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
	    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
	        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
	        Font chineseFontBig = new Font(bfChinese, 14, Font.BOLD, Color.BLACK);
	        Document document=new Document(saleDetailRectangle_new, MARGINLEFT_NEW,MARGINRIGHT_NEW, MARGINTOP_NEW, MARGINBOTTOM_NEW);
	    	try{
	    		PdfWriter writer=PdfWriter.getInstance(document,out);
	    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
	    		writer.setPageEvent(this);
	    		float  pchAmount = 0;
	    		//查询发料单主信息
	    		QuerySet info = dao.querySaleRetIn(user, ORDER_ID);
				String ORG_NAME = info.getString(1, "ORG_NAME");
				String IN_NO = info.getString(1, "IN_NO");
				String printDate = info.getString(1, "PRINT_DATE");
				String [] printDates =printDate.split("-");
	    		
	    				document.open();
	    				float[] widths={ 26.929f,90.055f,198.079f,34.016f,34.016f,52.441f,70.866f,48.189f};
	    				
	    				table=new PdfPTable(widths);
	    				table.setTotalWidth(530.0784f);
	    				table.setLockedWidth(true);
	    				table.setHeaderRows(3);
	    			
	    				 cell = new PdfPCell(new Paragraph("区域中心退库单",chineseFontBig));
	    				 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    				 cell.setBorder(PdfPCell.NO_BORDER);
	    				 cell.setColspan(8);
	    				 table.addCell(cell);
	    						 
	    				cell = new PdfPCell(new Paragraph("经销商名称 ："+ORG_NAME,chineseFont));
	    				cell.setColspan(4);
	    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("编号:"+IN_NO,chineseFont));
	    				cell.setNoWrap(true);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				cell.setColspan(4);
	    				table.addCell(cell);
	    				
	    				
	    				cell = new PdfPCell(new Paragraph("打印日期："+printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
	    				cell.setColspan(8);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("序号",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("配件编号",chineseFont));
	    				
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
	    				
	    				cell = new PdfPCell(new Paragraph("数量",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("零售价",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("金额",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("备注",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				//查询发料单明细信息
	    				QuerySet qs = dao.querySaleRetInDtlInfo(user,ORDER_ID);
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
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_NAME"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "SALE_COUNT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "SALE_PRICE"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "AMOUNT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    			
	    					cell = new PdfPCell(new Paragraph("", chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					pchAmount = pchAmount + Float.parseFloat(qs.getString(f+1, "AMOUNT"));
	    				}//end for
	    					
	    					double pAmount1 =  Math.floor(pchAmount *100+.5)/100;
	    					cell = new PdfPCell(new Paragraph("合计：",chineseFont));
	           				cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	           				cell.setColspan(5);
	           				table.addCell(cell);
	           				
	           				cell = new PdfPCell(new Paragraph("",chineseFont));
	           				cell.setNoWrap(true);
	           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           				cell.setColspan(1);
	           				table.addCell(cell);
	           				
	           				cell = new PdfPCell(new Paragraph(String.valueOf(pAmount1),chineseFont));
	           				cell.setNoWrap(true);
	           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           				cell.setColspan(1);
	           				table.addCell(cell);
	           				
	           				cell = new PdfPCell(new Paragraph("",chineseFont));
	           				cell.setNoWrap(true);
	           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           				cell.setColspan(1);
	           				table.addCell(cell);
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
//	    		total.showText(String.valueOf(writer.getPageNumber() - 1)+pagerInfo3);
	    		total.endText();
	    	}

	    	/**
	    	 * 页面结束时调用此方法
	    	 */
	    	public void onEndPage(PdfWriter writer, Document document) {
	    		PdfContentByte cb = writer.getDirectContent();
	    		cb.saveState();
//	    		String text = pagerInfo1 + writer.getPageNumber() + pagerInfo2;
//	    		float textSize = helv.getWidthPoint(text, pagerFontSize);
//	    		cb.beginText();
//	    		cb.setFontAndSize(helv, pagerFontSize);
//	            cb.setTextMatrix(pagerFixX, pagerFixY);
//	    		cb.showText(text);
//	    		cb.endText();
//	    		cb.addTemplate(total, pagerFixX+textSize, pagerFixY);

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
	    
	    public void printRealSaleRetPdf()throws Exception{
	    	RequestWrapper request = atx.getRequest();
	    	ResponseWrapper response =  atx.getResponse();
	    	User user = (User) atx.getSession().get(Globals.USER_KEY);
	    	String ORDER_ID = Pub.val(request, "ORDER_ID");
	    	String flag = Pub.val(request, "flag");
	    	printOrder(ORDER_ID);
	    	response.getHttpResponse().reset();
	    	response.getHttpResponse().setContentType("application/pdf");
	    	response.getHttpResponse().setHeader("content-disposition", "filename="
	    			+ new String("实销退件入库单".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

	    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
	    	DoPrint4 doPrint = new DoPrint4();
	    	doPrint.createPdf(user,ORDER_ID,request.getHttpRequest(),response.getHttpResponse(),out);
	    	out.close();
	    }	
	    	
	    	
	    private class DoPrint4 extends PdfPageEventHelper {
	    	private PdfPTable table;

	    	private PdfPCell cell;

	    	private Document document;
	    	
	    	private BaseFont bfChinese;
	    	
	    	private PdfTemplate total;// 总页数
	    	private BaseFont helv;//分页信息中的字体
	    	private int pagerFontSize=11;//分页信息中的字体大小
	    	private float pagerFixX=524.41054f;
	    	private float pagerFixY=290.402f;
	    	
//	    	private String pagerInfo1="第";
//	    	private String pagerInfo2="页，共";
//	    	private String pagerInfo3="页";

	    	public void createPdf(User user,String ORDER_ID,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
	    	//设置字体
	    	String fontPath = "/css/simsun.ttc";
	    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
	    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
	        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
	        Font chineseFontBig = new Font(bfChinese, 14, Font.BOLD, Color.BLACK);
	        Document document=new Document(saleDetailRectangle_new, MARGINLEFT_NEW,MARGINRIGHT_NEW, MARGINTOP_NEW, MARGINBOTTOM_NEW);
	    	try{
	    		PdfWriter writer=PdfWriter.getInstance(document,out);
	    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
	    		writer.setPageEvent(this);
	    		float  pchAmount = 0;
	    		//查询发料单主信息
	    		QuerySet info = dao.queryRealSaleRetIn(user, ORDER_ID);
				String ORG_NAME = info.getString(1, "ORG_NAME");
				String IN_NO = info.getString(1, "IN_NO");
				String printDate = info.getString(1, "PRINT_DATE");
				String [] printDates =printDate.split("-");
	    		
	    				document.open();
	    				float[] widths={ 26.929f,90.055f,198.079f,34.016f,34.016f,52.441f,70.866f,48.189f};
	    				
	    				table=new PdfPTable(widths);
	    				table.setTotalWidth(530.0784f);
	    				table.setLockedWidth(true);
	    				table.setHeaderRows(3);
	    			
	    				 cell = new PdfPCell(new Paragraph("区域中心实销退库单",chineseFontBig));
	    				 cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	    				 cell.setBorder(PdfPCell.NO_BORDER);
	    				 cell.setColspan(8);
	    				 table.addCell(cell);
	    						 
	    				cell = new PdfPCell(new Paragraph("经销商名称 ："+ORG_NAME,chineseFont));
	    				cell.setColspan(4);
	    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("编号:"+IN_NO,chineseFont));
	    				cell.setNoWrap(true);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				cell.setColspan(4);
	    				table.addCell(cell);
	    				
	    				
	    				cell = new PdfPCell(new Paragraph("打印日期："+printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
	    				cell.setColspan(8);
	    				cell.setBorder(PdfPCell.NO_BORDER);
	    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("序号",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("配件编号",chineseFont));
	    				
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
	    				
	    				cell = new PdfPCell(new Paragraph("数量",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("零售价",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("金额",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				
	    				cell = new PdfPCell(new Paragraph("备注",chineseFont));
	    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    				cell.setNoWrap(true);
	    				table.addCell(cell);
	    				//查询发料单明细信息
	    				QuerySet qs = dao.queryRealSaleRetInDtlInfo(user,ORDER_ID);
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
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_NAME"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "SALE_COUNT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "SALE_PRICE"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "AMOUNT"), chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    			
	    					cell = new PdfPCell(new Paragraph("", chineseFont));
	    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    					cell.setNoWrap(false);
	    					table.addCell(cell);
	    					
	    					pchAmount = pchAmount + Float.parseFloat(qs.getString(f+1, "AMOUNT"));
	    				}//end for
	    					
	    					double pAmount1 =  Math.floor(pchAmount *100+.5)/100;
	    					cell = new PdfPCell(new Paragraph("合计：",chineseFont));
	           				cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
	           				cell.setColspan(5);
	           				table.addCell(cell);
	           				
	           				cell = new PdfPCell(new Paragraph("",chineseFont));
	           				cell.setNoWrap(true);
	           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           				cell.setColspan(1);
	           				table.addCell(cell);
	           				
	           				cell = new PdfPCell(new Paragraph(String.valueOf(pAmount1),chineseFont));
	           				cell.setNoWrap(true);
	           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           				cell.setColspan(1);
	           				table.addCell(cell);
	           				
	           				cell = new PdfPCell(new Paragraph("",chineseFont));
	           				cell.setNoWrap(true);
	           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	           				cell.setColspan(1);
	           				table.addCell(cell);
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
//	    		total.showText(String.valueOf(writer.getPageNumber() - 1)+pagerInfo3);
	    		total.endText();
	    	}

	    	/**
	    	 * 页面结束时调用此方法
	    	 */
	    	public void onEndPage(PdfWriter writer, Document document) {
	    		PdfContentByte cb = writer.getDirectContent();
	    		cb.saveState();
//	    		String text = pagerInfo1 + writer.getPageNumber() + pagerInfo2;
//	    		float textSize = helv.getWidthPoint(text, pagerFontSize);
//	    		cb.beginText();
//	    		cb.setFontAndSize(helv, pagerFontSize);
//	            cb.setTextMatrix(pagerFixX, pagerFixY);
//	    		cb.showText(text);
//	    		cb.endText();
//	    		cb.addTemplate(total, pagerFixX+textSize, pagerFixY);

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
	    
	    public void printOrder(String ORDER_ID) throws Exception {
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try {
	        	PtBuDealerPrintLogVO vo = new PtBuDealerPrintLogVO();
	        	vo.setOrderId(ORDER_ID);
	        	vo.setPrintMan(user.getAccount());
	        	vo.setPrintDate(Pub.getCurrentDate());
	        	vo.setPrintType(DicConstant.CZLX_01);
	        	vo.setUpdateUser(user.getAccount());
	            vo.setUpdateTime(Pub.getCurrentDate());
	            vo.setOrgId(user.getOrgId());
	            dao.insertLog(vo);
	            //返回插入结果和成功信息
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    public void printOrder2(String ORDER_ID) throws Exception {
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        try {
	        	PtBuDealerPrintLogVO vo = new PtBuDealerPrintLogVO();
	        	vo.setOrderId(ORDER_ID);
	        	vo.setPrintMan(user.getAccount());
	        	vo.setPrintDate(Pub.getCurrentDate());
	        	vo.setPrintType(DicConstant.CZLX_02);
	        	vo.setUpdateUser(user.getAccount());
	            vo.setUpdateTime(Pub.getCurrentDate());
	            vo.setOrgId(user.getOrgId());
	            dao.insertLog(vo);
	            //返回插入结果和成功信息
	        } catch (Exception e) {
	            atx.setException(e);
	            logger.error(e);
	        }
	    }
	    public void printInSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.printInSearch(page,user,conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    //outPrintSearch
	    public void outPrintSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.outPrintSearch(page,user,conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
}
