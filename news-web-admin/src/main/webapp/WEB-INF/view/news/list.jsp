<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div style="padding:10px">
    <table class="easyui-datagrid" title="新闻列表" id="newsList" data-options="">

    </table>
</div>

<div id="newsMenu" class="easyui-menu" style="width:120px;">

</div>

<div id="tb" style="padding:2px 5px;">
    <form id="searchNewsForm" method="get">
        发布时间:
        <input class="easyui-datebox" name="dateStart" style="width:110px">
        -
        <input class="easyui-datebox" name="dateEnd" style="width:110px">
        标题:
        <input class="easyui-textbox" name="title" style="width: 200px">
        发布人:
        <input class="easyui-textbox" name="author" style="width: 120px">
        频道分类:
        <input id="q_channelType" name="channelType" class="easyui-combobox" panelHeight="auto" style="width:100px"/>
        所属频道:
        <input id="q_channelId" name="chan  nelId" class="easyui-combobox" panelHeight="auto" style="width:100px"/>
        <a href="javascript:" class="easyui-linkbutton" iconCls="icon-search" id="searchNewsBtn">查询</a>
    </form>
</div>

<div id="editNewsWindow" class="easyui-window"
     data-options="title:'编辑新闻',modal:true,closed:true,iconCls:'icon-edit',width:1200,height:750,top:10,left:10,maximized:true"
     style="padding:10px;">

</div>

<div id="previewNewsDialog" class="easyui-dialog" title="Fluid Dialog" style="padding:10px;" data-options="
    title:'新闻预览',modal:true,closed:true,iconCls:'icon-tip',width:380,height:540,top:138,left:400,
    onResize:function(){
      $(this).dialog('center');
    }">
    <iframe scrolling="auto" id='previewNewsIFrame' frameborder="0" src="" style='width:100%;height:100%;'></iframe>
