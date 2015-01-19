package com.org.dms.dao.part.storageMng.barcodeMng;

import com.org.dms.common.DicConstant;
import com.org.dms.vo.part.PtBuScanCodeBarcodeVO;
import com.org.framework.base.BaseDAO;
import com.org.framework.common.BaseResultSet;
import com.org.framework.common.DBFactory;
import com.org.framework.common.PageManager;
import com.org.framework.common.QuerySet;
import com.org.framework.common.User;
import com.org.mvc.context.ActionContext;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 配件条码打印Dao
 *
 * @user : lichuang
 * @date : 2014-08-01
 */
public class PartBarcodePrintDao extends BaseDAO {
    //定义instance
    public static final PartBarcodePrintDao getInstance(ActionContext atx) {
        PartBarcodePrintDao dao = new PartBarcodePrintDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 查询配件
     *
     * @param page
     * @param user
     * @param conditions
     * @return
     * @throws Exception
     */
    public BaseResultSet searchPart(PageManager page, User user, String conditions) throws Exception {
        String wheres = conditions;
        wheres += "  AND A.PART_ID = B.PART_ID\n";
        wheres += "  AND B.SUPPLIER_ID = C.SUPPLIER_ID AND C.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n";
        wheres += "  AND A.IF_SUPLY = " + DicConstant.SF_01 + " AND B.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n";
        wheres += "  AND IF_SCAN = " + DicConstant.SF_01 + "\n";
        wheres += "  AND A.STATUS = " + DicConstant.YXBS_01 + "\n";
        //wheres += "  AND A.COMPANY_ID=" + user.getCompanyId() + "\n";
        wheres += " ORDER BY A.PART_CODE ASC\n";
        page.setFilter(wheres);
        //定义返回结果集
        BaseResultSet bs = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.PART_ID,\n");
        sql.append("       A.PART_CODE,\n");
        sql.append("       A.PART_NAME,\n");
        sql.append("       A.PART_NO,\n");
        sql.append("       A.UNIT,\n");
        sql.append("       A.MIN_PACK,\n");
        sql.append("       A.MIN_UNIT,\n");
        sql.append("       C.SUPPLIER_ID,\n");
        sql.append("       C.SUPPLIER_CODE,\n");
        sql.append("       C.SUPPLIER_NAME\n");
        sql.append("  FROM PT_BA_INFO A,PT_BA_PART_SUPPLIER_RL B,PT_BA_SUPPLIER C\n");

        //执行方法，不需要传递conn参数
        bs = factory.select(sql.toString(), page);
        bs.setFieldDic("UNIT", "JLDW");
        bs.setFieldDic("MIN_UNIT", "JLDW");
        return bs;
    }

    /**
     * 查询备件信息
     *
     * @param PART_ID
     * @return
     * @throws Exception
     */
    public QuerySet queryPart(String PART_ID) throws Exception {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT A.PART_ID,\n");
        sql.append("       A.PART_CODE,\n");
        sql.append("       A.PART_NAME,\n");
        sql.append("       A.PART_NO,\n");
        sql.append("       A.UNIT,\n");
        sql.append("       A.MIN_PACK,\n");
        sql.append("       A.MIN_UNIT,\n");
        sql.append("       C.SUPPLIER_ID,\n");
        sql.append("       C.SUPPLIER_CODE,\n");
        sql.append("       C.SUPPLIER_NAME\n");
        sql.append("  FROM PT_BA_INFO A,PT_BA_PART_SUPPLIER_RL B,PT_BA_SUPPLIER C\n");
        sql.append(" WHERE  1=1\n");
        sql.append("  AND A.PART_ID = B.PART_ID\n");
        sql.append("  AND B.SUPPLIER_ID = C.SUPPLIER_ID AND C.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n");
        sql.append("  AND A.PART_ID = " + PART_ID + " AND B.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n");
        return factory.select(null, sql.toString());
    }

    /**
     * 生成条码
     *
     * @return
     * @throws Exception
     */
    public String createBarcode() throws Exception {
        String barcode = "";
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT F_GETBARCODE() FROM DUAL\n");
        QuerySet qs = factory.select(null, sql.toString());
        if (qs.getRowCount() > 0) {
            String sqlBarcode = qs.getString(1, 1);
            if (null != sqlBarcode && !"".equals(sqlBarcode)) {
                barcode = sqlBarcode;
            } 
        }
        return barcode;
    }

    /**
     * 新增备件条码
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public boolean insertPartBarcode(DBFactory fac,PtBuScanCodeBarcodeVO vo) throws Exception {
        return fac.insert(vo);
    }
    public QuerySet queryPartInfo(DBFactory fac,String partId,String supplierId) throws Exception
    {
    	QuerySet qs = null;
    	StringBuffer sql= new StringBuffer();
    	sql.append("SELECT T.PART_CODE, T.PART_NAME, T2.SUPPLIER_NAME,T2.SUPPLIER_CODE,T.MIN_PACK\n" );
    	sql.append("  FROM PT_BA_INFO T, PT_BA_PART_SUPPLIER_RL T1, PT_BA_SUPPLIER T2\n" );
    	sql.append(" WHERE 1 = 1\n" );
    	sql.append("   AND T.PART_ID = T1.PART_ID AND T1.PART_IDENTIFY = "+DicConstant.YXBS_01+" AND T2.PART_IDENTIFY = "+DicConstant.YXBS_01+"\n" );
    	sql.append("   AND T1.SUPPLIER_ID = T2.SUPPLIER_ID AND T.PART_ID ="+partId+" AND T1.SUPPLIER_ID = "+supplierId+"");
    	qs = fac.select(null, sql.toString());
    	return qs;
    }
}