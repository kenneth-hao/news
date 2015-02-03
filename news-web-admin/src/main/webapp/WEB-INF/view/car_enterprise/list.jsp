<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="padding:10px">
    <table class="easyui-datagrid" title="车企列表" id="carEnterpriseList" data-options="">

    </table>
</div>

<div id="editRowWindow" class="easyui-window"
     data-options="title:'编辑车企',modal:true,closed:true,iconCls:'icon-edit',width:560,height:450,top:138,left:400"
     style="padding:10px;">

</div>

<script type="text/javascript">
    jQuery(function () {
        $('#carEnterpriseList').datagrid({
            height: ($(window).height() * 0.83),
            iconCls: 'icon-grid',
            nowrap: false,
            idField: 'ceid',
            pageSize: 20,
            pageList: [5, 10, 20, 50, 100],
            url: ctx + '/admin/car_enterprise/list.html',
            fitColumns: true,
            pagination: true,
            singleSelect: true,
            columns: [[
                {field: 'name', title: '车企名称', width: 50},
                {field: 'appurl', title: '车企 APP 地址', width: 150},
                {field: 'seqno', title: '排序序号'},
                {
                    field: 'brandImgPath', title: '品牌图标', align: 'center', formatter: function (value, row, index) {
                    var imgSrc = ctxImgPath + '/' + value;
                    var imgTag = "<img title='品牌图标' src='" + imgSrc + "' width='50' height='50' />";
                    return imgTag;
                }
                },
                {
                    field: 'ceid', title: '操作', width: 140, align: 'center', formatter: function (value, row, index) {
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

    var editRow = function (id) {
        $('#carEnterpriseList').datagrid('selectRecord', id);
        var row = $('#carEnterpriseList').datagrid('getSelected');
        $('#editRowWindow').window({
            href: ctx + '/admin/car_enterprise/to_update.html',
            queryParams: {
                id: row.ceid
            }
        });
        $('#editRowWindow').window('open');
    };

    var delRow = function (id) {
        $.messager.confirm('操作提示', "确认删除吗?", function (r) {
            if (r) {
                $('#carEnterpriseList').datagrid('selectRecord', id);
                var row = $('#carEnterpriseList').datagrid('getSelected');
                row.valid = 0;

                $.ajax({
                    url: ctx + '/admin/car_enterprise/delete.html',
                    type: 'post',
                    data: row,
                    async: false,
                    dataType: 'json',
                    success: function (data, textStatus) {
                        if (data.result == 'ok') {
                            var rowIndex = $('#carEnterpriseList').datagrid('getRowIndex', row);
                            $('#carEnterpriseList').datagrid('deleteRow', rowIndex);
                        } else {
                            alert(data.msg);
                        }
                    }
                });
            }
        });
    }

</script>