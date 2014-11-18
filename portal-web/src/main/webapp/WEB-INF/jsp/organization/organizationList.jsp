<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/resources/css/user.css">
<div class="span12">
	<form class="form-horizontal"
		action="${pageContext.request.contextPath}/okcsys/okcorghier/doSelect">
		组织机构: <input id="orgid" type="text" name="orgid"
			class="input-medium search-query" /> <input type="submit" value="查询"
			class="btn btn-primary"> <a
			href="<spring:url value="/okcsys/okcorghier/create"></spring:url>"
			class="btn btn-primary">新增</a>
	</form>

	<br />

	<table class="table-bordered">
        <thead>
            <tr>
                <th>组织机构</th>
                <th>描述</th>
                <th>缺省GL清算科目</th>
                <th>基本货币1的货币代码</th>
                <th>基本货币2的货币代码</th>
                <th>语言列</th>
                <th>编辑</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${organizations}" var="organization">
                <tr>
                    <td align="center">${organization.orgid}</td>
                    <td align="center">${organization.description}</td>
                    <td align="center">${organization.clearingacct}</td>
                    <td align="center">${organization.basecurrency1}</td>
                    <td align="center">${organization.basecurrency2}</td>
                    <td align="center">${organization.langcode}</td>
                    <td><a
                            href="${pageContext.request.contextPath}/okcsys/okcorghier/update?organizationid=${organization.organizationid}">修改</a>
                        <a onclick="return confirm('你确定要删除数据?')"
                           href="${pageContext.request.contextPath}/okcsys/okcorghier/delete?organizationid=${organization.organizationid}">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>

	</table>
</div>