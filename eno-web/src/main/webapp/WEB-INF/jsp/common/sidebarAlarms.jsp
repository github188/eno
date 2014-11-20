<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../common/taglib.jsp" %>
<%
    //显示菜单元素，只有子元素的菜单添加过滤链接
%>


<div class="left_menu">
    <table>
        <tbody>
        <tr>
            <td class="xfgl bt" rowspan="1">消防管理</td>
            <td <c:if test="${groupname eq '消防系统'}">style="background-color:#39a6ae;"</c:if>><i class="icon_fas"></i><a
                    style="cursor: pointer;" onclick="showAlaramLog('消防系统',this)">消防系统</a></td>
        </tr>

        <tr>
            <td class="afgl bt" rowspan="4">安<br>防<br>管<br>理</td>
            <td <c:if test="${groupname eq '视频监控'}">style="background-color:#39a6ae;"</c:if>><i
                    class="icon_msvdo"></i><a style="cursor: pointer;" onclick="showAlaramLog('视频监控',this)">视频监控</a></td>
        </tr>
        <tr>
            <td <c:if test="${groupname eq '防盗报警'}">style="background-color:#39a6ae;"</c:if>><i
                    class="icon_sassa"></i><a style="cursor: pointer;" onclick="showAlaramLog('防盗报警',this)">防盗报警</a></td>
        </tr>
        <tr>
            <td <c:if test="${groupname eq '门禁管理'}">style="background-color:#39a6ae;"</c:if>><i
                    class="icon_sasac"></i><a style="cursor: pointer;" onclick="showAlaramLog('门禁管理',this)">门禁管理</a></td>
        </tr>
        <tr>
            <td <c:if test="${groupname eq '电子巡更'}">style="background-color:#39a6ae;"</c:if>><i class="icon_ep"></i><a
                    style="cursor: pointer;" onclick="showAlaramLog('电子巡更',this)">电子巡更</a></td>
        </tr>

        <tr>
            <td width="60" class="sbgl bt" rowspan="6">设<br>备<br>管<br>理</td>
            <td <c:if test="${groupname eq '暖通空调'}">style="background-color:#39a6ae;"</c:if>><i class="icon_hvac"></i><a
                    style="cursor: pointer;" onclick="showAlaramLog('暖通空调',this)">暖通空调</a></td>
        </tr>
        <tr>
            <td <c:if test="${groupname eq '给水排水'}">style="background-color:#39a6ae;"</c:if>><i class="icon_wsd"></i><a
                    style="cursor: pointer;" onclick="showAlaramLog('给水排水',this)">给水排水</a></td>
        </tr>
        <tr>
            <td <c:if test="${groupname eq '变配电'}">style="background-color:#39a6ae;"</c:if>><i class="icon_etd"></i><a
                    style="cursor: pointer;" onclick="showAlaramLog('变配电',this)">变配电</a></td>
        </tr>
        <tr>
            <td <c:if test="${groupname eq '公共照明'}">style="background-color:#39a6ae;"</c:if>><i
                    class="icon_lspub"></i><a style="cursor: pointer;" onclick="showAlaramLog('公共照明',this)">公共照明</a></td>
        </tr>
        <tr>
            <td <c:if test="${groupname eq '夜景照明'}">style="background-color:#39a6ae;"</c:if>><i class="icon_lsn"></i><a
                    style="cursor: pointer;" onclick="showAlaramLog('夜景照明',this)">夜景照明</a></td>
        </tr>
        <tr>
            <td <c:if test="${groupname eq '电梯运行'}">style="background-color:#39a6ae;"</c:if>><i class="icon_msem"></i><a
                    style="cursor: pointer;" onclick="showAlaramLog('电梯运行',this)">电梯运行</a></td>
        </tr>

        <tr>
            <td class="yygl bt" rowspan="4">运<br>营<br>管<br>理</td>
            <td <c:if test="${groupname eq '客流统计'}">style="background-color:#39a6ae;"</c:if>><i class="icon_pfe"></i><a
                    style="cursor: pointer;" onclick="showAlaramLog('客流统计',this)">客流统计</a></td>
        </tr>
        <tr>
            <td <c:if test="${groupname eq '停车管理'}">style="background-color:#39a6ae;"</c:if>><i
                    class="icon_parkm"></i><a style="cursor: pointer;" onclick="showAlaramLog('停车管理',this)">停车管理</a></td>
        </tr>
        <tr>
            <td <c:if test="${groupname eq '信息发布'}">style="background-color:#39a6ae;"</c:if>><i class="icon_infp"></i><a
                    style="cursor: pointer;" onclick="showAlaramLog('信息发布',this)">信息发布</a></td>
        </tr>
        <tr>
            <td <c:if test="${groupname eq '背景音乐'}">style="background-color:#39a6ae;"</c:if>><i class="icon_bgmb"></i><a
                    style="cursor: pointer;" onclick="showAlaramLog('背景音乐',this)">背景音乐</a></td>
        </tr>

        <tr>
            <td class="jngl bt" rowspan="1">节能管理</td>
            <td <c:if test="${groupname eq '能耗计量'}">style="background-color:#39a6ae;"</c:if>><i
                    class="icon_envms"></i><a style="cursor: pointer;" onclick="showAlaramLog('能耗计量',this)">能耗计量</a></td>
        </tr>
        </tbody>
    </table>
</div>
