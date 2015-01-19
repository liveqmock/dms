package com.org.dms.action.part.storageMng.shipMng;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.storageMng.shipMng.ShipOutDao;
import com.org.dms.vo.part.PtBuDealerStockChangeVO;
import com.org.dms.vo.part.PtBuDealerStockVO;
import com.org.dms.vo.part.PtBuOrderShipCarrierVO;
import com.org.dms.vo.part.PtBuOrderShipVO;
import com.org.dms.vo.part.PtBuSaleOrderDtlVO;
import com.org.dms.vo.part.PtBuSaleOrderVO;
import com.org.dms.vo.part.PtBuShipVanBoxRlVO;
import com.org.framework.Globals;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.fileimport.ExportManager;
import com.org.framework.fileimport.HeaderBean;
import com.org.framework.util.Pub;
import com.org.framework.util.RequestUtil;
import com.org.mvc.context.ActionContext;
import com.org.mvc.context.RequestWrapper;
import com.org.mvc.context.ResponseWrapper;

/**
 * 发运单出库Action
 *
 * @user : lichuang
 * @date : 2014-08-01
 */
public class ShipOutAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "ShipOutAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private ShipOutDao dao = ShipOutDao.getInstance(atx);


    /**
     * 查询发运单
     *
     * @throws Exception
     */
    public void searchShip() throws Exception {
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
            BaseResultSet bs = dao.searchShip(page, user, conditions);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询发运单明细
     *
     * @throws Exception
     */
    public void searchShipDtl() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String shipId = Pub.val(request, "shipId");
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchShipDtl(page, user, conditions, shipId);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 发运出库
     *
     * @throws Exception
     */
    public void shipOut() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBuOrderShipVO tempVO = new PtBuOrderShipVO();
        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String SHIP_ID = hm.get("SHIP_ID");

            if(!dao.checkAttIsUploaded(SHIP_ID,user)){
                throw new Exception("请上传装车图片附件!");
            };

            tempVO.setShipId(SHIP_ID);
            tempVO.setShipStatus(DicConstant.FYDZT_04);
            tempVO.setShipDate(Pub.getCurrentDate());
            tempVO.setUpdateUser(user.getAccount());
            tempVO.setUpdateTime(Pub.getCurrentDate());
            dao.updateShip(tempVO);

            //修改发运单对应的订单的发运状态为已发运
            dao.updateSaleOrder(SHIP_ID,user);
//            //修改发运单对应的订单的明细的发运数量
//            dao.updateSaleOrderDtl(SHIP_ID,user);
//            //将实发金额更新至订单表中
//            dao.updateSaleOrderAmount(SHIP_ID,user);
            //直营订单后续流程
            QuerySet querySet = dao.getOrderId(SHIP_ID);
            if (querySet.getRowCount() > 0) {
                for (int i=0;i<querySet.getRowCount();i++) {
                    String orderId = querySet.getString(i+1, "ORDER_ID");
                    String dirSourceOrderId = querySet.getString(i+1, "DIR_SOURCE_ORDER_ID");
                    dao.updateSourceSaleOrderDtl(orderId,dirSourceOrderId);
                    createDealerStockIn(orderId);
                }
            }
            
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(), "修改成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 
     * @date()2014年9月21日下午4:17:53
     * @author Administrator
     * @to_do:生成直营店入库
     * @param OrderId
     * @throws Exception
     */
    public void createDealerStockIn(String OrderId) throws Exception{
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        QuerySet getSaleInfo = dao.getSaleInfo(OrderId);
        String ORDER_NO = getSaleInfo.getString(1, "ORDER_NO");
        String orgId = getSaleInfo.getString(1, "ORG_ID");
        String orgCode = getSaleInfo.getString(1, "ORG_CODE");
        QuerySet getSaleDtlInfo = dao.getSaleDtlInfo(OrderId);//获取销售单明细表信息
        
        for(int i=1;i<=getSaleDtlInfo.getRowCount();i++){
            String DETAIL_ID = getSaleDtlInfo.getString(i, "DTL_ID");
            String PART_ID = getSaleDtlInfo.getString(i, "PART_ID");
            String PART_CODE = getSaleDtlInfo.getString(i, "PART_CODE");
            String PART_NAME = getSaleDtlInfo.getString(i, "PART_NAME");
            String SUPPLIER_ID = getSaleDtlInfo.getString(i, "SUPPLIER_ID");
            String SUPPLIER_CODE = getSaleDtlInfo.getString(i, "SUPPLIER_CODE");
            String SUPPLIER_NAME = getSaleDtlInfo.getString(i, "SUPPLIER_NAME");
            String SHOULD_COUNT = getSaleDtlInfo.getString(i, "DELIVERY_COUNT");
            /****更改订单验收数量************************************************/
            PtBuSaleOrderDtlVO pbodVo = new PtBuSaleOrderDtlVO();
            pbodVo.setDtlId(DETAIL_ID);
            pbodVo.setSignCount(SHOULD_COUNT);
            pbodVo.setUpdateTime(Pub.getCurrentDate());
            pbodVo.setUpdateUser(user.getAccount());
            dao.updateInStorageOrder(pbodVo);
            /****配送中心虚拟入库************************************************/
            String stock_id ="";
            //根据partid查询配件库存
            QuerySet qsstock =dao.dealerstock(PART_ID,SUPPLIER_ID,orgId);
            PtBuDealerStockVO pbdsVo = new PtBuDealerStockVO(); //修改直营店仓库库存，此处虚拟入库
            //已经存在库存更新
            if(qsstock.getRowCount()>0){
                stock_id =qsstock.getString(1, "STOCK_ID");
                String amount =qsstock.getString(1, "AMOUNT");
                String available_amount =qsstock.getString(1, "AVAILABLE_AMOUNT");
                amount =String.valueOf(Integer.parseInt(SHOULD_COUNT)+Integer.parseInt(amount));
                available_amount=String.valueOf(Integer.parseInt(SHOULD_COUNT)+Integer.parseInt(available_amount));
                pbdsVo.setStockId(stock_id);
                pbdsVo.setAmount(amount);
                pbdsVo.setAvailableAmount(available_amount);
                pbdsVo.setUpdateUser(user.getAccount());
                pbdsVo.setUpdateTime(Pub.getCurrentDate());
                dao.updateDealerStock(pbdsVo);
            }
                // 不存在插入新的记录
            else{
                pbdsVo.setPartId(PART_ID);
                pbdsVo.setPartCode(PART_CODE);
                pbdsVo.setPartName(PART_NAME);
                pbdsVo.setSupplierId(SUPPLIER_ID);
                pbdsVo.setSupplierCode(SUPPLIER_CODE);
                pbdsVo.setSupplierName(SUPPLIER_NAME);
                pbdsVo.setOrgCode(orgCode);
                pbdsVo.setOrgId(orgId);
                pbdsVo.setAmount(SHOULD_COUNT);
                pbdsVo.setOccupyAmount("0");
                pbdsVo.setAvailableAmount(SHOULD_COUNT);
                pbdsVo.setStorageStatus(DicConstant.KCZT_01);
                //pbdsVo.setCompanyId(user.getCompanyId());
                pbdsVo.setCreateUser(user.getAccount());
                pbdsVo.setCreateTime(Pub.getCurrentDate());
                pbdsVo.setStatus(DicConstant.YXBS_01);
                pbdsVo.setOemCompanyId(user.getOemCompanyId());
                dao.orderStockInsert(pbdsVo);
                stock_id =pbdsVo.getStockId();
            }
              PtBuDealerStockChangeVO pbdscVo =new PtBuDealerStockChangeVO();
                pbdscVo.setStockId(stock_id);
                pbdscVo.setOrgId(orgId);
                pbdscVo.setOrgCode(orgCode);
                pbdscVo.setPartId(PART_ID);
                pbdscVo.setPartCode(PART_CODE);
                pbdscVo.setPartName(PART_NAME);
                pbdscVo.setCount(SHOULD_COUNT);
                pbdscVo.setApplyDate(Pub.getCurrentDate());
                pbdscVo.setApplyType(DicConstant.CZLX_01);
                pbdscVo.setSupplierId(SUPPLIER_ID);
                pbdscVo.setSupplierCode(SUPPLIER_CODE);
                pbdscVo.setSupplierName(SUPPLIER_NAME);
                //pbdscVo.setRemarks(remarks);
                pbdscVo.setCreateUser(user.getAccount());
                pbdscVo.setCreateTime(Pub.getCurrentDate());
                pbdscVo.setStatus(DicConstant.YXBS_01);
                pbdscVo.setStorageType(DicConstant.QDCRKLX_02);
                pbdscVo.setInNo(ORDER_NO);
              dao.orderStockChangeInsert(pbdscVo);
        }
        createDealerStockOut(OrderId);
    }

    /**
     * 
     * @date()2014年9月21日下午4:17:53
     * @author Administrator
     * @to_do:生成直营店出库
     * @param OrderId
     * @throws Exception
     */
    public void createDealerStockOut(String OrderId) throws Exception{
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        QuerySet getSaleInfo = dao.getSaleInfo(OrderId);//获取销售订单主表信息
        String orgId = getSaleInfo.getString(1, "ORG_ID");
        String orgCode = getSaleInfo.getString(1, "ORG_CODE");
        String sourceOrderId = getSaleInfo.getString(1, "DIR_SOURCE_ORDER_ID");//原单ID
        String sourceOrderNo = getSaleInfo.getString(1, "DIR_SOURCE_ORDER_NO");//原单单号
        QuerySet getSaleDtlInfo = dao.getSaleDtlInfo(OrderId);//获取销售单明细表信息
        
        PtBuSaleOrderVO ptBuSaleOrderVO1 = new PtBuSaleOrderVO();
        ptBuSaleOrderVO1.setOrderId(OrderId);
        // 修改销售订单表
        dao.updateSourceSaleOrder(ptBuSaleOrderVO1,user);
        
        PtBuSaleOrderVO ptBuSaleOrderVO = new PtBuSaleOrderVO();
        ptBuSaleOrderVO.setOrderId(sourceOrderId);
        // 修改销售订单表
        dao.updateSourceSaleOrder(ptBuSaleOrderVO,user);
        for (int i = 1;i<=getSaleDtlInfo.getRowCount();i++) {
            /*******获取新直营订单明细信息*********************/
            String PART_ID = getSaleDtlInfo.getString(i, "PART_ID");
            String PART_CODE = getSaleDtlInfo.getString(i, "PART_CODE");
            String PART_NAME = getSaleDtlInfo.getString(i, "PART_NAME");
            String SUPPLIER_ID = getSaleDtlInfo.getString(i, "SUPPLIER_ID");
            String SUPPLIER_CODE = getSaleDtlInfo.getString(i, "SUPPLIER_CODE");
            String SUPPLIER_NAME = getSaleDtlInfo.getString(i, "SUPPLIER_NAME");
            String SHOULD_COUNT = getSaleDtlInfo.getString(i, "DELIVERY_COUNT");
            /****如果有库存获取库存ID*************************************/
            QuerySet qsstock =dao.dealerstock(PART_ID,SUPPLIER_ID,orgId);
            System.out.println(qsstock.getRowCount());
            if(qsstock.getRowCount()>0){
                String stock_id =qsstock.getString(1, 1);
                /****获取原订单明细明细ID*************************************/
                QuerySet getSourceOrderDtl = dao.getSourceOrderDtl(sourceOrderId,PART_ID);//获取原销售单明细表信息
                /*****更改原订单明细发运数量******************/
                String DTL_ID = getSourceOrderDtl.getString(1, "DTL_ID");
                PtBuSaleOrderDtlVO dtlVO = new PtBuSaleOrderDtlVO();
                dtlVO.setDtlId(DTL_ID);
                dtlVO.setDeliveryCount(SHOULD_COUNT);
                dtlVO.setUpdateUser(user.getAccount());
                dtlVO.setUpdateTime(Pub.getCurrentDate());
                dao.updateSourceSaleOrderDtl(dtlVO);
                //stock_id =qsstock.getString(1, 1);
                String amount =qsstock.getString(1, 2);
                String available_amount =qsstock.getString(1, 3);
                amount =String.valueOf(Integer.parseInt(amount)-Integer.parseInt(SHOULD_COUNT));
                available_amount=String.valueOf(Integer.parseInt(available_amount)-Integer.parseInt(SHOULD_COUNT));
                /*****虚拟出库******************/
                PtBuDealerStockVO pbdsVo = new PtBuDealerStockVO(); //修改直营店仓库，此处虚拟出库
                pbdsVo.setStockId(stock_id);
                pbdsVo.setAmount(amount);
                pbdsVo.setAvailableAmount(available_amount);
                pbdsVo.setUpdateUser(user.getAccount());
                pbdsVo.setUpdateTime(Pub.getCurrentDate());
                dao.updateDealerStock(pbdsVo);
                
                /***** 修改配件库存服务站异动表******************/
                
                PtBuDealerStockChangeVO pbdscVo =new PtBuDealerStockChangeVO();
                  pbdscVo.setStockId(stock_id);
                  pbdscVo.setOrgId(orgId);
                  pbdscVo.setOrgCode(orgCode);
                  pbdscVo.setPartId(PART_ID);
                  pbdscVo.setPartCode(PART_CODE);
                  pbdscVo.setPartName(PART_NAME);
                  pbdscVo.setCount(SHOULD_COUNT);
                  pbdscVo.setApplyDate(Pub.getCurrentDate());
                  pbdscVo.setApplyType(DicConstant.CZLX_02);
                  pbdscVo.setSupplierId(SUPPLIER_ID);
                  pbdscVo.setSupplierCode(SUPPLIER_CODE);
                  pbdscVo.setSupplierName(SUPPLIER_NAME);
                  pbdscVo.setCreateUser(user.getAccount());
                  pbdscVo.setCreateTime(Pub.getCurrentDate());
                  pbdscVo.setStatus(DicConstant.YXBS_01);
                  pbdscVo.setStorageType(DicConstant.QDCRKLX_01);
                  pbdscVo.setOutNo(sourceOrderNo);
                dao.orderStockChangeInsert(pbdscVo);    
            }
        }
        
    }
    /**
     * 查询承运信息
     *
     * @throws Exception
     */
    public void searchCarrier() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String shipId = Pub.val(request, "shipId");
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchCarrier(page, user, conditions, shipId);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    //searchBoxNo
    public void searchBoxNo() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String VEHICLE_ID = Pub.val(request, "VEHICLE_ID");
            String SHIP_ID = Pub.val(request, "SHIP_ID");
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchBoxNo(page, user, conditions, VEHICLE_ID,SHIP_ID);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    public void insertRl() throws Exception {
        RequestWrapper request = atx.getRequest();
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            String VEHICLE_ID = hm.get("VEHICLE_ID");//采购订单ID
            String SHIPIDS = hm.get("SHIPIDS");//配件代码
            String BOXNOS = hm.get("BOXNOS");//配件名称
            String[] SHIP_ID = SHIPIDS.split(",");
            String[] BOX_NO = BOXNOS.split(",");
            
            for (int i = 0; i < BOX_NO.length; i++) {
                PtBuShipVanBoxRlVO vo = new PtBuShipVanBoxRlVO();
                vo.setBoxNo(BOX_NO[i]);
                vo.setShipId(SHIP_ID[i]);
                vo.setVehicleId(VEHICLE_ID);
                dao.insertRl(vo);
            }
            atx.setOutMsg("", "操作成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    //downloadOrder
    public void downloadOrder()throws Exception{

        RequestWrapper request = atx.getRequest();
        ResponseWrapper response= atx.getResponse();
         String orderId = Pub.val(request, "orderId");
        
        try {
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("ORDER_NO");
            hBean.setTitle("订单号");
            header.add(0,hBean);

            hBean = new HeaderBean();
            hBean.setName("PART_CODE");
            hBean.setTitle("配件代码");
            header.add(1,hBean);

            hBean = new HeaderBean();
            hBean.setName("PART_NAME");
            hBean.setTitle("配件名称");
            hBean.setWidth(50);
            header.add(2,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("UNIT");
            hBean.setTitle("单位");
            header.add(3,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("ORDER_COUNT");
            hBean.setTitle("订购数量");
            header.add(4,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("AUDIT_COUNT");
            hBean.setTitle("实发数量");
            header.add(5,hBean);
            
            hBean = new HeaderBean();
            hBean.setName("BOX_NO");
            hBean.setTitle("箱号");
            header.add(6,hBean);
            
            QuerySet querySet = dao.downloadOrder(orderId);
            ExportManager.exportFile(response.getHttpResponse(), "订单明细", header, querySet);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    public void boxDetail() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String VEHICLE_ID = Pub.val(request, "VEHICLE_ID");
            String SHIP_ID = Pub.val(request, "SHIP_ID");
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.boxDetail(page, user, conditions, VEHICLE_ID,SHIP_ID);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    
    public void getTransReceipt() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String SHIP_ID = Pub.val(request, "SHIP_ID");
            String VEHICLE_ID = Pub.val(request, "VEHICLE_ID");
                page.setPageRows(999999);
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.getTransReceipt(page, user, conditions, SHIP_ID,VEHICLE_ID);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    
    public void packedInsert() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            hm = RequestUtil.getValues(request);
            String VEHICLE_ID = hm.get("VEHICLE_ID");
            String SHIP_ID = hm.get("SHIP_ID");
            QuerySet getDelId = dao.getDelId(VEHICLE_ID,SHIP_ID);
            String DEL_ID = getDelId.getString(1, "DEL_ID");
            QuerySet getCarrierNo = dao.getCarrierNo(SHIP_ID);
            String CARRIER_CODE = getCarrierNo.getString(1, "CARRIER_CODE");
            PtBuOrderShipCarrierVO s_vo = new PtBuOrderShipCarrierVO();
            String RECEIPT_NO = PartOddNumberUtil.getReceiptNo(atx.getDBFactory(),CARRIER_CODE);
            s_vo.setDelId(DEL_ID);
            s_vo.setValue(hm);
            s_vo.setReceiptNo(RECEIPT_NO);
            s_vo.setUpdateTime(Pub.getCurrentDate());
            s_vo.setUpdateUser(user.getAccount());
            dao.updateCarrier(s_vo);
            atx.setOutMsg("", "操作成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    
    public void printPdf()throws Exception{
    	RequestWrapper request = atx.getRequest();
    	ResponseWrapper response =  atx.getResponse();
    	User user = (User) atx.getSession().get(Globals.USER_KEY);
    	String SHIP_ID = Pub.val(request, "SHIP_ID");
    	String VEHICLE_ID = Pub.val(request, "VEHICLE_ID");
    	response.getHttpResponse().reset();
    	response.getHttpResponse().setContentType("application/pdf");
    	response.getHttpResponse().setHeader("content-disposition", "filename="
    			+ new String("运输回执".getBytes("gb2312"), "ISO-8859-1") + ".pdf");

    	OutputStream out = new BufferedOutputStream(response.getHttpResponse().getOutputStream());
    	DoPrint doPrint = new DoPrint();
    	doPrint.createPdf(user,SHIP_ID,VEHICLE_ID,request.getHttpRequest(),response.getHttpResponse(),out);
    	out.close();
    }
    private class DoPrint extends PdfPageEventHelper {
    	private PdfPTable table;

    	private PdfPCell cell;

    	private Document document;
    	
    	private BaseFont bfChinese;
    	
    	private PdfTemplate total;// 总页数
    	private BaseFont helv;//分页信息中的字体
    	private int pagerFontSize=11;//分页信息中的字体大小

    	public void createPdf(User user,String SHIP_ID,String VEHICLE_ID,HttpServletRequest request,HttpServletResponse response,OutputStream out) throws Exception {
    	//设置字体
    	String fontPath = "/css/simsun.ttc";
    	fontPath = request.getSession().getServletContext().getRealPath(fontPath);
    	 bfChinese = BaseFont.createFont(""+fontPath+",0",  BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
        Font chineseFont = new Font(bfChinese, 11, Font.NORMAL);
        Font chineseFontSmall = new Font(bfChinese, 10, Font.BOLD);
        Font chineseFontSmall1 = new Font(bfChinese, 10, Font.NORMAL);
        Font chineseFontBig = new Font(bfChinese, 14, Font.BOLD);
        Font chineseFontMod = new Font(bfChinese, 13, Font.BOLD);
        Font chineseFontSuperBig = new Font(bfChinese, 16, Font.BOLD);
    	
    	 document=new Document(new Rectangle(615.79544f,514.33072f),35.866f,46.85f,66.85f,36.85f);
    	
    	try{
    		PdfWriter writer=PdfWriter.getInstance(document,out);
    		writer.setViewerPreferences(PdfWriter.PageLayoutTwoColumnLeft);
    		writer.setPageEvent(this);
    		
    		//查询发料单主信息
    		QuerySet info = dao.getShipInfo(user,SHIP_ID, VEHICLE_ID);
    		String RECEIPT_NO = info.getString(1, "RECEIPT_NO");
    		String SHIPER = info.getString(1, "SHIPER");
    		String APPLY_USER = info.getString(1, "CHECK_USER");
    		String WOOD_BOX = info.getString(1, "WOOD_BOX");
    		String PAPER_BOX = info.getString(1, "PAPER_BOX");
    		String NO_PACKED = info.getString(1, "NO_PACKED");
    		String OTHER_PACKED = info.getString(1, "OTHER_PACKED");
    		String ALL_COUNT = info.getString(1, "ALL_COUNT");
    		String EXPECT_DATE = info.getString(1, "EXPECT_DATE");
    		String SHIP_VEL_REMARKS = info.getString(1, "SHIP_VEL_REMARKS");
    		String [] EXPECT_DATES =EXPECT_DATE.split("-");
    		String printDate = info.getString(1, "PRINT_DATE");
    		String [] printDates =printDate.split("-");
    		
    		QuerySet getCarrier = dao.getCarrier(SHIP_ID);
    		String CARRIER_NAME = getCarrier.getString(1, "CARRIER_NAME");
    		String C_PHONE = getCarrier.getString(1, "C_PHONE");
    		
    		QuerySet  getOrderNo = dao.getOrderNo(SHIP_ID);
    		String ORDER_NOS = getOrderNo.getString(1, "ORDER_NOS");
    		QuerySet getDAPS = dao.getDAPS(SHIP_ID);
    		String DAPS = getDAPS.getString(1, "DAPS");
    		
    				document.open();
    				float[] widths={65.87f,65.87f,65.87f,65.87f,65.87f,65.87f,65.87f,65.87f};
    				
    				table=new PdfPTable(widths);
    				table.setTotalWidth(556.96f);
    				table.setLockedWidth(true);
    				table.setHeaderRows(3);
    			
    				cell = new PdfPCell(new Paragraph("陕西重型汽车有限公司配件运输回执",chineseFontSuperBig));
					cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
					cell.setColspan(8);
					cell.setBorder(PdfPCell.NO_BORDER);
					cell.setFixedHeight(25.0f);
					table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("回执单号："+RECEIPT_NO,chineseFontMod));
					cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
					cell.setColspan(8);
					cell.setBorder(PdfPCell.NO_BORDER);
					cell.setFixedHeight(25.0f);
					table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("发运日期："+printDates[0]+"-"+printDates[1]+"-"+printDates[2],chineseFont));
					cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
					cell.setFixedHeight(25.0f);
					cell.setBorder(PdfPCell.NO_BORDER);
					cell.setColspan(4);
					table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("预计到货日期："+EXPECT_DATES[0]+"-"+EXPECT_DATES[1]+"-"+EXPECT_DATES[2],chineseFont));
					cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
					cell.setFixedHeight(25.0f);
					cell.setBorder(PdfPCell.NO_BORDER);
					cell.setColspan(4);
					table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("发运人："+SHIPER,chineseFont));
					cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
					cell.setColspan(2);
					cell.setFixedHeight(25.0f);
					table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("发运单制作人："+APPLY_USER,chineseFont));
					cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
					cell.setFixedHeight(25.0f);
					cell.setColspan(2);
					table.addCell(cell);
					
					cell = new PdfPCell(new Paragraph("承运公司："+CARRIER_NAME,chineseFont));
					cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
					cell.setFixedHeight(25.0f);
					cell.setColspan(4);
					table.addCell(cell);
					
					String[] ghDetail1 = { "配件包装分","类与件数"};
				      PdfPTable shuTable1 = new PdfPTable(1);
				      for (int i = 0; i < ghDetail1.length; i++) {
				        PdfPCell celln2 = new PdfPCell(new Paragraph(ghDetail1[i],chineseFont));
				        if (i == 0)
				          celln2.setFixedHeight(16.045F);
				        if (i != 0) {
				          celln2.setBorder(PdfPCell.NO_BORDER);
				        }
				        celln2.setNoWrap(false);
				        celln2.setHorizontalAlignment(1);
				        celln2.setBorder(PdfPCell.NO_BORDER);
				        shuTable1.addCell(celln2);
				      }
				      PdfPCell shuCell1 = new PdfPCell(shuTable1);
				      shuCell1.setColspan(1);
				      table.addCell(shuCell1);

				      float[] widthsNest = { 72.97f, 70.97f, 70.97f,70.97f,72.97f};
				      
				      PdfPTable tableNest = new PdfPTable(widthsNest);
				      tableNest.setTotalWidth(349.35f);
				      tableNest.setLockedWidth(true);
				      
				      PdfPCell cell2 = new PdfPCell(new Paragraph("木箱", chineseFont));
				      cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				      cell2.setFixedHeight(16.045F);
				      cell2.setColspan(1);
				      tableNest.addCell(cell2);
				      
				      cell2 = new PdfPCell(new Paragraph("纸箱", chineseFont));
				      cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				      cell2.setFixedHeight(16.045F);
				      cell2.setColspan(1);
				      tableNest.addCell(cell2);
					
				      cell2 = new PdfPCell(new Paragraph("无包装", chineseFont));
				      cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				      cell2.setFixedHeight(16.045F);
				      cell2.setColspan(1);
				      cell2.setHorizontalAlignment(0);
				      tableNest.addCell(cell2);
				      
				      cell2 = new PdfPCell(new Paragraph("其他", chineseFont));
				      cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				      cell2.setFixedHeight(16.045F);
				      cell2.setColspan(1);
				      tableNest.addCell(cell2);
				      
				      cell2 = new PdfPCell(new Paragraph("合计", chineseFont));
				      cell2.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				      cell2.setFixedHeight(16.045F);
				      cell2.setColspan(1);
				      tableNest.addCell(cell2);
				      
				      PdfPCell cell3 = new PdfPCell(new Paragraph(WOOD_BOX, chineseFont));
				      cell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				      cell3.setFixedHeight(16.045F);
				      cell3.setColspan(1);
				      tableNest.addCell(cell3);
				      
				      cell3 = new PdfPCell(new Paragraph(PAPER_BOX, chineseFont));
				      cell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				      cell3.setFixedHeight(16.045F);
				      cell3.setColspan(1);
				      tableNest.addCell(cell3);
					
				      cell3 = new PdfPCell(new Paragraph(NO_PACKED, chineseFont));
				      cell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				      cell3.setFixedHeight(16.045F);
				      cell3.setColspan(1);
				      tableNest.addCell(cell3);
				      
				      cell3 = new PdfPCell(new Paragraph(OTHER_PACKED, chineseFont));
				      cell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				      cell3.setFixedHeight(16.045F);
				      cell3.setColspan(1);
				      tableNest.addCell(cell3);
				      
				      cell3 = new PdfPCell(new Paragraph(ALL_COUNT, chineseFont));
				      cell3.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
				      cell3.setFixedHeight(16.045F);
				      cell3.setColspan(1);
				      tableNest.addCell(cell3);
					
				      PdfPCell ceel = new PdfPCell(tableNest);
				      ceel.setColspan(5);
				      table.addCell(ceel);
				      
				      cell = new PdfPCell(new Paragraph("备注:"+SHIP_VEL_REMARKS,chineseFont));
					  cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
					  cell.setFixedHeight(35.0f);
					  cell.setColspan(2);
					  table.addCell(cell);
				      
				      String[] ghDetail0 = DAPS.split(",");
 				      PdfPTable shuTable0 = new PdfPTable(1);
				      for (int i = 0; i < ghDetail0.length; i++) {
				        PdfPCell celln0 = new PdfPCell(new Paragraph(ghDetail0[i],chineseFontSmall));
				        celln0.setBorder(PdfPCell.NO_BORDER);
				        celln0.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				        shuTable0.addCell(celln0);
				      }
				      PdfPCell shuCell0 = new PdfPCell(shuTable0);
				      shuCell0.setColspan(5);
				      table.addCell(shuCell0);
				      
				      String[] ghDetail2 = ORDER_NOS.split(",");
 				      PdfPTable shuTable3 = new PdfPTable(1);
				      shuTable3.setTotalWidth(65.87f);
				      for (int i = 0; i < ghDetail2.length; i++) {
				    	  if(i%2==0){
				    		  if(i!=ghDetail2.length-1){
				    			  PdfPCell celln2 = new PdfPCell(new Paragraph(ghDetail2[i]+" "+ghDetail2[i+1],chineseFontSmall1));
							        celln2.setFixedHeight(15.045F);
							        celln2.setNoWrap(false);
							        celln2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
							        celln2.setBorder(PdfPCell.NO_BORDER);
							        shuTable3.addCell(celln2);
				    		  }else{
				    			  PdfPCell celln2 = new PdfPCell(new Paragraph(ghDetail2[i],chineseFontSmall1));
							        celln2.setFixedHeight(15.045F);
							        celln2.setNoWrap(false);
							        celln2.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
							        celln2.setBorder(PdfPCell.NO_BORDER);
							        shuTable3.addCell(celln2);
				    		  }
		    			  
				    	  }
				        
				      }
				      PdfPCell shuCell3 = new PdfPCell(shuTable3);
				      shuCell3.setColspan(3);
				      table.addCell(shuCell3);
					
					
			        PdfPTable shuTable4 = new PdfPTable(1);
				        PdfPCell celln3A = new PdfPCell(new Paragraph("承运人签字：              ",chineseFont));
				        celln3A.setFixedHeight(25.0f);
				        celln3A.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				        celln3A.setBorder(PdfPCell.NO_BORDER);
				        celln3A.setNoWrap(false);
				        shuTable4.addCell(celln3A);
				        
				        PdfPCell celln3B = new PdfPCell(new Paragraph("",chineseFont));
				        celln3B.setFixedHeight(25.0f);
				        celln3B.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				        celln3B.setBorder(PdfPCell.NO_BORDER);
				        celln3B.setNoWrap(false);
				        shuTable4.addCell(celln3B);
				        
				        PdfPCell celln3C = new PdfPCell(new Paragraph("      年 月 日 ",chineseFont));
				        celln3C.setFixedHeight(25.0f);
				        celln3C.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				        celln3C.setNoWrap(false);
				        celln3C.setBorder(PdfPCell.NO_BORDER);
				        shuTable4.addCell(celln3C);
				        
				        PdfPCell celln3D = new PdfPCell(new Paragraph("司机电话："+C_PHONE,chineseFont));
				        celln3D.setFixedHeight(25.0f);
				        celln3D.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				        celln3D.setNoWrap(false);
				        celln3D.setBorder(PdfPCell.NO_BORDER);
				        shuTable4.addCell(celln3D);
			        
			        PdfPCell shuCell4 = new PdfPCell(shuTable4);
			        shuCell4.setColspan(2);
			        table.addCell(shuCell4);
			        
			        PdfPTable shuTable5 = new PdfPTable(1);
				        PdfPCell celln4A = new PdfPCell(new Paragraph("收货人签字盖章：",chineseFont));
				        celln4A.setFixedHeight(25.0f);
				        celln4A.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				        celln4A.setBorder(PdfPCell.NO_BORDER);
				        celln4A.setNoWrap(false);
				        shuTable5.addCell(celln4A);
				        
				        PdfPCell celln4B = new PdfPCell(new Paragraph("",chineseFont));
				        celln4B.setFixedHeight(25.0f);
				        celln4B.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
				        celln4B.setBorder(PdfPCell.NO_BORDER);
				        celln4B.setNoWrap(false);
				        shuTable5.addCell(celln4B);
				        
				        PdfPCell celln4C = new PdfPCell(new Paragraph("",chineseFont));
				        celln4C.setFixedHeight(25.0f);
				        celln4C.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				        celln4C.setBorder(PdfPCell.NO_BORDER);
				        celln4C.setNoWrap(false);
				        shuTable5.addCell(celln4C);
				        
				        PdfPCell celln4D = new PdfPCell(new Paragraph("      年 月 日",chineseFont));
				        celln4D.setFixedHeight(25.0f);
				        celln4D.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
				        celln4D.setBorder(PdfPCell.NO_BORDER);
				        celln4D.setNoWrap(false);
				        shuTable5.addCell(celln4D);
			        
			        PdfPCell shuCell5 = new PdfPCell(shuTable5);
			        shuCell5.setColspan(2);
			        table.addCell(shuCell5);
			        
			        
			        cell = new PdfPCell(new Paragraph("到货情况说明：",chineseFont));
					cell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
					cell.setFixedHeight(65.0f);
					cell.setColspan(4);
					table.addCell(cell);
					
    				document.add(table);
    			
    		
    	}catch(Exception e){
    		throw new Exception(e);
    	}
    	finally{
    		document.close();
    		out.flush();
    	}
    		
    }
    	/**
    	 * 文档关闭时调用此方法
    	 */
    	public void onCloseDocument(PdfWriter writer, Document document) {
    		total.beginText();
    		total.setFontAndSize(helv, pagerFontSize);
    		total.setTextMatrix(0, 0);
    		total.endText();
    	}

    	/**
    	 * 页面结束时调用此方法
    	 */
    	public void onEndPage(PdfWriter writer, Document document) {
    		PdfContentByte cb = writer.getDirectContent();
    		cb.saveState();
//    		String text =  String.valueOf(writer.getPageNumber());
//    		float textSize = helv.getWidthPoint(text, pagerFontSize);
//    		cb.beginText();
//    		cb.setFontAndSize(helv, pagerFontSize);
//            cb.setTextMatrix(pagerFixX, pagerFixY);
//    		cb.showText(text);
//    		cb.endText();
//    		cb.addTemplate(total, pagerFixX+textSize, pagerFixY);

    		cb.restoreState();
    	}

    	/**
    	 * 文档打开时调用此方法
    	 */
    	public void onOpenDocument(PdfWriter writer, Document document) {
    		total = writer.getDirectContent().createTemplate(114f, 18.8f);
    		total.setBoundingBox(new Rectangle(-200,-200,20, 20));
    		try {
    			helv = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI,
    					BaseFont.NOT_EMBEDDED);
    			
    			helv=bfChinese;
    		} catch (Exception e) {
    			throw new ExceptionConverter(e);
    		}
    	}

    }
    
    public void searchImport() throws Exception {
        RequestWrapper request = atx.getRequest();
        PageManager pageManager = new PageManager();
    	String conditions = RequestUtil.getConditionsWhere(request, pageManager);
        PageManager page = new PageManager();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            BaseResultSet bs = dao.searchImport(page, user,conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }
    public void confirmImport() throws Exception {

        try {
        	RequestWrapper request = atx.getRequest();
	        User user = (User) atx.getSession().get(Globals.USER_KEY);
            // 合同明细主键
            String id = Pub.val(request, "ID");
            String b[] = id.split(",");
            String VEHICLE_ID = b[0];
            String SHIP_ID = b[1];
            String tmpNo = Pub.val(request, "tmpNo");
        	String sql = "";
        	if (!"".equals(tmpNo)&&tmpNo!=null) {
        		sql = " AND TMP_NO NOT IN ("+tmpNo+") ";
        	}
            // 合同明细更新
            dao.updateForecastDtl(VEHICLE_ID,SHIP_ID,user,sql);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    public void expData()throws Exception{

        try {
            // 获取session中的user对象
            User user = (User) atx.getSession().get(Globals.USER_KEY);
        	//获取封装后的request对象
        	RequestWrapper request = atx.getRequest();
            // 定义response对象
            ResponseWrapper responseWrapper= atx.getResponse();
            // 将request流中的信息转化为where条件
            String conditions = Pub.val(request, "seqs");
            List<HeaderBean> header = new ArrayList<HeaderBean>();
            HeaderBean hBean = null;
            hBean = new HeaderBean();
            hBean.setName("BOX_NO");
            hBean.setTitle("箱号");
            header.add(0,hBean);
            QuerySet querySet = dao.expData(conditions,user);
            ExportManager.exportFile(responseWrapper.getHttpResponse(), "VelBoxRl", header, querySet);
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
}