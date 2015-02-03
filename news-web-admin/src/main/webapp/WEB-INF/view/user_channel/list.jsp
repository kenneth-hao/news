<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="padding:10px">
    <table class="easyui-datagrid" title="用户频道列表" id="userChannelList" data-options="">

    </table>
</div>

<script type="text/javascript">
    jQuery(function () {
        $('#userChannelList').datagrid({
            height: ($(window).height() * 0.83),
            iconCls: 'icon-grid',
            nowrap: false,
            idField: 'ucid',
            pageSize: 20,
            pageList: [5, 10, 20, 50, 100],
            url: ctx + '/admin/user_channel/list.html',
            pagination: true,
            singleSelect: true,
            columns: [[
                {field: 'uname', title: '用户名称', width: 100},
                {
                    field: 'channelType', title: '频道类型', width: 200, formatter: function (value, row, index) {
                    return value == 1 ? '常规频道' : '车企频道';
                }
                },
                {field: 'channelName', title: '频道名称', width: 100},
                {field: 'seqno', title: '排序标志位'},
                {field: 'cdate', title: '定制时间', width: 200},
                {
                    field: 'ucid', title: '操作', width: 140, align: 'center', formatter: function (value, row, index) {
                    return " ";
                }
                }
            ]]
        });
    });


</script>