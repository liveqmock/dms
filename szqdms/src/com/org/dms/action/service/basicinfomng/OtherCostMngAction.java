package com.org.dms.action.service.basicinfomng;import com.org.dms.vo.service.SeBaOtherCostVO;import com.org.dms.dao.service.basicinfomng.SeBaOtherCostDao;import java.util.HashMap;import org.apache.log4j.Logger;import com.org.dms.common.DicConstant;import com.org.framework.Globals;import com.org.framework.common.User;import com.org.mvc.context.ActionContext;import com.org.framework.common.BaseResultSet;import com.org.framework.common.PageManager;import com.org.framework.common.QuerySet;import com.org.framework.log.LogManager;import com.org.framework.util.Pub;import com.org.framework.util.RequestUtil;import com.org.mvc.context.RequestWrapper;/** * 其他费用类型维护 * <p> * Company: szq * </p> *  * @author baixiaoliang * @version 1.0 2014-7-14 */public class OtherCostMngAction {	private Logger logger = com.org.framework.log.log			.getLogger("OtherCostMngAction");	private ActionContext atx = ActionContext.getContext();	private SeBaOtherCostDao dao = SeBaOtherCostDao.getInstance(atx);	/**	 * @auther baixiaoliang 其他费用类型维护新增	 * @return void	 */	public void insert() throws Exception {		RequestWrapper request = atx.getRequest();		User user = (User) atx.getSession().get(Globals.USER_KEY);		try {			SeBaOtherCostVO vo = new SeBaOtherCostVO();			// SeBaOtherCostVO_Ext voExt=new SeBaOtherCostVO_Ext();			HashMap<String, String> hm;			hm = RequestUtil.getValues(request);			vo.setValue(hm);		 /*	//判断是否已存在			QuerySet qs = dao.check(vo.getCostId());			if(qs.getRowCount() > 0)			{				String n = qs.getString(1, 1);				if(Integer.parseInt(n) > 0)				{					throw new Exception("其他费用类型已存在，保存失败！");				}			}*/			// voExt.setValue(hm);			vo.setCreateUser(user.getAccount());			vo.setCreateTime(Pub.getCurrentDate());			dao.insert(vo);			vo.bindFieldToDic("STATUS", "YXBS");			// voExt.setCostId(vo.getCostId());			// voExt.bindFieldToDic("STATUS","YXBS");			// voExt.bindFieldToDic("USER_TYPE","CLYHLX");			// atx.setOutMsg(voExt.getRowXml(),"其他费用类型维护新增成功！");			atx.setOutMsg(vo.getRowXml(), "其他费用类型维护新增成功！");			  //日志            LogManager.writeUserLog("","",Globals.OPERATION_TYPE_INSERT,LogManager.RESULT_SUCCESS                                    ,"更新其他费用类型 ["+vo.getCostId()+" / "+"] 成功",user);		} catch (Exception e) {			atx.setException(e);			logger.error(e);			 //日志            LogManager.writeUserLog("", "", Globals.OPERATION_TYPE_INSERT, LogManager.RESULT_FAILURE,"更新其他费用类型失败："+e.toString(), user);		}	}	/**	 * @auther baixiaoliang 其他费用类型维护新增	 * @return void	 */	public void update() throws Exception {		RequestWrapper request = atx.getRequest();		User user = (User) atx.getSession().get(Globals.USER_KEY);		try {			SeBaOtherCostVO vo = new SeBaOtherCostVO();			// SeBaOtherCostVO_Ext voExt=new SeBaOtherCostVO_Ext();			HashMap<String, String> hm;			hm = RequestUtil.getValues(request);			vo.setValue(hm);			// voExt.setValue(hm);			vo.setUpdateUser(user.getAccount());			vo.setUpdateTime(Pub.getCurrentDate());			dao.update(vo);			// voExt.setCostId(vo.getCostId());			// voExt.bindFieldToDic("STATUS","YXBS");			// voExt.bindFieldToDic("USER_TYPE","CLYHLX");			// atx.setOutMsg(voExt.getRowXml(),"其他费用类型维护修改成功！");			atx.setOutMsg(vo.getRowXml(), "其他费用类型维护修改成功！");			  //日志            LogManager.writeUserLog("","",Globals.OPERATION_TYPE_INSERT,LogManager.RESULT_SUCCESS                                    ,"更新其他费用类型 ["+vo.getCostId()+" / "+"] 成功",user);		} catch (Exception e) {			atx.setException(e);			logger.error(e);			 //日志            LogManager.writeUserLog("", "", Globals.OPERATION_TYPE_INSERT, LogManager.RESULT_FAILURE,"更新其他费用类型失败："+e.toString(), user);		}	}	/**	 * @auther baixiaoliang 其他费用类型维护删除	 * @return void	 */	public void delete() throws Exception {		RequestWrapper request = atx.getRequest();		String status = Pub.val(request, "status");		String costId = Pub.val(request, "costId");		try {			if (DicConstant.YXBS_02.equals(status)) {				dao.delete(costId);				atx.setOutMsg("", "其他费用类型维护额删除成功！");			} else {				atx.setOutMsg("", "只有无效的才可以进行删除操作！");			}		} catch (Exception e) {			atx.setException(e);			logger.error(e);		}	}	/**	 * @auther baixiaoliang 其他费用类型维护查询	 * @return void	 */	public void search() throws Exception {		RequestWrapper request = atx.getRequest();		User user = (User) atx.getSession().get(Globals.USER_KEY);		PageManager page = new PageManager();		String conditions = RequestUtil.getConditionsWhere(request, page);		try {			BaseResultSet bs = dao.search(page, user, conditions);			atx.setOutData("bs", bs);		} catch (Exception e) {			atx.setException(e);			logger.error(e);		}	}	/**	 * @auther baixiaoliang 其他费用类型维护删除	 * @return void	 */	public void resetStatus() throws Exception {		RequestWrapper request = atx.getRequest();		User user = (User) atx.getSession().get(Globals.USER_KEY);		String status = Pub.val(request, "status");		String costId = Pub.val(request, "costId");		try {			SeBaOtherCostVO vo = new SeBaOtherCostVO();			vo.setCostId(costId);			vo.setUpdateUser(user.getAccount());			vo.setUpdateTime(Pub.getCurrentDate());			vo.setStatus(status);			dao.update(vo);			atx.setOutMsg("", "其他费用类型维护删除成功！");			  //日志            LogManager.writeUserLog("","",Globals.OPERATION_TYPE_INSERT,LogManager.RESULT_SUCCESS                                    ,"外出费用 ["+costId+" / "+status+"] 成功",user);		} catch (Exception e) {			atx.setException(e);			logger.error(e);			 //日志            LogManager.writeUserLog("", "", Globals.OPERATION_TYPE_INSERT, LogManager.RESULT_FAILURE,"外出费用失败："+e.toString(), user);		}	}}