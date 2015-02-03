<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/1/4
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/taglib/ctx.tld" prefix="pj" %>

<div style="padding:10px">
    <div class="easyui-panel" title="添加角色" style="width:430px">
        <div style="padding:10px 60px 20px 60px">
            <form id="addRoleForm" method="post" action="<pj:ctx />/admin/role/add.html">
                <table cellpadding="5">
                    <tr>
                        <td>角色名称:</td>
                        <td><input name="name" class="easyui-textbox" type="text" data-options="required:true"/></td>
                    </tr>
                    <tr>
                        <td>唯一标识:</td>
                        <td><input name="alias" class="easyui-textbox" type="text" data-options="required:true"/></td>
                    </tr>
                </table>
            </form>
            <div style="text-align:center;padding:5px">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
            </div>
        </div>
    </div>
</div>
<script>

    function submitForm() {
        $('#addAccountForm').form('submit', {
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
                    $.messager.alert('操作提示', '添加成功!');
                    $('#addAccountForm').form('reset');
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
        $('#addAccountForm').form('reset');
    }
</script>