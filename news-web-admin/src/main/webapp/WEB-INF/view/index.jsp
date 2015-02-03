<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="pj" uri="/WEB-INF/taglib/ctx.tld" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>新华社汽车新闻发布后台管理系统</title>
    <link rel="stylesheet" type="text/css" href="<pj:ctx />/easyui/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="<pj:ctx />/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<pj:ctx />/easyui/themes/color.css">
    <script type="text/javascript" src="<pj:ctx />/js/jquery.min.js"></script>
    <script type="text/javascript" src="<pj:ctx />/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<pj:ctx />/easyui/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<pj:ctx />/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="<pj:ctx />/ckfinder/ckfinder.js"></script>
</head>
<body>
<div id="processBar" style="width:400px;"></div>

<h2>新华社发布汽车后台管理系统</h2>

<div style="margin:20px 0;"></div>
<div class="easyui-layout" data-options="fit:true">
    <div id="p" data-options="region:'west'" title="菜单" style="width:15%;padding:10px;">
        <div class="easyui-panel" style="padding:5px">
            <ul id="menu" class="easyui-tree" data-options="fit:true"></ul>
        </div>
    </div>
    <div data-options="region:'center'" title="内容">
        <div class="easyui-tabs" data-options="fit:true" border="false" id="tabs">

        </div>
    </div>
</div>

