<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="padding:10px">
    <table class="easyui-datagrid" title="用户反馈列表" id="feedbackList" data-options="">

    </table>
</div>

<script type="text/javascript">
    jQuery(function () {
        $('#feedbackList').datagrid({
            height: ($(window).height() * 0.83),
            iconCls: 'icon-grid',
            nowrap: false,
            idField: 'ufid',
            pageSize: 20,
            pageList: [5, 10, 20, 50, 100],
            url: ctx + '/admin/feedback/list.html',
            pagination: true,
            singleSelect: true,
            columns: [[
                {field: 'uname', title: '用户名称', width: 100},
                {field: 'content', title: '反馈内容', width: 500},
                {field: 'cdate', title: '提交时间', width: 200},
                {
                    field: 'status', title: '状态', width: 120, formatter: function (value, row, index) {
                    return value == 1 ? "已读" : "未读";
                }
                },
                {
                    field: 'fbid', title: '操作', width: 140, align: 'center', formatter: function (value, row, index) {
                    return " ";
                }
                }
            ]],
            onLoadSuccess: function (data) {
            }
        });
    });


</script>