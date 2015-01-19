package com.org.dms.common;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.exception.DecodingFailedException;

import com.swetake.util.Qrcode;

/*import com.infodms.framecommon.bean.AclUserBean;
import com.infodms.framecommon.fileUpload.FileUpLoadDAO;
import com.infodms.framecommon.po.FsFileuploadPO;
import com.infoservice.filestore.FileStore;*/

public class TwoDimensionCode { 
	//private final FileUpLoadDAO dao = FileUpLoadDAO.getInstance();
    /** 
     * 生成二维码(QRCode)图片 
     * @param cont`ent 存储内容 
     * @param output 输出流 
     */  
    public void encoderQRCode(String content, OutputStream output) {  
        this.encoderQRCode(content, output, "png", 7);  
    }  
      
    /** 
     * 生成二维码(QRCode)图片 
     * @param content 存储内容 
     * @param imgType 图片类型 
     * @param fileName 文件名称
     * @param key 业务主键
     */  
   /* public String encoderQRCode(String content, String imgType,String fileName,String key,AclUserBean logonUser) {  
        return this.encoderQRCode(content, imgType, 7,fileName,key,logonUser);  
    } */ 
      
    /** 
     * 生成二维码(QRCode)图片 
     * @param content 存储内容 
     * @param output 输出流 
     * @param imgType 图片类型 
     */  
    public void encoderQRCode(String content, OutputStream output, String imgType) {  
        this.encoderQRCode(content, output, imgType, 7);  
    }  
  
