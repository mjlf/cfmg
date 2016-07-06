package com.mjlf.cfmg.entity;

import java.util.ArrayList;
import java.util.List;

public class PageValue {
	/**
	 * �����û�����
	 */
	private int allCount;
	/**
	 * ��ǰ��ѯ�ڼ�ҳ
	 */
	private int pageIndex = 1;
	/**
	 * ÿҳ��ѯ����
	 */
	private int everypagenum = 3;
	/**
	 * һ������ҳ
	 */
	private int pageCount;
	private Object object;
	
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public PageValue(int pageindex, int everypagenum){
		this.pageIndex = pageindex;
		this.everypagenum = everypagenum;
	}
	public PageValue(){
		
	}
	private List<Object> list = new ArrayList<>();
	public int getAllCount() {
		return allCount;
	}
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public List<Object> getList() {
		return list;
	}
	public void setList(List<Object> list) {
		this.list = list;
	}
	
	public int getEverypagenum() {
		return everypagenum;
	}
	public void setEverypagenum(int everypagenum) {
		this.everypagenum = everypagenum;
	}
	/**
	 * ͳ��һ���ж���ҳ
	 */
	public void countPageCount(){
		if(this.allCount % everypagenum == 0 && this.allCount != 0){
			this.pageCount = this.allCount / this.everypagenum;
		}else if(this.allCount != 0 && this.allCount % everypagenum != 0){
			this.pageCount = this.allCount / this.everypagenum + 1;
		}else if(this.allCount == 0){
			this.pageCount = 1;
		}
	}
	
	/**
	 * �򼯺�����Ӳ�ѯ���
	 * @param value
	 */
	public void addValue(Object value){
		this.list.add(value);
	}
	public void addValue(List<Object> values){
		this.list.addAll(values);
	}
	@Override
	public String toString() {
		return "PageValue [allCount=" + allCount + ", pageIndex=" + pageIndex + ", everypagenum=" + everypagenum
				+ ", pageCount=" + pageCount + ", object=" + object + ", list=" + list + "]";
	}
}
