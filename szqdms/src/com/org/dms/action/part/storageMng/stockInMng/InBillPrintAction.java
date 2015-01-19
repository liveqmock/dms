package com.org.dms.action.part.storageMng.stockInMng;

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
import com.org.dms.dao.part.storageMng.stockInMng.InBillPrintDao;
import com.org.dms.vo.part.PtBuStockInVO;
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

/**
 * 入库单打印Action
 *
 * @user : lichuang
 * @date : 2014-08-08
 */
public class InBillPrintAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "InBillPrintAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private InBillPrintDao dao = InBillPrintDao.getInstance(atx);


    /**
     * 查询入库单
     *
     * @throws Exception
     */
    public void searchInBill() throws Exception {
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
            BaseResultSet bs = dao.searchInBill(page, user, conditions);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询采购入库单配件
     *
     * @throws Exception
     */
    public void queryPchInBillPart() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String IN_ID = Pub.val(request, "IN_ID");
            page.setPageRows(99999);
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.queryPchInBillPart(page, user, conditions, IN_ID);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询销售退件入库单配件
     *
     * @throws Exception
     */
    public void querySaleRetInBillPart() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String IN_ID = Pub.val(request, "IN_ID");
            page.setPageRows(99999);
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.querySaleRetInBillPart(page, user, conditions, IN_ID);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询移库入库单和其他入库单配件
     *
     * @throws Exception
     */
    public void queryMoveAndOtherInBillPart() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String IN_ID = Pub.val(request, "IN_ID");
            page.setPageRows(99999);
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.queryMoveAndOtherInBillPart(page, user, conditions, IN_ID);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 打印入库单
     * @param iN_ID 
     *
     * @throws Exception
     */
    public void printInBill(String iN_ID) throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            String IN_ID = Pub.val(request,"IN_ID");
            QuerySet getIntype = dao.getInType(IN_ID);
            String IN_TYPE = getIntype.getString(1, "IN_TYPE");
            //更新入库单的打印状态为已打印
            PtBuStockInVO stockInVO = new PtBuStockInVO();
            stockInVO.setInId(IN_ID);
            stockInVO.setPrintStatus(DicConstant.DYZT_02);
            stockInVO.setPrintMan(user.getAccount());
            stockInVO.setPrintDate(Pub.getCurrentDate());
            stockInVO.setUpdateUser(user.getAccount());
            dao.updateInBill(stockInVO);

//            //更新入库单明细的采购单价/采购金额/计划单价/计划金额/单位
//            dao.updateInBillDtl(IN_ID,IN_TYPE);
//            //更新入库流水的计划价
//            dao.updateInFlow(IN_ID);

            //返回插入结果和成功信息
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 
     * @date()2014年10月22日下午4:19:08
     * @author Administrator
     * @to_do:采购入库打印
     * @throws Exception
     */
    public void printInPdf()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response =  atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
		String IN_ID = Pub.val(request, "IN_ID");
		String flag = Pub.val(request, "flag");
		if("1".equals(flag)){
			printInBill(IN_ID);
		}
    	
    	response.getHttpResponse().reset();
    	response.getHttpResponse().setContentType("application/pdf");
    	response.getHttpResponse().setHeader("content-disposition", "filename="
    			+ new String("采购入库单".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
    	DoPrint doPrint = new DoPrint();
    	doPrint.createPdf(user,IN_ID,flag,request.getHttpRequest(),response.getHttpResponse(),out);
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

    	public void createPdf(User user,String IN_ID,String flag,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
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
    		QuerySet info = dao.queryPchInInfo(user, IN_ID);
    		String SUPPLIER_NAME = info.getString(1, "OUT_UNIT");
    		String SPLIT_NO = info.getString(1, "IN_NO");
    		String IN_TYPE = info.getString(1, "IN_TYPE");
    		String printDate = info.getString(1, "PRINT_DATE");
    		String pchAmount0 = info.getString(1, "AMOUNT");
    		String planAmount0 = info.getString(1, "PLAN_AMOUNT");
    		
    		float pchAmount = 0;
			float planAmount = 0;
    		String [] printDates =printDate.split("-");
    		
    				document.open();
    				float[] widths = {36.928f,51.25f,81.53f,119.68f,70.87f,58.52f,52.52f,52.52f,79.8f,89.8f,79.8f,89.8f,79.8f};
//    				float[] widths = {26.928f,21.25f,786.53f,129.68f,35F,40F,50F,52F,50F,52F,65F};
//    				float[] widths = {36.928f,51.25f,81.53f,129.68f,70.87f,52.52f,52.52f,52.52f,79.8f,79.8f,79.8f,79.8f,79.8f};
    				table=new PdfPTable(widths);
    				table.setTotalWidth(530.0784f);
    				table.setLockedWidth(true);
    				table.setHeaderRows(3);
    			
    				 cell = new PdfPCell(new Paragraph("供应商名称:"+SUPPLIER_NAME,chineseFont));
    				 cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    				 cell.setBorder(PdfPCell.NO_BORDER);
    				 cell.setColspan(8);
    				 table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("编号:"+SPLIT_NO,chineseFont));
    				cell.setNoWrap(true);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    				cell.setColspan(5);
    				table.addCell(cell);
    				
    				
    				cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
    				cell.setColspan(7);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    				table.addCell(cell);
    				
    				cell = new PdfPCell();
    				cell.setColspan(6);
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
    				
    				cell = new PdfPCell(new Paragraph("数量",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("采购价格",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("采购金额",chineseFont));
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
    				
    				cell = new PdfPCell(new Paragraph("配送号",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				//查询发料单明细信息
					QuerySet qs = dao.queryPchInDtlInfo(user,IN_ID,flag);
    					
    				
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
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "IN_AMOUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PCH_PRICE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    			
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PCH_AMOUNT"), chineseFont));
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
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "DISTRIBUTION_NO"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					pchAmount += Float.parseFloat(qs.getString(f+1, "PCH_AMOUNT"))*100;
//    					planAmount = planAmount + Float.parseFloat(qs.getString(f+1, "PLAN_AMOUNT"));
    					planAmount +=Float.parseFloat(qs.getString(f+1, "PLAN_AMOUNT"))*100;
    				}//end for
    					cell = new PdfPCell(new Paragraph("合计：",chineseFont));
           				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           				cell.setColspan(9);
           				table.addCell(cell);
           				
//           				double pAmount =  Math.floor(pchAmount *100+.5)/100;
//           				double pAmount1 =  Math.floor(planAmount *100+.5)/100;
           				
           				cell = new PdfPCell(new Paragraph(pchAmount0,chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
           				table.addCell(cell);
           				
           				cell = new PdfPCell(new Paragraph("",chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
           				table.addCell(cell);
           				
           				cell = new PdfPCell(new Paragraph(planAmount0,chineseFont));
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
    /***
     * 
     * @date()2014年10月22日下午4:18:53
     * @author Administrator
     * @to_do:移库入库打印
     * @throws Exception
     */
    public void printMovePdf()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response =  atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
		String IN_ID = Pub.val(request, "IN_ID");
		String flag = Pub.val(request, "flag");
		if("1".equals(flag)){
			printInBill(IN_ID);
		}
    	
    	response.getHttpResponse().reset();
    	response.getHttpResponse().setContentType("application/pdf");
    	response.getHttpResponse().setHeader("content-disposition", "filename="
    			+ new String("采购入库单".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
    	DoPrint1 doPrint = new DoPrint1();
    	doPrint.createPdf(user,IN_ID,flag,request.getHttpRequest(),response.getHttpResponse(),out);
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

    	public void createPdf(User user,String IN_ID,String flag,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
    	//设置字体
    	String fontPath = "/css/simsun.ttc";
    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
    	
    	 document=new Document(new Rectangle(765.53544f,354.33072f),70.866f,36.85f,36.85f,36.85f);
    	
    	try{
    		PdfWriter writer=PdfWriter.getInstance(document,out);
    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
    		writer.setPageEvent(this);
    		
    		//查询发料单主信息
    		QuerySet info = dao.queryMoveInInfo(user, IN_ID);
    		String OUT_UNIT = info.getString(1, "OUT_UNIT");
    		String OUT_NO = info.getString(1, "ORDER_NO");
    		String PLAN_AMOUNT = info.getString(1, "PLAN_AMOUNT");
    		String IN_TYPE = info.getString(1, "IN_TYPE");
    		String printDate = info.getString(1, "PRINT_DATE");
    		String [] printDates =printDate.split("-");
    		
    		float pchAmount = 0;
			float planAmount = 0;
    		
    				document.open();
    				float[] widths = {36.928f,21.25f,76.53f,129.68f,70.87f,52.52f,52.52f,52.52f,69.8f,69.8f,69.8f,74.8f};
//    				float[] widths = {26.928f,21.25f,76.53f,129.68f,35F,40F,50F,52F,50F,52F,65F};
    				table=new PdfPTable(widths);
    				table.setTotalWidth(530.0784f);
    				table.setLockedWidth(true);
    				table.setHeaderRows(3);
    			
    				 cell = new PdfPCell(new Paragraph("出库单位:"+OUT_UNIT,chineseFont));
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
    				
    				
    				cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
    				cell.setColspan(7);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    				table.addCell(cell);
    				
    				cell = new PdfPCell();
    				cell.setColspan(6);
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
    				
    				cell = new PdfPCell(new Paragraph("数量",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("采购价格",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("采购金额",chineseFont));
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
    				
    				/*cell = new PdfPCell(new Paragraph("配送号",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);*/
    				//查询发料单明细信息
    				QuerySet qs = dao.queryMoveInDtlInfo(user,IN_ID,flag);
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
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "IN_AMOUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PCH_PRICE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    			
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PCH_AMOUNT"), chineseFont));
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
    					
    /*					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "DISTRIBUTION_NO"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setNoWrap(false);
    					table.addCell(cell);*/
    					
    					pchAmount = pchAmount + Float.parseFloat(qs.getString(f+1, "PCH_AMOUNT"));
    					planAmount = planAmount + Float.parseFloat(qs.getString(f+1, "PLAN_AMOUNT"));
    					
    				}//end for
    					
    					double pAmount1 =  Math.floor(planAmount *100+.5)/100;
    					cell = new PdfPCell(new Paragraph("合计：",chineseFont));
           				cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           				cell.setColspan(9);
           				table.addCell(cell);
           				
           				cell = new PdfPCell(new Paragraph(String.valueOf(pchAmount),chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
           				table.addCell(cell);
           				
           				cell = new PdfPCell(new Paragraph("",chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
           				table.addCell(cell);
           				
           				cell = new PdfPCell(new Paragraph(String.valueOf(PLAN_AMOUNT),chineseFont));
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
    /**
     * 
     * @date()2014年10月22日下午5:16:12
     * @author Administrator
     * @to_do:销售退件入库
     * @throws Exception
     */
    public void printOtherPdf()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response =  atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
		String IN_ID = Pub.val(request, "IN_ID");
		String flag = Pub.val(request, "flag");
		if("1".equals(flag)){
			printInBill(IN_ID);
		}
    	
    	response.getHttpResponse().reset();
    	response.getHttpResponse().setContentType("application/pdf");
    	response.getHttpResponse().setHeader("content-disposition", "filename="
    			+ new String("其他入库单".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
    	DoPrint3 doPrint = new DoPrint3();
    	doPrint.createPdf(user,IN_ID,flag,request.getHttpRequest(),response.getHttpResponse(),out);
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

    	public void createPdf(User user,String IN_ID,String flag,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
    	//设置字体
    	String fontPath = "/css/simsun.ttc";
    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
    	
    	 document=new Document(new Rectangle(765.53544f,354.33072f),70.866f,36.85f,36.85f,36.85f);
    	
    	try{
    		PdfWriter writer=PdfWriter.getInstance(document,out);
    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
    		writer.setPageEvent(this);
    		
    		//查询发料单主信息
    		QuerySet info = dao.queryOtherInInfo(user, IN_ID);
    		String SUPPLIER_NAME = info.getString(1, "WAREHOUSE_NAME");
    		String SPLIT_NO = info.getString(1, "IN_NO");
    		String IN_TYPE = info.getString(1, "IN_TYPE");
    		String printDate = info.getString(1, "PRINT_DATE");
    		String [] printDates =printDate.split("-");
    		
    		float pchAmount = 0;
			float planAmount = 0;
    		
    				document.open();
    				float[] widths = {36.928f,21.25f,76.53f,129.68f,70.87f,52.52f,52.52f,52.52f,69.8f,69.8f,69.8f,74.8f};
//    				float[] widths = {26.928f,21.25f,76.53f,129.68f,35F,40F,50F,52F,50F,52F,65F};
    				table=new PdfPTable(widths);
    				table.setTotalWidth(530.0784f);
    				table.setLockedWidth(true);
    				table.setHeaderRows(3);
    			
    				 cell = new PdfPCell(new Paragraph("入库仓库:"+SUPPLIER_NAME,chineseFont));
    				 cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    				 cell.setBorder(PdfPCell.NO_BORDER);
    				 cell.setColspan(8);
    				 table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("编号:"+SPLIT_NO,chineseFont));
    				cell.setNoWrap(true);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    				cell.setColspan(5);
    				table.addCell(cell);
    				
    				
    				cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
    				cell.setColspan(7);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    				table.addCell(cell);
    				
    				cell = new PdfPCell();
    				cell.setColspan(5);
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
    				
    				cell = new PdfPCell(new Paragraph("数量",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("采购价格",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("采购金额",chineseFont));
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
    				
    			/*	cell = new PdfPCell(new Paragraph("配送号",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);*/
    				//查询发料单明细信息
    				QuerySet qs = dao.queryOtherInDtlInfo(user,IN_ID,flag);
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
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "IN_AMOUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PCH_PRICE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    			
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PCH_AMOUNT"), chineseFont));
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
    					
    		/*			cell = new PdfPCell(new Paragraph(qs.getString(f+1, "DISTRIBUTION_NO"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setNoWrap(false);
    					table.addCell(cell);*/
    					
    					pchAmount = pchAmount + Float.parseFloat(qs.getString(f+1, "PCH_AMOUNT"));
    					planAmount = planAmount + Float.parseFloat(qs.getString(f+1, "PLAN_AMOUNT"));
    					
    				}//end for
    					cell = new PdfPCell(new Paragraph("合计：",chineseFont));
           				cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           				cell.setColspan(9);
           				table.addCell(cell);
           				
           				cell = new PdfPCell(new Paragraph(String.valueOf(pchAmount),chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
           				table.addCell(cell);
           				
           				cell = new PdfPCell(new Paragraph("",chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
           				table.addCell(cell);
           				
           				cell = new PdfPCell(new Paragraph(String.valueOf(planAmount),chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
           				table.addCell(cell);
           				
           			/*	cell = new PdfPCell(new Paragraph("",chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
           				table.addCell(cell);*/
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
    /**
     * 
     * @date()2014年10月22日下午6:54:34
     * @author Administrator
     * @throws Exception
     */
    public void printRetPdf()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response =  atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
		String IN_ID = Pub.val(request, "IN_ID");
		String flag = Pub.val(request, "flag");
		if("1".equals(flag)){
			printInBill(IN_ID);
		}
    	
    	response.getHttpResponse().reset();
    	response.getHttpResponse().setContentType("application/pdf");
    	response.getHttpResponse().setHeader("content-disposition", "filename="
    			+ new String("销售退库单".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
    	DoPrint2 doPrint = new DoPrint2();
    	doPrint.createPdf(user,IN_ID,flag,request.getHttpRequest(),response.getHttpResponse(),out);
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

    	public void createPdf(User user,String IN_ID,String flag,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
    	//设置字体
    	String fontPath = "/css/simsun.ttc";
    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
    	
    	 document=new Document(new Rectangle(765.53544f,354.33072f),70.866f,36.85f,36.85f,36.85f);
    	
    	try{
    		PdfWriter writer=PdfWriter.getInstance(document,out);
    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
    		writer.setPageEvent(this);
    		
    		//查询发料单主信息
    		QuerySet info = dao.queryRetInInfo(user, IN_ID);
    		String IN_TYPE = info.getString(1, "IN_TYPE");
    		String ORDER_NO = info.getString(1, "RETURN_NO");
    		String ORG_NAME = info.getString(1, "APPLY_ORG_NAME");
    		String PLAN_AMOUNT = info.getString(1, "PLAN_AMOUNT");
    		String printDate = info.getString(1, "PRINT_DATE");
    		String [] printDates =printDate.split("-");
    		
    		float pchAmount = 0;
			float planAmount = 0;
    		
    				document.open();
    				float[] widths = {36.928f,21.25f,76.53f,129.68f,70.87f,52.52f,52.52f,52.52f,69.8f,69.8f,69.8f,74.8f};
//    				float[] widths = {26.928f,21.25f,76.53f,129.68f,35F,40F,50F,52F,50F,52F,65F};
    				table=new PdfPTable(widths);
    				table.setTotalWidth(530.0784f);
    				table.setLockedWidth(true);
    				table.setHeaderRows(3);
    			
    				 cell = new PdfPCell(new Paragraph("退货单位："+ORG_NAME,chineseFont));
    				 cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
    				 cell.setBorder(PdfPCell.NO_BORDER);
    				 cell.setColspan(8);
    				 table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("退件单号："+ORDER_NO,chineseFont));
    				cell.setNoWrap(true);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    				cell.setColspan(5);
    				table.addCell(cell);
    				
    				
    				cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
    				cell.setColspan(7);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    				table.addCell(cell);
    				
    				cell = new PdfPCell();
    				cell.setColspan(6);
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
    				
    				cell = new PdfPCell(new Paragraph("数量",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("销售价格",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("销售金额",chineseFont));
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
    				
    				/*cell = new PdfPCell(new Paragraph("配送号",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);*/
    				//查询发料单明细信息
    				QuerySet qs = dao.queryOtherInDtlInfo(user,IN_ID,flag);
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
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "IN_AMOUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PCH_PRICE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    			
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PCH_AMOUNT"), chineseFont));
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
    					
    					/*cell = new PdfPCell(new Paragraph(qs.getString(f+1, "DISTRIBUTION_NO"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setNoWrap(false);
    					table.addCell(cell);*/
    					
    					pchAmount = pchAmount + Float.parseFloat(qs.getString(f+1, "PCH_AMOUNT"));
    					planAmount = planAmount + Float.parseFloat(qs.getString(f+1, "PLAN_AMOUNT"));
    					
    				}//end for
    					cell = new PdfPCell(new Paragraph("合计：",chineseFont));
           				cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
           				cell.setColspan(9);
           				table.addCell(cell);
           				
           				cell = new PdfPCell(new Paragraph(String.valueOf(pchAmount),chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
           				table.addCell(cell);
           				
           				cell = new PdfPCell(new Paragraph("",chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
           				table.addCell(cell);
           				
           				cell = new PdfPCell(new Paragraph(String.valueOf(PLAN_AMOUNT),chineseFont));
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
//    public void printOtherPdf() throws Exception{
//		RequestWrapper request = atx.getRequest();
//		ResponseWrapper response =  atx.getResponse();
//		User user = (User) atx.getSession().get(Globals.USER_KEY);
//		String IN_ID = Pub.val(request, "IN_ID");
//		printInBill(IN_ID);
//		//设置字体
//		String fontPath = "simsun.ttc";
//		String url = request.getHttpRequest().getRealPath("css");
//		BaseFont baseFont = BaseFont.createFont(url+"/"+fontPath+ ",0","Identity-H", false);
//        Font chineseFont = new Font(baseFont, 11, Font.NORMAL, Color.BLACK);
//        Font chineseFontBig = new Font(baseFont, 12, Font.BOLD, Color.BLACK);
//        //设置PDF页面大小、左、右、上和下页边距
////		Document document = new Document(new Rectangle(637.79541F,340.33072F), 36.865997F, 36.849998F, 54.849998F, 36.849998F);
//		Document document = new Document(new Rectangle(637.79541F,340.33072F), 36.865997F, 38.849998F, 44.849998F, 30.849998F);
//		ByteArrayOutputStream  ba = new ByteArrayOutputStream();
//		PdfWriter writer = PdfWriter.getInstance(document, ba);
//		document.open();
//		//设置table列宽
////		float[] widths = {30F,90F,110F,40F,35F,40F,55F,55F,55F,55F};
//		float[] widths = {30F,90F,90F,40F,35F,40F,50F,52F,50F,52F};
//		PdfPTable table = new PdfPTable(widths);
//		table.setLockedWidth(true);
//		table.setTotalWidth(widths);
//		//查询发料单主信息
//		QuerySet info = dao.queryOtherInInfo(user, IN_ID);
//		String IN_TYPE = info.getString(1, "IN_TYPE");
//		String printDate = info.getString(1, "PRINT_DATE");
//		String [] printDates =printDate.split("-");
//		//查询发料单明细信息
//		QuerySet qs = dao.queryOtherInDtlInfo(user,IN_ID);
//		if(qs.getRowCount()>0){
//			//按10条分页，取余算共几页
//			int rows = qs.getRowCount();
//			int pageRow = rows/10;
//			int yu = rows%10;
//			if(yu>0){
//				pageRow = pageRow+1;
//			}
//			int pageNum = 1;
//			float pchAmount = 0;
//			float planAmount = 0;
//			for (int i = 0; i < qs.getRowCount(); i++) {
//				//按10条分页，重新设置表头
//				if(i%10==0){
//					/*PdfPCell cellA0 = new PdfPCell(new Paragraph("陕重汽销司销售服务部"+IN_TYPE+"单",chineseFontBig));
//					cellA0.setNoWrap(true);
//					cellA0.setFixedHeight(20.0F);
//					cellA0.setBorder(0);
//					cellA0.setHorizontalAlignment(Element.ALIGN_CENTER);
//					cellA0.setColspan(10);
//					table.addCell(cellA0);*/
//					PdfPCell cellA1 = new PdfPCell(new Paragraph("",chineseFont));
//					cellA1.setNoWrap(true);
//					cellA1.setBorder(0);
//					cellA1.setHorizontalAlignment(Element.ALIGN_LEFT);
//					cellA1.setColspan(5);
//					table.addCell(cellA1);
//					PdfPCell cellA2 = new PdfPCell(new Paragraph("",chineseFont));
//					cellA2.setNoWrap(true);
//					cellA2.setBorder(0);
//					cellA2.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					cellA2.setColspan(5);
//					table.addCell(cellA2);
//					PdfPCell cellB0 = new PdfPCell(new Paragraph("打印日期:"+printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日",chineseFont));
//					cellB0.setNoWrap(true);
//					cellB0.setBorder(0);
//					cellB0.setHorizontalAlignment(Element.ALIGN_LEFT);
//					cellB0.setColspan(6);
//					table.addCell(cellB0);
//					PdfPCell cellB1 = new PdfPCell(new Paragraph("第"+pageNum+"页，共"+pageRow+"页",chineseFont));
//					cellB1.setNoWrap(true);
//					cellB1.setBorder(0);
//					cellB1.setHorizontalAlignment(Element.ALIGN_RIGHT);
//					cellB1.setColspan(4);
//					table.addCell(cellB1);
//					
//					PdfPCell cell0 = new PdfPCell(new Paragraph("序号",chineseFont));
//					cell0.setFixedHeight(18.0F);
//					cell0.setBorderWidth(0.5F);
//					cell0.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell0);
//					PdfPCell cell1 = new PdfPCell(new Paragraph("配件代码", chineseFont));
//					cell1.setFixedHeight(17.0F);
//					cell1.setBorderWidth(0.5F);
//					cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell1);
//					PdfPCell cell2 = new PdfPCell(new Paragraph("配件名称", chineseFont));
//					cell2.setBorderWidth(0.5F);
//					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell2);
//					PdfPCell cell3 = new PdfPCell(new Paragraph("参图号", chineseFont));
//					cell3.setBorderWidth(0.5F);
//					cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell3);
//					PdfPCell cell4 = new PdfPCell(new Paragraph("单位", chineseFont));
//					cell4.setBorderWidth(0.5F);
//					cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell4);
//					PdfPCell cell5 = new PdfPCell(new Paragraph("数量", chineseFont));
//					cell5.setBorderWidth(0.5F);
//					cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell5);
//					PdfPCell cell6 = new PdfPCell(new Paragraph("采购价格", chineseFont));
//					cell6.setBorderWidth(0.5F);
//					cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell6);
//					PdfPCell cell7 = new PdfPCell(new Paragraph("采购金额", chineseFont));
//					cell7.setBorderWidth(0.5F);
//					cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell7);
//					PdfPCell cell8 = new PdfPCell(new Paragraph("计划价格", chineseFont));
//					cell8.setBorderWidth(0.5F);
//					cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell8);
//					PdfPCell cell9 = new PdfPCell(new Paragraph("计划金额", chineseFont));
//					cell9.setBorderWidth(0.5F);
//					cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
//					table.addCell(cell9);
//					pageNum=pageNum+1;
//					
//				}
//				/**序号**/
//				PdfPCell cella = new PdfPCell(new Paragraph(String.valueOf(i+1),chineseFont));
//				cella.setFixedHeight(18.0F);
//				cella.setBorderWidth(0.5F);
//				cella.setHorizontalAlignment(Element.ALIGN_CENTER);
//				table.addCell(cella);
//				/**配件代码**/
//				PdfPCell cellb = new PdfPCell(new Paragraph(qs.getString(i+1, "PART_CODE"), chineseFont));
//				cellb.setBorderWidth(0.5F);
//				table.addCell(cellb);
//				/**配件名称**/
//				PdfPCell cellc = new PdfPCell(new Paragraph(qs.getString(i+1, "PART_NAME"), chineseFont));
//				cellc.setBorderWidth(0.5F);
//				table.addCell(cellc);
//				/**参图号**/
//				PdfPCell celld = new PdfPCell(new Paragraph(qs.getString(i+1, "PART_NO"), chineseFont));
//				celld.setBorderWidth(0.5F);
//				table.addCell(celld);
//				/**单位**/
//				PdfPCell celle = new PdfPCell(new Paragraph(qs.getString(i+1, "UNIT"), chineseFont));
//				celle.setHorizontalAlignment(Element.ALIGN_CENTER);
//				celle.setBorderWidth(0.5F);
//				table.addCell(celle);
//				/**数量**/
//				PdfPCell cellf = new PdfPCell(new Paragraph(qs.getString(i+1, "IN_AMOUNT"), chineseFont));
//				cellf.setBorderWidth(0.5F);
//				table.addCell(cellf);
//				/**采购价格**/
//				PdfPCell cellg = new PdfPCell(new Paragraph(qs.getString(i+1, "PCH_PRICE"), chineseFont));
//				cellg.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				cellg.setBorderWidth(0.5F);
//				table.addCell(cellg);
//				/**采购金额**/
//				PdfPCell cellh = new PdfPCell(new Paragraph(qs.getString(i+1, "PCH_AMOUNT"), chineseFont));
//				cellh.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				cellh.setBorderWidth(0.5F);
//				table.addCell(cellh);
//				/**计划价格*/
//				PdfPCell celli = new PdfPCell(new Paragraph(qs.getString(i+1, "PLAN_PRICE"), chineseFont));
//				celli.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				celli.setBorderWidth(0.5F);
//				table.addCell(celli);
//				/**计划金额*/
//				PdfPCell cellj = new PdfPCell(new Paragraph(qs.getString(i+1, "PLAN_AMOUNT"), chineseFont));
//				cellj.setHorizontalAlignment(Element.ALIGN_RIGHT);
//				cellj.setBorderWidth(0.5F);
//				table.addCell(cellj);
//				pchAmount = pchAmount + Float.parseFloat(qs.getString(i+1, "PCH_AMOUNT"));
//				planAmount = planAmount + Float.parseFloat(qs.getString(i+1, "PLAN_AMOUNT"));
//			}
//			
//			
//			PdfPCell cellC0 = new PdfPCell(new Paragraph("合计:",chineseFont));
//			cellC0.setNoWrap(true);
//			cellC0.setBorderWidth(0.5F);
//			cellC0.setHorizontalAlignment(Element.ALIGN_CENTER);
//			cellC0.setColspan(7);
//			table.addCell(cellC0);
//			PdfPCell cellC1 = new PdfPCell(new Paragraph(String.valueOf(pchAmount),chineseFont));
//			cellC1.setNoWrap(true);
//			cellC1.setBorderWidth(0.5F);
//			cellC1.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			cellC1.setColspan(1);
//			table.addCell(cellC1);
//			PdfPCell cellC2 = new PdfPCell(new Paragraph("",chineseFont));
//			cellC2.setNoWrap(true);
//			cellC2.setBorderWidth(0.5F);
//			cellC2.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			cellC2.setColspan(1);
//			table.addCell(cellC2);
//			PdfPCell cellC3 = new PdfPCell(new Paragraph(String.valueOf(planAmount),chineseFont));
//			cellC3.setNoWrap(true);
//			cellC3.setBorderWidth(0.5F);
//			cellC3.setHorizontalAlignment(Element.ALIGN_RIGHT);
//			cellC3.setColspan(1);
//			table.addCell(cellC3);
//			
//			document.add(table);
//			document.close();
//			response.getHttpResponse().reset();
//			response.setContentType("application/pdf");
//			//设置保存的名称
//			response.addHeader("Content-Disposition","inline;filename=ceshi.pdf"); 
//			response.setContentLength(ba.size());
//			try {
//				ServletOutputStream out = response.getHttpResponse().getOutputStream();
//				ba.writeTo(out);
//				out.flush();
//				out.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
}