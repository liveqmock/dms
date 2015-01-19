package com.org.dms.action.part.storageMng.stockIssueMng;


import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;
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
import com.org.dms.dao.part.storageMng.stockIssueMng.PrintIssueDao;
import com.org.dms.vo.part.PtBuIssueOrderVO;
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
 * 打印发料单Action
 *
 * @user : lichuang
 * @date : 2014-07-21
 */
public class PrintIssueAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "PrintIssueAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PrintIssueDao dao = PrintIssueDao.getInstance(atx);
    
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


    /**
     * 查询销售订单
     *
     * @throws Exception
     */
    public void searchSaleOrder() throws Exception {
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
            BaseResultSet bs = dao.searchSaleOrder(page, user, conditions);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询销售订单配件
     *
     * @throws Exception
     */
    public void searchSaleOrderPart() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String ORDER_ID = Pub.val(request, "ORDER_ID");
            //BaseResultSet：结果集封装对象
            page.setPageRows(99999);
            BaseResultSet bs = dao.searchSaleOrderPart(page, user, conditions, ORDER_ID);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 生成发料单
     *
     * @throws Exception
     */
    public void createIssueOrder() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
       // User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String ORDER_ID = hm.get("ORDER_ID");//销售订单ID

            QuerySet qs0 = dao.getWarehouseId(ORDER_ID);
            String warehouseId = "";
            if (qs0.getRowCount() > 0) {
            	warehouseId = " DIR_SOURCE_ORDER_ID ="+qs0.getString(1, "WAREHOUSE_ID");
            } else {
            	warehouseId = " ORDER_ID = "+ORDER_ID+"";
            }
            QuerySet qs = dao.partIdCheckWarehouseKeeper(ORDER_ID,warehouseId);
            int count = qs.getRowCount();
            if ( count > 0) {
            	String partString = "";
            	for (int i=0;i<count;i++) {
            		partString+=qs.getString(i+1, "PART_CODE")+".";
            	}
                throw new Exception("请维护"+partString+"的库管关系.");
            }
            QuerySet qs2 = dao.getOrderShipStatus(ORDER_ID);
            if(qs2.getRowCount()>0){
            	String status=qs2.getString(1, "SHIP_STATUS");
            	if(DicConstant.DDFYZT_01.equals(status))
            		//调用存储过程根据库管员生成发料单
                    dao.createIssueOrder(ORDER_ID);
            	else
            		throw new Exception("该订单已生成发料单，请勿重复生成.");
            }
            

            //返回插入结果和成功信息
            atx.setOutMsg("", "生成发料单成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 查询发料单
     *
     * @throws Exception
     */
    public void searchIssueOrder() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String ORDER_ID = Pub.val(request, "ORDER_ID");
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchIssueOrder(page, user, conditions, ORDER_ID);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询发料单明细
     *
     * @throws Exception
     */
    public void searchIssueDtl() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String ISSUE_ID = Pub.val(request, "ISSUE_ID");
            String printFlag = Pub.val(request, "printFlag");
            if (!"".equals(printFlag)) {
                page.setPageRows(999999);
            }
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchIssueDtl(page, user, conditions, ISSUE_ID);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 打印发料单
     * @param iSSUE_ID 
     *
     * @throws Exception
     */
    public void printIssue(String iSSUE_ID) throws Exception {
        //获取封装后的request对象
      //  RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {

            PtBuIssueOrderVO issueOrderVO = new PtBuIssueOrderVO();
            issueOrderVO.setIssueId(iSSUE_ID);
            issueOrderVO.setPrintStatus(DicConstant.DYZT_02);
            issueOrderVO.setPrintMan(user.getAccount());
            issueOrderVO.setPrintDate(Pub.getCurrentDate());
            issueOrderVO.setUpdateUser(user.getAccount());
            issueOrderVO.setUpdateTime(Pub.getCurrentDate());

            dao.updateIssueOrder(issueOrderVO);

            //返回插入结果和成功信息
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    
    public void printPartTitle() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String ISSUE_ID = Pub.val(request, "ISSUE_ID");
            page.setPageRows(999999);
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchPartTitle(page, user, conditions, ISSUE_ID);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 
     * @date()2014年10月22日下午7:36:49
     * @author Administrator
     * @to_do: 打印发料单
     * @throws Exception
     */
	public void printPdf1() throws Exception{
		RequestWrapper request = atx.getRequest();
		ResponseWrapper response =  atx.getResponse();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String ISSUE_ID = Pub.val(request, "ISSUE_ID");
		printIssue(ISSUE_ID);
		//设置字体
		String fontPath = "simsun.ttc";
		String url = request.getHttpRequest().getRealPath("css");
		BaseFont baseFont = BaseFont.createFont(url+"/"+fontPath+ ",0","Identity-H", false);
        Font chineseFont = new Font(baseFont, 11, Font.NORMAL, Color.BLACK);
        //设置PDF页面大小、左、右、上和下页边距
		Document document = new Document(new Rectangle(637.79541F,340.33072F), 36.865997F, 38.849998F, 39.849998F, 46.849998F);
		ByteArrayOutputStream  ba = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, ba);
		document.open();
		//设置table列宽
		float[] widths = {30F, 90F, 120F, 30F, 55F,55F,40F,40F,60F};
		PdfPTable table = new PdfPTable(widths);
		table.setLockedWidth(true);
		table.setTotalWidth(widths);
		//查询发料单主信息
		QuerySet info = dao.queryIssueOrderInfo(user, ISSUE_ID);
		String orgName = info.getString(1, "ORG_NAME");
		String issueNo = info.getString(1, "ISSUE_NO");
		String keeperUser = info.getString(1, "KEEPER_USER");
		String checkUser = info.getString(1, "CHECK_USER");
		String transType = info.getString(1, "TRANS_TYPE");
		String printDate = info.getString(1, "PRINT_DATE");
		String [] printDates =printDate.split("-");
		//查询发料单明细信息
		QuerySet qs = dao.queryIssueOrderDtl(user,ISSUE_ID);
		if(qs.getRowCount()>0){
			//按10条分页，取余算共几页
			int rows = qs.getRowCount();
			int pageRow = rows/12;
			int yu = rows%12;
			if(yu>0){
				pageRow = pageRow+1;
			}
			int pageNum = 1;
			for (int i = 0; i < qs.getRowCount(); i++) {
				//按10条分页，重新设置表头
				if(i%12==0){
					PdfPCell cellA0 = new PdfPCell(new Paragraph("客  户："+orgName,chineseFont));
					//设置不换行
					cellA0.setNoWrap(true);
					//设置边框
					cellA0.setBorder(0);
					//设置文本水平位置
					cellA0.setHorizontalAlignment(Element.ALIGN_LEFT);
					//设置合并列
					cellA0.setColspan(3);
					table.addCell(cellA0);
					PdfPCell cellA1 = new PdfPCell(new Paragraph("发运方式:"+transType,chineseFont));
					cellA1.setNoWrap(true);
					cellA1.setBorder(0);
					cellA1.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellA1.setColspan(3);
					table.addCell(cellA1);
					PdfPCell cellA2 = new PdfPCell(new Paragraph("编号："+issueNo,chineseFont));
					cellA2.setNoWrap(true);
					cellA2.setBorder(0);
					cellA2.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cellA2.setColspan(3);
					table.addCell(cellA2);
					PdfPCell cellB0 = new PdfPCell(new Paragraph("库管员："+keeperUser,chineseFont));
					cellB0.setNoWrap(true);
					cellB0.setBorder(0);
					cellB0.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellB0.setColspan(2);
					table.addCell(cellB0);
					PdfPCell cellB1 = new PdfPCell(new Paragraph("销售员："+checkUser,chineseFont));
					cellB1.setNoWrap(true);
					cellB1.setBorder(0);
					cellB1.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellB1.setColspan(2);
					table.addCell(cellB1);
					PdfPCell cellB2 = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日",chineseFont));
					cellB2.setNoWrap(true);
					cellB2.setBorder(0);
					cellB2.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellB2.setColspan(2);
					table.addCell(cellB2);
					PdfPCell cellB3 = new PdfPCell(new Paragraph("第"+pageNum+"页，共"+pageRow+"页",chineseFont));
					cellB3.setNoWrap(true);
					cellB3.setBorder(0);
					cellB3.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cellB3.setColspan(4);
					table.addCell(cellB3);
					
					PdfPCell cell0 = new PdfPCell(new Paragraph("序号",chineseFont));
					cell0.setFixedHeight(14.0F);
					cell0.setBorderWidth(0.5F);
					cell0.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell0);
					PdfPCell cell1 = new PdfPCell(new Paragraph("配件代码", chineseFont));
					cell1.setBorderWidth(0.5F);
					cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell1);
					PdfPCell cell2 = new PdfPCell(new Paragraph("配件名称", chineseFont));
					cell2.setBorderWidth(0.5F);
					cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell2);
					PdfPCell cell3 = new PdfPCell(new Paragraph("单位", chineseFont));
					cell3.setBorderWidth(0.5F);
					cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell3);
					PdfPCell cell4 = new PdfPCell(new Paragraph("最小包装", chineseFont));
					cell4.setBorderWidth(0.5F);
					cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell4);
					PdfPCell cell5 = new PdfPCell(new Paragraph("库位", chineseFont));
					cell5.setBorderWidth(0.5F);
					cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell5);
					PdfPCell cell6 = new PdfPCell(new Paragraph("应发数", chineseFont));
					cell6.setBorderWidth(0.5F);
					cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell6);
					PdfPCell cell7 = new PdfPCell(new Paragraph("实发数", chineseFont));
					cell7.setBorderWidth(0.5F);
					cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell7);
					PdfPCell cell8 = new PdfPCell(new Paragraph("备注", chineseFont));
					cell8.setBorderWidth(0.5F);
					cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell8);
					pageNum=pageNum+1;
				}
				
				PdfPCell cella = new PdfPCell(new Paragraph(String.valueOf(i+1),chineseFont));
				cella.setFixedHeight(14.0F);
				cella.setBorderWidth(0.5F);
				cella.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cella);
				
				PdfPCell cellb = new PdfPCell(new Paragraph(qs.getString(i+1, "PART_CODE"), chineseFont));
				cellb.setBorderWidth(0.5F);
				cellb.setFixedHeight(14.0F);
				table.addCell(cellb);
				
				PdfPCell cellc = new PdfPCell(new Paragraph(qs.getString(i+1, "PART_NAME"), chineseFont));
				cellc.setBorderWidth(0.5F);
				table.addCell(cellc);
				
				PdfPCell celld = new PdfPCell(new Paragraph(qs.getString(i+1, "UNIT"), chineseFont));
				celld.setBorderWidth(0.5F);
				celld.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(celld);
				
				PdfPCell celle = new PdfPCell(new Paragraph(qs.getString(i+1, "MIN_PACK")+"/"+qs.getString(i+1, "UNIT"), chineseFont));
				celle.setBorderWidth(0.5F);
				celle.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(celle);
				
				PdfPCell cellf = new PdfPCell(new Paragraph(qs.getString(i+1, "POSITION_CODE"), chineseFont));
				cellf.setBorderWidth(0.5F);
				table.addCell(cellf);
				
				PdfPCell cellg = new PdfPCell(new Paragraph(qs.getString(i+1, "SHOULD_COUNT"), chineseFont));
				cellg.setBorderWidth(0.5F);
				cellg.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(cellg);
				PdfPCell cellh = new PdfPCell(new Paragraph("", chineseFont));
				cellh.setBorderWidth(0.5F);
				table.addCell(cellh);
				PdfPCell celli = new PdfPCell(new Paragraph("", chineseFont));
				celli.setBorderWidth(0.5F);
				table.addCell(celli);
			}
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
				searchIssueDtl();
				atx.setOutData("noresponse", "true");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
