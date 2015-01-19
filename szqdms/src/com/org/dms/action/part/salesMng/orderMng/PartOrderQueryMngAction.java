package com.org.dms.action.part.salesMng.orderMng;

import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
import com.org.dms.common.RowMapUtils;
import com.org.dms.dao.part.salesMng.orderMng.PartOrderQueryMngDao;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * 订单查询Action
 *
 * 订单查询
 * @author zhengyao
 * @date 2014-10-23
 * @version 1.0
 */
public class PartOrderQueryMngAction {

    // 日志类
    private Logger logger = com.org.framework.log.log.getLogger(this.getClass().getName());
    // 上下文对象
    private ActionContext actionContext = ActionContext.getContext();
    // 获取session中的user对象
    private User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    // 定义仓库管理Dao对象
    private PartOrderQueryMngDao dao = PartOrderQueryMngDao.getInstance(actionContext);
    // 定义request对象
    private RequestWrapper requestWrapper = actionContext.getRequest();
    // 定义reponse对象
    private ResponseWrapper responseWrapper = actionContext.getResponse();

    /**
     * 订单查询导出
     * @throws Exception
     */
    public void download()throws Exception{

        try {
        	//获取封装后的request对象
        	RequestWrapper request = actionContext.getRequest();
        	// 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
            
            // begin : 对查询条件进行过滤： by fuxiao 20150115
            conditions = this.filterConditions(conditions, requestWrapper);
            // end
            
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("ORDER_NO");
            hBean.setTitle("订单编号");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("ORDER_TYPE");
            hBean.setTitle("订单类型");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("APPLY_DATE");
            hBean.setTitle("申请日期");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("ORG_CODE");
            hBean.setTitle("渠道代码");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("ORG_NAME");
            hBean.setTitle("渠道名称");
            header.add(hBean);
            
            if(dao.checkUserIsAM(user)){
	            hBean = new HeaderBean();
	            hBean.setName("CUSTORM_NAME");
	            hBean.setTitle("客户名称");
	            header.add(hBean);
	            
	            hBean = new HeaderBean();
	            hBean.setName("ARMY_CONT_NO");
	            hBean.setTitle("合同编号");
	            header.add(hBean);
            }

            hBean = new HeaderBean();
            hBean.setName("SALEUSER_NAME");
            hBean.setTitle("销售员");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("ORDER_AMOUNT");
            hBean.setTitle("订单金额(元)");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("REAL_AMOUNT");
            hBean.setTitle("实发金额(元)");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("CHECK_DATE");
            hBean.setTitle("订单审核日期");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ORDER_STATUS");
            hBean.setTitle("订单状态");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IF_CREDIT");
            hBean.setTitle("使用额度");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IF_DELAY_ORDER");
            hBean.setTitle("是否延期");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SHIP_STATUS");
            hBean.setTitle("订单发运状态");
            header.add(hBean);
            
/*            hBean = new HeaderBean();
            hBean.setName("COUNTRY_NAME");
            hBean.setTitle("收货地址(省/市/区/县)");
            header.add(hBean);*/
            
            hBean = new HeaderBean();
            hBean.setName("DELIVERY_ADDR");
            hBean.setTitle("收货地址");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("LINK_MAN");
            hBean.setTitle("联系人");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PHONE");
            hBean.setTitle("联系电话");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("CREATE_TIME");
            hBean.setTitle("发料单生成日期");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("CLOSE_DATE");
            hBean.setTitle("订单关闭日期");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SPRINTDATE");
            hBean.setTitle("订单打印日期");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PRINT_USER");
            hBean.setTitle("打印人");
            header.add(hBean);
            QuerySet querySet = dao.download(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "订单导出", header, querySet);
        } catch (Exception e) {
            actionContext.setException(e);
            logger.error(e);
        }
    }

    /**
     * 订单查询
     * @throws Exception
     */
    public void searchOrder() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            
            // begin : 对查询条件进行过滤： by fuxiao 20150115
            conditions = this.filterConditions(conditions, requestWrapper);
            // end
            
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = dao.searchOrder(pageManager, user, conditions, true);
            // 订单类型
            baseResultSet.setFieldDic("ORDER_TYPE", DicConstant.DDLX);
            // 订单状态
            baseResultSet.setFieldDic("ORDER_STATUS", DicConstant.DDZT);
            // 订单发运状态
            baseResultSet.setFieldDic("SHIP_STATUS", DicConstant.DDFYZT);
            // 发运方式
            baseResultSet.setFieldDic("TRANS_TYPE", DicConstant.FYFS);
            //使用额度
            baseResultSet.setFieldDic("IF_CREDIT", DicConstant.SF);
            // 提报日期
            baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
            // 订单审核日期
            baseResultSet.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd HH:mm:ss");
            // 发料单生成日期
            baseResultSet.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
            // 订单关闭日期
            baseResultSet.setFieldDateFormat("CLOSE_DATE", "yyyy-MM-dd HH:mm:ss");
            // 订单打印日期
            baseResultSet.setFieldDateFormat("SPRINTDATE", "yyyy-MM-dd HH:mm:ss");
            // 打印人
            baseResultSet.setFieldUserID("PRINT_USER");
            // 是否延期
            baseResultSet.setFieldDic("IF_DELAY_ORDER", "SF");
            
