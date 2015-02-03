<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="padding:10px">
    <table class="easyui-datagrid" title="评论列表" id="commentList" data-options="">

    </table>
</div>


<script type="text/javascript">
    jQuery(function () {
        $('#commentList').datagrid({
            height: ($(window).height() * 0.83),
            iconCls: 'icon-grid',
            nowrap: false,
            idField: 'cmid',
            pageSize: 10,
            pageList: [5, 10, 20, 50, 100],
            url: ctx + '/admin/comment/list.html',
            pagination: true,
            singleSelect: true,
            columns: [[
                {field: 'content', title: '评论内容', width: 200},
                {field: 'ntitle', title: '新闻标题', width: 250},
                {field: 'uname', title: '用户', width: 120},
                {field: 'cdate', title: '评论时间', width: 180},
                {
                    field: 'cmid', title: '操作', width: 140, align: 'center', formatter: function (value, row, index) {
                    return " ";
                }
                }
            ]]
        });
    });
</script>