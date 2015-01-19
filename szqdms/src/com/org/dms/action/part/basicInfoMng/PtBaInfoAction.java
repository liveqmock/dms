package com.org.dms.action.part.basicInfoMng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.basicInfoMng.PtBaInfoDao;
import com.org.dms.vo.part.PtBaInfoVO;
import com.org.dms.vo.part.PtBaInfoVO_Ext;
import com.org.dms.vo.part.PtBaPartSupplierRlVO;
import com.org.dms.vo.part.PtBaPriceLogVO;
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
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

public class PtBaInfoAction {
	//日志类
    private Logger logger = com.org.framework.log.log.getLogger(
        "PtBaInfoAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PtBaInfoDao dao = PtBaInfoDao.getInstance(atx);   
//    private PtBaPriceLogDao logdao = PtBaPriceLogDao.getInstance(atx);
    // 定义response对象
    ResponseWrapper responseWrapper= atx.getResponse();
    
    //新增配件信息
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
            PtBaInfoVO vo = new PtBaInfoVO();
            PtBaInfoVO_Ext extvo = new PtBaInfoVO_Ext();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			extvo.setValue(hm);
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			vo.setStatus(DicConstant.YXBS_01);		
			vo.setPartStatus(DicConstant.PJZT_01);        
			vo.setSeStatus(DicConstant.YXBS_02);
			// 是否可定
			vo.setIfBook(DicConstant.SF_01);
			
			if(hm.get("DIRECT_TYPE_ID")!=null && hm.get("DIRECT_TYPE_ID")!=""){
				vo.setIfDirect(DicConstant.SF_01);		
			}else{
				vo.setIfDirect(DicConstant.SF_02);		
			}
			
			//判断配件代码是否已存在
			QuerySet qs = dao.checkPart_code(vo.getPartCode());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("配件代码已存在，保存失败！");
				}
			}
	
			//通过dao，执行插入
			dao.insertPtBaInfo(vo);	
				
			
			PtBaPriceLogVO logvo = new PtBaPriceLogVO();
			HashMap<String,String> hmm;
			hmm = RequestUtil.getValues(request);
			logvo.setValue(hmm);
			logvo.setPartId(vo.getPartId());
			
			//返回插入结果和成功信息
			atx.setOutMsg(extvo.getRowXml(),"配件档案新增成功！");  		

        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);            
        }
    }
    //新增配件信息(服务)
    public void insertService() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	//获取封装后的response对象
    	//ResponseWrapper response = atx.getResponse();
    	//获取当前登录user对象
    	
        User user = (User) atx.getSession().get(Globals.USER_KEY);
    	
        try
        {
            PtBaInfoVO vo = new PtBaInfoVO();
            PtBaInfoVO_Ext extvo = new PtBaInfoVO_Ext();
			HashMap<String,String> hm;
			//将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			vo.setValue(hm);
			extvo.setValue(hm);
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			vo.setStatus(DicConstant.YXBS_01);				//状态
			vo.setSeStatus(DicConstant.YXBS_01);			//服务状态
			vo.setPartStatus(DicConstant.PJZT_02);      	//配件状态
			vo.setIfDirect(DicConstant.SF_02);
			
			//判断配件代码是否已存在
			QuerySet qs = dao.checkPart_code(vo.getPartCode());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("配件代码已存在，保存失败！");
				}
			}
	
			//通过dao，执行插入
			dao.insertPtBaInfo(vo);	
				
			//返回插入结果和成功信息
			atx.setOutMsg(extvo.getRowXml(),"配件档案(服务)新增成功！");  		

        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);            
        }
    }
 
    
    //服务信息修改
    public void updateService() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);  	
    	//获取当前登录user对象
    	PtBaInfoVO tempVO = new PtBaInfoVO();
    	PtBaInfoVO_Ext extvo = new PtBaInfoVO_Ext();
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			//将hashmap映射到vo对象中,完成匹配赋值
			tempVO.setValue(hm);
			tempVO.setUpdateUser(user.getPersonName());
			tempVO.setUpdateTime(Pub.getCurrentDate());
			
			extvo.setValue(hm);
			extvo.setUpdateUser(user.getPersonName());
			extvo.setUpdateTime(Pub.getCurrentDate());
			
            dao.updatePtBaInfo(tempVO);
            
            //返回更新结果和成功信息
            atx.setOutMsg(extvo.getRowXml(),"配件档案信息修改成功！");
         
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);           
        }
    }
  //配件信息修改
    public void update() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);  	
    	//获取当前登录user对象
    	PtBaInfoVO tempVO = new PtBaInfoVO();
    	PtBaInfoVO_Ext extvo = new PtBaInfoVO_Ext();
        try
        {
            HashMap<String,String> hm;
            //将request流转换为hashmap结构体
			hm = RequestUtil.getValues(request);
			String partId = hm.get("PART_ID");
			String partStatus = hm.get("PART_STATUS");
			if(DicConstant.PJZT_02.equals(partStatus)){
				QuerySet qs = dao.selectDealerStock(partId);
				QuerySet qs1 = dao.selectStock(partId);
				if(qs.getRowCount()>0 || qs1.getRowCount()>0){
					throw new Exception("该配件有库存数据，不能设置为无效.");
				}
			}
			//将hashmap映射到vo对象中,完成匹配赋值
			tempVO.setValue(hm);
			tempVO.setUpdateUser(user.getPersonName());
			tempVO.setUpdateTime(Pub.getCurrentDate());
			
			// 是否是服务维护
			if (!DicConstant.SF_01.equals(hm.get("IF_SERVER"))) {
				if(hm.get("DIRECT_TYPE_ID")!=null && hm.get("DIRECT_TYPE_ID")!=""){
					tempVO.setIfDirect(DicConstant.SF_01);		
				}else{
					tempVO.setIfDirect(DicConstant.SF_02);		
				}
			}
			
			extvo.setValue(hm);
			extvo.setUpdateUser(user.getPersonName());
			extvo.setUpdateTime(Pub.getCurrentDate());
			
			//extvo.setPosition_id(tempVO.getBelongAssembly());
            dao.updatePtBaInfo(tempVO);
            
            //返回更新结果和成功信息
            atx.setOutMsg(extvo.getRowXml(),"配件档案信息修改成功！");
         
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);           
        }
    }
    
  //修改配件价格
    public void updatePrice() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);  	
    	//销售价格
    	String sale_price = Pub.val(request, "sale_price");
        String orld_sale_price = Pub.val(request, "orld_sale_price");
        //计划价格
        String plan_price = Pub.val(request, "plan_price");
        String orld_plan_price = Pub.val(request, "orld_plan_price");
        //零售价格
        String retail_price = Pub.val(request, "retail_price");
        String orld_retail_price = Pub.val(request, "orld_retail_price");
        //采购价格
//        String pch_price = Pub.val(request, "pch_price");
//        String orld_pch_price = Pub.val(request, "orld_pch_price");
        
        HashMap<String,String> hm;
		hm = RequestUtil.getValues(request);
		String part_id = hm.get("PART_ID");
//		String relation_id = hm.get("RELATION_ID");
    	
        try
        {
        	PtBaInfoVO tempVO = new PtBaInfoVO();
        	tempVO.setValue(hm);
        	tempVO.setPartId(part_id);
			tempVO.setPriceUser(user.getPersonName());
			tempVO.setPriceTime(Pub.getCurrentDate());
            
            PtBaPriceLogVO volog = new PtBaPriceLogVO();
            volog.setValue(hm);
            if(!sale_price.equals(orld_sale_price)){
            	//修改销售价格
            	tempVO.setSalePrice(sale_price);
            	dao.updatePtBaInfo(tempVO);
            	
            	//插入销售价格轨迹
            	volog.setLogId("");
            	volog.setOriginalPrice(orld_sale_price);
    			volog.setNowPrice(sale_price);
    			volog.setPriceType("205502");
    			volog.setCreateUser(user.getPersonName());
    			volog.setCreateTime(Pub.getCurrentDate());
    			dao.insertPtBaPriceLog(volog);
            }
            if(!plan_price.equals(orld_plan_price)){
            	//修改计划价格
            	tempVO.setPlanPrice(plan_price);
            	dao.updatePtBaInfo(tempVO);
            	QuerySet qs = dao.queryStock(hm.get("PART_ID"));
//    			dao.updatePlanPrice(tempVO.getPartId(), tempVO.getPlanPrice());
    			//插入计划价格轨迹
    			if(qs.getRowCount()>0){
    				for(int i=0;i<qs.getRowCount();i++){
    					PtBaPriceLogVO volog2 = new PtBaPriceLogVO();
    					volog2.setValue(hm);
    					volog2.setOriginalPrice(orld_plan_price);
    					volog2.setNowPrice(plan_price);
    					volog2.setWarehouseCode(qs.getString(i+1, "WAREHOUSE_CODE"));
    					volog2.setUsableStock(qs.getString(i+1, "AMOUNT"));
    					volog2.setInStransStock(qs.getString(i+1, "OUT_AMOUNT"));
    					volog2.setPriceType(DicConstant.PJJGLX_04);
    					volog2.setCreateUser(user.getPersonName());
    					volog2.setCreateTime(Pub.getCurrentDate());
    					dao.insertPtBaPriceLog(volog2);
    				}
    			}else{
    				//插入计划价格轨迹
                	volog.setLogId("");
                	volog.setOriginalPrice(orld_plan_price);
        			volog.setNowPrice(plan_price);
        			volog.setPriceType("205504");
        			volog.setCreateUser(user.getPersonName());
        			volog.setCreateTime(Pub.getCurrentDate());
        			dao.insertPtBaPriceLog(volog);
    			}
            }
            if(!retail_price.equals(orld_retail_price)){
            	//修改零售价格
            	tempVO.setRetailPrice(retail_price);
            	dao.updatePtBaInfo(tempVO);
            	//插入零售价格轨迹
            	volog.setLogId("");
            	volog.setOriginalPrice(orld_retail_price);
    			volog.setNowPrice(retail_price);
    			volog.setPriceType("205501");
    			volog.setCreateUser(user.getPersonName());
    			volog.setCreateTime(Pub.getCurrentDate());
    			dao.insertPtBaPriceLog(volog);
            }
//            if(!pch_price.equals(orld_pch_price)){
//            	//修改采购价格
//            	PtBaPartSupplierRlVO rlvo = new PtBaPartSupplierRlVO(); 
//            	rlvo.setRelationId(relation_id);
//            	rlvo.setPlanPrice(plan_price);
//            	rlvo.setPchPrice(pch_price);
//            	rlvo.setPchUser(user.getPersonName());
//            	rlvo.setPchTime(Pub.getCurrentDate());
//            	dao.updatePchPrice(rlvo);
//            	
//            	
//            	//插入采购价格轨迹
//            	volog.setLogId("");
//            	volog.setOriginalPrice(orld_pch_price);
//    			volog.setNowPrice(pch_price);
//    			volog.setPriceType("205505");
//    			volog.setUpdateUser(user.getPersonName());
//    			volog.setUpdateTime(Pub.getCurrentDate());
//    			dao.insertPtBaPriceLog(volog);
//            }
            atx.setOutMsg(tempVO.getRowXml(),"配件价格信息修改成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);           
        }
    }
    
  //修改零售价格
    public void updateRetailPrice() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String now_price = Pub.val(request, "now_price");
        String original_price = Pub.val(request, "original_price");
        PtBaInfoVO vo = new PtBaInfoVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
