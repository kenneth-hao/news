<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="padding:10px">
    <table class="easyui-datagrid" title="登陆日志列表 - 后台" id="logLoginList" data-options="">

    </table>
</div>


<script type="text/javascript">
    jQuery(function () {
        $('#logLoginList').datagrid({
            height: ($(window).height() * 0.83),
            iconCls: 'icon-grid',
            nowrap: false,
            idField: 'id',
            pageSize: 10,
            pageList: [5, 10, 20, 50, 100],
            url: ctx + '/admin/log_login/list.html',
            pagination: true,
            singleSelect: true,
            columns: [[
                {field: 'userName', title: '登陆账号', width: 100},
                {field: 'loginTime', title: '登陆时间', width: 180},
                {field: 'loginIP', title: '登陆IP', width: 150}
            ]]
        });
    });
</script>