<%--
  Created by IntelliJ IDEA.
  User: zc
  Date: 13-7-4
  Time: 下午3:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/resources/calendar/bootstrap/css/style.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/resources/calendar/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="${pageContext.request.contextPath}/resources/calendar/bootstrap/css/bootstrap-responsive.min.css"/>
    <script src="${pageContext.request.contextPath}/resources/calendar/js/jquery.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/calendar/js/jquery-ui-1.10.2.custom.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/calendar/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

    <script type="text/javascript">
        function showMsaDialog(){
            var dialogContent = $($('#msgForm'));
            dialogContent.dialog({
                modal: true,
                width: 490,
                closeOnEscape : false,
                title: '信息',
                close: function() {
                    dialogContent.dialog("destroy");
                    dialogContent.hide();
                    dialogContent = null;
                }
            }).show()
        }


    </script>
</head>
<body>

<div class="box box-color box-bordered magenta" id="msgForm">
    <div class="box-title">
        <h3>
            <i class="icon-comment"></i>
            Tooltips
        </h3>
        <div class="actions">
            <a href="#" class="btn btn-mini content-refresh"><i class="icon-refresh"></i></a>
            <a href="#" class="btn btn-mini content-remove"><i class="icon-remove"></i></a>
            <a href="#" class="btn btn-mini content-slideUp"><i class="icon-angle-down"></i></a>
        </div>
    </div>
    <div class="box-content">
        <h3 class="popover-title">Enter username</h3>
        <div class="popover-content">
            <div>
                <div style="display: none;" class="editableform-loading"></div>
                <form style="" class="form-inline editableform">
                    <div class="control-group">
                        <div>
                            <div style="position: relative;" class="editable-input">
                                <input class="input-medium" style="padding-right: 24px;" type="text">
                                <span style="bottom: 8.5px; right: 8.5px;" class="editable-clear-x"></span>
                            </div>
                            <div class="editable-buttons">
                                <button type="submit" class="btn btn-primary editable-submit">
                                    <i class="icon-ok icon-white"></i>
                                </button>
                                <button type="button" class="btn editable-cancel"><i class="icon-remove"></i></button>
                            </div>
                        </div>
                        <div style="display: none;" class="editable-error-block help-block">

                            </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<div class="row-fluid"><div class="span12">
<div class="box box-bordered box-color">
<div class="box-title">
    <h3>
        <i class="icon-envelope"></i>
        Message Center
    </h3>
</div>
<div class="box-content nopadding">
<ul class="tabs tabs-inline tabs-left">
    <li class="write hidden-480">
        <a href="#" onclick="showMsaDialog()">写信息</a>
    </li>
    <li class="">
        <a href="#inbox" data-toggle="tab"><i class="icon-inbox"></i> Inbox <strong>(3)</strong></a>
    </li>
    <li class="active">
        <a href="#sent" data-toggle="tab"><i class="icon-share-alt"></i> Sent items</a>
    </li>
    <li class="">
        <a href="#trash" data-toggle="tab"><i class="icon-trash"></i> Trash</a>
    </li>
</ul>
<div class="tab-content tab-content-inline">
<div class="tab-pane" id="inbox">
<div class="highlight-toolbar">
    <div class="pull-left">
        <div class="btn-toolbar">
            <div class="btn-group visible-480">
                <a href="" class="btn btn-danger">New</a>
            </div>
            <div class="btn-group"><a data-original-title="Refresh results" href="#" class="btn" rel="tooltip" data-placement="bottom" title=""><i class="icon-refresh"></i></a></div>
            <div class="btn-group hidden-768">
                <div class="dropdown">
                    <a data-original-title="Mark elements" href="#" class="btn" data-toggle="dropdown" rel="tooltip" data-placement="bottom" title=""><i class="icon-check-empty"></i><span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#" class="sel-all">All</a></li>
                        <li><a href="#" class="sel-unread">Unread</a></li>
                    </ul>
                </div>
            </div>
            <div class="btn-group">
                <a data-original-title="Archive" href="#" class="btn" rel="tooltip" data-placement="bottom" title=""><i class="icon-inbox"></i></a>
                <a data-original-title="Mark as spam" href="#" class="btn" rel="tooltip" data-placement="bottom" title=""><i class="icon-exclamation-sign"></i></a>
                <a data-original-title="Delete" href="#" class="btn" rel="tooltip" data-placement="bottom" title=""><i class="icon-trash"></i></a>
            </div>
            <div class="btn-group hidden-768">
                <div class="dropdown">
                    <a data-original-title="Move to folder" href="#" class="btn" data-toggle="dropdown" rel="tooltip" data-placement="bottom" title=""><i class="icon-folder-close"></i><span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Some folder</a></li>
                        <li><a href="#">Another folder</a></li>
                    </ul>
                </div>
            </div>
        </div></div>
    <div class="pull-right">
        <div class="btn-toolbar">
            <div class="btn-group text hidden-768">
                <span><strong>1-25</strong> of <strong>348</strong></span>
            </div>
            <div class="btn-group">
                <a href="#" class="btn"><i class="icon-angle-left"></i></a>
                <a href="#" class="btn"><i class="icon-angle-right"></i></a>
            </div>
            <div class="btn-group hidden-768">
                <div class="dropdown">
                    <a href="#" class="btn" data-toggle="dropdown"><i class="icon-cog"></i><span class="caret"></span></a>
                    <ul class="dropdown-menu pull-right">
                        <li><a href="#">Settings</a></li>
                        <li><a href="#">Account settings</a></li>
                        <li><a href="#">Email settings</a></li>
                        <li><a href="#">Themes</a></li>
                        <li><a href="#">Help &amp; FAQ</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>


