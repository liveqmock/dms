package com.org.dms.action.part.financeMng.remitMng;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.financeMng.remitMng.RemitCheckInConfirmDao;
import com.org.dms.vo.part.PtBuAccountLogVO;
import com.org.dms.vo.part.PtBuMoneyRemitVO;
import com.org.dms.vo.part.PtBuSaleOrderOccupyFundsVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class RemitCheckInConfirmAction {
	
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private RemitCheckInConfirmDao dao = RemitCheckInConfirmDao.getInstance(atx);
	    /**
	     * 
	     * @date()2014年8月1日上午8:56:53
	     * @author Administrator
	     * @to_do:打款查询
	     * @throws Exception
	     */
	    public void remitSearch() throws Exception
	    {
		    RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request,page);
			try
			{
				BaseResultSet bs = dao.remitSearch(page,user,conditions);
				atx.setOutData("bs", bs);
			}
			catch (Exception e)
			{
				logger.error(e);
				atx.setException(e);
			}
		}
	    /**
	     * 
	     * @date()2014年8月5日下午9:45:39
	     * @author Administrator
	     * @to_do:打款确认
	     * @throws Exception
	     */
	    public void remitConfirm() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
	        PtBuMoneyRemitVO tempVO = new PtBuMoneyRemitVO();
	        try
	        {
	        	HashMap<String,String> hm;
				hm = RequestUtil.getValues(request);
				String REMIT_ID = hm.get("REMIT_ID");
				String inDate = hm.get("IN_DATE");
//	        	String REMIT_ID = Pub.val(request, "REMIT_ID");
//	        	String IN_DATE = Pub.val(request, "IN_DATE");
				tempVO.setValue(hm);
				tempVO.setRemitStatus(DicConstant.DKZT_03);
				tempVO.setUpdateUser(user.getAccount());
				tempVO.setUpdateTime(Pub.getCurrentDate());
	            dao.updateRemit(tempVO);
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            Date date = sdf.parse(inDate);
	            /**
	             * 增加日志
	             */
	            QuerySet getAcc = dao.getAcc(REMIT_ID);
				//PtBuAccountLogVO vo = new PtBuAccountLogVO();
				//vo.setAccountId(getAcc.getString(1, "ACCOUNT_ID"));
//				vo.setLogType(DicConstant.ZJYDLX_01);
//				vo.setAmount(hm.get("BILL_AMOUNT"));
//				vo.setRemarks(hm.get("REMARK"));
//				vo.setCreateUser(user.getAccount());
//				vo.setCreateTime(Pub.getCurrentDate());//
//				vo.setOrgId(user.getOrgId());
//				dao.insertLog(vo);
	            
				/**
				 * 更改渠道账户金额
				 */
				QuerySet qs = dao.getAccount(REMIT_ID);
				String ORG_ID = qs.getString(1, "ORG_ID");
				String AMOUNT_TYPE = qs.getString(1, "AMOUNT_TYPE");
				String BILL_AMOUNT = qs.getString(1, "BILL_AMOUNT");
				String REMARK = qs.getString(1, "REMARK");
				String OCCUPY_AMOUNT = qs.getString(1, "OCCUPY_AMOUNT");
				String CLOSE_AMOUNT = qs.getString(1, "CLOSE_AMOUNT");
//				String occupyAll = "0";
//				QuerySet id = dao.getAccountId(ORG_ID,AMOUNT_TYPE);
				String ACCOUNT_ID = qs.getString(1, "ACCOUNT_ID");
//				QuerySet checkCreditAll =dao.checkCreditAll(ACCOUNT_ID);
//				if(checkCreditAll.getRowCount()>0)
//					occupyAll = checkCreditAll.getString(1, "ALL_OCCUPY_FUNDS");
				double amount = Double.parseDouble(BILL_AMOUNT);
				if(Double.parseDouble(CLOSE_AMOUNT)>0){
					double syamount =Double.parseDouble(CLOSE_AMOUNT);
					if(amount-syamount>0){
						dao.updateCloseCredit(ORG_ID,String.valueOf(syamount));
						PtBuAccountLogVO vo = new PtBuAccountLogVO();
						vo.setAccountId(ACCOUNT_ID);
						vo.setLogType(DicConstant.ZJYDLX_01);
						vo.setAmount(String.valueOf(syamount));
						vo.setSourceaccountType(getAcc.getString(1, "AMOUNT_TYPE"));
						vo.setRemarks(REMARK);
						vo.setInDate(date);
						vo.setRemitId(REMIT_ID);
						vo.setCreateUser(user.getAccount());
						vo.setCreateTime(Pub.getCurrentDate());
						vo.setOrgId(user.getOrgId());
						dao.insertLog(vo);
						amount= amount-syamount;
					}else{
						dao.updateCloseCredit(ORG_ID,String.valueOf(amount));
						PtBuAccountLogVO vo = new PtBuAccountLogVO();
						vo.setAccountId(ACCOUNT_ID);
						vo.setLogType(DicConstant.ZJYDLX_01);
						vo.setAmount(String.valueOf(amount));
						vo.setSourceaccountType(getAcc.getString(1, "AMOUNT_TYPE"));
						vo.setRemarks(REMARK);
						vo.setInDate(date);
						vo.setRemitId(REMIT_ID);
						vo.setCreateUser(user.getAccount());
						vo.setCreateTime(Pub.getCurrentDate());
						vo.setOrgId(user.getOrgId());
						dao.insertLog(vo);
						amount= 0;
					}
				}
				QuerySet check = dao.checkCredit(ACCOUNT_ID);
				if(check.getRowCount()>0){
					double occupyAmount=0;
					for(int i = 1;i<=check.getRowCount();i++){
						String OFUNDS_ID = check.getString(i, "OFUNDS_ID");
						double OCCUPY_FUND = Double.parseDouble(check.getString(i, "OCCUPY_FUNDS"));
						double repayAmount = Double.parseDouble(check.getString(i, "REPAY_AMOUNT"));
						if(amount>0){
							if(amount-OCCUPY_FUND>0&&amount>0){//打款金额大于单张订单信用额度占用
								PtBuSaleOrderOccupyFundsVO r_vo = new PtBuSaleOrderOccupyFundsVO();
								r_vo.setOfundsId(OFUNDS_ID);
								repayAmount = repayAmount +OCCUPY_FUND;
								r_vo.setRepayAmount(String.valueOf(repayAmount));
								r_vo.setStatus(DicConstant.YXBS_02);
								r_vo.setUpdateTime(Pub.getCurrentDate());
								r_vo.setUpdateUser(user.getAccount());
								dao.updateOfunds(r_vo);
								//减少信用额度占用
								dao.updateCredit(ORG_ID,String.valueOf(OCCUPY_FUND));
								occupyAmount =occupyAmount+OCCUPY_FUND;
								amount = amount-OCCUPY_FUND;
								
							}else{
								//打款金额小于单张订单信用额度占用
								PtBuSaleOrderOccupyFundsVO t_vo = new PtBuSaleOrderOccupyFundsVO();
								t_vo.setOfundsId(OFUNDS_ID);
								t_vo.setRepayAmount(String.valueOf(amount));
								t_vo.setUpdateTime(Pub.getCurrentDate());
								t_vo.setUpdateUser(user.getAccount());
								dao.updateOfunds(t_vo);
								//减少信用额度占用
								dao.updateCredit(ORG_ID,String.valueOf(amount));
								occupyAmount =occupyAmount+amount;
								amount = 0;
							}
						}
					}
					if(occupyAmount>0){
						//插入资金异动
						PtBuAccountLogVO vo2 = new PtBuAccountLogVO();
						vo2.setAccountId(ACCOUNT_ID);
						vo2.setLogType(DicConstant.ZJYDLX_01);
						vo2.setAmount(String.valueOf(occupyAmount));
						vo2.setSourceaccountType(getAcc.getString(1, "AMOUNT_TYPE"));
						vo2.setRemarks(REMARK);
						vo2.setInDate(date);
						vo2.setRemitId(REMIT_ID);
						vo2.setCreateUser(user.getAccount());
						vo2.setCreateTime(Pub.getCurrentDate());
						vo2.setOrgId(user.getOrgId());
						dao.insertLog(vo2);
					}
					//所有欠款金额都还清之后将剩余资金添加到账户中
					if(amount>0){
						dao.updateAccountAmount(ORG_ID,AMOUNT_TYPE,String.valueOf(amount));
						/**
						 * 插入账户异动
						 */
						PtBuAccountLogVO vo = new PtBuAccountLogVO();
						vo.setAccountId(getAcc.getString(1, "ACCOUNT_ID"));
						vo.setLogType(DicConstant.ZJYDLX_01);
						vo.setAmount(String.valueOf(amount));
						vo.setSourceaccountType(getAcc.getString(1, "AMOUNT_TYPE"));
						vo.setRemarks(REMARK);
						vo.setInDate(date);
						vo.setRemitId(REMIT_ID);
						vo.setCreateUser(user.getAccount());
						vo.setCreateTime(Pub.getCurrentDate());
						vo.setOrgId(user.getOrgId());
						dao.insertLog(vo);
					}
				}else{
					dao.updateAccountAmount(ORG_ID,AMOUNT_TYPE,BILL_AMOUNT);
					/**
					 * 插入账户异动
					 */
					PtBuAccountLogVO vo = new PtBuAccountLogVO();
					vo.setAccountId(getAcc.getString(1, "ACCOUNT_ID"));
					vo.setLogType(DicConstant.ZJYDLX_01);
					vo.setAmount(String.valueOf(amount));
					vo.setSourceaccountType(getAcc.getString(1, "AMOUNT_TYPE"));
					vo.setRemarks(REMARK);
					vo.setInDate(date);
					vo.setRemitId(REMIT_ID);
					vo.setCreateUser(user.getAccount());
					vo.setCreateTime(Pub.getCurrentDate());//
					vo.setOrgId(user.getOrgId());
					dao.insertLog(vo);
				}
				atx.setOutMsg("","打款确认成功！");
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }
	    
	    public void remitDelete() throws Exception
	    {
	    	RequestWrapper request = atx.getRequest();
	        try
	        {
				String REMIT_ID = Pub.val(request, "REMIT_ID");
	            dao.purchaseOrderDelete(REMIT_ID);
				atx.setOutMsg("","订单删除成功！");
	            
	        }
	        catch (Exception e)
	        {
	        	atx.setException(e);
	            logger.error(e);
	        }
	    }

}
