package com.org.dms.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.org.framework.common.QuerySet;
import com.org.framework.fileimport.HeaderBean;

public class PDFReport {
	Document document = new Document();// 建立一个Document对象      
    
	private static Font headfont ;// 设置字体大小  
    private static Font keyfont;// 设置字体大小  
    private static Font textfont;// 设置字体大小  
      
  
      
    static{  
        BaseFont bfChinese;  
        try {  
            //bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);  
            bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);  
            headfont = new Font(bfChinese, 10, Font.BOLD);// 设置字体大小  
            keyfont = new Font(bfChinese, 8, Font.BOLD);// 设置字体大小  
            textfont = new Font(bfChinese, 8, Font.NORMAL);// 设置字体大小  
        } catch (Exception e) {           
            e.printStackTrace();  
        }   
    }  
      
      
    @SuppressWarnings("unused")
	public PDFReport(ByteArrayOutputStream ba) {          
         document.setPageSize(PageSize.A4);// 设置页面大小  
         try {  
            //PdfWriter.getInstance(document,new FileOutputStream(file)); 
            PdfWriter writer = PdfWriter.getInstance(document, ba);
            document.open();   
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
          
          
    }  
    int maxWidth = 520;  
      
      
     public PdfPCell createCell(String value,com.itextpdf.text.Font font,int align){  
         PdfPCell cell = new PdfPCell();  
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);          
         cell.setHorizontalAlignment(align);      
         cell.setPhrase(new Phrase(value,font));  
        return cell;  
    }  
      
     public PdfPCell createCell(String value,com.itextpdf.text.Font font){  
         PdfPCell cell = new PdfPCell();  
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
         cell.setHorizontalAlignment(Element.ALIGN_CENTER);   
         cell.setPhrase(new Phrase(value,font));  
        return cell;  
    }  
  
     public PdfPCell createCell(String value,com.itextpdf.text.Font font,int align,int colspan){  
         PdfPCell cell = new PdfPCell();  
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
         cell.setHorizontalAlignment(align);      
         cell.setColspan(colspan);  
         cell.setPhrase(new Phrase(value,font));  
        return cell;  
    }  
    public PdfPCell createCell(String value,com.itextpdf.text.Font font,int align,int colspan,boolean boderFlag){  
         PdfPCell cell = new PdfPCell();  
         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);  
         cell.setHorizontalAlignment(align);      
         cell.setColspan(colspan);  
         cell.setPhrase(new Phrase(value,font));  
         cell.setPadding(3.0f);  
         if(!boderFlag){  
             cell.setBorder(0);  
             cell.setPaddingTop(15.0f);  
             cell.setPaddingBottom(8.0f);  
         }  
        return cell;  
    }  
     public PdfPTable createTable(int colNumber){  
        PdfPTable table = new PdfPTable(colNumber);  
        try{  
            table.setTotalWidth(maxWidth);  
            table.setLockedWidth(true);  
            table.setHorizontalAlignment(Element.ALIGN_CENTER);       
            table.getDefaultCell().setBorder(1);  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return table;  
    }  
     public PdfPTable createTable(float[] widths){  
            PdfPTable table = new PdfPTable(widths);  
            try{  
                table.setTotalWidth(maxWidth);  
                table.setLockedWidth(true);  
                table.setHorizontalAlignment(Element.ALIGN_CENTER);       
                table.getDefaultCell().setBorder(1);  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
            return table;  
        }  
      
     public PdfPTable createBlankTable(){  
         PdfPTable table = new PdfPTable(1);  
         table.getDefaultCell().setBorder(0);  
         table.addCell(createCell("", keyfont));           
         table.setSpacingAfter(20.0f);  
         table.setSpacingBefore(20.0f);  
         return table;  
     }  
       
     public void generatePDF(String title,List<HeaderBean> header, QuerySet qs) throws Exception{  
        PdfPTable table = createTable(header.size());  
        table.addCell(createCell(title+"：", headfont,Element.ALIGN_LEFT,header.size(),false));  
        if ((header != null) && (header.size() > 0))
        {
          for (int i = 0; i < header.size(); ++i)
          {
            table.addCell(createCell(((HeaderBean)header.get(i)).getTitle(), keyfont, Element.ALIGN_CENTER));  
          }

        }
     	 for (int m = 0; m < qs.getRowCount(); ++m)
         {
           for (int n = 0; n < header.size(); ++n)
           {
             String cellText = qs.getString(m + 1, ((HeaderBean)header.get(n)).getName());
             table.addCell(createCell(cellText, textfont)); 
             
           }
         }
        document.add(table);  
        document.close();  
        header=null;
        qs=null;
     }  
       
     public static void main(String[] args) throws Exception {  
         File file = new File("D:\\text.pdf");  
         file.createNewFile();  
        //new PDFReport(file).generatePDF();        
    } 
}
