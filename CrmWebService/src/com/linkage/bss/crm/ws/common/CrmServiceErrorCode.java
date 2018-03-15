package com.linkage.bss.crm.ws.common;

/**
 * 存放订单调度中的错误编码。 
 * @version 0.5
 * @author 景伯文
 * @author 边园
 */
public class CrmServiceErrorCode {
	
	/**
	 * 获取spring配置上下文对象出错
	 */
	public final static int CRMSERVICE_ERROR_30000 = 30000;
	
	/**
	 * 根据spring配置上下文获取SMO对象出错
	 */
	public final static int CRMSERVICE_ERROR_30001 = 30001;
	
	/**
	 * 根据号码和号码类型查询号码实例id出错
	 */
	public final static int CRMSERVICE_ERROR_30002 = 30002;

	/**
	 * 调用资源远程方法预占接入号码失败
	 */
	public final static int CRMSERVICE_ERROR_30003 = 30003;
	
	/**
	 * 调用资源远程方法释放接入号码失败
	 */
	public final static int CRMSERVICE_ERROR_30004 = 30004;
	
	/**
	 * 根据地区编码查询areaId异常
	 */
	public final static int CRMSERVICE_ERROR_30005 = 30005;
	
	/**
	 *  调用资源服务根据号码查询终端设备实例信息异常
	 */
	public final static int CRMSERVICE_ERROR_30006 = 30006;
	
	/**
	 *  根据号码、号码类型和服务等条件定位产品基本信息异常
	 */
	public final static int CRMSERVICE_ERROR_30007 = 30007;
	
	/**
	 *  主接入号的anId获取失败
	 */
	public final static int CRMSERVICE_ERROR_30008 = 30008;
	
	/**
	 *  号码资源校验失败
	 */
	public final static int CRMSERVICE_ERROR_30009 = 30009;
	
	/**
	 *  构造资源调用接口入参XML异常
	 */
	public final static int CRMSERVICE_ERROR_30010 = 30010;
	
	/**
	 *  访问资源远程EJB接口失败
	 */
	public final static int CRMSERVICE_ERROR_30011 = 30011;
	
	/**
	 *  访问资源服务接口内部处理异常
	 */
	public final static int CRMSERVICE_ERROR_30012 = 30012;
	
	/**
	 *  入参缺少关联的组合产品的ID
	 */
	public final static int CRMSERVICE_ERROR_30013 = 30013;

	/**
	 *  受理接口不支持的业务类型
	 */
	public final static int CRMSERVICE_ERROR_30014 = 30014;

	/**
	 *  根据购物车ID查询业务动作ID异常
	 */
	public final static int CRMSERVICE_ERROR_30015 = 30015;

	/**
	 *  调用资源webService接口方法判断上网本卡地域所属异常
	 */
	public final static int CRMSERVICE_ERROR_30016 = 30016;

	/**
	 *  调用资源webService接口方法省卡数据回填异常
	 */
	public final static int CRMSERVICE_ERROR_30017 = 30017;
	
	/**
	 * 服务属性值未做任何变动
	 */
	public final static int CRMSERVICE_ERROR_30018 = 30018;
	
	/**
	 * 销售品变更时，无法定位售品规格
	 */
	public final static int CRMSERVICE_ERROR_30019 = 30019;
	
	/**
	 * 业务受理时传入的帐户编码错误,库中不存在该帐户
	 */
	public final static int CRMSERVICE_ERROR_30020 = 30020;
	
	/**
	 * 业务受理查找产品实例失败
	 */
	public final static int CRMSERVICE_ERROR_30021 = 30021;
	
	/**
	 * 新装没有明确指定付费类型
	 */
	public final static int CRMSERVICE_ERROR_30022 = 30022;
}
