package com.org.dms.dao.part.basicInfoMng;

import com.org.dms.common.DicConstant;
import com.org.framework.common.DBFactory;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;

public class PtBaInfoCheckDao {
    /**
     * 获取配件采购价格临时表数据(价格无改动)
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet ptBaPchPriceTrueTmpCheck(User user,DBFactory factory) throws Exception{
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT A.ROW_NUM,A.PART_CODE\n" );
    	sql.append("  FROM PT_BA_PCH_TMP A, PT_BA_PART_SUPPLIER_RL B,PT_BA_INFO C,PT_BA_SUPPLIER D\n" );
    	sql.append(" WHERE A.PART_CODE=C.PART_CODE AND A.SUPPLIER_CODE=D.SUPPLIER_CODE AND B.PART_ID=C.PART_ID AND B.SUPPLIER_ID=D.SUPPLIER_ID\n" );
    	sql.append(" AND A.PART_CODE||A.SUPPLIER_CODE||A.PCH_PRICE=C.PART_CODE||D.SUPPLIER_CODE||B.PCH_PRICE\n" );
        sql.append(" AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
        return factory.select(null, sql.toString());
    }
    /**
     * 获取配件价格临时表数据(配件不存在)
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet ptBaPchPriceTrueTmpCheck1(User user,DBFactory factory) throws Exception{
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT A.ROW_NUM, A.PART_CODE\n" );
    	sql.append("  FROM PT_BA_PCH_TMP A, PT_BA_INFO B, PT_BA_SUPPLIER C\n" );
    	sql.append(" WHERE A.PART_CODE = B.PART_CODE\n" );
    	sql.append("   AND A.SUPPLIER_CODE = C.SUPPLIER_CODE\n" );
    	sql.append("   AND NOT EXISTS\n" );
    	sql.append("       (SELECT 1 FROM PT_BA_PART_SUPPLIER_RL WHERE PART_ID||SUPPLIER_ID = B.PART_ID || C.SUPPLIER_ID)");
        sql.append(" AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
        return factory.select(null, sql.toString());
    }
    /**
     * 获取配件采购价格临时表数据
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet searchPtBaPchPriceTrueTmp(User user, String errorInfoRowNum,DBFactory factory) throws Exception{
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT\n" );
        sql.append("ROW_NUM,\n" );
        sql.append("TMP_ID,\n" );
        sql.append("PART_CODE,\n" );
        sql.append("PART_NAME,\n" );
        sql.append("SUPPLIER_CODE,\n" );
        sql.append("SUPPLIER_NAME,\n" );
        sql.append("PCH_PRICE,\n" );
        sql.append("USER_ACCOUNT\n" );
        sql.append("FROM  PT_BA_PCH_TMP");

        sql.append(" WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
        if (!errorInfoRowNum.equals("")) {
            sql.append(" AND ROW_NUM not in ( "+errorInfoRowNum+")\n" );
        }
        return factory.select(null, sql.toString());
    }
    /**
     * 获取配件价格临时表数据(价格无改动)
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet ptBaPriceTmpCheck(User user,DBFactory factory) throws Exception{
    	StringBuilder sql= new StringBuilder();
    	sql.append("SELECT A.ROW_NUM,A.PART_CODE\n" );
    	sql.append("  FROM PT_BA_PRICE_TMP A, PT_BA_INFO B\n" );
    	sql.append(" WHERE A.PART_CODE || A.PLAN_PRICE || A.SALE_PRICE || A.RETAIL_PRICE =\n" );
    	sql.append("       B.PART_CODE || B.PLAN_PRICE || B.SALE_PRICE || B.RETAIL_PRICE\n" );
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
    	sql.append("SELECT A.ROW_NUM,A.PART_CODE\n" );
    	sql.append("  FROM PT_BA_PRICE_TMP A\n" );
    	sql.append(" WHERE NOT EXISTS (SELECT 1 FROM PT_BA_INFO B WHERE A.PART_CODE=B.PART_CODE AND B.PART_STATUS<>'"+DicConstant.PJZT_02+"')\n" );
        sql.append(" AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
        return factory.select(null, sql.toString());
    }
    /**
     * 获取配件价格临时表数据
     * @param user
     * @return
     * @throws Exception
     */
    public QuerySet searchPtBaPriceTmp(User user, String errorInfoRowNum,DBFactory factory) throws Exception{
        StringBuffer sql= new StringBuffer();
        sql.append("SELECT\n" );
        sql.append("TMP_ID,\n" );
        sql.append("PART_CODE,\n" );
        sql.append("PART_NAME,\n" );
        sql.append("ROW_NUM,\n" );
        sql.append("PCH_PRICE,\n" );
        sql.append("PLAN_PRICE,\n" );
        sql.append("SALE_PRICE,\n" );
        sql.append("RETAIL_PRICE,\n" );
        sql.append("SUPPLIER_CODE,\n" );
        sql.append("SUPPLIER_NAME,\n" );
        sql.append("USER_ACCOUNT\n" );
        sql.append("FROM  PT_BA_PRICE_TMP");

        sql.append(" WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
        if (!errorInfoRowNum.equals("")) {
            sql.append(" AND ROW_NUM not in ( "+errorInfoRowNum+")\n" );
        }
        return factory.select(null, sql.toString());
    }
    /**
     * 查询配件价格临时表信息（校验临时表数据:2、重复数据校验）
     * @throws Exception
     * @Author yhw 2014-10-24
     */
    public QuerySet searchPtBaPriceTmpRepeatData(User user,String partCode,DBFactory factory) throws Exception{
        
        StringBuffer sql= new StringBuffer();
        if (!partCode.equals("")) {
            sql.append("SELECT A.ROW_NUM, A.PART_CODE\n" );
            sql.append("  FROM PT_BA_PRICE_TMP A,\n" );
            sql.append("       (" );
        }
        sql.append("SELECT PART_CODE, COUNT(PART_CODE)\n" );
        sql.append("          FROM PT_BA_PRICE_TMP\n" );
        sql.append("         WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
        sql.append("         GROUP BY PART_CODE\n" );
        sql.append("        HAVING COUNT(PART_CODE) > 1" );
        if (!partCode.equals("")) {
            sql.append(") B\n" );
            sql.append(" WHERE A.PART_CODE = B.PART_CODE\n" );
            sql.append("   AND A.PART_CODE = '"+partCode+"'\n" );
            sql.append("   AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
            sql.append("   ORDER BY A.ROW_NUM\n" );
        }
        return factory.select(null, sql.toString());
    }
    /**
     * 查询配件采购价格临时表信息（校验临时表数据:2、重复数据校验）
     * @throws Exception
     * @Author yhw 2014-10-24
     */
    public QuerySet searchPtBaPchPriceTmpRepeatData(User user,String partCode, String supplierCode,DBFactory factory) throws Exception{
        
        StringBuffer sql= new StringBuffer();
        if (!partCode.equals("")) {
            sql.append("SELECT A.ROW_NUM, A.PART_CODE, A.SUPPLIER_CODE\n" );
            sql.append("  FROM PT_BA_PCH_TMP A,\n" );
            sql.append("       (" );
        }
        sql.append("SELECT PART_CODE, SUPPLIER_CODE, COUNT(PART_CODE)\n" );
        sql.append("          FROM PT_BA_PCH_TMP\n" );
        sql.append("         WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
        sql.append("         GROUP BY PART_CODE, SUPPLIER_CODE\n" );
        sql.append("        HAVING COUNT(PART_CODE) > 1" );
        if (!partCode.equals("")) {
            sql.append(") B\n" );
            sql.append(" WHERE A.PART_CODE = B.PART_CODE\n" );
            sql.append("   AND A.SUPPLIER_CODE = B.SUPPLIER_CODE\n" );
            sql.append("   AND A.PART_CODE = '"+partCode+"'\n" );
            sql.append("   AND A.SUPPLIER_CODE = '"+supplierCode+"'\n" );
            sql.append("   AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
            sql.append("   ORDER BY A.ROW_NUM\n" );
        }
        return factory.select(null, sql.toString());
    }
	public QuerySet searchPtBaInfoTmp(User user, String errorInfoRowNum,DBFactory factory) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT\n" );
		sql.append("PART_ID,\n" );
		sql.append("PART_CODE,\n" );
		sql.append("PART_NAME,\n" );
		sql.append("PART_NO,\n" );
		sql.append("UNIT,\n" );
		sql.append("PART_TYPE,\n" );
		sql.append("BELONG_ASSEMBLY,\n" );
		sql.append("USER_ACCOUNT,\n" );
		sql.append("ATTRIBUTE,\n" );
		sql.append("IF_ASSEMBLY,\n" );
		sql.append("IF_OUT,\n" );
		sql.append("IF_SUPLY,\n" );
		sql.append("IF_OIL,\n" );
		sql.append("IF_SCAN,\n" );
		sql.append("PART_STATUS,\n" );
		sql.append("POSITION_NAME,\n" );
		sql.append("F_POSITION_NAME,\n" );
		sql.append("REBATE_TYPE,\n" );
		sql.append("DIRECT_TYPE_NAME,\n" );
		sql.append("F_PART_CODE,\n" );
		sql.append("F_PART_NAME,\n" );
		sql.append("MIN_PACK,\n" );
		sql.append("MIN_UNIT,\n" );
		sql.append("SPE_NAME,\n" );
		sql.append("ROW_NUM\n" );
		sql.append("FROM PT_BA_INFO_TMP");
	
		sql.append(" WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		
		if (!errorInfoRowNum.equals("")) {
			sql.append(" AND ROW_NUM not in ( "+errorInfoRowNum+")\n" );
		}
		return factory.select(null, sql.toString());
	}
	public QuerySet searchPtBaInfoTmpRepeatData(User user,String partCode,DBFactory factory)throws Exception{
		
    	StringBuffer sql= new StringBuffer();
    	if (!partCode.equals("")) {
	    	sql.append("SELECT A.ROW_NUM, A.PART_CODE\n" );
	    	sql.append("  FROM PT_BA_INFO_TMP A,\n" );
	    	sql.append("       (" );
    	}
    	sql.append("SELECT PART_CODE, COUNT(PART_CODE)\n" );
    	sql.append("          FROM PT_BA_INFO_TMP\n" );
    	sql.append("         WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("         GROUP BY PART_CODE\n" );
    	sql.append("        HAVING COUNT(PART_CODE) > 1" );
    	if (!partCode.equals("")) {
	    	sql.append(") B\n" );
	    	sql.append(" WHERE A.PART_CODE = B.PART_CODE\n" );
	    	sql.append("   AND A.PART_CODE = B.PART_CODE\n" );
	    	sql.append("   AND A.PART_CODE = '"+partCode+"'\n" );
	    	sql.append("   AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	    	sql.append("   ORDER BY A.ROW_NUM\n" );
    	}
		return factory.select(null, sql.toString());
	}
	public QuerySet searchPtBaInfoTmpRepeatData1(User user,String partCode,DBFactory factory)throws Exception{
		
    	StringBuffer sql= new StringBuffer();
    	if (!partCode.equals("")) {
	    	sql.append("SELECT A.ROW_NUM, A.PART_CODE\n" );
	    	sql.append("  FROM PT_BA_INFO_TMP A,\n" );
	    	sql.append("       (" );
    	}
    	sql.append("SELECT PART_CODE, COUNT(PART_CODE)\n" );
    	sql.append("          FROM PT_BA_INFO_TMP\n" );
    	sql.append("         WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
    	sql.append("         GROUP BY PART_CODE\n" );
    	sql.append("        HAVING COUNT(PART_CODE) > 1" );
    	if (!partCode.equals("")) {
	    	sql.append(") B\n" );
	    	sql.append(" WHERE A.PART_CODE = B.PART_CODE\n" );
	    	sql.append("   AND A.PART_CODE = B.PART_CODE\n" );
	    	sql.append("   AND A.PART_CODE = '"+partCode+"'\n" );
	    	sql.append("   AND A.USER_ACCOUNT = '"+user.getAccount()+"'\n" );
	    	sql.append("   ORDER BY A.ROW_NUM\n" );
    	}
		return factory.select(null, sql.toString());
	}
	public QuerySet checkStock(User user,DBFactory factory) throws Exception{
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT T.ROW_NUM, T.PART_CODE\n" );
		sql.append("  FROM PT_BA_INFO_TMP T\n" );
		sql.append(" WHERE USER_ACCOUNT = '"+user.getAccount()+"'\n" );
		sql.append("   AND (EXISTS (SELECT 1\n" );
		sql.append("                  FROM PT_BU_DEALER_STOCK T1\n" );
		sql.append("                 WHERE T.PART_CODE = T1.PART_CODE\n" );
		sql.append("                   AND NVL(T1.AMOUNT, 0) > 0) OR EXISTS\n" );
		sql.append("        (SELECT 1\n" );
		sql.append("           FROM PT_BU_STOCK T1\n" );
		sql.append("          WHERE T.PART_CODE = T1.PART_CODE\n" );
		sql.append("            AND NVL(T1.AMOUNT, 0) > 0))\n" );
		sql.append("   AND T.PART_STATUS != '否'");
        return factory.select(null, sql.toString());
    }
    
}
