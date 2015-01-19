package com.org.dms.action.part.storageMng.stockOutMng;

import java.util.HashMap;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.storageMng.stockOutMng.RealSaleOutDao;
import com.org.dms.vo.part.PtBuDealerStockChangeVO;
import com.org.dms.vo.part.PtBuDealerStockVO;
import com.org.dms.vo.part.PtBuRealSaleDtlVO;
import com.org.dms.vo.part.PtBuRealSaleVO;
import com.org.frameImpl.Constant;
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
 * 实销出库  
 *
 * @user : 王晶鑫
 */
public class RealSaleOutAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger("SaleStockOutMngAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private RealSaleOutDao dao = RealSaleOutDao.getInstance(atx);

    /**
     * 查询实销单
     * @throws Exception
     */
    public void realSalesearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request,page);
        try {
        	HashMap<String, String> hm = RequestUtil.getValues(request);
        	String partCode = hm.get("PART_CODE");
            BaseResultSet bs = dao.realSalesearch(page,user,conditions,partCode);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 实销单修改
     * @throws Exception
     */
    public void updateRealSale() throws Exception {
        try {
            PtBuRealSaleVO ptBuRealSaleVO = new PtBuRealSaleVO();
            RequestWrapper request = atx.getRequest();
            HashMap<String,String> hm;
            hm = RequestUtil.getValues(request);
            ptBuRealSaleVO.setValue(hm);
            dao.realSaleUpdate(ptBuRealSaleVO);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 保存实销单
     * @throws Exception
     */
    public void insertRealSale() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            PtBuRealSaleVO vo =new PtBuRealSaleVO();
            HashMap<String,String> hm;
            hm = RequestUtil.getValues(request);
            vo.setValue(hm);
            vo.setSaleNo(PartOddNumberUtil.getRealSaleOutOrderNo(atx.getDBFactory(), user.getOrgDept().getOrgType(),user.getOrgCode()));
            vo.setCompanyId(user.getCompanyId());
            vo.setOrgId(user.getOrgId());
            vo.setOemCompanyId(user.getOemCompanyId());
            vo.setCreateUser(user.getAccount());
            vo.setCreateTime(Pub.getCurrentDate());
            vo.setSaleStatus(DicConstant.SXDZT_01);
            vo.setOutType(DicConstant.SXLX_01);
            vo.setStatus(Constant.YXBS_01);
            dao.insertRealSale(vo);
            atx.setOutMsg(vo.getXml(), "实销单保存成功");
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 保存实销单明细
     * @throws Exception
     */
    public void realSaleDtlinsert() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            PtBuRealSaleDtlVO vo =new PtBuRealSaleDtlVO();
            PtBuDealerStockVO pbsVo =new PtBuDealerStockVO();
            HashMap<String,String> hm =RequestUtil.getValues(request);
          
            // 配件主键
            String saleId= hm.get("SALEID");
            String partIds = hm.get("PARTIDS");
            // 配件名称
            String partNames = hm.get("PARTNAMES");
            String partCodes = hm.get("PARTCODES");
            // 配件单价
            String prices = hm.get("PRICES");
            // 数量
            String salecounts = hm.get("SALECOUNTS");
            String units = hm.get("UNITS");
            String suppids=hm.get("SUPPLIERIDS");
            String suppcodes=hm.get("SUPPLIERCODES");
            String suppnames=hm.get("SUPPLIERNAMES");
            String occamounts=hm.get("OCCUPY_AMOUNTS");
            String avaamounts=hm.get("AVAILABLE_AMOUNTS");
            String stockids=hm.get("STOCK_IDS");
            String[] partIdArr = partIds.split(",");
            String[] partNameArr = partNames.split(",");
            String[] priceArr = prices.split(",");
            String[] countArr = salecounts.split(",");
            String[] unitArr = units.split(",");
            String[] suppIdArr = suppids.split(",");
            String[] suppCodeArr = suppcodes.split(",");
            String[] suppNameArr = suppnames.split(",");
            String[] partCodeArr = partCodes.split(",");
            String[] occamountArr = occamounts.split(",");
            String[] avaamountArr = avaamounts.split(",");
            String[] stockidArr = stockids.split(",");
            // 实销单明细
            
             for (int i = 0; i < partIdArr.length; i++) {
                 QuerySet qs =dao.realSalePartIsHave(partIdArr[i], saleId,user);
                 if(Integer.parseInt(countArr[i])<0){
                	 throw new Exception("实销出库数量不能为负数.");
                 }
                 //配件已经存在时对其进行累加，更新数量
                 if(qs.getRowCount()>0) {
                     String salecount=qs.getString(1, 1);
                     String saleamout=qs.getString(1, 2);
                     String dtlId =qs.getString(1, 3);
                     Double amoutup =Double.parseDouble(countArr[i].toString())*Double.parseDouble(priceArr[i].toString());
                     Double salecountup =Double.parseDouble(countArr[i].toString())+Double.parseDouble(salecount);
                     Double saleamoutup =amoutup+Double.parseDouble(saleamout);
                     vo.setAmount(saleamoutup.toString());
                     vo.setSaleCount(salecountup.toString());
                     vo.setDtlId(dtlId);
                     //对库存进行变更
                     Double avaiamount =Double.parseDouble(avaamountArr[i].toString())-Double.parseDouble(countArr[i].toString());
                     Double occupyamount =Double.parseDouble(occamountArr[i].toString())+Double.parseDouble(countArr[i].toString());
                     pbsVo.setAvailableAmount(avaiamount.toString());
                     pbsVo.setOccupyAmount(occupyamount.toString());
                     pbsVo.setUpdateTime(Pub.getCurrentDate());
                     pbsVo.setUpdateUser(user.getAccount());
                     pbsVo.setStockId(stockidArr[i]);
                     dao.DealerStockUpdate(pbsVo);
                     dao.realSalePartUpdate(vo);
                 } else {
                     //配件不错在时新增
                     vo.setSaleId(saleId);
                     vo.setPartId(partIdArr[i]);
                     vo.setPartName(partNameArr[i]);
                     vo.setSalePrice(priceArr[i]);
                     vo.setSaleCount(countArr[i]);
                     vo.setUnit(unitArr[i]);
                     vo.setSupplierId(suppIdArr[i]);
                     vo.setSupplierCode(suppCodeArr[i]);
                     vo.setSupplierName(suppNameArr[i]);
                     vo.setPartCode(partCodeArr[i]);
                     Double price =Double.parseDouble(priceArr[i].toString());
                     Double salecount =Double.parseDouble(countArr[i].toString());
                     Double amount =price*salecount;
                     vo.setAmount(amount.toString());
                     vo.setCreateTime(Pub.getCurrentDate());
                     vo.setCreateUser(user.getAccount());
                     //对库存更新
                     Double avaiamount =Double.parseDouble(avaamountArr[i].toString())-salecount;
                     Double occupyamount =Double.parseDouble(occamountArr[i].toString())+salecount;
                     pbsVo.setAvailableAmount(avaiamount.toString());
                     pbsVo.setOccupyAmount(occupyamount.toString());
                     pbsVo.setUpdateTime(Pub.getCurrentDate());
                     pbsVo.setUpdateUser(user.getAccount());
                     pbsVo.setStockId(stockidArr[i]);
                     dao.DealerStockUpdate(pbsVo);
                     dao.realSaleDtlInsert(vo);
                 }
             }
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询实销单
     * @throws Exception
     */
    public void realSaleDtlsearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String saleId=request.getParamValue("saleId");
        try {
            BaseResultSet bs = dao.realSaleDtlsearch(page,user,saleId);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询配件库存
     * @throws Exception
     */
    public void realSalePartsearch() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PageManager page = new PageManager();
        String conditions = RequestUtil.getConditionsWhere(request,page);
        try {
            BaseResultSet bs = dao.realSalePartsearch(page,user,conditions);
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 实销单删除
     */
//    public void realSaleDelete() throws Exception {
//        RequestWrapper request = atx.getRequest();
//        String saleId = Pub.val(request, "sale_id");
//        User user = (User) atx.getSession().get(Globals.USER_KEY);
//        try {
//            QuerySet qs =dao.realSaleDtlSearch(saleId);
//            if(qs.getRowCount()>0) {
//                // 释放库存
//                for (int i = 0 ; i < qs.getRowCount() ; i++) {
//                    String partid =qs.getString(i+1, "PART_ID");
//                    // 销售数量
//                    String saleCount = qs.getString(i+1, "SALE_COUNT");
//                    dao.dealerStock(partid, saleCount,user);
//                }
//            }
//            PtBuRealSaleVO vo = new PtBuRealSaleVO();
//            PtBuRealSaleDtlVO pdvo = new PtBuRealSaleDtlVO();
//            pdvo.setSaleId(saleId);
//            vo.setSaleId(saleId);
//            dao.realSaleDelete(vo);
//            dao.realSaleDtlDelete(pdvo);
//            atx.setOutMsg("", "删除成功！");
//        } catch (Exception e) {
//            atx.setException(e);
//            logger.error(e);
//        }
//    }

    /**
     * 实销单单条删除
     */
    public void realSaleDtlDelete() throws Exception {
        RequestWrapper request = atx.getRequest();
        String dtlId = Pub.val(request, "dtl_id");
        String partid =Pub.val(request, "part_id");
        String saleid =Pub.val(request, "sale_id");
        String supplierId = Pub.val(request, "supplierId");
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
        	if ("".equals(partid)||partid==null) {
        		throw new Exception("配件代码有误,请联系管理员.");
        	}
        	if ("".equals(supplierId)||supplierId==null) {
        		throw new Exception("配件供应商有误,请联系管理员.");
        	}
            QuerySet qs =dao.realSaleDtlSearch(saleid);
            if(qs.getRowCount()>0){
                String salecount =qs.getString(1, "SALE_COUNT");
                dao.dealerStock(partid,supplierId, salecount,user);
                PtBuRealSaleDtlVO pdvo = new PtBuRealSaleDtlVO();
                pdvo.setDtlId(dtlId);
                dao.realSaleDtlOneDelete(pdvo);
                atx.setOutMsg("", "删除成功！");
            } else {
                 atx.setOutMsg("", "无此库存，请联系系统管理员！");
            }
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 实销单出库
     */
    public void realSaleOut() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        // 销售ID
        String saleId = Pub.val(request, "sale_id");
        // 出库单号
        String outNo = Pub.val(request, "out_no");

        try {
            QuerySet qs =dao.realSaleDtlSearch(saleId);
            if(qs.getRowCount()>0){
                for (int i = 0 ; i < qs.getRowCount() ; i++) {
                    String salecount =qs.getString(i+1, "SALE_COUNT");
                    String partid =qs.getString(i+1, "PART_ID");
                    dao.updateStock(partid, salecount,user.getOrgId());
                    QuerySet dealerStock=  dao.dealerStockSearch(partid, user);
                    PtBuDealerStockChangeVO pbdscVo =new PtBuDealerStockChangeVO();
                    // 库存主键
                    pbdscVo.setStockId(dealerStock.getString(1, "STOCK_ID"));
                    // 渠道ID
                    pbdscVo.setOrgId(user.getOrgId());
                    // 渠道代码
                    pbdscVo.setOrgCode(user.getOrgCode());
                    // 渠道名称
                    pbdscVo.setOrgName(user.getOrgDept().getOName());
                    // 配件主键
                    pbdscVo.setPartId(partid);
                    // 配件代码
                    pbdscVo.setPartCode(dealerStock.getString(1, "PART_CODE"));
                    // 配件名称
                    pbdscVo.setPartName(dealerStock.getString(1, "PART_NAME"));
                    // 实销数量
                    pbdscVo.setCount(salecount);
                    // 出库单号
                    pbdscVo.setOutNo(outNo);
                    // 渠道出入库类型(实销出库)
                    pbdscVo.setStorageType(DicConstant.QDCRKLX_03);
                    // 发生时间
                    pbdscVo.setApplyDate(Pub.getCurrentDate());
                    // 出入库(入库)
                    pbdscVo.setApplyType(DicConstant.CZLX_02);
                    // 创建人
                    pbdscVo.setCreateUser(user.getAccount());
                    // 创建时间
                    pbdscVo.setCreateTime(Pub.getCurrentDate());
                    // 状态(有效)
                    pbdscVo.setStatus(Constant.YXBS_01);
                    // 供应商ID
                    pbdscVo.setSupplierId(dealerStock.getString(1, "SUPPLIER_ID"));
                    // 供应商代码
                    pbdscVo.setSupplierCode(dealerStock.getString(1, "SUPPLIER_CODE"));
                    // 供应商名称
                    pbdscVo.setSupplierName(dealerStock.getString(1, "SUPPLIER_NAME"));
                    dao.StockChangeInsert(pbdscVo);
                    
                    String url = "/part/storageMng/stockOutMng/RealSaleOutAction/realSaleOut";
                    dao.insetStockDtl(salecount,"0",salecount,user,saleId,url,partid);
                }
            }
            QuerySet qsCount =dao.realSaleDtlCountSearch(saleId);
            PtBuRealSaleVO vo = new PtBuRealSaleVO();
            // 实销数量
            vo.setSaleCount(qsCount.getString(1, "SUM_SALE_COUNT"));
            // 实销金额
            vo.setSaleAmount(qsCount.getString(1, "SUM_AMOUNT"));
            // 更新日期
            vo.setUpdateTime(Pub.getCurrentDate());
            // 更新人
            vo.setUpdateUser(user.getAccount());
            // 销售主键
            vo.setSaleId(saleId);
            // 销售日期
            vo.setSaleDate(Pub.getCurrentDate());
            // 销售状态(已出库)
            vo.setSaleStatus(DicConstant.SXDZT_02);
            dao.realSaleOutUpdate(vo);
            atx.setOutMsg("", "出库完成！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
}