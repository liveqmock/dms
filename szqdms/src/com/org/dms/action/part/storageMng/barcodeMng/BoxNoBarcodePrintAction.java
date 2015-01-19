package com.org.dms.action.part.storageMng.barcodeMng;

import com.org.dms.common.TwoDimensionCode;
import com.org.dms.dao.part.storageMng.barcodeMng.BoxNoBarcodePrintDao;
import com.org.dms.vo.part.PtBuScanCodeBoxnoVO;
import com.org.framework.Globals;
import com.org.framework.common.*;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import org.apache.log4j.Logger;

import java.awt.image.BufferedImage;

/**
 * 箱号条码打印Action
 *
 * @user : lichuang
 * @date : 2014-08-01
 */
public class BoxNoBarcodePrintAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "BoxNoBarcodePrintAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private BoxNoBarcodePrintDao dao = BoxNoBarcodePrintDao.getInstance(atx);

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

            String BOX_NO = Pub.val(request,"BOX_NO");

            PtBuScanCodeBoxnoVO vo = new PtBuScanCodeBoxnoVO();
            String boxId = dao.getId();
            vo.setBoxId(boxId);
            vo.setBoxNo(BOX_NO);
            dao.insertVO(vo);

            String content = BOX_NO+";"+boxId;
            TwoDimensionCode t = new TwoDimensionCode();
            BufferedImage b = t.encoderQRCode(content, "png", 10);
            request.setAttribute("bufImg", b);

            atx.setForword("/jsp/dms/oem/part/storage/barcodeMng/showPicture.jsp");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

}