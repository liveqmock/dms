package com.org.dms.action.service.oldpartMng;

import java.awt.image.BufferedImage;

import org.apache.log4j.Logger;

import com.org.dms.common.TwoDimensionCode;
import com.org.dms.dao.service.oldpartMng.OldPartPrintPJGLDao;
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
 * 配件管理-旧件标签打印功能
 * 
 * @author fuxiao
 * @version 1.0
 */
public class OldPartPJGLPrintAction {

	// 日志类
	private Logger logger = com.org.framework.log.log
			.getLogger("OldPartPJGLPrintAction");

	// 上下文对象
	private ActionContext atx = ActionContext.getContext();

	// Dao
	private OldPartPrintPJGLDao dao = OldPartPrintPJGLDao.getInstance(atx);
	
	// 二维码图片
	private final String tmPrctureUrl = "/jsp/dms/dealer/service/oldpart/showPicture.jsp";

	/**
	 * 页面查询方法
	 * 
	 * @throws Exception
	 */
	public void searchPart() {
		try {
			RequestWrapper rw = atx.getRequest();
			PageManager pm = new PageManager();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			String conditions = RequestUtil.getConditionsWhere(rw, pm);
			BaseResultSet bs = dao.searchPartList(pm, conditions, user);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
    /**
     *二维码图片
     */
    public void partPrintPicture(){
    	RequestWrapper request = atx.getRequest();
		String applyId=Pub.val(request, "applyId");
		try
		{
			QuerySet qs = dao.queryPartBarCode(applyId );
			BufferedImage bufImg=null;
			String content="";
			if(qs.getRowCount() > 0){
				String orgName=qs.getString(1, "ORG_NAME");							// 渠道商名称
				String orgCode=qs.getString(1, "ORG_CODE");							// 渠道商代码
				String applyNo = qs.getString(1, "APPLY_NO");						// 申请单号码
				String partCode=qs.getString(1, "PART_CODE");						// 配件代码
				String partName=qs.getString(1, "PART_NAME");						// 配件名称
				String claimCount = qs.getString(1, "CLAIM_COUNT");					// 索赔数量
				String supplierCode = qs.getString(1, "SUPPLIER_CODE");				// 供应商代码
				String supplierName = qs.getString(1, "SUPPLIER_NAME");				// 供应商名称
				String faultReason=qs.getString(1, "FAULT_CONDITONS");				// 故障情况
				content = "渠道商代码:" + orgCode + "\r\n" + 
						  "渠道商名称:" + orgName + "\r\n" +
						  "申请单号:"   + applyNo + "\r\n" +
						  "配件代码:"   + partCode + "\r\n" +
						  "配件名称:" + partName + "\r\n" + 
						  "索赔数量:" + claimCount + "\r\n" + 
						  "供应商代码:" + supplierCode + "\r\n" + 
						  "供应商名称:" + supplierName + "\r\n" + 
						  "故障情况:" + faultReason;
				TwoDimensionCode handler = new TwoDimensionCode();
				bufImg=handler.encoderQRCode(content, "png", 20);
			}
			atx.setOutData("bufImg", bufImg);
			atx.setForword(tmPrctureUrl);
		}
		catch (Exception e)
		{
			logger.error(e);
			atx.setException(e);
		}
	}
    
 

}
