package com.org.dms.dao.service.oldpartMng;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.service.SeBuClaimVO;
import com.org.dms.vo.service.SeBuReturnOrderVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.framework.common.dataset.DataObjImpl;
import com.org.mvc.context.ActionContext;

/**
 * 旧件装箱DAO
 * @author zts
 *
 */
public class OldPartPackDao extends BaseDAO{

	 //定义instance
   public  static final OldPartPackDao getInstance(ActionContext atx)
   {
	   OldPartPackDao dao = new OldPartPackDao();
       atx.setDBFactory(dao.factory);
       return dao;
   }
   /**
    * 回运单查询
    * @param page
    * @param conditions
    * @param user
    * @return
    * @throws SQLException
    */
   public BaseResultSet oldPartSearch(PageManager page ,String conditions,User user) throws SQLException{
	   String wheres = conditions;
	   wheres +=" AND O.ORDER_STATUS IN ("+DicConstant.HYDZT_01+","+DicConstant.HYDZT_04+") \n "+
			    " AND O.ORG_ID= "+user.getOrgId()+" \n"+
			    " AND O.STATUS= "+DicConstant.YXBS_01+" ";
	   page.setFilter(wheres);
	   BaseResultSet bs = null;
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT O.ORDER_ID,\n" );
	   sql.append("       O.ORDER_NO,\n" );
	   sql.append("       O.PRODUCE_DATE,\n" );
	   sql.append("       O.RETURN_DATE,\n" );
	   sql.append("       O.TRANS_TYPE,\n" );
	   sql.append("       O.ORDER_STATUS,\n" );
	   sql.append("       O.AMOUNT,\n" );
	   sql.append("		  O.REMARKS,\n" );
	   sql.append("       NVL((SELECT NVL(SUM(D.OUGHT_COUNT), 0)\n" );
	   sql.append("           FROM SE_BU_RETURNORDER_DETAIL D\n" );
	   sql.append("          WHERE D.ORDER_ID = O.ORDER_ID\n" );
	   sql.append("          GROUP BY D.ORDER_ID),\n" );
	   sql.append("         0) AS PARTCOUNT \n");
	   sql.append("  FROM SE_BU_RETURN_ORDER O");
	   bs=factory.select(sql.toString(), page);
	   bs.setFieldDic("TRANS_TYPE","HYDYSFS");
	   bs.setFieldDic("ORDER_STATUS","HYDZT");
	   bs.setFieldDateFormat("PRODUCE_DATE", "yyyy-MM");
	   bs.setFieldDateFormat("RETURN_DATE", "yyyy-MM-dd");
	   return bs;
   }
   
   
   /**
    * sql优化 回运单查询
    */
   /**
    SELECT O.ORDER_ID,
       O.ORDER_NO,
       O.PRODUCE_DATE,
       O.RETURN_DATE,
       O.TRANS_TYPE,
       O.ORG_ID ORG_CODE,
       O.ORG_ID ORG_NAME,
       O.AMOUNT,
       O.REMARKS,
       NVL((SELECT NVL(SUM(D.OUGHT_COUNT), 0)
             FROM SE_BU_RETURNORDER_DETAIL D
            WHERE D.ORDER_ID = O.ORDER_ID
            GROUP BY D.ORDER_ID),
           0) AS PARTCOUNT,
       E.SL
  FROM SE_BU_RETURN_ORDER O,
       (SELECT ORDER_ID, COUNT(1) SL
          FROM (SELECT DISTINCT COUNT(1) OVER(PARTITION BY D.ORDER_ID, D.CLAIM_ID) AS CLAIMCOUNT,
                                D.ORDER_ID
                  FROM SE_BU_RETURN_ORDER O1, SE_BU_RETURNORDER_DETAIL D
                 WHERE 1 = 1
                   AND D.ORDER_ID = O1.ORDER_ID
                   AND O1.ORDER_STATUS IN (302502))
         GROUP BY ORDER_ID) E
 WHERE 1 = 1
   AND E.ORDER_ID = O.ORDER_ID
   AND O.ORDER_STATUS IN (302502)
    **/
   /**
    * 新增回运单
    * @param vo
    * @return
    * @throws Exception
    */
   public boolean insertOldReturn(SeBuReturnOrderVO vo)throws Exception
   {
   		return factory.insert(vo);
   }
   /**
    * 修改回运单
    * @param vo
    * @return
    * @throws Exception
    */
   public boolean updateOldReturn(SeBuReturnOrderVO vo)throws Exception
   {
   		return factory.update(vo);
   }
   /**
    * 修改索赔单
    * @param vo
    * @return
    * @throws Exception
    */
   public boolean updateClaim(SeBuClaimVO vo)throws Exception
   {
	   return factory.update(vo);
   }
   
