package com.zc.app.sebc.lx;

public class purchaseLogResult {
	private Object content;
	private String totalElements;
	private String totalPages;
	private boolean firstPage;
	private boolean lastPage;
	private int numberOfElements;
	private Object sort;
	private int size;
	private int number;

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public String getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(String totalElements) {
		this.totalElements = totalElements;
	}

	public String getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
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

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public Object isSort() {
		return sort;
	}

	public void setSort(Object sort) {
		this.sort = sort;
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
		return "purchaseLogResult [content=" + content + ", totalElements="
				+ totalElements + ", totalPages=" + totalPages + ", firstPage="
				+ firstPage + ", lastPage=" + lastPage + ", numberOfElements="
				+ numberOfElements + ", sort=" + sort + ", size=" + size
				+ ", number=" + number + "]";
	}

}
