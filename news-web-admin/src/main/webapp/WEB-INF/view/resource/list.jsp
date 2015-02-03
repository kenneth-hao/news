<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="padding:10px">
    <table class="easyui-datagrid" title="资源列表" id="resourceList" data-options="">

    </table>
</div>


<script type="text/javascript">
    jQuery(function () {
        $('#resourceList').treegrid({
            height: ($(window).height() * 0.83),
            iconCls: 'icon-grid',
            nowrap: false,
            idField: 'id',
            treeField: "name",
            pageSize: 10,
            pageList: [5, 10, 20, 50, 100],
            url: ctx + '/admin/resource/list.html',
            pagination: true,
            singleSelect: true,
            columns: [[
                {field: 'name', title: '资源名称', width: 250},
                {field: 'alias', title: '唯一标识', width: 250},
                {field: 'url', title: '资源地址', width: 300},
                {field: 'type', title: '资源类型', width: 80, formatter: function(value, row, index) {
                    return value == 1 ? "菜单" : (value == 2 ? "按钮" : (value == 3 ? "数据" : ""));
                }},
                {field: 'seqno', title: '序号', width: 60},
                {
                    field: 'id', title: '操作', width: 140, align: 'center', formatter: function (value, row, index) {
                    return " ";
                }}
            ]]
        });
    });
</script>