<table class="table table-striped table-nomargin table-mail">
<thead>
<tr>
    <th class="table-checkbox hidden-480">
        <input class="sel-all" type="checkbox">
    </th>
    <th class="table-icon hidden-480"></th>
    <th>Sender</th>
    <th>Subject</th>
    <th class="table-icon hidden-480"></th>
    <th class="table-date hidden-480">Date</th>
</tr>
</thead>
<tbody>
     <c:forEach items="${inceptMsg.content}" var="msg">
         <tr>
             <td class="table-checkbox hidden-480">
                 <input class="selectable" type="checkbox">
             </td>
             <td class="table-icon hidden-480">
                 <a href="#" class="sel-star active"><i class="icon-star"></i></a>
             </td>
             <td class="table-fixed-medium">
                 ${msg.sender}
             </td>
             <td>
                     ${msg.title}
             </td>
             <td class="table-icon hidden-480">
                 <i class="icon-paper-clip"></i>
             </td>
             <td class="table-date hidden-480">
                     ${msg.sendTime}
             </td>
         </tr>

     </c:forEach>
</tbody>
</table>
</div>


<div class="tab-pane active" id="sent">
<div class="highlight-toolbar">
    <div class="pull-left"><div class="btn-toolbar">
        <div class="btn-group">
            <div class="dropdown hidden-768">
                <a data-original-title="Mark elements" href="#" class="btn" data-toggle="dropdown" rel="tooltip" data-placement="bottom" title=""><i class="icon-check-empty"></i><span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="#" class="sel-all">All</a></li>
                    <li><a href="#" class="sel-unread">Unread</a></li>
                </ul>
            </div>
        </div>
        <div class="btn-group">
            <a data-original-title="Archive" href="#" class="btn" rel="tooltip" data-placement="bottom" title=""><i class="icon-inbox"></i></a>
            <a data-original-title="Mark as spam" href="#" class="btn" rel="tooltip" data-placement="bottom" title=""><i class="icon-exclamation-sign"></i></a>
            <a data-original-title="Delete" href="#" class="btn" rel="tooltip" data-placement="bottom" title=""><i class="icon-trash"></i></a>
        </div>
        <div class="btn-group hidden-768">
            <div class="dropdown">
                <a data-original-title="Move to folder" href="#" class="btn" data-toggle="dropdown" rel="tooltip" data-placement="bottom" title=""><i class="icon-folder-close"></i><span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="#">Some folder</a></li>
                    <li><a href="#">Another folder</a></li>
                </ul>
            </div>
        </div>
    </div></div>
    <div class="pull-right">
        <div class="btn-toolbar">
            <div class="btn-group text hidden-768">
                <span><strong>1-25</strong> of <strong>348</strong></span>
            </div>
            <div class="btn-group">
                <a href="#" class="btn" data-toggle="dropdown"><i class="icon-angle-left"></i></a>
                <a href="#" class="btn" data-toggle="dropdown"><i class="icon-angle-right"></i></a>
            </div>
            <div class="btn-group hidden-768">
                <div class="dropdown">
                    <a href="#" class="btn" data-toggle="dropdown"><i class="icon-cog"></i><span class="caret"></span></a>
                    <ul class="dropdown-menu pull-right">
                        <li><a href="#">Settings</a></li>
                        <li><a href="#">Account settings</a></li>
                        <li><a href="#">Email settings</a></li>
                        <li><a href="#">Themes</a></li>
                        <li><a href="#">Help &amp; FAQ</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<table class="table table-striped table-nomargin table-mail">