<script type="text/javascript">
    var ctx = '<pj:ctx />';
    var ctxImgPath = '${ctxImgPath }';

    //建立一個可存取到該file的url
    var getObjectURL = function (file) {
        var url = null;
        if (window.createObjectURL != undefined) { // basic
            url = window.createObjectURL(file);
        } else if (window.URL != undefined) { // mozilla(firefox)
            url = window.URL.createObjectURL(file);
        } else if (window.webkitURL != undefined) { // webkit or chrome
            url = window.webkitURL.createObjectURL(file);
        }
        return url;
    };
    var App = {};

    App.showProgress = function (isShow, title, msg) {
        if (!isShow) {
            $.messager.progress('close');
        } else {
            $.messager.progress({
                title: title,
                msg: msg
            })
        }
    };
    $(function () {
        var menu = [{
            "id": 1,
            "text": "主页",
            "attributes": {
                url: ctx + "/admin/to_main.html"
            }
        }, {
            "id": 2,
            "text": "新闻管理",
            "state": "open",
            "children": [{
                "id": 21,
                "text": "新闻列表",
                "attributes": {
                    url: ctx + "/admin/news/to_list.html"
                }
            }, {
                "id": 22,
                "text": "添加新闻",
                "attributes": {
                    url: ctx + "/admin/news/to_add.html"
                }
            }, {
                "id": 23,
                "text": "新闻回收站",
                "attributes": {
                    url: ctx + "/admin/news/to_recycle.html"
                }
            }]
        }, {
            id: 3,
            text: '车企管理',
            state: "close",
            children: [
                {
                    "id": 31,
                    "text": "车企列表",
                    "attributes": {
                        url: ctx + "/admin/car_enterprise/to_list.html"
                    }
                }, {
                    "id": 32,
                    "text": "添加车企",
                    "attributes": {
                        url: ctx + "/admin/car_enterprise/to_add.html"
                    }
                }
            ]
        }, {
            id: 4,
            text: '栏目管理',
            state: "close",
            children: [
                {
                    "id": 41,
                    "text": "栏目列表",
                    "attributes": {
                        url: ctx + "/admin/category/to_list.html"
                    }
                }, {
                    "id": 42,
                    "text": "添加栏目",
                    "attributes": {
                        url: ctx + "/admin/category/to_add.html"
                    }
                }
            ]
        }, {
            id: 11,
            text: '用户评论管理',
            state: "close",
            children: [
                {
                    "id": 111,
                    "text": "用户评论列表",
                    "attributes": {
                        url: ctx + "/admin/comment/to_list.html"
                    }
                }
            ]
        }, {
            id: 16,
            text: '用户反馈管理',
            state: "close",
            children: [
                {
                    "id": 161,
                    "text": "用户反馈列表",
                    "attributes": {
                        url: ctx + "/admin/feedback/to_list.html"
                    }
                }
            ]
        }, {
            id: 15,
            text: '用户频道管理',
            state: "close",
            children: [
                {
                    "id": 151,
                    "text": "用户频道列表",
                    "attributes": {
                        url: ctx + "/admin/user_channel/to_list.html"
                    }
                }
            ]
        }, {
            id: 14,
            text: '用户收藏管理',
            state: "close",
            children: [
                {
                    "id": 141,
                    "text": "用户收藏列表",
                    "attributes": {
                        url: ctx + "/admin/user_favorite/to_list.html"
                    }
                }
            ]
        }, {
            id: 12,
            text: '广告管理',
            state: "close",
            children: [
                {
                    "id": 111,
                    "text": "广告列表",
                    "attributes": {
                        url: ctx + "/admin/ad/to_list.html"
                    }
                },
                {
                    "id": 112,
                    "text": "添加广告",
                    "attributes": {
                        url: ctx + "/admin/ad/to_add.html"
                    }
                }
            ]
        }, {
            id: 5,
            text: '维权管理',
            state: "close",
            children: [
                {
                    "id": 51,
                    "text": "维权列表",
                    "attributes": {
                        url: ctx + "/admin/rights_protection/to_list.html"
                    }
                }
            ]
        }, {
            id: 6,
            text: '用户管理',
            state: "close",
            children: [
                {
                    "id": 61,
                    "text": "用户列表",
                    "attributes": {
                        url: ctx + "/admin/user/to_list.html"
                    }
                }
            ]
        }, {
            id: 10,
            text: '日志管理',
            state: "close",
            children: [
                {
                    "id": 101,
                    "text": "操作日志 - 后台",
                    "attributes": {
                        url: ctx + "/admin/log_operation/to_list.html"
                    }
                }, {
                    "id": 102,
                    "text": "登录日志 - 后台",
                    "attributes": {
                        url: ctx + "/admin/log_login/to_list.html"
                    }
                }
            ]
        }, {
            id: 7,
            text: '账号管理',
            state: "close",
            children: [
                {
                    "id": 71,
                    "text": "账号列表",
                    "attributes": {
                        url: ctx + "/admin/account/to_list.html"
                    }
                }, {
                    "id": 72,
                    "text": "添加账号",
                    "attributes": {
                        url: ctx + "/admin/account/to_add.html"
                    }
                }, {
                    "id": 73,
                    "text": "分配角色",
                    "attributes": {
                        url: ctx + "/admin/account/to_distribute_role.html"
                    }
                }
            ]
        }, {
            id: 8,
            text: '角色管理',
            state: "close",
            children: [
                {
                    "id": 81,
                    "text": "角色列表",
                    "attributes": {
                        url: ctx + "/admin/role/to_list.html"
                    }
                }, {
                    "id": 82,
                    "text": "添加角色",
                    "attributes": {
                        url: ctx + "/admin/role/to_add.html"
                    }
                }, {
                    "id": 83,
                    "text": "分配资源",
                    "attributes": {
                        url: ctx + "/admin/role/to_distribute_resource.html"
                    }
                }
            ]
        }, {
            id: 9,
            text: '资源管理',
            state: "close",
            children: [
                {
                    "id": 91,
                    "text": "资源列表",
                    "attributes": {
                        url: ctx + "/admin/resource/to_list.html"
                    }
                }, {
                    "id": 92,
                    "text": "添加资源",
                    "attributes": {
                        url: ctx + "/admin/resource/to_add.html"
                    }
                }
            ]
        }, {
            id: 13,
            text: '菜单管理',
            state: "close",
            children: [
                {
                    "id": 131,
                    "text": "菜单列表",
                    "attributes": {
                        url: ctx + "/admin/menu/to_list.html"
                    }
                }, {
                    "id": 132,
                    "text": "添加菜单",
                    "attributes": {
                        url: ctx + "/admin/menu/to_add.html"
                    }
                }
            ]
        }
        ];

        $('#menu').tree({
            data: menu,
            animate: true,
            onSelect: function (node) {
                var url = node.attributes ? node.attributes.url : '';
                if (url) {
                    Open(node.text, node.attributes.url);
                }
            }
        });

        //在右边center区域打开菜单，新增tab
        var Open = function (text, url) {
            if ($("#tabs").tabs('exists', text)) {
                $('#tabs').tabs('select', text);
                var tab = $('#tabs').tabs('getSelected');
                tab.panel('refresh', url);
            } else {
                $('#tabs').tabs('add', {
                    title: text,
                    closable: true,
                    href: url
                });
            }
        };
        //绑定tabs的右键菜单
        $("#tabs").tabs({
            fit: true,
            onContextMenu: function (e, title) {
                e.preventDefault();
                $('#tabsMenu').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                }).data("tabTitle", title);
            }
        });

        //实例化menu的onClick事件
        $("#tabsMenu").menu({
            onClick: function (item) {
                CloseTab(this, item.name);
            }
        });

        //几个关闭事件的实现
        function CloseTab(menu, type) {
            var curTabTitle = $(menu).data("tabTitle");
            var tabs = $("#tabs");

            if (type === "close") {
                tabs.tabs("close", curTabTitle);
                return;
            }

            var allTabs = tabs.tabs("tabs");
            var closeTabsTitle = [];

            $.each(allTabs, function () {
                var opt = $(this).panel("options");
                if (opt.closable && opt.title != curTabTitle && type === "Other") {
                    closeTabsTitle.push(opt.title);
                } else if (opt.closable && type === "All") {
                    closeTabsTitle.push(opt.title);
                }
            });

            for (var i = 0; i < closeTabsTitle.length; i++) {
                tabs.tabs("close", closeTabsTitle[i]);
            }
        }

        var indexNode = $('#menu').tree('find', 1);
        $('#menu').tree('select', indexNode.target);


        $.fn.serializeObject = function () {
            var o = {};
            var a = this.serializeArray();
            $.each(a, function () {
                if (o[this.name]) {
                    if (!o[this.name].push) {
                        o[this.name] = [o[this.name]];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        }
    });
</script>
</body>
</html>