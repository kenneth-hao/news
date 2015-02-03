<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="padding:10px">
    <table class="easyui-datagrid" title="后台账号列表" id="accountList" data-options="">

    </table>
</div>

<div id="editRowWindow" class="easyui-window"
     data-options="title:'编辑账号',modal:true,closed:true,iconCls:'icon-edit',width:530,height:380,top:138,left:400"
     style="padding:10px;">

</div>

<script type="text/javascript">
    jQuery(function () {
        $('#accountList').datagrid({
            height: ($(window).height() * 0.83),
            iconCls: 'icon-grid',
            nowrap: false,
            idField: 'id',
            pageSize: 10,
            pageList: [5, 10, 20, 50, 100],
            url: ctx + '/admin/account/list.html',
            pagination: true,
            singleSelect: true,
            columns: [[
                {field: 'userName', title: '用户名', width: 100},
                {field: 'realName', title: '真实姓名', width: 120},
                {
                    field: 'status', title: '账号状态', formatter: function (value, row, index) {
                    return value == 1 ? '正常' : '停用';
                }
                },
                {field: 'cdate', title: '创建时间', width: 170},
                {field: 'udate', title: '更新时间', width: 170},
                {
                    field: 'cid', title: '操作', width: 140, align: 'center', formatter: function (value, row, index) {
                    if (row.isfixed == 1) {
                        return '';
                    }
                    var updBtn = '<a href="javascript:;" class="operate_btn_edit easyui-linkbutton" data-options="iconCls:\'icon-edit\'" onclick="editRow(' + index + ')">编辑</a>';
                    var delBtn = '<a href="javascript:;" class="operate_btn_del  easyui-linkbutton" data-options="iconCls:\'icon-cancel\'" onclick="delRow(' + index + ')">删除</a>';
                    return " " + updBtn + " " + delBtn;
                }
                }
            ]],
            onLoadSuccess: function (data) {
                $('.operate_btn_edit').linkbutton();
                $('.operate_btn_del').linkbutton();
            }
        });
    });

    var editRow = function (index) {
        $('#accountList').datagrid('selectRow', index);
        var row = $('#accountList').datagrid('getSelected');
        $('#editRowWindow').window({
            href: ctx + '/admin/account/to_update.html',
            queryParams: {
                id: row.id
            }
        });
        $('#editRowWindow').window('open');
    };

    var delRow = function (index) {
        $('#accountList').datagrid('selectRow', index);
        var row = $('#accountList').datagrid('getSelected');
        row.valid = 0;

        $.ajax({
            url: ctx + '/admin/account/delete.html',
            type: 'post',
            data: row,
            async: false,
            dataType: 'json',
            success: function (data, textStatus) {
                if (data.result == 'ok') {
                    var rowIndex = $('#accountList').datagrid('getRowIndex', row);
                    $('#accountList').datagrid('deleteRow', rowIndex);
                } else {
                    alert(data.msg);
                }
            }
        });
    }

</script>