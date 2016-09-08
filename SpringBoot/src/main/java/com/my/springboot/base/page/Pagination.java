/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.my.springboot.base.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pagination<T> implements Serializable {
	private static final long serialVersionUID = 5029894223695629135L;
	private int rows = 20;
	private int page = 1;
	private long totalRows = -1L;
	private List<T> list = new ArrayList();
	private List<String> keyList = new ArrayList();

	public Pagination() {
	}

	public Pagination(int page, int rows) {
		this.page = page;
		this.rows = rows;
	}

	public int getRows() {
		return this.rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		if (this.totalRows == -1L) {
			return this.page;
		} else if (this.totalRows == 0L) {
			return 1;
		} else {
			int maxPage = (int) (this.totalRows / (long) this.rows);
			if (this.totalRows % (long) this.rows > 0L) {
				++maxPage;
			}

			if (this.page > maxPage) {
				this.page = maxPage;
			}

			return this.page < 1 ? 1 : this.page;
		}
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getTotalRows() {
		return this.totalRows;
	}

	public void setTotalRows(long totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalPages() {
		int totalPages = (int) (this.totalRows / (long) this.rows);
		if (this.totalRows % (long) this.rows > 0L) {
			++totalPages;
		}

		return totalPages;
	}

	public List<T> getList() {
		return this.list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public List<String> getKeyList() {
		return this.keyList;
	}

	public void setKeyList(List<String> keyList) {
		this.keyList = keyList;
	}

	public String toString() {
		return "Pagination [rows=" + this.rows + ", page=" + this.page + "]";
	}
}