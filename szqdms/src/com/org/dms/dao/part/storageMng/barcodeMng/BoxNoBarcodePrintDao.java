package com.org.dms.dao.part.storageMng.barcodeMng;

import com.org.dms.common.DicConstant;
import com.org.framework.base.BaseDAO;
import com.org.framework.base.BaseVO;
import com.org.framework.common.*;
import com.org.mvc.context.ActionContext;

/**
 * 库位条码打印Dao
 *
 * @user : lichuang
 * @date : 2014-08-01
 */
public class BoxNoBarcodePrintDao extends BaseDAO {
    //定义instance
    public static final BoxNoBarcodePrintDao getInstance(ActionContext atx) {
        BoxNoBarcodePrintDao dao = new BoxNoBarcodePrintDao();
        atx.setDBFactory(dao.factory);
        return dao;
    }

    /**
     * 新增VO
     * @param vo
     * @return
     */
    public boolean insertVO(BaseVO vo) throws Exception{
        return factory.insert(vo);
    }

    /**
     * 获取主键ID
     *
     * @return
     * @throws Exception
     */
    public String getId() throws Exception {
        return SequenceUtil.getCommonSerivalNumber(factory);
    }

}