            baseResultSet.setFieldUserID("SALEUSER_NAME");
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
    
    /**
     * 
     * @Title: filterConditions
     * @Description: 对查询条件进行自定义过滤
     * @param conditions
     * @param requestWrapper
     * @return
     * @return: String
     */
    public String filterConditions(String conditions, RequestWrapper requestWrapper){
    	Map<String, String> map = RequestUtil.getValues(requestWrapper);
    	
    	// 订单关闭状态
    	String closeStatus = map.get("CLOSE_STATUS") == null ? "" : map.get("CLOSE_STATUS");
    	if(closeStatus.equals("0")){
    		conditions += " AND T.ORDER_STATUS <> " + DicConstant.DDZT_06 + " ";
    	} else if(closeStatus.equals("1")) {
    		conditions += " AND T.ORDER_STATUS = " + DicConstant.DDZT_06 + " ";
    	}
    	return conditions;
    }
    
    /**
     * 
     * queryAmount: 根据查询条件查询金额汇总
     * @author fuxiao
     * Date:2014年11月26日
     *
     */
	public void queryAmount() {
		try {
			RequestWrapper requestWrapper = actionContext.getRequest();
			PageManager pageManager = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
			conditions = this.filterConditions(conditions, requestWrapper);
			QuerySet qs = this.dao.searchAmount(conditions,user); 											
			Map<String, Map<String, String>> resultMap = RowMapUtils.toConvert(qs);					
			JSONObject jsonObj = JSONObject.fromObject(resultMap);
			actionContext.setOutMsg(jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			actionContext.setException(e);
		}
	}

    /**
     * 订单明细查询
     * @throws Exception
     */
    public void searchOrderDtl() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = dao.searchOrderDtl(pageManager,conditions);
            baseResultSet.setFieldDic("UNIT", DicConstant.JLDW);
            baseResultSet.setFieldDic("MIN_UNIT", DicConstant.JLDW);
            baseResultSet.setFieldDic("IF_SUPPLIER", DicConstant.SF);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
    
  //----------------------报表查询：配件经销商销量统计查询--------------------------------------------------	    
    /**
     * 配件经销商销量统计查询
     * @throws Exception
     * @Author suoxiuli 2014-11-1
     */
    public void dealerSalesStatisticsQuery() throws Exception
    {
	    RequestWrapper request = actionContext.getRequest();	   
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			String condResult = getConditions(conditions);
			
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			String startTime = hm.get("B.CLOSE_DATE1");
			String endTime = hm.get("B.CLOSE_DATE2");
			
			BaseResultSet bs = dao.dealerSalesStatisticsQuery(page, condResult, startTime, endTime);
			actionContext.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			actionContext.setException(e);
		}
	}
    
