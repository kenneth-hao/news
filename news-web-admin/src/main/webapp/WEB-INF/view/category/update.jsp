<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/1/4
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/taglib/ctx.tld" prefix="pj" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div style="padding:10px">
    <div style="padding:10px 60px 20px 60px">
        <form id="updateCategoryForm" method="post" action="<pj:ctx />/admin/category/update.html">
            <input name="cid" value="${updatingCategory.cid}" type="hidden"/>
            <table cellpadding="5">
                <tr>
                    <td>栏目名称:</td>
                    <td><input name="name" value="${updatingCategory.name}" class="easyui-textbox" type="text"
                               data-options="required:true,validType:['length[1,6]']"/></td>
                </tr>
                <tr>
                    <td>是否固定:</td>
                    <td>
                        <input id="isFixed" name="isfixed" type="radio" value="1"
                               <c:if test="${updatingCategory.isfixed == 1 }">checked="checked"</c:if> />
                        <label for="isFixed">是</label>
                        <input id="isNotFixed" name="isfixed" type="radio" value="0"
                               <c:if test="${updatingCategory.isfixed == 0 }">checked="checked"</c:if> />
                        <label for="isNotFixed">否</label>
                    </td>
                </tr>
                <tr>
                    <td>排序标志位:</td>
                    <td><input name="seqno" class="easyui-numberspinner"
                               data-options="required:true, min:1, max:999, value: ${updatingCategory.seqno }"/></td>
                </tr>

            </table>
        </form>
        <div style="text-align:center;padding:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
        </div>
    </div>
</div>
<script>
    jQuery(function () {

    });

    function submitForm() {
        $('#updateCategoryForm').form('submit', {
            onSubmit: function () {
                var flag = $(this).form('validate');
                if (flag) {
                    App.showProgress(true, '操作提示', '提交中..');
                }
                return flag;
            },
            success: function (data) {
                App.showProgress(false);
                var dataObj = jQuery.parseJSON(data);
                if (dataObj.result == 'ok') {
                    $.messager.alert('操作提示', '更新成功!');
                    $('#updateCategoryForm').form('reset');
                    var row = $('#categoryList').datagrid('getSelected');
                    var rowIndex = $('#categoryList').datagrid('getRowIndex', row);
                    $('#categoryList').datagrid('updateRow', {index: rowIndex, row: dataObj.updatedCategory});
                    $('#categoryList').datagrid('refreshRow', rowIndex);
                    $('.operate_btn_edit').linkbutton();
                    $('.operate_btn_del').linkbutton();
                    $('#editRowWindow').window('close');
                } else {
                    $.messager.alert('操作提示', data.msg);
                }
            },
            onLoadError: function () {
                App.showProgress(false);
                $.messager.alert('操作提示', '由于网络或服务器太忙，提交失败，请重试！');
            }
        });
    }
    function clearForm() {
        $('#updateCategoryForm').form('reset');
    }
</script>