public void printPdf()throws Exception{
	RequestWrapper request = atx.getRequest();
	ResponseWrapper response =  atx.getResponse();
	User user = (User) atx.getSession().get(Globals.USER_KEY);
	String ISSUE_ID = Pub.val(request, "ISSUE_ID");
	String flag = Pub.val(request, "flag");
	if("1".equals(flag)){
		printIssue(ISSUE_ID);
	}
	
	response.getHttpResponse().reset();
	response.getHttpResponse().setContentType("application/pdf");
	response.getHttpResponse().setHeader("content-disposition", "filename="
			+ new String("发料单".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
	DoPrint doPrint = new DoPrint();
	doPrint.createPdf(user,ISSUE_ID,request.getHttpRequest(),response.getHttpResponse(),out);
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
		QuerySet info = dao.queryIssueOrderInfo(user, ISSUE_ID);
		String orgName = info.getString(1, "ORG_NAME");
		String issueNo = info.getString(1, "ISSUE_NO");
		String keeperUser = info.getString(1, "KEEPER_USER");
		String checkUser = info.getString(1, "CHECK_USER");
		String transType = info.getString(1, "TRANS_TYPE");
		String printDate = info.getString(1, "PRINT_DATE");
		String orderType = info.getString(1, "ORDER_TYPE");
		String custName = info.getString(1, "CUSTORM_NAME");
		String [] printDates =printDate.split("-");
		QuerySet checkTH = dao.checkTH(ISSUE_ID);
		String d_code = checkTH.getString(1, "DIRECT_TYPE_CODE");
				document.open();
				float[] widths={26.928f,21.25f,76.53f,129.68f,70.87f,22.67f,69.8f,69.8f,42.52f};
				
				table=new PdfPTable(widths);
				table.setTotalWidth(530.0784f);
				table.setLockedWidth(true);
				table.setHeaderRows(3);
			
				 cell = new PdfPCell(new Paragraph("客  户:"+orgName,chineseFont));
				 cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				 cell.setBorder(PdfPCell.NO_BORDER);
				 cell.setColspan(4);
				 table.addCell(cell);
						 
				cell = new PdfPCell(new Paragraph("发运方式:"+transType,chineseFont));
				cell.setColspan(2);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setBorder(PdfPCell.NO_BORDER);
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("编号:"+issueNo,chineseFont));
				cell.setNoWrap(true);
				cell.setBorder(PdfPCell.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setColspan(3);
				table.addCell(cell);
						 
				if(DicConstant.DDLX_08.equals(orderType)){
					cell = new PdfPCell(new Paragraph("客户名称:"+custName,chineseFont));
					cell.setNoWrap(true);
					cell.setBorder(PdfPCell.NO_BORDER);
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setColspan(9);
					table.addCell(cell);
				}
				
				
				
				cell = new PdfPCell(new Paragraph("库管员:"+keeperUser+"         "+"销售员:"+checkUser,chineseFont));
				cell.setBorder(PdfPCell.NO_BORDER);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setColspan(4);
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
				cell.setColspan(2);
				cell.setBorder(PdfPCell.NO_BORDER);
				table.addCell(cell);
				
				cell = new PdfPCell();
				cell.setColspan(3);
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
				
				cell = new PdfPCell(new Paragraph("库位",chineseFont));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setNoWrap(true);
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("单位",chineseFont));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setNoWrap(true);
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("应发数",chineseFont));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setNoWrap(true);
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph("实发数",chineseFont));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setNoWrap(true);
				table.addCell(cell);
				
			
				
				cell = new PdfPCell(new Paragraph("备注",chineseFont));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setNoWrap(true);
				table.addCell(cell);
				//查询发料单明细信息
				QuerySet qs = dao.queryIssueOrderDtl(user,ISSUE_ID);
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
					
					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "POSITION_CODE"), chineseFont));
					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					cell.setNoWrap(false);
					table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT"), chineseFont));
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setNoWrap(false);
					table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "SHOULD_COUNT"), chineseFont));
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setNoWrap(false);
					table.addCell(cell);
					if("THZF".equals(d_code)){
						cell = new PdfPCell(new Paragraph(qs.getString(f+1, "REAL_COUNT"), chineseFont));
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setNoWrap(false);
						table.addCell(cell);
					}else{
						cell = new PdfPCell(new Paragraph("", chineseFont));
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cell.setNoWrap(false);
						table.addCell(cell);
					}
					
			
					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "REMARK"), chineseFont));
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
	/**
	 * 
	 * @date()2014年10月22日下午7:32:29
	 * @author Administrator
	 * @to_do:打印发料单标签
	 * @throws Exception
	 */
	public void printTitlePdf() throws Exception{
		RequestWrapper request = atx.getRequest();
		ResponseWrapper response =  atx.getResponse();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String ISSUE_ID = Pub.val(request, "ISSUE_ID");
//		printIssue(ISSUE_ID);
		QuerySet qs = dao.getTitleInfo(user,ISSUE_ID);
		//设置字体
		String fontPath = "simsun.ttc";
		String url = request.getHttpRequest().getRealPath("css");
		BaseFont baseFont = BaseFont.createFont(url+"/"+fontPath+ ",0","Identity-H", false);
        Font chineseFont = new Font(baseFont, 11, Font.NORMAL, Color.BLACK);
        Document document=new Document(saleDetailRectangle,MARGINLEFT,MARGINTOP,MARGINRIGHT,MARGINBOTTOM);
        ByteArrayOutputStream  ba = new ByteArrayOutputStream();
		PdfWriter writer=PdfWriter.getInstance(document,ba);
		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
		
		document.open();
		float[] widths=tableWidths;
		PdfPTable table=new PdfPTable(widths);
		table.setTotalWidth(tableTotalWidth);
		table.setLockedWidth(true);
		
		int rowNum=0;
		String	DeptCode="";
		String	Acode="";
		String	AName="";
		String positionCode ="";
		String	UnitName="";
		String	PlanAmount="0";
		String ToaddressName="";
		for(int f=0;f<qs.getRowCount();f++){
			   DeptCode=qs.getString(f+1, "ORG_NAME");//客户名称
			   Acode=qs.getString(f+1, "PART_CODE");//配件图号
			   AName=qs.getString(f+1, "PART_NAME");//配件名称
			   UnitName=qs.getString(f+1, "UNIT");//计量单位
		       PlanAmount=qs.getString(f+1, "AMOUNT");//计划数量
		       positionCode = qs.getString(f+1, "POSITION_CODE");
/*		       ToaddressName=(String)hm9.get("TOADDRESSNAME");//分运地名称
		       String	ShelfNo=(String)hm9.get("ShelfNo".toUpperCase());*/
		       rowNum=f/3+1;
				
				//求得list所需的行数
				int row=(qs.getRowCount())/3;
				int yu=(qs.getRowCount())%3;
				if(yu>0){
					row=row+1;
				}
				
				PdfPCell cell = new PdfPCell(new Paragraph(Acode,chineseFont));
				cell.setBorderWidth(0f);
				cell.setPadding(0f);
				if(rowNum%8==0){
					cell.setFixedHeight(70f);
				}else{
					cell.setFixedHeight(102.04704f);
				}
				PdfPTable table2=new PdfPTable(tableWidths2);
				  table2.setTotalWidth(tableTotalWidth2);
				  table2.setWidthPercentage(100);
				  PdfPCell cell0 = new PdfPCell(new Paragraph("", chineseFont));
				  	cell0.setFixedHeight(10f);
				  	cell0.setBorderWidth(0f);
				  	cell0.setColspan(2);
				  	table2.addCell(cell0);
				  	if(DeptCode.length()>13){
				  		DeptCode=DeptCode.substring(0,13);
				  	}
				  	PdfPCell cell2 = new PdfPCell(new Paragraph(DeptCode, chineseFont));
				  	cell2.setBorderWidth(0f);
				  	cell2.setColspan(2);
				  	table2.addCell(cell2);
				  
				  	PdfPCell cell3 = new PdfPCell(new Paragraph(Acode+" ("+positionCode+")", chineseFont));
				  	cell3.setBorderWidth(0f);
				  	cell3.setColspan(2);
				  	table2.addCell(cell3);
				  	
				  	if(AName.length()>13){
				  		AName=AName.substring(0, 13);
				  	}
				  	PdfPCell cell4 = new PdfPCell(new Paragraph(AName, chineseFont));
				  	cell4.setBorderWidth(0f);
				  	cell4.setColspan(2);
				  	table2.addCell(cell4);
				  	
				  	String name="";
				  	if(ToaddressName.equals("")){
				  		name=PlanAmount+""+UnitName;
				  	}
				  	else{
				  		 name=PlanAmount+""+UnitName+ "      ["+ToaddressName+"]";
					}
				  	PdfPCell cell5 = new PdfPCell(new Paragraph(name, chineseFont));
				  	cell5.setBorderWidth(0f);
				  	cell5.setColspan(1);
				  	table2.addCell(cell5);
				  	
				  	//上面的字体
				  	PdfPCell cell1 = new PdfPCell(new Paragraph("("+(f+1)+")", chineseFont));
				  	cell1.setBorderWidth(0f);
				  	cell1.setColspan(1);
				  	table2.addCell(cell1);
				  	
				  	cell.addElement(table2);
					cell.setNoWrap(false);
					table.addCell(cell);
		}
		int num=3-(qs.getRowCount()%3);
		if(num==3) num=0;
		for(int j=0;j<num;j++){	

			PdfPCell cell = new PdfPCell(new Paragraph(Acode,chineseFont));
			//cell.setFixedHeight(96.37776f);
			//cell.setFixedHeight(102.04704f);
			cell.setFixedHeight(70f);
			cell = new PdfPCell();
			cell.setNoWrap(false);
			cell.setBorderWidth(0f);
			table.addCell(cell);
		}
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