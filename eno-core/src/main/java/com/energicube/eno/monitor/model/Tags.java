package com.energicube.eno.monitor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * tagName 转成 tagid
 * <p/>
 * <p>
 * 主要用于定义各个页面包含的设备点信息，包括设备点ID，名称，以前在页面中的绝对位置
 * </p>
 *
 * @author CHENPING
 * @version 1.0
 */
@Entity
@Table(name = "TAGS")
public class Tags implements java.io.Serializable{

	private static final long serialVersionUID = 6599583083062139049L;
	private String tagid;
	private String valuetag;
	
	public Tags() {
		super();
	}
	
	public Tags(String tagid, String valuetag) {
		super();
		this.tagid = tagid;
		this.valuetag = valuetag;
	}
	@NotEmpty
    @Column(name = "TAGID", nullable = false)
	public String getTagid() {
		return tagid;
	}
	public void setTagid(String tagid) {
		this.tagid = tagid;
	}
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "VALUETAG", unique = true, nullable = false)
	public String getValuetag() {
		return valuetag;
	}
	public void setValuetag(String valuetag) {
		this.valuetag = valuetag;
	}
	
	
}
