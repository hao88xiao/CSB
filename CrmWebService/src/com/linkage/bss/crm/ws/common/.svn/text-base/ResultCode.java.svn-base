package com.linkage.bss.crm.ws.common;

public enum ResultCode {

	//BUSINESSS("B0000", "B开头的是业务错误"),
	
	CUSTOMER_NOT_EXIST("B0001", "客户不存在"),
	CUSTOMER_HAS_EXIST("B0002","存在同名用户"),
	CUST_GRADE_INFO_IS_NOT_EXIST("B0009","客户等级信息为空"),
	IDENTITY_TYPE_IS_NOT_EXIST("B0003","证件号码校验失败"),
	REQUEST_PARAME_IS_ERROR("B0004","入参错误"),
	REQUEST_IDENTIFY_TYPE_IS_NOT_EXIST("B0010","无效的证件类型"),
    PREPARE_ORDER_RESULT_IS_NULL("B0005","查询预受理订单信息结果为空"),
    PREPARE_ORDER_TO_RELEASE("B0006","预受理撤单失败"),
    PREPARE_ORDER_TO_COMMIT_FLASE("B0007","预受理单转正失败"),
    RESULT_IS_NULL("B0011","查询结果为空"),
    PROD_NOT_EXIST("B0012", "产品不存在"),
    ACCOUNT_NOT_EXIST("B0013", "账户不存在"),
    ACCNBR_TYPE_WRONG("B0014", "接入号码类型错误"),
    PARAMETER_ERROR("B0015", "参数错误"),
	MATERIAL_INST_NOT_EXIST("B0016","物品不存在或状态不符"),
	CHANGE_PASSWORD_ERROR("B0017","修改密码失败，旧密码校验失败"),
	CHANGE_PASSWORD_EXCEPTION("B0018","修改用户密码失败，一点通受理失败"),
	CHANGE_PASSWORD_NO_OLD_PSD("B0019","尚未设置密码"),
	OFFERLIST_NOT_EXIST("B0020", "销售品不存在"),
	QUERY_FNS_NUM_ERROR("B0020","查询亲情号码失败  根据offer_id未查询到属性规格为itemSpecId的规格参数信息"),
	//PERMISSION("P0000", "P开头的是权限错误"),
	INVALID_ACCNBR_TYPE("B0021","不能识别的接入号类型"),
	IS_SUBSIDY_ERROR("B0022","判断是否已做话费补贴失败，根据实例编码未查询到相应补贴信息"),
	STAFF_CODE_NOT_EXIST("P0001", "员工不存在"),
	FILTER_TYPE_ERROR("B0023","筛选类型不对"),
	PARAMETER_NOT_EXIST("P0002","参数为空"),
	PARAMETER_NOT_REAL("P0003","弱密码,请重新修改"),
	PRODID_BY_ACCNBR_NOT_EXIST("B0024","通过号码查prodId为空"),
	PARTY_NOT_EXIST("B0025","客户不存在"),
	COMPROD_NOT_EXIST("B0026","可纳入的产品不存在"),
	TERMINALCODE_BY_PRODID_IS_NULL("B0027","根据prodId查询的terminalCode为空"),
	IMIINFOWLANCE_IS_NULL("B0028","数据卡为空"),
	IMIINFOVOICE_IS_NULL("B0029","语音卡为空"),
	IMIINFO_IS_NULL("B0030","UIM卡信息查询为空"),
	OBJINFO_IS_NULL("B0031","根据接入号查询ObjInfoDto为空"),
	OFFERSPEC_IS_NULL("B0032","销售品规格不存在") ,
	GLOBALROAM_NOT_EXIST("B0033","根据接入号查询国际漫游为空"),
	OFFER_PROD_BY_PRODID_IS_NULL("B0034","根据prodId查询附属销售品为空")  ,
	POINT_EXCHANGE_ERROR("B0035","积分兑换失败")  ,
	STAFF_LOGIN_NULL("B0036","代理商登录接口返回为空")  ,
	QUERY_CHANNEL_NULL("B0037","渠道查询为空")  ,
	PWD_ERROR("B0038","密码出错")  ,
	STAFF_NOT_EXIST("B0039","无员工记录")  ,
	ACCNBR_TYPE_ERROR("B0040","号码类型错误,请检查输入信息！")  ,
	OFFERLIST_BY_PRODID_NOT_EXIST("B0041","通过产品id查询销售品不存在")  ,
	PARTY_2_PROD_IS_NULL("B0042","客户下未查找到产品信息")  ,
	MATERIAL_CODE_NOT_EXIST("B0043","未查询到此实例编码信息")  ,
	CALL_METHOD_ERROR("B1000","调用第三方返回错误信息")  ,
	TICKET_IS_NULL("T0001","抵用券信息查询结果为空"),
	PASSWORD_INVALID("B0044","密码无效"),
	PASSWORD_UNSET("B0045","未设置密码"),
	QUERY_RESULT_IS_NULL("B0046","查询信息为空，请检查输入信息！"),
	NOT_IN_COMPPROD("B0047","不在组合产品中"),
	CATEGROYNODEID_ERROR("B0048","传入目录节点不正确!"),
	NOTIMPORTANTPARTY_BY_PARTYID("B0049","False:非重保客户"),
	UNITYPAY_FAILED("B0050","调用统一支付失败"),
	RESOURSE_STATE_UNVALIBLE("B0051", "该资源状态为不可用"),
	RESOURSE_INFO_ERROR("B0052", "串码、密码错误或类型错误"),
	SR_SALE_CARD_FAILED("B0053","调用营销资源失败"),
	GZT_QUERY_FAILED("B0054","国政通查询失败，请检查姓名和身份证号码"),
	GZT_VALIDATION_FAILED("B0055","国政通校验失败"),
	RELA_PROD_FAILED("B0056","该号码该关联号码没有对应的产品"),
	REPEAT_CODE_MSG("B0057","重复订购"),
	CONTINUE_ORDER_OFFERSPEC_MSG("B0058","该号码已续费"),
	HUCHI_OFFER_SPEC_FOR_CHANGE_STARTFASHION("B0059","存在互斥业务，生效时间必须为次月生效"),
	GUO_JI_MAN_YOU_BU_YUN_XU_BAN_LI("B0060","该用户不能办理国际漫游"),
	HU_CHI_JIAO_YAN_EXCEPTION("B0061","互斥校验java异常"),
	GETINCOICEID_ERROR("B0062","获取发票号失败"),
	INSERTPOINT_BSS_ERROR("B0063","调用积分扣减业务异常"),
	TICKET_OF_CAMP_TYPE_IS_NOT_EXIST("T0002","抵用券预占类型无效"),
	RECHARGE_CARD_CHECK_ERROR("B0064","有价卡对账异常"),
	BUILDING_IS_NOT_EXIST("B0065","未找到符合条件的楼宇信息"),
	BANK_ACTION_TYPE("B0066","冻结，解冻动作类型错误"),
	BANK_TABLE_CONFIG("B0067","冻结，解冻数据库没有进行配置"),
	PW_RULEERROR("B0066","密码格式错误,必须为6位0-9的数字！"),
	ORDER_ONWAY("B0068","存在在途工单！"),
	BUSINESS_CHECK_ISNOTOK("B0069","不允许订购的销售品！"),
	MANYPARTY_BY_ID("B0070","同一证件号不能创建多个客户！"),
	SCORE_CHECK_CRM("B0071","客户积分业务校验不通过"),
	//SYSTEM("S0000", "S开头的是系统错误"),

