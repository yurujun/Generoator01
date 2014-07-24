package com.yzr.servlet;

import java.io.Serializable;

public class Json implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 是否成功
	 */
	private boolean success;
	// 消息内容
	private String msg;
	// 数据
	private Object obj;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