<thead>
<tr>
    <th class="table-checkbox hidden-480">
        <input class="sel-all" type="checkbox">
    </th>
    <th class="table-icon hidden-480"></th>
    <th>Sender</th>
    <th>Subject</th>
    <th class="table-icon hidden-480"></th>
    <th class="table-date hidden-480">Date</th>
</tr>
</thead>
<tbody>
<tr>
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star active"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        Johny Doesy
    </td>
    <td>
        Lorem ipsum sint laborum.
    </td>
    <td class="table-icon hidden-480">
        <i class="icon-paper-clip"></i>
    </td>
    <td class="table-date hidden-480">
        12. Feb
    </td>
</tr>
<tr class="unread">
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        proident
    </td>
    <td>Lorem ipsum Incididunt consectetur Duis proident laboris nulla cillum dolore elit esse enim tempor veniam.</td>
    <td class="table-icon hidden-480">
        <i class="icon-paper-clip"></i>
    </td>
    <td class="table-date hidden-480">
        11. Feb
    </td>
</tr>
<tr>
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        reprehenderit
    </td>
    <td>Lorem ipsum Laborum consequat dolor amet reprehenderit dolor dolor amet.</td>
    <td class="table-icon hidden-480">
    </td>
    <td class="table-date hidden-480">
        10. Feb
    </td>
</tr>
<tr>
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        eiusmod
    </td>
    <td>
        Lorem ipsum ut in in eiusmod ut occaecat tempor.
    </td>
    <td class="table-icon hidden-480">
    </td>
    <td class="table-date hidden-480">
        9. Feb
    </td>
</tr>
<tr>
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        occaecat
    </td>
    <td>Lorem ipsum Consequat Duis adipisicing dolor occaecat cillum aliquip adipisicing in cupidatat irure id in.</td>
    <td class="table-icon hidden-480">
    </td>
    <td class="table-date hidden-480">
        8. Feb
    </td>
</tr>
<tr>
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star active"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        nisi
    </td>
    <td>Lorem ipsum Culpa magna aliqua Duis cillum dolor officia proident.</td>
    <td class="table-icon hidden-480">

    </td>
    <td class="table-date hidden-480">
        12. Feb
    </td>
</tr>
<tr class="unread">
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        esse
    </td>
    <td>
        Lorem ipsum veniam esse nisi in ut aliquip do laboris sed pariatur.
    </td>
    <td class="table-icon hidden-480">

    </td>
    <td class="table-date hidden-480">
        11. Feb
    </td>
</tr>
<tr>
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        ipsum
    </td>
    <td>Lorem ipsum Sed sit enim cillum reprehenderit Excepteur pariatur.</td>
    <td class="table-icon hidden-480">

    </td>
    <td class="table-date hidden-480">
        10. Feb
    </td>
</tr>
<tr>
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        eiusmod
    </td>
    <td>
        Lorem ipsum ut in in eiusmod ut occaecat tempor.
    </td>
    <td class="table-icon hidden-480">
        <i class="icon-paper-clip"></i>
    </td>
    <td class="table-date hidden-480">
        9. Feb
    </td>
</tr>
<tr>
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        elit
    </td>
    <td>
        Lorem ipsum sed sed elit nisi adipisicing mollit eu adipisicing.
    </td>
    <td class="table-icon hidden-480">

    </td>
    <td class="table-date hidden-480">
        8. Feb
    </td>
</tr>

</tbody>
</table>
</div>

