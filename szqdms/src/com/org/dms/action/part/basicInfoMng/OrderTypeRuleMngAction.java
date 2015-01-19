package com.org.dms.action.part.basicInfoMng;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.org.dms.dao.part.basicInfoMng.OrderTypeRuleMngDao;
import com.org.dms.vo.part.PtBaOrderTypeRuleVO;
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
 * @Author: gouwentan
 * @Date: 2014-07-14
 * @Description:订单类型规则维护Action
 */
public class OrderTypeRuleMngAction {
    private Logger logger = com.org.framework.log.log.getLogger("OrderTypeRuleMngAction");
    private ActionContext atx = ActionContext.getContext();
    private OrderTypeRuleMngDao dao = OrderTypeRuleMngDao.getInstance(atx);
    /**
     * 订单类型规则查询
     */
    public void orderTypeRuleSearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            BaseResultSet bs = dao.orderTypeRuleSearch(page, user, conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    /**
     * 订单类型规则新增
     */
    public void orderTypeRuleInsert() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            PtBaOrderTypeRuleVO vo = new PtBaOrderTypeRuleVO();
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            vo.setValue(hm);
            vo.setCompanyId(user.getCompanyId());
            vo.setOrgId(user.getOrgId());
            vo.setOemCompanyId(user.getOemCompanyId());
            vo.setCreateUser(user.getAccount());
            vo.setCreateTime(Pub.getCurrentDate());
            QuerySet querySet = dao.orderTypeCheck(vo.getTypeCode());
            if (querySet.getRowCount() > 0) {
            	throw new Exception("类型代码重复！");
            } else {
            	dao.orderTypeRuleInsert(vo);
            	atx.setOutMsg(vo.getRowXml(), "新增成功！");
            }
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 订单类型规则修改
     */
    public void orderTypeRuleUpdate() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBaOrderTypeRuleVO vo = new PtBaOrderTypeRuleVO();
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            vo.setValue(hm);
            vo.setUpdateUser(user.getAccount());
            vo.setUpdateTime(Pub.getCurrentDate());
            dao.orderTypeRuleUpdate(vo);
            atx.setOutMsg(vo.getRowXml(), "修改成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    /**
     * 订单类型规则删除
     */
    public void orderTypeRuleDelete() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String typeRuleId = Pub.val(request, "typeRuleId");
        try {
        	PtBaOrderTypeRuleVO vo = new PtBaOrderTypeRuleVO();
        	vo.setTyperuleId(typeRuleId);
        	dao.orderTypeRuleDelete(vo);
            atx.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
}
