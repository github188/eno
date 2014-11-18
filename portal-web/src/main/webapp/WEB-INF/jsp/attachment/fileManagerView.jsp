<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style>
#menu {
	position: absolute;
	border: 2px outset threedhighlight;
	background-color: inactiveborder;
	padding: 2px;
	width: 180px;
	visibility: hidden;
}

.item {
	padding: 2px;
	width: 100%;
	cursor: default;
	border: 1px solid inactiveborder;
}

.item img {
	vertical-align: middle;
	margin-right: 10px;
}

.item span {
	visibility: hidden;
	width: 21px;
	height: 12px;
}

.line {
	border-top: 1px solid #848284;
	border-bottom: 1px solid white;
	margin: 2px;
}
</style>
<div class="row-fluid">
<div id="contextmenu"
	style="border: 1px solid #666666; background: white; width: 50px; padding: 5px 5px; display: none; position: absolute; text-align: center">
	<div>
		<a id="contextmenu1">下载</a>
	</div>
	<div>
		<a id="contextmenu2">删除</a>
	</div>
	<div id="view" style="display: none;">
		<a id="contextmenu3">查看</a>
	</div>
</div>
</div>
	<div class="span12" style="margin-top:20px;">
		<div class="span2" style="background-color: #bbd8e9; height: 100%">
			&nbsp&nbsp<span
				style="padding-left:15px;background:url(${pageContext.request.contextPath}/resources/images/filehome.png) no-repeat;">
				<a href='<spring:url value="/pacs/attachment.html"></spring:url>' style="color: black"> 附件管理 </a>
			</span>
			<c:forEach var="doctype" items="${doctypes}" begin='0'
				varStatus="status">
				<ul class="doctype-list">
					<li class="doctype-list1" style="margin-bottom: -10px"><a
						href="showFile?doctypesid=${doctype.doctypesid}"> <img
							width="20" height="20"
							src="${pageContext.request.contextPath}/resources/images/icons-file.png">
							${doctype.doctype}
					</a></li>
				</ul>
			</c:forEach>
		</div>
		<div class="span10" style="background-color: #dceaf4; height: 100%">
			<c:if test="${bz eq '0'}">
				<c:forEach var="doctype" items="${doctypes}" begin='0'
					varStatus="status">
					<img onclick="show('${doctype.doctype}')"
						style="cursor: pointer; padding-left: 30px; padding-top: 20px"
						width="50" height="50"
						src="${pageContext.request.contextPath}/resources/images/icons-file.png">
  ${doctype.doctype}
  </c:forEach>
			</c:if>
			<c:if test="${bz eq '1'}">
				<c:forEach var="docinfo" items="${docinfos}" begin='0'
					varStatus="status">
					<c:if test="${fn:split(docinfo.document,'.')[1] eq 'jpg'}">
						<img id="chl"
							onmousemove="rightMenu(event,'${docinfo.docinfoid}',0)"
							title="${docinfo.document}"
							style="padding-left: 30px; padding-top: 20px" width="50"
							height="50"
							src="${pageContext.request.contextPath}/resources/images/icons-images.png">
					</c:if>
					<c:if test="${fn:split(docinfo.document,'.')[1] eq 'txt'}">
						<img id="chl"
							onmousemove="rightMenu(event,'${docinfo.docinfoid}',1)"
							title="${docinfo.document}"
							style="padding-left: 30px; padding-top: 20px" width="50"
							height="50"
							src="${pageContext.request.contextPath}/resources/images/icons-txt.png">
					</c:if>
					<c:if test="${fn:split(docinfo.document,'.')[1] eq 'doc'}">
						<img id="chl"
							onmousemove="rightMenu(event,'${docinfo.docinfoid}',2)"
							title="${docinfo.document}"
							style="padding-left: 30px; padding-top: 20px" width="50"
							height="50"
							src="${pageContext.request.contextPath}/resources/images/icons-word.png">
					</c:if>
    ${docinfo.document}
  </c:forEach>
			</c:if>
		</div>
	</div>


<script type="text/javascript">
	function show(doctype) {
		var Url = 'showDocinfos?doctype=' + doctype;
		window.location.href = Url;
	}
	function rightMenu(ev, docinfoid, type) {
		ev = ev || window.event;
		var mousePos = mouseCoords(ev);
		oncontextmenu = function() {
			document.getElementById("contextmenu").style.left = mousePos.x + 10
					+ document.body.scrollLeft;
			document.getElementById("contextmenu").style.top = mousePos.y + 10
					+ document.body.scrollTop;
			document.getElementById("contextmenu").style.display = "inline";
			document.getElementById("contextmenu").style.left = mousePos.x + 10
					+ document.body.scrollLeft;
			document.getElementById("contextmenu1").href = "${pageContext.request.contextPath}/pacs/attachment/download?docinfoid="
					+ docinfoid;
			document.getElementById("contextmenu2").href = "${pageContext.request.contextPath}/pacs/attachment/delete?docinfoid="
					+ docinfoid;
			if (type == "0") {
				document.getElementById("view").style.display = "inline";
				document.getElementById("contextmenu3").href = "${pageContext.request.contextPath}/pacs/attachment/view?docinfoid="
						+ docinfoid;
			}
			return false;
		}
	}
	function mouseCoords(ev) {
		if (ev.pageX || ev.pageY) {
			return {
				x : ev.pageX,
				y : ev.pageY
			};
		}
		return {
			x : ev.clientX + document.body.scrollLeft
					- document.body.clientLeft,
			y : ev.clientY + document.body.scrollTop - document.body.clientTop
		};
	}
	document.onclick = function() {
		if (document.activeElement != contextmenu)
			document.getElementById("contextmenu").style.display = "none";
	}
</script>