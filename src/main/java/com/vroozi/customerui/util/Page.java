package com.vroozi.customerui.util;

import java.util.ArrayList;
import java.util.List;

import com.vroozi.customerui.user.services.user.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: qureshit
 * Date: 10/14/12
 * Time: 5:00 AM
 * To change this template use File | Settings | File Templates.
 */

public class Page<E> {

    private int pageNumber;
    private long totalRecords;
    private int pagesAvailable;
    private List<E> pageItems = new ArrayList<E>();

    protected int pageSize = 8;
	
	protected int firstElementOnPage = 0;
	protected int lastElementOnPage = 0;
    
	public Page() {
	}
	
	public Page(List<E> pageItems, int pageNumber, int pageSize, long totalRecords) {
		setPageItems(pageItems);
		setTotalRecords(totalRecords);
		setPageNumber(pageNumber);
		setPageSize(pageSize);
		
		
		int pageCount = (int) (totalRecords / pageSize);
        if (totalRecords > pageSize * pageCount) {
            pageCount++;
        }
        setPagesAvailable(pageCount);
        
        firstElementOnPage = (getPageNumber()-1)*pageSize+1;
        lastElementOnPage = firstElementOnPage+getPageItems().size()-1;
        
        if(totalRecords==0) {
        	setPageNumber(0);
        	firstElementOnPage--;
        }
	}
	
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPagesAvailable(int pagesAvailable) {
        this.pagesAvailable = pagesAvailable;
    }

    public void setPageItems(List<E> pageItems) {
        this.pageItems = pageItems;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPagesAvailable() {
        return pagesAvailable;
    }

    public List<E> getPageItems() {
        return pageItems;
    }

    public long getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstElementOnPage() {
		return firstElementOnPage;
	}

	public void setFirstElementOnPage(int firstElementOnPage) {
		this.firstElementOnPage = firstElementOnPage;
	}

	public int getLastElementOnPage() {
		return lastElementOnPage;
	}

	public void setLastElementOnPage(int lastElementOnPage) {
		this.lastElementOnPage = lastElementOnPage;
	}
    
}
