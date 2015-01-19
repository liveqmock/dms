package com.org.dms.action.service.financeMng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.dao.service.financeMng.StatementAdjustDao;
import com.org.dms.vo.service.SeBuClaimSettleVO;
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

/**
 * 结算单调整ACTION
 * @author zts
 *
 */
public class StatementAdjustAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "StatementAdjustAction");
	private ActionContext atx = ActionContext.getContext();
	private StatementAdjustDao dao = StatementAdjustDao.getInstance(atx);

	/**
     * 结算单查询
     * @throws Exception
     */
    public void settleSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		String ifmake=Pub.val(request, "ifmake");
 		try
 		{
 			BaseResultSet bs = dao.settleSearch(page,conditions,user,ifmake);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    
    /**
     * 结算单调整保存
     * @throws Exception
     */
    public void settleAdjustSave()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String flag=Pub.val(request, "flag");//1表示正常调整 2表示政策调整 3表示其它费用调整
        try
        {
        	SeBuClaimSettleVO vo=new SeBuClaimSettleVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			vo.setUpdateUser(user.getAccount());// 设置创建人
			vo.setUpdateTime(Pub.getCurrentDate());// 创建时间
			if(flag.equals("1")){
				vo.setAdjustTime(Pub.getCurrentDate());//调整时间
				vo.setAdjustUser(user.getAccount());//调整人
			}else if(flag.equals("2")){
				vo.setPolicyAdjustTime(Pub.getCurrentDate());//政策调整时间
				vo.setPolicyAdjustUser(user.getAccount());//政策调整人
			}else{
				vo.setOthersAdjustTime(Pub.getCurrentDate());//其它调整时间
				vo.setOthersAdjustUser(user.getAccount());//其它调整人
			}
			vo.setOrgId(user.getOrgId());
			vo.setCompanyId(user.getCompanyId());
			vo.setOemCompanyId(user.getOemCompanyId());
			dao.updateSettle(vo);
			vo.bindFieldToDic("SETTLE_TYPE","JSLX");
			atx.setOutMsg(vo.getRowXml(),"调整成功.");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 下载数据
     * @throws Exception
     */
    public void download()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
        try
        {
			String ifmakeAmount=Pub.val(request, "ifmake");
        	// 声明整型变量，用来做添加Title的位置下标，并在主功能中屏蔽对政策支持费用，其他费用修改的列
        	int index = 0;
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
        	hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(index++,hBean);
	    	hBean = new HeaderBean();
    		hBean.setName("SETTLE_NO");
    		hBean.setTitle("结算单号");
    		header.add(index++,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SETTLE_TYPE");
    		hBean.setTitle("结算类型");
    		header.add(index++,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ORG_CODE");
    		hBean.setTitle("服务站代码");
    		header.add(index++,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("ORG_NAME");
    		hBean.setTitle("服务站名称");
    		header.add(index++,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SETTLE_DATE");
    		hBean.setTitle("结算日期");
    		header.add(index++,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("COSTS");
    		hBean.setTitle("服务费/材料费");
    		header.add(index++,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("RE_COSTS");
    		hBean.setTitle("旧件运费/配件返利");
    		header.add(index++,hBean);

    		/* 主功能屏蔽政策支持费用的修改		
      		hBean = new HeaderBean();
    		hBean.setName("POLICY_SUP");
    		hBean.setTitle("政策支持");
    		header.add(index++,hBean);
			*/
    		hBean = new HeaderBean();
    		hBean.setName("CASH_GIFT");
    		hBean.setTitle("礼金");
    		header.add(index++,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("CAR_AWARD");
    		hBean.setTitle("售车奖励");
    		header.add(index++,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("AP_COSTS");
    		hBean.setTitle("考核费用");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("MANUALLY_COST");
    		hBean.setTitle("手工帐费用");
    		header.add(index++,hBean);
    		
    		/* 主功能屏蔽政其他费用的修改		
     		hBean = new HeaderBean();
    		hBean.setName("OTHERS");
    		hBean.setTitle("其它费用");
    		header.add(index++,hBean);
    		*/ 
    		hBean = new HeaderBean();
    		hBean.setName("PART_MATERIAL_COSTS");
    		hBean.setTitle("配件三包材料费");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("MATERIALCOST_REDUCE");
    		hBean.setTitle("材料费冲减");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("REMARKS");
    		hBean.setTitle("备注");
    		header.add(index++,hBean);
    		QuerySet qs = dao.download(conditions,user,ifmakeAmount);
    		ExportManager.exportFile(response.getHttpResponse(), "结算单调整数据", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    
    /**
     * 下载其他费用模板
     * @throws Exception
     */
    public void downloadOtherTemplet()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
        try
        {
        	int index = 0;
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	
        	HeaderBean hBean = null;
        	hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SETTLE_NO");
    		hBean.setTitle("结算单号");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SETTLE_TYPE");
    		hBean.setTitle("结算类型");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("ORG_CODE");
    		hBean.setTitle("服务站代码");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("ORG_NAME");
    		hBean.setTitle("服务站名称");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SETTLE_DATE");
    		hBean.setTitle("结算日期");
    		header.add(index++,hBean);
    		
     		hBean = new HeaderBean();
    		hBean.setName("OTHERS");
    		hBean.setTitle("其它费用");
    		header.add(index++,hBean);
    		
    		QuerySet qs = dao.download(conditions,user,"");
    		ExportManager.exportFile(response.getHttpResponse(), "结算单调整-其他费用", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    
    /**
     * 下载政策支持调整模板
     * @throws Exception
     */
    public void downloadPolicyTemplet()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
        try
        {
        	int index = 0;
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
        	hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SETTLE_NO");
    		hBean.setTitle("结算单号");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SETTLE_TYPE");
    		hBean.setTitle("结算类型");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("ORG_CODE");
    		hBean.setTitle("服务站代码");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("ORG_NAME");
    		hBean.setTitle("服务站名称");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SETTLE_DATE");
    		hBean.setTitle("结算日期");
    		header.add(index++,hBean);
    		
      		hBean = new HeaderBean();
    		hBean.setName("POLICY_SUP");
    		hBean.setTitle("政策支持");
    		header.add(index++,hBean);
    		
    		QuerySet qs = dao.download(conditions,user,"");
    		ExportManager.exportFile(response.getHttpResponse(), "结算单调整-政策支持", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    
    /**
     * 查询导入数据
     * @throws Exception
     */
    public void searchImport()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		RequestUtil.getConditionsWhere(request,page);
 		String conditions = RequestUtil.getConditionsWhere(request, page);
 		try
 		{
 			BaseResultSet bs = dao.searchImport(page,user,conditions);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    
    /**
     * 导入数据校验
     * @return
     * @throws Exception
     */
    public List<ExcelErrors> checkData()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	ExcelErrors errors = null;
		List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		QuerySet qs1=dao.checkList1(user);//结算单号不能为空，校验费用都是数字
		QuerySet qs2=dao.checkList2(user);//结算单号是当前月份的单号
		if(qs1.getRowCount()>0){
			for(int i=0;i<qs1.getRowCount();i++){
				errors=new ExcelErrors();
				String p="^(([1-9]\\d*)|0)(\\.\\d{1,2})?$";//校验钱
				String rowNum=qs1.getString(i+1, "ROW_NUM"); 
				String settleNo=qs1.getString(i+1,"SETTLE_NO");//结算单号
				String seReCosts=qs1.getString(i+1,"SE_RE_COSTS");//旧件运费、配件返利
	    		String sePolicySup=qs1.getString(i+1,"SE_POLICY_SUP");//政策支持
	    		String seCashGift=qs1.getString(i+1,"SE_CASH_GIFT");//礼金
	    		String seCarAward=qs1.getString(i+1,"SE_CAR_AWARD");//售车奖励
	    		String seApCosts=qs1.getString(i+1,"SE_AP_COSTS");//考核费用
	    		String seOthers=qs1.getString(i+1,"SE_OTHERS");//其它费用
				if(null==settleNo||"".equals(settleNo)){
					errors.setRowNum(Integer.parseInt(rowNum));
                    errors.setErrorDesc("结算单号不能为空.");
                    errorList.add(errors);
                }
				/****服务项校验*****/
				//旧件运费/配件返利
				if(null!=seReCosts&&!"".equals(seReCosts)){
					if(!seReCosts.matches(p)){
						errors.setRowNum(Integer.parseInt(rowNum));
						errors.setErrorDesc("请输入正确的旧件运费/配件返利金额.");
                        errorList.add(errors);
                    }
                }
				//政策支持
				if(null!=sePolicySup&&!"".equals(sePolicySup)){
					if(!sePolicySup.matches(p)){
						errors.setRowNum(Integer.parseInt(rowNum));
                        errors.setErrorDesc("请输入正确的政策支持金额.");
                        errorList.add(errors);
                    }
                }
				//礼金
				if(null!=seCashGift&&!"".equals(seCashGift)){
					if(!seCashGift.matches(p)){
						errors.setRowNum(Integer.parseInt(rowNum));
                        errors.setErrorDesc("请输入正确的礼金金额.");
                        errorList.add(errors);
                    }
                }
				//售车奖励
				if(null!=seCarAward&&!"".equals(seCarAward)){
					if(!seCarAward.matches(p)){
						errors.setRowNum(Integer.parseInt(rowNum));
                        errors.setErrorDesc("请输入正确的售车奖励金额.");
                        errorList.add(errors);
                    }
                }
				//售车奖励
				if(null!=seApCosts&&!"".equals(seApCosts)){
					if(!seApCosts.matches(p)){
						errors.setRowNum(Integer.parseInt(rowNum));
                        errors.setErrorDesc("请输入正确的售车奖励金额.");
                        errorList.add(errors);
                    }
                }
				//其它费用
				if(null!=seOthers&&!"".equals(seOthers)){
					if(!seOthers.matches(p)){
						errors.setRowNum(Integer.parseInt(rowNum));
                        errors.setErrorDesc("请输入正确的其它费用金额.");
                        errorList.add(errors);
                    }
                }
			}
		}
		if(qs2.getRowCount()>0){
			for(int i=0;i<qs2.getRowCount();i++){
				errors=new ExcelErrors();
				String rowNum =qs2.getString(i+1,"ROW_NUM");
				String settleNo =qs2.getString(i+1,"SETTLE_NO");
				errors.setRowNum(Integer.parseInt(rowNum));
				errors.setErrorDesc("结算单号"+settleNo+"不是该月份的结算单号.");
				errorList.add(errors);
			}
		}
		if(errorList!=null&&errorList.size()>0){
			return errorList;
		}else{
			return null;
		}
    }
    
    /**
     * 导入确定
     * @throws Exception
     */
    public void settleImport()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	RequestWrapper request = atx.getRequest();
 		try
 		{
 			//更新
 			String tmpNo = Pub.val(request, "tmpNo");
 			String rowNo = "";
        	if ("".equals(tmpNo)==false) {
        		rowNo = " AND T.ROW_NUM NOT IN ("+tmpNo+") ";
        	}
 		    QuerySet qs1=dao.getSettleTmp1(user,rowNo);
 		    if(qs1.getRowCount() > 0 ){
 		    	for(int i=0;i<qs1.getRowCount();i++){
 		    		String settleId=qs1.getString(i+1,"SETTLE_ID");//结算单ID
 		    		String seCosts=qs1.getString(i+1,"SE_COSTS");//服务费、材料费
 		    		String seReCosts=qs1.getString(i+1,"SE_RE_COSTS");//旧件运费、配件返利
 		    		String sePolicySup=qs1.getString(i+1,"SE_POLICY_SUP");//政策支持
 		    		String seCashGift=qs1.getString(i+1,"SE_CASH_GIFT");//礼金
 		    		String seCarAward=qs1.getString(i+1,"SE_CAR_AWARD");//售车奖励
 		    		String seApCosts=qs1.getString(i+1,"SE_AP_COSTS");//考核费用
 		    		String partMaterialCosts=qs1.getString(i+1,"PART_MATERIAL_COSTS");//配件索赔材料费
  		    		String seOthers=qs1.getString(i+1,"SE_OTHERS");//服务其它费用
 		    		String manuallyCost=qs1.getString(i+1,"MANUALLY_COST");//手工帐费用
 		    		String materialcostReduc=qs1.getString(i+1,"MATERIALCOST_REDUCE");//材料费冲减
 		    		String seRemarks=qs1.getString(i+1,"SE_REMARKS");//服务备注
 		    		//汇总
 		    		double seSummary=Double.parseDouble(seCosts)+Double.parseDouble(seReCosts)+Double.parseDouble(sePolicySup)+Double.parseDouble(seCashGift)+Double.parseDouble(seCarAward)+Double.parseDouble(seApCosts)+Double.parseDouble(seOthers)+Double.parseDouble(partMaterialCosts)+Double.parseDouble(manuallyCost)+Double.parseDouble(materialcostReduc);
 		    		SeBuClaimSettleVO vo=new SeBuClaimSettleVO();
 		    		vo.setSettleId(settleId);
 		    		vo.setReCosts(seReCosts);
 		    		vo.setPolicySup(sePolicySup);
 		    		vo.setCashGift(seCashGift);
 		    		vo.setCarAward(seCarAward);
 		    		vo.setApCosts(seApCosts);
 		    		vo.setOthers(seOthers);
 		    		vo.setManuallyCost(manuallyCost);
 		    		vo.setPartMaterialCosts(partMaterialCosts);
 		    		vo.setMaterialcostReduce(materialcostReduc);
 		    		vo.setOthers(seOthers);
 		    		vo.setRemarks(seRemarks);
 		    		vo.setSummary(String.valueOf(seSummary));
 		    		vo.setAdjustTime(Pub.getCurrentDate());//调整时间
 		    		vo.setAdjustUser(user.getAccount());//调整人
 		    		dao.updateSettle(vo);
 		    	}
 		    }
 		    
 			atx.setOutMsg("", "导入成功.");
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    
    /**
     * 其他费用导入确定
     * @throws Exception
     */
    public void settleOtherFeetsImport()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
 		try
 		{
 			//更新
 		    QuerySet qs1=dao.getSettleTmp(user);
 		    if(qs1.getRowCount() > 0 ){
 		    	for(int i=0;i<qs1.getRowCount();i++){
 		    		String settleId=qs1.getString(i+1,"SETTLE_ID");//结算单ID
 		    		String seOthers=qs1.getString(i+1,"SE_OTHERS");//服务其它费用
 		    		SeBuClaimSettleVO vo=new SeBuClaimSettleVO();
 		    		vo.setSettleId(settleId);
 		    		vo.setOthers(seOthers);
 		    		dao.updateOtherFeetsSettle(vo,user);
 		    	}
 		    }
 		    
 			atx.setOutMsg("", "导入成功.");
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    
    /**
     * 政策支持导入确定
     * @throws Exception
     */
    public void settlePolicyFeetsImport()throws Exception{
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
 		try
 		{
 			//更新
 		    QuerySet qs1=dao.getSettleTmp(user);
 		    if(qs1.getRowCount() > 0 ){
 		    	for(int i=0;i<qs1.getRowCount();i++){
 		    		String settleId=qs1.getString(i+1,"SETTLE_ID");//结算单ID
 		    		String sePolicySup=qs1.getString(i+1,"SE_POLICY_SUP");//政策支持
 		    		SeBuClaimSettleVO vo=new SeBuClaimSettleVO();
 		    		vo.setSettleId(settleId);
 		    		vo.setPolicySup(sePolicySup);
 		    		dao.updatePolicyFeetsSettle(vo,user);
 		    	}
 		    }
 		    
 			atx.setOutMsg("", "导入成功.");
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    
    /**
     * 开票通知单
     * @throws Exception
     */
    public void settleUpdate()throws Exception{
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	QuerySet qs = dao.querySettleId(user);
        	if(qs.getRowCount()>0){
        		dao.updateSettleStatus(user);
            	atx.setOutMsg("1", "通知开票成功.");
        	}
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 错误数据导出
     * @throws Exception
     */
    public void expData()throws Exception{

        try {
        	RequestWrapper request = atx.getRequest();
            String conditions = Pub.val(request, "seqs");
            ResponseWrapper response= atx.getResponse();
            User user = (User) atx.getSession().get(Globals.USER_KEY);
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("SETTLE_NO");
            hBean.setTitle("结算单号");
            header.add(0,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ORG_CODE");
            hBean.setTitle("服务站代码");
            header.add(1,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ORG_NAME");
            hBean.setTitle("服务站名称");
            header.add(2,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SE_COSTS");
            hBean.setTitle("服务费/材料费");
            header.add(3,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SE_RE_COSTS");
            hBean.setTitle("旧件运费/配件返利");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SE_CASH_GIFT");
            hBean.setTitle("礼金");
            header.add(5,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SE_CAR_AWARD");
            hBean.setTitle("售车奖励");
            header.add(6,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SE_AP_COSTS");
            hBean.setTitle("考核费用");
            header.add(7,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("PART_MATERIAL_COSTS");
    		hBean.setTitle("配件三包材料费");
            header.add(8,hBean);
            
            hBean = new HeaderBean();
        	hBean.setName("MATERIALCOST_REDUCE");
    		hBean.setTitle("材料费冲减");
            header.add(9,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("SE_REMARKS");
            hBean.setTitle("备注");
            header.add(10,hBean);
            
            QuerySet querySet = dao.expData(conditions,user);
            ExportManager.exportFile(response.getHttpResponse(), "结算单调整错误数据", header, querySet);
        } catch (Exception e) {
        	atx.setException(e);
            logger.error(e);
        }
    }
}
