<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="padding:10px">
    <table class="easyui-datagrid" title="广告列表" id="adList" data-options="">

    </table>
</div>


<div id="editAdWindow" class="easyui-window"
     data-options="title:'编辑广告',modal:true,closed:true,iconCls:'icon-edit',width:530,height:380,top:138,left:400"
     style="padding:10px;">

</div>

<script type="text/javascript">
    jQuery(function () {
        $('#adList').datagrid({
            height: ($(window).height() * 0.83),
            iconCls: 'icon-grid',
            nowrap: false,
            idField: 'id',
            pageSize: 10,
            pageList: [5, 10, 20, 50, 100],
            url: ctx + '/admin/ad/list.html',
            pagination: true,
            singleSelect: true,
            columns: [[
                {field: 'title', title: '广告标题', width: 200},
                {field: 'cdate', title: '创建时间', width: 170},
                {
                    field: 'id', title: '操作', width: 140, align: 'center', formatter: function (value, row, index) {
                    return " ";
                }
                }
            ]],
            onLoadSuccess: function (data) {

            }
        });
    });

    var editRow = function (index) {

    };
    var delRow = function (index) {

    }

</script>