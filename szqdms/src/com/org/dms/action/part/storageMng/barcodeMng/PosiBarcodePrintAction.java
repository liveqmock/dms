package com.org.dms.action.part.storageMng.barcodeMng;

import com.org.dms.common.TwoDimensionCode;
import com.org.dms.dao.part.storageMng.barcodeMng.PosiBarcodePrintDao;
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

import java.awt.image.BufferedImage;

/**
 * 库位条码打印Action
 *
 * @user : lichuang
 * @date : 2014-08-01
 */
public class PosiBarcodePrintAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "PosiBarcodePrintAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PosiBarcodePrintDao dao = PosiBarcodePrintDao.getInstance(atx);


    /**
     * 查询库位
     *
     * @throws Exception
     */
    public void searchPosition() throws Exception {
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
            BaseResultSet bs = dao.searchPosition(page, user, conditions);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 生成条码
     *
     * @throws Exception
     */
    public void createBarcode() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            String POSITION_ID = Pub.val(request,"POSITION_ID");
            String POSITION_CODE = "";
            QuerySet qs = dao.queryPosition(POSITION_ID);
            if(qs.getRowCount()>0){
                POSITION_CODE = qs.getString(1,"POSITION_CODE");
            }
            TwoDimensionCode t = new TwoDimensionCode();
            BufferedImage b = t.encoderQRCode(POSITION_CODE, "png", 10);
            request.setAttribute("bufImg", b);

            atx.setForword("/jsp/dms/oem/part/storage/barcodeMng/showPicture.jsp");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

}