/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.common.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE })
public @interface Persistence {
	String SUFFIX = "${SUFFIX}";
	String PAGINATION = "${PAGINATION}";
	int KEY_ONE = 1;
	int KEY_TWO = 2;
	int KEY_SHARDING = 7;
	int KEY_ONE_SHARDING = 17;

	int key() default 0;

	boolean isNotAutoIncrement() default false;

	String type() default "";

	int length() default 60;

	String mapper() default "";

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	public @interface ignore {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	public @interface isEmail {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	public @interface isMobile {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.TYPE })
	public @interface noCache {
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	public @interface notNull {
	}
}