//			vo.setPartId(hm.get("PART_ID"));
//			vo.setRetailPrice(hm.get("RETAIL_PRICE"));
			vo.setPriceUser(user.getPersonName());
			vo.setPriceTime(Pub.getCurrentDate());
			dao.updatePtBaInfoPrice(vo);
//			dao.updateRetailPrice(vo.getPartId(), vo.getRetailPrice());
			//插入零售价格轨迹
			PtBaPriceLogVO volog = new PtBaPriceLogVO();
			volog.setValue(hm);
			volog.setOriginalPrice(original_price);
			volog.setNowPrice(now_price);
			volog.setPriceType(DicConstant.PJJGLX_01);
			volog.setCreateUser(user.getPersonName());
			volog.setCreateTime(Pub.getCurrentDate());
			dao.insertPtBaPriceLog(volog);
            
            //返回更新结果和成功信息
            atx.setOutMsg(vo.getRowXml(),"配件零售价格修改成功！");
         
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);           
        }
    }
  //修改销售价格
    public void updateSalePrice() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String now_price = Pub.val(request, "now_price");
        String original_price = Pub.val(request, "original_price");
        PtBaInfoVO tempVO = new PtBaInfoVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);	
			tempVO.setPriceUser(user.getPersonName());
			tempVO.setPriceTime(Pub.getCurrentDate());
			dao.updatePtBaInfoPrice(tempVO);
			
//			dao.updateSalePrice(tempVO.getPartId(), tempVO.getSalePrice());
			//插入销售价格轨迹
			PtBaPriceLogVO volog = new PtBaPriceLogVO();
			volog.setValue(hm);
			volog.setOriginalPrice(original_price);
			volog.setNowPrice(now_price);
			volog.setPriceType(DicConstant.PJJGLX_02);
			volog.setCreateUser(user.getPersonName());
			volog.setCreateTime(Pub.getCurrentDate());
			dao.insertPtBaPriceLog(volog);
            
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"配件销售价格修改成功！");
         
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);           
        }
    }
  //修改服务索赔价格
    public void updateSeClPrice() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String now_price = Pub.val(request, "now_price");
        String original_price = Pub.val(request, "original_price");
        PtBaInfoVO tempVO = new PtBaInfoVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);	
			tempVO.setPriceUser(user.getPersonName());
			tempVO.setPriceTime(Pub.getCurrentDate());
			dao.updatePtBaInfoPrice(tempVO);
			
//			dao.updateSeClPrice(tempVO.getPartId(), tempVO.getSeClprice());
			//插入服务索赔价格轨迹
			PtBaPriceLogVO volog = new PtBaPriceLogVO();
			volog.setValue(hm);
			volog.setOriginalPrice(original_price);
			volog.setNowPrice(now_price);
			volog.setPriceType(DicConstant.PJJGLX_06);
			volog.setCreateUser(user.getPersonName());
			volog.setCreateTime(Pub.getCurrentDate());
//			volog.setUpdateUser(user.getPersonName());
//			volog.setUpdateTime(Pub.getCurrentDate());
			dao.insertPtBaPriceLog(volog);
            
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"服务索赔价格修改成功！");
         
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);           
        }
    }
  //修改服务追偿价格
    public void updateSeRePrice() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String now_price = Pub.val(request, "now_price");
        String original_price = Pub.val(request, "original_price");
        PtBaInfoVO tempVO = new PtBaInfoVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);	
			tempVO.setPriceUser(user.getPersonName());
			tempVO.setPriceTime(Pub.getCurrentDate());
			dao.updatePtBaInfoPrice(tempVO);
//			dao.updateSeRePrice(tempVO.getPartId(), tempVO.getSeReprice());
			//插入服务索赔价格轨迹
			PtBaPriceLogVO volog = new PtBaPriceLogVO();
			volog.setValue(hm);
			volog.setOriginalPrice(original_price);
			volog.setNowPrice(now_price);
			volog.setPriceType(DicConstant.PJJGLX_07);
			volog.setCreateUser(user.getPersonName());
			volog.setCreateTime(Pub.getCurrentDate());
			dao.insertPtBaPriceLog(volog);
            
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"服务追偿价格修改成功！");
         
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);           
        }
    }
  //修改计划价格
    public void updatePlanPrice() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String now_price = Pub.val(request, "now_price");
        String original_price = Pub.val(request, "original_price");
        PtBaInfoVO tempVO = new PtBaInfoVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);	
			
			tempVO.setPriceUser(user.getPersonName());
			tempVO.setPriceTime(Pub.getCurrentDate());
			dao.updatePtBaInfoPrice(tempVO);
			QuerySet qs = dao.queryStock(hm.get("PART_ID"));
//			dao.updatePlanPrice(tempVO.getPartId(), tempVO.getPlanPrice());
			//插入计划价格轨迹
			if(qs.getRowCount()>0){
				for(int i=0;i<qs.getRowCount();i++){
					PtBaPriceLogVO volog = new PtBaPriceLogVO();
					volog.setValue(hm);
					volog.setOriginalPrice(original_price);
					volog.setNowPrice(now_price);
					volog.setWarehouseCode(qs.getString(i+1, "WAREHOUSE_CODE"));
					volog.setUsableStock(qs.getString(i+1, "AMOUNT"));
					volog.setInStransStock(qs.getString(i+1, "OUT_AMOUNT"));
					volog.setPriceType(DicConstant.PJJGLX_04);
					volog.setCreateUser(user.getPersonName());
					volog.setCreateTime(Pub.getCurrentDate());
					dao.insertPtBaPriceLog(volog);
				}
			}else{
				PtBaPriceLogVO volog = new PtBaPriceLogVO();
				volog.setValue(hm);
				volog.setOriginalPrice(original_price);
				volog.setNowPrice(now_price);
				volog.setPriceType(DicConstant.PJJGLX_04);
				volog.setCreateUser(user.getPersonName());
				volog.setCreateTime(Pub.getCurrentDate());
				dao.insertPtBaPriceLog(volog);
			}
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"配件计划价格修改成功！");
         
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);           
        }
    }
  //修改军品价格
    public void updateArmyPrice() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String now_price = Pub.val(request, "now_price");
        String original_price = Pub.val(request, "original_price");
        PtBaInfoVO tempVO = new PtBaInfoVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);			
			tempVO.setPriceUser(user.getPersonName());
			tempVO.setPriceTime(Pub.getCurrentDate());
			dao.updatePtBaInfoPrice(tempVO);
//			dao.updateArmyPrice(tempVO.getPartId(), tempVO.getArmyPrice());
			
			//插入军品价格轨迹
			PtBaPriceLogVO volog = new PtBaPriceLogVO();
