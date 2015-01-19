package com.org.dms.action.part.salesMng.orderMng;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import javax.servlet.ServletOutputStream;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.salesMng.orderMng.PartOrderEntrustDao;
import com.org.dms.vo.part.PtBuInvoiceEntrustDtlVO;
import com.org.dms.vo.part.PtBuInvoiceEntrustVO;
import com.org.dms.vo.part.PtBuPchContractDtlVO;
import com.org.dms.vo.part.PtBuPchContractVO;
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

public class PartOrderEntrustAction {
	private Logger logger = com.org.framework.log.log.getLogger("PartOrderCloseAction");
    private ActionContext atx = ActionContext.getContext();
    private PartOrderEntrustDao dao = PartOrderEntrustDao.getInstance(atx);
    
    
//    public static Rectangle saleDetailRectangle = new Rectangle(520f,800f);//(518f
    
    
    public void partOrderEntrustSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.partOrderEntrustSearch(page, user, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    
    public void orgSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.orgSearch(page,user,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    public void entrustInsert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	PtBuInvoiceEntrustVO vo = new PtBuInvoiceEntrustVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			String ORDER_NO = PartOddNumberUtil.getEntrustNo(atx.getDBFactory());
			vo.setEntrustNo(ORDER_NO);
			vo.setUserAccount(user.getAccount());
			vo.setPrintStatus(DicConstant.DYZT_01);
			vo.setStatus(DicConstant.YXBS_01);
			vo.setOemCompanyId(user.getOemCompanyId());
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());//
			vo.setOrgId(user.getOrgId());
			dao.insertEntrust(vo);
			atx.setOutMsg(vo.getRowXml(),"委托单新增成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    public void entrustInsert1() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	PtBuInvoiceEntrustVO vo = new PtBuInvoiceEntrustVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			String ORDER_NO = PartOddNumberUtil.getEntrustNo(atx.getDBFactory());
			vo.setEntrustNo(ORDER_NO);
			vo.setOrgCode(user.getOrgCode());
			vo.setOrgId(user.getOrgId());
			vo.setUserAccount(user.getAccount());
			vo.setPrintStatus(DicConstant.DYZT_01);
			vo.setStatus(DicConstant.YXBS_01);
			vo.setOemCompanyId(user.getOemCompanyId());
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());//
			vo.setOrgId(user.getOrgId());
			dao.insertEntrust(vo);
			atx.setOutMsg(vo.getRowXml(),"委托单新增成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    public void entrustUpdate() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBuInvoiceEntrustVO tempVO = new PtBuInvoiceEntrustVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());//
            dao.updateEntrust(tempVO);
            atx.setOutMsg(tempVO.getRowXml(),"委托单修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    public void printIssue(String ENTRUST_ID) throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            PtBuInvoiceEntrustVO eVO = new PtBuInvoiceEntrustVO();
            eVO.setEntrustId(ENTRUST_ID);
//            eVO.setOrgCode(getOrderInfo.getString(1, "ORG_CODE"));
//            eVO.setOrgName(getOrderInfo.getString(1, "ORG_NAME"));
//            eVO.setOrgId(user.getOrgId());
//            eVO.setCreateUser(user.getAccount());
//            eVO.setCreateTime(Pub.getCurrentDate());
            eVO.setPrintStatus(DicConstant.DYZT_02);
            eVO.setPrintDate(Pub.getCurrentDate());
            dao.updateEntrust(eVO);
            //返回插入结果和成功信息
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    
    public void proSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		RequestUtil.getConditionsWhere(request,page);
		try
		{
			String ENTRUST_ID = Pub.val(request, "ENTRUST_ID");
			BaseResultSet bs = dao.proSearch(page,user,ENTRUST_ID);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    public void partInsert() throws Exception {
        RequestWrapper request = atx.getRequest();
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            PtBuInvoiceEntrustDtlVO p_vo = new PtBuInvoiceEntrustDtlVO();
            p_vo.setValue(hm);
            dao.insertEntrustDtl(p_vo);
            //返回插入结果和成功信息
            atx.setOutMsg(p_vo.getRowXml(), "新增成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    public void partUpdate() throws Exception {
        RequestWrapper request = atx.getRequest();
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            PtBuInvoiceEntrustDtlVO p_vo = new PtBuInvoiceEntrustDtlVO();
            p_vo.setValue(hm);
            dao.partUpdate(p_vo);
            //返回插入结果和成功信息
            atx.setOutMsg(p_vo.getRowXml(), "修改成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    
    public void contractPartDelete() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBuPchContractVO tempVO = new PtBuPchContractVO();
        try
        {
			String DTL_ID = Pub.val(request, "DTL_ID");
            dao.updateParts(DTL_ID);
			atx.setOutMsg("","配件删除成功！");
            
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    public void printPdf() throws Exception{
		RequestWrapper request = atx.getRequest();
		ResponseWrapper response =  atx.getResponse();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String ENTRUST_ID = Pub.val(request, "ENTRUST_ID");
		String flag = Pub.val(request, "flag");
		if("1".equals(flag)){
			printIssue(ENTRUST_ID);
		}
		//设置字体
		String fontPath = "simsun.ttc";
		String url = request.getHttpRequest().getRealPath("css");
		BaseFont baseFont = BaseFont.createFont(url+"/"+fontPath+ ",0","Identity-H", false);
        Font chineseFont = new Font(baseFont, 11, Font.NORMAL, Color.BLACK);
        //设置PDF页面大小、左、右、上和下页边距
        Document document = new Document(new Rectangle(550f,800f), 52.365997F,55.349998F, 36.849998F, 36.849998F);
		ByteArrayOutputStream  ba = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, ba);
		document.open();
		//设置table列宽
		float[] widths = { 60.196899F, 60.196899F, 60.196899F, 60.196899F, 42.52F,60.196899F, 60.196899F, 60.196899F };
		PdfPTable table = new PdfPTable(widths);
        table.setTotalWidth(530.07892F);
        table.setLockedWidth(true);
        PdfPCell cell = new PdfPCell();
        cell.setColspan(3);
        cell.setFixedHeight(20.045F);
        cell.setBorder(0);
		//查询发料单主信息
		QuerySet info = dao.getEntrust(ENTRUST_ID);
		String ENTRUST_NO = info.getString(1, "ENTRUST_NO");
		String USER_ACCOUNT = info.getString(1, "USER_ACCOUNT");
		String TARIFF_TYPE = info.getString(1, "TARIFF_TYPE");
		String ORG_NAME = info.getString(1, "ORG_NAME");
		String ADDRESS = info.getString(1, "ADDRESS");
		String TELEPHONE = info.getString(1, "TELEPHONE");
		String TARIFF_NO = info.getString(1, "TARIFF_NO");
		String OPEN_BANK = info.getString(1, "OPEN_BANK");
		String BANK_ACCOUNT = info.getString(1, "BANK_ACCOUNT");
		String REMARK = info.getString(1, "REMARKS");
		String printDate = info.getString(1, "PRINT_DATE");
		String [] printDates =printDate.split("-");
		
		table.addCell(cell);
	      cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日",chineseFont));
	      cell.setColspan(3);
	      cell.setFixedHeight(20.045F);
	      cell.setBorder(0);
	      cell.setHorizontalAlignment(1);
	      table.addCell(cell);

	      cell = new PdfPCell();
	      cell.setFixedHeight(20.045F);
	      cell.setBorder(0);
	      cell.setColspan(2);
	      table.addCell(cell);
	      
	      cell = new PdfPCell(new Paragraph("委托单位:", chineseFont));
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph("配件公司", chineseFont));
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(1);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph("经办人:", chineseFont));
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(1);
	      table.addCell(cell);
	      
	      cell = new PdfPCell(new Paragraph(USER_ACCOUNT, chineseFont));
	      cell.setColspan(2);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(1);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph(TARIFF_TYPE, chineseFont));
	      //cell.setColspan(2);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(1);
	      table.addCell(cell);

	      cell = new PdfPCell();
	      cell.setColspan(2);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(1);
	      table.addCell(cell);
	      
	      cell = new PdfPCell(new Paragraph("购货单位:", chineseFont));
	      //cell.setColspan(2);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph(ORG_NAME,chineseFont));
	      cell.setColspan(7);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph("地址及电话:", chineseFont));
	      //cell.setColspan(2);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph("地址:"+ADDRESS +" 电话:"+ TELEPHONE, chineseFont));
	      cell.setColspan(7);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);
	      
	      cell = new PdfPCell(new Paragraph("税号:", chineseFont));
	      //cell.setColspan(2);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph(TARIFF_NO,chineseFont));
	      cell.setColspan(7);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph("开户行:", chineseFont));
	      //cell.setColspan(2);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph(OPEN_BANK, chineseFont));
	      cell.setColspan(4);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph("帐号:", chineseFont));
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(1);
	      table.addCell(cell);
	      
	      cell = new PdfPCell(new Paragraph(BANK_ACCOUNT,  chineseFont));
	      cell.setColspan(2);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);

