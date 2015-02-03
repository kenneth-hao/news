<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="padding:10px">
    <table class="easyui-datagrid" title="操作日志列表 - 后台" id="logOperationList" data-options="">

    </table>
</div>


<script type="text/javascript">
    jQuery(function () {
        $('#logOperationList').datagrid({
            height: ($(window).height() * 0.83),
            iconCls: 'icon-grid',
            nowrap: false,
            idField: 'id',
            pageSize: 10,
            pageList: [5, 10, 20, 50, 100],
            url: ctx + '/admin/log_operation/list.html',
            pagination: true,
            singleSelect: true,
            columns: [[
                {field: 'username', title: '操作账号', width: 100},
                {field: 'module', title: '操作模块', width: 150},
                {field: 'action', title: '操作行为', width: 150},
                {field: 'actionTime', title: '操作时间', width: 180},
                {field: 'userIP', title: '操作者IP', width: 150},
                {field: 'elapseTime', title: '操作耗时', width: 80}
            ]]
        });
    });
</script>