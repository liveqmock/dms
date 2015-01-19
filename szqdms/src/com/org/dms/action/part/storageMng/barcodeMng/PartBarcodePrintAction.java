package com.org.dms.action.part.storageMng.barcodeMng;

import com.org.dms.common.TwoDimensionCode;
import com.org.dms.dao.part.storageMng.barcodeMng.PartBarcodePrintDao;
import com.org.dms.vo.part.PtBuScanCodeBarcodeVO;
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
 * 配件条码打印Action
 *
 * @user : lichuang
 * @date : 2014-08-01
 */
public class PartBarcodePrintAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "PartBarcodePrintAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PartBarcodePrintDao dao = PartBarcodePrintDao.getInstance(atx);


    /**
     * 查询配件
     *
     * @throws Exception
     */
    public void searchPart() throws Exception {
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
            BaseResultSet bs = dao.searchPart(page, user, conditions);
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

            String PART_ID = Pub.val(request, "PART_ID");
            String PART_CODE = "";
            String PART_NAME = "";
            String SUPPLIER_ID = "";
            String SUPPLIER_CODE = "";
            String SUPPLIER_NAME = "";
            String COUNTS = "";
            QuerySet qs = dao.queryPart(PART_ID);
            if(qs.getRowCount()>0){
                PART_CODE = qs.getString(1,"PART_CODE");
                PART_NAME = qs.getString(1,"PART_NAME");
                SUPPLIER_ID = qs.getString(1,"SUPPLIER_ID");
                SUPPLIER_CODE = qs.getString(1,"SUPPLIER_CODE");
                COUNTS = qs.getString(1,"MIN_PACK");
            }

            String barcode = dao.createBarcode();
            PtBuScanCodeBarcodeVO barcodeVO = new PtBuScanCodeBarcodeVO();
            barcodeVO.setBarcode(barcode);
            barcodeVO.setPartId(PART_ID);
            barcodeVO.setPartCode(PART_CODE);
            barcodeVO.setPartName(PART_NAME);
            barcodeVO.setSupplierId(SUPPLIER_ID);
            barcodeVO.setSupplierCode(SUPPLIER_CODE);
            barcodeVO.setSupplierName(SUPPLIER_NAME);
            barcodeVO.setCounts(COUNTS);
            //dao.insertPartBarcode(barcodeVO);

            String context="";
            context+="唯一码:"+barcode;
            context+="配件代码:"+PART_CODE;
            context+="配件名称:"+PART_NAME;
            context+="供应商名称:"+SUPPLIER_NAME;
            TwoDimensionCode t = new TwoDimensionCode();
            BufferedImage b = t.encoderQRCode(context, "png", 10);
            request.setAttribute("bufImg", b);

            atx.setForword("/jsp/dms/oem/part/storage/barcodeMng/showPicture.jsp");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

}