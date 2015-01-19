package com.org.dms.common;
public interface DicConstant {
	//是否
	public final static String SF="SF";
	public final static String SF_01 = "100101"; //是
	public final static String SF_02 = "100102"; //否
	//有效标识
	public final static String YXBS="YXBS";
	public final static String YXBS_01 = "100201"; //有效
	public final static String YXBS_02 = "100202"; //无效
	//性别
	public final static String XB="XB";
	public final static String XB_01 = "100301"; //女
	public final static String XB_02 = "100302"; //男
	//用户类型
	public final static String YHLX="YHLX";
	public final static String YHLX_01 = "100401"; //普通用户
	public final static String YHLX_02 = "100402"; //管理员
	public final static String YHLX_03 = "100403"; //超级用户
	//数据密级
	public final static String SJMJ="SJMJ";
	public final static String SJMJ_01 = "1"; //一级
	public final static String SJMJ_02 = "2"; //二级
	public final static String SJMJ_03 = "3"; //三级
	public final static String SJMJ_04 = "4"; //四级
	public final static String SJMJ_05 = "5"; //五级
	public final static String SJMJ_06 = "6"; //六级
	public final static String SJMJ_07 = "7"; //七级
	public final static String SJMJ_08 = "8"; //八级
	public final static String SJMJ_09 = "9"; //九级
	//机构级别
	public final static String JGJB="JGJB";
	public final static String JGJB_01 = "100701"; //销售公司
	public final static String JGJB_02 = "100702"; //销司部门
	public final static String JGJB_03 = "100703"; //大区
	public final static String JGJB_04 = "100704"; //办事处
	public final static String JGJB_05 = "100705"; //渠道网点
	public final static String JGJB_06 = "100706"; //车厂渠道商
	public final static String JGJB_07 = "100797"; //集团子公司
	public final static String JGJB_08 = "100798"; //集团部门
	public final static String JGJB_09 = "100799"; //集团公司
	//关系符号
	public final static String GXFH="GXFH";
	public final static String GXFH_01 = "100801"; //=
	public final static String GXFH_02 = "100802"; //(+)=
	public final static String GXFH_03 = "100803"; //=(+)
	public final static String GXFH_04 = "100804"; //!=
	public final static String GXFH_05 = "100805"; //>
	public final static String GXFH_06 = "100806"; //>=
	public final static String GXFH_07 = "100807"; //<
	public final static String GXFH_08 = "100808"; //<=
	public final static String GXFH_09 = "100809"; //IN
	public final static String GXFH_10 = "100810"; //NOT IN
	//启用标识
	public final static String QYBS="QYBS";
	public final static String QYBS_01 = "100901"; //启用
	public final static String QYBS_02 = "100902"; //停用
	//组织类别
	public final static String ZZLB="ZZLB";
	public final static String ZZLB_01 = "100002"; //集团子公司
	public final static String ZZLB_02 = "100003"; //集团部门
	public final static String ZZLB_03 = "100004"; //集团科室
	public final static String ZZLB_04 = "200000"; //销售公司
	public final static String ZZLB_05 = "200001"; //销司部门
	public final static String ZZLB_06 = "200002"; //销司科室
	public final static String ZZLB_07 = "200003"; //大区
	public final static String ZZLB_08 = "200004"; //办事处
	public final static String ZZLB_09 = "200005"; //配送中心
	public final static String ZZLB_10 = "200006"; //配件经销商
	public final static String ZZLB_11 = "200007"; //服务商
	public final static String ZZLB_12 = "300001"; //供应商
	public final static String ZZLB_13 = "300002"; //运送公司
	//业务类型
	public final static String YWLX="YWLX";
	public final static String YWLX_01 = "101101"; //配件业务
	public final static String YWLX_02 = "101102"; //服务业务
	public final static String YWLX_03 = "101103"; //配件+服务
	//公司类型
	public final static String GSLX="GSLX";
	public final static String GSLX_01 = "101201"; //车厂公司
	public final static String GSLX_02 = "101202"; //渠道公司
	//提醒信息状态
	public final static String TXXXZT="TXXXZT";
	public final static String TXXXZT_01 = "101301"; //未处理
	public final static String TXXXZT_02 = "101302"; //已知晓
	public final static String TXXXZT_03 = "101303"; //已完成
	//提醒类型
	public final static String TXLX="TXLX";
	public final static String TXLX_01 = "101401"; //待办
	public final static String TXLX_02 = "101402"; //提醒
	public final static String TXLX_03 = "101403"; //消息
	//提醒创建类型
	public final static String TXCJLX="TXCJLX";
	public final static String TXCJLX_01 = "101501"; //系统创建
	public final static String TXCJLX_02 = "101502"; //手工创建
	//组织业务状态
	public final static String ZZYWZT="ZZYWZT";
	public final static String ZZYWZT_01 = "101601"; //运营
	public final static String ZZYWZT_02 = "101602"; //整改
	public final static String ZZYWZT_03 = "101603"; //待撤
	public final static String ZZYWZT_04 = "101604"; //撤出
	//用户权限
	public final static String YHQX="YHQX";
	public final static String YHQX_01 = "0"; //内网用户
	public final static String YHQX_02 = "1"; //外网用户
	public final static String YHQX_03 = "2"; //不限制
	//采购合同状态
	public final static String CGHTZT="CGHTZT";
	public final static String CGHTZT_01 = "200101"; //未提交
	public final static String CGHTZT_02 = "200102"; //已提交
	public final static String CGHTZT_03 = "200103"; //已上传资料
	public final static String CGHTZT_04 = "200104"; //资料审核通过
	public final static String CGHTZT_05 = "200105"; //资料审核驳回
	public final static String CGHTZT_06 = "200106"; //审核通过
	public final static String CGHTZT_07 = "200107"; //审核驳回
	public final static String CGHTZT_08 = "200108"; //已关闭
	public final static String CGHTZT_09 = "200109"; //已归档
	public final static String CGHTZT_10 = "200110"; //已签订
	//合同类型
	public final static String HTLX="HTLX";
	public final static String HTLX_01 = "200201"; //陕重汽配套
	public final static String HTLX_02 = "200202"; //自产
	public final static String HTLX_03 = "200203"; //油品
	//促销活动类型
	public final static String CXHDLX="CXHDLX";
	public final static String CXHDLX_01 = "200301"; //折扣促销
	public final static String CXHDLX_02 = "200302"; //新品上市
	//配件类别
	public final static String PJLB="PJLB";
	public final static String PJLB_01 = "200401"; //混用件
	public final static String PJLB_02 = "200402"; //民品件
	public final static String PJLB_03 = "200403"; //军品件
	//促销活动状态
	public final static String CXHDZT="CXHDZT";
	public final static String CXHDZT_01 = "200501"; //已保存
	public final static String CXHDZT_02 = "200502"; //促销中
	public final static String CXHDZT_03 = "200503"; //已关闭
	//配件状态
	public final static String PJZT="PJZT";
	public final static String PJZT_01 = "200601"; //有效
	public final static String PJZT_02 = "200602"; //无效
	public final static String PJZT_03 = "200603"; //待报废
	//配件总成类别
	public final static String PJZCLB="PJZCLB";
	public final static String PJZCLB_01 = "200701"; //发动机总成
	public final static String PJZCLB_02 = "200702"; //变速箱总成
	public final static String PJZCLB_03 = "200703"; //车桥总成
	public final static String PJZCLB_04 = "200704"; //驾驶室总成
	public final static String PJZCLB_05 = "200705"; //壳体总成
	public final static String PJZCLB_06 = "200706"; //车架总成
	public final static String PJZCLB_07 = "200707"; //气瓶总成
	public final static String PJZCLB_08 = "200708"; //其他总成
	//配件预测状态
	public final static String PJYCZT="PJYCZT";
	public final static String PJYCZT_01 = "200801"; //未提报
	public final static String PJYCZT_02 = "200802"; //已提报
	//配件计划状态
	public final static String PJJHZT="PJJHZT";
	public final static String PJJHZT_01 = "200901"; //未提报
	public final static String PJJHZT_02 = "200902"; //已提报
	public final static String PJJHZT_03 = "200903"; //审核通过
	public final static String PJJHZT_04 = "200904"; //审核驳回
	//采购订单状态
	public final static String CGDDZT="CGDDZT";
	public final static String CGDDZT_01 = "201001"; //未提报
	public final static String CGDDZT_02 = "201002"; //已提报
	public final static String CGDDZT_03 = "201003"; //已确认
	public final static String CGDDZT_04 = "201004"; //已发货
	public final static String CGDDZT_05 = "201005"; //已关闭
	//采购退货单状态
	public final static String CGTHDZT="CGTHDZT";
	public final static String CGTHDZT_01 = "201101"; //未申请
	public final static String CGTHDZT_02 = "201102"; //已申请
	public final static String CGTHDZT_03 = "201103"; //已确认
	public final static String CGTHDZT_04 = "201104"; //已关闭
	//库存状态
	public final static String KCZT="KCZT";
	public final static String KCZT_01 = "201201"; //正常库存
	public final static String KCZT_02 = "201202"; //库存锁定
	public final static String KCZT_03 = "201203"; //盘点锁定
	//入库类型
	public final static String RKLX="RKLX";
	public final static String RKLX_01 = "201301"; //采购入库
	public final static String RKLX_02 = "201302"; //移库入库
	public final static String RKLX_03 = "201303"; //销售退件
	public final static String RKLX_04 = "201304"; //其他入库
	//出库类型
	public final static String CKLX="CKLX";
	public final static String CKLX_01 = "201401"; //销售出库
	public final static String CKLX_02 = "201402"; //直营出库
	public final static String CKLX_03 = "201403"; //移库出库
	public final static String CKLX_04 = "201404"; //采购退货
	public final static String CKLX_05 = "201405"; //其他出库
	public final static String CKLX_06 = "201406"; //三包急件出库
	//入库单状态
	public final static String RKDZT="RKDZT";
	public final static String RKDZT_01 = "201501"; //已保存
	public final static String RKDZT_02 = "201502"; //已入库
	//出库单状态
	public final static String CKDZT="CKDZT";
	public final static String CKDZT_01 = "201601"; //已保存
	public final static String CKDZT_02 = "201602"; //已出库
	//打印状态
	public final static String DYZT="DYZT";
	public final static String DYZT_01 = "201701"; //未打印
	public final static String DYZT_02 = "201702"; //已打印
	//盘点类型
	public final static String PDLX="PDLX";
	public final static String PDLX_01 = "201801"; //部分
	public final static String PDLX_02 = "201802"; //全部
	//盘点状态
	public final static String PDZT="PDZT";
	public final static String PDZT_01 = "201901"; //已保存
	public final static String PDZT_02 = "201902"; //盘点中
	public final static String PDZT_03 = "201903"; //已盘点
	public final static String PDZT_04 = "201904"; //已调整
	public final static String PDZT_05 = "201905"; //已确认
	public final static String PDZT_06 = "201906"; //已封存
	//盘点结果
	public final static String PDJG="PDJG";
	public final static String PDJG_01 = "202001"; //盘盈
	public final static String PDJG_02 = "202002"; //盘亏
	public final static String PDJG_03 = "202003"; //相符
	//发运单状态
	public final static String FYDZT="FYDZT";
	public final static String FYDZT_01 = "202101"; //已保存
	public final static String FYDZT_02 = "202102"; //已提交
	public final static String FYDZT_03 = "202103"; //已确认
	public final static String FYDZT_04 = "202104"; //已发运
	public final static String FYDZT_05 = "202105"; //已签收
	public final static String FYDZT_06 = "202106"; //确认回执
	public final static String FYDZT_07 = "202107"; //已结算
	//订单状态
	public final static String DDZT="DDZT";
	public final static String DDZT_01 = "202201"; //未提报
	public final static String DDZT_02 = "202202"; //已提报
	public final static String DDZT_03 = "202203"; //审核通过
	public final static String DDZT_04 = "202204"; //审核驳回
	public final static String DDZT_05 = "202205"; //已取消
	public final static String DDZT_06 = "202206"; //已关闭
	public final static String DDZT_07 = "202207"; //直营提报
	//开票状态
	public final static String KPZT="KPZT";
	public final static String KPZT_01 = "202301"; //未开票
	public final static String KPZT_02 = "202302"; //已开票
	//退件申请单状态
	public final static String TJSQDZT="TJSQDZT";
	public final static String TJSQDZT_01 = "202401"; //未申请
	public final static String TJSQDZT_02 = "202402"; //已申请
	public final static String TJSQDZT_03 = "202403"; //审核通过
	public final static String TJSQDZT_04 = "202404"; //审核驳回
	public final static String TJSQDZT_05 = "202405"; //已关闭
	public final static String TJSQDZT_06 = "202406"; //已出库
	//打款状态
	public final static String DKZT="DKZT";
	public final static String DKZT_01 = "202501"; //已保存
	public final static String DKZT_02 = "202502"; //已提交
	public final static String DKZT_03 = "202503"; //已确认
	//转账状态
	public final static String ZZZT="ZZZT";
	public final static String ZZZT_01 = "202601"; //已保存
	public final static String ZZZT_02 = "202602"; //已申请
	public final static String ZZZT_03 = "202603"; //审核通过
	public final static String ZZZT_04 = "202604"; //审核驳回
	//资金账户类型
	public final static String ZJZHLX="ZJZHLX";
	public final static String ZJZHLX_01 = "202701"; //现金
	public final static String ZJZHLX_02 = "202702"; //承兑
	public final static String ZJZHLX_03 = "202703"; //材料费
	public final static String ZJZHLX_04 = "202704"; //信用额度
	public final static String ZJZHLX_05 = "202705"; //返利
	//资金异动类型
	public final static String ZJYDLX="ZJYDLX";
	public final static String ZJYDLX_01 = "202801"; //打款
	public final static String ZJYDLX_02 = "202802"; //转出
	public final static String ZJYDLX_03 = "202803"; //转入
	public final static String ZJYDLX_04 = "202804"; //扣款
	public final static String ZJYDLX_05 = "202805"; //罚款
	public final static String ZJYDLX_06 = "202806"; //奖励
	public final static String ZJYDLX_07 = "202807"; //返利
	public final static String ZJYDLX_08 = "202808"; //调账
	public final static String ZJYDLX_09 = "202809"; //调账调出
	public final static String ZJYDLX_10 = "202810"; //调账调入
	
