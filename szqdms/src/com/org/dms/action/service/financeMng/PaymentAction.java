package com.org.dms.action.service.financeMng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.financeMng.PaymentDao;
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
 * 付款单据下载ACTOIN
 * @author zts
 *
 */
public class PaymentAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "PaymentAction");
	private ActionContext atx = ActionContext.getContext();
	private PaymentDao dao = PaymentDao.getInstance(atx);
	
	/**
     * 付款单据下载查询
     * @throws Exception
     */
    public void settleSearch() throws Exception{
    	RequestWrapper request = atx.getRequest();
 		PageManager page = new PageManager();
 		User user = (User) atx.getSession().get(Globals.USER_KEY);
 		String conditions = RequestUtil.getConditionsWhere(request,page);
 		try
 		{
 			BaseResultSet bs = dao.settleSearch(page,conditions,user);
 			atx.setOutData("bs", bs);
 		}
 		catch (Exception e)
 		{
 			logger.error(e);
 			atx.setException(e);
 		}
    }
    /**
     * 付款更新导入临时表查询
     * @throws Exception
     */
    public void settleImpSearch() throws Exception{
    	PageManager page = new PageManager();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	try
    	{
    		BaseResultSet bs = dao.settleImpSearch(page,user);
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
	public List<ExcelErrors> checkSettlePayUpdate() throws Exception
    {
        User user = (User) atx.getSession().get(Globals.USER_KEY);
      	ExcelErrors errors = null;
    	List<ExcelErrors> errorList = new LinkedList<ExcelErrors>();
		try
		{
			QuerySet qs= dao.queryOrgCode(user);
			if(qs.getRowCount()>0){
				for(int i=0; i<qs.getRowCount();i++){
					errors=new ExcelErrors();
					String rowNo =qs.getString(i+1, "ROW_NO");
					String orgCode =qs.getString(i+1, "ORG_CODE");
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNo));
					errors.setErrorDesc("渠道商代码："+orgCode+"不存在！");
					errorList.add(errors);
				}
			}
			QuerySet qs1 = dao.querySettleNo(user);
			if(qs1.getRowCount()>0){
				for(int i=0;i<qs1.getRowCount();i++){
					errors=new ExcelErrors();
					String rowNo =qs1.getString(i+1, "ROW_NO");
					String orgCode =qs1.getString(i+1, "ORG_CODE");
					String settleNo=qs1.getString(i+1, "SETTLE_NO");
					errors=new ExcelErrors();
					errors.setRowNum(Integer.parseInt(rowNo));
					errors.setErrorDesc("渠道商代码："+orgCode+"与结算单号:"+settleNo+"不统一！");
					errorList.add(errors);
				}
			}
			QuerySet qs2 =dao.checkList(user);
			if(qs2.getRowCount()>0){
				for(int i=0;i<qs2.getRowCount();i++){
					String payAmount =qs2.getString(i+1, "PAY_AMOUNT");
					String rowNo =qs2.getString(i+1, "ROW_NO");
					String reg="^(([1-9]\\d*)|0)(\\.\\d{1,2})?$";//金额正则(最多两位小数)
					if(null!=payAmount&&!"".equals(payAmount)){
						if(!payAmount.matches(reg)){
							errors.setRowNum(Integer.parseInt(rowNo));
	                        errors.setErrorDesc("请输入正确的支付金额!");
	                        errorList.add(errors);
	                    }
	                }
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
	 public void settleUpdateImport()throws Exception{
	    	User user = (User) atx.getSession().get(Globals.USER_KEY);
	    	SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
	 		try
	 		{
	 			QuerySet qs=dao.checkList(user);
				if(qs.getRowCount()>0){
					for(int i=0;i<qs.getRowCount();i++){
						SeBuClaimSettleVO vo =new SeBuClaimSettleVO();
						String settleNo =qs.getString(i+1, "SETTLE_NO");
						String payDate = qs.getString(i+1, "PAY_DATE");
						String payAmount=qs.getString(i+1, "PAY_AMOUNT");
						String ifPay=qs.getString(i+1, "IF_PAY");
						String settleStatus=null;
						if(ifPay.equals("是")){
							settleStatus=DicConstant.JSZT_07;
						}else{
							settleStatus=DicConstant.JSZT_06;
						}
						QuerySet qs1 =dao.queryId(settleNo);
						String settleId= qs1.getString(i+1, "SETTLE_ID");
						vo.setSettleId(settleId);
						vo.setInvoiceDate(sdf.parse(payDate));
						vo.setPaymentAmout(payAmount);
						vo.setSettleStatus(settleStatus);
						vo.setUpdateTime(Pub.getCurrentDate());
						vo.setUpdateUser(user.getAccount());
						dao.settleUpdate(vo);
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
     * 付款单据下载
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
        	int index = 0;
        	List<HeaderBean> header = new ArrayList<HeaderBean>();
        	HeaderBean hBean = null;
        	hBean = new HeaderBean();
    		hBean.setName("ROWNUM");
    		hBean.setTitle("序号");
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
    		hBean.setName("ERP_ID");
    		hBean.setTitle("erp代码");
    		header.add(index++,hBean);
	    	hBean = new HeaderBean();
    		hBean.setName("SETTLE_NO");
    		hBean.setTitle("结算单号");
    		header.add(index++,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SETTLE_DATE");
    		hBean.setTitle("结算日期");
    		header.add(index++,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SETTLE_TYPE");
    		hBean.setTitle("结算类型");
    		header.add(index++,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("COSTS");
    		hBean.setTitle("服务费/材料费");
    		header.add(index++,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("RE_COSTS");
    		hBean.setTitle("旧件运费/配件返利");
    		header.add(index++,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("POLICY_SUP");
    		hBean.setTitle("政策支持");
    		header.add(index++,hBean);
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
    		hBean.setName("OTHERS");
    		hBean.setTitle("其它费用");
    		header.add(index++,hBean);
    		hBean = new HeaderBean();
    		hBean.setName("SUMMARY");
    		hBean.setTitle("汇总");
    		header.add(index++,hBean);
    		QuerySet qs = dao.download(conditions,user);
    		ExportManager.exportFile(response.getHttpResponse(), "付款单据下载", header, qs);
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 付款单据下载
     * @throws Exception
     */
    public void UpdateDownload()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response= atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	PageManager page = new PageManager();
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
    		hBean.setName("ORG_CODE");
    		hBean.setTitle("服务站代码");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SETTLE_NO");
    		hBean.setTitle("结算单号");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FKRQ");
    		hBean.setTitle("结算日期");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("FKJE");
    		hBean.setTitle("结算金额");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("SFYF");
    		hBean.setTitle("是否已付");
    		header.add(index++,hBean);
    		
    		hBean = new HeaderBean();
    		hBean.setName("BZ");
    		hBean.setTitle("备注");
    		header.add(index++,hBean);
    		QuerySet qs = dao.UpdateDownload(conditions,user);
    		ExportManager.exportFile(response.getHttpResponse(), "付款更新模版", header, qs);
    	}
    	catch (Exception e)
    	{
    		atx.setException(e);
    		logger.error(e);
    	}
    }
    
    /**
     * 结算单修改
     * @throws Exception
     */
    public void settleUpdate()throws Exception{
    	//获取封装后的request对象
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try
        {
        	SeBuClaimSettleVO vo=new SeBuClaimSettleVO();
			HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			vo.setSettleStatus(DicConstant.JSZT_07);
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			vo.setOrgId(user.getOrgId());
			vo.setCompanyId(user.getCompanyId());
			dao.settleUpdate(vo);
			atx.setOutMsg("","付款成功！");
        }
        catch (Exception e)
        {
        	//设置失败异常处理
        	atx.setException(e);
            logger.error(e);
        }
    }
}
