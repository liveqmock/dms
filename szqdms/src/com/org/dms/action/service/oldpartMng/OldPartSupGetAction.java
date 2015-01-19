package com.org.dms.action.service.oldpartMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.oldpartMng.OldPartSupGetDao;
import com.org.dms.vo.service.SeBuReturnStorageVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

/**
 * 供应商旧件认领CTION
 * @author zts
 *
 */
public class OldPartSupGetAction {
	private Logger logger = com.org.framework.log.log.getLogger(
		        "OldPartSupGetAction");
    private ActionContext atx = ActionContext.getContext();
    private OldPartSupGetDao dao = OldPartSupGetDao.getInstance(atx);
    
    /**
     * 供应商旧件认领查询
     * @throws Exception
     */
    public void oldPartSupGetSearch()throws Exception{
    	RequestWrapper request = atx.getRequest();
  		PageManager page = new PageManager();
  		User user = (User) atx.getSession().get(Globals.USER_KEY);
  		String conditions = RequestUtil.getConditionsWhere(request,page);
  		try
  		{
  			BaseResultSet bs = dao.oldPartSupGetSearch(page,conditions,user);
  			atx.setOutData("bs", bs);
  		}
  		catch (Exception e)
  		{
  			logger.error(e);
  			atx.setException(e);
  		}
    }
    
    /**
     * 供应商旧件认领保存
     * @throws Exception
     */
    public void oldPartSupGetSave()throws Exception{
    	 RequestWrapper request = atx.getRequest();
         User user = (User) atx.getSession().get(Globals.USER_KEY);
         try {
             HashMap<String, String> hm;
             hm = RequestUtil.getValues(request);
             String storageId=hm.get("STORAGE_ID");
             String surplusAmount=hm.get("SURPLUS_AMOUNT");//剩余数量
             String getAmount=hm.get("GET_AMOUNT");//认领数量
             String sumAmount=hm.get("SUM_AMOUNT");//库存总数量
             String outAmount=hm.get("OUT_AMOUNT");//出库数量
             String remarks=hm.get("REMARKS");//出库数量
             int surplusAmount1=Integer.parseInt(surplusAmount) - Integer.parseInt(getAmount);//本次出库之后剩余数量
             int outAmount1=Integer.parseInt(outAmount) + Integer.parseInt(getAmount);//本次出库之后出库数量
             //库存表
             SeBuReturnStorageVO storgaeVo=new SeBuReturnStorageVO();
             storgaeVo.setStorageId(storageId);
             storgaeVo.setSurplusAmount(String.valueOf(surplusAmount1));
             storgaeVo.setOutAmount(String.valueOf(outAmount1));
             storgaeVo.setUpdateTime(Pub.getCurrentDate());
             storgaeVo.setUpdateUser(user.getAccount());
             //更新库存
             dao.updateStorage(storgaeVo);
             //插入出库记录
             dao.insertOut(getAmount,user,DicConstant.JJCKLX_01,storageId,remarks);
             if(sumAmount.equals(String.valueOf(outAmount1))){
            	 atx.setOutMsg("1", "入库完成");
             }else{
            	 atx.setOutMsg("", "入库成功");
             }
         } catch (Exception e) {
             atx.setException(e);
             logger.error(e);
         }
    }
}