	      String[] ghDetail1 = { "","购", "货", "明",  "细",  "", "", "" };
	      PdfPTable shuTable1 = new PdfPTable(1);
	      for (int i = 0; i < ghDetail1.length; i++) {
	        PdfPCell celln2 = new PdfPCell(new Paragraph(ghDetail1[i],chineseFont));
	        if (i == 0)
	          celln2.setFixedHeight(20.045F);
	        if (i != 0) {
	          celln2.setBorder(0);
	        }
	        celln2.setNoWrap(false);
	        celln2.setHorizontalAlignment(1);
	        shuTable1.addCell(celln2);
	      }
	      PdfPCell shuCell1 = new PdfPCell(shuTable1);
	      table.addCell(shuCell1);

	      float[] widthsNest = { 60.196899F, 60.196899F, 60.196899F, 42.52F,60.196899F, 60.196899F };
	      
	      PdfPTable tableNest = new PdfPTable(widthsNest);
	      tableNest.setTotalWidth(393.504495F);
	      tableNest.setLockedWidth(true);
	      
	      PdfPCell cell2 = new PdfPCell(new Paragraph("配件编号", chineseFont));
	      cell2.setFixedHeight(20.045F);
	      cell2.setHorizontalAlignment(1);
	      tableNest.addCell(cell2);

