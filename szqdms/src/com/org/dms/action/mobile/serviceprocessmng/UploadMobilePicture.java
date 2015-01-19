package com.org.dms.action.mobile.serviceprocessmng;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


import net.sf.json.JSONArray;


import com.org.framework.common.DBFactory;
import com.org.framework.common.SequenceUtil;
import com.org.framework.filestore.FileObject;
import com.org.framework.filestore.FileStore;
import com.org.framework.filestore.FsFilestoreVO;
import com.org.framework.util.Pub;

@SuppressWarnings("serial")
public class UploadMobilePicture extends HttpServlet {

		public void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {	
			doPost(request,response);
		}
		
		@SuppressWarnings({ "rawtypes", "unused" })
		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			response.setCharacterEncoding("UTF-8");

			ByteArrayOutputStream bos = null;
	        String workId = "";
	        String firstFileName = "";
			BufferedInputStream in =null;
			BufferedOutputStream outStream = null;
			String fileName="";
			DBFactory factory = null;
			List list = new ArrayList();
			Map<String,String> map = new HashMap<String,String>();
	        try {    
	        	DiskFileItemFactory fac = new DiskFileItemFactory();    
	            ServletFileUpload upload = new ServletFileUpload(fac);    
	            upload.setHeaderEncoding("UTF-8");    
	            // 获取多个上传文件    
	          //  List fileList  = upload.parseRequest(request);   
	            List fileList = fileList = upload.parseRequest(request);
	            // 遍历上传文件写入磁盘    
	            Iterator it = fileList.iterator();    
	            while (it.hasNext()) {    
	              FileItem obit = (FileItem)it.next();  
	                //如果是普通  表单参数
	              if(obit.isFormField()){ //普通域,获取页面参数
	                  String field = obit.getFieldName();
	                  
	               if(field.equals("workId"))
	               {
	            	   workId = obit.getString("UTF-8");
	               }

            
	              }// 如果是 多媒体	               
	              else if(!obit.isFormField()){
	                    DiskFileItem item = (DiskFileItem) obit;  
	                   
	                    // 如果item是文件上传表单域       
	                    // 获得文件名及路径       
	                     fileName = item.getName();
	    				if (fileName == null || fileName.trim().equals("")) {
	    					continue;
	    				}
	                    if (fileName != null) {
	                    	
	                        firstFileName=item.getName().substring(item.getName().lastIndexOf("\\")+1);    
	                        String formatName = ".jpg";
	                        if(firstFileName.toUpperCase().lastIndexOf(".JPG")>=0)
	                        {
	                        	//formatName = firstFileName.substring(firstFileName.lastIndexOf("."));//获取文件后缀名
	                        }else
	                        {
	                        	 firstFileName += formatName;
	                        }
	                        in = new BufferedInputStream(item.getInputStream());// 获得文件输入流    
	                         bos = new ByteArrayOutputStream();
	                        //上传成功， 
	                         int buf_size = 1024;   
	                          byte[] buffer = new byte[buf_size];   
	                         int len = 0;   
	                           while(-1 != (len = in.read(buffer,0,buf_size))){   
	                           bos.write(buffer,0,len);   
	                        }  
	                        in.close();
	                        //outStream.close();
               
	                    }     
	                }  
	            }
	            
				FileObject uploadFile = new FileObject();
				uploadFile.setContent(bos.toByteArray());
				factory = new DBFactory();
				bos.close();
				uploadFile.setFileName(firstFileName);
				String holdName="true";
				String folder="true";
				upload(uploadFile, workId, "手机端用户", holdName, folder,factory);
				factory.getConnection().commit();
	        }catch (Exception e)
			{
	        	if(factory != null)
	        	{
	        		try {
	        			factory.getConnection().rollback();
					} catch (SQLException ee) {
						// TODO Auto-generated catch block
						ee.printStackTrace();
					}
	        	}
	        	e.printStackTrace(System.out);
				map.put("status", "error");
				map.put("filename", firstFileName);
				//Pub.writeXmlErrorMessage(response, "意外错误！！" + e.toString());
			}
	        finally
			{
				if(in!=null)
					in.close();
				if(outStream!=null)
					outStream.close();
				if(bos!=null)
					bos.close();
				 if(factory != null)
				    {
				    	try {
							factory.getConnection().close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    	factory.setConnection(null);
				    	factory.setFactory(null);
				    	factory = null;
				    }
			}
	  }
		//上传附件主方法   
	    public String upload(FileObject uploadFile, String key, String userid, String holdName, String folder,DBFactory factory)
	    	    throws Exception
	    	  {
	    	    String fileName = uploadFile.getFileName();
	    	    fileName = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.length());
	    	    ByteArrayInputStream is = new ByteArrayInputStream(uploadFile.getContent());
	    	    FileStore store = FileStore.getInstance();
	    	    String fileid = store.write(uploadFile.getFileName(), is, holdName, folder);
	    	    String fileUrl = store.getDomainURL(fileid, folder);
	    	    FsFilestoreVO vo = new FsFilestoreVO();
	    	   
	    	    vo.setFjid(SequenceUtil.getCommonSerivalNumber(factory));
	    	    vo.setFid(fileid);
	    	    vo.setFjlj(fileUrl);
	    	    vo.setFjmc(fileName);
	    	    vo.setYwzj(key);
	    	    vo.setFjzt("1");
	    	    vo.setCjr(userid);
	    	    if ("true".equals(holdName))
	    	      vo.setBlwjm("1");
	    	    else {
	    	      vo.setBlwjm("0");
	    	    }
	    	    if ("true".equals(folder))
	    	      vo.setWjjbs("1");
	    	    else
	    	      vo.setWjjbs("0");
	    	    vo.setCjsj(Pub.getCurrentDate());
	    	    factory.insert(vo);
	    	    return vo.getFjid();
	    	  }
	    @SuppressWarnings("rawtypes")
		public void outPrintInfo(HttpServletRequest request, HttpServletResponse response,List list){
			String callback=request.getParameter("callback");
			JSONArray jsonarray = JSONArray.fromObject(list);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				String retStr = jsonarray.toString();
				//retStr = retStr.substring(1, retStr.length()-1);
				if(callback != null){
					out.write(callback+"("+retStr+")");
				}else{
					out.write(retStr);
				}
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}