package com.org.dms.action.part.storageMng.stockOutMng;

import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.org.dms.dao.part.storageMng.stockOutMng.OutBillPrintDao;
import com.org.dms.vo.part.PtBuStockOutVO;
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

import org.apache.log4j.Logger;

/**
 * 出库单打印Action
 *
 * @user : lichuang
 * @date : 2014-08-08
 */
public class OutBillPrintAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "OutBillPrintAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private OutBillPrintDao dao = OutBillPrintDao.getInstance(atx);


    /**
     * 查询出库单
     *
     * @throws Exception
     */
    public void searchOutBill() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchOutBill(page, user, conditions);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询出库单配件
     *
     * @throws Exception
     */
    public void queryOutBillPart() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String OUT_ID = Pub.val(request, "OUT_ID");
            page.setPageRows(99999);
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.queryOutBillPart(page, user, conditions, OUT_ID);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 打印出库单
     *
     * @throws Exception
     */
    public void printOutBill(String OUT_ID) throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
//            String OUT_ID = Pub.val(request, "OUT_ID");
            //更新出库单的打印状态为已打印
            PtBuStockOutVO stockOutVO = new PtBuStockOutVO();
            stockOutVO.setOutId(OUT_ID);
            stockOutVO.setPrintStatus(DicConstant.DYZT_02);
            stockOutVO.setPrintDate(Pub.getCurrentDate());
            stockOutVO.setPrintMan(user.getAccount());
            stockOutVO.setUpdateTime(Pub.getCurrentDate());
            stockOutVO.setUpdateUser(user.getAccount());
            dao.updateOutBill(stockOutVO);
            //更新出库单明细的销售单价/销售金额/计划单价/计划金额/单位
            //dao.updateOutBillDtl(OUT_ID);
            //更新入库流水的计划价
            //dao.updateOutFlow(OUT_ID);
            //返回插入结果和成功信息
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    
    public void printSaleInPdf()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response =  atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
		String OUT_ID = Pub.val(request, "OUT_ID");
		String flag = Pub.val(request, "flag");
		if("1".equals(flag)){
			printOutBill(OUT_ID);
		}
    	
    	response.getHttpResponse().reset();
    	response.getHttpResponse().setContentType("application/pdf");
    	response.getHttpResponse().setHeader("content-disposition", "filename="
    			+ new String("销售出库单".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
    	DoPrint doPrint = new DoPrint();
    	doPrint.createPdf(user,OUT_ID,request.getHttpRequest(),response.getHttpResponse(),out);
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

    	public void createPdf(User user,String OUT_ID,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
    	//设置字体
    	String fontPath = "/css/simsun.ttc";
    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
    	
    	 document=new Document(new Rectangle(765.53544f,354.33072f),25.866f,36.85f,36.85f,36.85f);
    	
    	try{
    		PdfWriter writer=PdfWriter.getInstance(document,out);
    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
    		writer.setPageEvent(this);
    		
    		//查询发料单主信息
    		QuerySet info = dao.querySaleInInfo(user, OUT_ID);
    		String CHECK_USER = info.getString(1, "CHECK_USER");
    		String ORDER_NO = info.getString(1, "ORDER_NO");
    		String CUST_NAME = info.getString(1, "ORG_NAME");
    		String printDate = info.getString(1, "PRINT_DATE");
    		
			float planAmount = 0;
    		String [] printDates =printDate.split("-");
    		
    				document.open();
    				float[] widths = {36.928f,21.25f,81.53f,129.68f,70.87f,52.52f,52.52f,52.52f,69.8f,79.8f,69.8f,79.8f,79.8f};
//    				float[] widths = {26.928f,21.25f,786.53f,129.68f,35F,40F,50F,52F,50F,52F,65F};
    				table=new PdfPTable(widths);
    				table.setTotalWidth(530.0784f);
    				table.setLockedWidth(true);
    				table.setHeaderRows(3);
    			
    				 cell = new PdfPCell(new Paragraph("审核员:"+CHECK_USER,chineseFont));
    				 cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    				 cell.setBorder(PdfPCell.NO_BORDER);
    				 cell.setColspan(8);
    				 table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("编号:"+ORDER_NO,chineseFont));
    				cell.setNoWrap(true);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    				cell.setColspan(5);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("客户名称:"+CUST_NAME,chineseFont));
    				cell.setColspan(6);
    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
    				cell.setColspan(7);
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
    				cell.setColspan(2);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("参图号",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("单位",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("库区",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("库位",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("数量",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("计划价",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("计划金额",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("备注",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				//查询发料单明细信息
    				QuerySet qs = dao.querySaleDtlInfo(user,OUT_ID);
    				if(qs.getRowCount()>0){
    					for(int f=0;f<qs.getRowCount();f++){
    			
    					String sf=f+1+"";
    					cell = new PdfPCell(new Paragraph(sf, chineseFont));
    					cell.setNoWrap(false);
    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_CODE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setColspan(2);
    					cell.setNoWrap(false);
    					
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_NAME"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setColspan(2);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_NO"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "AREA_CODE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "POSITION_CODE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    			
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "OUT_AMOUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PLAN_PRICE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PLAN_AMOUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph("", chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					planAmount = planAmount + Float.parseFloat(qs.getString(f+1, "PLAN_AMOUNT"));
    					
    				}//end for
    					cell = new PdfPCell(new Paragraph("合计：",chineseFont));
           				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           				cell.setColspan(9);
           				table.addCell(cell);
           				
//           				double pAmount =  Math.floor(pchAmount *100+.5)/100;
           				double pAmount1 =  Math.floor(planAmount *100+.5)/100;
           				
           				cell = new PdfPCell(new Paragraph("",chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
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
    
    
    public void printDirSaleInPdf()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response =  atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
		String OUT_ID = Pub.val(request, "OUT_ID");
		String flag = Pub.val(request, "flag");
		if("1".equals(flag)){
			printOutBill(OUT_ID);
		}
    	
    	response.getHttpResponse().reset();
    	response.getHttpResponse().setContentType("application/pdf");
    	response.getHttpResponse().setHeader("content-disposition", "filename="
    			+ new String("直营出库单".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
    	DoPrint1 doPrint = new DoPrint1();
    	doPrint.createPdf(user,OUT_ID,request.getHttpRequest(),response.getHttpResponse(),out);
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

    	public void createPdf(User user,String OUT_ID,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
    	//设置字体
    	String fontPath = "/css/simsun.ttc";
    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
    	
    	 document=new Document(new Rectangle(765.53544f,354.33072f),25.866f,36.85f,36.85f,36.85f);
    	
    	try{
    		PdfWriter writer=PdfWriter.getInstance(document,out);
    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
    		writer.setPageEvent(this);
    		
    		//查询发料单主信息
    		QuerySet info = dao.queryDirSaleInfo(user, OUT_ID);
    		String CHECK_USER = info.getString(1, "CHECK_USER");
    		String ORDER_NO = info.getString(1, "ORDER_NO");
    		String CUST_NAME = info.getString(1, "ORG_NAME");
    		String printDate = info.getString(1, "PRINT_DATE");
    		
			float planAmount = 0;
    		String [] printDates =printDate.split("-");
    		
    				document.open();
    				float[] widths = {36.928f,21.25f,81.53f,129.68f,70.87f,52.52f,52.52f,52.52f,89.8f,59.8f,69.8f,79.8f,79.8f};
//    				float[] widths = {26.928f,21.25f,786.53f,129.68f,35F,40F,50F,52F,50F,52F,65F};
    				table=new PdfPTable(widths);
    				table.setTotalWidth(530.0784f);
    				table.setLockedWidth(true);
    				table.setHeaderRows(3);
    			
    				 cell = new PdfPCell(new Paragraph("审核员:"+CHECK_USER,chineseFont));
    				 cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    				 cell.setBorder(PdfPCell.NO_BORDER);
    				 cell.setColspan(8);
    				 table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("编号:"+ORDER_NO,chineseFont));
    				cell.setNoWrap(true);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    				cell.setColspan(5);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("客户名称:"+CUST_NAME,chineseFont));
    				cell.setColspan(6);
    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
    				cell.setColspan(7);
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
    				cell.setColspan(2);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("参图号",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("单位",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("库区",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("库位",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("数量",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("计划价",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("计划金额",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("备注",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				//查询发料单明细信息
    				QuerySet qs = dao.querySaleDtlInfo(user,OUT_ID);
    				if(qs.getRowCount()>0){
    					for(int f=0;f<qs.getRowCount();f++){
    			
    					String sf=f+1+"";
    					cell = new PdfPCell(new Paragraph(sf, chineseFont));
    					cell.setNoWrap(false);
    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_CODE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setColspan(2);
    					cell.setNoWrap(false);
    					
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_NAME"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setColspan(2);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_NO"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "AREA_CODE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "POSITION_CODE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    			
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "OUT_AMOUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PLAN_PRICE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PLAN_AMOUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph("", chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					planAmount = planAmount + Float.parseFloat(qs.getString(f+1, "PLAN_AMOUNT"));
    					
    				}//end for
    					cell = new PdfPCell(new Paragraph("合计：",chineseFont));
           				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           				cell.setColspan(9);
           				table.addCell(cell);
           				
//           				double pAmount =  Math.floor(pchAmount *100+.5)/100;
           				double pAmount1 =  Math.floor(planAmount *100+.5)/100;
           				
           				cell = new PdfPCell(new Paragraph("",chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
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
    
    public void printPchRetPdf()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response =  atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
		String OUT_ID = Pub.val(request, "OUT_ID");
		String flag = Pub.val(request, "flag");
		if("1".equals(flag)){
			printOutBill(OUT_ID);
		}
    	
    	response.getHttpResponse().reset();
    	response.getHttpResponse().setContentType("application/pdf");
    	response.getHttpResponse().setHeader("content-disposition", "filename="
    			+ new String("采购退货出库单".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
    	DoPrint2 doPrint = new DoPrint2();
    	doPrint.createPdf(user,OUT_ID,request.getHttpRequest(),response.getHttpResponse(),out);
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
    	
    	private String pagerInfo1="第";
    	private String pagerInfo2="页，共";
    	private String pagerInfo3="页";

    	public void createPdf(User user,String OUT_ID,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
    	//设置字体
    	String fontPath = "/css/simsun.ttc";
    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
    	
    	 document=new Document(new Rectangle(765.53544f,354.33072f),25.866f,36.85f,36.85f,36.85f);
    	
    	try{
    		PdfWriter writer=PdfWriter.getInstance(document,out);
    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
    		writer.setPageEvent(this);
    		
    		//查询发料单主信息
    		QuerySet info = dao.queryPchRetInfo(user, OUT_ID);
    		String SUPPLIER_NAME = info.getString(1, "SUPPLIER_NAME");
    		String OUT_NO = info.getString(1, "OUT_NO");
    		String APPLY_USER = info.getString(1, "APPLY_USER");
    		String printDate = info.getString(1, "PRINT_DATE");
    		
    		float pchAmount = 0;
			float planAmount = 0;
    		String [] printDates =printDate.split("-");
    		
    				document.open();
    				float[] widths = {36.928f,21.25f,81.53f,129.68f,70.87f,52.52f,52.52f,52.52f,69.8f,79.8f,69.8f,79.8f,79.8f};
//    				float[] widths = {26.928f,21.25f,786.53f,129.68f,35F,40F,50F,52F,50F,52F,65F};
    				table=new PdfPTable(widths);
    				table.setTotalWidth(530.0784f);
    				table.setLockedWidth(true);
    				table.setHeaderRows(3);
    			
    				 cell = new PdfPCell(new Paragraph("供应商:"+SUPPLIER_NAME,chineseFont));
    				 cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    				 cell.setBorder(PdfPCell.NO_BORDER);
    				 cell.setColspan(8);
    				 table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("编号:"+OUT_NO,chineseFont));
    				cell.setNoWrap(true);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    				cell.setColspan(5);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("采购员："+APPLY_USER,chineseFont));
    				cell.setColspan(6);
    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
    				cell.setColspan(7);
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
    				cell.setColspan(2);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("参图号",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("单位",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("库区",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("库位",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("数量",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("计划价",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("计划金额",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("备注",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				//查询发料单明细信息
    				QuerySet qs = dao.queryPchRetDtlInfo(user,OUT_ID);
    				if(qs.getRowCount()>0){
    					for(int f=0;f<qs.getRowCount();f++){
    			
    					String sf=f+1+"";
    					cell = new PdfPCell(new Paragraph(sf, chineseFont));
    					cell.setNoWrap(false);
    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_CODE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setColspan(2);
    					cell.setNoWrap(false);
    					
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_NAME"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setColspan(2);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_NO"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "AREA_CODE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "POSITION_CODE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    			
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "OUT_AMOUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PLAN_PRICE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PLAN_AMOUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "REMARKS"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					planAmount = planAmount + Float.parseFloat(qs.getString(f+1, "PLAN_AMOUNT"));
    					
    				}//end for
    					cell = new PdfPCell(new Paragraph("合计：",chineseFont));
           				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           				cell.setColspan(9);
           				table.addCell(cell);
           				
//           				double pAmount =  Math.floor(pchAmount *100+.5)/100;
           				double pAmount1 =  Math.floor(planAmount *100+.5)/100;
           				
           				cell = new PdfPCell(new Paragraph("",chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
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
    
    public void printMovePdf()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response =  atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
		String OUT_ID = Pub.val(request, "OUT_ID");
		String flag = Pub.val(request, "flag");
		if("1".equals(flag)){
			printOutBill(OUT_ID);
		}
    	
    	response.getHttpResponse().reset();
    	response.getHttpResponse().setContentType("application/pdf");
    	response.getHttpResponse().setHeader("content-disposition", "filename="
    			+ new String("移库出库单".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
    	DoPrint6 doPrint = new DoPrint6();
    	doPrint.createPdf(user,OUT_ID,request.getHttpRequest(),response.getHttpResponse(),out);
    	out.close();
    }
    
    private class DoPrint6 extends PdfPageEventHelper {
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

    	public void createPdf(User user,String OUT_ID,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
    	//设置字体
    	String fontPath = "/css/simsun.ttc";
    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
    	
    	 document=new Document(new Rectangle(765.53544f,354.33072f),25.866f,36.85f,36.85f,36.85f);
    	
    	try{
    		PdfWriter writer=PdfWriter.getInstance(document,out);
    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
    		writer.setPageEvent(this);
    		
    		//查询发料单主信息
    		QuerySet info = dao.queryMoveInInfo(user, OUT_ID);
    		String WAREHOUSE_NAME = info.getString(1, "WAREHOUSE_NAME");
    		String OUT_NO = info.getString(1, "OUT_NO");
    		String RECEIVE_WAREHOUSE = info.getString(1, "RECEIVE_WAREHOUSE");
    		String printDate = info.getString(1, "PRINT_DATE");
    		
    		float pchAmount = 0;
			float planAmount = 0;
    		String [] printDates =printDate.split("-");
    		
    				document.open();
    				float[] widths = {36.928f,21.25f,81.53f,129.68f,70.87f,52.52f,52.52f,52.52f,69.8f,79.8f,69.8f,79.8f,79.8f};
//    				float[] widths = {26.928f,21.25f,786.53f,129.68f,35F,40F,50F,52F,50F,52F,65F};
    				table=new PdfPTable(widths);
    				table.setTotalWidth(530.0784f);
    				table.setLockedWidth(true);
    				table.setHeaderRows(3);
    			
    				 cell = new PdfPCell(new Paragraph("出库仓库 :"+WAREHOUSE_NAME,chineseFont));
    				 cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    				 cell.setBorder(PdfPCell.NO_BORDER);
    				 cell.setColspan(8);
    				 table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("编号:"+OUT_NO,chineseFont));
    				cell.setNoWrap(true);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    				cell.setColspan(5);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("接收仓库："+RECEIVE_WAREHOUSE,chineseFont));
    				cell.setColspan(6);
    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
    				cell.setColspan(7);
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
    				cell.setColspan(2);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("参图号",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("单位",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("库区",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("库位",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("数量",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("计划价",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("计划金额",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("备注",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				//查询发料单明细信息
    				QuerySet qs = dao.queryMoveInDtlInfo(user,OUT_ID);
    				if(qs.getRowCount()>0){
    					for(int f=0;f<qs.getRowCount();f++){
    			
    					String sf=f+1+"";
    					cell = new PdfPCell(new Paragraph(sf, chineseFont));
    					cell.setNoWrap(false);
    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_CODE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setColspan(2);
    					cell.setNoWrap(false);
    					
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_NAME"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setColspan(2);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_NO"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "AREA_CODE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "POSITION_CODE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    			
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "OUT_AMOUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PLAN_PRICE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PLAN_AMOUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph("", chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					planAmount = planAmount + Float.parseFloat(qs.getString(f+1, "PLAN_AMOUNT"));
    					
    				}//end for
    					cell = new PdfPCell(new Paragraph("合计：",chineseFont));
           				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           				cell.setColspan(9);
           				table.addCell(cell);
           				
//           				double pAmount =  Math.floor(pchAmount *100+.5)/100;
           				double pAmount1 =  Math.floor(planAmount *100+.5)/100;
           				
           				cell = new PdfPCell(new Paragraph("",chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
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
    
    
    public void printOhterPdf()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response =  atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
		String OUT_ID = Pub.val(request, "OUT_ID");
		String flag = Pub.val(request, "flag");
		if("1".equals(flag)){
			printOutBill(OUT_ID);
		}
    	
    	response.getHttpResponse().reset();
    	response.getHttpResponse().setContentType("application/pdf");
    	response.getHttpResponse().setHeader("content-disposition", "filename="
    			+ new String("采购入库单".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
    	DoPrint3 doPrint = new DoPrint3();
    	doPrint.createPdf(user,OUT_ID,request.getHttpRequest(),response.getHttpResponse(),out);
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
    	
    	private String pagerInfo1="第";
    	private String pagerInfo2="页，共";
    	private String pagerInfo3="页";

    	public void createPdf(User user,String OUT_ID,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
    	//设置字体
    	String fontPath = "/css/simsun.ttc";
    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
    	
    	 document=new Document(new Rectangle(765.53544f,354.33072f),25.866f,36.85f,36.85f,36.85f);
    	
    	try{
    		PdfWriter writer=PdfWriter.getInstance(document,out);
    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
    		writer.setPageEvent(this);
    		
    		//查询发料单主信息
    		QuerySet info = dao.queryOtherInInfo(user, OUT_ID);
    		String OUT_NO = info.getString(1, "OUT_NO");
    		String PERSON_NAME = info.getString(1, "PERSON_NAME");
    		String printDate = info.getString(1, "PRINT_DATE");
    		
			float planAmount = 0;
    		String [] printDates =printDate.split("-");
    		
    				document.open();
    				float[] widths = {36.928f,21.25f,81.53f,129.68f,70.87f,52.52f,52.52f,52.52f,69.8f,79.8f,69.8f,79.8f,79.8f};
//    				float[] widths = {26.928f,21.25f,786.53f,129.68f,35F,40F,50F,52F,50F,52F,65F};
    				table=new PdfPTable(widths);
    				table.setTotalWidth(530.0784f);
    				table.setLockedWidth(true);
    				table.setHeaderRows(3);
    			
    				 cell = new PdfPCell(new Paragraph("",chineseFont));
    				 cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    				 cell.setBorder(PdfPCell.NO_BORDER);
    				 cell.setColspan(8);
    				 table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("编号:"+OUT_NO,chineseFont));
    				cell.setNoWrap(true);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    				cell.setColspan(5);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("库管员:"+PERSON_NAME,chineseFont));
    				cell.setColspan(6);
    				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
    				cell.setColspan(7);
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
    				cell.setColspan(2);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("参图号",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("单位",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("库区",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("库位",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("数量",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("计划价",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("计划金额",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("备注",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				//查询发料单明细信息
    				QuerySet qs = dao.queryMoveInDtlInfo(user,OUT_ID);
    				if(qs.getRowCount()>0){
    					for(int f=0;f<qs.getRowCount();f++){
    			
    					String sf=f+1+"";
    					cell = new PdfPCell(new Paragraph(sf, chineseFont));
    					cell.setNoWrap(false);
    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_CODE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setColspan(2);
    					cell.setNoWrap(false);
    					
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_NAME"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setColspan(2);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PART_NO"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "AREA_CODE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "POSITION_CODE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    			
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "OUT_AMOUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PLAN_PRICE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PLAN_AMOUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph("", chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					planAmount = planAmount + Float.parseFloat(qs.getString(f+1, "PLAN_AMOUNT"));
    					
    				}//end for
    					cell = new PdfPCell(new Paragraph("合计：",chineseFont));
           				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           				cell.setColspan(9);
           				table.addCell(cell);
           				
//           				double pAmount =  Math.floor(pchAmount *100+.5)/100;
           				double pAmount1 =  Math.floor(planAmount *100+.5)/100;
           				
           				cell = new PdfPCell(new Paragraph("",chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
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
    public void printGreePdf() throws Exception{
		RequestWrapper request = atx.getRequest();
		ResponseWrapper response =  atx.getResponse();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String OUT_ID = Pub.val(request, "OUT_ID");
		printOutBill(OUT_ID);
		//设置字体
		String fontPath = "simsun.ttc";
		String url = request.getHttpRequest().getRealPath("css");
		BaseFont baseFont = BaseFont.createFont(url+"/"+fontPath+ ",0","Identity-H", false);
        Font chineseFont = new Font(baseFont, 11, Font.NORMAL, Color.BLACK);
        Font chineseFontBig = new Font(baseFont, 12, Font.BOLD, Color.BLACK);
        //设置PDF页面大小、左、右、上和下页边距
//		Document document = new Document(new Rectangle(637.79541F,340.33072F), 36.865997F, 36.849998F, 54.849998F, 36.849998F);
        Document document = new Document(new Rectangle(637.79541F,340.33072F), 36.865997F, 38.849998F, 44.849998F, 30.849998F);
		ByteArrayOutputStream  ba = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, ba);
		document.open();
		//设置table列宽
//		float[] widths = {30F,90F,90F,40F,35F,40F,50F,52F,50F,52F};
		float[] widths = {30F,90F,90F,40F,35F,40F,50F,52F,50F,52F,52F};
		PdfPTable table = new PdfPTable(widths);
		table.setLockedWidth(true);
		table.setTotalWidth(widths);
		//查询发料单主信息
		QuerySet info = dao.queryDirSaleInfo(user, OUT_ID);
		String CHECK_USER = info.getString(1, "CHECK_USER");
		String ORDER_NO = info.getString(1, "ORDER_NO");
		String CUST_NAME = info.getString(1, "ORG_NAME");
		String printDate = info.getString(1, "PRINT_DATE");
		String [] printDates =printDate.split("-");
		//查询发料单明细信息
		QuerySet qs = dao.querySaleDtlInfo(user,OUT_ID);
		if(qs.getRowCount()>0){
			//按10条分页，取余算共几页
			int rows = qs.getRowCount();
			int pageRow = rows/10;
			int yu = rows%10;
			if(yu>0){
				pageRow = pageRow+1;
			}
			int pageNum = 1;
			float planAmount = 0;
			for (int i = 0; i < qs.getRowCount(); i++) {
				//按10条分页，重新设置表头
				if(i%10==0){
					PdfPCell cellA1 = new PdfPCell(new Paragraph("审核员:"+CHECK_USER,chineseFont));
					cellA1.setNoWrap(true);
					cellA1.setBorder(0);
					cellA1.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellA1.setColspan(5);
					table.addCell(cellA1);
					PdfPCell cellA2 = new PdfPCell(new Paragraph("编号:"+ORDER_NO,chineseFont));
					cellA2.setNoWrap(true);
					cellA2.setBorder(0);
					cellA2.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cellA2.setColspan(6);
					table.addCell(cellA2);
					PdfPCell cellB0 = new PdfPCell(new Paragraph("客户名称:"+CUST_NAME,chineseFont));
					cellB0.setNoWrap(true);
					cellB0.setBorder(0);
					cellB0.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellB0.setColspan(6);
					table.addCell(cellB0);
					PdfPCell cellB1 = new PdfPCell(new Paragraph("打印日期:"+printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日",chineseFont));
					cellB1.setNoWrap(true);
					cellB1.setBorder(0);
					cellB1.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellB1.setColspan(5);
					table.addCell(cellB1);
					
					PdfPCell cell0 = new PdfPCell(new Paragraph("序号",chineseFont));
					cell0.setFixedHeight(17.0F);
					cell0.setBorderWidth(0.5F);
					cell0.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell0);
					PdfPCell cell1 = new PdfPCell(new Paragraph("配件代码", chineseFont));
					cell1.setFixedHeight(17.0F);
					cell1.setBorderWidth(0.5F);
					cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell1);
					PdfPCell cell2 = new PdfPCell(new Paragraph("配件名称", chineseFont));
					cell2.setFixedHeight(17.0F);
					cell2.setBorderWidth(0.5F);
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell2);
					PdfPCell cell3 = new PdfPCell(new Paragraph("参图号", chineseFont));
					cell3.setBorderWidth(0.5F);
					cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell3);
					PdfPCell cell4 = new PdfPCell(new Paragraph("单位", chineseFont));
					cell4.setBorderWidth(0.5F);
					cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell4);
					PdfPCell cell5 = new PdfPCell(new Paragraph("库区", chineseFont));
					cell5.setBorderWidth(0.5F);
					cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell5);
					PdfPCell cell6 = new PdfPCell(new Paragraph("库位", chineseFont));
					cell6.setBorderWidth(0.5F);
					cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell6);
					PdfPCell cell7 = new PdfPCell(new Paragraph("数量", chineseFont));
					cell7.setBorderWidth(0.5F);
					cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell7);
					PdfPCell cell8 = new PdfPCell(new Paragraph("计划价", chineseFont));
					cell8.setBorderWidth(0.5F);
					cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell8);
					PdfPCell cell9 = new PdfPCell(new Paragraph("金额", chineseFont));
					cell9.setBorderWidth(0.5F);
					cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell9);
					PdfPCell cell10 = new PdfPCell(new Paragraph("备注", chineseFont));
					cell10.setBorderWidth(0.5F);
					cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell10);
					pageNum=pageNum+1;
					
				}
				/**序号**/
				PdfPCell cella = new PdfPCell(new Paragraph(String.valueOf(i+1),chineseFont));
				cella.setFixedHeight(18.0F);
				cella.setBorderWidth(0.5F);
				cella.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cella);
				/**配件代码**/
				PdfPCell cellb = new PdfPCell(new Paragraph(qs.getString(i+1, "PART_CODE"), chineseFont));
				cellb.setBorderWidth(0.5F);
				table.addCell(cellb);
				/**配件名称**/
				PdfPCell cellc = new PdfPCell(new Paragraph(qs.getString(i+1, "PART_NAME"), chineseFont));
				cellc.setBorderWidth(0.5F);
				table.addCell(cellc);
				/**参图号**/
				PdfPCell celld = new PdfPCell(new Paragraph(qs.getString(i+1, "PART_NO"), chineseFont));
				celld.setBorderWidth(0.5F);
				table.addCell(celld);
				/**单位**/
				PdfPCell celle = new PdfPCell(new Paragraph(qs.getString(i+1, "UNIT"), chineseFont));
				celle.setHorizontalAlignment(Element.ALIGN_CENTER);
				celle.setBorderWidth(0.5F);
				table.addCell(celle);
				/**数量**/
				PdfPCell cellf = new PdfPCell(new Paragraph(qs.getString(i+1, "AREA_CODE"), chineseFont));
				cellf.setBorderWidth(0.5F);
				table.addCell(cellf);
				/**采购价格**/
				PdfPCell cellg = new PdfPCell(new Paragraph(qs.getString(i+1, "POSITION_CODE"), chineseFont));
				cellg.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellg.setBorderWidth(0.5F);
				table.addCell(cellg);
				/**采购金额**/
				PdfPCell cellh = new PdfPCell(new Paragraph(qs.getString(i+1, "OUT_AMOUNT"), chineseFont));
				cellh.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellh.setBorderWidth(0.5F);
				table.addCell(cellh);
				/**计划价格*/
				PdfPCell celli = new PdfPCell(new Paragraph(qs.getString(i+1, "PLAN_PRICE"), chineseFont));
				celli.setHorizontalAlignment(Element.ALIGN_RIGHT);
				celli.setBorderWidth(0.5F);
				table.addCell(celli);
				/**计划金额*/
				PdfPCell cellj = new PdfPCell(new Paragraph(qs.getString(i+1, "PLAN_AMOUNT"), chineseFont));
				cellj.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellj.setBorderWidth(0.5F);
				table.addCell(cellj);
				
				PdfPCell cellk = new PdfPCell(new Paragraph("", chineseFont));
				cellk.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cellk.setBorderWidth(0.5F);
				table.addCell(cellk);
				planAmount = planAmount + Float.parseFloat(qs.getString(i+1, "PLAN_AMOUNT"));
			}
			
			
			PdfPCell cellC0 = new PdfPCell(new Paragraph("合计:",chineseFont));
			cellC0.setNoWrap(true);
			cellC0.setBorderWidth(0.5F);
			cellC0.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellC0.setColspan(7);
			table.addCell(cellC0);
			PdfPCell cellC1 = new PdfPCell(new Paragraph("",chineseFont));
			cellC1.setNoWrap(true);
			cellC1.setBorderWidth(0.5F);
			cellC1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellC1.setColspan(2);
			table.addCell(cellC1);
			PdfPCell cellC2 = new PdfPCell(new Paragraph(String.valueOf(planAmount),chineseFont));
			cellC2.setNoWrap(true);
			cellC2.setBorderWidth(0.5F);
			cellC2.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellC2.setColspan(1);
			table.addCell(cellC2);
			PdfPCell cellC3 = new PdfPCell(new Paragraph("",chineseFont));
			cellC3.setNoWrap(true);
			cellC3.setBorderWidth(0.5F);
			cellC3.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellC3.setColspan(1);
			table.addCell(cellC3);
			
			document.add(table);
			document.close();
			response.getHttpResponse().reset();
			response.setContentType("application/pdf");
			//设置保存的名称
			response.addHeader("Content-Disposition","inline;filename=ceshi.pdf"); 
			response.setContentLength(ba.size());
			try {
				ServletOutputStream out = response.getHttpResponse().getOutputStream();
				ba.writeTo(out);
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}