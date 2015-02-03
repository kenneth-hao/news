<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="padding:10px">
    <table class="easyui-datagrid" title="用户收藏列表" id="userFavoriteList" data-options="">

    </table>
</div>

<script type="text/javascript">
    jQuery(function () {
        $('#userFavoriteList').datagrid({
            height: ($(window).height() * 0.83),
            iconCls: 'icon-grid',
            nowrap: false,
            idField: 'ufid',
            pageSize: 20,
            pageList: [5, 10, 20, 50, 100],
            url: ctx + '/admin/user_favorite/list.html',
            pagination: true,
            singleSelect: true,
            columns: [[
                {field: 'uname', title: '用户名称', width: 100},
                {field: 'title', title: '新闻标题', width: 200},
                {field: 'author', title: '发布人', width: 100},
                {field: 'cdate', title: '收藏时间', width: 200},
                {
                    field: 'ufid', title: '操作', width: 140, align: 'center', formatter: function (value, row, index) {
                    return " ";
                }
                }
            ]],
            onLoadSuccess: function (data) {
            }
        });
    });


</script>