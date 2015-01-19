package com.org.dms.action.service.oldpartMng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.oldpartMng.OldPartCheckStorageDao;
import com.org.dms.vo.service.SeBuClaimVO;
import com.org.dms.vo.service.SeBuReturnOrderVO;
import com.org.dms.vo.service.SeBuReturnorderDetailVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExcelErrors;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.params.ParaManager;
import com.org.framework.params.UserPara.UserParaConfigureVO;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;
/**
 * 旧件终审ACTION
 * @author 	zts
 *
 */
public class OldPartCheckStorageAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "OldPartCheckStorageAction");
    private ActionContext atx = ActionContext.getContext();
    private OldPartCheckStorageDao dao = OldPartCheckStorageDao.getInstance(atx);
    
    /**
     * 旧件入库查询
     * @throws Exception
     */
    public void oldPartSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			String claimNo = Pub.val(request, "claimNo");
 			String dealerCode = Pub.val(request, "dealerCode");
 			String rOrgCode = Pub.val(request, "rOrgCode");
 			BaseResultSet bs = dao.oldPartSearch(page,conditions,user,claimNo,dealerCode,rOrgCode);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    /**
     * 旧件终审查询
     * @throws Exception
     */
    public void oldPartCheckSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		String claimNo = Pub.val(request, "claimNo");
    		String dealerCode = Pub.val(request, "dealerCode");
    		BaseResultSet bs = dao.oldPartCheckSearch(page,conditions,user,claimNo,dealerCode);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 旧件终审查询（终审查询页面）
     * @throws Exception
     */
    public void oldPartCheckedSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		String claimNo = Pub.val(request, "claimNo");
    		String dealerCode = Pub.val(request, "dealerCode");
    		BaseResultSet bs = dao.oldPartCheckedSearch(page,conditions,user,claimNo,dealerCode);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    /**
     * 旧件导入查询
     * @throws Exception
     */
    public void oldPartImpSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.oldPartImpSearch(page,conditions,user);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    
    /**
     * 旧件入库导入查询
     * @throws Exception
     */
    public void searchImport() throws Exception{
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	try
    	{
    		BaseResultSet bs = dao.searchImport(page,conditions,user);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    
    /**
     * 查询回运清单
     * @throws Exception
     */
    public void returnPartSearch()throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		String orderId=Pub.val(request, "orderId");
 		//page.setPageRows(10000);
 		try
 		{
 			BaseResultSet bs = dao.returnPartSearch(page,orderId,conditions);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    /**
     * 查询回运清单
     * @throws Exception
     */
    public void returnPartSearch2()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	String orderId=Pub.val(request, "orderId");
    	//page.setPageRows(999);
    	try
    	{
    		BaseResultSet bs = dao.returnPartSearch2(page,orderId,conditions);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    
    /**
     * 查询回运清单
     * @throws Exception
     */
    public void returnPartSearch3()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request,page);
    	String orderId=Pub.val(request, "orderId");
    	page.setPageRows(999);
    	try
    	{
    		BaseResultSet bs = dao.returnPartSearch3(page,orderId,conditions);
    		atx.setOutData("bs", bs);
    	}
    	catch (Exception e)
    	{
    		logger.error(e);
    		atx.setException(e);
    	}
    }
    
    /**
     * @return 
     * @title: checkOldPartReturn
     * @description: 
     * @throws Exception    设定文件
     * @return void    返回类型
     * @auther sunxuedong
     */
	public List<ExcelErrors> checkOldPartReturn() throws Exception
    {
        User user = (User) atx.getSession().get(Globals.USER_KEY);
      	ExcelErrors errors = null;
    	List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		try
		{
			QuerySet qs = dao.queryImp(user);
			for(int i=0;i<qs.getRowCount();i++){
				String returnOrderNo =qs.getString(i+1, "RETURN_ORDER_NO");
				String claimNo = qs.getString(i+1, "CLAIM_NO");
				String faultCode=qs.getString(i+1, "FAULT_CODE");
				String partCode=qs.getString(i+1, "PART_CODE");
				String rowNo =qs.getString(i+1, "ROW_NO");
				String supplierCode=qs.getString(i+1, "SUPPLIER_CODE");
				String oldPartStatus=qs.getString(i+1, "OLD_PART_STATUS");
				String amount=qs.getString(i+1, "AMOUNT");//Excel中填写的数量
				QuerySet qids=dao.queryid(claimNo, faultCode, partCode, supplierCode, returnOrderNo);
				String oughtCount=qids.getString(1, "OUGHT_COUNT");
				String actulCount=qids.getString(1, "ACTUL_COUNT");
				String missCount=qids.getString(1, "MISS_COUNT");
				String ropStatus=qids.getString(1, "OLD_PART_STATUS");//旧件原状态
				if(oldPartStatus.equals("待审")){
					if(Integer.parseInt(amount)+Integer.parseInt(actulCount)>Integer.parseInt(oughtCount)){
						errors=new ExcelErrors();
						if(null==rowNo||rowNo.equals("")){
						}else{
							errors.setRowNum(Integer.parseInt(rowNo));
						}
						errors.setErrorDesc("填写的数量大于实际返回旧件数量。");
						errorList.add(errors);
					}
				}else if(oldPartStatus.equals("正常")){
					if(ropStatus.equals(DicConstant.JJZT_03)){
						if(Integer.parseInt(amount)>Integer.parseInt(actulCount)){
							errors=new ExcelErrors();
							if(null==rowNo||rowNo.equals("")){
							}else{
								errors.setRowNum(Integer.parseInt(rowNo));
							}
							errors.setErrorDesc("填写的数量大于待审数量。");
							errorList.add(errors);
						}
					}else if (ropStatus.equals(DicConstant.JJZT_04)){
						if(Integer.parseInt(amount)>Integer.parseInt(missCount)){
							errors=new ExcelErrors();
							if(null==rowNo||rowNo.equals("")){
							}else{
								errors.setRowNum(Integer.parseInt(rowNo));
							}
							errors.setErrorDesc("填写的数量大于缺失数量。");
							errorList.add(errors);
						}
					}
				}
			}
			QuerySet qs1=dao.queryReturnNo(user);//回运单号是否存在
			if(qs1.getRowCount()>0){
				for(int i=0;i<qs1.getRowCount();i++){
					errors=new ExcelErrors();
					String returnOrderNo =qs1.getString(i+1, "RETURN_ORDER_NO");
					String rowNo =qs1.getString(i+1, "ROW_NO");
					if(null==rowNo||rowNo.equals("")){
					}else{
						errors.setRowNum(Integer.parseInt(rowNo));
					}
					errors.setErrorDesc("回运单号："+returnOrderNo+"不存在！");
					errorList.add(errors);
				}
			}
			QuerySet qs2 = dao.queryClaimNo(user);//判断索赔单号在回运单中是否存在
			if(qs2.getRowCount()>0){
				for(int j=0;j<qs2.getRowCount();j++){
					errors=new ExcelErrors();
					String rowNo =qs2.getString(j+1, "ROW_NO");
					String claimNo = qs2.getString(j+1, "CLAIM_NO");
					String returnOrderNo =qs2.getString(j+1, "RETURN_ORDER_NO");
					if(null==rowNo||rowNo.equals("")){
					}else{
						errors.setRowNum(Integer.parseInt(rowNo));
					}
					errors.setErrorDesc("索赔单号："+claimNo+"在回运单："+returnOrderNo+"中不存在！");
					errorList.add(errors);
				}
			}
			QuerySet qs3 = dao.queryNos(user);//判断索赔单号、回运单号、配件代码、供应商、故障是否一致
			if(qs3.getRowCount()>0){
				for(int k=0;k<qs3.getRowCount();k++){
					errors=new ExcelErrors();
					String rowNo =qs3.getString(k+1, "ROW_NO");
					String claimNo = qs3.getString(k+1, "CLAIM_NO");
					String partCode=qs3.getString(k+1, "PART_CODE");
					String returnOrderNo =qs3.getString(k+1, "RETURN_ORDER_NO");
					String faultCode=qs3.getString(k+1, "FAULT_CODE");
					String supplierCode=qs3.getString(k+1, "SUPPLIER_CODE");
					if(null==rowNo||rowNo.equals("")){
					}else{
						errors.setRowNum(Integer.parseInt(rowNo));
					}
					errors.setErrorDesc("索赔单号："+claimNo+",回运单号："+returnOrderNo+",旧件代码："+partCode+",供应商代码："+supplierCode+",故障代码："+faultCode+"数据不一致！");
					errorList.add(errors);
				}
			}
			QuerySet qs4 = dao.queryStatus(user);//判断状态
			if(qs4.getRowCount()>0){
				for(int l=0;l<qs4.getRowCount();l++){
					errors=new ExcelErrors();
					String rowNo =qs4.getString(l+1, "ROW_NO");
					String claimNo = qs4.getString(l+1, "CLAIM_NO");
					String partCode=qs4.getString(l+1, "PART_CODE");
					if(null==rowNo||rowNo.equals("")){
					}else{
						errors.setRowNum(Integer.parseInt(rowNo));
					}
					errors.setErrorDesc("索赔单号："+claimNo+"中的旧件代码为："+partCode+"已审核通过，或作废。不可再次更改状态！");
					errorList.add(errors);
				}
			}
			QuerySet qs5=dao.checkList3(user);//判断导入数据是否重复
			if(qs5.getRowCount()>0){
				for(int h=0;h<qs5.getRowCount();h++){
					errors=new ExcelErrors();
					String cou=qs5.getString(h+1,"COU");
					String claimNo = qs5.getString(h+1, "CLAIM_NO");
					String partCode=qs5.getString(h+1, "PART_CODE");
					String returnOrderNo =qs5.getString(h+1, "RETURN_ORDER_NO");
					String faultCode=qs5.getString(h+1, "FAULT_CODE");
					String supplierCode=qs5.getString(h+1, "SUPPLIER_CODE");
					errors.setErrorDesc("索赔单号："+claimNo+",回运单号："+returnOrderNo+",旧件代码："+partCode+",供应商代码："+supplierCode+",故障代码："+faultCode+"在导入模板中已重复填写"+cou+"次！");
					errorList.add(errors);
				}
			}
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
			if(errorList!=null&&errorList.size()>0){
				return errorList;
			}else{
				return null;
			}
		}
	/**
     * 修改旧件状态
     * @throws Exception
     */
    public void oldPartImport()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	RequestWrapper request = atx.getRequest();
 		try
 		{
 			String tmpNo = Pub.val(request, "tmpNo");
        	String rowNo = "";
        	if ("".equals(tmpNo)==false) {
        		rowNo = " AND T.ROW_NO NOT IN ("+tmpNo+") ";
        	}
 			QuerySet qs=dao.checkList(user,rowNo);
			if(qs.getRowCount()>0){
				for(int i=0;i<qs.getRowCount();i++){
					String returnOrderNo =qs.getString(i+1, "RETURN_ORDER_NO");
					String claimNo = qs.getString(i+1, "CLAIM_NO");
					String faultCode=qs.getString(i+1, "FAULT_CODE");
					String partCode=qs.getString(i+1, "PART_CODE");
					String supplierCode=qs.getString(i+1, "SUPPLIER_CODE");
					String amount=qs.getString(i+1, "AMOUNT");//Excel中填写的数量
					String oldPartStatus=qs.getString(i+1, "OLD_PART_STATUS");
					String remarks=qs.getString(i+1, "REMARKS1");
					String mbStatus=null;
					QuerySet qs1=dao.queryid(claimNo,faultCode,partCode,supplierCode,returnOrderNo);
					String dtlId=qs1.getString(1, "DETAIL_ID");//通过Excel中填写的数据项，确定回运单明细表的主键ID
					//String claimId=qs1.getString(1, "CLAIM_ID");
					//SeBuClaimVO vo1=new SeBuClaimVO();
					SeBuReturnorderDetailVO vo =new SeBuReturnorderDetailVO();
					//vo1.setClaimId(claimId);
					//vo1.setFaultCode(faultCode);
					vo.setDetailId(dtlId);
					QuerySet qs2 = dao.queryAmount(dtlId);//查询回运单明细表中所需数据。
					String jjzt=qs2.getString(1, "OLD_PART_STATUS");
					if(oldPartStatus.equals("待审")){//Excel中填写的旧件状态。
						oldPartStatus=DicConstant.JJZT_03;
						  mbStatus =DicConstant.JJZT_03;
					}else if(oldPartStatus.equals("正常")){//所有旧件都已更新为正常，才可以更新状态。
						  mbStatus =DicConstant.JJZT_01;
						if(jjzt.equals(DicConstant.JJZT_03)){//如果旧件状态为待审
							if(0==Integer.parseInt(qs2.getString(1, "ACTUL_COUNT"))-Integer.parseInt(amount)){
								oldPartStatus=DicConstant.JJZT_01;
							}else{
								oldPartStatus=jjzt;
							}
						}else if(jjzt.equals(DicConstant.JJZT_04)){
							if(0==Integer.parseInt(qs2.getString(1, "MISS_COUNT"))-Integer.parseInt(amount)){
								oldPartStatus=DicConstant.JJZT_01;
							}else{
								oldPartStatus=jjzt;
							}
						}else{
							oldPartStatus=jjzt;
						}
					}else if(oldPartStatus.equals("缺失")){//Excel中填写的旧件状态。
						oldPartStatus=DicConstant.JJZT_04;
						mbStatus= DicConstant.JJZT_04;
					}
					
					if(mbStatus.equals(DicConstant.JJZT_03))//如果旧件的目标修改状态为待审
					{
						int sl =Integer.parseInt(amount)+Integer.parseInt(qs2.getString(1, "ACTUL_COUNT"));
						vo.setActulCount(String.valueOf(sl));//待审数量
					}
					if(mbStatus.equals(DicConstant.JJZT_01))//如果旧件的目标修改状态为保存
					{
						if(jjzt.equals(DicConstant.JJZT_04))//如果旧件本身状态为缺失
						{
							int zcsl = Integer.parseInt(qs2.getString(1, "OUGHT_COUNT"))+Integer.parseInt(amount);//将Excel中填写的的数量加回到实返数量中
							int qssl = Integer.parseInt(qs2.getString(1, "MISS_COUNT"))-Integer.parseInt(amount);//从缺失数量中减掉Excel中填写的的数量
							vo.setOughtCount(String.valueOf(zcsl));
							vo.setMissCount(String.valueOf(qssl));
						}else if(jjzt.equals(DicConstant.JJZT_03))//如果旧件本身状态为待审
						{
							int dssl = Integer.parseInt(qs2.getString(1, "ACTUL_COUNT"))-Integer.parseInt(amount);//从待审数量中减掉Excel中填写的的数量
							vo.setActulCount(String.valueOf(dssl));//待审数量
						}
					}
					if(mbStatus.equals(DicConstant.JJZT_04)){
						int qssl=Integer.parseInt(amount)+Integer.parseInt(qs2.getString(1, "MISS_COUNT"));
						int yrksl=Integer.parseInt(qs2.getString(1, "ALREADY_IN"))-Integer.parseInt(amount);
						vo.setMissCount(String.valueOf(qssl));//缺失数量
						vo.setAlreadyIn(String.valueOf(yrksl));//已入库数量
					}
					vo.setRemarks(remarks);
					vo.setOldPartStatus(oldPartStatus);
					vo.setCheckDate(Pub.getCurrentDate());
					vo.setCheckUser(user.getAccount());
					dao.updateDetail(vo);
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
     * 全部审核通过
     * @throws Exception
     */
    public void returnOldPartUpdate()throws Exception{
       // RequestWrapper request = atx.getRequest();
        String orderId="";
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
        	QuerySet qsOrder=dao.getOrder();
        	if(qsOrder.getRowCount() > 0){
        		for(int n=0;n<qsOrder.getRowCount();n++){
        			orderId=qsOrder.getString(n+1, "ORDER_ID");
        			QuerySet qsCid=dao.queryCid(orderId);
        			if(qsCid.getRowCount()>0){
        				for(int j=0;j<qsCid.getRowCount();j++){
        					 String cId=qsCid.getString(j+1, "CLAIM_ID");
        					 dao.updateOught1(cId);//更新维修和加装的审核数量
        					 dao.updateOught(cId);//更新不需要回运旧件的审核通过数量
        				}
        			}
        			QuerySet qs =dao.queryClaims(orderId);//查询所有旧件状态为已保存的数据
            		if(qs.getRowCount()>0){
            			for(int i=0;i<qs.getRowCount();i++){
        	        		String detailId = qs.getString(i+1, "DETAIL_ID");//回运旧件明细ID
        	        		String partId = qs.getString(i+1, "PART_ID");//配件ID
        	        		String cId = qs.getString(i+1, "CLAIM_ID");//索赔单ID
        	        		String oughtCount = qs.getString(i+1, "OUGHT_COUNT");//实际通过数量
        	        		String prosupplyId = qs.getString(i+1, "PROSUPPLY_ID");//生产供应商ID
        	        		String claimNo = qs.getString(i+1, "CLAIM_NO");//索赔单号
        	        		String claimDtlId = qs.getString(i+1, "CLAIM_DTL_ID");//索赔单故障明细主键
        	        		dao.updateFaultPartCount(partId,cId,prosupplyId,claimDtlId,oughtCount);
        	        		
        	            	SeBuReturnorderDetailVO detailVo=new SeBuReturnorderDetailVO();
        	            	detailVo.setDetailId(detailId);
        	            	detailVo.setCheckUser(user.getAccount());
        	    			detailVo.setCheckDate(Pub.getCurrentDate());
        	            	detailVo.setOldPartStatus(DicConstant.JJZT_02);
        	            	dao.updateDetail(detailVo);
        	            	QuerySet qs1=dao.queryParts(orderId,claimNo);//查询旧件对应索赔单对应ID
        	            	QuerySet qs2=dao.queryClaimId(claimNo);//获得索赔单ID
        	            	
        	            	String claimId =qs2.getString(1, "CLAIM_ID");
        		            	if(qs1.getRowCount()>0){
        		            	}else{
        		            		SeBuClaimVO vo = new SeBuClaimVO();
        		            		vo.setClaimId(claimId);
        		            		vo.setOldpartFinalDate(Pub.getCurrentDate());
        		            		vo.setFinalUser(user.getAccount());
        		            		vo.setClaimStatus(DicConstant.SPDZT_15);
        		            		dao.updateCheckDate(vo);
        		            		atx.setOutMsg("", "审核通过成功！");
        		            	}
            			  }
            			QuerySet qs3 =dao.sumClaims(orderId);
            			if(qs3.getRowCount()>0){
            			}else{
            				SeBuReturnOrderVO vo=new SeBuReturnOrderVO();
            				vo.setOrderId(orderId);
            				vo.setOrderStatus(DicConstant.HYDZT_06);
            				dao.updateStatus(vo);
            				atx.setOutMsg("", "终审完成！");
            			}
            		}
        		}
        	}else {
        		atx.setOutMsg("1", "");
			}
        	
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 旧件入库
     * @throws Exception
     */
    public void returnOldPartStorageIn()throws Exception{
        RequestWrapper request = atx.getRequest();
    	UserParaConfigureVO userVo1= (UserParaConfigureVO) ParaManager.getInstance().getUserParameter("201101");
    	String flagSf=userVo1.getParavalue1();//旧件入库类型，1是用扫描枪入库，否则不用扫描枪
        int instoreAmount=0;
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
	        	HashMap<String, String> hm;
	            //将request流转换为hashmap结构体
	            hm = RequestUtil.getValues(request);
	            String DETAIL_IDS=hm.get("DETAIL_IDS");
				String INSTOR_AMOUNTS=hm.get("INSTOR_AMOUNTS");
	            String[] detailIdArr = DETAIL_IDS.split(",");
	            String[] instorAmountArr = INSTOR_AMOUNTS.split(",");
	            
	            for(int i=0;i<detailIdArr.length;i++){
	            	QuerySet qs =dao.getOldPartDetail(detailIdArr[i]);
            		String sl=qs.getString(1, "ALREADY_IN");//已入库数量
            		String claimId=qs.getString(1, "CLAIM_ID");//索赔单ID
	            	if(!"1".equals(flagSf)){
	            		SeBuReturnorderDetailVO detailVo1=new SeBuReturnorderDetailVO();
	            		int sfsl=Integer.parseInt(qs.getString(1, "OUGHT_COUNT"));//集中点实际返回数量
	            		instoreAmount=Integer.parseInt(instorAmountArr[i]);//本次入库数量
	            		instoreAmount += Integer.parseInt(sl);//更新已入库数量
	            		detailVo1.setDetailId(detailIdArr[i]);
            			detailVo1.setStorageStatus(DicConstant.HYDZT_05);//将旧件状态设置为已回运
	            		detailVo1.setAlreadyIn(String.valueOf(instoreAmount));//将更新的已入库数量插入到数据库
	            		String rksl=instorAmountArr[i];
	            		//旧件库存表
	            		dao.insertReturnStore(detailIdArr[i],rksl,user);
	                	//更新旧件回运明细表
	                	dao.updateDetail(detailVo1);
	            	}
	            	QuerySet qs1=dao.checklist2(claimId);
                	SeBuClaimVO vo1=new SeBuClaimVO();
                	if(qs1.getRowCount()>0){
                	}else{
                		//如果索赔单中所有旧件已经入库（刨除掉缺失）
                		vo1.setClaimId(claimId);
                		vo1.setIfStorage(DicConstant.HYDZT_05);
                		dao.updateCheckDate(vo1);
                	}
	            }
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 旧件入库(导入)
     * @throws Exception
     */
    public void impStorageIn()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	int instoreAmount=0;
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try {
			String tmpNo = Pub.val(request, "rowStrNo");
			if(tmpNo.length()>1000){
				atx.setOutMsg("", "导入数据中存在过多错误信息，请仔细核对后重新导入。");
			}else{
			String rowNo = "";
	    	if ("".equals(tmpNo)==false) {
	    		rowNo = " AND T.ROW_NO NOT IN ("+tmpNo+") ";
	    	}
	    	QuerySet qs2 =dao.queryCount(user,rowNo);
    		if(qs2.getRowCount()>0){
	    		for(int i=0;i<qs2.getRowCount();i++){
					SeBuReturnorderDetailVO detailVo1=new SeBuReturnorderDetailVO();
					String dtlId=qs2.getString(i+1, "DETAIL_ID");
					String storageCount=qs2.getString(i+1, "STORAGE_COUNT");
					QuerySet qs =dao.getOldPartDetail(dtlId);
					String yrksl=qs.getString(1, "ALREADY_IN");//已入库数量
					String claimId=qs.getString(1, "CLAIM_ID");//索赔单ID
					int sfsl=Integer.parseInt(qs.getString(1, "OUGHT_COUNT"));//集中点实际返回数量
					instoreAmount=Integer.parseInt(storageCount);//本次入库数量
					instoreAmount += Integer.parseInt(yrksl);//更新已入库数量
					detailVo1.setDetailId(dtlId);
					detailVo1.setStorageStatus(DicConstant.HYDZT_05);//将旧件状态设置为已回运
					detailVo1.setAlreadyIn(String.valueOf(instoreAmount));//将更新的已入库数量插入到数据库
					String rksl=storageCount;
					//旧件库存表
					dao.insertReturnStore(dtlId,rksl,user);
					//更新旧件回运明细表
					dao.updateDetail(detailVo1);
					QuerySet qs1=dao.checklist2(claimId);
					SeBuClaimVO vo1=new SeBuClaimVO();
					if(qs1.getRowCount()>0){
					}else{
						//如果索赔单中所有旧件已经入库（刨除掉缺失）
						vo1.setClaimId(claimId);
						vo1.setIfStorage(DicConstant.HYDZT_05);
						dao.updateCheckDate(vo1);
						}
	    			}
    			}
    		}
    	} catch (Exception e) {
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 旧件审核作废
     * @throws Exception
     */
    public void returnOldPartCancel()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	String orderId=Pub.val(request, "orderId");
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try {
    		HashMap<String, String> hm;
    		//将request流转换为hashmap结构体
    		hm = RequestUtil.getValues(request);
    		String detailIds = hm.get("DETAIL_IDS");//明细ID
    		String[] detailIdArr = detailIds.split(",");
    		for (int i = 0; i < detailIdArr.length; i++) {
    			SeBuReturnorderDetailVO detailVo=new SeBuReturnorderDetailVO();
    			QuerySet qs=dao.getOldPartDetail(detailIdArr[i]);
    			int oughtCount=0;
    			int actulCount=0;
    			int missCount=0;
    			String claimNo=null;
    			if(qs.getRowCount()>0){
    				oughtCount=Integer.parseInt(qs.getString(1, "OUGHT_COUNT"));//实返旧件数量
    				actulCount=Integer.parseInt(qs.getString(1, "ACTUL_COUNT"));//待审旧件数量
    				missCount=Integer.parseInt(qs.getString(1, "MISS_COUNT"));//缺失旧件数量
    				claimNo = qs.getString(1, "CLAIM_NO");
    			
    			oughtCount=oughtCount-actulCount-missCount;//实际通过数量 = 实返数量-待审数量-缺失数量; 
    			String sl=String.valueOf(oughtCount);
    			detailVo.setDetailId(detailIdArr[i]);
    			detailVo.setCheckUser(user.getAccount());
    			detailVo.setCheckDate(Pub.getCurrentDate());
    			detailVo.setOughtCount(sl);
    			detailVo.setOldPartStatus(DicConstant.JJZT_02);
    			//更新旧件回运明细表
    			dao.updateDetail(detailVo);
    			
    			QuerySet qs1=dao.queryParts(orderId,claimNo);
            	QuerySet qs2=dao.queryClaimId(claimNo);
            	String claimId =qs2.getString(1, "CLAIM_ID");
	            	if(qs1.getRowCount()>0){
	            	}else{
	            		SeBuClaimVO vo = new SeBuClaimVO();
	            		vo.setClaimId(claimId);
	            		vo.setOldpartFinalDate(Pub.getCurrentDate());
	            		vo.setFinalUser(user.getAccount());
	            		vo.setClaimStatus(DicConstant.SPDZT_15);
	            		dao.updateCheckDate(vo);
	            		atx.setOutMsg("", "索赔单中旧件审核完成！");
	            	}
    			}
    		}
    		QuerySet qs2 =dao.sumClaims(orderId);
			if(qs2.getRowCount()>0){
			}else{
				SeBuReturnOrderVO vo=new SeBuReturnOrderVO();
				vo.setOrderId(orderId);
				vo.setOrderStatus(DicConstant.HYDZT_06);
				dao.updateStatus(vo);
				atx.setOutMsg("1", "终审完成！");
			}
    	} catch (Exception e) {
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     * 旧件审核通过
     * @throws Exception
     */
    public void returnOldPartPass()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	String orderId=Pub.val(request, "orderId");
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try {
    		HashMap<String, String> hm;
    		//将request流转换为hashmap结构体
    		hm = RequestUtil.getValues(request);
    		String detailIds = hm.get("DETAIL_IDS");//明细ID
    		String[] detailIdArr = detailIds.split(",");
    		for (int i = 0; i < detailIdArr.length; i++) {
    			SeBuReturnorderDetailVO detailVo=new SeBuReturnorderDetailVO();
    			QuerySet qs=dao.getOldPartDetail(detailIdArr[i]);
    			int shouldCount=0;
    			String claimNo=null;
    			if(qs.getRowCount()>0){
    				shouldCount=Integer.parseInt(qs.getString(1, "SHOULD_COUNT"));
    				claimNo = qs.getString(1, "CLAIM_NO");
    			String sl=String.valueOf(shouldCount);
    			detailVo.setDetailId(detailIdArr[i]);
    			detailVo.setCheckUser(user.getAccount());
    			detailVo.setCheckDate(Pub.getCurrentDate());
    			detailVo.setOughtCount(sl);
    			detailVo.setActulCount("0");
    			detailVo.setMissCount("0");
    			detailVo.setOldPartStatus(DicConstant.JJZT_02);
    			//更新旧件回运明细表
    			dao.updateDetail(detailVo);
    			
    			QuerySet qs1=dao.queryParts(orderId,claimNo);
    			QuerySet qs2=dao.queryClaimId(claimNo);
    			String claimId =qs2.getString(1, "CLAIM_ID");
    			if(qs1.getRowCount()>0){
    			}else{
    				SeBuClaimVO vo = new SeBuClaimVO();
    				vo.setClaimId(claimId);
    				vo.setOldpartFinalDate(Pub.getCurrentDate());
    				vo.setFinalUser(user.getAccount());
    				vo.setClaimStatus(DicConstant.SPDZT_15);
    				dao.updateCheckDate(vo);
    				atx.setOutMsg("", "索赔单中旧件审核完成！");
    			}
    		}
    		QuerySet qs2 =dao.sumClaims(orderId);
    		if(qs2.getRowCount()>0){
    		}else{
    			SeBuReturnOrderVO vo=new SeBuReturnOrderVO();
    			vo.setOrderId(orderId);
    			vo.setOrderStatus(DicConstant.HYDZT_06);
    			dao.updateStatus(vo);
    			atx.setOutMsg("1", "终审完成！");
    			}
    		}
    	} catch (Exception e) {
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    /**
     *  带有数据模版下载
     * @throws Exception
     */
	public void download()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
 		String conditions = RequestUtil.getConditionsWhere(request,page);
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
	    	hBean = new HeaderBean();
    		hBean.setName("ORDER_NO");
    		hBean.setTitle("回运单号");
    		header.add(0,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("CLAIM_NO");
    		hBean.setTitle("索赔单号");
    		header.add(1,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FAULT_CODE");
    		hBean.setTitle("故障代码");
    		header.add(2,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PART_CODE");
    		hBean.setTitle("配件代码");
    		header.add(3,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PART_NAME");
    		hBean.setTitle("配件名称");
    		header.add(4,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PROSUPPLY_CODE");
    		hBean.setTitle("供应商代码");
    		header.add(5,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("PROSUPPLY_NAME");
    		hBean.setTitle("供应商名称");
    		header.add(6,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("CODE");
    		hBean.setTitle("渠道商代码");
    		header.add(7,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("NAME");
    		hBean.setTitle("渠道商名称");
    		header.add(8,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SHOULD_COUNT");
    		hBean.setTitle("应返旧件数");
    		header.add(9,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("OUGHT_COUNT");
    		hBean.setTitle("实返旧件数");
    		header.add(10,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("MISS_COUNT");
    		hBean.setTitle("缺失旧件数");
    		header.add(11,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("ALREADY_IN");
    		hBean.setTitle("已入库旧件数");
    		header.add(12,hBean);
    		QuerySet qs = dao.download(conditions,user);
    		ExportManager.exportFile(response.getHttpResponse(), "供应商审件数据", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
	/**
	 *  入库导出数据方法（入库导入时使用。）
	 * @throws Exception
	 */
	public void storageInDownLoad()throws Exception{
		//获取封装后的request对象
		RequestWrapper request = atx.getRequest();
		ResponseWrapper response= atx.getResponse();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String conditions = RequestUtil.getConditionsWhere(request,page);
		String claimNo = Pub.val(request, "claimNo");
		String dealerCode = Pub.val(request, "dealerCode");
		try
		{
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();
			hBean.setName("ORDER_NO");
			hBean.setTitle("回运单号");
			header.add(0,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("CLAIM_NO");
			hBean.setTitle("索赔单号");
			header.add(1,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("FAULT_CODE");
			hBean.setTitle("故障代码");
			header.add(2,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("PART_CODE");
			hBean.setTitle("配件代码");
			header.add(3,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("PART_NAME");
			hBean.setTitle("配件名称");
			header.add(4,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("PROSUPPLY_CODE");
			hBean.setTitle("供应商代码");
			header.add(5,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("PROSUPPLY_NAME");
			hBean.setTitle("供应商名称");
			header.add(6,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("SHOULD_COUNT");
			hBean.setTitle("索赔旧件总数");
			header.add(7,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("OUGHT_COUNT");
			hBean.setTitle("集中点实返旧件数量");
			header.add(8,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("MISS_COUNT");
			hBean.setTitle("集中点缺失旧件数量");
			header.add(9,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("BCRKS");
			hBean.setTitle("本次入库数量");
			header.add(10,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("ALREADY_IN");
			hBean.setTitle("已入库旧件数量");
			header.add(11,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("CODE");
			hBean.setTitle("渠道商代码");
			header.add(12,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("NAME");
			hBean.setTitle("渠道商名称");
			header.add(13,hBean);
			
			QuerySet qs = dao.storageInDownLoad(user,conditions,claimNo,dealerCode);
			ExportManager.exportFile(response.getHttpResponse(), "旧件入库数据", header, qs);
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
	
	/**
     *  带有数据模版下载
     * @throws Exception
     */
	public void expData()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions=Pub.val(request, "seqs");
        try
        {
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
			hBean = new HeaderBean();
			hBean.setName("RETURN_ORDER_NO");
			hBean.setTitle("回运单号");
			header.add(0,hBean);
			hBean = new HeaderBean();
			hBean.setName("CLAIM_NO");
			hBean.setTitle("索赔单号");
			header.add(1,hBean);
			hBean = new HeaderBean();
			hBean.setName("FAULT_CODE");
			hBean.setTitle("故障代码");
			header.add(2,hBean);
			hBean = new HeaderBean();
			hBean.setName("PART_CODE");
			hBean.setTitle("配件代码");
			header.add(3,hBean);
			hBean = new HeaderBean();
			hBean.setName("PART_NAME");
			hBean.setTitle("配件名称");
			header.add(4,hBean);
			hBean = new HeaderBean();
			hBean.setName("SUPPLIER_CODE");
			hBean.setTitle("供应商代码");
			header.add(5,hBean);
			hBean = new HeaderBean();
			hBean.setName("SUPPLIER_NAME");
			hBean.setTitle("供应商名称");
			header.add(6,hBean);
			hBean = new HeaderBean();
			hBean.setName("AMOUNT");
			hBean.setTitle("数量");
			header.add(7,hBean);
			hBean = new HeaderBean();
			hBean.setName("REMARKS1");
			hBean.setTitle("备注");
			header.add(8,hBean);
			hBean = new HeaderBean();
			hBean.setName("OLD_PART_STATUS");
			hBean.setTitle("旧件状态");
			header.add(9,hBean);
			QuerySet qs = dao.expData(user,conditions);
    		ExportManager.exportFile(response.getHttpResponse(), "WorngOldPartInfo", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
	
	/**
	 *  错误数据导出(入库)
	 * @throws Exception
	 */
	public void expData1()throws Exception{
		//获取封装后的request对象
		RequestWrapper request = atx.getRequest();
		ResponseWrapper response= atx.getResponse();
		PageManager page = new PageManager();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String tmpNo = Pub.val(request, "seqs");
		String rowNo = "";
    	if ("".equals(tmpNo)==false) {
    		rowNo = " ROW_NO IN ("+tmpNo+") ";
    	}
		try
		{
			List<HeaderBean> header = new ArrayList<HeaderBean>();
			HeaderBean hBean = null;
			hBean = new HeaderBean();
			hBean.setName("RETURN_ORDER_NO");
			hBean.setTitle("回运单号");
			header.add(0,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("CLAIM_NO");
			hBean.setTitle("索赔单号");
			header.add(1,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("FAULT_CODE");
			hBean.setTitle("故障代码");
			header.add(2,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("PART_CODE");
			hBean.setTitle("配件代码");
			header.add(3,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("PART_NAME");
			hBean.setTitle("配件名称");
			header.add(4,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("SUPPLIER_CODE");
			hBean.setTitle("供应商代码");
			header.add(5,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("SUPPLIER_NAME");
			hBean.setTitle("供应商名称");
			header.add(6,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("CLAIM_COUNT");
			hBean.setTitle("应返旧件数量");
			header.add(7,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("OUGHT_COUNT");
			hBean.setTitle("集中点实返旧件数量");
			header.add(8,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("MISS_COUNT");
			hBean.setTitle("缺失旧件数量");
			header.add(9,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("STORAGE_COUNT");
			hBean.setTitle("本次入库数量");
			header.add(10,hBean);
			
			hBean = new HeaderBean();
			hBean.setName("ALREADY_IN");
			hBean.setTitle("已入库旧件数量");
			header.add(11,hBean);
			
			QuerySet qs = dao.expData1(page,user,rowNo);
			ExportManager.exportFile(response.getHttpResponse(), "入库导入(错误数据导出)", header, qs);
		}
		catch (Exception e)
		{
			atx.setException(e);
			logger.error(e);
		}
	}
	
    /**
     * 审批入库
     * @throws Exception
     */
    /* public void returnOldPartUpdate()throws Exception{
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String orderId=Pub.val(request, "orderId");
        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String detailIds = hm.get("DETAIL_IDS");//明细ID
            String instorAmounts = hm.get("INSTOR_AMOUNTS");//入库数量
            String remarks = hm.get("REMARKS");//备注
            String[] detailIdArr = detailIds.split(",");
            String[] instorAmountArr = instorAmounts.split(",");
            String[] remarkArr = remarks.split(",");
            for (int i = 0; i < detailIdArr.length; i++) {
            	SeBuReturnorderDetailVO detailVo=new SeBuReturnorderDetailVO();
            	QuerySet qs=dao.getOldPartDetail(detailIdArr[i]);
            	int instoreAmount=0;
            	if(qs.getRowCount()>0){
            		instoreAmount=Integer.parseInt(qs.getString(1, "ALREADY_IN"));
            	}
            	instoreAmount +=Integer.parseInt(instorAmountArr[i]);
            	detailVo.setDetailId(detailIdArr[i]);
            	detailVo.setAlreadyIn(String.valueOf(instoreAmount));
            	if(remarkArr[i].equals("anull")){
            		detailVo.setRemarks("");
            	}else{
            		detailVo.setRemarks(remarkArr[i]);
            	}
            	//旧件库存表
            	dao.insertReturnStore(detailIdArr[i],instorAmountArr[i],user);
            	//更新旧件回运明细表
            	dao.updateDetail(detailVo);
            }
            //判断 该回运单是否有未回运旧件，如果有未回运旧件，继续审批入库，如果全部回运，则修改回运单状态为已入库
            QuerySet qs=dao.getCount(orderId);
            if(qs.getRowCount()>0){
            	String num=qs.getString(1, "COU");
            	int number=Integer.parseInt(num);
            	if(number==0){
            		 //更新主表，入库完成操作
            		 SeBuReturnOrderVO vo=new SeBuReturnOrderVO();
                	 vo.setOrderId(orderId);
                	 vo.setOrderStatus(DicConstant.HYDZT_06);
                	 vo.setUpdateTime(Pub.getCurrentDate());
                	 vo.setUpdateUser(user.getAccount());
                	 dao.returnOldPartUpdate(vo);
                	 //更新索赔单表
                	 dao.claimUpdate(orderId);
                	 //返回插入结果和成功信息
                     atx.setOutMsg("1", "入库完成成功！");
            	}else{
            		//返回插入结果和成功信息
                    atx.setOutMsg("", "入库成功！");
            	}
            }
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    *//**
     * 审批入库完成
     * @throws Exception
     *//*
    public void returnOldPartAllFinal()throws Exception{
    	 RequestWrapper request = atx.getRequest();
    	 User user = (User) atx.getSession().get(Globals.USER_KEY);
    	 String orderId=Pub.val(request, "orderId");
         try {
        	 //更新主表，入库完成操作
        	 SeBuReturnOrderVO vo=new SeBuReturnOrderVO();
        	 vo.setOrderId(orderId);
        	 vo.setOrderStatus(DicConstant.HYDZT_06);
        	 vo.setUpdateTime(Pub.getCurrentDate());
        	 vo.setUpdateUser(user.getAccount());
        	 dao.returnOldPartUpdate(vo);
        	 //更新索赔单表
        	 dao.claimUpdate(orderId);
        	 //更新明细
             HashMap<String, String> hm;
             hm = RequestUtil.getValues(request);
             String detailIds = hm.get("DETAIL_IDS");//明细ID
             String instorAmounts = hm.get("INSTOR_AMOUNTS");//入库数量
             String remarks = hm.get("REMARKS");//备注
             String[] detailIdArr = detailIds.split(",");
             String[] instorAmountArr = instorAmounts.split(",");
             String[] remarkArr = remarks.split(",");
             for (int i = 0; i < detailIdArr.length; i++) {
             	SeBuReturnorderDetailVO detailVo=new SeBuReturnorderDetailVO();
             	QuerySet qs=dao.getOldPartDetail(detailIdArr[i]);
             	int instoreAmount=0;
             	if(qs.getRowCount()>0){
             		instoreAmount=Integer.parseInt(qs.getString(1, "ALREADY_IN"));
             	}
             	instoreAmount +=Integer.parseInt(instorAmountArr[i]);
             	detailVo.setDetailId(detailIdArr[i]);
             	detailVo.setAlreadyIn(String.valueOf(instoreAmount));
             	if(remarkArr[i].equals("anull")){
            		detailVo.setRemarks("");
            	}else{
            		detailVo.setRemarks(remarkArr[i]);
            	}
            	//旧件库存表
            	dao.insertReturnStore(detailIdArr[i],instorAmountArr[i],user);
            	//更新旧件回运明细表
            	dao.updateDetail(detailVo);
             }
             //返回插入结果和成功信息
             atx.setOutMsg("", "入库完成成功！");
         } catch (Exception e) {
             atx.setException(e);
             logger.error(e);
         }
    }*/
}
