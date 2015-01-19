package com.org.dms.action.part.financeMng.transfer;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.financeMng.transfer.TransferReportAdjustMngADao;
import com.org.dms.vo.part.PtBuAccountLogVO;
import com.org.dms.vo.part.PtBuAccountTransferVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class TransferReportAdjustMngAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "Logger");
	    //上下文对象
	    private ActionContext atx = ActionContext.getContext();
	    //定义dao对象
	    private TransferReportAdjustMngADao dao = TransferReportAdjustMngADao.getInstance(atx);
	
	/**
	 * 
	 * @date()2014年8月2日上午9:53:35
	 * @author Administrator
	 * @to_do:转账审核查询
	 * @throws Exception
	 */
	public void transferSearch() throws Exception
    {
	    RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
		PageManager page = new PageManager();
		String conditions = RequestUtil.getConditionsWhere(request,page);
		try
		{
			BaseResultSet bs = dao.transferSearch(page,user,conditions);
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
	 * @date()2014年8月2日上午9:53:56
	 * @author Administrator
	 * @to_do:获取账户余额
	 * @throws Exception
	 */
	public void getBalanceAmount() throws Exception {
		RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String TRANSFER_ID = Pub.val(request, "TRANSFER_ID");
        BaseResultSet bs = dao.getAccount(TRANSFER_ID);
        atx.setOutData("bs", bs);
   }
	/**
	 * 
	 * @date()2014年8月2日上午10:27:59
	 * @author Administrator
	 * @to_do:转账申请驳回
	 * @throws Exception
	 */
	public void transferRejected() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBuAccountTransferVO tempVO = new PtBuAccountTransferVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			tempVO.setTransferStatus(DicConstant.ZZZT_04);
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());//
            dao.updateTransfer(tempVO);
            atx.setOutMsg(tempVO.getRowXml(),"转账申请驳回成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
	/**
	 * 
	 * @date()2014年8月2日上午10:30:26
	 * @author Administrator
	 * @to_do:转账申请通过
	 * @throws Exception
	 */
	public void transferPass() throws Exception
    {
    	RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBuAccountTransferVO tempVO = new PtBuAccountTransferVO();
        try
        {
            HashMap<String,String> hm;
			hm = RequestUtil.getValues(request);
			tempVO.setValue(hm);
			tempVO.setTransferStatus(DicConstant.ZZZT_03);
			tempVO.setUpdateUser(user.getAccount());
			tempVO.setUpdateTime(Pub.getCurrentDate());//
            dao.updateTransfer(tempVO);
            /*
             * 更新渠道账户信息
             */
            String ORG_ID = hm.get("ORG_ID");
            String AMOUNT = hm.get("AMOUNT");
            
            QuerySet bs = dao.getRebate(ORG_ID);//获取返利账户ID
            String REBATE_ACCOUNT_ID = bs.getString(1, "ACCOUNT_ID");
            dao.changeAccount02(REBATE_ACCOUNT_ID,AMOUNT);//减少返利账户余额 可用余额
            QuerySet qs = dao.getMaterial(ORG_ID); //获取材料费账户ID 
            String MATERIAL_ACCOUNT_ID = qs.getString(1, "ACCOUNT_ID");
            dao.changeAccount01(MATERIAL_ACCOUNT_ID,AMOUNT);//增加材料费账户余额可用余额
            /*
             *插入账户异动信息 
             */
            //返利账户转出
            PtBuAccountLogVO vo = new PtBuAccountLogVO();
			vo.setAccountId(REBATE_ACCOUNT_ID);
			vo.setLogType(DicConstant.ZJYDLX_02);
			vo.setAmount(AMOUNT);
			vo.setInDate(Pub.getCurrentDate());
			vo.setSourceaccountType(DicConstant.ZJZHLX_05);
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());//
			vo.setOrgId(user.getOrgId());
			dao.insertLog(vo);
			//材料费账户转入
			PtBuAccountLogVO mvo = new PtBuAccountLogVO();
			mvo.setAccountId(MATERIAL_ACCOUNT_ID);
			mvo.setLogType(DicConstant.ZJYDLX_03);
			mvo.setAmount(AMOUNT);
			mvo.setInDate(Pub.getCurrentDate());
			mvo.setSourceaccountType(DicConstant.ZJZHLX_05);
			mvo.setCreateUser(user.getAccount());
			mvo.setCreateTime(Pub.getCurrentDate());//
			mvo.setOrgId(user.getOrgId());
			dao.insertLog(mvo);
            
            atx.setOutMsg(tempVO.getRowXml(),"转账申请通过成功！");
        }
        catch (Exception e)
        {
        	atx.setException(e);
            logger.error(e);
        }
    }
}
