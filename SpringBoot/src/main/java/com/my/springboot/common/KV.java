/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.common;

import java.io.Serializable;

public class KV implements Serializable {
	private static final long serialVersionUID = -3617796537738183236L;
	public String k;
	public Object v;

	public KV() {
	}

	public KV(String k, Object v) {
		this.k = k;
		this.v = v;
	}

	public String getK() {
		return this.k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public Object getV() {
		return this.v;
	}

	public void setV(Object v) {
		this.v = v;
	}
}