<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="padding:10px">
    <table class="easyui-datagrid" title="栏目列表" id="categoryList" data-options="">

    </table>
</div>


<div id="editRowWindow" class="easyui-window"
     data-options="title:'编辑栏目',modal:true,closed:true,iconCls:'icon-edit',width:530,height:380,top:138,left:400"
     style="padding:10px;">

</div>

<script type="text/javascript">
    jQuery(function () {
        $('#categoryList').datagrid({
            height: ($(window).height() * 0.83),
            iconCls: 'icon-grid',
            nowrap: false,
            idField: 'cid',
            pageSize: 20,
            pageList: [5, 10, 20, 50, 100],
            url: ctx + '/admin/category/list.html',
            pagination: true,
            singleSelect: true,
            columns: [[
                {field: 'name', title: '栏目名称', width: 100},
                {field: 'seqno', title: '排序序号'},
                {
                    field: 'isfixed', title: '是否固定', formatter: function (value, row, index) {
                    return value == 1 ? '是' : '否';
                }
                },
                {
                    field: 'cid', title: '操作', width: 140, align: 'center', formatter: function (value, row, index) {
                    if (row.isfixed == 1) {
                        return '';
                    }
                    var updBtn = '<a href="javascript:;" class="operate_btn_edit easyui-linkbutton" data-options="iconCls:\'icon-edit\'" onclick="editRow(' + value + ')">编辑</a>';
                    var delBtn = '<a href="javascript:;" class="operate_btn_del  easyui-linkbutton" data-options="iconCls:\'icon-cancel\'" onclick="delRow(' + value + ')">删除</a>';
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

    var editRow = function (cid) {
        $('#categoryList').datagrid('selectRecord', cid);
        var row = $('#categoryList').datagrid('getSelected');
        $('#editRowWindow').window({
            href: ctx + '/admin/category/to_update.html',
            queryParams: {
                id: row.cid
            }
        });
        $('#editRowWindow').window('open');
    };

    var delRow = function (cid) {
        $.messager.confirm('操作提示', "确认删除吗?", function (r) {
            if (r) {
                $('#categoryList').datagrid('selectRecord', cid);
                var row = $('#categoryList').datagrid('getSelected');
                row.valid = 0;

                $.ajax({
                    url: ctx + '/admin/category/delete.html',
                    type: 'post',
                    data: row,
                    async: false,
                    dataType: 'json',
                    success: function (data, textStatus) {
                        if (data.result == 'ok') {
                            var rowIndex = $('#categoryList').datagrid('getRowIndex', row);
                            $('#categoryList').datagrid('deleteRow', rowIndex);
                        } else {
                            alert(data.msg);
                        }
                    }
                });
            }
        });
    }

</script>