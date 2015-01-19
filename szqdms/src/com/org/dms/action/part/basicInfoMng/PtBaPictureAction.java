package com.org.dms.action.part.basicInfoMng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.basicInfoMng.PtBaInfoDao;
import com.org.dms.dao.part.basicInfoMng.PtBaPictureDao;
import com.org.dms.vo.part.FsFilestoreVO;
import com.org.dms.vo.part.PtBaInfoVO;
import com.org.dms.vo.part.PtBaInfoVO_Ext;
import com.org.dms.vo.part.PtBaPictureVO;
import com.org.frameImpl.Constant;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExcelErrors;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.filestore.utils.IdUtil2;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

public class PtBaPictureAction {
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "OrgPersonAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PtBaPictureDao dao = PtBaPictureDao.getInstance(atx);
    public void insert() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取封装后的response对象
    	//ResponseWrapper response = atx.getResponse();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
    	
        try
        {           
            PtBaPictureVO vo = new PtBaPictureVO();
//            PtBaInfoVO_Ext evo = new PtBaInfoVO_Ext();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
//			evo.setValue(hm);
			//设置通用字段
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			vo.setStatus(DicConstant.YXBS_01);		
			vo.setPartStatus(DicConstant.PJZT_01);		
			
			//判断配件代码是否已存在
			QuerySet qs = dao.checkPart_code(vo.getPartCode());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("配件信息已存在！");
				}
			}
					
	
			//通过dao，执行插入
			dao.insertPtBaPicture(vo);
			
//			evo.setUnit(vo.getUnit());	//计量单位
										//配件类型
//			voExt.bindFieldToDic("STATUS", "YXBS");	//基本表
			
//			evo.setAttribute(vo.getAttribute()); 	//配件属性ATTRIBUTEs
//			evo.setMinPack(vo.getMinPack());		//最小包装单位MIN_PACK
			//是否直发IF_DIRECT
			//是否保外IF_OUT
			//是否可订IF_BOOK
			//是否回运IF_RETURN
			//是否大总成IF_ASSEMBLY
			//所属总成BELONG_ASSEMBLY
			//是否扫码件IF_SCAN
			//是否指定供应商IF_SUPLY
			//配件状态PART_STATUS
			
			
			
			
