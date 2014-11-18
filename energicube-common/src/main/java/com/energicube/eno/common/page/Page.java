package com.energicube.eno.common.page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: EnergyUser
 * Date: 13-11-26
 * Time: 上午11:39
 * To change this template use File | Settings | File Templates.
 */
public class Page<T> {
    private int pageNumber;
    private int pagesAvailable;
    private List<T> pageItems = new ArrayList<T>();
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    public void setPagesAvailable(int pagesAvailable) {
        this.pagesAvailable = pagesAvailable;
    }
    public void setPageItems(List<T> pageItems) {
        this.pageItems = pageItems;
    }
    public int getPageNumber() {
        return pageNumber;
    }
    public int getPagesAvailable() {
        return pagesAvailable;
    }
    public List<T> getPageItems() {
        return pageItems;
    }
}
