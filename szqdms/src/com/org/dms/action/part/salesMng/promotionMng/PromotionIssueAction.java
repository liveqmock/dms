package com.org.dms.action.part.salesMng.promotionMng;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.salesMng.promotionMng.PromotionIssueDao;
import com.org.dms.vo.part.PtBuPromotionVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import org.apache.log4j.Logger;

/**
 * 促销活动下发Action
 *
 * @user : lichuang
 * @date : 2014-07-03
 */
public class PromotionIssueAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "PromotionIssueAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PromotionIssueDao dao = PromotionIssueDao.getInstance(atx);


    /**
     * 查询促销活动
     *
     * @throws Exception
     */
    public void searchPromotion() throws Exception {
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
            BaseResultSet bs = dao.searchPromotion(page, user, conditions);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }


    /**
     * 下发促销活动
     *
     * @throws Exception
     */
    public void issuePromotion() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String promotionId = Pub.val(request, "promotionId");
        try {
            if(!dao.checkIsAddedPart(promotionId)){
                throw new Exception("下发失败,请维护促销配件!");
            }
            if(!dao.checkIsAddedArea(promotionId)){
                throw new Exception("下发失败,请维护促销范围!");
            }
            PtBuPromotionVO pbpVo = new PtBuPromotionVO();
            pbpVo.setPromId(promotionId);
            pbpVo.setPromStatus(DicConstant.CXHDZT_02);
            pbpVo.setPublisher(user.getAccount());
            pbpVo.setPublishTime(Pub.getCurrentDate());
            pbpVo.setUpdateTime(Pub.getCurrentDate());
            pbpVo.setUpdateUser(user.getAccount());
            dao.issuePromotion(pbpVo);
            atx.setOutMsg("", "下发成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    
    
    /**
     * 
     * @date()2014年8月29日上午11:30:54
     * @author Administrator
     * @to_do:促销活动取消(在有效期内突然终止，人工关闭促销活动)
     * @throws Exception
     */
    public void cancelPromotion() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String promotionId = Pub.val(request, "promotionId");
        try {
            PtBuPromotionVO pbpVo = new PtBuPromotionVO();
            pbpVo.setPromId(promotionId);
            pbpVo.setPromStatus(DicConstant.CXHDZT_03);
            pbpVo.setUpdateTime(Pub.getCurrentDate());
            pbpVo.setUpdateUser(user.getAccount());
            dao.issuePromotion(pbpVo);
            atx.setOutMsg("", "取消成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
}