   //更新索赔单配件信息
   public boolean updateFaultPartByOrder(String orderId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("UPDATE SE_BU_CLAIM_FAULT_PART D\n" );
	   sql.append("   SET D.IF_RETURN = "+DicConstant.SF_02+"\n" );
	   sql.append(" WHERE EXISTS (SELECT 1\n" );
	   sql.append("          FROM SE_BU_RETURNORDER_DETAIL T\n" );
	   sql.append("         WHERE T.ORDER_ID = "+orderId+"\n" );
	   sql.append("           AND T.PART_ID = D.OLD_PART_ID\n" );
	   sql.append("           AND T.CLAIM_ID = D.CLAIM_ID\n" );
	   sql.append("           AND T.CLAIM_DTL_ID = D.CLAIM_DTL_ID\n" );
	   sql.append("           AND T.PROSUPPLY_ID = D.OLD_SUPPLIER_ID)");
	   return factory.update(sql.toString(), null);
   }
   //更新索赔单配件信息
   public boolean updateMissCount(String dtlId,String miss)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("UPDATE SE_BU_RETURNORDER_DETAIL D\n" );
	   sql.append("   SET D.MISS_COUNT ="+miss+", D.OLD_PART_STATUS ="+DicConstant.JJZT_04+"\n" );
	   sql.append(" WHERE D.DETAIL_ID ="+dtlId+" ");
	   return factory.update(sql.toString(), null);
   }
   /**
    * TO_DO:将索赔单中遗漏或者未返旧件状态置为缺失，并添加上响应的缺失数量。
    * @param orderId
    * @param user
    * @return
    * @throws Exception
    */
	public boolean insertMissOldPart(String orderId,User user,String produceDate)
	            throws Exception
	    {
		StringBuffer sql= new StringBuffer();
		sql.append("INSERT INTO SE_BU_RETURNORDER_DETAIL\n" );
		sql.append("  (DETAIL_ID,\n" );
		sql.append("   ORDER_ID,\n" );
		sql.append("   ORDER_NO,\n" );
		sql.append("   PART_ID,\n" );
		sql.append("   PART_CODE,\n" );
		sql.append("   PART_NAME,\n" );
		sql.append("   CLAIM_ID,\n" );
		sql.append("   CLAIM_NO,\n" );
		sql.append("   VEHICLE_ID,\n" );
		sql.append("   VIN,\n" );
		sql.append("   MODELS_CODE,\n" );
		sql.append("   MILEAGE,\n" );
		sql.append("   PART_AMOUNT,\n" );
		sql.append("   PROSUPPLY_ID,\n" );
		sql.append("   PROSUPPLY_CODE,\n" );
		sql.append("   PROSUPPLY_NAME,\n" );
		sql.append("   DUTYSUPPLY_ID,\n" );
		sql.append("   DUTYSUPPLY_CODE,\n" );
		sql.append("   DUTYSUPPLY_NAME,\n" );
		sql.append("   BROKEN_REASON,\n" );
		sql.append("   IS_MAIN,\n" );
		sql.append("   CLAIM_DTL_ID,\n" );
		sql.append("   MISS_COUNT,\n" );
		sql.append("   MEASURES,\n" );
		sql.append("   OLD_PART_STATUS)\n" );
		sql.append("  SELECT F_GETID(),\n" );
		sql.append("         "+orderId+",\n" );
		sql.append("         (SELECT SR.ORDER_NO FROM SE_BU_RETURN_ORDER SR WHERE SR.ORDER_ID ="+orderId+"), P.OLD_PART_ID,\n" );
		sql.append("         P.OLD_PART_CODE,\n" );
		sql.append("         P.OLD_PART_NAME,\n" );
		sql.append("         C.CLAIM_ID,\n" );
		sql.append("         C.CLAIM_NO,\n" );
		sql.append("         C.VEHICLE_ID,\n" );
		sql.append("         C.VIN,\n" );
		sql.append("         (SELECT V.MODELS_CODE\n" );
		sql.append("            FROM MAIN_VEHICLE V\n" );
		sql.append("           WHERE V.VEHICLE_ID = C.VEHICLE_ID),\n" );
		sql.append("         C.MILEAGE,\n" );
		sql.append("         P.OLD_PART_COUNT * P.CLAIM_UPRICE,\n" );
		sql.append("         P.OLD_SUPPLIER_ID,\n" );
		sql.append("         (SELECT PS.SUPPLIER_CODE\n" );
		sql.append("            FROM PT_BA_SUPPLIER PS\n" );
		sql.append("           WHERE PS.SUPPLIER_ID = P.OLD_SUPPLIER_ID) SUPPLIER_CODE,\n" );
		sql.append("         (SELECT PS.SUPPLIER_NAME\n" );
		sql.append("            FROM PT_BA_SUPPLIER PS\n" );
		sql.append("           WHERE PS.SUPPLIER_ID = P.OLD_SUPPLIER_ID) SUPPLIER_NAME,\n" );
		sql.append("         (SELECT R.SUPPLIER_ID\n" );
		sql.append("            FROM PT_BA_SUPPLIER R\n" );
		sql.append("           WHERE R.SUPPLIER_ID =\n" );
		sql.append("                 (DECODE(P.FAULT_TYPE,\n" );
		sql.append("                         301601,\n" );
		sql.append("                         P.OLD_SUPPLIER_ID,\n" );
		sql.append("                         (SELECT P1.OLD_SUPPLIER_ID\n" );
		sql.append("                            FROM SE_BU_CLAIM_FAULT_PART P1\n" );
		sql.append("                           WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
		sql.append("                             AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
		sql.append("                             AND P1.FAULT_TYPE = 301601)))),\n" );
		sql.append("         (SELECT R.SUPPLIER_CODE\n" );
		sql.append("            FROM PT_BA_SUPPLIER R\n" );
		sql.append("           WHERE R.SUPPLIER_ID =\n" );
		sql.append("                 (DECODE(P.FAULT_TYPE,\n" );
		sql.append("                         301601,\n" );
		sql.append("                         P.OLD_SUPPLIER_ID,\n" );
		sql.append("                         (SELECT P1.OLD_SUPPLIER_ID\n" );
		sql.append("                            FROM SE_BU_CLAIM_FAULT_PART P1\n" );
		sql.append("                           WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
		sql.append("                             AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
		sql.append("                             AND P1.FAULT_TYPE = 301601)))),\n" );
		sql.append("         (SELECT R.SUPPLIER_NAME\n" );
		sql.append("            FROM PT_BA_SUPPLIER R\n" );
		sql.append("           WHERE R.SUPPLIER_ID =\n" );
		sql.append("                 (DECODE(P.FAULT_TYPE,\n" );
		sql.append("                         301601,\n" );
		sql.append("                         P.OLD_SUPPLIER_ID,\n" );
		sql.append("                         (SELECT P1.OLD_SUPPLIER_ID\n" );
		sql.append("                            FROM SE_BU_CLAIM_FAULT_PART P1\n" );
		sql.append("                           WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
		sql.append("                             AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
		sql.append("                             AND P1.FAULT_TYPE = 301601)))),\n" );
		sql.append("         P.FAULT_REASON,\n" );
		sql.append("         P.FAULT_TYPE,\n" );
		sql.append("         P.CLAIM_DTL_ID,\n" );
		sql.append("         P.OLD_PART_COUNT,\n" );
		sql.append("         P.MEASURES,\n" );
		sql.append("         "+DicConstant.JJZT_04+"\n" );
		sql.append("    FROM SE_BU_CLAIM_FAULT_PART P,\n" );
		sql.append("         SE_BU_CLAIM            C,\n" );
		sql.append("         SE_BU_CLAIM_FAULT      F,\n" );
		sql.append("         PT_BA_INFO             I\n" );
		sql.append("   WHERE NOT EXISTS (SELECT 1\n" );
		sql.append("            FROM SE_BU_RETURNORDER_DETAIL D\n" );
		sql.append("           WHERE D.PROSUPPLY_ID = P.OLD_SUPPLIER_ID\n" );
		sql.append("             AND D.PART_ID = P.OLD_PART_ID\n" );
		sql.append("             AND D.CLAIM_ID = P.CLAIM_ID\n" );
		sql.append("             AND D.CLAIM_DTL_ID = P.CLAIM_DTL_ID ) --不包含在旧件回运明细表中存在记录的\n" );
		sql.append("     AND C.CLAIM_ID = F.CLAIM_ID\n" );
		sql.append("     AND F.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
		sql.append("     AND P.OLD_PART_ID = I.PART_ID\n" );
		sql.append("     AND P.MEASURES =300602\n" );
		sql.append("     AND C.CLAIM_STATUS = 301005\n" );
		sql.append("     AND C.CHECKPASS_DATE< ADD_MONTHS(TO_DATE('"+produceDate+"','YYYY-MM'),+1)\n" );
		sql.append("     AND I.IF_RETURN = 100101\n" );
		sql.append("     AND P.IF_RETURN = 100102\n" );
		sql.append("     AND EXISTS (SELECT 1 FROM SE_BU_RETURNORDER_DETAIL RD \n" );
		sql.append("     					 WHERE RD.CLAIM_ID = C.CLAIM_ID) --不包含没有导入到这个回运单中的索赔单\n" );
		sql.append("     AND NOT EXISTS\n" );
		sql.append("   (SELECT 1\n" );
		sql.append("            FROM SE_BU_RETURNORDER_NOT N, SE_BU_RETURNORDER_NOT_DTL DTL\n" );
		sql.append("           WHERE N.NOTBACK_ID = DTL.NOTBACK_ID\n" );
		sql.append("             AND N.CLAIM_ID = C.CLAIM_ID\n" );
		sql.append("             AND DTL.FAULT_CODE = F.FAULT_CODE\n" );
		sql.append("             AND DTL.PART_ID = P.OLD_PART_ID\n" );
		sql.append("             AND DTL.SUPPLIER_ID = P.OLD_SUPPLIER_ID\n" );
		sql.append("             AND N.APPLY_STATUS = 303002) --不包含旧件不回运申请通过的\n" );
		sql.append("     AND EXISTS (SELECT 1\n" );
		sql.append("            FROM SE_BA_RETURN_DEALER_RELATION SR\n" );
		sql.append("           WHERE SR.D_ORGID = C.ORG_ID\n" );
		sql.append("             AND SR.R_ORGID = "+user.getOrgId()+") --旧件集中点与服务站有对应关系\n" );
		sql.append("   ORDER BY P.OLD_PART_ID");
		return factory.update(sql.toString(),null);
	    }
   /**
    * 删除明细byOrderId
    * @param orderId
    * @return
    * @throws Exception
    */
   public boolean returnOldDetailDelete(String orderId) throws Exception
   {
   	StringBuffer sql= new StringBuffer();
   	sql.append("DELETE FROM SE_BU_RETURNORDER_DETAIL T\n" );
   	sql.append("WHERE T.ORDER_ID ="+orderId+"");
   	return factory.delete(sql.toString(), null);
   }
   /**
    * 删除回运单
    * @param detailIds
    * @return
    * @throws Exception
    */
   public boolean returnOldDelete(String orderId) throws Exception
   {
   	StringBuffer sql= new StringBuffer();
   	sql.append("DELETE FROM SE_BU_RETURN_ORDER T\n" );
   	sql.append("WHERE T.ORDER_ID ="+orderId+"");
   	return factory.delete(sql.toString(), null);
   }
   /***
    * 查询已回运的清单
    * @param page
    * @param orderId
    * @return
    * @throws Exception
    */
   public BaseResultSet returnPartSearch(PageManager page,String orderId,String conditions)throws Exception{
	   BaseResultSet bs = null;
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT D.DETAIL_ID,\n" );
	   sql.append("       D.PART_ID,\n" );
	   sql.append("       D.PART_CODE,\n" );
	   sql.append("       D.PART_NAME,\n" );
	   sql.append("       (SELECT G.CODE FROM TM_ORG G WHERE G.ORG_ID = C.ORG_ID) CODE,\n" );
	   sql.append("       (SELECT G.ONAME FROM TM_ORG G WHERE G.ORG_ID = C.ORG_ID) ONAME,\n" );
	   sql.append("       D.CLAIM_NO,\n" );
	   sql.append("       D.VEHICLE_ID,\n" );
	   sql.append("       D.VIN,\n" );
	   sql.append("       D.MILEAGE,\n" );
	   sql.append("       NVL(D.OUGHT_COUNT,0) OUGHT_COUNT,\n" );
	   sql.append("       NVL(D.SHOULD_COUNT,0)-NVL(D.OUGHT_COUNT,0) MISS_COUNT,\n" );
	   sql.append("       NVL(D.SHOULD_COUNT,0)SHOULD_COUNT,\n" );
	   sql.append("       D.BOX_NO,\n" );
	   sql.append("       D.PROSUPPLY_ID,\n" );
	   sql.append("       D.PROSUPPLY_CODE,\n" );
	   sql.append("       D.PROSUPPLY_NAME,\n" );
	   sql.append("       D.DUTYSUPPLY_ID,\n" );
	   sql.append("       D.DUTYSUPPLY_CODE,\n" );
	   sql.append("       D.DUTYSUPPLY_NAME,\n" );
	   sql.append("       D.BROKEN_REASON,\n" );
	   sql.append("       D.REMARKS\n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DETAIL D, SE_BU_CLAIM C, MAIN_DEALER M\n" );
	   sql.append(" WHERE "+conditions+" AND D.ORDER_ID = "+orderId+"");
	   sql.append("   AND D.CLAIM_ID = C.CLAIM_ID\n" );
	   sql.append("   AND C.ORG_ID = M.ORG_ID");
	   sql.append("   ORDER BY C.CLAIM_ID");
	   bs=factory.select(sql.toString(), page);
	   return bs;
   }
   
   /**
    * 更新故障配件是否已回运
    * @param detailIds
    * @return
    * @throws Exception
    */
   public boolean updateFaultPart(String detailIds) throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("UPDATE SE_BU_CLAIM_FAULT_PART D\n" );
	   sql.append("       SET D.IF_RETURN = "+DicConstant.SF_02+"\n" );
	   sql.append("     WHERE EXISTS (SELECT 1\n" );
	   sql.append("              FROM SE_BU_RETURNORDER_DETAIL T\n" );
	   sql.append("             WHERE T.DETAIL_ID IN ("+detailIds+")\n" );
	   sql.append("             AND T.PART_ID = D.OLD_PART_ID\n" );
	   sql.append("             AND T.CLAIM_ID=D.CLAIM_ID\n" );
	   sql.append("             AND T.CLAIM_DTL_ID=D.CLAIM_DTL_ID\n" );
	   sql.append("             AND T.PROSUPPLY_ID=D.OLD_SUPPLIER_ID )" );
	   return factory.update(sql.toString(), null);
   }
   
   /**
    * 更新故障配件的已回运数量
    * @param detailIds
    * @return
    * @throws Exception
    */
   public boolean updateFaultPartAlready(String detailIds)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("MERGE INTO SE_BU_CLAIM_FAULT_PART T2\n" );
	   sql.append("USING (SELECT D.PART_ID,\n" );
	   sql.append("              D.CLAIM_ID,\n" );
	   sql.append("              D.CLAIM_DTL_ID,\n" );
	   sql.append("              D.PROSUPPLY_ID,\n" );
	   sql.append("              D.OUGHT_COUNT\n" );
	   sql.append("         FROM SE_BU_RETURNORDER_DETAIL D\n" );
	   sql.append("        WHERE D.DETAIL_ID IN ("+detailIds+")) T1\n" );
	   sql.append("ON (T2.CLAIM_ID = T1.CLAIM_ID AND T2.CLAIM_DTL_ID = T1.CLAIM_DTL_ID AND T2.OLD_PART_ID = T1.PART_ID AND T2.OLD_SUPPLIER_ID = T1.PROSUPPLY_ID)\n" );
	   sql.append("WHEN MATCHED THEN\n" );
	   sql.append("  UPDATE\n" );
	   sql.append("     SET T2.OLD_PART_ALREADY_IN = 0");
	   return factory.update(sql.toString(), null);
   }
   /**
    * 更新故障配件的已回运数量
    * @param detailIds
    * @return
    * @throws Exception
    */
   public boolean updateFaultPartAlreadyById(String orderId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("MERGE INTO SE_BU_CLAIM_FAULT_PART T2\n" );
	   sql.append("USING (SELECT D.PART_ID,\n" );
	   sql.append("              D.CLAIM_ID,\n" );
	   sql.append("              D.CLAIM_DTL_ID,\n" );
	   sql.append("              D.PROSUPPLY_ID,\n" );
	   sql.append("              D.OUGHT_COUNT\n" );
	   sql.append("         FROM SE_BU_RETURNORDER_DETAIL D\n" );
	   sql.append("        WHERE D.ORDER_ID = "+orderId+") T1\n" );
	   sql.append("ON (T2.CLAIM_ID = T1.CLAIM_ID AND T2.CLAIM_DTL_ID = T1.CLAIM_DTL_ID AND T2.OLD_PART_ID = T1.PART_ID AND T2.OLD_SUPPLIER_ID = T1.PROSUPPLY_ID)\n" );
	   sql.append("WHEN MATCHED THEN\n" );
	   sql.append("  UPDATE\n" );
	   sql.append("     SET T2.OLD_PART_ALREADY_IN = 0");
	   return factory.update(sql.toString(), null);
   }
   
   /**
    * 删除方法
    * @param detailIds
    * @return
    * @throws Exception
    */
   public boolean detailDelete(String detailIds) throws Exception
   {
   	StringBuffer sql= new StringBuffer();
   	sql.append("DELETE FROM SE_BU_RETURNORDER_DETAIL T\n" );
   	sql.append("WHERE T.DETAIL_ID IN ("+detailIds+")");
   	return factory.delete(sql.toString(), null);
   }
   
   /**
    * 生成回运单号
    * @param vo
    * @return
    * @throws Exception
    */
   public String getOrderNo()throws Exception
   {
   	QuerySet qs = null;
   	String date = getDateToString();
   	String num = "HYD";
   	if(date!=null){
			date = date.replaceAll("-", "");
			num+=date;
		}
   	StringBuffer sql = new StringBuffer();
   	//sql.append("SELECT max(").append(numberName).append(") as DH FROM ");//变的字段
		sql.append("SELECT max(").append("ORDER_NO").append(") as DH FROM ");
		sql.append( "SE_BU_RETURN_ORDER").append(" t");
		sql.append(" where t.").append("ORDER_NO").append(" like '%").append(num).append("%'");
		qs = factory.select(null, sql.toString());
		if(qs.getRowCount()==0){
			 num+="0001";
		}else{
			    DataObjImpl dateObj = (DataObjImpl) qs.getDataObjs().get(0);
			 	String tem  = dateObj.getString("DH");

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
   /**
    * 下载回运单
    * @param produceDate
    * @return
    * @throws Exception
    */
   public QuerySet download(String produceDate,String conditions,User user) throws Exception{
	   QuerySet qs = null;
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT ROWNUM,--行号\n" );
	   sql.append("       C.CLAIM_ID, --索赔单ID\n" );
	   sql.append("       C.CLAIM_NO, --索赔单单号\n" );
	   sql.append("       C.VIN, --VIN\n" );
	   sql.append("       F.FAULT_CODE,--故障代码\n" );
	   sql.append("       V.MODELS_CODE, --车型\n" );
	   sql.append("       (SELECT G.CODE FROM TM_ORG G WHERE G.ORG_ID = C.ORG_ID) CODE,--渠道商代码\n" );
	   sql.append("       (SELECT G.ONAME FROM TM_ORG G WHERE G.ORG_ID = C.ORG_ID) ONAME, --渠道商名称\n" );
	   sql.append("       C.MILEAGE, --里程\n" );
	   sql.append("       P.OLD_PART_ID, --配件ID\n" );
	   sql.append("       P.OLD_PART_CODE, --配件代码\n" );
	   sql.append("       P.OLD_PART_NAME, --配件名称\n" );
	   sql.append("       (P.REPAY_UPRICE*P.OLD_PART_COUNT) CLAIM_COSTS, --材料费\n" );
	   sql.append("       P.OLD_PART_COUNT-NVL(OLD_PART_ALREADY_IN,0) OLD_PART_COUNT, --配件数量\n" );
	   sql.append("       S.SUPPLIER_NAME, --生产厂商名称\n" );
	   sql.append("       S.SUPPLIER_CODE, --生产厂商CODE\n" );
	   sql.append("(SELECT R.SUPPLIER_NAME\n" );
	   sql.append("         FROM PT_BA_SUPPLIER R\n" );
	   sql.append("        WHERE R.SUPPLIER_ID =\n" );
	   sql.append("              (DECODE(P.FAULT_TYPE,\n" );
	   sql.append("                      "+DicConstant.GZLB_01+",\n" );
	   sql.append("                      P.OLD_SUPPLIER_ID,\n" );
	   sql.append("                      (SELECT P1.OLD_SUPPLIER_ID\n" );
	   sql.append("                         FROM SE_BU_CLAIM_FAULT_PART P1\n" );
	   sql.append("                        WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
	   sql.append("                          AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
	   sql.append("                          AND P1.FAULT_TYPE = "+DicConstant.GZLB_01+")))) AS MAIN_SUPP_NAME, --责任厂商名称\n" );
	   sql.append("      (SELECT R.SUPPLIER_CODE\n" );
	   sql.append("         FROM PT_BA_SUPPLIER R\n" );
	   sql.append("        WHERE R.SUPPLIER_ID =\n" );
	   sql.append("              (DECODE(P.FAULT_TYPE,\n" );
	   sql.append("                      "+DicConstant.GZLB_01+",\n" );
	   sql.append("                      P.OLD_SUPPLIER_ID,\n" );
	   sql.append("                      (SELECT P1.OLD_SUPPLIER_ID\n" );
	   sql.append("                         FROM SE_BU_CLAIM_FAULT_PART P1\n" );
	   sql.append("                        WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
	   sql.append("                          AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
	   sql.append("                          AND P1.FAULT_TYPE = "+DicConstant.GZLB_01+")))) AS MAIN_SUPP_CODE, --责任厂商CODE\n");
	   sql.append("       P.FAULT_REASON ,--原因\n" );
	   sql.append("       '' AS BOX_NO,--箱号 \n" );
	   sql.append("       '0' AS SSSL,--实收数量 \n" );
	   sql.append("       '' AS REMARK \n" );
	   sql.append("  FROM SE_BU_CLAIM            C,\n" );
	   sql.append("       SE_BU_CLAIM_FAULT      F,\n" );
	   sql.append("       SE_BU_CLAIM_FAULT_PART P,\n" );
	   sql.append("       MAIN_VEHICLE           V,\n" );
	   sql.append("       PT_BA_INFO             I,\n" );
	   sql.append("       PT_BA_SUPPLIER         S,\n" );
	   sql.append("       MAIN_DEALER            M\n" );
	   sql.append(" WHERE C.CLAIM_ID = F.CLAIM_ID\n" );
	   sql.append("   AND F.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
	   sql.append("   AND C.VEHICLE_ID = V.VEHICLE_ID\n" );
	   sql.append("   AND P.OLD_PART_ID = I.PART_ID\n" );
	   sql.append("   AND C.OLDPART_STATUS IS NULL\n" );
	   sql.append("   AND P.OLD_SUPPLIER_ID = S.SUPPLIER_ID\n");
	   sql.append("   AND C.ORG_ID=M.ORG_ID\n");
	   sql.append("   AND "+conditions+"\n");
	   sql.append("   AND P.IF_RETURN="+DicConstant.SF_02+"\n" );//索赔单配件表 是否回运过的
	   sql.append("   AND I.IF_RETURN="+DicConstant.SF_01+"\n" );//配件属性 为需要回运的
	   sql.append("   AND C.OLDPART_STATUS IS NULL\n" );//配件属性 为需要回运的
	   sql.append("   AND C.CLAIM_STATUS = "+DicConstant.SPDZT_05+"\n" );//索赔单状态 人工审核通过
	   sql.append("   AND P.MEASURES ="+DicConstant.CLFS_02+" "); //处理措施只下载 零件更换的 
	   //sql.append("   AND C.APPLY_DATE< TO_DATE(ADD_MONTH'"+produceDate+"', 'YYYY-MM') \n");//旧件产生的日期之前的
	   sql.append("   AND C.CHECKPASS_DATE< ADD_MONTHS(TO_DATE('"+produceDate+"','YYYY-MM'),+1) \n");//旧件产生的日期之前的
	   //sql.append("   AND C.CHECKPASS_DATE> TO_DATE('"+produceDate+"','YYYY-MM' ) \n");//旧件产生的日期之前的
	   sql.append("	  AND EXISTS (SELECT 1\n" );
	   sql.append("          FROM SE_BA_RETURN_DEALER_RELATION DR\n" );
	   sql.append("         WHERE DR.R_ORGID = "+user.getOrgId()+"\n" );
	   sql.append("           AND DR.D_ORGID = C.ORG_ID) \n");
	   sql.append("  AND NOT EXISTS\n" );//不存在旧件不回运表
	   sql.append(" (SELECT 1\n" );
	   sql.append("         FROM SE_BU_RETURNORDER_NOT N, SE_BU_RETURNORDER_NOT_DTL D\n" );
	   sql.append("        WHERE N.NOTBACK_ID = D.NOTBACK_ID\n" );
	   sql.append("          AND N.CLAIM_ID = C.CLAIM_ID\n" );
	   sql.append("          AND D.FAULT_CODE = F.FAULT_CODE\n" );
	   sql.append("          AND D.PART_ID = P.OLD_PART_ID\n" );
	   sql.append("          AND D.SUPPLIER_ID = P.OLD_SUPPLIER_ID");
	   sql.append("          AND N.APPLY_STATUS = 303002)");
	   qs = factory.select(null, sql.toString());
	   return qs;
   }
   
   /**
    * 提报校验
    * @return
    * @throws Exception
    */
   public QuerySet expData(String conditions,User user) throws Exception{
	   QuerySet qs = null;
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.CLAIM_NO,\n" );
	   sql.append("       T.VIN,\n" );
	   sql.append("       T.MODELS_CODE,\n" );
	   sql.append("       T.MILEAGE,\n" );
	   sql.append("       T.PART_CODE,\n" );
	   sql.append("       T.PART_NAME,\n" );
	   sql.append("       T.PART_AMOUNT,\n" );
	   sql.append("       T.OUGHT_COUNT,\n" );
	   sql.append("       T.ACTUL_COUNT,\n" );
	   sql.append("       T.BOX_NO,\n" );
	   sql.append("       T.MILEAGE,\n" );
	   sql.append("       T.PROSUPPLY_CODE,\n" );
	   sql.append("       (SELECT P.SUPPLIER_NAME FROM PT_BA_SUPPLIER P WHERE P.SUPPLIER_CODE = PROSUPPLY_CODE) PROSUPPLY_NAME,\n" );
	   sql.append("       T.DUTYSUPPLY_CODE,\n" );
	   sql.append("       (SELECT P.SUPPLIER_NAME FROM PT_BA_SUPPLIER P WHERE P.SUPPLIER_CODE = DUTYSUPPLY_CODE) DUTYSUPPLY_NAME,\n" );
	   sql.append("       T.BROKEN_REASON,\n" );
	   sql.append("       T.REMARKS,\n" );
	   sql.append("       T.ROW_NUM,\n" );
	   sql.append("       T.FAULT_CODE,\n" );
	   sql.append("       T.ORDER_NO,\n" );
	   sql.append("       T.USER_ACCOUNT\n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP T\n" );
	   sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'");
	   sql.append(" AND T.ROW_NUM IN ("+conditions+")");
	   
	   qs = factory.select(null, sql.toString());
	   return qs;
   }
   /**
    * 提报校验
    * @return
    * @throws Exception
    */
   public QuerySet checkReport(String orderId) throws Exception{
	   QuerySet qs = null;
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.ORDER_ID \n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DETAIL T\n" );
	   sql.append(" WHERE T.ORDER_ID = "+orderId+"\n" );
	   qs = factory.select(null, sql.toString());
	   return qs;
   }
   /**
    * 查询回运单下所有索赔单。
    * @return
    * @throws Exception
    */
   public QuerySet queryClaim(String orderId) throws Exception{
	   QuerySet qs = null;
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT DISTINCT D.CLAIM_ID FROM SE_BU_RETURNORDER_DETAIL D WHERE D.ORDER_ID ="+orderId+"\n" );
	   qs = factory.select(null, sql.toString());
	   return qs;
   }
   /**
    * 查询回运单下没有完整导入的配件数量。
    * @return
    * @throws Exception
    */
   public QuerySet checkMissCount(String orderId) throws Exception{
	   QuerySet qs = null;
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT D.DETAIL_ID, NVL(D.SHOULD_COUNT,0) SHOULD_COUNT, NVL(D.OUGHT_COUNT,0) OUGHT_COUNT\n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DETAIL D\n" );
	   sql.append(" WHERE D.ORDER_ID = "+orderId+"");
	   qs = factory.select(null, sql.toString());
	   return qs;
   }
   
   public BaseResultSet searchImport(PageManager page,String conditions,User user)throws Exception{
	   BaseResultSet bs = null;
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT D.* \n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP D");
	   sql.append(" WHERE "+conditions+" AND D.USER_ACCOUNT ='"+user.getAccount()+"'");
	   bs=factory.select(sql.toString(), page);
	   return bs;
   }
   
   /**
    * 校验索赔单号、配件代码、供应商代码 一致
    * (数量可以改)
    * @return
    * @throws Exception
    */
   public QuerySet checkList1(User user)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.CLAIM_NO, T.PART_CODE, T.PROSUPPLY_CODE, T.ROW_NUM\n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP T\n" );
	   sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	   sql.append("AND NOT EXISTS (SELECT D.*\n" );
	   sql.append("       FROM SE_BU_CLAIM               C,\n" );
	   sql.append("            SE_BU_CLAIM_FAULT         F,\n" );
	   sql.append("            SE_BU_CLAIM_FAULT_PART    D,\n" );
	   sql.append("            PT_BA_SUPPLIER            P,\n" );
	   sql.append("            PT_BA_INFO                B\n" );
	   sql.append("      WHERE T.CLAIM_NO = C.CLAIM_NO\n" );
	   sql.append("        AND C.CLAIM_ID = F.CLAIM_ID\n" );
	   sql.append("        AND F.CLAIM_DTL_ID = D.CLAIM_DTL_ID\n" );
	   sql.append("        AND T.FAULT_CODE = F.FAULT_CODE\n" );
	   sql.append("        AND T.PART_CODE = D.OLD_PART_CODE\n" );
	   sql.append("        AND D.OLD_PART_ID = B.PART_ID\n" );
	   sql.append("        AND T.PROSUPPLY_CODE = P.SUPPLIER_CODE\n" );
	   sql.append("        AND D.OLD_SUPPLIER_ID = P.SUPPLIER_ID\n" );
	 //  sql.append("        AND T.OUGHT_COUNT = D.OLD_PART_COUNT\n");
	   sql.append("           AND D.IF_RETURN = "+DicConstant.SF_02+"\n" );//索赔单配件 未回运的
	   sql.append("           AND B.IF_RETURN = "+DicConstant.SF_01+"\n");//配件属性 需要回运的旧件
	   sql.append("           )");//配件属性 需要回运的旧件
	   sql.append("         ORDER BY TO_NUMBER(T.ROW_NUM) ");
	   return factory.select(null, sql.toString());
   }
   /**
    * 数量不能大于故障配件数量
    * @param user
    * @return
    * @throws Exception
    */
   public QuerySet checkList6(User user)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.CLAIM_NO, T.PART_CODE, T.PROSUPPLY_CODE, T.ROW_NUM\n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP T\n" );
	   sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	   sql.append("AND EXISTS (SELECT D.*\n" );
	   sql.append("       FROM SE_BU_CLAIM               C,\n" );
	   sql.append("            SE_BU_CLAIM_FAULT         F,\n" );
	   sql.append("            SE_BU_CLAIM_FAULT_PART    D,\n" );
	   sql.append("            PT_BA_SUPPLIER            P,\n" );
	   sql.append("            PT_BA_INFO                B\n" );
	   sql.append("      WHERE T.CLAIM_NO = C.CLAIM_NO\n" );
	   sql.append("        AND C.CLAIM_ID = F.CLAIM_ID\n" );
	   sql.append("        AND F.CLAIM_DTL_ID = D.CLAIM_DTL_ID\n" );
	   sql.append("        AND T.FAULT_CODE = F.FAULT_CODE\n" );
	   sql.append("        AND T.PART_CODE = D.OLD_PART_CODE\n" );
	   sql.append("        AND D.OLD_PART_ID = B.PART_ID\n" );
	   sql.append("        AND T.PROSUPPLY_CODE = P.SUPPLIER_CODE\n" );
	   sql.append("        AND D.OLD_SUPPLIER_ID = P.SUPPLIER_ID\n" );
	   sql.append("        AND T.OUGHT_COUNT > D.OLD_PART_COUNT - NVL(D.OLD_PART_ALREADY_IN, 0)\n");
	   sql.append("           AND D.IF_RETURN = "+DicConstant.SF_02+"\n" );//索赔单配件 未回运的
	   sql.append("           AND B.IF_RETURN = "+DicConstant.SF_01+"\n");//配件属性 需要回运的旧件
	   sql.append("           )");
	   sql.append("         ORDER BY TO_NUMBER(T.ROW_NUM) ");
	   return factory.select(null, sql.toString());
   }
   /**
    * 校验箱号不能为空
    * @param user
    * @return
    * @throws Exception
    */
   public QuerySet checkList2(User user)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.ROW_NUM\n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP T\n" );
	   sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	   sql.append("   AND T.BOX_NO IS NULL");
	   return factory.select(null, sql.toString());
   }
   
   /**
    * 校验导入的数据是否重复
    * @param user
    * @return
    * @throws Exception
    */
   public QuerySet checkList3(User user)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT P.CLAIM_NO,\n" );
	   sql.append("       P.FAULT_CODE,\n" );
	   sql.append("       P.PART_CODE,\n" );
	   sql.append("       P.PROSUPPLY_CODE,\n" );
	   sql.append("       P.OUGHT_COUNT,\n" );
	   sql.append("       COUNT(P.TMP_ID)\n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP P\n" );
	   sql.append(" WHERE P.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	   sql.append(" GROUP BY P.CLAIM_NO,\n" );
	   sql.append("          P.FAULT_CODE,\n" );
	   sql.append("          P.PART_CODE,\n" );
	   sql.append("          P.PROSUPPLY_CODE,\n" );
	   sql.append("          P.OUGHT_COUNT\n" );
	   sql.append("HAVING COUNT(P.TMP_ID) > 1");
	   return factory.select(null, sql.toString());
   }
   /**
    * 校验导入的数据是否在旧件明细表中存在
    * @param user
    * @return
    * @throws Exception
    */
   public QuerySet checkList4(User user)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.ROW_NUM,T.CLAIM_NO, T.PART_CODE, T.OUGHT_COUNT, T.PROSUPPLY_CODE\n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP T\n" );
	   sql.append(" WHERE T.USER_ACCOUNT ='"+user.getAccount()+"'\n" );
	   sql.append("  AND EXISTS (SELECT 1\n" );
	   sql.append("           FROM SE_BU_RETURNORDER_DETAIL P\n" );
	   sql.append("          WHERE T.CLAIM_NO = P.CLAIM_NO\n" );
	   sql.append("            AND T.PART_CODE = P.PART_CODE\n" );
	   sql.append("            AND T.OUGHT_COUNT = P.OUGHT_COUNT\n" );
	   sql.append("            AND T.PROSUPPLY_CODE = P.PROSUPPLY_CODE) \n");
	   sql.append("    ORDER BY TO_NUMBER(T.ROW_NUM)  ");
	   return factory.select(null, sql.toString());
   }
   /**
    * 校验导入的数据不存在旧件不回运表
    * @param user
    * @return
    * @throws Exception
    */
   public QuerySet checkList5(User user)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT T.CLAIM_NO, T.PART_CODE, T.PROSUPPLY_CODE, T.ROW_NUM\n" );
	   sql.append("  FROM SE_BU_RETURNORDER_DTL_TMP T\n" );
	   sql.append(" WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	   sql.append("   AND EXISTS (SELECT 1\n" );
	   sql.append("          FROM SE_BU_RETURNORDER_NOT     N,\n" );
	   sql.append("               SE_BU_RETURNORDER_NOT_DTL D,\n" );
	   sql.append("               SE_BU_CLAIM               C,\n" );
	   sql.append("               SE_BU_CLAIM_FAULT         F\n" );
	   sql.append("         WHERE N.NOTBACK_ID = D.NOTBACK_ID\n" );
	   sql.append("           AND N.CLAIM_ID = C.CLAIM_ID\n" );
	   sql.append("           AND C.CLAIM_ID = F.CLAIM_ID\n" );
	   sql.append("           AND T.CLAIM_NO = C.CLAIM_NO\n" );
	   sql.append("           AND T.PART_CODE = D.PART_CODE\n" );
	   sql.append("           AND T.FAULT_CODE = F.FAULT_CODE)\n");
	   sql.append("    ORDER BY TO_NUMBER(T.ROW_NUM)  ");
	   return factory.select(null, sql.toString());
   }
   /**
    * 校验是否存在为提报的回运单。
    * @param user
    * @return
    * @throws Exception
    */
   public QuerySet queryReturnOrder(User user,String proDate)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("SELECT 1\n" );
	   sql.append("  FROM SE_BU_RETURN_ORDER T\n" );
	   sql.append(" WHERE T.ORG_ID ="+user.getOrgId()+"\n" );
	   sql.append("   AND T.ORDER_STATUS ="+DicConstant.HYDZT_01+"");
	   sql.append("   AND TO_DATE(T.PRODUCE_DATE, 'YYYY-MM') = TO_DATE('"+proDate+"', 'YYYY-MM')");
	   return factory.select(null, sql.toString());
   }
   /**
    * 导入确定方法
    * @param user
    * @param orderId
    * @return
    * @throws Exception
    */
   public boolean insertDetail(User user,String orderId,String rowNo)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("MERGE INTO SE_BU_RETURNORDER_DETAIL T2\n" );
	   sql.append("USING (SELECT C.CLAIM_ID,\n" );
	   sql.append("              F.CLAIM_DTL_ID,\n" );
	   sql.append("              P.OLD_PART_ID,\n" );
	   sql.append("              P.OLD_SUPPLIER_ID,\n" );
	   sql.append("              NVL(T.SHOULD_COUNT,0)SHOULD_COUNT,\n" );//应返旧件数量
	   sql.append("              NVL(T.OUGHT_COUNT,0)OUGHT_COUNT,\n" );//实返旧件数量
	   sql.append("              T.PART_CODE,\n" );
	   sql.append("              T.PART_NAME,\n" );
	   sql.append("              C.CLAIM_NO,\n" );
	   sql.append("              C.VEHICLE_ID,\n" );
	   sql.append("              T.VIN,\n" );
	   sql.append("              T.MODELS_CODE,\n" );
	   sql.append("              T.MILEAGE,\n" );
	   sql.append("              T.PART_AMOUNT,\n" );
	   sql.append("              T.BOX_NO,\n" );
	   sql.append("              S.SUPPLIER_ID,\n" );
	   sql.append("              S.SUPPLIER_CODE,\n" );
	   sql.append("              S.SUPPLIER_NAME,\n" );
	   sql.append("              (SELECT R.SUPPLIER_ID\n" );
	   sql.append("                 FROM PT_BA_SUPPLIER R\n" );
	   sql.append("                WHERE R.SUPPLIER_ID =\n" );
	   sql.append("                      (DECODE(P.FAULT_TYPE,\n" );
	   sql.append("                              "+DicConstant.GZLB_01+",\n" );
	   sql.append("                              P.OLD_SUPPLIER_ID,\n" );
	   sql.append("                              (SELECT P1.OLD_SUPPLIER_ID\n" );
	   sql.append("                                 FROM SE_BU_CLAIM_FAULT_PART P1\n" );
	   sql.append("                                WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
	   sql.append("                                  AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
	   sql.append("                                  AND P1.FAULT_TYPE = "+DicConstant.GZLB_01+")))) AS MAIN_SUPPLIER_ID,\n" );
	   sql.append("              (SELECT R.SUPPLIER_CODE\n" );
	   sql.append("                 FROM PT_BA_SUPPLIER R\n" );
	   sql.append("                WHERE R.SUPPLIER_ID =\n" );
	   sql.append("                      (DECODE(P.FAULT_TYPE,\n" );
	   sql.append("                              "+DicConstant.GZLB_01+",\n" );
	   sql.append("                              P.OLD_SUPPLIER_ID,\n" );
	   sql.append("                              (SELECT P1.OLD_SUPPLIER_ID\n" );
	   sql.append("                                 FROM SE_BU_CLAIM_FAULT_PART P1\n" );
	   sql.append("                                WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
	   sql.append("                                  AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
	   sql.append("                                  AND P1.FAULT_TYPE = "+DicConstant.GZLB_01+")))) AS MAIN_SUPPLIER_CODE,\n" );
	   sql.append("              (SELECT R.SUPPLIER_NAME\n" );
	   sql.append("                 FROM PT_BA_SUPPLIER R\n" );
	   sql.append("                WHERE R.SUPPLIER_ID =\n" );
	   sql.append("                      (DECODE(P.FAULT_TYPE,\n" );
	   sql.append("                              "+DicConstant.GZLB_01+",\n" );
	   sql.append("                              P.OLD_SUPPLIER_ID,\n" );
	   sql.append("                              (SELECT P1.OLD_SUPPLIER_ID\n" );
	   sql.append("                                 FROM SE_BU_CLAIM_FAULT_PART P1\n" );
	   sql.append("                                WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
	   sql.append("                                  AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
	   sql.append("                                  AND P1.FAULT_TYPE = "+DicConstant.GZLB_01+")))) AS MAIN_SUPPLIER_NAME,\n" );
	   sql.append("              T.BROKEN_REASON,\n" );
	   sql.append("              T.REMARKS,\n" );
	   sql.append("              P.FAULT_TYPE,\n" );
	   sql.append("              P.MEASURES\n" );
	   sql.append("         FROM SE_BU_RETURNORDER_DTL_TMP T,\n" );
	   sql.append("              SE_BU_CLAIM               C,\n" );
	   sql.append("              SE_BU_CLAIM_FAULT         F,\n" );
	   sql.append("              SE_BU_CLAIM_FAULT_PART    P,\n" );
	   sql.append("              PT_BA_SUPPLIER            S\n" );
	   sql.append("        WHERE T.CLAIM_NO = C.CLAIM_NO\n" );
	   sql.append("          AND C.CLAIM_ID = F.CLAIM_ID\n" );
	   sql.append("          AND T.FAULT_CODE = F.FAULT_CODE\n" );
	   sql.append("          AND F.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
	   sql.append("          AND T.PART_CODE = P.OLD_PART_CODE\n" );
	   sql.append("          AND P.OLD_SUPPLIER_ID = S.SUPPLIER_ID\n" );
	   sql.append("          AND T.PROSUPPLY_CODE = S.SUPPLIER_CODE\n" );
	   sql.append("          AND T.USER_ACCOUNT = '"+user.getAccount()+"' "+rowNo+" ) T1\n" );
	   sql.append("ON (T2.CLAIM_ID = T1.CLAIM_ID AND T2.PART_ID = T1.OLD_PART_ID AND T2.PROSUPPLY_ID = T1.OLD_SUPPLIER_ID AND T2.CLAIM_DTL_ID = T1.CLAIM_DTL_ID AND T2.ORDER_ID ="+orderId+")\n" );
	   sql.append("WHEN MATCHED THEN\n" );
	   sql.append("  UPDATE SET T2.OUGHT_COUNT = T1.OUGHT_COUNT + nvl(T2.OUGHT_COUNT,0)\n" );
	   sql.append("WHEN NOT MATCHED THEN\n" );
	   sql.append("  INSERT\n" );
	   sql.append("    (T2.DETAIL_ID,\n" );
	   sql.append("     T2.ORDER_ID,\n" );
	   sql.append("     T2.ORDER_NO,\n" );
	   sql.append("     T2.PART_ID,\n" );
	   sql.append("     T2.PART_CODE,\n" );
	   sql.append("     T2.PART_NAME,\n" );
	   sql.append("     T2.CLAIM_ID,\n" );
	   sql.append("     T2.CLAIM_NO,\n" );
	   sql.append("     T2.VEHICLE_ID,\n" );
	   sql.append("     T2.VIN,\n" );
	   sql.append("     T2.MODELS_CODE,\n" );
	   sql.append("     T2.MILEAGE,\n" );
	   sql.append("     T2.PART_AMOUNT,\n" );
	   sql.append("     T2.SHOULD_COUNT,\n" );
	   sql.append("     T2.OUGHT_COUNT,\n" );
	   sql.append("     T2.BOX_NO,\n" );
	   sql.append("     T2.PROSUPPLY_ID,\n" );
	   sql.append("     T2.PROSUPPLY_CODE,\n" );
	   sql.append("     T2.PROSUPPLY_NAME,\n" );
	   sql.append("     T2.DUTYSUPPLY_ID,\n" );
	   sql.append("     T2.DUTYSUPPLY_CODE,\n" );
	   sql.append("     T2.DUTYSUPPLY_NAME,\n" );
	   sql.append("     T2.BROKEN_REASON,\n" );
	   sql.append("     T2.REMARKS,\n" );
	   sql.append("     T2.ALREADY_IN,\n" );
	   sql.append("     T2.IS_MAIN,\n" );
	   sql.append("     T2.MEASURES,\n" );
	   sql.append("     T2.OLD_PART_STATUS,\n" );
	   sql.append("     T2.CLAIM_DTL_ID) VALUES\n" );
	   sql.append("    (F_GETID(),\n" );
	   sql.append("     "+orderId+",\n" );
	   sql.append("     (SELECT O.ORDER_NO FROM SE_BU_RETURN_ORDER O WHERE O.ORDER_ID = "+orderId+"),\n" );
	   sql.append("     T1.OLD_PART_ID,\n" );
	   sql.append("     T1.PART_CODE,\n" );
	   sql.append("     T1.PART_NAME,\n" );
	   sql.append("     T1.CLAIM_ID,\n" );
	   sql.append("     T1.CLAIM_NO,\n" );
	   sql.append("     T1.VEHICLE_ID,\n" );
	   sql.append("     T1.VIN,\n" );
	   sql.append("     T1.MODELS_CODE,\n" );
	   sql.append("     T1.MILEAGE,\n" );
	   sql.append("     T1.PART_AMOUNT,\n" );
	   sql.append("     T1.SHOULD_COUNT,\n" );
	   sql.append("     T1.OUGHT_COUNT,\n" );
	   sql.append("     T1.BOX_NO,\n" );
	   sql.append("     T1.SUPPLIER_ID,\n" );
	   sql.append("     T1.SUPPLIER_CODE,\n" );
	   sql.append("     T1.SUPPLIER_NAME,\n" );
	   sql.append("     T1.MAIN_SUPPLIER_ID,\n" );
	   sql.append("     T1.MAIN_SUPPLIER_CODE,\n" );
	   sql.append("     T1.MAIN_SUPPLIER_NAME,\n" );
	   sql.append("     T1.BROKEN_REASON,\n" );
	   sql.append("     T1.REMARKS,\n" );
	   sql.append("     0,\n" );
	   sql.append("     T1.FAULT_TYPE,\n" );
	   sql.append("     T1.MEASURES,\n" );
	   sql.append("     "+DicConstant.JJZT_01+",\n" );
	   sql.append("     T1.CLAIM_DTL_ID)");
	   return factory.update(sql.toString(),null);
   }
  /* public boolean insertDetail(User user,String orderId)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("INSERT INTO SE_BU_RETURNORDER_DETAIL\n" );
	   sql.append("  (DETAIL_ID,\n" );
	   sql.append("   ORDER_ID,\n" );
	   sql.append("   ORDER_NO,\n" );
	   sql.append("   PART_ID,\n" );
	   sql.append("   PART_CODE,\n" );
	   sql.append("   PART_NAME,\n" );
	   sql.append("   CLAIM_ID,\n" );
	   sql.append("   CLAIM_NO,\n" );
	   sql.append("   VEHICLE_ID,\n" );
	   sql.append("   VIN,\n" );
	   sql.append("   MODELS_CODE,\n" );
	   sql.append("   MILEAGE,\n" );
	   sql.append("   PART_AMOUNT,\n" );
	   sql.append("   OUGHT_COUNT,\n" );
	   sql.append("   BOX_NO,\n" );
	   sql.append("   PROSUPPLY_ID,\n" );
	   sql.append("   PROSUPPLY_CODE,\n" );
	   sql.append("   PROSUPPLY_NAME,\n" );
	   sql.append("   DUTYSUPPLY_ID,\n" );
	   sql.append("   DUTYSUPPLY_CODE,\n" );
	   sql.append("   DUTYSUPPLY_NAME,\n" );
	   sql.append("   BROKEN_REASON,\n" );
	   sql.append("   REMARKS,\n" );
	   sql.append("   ALREADY_IN,\n" );
	   sql.append("   IS_MAIN,\n" );
	   sql.append("   MEASURES,\n" );
	   sql.append("   OLD_PART_STATUS,\n" );
	   sql.append("   CLAIM_DTL_ID)\n");
	   sql.append("  SELECT F_GETID(),\n" );
	   sql.append("         "+orderId+",\n" );
	   sql.append("         (SELECT A.ORDER_NO FROM SE_BU_RETURN_ORDER A WHERE A.ORDER_ID="+orderId+") ORDER_NO,\n" );
	   sql.append("         P.OLD_PART_ID,\n" );
	   sql.append("         T.PART_CODE,\n" );
	   sql.append("         T.PART_NAME,\n" );
	   sql.append("         C.CLAIM_ID,\n" );
	   sql.append("         T.CLAIM_NO,\n" );
	   sql.append("         C.VEHICLE_ID,\n" );
	   sql.append("         T.VIN,\n" );
	   sql.append("         T.MODELS_CODE,\n" );
	   sql.append("         T.MILEAGE,\n" );
	   sql.append("         T.PART_AMOUNT,\n" );
	   sql.append("         T.OUGHT_COUNT,\n" );
	   sql.append("         T.BOX_NO,\n" );
	   sql.append("         S.SUPPLIER_ID,\n" );
	   sql.append("         S.SUPPLIER_CODE,\n" );
	   sql.append("         S.SUPPLIER_NAME,\n" );
	   sql.append("(SELECT R.SUPPLIER_ID\n" );
	   sql.append("           FROM PT_BA_SUPPLIER R\n" );
	   sql.append("          WHERE R.SUPPLIER_ID =\n" );
	   sql.append("                (DECODE(P.FAULT_TYPE,\n" );
	   sql.append("                        "+DicConstant.GZLB_01+",\n" );
	   sql.append("                        P.OLD_SUPPLIER_ID,\n" );
	   sql.append("                        (SELECT P1.OLD_SUPPLIER_ID\n" );
	   sql.append("                           FROM SE_BU_CLAIM_FAULT_PART P1\n" );
	   sql.append("                          WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
	   sql.append("                            AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
	   sql.append("                            AND P1.FAULT_TYPE = "+DicConstant.GZLB_01+")))) AS SUPPLIER_ID,\n" );
	   sql.append("        (SELECT R.SUPPLIER_CODE\n" );
	   sql.append("           FROM PT_BA_SUPPLIER R\n" );
	   sql.append("          WHERE R.SUPPLIER_ID =\n" );
	   sql.append("                (DECODE(P.FAULT_TYPE,\n" );
	   sql.append("                        "+DicConstant.GZLB_01+",\n" );
	   sql.append("                        P.OLD_SUPPLIER_ID,\n" );
	   sql.append("                        (SELECT P1.OLD_SUPPLIER_ID\n" );
	   sql.append("                           FROM SE_BU_CLAIM_FAULT_PART P1\n" );
	   sql.append("                          WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
	   sql.append("                            AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
	   sql.append("                            AND P1.FAULT_TYPE = "+DicConstant.GZLB_01+")))) AS SUPPLIER_CODE,\n" );
	   sql.append("        (SELECT R.SUPPLIER_NAME\n" );
	   sql.append("           FROM PT_BA_SUPPLIER R\n" );
	   sql.append("          WHERE R.SUPPLIER_ID =\n" );
	   sql.append("                (DECODE(P.FAULT_TYPE,\n" );
	   sql.append("                        "+DicConstant.GZLB_01+",\n" );
	   sql.append("                        P.OLD_SUPPLIER_ID,\n" );
	   sql.append("                        (SELECT P1.OLD_SUPPLIER_ID\n" );
	   sql.append("                           FROM SE_BU_CLAIM_FAULT_PART P1\n" );
	   sql.append("                          WHERE P1.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
	   sql.append("                            AND P1.OLD_PART_ID = P.FIRST_PART_ID\n" );
	   sql.append("                            AND P1.FAULT_TYPE = "+DicConstant.GZLB_01+")))) AS SUPPLIER_NAME,\n");
	   sql.append("         T.BROKEN_REASON,\n" );
	   sql.append("         T.REMARKS,\n" );
	   sql.append("         0 ,\n" );
	   sql.append("         P.FAULT_TYPE,\n" );
	   sql.append("         P.MEASURES,\n" );
	   sql.append("         "+DicConstant.JJZT_01+",\n" );
	   sql.append("         P.CLAIM_DTL_ID\n" );
	   sql.append("    FROM SE_BU_RETURNORDER_DTL_TMP T,\n" );
	   sql.append("         SE_BU_CLAIM               C,\n" );
	   sql.append("         SE_BU_CLAIM_FAULT         F,\n" );
	   sql.append("         SE_BU_CLAIM_FAULT_PART    P,\n" );
	   sql.append("         PT_BA_SUPPLIER            S\n" );
	   sql.append("WHERE T.CLAIM_NO = C.CLAIM_NO\n" );
	   sql.append("  AND C.CLAIM_ID = F.CLAIM_ID\n" );
	   sql.append("  AND T.FAULT_CODE = F.FAULT_CODE\n" );
	   sql.append("  AND F.CLAIM_DTL_ID = P.CLAIM_DTL_ID\n" );
	   sql.append("  AND T.PART_CODE = P.OLD_PART_CODE\n" );
	   sql.append("  AND P.OLD_SUPPLIER_ID = S.SUPPLIER_ID");
	   sql.append("  AND T.USER_ACCOUNT = '"+user.getAccount()+"'");
	   return factory.update(sql.toString(),null);
   }*/
   
   /**
    * 更新索赔单配件表旧件入库数量
    * @param user
    * @return
    * @throws Exception
    */
   public boolean updateClaimPartCount(User user,String rowNo)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("MERGE INTO SE_BU_CLAIM_FAULT_PART T2\n" );
	   sql.append("USING (SELECT T.OUGHT_COUNT, T3.FAULT_PART_ID\n" );
	   sql.append("         FROM SE_BU_CLAIM               C,\n" );
	   sql.append("              SE_BU_CLAIM_FAULT         F,\n" );
	   sql.append("              SE_BU_RETURNORDER_DTL_TMP T,\n" );
	   sql.append("              PT_BA_SUPPLIER            S,\n" );
	   sql.append("              PT_BA_INFO                P,\n" );
	   sql.append("              SE_BU_CLAIM_FAULT_PART    T3\n" );
	   sql.append("        WHERE C.CLAIM_NO = T.CLAIM_NO\n" );
	   sql.append("          AND C.CLAIM_ID = F.CLAIM_ID\n" );
	   sql.append("          AND F.FAULT_CODE = T.FAULT_CODE\n" );
	   sql.append("          AND S.SUPPLIER_CODE = T.PROSUPPLY_CODE\n" );
	   sql.append("          AND T3.CLAIM_DTL_ID = F.CLAIM_DTL_ID\n" );
	   sql.append("          AND T3.OLD_PART_CODE = P.PART_CODE\n" );
	   sql.append("          AND T3.OLD_SUPPLIER_ID = S.SUPPLIER_ID\n" );
	   sql.append("          AND T3.CLAIM_ID = C.CLAIM_ID\n" );
	   sql.append("          AND P.PART_CODE = T.PART_CODE\n" );
	   sql.append("          AND T.USER_ACCOUNT = '"+user.getAccount()+"' "+rowNo+") T1\n" );
	   sql.append("ON (T2.FAULT_PART_ID = T1.FAULT_PART_ID)\n" );
	   sql.append("WHEN MATCHED THEN\n" );
	   sql.append("  UPDATE\n" );
	   sql.append("     SET T2.OLD_PART_ALREADY_IN = T1.OUGHT_COUNT +\n" );
	   sql.append("                                  NVL(T2.OLD_PART_ALREADY_IN, 0)");
	   return factory.update(sql.toString(), null);
   }
   
   /**
    * 更改索赔单配件 回运状态
    * @param user
    * @return
    * @throws Exception
    */
  /* public boolean updateClaimPart(User user)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("UPDATE SE_BU_CLAIM_FAULT_PART D\n" );
	   sql.append("   SET D.IF_RETURN = "+DicConstant.SF_01+"\n" );
	   sql.append(" WHERE EXISTS (SELECT 1\n" );
	   sql.append("          FROM SE_BU_RETURNORDER_DTL_TMP T,\n" );
	   sql.append("               PT_BA_SUPPLIER            P,\n" );
	   sql.append("               SE_BU_CLAIM               C,\n" );
	   sql.append("               SE_BU_CLAIM_FAULT         F\n" );
	   sql.append("         WHERE D.OLD_SUPPLIER_ID = P.SUPPLIER_ID\n" );
	   sql.append("           AND T.PROSUPPLY_CODE = P.SUPPLIER_CODE\n" );
	   sql.append("           AND D.CLAIM_ID = C.CLAIM_ID\n" );
	   sql.append("           AND T.CLAIM_NO = C.CLAIM_NO\n" );
	   sql.append("           AND T.PART_CODE = D.OLD_PART_CODE\n" );
	   sql.append("           AND T.FAULT_CODE = F.FAULT_CODE\n" );
	   sql.append("           AND D.CLAIM_DTL_ID = F.CLAIM_DTL_ID\n" );
	   sql.append("           AND T.USER_ACCOUNT = '"+user.getAccount()+"')");
	   return factory.update(sql.toString(), null);
   }*/
   
   /**
    * 更新索赔单配件表 是否回运字段
    * @param user
    * @return
    * @throws Exception
    */
   public boolean updateClaimPart(User user,String rowNo)throws Exception{
	   StringBuffer sql= new StringBuffer();
	   sql.append("MERGE INTO SE_BU_CLAIM_FAULT_PART T2\n" );
	   sql.append("USING (SELECT T3.FAULT_PART_ID, T3.OLD_PART_COUNT\n" );
	   sql.append("         FROM SE_BU_CLAIM               C,\n" );
	   sql.append("              SE_BU_CLAIM_FAULT         F,\n" );
	   sql.append("              SE_BU_RETURNORDER_DTL_TMP T,\n" );
	   sql.append("              PT_BA_SUPPLIER            S,\n" );
	   sql.append("              PT_BA_INFO                P,\n" );
	   sql.append("              SE_BU_CLAIM_FAULT_PART    T3\n" );
	   sql.append("        WHERE C.CLAIM_NO = T.CLAIM_NO\n" );
	   sql.append("          AND C.CLAIM_ID = F.CLAIM_ID\n" );
	   sql.append("          AND F.FAULT_CODE = T.FAULT_CODE\n" );
	   sql.append("          AND S.SUPPLIER_CODE = T.PROSUPPLY_CODE\n" );
	   sql.append("          AND T3.CLAIM_DTL_ID = F.CLAIM_DTL_ID\n" );
	   sql.append("          AND T3.OLD_PART_CODE = P.PART_CODE\n" );
	   sql.append("          AND T3.OLD_SUPPLIER_ID = S.SUPPLIER_ID\n" );
	   sql.append("          AND T3.CLAIM_ID = C.CLAIM_ID\n" );
	   sql.append("          AND P.PART_CODE = T.PART_CODE\n" );
	   sql.append("          AND T.USER_ACCOUNT = '"+user.getAccount()+"' "+rowNo+") T1\n" );
	   sql.append("ON (T2.FAULT_PART_ID = T1.FAULT_PART_ID AND NVL(T2.OLD_PART_ALREADY_IN, 0) = T1.OLD_PART_COUNT)\n" );
	   sql.append("WHEN MATCHED THEN\n" );
	   sql.append("  UPDATE SET  T2.IF_RETURN="+DicConstant.SF_01+"");
	   return factory.update(sql.toString(), null);
   } 
   
}
