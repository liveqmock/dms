package com.org.dms.action.part.basicInfoMng;

import java.util.*;

import com.org.dms.dao.part.basicInfoMng.TransAddressDao;
import com.org.dms.vo.part.PtBaChannelSafestockVO;
import com.org.dms.vo.part.PtBaTransportAddressTmpVO;
import com.org.dms.vo.part.PtBaTransportAddressVO;
import com.org.frameImpl.Constant;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExcelErrors;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import org.apache.log4j.Logger;
import com.org.dms.common.DicConstant;

import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * 发运地址action
 */
public class TransAddressAction
{
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "TransAddressAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    
    //定义dao对象
    private TransAddressDao dao = TransAddressDao.getInstance(atx);
    // 定义response对象
    ResponseWrapper responseWrapper= atx.getResponse();

    /**
     * 新增发运地址
     * @throws Exception
     * @Author suoxiuli 2014-07-14
     */
    public void insert() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	PtBaTransportAddressVO vo = new PtBaTransportAddressVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			
			//判断渠道商地址是否相同
			QuerySet qs = dao.checkOrgCodeAddress(vo.getOrgCode(), vo
					.getProvinceCode(), vo.getCityCode(), vo.getCountryCode(),vo.getAddress());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("此渠道商地址已存在，保存失败！");
				}
			}
			
			vo.setAddrType(DicConstant.JHDDLX_01);//默认小库
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			
			dao.insertTransAddr(vo);
			//返回插入结果和成功信息
			atx.setOutMsg(vo.getRowXml(),"发运地址新增成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 更新发运地址
     * @throws Exception
     * @Author suoxiuli 2014-07-14
     */
    public void update() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBaTransportAddressVO tempVO = new PtBaTransportAddressVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			
			//判断渠道商地址是否相同
			/*QuerySet qs = dao.checkOrgCodeAddress(tempVO.getOrgCode(), tempVO
					.getProvinceCode(), tempVO.getCityCode(), tempVO.getCountryCode());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("此渠道商地址已存在，保存失败！");
				}
			}*/
			
			tempVO.setAddrType(DicConstant.JHDDLX_01);//默认小库
			//设置通用字段
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());
			
            dao.updateTransAddr(tempVO);
            atx.setOutMsg(tempVO.getRowXml(),"发运地址修改成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除发运地址
     * @throws Exception
     * @Author suoxiuli 2014-07-14
     */
    public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String addressId = Pub.val(request, "adressId");
        try
        {
            //更新直发类型为无效状态
            boolean b = dao.updateTransAddrStatus(addressId, user.getAccount(), Constant.YXBS_02);
            atx.setOutMsg("","发运地址删除成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 发运地址查询
     * @throws Exception
     * Author suoxiuli 2014-07-14
     */
    public void search() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.search(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
  //-------------------------以下是导入、导出信息------------------------------------------- 
    /**
	 * 校验渠道商发运地址临时表数据
	 * @throws Exception
     * Author suoxiuli 2014-10-25
	 */
    public List<ExcelErrors> checkData()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	ExcelErrors errors = null;
		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		QuerySet qs = dao.searchTransAddressTmp(user, "");//查询此用户下的所有安全库存临时表信息
		if(qs.getRowCount()>0){
			for(int i=0;i<qs.getRowCount();i++){
				String rowNum = qs.getString(i+1, "ROW_NUM"); //行号
				String orgCode = qs.getString(i+1,"ORG_CODE");//服务商代码
	    		String linkMan = qs.getString(i+1,"LINK_MAN");//联系人
	    		String phone = qs.getString(i+1,"PHONE");//联系电话
	    		String fax = qs.getString(i+1,"FAX");//传真
	    		String eMail = qs.getString(i+1,"E_MAIL");//邮箱
	    		String zipCode = qs.getString(i+1,"ZIP_CODE");//邮编
	    		String addrType = qs.getString(i+1,"ADDR_TYPE");//地址类型
	    		String provinceCode = qs.getString(i+1,"PROVINCE_CODE");//省代码
	    		String cityCode = qs.getString(i+1,"CITY_CODE");//市代码
	    		String countryCode = qs.getString(i+1,"COUNTRY_CODE");//区县代码
	    		String address = qs.getString(i+1,"ADDRESS");//详细地址
	    		
	    		//1、空校验
				//服务商代码orgCode
				if(null==orgCode || "".equals(orgCode)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("渠道商代码不能为空!");
                    errorList.add(errors);
                }
				
				//联系人linkMan
				if(null==linkMan || "".equals(linkMan)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("联系人不能为空!");
                    errorList.add(errors);
                }
				
				//联系电话phone
				String phoneP = "[0-9]{3,4}//-?[0-9]+";//校验规则
				if(null==phone || "".equals(phone)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("联系电话不能为空!");
                    errorList.add(errors);
                }
				if(null!=phone && !"".equals(phone)){
	    			if(!phone.matches(phoneP)){
	    				errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
	                    errors.setErrorDesc("电话格式不正确!");
	                    errorList.add(errors);
	    			}
                }
				
				//传真fax
				if(null==fax || "".equals(fax)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("传真不能为空!");
                    errorList.add(errors);
                }
				if(null!=fax && !"".equals(fax)){
	    			if(!fax.matches(phoneP)){
	    				errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
	                    errors.setErrorDesc("传真格式不正确!");
	                    errorList.add(errors);
	    			}
                }
				
				//邮箱eMail
				String eMailP = "w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*";
				if(null==eMail || "".equals(eMail)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("邮箱不能为空!");
                    errorList.add(errors);
                }
				if(null!=eMail && !"".equals(eMail)){
	    			if(!eMail.matches(eMailP)){
	    				errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
	                    errors.setErrorDesc("邮箱输入格式不正确!");
	                    errorList.add(errors);
	    			}
                }
				
				//邮编zipCode
				String zipCodeP = "[0-9]{6}";//校验规则
				if(null==zipCode || "".equals(zipCode)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("邮编不能为空!");
                    errorList.add(errors);
                }
				if(null!=zipCode && !"".equals(zipCode)){
	    			if(!zipCode.matches(zipCodeP)){
	    				errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
	                    errors.setErrorDesc("邮编输入格式不正确!");
	                    errorList.add(errors);
	    			}
                }
				
				//地址类型addrType
				if(null==addrType || "".equals(addrType)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("地址类型不能为空!");
                    errorList.add(errors);
                }
				
				//省代码provinceCode
				if(null==provinceCode || "".equals(provinceCode)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("省代码不能为空!");
                    errorList.add(errors);
                }
				
				//市代码cityCode
				if(null==cityCode || "".equals(cityCode)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("市代码不能为空!");
                    errorList.add(errors);
                }
			}
			
			//3、重复数据校验，临时表中存在相同的仓库代码和配件代码信息，则必须删除一个
			QuerySet qs2 = dao.searchTransAddrTmpRepeatData(user, ""); 
			if(qs2.getRowCount() > 0)
			{
				for(int j=0; j<qs2.getRowCount(); j++){
					String orgCode2 = qs2.getString(j+1, "ORG_CODE"); //仓库代码
					
					String errorStr = "";
					QuerySet qs3 = dao.searchTransAddrTmpRepeatData(user, orgCode2);
					for(int k=0; k<qs3.getRowCount(); k++){
						String rowNum3 = qs3.getString(k+1, "ROW_NUM"); //行号
						
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum3));
						if (k != (qs3.getRowCount() -1)) {
							errors.setErrorDesc("行服务商代码重复数据!");
							errorList.add(errors);
						}
						
						errorStr = errorStr + rowNum3 + ",";
					}

					errorStr = errorStr.substring(0, errorStr.length()-1);//删除最后一个","号
					errorStr = errorStr + "行服务商代码重复，重复行是("+errorStr+")！";
	
					//添加错误描述
					//errors=new ExcelErrors();
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
    
    /**
     * 发运地址临时表查询（显示临时表数据在页面弹出框中）
     * @throws Exception
     * Author suoxiuli 2014-10-25
     */
    public void searchImport() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		
		try
		{
			BaseResultSet bs = dao.searchTransAddrTmpInfo(page, conditions, user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    /**
     * 导入确定按钮：把临时表的数据放入到主表
     * @throws Exception
     */
    public void transAddressImport()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String errorInfoRowNum = Pub.val(request, "rowNum");
 		try
 		{
 			//查询临时表的所有数据
 		    QuerySet qs = dao.searchTransAddressTmp(user, errorInfoRowNum);
 		    if(qs.getRowCount() > 0 ){
 		    	for(int i=0;i<qs.getRowCount();i++){
 		    		String rowNum3 = qs.getString(i+1, "ROW_NUM"); //行号
 		    		String userAccount = qs.getString(i+1,"USER_ACCOUNT");//导入人账号

 		    		PtBaTransportAddressVO vo = new PtBaTransportAddressVO();
 					vo.setOrgCode(qs.getString(i+1,"ORG_CODE"));//服务商代码
                    vo.setLinkMan(qs.getString(i+1,"LINK_MAN"));//联系人
                    vo.setPhone(qs.getString(i+1,"PHONE"));//固定电话
                    vo.setMobile(qs.getString(i+1,"MOBILE"));//移动电话
                    vo.setFax(qs.getString(i+1,"FAX"));//传真
                    vo.setEMail(qs.getString(i+1,"E_MAIL"));//邮箱
                    vo.setZipCode(qs.getString(i+1,"ZIP_CODE"));//邮编
                    vo.setAddrType(qs.getString(i+1,"ADDR_TYPE"));//地址类型
                    vo.setProvinceCode(qs.getString(i+1,"PROVINCE_CODE"));//省代码
                    vo.setCityCode(qs.getString(i+1,"CITY_CODE"));//市代码
                    vo.setCountryCode(qs.getString(i+1,"COUNTRY_CODE"));//区县代码
                    vo.setAddress(qs.getString(i+1,"ADDRESS"));//详细地址
 		    		
                    //通过代码得到名称
					QuerySet qs1 = dao.getNameByCode(vo.getOrgCode(), userAccount);
					if(qs1.getRowCount() > 0) {
						vo.setOrgId(qs1.getString(1,"ORG_ID"));
						vo.setOrgName(qs1.getString(1,"ONAME"));
						vo.setAddrType(qs1.getString(1,"DIC_CODE"));
						vo.setProvinceName(qs1.getString(1,"PROV_JC"));
						vo.setCityName(qs1.getString(1,"CITY_JC"));
						vo.setCountryName(qs1.getString(1,"COUT_JC"));
					}
					
                    //判断临时表服务商代码主表中有没：有--更新，否则--新增
                    QuerySet qs2 = dao.checkOrgCodeIsExists(vo.getOrgCode());
					if(qs2.getRowCount() > 0)
					{
						//更新
						String addressId = qs2.getString(1, 1);
						vo.setAddressId(addressId);
						vo.setUpdateUser(user.getAccount());
						vo.setUpdateTime(Pub.getCurrentDate());
						dao.updateTransAddr(vo);
						
					} else {
						//新增
						vo.setCreateUser(user.getAccount());
						vo.setCreateTime(Pub.getCurrentDate());
						vo.setStatus("100201");
						dao.insertTransAddr(vo);
					}
 		    	}
 		    }
 		    
 			atx.setOutMsg("", "导入成功！");
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    
    /**
     * 导出错误数据按钮：把临时表的错误数据导出到EXCEL
     * @throws Exception
     * Author suoxiuli 2014-11-5
     */
    public void expSafeStockTmpErrorData()throws Exception{
    	
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
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
            hBean.setName("ORG_CODE");
            hBean.setTitle("服务商代码");
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("LINK_MAN");
            hBean.setTitle("联系人");
            header.add(2,hBean);

            hBean = new HeaderBean();
            hBean.setName("PHONE");
            hBean.setTitle("联系电话");
            header.add(3,hBean);

            hBean = new HeaderBean();
            hBean.setName("FAX");
            hBean.setTitle("传真");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("E_MAIL");
            hBean.setTitle("邮箱");
            header.add(5,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ZIP_CODE");
            hBean.setTitle("邮编");
            header.add(6,hBean); 
            
            hBean = new HeaderBean();
            hBean.setName("ADDR_TYPE");
            hBean.setTitle("地址类型");
            header.add(7,hBean); 
            
            hBean = new HeaderBean();
            hBean.setName("PROVINCE_CODE");
            hBean.setTitle("省代码");
            header.add(8,hBean); 
            
            hBean = new HeaderBean();
            hBean.setName("CITY_CODE");
            hBean.setTitle("市代码");
            header.add(9,hBean); 
            
            hBean = new HeaderBean();
            hBean.setName("COUNTRY_CODE");
            hBean.setTitle("县区代码");
            header.add(10,hBean); 
            
            hBean = new HeaderBean();
            hBean.setName("ADDRESS");
            hBean.setTitle("详细地址");
            header.add(11,hBean); 

            QuerySet querySet = dao.expSafeStockTmpErrorData(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "transAddrErrorDataExp", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 发运地址导出
     * @throws Exception
     */
    public void download()throws Exception{

    	// 定义request对象
    	ResponseWrapper response = atx.getResponse();
        
        try {
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            
            hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(0,hBean);
    		
            hBean = new HeaderBean();
            hBean.setName("ORG_CODE");
            hBean.setTitle("渠道商代码");
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("ORG_NAME");
            hBean.setTitle("渠道商名称");
            header.add(2,hBean);

            hBean = new HeaderBean();
            hBean.setName("PROVINCE_NAME");
            hBean.setTitle("省份");
            header.add(3,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("CITY_NAME");
            hBean.setTitle("城市");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("COUNTRY_NAME");
            hBean.setTitle("区县");
            header.add(5,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ADDRESS");
            hBean.setTitle("地址");
            header.add(6,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("LINK_MAN");
            hBean.setTitle("联系人");
            header.add(7,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PHONE");
            hBean.setTitle("电话");
            header.add(8,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ZIP_CODE");
            hBean.setTitle("邮编");
            header.add(9,hBean);

            QuerySet querySet = dao.download();
            ExportManager.exportFile(response.getHttpResponse(), "发运地址", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
}