package com.org.dms.dao.part.purchaseMng.purchaseOrder;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBaSupplierVO;
import com.org.dms.vo.part.PtBuSupPartPhotoVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

public class SupplierPartPhotoApplyDao extends BaseDAO{
	public  static final SupplierPartPhotoApplyDao getInstance(ActionContext atx)
    {
		SupplierPartPhotoApplyDao dao = new SupplierPartPhotoApplyDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }
	/**
	 * 
	 * @date()2014年7月16日下午2:56:52
	 * @author Administrator
	 * @to_do:订单确认查询
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet queryDtl(PageManager page,User user) throws Exception
    {
    	
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.SUPPLIER_ID,T.GET_NAME,T.GET_NO,T.GET_PHONE FROM PT_BA_SUPPLIER T WHERE T.ORG_ID = "+user.getOrgId()+"\n" );
    	bs = factory.select(sql.toString(),page);
    	return bs;
    }
	/**
	 * 
	 * @date()2014年7月16日下午2:56:52
	 * @author Administrator
	 * @to_do:订单确认查询
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet search(PageManager page, User user, String conditions) throws Exception
	{
		String wheres = conditions;
		wheres +="	AND S.SUPPLIER_ID = R.SUPPLIER_ID\n"+
				"	AND P.PART_ID = R.PART_ID\n" + 
				"   AND S.ORG_ID = "+user.getOrgId()+" "
				 + "AND S.SE_IDENTIFY = "+DicConstant.YXBS_01+"\n" + 
				"   AND R.SE_IDENTIFY = "+DicConstant.YXBS_01+""+
				"	ORDER BY P.PART_ID";
		page.setFilter(wheres);
		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT P.PART_ID,\n" );
		sql.append("       P.PART_CODE,\n" );
		sql.append("       P.PART_NAME,\n" );
		sql.append("       P.PART_TYPE,\n" );
		sql.append("       P.MIN_PACK,\n" );
		sql.append("       R.RELATION_ID,\n" );
		sql.append("       P.MIN_UNIT,\n" );
		sql.append("       S.SUPPLIER_ID,\n" );
		sql.append("       SP.APPLY_STATUS,\n" );
		sql.append("       SP.REMARKS\n" );
		sql.append("  FROM PT_BA_SUPPLIER S, PT_BA_INFO P, PT_BA_PART_SUPPLIER_RL R\n" );
		sql.append("  LEFT JOIN PT_BU_SUP_PART_PHOTO SP\n" );
		sql.append("    ON R.RELATION_ID = SP.REATION_ID");
		bs = factory.select(sql.toString(), page);
		bs.setFieldDic("PART_TYPE", DicConstant.PJLB);
		bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);
		bs.setFieldDic("APPLY_STATUS", DicConstant.GYSPJZPSC);
		return bs;
	}
	/**
	 * 
	 * @date()2014年7月16日下午2:56:52
	 * @author Administrator
	 * @to_do:订单确认查询
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet photosSearch(PageManager page, User user, String conditions) throws Exception
	{
		String wheres = conditions;
		wheres +="	AND  P.SUPPLIER_ID = S.SUPPLIER_ID AND S.SE_IDENTIFY = "+DicConstant.YXBS_01+"\n" +
				"	ORDER BY S.SUPPLIER_CODE";
		page.setFilter(wheres);
		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT DISTINCT S.SUPPLIER_ID,\n" );
		sql.append("                S.SUPPLIER_CODE,\n" );
		sql.append("                S.SUPPLIER_NAME,\n" );
		sql.append("                S.GET_NAME,\n" );
		sql.append("                S.GET_NO,\n" );
		sql.append("                S.GET_PHONE,\n" );
		sql.append("                NVL((SELECT COUNT(1)\n" );
		sql.append("                      FROM PT_BU_SUP_PART_PHOTO PO\n" );
		sql.append("                     WHERE PO.SUPPLIER_ID = S.SUPPLIER_ID\n" );
		sql.append("                       AND PO.APPLY_STATUS IN (307403, 307404)\n" );
		sql.append("                     GROUP BY PO.SUPPLIER_ID),\n" );
		sql.append("                    0) SHSL,\n" );
		sql.append("                NVL((SELECT COUNT(1)\n" );
		sql.append("                      FROM PT_BU_SUP_PART_PHOTO PO\n" );
		sql.append("                     WHERE PO.SUPPLIER_ID = S.SUPPLIER_ID\n" );
		sql.append("                       AND PO.APPLY_STATUS = 307402\n" );
		sql.append("                     GROUP BY PO.SUPPLIER_ID),\n" );
		sql.append("                    0) SQSL");
		sql.append("  FROM PT_BU_SUP_PART_PHOTO P,PT_BA_SUPPLIER S");
		bs = factory.select(sql.toString(), page);
		bs.setFieldDic("PART_TYPE", DicConstant.PJLB);
		bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);
		return bs;
	}
	/**
	 * 
	 * @date()2014年7月16日下午2:56:52
	 * @author Administrator
	 * @to_do:查询图片
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet detaileSearch(PageManager page, String supId, String conditions) throws Exception
	{
		String wheres = conditions;
		wheres +=" AND T.PART_ID = P.PART_ID\n" +
				 " AND P.REATION_ID=S.YWZJ "
				+" AND P.SUPPLIER_ID ="+supId+" "
				+" AND P.APPLY_STATUS IN ("+DicConstant.GYSPJZPSC_02+","+DicConstant.GYSPJZPSC_03+","+DicConstant.GYSPJZPSC_04+")"
				+" GROUP BY  T.PART_ID, "
				+" T.PART_CODE,"
				+" P.PHOTO_ID, "
				+" P.APPLY_STATUS, "
				+" T.PART_NAME, "
				+" T.MIN_PACK, "
				+" T.MIN_UNIT, "
				+" P.REATION_ID ";
		page.setFilter(wheres);
		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.PART_ID,\n" );
		sql.append("       T.PART_CODE,\n" );
		sql.append("       P.PHOTO_ID,\n" );
		sql.append("       P.APPLY_STATUS,\n" );
		sql.append("       T.PART_NAME,\n" );
		sql.append("       T.MIN_PACK,\n" );
		sql.append("       T.MIN_UNIT,\n" );
		sql.append("       P.REATION_ID,\n" );
		sql.append("       WM_CONCAT(S.FID) FID,\n" );
		sql.append("       WM_CONCAT(S.FJMC) FJMC,\n" );
		sql.append("       WM_CONCAT(S.WJJBS) WJJBS,\n" );
		sql.append("       WM_CONCAT(S.BLWJM) BLWJM\n" );
		sql.append("  FROM PT_BU_SUP_PART_PHOTO P, PT_BA_INFO T,FS_FILESTORE S");
		bs = factory.select(sql.toString(), page);
		bs.setFieldDic("PART_TYPE", DicConstant.PJLB);
		bs.setFieldDic("APPLY_STATUS", DicConstant.GYSPJZPSC);
		bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);
		return bs;
	}
	/**
	 * 
	 * @date()2014年7月16日下午2:56:52
	 * @author Administrator
	 * @to_do:查询图片
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet detaileSearchCheck(PageManager page, String supId, String conditions) throws Exception
	{
		String wheres = conditions;
		wheres +=" AND T.PART_ID = P.PART_ID\n" +
				" AND P.REATION_ID=S.YWZJ "
				+" AND P.SUPPLIER_ID ="+supId+" "
				+" AND P.APPLY_STATUS = "+DicConstant.GYSPJZPSC_02+" "
				+" GROUP BY  T.PART_ID, "
				+" T.PART_CODE,"
				+" P.PHOTO_ID, "
				+" P.APPLY_STATUS, "
				+" T.PART_NAME, "
				+" T.MIN_PACK, "
				+" T.MIN_UNIT, "
				+" P.REATION_ID ";
		page.setFilter(wheres);
		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.PART_ID,\n" );
		sql.append("       T.PART_CODE,\n" );
		sql.append("       P.PHOTO_ID,\n" );
		sql.append("       P.APPLY_STATUS,\n" );
		sql.append("       T.PART_NAME,\n" );
		sql.append("       T.MIN_PACK,\n" );
		sql.append("       T.MIN_UNIT,\n" );
		sql.append("       P.REATION_ID,\n" );
		sql.append("       WM_CONCAT(S.FID) FID,\n" );
		sql.append("       WM_CONCAT(S.FJMC) FJMC,\n" );
		sql.append("       WM_CONCAT(S.WJJBS) WJJBS,\n" );
		sql.append("       WM_CONCAT(S.BLWJM) BLWJM\n" );
		sql.append("  FROM PT_BU_SUP_PART_PHOTO P, PT_BA_INFO T,FS_FILESTORE S");
		bs = factory.select(sql.toString(), page);
		bs.setFieldDic("PART_TYPE", DicConstant.PJLB);
		bs.setFieldDic("APPLY_STATUS", DicConstant.GYSPJZPSC);
		bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);
		return bs;
	}
	/**
	 * 
	 * @date()2014年7月16日下午2:56:52
	 * @author Administrator
	 * @to_do:查询图片
	 * @param page
	 * @param user
	 * @param conditions
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet detaileSearchChecked(PageManager page, String supId, String conditions) throws Exception
	{
		String wheres = conditions;
		wheres +=" AND T.PART_ID = P.PART_ID\n" +
				" AND P.REATION_ID=S.YWZJ "
				+" AND P.SUPPLIER_ID ="+supId+" "
				+" AND P.APPLY_STATUS IN( "+DicConstant.GYSPJZPSC_03+","+DicConstant.GYSPJZPSC_04+") "
				+" GROUP BY  T.PART_ID, "
				+" T.PART_CODE,"
				+" P.PHOTO_ID, "
				+" P.APPLY_STATUS, "
				+" T.PART_NAME, "
				+" T.MIN_PACK, "
				+" T.MIN_UNIT, "
				+" P.REATION_ID ";
		page.setFilter(wheres);
		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.PART_ID,\n" );
		sql.append("       T.PART_CODE,\n" );
		sql.append("       P.PHOTO_ID,\n" );
		sql.append("       P.APPLY_STATUS,\n" );
		sql.append("       T.PART_NAME,\n" );
		sql.append("       T.MIN_PACK,\n" );
		sql.append("       T.MIN_UNIT,\n" );
		sql.append("       P.REATION_ID,\n" );
		sql.append("       WM_CONCAT(S.FID) FID,\n" );
		sql.append("       WM_CONCAT(S.FJMC) FJMC,\n" );
		sql.append("       WM_CONCAT(S.WJJBS) WJJBS,\n" );
		sql.append("       WM_CONCAT(S.BLWJM) BLWJM\n" );
		sql.append("  FROM PT_BU_SUP_PART_PHOTO P, PT_BA_INFO T,FS_FILESTORE S");
		bs = factory.select(sql.toString(), page);
		bs.setFieldDic("PART_TYPE", DicConstant.PJLB);
		bs.setFieldDic("APPLY_STATUS", DicConstant.GYSPJZPSC);
		bs.setFieldDic("MIN_UNIT", DicConstant.JLDW);
		return bs;
	}
    /**
     * 
     * @date()2014年7月3日 下午3:44:11
     * @user()Sonia
     * TODO
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet appendixSearch(PageManager page, User user, String relationId) throws Exception
    {
    	//定义返回结果集
    	BaseResultSet bs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.FJID,\n" );
    	sql.append("       T.FJMC,\n" );
    	sql.append("       T.YWZJ,\n" );
    	sql.append("       T.CJR CREATE_USER,\n" );
    	sql.append("       TO_CHAR(T.CJSJ, 'YYYY-MM-DD') CREATE_TIME,\n" );
    	sql.append("       T.WJJBS,\n" );
    	sql.append("       T.BLWJM,\n" );
    	sql.append("       T.FID\n" );
    	sql.append("  FROM FS_FILESTORE T\n" );
    	sql.append(" WHERE T.YWZJ = '"+relationId+"'\n" );
    	sql.append(" ORDER BY T.FJID");
    	bs = factory.select(sql.toString(), page);
    	bs.setFieldUserID("CREATE_USER");
    	return bs;
    }
  //新增配件照片表信息，并提报。
	public boolean insertPhotos(String partId,User user)throws Exception{
  		StringBuffer sql= new StringBuffer();
  		sql.append("INSERT INTO PT_BU_SUP_PART_PHOTO\n" );
  		sql.append("  (PHOTO_ID,\n" );
  		sql.append("   SUPPLIER_ID,\n" );
  		sql.append("   SUPPLIER_CODE,\n" );
  		sql.append("   SUPPLIER_NAME,\n" );
  		sql.append("   PART_ID,\n" );
  		sql.append("   PART_CODE,\n" );
  		sql.append("   PART_NAME,\n" );
  		sql.append("   REATION_ID,\n" );
  		sql.append("   APPLY_TIME,\n" );
  		sql.append("   APPLY_STATUS,\n" );
  		sql.append("   CREATE_USER,\n" );
  		sql.append("   CREATE_TIME,\n" );
  		sql.append("   STATUS)\n" );
  		sql.append("SELECT F_GETID(),\n" );
  		sql.append("       S.SUPPLIER_ID,\n" );
  		sql.append("       S.SUPPLIER_CODE,\n" );
  		sql.append("       S.SUPPLIER_NAME,\n" );
  		sql.append("       P.PART_ID,\n" );
  		sql.append("       P.PART_CODE,\n" );
  		sql.append("       P.PART_NAME,\n" );
  		sql.append("       R.RELATION_ID,\n" );
  		sql.append("       SYSDATE,\n" );
  		sql.append("       '"+DicConstant.GYSPJZPSC_02+"',\n" );
  		sql.append("       '"+user.getAccount()+"',\n" );
  		sql.append("       SYSDATE,\n" );
  		sql.append("       '"+DicConstant.YXBS_01+"'\n" );
  		sql.append(" FROM PT_BA_INFO P, PT_BA_SUPPLIER S, PT_BA_PART_SUPPLIER_RL R\n" );
  		sql.append("WHERE P.PART_ID = R.PART_ID\n" );
  		sql.append("  AND S.SUPPLIER_ID = R.SUPPLIER_ID\n" );
  		sql.append("  AND P.PART_ID = "+partId+" AND S.SE_IDENTIFY = "+DicConstant.YXBS_01+" AND R.SE_IDENTIFY = "+DicConstant.YXBS_01+"");
  		sql.append("  AND S.ORG_ID="+user.getOrgId()+"");
  		return factory.update(sql.toString(),null);
    }
  		/**
  		 * 校验配件是否添加照片信息。
  		 * @param user
  		 * @return
  		 * @throws Exception
  		 */
  		public QuerySet photosCheck(String relationId) throws Exception{
  			StringBuffer sql= new StringBuffer();
  	    	sql.append("SELECT T.FJID,\n" );
  	    	sql.append("       T.FJMC,\n" );
  	    	sql.append("       T.YWZJ,\n" );
  	    	sql.append("       T.CJR CREATE_USER,\n" );
  	    	sql.append("       TO_CHAR(T.CJSJ, 'YYYY-MM-DD') CREATE_TIME,\n" );
  	    	sql.append("       T.WJJBS,\n" );
  	    	sql.append("       T.BLWJM,\n" );
  	    	sql.append("       T.FID\n" );
  	    	sql.append("  FROM FS_FILESTORE T\n" );
  	    	sql.append(" WHERE T.YWZJ = '"+relationId+"'\n" );
  	    	sql.append(" ORDER BY T.FJID");
  			return factory.select(null, sql.toString());
  		}
  		/**
  		 * 验证本配件是否已存在供应商配件照片记录。
  		 * @param user
  		 * @return
  		 * @throws Exception
  		 */
  		public QuerySet checkPhotos(String relationId) throws Exception{
  			StringBuffer sql= new StringBuffer();
  			sql.append("SELECT T.PHOTO_ID FROM PT_BU_SUP_PART_PHOTO T WHERE T.REATION_ID = "+relationId+"\n" );
  			return factory.select(null, sql.toString());
  		}
  	/**
     * 
     * @date()2014年7月15日上午11:45:28
     * @author Administrator
     * @to_do:供应商配件图片表修改
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean updatePhoto(PtBuSupPartPhotoVO vo)
    		throws Exception
    {
    	return factory.update(vo);
    }
    public boolean updateSupplier(PtBaSupplierVO vo)throws Exception{
    	return factory.update(vo);
    }
    /**
	 * 验证本配件是否已存在供应商配件照片记录。
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public QuerySet querySupplerId(User user) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.SUPPLIER_ID FROM PT_BA_SUPPLIER T WHERE T.ORG_ID = "+user.getOrgId()+"\n" );
		return factory.select(null, sql.toString());
	}
	public QuerySet download(String conditions) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT DISTINCT S.SUPPLIER_ID,\n" );
		sql.append("                S.SUPPLIER_CODE,\n" );
		sql.append("                S.SUPPLIER_NAME,\n" );
		sql.append("                S.GET_NAME,\n" );
		sql.append("                S.GET_NO,\n" );
		sql.append("                S.GET_PHONE\n" );
		sql.append("  FROM PT_BU_SUP_PART_PHOTO P, PT_BA_SUPPLIER S\n" );
		sql.append(" WHERE "+conditions+"\n" );
		sql.append("   AND P.SUPPLIER_ID = S.SUPPLIER_ID\n" );
		sql.append("   AND S.SE_IDENTIFY = 100201\n" );
		sql.append(" ORDER BY S.SUPPLIER_CODE");

		return factory.select(null, sql.toString());
	}
	//新增配件照片表信息，并提报。
	public boolean deletePhoto(String sId,User user)throws Exception{
  		StringBuffer sql= new StringBuffer();
  		sql.append("DELETE PT_BU_SUP_PART_PHOTO T WHERE T.SUPPLIER_ID ="+sId+"");
  		return factory.delete(sql.toString(),null);
    }
}
