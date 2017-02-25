package com.dark.model.base;

import java.io.Serializable;
import java.util.List;

public class DtParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DtParam() {
	}

	private int start;
	private int length;
	private List<DtSearchColumn> columns;
	private List<DtSearchColumn.DtSearchOrder> orders;
	private DtSearchColumn.DtColSearchCondition globalSearch;
	private int draw = 1;

	public DtParam(int start, int length, int draw, List<DtSearchColumn> columns, List<DtSearchColumn.DtSearchOrder> orders, DtSearchColumn.DtColSearchCondition globalSearch) {
		this.start = start;
		this.length = length < 0 ? Integer.MAX_VALUE : length;
		this.draw = draw;
		this.columns = columns;
		this.orders = orders;
		this.globalSearch = globalSearch;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public List<DtSearchColumn> getColumns() {
		return columns;
	}

	public List<DtSearchColumn.DtSearchOrder> getOrders() {
		return orders;
	}

	public DtSearchColumn.DtColSearchCondition getGlobalSearch() {
		return globalSearch;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

}
