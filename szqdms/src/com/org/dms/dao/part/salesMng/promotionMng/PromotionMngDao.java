package com.org.dms.dao.part.salesMng.promotionMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuPromotionAreaVO;
import com.org.dms.vo.part.PtBuPromotionPartTmpVO;
import com.org.dms.vo.part.PtBuPromotionPartVO;
import com.org.dms.vo.part.PtBuPromotionVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

/**
 * 促销活动维护Dao
 *
 * @user : lichuang
 * @date : 2014-07-03
 */
public class PromotionMngDao extends BaseDAO {
    //定义instance
    public static final PromotionMngDao getInstance(ActionContext atx) {
        PromotionMngDao dao = new PromotionMngDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 新增促销活动
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertPromotion(PtBuPromotionVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 新增促销配件
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertPromPart(PtBuPromotionPartVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 新增促销范围
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertPromArea(PtBuPromotionAreaVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 修改促销活动
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updatePromotion(PtBuPromotionVO vo) throws Exception {
        return factory.update(vo);
    }

    /**
     * 删除促销活动
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean deletePromotion(PtBuPromotionVO vo) throws Exception {
        return factory.delete(vo);
    }

    /**
     * 删除促销配件
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean deletePromoPart(PtBuPromotionPartVO vo) throws Exception {
        return factory.delete(vo);
    }

    /**
     * 删除促销配件
     *
     * @param promotionId 促销活动ID
     * @return
     * @throws Exception
     */
    public boolean deletePromoPart(String promotionId) throws Exception {
        StringBuilder sql= new StringBuilder();
        sql.append("DELETE FROM PT_BU_PROMOTION_PART WHERE PROM_ID = "+promotionId+"\n");
        return factory.delete(sql.toString(), null);
    }

    /**
     * 删除促销范围
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean deletePromoArea(PtBuPromotionAreaVO vo) throws Exception {
        return factory.delete(vo);
    }

    /**
     * 删除促销范围
     *
     * @param promotionId
     * @return
     * @throws Exception
     */
    public boolean deletePromoArea(String promotionId) throws Exception {
        StringBuilder sql= new StringBuilder();
        sql.append("DELETE FROM PT_BU_PROMOTION_AREA WHERE PROM_ID = "+promotionId+"\n");
        return factory.delete(sql.toString(), null);
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
        wheres += " AND PBP.OEM_COMPANY_ID=" + user.getOemCompanyId()+" AND PBP.PROM_STATUS = "+DicConstant.CXHDZT_01+"\n";
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
        sql.append("       PBP.ANNOUNCEMENT_NO,\n");
        sql.append("       PBP.REMARKS\n");
        sql.append("  FROM PT_BU_PROMOTION PBP\n");
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("PROM_TYPE", "CXHDLX");
        bs.setFieldDic("PROM_STATUS", "CXHDZT");
        bs.setFieldDic("IF_TRANS_FREE", "SF");
        bs.setFieldDateFormat("START_DATE", "yyyy-MM-dd");
        bs.setFieldDateFormat("END_DATE", "yyyy-MM-dd");
        return bs;
    }


    /**
     * 查询配件
     *
     * @param page
     * @param user
     * @param conditions
     * @param promotionId 促销活动ID
     * @return
     * @throws Exception
     */
    public BaseResultSet searchPart(PageManager page, User user, String conditions, String promotionId) throws Exception {
        String wheres = conditions;

        //过滤掉当前促销活动已存在的配件
//        wheres += " AND PBI.IF_BOOK = "+DicConstant.SF_01+"\n";
        wheres += " AND PBI.PART_TYPE <> "+DicConstant.PJLB_03+"\n";
        wheres += " AND PBI.PART_STATUS <> "+DicConstant.PJZT_01+"\n";
        wheres += " AND PBI.SALE_PRICE >0\n";
        wheres += " AND NOT EXISTS(SELECT 1 FROM PT_BU_PROMOTION_PART PBPP \n";
        wheres += "                          WHERE PBPP.PROM_ID = "+promotionId+"\n";
        wheres += "                          AND PBI.PART_ID = PBPP.PART_ID)\n";
        wheres += " ORDER BY PBI.PART_CODE ASC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT PBI.PART_ID,\n");
        sql.append("       PBI.PART_CODE,\n");
        sql.append("       PBI.PART_NAME,\n");
        sql.append("       PBI.PART_NO,\n");
        sql.append("       PBI.UNIT,\n");
        sql.append("       PBI.MIN_PACK,\n");
        sql.append("       PBI.MIN_UNIT,\n");
        sql.append("       PBI.SALE_PRICE\n");
        sql.append("  FROM PT_BA_INFO PBI\n");
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", "JLDW");
        bs.setFieldDic("MIN_UNIT", "JLDW");
        return bs;
    }

    /**
     * 查询办事处
     *
     * @param page
     * @param user
     * @param conditions
     * @param promotionId 促销活动ID
     * @return
     * @throws Exception
     */
    public BaseResultSet searchArea(PageManager page, User user, String conditions, String promotionId) throws Exception {
        String wheres = conditions;

        //过滤掉当前促销活动已存在的配件
        wheres += " AND ORG.ORG_TYPE = "+ DicConstant.ZZLB_08+"\n";
        wheres += " AND ORG.STATUS = "+ DicConstant.YXBS_01+"\n";
        wheres += " AND ORG.OEM_COMPANY_ID = "+user.getOemCompanyId()+"\n";
        wheres += " AND NOT EXISTS(SELECT 1 FROM PT_BU_PROMOTION_AREA PBPA\n";
        wheres += "                         WHERE PBPA.PROM_ID = "+promotionId+"\n";
        wheres += "                         AND ORG.ORG_ID = PBPA.AREA_ID)\n";
        wheres += " ORDER BY ORG.CODE ASC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ORG.ORG_ID,\n");
        sql.append("       ORG.CODE,\n");
        sql.append("       ORG.ONAME\n");;
        sql.append("  FROM TM_ORG ORG\n");
        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        return bs;
    }

    /**
     * 查询促销配件
     *
     * @param page
     * @param user
     * @param conditions
     * @param promotionId 促销活动ID
     * @return
     * @throws Exception
     */
    public BaseResultSet searchPromPart(PageManager page, User user, String conditions,String promotionId) throws Exception {
        String wheres = conditions;
        wheres += " AND PBPP.PART_ID = PBI.PART_ID\n";
        wheres += " AND PBPP.PROM_ID = "+promotionId+"\n";
        wheres += " ORDER BY PBPP.PART_CODE ASC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT PBPP.RELATION_ID,\n");
        sql.append("       PBI.PART_ID,\n");
        sql.append("       PBI.PART_CODE,\n");
        sql.append("       PBI.PART_NAME,\n");
        sql.append("       PBI.PART_NO,\n");
        sql.append("       PBI.UNIT,\n");
        sql.append("       PBI.MIN_PACK,\n");
        sql.append("       PBI.MIN_UNIT,\n");
        sql.append("       PBI.SALE_PRICE,\n");
        sql.append("       PBPP.PROM_PRICE\n");
        sql.append("  FROM PT_BU_PROMOTION_PART PBPP, PT_BA_INFO PBI\n");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", "JLDW");
        bs.setFieldDic("MIN_UNIT", "JLDW");
        return bs;
    }

    /**
     * 查询促销范围
     *
     * @param page
     * @param user
     * @param conditions
     * @param promotionId 促销活动ID
     * @return
     * @throws Exception
     */
    public BaseResultSet searchPromArea(PageManager page, User user, String conditions,String promotionId) throws Exception {
        String wheres = conditions;
        wheres += " AND PBPA.PROM_ID = "+promotionId+"\n";
        wheres += " ORDER BY PBPA.AREA_CODE ASC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT PBPA.RELATION_ID,\n");
        sql.append("       PBPA.AREA_CODE,\n");
        sql.append("       PBPA.AREA_NAME\n");
        sql.append("  FROM PT_BU_PROMOTION_AREA PBPA\n");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        return bs;
    }

