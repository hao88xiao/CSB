package com.linkage.bss.crm.ws.others.query;

import java.util.Map;

/**
 * ��ѯ��Ʒ�ӿ���
 * @author hll
 *  2013/07/19
 */
public interface QueryProductIntf {

	public int getProductType();

	public String getProductName();

	public Map<String, Object> getProductResult() throws Exception;
}
