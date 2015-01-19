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
public class PartBarcodePrint2Dao {
   
	/**
     * 生成条码
     *
     * @return
     * @throws Exception
     */
    public String createBarcode(DBFactory fac) throws Exception {
        String barcode = "";
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT F_GETBARCODE() FROM DUAL\n");
        QuerySet qs = fac.select(null, sql.toString());
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