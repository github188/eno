package com.energicube.eno.common.model;

import java.util.ArrayList;
import java.util.List;

public class Tree extends BaseTree {
	private static final long serialVersionUID = -5606895454250797400L;
	/**
	 * ID
	 * */
	private String id;
	/**
	 * Text
	 * */
	private String text;

	private String parentid;
	
	/**
	 * 节点类型
	 * */
	private String nodetype;

	private List children;

    /**
     * 节点动态加载请求的url
     */
    private String url;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getParentid() {
		if (parentid == null)
			parentid = "";
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public List getChildren() {
		if (children == null)
			children = new ArrayList<Tree>();
		return children;
	}

	public void setChildren(List children) {
		this.children = children;
	}

	public String getNodetype() {
		return nodetype;
	}

	public void setNodetype(String nodetype) {
		this.nodetype = nodetype;
	}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
	public String toString() {
		return "Tree [id=" + id + ", text=" + text + ", parentid=" + parentid
				+ ", nodetype=" + nodetype + ", children=" + children + "]";
	}
	
	
}
