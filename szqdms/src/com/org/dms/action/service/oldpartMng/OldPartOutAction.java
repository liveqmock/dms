package com.org.dms.action.service.oldpartMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.oldpartMng.OldPartOutDao;
import com.org.dms.vo.service.SeBuReturnStorageVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
/**
 * 旧件出库ACTION
 * @author zts
 *
 */
public class OldPartOutAction {
	private Logger logger = com.org.framework.log.log.getLogger(
	        "OldPartOutAction");
	private ActionContext atx = ActionContext.getContext();
	private OldPartOutDao dao = OldPartOutDao.getInstance(atx);
	/**
	 * 旧件出库查询
	 * @throws Exception
	 */
	public void oldPartOutSearch()throws Exception{
		RequestWrapper request = atx.getRequest();
  		PageManager page = new PageManager();
  		User user = (User) atx.getSession().get(Globals.USER_KEY);
  		String conditions = RequestUtil.getConditionsWhere(request,page);
  		
  		try
  		{
  			BaseResultSet bs = dao.oldPartOutSearch(page,conditions,user);
  			atx.setOutData("bs", bs);
  		}
  		catch (Exception e)
  		{
  			logger.error(e);
  			atx.setException(e);
  		}
	}
	/**
	 * 旧件出库保存
	 * @throws Exception
	 */
	public void oldPartOutSave()throws Exception{
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
            String outType=hm.get("OUT_TYPE");//出库类型
            String supplierCode=hm.get("SUPPLIER_CODE");
            String partCode=hm.get("PART_CODE");
            String remarks=hm.get("REMARKS");
            int surplusAmount1=Integer.parseInt(surplusAmount) - Integer.parseInt(getAmount);//认领后剩余数量
            int outAmount1=Integer.parseInt(outAmount) + Integer.parseInt(getAmount);//认领后出库数量
            QuerySet qs = dao.checkStatus(supplierCode,partCode);
            if(outType.equals(DicConstant.JJCKLX_01)){
            	if(qs.getRowCount()>0){
                	//更新库存
                    SeBuReturnStorageVO storgaeVo=new SeBuReturnStorageVO();
                    storgaeVo.setStorageId(storageId);
                    storgaeVo.setSurplusAmount(String.valueOf(surplusAmount1));
                    storgaeVo.setOutAmount(String.valueOf(outAmount1));
                    storgaeVo.setUpdateTime(Pub.getCurrentDate());
                    storgaeVo.setUpdateUser(user.getAccount());
                    dao.updateStorage(storgaeVo);
                    //插入出库记录
                    dao.insertOut(getAmount,user,outType,storageId,remarks);
                    //返回插入结果和成功信息
                    if(sumAmount.equals(String.valueOf(outAmount1))){
                   	 atx.setOutMsg("1", "认领完成！");
                    }else{
                   	 atx.setOutMsg("", "认领成功！");
                    }
                }else{
                	atx.setOutMsg("", "供应商认领资格不充分");
                }
            }else{
            	//更新库存
                SeBuReturnStorageVO storgaeVo=new SeBuReturnStorageVO();
                storgaeVo.setStorageId(storageId);
                storgaeVo.setSurplusAmount(String.valueOf(surplusAmount1));
                storgaeVo.setOutAmount(String.valueOf(outAmount1));
                storgaeVo.setUpdateTime(Pub.getCurrentDate());
                storgaeVo.setUpdateUser(user.getAccount());
                dao.updateStorage(storgaeVo);
                //插入出库记录
                dao.insertOut(getAmount,user,outType,storageId,remarks);
                //返回插入结果和成功信息
                if(sumAmount.equals(String.valueOf(outAmount1))){
               	 atx.setOutMsg("1", "认领完成！");
                }else{
               	 atx.setOutMsg("", "认领成功！");
                }
            }
            
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
	}
}