	SYSTEM_ERROR("S0001", "系统错误"),
	ZJ_INFO_IS_NOT_NULL("Z0001","已订购租机且未到期！"),
	ZJ_INFO_IS_NOTBLZJ("Z0002","主套餐不能办理该业务！"),
	ZJ_INFO_IS_NOT_NULL_NOTBLZJ("Z0001|Z0002","已订购租机且未到期并且主套餐不能办理该业务！"),
	UNSUCCESS("1","失败"),
	SUCCESS("0", "成功"),
	
	//彩铃集团接口协议规范编码
	createCRAccountREP_SUC("000000","成功"),
	createCRAccountREP_A("000001","开销户已经成功受理（异步方式）"),
	createCRAccountREP_B("200001","输入的必选参数为空"),
	createCRAccountREP_C("200002","参数格式错误"),
	createCRAccountREP_D("200006","设备代码错"),
	createCRAccountREP_E("200007","访问序列号不对称"),
	createCRAccountREP_F("200008","访问密码错误"),
	createCRAccountREP_G("100002","系统忙"),
	createCRAccountREP_H("301000","用户已开通彩铃");

	
	private String code;

	private String desc;

	private ResultCode(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String toString() {
		return code;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static void main(String args[]) {
		System.out.println(ResultCode.SUCCESS);
		System.out.println(ResultCode.SUCCESS.getCode());
		System.out.println(ResultCode.SUCCESS.getDesc());
		System.out.println(ResultCode.SUCCESS.getDeclaringClass());
		System.out.println(ResultCode.SUCCESS.name());
		System.out.println(ResultCode.SUCCESS.ordinal());
	}
}