	      cell2 = new PdfPCell(new Paragraph("材料名称", chineseFont));
	      cell2.setFixedHeight(20.045F);
	      cell2.setHorizontalAlignment(1);
	      tableNest.addCell(cell2);

	      cell2 = new PdfPCell(new Paragraph("数量", chineseFont));
	      cell2.setFixedHeight(20.045F);
	      cell2.setHorizontalAlignment(1);
	      tableNest.addCell(cell2);

	      cell2 = new PdfPCell(new Paragraph("单位", chineseFont));
	      cell2.setFixedHeight(20.045F);
	      cell2.setHorizontalAlignment(1);
	      tableNest.addCell(cell2);

	      cell2 = new PdfPCell(new Paragraph("含税单价", chineseFont));
	      cell2.setFixedHeight(20.045F);
	      cell2.setHorizontalAlignment(1);
	      tableNest.addCell(cell2);

	      cell2 = new PdfPCell(new Paragraph("含税金额", chineseFont));
	      cell2.setFixedHeight(20.045F);
	      cell2.setHorizontalAlignment(1);
	      tableNest.addCell(cell2);
	      BigDecimal totalNum = new BigDecimal("0");
	      
	      
		QuerySet qs = dao.queryEntrustDtl(ENTRUST_ID);
		for (int i = 0; i < qs.getRowCount(); i++) {
			
			
	        PdfPCell cell3 = new PdfPCell(new Paragraph(qs.getString(i+1, "PRO_NAME"), chineseFont));

	        cell3.setFixedHeight(20.045F);
	        cell3.setHorizontalAlignment(0);
	        tableNest.addCell(cell3);

	        cell3 = new PdfPCell(new Paragraph(qs.getString(i+1, "PRO_NAME"),chineseFont));
	        cell3.setFixedHeight(20.045F);
	        cell3.setHorizontalAlignment(2);
	        tableNest.addCell(cell3);

	        cell3 = new PdfPCell(new Paragraph(qs.getString(i+1, "PRO_COUNT"), chineseFont));
	        cell3.setFixedHeight(15.045F);
	        cell3.setHorizontalAlignment(2);
	        tableNest.addCell(cell3);

	        cell3 = new PdfPCell(new Paragraph(qs.getString(i+1, "PRO_UNIT"), chineseFont));
	        cell3.setFixedHeight(20.045F);
	        cell3.setHorizontalAlignment(0);
	        tableNest.addCell(cell3);

	        cell3 = new PdfPCell(new Paragraph(qs.getString(i+1, "IN_INVOICE_PRICE"), chineseFont));
	        cell3.setFixedHeight(25.045F);
	        cell3.setHorizontalAlignment(2);
	        tableNest.addCell(cell3);

	        cell3 = new PdfPCell(new Paragraph(qs.getString(i+1, "IN_INVOICE_AMOUNT"), chineseFont));
	        cell3.setFixedHeight(20.045F);
	        cell3.setHorizontalAlignment(2);
	        tableNest.addCell(cell3);
	        String amountPrice = qs.getString(i+1, "IN_INVOICE_AMOUNT");
	        amountPrice = (amountPrice == null) || (amountPrice.equals("")) ? "0" : 
	          amountPrice;
	        totalNum = totalNum.add(new BigDecimal(amountPrice));
	      }
		PdfPCell cell4 = new PdfPCell(new Paragraph("合计", chineseFont));
	      cell4.setColspan(5);
	      cell4.setHorizontalAlignment(1);
	      tableNest.addCell(cell4);