    /**
     * 删除促销配件导入临时数据
     *
     * @param sql
     * @return
     * @throws Exception
     */
    public boolean deletePromPartImpTmp(String sql) throws Exception {
        return factory.delete(sql,null);
    }

    /**
     * 新增促销配件导入临时数据
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertPromPartImpTmp(PtBuPromotionPartTmpVO vo)
            throws Exception {
        return factory.insert(vo);
    }

    /**
     * 校验配件代码是否正确
     * @param partCode 配件代码
     * @return
     * @throws Exception
     */
    public QuerySet checkPartCodeIsCorrect(String partCode) throws Exception{
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT * FROM PT_BA_INFO PBI WHERE PBI.PART_CODE = '"+partCode+"'\n");

        return factory.select(null,sql.toString());
    }

    /**
     * 校验配件代码是否已经添加过
     * @param partCode 配件代码
     * @return
     * @throws Exception
     */
    public QuerySet checkPartCodeIsAlreadyAdded(String PROM_ID,String partCode) throws Exception{
        StringBuilder sql= new StringBuilder();
        sql.append("SELECT * FROM PT_BU_PROMOTION_PART T WHERE T.PROM_ID = "+PROM_ID+" AND T.PART_CODE ='"+partCode+"'\n");

        return factory.select(null,sql.toString());
    }

