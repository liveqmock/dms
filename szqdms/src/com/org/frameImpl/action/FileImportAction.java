package com.org.frameImpl.action;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.framework.Globals;
import com.org.framework.common.User;
import com.org.framework.csv.FileReader;
import com.org.framework.fileimport.BaseImport;
import com.org.framework.fileimport.ExcelErrors;
import com.org.framework.filestore.FileObject;
import com.org.framework.util.Pub;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * @Copyright: Copyright (c) 2012
 * @author andy 
 */
public class FileImportAction
{
	private Logger logger = com.org.framework.log.log.getLogger("ImportXlsAction");
	//上下文对象
    private ActionContext atx = ActionContext.getContext();
	@SuppressWarnings("rawtypes")
	private List<Map> mapList=new ArrayList<Map>();
	private List<ExcelErrors> errList=new ArrayList<ExcelErrors>();
	private boolean isEmptyFile;
	public void importXls()
			throws Exception
    {
		RequestWrapper request = atx.getRequest();
		String bType = Pub.val(request, "bType");
		//关联业务主键
		String bParams = Pub.val(request, "bParams");
		String sCol = Pub.val(request, "sCol");
		if("".equals(sCol)) sCol = "0";
		String sRow = Pub.val(request, "sRow");
		if("".equals(sRow)) sRow = "0";
		FileObject file = request.getParamObject("myfile");
		String reMsg = "";
		try
		{
			if (file != null)
			{
				String ext = file.getFileName().substring(file.getFileName().lastIndexOf(".")).toLowerCase();
				List<String[]> list = new ArrayList<String[]>();
				// 验证后缀名及校验文件格式
				if (ext.equals(".xls") || ext.equals(".xlsx"))
				{
					list = FileReader.importXlsFile(0, Integer.parseInt(sCol,10), Integer.parseInt(sRow,10), file);
				} else if (ext.equals(".csv"))
				{
					list = FileReader.importCsvFile(Integer.parseInt(sCol,10), Integer.parseInt(sRow,10), file, "UTF-8");
				}
				
				// 导入的数据进行初步校验
				List<String> myErrorList = FileReader.preXlsCheck(list,bType,bParams,sCol, sRow);
				if (myErrorList != null && myErrorList.size() > 0)
				{
					request.setAttribute("itemslist", myErrorList);
					request.setAttribute("bType", bType);
					request.setAttribute("bRow", sRow);
					 request.setAttribute("bCol", sCol);
					reMsg = "/jsp/jQFrameWork/dialog/ImportErrors.jsp";
				} else
				{
					 //插入临时表
					 FileReader.insertXlsCheck(list,bType,bParams,sCol, sRow);
					 request.setAttribute("bType", bType);
					 request.setAttribute("bRow", sRow);
					 request.setAttribute("bCol", sCol);
					 request.setAttribute("bParams", bParams);
					 request.setAttribute("itemslist", list);
					 reMsg = "/jsp/jQFrameWork/dialog/ImportSuccess.jsp";
				}
			}else //文件为空报空指针异常
			{
				ExcelErrors error=new ExcelErrors();
				error.setRowNum(new Integer(0));
				error.setErrorDesc("文件不能为空!");
				errList.add(error);
				request.setAttribute("itemslist", error);
				reMsg = "/jsp/jQFrameWork/dialog/ImportErrors.jsp";
			}
		} catch (Exception e)
		{
			// TODO: handle exception
			reMsg = "/jsp/jQFrameWork/dialog/ImportErrors.jsp";
			logger.error(e);
            atx.setException(e);
		}
        atx.setForword(reMsg);
    }
	
	/**
	 * 导入文件
	 * @throws Exception
	 * @author andy.ten@tom.com 
	 * @Time Jul 11, 2014 11:07:09 AM
	 */
	public void importFile()
		throws Exception
	{
		RequestWrapper request = atx.getRequest();
		//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		String bType = Pub.val(request, "bType");
		String bParams = Pub.val(request, "bParams");
		String bUrl = Pub.val(request, "bUrl");
		FileObject uploadFile = request.getParamObject("myfile");
		//最大列数
		String maxCol = Pub.val(request, "maxCol");
		if("".equals(maxCol)) maxCol = "0";
		//充许最多连续空行数
		String sRow = Pub.val(request, "blankRows");//开始行，不算表头行
		if("".equals(sRow)) sRow = "0";
		String rePage = "";
		request.setAttribute("errorflag", "0");
		if(uploadFile==null)
		{	//文件为空报空指针异常
			ExcelErrors error=new ExcelErrors();
			error.setRowNum(new Integer(0));
			error.setErrorDesc("文件不能为空");
			errList.add(error);
			request.setAttribute("itemslist", error);
			request.setAttribute("errorflag", "1");
			rePage = "/jsp/jQFrameWork/dialog/ImportErrors.jsp";
		}else 
		{
			String fileName = uploadFile.getFileName();//获取文件名
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.length());//截取文件名
			ByteArrayInputStream is = new ByteArrayInputStream(uploadFile.getContent());//获取文件数据
			int errNum = BaseImport.checkFile(is, Integer.parseInt(maxCol), Integer.parseInt(sRow), errList, mapList);
			if(errNum == 5) request.setAttribute("errorflag", "1");
			if((mapList==null||mapList.size()==0)&&errNum==0)
			{
				ExcelErrors error=new ExcelErrors();
				error.setRowNum(new Integer(0));
				error.setErrorDesc("文件不能为空！");
				errList.add(error);
				request.setAttribute("itemslist", error);
				request.setAttribute("errorflag", "1");
				rePage = "/jsp/jQFrameWork/dialog/ImportErrors.jsp";
			}else
			{
				setIsEmptyFile();
				if(isEmptyFile&&errList.size()==0)
				{
					ExcelErrors error=new ExcelErrors();
					error.setRowNum(new Integer(0));
					error.setErrorDesc("文件不能为空");
					errList.add(error);
					request.setAttribute("errorflag", "1");
					request.setAttribute("itemslist", error);
					rePage = "/jsp/jQFrameWork/dialog/ImportErrors.jsp";
				}
			}
		}
		if("".equals(bUrl))
			throw new Exception("返回url为空！");
		else
			rePage = bUrl.replaceAll(";", "\\/");
		//没有错误
		if(errList.size() == 0)
		{
			//根据用户账号清空临时表并导入临时表
			FileReader.insertTmp(mapList, bType, bParams, user);
			//已导入临时表的数据进行初步校验
			List<ExcelErrors> checkErrList=new ArrayList<ExcelErrors>();
			checkErrList = FileReader.checkTmpData(mapList, bType, bParams,user);
			if (checkErrList != null && checkErrList.size() > 0)
			{
				request.setAttribute("itemslist", checkErrList);
				//rePage = "/jsp/jQFrameWork/dialog/ImportErrors.jsp";
			}else 
			{
				request.setAttribute("itemslist", new ArrayList<ExcelErrors>());
			}
		}else
		{
			request.setAttribute("itemslist", errList);
			//rePage = "/jsp/jQFrameWork/dialog/ImportErrors.jsp";
		}
		request.setAttribute("bType", bType);
		request.setAttribute("bParams", bParams);
		atx.setForword(rePage);
	}
	
	@SuppressWarnings("rawtypes")
	private void setIsEmptyFile()
	{
		isEmptyFile=true;
		if(null==mapList)
		{
			isEmptyFile=true;
		}else
		{
			Map map=null;
			for(int i=0;i<mapList.size();i++)
			{
				map=mapList.get(i);
				if(null!=map&&map.size()>0)
				{
					isEmptyFile=false;
				}
			}
		}
	}
}
