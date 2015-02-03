<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="padding:10px">
    <table class="easyui-datagrid" title="角色列表" id="roleList" data-options="">

    </table>
</div>


<div id="editRoleWindow" class="easyui-window"
     data-options="title:'编辑角色',modal:true,closed:true,iconCls:'icon-edit',width:530,height:380,top:138,left:400"
     style="padding:10px;">

</div>

<script type="text/javascript">
    jQuery(function () {
        $('#roleList').datagrid({
            height: ($(window).height() * 0.83),
            iconCls: 'icon-grid',
            nowrap: false,
            idField: 'id',
            pageSize: 10,
            pageList: [5, 10, 20, 50, 100],
            url: ctx + '/admin/role/list.html',
            pagination: true,
            singleSelect: true,
            columns: [[
                {field: 'name', title: '角色名称', width: 100},
                {field: 'alias', title: '唯一标识'},
                {
                    field: 'status', title: '状态', formatter: function (value, row, index) {
                    return value == 1 ? '正常' : '停用';
                }
                },
                {field: 'cdate', title: '创建时间'},
                {
                    field: 'id', title: '操作', width: 140, align: 'center', formatter: function (value, row, index) {
                    if (row.alias == 'root') {
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
        $('#roleList').datagrid('selectRow', index);
        var row = $('#roleList').datagrid('getSelected');
        $('#editRoleWindow').window({
            href: ctx + '/admin/role/to_update.html',
            queryParams: {
                id: row.id
            }
        });
        $('#editRoleWindow').window('open');
    };
    var delRow = function (index) {
        $('#roleList').datagrid('selectRow', index);
        var row = $('#roleList').datagrid('getSelected');

        $.ajax({
            url: ctx + '/admin/role/delete.html',
            type: 'post',
            data: row,
            async: false,
            dataType: 'json',
            success: function (data, textStatus) {
                if (data.result == 'ok') {
                    var rowIndex = $('#roleList').datagrid('getRowIndex', row);
                    $('#roleList').datagrid('deleteRow', rowIndex);
                } else {
                    alert(data.msg);
                }
            }
        });
    }

</script>