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
    <div class="easyui-panel" title="添加账号" style="width:430px">
        <div style="padding:10px 60px 20px 60px">
            <form id="addAccountForm" method="post" action="<pj:ctx />/admin/account/add.html">
                <table cellpadding="5">
                    <tr>
                        <td>用户名:</td>
                        <td><input name="userName" class="easyui-textbox" type="text" data-options="required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td>真实姓名:</td>
                        <td><input name="realName" class="easyui-textbox" type="text" data-options="required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td>密码:</td>
                        <td><input name="password" class="easyui-textbox" type="password" data-options="required:true"/>
                        </td>
                    </tr>
                    <tr>
                        <td>再次输入密码:</td>
                        <td><input name="confirmPassword" class="easyui-textbox" type="password"
                                   data-options="required:true"
                                   validType="eqPassword['#addAccountForm input[name=password]']"/></td>
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
    jQuery(function () {
        $.extend($.fn.validatebox.defaults.rules,
                {
                    eqPassword: {
                        validator: function (value, param) {
                            return value == $(param[0]).val();
                        },
                        message: '两次输入的密码不一致！'
                    }
                });
    });

    function submitForm() {
        $('#addAccountForm').form('submit', {
            onSubmit: function () {
                // do some check
                // return false to prevent submit;
            },
            success: function (data) {
                var dataObj = jQuery.parseJSON(data);
                if (dataObj.result == 'ok') {
                    $.messager.alert('操作提示', '添加成功!');
                    $('#addAccountForm').form('clear');
                } else {
                    $.messager.alert('操作提示', data.msg);
                }
            }
        });
    }
    function clearForm() {
        $('#addAccountForm').form('clear');
    }
</script>