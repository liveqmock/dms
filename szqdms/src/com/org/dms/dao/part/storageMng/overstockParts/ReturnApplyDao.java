package com.org.dms.dao.part.storageMng.overstockParts;

import com.org.framework.base.BaseDAO;
import com.org.mvc.context.ActionContext;

/**
 * 
 * ClassName: ReturnApplyDao 
 * Function: 退货单相关操作Dao
 * date: 2014年9月22日 下午1:42:48
 * @author fuxiao
 * @version 1.0.0
 * @since JDK 1.6
 */
public class ReturnApplyDao extends BaseDAO{
	
	public static final ReturnApplyDao getInstance(ActionContext ac){
		ReturnApplyDao dao = new ReturnApplyDao();
		ac.setDBFactory(dao.factory);
		return dao;
	}
	
	public boolean addReturnAppliy(){
		
		return false;
	}
	
	public boolean addReturnAppliyPartsInfo(){
		
		return false;
	}
}
