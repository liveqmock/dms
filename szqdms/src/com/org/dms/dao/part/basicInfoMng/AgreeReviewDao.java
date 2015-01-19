package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.vo.part.PtBaAgreeReviewVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.mvc.context.ActionContext;

public class AgreeReviewDao extends BaseDAO
{
    //定义instance
    public  static final AgreeReviewDao getInstance(ActionContext atx)
    {
    	AgreeReviewDao dao = new AgreeReviewDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 判断组员账号代码是否存在
     * @throws Exception
     * @Author suoxiuli 2014-07-26
     */
	public QuerySet checkUserAccount(String userAccount) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql = new StringBuffer();
    	sql.append(" select count(1) nums \n");
    	sql.append(" from pt_ba_agree_review \n");
    	sql.append(" where user_account = '" + userAccount +"'");
    	qs = factory.select(null, sql.toString());
    	return qs;
    }
	
    /**
	 * 合同评审组成员新增
	 * @throws Exception
     * @Author suoxiuli 2014-07-26
	 */
    public boolean insertAgreeReview(PtBaAgreeReviewVO vo)
            throws Exception
    {
    	return factory.insert(vo);
    }
	
    /**
	 * 合同评审组成员修改
	 * @throws Exception
     * @Author suoxiuli 2014-07-26
	 */
	public boolean updateAgreeReview(PtBaAgreeReviewVO vo) throws Exception
    {
    	return factory.update(vo);
    }

	/**
	 * 合同评审组成员查询
	 * @throws Exception
     * @Author suoxiuli 2014-07-26
	 */
    public BaseResultSet search(PageManager page, String conditions) throws Exception
    {
    	String wheres = conditions;
        //wheres += " and status="+DicConstant.YXBS_01+"  ";
		wheres += " order by create_time desc";
		page.setFilter(wheres);
    	
    	BaseResultSet bs = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" select review_id,user_account,user_name,review_type,status,secret_level,create_user,create_time ");
    	sql.append(" from pt_ba_agree_review ");
    	

    	bs = factory.select(sql.toString(), page);
    	bs.setFieldDic("STATUS","YXBS");
    	bs.setFieldDic("REVIEW_TYPE","PSZN");
    	bs.setFieldDateFormat("CREATE_TIME", "yyyy-MM-dd HH:mm:ss");
    	return bs;
    }

    /**
     * 更新合同评审组人员的有效状态
     * @throws Exception
     * @Author suoxiuli 2014-07-26
     */
    public boolean updateAgreeReviewStatus(String reviewId, String updateUser, String status) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" update pt_ba_agree_review \n");
    	sql.append(" set status = '" + status + "', \n");
    	sql.append(" update_user = '" + updateUser + "', \n");
    	sql.append(" update_time = sysdate \n");
    	sql.append(" where review_id = '" + reviewId + "' \n");
    	return factory.update(sql.toString(), null);
    }
}
