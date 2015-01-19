package com.org.dms.action.part.storageMng.stockMng;

import com.org.dms.dao.part.storageMng.stockMng.StockMoveDao;
import com.org.dms.vo.part.PtBuStockDtlVO;
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

import java.util.HashMap;
import java.util.Map;

/**
 * 移位管理Action
 *
 * @user : lichuang
 * @date : 2014-07-14
 */
public class StockMoveAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "StockMoveAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private StockMoveDao dao = StockMoveDao.getInstance(atx);


    /**
     * 查询库存明细
     *
     * @throws Exception
     */
    public void searchStockDtl() throws Exception {
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
            BaseResultSet bs = dao.searchStockDtl(page, user, conditions);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 移动库位
     *
     * @throws Exception
     */
    public void movePosition() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""

        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String DTL_ID = hm.get("DTL_ID");//库存明细ID
            String STOCK_ID = hm.get("STOCK_ID");//库存ID
            String PART_ID = hm.get("PART_ID");//配件ID
            String PART_CODE = hm.get("PART_CODE");//配件代码
            String PART_NAME = hm.get("PART_NAME");//配件名称
            String SUPPLIER_ID = hm.get("SUPPLIER_ID");//供应商ID
            String SUPPLIER_CODE = hm.get("SUPPLIER_CODE");//供应商代码
            String SUPPLIER_NAME = hm.get("SUPPLIER_NAME");//供应商名称
            String DES_AREA_ID = hm.get("DES_AREA_ID");//目标库区ID
            String DES_AREA_CODE = hm.get("DES_AREA_CODE");//目标库区代码
            String DES_AREA_NAME = hm.get("DES_AREA_NAME");//目标库区名称
            String DES_POSITION_ID = hm.get("DES_POSITION_ID");//目标库位ID
            String DES_POSITION_CODE = hm.get("DES_POSITION_CODE");//目标库位代码
            String DES_POSITION_NAME = hm.get("DES_POSITION_NAME");//目标库位名称
            String AMOUNT = hm.get("AMOUNT");//库位库存
            String AVAILABLE_AMOUNT = hm.get("AVAILABLE_AMOUNT");//可用库存
            String MOVE_AMOUNT = hm.get("MOVE_AMOUNT");//移动数量

            QuerySet checkLock = dao.checkLock1(PART_ID,STOCK_ID);
            String locks = checkLock.getString(1, "CODES");
            if(!"".equals(locks)&&locks!=null){
            	throw new Exception("配件"+locks+"处于库存锁定状态，不能进行入库操作");
            }
            /****校验库存锁定状态*******************************/
            QuerySet checkLock1 = dao.checkLock2(PART_ID,STOCK_ID);
            String locks2 = checkLock1.getString(1, "CODES");
            if(!"".equals(locks2)&&locks2!=null){
            	throw new Exception("配件"+locks2+"处于盘点锁定状态，不能进行入库操作");
            }
            
            PtBuStockDtlVO dtlVO = new PtBuStockDtlVO();
            dtlVO.setDtlId(DTL_ID);
            dtlVO.setAmount(String.valueOf(Integer.parseInt(AMOUNT) - Integer.parseInt(MOVE_AMOUNT)));
            dtlVO.setAvailableAmount(String.valueOf(Integer.parseInt(AVAILABLE_AMOUNT) - Integer.parseInt(MOVE_AMOUNT)));
            dtlVO.setUpdateTime(Pub.getCurrentDate());
            dtlVO.setUpdateUser(user.getAccount());
            dao.updateStockDtl(dtlVO);

            //修改或新增库存明细(该配件的目标库位等信息在库存明细中存在则更新,否则新增)
            Map<String,String> map= new HashMap<String,String>();
            map.put("DTL_ID",DTL_ID);
            map.put("STOCK_ID",STOCK_ID);
            map.put("PART_ID",PART_ID);
            map.put("PART_CODE",PART_CODE);
            map.put("PART_NAME",PART_NAME);
            map.put("PART_NAME",PART_NAME);
            map.put("SUPPLIER_ID",SUPPLIER_ID);
            map.put("SUPPLIER_CODE",SUPPLIER_CODE);
            map.put("SUPPLIER_NAME",SUPPLIER_NAME);
            map.put("DES_AREA_ID",DES_AREA_ID);
            map.put("DES_AREA_CODE",DES_AREA_CODE);
            map.put("DES_AREA_NAME",DES_AREA_NAME);
            map.put("DES_POSITION_ID",DES_POSITION_ID);
            map.put("DES_POSITION_CODE",DES_POSITION_CODE);
            map.put("DES_POSITION_NAME",DES_POSITION_NAME);
            map.put("MOVE_AMOUNT",MOVE_AMOUNT);
            dao.insertOrUpdateStockDtl(user,map);
            atx.setOutMsg("", "移位成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
}