    /** 
     * 生成二维码(QRCode)图片 
     * @param content 存储内容 
     * @param imgType 图片类型 
     * @param size 二维码尺寸 
     * @param fileName 文件名称 
     * @param key 业务主键 
     * @param logonUser session用户 
     */  
   /* public String encoderQRCode(String content, String imgType, int size,String fileName,String key,AclUserBean logonUser) {  
    	 String fileid="";
    	 String fileUrl="";
        try {  
            BufferedImage bufImg = this.qRCodeCommon(content, imgType, size);  
            FileStore store = FileStore.getInstance();
            ByteArrayOutputStream aa=new ByteArrayOutputStream();
            ImageIO.write(bufImg, imgType, aa);  
            ByteArrayInputStream is = new ByteArrayInputStream(aa.toByteArray());
			// 上传到文件服务器并获取文件ID
            fileid = store.write(fileName+"."+imgType, is);
            // 通过文件ID获取文件URL
		    fileUrl = store.getDomainURL(fileid);
            if(key == null) key = "-1";
			Long ywzj = new Long(key);
			FsFileuploadPO po = new FsFileuploadPO();
			po.setFileid(fileid);
			po.setFileurl(fileUrl);
			po.setFilename(fileName);
			po.setYwzj(ywzj);
			FileUpLoadDAO dao = new FileUpLoadDAO();
			dao.addDisableFile(po, logonUser);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return fileUrl;
    } */
    /** 
     * 生成二维码(QRCode)图片 
     * @param content 存储内容 
     * @param imgType 图片类型 
     * @param size 二维码尺寸 
     */  
    public BufferedImage encoderQRCode(String content, String imgType, int size) {  
    	 BufferedImage bufImg=null;
        try {  
             bufImg = this.qRCodeCommon(content, imgType, size);  
           
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return bufImg;
    } 
  
    /** 
     * 生成二维码(QRCode)图片 
     * @param content 存储内容 
     * @param output 输出流 
     * @param imgType 图片类型 
     * @param size 二维码尺寸 
     */  
    public void encoderQRCode(String content, OutputStream output, String imgType, int size) {  
        try {  
            BufferedImage bufImg = this.qRCodeCommon(content, imgType, size);  
            // 生成二维码QRCode图片  
            ImageIO.write(bufImg, imgType, output);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 生成二维码(QRCode)图片的公共方法 
     * @param content 存储内容 
     * @param imgType 图片类型 
     * @param size 二维码尺寸 
     * @return 
     */  
    private BufferedImage qRCodeCommon(String content, String imgType, int size) {  
        BufferedImage bufImg = null;  
        try {  
            Qrcode qrcodeHandler = new Qrcode();  
            // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小  
            qrcodeHandler.setQrcodeErrorCorrect('M');  
            qrcodeHandler.setQrcodeEncodeMode('B');  
            // 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大  
            qrcodeHandler.setQrcodeVersion(size);  
            // 获得内容的字节数组，设置编码格式  
            byte[] contentBytes = content.getBytes("utf-8");  
            // 图片尺寸  
            int imgSize = 67 + 12 * (size - 1);  
            bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);  
            Graphics2D gs = bufImg.createGraphics();  
            // 设置背景颜色  
            gs.setBackground(Color.WHITE);  
            gs.clearRect(0, 0, imgSize, imgSize);  
  
            // 设定图像颜色> BLACK  
            gs.setColor(Color.BLACK);  
            // 设置偏移量，不设置可能导致解析出错  
            int pixoff = 2;  
            // 输出内容> 二维码  
            if (contentBytes.length > 0 && contentBytes.length < 800) {  
                boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);  
                for (int i = 0; i < codeOut.length; i++) {  
                    for (int j = 0; j < codeOut.length; j++) {  
                        if (codeOut[j][i]) {  
                            gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);  
                        }  
                    }  
                }  
            } else {  
                throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");  
            }  
            gs.dispose();  
            bufImg.flush();
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return bufImg;  
    }  
      
    /** 
     * 解析二维码（QRCode） 
     * @param imgPath 图片路径 
     * @return 
     */  
    public String decoderQRCode(String imgPath) {  
        // QRCode 二维码图片的文件  
        File imageFile = new File(imgPath);  
        BufferedImage bufImg = null;  
        String content = null;  
        try {  
            bufImg = ImageIO.read(imageFile);  
            QRCodeDecoder decoder = new QRCodeDecoder();  
            content = new String(decoder.decode(new TwoDimensionCodeImage(bufImg)), "utf-8");   
        } catch (IOException e) {  
            System.out.println("Error: " + e.getMessage());  
            e.printStackTrace();  
        } catch (DecodingFailedException dfe) {  
            System.out.println("Error: " + dfe.getMessage());  
            dfe.printStackTrace();  
        }  
        return content;  
    }  
      
    /** 
     * 解析二维码（QRCode） 
     * @param input 输入流 
     * @return 
     */  
    public String decoderQRCode(InputStream input) {  
        BufferedImage bufImg = null;  
        String content = null;  
        try {  
            bufImg = ImageIO.read(input);  
            QRCodeDecoder decoder = new QRCodeDecoder();  
            content = new String(decoder.decode(new TwoDimensionCodeImage(bufImg)), "utf-8");   
        } catch (IOException e) {  
            System.out.println("Error: " + e.getMessage());  
            e.printStackTrace();  
        } catch (DecodingFailedException dfe) {  
            System.out.println("Error: " + dfe.getMessage());  
            dfe.printStackTrace();  
        }  
        return content;  
    }  
  
   /* public static void main(String[] args) {  
        //String imgPath = "D:/TMP/DOWNLOAD/Michael_QRCode.png";  
        String encoderContent = "part_code:341AAA00021;part_name:转向器回油管";  
        AclUserBean logonUser=new AclUserBean();
        TwoDimensionCode handler = new TwoDimensionCode();  
        handler.encoderQRCode(encoderContent, "png","a","-1",logonUser);  
//      try {  
//          OutputStream output = new FileOutputStream(imgPath);  
//          handler.encoderQRCode(content, output);  
//      } catch (Exception e) {  
//          e.printStackTrace();  
//      }  
        System.out.println("========decoder success!!!");  
    }  */
}
