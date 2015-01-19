package com.org.dms.action.service.basicinfomng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.service.basicinfomng.VehicleModelMngDao;
import com.org.dms.vo.service.MainModelsVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;

public class VehicleModelMngAction {
	private Logger logger = com.org.framework.log.log
			.getLogger("VehicleModelMngAction");
	private ActionContext atx = ActionContext.getContext();
	private VehicleModelMngDao dao = VehicleModelMngDao.getInstance(atx);
	
	public void search() throws Exception {
			RequestWrapper request = atx.getRequest();
			User user = (User) atx.getSession().get(Globals.USER_KEY);
			PageManager page = new PageManager();
			String conditions = RequestUtil.getConditionsWhere(request, page);
			try {
				BaseResultSet bs = dao.search(page, user, conditions);
				atx.setOutData("bs", bs);
			} catch (Exception e) {
				atx.setException(e);
				logger.error(e);
			}
		}
	public void resetStatus() throws Exception 
	{
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		String modelsId = Pub.val(request, "modelsId");
		try {
			MainModelsVO vo = new MainModelsVO();
			vo.setModelsId(modelsId);
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			vo.setStatus(DicConstant.YXBS_02);
			dao.update(vo);
			atx.setOutMsg(vo.getRowXml(), "车型删除成功！");
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		}
	}
	public void update() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		try {
			MainModelsVO vo = new MainModelsVO();
			HashMap<String, String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			vo.setUpdateUser(user.getAccount());
			vo.setUpdateTime(Pub.getCurrentDate());
			dao.update(vo);
			vo.bindFieldToDic("STATUS", "YXBS");
			atx.setOutMsg(vo.getRowXml(), "更新车型成功！");
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		}
	}
	public void insert() throws Exception {
		RequestWrapper request = atx.getRequest();
		User user = (User) atx.getSession().get(Globals.USER_KEY);
		try {
			MainModelsVO vo = new MainModelsVO();
			HashMap<String, String> hm;
			hm = RequestUtil.getValues(request);
			vo.setValue(hm);
			//判断是否已存在
			QuerySet qs = dao.check(vo.getModelsCode());
			if(qs.getRowCount() > 0)
			{
				String n = qs.getString(1, 1);
				if(Integer.parseInt(n) > 0)
				{
					throw new Exception("车型代码已存在，保存失败！");
				}
			}
			vo.setCreateUser(user.getAccount());
			vo.setCreateTime(Pub.getCurrentDate());
			dao.insert(vo);
			vo.bindFieldToDic("STATUS", "YXBS");
			atx.setOutMsg(vo.getRowXml(), "新增车型成功！");
		} catch (Exception e) {
			atx.setException(e);
			logger.error(e);
		}
	}
}