	      cell4 = new PdfPCell(new Paragraph(totalNum.toString(), chineseFont));
	      cell4.setHorizontalAlignment(2);
	      tableNest.addCell(cell4);

	      cell4 = new PdfPCell();
	      cell4.setHorizontalAlignment(1);
	      tableNest.addCell(cell4);

	      PdfPCell ceel = new PdfPCell(tableNest);

	      ceel.setColspan(6);
	      table.addCell(ceel);

	      String[] ghDetail2 = { "备注:", ""};
	      PdfPTable shuTable2 = new PdfPTable(1);
	      for (int i = 0; i < ghDetail2.length; i++) {
	    	  if(i==0){
		    		PdfPCell celln2 = new PdfPCell(new Paragraph(ghDetail2[i],chineseFont));
		    		celln2.setFixedHeight(20.045F);
		    		celln2.setNoWrap(false);
			        celln2.setHorizontalAlignment(1);
			        shuTable2.addCell(celln2);
		    	}else{
		    		PdfPCell celln2 = new PdfPCell(new Paragraph(REMARK,chineseFont));
		    		celln2.setFixedHeight(0);
		    		celln2.setNoWrap(true);
			        celln2.setHorizontalAlignment(1);
			        shuTable2.addCell(celln2);
		    	}
	      }
	      PdfPCell shuCell2 = new PdfPCell(shuTable2);
	      table.addCell(shuCell2);
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
    

    public void printPdf1() throws Exception{
		RequestWrapper request = atx.getRequest();
		ResponseWrapper response =  atx.getResponse();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String ENTRUST_ID = Pub.val(request, "ENTRUST_ID");
//		printIssue(ENTRUST_ID);
		//设置字体
		String fontPath = "simsun.ttc";
		String url = request.getHttpRequest().getRealPath("css");
		BaseFont baseFont = BaseFont.createFont(url+"/"+fontPath+ ",0","Identity-H", false);
        Font chineseFont = new Font(baseFont, 11, Font.NORMAL, Color.BLACK);
        //设置PDF页面大小、左、右、上和下页边距
        Document document = new Document(new Rectangle(550f,800f), 52.365997F,55.349998F, 36.849998F, 36.849998F);
		ByteArrayOutputStream  ba = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, ba);
		document.open();
		//设置table列宽
		float[] widths = { 62.362F, 76.5354F, 56.692902F, 65.196899F, 42.52F,58.110001F, 86.457001F, 82.204002F };
		PdfPTable table = new PdfPTable(widths);
        table.setTotalWidth(530.07892F);
        table.setLockedWidth(true);
        PdfPCell cell = new PdfPCell();
        cell.setColspan(3);
        cell.setFixedHeight(20.045F);
        cell.setBorder(0);
		//查询发料单主信息
		QuerySet info = dao.getEntrust(ENTRUST_ID);
		String ENTRUST_NO = info.getString(1, "ENTRUST_NO");
		String USER_ACCOUNT = info.getString(1, "USER_ACCOUNT");
		String TARIFF_TYPE = info.getString(1, "TARIFF_TYPE");
		String ORG_NAME = info.getString(1, "ORG_NAME");
		String ADDRESS = info.getString(1, "ADDRESS");
		String TELEPHONE = info.getString(1, "TELEPHONE");
		String printDate = info.getString(1, "PRINT_DATE");
		String TARIFF_NO  = info.getString(1, "TARIFF_NO");
		String OPEN_BANK = info.getString(1, "OPEN_BANK");
		String BANK_ACCOUNT = info.getString(1, "BANK_ACCOUNT");
		String REMARK = info.getString(1, "REMARKS");
		String [] printDates =printDate.split("-");
		
