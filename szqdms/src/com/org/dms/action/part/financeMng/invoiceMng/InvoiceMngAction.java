package com.org.dms.action.part.financeMng.invoiceMng;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.financeMng.invoiceMng.InvoiceMngDao;
import com.org.dms.vo.part.PtBuSaleInvoiceSummaryVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * 开票管理Action
 *
 * @user : lichuang
 * @date : 2014-07-14
 */
public class InvoiceMngAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "InvoiceMngAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private InvoiceMngDao dao = InvoiceMngDao.getInstance(atx);


    /**
     * 查询销售订单
     *
     * @throws Exception
     */
    public void searchSaleOrder() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchSaleOrder(page, user, conditions);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 新增开票信息
     *
     * @throws Exception
     */
    public void insertInvoice() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""

        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);

            PtBuSaleInvoiceSummaryVO vo = new PtBuSaleInvoiceSummaryVO();
            vo.setValue(hm);
            vo.setExpressStatus(DicConstant.FPYJZT_01);
            vo.setInvoiceStatus(DicConstant.KPZT_02);
            vo.setCreateTime(Pub.getCurrentDate());
            vo.setCreateUser(user.getAccount());
            dao.insertInvoice(vo);

            PtBuSaleOrderVO saleOrderVO = new PtBuSaleOrderVO();
            saleOrderVO.setOrderId(hm.get("ORDER_ID"));
            saleOrderVO.setInvoiceStatus(DicConstant.KPZT_02);
            saleOrderVO.setUpdateTime(Pub.getCurrentDate());
            saleOrderVO.setUpdateUser(user.getAccount());
            dao.updateSaleOrder(saleOrderVO);

            atx.setOutMsg("", "开票成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
}