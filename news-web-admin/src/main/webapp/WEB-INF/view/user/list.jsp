<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="padding:10px">
    <table class="easyui-datagrid" title="用户列表" id="userList" data-options="">

    </table>
</div>

<script type="text/javascript">
    jQuery(function () {
        $('#userList').datagrid({
            height: ($(window).height() * 0.83),
            iconCls: 'icon-grid',
            nowrap: false,
            idField: 'uid',
            pageSize: 10,
            pageList: [5, 10, 20, 50, 100],
            url: ctx + '/admin/user/list.html',
            pagination: true,
            singleSelect: true,
            columns: [[
                {field: 'name', title: '用户名', width: 100},
                {field: 'realname', title: '真实姓名', width: 120},
                {field: 'gender', title: '性别'},
                {field: 'mobile', title: '手机号', width: 150},
                {field: 'email', title: 'E-Mail', width: 180},
                {
                    field: 'usertype', title: '用户类型', formatter: function (value, row, index) {
                    return value == 1 ? '游客' : (value == 2 ? '用户' : '未知');
                }
                },
                {field: 'cdate', title: '创建时间', width: 170},
                {field: 'udate', title: '更新时间', width: 170},
                {
                    field: 'cid', title: '操作', width: 140, align: 'center', formatter: function (value, row, index) {
                    return '';
                }
                }
            ]],
            onLoadSuccess: function (data) {
                $('.operate_btn_edit').linkbutton();
                $('.operate_btn_del').linkbutton();
            }
        });
    });


    var delRow = function (index) {
        $('#userList').datagrid('selectRow', index);
        var row = $('#accountList').datagrid('getSelected');
        row.valid = 0;

        $.ajax({
            url: ctx + '/admin/user/delete.html',
            type: 'post',
            data: row,
            async: false,
            dataType: 'json',
            success: function (data, textStatus) {
                if (data.result == 'ok') {
                    var rowIndex = $('#userList').datagrid('getRowIndex', row);
                    $('#userList').datagrid('deleteRow', rowIndex);
                } else {
                    alert(data.msg);
                }
            }
        });
    }

</script>