package com.org.dms.action.service.oldpartMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.oldpartMng.OldPartCheckStoragePJDao;
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
 * 功能：配件管理-旧件终审
 * @author fuxiao
 *
 */
public class OldPartPJCheckStorageAction {
	private Logger logger = com.org.framework.log.log.getLogger("OldPartPJCheckStorageAction");
	private ActionContext atx = ActionContext.getContext();
	private OldPartCheckStoragePJDao dao = OldPartCheckStoragePJDao.getInstance(atx);
	
	/**
	 * 
	 * checkStorageSearch: 查询终审的数据
	 * @author fuxiao
	 * @since JDK 1.6
	 */
	public void checkStorageSearch() throws Exception{
		try {
			RequestWrapper rw = atx.getRequest();
			PageManager pm = new PageManager();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			String conditions = RequestUtil.getConditionsWhere(rw, pm);
			BaseResultSet bs = dao.getCheckStoragePartList(pm, conditions, user);
			atx.setOutData("bs", bs);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	
	/**
	 * saveInCount:保存审核信息并新增一条入库记录
	 * @author fuxiao
	 * @since JDK 1.6
	 */
	public void saveInCount() throws Exception{
		RequestWrapper rw = atx.getRequest();
		try {
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			HashMap<String,String> hm = RequestUtil.getValues(rw);
			String applyId = hm.get("APPLY_ID");
			String partCode = hm.get("PART_CODE");
			String partName = hm.get("PART_NAME");
			String supplierCode = hm.get("SUPPLIER_CODE");
			String supplierName = hm.get("SUPPLIER_NAME");
			int inCount = Integer.parseInt(hm.get("IN_COUNT"));
			String aduitRemarks = hm.get("ADUIT_REMARKS");
			this.dao.saveInCountByApplyId(applyId, inCount, aduitRemarks, user);
			SeBuReturnStorageVO vo = new SeBuReturnStorageVO();
			vo.setPartCode(partCode);
			vo.setPartName(partName);
			vo.setSupplierCode(supplierCode);
			vo.setSupplierName(supplierName);
			vo.setSumAmount(String.valueOf(inCount));
			this.dao.insertReturnStorage(vo, user);
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
	
	
	/**
	 * 
	 * cancelPartStorage:取消一条入库信息
	 * @author fuxiao
	 * @since JDK 1.6
	 * @throws Exception 
	 */
	public void cancelPartStorage() throws Exception{
		RequestWrapper rw = atx.getRequest();
		try {
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			String applyId = Pub.val(rw, "APPLY_ID");
			String status = DicConstant.YXBS_02;
			this.dao.updateReturnStorageStatusByApplyId(applyId, status, user);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			atx.setException(e);
		}
	}
}
