package com.org.dms.dao.part.salesMng.promotionMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPromotionVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 促销活动下发Dao
 *
 * @user : lichuang
 * @date : 2014-07-03
 */
public class PromotionIssueDao extends BaseDAO {
    //定义instance
    public static final PromotionIssueDao getInstance(ActionContext atx) {
        PromotionIssueDao dao = new PromotionIssueDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }


    /**
     * 下发促销活动
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean issuePromotion(PtBuPromotionVO vo) throws Exception {
        return factory.update(vo);
    }

    /**
     * 查询促销活动
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchPromotion(PageManager page, User user, String conditions) throws Exception {
        String wheres = conditions;
        wheres += " AND PBP.PROM_STATUS IN( "+ DicConstant.CXHDZT_01+","+ DicConstant.CXHDZT_02+")\n";
        wheres += " AND PBP.OEM_COMPANY_ID=" + user.getOemCompanyId()+"\n";
        wheres += " ORDER BY PBP.CREATE_TIME DESC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT PBP.PROM_ID,\n");
        sql.append("       PBP.PROM_CODE,\n");
        sql.append("       PBP.PROM_NAME,\n");
        sql.append("       PBP.PROM_TYPE,\n");
        sql.append("       PBP.START_DATE,\n");
        sql.append("       PBP.END_DATE,\n");
        sql.append("       PBP.IF_TRANS_FREE,\n");
        sql.append("       PBP.PROM_STATUS,\n");
        sql.append("       PBP.PUBLISHER,\n");
        sql.append("       PBP.PUBLISH_TIME,\n");
        sql.append("       PBP.REMARKS\n");
        sql.append("  FROM PT_BU_PROMOTION PBP\n");
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("PROM_TYPE", "CXHDLX");
        bs.setFieldDic("PROM_STATUS", "CXHDZT");
        bs.setFieldDic("IF_TRANS_FREE", "SF");
        bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("PUBLISH_TIME", "yyyy-MM-dd");
        bs.setFieldUserID("PUBLISHER");
        return bs;
    }

    /**
     * 校验配件代码是否已经添加过
     * @param promotionId 促销活动ID
     * @return
     * @throws Exception
     */
    public Boolean checkIsAddedPart(String promotionId) throws Exception{
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT 1 FROM PT_BU_PROMOTION_PART WHERE PROM_ID = "+promotionId+"\n");
        QuerySet qs = factory.select(null,sql.toString());
        if(qs.getRowCount()>0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 校验促销范围是否已经添加过
     * @param promotionId 促销活动ID
     * @return
     * @throws Exception
     */
    public Boolean checkIsAddedArea(String promotionId) throws Exception{
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT 1 FROM PT_BU_PROMOTION_AREA WHERE PROM_ID = "+promotionId+"\n");
        QuerySet qs = factory.select(null,sql.toString());
        if(qs.getRowCount()>0){
            return true;
        }else {
            return false;
        }
    }
}