		table.addCell(cell);
	      cell = new PdfPCell(new Paragraph(printDates[0]+"年"+printDates[1]+"月"+printDates[2]+"日",chineseFont));
	      cell.setColspan(3);
	      cell.setFixedHeight(20.045F);
	      cell.setBorder(0);
	      cell.setHorizontalAlignment(1);
	      table.addCell(cell);

	      cell = new PdfPCell();
	      cell.setFixedHeight(20.045F);
	      cell.setBorder(0);
	      cell.setColspan(2);
	      table.addCell(cell);
	      
	      cell = new PdfPCell(new Paragraph("委托单位:", chineseFont));
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph("配件公司", chineseFont));
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(1);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph("经办人:", chineseFont));
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(1);
	      table.addCell(cell);
	      
	      cell = new PdfPCell(new Paragraph(USER_ACCOUNT, chineseFont));
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(1);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph(TARIFF_TYPE, chineseFont));
	      cell.setColspan(2);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(1);
	      table.addCell(cell);

	      cell = new PdfPCell();
	      cell.setColspan(2);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(1);
	      table.addCell(cell);
	      
	      cell = new PdfPCell(new Paragraph("客户名称:", chineseFont));
	      cell.setColspan(2);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph(ORG_NAME,chineseFont));
	      cell.setColspan(6);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph("地址及电话:", chineseFont));
	      cell.setColspan(2);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph("地址:"+ADDRESS +" 电话:"+ TELEPHONE, chineseFont));
	      cell.setColspan(6);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);
	      
	      
	      cell = new PdfPCell(new Paragraph("税号:", chineseFont));
	      cell.setColspan(2);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);
	      cell = new PdfPCell(new Paragraph(TARIFF_NO,chineseFont));
	      cell.setColspan(6);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph("开户行:", chineseFont));
	      cell.setColspan(2);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph(OPEN_BANK, chineseFont));
	      cell.setColspan(3);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);

	      cell = new PdfPCell(new Paragraph("帐号:", chineseFont));
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(1);
	      table.addCell(cell);
	      
	      cell = new PdfPCell(new Paragraph(BANK_ACCOUNT,  chineseFont));
	      cell.setColspan(2);
	      cell.setFixedHeight(20.045F);
	      cell.setHorizontalAlignment(0);
	      table.addCell(cell);

	      String[] ghDetail1 = { "","购", "货", "明",  "细",  "", "", "" };
	      PdfPTable shuTable1 = new PdfPTable(1);
	      for (int i = 0; i < ghDetail1.length; i++) {
	        PdfPCell celln2 = new PdfPCell(new Paragraph(ghDetail1[i],chineseFont));
	        if (i == 0)
	          celln2.setFixedHeight(20.045F);
	        if (i != 0) {
	          celln2.setBorder(0);
	        }
	        celln2.setNoWrap(false);
	        celln2.setHorizontalAlignment(1);
	        shuTable1.addCell(celln2);
	      }
	      PdfPCell shuCell1 = new PdfPCell(shuTable1);
	      table.addCell(shuCell1);

	      float[] widthsNest = { 76.5354F, 56.692902F, 65.196899F, 42.52F,58.110001F, 86.457001F };
	      
	      PdfPTable tableNest = new PdfPTable(widthsNest);
	      tableNest.setTotalWidth(385.5029F);
	      tableNest.setLockedWidth(true);
	      
	      PdfPCell cell2 = new PdfPCell(new Paragraph("配件编号", chineseFont));
	      cell2.setFixedHeight(20.045F);
	      cell2.setHorizontalAlignment(1);
	      tableNest.addCell(cell2);

	      cell2 = new PdfPCell(new Paragraph("材料名称", chineseFont));
	      cell2.setFixedHeight(20.045F);
	      cell2.setHorizontalAlignment(1);
	      tableNest.addCell(cell2);

	      cell2 = new PdfPCell(new Paragraph("数量", chineseFont));
	      cell2.setFixedHeight(20.045F);
	      cell2.setHorizontalAlignment(1);
	      tableNest.addCell(cell2);

	      cell2 = new PdfPCell(new Paragraph("单位", chineseFont));
	      cell2.setFixedHeight(20.045F);
	      cell2.setHorizontalAlignment(1);
	      tableNest.addCell(cell2);

	      cell2 = new PdfPCell(new Paragraph("含税单价", chineseFont));
	      cell2.setFixedHeight(20.045F);
	      cell2.setHorizontalAlignment(1);
	      tableNest.addCell(cell2);

	      cell2 = new PdfPCell(new Paragraph("含税金额", chineseFont));
	      cell2.setFixedHeight(20.045F);
	      cell2.setHorizontalAlignment(1);
	      tableNest.addCell(cell2);
	      BigDecimal totalNum = new BigDecimal("0");
	      
	      
		QuerySet qs = dao.queryEntrustDtl(ENTRUST_ID);
		for (int i = 0; i < qs.getRowCount(); i++) {
			
			
	        PdfPCell cell3 = new PdfPCell(new Paragraph(qs.getString(i+1, "PRO_NAME"), chineseFont));

	        cell3.setFixedHeight(20.045F);
	        cell3.setHorizontalAlignment(0);
	        tableNest.addCell(cell3);

	        cell3 = new PdfPCell(new Paragraph(qs.getString(i+1, "PRO_NAME"),chineseFont));
	        cell3.setFixedHeight(20.045F);
	        cell3.setHorizontalAlignment(2);
	        tableNest.addCell(cell3);

	        cell3 = new PdfPCell(new Paragraph(qs.getString(i+1, "PRO_COUNT"), chineseFont));
	        cell3.setFixedHeight(20.045F);
	        cell3.setHorizontalAlignment(2);
	        tableNest.addCell(cell3);

	        cell3 = new PdfPCell(new Paragraph(qs.getString(i+1, "PRO_UNIT"), chineseFont));
	        cell3.setFixedHeight(20.045F);
	        cell3.setHorizontalAlignment(0);
	        tableNest.addCell(cell3);

	        cell3 = new PdfPCell(new Paragraph(qs.getString(i+1, "IN_INVOICE_PRICE"), chineseFont));
	        cell3.setFixedHeight(20.045F);
	        cell3.setHorizontalAlignment(2);
	        tableNest.addCell(cell3);

	        cell3 = new PdfPCell(new Paragraph(qs.getString(i+1, "IN_INVOICE_AMOUNT"), chineseFont));
	        cell3.setFixedHeight(20.045F);
	        cell3.setHorizontalAlignment(2);
	        tableNest.addCell(cell3);
	        String amountPrice = qs.getString(i+1, "IN_INVOICE_AMOUNT");
	        amountPrice = (amountPrice == null) || (amountPrice.equals("")) ? "0" : 
	          amountPrice;
	        totalNum = totalNum.add(new BigDecimal(amountPrice));
	      }
		PdfPCell cell4 = new PdfPCell(new Paragraph("合计", chineseFont));
	      cell4.setColspan(5);
	      cell4.setHorizontalAlignment(1);
	      tableNest.addCell(cell4);

	      cell4 = new PdfPCell(new Paragraph(totalNum.toString(), chineseFont));
	      cell4.setHorizontalAlignment(2);
	      tableNest.addCell(cell4);

	      cell4 = new PdfPCell();
	      cell4.setHorizontalAlignment(1);
	      tableNest.addCell(cell4);

	      PdfPCell ceel = new PdfPCell(tableNest);

	      ceel.setColspan(6);
	      table.addCell(ceel);

	      String[] ghDetail2 = { "备注:", ""};
	      PdfPTable shuTable2 = new PdfPTable(1);
	      for (int i = 0; i < ghDetail2.length; i++) {
	    	if(i==0){
	    		PdfPCell celln2 = new PdfPCell(new Paragraph(ghDetail2[i],chineseFont));
	    		celln2.setFixedHeight(20.045F);
	    		celln2.setNoWrap(false);
		        celln2.setHorizontalAlignment(1);
		        shuTable2.addCell(celln2);
	    	}else{
	    		PdfPCell celln2 = new PdfPCell(new Paragraph(REMARK,chineseFont));
	    		celln2.setFixedHeight(20.045F);
	    		celln2.setNoWrap(false);
		        celln2.setHorizontalAlignment(1);
		        shuTable2.addCell(celln2);
	    	}
	        
	        
	      }
	      PdfPCell shuCell2 = new PdfPCell(shuTable2);
	      table.addCell(shuCell2);
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
    //getOrg
    public void getOrg() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        try {
            BaseResultSet bs = dao.getOrg(user,page);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
}