	//信用额度类型
	public final static String XYEDLX="XYEDLX";
	public final static String XYEDLX_01 = "202901"; //固定
	public final static String XYEDLX_02 = "202902"; //临时
	//返利状态
	public final static String FLZT="FLZT";
	public final static String FLZT_01 = "203001"; //已计算
	public final static String FLZT_02 = "203002"; //已调整
	public final static String FLZT_03 = "203003"; //审核通过
	public final static String FLZT_04 = "203004"; //审核驳回
	//发票邮寄状态
	public final static String FPYJZT="FPYJZT";
	public final static String FPYJZT_01 = "203101"; //未邮寄
	public final static String FPYJZT_02 = "203102"; //已邮寄
	public final static String FPYJZT_03 = "203103"; //已签收
	//采购订单类型
	public final static String CGDDLX="CGDDLX";
	public final static String CGDDLX_01 = "203201"; //月度计划
	public final static String CGDDLX_02 = "203202"; //缺件
	public final static String CGDDLX_03 = "203203"; //其他
	public final static String CGDDLX_04 = "203204"; //军品
	public final static String CGDDLX_05 = "203205"; //直发
	public final static String CGDDLX_06 = "203206"; //总成
	public final static String CGDDLX_07 = "203207"; //技术升级
	//采购类型
	public final static String CGLX="CGLX";
	public final static String CGLX_01 = "203301"; //月度计划
	public final static String CGLX_02 = "203302"; //缺件
	public final static String CGLX_03 = "203303"; //其他
	//采购类别
	public final static String CGLB="CGLB";
	public final static String CGLB_01 = "203401"; //采购
	public final static String CGLB_02 = "203402"; //领用
	//计量单位
	public final static String JLDW="JLDW";
	public final static String JLDW_01 = "203501"; //件
	public final static String JLDW_02 = "203502"; //包
	public final static String JLDW_03 = "203503"; //箱
	public final static String JLDW_04 = "203504"; //桶
	public final static String JLDW_05 = "203505"; //只
	public final static String JLDW_06 = "203506"; //台
	public final static String JLDW_07 = "203507"; //套
	public final static String JLDW_08 = "203508"; //片
	public final static String JLDW_09 = "203509"; //kg
	public final static String JLDW_10 = "203510"; //组
	public final static String JLDW_11 = "203511"; //张
	public final static String JLDW_12 = "203512"; //个
	public final static String JLDW_13 = "203513"; //根
	public final static String JLDW_14 = "203514"; //块
	public final static String JLDW_15 = "203515"; //架
	public final static String JLDW_16 = "203516"; //条
	public final static String JLDW_17 = "203517"; //米
	public final static String JLDW_18 = "203518"; //辆
	public final static String JLDW_19 = "203519"; //对
	public final static String JLDW_20 = "203520"; //瓶
	//配件属性
	public final static String PJSX="PJSX";
	public final static String PJSX_01 = "203601"; //常用件
	public final static String PJSX_02 = "203602"; //非常用件
	public final static String PJSX_03 = "203603"; //易损件
	//订单类型
	public final static String DDLX="DDLX";
	public final static String DDLX_01 = "203701"; //月度订单
	public final static String DDLX_02 = "203702"; //周度订单
	public final static String DDLX_03 = "203703"; //临时订单
	public final static String DDLX_04 = "203704"; //总成订单
	public final static String DDLX_05 = "203705"; //直发订单
	public final static String DDLX_06 = "203706"; //促销订单
	public final static String DDLX_07 = "203707"; //直营订单
	public final static String DDLX_08 = "203708"; //军品订单
	public final static String DDLX_09 = "203709"; //三包急件
	public final static String DDLX_10 = "203710"; //技术升级
	public final static String DDLX_11 = "203711"; //预投订单
	public final static String DDLX_12 = "203712"; //整改订单
	public final static String DDLX_99 = "203799"; // 调拨订单
	
