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
    <div class="easyui-panel" title="添加资源" style="width:550px">
        <div style="padding:10px 60px 20px 60px">
            <form id="addResourceForm" method="post" action="<pj:ctx />/admin/resource/add.html">
                <table cellpadding="5">
                    <tr>
                        <td>资源名称:</td>
                        <td><input name="name" class="easyui-textbox" type="text" data-options="required:true"/></td>
                    </tr>
                    <tr>
                        <td>唯一标识:</td>
                        <td><input name="alias" class="easyui-textbox" type="text" data-options="required:true"/></td>
                    </tr>
                    <tr>
                        <td>资源URL:</td>
                        <td><input name="url" class="easyui-textbox" type="text" data-options=""/></td>
                    </tr>
                    <tr>
                        <td>资源类型:</td>
                        <td>
                            <input id="addResourceType" name="type" data-options="required:true" panelHeight="auto"
                                   style="width:100px"/>
                        </td>
                    </tr>
                    <tr>
                        <td>序号:</td>
                        <td><input name="seqno" class="easyui-textbox" type="text" /></td>
                    </tr>
                    <tr>
                        <td>上级资源:</td>
                        <td>
                            <input id="addResourceParentId" name="parentId" style="width:220px" />
                        </td>
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
        var dataResourceType = [{value: 1, text: '菜单'}, {value: 2, text: '按钮'}, {value: 3, text: '数据'}];
        $('#addResourceType').combobox({
            data: dataResourceType,
            valueField: 'value',
            textField: 'text',
            value: '1'
        });

        $('#addResourceParentId').combotree({
            url: ctx + '/admin/resource/tree.html',
            onSelect: function(record) {
                console.log(record)
            }
        });
    });

    function submitForm() {
        $('#addResourceForm').form('submit', {
            success: function (data) {
                var dataObj = jQuery.parseJSON(data);
                if (dataObj.result == 'ok') {
                    $.messager.alert('操作提示', '添加成功!');
                    $('#addResourceForm').form('clear');
                } else {
                    $.messager.alert('操作提示', data.msg);
                }
            }
        });
    }
    function clearForm() {
        $('#addResourceForm').form('clear');
    }
</script>