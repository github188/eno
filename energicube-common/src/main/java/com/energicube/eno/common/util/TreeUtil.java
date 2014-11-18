package com.energicube.eno.common.util;

import com.energicube.eno.common.model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TreeUtil {
	/**
	 * 构建Easyui Tree
	 * <p>来源数据列表类型必须为Tree,且 id,text,parentid都必须赋值</p>
	 * @param source 来源数据
	 * */
	public static List<Tree> build(List<Tree> source) {
		List<Tree> parents = new ArrayList<Tree>();
		List<Tree> childs = new ArrayList<Tree>();
		for (Tree entry : source) {
			if ("".equals(entry.getParentid())) {
				Tree result = entry;
				result.setChildren(new ArrayList<Tree>());
				parents.add(result);
			} else {
				childs.add(entry);
			}
		}
		buildTree(parents, childs);
		return parents;
	}

	private static void buildTree(List<Tree> parents, List<Tree> others) {
		List<Tree> record = new ArrayList<Tree>();
		for (Iterator<Tree> iterator = parents.iterator(); iterator.hasNext();) {
			Tree vi = iterator.next();
			if (vi.getId() != null) {
				for (Iterator<Tree> childIt = others.iterator(); childIt
						.hasNext();) {
					Tree inVi = childIt.next();
					if (vi.getId().equals(inVi.getParentid())) {
						if (null == vi.getChildren()) {
							vi.setChildren(new ArrayList<Tree>());
						}
						vi.getChildren().add(inVi);
						record.add(inVi);
						childIt.remove();
					}
				}
			}
		}
		if (others.size() == 0) {
			return;
		} else {
			buildTree(record, others);
		}
	}
}