	//库区属性
	public final static String KQSX="KQSX";
	public final static String KQSX_01 = "203801"; //发动机库
	public final static String KQSX_02 = "203802"; //电器件
	public final static String KQSX_03 = "203803"; //自制件
	public final static String KQSX_04 = "203804"; //轴承库
	public final static String KQSX_05 = "203805"; //汉德桥
	public final static String KQSX_06 = "203806"; //领用库
	public final static String KQSX_07 = "203807"; //大件库
	public final static String KQSX_08 = "203808"; //齐星库
	public final static String KQSX_09 = "203809"; //大总成库
	public final static String KQSX_10 = "203810"; //内饰件
	public final static String KQSX_11 = "203811"; //大件二库
	//发运方式
	public final static String FYFS="FYFS";
	public final static String FYFS_01 = "203901"; //发运
	public final static String FYFS_02 = "203902"; //自提
	//交货地点类型
	public final static String JHDDLX="JHDDLX";
	public final static String JHDDLX_01 = "204001"; //小库
	public final static String JHDDLX_02 = "204002"; //大库
	//操作类型
	public final static String CZLX="CZLX";
	public final static String CZLX_01 = "204101"; //入库
	public final static String CZLX_02 = "204102"; //出库
	//采购退货类型
	public final static String CGTHLX="CGTHLX";
	public final static String CGTHLX_01 = "204201"; //采购退回
	public final static String CGTHLX_02 = "204202"; //领用退回
	//退货单状态
	public final static String THDZT="THDZT";
	public final static String THDZT_01 = "204301"; //未提交
	public final static String THDZT_02 = "204302"; //已提交
	public final static String THDZT_03 = "204303"; //已确认
	public final static String THDZT_04 = "204304"; //已关闭
	//退货单验收状态
	public final static String THDYSZT="THDYSZT";
	public final static String THDYSZT_01 = "204401"; //已验收
	public final static String THDYSZT_02 = "204402"; //未验收
	//发料单发料状态
	public final static String FLDFLZT="FLDFLZT";
	public final static String FLDFLZT_01 = "204501"; //未发料
	public final static String FLDFLZT_02 = "204502"; //已发料
	//发票类型
	public final static String FPLX="FPLX";
	public final static String FPLX_01 = "204601"; //普通发票
	public final static String FPLX_02 = "204602"; //增值税发票
	//渠道出入库类型
	public final static String QDCRKLX="QDCRKLX";
	public final static String QDCRKLX_01 = "204701"; //销售出库
	public final static String QDCRKLX_02 = "204702"; //订单验收入库
	public final static String QDCRKLX_03 = "204703"; //实销出库
	public final static String QDCRKLX_04 = "204704"; //实销退件入库
	public final static String QDCRKLX_05 = "204705"; //销售退件出库
	public final static String QDCRKLX_06 = "204706"; //销售退件入库
	//订单发运状态
	public final static String DDFYZT="DDFYZT";
	public final static String DDFYZT_01 = "204801"; //未发料
	public final static String DDFYZT_02 = "204802"; //发料单生成
	public final static String DDFYZT_03 = "204803"; //已发料
	public final static String DDFYZT_04 = "204804"; //已装箱
	public final static String DDFYZT_05 = "204805"; //未发运
	public final static String DDFYZT_06 = "204806"; //已发运
	public final static String DDFYZT_07 = "204807"; //已签收
	//交货地址变更状态
	public final static String JHDZBGZT="JHDZBGZT";
	public final static String JHDZBGZT_01 = "204901"; //已保存
	public final static String JHDZBGZT_02 = "204902"; //已提报
	public final static String JHDZBGZT_03 = "204903"; //审核通过
	public final static String JHDZBGZT_04 = "204904"; //审核驳回
	//实销单状态
	public final static String SXDZT="SXDZT";
	public final static String SXDZT_01 = "205001"; //已保存
	public final static String SXDZT_02 = "205002"; //已出库
	//其他出库出库类型
	public final static String QTCKCKLX="QTCKCKLX";
	public final static String QTCKCKLX_01 = "205101"; //三包领用（调度）
	public final static String QTCKCKLX_02 = "205102"; //配件公司自行耗用
	public final static String QTCKCKLX_03 = "205103"; //厂内部门管理耗用
	public final static String QTCKCKLX_04 = "205104"; //其他
	//供应商开票状态
	public final static String GYSKPZT="GYSKPZT";
	public final static String GYSKPZT_01 = "205201"; //已出帐
	public final static String GYSKPZT_02 = "205202"; //通知开票
	public final static String GYSKPZT_03 = "205203"; //已提交
	public final static String GYSKPZT_04 = "205204"; //审核通过
	public final static String GYSKPZT_05 = "205205"; //审核驳回
	public final static String GYSKPZT_06 = "205206"; //已开票
	//供应商结算状态
	public final static String GYSJSZT="GYSJSZT";
	public final static String GYSJSZT_01 = "205301"; //未结算
	public final static String GYSJSZT_02 = "205302"; //已结算
	public final static String GYSJSZT_03 = "205303"; //结算中
	//配件三包索赔状态
	public final static String PJSBSPZT="PJSBSPZT";
	public final static String PJSBSPZT_01 = "205401"; //已保存
	public final static String PJSBSPZT_02 = "205402"; //已提报
	public final static String PJSBSPZT_03 = "205403"; //审核驳回
	public final static String PJSBSPZT_04 = "205404"; //初审通过
	public final static String PJSBSPZT_05 = "205405"; //终审通过
	public final static String PJSBSPZT_06 = "205406"; //作废
	//配件价格类型
	public final static String PJJGLX="PJJGLX";
	public final static String PJJGLX_01 = "205501"; //零售价格
	public final static String PJJGLX_02 = "205502"; //销售价格
	public final static String PJJGLX_03 = "205503"; //军品价格
	public final static String PJJGLX_04 = "205504"; //计划价格
	public final static String PJJGLX_05 = "205505"; //采购价格
	public final static String PJJGLX_06 = "205506"; //服务索赔价格
	public final static String PJJGLX_07 = "205507"; //服务追偿价格
	//配件三包索赔结算状态
	public final static String PJSBSPJSZT="PJSBSPJSZT";
	public final static String PJSBSPJSZT_01 = "205601"; //未结算
	public final static String PJSBSPJSZT_02 = "205602"; //已结算
	//运费结算状态
	public final static String YSJSZT="YSJSZT";
	public final static String YSJSZT_01 = "205701"; //未结算
	public final static String YSJSZT_02 = "205702"; //已结算
	//评审职能
	public final static String PSZN="PSZN";
	public final static String PSZN_01 = "205801"; //组长
	public final static String PSZN_02 = "205802"; //组员
	//承运车型
	public final static String CYCX="CYCX";
	public final static String CYCX_01 = "205901"; //微型载货车/总重量（G）小于等于1.8吨
	public final static String CYCX_02 = "205902"; //轻型载货车/总重量（G）大于1.8吨小于等于6吨
	public final static String CYCX_03 = "205903"; //中型载货车/总重量（G）大于6吨小于等于14吨
	public final static String CYCX_04 = "205904"; //重型载货车/总重量（G）大于14吨
	//活动流程状态
	public final static String HDLCZT="HDLCZT";
	public final static String HDLCZT_01 = "206001"; //已保存
	public final static String HDLCZT_02 = "206002"; //已下发
	//活动执行状态
	public final static String HDZXZT="HDZXZT";
	public final static String HDZXZT_01 = "206101"; //已保存
	public final static String HDZXZT_02 = "206102"; //已提报
	public final static String HDZXZT_03 = "206103"; //审核通过
	public final static String HDZXZT_04 = "206104"; //审核驳回
	//价差类型
	public final static String JCLX="JCLX";
	public final static String JCLX_01 = "206201"; //一次应补
	public final static String JCLX_02 = "206202"; //二次应补
	//总成附件确认状态
	public final static String ZCFJQRZT="ZCFJQRZT";
	public final static String ZCFJQRZT_01 = "206301"; //已保存
	public final static String ZCFJQRZT_02 = "206302"; //已提交
	public final static String ZCFJQRZT_03 = "206303"; //已确认
	public final static String ZCFJQRZT_04 = "206304"; //已关闭
	//供应商账户类型
	public final static String GYSZHLX="GYSZHLX";
	public final static String GYSZHLX_01 = "206401"; //现金
	public final static String GYSZHLX_02 = "206402"; //承兑
	public final static String GYSZHLX_03 = "206403"; //转账
	
