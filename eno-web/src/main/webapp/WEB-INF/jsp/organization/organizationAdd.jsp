<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

 <form action="doInsert" method="post">
	<table height="100"  border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
	  <tr>
        <td width="80" align="right">组织机构：</td>
        <td height="35"><input name="orgid" type="text" id="orgid" size="30">
         </td>
      </tr>
      <tr>
        <td width="80" align="right">语言列：</td>
        <td width="198" height="39">
          <input name="langcode" type="text" size="30" id="langcode">
        </td>
      </tr>
      <tr>
        <td width="80" align="right">描述：</td>
         <td height="35"><textarea name="description" rows="3" id="description" ></textarea>
         </td>
      </tr>
      <tr>
        <td width="180" align="right">缺省GL清算科目：</td>
         <td height="35"><input name="clearingacct" type="text" id="clearingacct" size="30">
         </td>
      </tr>
      <tr>
        <td width="180" align="right">基本货币1的货币代码：</td>
        <td height="35"><input name="basecurrency1" type="text" id="basecurrency1" size="30">
         </td>
      </tr>
      <tr>
        <td width="180" align="right">基本货币2的货币代码：</td>
        <td height="35"><input name="basecurrency2" type="text" id="basecurrency2" size="30"/>
         </td>
      </tr>
      <tr >
        <td align="center"></td>
        <td align="center">
            <input name="Submit" type="submit" class="btn btn-primary" value="保存"">
            <input name="Submit2" type="button" class="btn btn-primary" value="关闭" onClick="javascript:history.back(-1);">
        </td>
       </tr>
    </table>
  </form>

   
     