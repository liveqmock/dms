package com.org.dms.dao.service.oldpartMng;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuReturnorderNotDtlVO;
import com.org.dms.vo.service.SeBuReturnorderNotVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.SequenceUtil;
import com.org.framework.common.User;
import com.org.framework.common.dataset.DataObjImpl;
import com.org.mvc.context.ActionContext;

/**
 * 旧件不回运申请DAO
 * @author zts
 *
 */
public class OldPartNotReturnApplyDao extends BaseDAO{

	 //定义instance
	public  static final OldPartNotReturnApplyDao getInstance(ActionContext atx)
	{
		OldPartNotReturnApplyDao dao = new OldPartNotReturnApplyDao();
	    atx.setDBFactory(dao.factory);
	    return dao;
	}
	
	/**
	 * 不回运申请
	 * @param page
	 * @param condition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet oldPartNotReturnSearch(PageManager page,String conditions,User user,String checkDate)throws Exception{
		String wheres = "C.CLAIM_STATUS IN ("+DicConstant.SPDZT_05+") \n"+ //索赔单状态
    				" AND  TO_CHAR(C.CHECKPASS_DATE,'yyyy-mm') LIKE '%"+checkDate+"%'\n"+ 
    				" AND C.ORG_ID="+user.getOrgId()+" \n"+
    		        " ORDER BY C.CLAIM_NO \n" ;
        page.setFilter(wheres);
        BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT  C.CLAIM_ID,\n" );
		sql.append("       C.CLAIM_NO,\n" );
		sql.append("       C.CLAIM_TYPE,\n" );
		sql.append("       C.CLAIM_STATUS,\n" );
		sql.append("       S.APPLY_USER,\n" );
		sql.append("       S.APPLY_DATE,\n" );
		sql.append("       S.APPLY_STATUS,\n" );
		sql.append("       S.NOTBACK_ID,\n" );
		sql.append("       S.REMARKS\n" );
		sql.append("  FROM SE_BU_CLAIM C\n" );
		sql.append("  LEFT JOIN SE_BU_RETURNORDER_NOT S\n" );
		sql.append("    ON C.CLAIM_ID = S.CLAIM_ID\n" );
	    bs=factory.select(sql.toString(), page);
	    bs.setFieldDic("CLAIM_TYPE","SPDLX");
	    bs.setFieldDic("APPLY_STATUS","BHYSQZT");
	    bs.setFieldDateFormat("APPLY_DATE","yyyy-MM-dd");
		return bs;
	}
	/**
	 * 不回运主表信息查询
	 * @param page
	 * @param condition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet searchParts(PageManager page,String conditions,User user,String nId)throws Exception{
		String wheres = conditions;
	   wheres +=  " AND T.NOTBACK_ID ="+nId+"\n"+ 
				  " AND T.STATUS ="+DicConstant.YXBS_01+"\n"+
				  " AND T.NOTBACK_ID =TD.NOTBACK_ID "+
				  " AND T.ORG_ID ="+user.getOrgId()+"\n"+
			  "ORDER BY T.NOTBACK_ID\n" ;
		page.setFilter(wheres);
		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.NOTBACK_ID,\n" );
		sql.append("	   TD.DTL_ID,\n" );
		sql.append("       T.CLAIM_ID,\n" );
		sql.append("       T.CLAIM_TYPE,\n" );
		sql.append("       T.CLAIM_NO,\n" );
		sql.append("       T.RETURN_NO,\n" );
		sql.append("       T.OFFICE_ORG_ID,\n" );
		sql.append("       T.APPLY_MONTH,\n" );
		sql.append("       T.CHECK_USER,\n" );
		sql.append("       T.CHECK_DATE,\n" );
		sql.append("       T.APPLY_STATUS,\n" );
		sql.append("       T.CHECK_REMARKS,\n" );
		sql.append("       T.APPLY_USER,\n" );
		sql.append("       T.APPLY_DATE,\n" );
		sql.append("       T.REMARKS,\n" );
		sql.append("       T.ORG_ID,\n" );
		sql.append("       T.CREATE_USER,\n" );
		sql.append("       T.CREATE_TIME,\n" );
		sql.append("       T.UPDATE_USER,\n" );
		sql.append("       T.UPDATE_TIME,\n" );
		sql.append("       T.STATUS,\n" );
		sql.append("       T.OEM_COMPANY_ID,\n" );
		sql.append("       T.SECRET_LEVEL,");
		sql.append("       TD.PART_ID,");
		sql.append("       TD.PART_CODE,");
		sql.append("       TD.PART_NAME");
		sql.append("  FROM SE_BU_RETURNORDER_NOT T,SE_BU_RETURNORDER_NOT_DTL TD");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_TYPE","SPDLX");
		bs.setFieldDic("APPLY_STATUS","BHYSQZT");
		bs.setFieldDateFormat("APPLY_DATE","yyyy-MM-dd");
		return bs;
	}
	/**
	 * 不回运旧件信息查询
	 * @param page
	 * @param condition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public BaseResultSet searchOldPart(PageManager page,String conditions,User user,String nId,String cId)throws Exception{
		BaseResultSet bs = null;
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT SP.OLD_PART_ID,\n" );
		sql.append("       SP.FAULT_PART_ID,\n" );
		sql.append("       sum(sp.OLD_PART_COUNT) SL,\n" );
		sql.append("       SP.OLD_PART_CODE,\n" );
		sql.append("       SP.OLD_PART_NAME,\n" );
		sql.append("       SP.OLD_SUPPLIER_ID,\n" );
		sql.append("       SF.FAULT_CODE,\n" );
		sql.append("       PS.SUPPLIER_CODE,\n" );
		sql.append("       PS.SUPPLIER_NAME,\n" );
		sql.append("       C.CLAIM_NO\n" );
		sql.append("  FROM SE_BU_CLAIM_FAULT SF, SE_BU_CLAIM_FAULT_PART SP,PT_BA_SUPPLIER PS,SE_BU_CLAIM C\n" );
		sql.append(" WHERE  1=1  AND SF.CLAIM_ID = SP.CLAIM_ID\n" );
		sql.append("   AND PS.SUPPLIER_ID = SP.OLD_SUPPLIER_ID\n" );
		sql.append("   AND C.CLAIM_ID = SF.CLAIM_ID\n" );
		sql.append(" AND SF.STATUS =100201\n" );
		sql.append(" AND SF.CLAIM_ID ="+cId+"\n" );
		sql.append("GROUP BY  SP.OLD_PART_ID,\n" );
		sql.append("          SP.FAULT_PART_ID,\n" );
		sql.append("          SP.OLD_PART_CODE,\n" );
		sql.append("          SP.OLD_PART_NAME,\n" );
		sql.append("          SP.OLD_SUPPLIER_ID,\n" );
		sql.append("          SF.FAULT_CODE,\n" );
		sql.append("       	  PS.SUPPLIER_CODE,\n" );
		sql.append("          PS.SUPPLIER_NAME,\n" );
		sql.append("          C.CLAIM_NO\n" );
		sql.append("ORDER BY  SP.OLD_PART_ID");
		bs=factory.select(sql.toString(), page);
		bs.setFieldDic("CLAIM_TYPE","SPDLX");
		bs.setFieldDic("APPLY_STATUS","BHYSQZT");
		bs.setFieldDateFormat("APPLY_DATE","yyyy-MM-dd");
		return bs;
	}
	/**
     * 生成活动代码
     * @param vo
     * @return
     * @throws Exception
     */
    public String getReturnNo()throws Exception
    {
    	QuerySet qs = null;
    	String date = getDateToString();
    	String num = "JJBHY";
    	if(date!=null){
			date = date.replaceAll("-", "");
			num+=date;
		}
    	StringBuffer sql = new StringBuffer();
		sql.append("SELECT max(").append("RETURN_NO").append(") as JJDM FROM ");
		sql.append( "SE_BU_RETURNORDER_NOT").append(" t");
		sql.append(" where t.").append("RETURN_NO").append(" like '%").append(num).append("%'");
		qs = factory.select(null, sql.toString());
		if(qs.getRowCount()==0){
			 num+="0001";
		}else{
			    DataObjImpl dateObj = (DataObjImpl) qs.getDataObjs().get(0);
			 	String tem  = dateObj.getString("JJDM");

			if(tem==null||"null".equals(tem)){
				num+="0001";	
			}else{
				int sz = Integer.parseInt(tem.substring(tem.length()-4, tem.length()))+1;
				//如果1位数
				if(String.valueOf(sz).length()==1){
					num=tem.substring(0, tem.length()-4)+"000"+String.valueOf(sz);
				}
				//如果2位数
				else if(String.valueOf(sz).length()==2){
					num=tem.substring(0, tem.length()-4)+"00"+String.valueOf(sz);
				}
				//如果3位数
				else if(String.valueOf(sz).length()==3){
					num=tem.substring(0, tem.length()-4)+"0"+String.valueOf(sz);
				}
				//如果4位数
				else{
				num=tem.substring(0, tem.length()-4)+String.valueOf(sz);
				}
			}
		 }
		return num;
    }
    public static String getDateToString(){
  		Date date = new Date();
  		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
  		String d1 = formatter.format(date);
  		if(null!=d1&&!"".equals(d1))
  		return d1;
  		else return null;
  	}
	//新增车型
		public boolean insertOldPartNotReturn(SeBuReturnorderNotVO vo)throws Exception{
			String id=SequenceUtil.getCommonSerivalNumber(factory);
			vo.setNotbackId(id);
	    	return factory.insert(vo);
	    }
	/**
	 * 不回运插入
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public boolean updateNotReturn(SeBuReturnorderNotVO vo)throws Exception{
		return factory.update(vo);
	}
	public QuerySet queryPartId(String claimId) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT D.PART_ID\n" );
		sql.append("  FROM SE_BU_CLAIM S, SE_BU_RETURNORDER_NOT N, SE_BU_RETURNORDER_NOT_DTL D\n" );
		sql.append(" WHERE S.CLAIM_ID = N.CLAIM_ID\n" );
		sql.append("   AND N.NOTBACK_ID = D.NOTBACK_ID");
		sql.append("    AND S.CLAIM_ID = "+claimId+"");
		return factory.select(null, sql.toString());
	}
	public QuerySet searchOldPart1(String claimId) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT SP.OLD_PART_ID,\n" );
		sql.append("       sum(sp.OLD_PART_COUNT) SL,\n" );
		sql.append("       SP.OLD_PART_CODE,\n" );
		sql.append("       SP.OLD_PART_NAME,\n" );
		sql.append("       SP.OLD_SUPPLIER_ID,\n" );
		sql.append("       SF.FAULT_CODE,\n" );
		sql.append("       PS.SUPPLIER_CODE,\n" );
		sql.append("       PS.SUPPLIER_NAME,\n" );
		sql.append("       C.CLAIM_NO\n" );
		sql.append("  FROM SE_BU_CLAIM_FAULT SF, SE_BU_CLAIM_FAULT_PART SP,PT_BA_SUPPLIER PS,SE_BU_CLAIM C\n" );
		sql.append(" WHERE  1=1  AND SF.CLAIM_ID = SP.CLAIM_ID\n" );
		sql.append("   AND PS.SUPPLIER_ID = SP.OLD_SUPPLIER_ID\n" );
		sql.append("   AND C.CLAIM_ID = SF.CLAIM_ID\n" );
		sql.append(" AND SF.STATUS =100201\n" );
		sql.append(" AND SF.CLAIM_ID ="+claimId+"\n" );
		sql.append("GROUP BY  SP.OLD_PART_ID,\n" );
		sql.append("          SP.OLD_PART_CODE,\n" );
		sql.append("          SP.OLD_PART_NAME,\n" );
		sql.append("          SP.OLD_SUPPLIER_ID,\n" );
		sql.append("          SF.FAULT_CODE,\n" );
		sql.append("       	  PS.SUPPLIER_CODE,\n" );
		sql.append("          PS.SUPPLIER_NAME,\n" );
		sql.append("          C.CLAIM_NO\n" );
		sql.append("ORDER BY  SP.OLD_PART_ID");
		return factory.select(null, sql.toString());
	}
	public QuerySet queryClaimId(String checkDate,User user) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT C.CLAIM_ID,\n" );
		sql.append("       C.CLAIM_NO,\n" );
		sql.append("       C.CLAIM_TYPE,\n" );
		sql.append("       C.CLAIM_STATUS,\n" );
		sql.append("       S.APPLY_USER,\n" );
		sql.append("       S.APPLY_DATE,\n" );
		sql.append("       S.APPLY_STATUS,\n" );
		sql.append("       S.NOTBACK_ID,\n" );
		sql.append("       S.REMARKS\n" );
		sql.append("  FROM SE_BU_CLAIM C\n" );
		sql.append("  LEFT JOIN SE_BU_RETURNORDER_NOT S\n" );
		sql.append("    ON C.CLAIM_ID = S.CLAIM_ID\n" );
		sql.append(" WHERE C.CLAIM_STATUS IN (301005)\n" );
		sql.append("   AND TO_CHAR(C.CHECKPASS_DATE,'yyyy-mm') LIKE '%"+checkDate+"%'\n" );
		sql.append("   AND NOT EXISTS (SELECT 1\n" );
		sql.append("          FROM SE_BU_RETURNORDER_NOT T\n" );
		sql.append("         WHERE C.CLAIM_ID = T.CLAIM_ID\n" );
		sql.append("           AND T.APPLY_STATUS in (303001, 303002))\n" );
		sql.append("   AND C.ORG_ID = "+user.getOrgId()+"\n" );
		sql.append(" ORDER BY C.CLAIM_NO");

		return factory.select(null, sql.toString());
	}
	public boolean insertParts(SeBuReturnorderNotDtlVO vo)throws Exception{
		return factory.insert(vo);
	}
    public boolean deleteParts(String mxids) throws Exception
    {
    	StringBuffer sql = new StringBuffer();
    	sql.append(" DELETE SE_BU_RETURNORDER_NOT_DTL A WHERE A.DTL_ID IN ("+mxids+") \n");
    	return factory.delete(sql.toString(), null);
    }
}