    /**
     * 临时表(PT_BU_PROMOTION_PART_TMP)查询
     *
     * @param pPageManager 查询分页对象
     * @param pConditions sql条件(默认：1=1)
     * @return BaseResultSet 结果集
     * @throws Exception
     */
    public BaseResultSet searchImport(PageManager pPageManager, String pConditions) throws Exception {

        pPageManager.setFilter(pConditions);
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT \n");
        sql.append("     * \n");
        sql.append(" FROM \n");
        sql.append("     PT_BU_PROMOTION_PART_TMP \n");
        //执行方法，不需要传递conn参数
        return factory.select(sql.toString(),pPageManager);
    }

    /**
     * 导入促销配件
     * @param PROM_ID 促销活动ID
     * @param user
     * @return
     * @throws Exception
     */
    public boolean importPromPart(String PROM_ID,User user) throws Exception{
        StringBuilder sql= new StringBuilder();
        sql.append("INSERT INTO PT_BU_PROMOTION_PART\n" );
        sql.append("  (RELATION_ID,\n" );
        sql.append("   PROM_ID,\n" );
        sql.append("   PART_ID,\n" );
        sql.append("   PART_CODE,\n" );
        sql.append("   PART_NAME,\n" );
        sql.append("   PART_NO,\n" );
        sql.append("   PROM_PRICE)\n" );
        sql.append("  SELECT F_GETID(),\n" );
        sql.append("         "+PROM_ID+",\n" );
        sql.append("         T2.PART_ID,\n" );
        sql.append("         T2.PART_CODE,\n" );
        sql.append("         T2.PART_NAME,\n" );
        sql.append("         T2.PART_NO,\n" );
        sql.append("         T1.PROM_PRICE\n" );
        sql.append("    FROM PT_BU_PROMOTION_PART_TMP T1, PT_BA_INFO T2\n" );
        sql.append("   WHERE T1.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
        sql.append("     AND T1.PART_CODE = T2.PART_CODE\n");

        return factory.update(sql.toString(),null);
    }
    
    public void updatePromPrice(String RELATION_ID,String PROM_ID,String P_PRICE,User user) throws Exception {
    	StringBuffer sql = new StringBuffer();
    	sql.append("UPDATE PT_BU_PROMOTION_PART SET PROM_PRICE= "+P_PRICE+",UPDATE_USER='"+user.getAccount()+"',UPDATE_TIME=SYSDATE WHERE RELATION_ID ="+RELATION_ID+" AND PROM_ID ="+PROM_ID+"\n");
       factory.update(sql.toString(),null);
    }
}