	//结算类型
	public final static String PJJSLX="PJJSLX";
	public final static String PJJSLX_01 = "206501"; //供应商结算
	public final static String PJJSLX_02 = "206502"; //渠道商结算

	//统计状态
	public final static String TJZT="TJZT";
	public final static String TJZT_01 = "206601"; //未统计
	public final static String TJZT_02 = "206602"; //已统计
	
	//车辆用户类型
	public final static String CLYHLX="CLYHLX";
	public final static String CLYHLX_01 = "300101"; //民用
	public final static String CLYHLX_02 = "300102"; //军用
	//车辆用途
	public final static String CLYT="CLYT";
	public final static String CLYT_01 = "300201"; //非公路用车
	public final static String CLYT_02 = "300202"; //公路用车
	//预授权类型
	public final static String YSQLX="YSQLX";
	public final static String YSQLX_01 = "300301"; //照顾性保修
	public final static String YSQLX_02 = "300302"; //跨区服务
	public final static String YSQLX_03 = "300303"; //其它类型
	public final static String YSQLX_04 = "300304"; //单车材料费
	//预授权状态
	public final static String YSQZT="YSQZT";
	public final static String YSQZT_01 = "300401"; //已保存
	public final static String YSQZT_02 = "300402"; //已提报
	public final static String YSQZT_03 = "300403"; //审核驳回
	public final static String YSQZT_04 = "300404"; //审核通过
	//活动类别
	public final static String HDLB="HDLB";
	public final static String HDLB_01 = "300501"; //整改
	public final static String HDLB_02 = "300502"; //促销
	//处理方式
	public final static String CLFS="CLFS";
	public final static String CLFS_01 = "300601"; //维修
	public final static String CLFS_02 = "300602"; //零件更换
	public final static String CLFS_03 = "300603"; //加装
	//活动状态
	public final static String HDZT="HDZT";
	public final static String HDZT_01 = "300701"; //未发布
	public final static String HDZT_02 = "300702"; //已发布
	public final static String HDZT_03 = "300703"; //已取消
	public final static String HDZT_04 = "300704"; //已关闭
	//车辆状态销售
	public final static String CLXSZT="CLXSZT";
	public final static String CLXSZT_01 = "300801"; //未销售
	public final static String CLXSZT_02 = "300802"; //已销售
	//车辆状态
	public final static String CLZT="CLZT";
	public final static String CLZT_01 = "300901"; //生产线
	public final static String CLZT_02 = "300902"; //车厂库存
	public final static String CLZT_03 = "300903"; //发运在途
	public final static String CLZT_04 = "300904"; //经销商库存
	public final static String CLZT_05 = "300905"; //已销售
	public final static String CLZT_06 = "300906"; //无效
	//索赔单状态
	public final static String SPDZT="SPDZT";
	public final static String SPDZT_01 = "301001"; //未提报
	public final static String SPDZT_02 = "301002"; //已提报
	public final static String SPDZT_03 = "301003"; //自动审核通过
	public final static String SPDZT_04 = "301004"; //自动审核驳回
	public final static String SPDZT_05 = "301005"; //初审通过
	public final static String SPDZT_06 = "301006"; //初审退回
	public final static String SPDZT_07 = "301007"; //初审作废
	public final static String SPDZT_08 = "301008"; //放弃提报
	public final static String SPDZT_09 = "301009"; //转领导审批
	public final static String SPDZT_10 = "301010"; //领导审批完成
	public final static String SPDZT_11 = "301011"; //重新提单驳回
	public final static String SPDZT_12 = "301012"; //重新提单通过
	public final static String SPDZT_13 = "301013"; //里程调整驳回
	public final static String SPDZT_14 = "301014"; //里程调整通过
	public final static String SPDZT_15 = "301015"; //终审通过
	//服务类型
	public final static String FWLX="FWLX";
	public final static String FWLX_01 = "301101"; //送修
	public final static String FWLX_02 = "301102"; //救援
	public final static String FWLX_03 = "301103"; //跟踪
	//外出次数
	public final static String WCCS="WCCS";
	public final static String WCCS_01 = "301201"; //一次外出
	public final static String WCCS_02 = "301202"; //二次外出
	//外出方式
	public final static String WCFS="WCFS";
	public final static String WCFS_01 = "301301"; //自备车
	public final static String WCFS_02 = "301302"; //非自备车
	//索赔单类型
	public final static String SPDLX="SPDLX";
	public final static String SPDLX_01 = "301401"; //正常保修
	public final static String SPDLX_02 = "301402"; //强保
	public final static String SPDLX_03 = "301403"; //服务活动
	public final static String SPDLX_04 = "301404"; //售前维修
	public final static String SPDLX_05 = "301405"; //定保
	public final static String SPDLX_06 = "301406"; //售前培训检查
	public final static String SPDLX_07 = "301407"; //安全检查
	public final static String SPDLX_08 = "301408"; //照顾性保修
	public final static String SPDLX_09 = "301409"; //事故车维修
	//保修人类型
	public final static String BXRLX="BXRLX";
	public final static String BXRLX_01 = "301501"; //车主
	public final static String BXRLX_02 = "301502"; //家人
	public final static String BXRLX_03 = "301503"; //司机
	public final static String BXRLX_04 = "301504"; //其他
	public final static String BXRLX_05 = "301505"; //朋友
	//故障类别
	public final static String GZLB="GZLB";
	public final static String GZLB_01 = "301601"; //主损件
	public final static String GZLB_02 = "301602"; //附损件
	//新件来源
	public final static String XJLY="XJLY";
	public final static String XJLY_01 = "301701"; //站内储备
	public final static String XJLY_02 = "301702"; //紧急自购
	//项目类型
	public final static String XMLX="XMLX";
	public final static String XMLX_01 = "301801"; //作业项目
	public final static String XMLX_02 = "301802"; //更换零件
	public final static String XMLX_03 = "301803"; //其它项目
	//审核类型
	public final static String SHLX="SHLX";
	public final static String SHLX_01 = "301901"; //人工审核
	public final static String SHLX_02 = "301902"; //自动审核
	//审核结果
	public final static String SHJG="SHJG";
	public final static String SHJG_01 = "302001"; //审核通过
	public final static String SHJG_02 = "302002"; //审核驳回
	//派工类型
	public final static String PGLX="PGLX";
	public final static String PGLX_01 = "302101"; //渠道商派工
	public final static String PGLX_02 = "302102"; //400派工
	//派工单状态
	public final static String PGDZT="PGDZT";
	public final static String PGDZT_01 = "302201"; //未派工
	public final static String PGDZT_02 = "302202"; //已派工
	public final static String PGDZT_03 = "302203"; //工单完成
	public final static String PGDZT_04 = "302204"; //已转索赔单
	public final static String PGDZT_05 = "302205"; //放弃派工
	//外出时间
	public final static String WCSJ="WCSJ";
	public final static String WCSJ_01 = "302301"; //白天
	public final static String WCSJ_02 = "302302"; //夜间
	//日期类型
	public final static String RQLX="RQLX";
	public final static String RQLX_01 = "302401"; //销售日期
	public final static String RQLX_02 = "302402"; //生产日期
	//回运单状态
	public final static String HYDZT="HYDZT";
	public final static String HYDZT_01 = "302501"; //已保存
	public final static String HYDZT_02 = "302502"; //已提报
	public final static String HYDZT_03 = "302503"; //审核通过
	public final static String HYDZT_04 = "302504"; //审核驳回
	public final static String HYDZT_05 = "302505"; //已回运
	public final static String HYDZT_06 = "302506"; //终审完成
	//回运单运输方式
	public final static String HYDYSFS="HYDYSFS";
	public final static String HYDYSFS_01 = "302601"; //配货
	public final static String HYDYSFS_02 = "302602"; //专车
	public final static String HYDYSFS_03 = "302603"; //铁路
	public final static String HYDYSFS_04 = "302604"; //空运
	//代码类别
	public final static String DMLB="DMLB";
	public final static String DMLB_01 = "302701"; //故障来源
	public final static String DMLB_02 = "302702"; //故障类别
	public final static String DMLB_03 = "302703"; //严重程度
	//自动审核状态
	public final static String ZDSHZT="ZDSHZT";
	public final static String ZDSHZT_01 = "302801"; //停止
	public final static String ZDSHZT_02 = "302802"; //启动
	//自动审核规则
	public final static String ZDSHGZ="ZDSHGZ";
	public final static String ZDSHGZ_01 = "302901"; //退回规则
	public final static String ZDSHGZ_02 = "302902"; //拒绝规则
	//不回运申请状态
	public final static String BHYSQZT="BHYSQZT";
	public final static String BHYSQZT_01 = "303001"; //已申请
	public final static String BHYSQZT_02 = "303002"; //同意延返
	public final static String BHYSQZT_03 = "303003"; //拒绝延返
	//旧件出库类型
	public final static String JJCKLX="JJCKLX";
	public final static String JJCKLX_01 = "303101"; //认领
	public final static String JJCKLX_02 = "303102"; //旧件销毁
	public final static String JJCKLX_03 = "303103"; //其它出库
	//定保单状态
	public final static String DBDZT="DBDZT";
	public final static String DBDZT_01 = "303201"; //已保存
	public final static String DBDZT_02 = "303202"; //已提报
	//激励类型
	public final static String JLLX="JLLX";
	public final static String JLLX_01 = "303301"; //大客户
	public final static String JLLX_02 = "303302"; //产品激励
	//三包急件申请状态
	public final static String SBJJSQZT="SBJJSQZT";
	public final static String SBJJSQZT_01 = "303401"; //已保存
	public final static String SBJJSQZT_02 = "303402"; //已通过
	public final static String SBJJSQZT_03 = "303403"; //已驳回
	//购车数量
	public final static String GCSL="GCSL";
	public final static String GCSL_01 = "303501"; //50辆以上
	public final static String GCSL_02 = "303502"; //10-50辆间
	public final static String GCSL_03 = "303503"; //10辆以下
	public final static String GCSL_04 = "303504"; //1辆
	//驾驶员状态
	public final static String JSYZT="JSYZT";
	public final static String JSYZT_01 = "303601"; //固定
	public final static String JSYZT_02 = "303602"; //不固定
	//车辆用途
	public final static String FKCLYT="FKCLYT";
	public final static String FKCLYT_01 = "303701"; //运输
	public final static String FKCLYT_02 = "303702"; //工程
	public final static String FKCLYT_03 = "303703"; //专用
	public final static String FKCLYT_04 = "303704"; //其他
	//日作业时间
	public final static String RZYSJ="RZYSJ";
	public final static String RZYSJ_01 = "303801"; //8小时以上
	public final static String RZYSJ_02 = "303802"; //4-8小时间
	public final static String RZYSJ_03 = "303803"; //4小时以下
	//车辆故障地点
	public final static String CLGZDD="CLGZDD";
	public final static String CLGZDD_01 = "303901"; //公路
	public final static String CLGZDD_02 = "303902"; //非公路
	public final static String CLGZDD_03 = "303903"; //矿
	//日常道路状况
	public final static String RCDLZK="RCDLZK";
	public final static String RCDLZK_01 = "304001"; //高速
	public final static String RCDLZK_02 = "304002"; //普通
	public final static String RCDLZK_03 = "304003"; //非硬化路面
	public final static String RCDLZK_04 = "304004"; //坡道
	//保养状况
	public final static String BYZK="BYZK";
	public final static String BYZK_01 = "304101"; //良好
	public final static String BYZK_02 = "304102"; //一般
	public final static String BYZK_03 = "304103"; //差
	//车辆状况
	public final static String CLZK="CLZK";
	public final static String CLZK_01 = "304201"; //标载
	public final static String CLZK_02 = "304202"; //超载
	public final static String CLZK_03 = "304203"; //严重超载
	//反馈途径
	public final static String FKTJ="FKTJ";
	public final static String FKTJ_01 = "304301"; //已发邮箱
	//质量反馈状态
	public final static String ZLFKZT="ZLFKZT";
	public final static String ZLFKZT_01 = "304401"; //已保存
	public final static String ZLFKZT_02 = "304402"; //已提报
	public final static String ZLFKZT_03 = "304403"; //已处理
	public final static String ZLFKZT_04 = "304404"; //已驳回
	public final static String ZLFKZT_05 = "304405"; //已关闭
	//结算状态
	public final static String JSZT="JSZT";
	public final static String JSZT_01 = "304501"; //已生成
	public final static String JSZT_02 = "304502"; //开票通知
	public final static String JSZT_03 = "304503"; //已打印
	public final static String JSZT_04 = "304504"; //发票邮寄
	public final static String JSZT_05 = "304505"; //发票签收
	public final static String JSZT_06 = "304506"; //已结算
	public final static String JSZT_07 = "304507"; //已付款
	//结算类型
	public final static String JSLX="JSLX";
	public final static String JSLX_01 = "304601"; //服务费
	public final static String JSLX_02 = "304602"; //材料费
	//通告发布状态
	public final static String TGFBZT="TGFBZT";
	public final static String TGFBZT_01 = "304701"; //未发布
	public final static String TGFBZT_02 = "304702"; //已发布
	public final static String TGFBZT_03 = "304703"; //已归档
	//通告签收状态
	public final static String TGQSZT="TGQSZT";
	public final static String TGQSZT_01 = "304801"; //未签收
	public final static String TGQSZT_02 = "304802"; //已签收
	//车辆登记状态
	public final static String CLDJZT="CLDJZT";
	public final static String CLDJZT_01 = "304901"; //未登记
	public final static String CLDJZT_02 = "304902"; //已登记
	//驱动形式
	public final static String QDXS="QDXS";
	public final static String QDXS_01 = "305001"; //4X2
	public final static String QDXS_02 = "305002"; //6X2
	public final static String QDXS_03 = "305003"; //6X4
	public final static String QDXS_04 = "305004"; //6X6
	public final static String QDXS_05 = "305005"; //8X2
	public final static String QDXS_06 = "305006"; //8X4
	public final static String QDXS_07 = "305007"; //8X6
	public final static String QDXS_08 = "305008"; //8X8
	public final static String QDXS_09 = "305009"; //10X2
	public final static String QDXS_10 = "305010"; //10X4
	public final static String QDXS_11 = "305011"; //10X6
	public final static String QDXS_12 = "305012"; //10X8
	public final static String QDXS_13 = "305013"; //10X10
	public final static String QDXS_14 = "305014"; //12X2
	public final static String QDXS_15 = "305015"; //12X4
	public final static String QDXS_16 = "305016"; //12X6
	public final static String QDXS_17 = "305017"; //12X8
	public final static String QDXS_18 = "305018"; //12X10
	public final static String QDXS_19 = "305019"; //12X12
	public final static String QDXS_20 = "305020"; //4X4
	public final static String QDXS_21 = "305021"; //6X2(双前轴)
	//费用类别
	public final static String FYLB="FYLB";
	public final static String FYLB_01 = "305101"; //在途补助
	public final static String FYLB_02 = "305102"; //服务车费
	public final static String FYLB_03 = "305103"; //差旅费
	//重新申请状态
	public final static String CXSQZT="CXSQZT";
	public final static String CXSQZT_01 = "305201"; //已申请
	public final static String CXSQZT_02 = "305202"; //审核驳回
	public final static String CXSQZT_03 = "305203"; //审核通过
	//里程申请状态
	public final static String LCSQZT="LCSQZT";
	public final static String LCSQZT_01 = "305301"; //已申请
	public final static String LCSQZT_02 = "305302"; //审核驳回
	public final static String LCSQZT_03 = "305303"; //审核通过
	//旧件库存类型
	public final static String JJKCLX="JJKCLX";
	public final static String JJKCLX_01 = "305401"; //旧件库
	public final static String JJKCLX_02 = "305402"; //配件三包库
	public final static String JJKCLX_03 = "305403"; //东郊库
	public final static String JJKCLX_04 = "305404"; //北郊库
	//授权关系
	public final static String SQGX="SQGX";
	public final static String SQGX_01 = "305501"; //and
	public final static String SQGX_02 = "305502"; //or
	//预授权比较符
	public final static String YSQBJF="YSQBJF";
	public final static String YSQBJF_01 = "305601"; //Begin
	public final static String YSQBJF_02 = "305602"; //Equal
	public final static String YSQBJF_03 = "305603"; //notBegin
	public final static String YSQBJF_04 = "305604"; //notEqual
	//入账类型
	public final static String RZLX="RZLX";
	public final static String RZLX_01 = "305701"; //材料费
	public final static String RZLX_02 = "305702"; //服务费
	//旧件状态
	public final static String JJZT="JJZT";
	public final static String JJZT_01 = "305801"; //保存
	public final static String JJZT_02 = "305802"; //审核通过
	public final static String JJZT_03 = "305803"; //待审核
	public final static String JJZT_04 = "305804"; //缺失
	//车辆销售日期状态
	public final static String CLXSRQZT="CLXSRQZT";
	public final static String CLXSRQZT_01 = "305901"; //未审批
	public final static String CLXSRQZT_02 = "305902"; //审批通过
	public final static String CLXSRQZT_03 = "305903"; //审批驳回
	//终审状态
	public final static String ZSZT="ZSZT";
	public final static String ZSZT_01 = "306001"; //终审通过
	//授权项目
	public final static String SQXM="SQXM";
	public final static String SQXM_01 = "306101"; //金额
	public final static String SQXM_02 = "306102"; //类型
	//积压件申请状态
	public final static String JYJSQZT="JYJSQZT";
	public final static String JYJSQZT_01 = "306201"; //未提交
	public final static String JYJSQZT_02 = "306202"; //已提交待确认
	public final static String JYJSQZT_03 = "306203"; //确认通过待审核
	public final static String JYJSQZT_04 = "306204"; //确认驳回
	public final static String JYJSQZT_05 = "306205"; //审核通过
	public final static String JYJSQZT_06 = "306206"; //审核驳回
	//旧件修改申请状态
	public final static String JJXGSQZT="JJXGSQZT";
	public final static String JJXGSQZT_01 = "306301"; //申请
	public final static String JJXGSQZT_02 = "306302"; //审批通过
	public final static String JJXGSQZT_03 = "306303"; //审批驳回
	//旧件修改类型
	public final static String JJXGLX="JJXGLX";
	public final static String JJXGLX_01 = "306401"; //修改供应商
	public final static String JJXGLX_02 = "306402"; //修改配件
	//乔类型
	public final static String QLX="QLX";
	public final static String QLX_01 = "306601"; //第一桥
	public final static String QLX_02 = "306602"; //第二桥
	public final static String QLX_03 = "306603"; //第三桥
	public final static String QLX_04 = "306604"; //第四桥
	public final static String QLX_05 = "306605"; //第五桥
	public final static String QLX_06 = "306606"; //第六桥
	public final static String QLX_07 = "306607"; //第七桥
	public final static String QLX_08 = "306608"; //第八桥
	public final static String QLX_09 = "306609"; //第九桥
	public final static String QLX_10 = "306610"; //第十桥
	//配件维护申请状态
	public final static String PJWHSQZT="PJWHSQZT";
	public final static String PJWHSQZT_01 = "307101"; // 未提交
	public final static String PJWHSQZT_02 = "307102"; // 已提交
	public final static String PJWHSQZT_03 = "307103"; // 技术科审核通过
	public final static String PJWHSQZT_04 = "307104"; // 技术科审核驳回
	public final static String PJWHSQZT_05 = "307105"; // 采供科审核通过
	public final static String PJWHSQZT_06 = "307106"; // 采供科审核驳回
	public final static String PJWHSQZT_07 = "307107"; // 技术科审核暂存
	public final static String PJWHSQZT_08 = "307108"; // 采供科审核暂存
	
