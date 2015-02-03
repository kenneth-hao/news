<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="padding:10px">
    <table class="easyui-datagrid" title="新闻列表" id="recycleNewsList" data-options="">

    </table>
</div>

<div id="tb" style="padding:2px 5px;">
    <form id="r_searchNewsForm" method="get">
        发布时间:
        <input class="easyui-datebox" name="dateStart" style="width:110px">
        -
        <input class="easyui-datebox" name="dateEnd" style="width:110px">
        频道分类:
        <input id="r_q_channelType" name="channelType" class="easyui-combobox" panelHeight="auto" style="width:100px"/>
        所属频道:
        <input id="r_q_channelId" name="channelId" class="easyui-combobox" panelHeight="auto" style="width:100px"/>
        <a href="javascript:" class="easyui-linkbutton" iconCls="icon-search" id="r_searchNewsBtn">查询</a>
    </form>
</div>

<script type="text/javascript">

    $(function () {

        $('#r_searchNewsBtn').linkbutton({
            onClick: function () {
                var queryParams = $('#r_searchNewsForm').serializeObject();
                if (queryParams.dateStart) {
                    queryParams.dateStart = queryParams.dateStart + " 00:00:00";
                }
                if (queryParams.dateEnd) {
                    queryParams.dateEnd = queryParams.dateEnd + " 23:59:59";
                }
                queryParams.valid = 0;
                $('#recycleNewsList').datagrid('options').queryParams = queryParams;
                console.log($('#recycleNewsList').datagrid('options').queryParams);
                $('#recycleNewsList').datagrid('reload');
            }
        });

        var dataChannelType = [{value: '', text: '请选择'}, {value: 1, text: '常规频道'}, {value: 2, text: '车企频道'}];

        $('#r_q_channelType').combobox({
            data: dataChannelType,
            valueField: 'value',
            textField: 'text',
            'onSelect': function (record) {
                $('#r_q_channelId').combobox('clear');
                $('#r_q_channelId').combobox('loadData', []);
                if (record.value == 1) {
                    $('#r_q_channelId').combobox({
                        url: ctx + "/admin/category/query.html",
                        valueField: 'cid',
                        textField: 'name'
                    });
                } else if (record.value == 2) {
                    $('#r_q_channelId').combobox({
                        url: ctx + "/admin/car_enterprise/query.html",
                        valueField: 'ceid',
                        textField: 'name'
                    });
                }
            }
        });

        $('#recycleNewsList').datagrid({
            height: ($(window).height() * 0.83),
            toolbar: '#tb',
            iconCls: 'icon-grid',
            nowrap: false,
            idField: 'nid',
            pageSize: 10,
            pageList: [5, 10, 20, 50, 100],
            url: ctx + '/admin/news/list.html',
            fitColumns: true,
            pagination: true,
            singleSelect: true,
            queryParams: {valid: 0},
            columns: [[
                {field: 'title', title: '标题', width: 200},
                {
                    field: 'channelType', title: '频道分类', width: 100, formatter: function (value, row, index) {
                    if (value == 1) {
                        return "常规频道";
                    } else if (value == 2) {
                        return "车企频道";
                    }
                }
                },
                {field: 'channelName', title: '所属频道', width: 100},
                {field: 'author', title: '发布人', width: 100},
                {field: 'publishTime', title: '发布时间', width: 150},
                {
                    field: 'status', title: '审核状态', width: 100, formatter: function (value, row, index) {
                    if (value == 1) {
                        return "通过";
                    } else if (value == 0) {
                        return "未审核";
                    } else if (value == 2) {
                        return "未通过";
                    } else {
                        return "未审核";
                    }
                }
                },
                {
                    field: 'isTop', title: '是否置顶', width: 100, formatter: function (value, row, index) {
                    if (value == 1) {
                        return "是";
                    } else {
                        return "否";
                    }
                }
                },
                {
                    field: 'isCarousel', title: '是否轮播', width: 100, formatter: function (value, row, index) {
                    if (value == 1) {
                        return "是";
                    } else {
                        return "否";
                    }
                }
                },
                {
                    field: 'isRecommand', title: '是否推荐', width: 100, formatter: function (value, row, index) {
                    if (value == 1) {
                        return "是";
                    } else {
                        return "否";
                    }
                }
                },
                {
                    field: 'nid', title: '操作', width: 140, align: 'center', formatter: function (value, row, index) {
                    var revBtn = '<a href="javascript:;" class="operate_btn_revert easyui-linkbutton" data-options="iconCls:\'icon-redo\'" onclick="revertRow(' + value + ')">恢复</a>';
                    return " " + revBtn;
                }
                }
            ]],
            onLoadSuccess: function (data) {
                $('.operate_btn_revert').linkbutton();
            }
        });

    });

    var revertRow = function (id) {
        $.messager.confirm('操作提示', "确认还原吗?", function (r) {
            if (r) {
                $('#recycleNewsList').datagrid('selectRecord', id);
                var row = $('#recycleNewsList').datagrid('getSelected');
                row.valid = 1;

                $.ajax({
                    url: ctx + '/admin/news/revert.html',
                    type: 'post',
                    data: row,
                    async: false,
                    dataType: 'json',
                    success: function (data, textStatus) {
                        if (data.result == 'ok') {
                            var rowIndex = $('#recycleNewsList').datagrid('getRowIndex', row);
                            $('#recycleNewsList').datagrid('deleteRow', rowIndex);
                        } else {
                            alert(data.msg);
                        }
                    }
                });
            }
        });
    }

</script>