package com.org.dms.action.part.salesMng.promotionMng;

import com.org.dms.common.DicConstant;
import com.org.dms.dao.part.common.PartOddNumberUtil;
import com.org.dms.dao.part.salesMng.promotionMng.PromotionMngDao;
import com.org.dms.vo.part.PtBuPromotionAreaVO;
import com.org.dms.vo.part.PtBuPromotionPartVO;
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

import java.util.Date;
import java.util.HashMap;

/**
 * 促销活动维护Action
 *
 * @user : lichuang
 * @date : 2014-07-03
 */
public class PromotionMngAction {
    //日志类
    private Logger logger = com.org.framework.log.log.getLogger(
            "PromotionMngAction");
    //上下文对象
    private ActionContext atx = ActionContext.getContext();
    //定义dao对象
    private PromotionMngDao dao = PromotionMngDao.getInstance(atx);


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
            String promotionId = Pub.val(request, "promotionId");
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchPart(page, user, conditions,promotionId);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询办事处
     *
     * @throws Exception
     */
    public void searchArea() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String promotionId = Pub.val(request, "promotionId");
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchArea(page, user, conditions,promotionId);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询促销配件
     *
     * @throws Exception
     */
    public void searchPromPart() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String promotionId = Pub.val(request, "promotionId");
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchPromPart(page, user, conditions,promotionId);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 查询促销范围
     *
     * @throws Exception
     */
    public void searchPromArea() throws Exception {
        //定义request对象
        RequestWrapper request = atx.getRequest();
        //获取session中的user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //定义查询分页对象
        PageManager page = new PageManager();
        //将request流中的信息转化为where条件
        String conditions = RequestUtil.getConditionsWhere(request, page);
        try {
            String promotionId = Pub.val(request, "promotionId");
            //BaseResultSet：结果集封装对象
            BaseResultSet bs = dao.searchPromArea(page, user, conditions,promotionId);
            //输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", bs);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 新增促销活动
     *
     * @throws Exception
     */
    public void insertPromotion() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
        	
        	String PROM_NO = PartOddNumberUtil.getPromotionNo(atx.getDBFactory() );
            PtBuPromotionVO vo = new PtBuPromotionVO();
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            //将hashmap映射到vo对象中,完成匹配赋值
            vo.setValue(hm);
            vo.setPromCode(PROM_NO);
            vo.setPromStatus(DicConstant.CXHDZT_01);
            vo.setCompanyId(user.getCompanyId());
            vo.setOrgId(user.getOrgId());
            vo.setOemCompanyId(user.getOemCompanyId());
            vo.setCreateUser(user.getAccount());
            vo.setCreateTime(Pub.getCurrentDate());
            vo.setStatus(DicConstant.YXBS_01);
            //通过dao，执行插入
            dao.insertPromotion(vo);
            //返回插入结果和成功信息
            atx.setOutMsg(vo.getRowXml(), "新增成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 新增促销配件
     *
     * @throws Exception
     */
    public void insertPromPart() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String promId = hm.get("PROMID");//促销活动ID
            String partIds = hm.get("PARTIDS");//促销配件ID(逗号分隔)
            String partCodes = hm.get("PARTCODES");//促销配件代码(逗号分隔)
            String partNames = hm.get("PARTNAMES");//促销配件名称(逗号分隔)
            //String partNos = hm.get("PARTNOS");//促销配件图号(逗号分隔)
            String promPrices = hm.get("PROMPRICES");//促销配件促销价(逗号分隔)
            String[] partIdArr = partIds.split(",");
            String[] partCodeArr = partCodes.split(",");
            String[] partNameArr = partNames.split(",");
            //String[] partNoArr = partNos.split(",");
            String[] promPriceArr = promPrices.split(",");
            for (int i = 0; i < partIdArr.length; i++) {
                PtBuPromotionPartVO pbppVo = new PtBuPromotionPartVO();
                pbppVo.setPromId(promId);
                pbppVo.setPartId(partIdArr[i]);
                pbppVo.setPartCode(partCodeArr[i]);
                pbppVo.setPartName(partNameArr[i]);
                //pbppVo.setPartNo(partNoArr[i]);
                double price =  Math.floor(Double.parseDouble(promPriceArr[i]) *100+.5)/100;
                pbppVo.setPromPrice(String.valueOf(price));
                pbppVo.setCreateTime(Pub.getCurrentDate());
                pbppVo.setCreateUser(user.getAccount());
                dao.insertPromPart(pbppVo);
            }
            //返回插入结果和成功信息
            atx.setOutMsg("", "新增成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 新增促销范围
     *
     * @throws Exception
     */
    public void insertPromArea() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取封装后的response对象
        //ResponseWrapper response = atx.getResponse();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            String promId = hm.get("PROMID");//促销活动ID
            String areaIds = hm.get("AREAIDS");//促销范围ID(逗号分隔)
            String areaCodes = hm.get("AREACODES");//促销范围代码(逗号分隔)
            String areaNames = hm.get("AREANAMES");//促销范围名称(逗号分隔)
            String[] areaIdArr = areaIds.split(",");
            String[] areaCodeArr = areaCodes.split(",");
            String[] areaNameArr = areaNames.split(",");
            for (int i = 0; i < areaIdArr.length; i++) {
                PtBuPromotionAreaVO pbpaVo = new PtBuPromotionAreaVO();
                pbpaVo.setPromId(promId);
                pbpaVo.setAreaId(areaIdArr[i]);
                pbpaVo.setAreaCode(areaCodeArr[i]);
                pbpaVo.setAreaName(areaNameArr[i]);
                pbpaVo.setCreateUser(user.getAccount());
                pbpaVo.setCreateTime(Pub.getCurrentDate());
                dao.insertPromArea(pbpaVo);
            }
            //返回插入结果和成功信息
            atx.setOutMsg("", "新增成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 修改促销活动
     *
     * @throws Exception
     */
    public void updatePromotion() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        PtBuPromotionVO tempVO = new PtBuPromotionVO();
        try {
            HashMap<String, String> hm;
            //将request流转换为hashmap结构体
            hm = RequestUtil.getValues(request);
            tempVO.setValue(hm);
            tempVO.setPromStatus(DicConstant.CXHDZT_01);
            tempVO.setUpdateUser(user.getAccount());
            tempVO.setUpdateTime(Pub.getCurrentDate());
            dao.updatePromotion(tempVO);
            //返回更新结果和成功信息
            atx.setOutMsg(tempVO.getRowXml(), "修改成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除促销活动
     *
     * @throws Exception
     */
    public void deletePromotion() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String promotionId = Pub.val(request, "promotionId");
        try {
            PtBuPromotionVO delVo1 = new PtBuPromotionVO();
            delVo1.setPromId(promotionId);
            dao.deletePromotion(delVo1);

            dao.deletePromoPart(promotionId);
            dao.deletePromoArea(promotionId);

            atx.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除促销配件
     *
     * @throws Exception
     */
    public void deletePromPart() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String relationId = Pub.val(request, "relationId");
        try {
            PtBuPromotionPartVO delVo = new PtBuPromotionPartVO();
            delVo.setRelationId(relationId);
            dao.deletePromoPart(delVo);
            atx.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 删除促销范围
     *
     * @throws Exception
     */
    public void deletePromArea() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        //通过request获取页面传递的参数，对于null值通过该方法将转换为""
        String relationId = Pub.val(request, "relationId");
        try {
            PtBuPromotionAreaVO delVo = new PtBuPromotionAreaVO();
            delVo.setRelationId(relationId);
            dao.deletePromoArea(delVo);
            atx.setOutMsg("", "删除成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

    /**
     * 预测配件明细临时表查询(导入)
     * @throws Exception
     */
    public void searchImport() throws Exception {

        try {
            // 定义查询分页对象
            PageManager pageManager = new PageManager();
            //获取封装后的request对象
            RequestWrapper request = atx.getRequest();
            // 将request流中的信息转化为where条件
            String conditions = RequestUtil.getConditionsWhere(request, pageManager);
            // BaseResultSet：结果集封装对象
            BaseResultSet baseResultSet = (BaseResultSet)dao.searchImport(pageManager, conditions);
            // 输出结果集，第一个参数”bs”为固定名称，不可修改
            atx.setOutData("bs", baseResultSet);
        } catch (Exception e) {
            logger.error(e);
            atx.setException(e);
        }
    }

    /**
     * 导入促销配件
     *
     * @throws Exception
     */
    public void importPromPart() throws Exception {
        //获取封装后的request对象
        RequestWrapper request = atx.getRequest();
        //获取当前登录user对象
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        String PROM_ID = Pub.val(request, "PROM_ID");
        try {
            dao.importPromPart(PROM_ID,user);
            atx.setOutMsg("", "导入成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }
    
    public void proPriceSave() throws Exception {
        RequestWrapper request = atx.getRequest();
        User user = (User) atx.getSession().get(Globals.USER_KEY);
        HashMap<String, String> hm = RequestUtil.getValues(request);
        try {
        	
			String PROM_ID = hm.get("PRO_ID");
			String RELATION_ID = hm.get("DTL_ID");
			String P_PRICE = hm.get("N_PRICE");
            dao.updatePromPrice(RELATION_ID,PROM_ID,P_PRICE,user);
            atx.setOutMsg("", "保存成功！");
        } catch (Exception e) {
            atx.setException(e);
            logger.error(e);
        }
    }

}