</div>
<script type="text/javascript">

    $(function () {

        $('#searchNewsBtn').linkbutton({
            onClick: function () {
                var queryParams = $('#searchNewsForm').serializeObject();
                if (queryParams.dateStart) {
                    queryParams.dateStart = queryParams.dateStart + " 00:00:00";
                }
                if (queryParams.dateEnd) {
                    queryParams.dateEnd = queryParams.dateEnd + " 23:59:59";
                }
                queryParams.valid = 1;
                $('#newsList').datagrid('options').queryParams = queryParams;
                console.log($('#newsList').datagrid('options').queryParams);
                $('#newsList').datagrid('reload');
            }
        });

        var dataChannelType = [{value: '', text: '请选择'}, {value: 1, text: '常规频道'}, {value: 2, text: '车企频道'}];

        $('#q_channelType').combobox({
            data: dataChannelType,
            valueField: 'value',
            textField: 'text',
            'onSelect': function (record) {
                $('#q_channelId').combobox('clear');
                $('#q_channelId').combobox('loadData', []);
                if (record.value == 1) {
                    $('#q_channelId').combobox({
                        url: ctx + "/admin/category/query.html",
                        valueField: 'cid',
                        textField: 'name'
                    });
                } else if (record.value == 2) {
                    $('#q_channelId').combobox({
                        url: ctx + "/admin/car_enterprise/query.html",
                        valueField: 'ceid',
                        textField: 'name'
                    });
                }
            }
        });

        $('#newsList').datagrid({
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
            queryParams: {valid: 1},
            onRowContextMenu: function (e, rowIndex, rowData) {
                e.preventDefault();
                $(this).datagrid("selectRow", rowIndex);
                $('#newsMenu').empty();
                $('#newsMenu').menu('appendItem', {
                    text: '预览', iconCls: 'icon-search', onclick: function () {
                        $('#newsList').datagrid('selectRow', rowIndex);
                        var row = $('#newsList').datagrid('getSelected');
                        $('#previewNewsIFrame')[0].src = row.linkUrl;
                        $('#previewNewsDialog').window('open');
                    }
                });

                var statusTextOk = '通过审核';
                var statusIconOk = "icon-edit";
                var statusTextFailure = '未通过审核';
                var statusIconFailure = "icon-remove";
                if (!rowData.status || rowData.status == 0 || rowData.status == 2) {
                    $('#newsMenu').menu('appendItem', {
                        text: statusTextOk, iconCls: statusIconOk, onclick: function () {
                            var row = $('#newsList').datagrid('getSelected');
                            row.status = 1;
                            updateNews(row, '/admin/news/updateStatus.html');
                        }
                    });
                }
                if (!rowData.status || rowData.status == 0 || rowData.status == 1) {
                    $('#newsMenu').menu('appendItem', {
                        text: statusTextFailure, iconCls: statusIconFailure, onclick: function () {
                            var row = $('#newsList').datagrid('getSelected');
                            row.status = 2;
                            updateNews(row, '/admin/news/updateStatus.html');
                        }
                    });
                }

                $('#newsMenu').menu('appendItem', {separator: true});

                var isTopText = rowData.isTop == 0 ? "设置置顶" : "取消置顶";
                var isTopIcon = rowData.isTop == 0 ? "icon-add" : "icon-remove";
                $('#newsMenu').menu('appendItem', {
                    text: isTopText, iconCls: isTopIcon, onclick: function () {
                        var row = $('#newsList').datagrid('getSelected');
                        row.isTop = row.isTop == 1 ? 0 : 1;
                        updateNews(row, '/admin/news/updateTop.html');
                    }
                });

                var isCarouselText = rowData.isCarousel == 0 ? "设置轮播" : "取消轮播";
                var isCarouselIcon = rowData.isCarousel == 0 ? "icon-add" : "icon-remove";
                $('#newsMenu').menu('appendItem', {
                    text: isCarouselText, iconCls: isCarouselIcon, onclick: function () {
                        var row = $('#newsList').datagrid('getSelected');
                        row.isCarousel = row.isCarousel == 1 ? 0 : 1;
                        updateNews(row, '/admin/news/updateCarousel.html');
                    }
                });

                var isRecommandText = rowData.isRecommand == 0 ? "设置推荐" : "取消推荐";
                var isRecommandIcon = rowData.isRecommand == 0 ? "icon-add" : "icon-remove";
                $('#newsMenu').menu('appendItem', {
                    text: isRecommandText, iconCls: isRecommandIcon, onclick: function () {
                        var row = $('#newsList').datagrid('getSelected');
                        row.isRecommand = row.isRecommand == 1 ? 0 : 1;
                        updateNews(row, '/admin/news/updateRecommand.html');
                    }
                });


                $('#newsMenu').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
            },
            columns: [[
                {field: 'title', title: '标题', width: 200},
                {
                    field: 'channelType', title: '频道分类', width: 60, formatter: function (value, row, index) {
                    if (value == 1) {
                        return "常规频道";
                    } else if (value == 2) {
                        return "车企频道";
                    }
                }
                },
                {field: 'channelName', title: '所属频道', width: 70},
                {field: 'author', title: '发布人', width: 80},
                {field: 'publishTime', title: '发布时间', width: 100},
                {field: 'linkUrl', title: '链接地址', width: 340},
                {
                    field: 'status', title: '审核状态', width: 50, formatter: function (value, row, index) {
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
                    field: 'isTop', title: '是否置顶', width: 50, formatter: function (value, row, index) {
                    if (value == 1) {
                        return "是";
                    } else {
                        return "否";
                    }
                }
                },
                {
                    field: 'isCarousel', title: '是否轮播', width: 50, formatter: function (value, row, index) {
                    if (value == 1) {
                        return "是";
                    } else {
                        return "否";
                    }
                }
                },
                {
                    field: 'isRecommand', title: '是否推荐', width: 50, formatter: function (value, row, index) {
                    if (value == 1) {
                        return "是";
                    } else {
                        return "否";
                    }
                }
                },
                {
                    field: 'nid', title: '操作', width: 140, align: 'center', formatter: function (value, row, index) {
                    var updBtn = '<a href="javascript:;" class="operate_btn_edit easyui-linkbutton" data-options="iconCls:\'icon-edit\'" onclick="editRow(' + value + ')">编辑</a>';
                    var delBtn = '<a href="javascript:;" class="operate_btn_del  easyui-linkbutton" data-options="iconCls:\'icon-cancel\'" onclick="delRow(' + value + ')">删除</a>';
                    return " " + updBtn + " " + delBtn;
                }
                }
            ]],
            onLoadSuccess: function (data) {
                $('.operate_btn_edit').linkbutton();
                $('.operate_btn_del').linkbutton();
            }
        });

    });

    function updateNews(row, url) {
        $.ajax({
            url: ctx + url,
            type: 'post',
            data: row,
            async: false,
            dataType: 'json',
            success: function (data, textStatus) {
                if (data.result == 'ok') {
                    var rowIndex = $('#newsList').datagrid('getRowIndex', row);
                    $('#newsList').datagrid('refreshRow', rowIndex);
                    $('.operate_btn_edit').linkbutton();
                    $('.operate_btn_del').linkbutton();
                } else {
                    alert(data.msg);
                }
            }
        });
    }

    var editRow = function (id) {
        $('#newsList').datagrid('selectRecord', id);
        var row = $('#newsList').datagrid('getSelected');
        $('#editNewsWindow').window({
            href: ctx + '/admin/news/to_update.html',
            queryParams: {
                nid: row.nid
            }
        });
        $('#editNewsWindow').window('open');
    };
    var delRow = function (id) {
        $.messager.confirm('操作提示', "确认删除吗?", function (r) {
            if (r) {
                $('#newsList').datagrid('selectRecord', id);
                var row = $('#newsList').datagrid('getSelected');
                row.valid = 0;

                $.ajax({
                    url: ctx + '/admin/news/delete.html',
                    type: 'post',
                    data: row,
                    async: false,
                    dataType: 'json',
                    success: function (data, textStatus) {
                        if (data.result == 'ok') {
                            var rowIndex = $('#newsList').datagrid('getRowIndex', row);
                            $('#newsList').datagrid('deleteRow', rowIndex);
                        } else {
                            alert(data.msg);
                        }
                    }
                });
            }
        });
    }

</script>