//			evo.setPosition_name(vo.getBelongAssembly());
//			
//			evo.setPartId(vo.getBelongAssembly());	
//			evo.setPartId(vo.getBelongAssembly());	
//			evo.setPartId(vo.getBelongAssembly());	
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"配件照片信息新增成功！");           
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);            
        }
    }
    
    //修改
    public void update() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PtBaPictureVO tempVO = new PtBaPictureVO();
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			tempVO.setValue(hm);
			//设置通用字段
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
            dao.updatePtBaPicture(tempVO);
            
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"配件照片信息修改成功！");
         
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);           
        }
    }
    
    //删除
    public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String picture_id = Pub.val(request, "picture_id");
        try
        {
            //更新配件档案信息为无效状态
            dao.updatePtBaPictureStatus(picture_id, Constant.YXBS_02);        
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);          
        }
    }
    //删除图片
    public void deletepic() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String fjid = Pub.val(request, "fjid");
        try
        {
            //更新配件档案信息为无效状态
            dao.deletePic(fjid);        
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);          
        }
    }
    
    //查询
    public void search() throws Exception
    {
		
    	//定义request对象
	    RequestWrapper request = atx.getRequest();	   
	    HashMap<String,String> hm;
		//将request流转换为hashmap结构体
		hm = RequestUtil.getValues(request);
		
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
//		String partCode = Pub.val(request, "partCode");
//		String partName = Pub.val(request, "partName");
		String partCode = hm.get("PART_CODE");
		String partName = hm.get("PART_NAME");
		String if_null = Pub.val(request, "if_null");
		try
		{
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.search(page,partCode,partName,if_null);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 配件照片信息导出
     * @throws Exception
     */
    public void download()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response = atx.getResponse();
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
        try {
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(0,hBean);
    		
    		hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(1,hBean);
            
            
            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(2,hBean);

            hBean = new HeaderBean();
            hBean.setName("FJMC");
            hBean.setTitle("图片名称");
            header.add(3,hBean);
            
            
            QuerySet querySet = dao.download(conditions);
            ExportManager.exportFile(response.getHttpResponse(), "配件图片信息表", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    //验证配件采购价格明细零时表数据的准确性、此处调用searchPtBaPriceTmp
    public List<ExcelErrors> checkData()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	ExcelErrors errors = null;
		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		QuerySet qs = dao.searchTmp(user, "");//查询此用户下的所有配件图片临时表信息
		if(qs.getRowCount()>0){
			for(int i=0;i<qs.getRowCount();i++){
				errors=new ExcelErrors();
				String p="^(0|[1-9][0-9]*)$";//校验钱
				String rowNum = qs.getString(i+1, "ROW_NUM"); 				//行号
				String PART_CODE = qs.getString(i+1, "PART_CODE"); 			//配件代码
				String PART_NAME = qs.getString(i+1, "PART_NAME"); 			//配件名称
				String URL = qs.getString(i+1, "URL"); 						//图片URL
				String PICTURE_NAME = qs.getString(i+1, "PICTURE_NAME"); 	//图片名称

				
	   //1.必填项空校验
				//配件代码
				if(null==PART_CODE || "".equals(PART_CODE)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件代码不能为空!");
                    errorList.add(errors);
                }
				//配件名称
				if(null==PART_NAME || "".equals(PART_NAME)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件名称不能为空!");
                    errorList.add(errors);
                }
				//图片名称
				if(null==PICTURE_NAME || "".equals(PICTURE_NAME)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("图片名称不能为空!");
                    errorList.add(errors);
                }
			}
			//2.重复数据校验,零时表中存在相同的(配件代码+图片名称)则必须删除一个		
			QuerySet qs2 = dao.searchTmpRepeatData(user,"",""); 
			if(qs2.getRowCount() > 0)
			{
				for(int j=0; j<qs2.getRowCount(); j++){
					String partCode = qs2.getString(j+1, "PART_CODE"); 			//配件代码
					String pictureName = qs2.getString(j+1, "PICTURE_NAME"); 	//图片名称
					
					String errorStr = "";
					QuerySet qs3 = dao.searchTmpRepeatData(user, partCode, pictureName);
					for(int k=0; k<qs3.getRowCount(); k++){
						String rowNum3 = qs3.getString(k+1, "ROW_NUM"); //行号
						
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum3));
						
						if (k != (qs3.getRowCount() -1)) {
							errors.setErrorDesc("配件代码+图片名称是重复数据!");
							errorList.add(errors);
						}
						
						errorStr = errorStr + rowNum3 + ",";
					}
					
					errorStr = errorStr.substring(0, errorStr.length()-1);//删除最后一个","号
					errorStr = errorStr + "配件代码+图片名称重复，请删除重复数据！重复行是("+errorStr+")！";
					
					//添加错误描述
					errors.setErrorDesc(errorStr);
					errorList.add(errors);
				}
			}
		}
		
		if(errorList!=null && errorList.size()>0){
			return errorList;
		}else{
			return null;
		}
    }
    
    //配件照片零时表数据查询
    public void searchImport() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			BaseResultSet bs = dao.searchPtBaInfoTmpImport(page, conditions, user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    //导入确定按钮：把配件照片临时表的数据放入到主表
    public void confirmImport()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
 		try
 		{
 			String errorInfoRowNum = Pub.val(request, "rowNum");
 		    QuerySet qs = dao.searchTmp(user, errorInfoRowNum);//查询此用户下的所有配件照片临时表信息
 		    if(qs.getRowCount() > 0 ){
 		    	for(int i=0;i<qs.getRowCount();i++){
 		    		//必填
 					String PART_CODE = qs.getString(i+1,"PART_CODE");
 					String PART_NAME = qs.getString(i+1,"PART_NAME");
// 					String URL = qs.getString(i+1,"URL");
 					String PICTURE_NAME = qs.getString(i+1,"PICTURE_NAME");
 					String fid = IdUtil2.genFileId("5e57", "176d", PICTURE_NAME);
 					String URL = "http://172.16.8.24:8082/dms/partpicture/"+fid;
 					
 					
 					PtBaPictureVO vo=new PtBaPictureVO();
 					FsFilestoreVO filevo=new FsFilestoreVO();
 					 		
 					QuerySet qsId = dao.getTableId("PART_ID","PART_CODE","PT_BA_PICTURE",PART_CODE);
 					if(qsId.getRowCount() == 0){
 						QuerySet qssId = dao.getTableId("PART_ID","PART_CODE","PT_BA_INFO",PART_CODE);
 						String partId = qssId.getString(1, "PART_ID");
 						
 						vo.setPartId(partId);
 	 					vo.setPartCode(PART_CODE);
 	 					vo.setPartName(PART_NAME);
 	 					dao.insertImportPictureFromTmp(vo);
 	 					
 	 					filevo.setFjlj(URL);
 	 					filevo.setFjmc(PICTURE_NAME);
 	 					filevo.setYwzj(partId);
 	 					filevo.setFid(fid);
 	 					filevo.setFjzt("1");
 	 					filevo.setCjsj(Pub.getCurrentDate());
 	 					filevo.setCjr(user.getAccount());
 	 					filevo.setWjjbs("0");
 	 					filevo.setBlwjm("1");
 	 					dao.insertImportFileFromTmp(filevo);
 						
 					}else{
 						String partId = qsId.getString(1, "PART_ID");
 						filevo.setFjlj(URL);
 	 					filevo.setFjmc(PICTURE_NAME);
 	 					filevo.setYwzj(partId);
 	 					filevo.setFid(fid);
 	 					filevo.setFjzt("1");
 	 					filevo.setCjsj(Pub.getCurrentDate());
 	 					filevo.setCjr(user.getAccount());
 	 					filevo.setWjjbs("0");
 	 					filevo.setBlwjm("1");
 	 					dao.insertImportFileFromTmp(filevo);
 					}
 					
 		    	}
 		    }
 		    
 			atx.setOutMsg("", "导入成功！");
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 			atx.setOutMsg("", "请检查EXCEL数据！");
 		}
    }
 public void expTmpErrorData()throws Exception{
    	
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response = atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	// 将request流中的信息转化为where条件
        String conditions = Pub.val(request, "errorDataRowNum");
    	
        try {
        	
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("ROW_NUM");
    		hBean.setTitle("导入数据EXCEL行号");
    		header.add(0,hBean);
    		
    		hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(1,hBean);
            
            
            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(2,hBean);

            hBean = new HeaderBean();
            hBean.setName("PICTURE_NAME");
            hBean.setTitle("图片名称");
            header.add(3,hBean);
            

            QuerySet querySet = dao.expTmpErrorData(conditions,user);
            ExportManager.exportFile(response.getHttpResponse(), "配件图片导入错误信息", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }

}