//			HashMap<String,String> hmm;
//			hmm = RequestUtil.getValues(request);
			volog.setValue(hm);
			volog.setOriginalPrice(original_price);
			volog.setNowPrice(now_price);
			volog.setPriceType(DicConstant.PJJGLX_03);
			volog.setCreateUser(user.getPersonName());
			volog.setCreateTime(Pub.getCurrentDate());
			dao.insertArmyPriceLog(volog);
            
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(),"配件军品价格修改成功！");
         
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);           
        }
    }
    
    //配件删除(配件)
    public void delete() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String partid = Pub.val(request, "partid");
        try
        {
            //更新配件档案信息为无效状态
            dao.updatePtBaInfoStatus(partid, DicConstant.PJZT_02);           
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);          
        }
    }
  //配件删除(服务)
    public void deleteService() throws Exception
    {
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String partid = Pub.val(request, "partid");
        try
        {
            //更新配件档案信息为无效状态
            dao.updatePtBaInfoServiceStatus(partid, DicConstant.YXBS_02);           
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);          
        }
    }
  //查询服务配件
    public void searchService() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();	   
        //定义查询分页对象
		PageManager page = new PageManager();
		//将request流中的信息转化为where条件
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.searchService(page,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    //查询
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
			//执行dao中search方法，BaseResultSet：结果集封装对象
			BaseResultSet bs = dao.search(page,conditions);
			//输出结果集，第一个参数”bs”为固定名称，不可修改
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
  //配件计划价格查询
    public void searchPlanPrice() throws Exception
    {
	    RequestWrapper request = atx.getRequest();	   
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchPlanPrice(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	} 
  //配件零售价格查询
    public void searchRetailPrice() throws Exception
    {
	    RequestWrapper request = atx.getRequest();	   
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchRetailPrice(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	} 
  //配件销售价格查询
    public void searchSalePrice() throws Exception
    {
	    RequestWrapper request = atx.getRequest();	   
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchSalePrice(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	} 
  //服务索赔价格查询
    public void searchSeClPrice() throws Exception
    {
	    RequestWrapper request = atx.getRequest();	   
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchSeClPrice(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	} 
  //服务追偿价格查询
    public void searchSeRePrice() throws Exception
    {
	    RequestWrapper request = atx.getRequest();	   
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchSeRePrice(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	} 
  //配件军品价格查询
    public void searchArmyPrice() throws Exception
    {
	    RequestWrapper request = atx.getRequest();	   
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.searchArmyPrice(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
  //配件计划、经销、零售价格查询
    public void selectThirdPrice() throws Exception
    {
	    RequestWrapper request = atx.getRequest();	   
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.selectThirdPrice(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
  //配件计划、经销、零售、采购价格查询
    public void selectPrice() throws Exception
    {
	    RequestWrapper request = atx.getRequest();	   
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.selectPrice(page,conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
  //配件价格查询
    public void searchPrice() throws Exception
    {
	    RequestWrapper request = atx.getRequest();	   
		PageManager page = new PageManager();
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
    public void partSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.partSearch(page, conditions);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
    public void positionSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		String p_id = Pub.val(request, "p_id");
		try
		{
			BaseResultSet bs = dao.positionSearch(page,conditions,p_id);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
  //验证配件档案明细零时表数据的准确性、此处调用searchSafeStockTmpInfo
    public List<ExcelErrors> checkData()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	ExcelErrors errors = null;
		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		QuerySet qs = dao.searchPtBaInfoTmp(user, "");//查询此用户下的所有配件档案临时表信息
		if(qs.getRowCount()>0){
			for(int i=0;i<qs.getRowCount();i++){
				errors=new ExcelErrors();
				String p="^(0|[1-9][0-9]*)$";//校验钱
				String rowNum = qs.getString(i+1, "ROW_NUM"); //行号
				String PART_CODE = qs.getString(i+1, "PART_CODE"); //配件代码
				String PART_NAME = qs.getString(i+1, "PART_NAME"); //配件名称
				String UNIT = qs.getString(i+1,"UNIT");//计量单位
				String PART_TYPE = qs.getString(i+1,"PART_TYPE");//配件类型
				String IF_ASSEMBLY = qs.getString(i+1,"IF_ASSEMBLY");//是否大总成
				String IF_OUT = qs.getString(i+1,"IF_OUT");//是否保外
				String IF_SUPLY = qs.getString(i+1,"IF_SUPLY");//是否指定供货商
				String IF_OIL = qs.getString(i+1,"IF_OIL");//是否油品
				String IF_SCAN = qs.getString(i+1,"IF_SCAN");//是否扫码
				String PART_STATUS = qs.getString(i+1,"PART_STATUS");//配件状态
				String MIN_PACK = qs.getString(i+1,"MIN_PACK");//最小包装数
				String MIN_UNIT = qs.getString(i+1,"MIN_UNIT");//最小包装单位
				
				
				//选填
//				String POSITION_NAME = qs.getString(i+1,"POSITION_NAME");//一级总成
//				String F_POSITION_NAME = qs.getString(i+1,"F_POSITION_NAME");//二级总成
				
//				String PART_NO = qs.getString(i+1,"PART_NO");//参图号
//				String REBATE_TYPE = qs.getString(i+1,"REBATE_TYPE");//返利类别
//				String ATTRIBUTE = qs.getString(i+1,"ATTRIBUTE");//配件属性
//				String DIRECT_TYPE_NAME = qs.getString(i+1,"DIRECT_TYPE_NAME");//直发类型
//				String F_PART_CODE = qs.getString(i+1,"F_PART_CODE");//父级零件号
//				String F_PART_NAME = qs.getString(i+1,"F_PART_NAME");//父级零件名称
//				String BELONG_ASSEMBLY = qs.getString(i+1,"BELONG_ASSEMBLY");//所属七大总成
//				String SPE_TYPE = qs.getString(i+1,"SPE_TYPE");//配件特殊分类
				
//				String SPE_NAME = qs.getString(i+1,"SPE_NAME");//配件特殊分类
				
				
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
				//计量单位
				if(null==UNIT || "".equals(UNIT)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("计量单位不能为空!");
                    errorList.add(errors);
                }else{
                	if(UNIT.matches(p)){
	    				errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
	                    errors.setErrorDesc("计量单位不能填写数字!");
	                    errorList.add(errors);
	    			}
                }
				//配件类型
				if(null==PART_TYPE || "".equals(PART_TYPE)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件类型不能为空!");
                    errorList.add(errors);
                }else{
                	if(PART_TYPE.matches(p)){
	    				errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum));
	                    errors.setErrorDesc("配件类型不能填写数字!");
	                    errorList.add(errors);
	    			}
                }
				//是否大总成
				if(null==IF_ASSEMBLY || "".equals(IF_ASSEMBLY)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("是否大总成不能为空!");
                    errorList.add(errors);
                }if(IF_ASSEMBLY.matches(p)){
    				errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("是否大总成不能填写数字!");
                    errorList.add(errors);
    			}
				//是否保外
				if(null==IF_OUT || "".equals(IF_OUT)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("是否保外不能为空!");
                    errorList.add(errors);
                }if(IF_OUT.matches(p)){
    				errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("是否保外不能填写数字!");
                    errorList.add(errors);
    			}
				//是否指定供货商
				if(null==IF_SUPLY || "".equals(IF_SUPLY)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("是否指定供货商不能为空!");
                    errorList.add(errors);
                }if(IF_SUPLY.matches(p)){
    				errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("是否指定供货商不能填写数字!");
                    errorList.add(errors);
    			}
				//是否油品
				if(null==IF_OIL || "".equals(IF_OIL)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("是否油品不能为空!");
                    errorList.add(errors);
                }if(IF_OIL.matches(p)){
    				errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("是否油品不能填写数字!");
                    errorList.add(errors);
    			}
				//是否扫码
				if(null==IF_SCAN || "".equals(IF_SCAN)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("是否扫码不能为空!");
                    errorList.add(errors);
                }if(IF_SCAN.matches(p)){
    				errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("是否扫码不能填写数字!");
                    errorList.add(errors);
    			}
				//配件状态
				if(null==PART_STATUS || "".equals(PART_STATUS)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("配件状态不能为空!");
                    errorList.add(errors);
                }if(PART_STATUS.matches(p)){
    				errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("配件状态不能填写数字!");
                    errorList.add(errors);
    			}
              //最小包装数
				if(null==MIN_PACK || "".equals(MIN_PACK)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("最小包装数不能为空!");
                    errorList.add(errors);
                }
              //最小包装单位
				if(null==MIN_UNIT || "".equals(MIN_UNIT)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("最小包装单位不能为空!");
                    errorList.add(errors);
                }if(MIN_UNIT.matches(p)){
    				errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("最小包装单位不能填写数字!");
                    errorList.add(errors);
    			}
                
                
                
                
                
//				//一级总成
//				if(null==POSITION_NAME || "".equals(POSITION_NAME)){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("一级总成不能为空!");
//                    errorList.add(errors);
//                }if(POSITION_NAME.matches(p)){
//    				errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//                    errors.setErrorDesc("一级总成不能填写数字!");
//                    errorList.add(errors);
//    			}
//				//二级总成
//				if(null==F_POSITION_NAME || "".equals(F_POSITION_NAME)){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("二级总成不能为空!");
//                    errorList.add(errors);
//                }if(F_POSITION_NAME.matches(p)){
//    				errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//                    errors.setErrorDesc("二级总成不能填写数字!");
//                    errorList.add(errors);
//    			}
		
			}
			//2.重复数据校验,零时表中存在相同的配件代码则必须删除一个		
			QuerySet qs2 = dao.searchPtBaInfoTmpRepeatData(user,""); 
			if(qs2.getRowCount() > 0)
			{
				for(int j=0; j<qs2.getRowCount(); j++){
					String partCode = qs2.getString(j+1, "PART_CODE"); //配件代码
					
					String errorStr = "";
					QuerySet qs3 = dao.searchPtBaInfoTmpRepeatData(user, partCode);
					for(int k=0; k<qs3.getRowCount(); k++){
						String rowNum3 = qs3.getString(k+1, "ROW_NUM"); //行号
						
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum3));
						
						if (k != (qs3.getRowCount() -1)) {
							errors.setErrorDesc("配件代码是重复数据!");
							errorList.add(errors);
						}
						
						errorStr = errorStr + rowNum3 + ",";
					}
					
					errorStr = errorStr.substring(0, errorStr.length()-1);//删除最后一个","号
					errorStr = "配件代码重复，请删除重复数据！重复行是("+errorStr+")！";
					
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
    
    
    //验证配件价格明细零时表数据的准确性、此处调用searchPtBaPriceTmp
    public List<ExcelErrors> checkPriceData()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	ExcelErrors errors = null;
		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		QuerySet qs = dao.searchPtBaPriceTmp(user, "");//查询此用户下的所有配件价格临时表信息
		if(qs.getRowCount()>0){
			for(int i=0;i<qs.getRowCount();i++){
				errors=new ExcelErrors();
				String p="^(0|[1-9][0-9]*)$";//校验钱
				String rowNum = qs.getString(i+1, "ROW_NUM"); //行号
				String PART_CODE = qs.getString(i+1, "PART_CODE"); //配件代码
				String PART_NAME = qs.getString(i+1, "PART_NAME"); //配件名称
//				String PCH_PRICE = qs.getString(i+1, "PCH_PRICE"); //采购价格
				String PLAN_PRICE = qs.getString(i+1, "PLAN_PRICE"); //计划价格
				String SALE_PRICE = qs.getString(i+1, "SALE_PRICE"); //销售价格
				String RETAIL_PRICE = qs.getString(i+1, "RETAIL_PRICE"); //零售价格
//				String SUPPLIER_CODE = qs.getString(i+1, "SUPPLIER_CODE"); //供应商代码
//				String SUPPLIER_NAME = qs.getString(i+1, "SUPPLIER_NAME"); //供应商名称

				
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
//				//采购价格
//				if(null==PCH_PRICE || "".equals(PCH_PRICE)){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("采购价格不能为空!");
//                    errorList.add(errors);
//                }
				
				//计划价格
				if(null==PLAN_PRICE || "".equals(PLAN_PRICE)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("计划价格不能为空!");
                    errorList.add(errors);
                }
				
				//销售价格
				if(null==SALE_PRICE || "".equals(SALE_PRICE)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("销售价格不能为空!");
                    errorList.add(errors);
                }
				
				//零售价格
				if(null==RETAIL_PRICE || "".equals(RETAIL_PRICE)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("零售价格不能为空!");
                    errorList.add(errors);
                }
				
//				//供应商代码
//				if(null==SUPPLIER_CODE || "".equals(SUPPLIER_CODE)){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("供应商代码不能为空!");
//                    errorList.add(errors);
//                }
//				
//				//供应商名称
//				if(null==SUPPLIER_NAME || "".equals(SUPPLIER_NAME)){
//					errors=new ExcelErrors();
//					errors.setRowNum(Integer.parseInt(rowNum));
//					errors.setErrorDesc("供应商名称不能为空!");
//                    errorList.add(errors);
//                }
				
		
			}
			//2.重复数据校验,零时表中存在相同的配件代码则必须删除一个		
			QuerySet qs2 = dao.searchPtBaPriceTmpRepeatData(user,""); 
			if(qs2.getRowCount() > 0)
			{
				for(int j=0; j<qs2.getRowCount(); j++){
					String partCode = qs2.getString(j+1, "PART_CODE"); //配件代码
					
					String errorStr = "";
					QuerySet qs3 = dao.searchPtBaPriceTmpRepeatData(user, partCode);
					for(int k=0; k<qs3.getRowCount(); k++){
						String rowNum3 = qs3.getString(k+1, "ROW_NUM"); //行号
						
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum3));
						
						if (k != (qs3.getRowCount() -1)) {
							errors.setErrorDesc("配件代码是重复数据!");
							errorList.add(errors);
						}
						
						errorStr = errorStr + rowNum3 + ",";
					}
					
					errorStr = errorStr.substring(0, errorStr.length()-1);//删除最后一个","号
					errorStr = errorStr + "配件代码重复，请删除重复数据！重复行是("+errorStr+")！";
					
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
    //验证配件采购价格明细零时表数据的准确性、此处调用searchPtBaPriceTmp
    public List<ExcelErrors> checkPchPriceData()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	ExcelErrors errors = null;
		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		QuerySet qs = dao.searchPtBaPchPriceTrueTmp(user, "");//查询此用户下的所有配件采购价格临时表信息
		if(qs.getRowCount()>0){
			for(int i=0;i<qs.getRowCount();i++){
				errors=new ExcelErrors();
				String p="^(0|[1-9][0-9]*)$";//校验钱
				String rowNum = qs.getString(i+1, "ROW_NUM"); //行号
				String PART_CODE = qs.getString(i+1, "PART_CODE"); //配件代码
				String PART_NAME = qs.getString(i+1, "PART_NAME"); //配件名称
				String SUPPLIER_CODE = qs.getString(i+1, "SUPPLIER_CODE"); //供应商代码
				String SUPPLIER_NAME = qs.getString(i+1, "SUPPLIER_NAME"); //供应商名称
				String PCH_PRICE = qs.getString(i+1, "PCH_PRICE"); //采购价格

				
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
				//供应商代码
				if(null==SUPPLIER_CODE || "".equals(SUPPLIER_CODE)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("供应商代码不能为空!");
                    errorList.add(errors);
                }
				
				//供应商名称
				if(null==SUPPLIER_NAME || "".equals(SUPPLIER_NAME)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("供应商名称不能为空!");
                    errorList.add(errors);
                }
				//采购价格
				if(null==PCH_PRICE || "".equals(PCH_PRICE)){
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNum));
					errors.setErrorDesc("采购价格不能为空!");
                    errorList.add(errors);
                }
				
				
				
				
				
				
		
			}
			//2.重复数据校验,零时表中存在相同的(配件代码+供应商代码)则必须删除一个		
			QuerySet qs2 = dao.searchPtBaPchPriceTmpRepeatData(user,"",""); 
			if(qs2.getRowCount() > 0)
			{
				for(int j=0; j<qs2.getRowCount(); j++){
					String partCode = qs2.getString(j+1, "PART_CODE"); //配件代码
					String supplierCode = qs2.getString(j+1, "SUPPLIER_CODE"); //供应商代码
					
					String errorStr = "";
					QuerySet qs3 = dao.searchPtBaPchPriceTmpRepeatData(user, partCode, supplierCode);
					for(int k=0; k<qs3.getRowCount(); k++){
						String rowNum3 = qs3.getString(k+1, "ROW_NUM"); //行号
						
						errors=new ExcelErrors();
						errors.setRowNum(Integer.parseInt(rowNum3));
						
						if (k != (qs3.getRowCount() -1)) {
							errors.setErrorDesc("配件代码+供应商代码是重复数据!");
							errorList.add(errors);
						}
						
						errorStr = errorStr + rowNum3 + ",";
					}
					
					errorStr = errorStr.substring(0, errorStr.length()-1);//删除最后一个","号
					errorStr = errorStr + "配件代+供应商代码重复，请删除重复数据！重复行是("+errorStr+")！";
					
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
    
    /**
     * 配件明细导入临时表查询
     * @throws Exception
     * Author suoxiuli 2014-07-25
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
			BaseResultSet bs = dao.searchPtBaInfoTmpImport(page, conditions, user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * 配件采购价格零时表查询
     * @throws Exception
     * Author suoxiuli 2014-07-25
     */
    public void searchPchPriceTmp() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			BaseResultSet bs = dao.searchPtBaPchPriceTmpImport(page, conditions, user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    /**
     * 配件价格零时表查询
     * @throws Exception
     * Author suoxiuli 2014-07-25
     */
    public void searchPriceTmp() throws Exception
    {
    	//定义request对象
	    RequestWrapper request = atx.getRequest();
	    User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		
		try
		{
			BaseResultSet bs = dao.searchPtBaPriceTmpImport(page, conditions, user);
			atx.setOutData("bs", bs);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
     
    /**
     * 导入确定按钮：把配件档案临时表的数据放入到主表
     * ptbainfoTMP表数据需要改造为联合数据
     * @throws Exception
     */
    public void PtBaInfoImport()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
 		try
 		{
 			String errorInfoRowNum = Pub.val(request, "rowNum");
 		    QuerySet qs = dao.searchPtBaInfoTmp(user, errorInfoRowNum);//查询此用户下的所有配件档案临时表信息
 		    if(qs.getRowCount() > 0 ){
 		    	for(int i=0;i<qs.getRowCount();i++){
 		    		//必填
 					String PART_CODE = qs.getString(i+1,"PART_CODE");
 					String PART_NAME = qs.getString(i+1,"PART_NAME");
 					String UNIT = qs.getString(i+1,"UNIT");							//字典
 					String PART_TYPE = qs.getString(i+1,"PART_TYPE");				//字典
 					String IF_ASSEMBLY = qs.getString(i+1,"IF_ASSEMBLY");			//字典
 					String IF_OUT = qs.getString(i+1,"IF_OUT");						//字典
 					String IF_SUPLY = qs.getString(i+1,"IF_SUPLY");					//字典
 					String IF_OIL = qs.getString(i+1,"IF_OIL");						//字典
 					String IF_SCAN = qs.getString(i+1,"IF_SCAN");					//字典
 					String PART_STATUS = qs.getString(i+1,"PART_STATUS");			//字典
 					
 					String MIN_PACK = qs.getString(i+1,"MIN_PACK");			
 					String MIN_UNIT = qs.getString(i+1,"MIN_UNIT");			//字典
 					
 					
 					//选填
 					String POSITION_NAME = qs.getString(i+1,"POSITION_NAME");		//一级总成名称
 					String F_POSITION_NAME = qs.getString(i+1,"F_POSITION_NAME");	//二级总成名称
 					
 					String PART_NO = qs.getString(i+1,"PART_NO");
 					String REBATE_TYPE = qs.getString(i+1,"REBATE_TYPE");
 					String ATTRIBUTE = qs.getString(i+1,"ATTRIBUTE");				//字典
 					String DIRECT_TYPE_NAME = qs.getString(i+1,"DIRECT_TYPE_NAME"); //表选
 					String F_PART_CODE = qs.getString(i+1,"F_PART_CODE");
 					String F_PART_NAME = qs.getString(i+1,"F_PART_NAME");
 					String BELONG_ASSEMBLY = qs.getString(i+1,"BELONG_ASSEMBLY");	//字典
// 					String SPE_TYPE = qs.getString(i+1,"SPE_TYPE");
 					
 					String SPE_NAME = qs.getString(i+1,"SPE_NAME");
 					
 					
 		//必填字典项			
 					//字典名取字典码
 					String UNIT_CODE = dao.getDicTree(UNIT,"计量单位");
 					String PART_TYPE_CODE = dao.getDicTree(PART_TYPE,"配件类别");
 					String IF_ASSEMBLY_CODE = dao.getDicTree(IF_ASSEMBLY,"是否");
 					String IF_OUT_CODE = dao.getDicTree(IF_OUT,"是否");
 					String IF_SUPLY_CODE = dao.getDicTree(IF_SUPLY,"是否");
 					String IF_OIL_CODE = dao.getDicTree(IF_OIL,"是否");
 					String IF_SCAN_CODE = dao.getDicTree(IF_SCAN,"是否");
 					String PART_STATUS_CODE = dao.getDicTree(PART_STATUS,"配件状态");
 					String MIN_UNIT_CODE = dao.getDicTree(MIN_UNIT,"计量单位");
 					
 					
 					
 					//主表VO
 		    		PtBaInfoVO vo=new PtBaInfoVO();
 		    		
 		    		vo.setPartCode(PART_CODE);
 		    		vo.setPartName(PART_NAME);
 		    		vo.setUnit(UNIT_CODE);
 		    		vo.setPartType(PART_TYPE_CODE);
 		    		vo.setIfAssembly(IF_ASSEMBLY_CODE);
 		    		vo.setIfOut(IF_OUT_CODE);
 		    		vo.setIfSuply(IF_SUPLY_CODE);
 		    		vo.setIfOil(IF_OIL_CODE);
 		    		vo.setIfScan(IF_SCAN_CODE);
 		    		vo.setPartStatus(PART_STATUS_CODE);
 		    		vo.setMinPack(MIN_PACK);
 		    		vo.setMinUnit(MIN_UNIT_CODE);
 		    		
 		    		
 		    		
 		    		
 		    		//选填
 		    		if(POSITION_NAME != "" && POSITION_NAME != null){
 		    			//表选字典名取字典ID、字典码
 						//一级总成
	 					QuerySet qs1 = dao.getBxCode(POSITION_NAME);
	 					String POSITION_ID = qs1.getString(1,"POSITION_ID");				//一级总成ID
	 					String POSITION_CODE = qs1.getString(1,"POSITION_CODE");			//一级总成CODE
 					
 		    			vo.setPositionId(POSITION_ID);
 	 		    		vo.setPositionCode(POSITION_CODE);
 	 		    		vo.setPositionName(POSITION_NAME);
 		    		}
 		    		if(F_POSITION_NAME != "" && F_POSITION_NAME != null){
 		    			//二级总成
 	 					QuerySet qs2 = dao.getBxCode(F_POSITION_NAME);
 	 					String F_POSITION_ID = qs2.getString(1,"POSITION_ID");				//一级总成ID
 	 					String F_POSITION_CODE = qs2.getString(1,"POSITION_CODE");			//一级总成CODE
 	 					
 		    			vo.setFPositionId(F_POSITION_ID);
 	 		    		vo.setFPositionCode(F_POSITION_CODE);
 	 		    		vo.setFPositionName(F_POSITION_NAME);
 		    		}
 		    		
 		    		
 		    		
 		    		if(PART_NO != "" && PART_NO != null){
 		    			vo.setPartNo(PART_NO);
 		    		}
 		    		if(REBATE_TYPE != "" && REBATE_TYPE != null){
 		    			vo.setRebateType(REBATE_TYPE);
 		    		}
 		    		if(ATTRIBUTE != "" && ATTRIBUTE != null){
 		    			String ATTRIBUTE_CODE = dao.getDicTree(ATTRIBUTE,"配件属性");
 		    			vo.setAttribute(ATTRIBUTE_CODE);
 		    		}
 		    		if(DIRECT_TYPE_NAME != "" && DIRECT_TYPE_NAME != null){
 		    			vo.setIfDirect(DicConstant.SF_01);
 		    			QuerySet qs3 = dao.getZfCode(DIRECT_TYPE_NAME);
 	 					String DIRECT_TYPE_ID = qs3.getString(1,"TYPE_ID");				//直发类型ID
 	 					String DIRECT_TYPE_CODE = qs3.getString(1,"TYPE_CODE");			//直发类型CODE
 	 					
 	 					vo.setDirectTypeId(DIRECT_TYPE_ID);
 	 		    		vo.setDirectTypeCode(DIRECT_TYPE_CODE);
 	 		    		vo.setDirectTypeName(DIRECT_TYPE_NAME);
 		    		}else{
 		    			vo.setIfDirect(DicConstant.SF_02);
 		    		}
 		    		if(F_PART_CODE != "" && F_PART_CODE != null){
 		    			vo.setFPartCode(F_PART_CODE);
 		    		}
 		    		if(F_PART_NAME != "" && F_PART_NAME != null){
 	 		    		vo.setFPartName(F_PART_NAME);
 		    		}
 		    		if(BELONG_ASSEMBLY != "" && BELONG_ASSEMBLY != null){
 		    			String BELONG_ASSEMBLY_CODE = dao.getDicTree(BELONG_ASSEMBLY,"配件总成类别");
 		    			vo.setBelongAssembly(BELONG_ASSEMBLY_CODE);
 		    		}
 		    		if(SPE_NAME != "" && SPE_NAME != null){
 		    			vo.setSpeName(SPE_NAME);
 		    		}
 		    		
 		    		
 		    		
 		    		
 					//验证导入的配件编号在正式表中是否存在
 					QuerySet qss = dao.checkPtBaInfoCode(PART_CODE);
					if(qss.getRowCount() > 0)
					{
						//更新
						vo.setPartId(qss.getString(1, "PART_ID"));
						vo.setUpdateUser(user.getAccount());
						vo.setUpdateTime(Pub.getCurrentDate());
						dao.updateImportPtBaInfoFromTmp(vo);
					} else {
						//新增
						vo.setPartId("");
						vo.setCreateUser(user.getAccount());
						vo.setCreateTime(Pub.getCurrentDate());
						vo.setPartStatus(DicConstant.PJZT_01);
						vo.setSeStatus(DicConstant.YXBS_02);
						vo.setStatus(DicConstant.YXBS_01);
						dao.insertImportPtBaInfoFromTmp(vo);
						
					}
					
// 		    		dao.insertPtBaInfoImport(vo);
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
    
    /**
     * 导出配件档案错误数据按钮：把临时表的错误数据导出到EXCEL
     * @throws Exception
     * Author yhw 2014-11-5
     */
    public void expPtBaInfoTmpErrorData()throws Exception{
    	
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
            hBean.setName("UNIT");
            hBean.setTitle("计量单位");
            header.add(3,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_TYPE");
            hBean.setTitle("配件类型");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IF_ASSEMBLY");
            hBean.setTitle("是否大总成");
            header.add(5,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IF_OUT");
            hBean.setTitle("是否保外");
            header.add(6,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IF_SUPLY");
            hBean.setTitle("是否指定供货商");
            header.add(7,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IF_OIL");
            hBean.setTitle("是否油品");
            header.add(8,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IF_SCAN");
            hBean.setTitle("是否扫码");
            header.add(9,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_STATUS");
            hBean.setTitle("配件状态");
            header.add(10,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("POSITION_NAME");
            hBean.setTitle("一级总成");
            header.add(11,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("F_POSITION_NAME");
            hBean.setTitle("二级总成");
            header.add(12,hBean);
            
            //可选项
            hBean = new HeaderBean();
            hBean.setName("PART_NO");
            hBean.setTitle("参图号");
            header.add(13,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("REBATE_TYPE");
            hBean.setTitle("返利类别");
            header.add(14,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ATTRIBUTE");
            hBean.setTitle("配件属性");
            header.add(15,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("DIRECT_TYPE_NAME");
            hBean.setTitle("直发类型");
            header.add(16,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("F_PART_CODE");
            hBean.setTitle("父级零件号");
            header.add(17,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("F_PART_NAME");
            hBean.setTitle("父级零件名称");
            header.add(18,hBean);
            
                       
            hBean = new HeaderBean();
            hBean.setName("BELONG_ASSEMBLY");
            hBean.setTitle("所属七大总成");
            header.add(19,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SPE_NAME");
            hBean.setTitle("配件特殊分类");
            header.add(20,hBean);

            QuerySet querySet = dao.expPtBaInfoTmpErrorData(conditions,user);
            ExportManager.exportFile(response.getHttpResponse(), "配件档案导入错误信息", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * yhw
     * 导入确定按钮：把配件价格临时表的数据放入到主表
     * ptbapriceTMP表数据需要改造为联合数据
     * @throws Exception
     */
    public void PtBaPriceImport()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
 		try
 		{
 			String errorInfoRowNum = Pub.val(request, "rowNum");
 		    QuerySet qs = dao.searchPtBaPriceTmp(user, errorInfoRowNum);//查询此用户下的所有配件价格临时表信息
 		    if(qs.getRowCount() > 0 ){
 		    	for(int i=0;i<qs.getRowCount();i++){
 		    		//必填
 					String PART_CODE = qs.getString(i+1,"PART_CODE");
 					String PART_NAME = qs.getString(i+1,"PART_NAME");
// 					String pchPrice = qs.getString(i+1,"PCH_PRICE");				//采购
 					
 					String planPrice = qs.getString(i+1,"PLAN_PRICE");				//计划
 					String salePrice = qs.getString(i+1,"SALE_PRICE");				//销售
 					String retailPrice = qs.getString(i+1,"RETAIL_PRICE");			//零售
 					
// 					String SUPPLIER_CODE = qs.getString(i+1,"SUPPLIER_CODE");
// 					String SUPPLIER_NAME = qs.getString(i+1,"SUPPLIER_NAME");
 					
 					 //获得	partId				
 					QuerySet partIdqs = dao.getTableId("PART_ID","PART_CODE","PT_BA_INFO",PART_CODE);
 					String partId = partIdqs.getString(1, "PART_ID");
 					
 					 //获得	supplierId				
// 					QuerySet supplierIdqs = dao.getTableId("SUPPLIER_ID","SUPPLIER_CODE","PT_BA_SUPPLIER",SUPPLIER_CODE);
// 					String supplierId = supplierIdqs.getString(1, "SUPPLIER_ID");
 					
 					
 					
 					
 					
 					//主表VO
 		    		PtBaInfoVO vo = new PtBaInfoVO();
// 		    		PtBaPartSupplierRlVO  rlvo = new PtBaPartSupplierRlVO();
 		    		PtBaPriceLogVO logvo = new PtBaPriceLogVO();
 		    		
 		    		
 		    		//验证导入的配件编号,计划,销售,零售等价格在正式表pt_ba_info中是否存在
 					QuerySet qss = dao.checkPtBaPriceCode(PART_CODE,planPrice,salePrice,retailPrice);
					if(qss.getRowCount() == 0)
					{
						
						//获得原价格
	 					QuerySet priceVarqs = dao.getPrice(PART_CODE);
						String PLAN_PRICE_OLD = priceVarqs.getString(1,"PLAN_PRICE");	    		//计划原价
						String SALE_PRICE_OLD = priceVarqs.getString(1,"SALE_PRICE");				//销售原价
	 					String RETAIL_PRICE_OLD = priceVarqs.getString(1,"RETAIL_PRICE");			//零售原价
	 					
	 					if(!planPrice.equals(PLAN_PRICE_OLD)){
	 						//更新配件档案表计划价格
	 						vo.setPartId(partId);
	 						vo.setPlanPrice(planPrice);
	 						vo.setPriceUser(user.getAccount());
							vo.setPriceTime(Pub.getCurrentDate());
	 						dao.updateImportPtBaPriceFromTmp(vo);
	 						
	 						QuerySet qs2 = dao.queryStock(partId);
//	 		    			dao.updatePlanPrice(tempVO.getPartId(), tempVO.getPlanPrice());
	 		    			//插入计划价格轨迹
	 		    			if(qs2.getRowCount()>0){
	 		    				for(int j=0;i<qs2.getRowCount();j++){
	 		    					PtBaPriceLogVO volog2 = new PtBaPriceLogVO();
	 		    					volog2.setPartId(partId);
	 		    					volog2.setPartCode(PART_CODE);
	 		    					volog2.setPartName(PART_NAME);
	 		    					volog2.setOriginalPrice(PLAN_PRICE_OLD);
	 		    					volog2.setNowPrice(planPrice);
	 		    					volog2.setWarehouseCode(qs2.getString(j+1, "WAREHOUSE_CODE"));
	 		    					volog2.setUsableStock(qs2.getString(j+1, "AMOUNT"));
	 		    					volog2.setInStransStock(qs2.getString(j+1, "OUT_AMOUNT"));
	 		    					volog2.setPriceType(DicConstant.PJJGLX_04);
	 		    					volog2.setCreateUser(user.getPersonName());
	 		    					volog2.setCreateTime(Pub.getCurrentDate());
	 		    					dao.insertPtBaPriceLog(volog2);
	 		    				}
	 		    			}else{
	 		    				//插入计划价格调价日志	
		 						logvo.setLogId("");
		 						logvo.setOriginalPrice(PLAN_PRICE_OLD);
		 						logvo.setNowPrice(planPrice);
		 						logvo.setPriceType(DicConstant.PJJGLX_04);
		 						logvo.setCreateUser(user.getPersonName());
		 						logvo.setCreateTime(Pub.getCurrentDate());
		 						
		 						logvo.setPartId(partId);
		 						logvo.setPartCode(PART_CODE);
		 						logvo.setPartName(PART_NAME);
		 						
//		 						logvo.setSupplierId(supplierId);
//		 						logvo.setSupplierCode(SUPPLIER_CODE);
//		 						logvo.setSupplierName(SUPPLIER_NAME);
		 						
		 						dao.insertPtBaPriceLog(logvo);
	 		    			}
	 					}
	 					if(!salePrice.equals(SALE_PRICE_OLD)){
	 						//更新配件档案表销售价格
	 						vo.setPartId(partId);
	 						vo.setSalePrice(salePrice);
	 						vo.setPriceUser(user.getAccount());
							vo.setPriceTime(Pub.getCurrentDate());
	 						dao.updateImportPtBaPriceFromTmp(vo);
	 						
	 						//插入销售价格调价日志	
	 						logvo.setLogId("");
	 						logvo.setOriginalPrice(SALE_PRICE_OLD);
	 						logvo.setNowPrice(salePrice);
	 						logvo.setPriceType(DicConstant.PJJGLX_02);
	 						logvo.setCreateUser(user.getPersonName());
	 						logvo.setCreateTime(Pub.getCurrentDate());
	 						
	 						logvo.setPartId(partId);
	 						logvo.setPartCode(PART_CODE);
	 						logvo.setPartName(PART_NAME);
	 						
//	 						logvo.setSupplierId(supplierId);
//	 						logvo.setSupplierCode(SUPPLIER_CODE);
//	 						logvo.setSupplierName(SUPPLIER_NAME);
	 						
	 						dao.insertPtBaPriceLog(logvo);
	 						
	 					}
	 					if(!retailPrice.equals(RETAIL_PRICE_OLD)){
	 						//更新配件档案表零售价格
	 						vo.setPartId(partId);
	 						vo.setRetailPrice(retailPrice);
	 						vo.setPriceUser(user.getAccount());
							vo.setPriceTime(Pub.getCurrentDate());
	 						dao.updateImportPtBaPriceFromTmp(vo);
	 						
	 						//插入零售价格调价日志	
	 						logvo.setLogId("");
	 						logvo.setOriginalPrice(RETAIL_PRICE_OLD);
	 						logvo.setNowPrice(retailPrice);
	 						logvo.setPriceType(DicConstant.PJJGLX_01);
	 						logvo.setCreateUser(user.getPersonName());
	 						logvo.setCreateTime(Pub.getCurrentDate());
	 						
	 						logvo.setPartId(partId);
	 						logvo.setPartCode(PART_CODE);
	 						logvo.setPartName(PART_NAME);
	 						
//	 						logvo.setSupplierId(supplierId);
//	 						logvo.setSupplierCode(SUPPLIER_CODE);
//	 						logvo.setSupplierName(SUPPLIER_NAME);
	 						
	 						dao.insertPtBaPriceLog(logvo);
	 						
	 					}
	 					
						
					} 
					
					
//					QuerySet qss2 = dao.checkPtBaPartSupplierRlPriceCode(partId,supplierId,pchPrice);
//					if(qss2.getRowCount() == 0)
//					{
//						String PCH_PRICE_OLD = dao.getPchPrice(partId, supplierId);			//采购原价
//						QuerySet qss3 = dao.getPartSupplierRlId(partId,supplierId);
						//更新配件档案表
//						rlvo.setRelationId(qss3.getString(1, "RELATION_ID"));
//						rlvo.setPchPrice(pchPrice);
						
//						vo.setUpdateUser(user.getAccount());
//						vo.setUpdateTime(Pub.getCurrentDate());
//						dao.updateImportPtBaPartSupplierRlFromTmp(rlvo);
						
						//插入采购价格调价日志	
						
//						logvo.setLogId("");
// 						logvo.setOriginalPrice(PCH_PRICE_OLD);
// 						logvo.setNowPrice(pchPrice);
// 						logvo.setPriceType(DicConstant.PJJGLX_05);
// 						logvo.setUpdateUser(user.getPersonName());
// 						logvo.setUpdateTime(Pub.getCurrentDate());
// 						
// 						logvo.setPartId(partId);
// 						logvo.setPartCode(PART_CODE);
// 						logvo.setPartName(PART_NAME);
// 						
// 						logvo.setSupplierId(supplierId);
// 						logvo.setSupplierCode(SUPPLIER_CODE);
// 						logvo.setSupplierName(SUPPLIER_NAME);
 						
// 						dao.insertPtBaPriceLog(logvo);
//					} 
		
			
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
    /**
     * yhw
     * 导入确定按钮：把配件采购价格临时表的数据放入到主表
     * ptbapriceTMP表数据需要改造为联合数据
     * @throws Exception
     */
    public void PtBaPchPriceImport()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
 		try
 		{
 			String errorInfoRowNum = Pub.val(request, "rowNum");
 		    QuerySet qs = dao.searchPtBaPchPriceTrueTmp(user, errorInfoRowNum);//查询此用户下的所有配件采购价格临时表信息
 		    if(qs.getRowCount() > 0 ){
 		    	for(int i=0;i<qs.getRowCount();i++){
 		    		//必填
 					String PART_CODE = qs.getString(i+1,"PART_CODE");
 					String PART_NAME = qs.getString(i+1,"PART_NAME");
 					String pchPrice = qs.getString(i+1,"PCH_PRICE");				//采购
 					
 					
 					String SUPPLIER_CODE = qs.getString(i+1,"SUPPLIER_CODE");
 					String SUPPLIER_NAME = qs.getString(i+1,"SUPPLIER_NAME");
 					
 					 //获得	partId				
 					QuerySet partIdqs = dao.getTableId("PART_ID","PART_CODE","PT_BA_INFO",PART_CODE);
 					String partId = partIdqs.getString(1, "PART_ID");
 					
 					 //获得	supplierId				
 					QuerySet supplierIdqs = dao.getTableId("SUPPLIER_ID","SUPPLIER_CODE","PT_BA_SUPPLIER",SUPPLIER_CODE);
 					String supplierId = supplierIdqs.getString(1, "SUPPLIER_ID");
 					
 					
 					
 					
 					
 					//主表VO
 		    		PtBaPartSupplierRlVO  rlvo = new PtBaPartSupplierRlVO();
 		    		PtBaPriceLogVO logvo = new PtBaPriceLogVO();
 		    		
 		    		
					
					
					QuerySet qss2 = dao.checkPtBaPartSupplierRlPriceCode(partId,supplierId,pchPrice);
					if(qss2.getRowCount() == 0)
					{
						String PCH_PRICE_OLD = dao.getPchPrice(partId, supplierId);			//采购原价
						QuerySet qss3 = dao.getPartSupplierRlId(partId,supplierId);
						//更新配件档案表
						rlvo.setRelationId(qss3.getString(1, "RELATION_ID"));
						rlvo.setPchPrice(pchPrice);
						rlvo.setPchUser(user.getAccount());
						rlvo.setPchTime(Pub.getCurrentDate());
						
						dao.updateImportPtBaPartSupplierRlFromTmp(rlvo);
						
						//插入采购价格调价日志	
						
						logvo.setLogId("");
 						logvo.setOriginalPrice(PCH_PRICE_OLD);
 						logvo.setNowPrice(pchPrice);
 						logvo.setPriceType(DicConstant.PJJGLX_05);
 						logvo.setCreateUser(user.getPersonName());
 						logvo.setCreateTime(Pub.getCurrentDate());
 						
 						logvo.setPartId(partId);
 						logvo.setPartCode(PART_CODE);
 						logvo.setPartName(PART_NAME);
 						
 						logvo.setSupplierId(supplierId);
 						logvo.setSupplierCode(SUPPLIER_CODE);
 						logvo.setSupplierName(SUPPLIER_NAME);
 						
 						dao.insertPtBaPriceLog(logvo);
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
     * 配件档案信息导出
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
            hBean.setName("UNIT_NAME");
            hBean.setTitle("计量单位");
            header.add(3,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_TYPE_NAME");
            hBean.setTitle("配件类型");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IF_ASSEMBLY_NAME");
            hBean.setTitle("是否大总成");
            header.add(5,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IF_OUT_NAME");
            hBean.setTitle("是否保外");
            header.add(6,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IF_SUPLY_NAME");
            hBean.setTitle("是否指定供货商");
            header.add(7,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IF_OIL_NAME");
            hBean.setTitle("是否油品");
            header.add(8,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IF_SCAN_NAME");
            hBean.setTitle("是否扫码");
            header.add(9,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_STATUS_NAME");
            hBean.setTitle("配件状态");
            header.add(10,hBean);
            
                        
            hBean = new HeaderBean();
            hBean.setName("MIN_PACK");
            hBean.setTitle("最小包装数");
            header.add(11,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("MIN_UNIT_NAME");
            hBean.setTitle("最小包装单位");
            header.add(12,hBean);
            
            
            
                        
            //可选项
            hBean = new HeaderBean();
            hBean.setName("POSITION_NAME");
            hBean.setTitle("一级总成");
            header.add(13,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("F_POSITION_NAME");
            hBean.setTitle("二级总成");
            header.add(14,hBean);
            
            
            hBean = new HeaderBean();
            hBean.setName("PART_NO");
            hBean.setTitle("参图号");
            header.add(15,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("REBATE_TYPE");
            hBean.setTitle("返利类别");
            header.add(16,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ATTRIBUTE_NAME");
            hBean.setTitle("配件属性");
            header.add(17,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("DIRECT_TYPE_NAME");
            hBean.setTitle("直发类型");
            header.add(18,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("F_PART_CODE");
            hBean.setTitle("父级零件号");
            header.add(19,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("F_PART_NAME");
            hBean.setTitle("父级零件名称");
            header.add(20,hBean);
            
                       
            hBean = new HeaderBean();
            hBean.setName("BELONG_ASSEMBLY_NAME");
            hBean.setTitle("所属七大总成");
            header.add(21,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SPE_NAME");
            hBean.setTitle("配件特殊分类");
            header.add(22,hBean);
            
            
            QuerySet querySet = dao.download(conditions);
            ExportManager.exportFile(response.getHttpResponse(), "配件档案明细表", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 服务档案信息导出
     * @throws Exception
     */
    public void downloadService()throws Exception{
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
            hBean.setName("UNIT_NAME");
            hBean.setTitle("计量单位(服务)");
            header.add(3,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_TYPE_NAME");
            hBean.setTitle("配件类型");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IF_OUT_NAME");
            hBean.setTitle("是否保外");
            header.add(5,hBean);
            
            
            hBean = new HeaderBean();
            hBean.setName("PART_STATUS_NAME");
            hBean.setTitle("配件状态");
            header.add(6,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SE_STATUS_NAME");
            hBean.setTitle("服务状态");
            header.add(7,hBean);
            
            
                        
            hBean = new HeaderBean();
            hBean.setName("POSITION_NAME");
            hBean.setTitle("一级总成");
            header.add(8,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("F_POSITION_NAME");
            hBean.setTitle("二级总成");
            header.add(9,hBean);
            
            
            hBean = new HeaderBean();
            hBean.setName("PART_NO");
            hBean.setTitle("参图号");
            header.add(10,hBean);
            
            
            hBean = new HeaderBean();
            hBean.setName("F_PART_CODE");
            hBean.setTitle("父级零件号");
            header.add(11,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("F_PART_NAME");
            hBean.setTitle("父级零件名称");
            header.add(12,hBean);
            
                       
            hBean = new HeaderBean();
            hBean.setName("IF_WORK_HOURS_TIMES_NAME");
            hBean.setTitle("是否工时倍数");
            header.add(13,hBean);
            
            
            QuerySet querySet = dao.downloadService(conditions);
            ExportManager.exportFile(response.getHttpResponse(), "配件档案明细表(服务)", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 计划价格导出
     * @throws Exception
     */
    public void planPriceDownload()throws Exception{

    	// 定义request对象
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
            hBean.setName("PLAN_PRICE");
            hBean.setTitle("计划价格");
            header.add(3,hBean);
            
            QuerySet qs = dao.priceDownload(conditions);
            ExportManager.exportFile(response.getHttpResponse(), "计划价格", header, qs);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 零售价格导出
     * @throws Exception
     */
    public void retailPriceDownload()throws Exception{

    	// 定义request对象
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
            hBean.setName("RETAIL_PRICE");
            hBean.setTitle("零售价格");
            header.add(3,hBean);
            
            QuerySet qs = dao.priceDownload(conditions);
            ExportManager.exportFile(response.getHttpResponse(), "零售价格", header, qs);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 销售价格导出
     * @throws Exception
     */
    public void salePriceDownload()throws Exception{

    	// 定义request对象
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
            hBean.setName("SALE_PRICE");
            hBean.setTitle("销售价格");
            header.add(3,hBean);
            
            QuerySet qs = dao.priceDownload(conditions);
            ExportManager.exportFile(response.getHttpResponse(), "销售价格", header, qs);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 配件价格导出(销售、计划、采购、零售、供应商名称)
     * @throws Exception
     */
    public void downloadPrice()throws Exception{

    	// 定义request对象
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
            hBean.setName("PCH_PRICE");
            hBean.setTitle("采购价格");
            header.add(3,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PLAN_PRICE");
            hBean.setTitle("计划价格");
            header.add(4,hBean);
            
            
            hBean = new HeaderBean();
            hBean.setName("SALE_PRICE");
            hBean.setTitle("销售价格");
            header.add(5,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("RETAIL_PRICE");
            hBean.setTitle("零售价格");
            header.add(6,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_CODE");
            hBean.setTitle("供应商代码");
            header.add(7,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SUPPLIER_NAME");
            hBean.setTitle("供应商名称");
            header.add(8,hBean);
            
            QuerySet querySet = dao.downloadPrice(conditions);
            ExportManager.exportFile(response.getHttpResponse(), "配件价格", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 配件价格导出(销售、计划、零售)
     * @throws Exception
     */
    public void downloadThirdPrice()throws Exception{

    	// 定义request对象
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
            hBean.setName("PLAN_PRICE");
            hBean.setTitle("计划价格");
            header.add(3,hBean);
            
            
            hBean = new HeaderBean();
            hBean.setName("SALE_PRICE");
            hBean.setTitle("销售价格");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("RETAIL_PRICE");
            hBean.setTitle("零售价格");
            header.add(5,hBean);
            
            QuerySet querySet = dao.downloadThirdPrice(conditions);
            ExportManager.exportFile(response.getHttpResponse(), "配件价格", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 配件价格错误信息导出(销售、计划、零售)
     * @throws Exception
     */
    public void expThirdPriceTmpErrorData()throws Exception{

    	// 定义request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response = atx.getResponse();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
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
            hBean.setName("PLAN_PRICE");
            hBean.setTitle("计划价格");
            header.add(3,hBean);
            
            
            hBean = new HeaderBean();
            hBean.setName("SALE_PRICE");
            hBean.setTitle("销售价格");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("RETAIL_PRICE");
            hBean.setTitle("零售价格");
            header.add(5,hBean);
            
            QuerySet querySet = dao.downloadThirdPriceError(conditions,user);
            ExportManager.exportFile(response.getHttpResponse(), "配件价格错误信息", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 配件档案报表信息导出
     * @throws Exception
     */
    public void downloadReport()throws Exception{
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
            hBean.setName("UNIT_NAME");
            hBean.setTitle("计量单位");
            header.add(3,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IF_DIRECT_NAME");
            hBean.setTitle("是否直发");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("IF_OUT_NAME");
            hBean.setTitle("是否保外");
            header.add(5,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_STATUS_NAME");
            hBean.setTitle("配件状态");
            header.add(6,hBean);
            
            
            hBean = new HeaderBean();
            hBean.setName("PLAN_PRICE");
            hBean.setTitle("计划价格");
            header.add(7,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SALE_PRICE");
            hBean.setTitle("销售价格");
            header.add(8,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("RETAIL_PRICE");
            hBean.setTitle("零售价");
            header.add(9,hBean);
            
                        
            hBean = new HeaderBean();
            hBean.setName("IF_OIL_NAME");
            hBean.setTitle("是否油品");
            header.add(10,hBean);
            
            
            hBean = new HeaderBean();
            hBean.setName("IF_OUT_BUY_NAME");
            hBean.setTitle("是否可外购");
            header.add(11,hBean);
            
            
            hBean = new HeaderBean();
            hBean.setName("REBATE_TYPE");
            hBean.setTitle("返利类别");
            header.add(12,hBean);
            
            
            hBean = new HeaderBean();
            hBean.setName("WEIGHT");
            hBean.setTitle("重量（KG）");
            header.add(13,hBean);
            
            
            hBean = new HeaderBean();
            hBean.setName("P_SPECI");
            hBean.setTitle("产品规格");
            header.add(14,hBean);
            
            
            
            QuerySet querySet = dao.downloadReport(conditions);
            ExportManager.exportFile(response.getHttpResponse(), "配件基础报表信息表", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
   
    
    public void expArmyPrice()throws Exception{

    	// 定义request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response = atx.getResponse();
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
        
        try {
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(0,hBean);

            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(1,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ARMY_PRICE");
            hBean.setTitle("军品价格");
            header.add(2,hBean);
            
            QuerySet querySet = dao.downloadThirdPrice(conditions);
            ExportManager.exportFile(response.getHttpResponse(), "配件军品价格", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    
    public void searchArmyPriceImport() throws Exception {

        try {
        	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = dao.searchArmyPriceImport(pageManager, conditions,user);
            baseResultSet.setFieldDic("PART_STATUS", DicConstant.YXBS);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    
    public void expArmyPriceData()throws Exception{

        try {
            // 获取session中的user对象
            User user = (User) atx.getSession().get(Globals.USER_KEY);
        	//获取封装后的request对象
        	RequestWrapper request = atx.getRequest();
            // 定义response对象
            ResponseWrapper responseWrapper= atx.getResponse();
            // 将request流中的信息转化为where条件
            String conditions = Pub.val(request, "seqs");
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(0,hBean);

            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("ARMY_PRICE");
            hBean.setTitle("军品价格");
            header.add(2,hBean);
            
            QuerySet querySet = dao.expArmyPriceData(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "军品价格", header, querySet);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    
    //insertArmyPriceImport
    
    public void insertArmyPriceImport() throws Exception {

        try {
        	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
            String tmpNo = Pub.val(request, "tmpNo");
            QuerySet getArmyPriceTmp = dao.getArmyPriceTmp(tmpNo,user);
            for(int i = 0;i<getArmyPriceTmp.getRowCount();i++){
            	String partId = getArmyPriceTmp.getString(i+1, "PART_ID");
            	String partCode = getArmyPriceTmp.getString(i+1, "PART_CODE");
            	String partName = getArmyPriceTmp.getString(i+1, "PART_NAME");
            	String newPrice = getArmyPriceTmp.getString(i+1, "NEW_PRICE");
            	String oldPrice = getArmyPriceTmp.getString(i+1, "OLD_PRICE");
            	
            	PtBaPriceLogVO volog = new PtBaPriceLogVO();
            	volog.setPartId(partId);
            	volog.setPartCode(partCode);
            	volog.setPartName(partName);
            	volog.setModifyDate(Pub.getCurrentDate());
            	volog.setModifyUser(user.getAccount());
    			volog.setOriginalPrice(oldPrice);
    			volog.setNowPrice(newPrice);
    			volog.setPriceType(DicConstant.PJJGLX_03);
    			volog.setUpdateUser(user.getPersonName());
    			volog.setUpdateTime(Pub.getCurrentDate());
    			volog.setCreateTime(Pub.getCurrentDate());
    			volog.setCreateUser(user.getAccount());
    			dao.insertArmyPriceLog(volog);
    			
    			PtBaInfoVO p_vo = new PtBaInfoVO();
    			p_vo.setPartId(partId);
    			p_vo.setArmyPrice(newPrice);
    			p_vo.setPriceUser(user.getAccount());
    			p_vo.setPriceTime(Pub.getCurrentDate());
    			dao.updatePtBaInfoPrice(p_vo);
    			
            }
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 将配件设置成需回运
     * @throws Exception
     */
    public void needReturn() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	PtBaInfoVO vo = new PtBaInfoVO();
        	HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String partId=hm.get("PART_ID");
            String[] partIdArr = partId.split(",");
            if(partIdArr.length>0){
            	for(int i=0; i<partIdArr.length;i++){
	    			vo.setPartId(partIdArr[i]);
	    			vo.setUpdateUser(user.getAccount());
	    			vo.setUpdateTime(Pub.getCurrentDate());
	    			vo.setIfReturn(DicConstant.SF_01);
	    			dao.updatePartInfo(vo);
	    			atx.setOutMsg(vo.getRowXml(),"配件需回运修改成功。");
	            }
	        }
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 将配件设置成不需回运
     * @throws Exception
     */
    public void noReturn() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		PtBaInfoVO vo = new PtBaInfoVO();
        	HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String partId=hm.get("PART_ID");
            String[] partIdArr = partId.split(",");
            if(partIdArr.length>0){
            	for(int i=0; i<partIdArr.length;i++){
	    			vo.setPartId(partIdArr[i]);
	    			vo.setUpdateUser(user.getAccount());
	    			vo.setUpdateTime(Pub.getCurrentDate());
	    			vo.setIfReturn(DicConstant.SF_02);
	    			dao.updatePartInfo(vo);
	    			atx.setOutMsg(vo.getRowXml(),"配件不需回运修改成功。");
	            }
	        }
        }
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
}

