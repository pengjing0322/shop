package com.util;

import com.beans.PageInfo;

public class PageUtil {

	public static final int PAGESIZE=3;
	
	public static PageInfo getPageInfo(int pageSize,int pageIndex,int rowCount) {
		PageInfo pageInfo=new PageInfo();
		pageInfo.setPageIndex(pageIndex);
		pageInfo.setPageSize(pageSize);
		pageInfo.setRowCount(rowCount);
		pageInfo.setPageCount((rowCount+pageSize-1)/pageSize);
		pageInfo.setBeginRow(pageSize*(pageIndex-1));
		
		pageInfo.setHasNext(!(pageInfo.getPageCount()<=pageIndex)); //false 没有下一页
		pageInfo.setHasPre(!(pageIndex==1 || rowCount==0));  //false 没有上一页
		return pageInfo;
	}
}