    /**
     * 配件经销商销量统计查询导出：报表的导出按钮
     * @throws Exception
     * @Author suoxiuli 2014-11-1
     */
    public void dealerSalesStatiReportExportExcel() throws Exception{

    	ResponseWrapper response= actionContext.getResponse();
    	RequestWrapper requestWrapper = actionContext.getRequest();
    	PageManager page = new PageManager();
    	page.setPageRows(99999);
 		String conditions = RequestUtil.getConditionsWhere(requestWrapper,page);
 		OutputStream os = null;
        
        try {
        	String condResult = getConditions(conditions);
			
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(requestWrapper);
			String startTime = hm.get("B.CLOSE_DATE1");
			String endTime = hm.get("B.CLOSE_DATE2");
			
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            
            hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(hBean);
    		
            hBean = new HeaderBean();
            hBean.setName("DEALER_CODE");
            hBean.setTitle("经销商代码");
            header.add(hBean);

            hBean = new HeaderBean();
            hBean.setName("DEALER_NAME");
            hBean.setTitle("经销商名称");
            header.add(hBean);
			
            hBean = new HeaderBean();
            hBean.setName("DC_NAME");
            hBean.setTitle("上级配送中心");
            header.add(hBean);
			
            hBean = new HeaderBean();
            hBean.setName("OFFICE_NAME");
            hBean.setTitle("办事处");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUM_REAL_AMOUNT");
            hBean.setTitle("经销商采购总金额");
            header.add(hBean);
            
            hBean = new HeaderBean();
            hBean.setName("DATE_PIREIOD");
            hBean.setTitle("时间段");
            header.add(hBean);

            QuerySet querySet = dao.dealerSalesStatiReportExportExcel(page, condResult, startTime, endTime);
            os = response.getHttpResponse().getOutputStream();
    		response.getHttpResponse().reset();
            ExportManager.exportFile(response.getHttpResponse(), "配件经销商销量统计查询", header, querySet);
        } catch (Exception e) {
        	actionContext.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 获得配件经销商销量统计查询条件
     * @throws Exception
     * @Author suoxiuli 2014-11-1
     */
    public String getConditions(String conditions) throws Exception
    {
		
		//从条件中删除开始时间
		int startTimeLoc = conditions.lastIndexOf("AND B.CLOSE_DATE1");
		String condFirst = conditions.substring(0, startTimeLoc);
		String condEnd = conditions.substring(startTimeLoc+54, conditions.length());
		conditions = condFirst + condEnd;
		
		//从条件中删除结束时间
		int endTimeLoc = conditions.lastIndexOf("AND B.CLOSE_DATE2");
		condFirst = conditions.substring(0, endTimeLoc);
		condEnd = conditions.substring(endTimeLoc+54, conditions.length());
		conditions = condFirst + condEnd;
		
		return conditions;
    }
    


    
    public void printPdf() throws Exception{
		RequestWrapper request = actionContext.getRequest();
		ResponseWrapper response =  actionContext.getResponse();
		User user = (User) actionContext.getSession().get(Globals.USER_KEY);
		String ORDER_ID = Pub.val(request, "ORDER_ID");
		QuerySet checkPrint = dao.getPrintUser(ORDER_ID);
		if(checkPrint.getRowCount()>0){
			printOrder(ORDER_ID);
		}
		//设置字体
		String fontPath = "simsun.ttc";
		String url = request.getHttpRequest().getRealPath("css");
		BaseFont baseFont = BaseFont.createFont(url+"/"+fontPath+ ",0","Identity-H", false);
        Font chineseFont = new Font(baseFont, 13, Font.NORMAL, Color.BLACK);
        //设置PDF页面大小、左、右、上和下页边距
//      	Document document = new Document(new Rectangle(637.79541F,820.33072F), 36.865997F, 38.849998F, 54.849998F, 36.849998F);
//		com.itextpdf.text.Document document  = new com.itextpdf.text.Document(PageSize.A4);
        Document document = new Document(); 
        document=new Document(new Rectangle(645.79544f,444.33072f),35.866f,76.85f,36.85f,36.85f);
		ByteArrayOutputStream  ba = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, ba);
		document.open();
		//设置table列宽
//		float[] widths = {30F, 90F, 100F, 30F, 65F,55F,50F,50F,60F};
		float[] widths={26.928f,21.25f,76.53f,119,40.67f,40.67f,50.67f,50.67f,50.67f,50.67f,40.67f};
		//		float[] widths = {50F, 190F, 200F, 50F, 95F,95F,100F,100F,100F};
		PdfPTable table = new PdfPTable(widths);
		table.setLockedWidth(true);
		table.setTotalWidth(widths);
		//查询发料单主信息
		QuerySet info = dao.queryOrderInfo(user, ORDER_ID);
		String orgName = info.getString(1, "ORG_NAME");
		String ORDER_NO = info.getString(1, "ORDER_NO");
		String AMOUNT = info.getString(1, "AMOUNT");
		String printDate = info.getString(1, "PRINT_DATE");
		String [] printDates =printDate.split("-");
		//查询发料单明细信息
		QuerySet qs = dao.queryOrderDtl(user,ORDER_ID);
		if(qs.getRowCount()>0){
			//按10条分页，取余算共几页
			int rows = qs.getRowCount();
			int pageRow = rows/40;
			int yu = rows%40;
			if(yu>0){
				pageRow = pageRow+1;
			}
			int pageNum = 1;
			for (int i = 0; i < qs.getRowCount(); i++) {
				//按10条分页，重新设置表头
				if(i%40==0){
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
					PdfPCell cellA1 = new PdfPCell(new Paragraph("",chineseFont));
					cellA1.setNoWrap(true);
					cellA1.setBorder(0);
					cellA1.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellA1.setColspan(3);
					table.addCell(cellA1);
					PdfPCell cellA2 = new PdfPCell(new Paragraph("编号："+ORDER_NO,chineseFont));
					cellA2.setNoWrap(true);
					cellA2.setBorder(0);
					cellA2.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cellA2.setColspan(3);
					table.addCell(cellA2);
					PdfPCell cellB0 = new PdfPCell(new Paragraph("订单金额："+AMOUNT,chineseFont));
					cellB0.setNoWrap(true);
					cellB0.setBorder(0);
					cellB0.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellB0.setColspan(2);
					table.addCell(cellB0);
					PdfPCell cellB1 = new PdfPCell(new Paragraph("",chineseFont));
					cellB1.setNoWrap(true);
					cellB1.setBorder(0);
					cellB1.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellB1.setColspan(2);
					table.addCell(cellB1);
					PdfPCell cellB2 = new PdfPCell(new Paragraph("打印日期："+printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日",chineseFont));
					cellB2.setNoWrap(true);
					cellB2.setBorder(0);
					cellB2.setHorizontalAlignment(Element.ALIGN_LEFT);
					cellB2.setColspan(2);
					table.addCell(cellB2);
					PdfPCell cellB3 = new PdfPCell(new Paragraph("第"+pageNum+"页，共"+pageRow+"页",chineseFont));
					cellB3.setNoWrap(true);
					cellB3.setBorder(0);
					cellB3.setHorizontalAlignment(Element.ALIGN_CENTER);
					cellB3.setColspan(4);
					table.addCell(cellB3);
					
					PdfPCell cell0 = new PdfPCell(new Paragraph("序号",chineseFont));
					cell0.setFixedHeight(18.0F);
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
					PdfPCell cell5 = new PdfPCell(new Paragraph("订单数量", chineseFont));
					cell5.setBorderWidth(0.5F);
					cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell5);
					PdfPCell cell6 = new PdfPCell(new Paragraph("实发数量", chineseFont));
					cell6.setBorderWidth(0.5F);
					cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell6);
					PdfPCell cell7 = new PdfPCell(new Paragraph("订单金额", chineseFont));
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
				cella.setFixedHeight(18.0F);
				cella.setBorderWidth(0.5F);
				cella.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cella);
				
				PdfPCell cellb = new PdfPCell(new Paragraph(qs.getString(i+1, "PART_CODE"), chineseFont));
				cellb.setBorderWidth(0.5F);
				table.addCell(cellb);
				
				PdfPCell cellc = new PdfPCell(new Paragraph(qs.getString(i+1, "PART_NAME"), chineseFont));
				cellc.setBorderWidth(0.5F);
				cellc.setNoWrap(false);
				table.addCell(cellc);
				
				PdfPCell celld = new PdfPCell(new Paragraph(qs.getString(i+1, "DIC_VALUE"), chineseFont));
				celld.setBorderWidth(0.5F);
				celld.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(celld);
				
				PdfPCell celle = new PdfPCell(new Paragraph(qs.getString(i+1, "UNIT"), chineseFont));
				celle.setBorderWidth(0.5F);
				celle.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(celle);
				
				PdfPCell cellf = new PdfPCell(new Paragraph(qs.getString(i+1, "ORDER_COUNT"), chineseFont));
				cellf.setHorizontalAlignment(Element.ALIGN_CENTER);
				cellf.setBorderWidth(0.5F);
				table.addCell(cellf);
				PdfPCell cellg = new PdfPCell(new Paragraph(qs.getString(i+1, "COUNT"), chineseFont));
				cellg.setBorderWidth(0.5F);
				cellg.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cellg);
				PdfPCell cellh = new PdfPCell(new Paragraph(qs.getString(i+1, "AMOUINT"), chineseFont));
				cellh.setBorderWidth(0.5F);
				cellh.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cellh);
				PdfPCell celli = new PdfPCell(new Paragraph(qs.getString(i+1, "REMARKS"), chineseFont));
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    public void printOrder(String ORDER_ID) throws Exception {
        //获取封装后的request对象
        RequestWrapper request = actionContext.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) actionContext.getSession().get(Globals.USER_KEY);
        try {
        	QuerySet querySet = dao.getPrintUser(ORDER_ID);
        	if (querySet.getRowCount()>0) {
        		PtBuSaleOrderVO vo = new PtBuSaleOrderVO();
        		vo.setOrderId(ORDER_ID);
        		vo.setSprintdate(Pub.getCurrentDate());
        		vo.setPrintUser(user.getAccount());
        		dao.updateSaleOrder(vo);
        	}

            //返回插入结果和成功信息
        } catch (Exception e) {
        	actionContext.setException(e);
            logger.error(e);
        }
    }
    
    
    
    
    
    public void printProofPdf()throws Exception{
    	RequestWrapper request = actionContext.getRequest();
    	ResponseWrapper response =  actionContext.getResponse();
    	User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    	String ORDER_ID = Pub.val(request, "ORDER_ID");
    	printOrder(ORDER_ID);
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
    	private float pagerFixX=499.41054f;
    	private float pagerFixY=360.402f;
    	
    	private String pagerInfo1="第";
    	private String pagerInfo2="页，共";
    	private String pagerInfo3="页";

    	public void createPdf(User user,String ORDER_ID,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
    	//设置字体
    	String fontPath = "/css/simsun.ttc";
    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
        Font chineseFontBig = new Font(bfChinese, 14, Font.BOLD);
    	
    	 document=new Document(new Rectangle(645.79544f,444.33072f),35.866f,76.85f,36.85f,36.85f);
    	 //    	 document=new Document(new Rectangle(667.79544f,454.33072f),35.866f,76.85f,36.85f,36.85f);
    	
    	try{
    		PdfWriter writer=PdfWriter.getInstance(document,out);
    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
    		writer.setPageEvent(this);
    		
    		//查询发料单主信息
    		QuerySet info = dao.querySaleOrderProofInfo(user, ORDER_ID);
    		String orgName = info.getString(1, "ORG_NAME");
    		String ORDER_NO = info.getString(1, "ORDER_NO");
    		String printDate = info.getString(1, "PRINT_DATE");
    		String [] printDates =printDate.split("-");
    		float realAmount = 0;
			float planAmount = 0;
    		
    				document.open();
    				//    				float[] widths={26.928f,51.25f,81.53f,119.68f,40.87f,40.67f,62.8f,62.8f,62.8f,62.8f,62.8f};
    				float[] widths={26.928f,21.25f,76.53f,119,40.67f,40.67f,50.67f,50.67f,50.67f,50.67f,40.67f};
    				
    				table=new PdfPTable(widths);
    				table.setTotalWidth(528.0784f);
    				table.setLockedWidth(true);
    				table.setHeaderRows(3);
    				
    				cell = new PdfPCell(new Paragraph("陕重汽配件公司备品销售明细单",chineseFontBig));
    				cell.setNoWrap(true);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setColspan(11);
    				table.addCell(cell);
    			
    				cell = new PdfPCell(new Paragraph("编号:"+ORDER_NO,chineseFont));
    				cell.setNoWrap(true);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    				cell.setColspan(11);
    				table.addCell(cell);
    				
				   cell = new PdfPCell(new Paragraph("客户名称:"+orgName,chineseFont));
				   cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				   cell.setBorder(PdfPCell.NO_BORDER);
				   cell.setColspan(7);
				   table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
    				cell.setColspan(2);
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
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
    				
    				cell = new PdfPCell(new Paragraph("数量",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("单价",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("金额",chineseFont));
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
    				QuerySet qs = dao.queryIssueOrderDtl(user,ORDER_ID);
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
    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "DELIVERY_COUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT_PRICE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT_AMOUNT"), chineseFont));
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
    					realAmount = realAmount + Float.parseFloat(qs.getString(f+1, "UNIT_AMOUNT"));
    					planAmount = planAmount + Float.parseFloat(qs.getString(f+1, "PLAN_AMOUNT"));
    					
    				}//end for
    					cell = new PdfPCell(new Paragraph("合计：",chineseFont));
           				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           				cell.setColspan(7);
           				table.addCell(cell);
           				
           				double pAmount =  Math.floor(realAmount *100+.5)/100;
           				double pAmount1 =  Math.floor(planAmount *100+.5)/100;
           				
           				cell = new PdfPCell(new Paragraph(String.valueOf(pAmount),chineseFont));
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
           				
           				cell = new PdfPCell(new Paragraph("主管:",chineseFont));
     				    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
     				    cell.setBorder(PdfPCell.NO_BORDER);
     				    cell.setColspan(7);
     				    table.addCell(cell);
           				cell = new PdfPCell(new Paragraph("制单:",chineseFont));
     				    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
     				    cell.setBorder(PdfPCell.NO_BORDER);
     				    cell.setColspan(4);
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
    public void searchOrderPrint() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(requestWrapper, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = dao.searchOrderPrint(pageManager, user, conditions, true);
            // 订单类型
            baseResultSet.setFieldDic("ORDER_TYPE", DicConstant.DDLX);
            // 订单状态
            baseResultSet.setFieldDic("ORDER_STATUS", DicConstant.DDZT);
            // 订单发运状态
            baseResultSet.setFieldDic("SHIP_STATUS", DicConstant.DDFYZT);
            // 发运方式
            baseResultSet.setFieldDic("TRANS_TYPE", DicConstant.FYFS);
            // 提报日期
            baseResultSet.setFieldDateFormat("APPLY_DATE", "yyyy-MM-dd HH:mm:ss");
            // 订单审核日期
            baseResultSet.setFieldDateFormat("CHECK_DATE", "yyyy-MM-dd HH:mm:ss");
            // 发料单生成日期
            baseResultSet.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
            // 订单关闭日期
            baseResultSet.setFieldDateFormat("CLOSE_DATE", "yyyy-MM-dd HH:mm:ss");
            // 订单打印日期
            baseResultSet.setFieldDateFormat("SPRINTDATE", "yyyy-MM-dd HH:mm:ss");
            // 打印人
            baseResultSet.setFieldUserID("PRINT_USER");
            
            baseResultSet.setFieldUserID("SALEUSER_NAME");
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            actionContext.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
    public void searchOrgPrint() throws Exception {

        try {
        	 RequestWrapper request = actionContext.getRequest();
 	        User user = (User) actionContext.getSession().get(Globals.USER_KEY);
 			PageManager page = new PageManager();
 			String conditions = RequestUtil.getConditionsWhere(request,page);
 			try
 			{
 				BaseResultSet bs = dao.searchOrgPrint(page,user,conditions);
 				actionContext.setOutData("bs", bs);
 			}
 			catch (Exception e)
 			{
 				logger.error(e);
 				actionContext.setException(e);
 			}
        } catch (Exception e) {
            logger.error(e);
            actionContext.setException(e);
        }
    }
    
    
    public void printOrderPartPdf()throws Exception{
    	RequestWrapper request = actionContext.getRequest();
    	ResponseWrapper response =  actionContext.getResponse();
    	User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    	String ORG_ID = Pub.val(request, "ORG_ID");
    	String BEGIN_DATE = Pub.val(request, "BEGIN_DATE");
    	String END_DATE = Pub.val(request, "END_DATE");
//    	printOrder(ORDER_ID);
    	response.getHttpResponse().reset();
    	response.getHttpResponse().setContentType("application/pdf");
    	response.getHttpResponse().setHeader("content-disposition", "filename="
    			+ new String("销售订单明细".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
    	DoPrint0 doPrint = new DoPrint0();
    	doPrint.createPdf(user,ORG_ID,BEGIN_DATE,END_DATE,request.getHttpRequest(),response.getHttpResponse(),out);
    	out.close();
    }	
    	
    	
    private class DoPrint0 extends PdfPageEventHelper {
    	private PdfPTable table;

    	private PdfPCell cell;

    	private Document document;
    	
    	private BaseFont bfChinese;
    	
    	private PdfTemplate total;// 总页数
    	private BaseFont helv;//分页信息中的字体
    	private int pagerFontSize=11;//分页信息中的字体大小
//    	private float pagerFixX=625.41054f;
//    	private float pagerFixY=373.402f;
//    	private float pagerFixX=499.41054f;
//    	private float pagerFixY=373.402f;
    	private float pagerFixX=504.41054f;
    	private float pagerFixY=732.402f;
    	
    	private String pagerInfo1="第";
    	private String pagerInfo2="页，共";
    	private String pagerInfo3="页";

    	public void createPdf(User user,String ORG_ID,String BEGIN_DATE,String END_DATE,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
    	//设置字体
    	String fontPath = "/css/simsun.ttc";
    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
        Font chineseFontBig = new Font(bfChinese, 14, Font.BOLD);
    	
    	 document=new Document(new Rectangle(609.44882f,793.70079f),23.364f,33.364f,28.364f,51.0236f);
    	 //    	 document=new Document(new Rectangle(667.79544f,454.33072f),35.866f,76.85f,36.85f,36.85f);
    	
    	try{
    		PdfWriter writer=PdfWriter.getInstance(document,out);
    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
    		writer.setPageEvent(this);
    		
    		//查询发料单主信息
    		QuerySet info = dao.queryOrderDtlInfo(user, ORG_ID);
    		String orgName = info.getString(1, "ORG_NAME");
    		String orgCode = info.getString(1, "ORG_CODE");
    		String printDate = info.getString(1, "PRINT_DATE");
    		String [] printDates =printDate.split("-");
    		float realAmount = 0;
			float planAmount = 0;
    		
    				document.open();
    				//    				float[] widths={26.928f,51.25f,81.53f,119.68f,40.87f,40.67f,62.8f,62.8f,62.8f,62.8f,62.8f};
    				float[] widths={26.928f,31.25f,26.53f,89.68f,90.67f,55.67f,45.67f,60.67f,45.67f,60.67f,52.67f,100.67f};
    				
    				table=new PdfPTable(widths);
//    				table.setTotalWidth(695.0784f);
    				table.setTotalWidth(552.7559f);
    				table.setLockedWidth(true);
    				table.setHeaderRows(3);
    				
    				cell = new PdfPCell(new Paragraph("陕重汽配件公司备品销售明细单",chineseFontBig));
    				cell.setNoWrap(true);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setColspan(12);
    				table.addCell(cell);
    			
    				cell = new PdfPCell(new Paragraph("",chineseFont));
    				cell.setNoWrap(true);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    				cell.setColspan(12);
    				table.addCell(cell);
    				
				   cell = new PdfPCell(new Paragraph("渠道名称:"+orgName,chineseFont));
				   cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				   cell.setBorder(PdfPCell.NO_BORDER);
				   cell.setColspan(7);
				   table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
    				cell.setColspan(3);
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
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
    				cell.setColspan(2);
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("配件名称",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("订单号",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("出库数量",chineseFont));
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

    				cell = new PdfPCell(new Paragraph("单价",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("金额",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("渠道代码",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("渠道名称",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				//查询发料单明细信息
    				QuerySet qs = dao.queryDtl(user,ORG_ID,BEGIN_DATE,END_DATE);
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
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "ORDER_NO"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "DELIVERY_COUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
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
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT_PRICE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT_AMOUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    			
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "ORG_CODE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "ORG_NAME"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					realAmount = realAmount + Float.parseFloat(qs.getString(f+1, "UNIT_AMOUNT"));
    					planAmount = planAmount + Float.parseFloat(qs.getString(f+1, "PLAN_AMOUNT"));
    					
    				}//end for
    					cell = new PdfPCell(new Paragraph("合计：",chineseFont));
           				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           				cell.setColspan(7);
           				table.addCell(cell);
           				
           				double pAmount =  Math.floor(realAmount *100+.5)/100;
           				double pAmount1 =  Math.floor(planAmount *100+.5)/100;
           				
           				cell = new PdfPCell(new Paragraph(String.valueOf(pAmount),chineseFont));
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
           				
           				cell = new PdfPCell(new Paragraph("",chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
           				table.addCell(cell);
           				
           				cell = new PdfPCell(new Paragraph("主管:",chineseFont));
     				    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
     				    cell.setBorder(PdfPCell.NO_BORDER);
     				    cell.setColspan(7);
     				    table.addCell(cell);
           				cell = new PdfPCell(new Paragraph("制单:",chineseFont));
     				    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
     				    cell.setBorder(PdfPCell.NO_BORDER);
     				    cell.setColspan(4);
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
    
    
    public void printOrderPdf()throws Exception{
    	RequestWrapper request = actionContext.getRequest();
    	ResponseWrapper response =  actionContext.getResponse();
    	User user = (User) actionContext.getSession().get(Globals.USER_KEY);
    	String ORDER_ID = Pub.val(request, "ORDER_ID");
    	printOrder(ORDER_ID);
    	response.getHttpResponse().reset();
    	response.getHttpResponse().setContentType("application/pdf");
    	response.getHttpResponse().setHeader("content-disposition", "filename="
    			+ new String("发料单".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
    	DoPrint01 doPrint = new DoPrint01();
    	doPrint.createPdf(user,ORDER_ID,request.getHttpRequest(),response.getHttpResponse(),out);
    	out.close();
    }	
    	
    	
    private class DoPrint01 extends PdfPageEventHelper {
    	private PdfPTable table;

    	private PdfPCell cell;

    	private Document document;
    	
    	private BaseFont bfChinese;
    	
    	private PdfTemplate total;// 总页数
    	private BaseFont helv;//分页信息中的字体
    	private int pagerFontSize=11;//分页信息中的字体大小
    	private float pagerFixX=499.41054f;
    	private float pagerFixY=360.402f;
    	
    	private String pagerInfo1="第";
    	private String pagerInfo2="页，共";
    	private String pagerInfo3="页";

    	public void createPdf(User user,String ORDER_ID,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
    	//设置字体
    	String fontPath = "/css/simsun.ttc";
    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
        Font chineseFontBig = new Font(bfChinese, 14, Font.BOLD);
    	
    	 document=new Document(new Rectangle(645.79544f,444.33072f),35.866f,76.85f,36.85f,36.85f);
    	 //    	 document=new Document(new Rectangle(667.79544f,454.33072f),35.866f,76.85f,36.85f,36.85f);
    	
    	try{
    		PdfWriter writer=PdfWriter.getInstance(document,out);
    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
    		writer.setPageEvent(this);
    		
    		//查询发料单主信息
    		QuerySet info = dao.querySaleOrderProofInfo(user, ORDER_ID);
    		String orgName = info.getString(1, "ORG_NAME");
    		String ORDER_NO = info.getString(1, "ORDER_NO");
    		String printDate = info.getString(1, "PRINT_DATE");
    		String [] printDates =printDate.split("-");
    		float realAmount = 0;
			float planAmount = 0;
    		
    				document.open();
    				//    				float[] widths={26.928f,51.25f,81.53f,119.68f,40.87f,40.67f,62.8f,62.8f,62.8f,62.8f,62.8f};
    				float[] widths={26.928f,21.25f,76.53f,119,40.67f,40.67f,50.67f,50.67f,50.67f,50.67f,40.67f};
    				
    				table=new PdfPTable(widths);
    				table.setTotalWidth(528.0784f);
    				table.setLockedWidth(true);
    				table.setHeaderRows(3);
    				
    				cell = new PdfPCell(new Paragraph("陕重汽配件公司备品销售明细单",chineseFontBig));
    				cell.setNoWrap(true);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setColspan(11);
    				table.addCell(cell);
    			
    				cell = new PdfPCell(new Paragraph("编号:"+ORDER_NO,chineseFont));
    				cell.setNoWrap(true);
    				cell.setBorder(PdfPCell.NO_BORDER);
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    				cell.setColspan(11);
    				table.addCell(cell);
    				
				   cell = new PdfPCell(new Paragraph("客户名称:"+orgName,chineseFont));
				   cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				   cell.setBorder(PdfPCell.NO_BORDER);
				   cell.setColspan(7);
				   table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日", chineseFont));
    				cell.setColspan(2);
    				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
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
    				cell.setColspan(2);
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("配件名称",chineseFont));
    				cell.setColspan(3);
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
    				
    				cell = new PdfPCell(new Paragraph("单价",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("金额",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				
//    				cell = new PdfPCell(new Paragraph("计划价",chineseFont));
//    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//    				cell.setNoWrap(true);
//    				table.addCell(cell);
//    				
//    				cell = new PdfPCell(new Paragraph("计划金额",chineseFont));
//    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//    				cell.setNoWrap(true);
//    				table.addCell(cell);
    				
    				cell = new PdfPCell(new Paragraph("备注",chineseFont));
    				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    				cell.setNoWrap(true);
    				table.addCell(cell);
    				//查询发料单明细信息
    				QuerySet qs = dao.queryIssueOrderDtl(user,ORDER_ID);
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
    					cell.setColspan(3);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "DELIVERY_COUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT_PRICE"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "UNIT_AMOUNT"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					
//    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PLAN_PRICE"), chineseFont));
//    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    					cell.setNoWrap(false);
//    					table.addCell(cell);
//    					
//    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "PLAN_AMOUNT"), chineseFont));
//    					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//    					cell.setNoWrap(false);
//    					table.addCell(cell);
    			
    					cell = new PdfPCell(new Paragraph(qs.getString(f+1, "REMARKS"), chineseFont));
    					cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    					cell.setNoWrap(false);
    					table.addCell(cell);
    					realAmount = realAmount + Float.parseFloat(qs.getString(f+1, "UNIT_AMOUNT"));
    					planAmount = planAmount + Float.parseFloat(qs.getString(f+1, "PLAN_AMOUNT"));
    					
    				}//end for
    					cell = new PdfPCell(new Paragraph("合计：",chineseFont));
           				cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
           				cell.setColspan(9);
           				table.addCell(cell);
           				
           				double pAmount =  Math.floor(realAmount *100+.5)/100;
           				double pAmount1 =  Math.floor(planAmount *100+.5)/100;
           				/*
           				cell = new PdfPCell(new Paragraph(String.valueOf(pAmount),chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
           				table.addCell(cell);
           				
           				cell = new PdfPCell(new Paragraph("",chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
           				table.addCell(cell);*/
           				
           				cell = new PdfPCell(new Paragraph(String.valueOf(pAmount),chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
           				table.addCell(cell);
           				
           				cell = new PdfPCell(new Paragraph("",chineseFont));
           				cell.setNoWrap(true);
           				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
           				cell.setColspan(1);
           				table.addCell(cell);
           				
           				cell = new PdfPCell(new Paragraph("主管:",chineseFont));
     				    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
     				    cell.setBorder(PdfPCell.NO_BORDER);
     				    cell.setColspan(7);
     				    table.addCell(cell);
           				cell = new PdfPCell(new Paragraph("制单:",chineseFont));
     				    cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
     				    cell.setBorder(PdfPCell.NO_BORDER);
     				    cell.setColspan(4);
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
