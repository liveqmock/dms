package com.org.dms.action.part.basicInfoMng;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.basicInfoMng.InvoiceDaySetDao;
import com.org.dms.vo.part.PtBaInvoiceDayVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
/**
 * @Author: gouwentan
 * @Date: 2014-12-18
 * @Description:结算日设置Action
 */
public class InvoiceDaySetAction {
	private Logger logger = com.org.framework.log.log.getLogger("InvoiceDaySetAction");
    private ActionContext atx = ActionContext.getContext();
    private InvoiceDaySetDao dao = InvoiceDaySetDao.getInstance(atx);
    /**
     * 结算日查询
     */
    public void invoiceDaySetSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.invoiceDaySetSearch(page, user, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 结算日设置新增
     */
    public void invoiceDaySetInsert() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
        	PtBaInvoiceDayVO vo = new PtBaInvoiceDayVO();
        	SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            String yearStr = yearFormat.format(Pub.getCurrentDate());
            String monthStr = monthFormat.format(Pub.getCurrentDate());
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            vo.setValue(hm);
            vo.setInvoiceMonth(yearStr+"-"+monthStr);
            vo.setInvoiceStatus(DicConstant.TJZT_01);
            vo.setCreateUser(user.getAccount());
            vo.setCreateTime(Pub.getCurrentDate());
            dao.invoiceDaySetInsert(vo);
            atx.setOutMsg(vo.getRowXml(), "新增成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 结算日设置修改
     */
    public void invoiceDaySetUpdate() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBaInvoiceDayVO vo = new PtBaInvoiceDayVO();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        String yearStr = yearFormat.format(Pub.getCurrentDate());
        String monthStr = monthFormat.format(Pub.getCurrentDate());
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            vo.setValue(hm);
            vo.setInvoiceMonth(yearStr+"-"+monthStr);
            dao.invoiceDaySetUpdate(vo);
            atx.setOutMsg(vo.getRowXml(), "修改成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 结算统计
     */
    public void invoice() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String dayId = Pub.val(request, "dayId");
        try {
        	dao.invoice(dayId);
            atx.setOutMsg("", "统计完成.");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 清除结算统计
     */
    public void delInvoice() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String dayId = Pub.val(request, "dayId");
        try {
        	dao.delInvoice(dayId);
            atx.setOutMsg("", "清除成功.");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
}