	//配件维护申请类型
	public final static String PJWHSQLX="PJWHSQLX";
	public final static String PJWHSQLX_01 = "307201"; //零件编号录入
	public final static String PJWHSQLX_02 = "307202"; //驾驶室总成录入
	public final static String PJWHSQLX_03 = "307203"; //驾驶室本体录入
	public final static String PJWHSQLX_04 = "307204"; //零件编号变更(禁用)
	public final static String PJWHSQLX_05 = "307205"; //供货商变更(禁用)
	//配件维护车体类型
	public final static String PJWHCTLX="PJWHCTLX";
	public final static String PJWHCTLX_01 = "307301"; //德龙F3000
	public final static String PJWHCTLX_02 = "307302"; //德龙F2000
	public final static String PJWHCTLX_03 = "307303"; //M3000
	public final static String PJWHCTLX_04 = "307304"; //新M3000
	public final static String PJWHCTLX_05 = "307305"; //奥龙
	public final static String PJWHCTLX_06 = "307306"; //偏置码头车
	public final static String PJWHCTLX_07 = "307307"; //非公路矿用车
	//供应商照片上传
	public final static String GYSPJZPSC="GYSPJZPSC";
	public final static String GYSPJZPSC_01 = "307401"; //未提交
	public final static String GYSPJZPSC_02 = "307402"; //已提交
	public final static String GYSPJZPSC_03 = "307403"; //审核通过
	public final static String GYSPJZPSC_04 = "307404"; //审核驳回
	//供应商意见状态
	public final static String GYSYJZT="GYSYJZT";
	public final static String GYSYJZT_01 = "307501"; //同意
	public final static String GYSYJZT_02 = "307502"; //意见
	//实销类型
	public final static String SXLX="SXLX";
	public final static String SXLX_01 = "307601"; //销售出库
	public final static String SXLX_02 = "307602"; //索赔出库
	//索赔单流程状态
	public final static String SPDLCZT="SPDLCZT";
	public final static String SPDLCZT_01 = "307701"; //未提报
	public final static String SPDLCZT_02 = "307702"; //已提报
	public final static String SPDLCZT_03 = "307703"; //自动审核通过
	public final static String SPDLCZT_04 = "307704"; //自动审核驳回
	public final static String SPDLCZT_05 = "307705"; //办事处审核通过
	public final static String SPDLCZT_06 = "307706"; //办事处审核驳回
	public final static String SPDLCZT_07 = "307707"; //供应商审核超时
	public final static String SPDLCZT_08 = "307708"; //供应商审核
	public final static String SPDLCZT_09 = "307709"; //初审通过
	public final static String SPDLCZT_10 = "307710"; //人工审核驳回
	public final static String SPDLCZT_11 = "307711"; //终审通过
	public final static String SPDLCZT_12 = "307712"; //审核拒绝
	//问题回复状态
	public final static String WTHFZT="WTHFZT";
	public final static String WTHFZT_01 = "307801"; //未回复
	public final static String WTHFZT_02 = "307802"; //已回复
	//入账类型
	public final static String SGRZLX="SGRZLX";
	public final static String SGRZLX_01 = "307901"; //冲减
	public final static String SGRZLX_02 = "307902"; //处罚
	public final static String SGRZLX_03 = "307903"; //奖励
	public final static String SGRZLX_04 = "307904"; //其他
	//手工帐状态
	public final static String SGZZT="SGZZT";
	public final static String SGZZT_01 = "308001"; //保存
	public final static String SGZZT_02 = "308002"; //已提报
	public final static String SGZZT_03 = "308003"; //审核通过
	public final static String SGZZT_04 = "308004"; //审核驳回
	
	// 结算日期状态
	public final static String JSRQZT = "JSRQZT";
	public final static String JSRQZT_01 = "111001"; // 日期已确定
	public final static String JSRQZT_02 = "111002"; // 日期未确定
	
}
