package com.linkage.bss.crm.ws.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD })
public @interface Required {

	/**
	 * 必填的节点
	 * 
	 * @return
	 */
	public Node[] nodes() default {};
}
