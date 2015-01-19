package com.org.framework.fileimport;

import com.org.framework.common.QuerySet;
import com.org.framework.dic.Dics;
import java.io.OutputStream;
import java.util.List;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Alignment;
import jxl.write.Label;
import jxl.write.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

@SuppressWarnings("deprecation")
public class ExcelBean
{
  public void expordExcel(OutputStream os, WritableWorkbook wbook, List<HeaderBean> header, QuerySet qs)
    throws Exception
  {
    WritableSheet wsheet = wbook.createSheet("第一页", 0);

    wsheet.getSettings().setShowGridLines(true);

    WritableFont wfont = new WritableFont(
      WritableFont.TAHOMA, 11, 
      WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
      Colour.BLACK);
    WritableCellFormat wcfFC = new WritableCellFormat(
      wfont);

    wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN);

    wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);

    wcfFC.setAlignment(Alignment.CENTRE);

    wcfFC.setBackground(Colour.GRAY_25);

    wsheet.setColumnView(0, 4);

    wsheet.setRowView(0, 300);

    if ((header != null) && (header.size() > 0))
    {
      for (int i = 0; i < header.size(); i++)
      {
        String title = ((HeaderBean)header.get(i)).getTitle();
        wsheet.addCell(new Label(i + 1, 1, title, wcfFC));
        int headerLen = title.length() * 2 + 4;
        wsheet.setColumnView(i + 1, headerLen);
      }

    }

    wfont = new WritableFont(
      WritableFont.TAHOMA, 10, 
      WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
      Colour.BLACK);
    wcfFC = new WritableCellFormat(wfont);

    wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN);

    wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);

    wcfFC.setAlignment(Alignment.CENTRE);

    for (int m = 0; m < qs.getRowCount(); m++)
    {
      for (int n = 0; n < header.size(); n++)
      {
        String cellText = qs.getString(m + 1, ((HeaderBean)header.get(n)).getName());
        if (cellText == null) cellText = "";
        String dic = ((HeaderBean)header.get(n)).getDic();
        if ((dic != null) && (!"".equals(dic)))
        {
          cellText = Dics.getTextByDicNameCode(dic, cellText);
        }
        //获取width
        int hw = ((HeaderBean)header.get(n)).getWidth();
        if(hw > 0)
        {
        	wsheet.setColumnView(n + 1, hw);
        }else
        {
        	String temp = cellText.replaceAll("[^\\x00-\\xff]", "xx");
            int contentLen = temp.length() / 2 * 2;
            if (contentLen == 0)
              contentLen = 9;
            int headerLen = wsheet.getColumnWidth(n + 1);
            if (contentLen > headerLen)
              wsheet.setColumnView(n + 1, contentLen);
        }
        
        if("".equals(cellText))
        	cellText = " ";
        wsheet.addCell(new Label(n + 1, m + 2, cellText, wcfFC));
      }
    }

    wbook.write();
    wbook.close();
    os.close();
  }
  
  public void expordExcel(OutputStream os, WritableWorkbook wbook, List<HeaderBean> header, QuerySet qs,String nrow,String ncol)
		    throws Exception
  {
    WritableSheet wsheet = wbook.createSheet("第一页", 0);

    wsheet.getSettings().setShowGridLines(true);

    WritableFont wfont = new WritableFont(
      WritableFont.TAHOMA, 11, 
      WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, 
      Colour.BLACK);
    WritableCellFormat wcfFC = new WritableCellFormat(
      wfont);

    wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN);

    wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);

    wcfFC.setAlignment(Alignment.CENTRE);

    wcfFC.setBackground(Colour.GRAY_25);
    if(ncol == "" || ncol == null) ncol = "0";
    if(nrow == "" || nrow == null) nrow = "0";
    int t = Integer.parseInt(ncol);
    int tt = t;
    while(tt >= 0)
    {
    	wsheet.setColumnView(tt, 4);
    	tt--;
    }
    int ttt = Integer.parseInt(nrow);
    while(ttt >= 0)
    {
    	wsheet.setRowView(ttt, 300);
    	ttt--;
    }

    if ((header != null) && (header.size() > 0))
    {
      for (int i = 0; i < header.size(); i++)
      {
        String title = ((HeaderBean)header.get(i)).getTitle();
        wsheet.addCell(new Label(i + t, 1, title, wcfFC));
        int headerLen = title.length() * 2 + 4;
        wsheet.setColumnView(i + t, headerLen);
      }
    }

    wfont = new WritableFont(
      WritableFont.TAHOMA, 10, 
      WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, 
      Colour.BLACK);
    wcfFC = new WritableCellFormat(wfont);

    wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN);

    wcfFC.setVerticalAlignment(VerticalAlignment.CENTRE);

    wcfFC.setAlignment(Alignment.CENTRE);

    for (int m = 0; m < qs.getRowCount(); m++)
    {
      for (int n = 0; n < header.size(); n++)
      {
        String cellText = qs.getString(m + 1, ((HeaderBean)header.get(n)).getName());
        if (cellText == null) cellText = "";
        String dic = ((HeaderBean)header.get(n)).getDic();
        if ((dic != null) && (!"".equals(dic)))
        {
          cellText = Dics.getTextByDicNameCode(dic, cellText);
        }
        //获取width
        int hw = ((HeaderBean)header.get(n)).getWidth();
        if(hw > 0)
        {
        	wsheet.setColumnView(n + 1, hw);
        }else
        {
        	String temp = cellText.replaceAll("[^\\x00-\\xff]", "xx");
            int contentLen = temp.length() / 2 * 2;
            if (contentLen == 0)
              contentLen = 9;
            int headerLen = wsheet.getColumnWidth(n + 1);
            if (contentLen > headerLen)
              wsheet.setColumnView(n + 1, contentLen);
        }
        if("".equals(cellText))
        	cellText = " ";
        wsheet.addCell(new Label(n + 1, m + 2, cellText, wcfFC));
      }
    }

    wbook.write();
    wbook.close();
    os.close();
  }
}