package com.peiqi.hibernate.bean;

import com.alibaba.fastjson.JSONObject;

public class PageBean {

	// 当前页
	private int curpage = 1;
	// 每页显示数量
	private int pagesize = 10;
	// 总页数
	private int totalpage;
	// 数据总数
	private int totalNum;
	// 下一页页数
	private int nextpage;
	// 排序类型：0降序，1升序
	private int sorttype;
	// 是否需要重新统计(0:不需要，1：需要)
	private int isnewcomp = 1;

	// 操作类型(pre:上一页，next:下一页，first:首页，final:尾页)
	private String opertype;
	// 排序字段(默认根据ID降序)
	private String sortfeild = "id";

	public PageBean() {
		super();
	}

	// 根据前端参数构造pagebean
	public PageBean(JSONObject pageparam) {
		if (pageparam.containsKey("pagesize")) {
			this.pagesize = pageparam.getInteger("pagesize");
		}
		if (pageparam.containsKey("currentpage")) {
			this.curpage = pageparam.getInteger("currentpage");
		}
		if (pageparam.containsKey("isnewcomp")) {
			this.isnewcomp = pageparam.getInteger("isnewcomp");
		}
		if (pageparam.containsKey("sortparam")) {
			this.sortfeild = pageparam.getString("sortparam");
		}
		if (pageparam.containsKey("sorttype")) {
			this.sorttype = pageparam.getInteger("sorttype");
		}
	}

	public void updatePage() {
		this.totalpage = totalNum / pagesize + (totalNum % pagesize == 0 ? 0 : 1);
		if (this.curpage > this.totalpage && isnewcomp == 1 && this.totalpage != 0) {
			this.curpage = this.totalpage;
		}
		setNextpage();
		if (opertype != null) {
			switch (this.opertype) {
			case "pre":
				if (this.curpage > 1) {
					this.curpage--;
				}
				break;
			case "next":
				this.curpage = this.nextpage;
				break;
			case "first":
				this.curpage = 1;
				break;
			case "final":
				this.curpage = this.totalpage;
				break;
			default:
				break;
			}
		}
		setNextpage();
	}

	public int getCurpage() {
		return curpage;
	}

	public void setCurpage(int curpage) {
		this.curpage = curpage;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public String getOpertype() {
		return opertype;
	}

	public void setOpertype(String opertype) {
		this.opertype = opertype;
	}

	public int getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getNextpage() {
		return nextpage;
	}

	public void setNextpage() {
		if (this.totalpage > this.curpage) {
			this.nextpage = this.curpage + 1;
		} else {
			this.nextpage = this.curpage;
		}
	}

	public String getSortfeild() {
		return sortfeild;
	}

	public void setSortfeild(String sortfeild) {
		this.sortfeild = sortfeild;
	}

	public int getSorttype() {
		return sorttype;
	}

	public void setSorttype(int sorttype) {
		this.sorttype = sorttype;
	}

	public int getIsnewcomp() {
		return isnewcomp;
	}

	public void setIsnewcomp(int isnewcomp) {
		this.isnewcomp = isnewcomp;
	}

	@Override
	public String toString() {
		return "PageBean [curpage=" + curpage + ", pagesize=" + pagesize + ", totalpage=" + totalpage + ", totalNum="
				+ totalNum + ", nextpage=" + nextpage + ", sorttype=" + sorttype + ", isnewcomp=" + isnewcomp
				+ ", opertype=" + opertype + ", sortfeild=" + sortfeild + "]";
	}

}