<div class="tab-pane" id="trash">
<div class="highlight-toolbar">
    <div class="pull-left"><div class="btn-toolbar">
        <div class="btn-group">
            <a data-original-title="Undo" href="#" class="btn" rel="tooltip" data-placement="bottom" title=""><i class="icon-arrow-left"></i></a>
        </div>
    </div></div>
    <div class="pull-right">
        <div class="btn-toolbar">
            <div class="btn-group text hidden-768">
                <span><strong>1-25</strong> of <strong>348</strong></span>
            </div>
            <div class="btn-group">
                <a href="#" class="btn" data-toggle="dropdown"><i class="icon-angle-left"></i></a>
                <a href="#" class="btn" data-toggle="dropdown"><i class="icon-angle-right"></i></a>
            </div>
            <div class="btn-group hidden-768">
                <div class="dropdown">
                    <a href="#" class="btn" data-toggle="dropdown"><i class="icon-cog"></i><span class="caret"></span></a>
                    <ul class="dropdown-menu pull-right">
                        <li><a href="#">Settings</a></li>
                        <li><a href="#">Account settings</a></li>
                        <li><a href="#">Email settings</a></li>
                        <li><a href="#">Themes</a></li>
                        <li><a href="#">Help &amp; FAQ</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<table class="table table-striped table-nomargin table-mail">
<thead>
<tr>
    <th class="table-checkbox hidden-480">
        <input class="sel-all" type="checkbox">
    </th>
    <th class="table-icon hidden-480"></th>
    <th>Sender</th>
    <th>Subject</th>
    <th class="table-icon hidden-480"></th>
    <th class="table-date hidden-480">Date</th>
</tr>
</thead>
<tbody>
<tr>
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star active"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        veniam
    </td>
    <td>
        Lorem ipsum sint laborum.
    </td>
    <td class="table-icon hidden-480">
        <i class="icon-paper-clip"></i>
    </td>
    <td class="table-date hidden-480">
        12. Feb
    </td>
</tr>
<tr class="unread">
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        Lorem ipsum
    </td>
    <td>
        Lorem ipsum veniam esse nisi in ut aliquip do laboris sed pariatur.
    </td>
    <td class="table-icon hidden-480">
        <i class="icon-paper-clip"></i>
    </td>
    <td class="table-date hidden-480">
        11. Feb
    </td>
</tr>
<tr>
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        commodo
    </td>
    <td>Lorem ipsum In id magna commodo esse do ad esse labore elit.</td>
    <td class="table-icon hidden-480">
    </td>
    <td class="table-date hidden-480">
        10. Feb
    </td>
</tr>
<tr>
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        tempor
    </td>
    <td>
        Lorem ipsum ut in in eiusmod ut occaecat tempor.
    </td>
    <td class="table-icon hidden-480">
    </td>
    <td class="table-date hidden-480">
        9. Feb
    </td>
</tr>
<tr>
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        mollit
    </td>
    <td>
        Lorem ipsum sed sed elit nisi adipisicing mollit eu adipisicing.
    </td>
    <td class="table-icon hidden-480">
    </td>
    <td class="table-date hidden-480">
        8. Feb
    </td>
</tr>
<tr>
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star active"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        adipisicing
    </td>
    <td>Lorem ipsum Magna sunt mollit adipisicing est elit veniam nostrud.</td>
    <td class="table-icon hidden-480">

    </td>
    <td class="table-date hidden-480">
        12. Feb
    </td>
</tr>
<tr class="unread">
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        cillum ut
    </td>
    <td>Lorem ipsum Sunt qui quis laboris aliquip officia sed et.</td>
    <td class="table-icon hidden-480">

    </td>
    <td class="table-date hidden-480">
        11. Feb
    </td>
</tr>
<tr>
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        veniam incididunt
    </td>
    <td>
        Lorem ipsum dolore do ullamco id ullamco occaecat sed dolore adipisicing officia in cillum ut.
    </td>
    <td class="table-icon hidden-480">

    </td>
    <td class="table-date hidden-480">
        10. Feb
    </td>
</tr>
<tr>
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        Sed Laborum
    </td>
    <td>
        Lorem ipsum ut in in eiusmod ut occaecat tempor.
    </td>
    <td class="table-icon hidden-480">
        <i class="icon-paper-clip"></i>
    </td>
    <td class="table-date hidden-480">
        9. Feb
    </td>
</tr>
<tr>
    <td class="table-checkbox hidden-480">
        <input class="selectable" type="checkbox">
    </td>
    <td class="table-icon hidden-480">
        <a href="#" class="sel-star"><i class="icon-star"></i></a>
    </td>
    <td class="table-fixed-medium">
        Duis amet
    </td>
    <td>Lorem ipsum Duis amet sed laborum veniam incididunt occaecat.</td>
    <td class="table-icon hidden-480">

    </td>
    <td class="table-date hidden-480">
        8. Feb
    </td>
</tr>

</tbody>
</table>
</div>
</div>
</div>
     </div>
     </div>
     </div>
</body>
</html>