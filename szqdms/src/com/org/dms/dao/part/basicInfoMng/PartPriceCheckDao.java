package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

public class PartPriceCheckDao {


	/**
     * 获取配件价格临时表数据(价格无改动)
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet ptBaPriceTmpCheck(User user,DBFactory factory) throws Exception{
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT A.TMP_NO,A.PART_CODE\n" );
    	sql.append("  FROM PT_BA_ARMY_PRICE_TMP A, PT_BA_INFO B\n" );
    	sql.append(" WHERE A.PART_CODE || A.ARMY_PRICE =\n" );
    	sql.append("       B.PART_CODE || B.ARMY_PRICE\n" );
        sql.append(" AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
        return factory.select(null, sql.toString());
    }
    /**
     * 获取配件价格临时表数据(配件不存在)
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet ptBaPriceTmpCheck1(User user,DBFactory factory) throws Exception{
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT A.TMP_NO,A.PART_CODE\n" );
    	sql.append("  FROM PT_BA_ARMY_PRICE_TMP A\n" );
    	sql.append(" WHERE A.PART_CODE NOT IN (SELECT B.PART_CODE FROM PT_BA_INFO B WHERE B.PART_STATUS<>'"+DicConstant.PJZT_02+"')\n" );
        sql.append(" AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
        return factory.select(null, sql.toString());
    }
	/**
	 * 订单主信息查询
	 */
   public QuerySet checkPart(User user,DBFactory factory) throws Exception {
   	   String sql = "SELECT T.TMP_NO,T.PART_CODE FROM PT_BA_ARMY_PRICE_TMP T WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'"
   	   		+ "AND NOT EXISTS (SELECT 1 FROM PT_BA_INFO T1 WHERE T1.PART_CODE = T.PART_CODE)\n";
       return factory.select(null,sql);
   }
   public QuerySet checkPrice(User user,DBFactory factory) throws Exception {
   	   String sql = "SELECT T.TMP_NO,T.PART_CODE,T.ARMY_PRICE FROM PT_BA_ARMY_PRICE_TMP T WHERE NVL(T.ARMY_PRICE,0)=0 AND T.USER_ACCOUNT = '"+user.getAccount()+"'\n";
       return factory.select(null,sql);
   }
   public QuerySet checkZero(User user,DBFactory factory) throws Exception {
   	   String sql = "SELECT T.TMP_NO,T.PART_CODE,T.ARMY_PRICE FROM PT_BA_ARMY_PRICE_TMP T WHERE T.USER_ACCOUNT = '"+user.getAccount()+"'\n";
       return factory.select(null,sql);
   }
}
