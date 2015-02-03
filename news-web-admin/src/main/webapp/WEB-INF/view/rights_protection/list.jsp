<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="padding:10px">
    <table class="easyui-datagrid" title="维权列表" id="list" data-options="">

    </table>
</div>

<script type="text/javascript">
    jQuery(function () {
        $('#list').datagrid({
            height: ($(window).height() * 0.83),
            iconCls: 'icon-grid',
            nowrap: false,
            idField: 'rpid',
            pageSize: 10,
            pageList: [5, 10, 20, 50, 100],
            url: ctx + '/admin/rights_protection/list.html',
            pagination: true,
            singleSelect: true,
            columns: [[
                {field: 'title', title: '标题', width: 150},
                {field: 'author', title: '发布人', width: 100},
                {field: 'mobile', title: '手机号', width: 150},
                {field: 'carinfo', title: '车辆信息', width: 140},
                {field: 'carno', title: '车牌号', width: 120},
                {field: 'cdate', title: '创建时间', width: 170},
                {field: 'udate', title: '更新时间', width: 170},
                {
                    field: 'rpid', title: '操作', width: 140, align: 'center', formatter: function (value, row, index) {
                    return "";
                }
                }
            ]]
        });
    });


</script>