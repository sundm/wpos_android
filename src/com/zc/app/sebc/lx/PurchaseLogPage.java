package com.zc.app.sebc.lx;

import java.util.List;

public class PurchaseLogPage {
	private List<PurchaseLog> content;
	private Object sort;
	private int totalPages;
	private int numberOfElements;
	private long totalElements;
	private boolean firstPage;
	private boolean lastPage;
	private int size;
	private int number;

	public List<PurchaseLog> getContent() {
		return content;
	}

	public void setContent(List<PurchaseLog> content) {
		this.content = content;
	}

	public Object getSort() {
		return sort;
	}

	public void setSort(Object sort) {
		this.sort = sort;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public boolean isFirstPage() {
		return firstPage;
	}

	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "PurchaseLogPage [content=" + content + ", sort=" + sort
				+ ", totalPages=" + totalPages + ", numberOfElements="
				+ numberOfElements + ", totalElements=" + totalElements
				+ ", firstPage=" + firstPage + ", lastPage=" + lastPage
				+ ", size=" + size + ", number=" + number + "]";
	}

}
