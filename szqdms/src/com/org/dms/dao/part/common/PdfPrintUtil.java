package com.org.dms.dao.part.common;

import com.lowagie.text.Document;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class PdfPrintUtil {
	
	
	public static BaseFont bfChinese;
	
	public static PdfTemplate total;// 总页数
	public static BaseFont helv;//分页信息中的字体
	public static int pagerFontSize=11;//分页信息中的字体大小
	
	public static String pagerInfo1="第";
	public static String pagerInfo2="页，共";
	public static String pagerInfo3="页";
	
	public static  boolean onCloseDocument(PdfWriter writer, Document document) {
		total.beginText();
		total.setFontAndSize(helv, pagerFontSize);
		total.setTextMatrix(0, 0);
		total.showText(String.valueOf(writer.getPageNumber() - 1)+pagerInfo3);
		total.endText();
		return true;
	}

	/**
	 * 页面结束时调用此方法
	 */
	public static boolean onEndPage(PdfWriter writer, Document document,float W,float H) {
		PdfContentByte cb = writer.getDirectContent();
		cb.saveState();
		String text = pagerInfo1 + writer.getPageNumber() + pagerInfo2;
		float textSize = helv.getWidthPoint(text, pagerFontSize);
		cb.beginText();
		cb.setFontAndSize(helv, pagerFontSize);
        cb.setTextMatrix(W, H);
		cb.showText(text);
		cb.endText();
		cb.addTemplate(total, W+textSize, H);

		cb.restoreState();
		return true;
	}

	/**
	 * 文档打开时调用此方法
	 */
	public static boolean onOpenDocument(PdfWriter writer, Document document) {
		total = writer.getDirectContent().createTemplate(114f, 18.8f);
		total.setBoundingBox(new Rectangle(-200,-200,20, 20));
		try {
			helv = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI,
					BaseFont.NOT_EMBEDDED);
			
			helv=bfChinese;
			return true;
		} catch (Exception e) {
			throw new ExceptionConverter(e);
		}
	}
	

}
