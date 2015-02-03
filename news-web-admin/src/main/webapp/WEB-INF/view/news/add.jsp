<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/1/4
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/taglib/ctx.tld" prefix="pj" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="padding:10px">
    <div class="easyui-panel" id="addNewsPanel" title="新闻发布">
        <div style="padding:10px 60px 20px 60px">
            <form id="addNewsForm" method="post" action="<pj:ctx />/admin/news/add.html" enctype="multipart/form-data">
                <table cellpadding="5">
                    <tr>
                        <td>新闻标题:</td>
                        <td><input name="title" class="easyui-textbox" type="text"
                                   data-options="required:true,validType:['length[1,30]']"/></td>
                        <td align="right">发布人:</td>
                        <td><input name="author" class="easyui-textbox" type="text" data-options="required:true"
                                   value="新华社发布汽车"/></td>
                    </tr>
                    <tr>
                        <td>频道分类:</td>
                        <td>
                            <input id="channelType" name="channelType" data-options="required:true" panelHeight="auto"
                                   style="width:100px"/>
                        </td>
                        <td align="right">稿件属性:</td>
                        <td>
                            <input id="fileFormat" name="fileFormat" data-options="required:true" panelHeight="auto"
                                   style="width:100px"/>
                        </td>
                    </tr>
                    <tr>
                        <td>所属频道:</td>
                        <td>
                            <input id="channelId" name="channelId" data-options="required:true" panelHeight="auto"
                                   style="width:100px"/>
                        </td>
                        <td align="right">发布时间:</td>
                        <td>
                            <input id="publishTime" name="publishTime" class="easyui-datetimebox"
                                   data-options="required:true" style="width:200px">
                        </td>
                    </tr>
                    <tr>
                        <td valign="top">上传轮播图:</td>
                        <td colspan="3">
                            <img id="uploadingAddNewsCarouselImg" title="已上传轮播图" src="" width="540" height="333"/><br/>
                            <input id="addNewsCarouselImgFilebox" name="carouselImg" class="f1 easyui-filebox"
                                   value=""/>
                            <small style="padding-left: 5px; color: #ff0000">注意: 推荐上传的图片大小: 1080 * 600</small>
                        </td>
                    </tr>
                    <tr>
                        <td valign="top">上传列表图:</td>
                        <td colspan="3">
                            <img id="uploadingAddNewsListImg" title="已上传列表图" src="" width="300" height="225"/><br/>
                            <input id="addNewsListImgFilebox" name="listImg" class="f1 easyui-filebox" value=""/>
                            <small style="padding-left: 5px; color: #ff0000">注意: 推荐上传的图片大小: 600 * 450</small>
                        </td>
                    </tr>
                    <tr>
                        <td>是否设为轮播:</td>
                        <td>
                            <input id="isCarousel" name="isCarousel" type="radio" value="1"/><label
                                for="isCarousel">是</label>
                            <input id="isNotCarousel" name="isCarousel" type="radio" value="0" checked="checked"/><label
                                for="isNotCarousel">否</label>
                        </td>
                    </tr>

                    <tr>
                        <td>是否设为置顶:</td>
                        <td>
                            <input id="isTop" name="isTop" type="radio" value="1"/><label for="isTop">是</label>
                            <input id="isNotTop" name="isTop" type="radio" value="0" checked="checked"/><label
                                for="isNotTop">否</label>
                        </td>
                    </tr>
                    <tr>
                        <td>是否设为推荐:</td>
                        <td>
                            <input id="isRecommand" name="isRecommand" type="radio" value="1"/><label for="isRecommand">是</label>
                            <input id="isNotRecommand" name="isRecommand" type="radio" value="0"
                                   checked="checked"/><label for="isNotRecommand">否</label>
                        </td>
                    </tr>
                    <tr>
                        <td valign="top">新闻内容:</td>
                        <td colspan="3">
                            <textarea id="addNewsContent" name="content"></textarea>
                        </td>
                    </tr>

                </table>
            </form>
            <div style="text-align:center;padding:5px">
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a>
            </div>
        </div>
    </div>
</div>
<script>

    jQuery(function () {

        $('#addNewsPanel').panel({
            height: ($(window).height() * 0.83)
        });

        var addNewsContent = CKEDITOR.replace('addNewsContent', {height: '380px', width: '880px'});

        var date = new Date();
        var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1);
        var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();

        var hour = date.getHours() > 9 ? date.getHours() : "0" + date.getHours();
        var minute = date.getMinutes() > 9 ? date.getMinutes() : "0" + date.getMinutes();
        var second = date.getSeconds() > 9 ? date.getSeconds() : "0" + date.getSeconds();

        var currentTimeStr = date.getFullYear() + '-' + month + '-' + day + " " + hour + ":" + minute + ":" + second;
        $('#publishTime').datetimebox({
            value: currentTimeStr
        });

        $('#addNewsCarouselImgFilebox').filebox({
            buttonText: '选择图片',
            onChange: function (newValue, oldValue) {
                if (!newValue) {
                    return false;
                }
                //验证上传文件格式是否正确
                var pos = newValue.lastIndexOf(".");
                var lastname = newValue.substring(pos + 1);
                if (lastname.toLowerCase() != "jpg" && lastname.toLowerCase() != "png") {
                    alert("您上传的文件类型为" + lastname + "，图片必须为 JPG, PNG 类型");
                    return false;
                }

                var files = $(this).next('span.filebox').children("input[type=file]")[0].files;
                var objUrl = getObjectURL(files[0]);

                var img = new Image();
                img.src = objUrl;
                //验证上传文件宽高比例

                if (img.height / img.width > 1.5 || img.height / img.width < 1.25) {
                    alert("您上传的图片比例超出范围，宽高比应介于1.25-1.5");
                    return;
                }

                //验证上传文件是否超出了大小
                if (img.fileSize / 1024 > 15000) {
                    alert("您上传的文件大小超出了15000K限制！");
                    return false;
                }

                if (objUrl) {
                    $("#uploadingAddNewsCarouselImg").attr("src", objUrl);
                }
            }
        });


        $('#addNewsListImgFilebox').filebox({
            buttonText: '选择图片',
            required: true,
            editable: false,
            onChange: function (newValue, oldValue) {
                if (!newValue) {
                    return false;
                }
                //验证上传文件格式是否正确
                var pos = newValue.lastIndexOf(".");
                var lastname = newValue.substring(pos + 1);
                if (lastname.toLowerCase() != "jpg" && lastname.toLowerCase() != "png") {
                    alert("您上传的文件类型为" + lastname + "，图片必须为 JPG, PNG 类型");
                    return false;
                }

                var files = $(this).next('span.filebox').children("input[type=file]")[0].files;
                var objUrl = getObjectURL(files[0]);

                var img = new Image();
                img.src = objUrl;
                //验证上传文件宽高比例

                if (img.height / img.width > 1.5 || img.height / img.width < 1.25) {
                    alert("您上传的图片比例超出范围，宽高比应介于1.25-1.5");
                    return;
                }

                //验证上传文件是否超出了大小
                if (img.fileSize / 1024 > 15000) {
                    alert("您上传的文件大小超出了15000K限制！");
                    return false;
                }

                if (objUrl) {
                    $("#uploadingAddNewsListImg").attr("src", objUrl);
                }
            }
        });

        var dataFileFormat = [{value: 1, text: '原创'}, {value: 2, text: '转载'}];
        $('#fileFormat').combobox({
            data: dataFileFormat,
            valueField: 'value',
            textField: 'text',
            value: '1'
        });

        var dataChannelType = [{value: 1, text: '常规频道'}, {value: 2, text: '车企频道'}];
        var defaultChannelType = 1;

        $('#channelType').combobox({
            data: dataChannelType,
            valueField: 'value',
            textField: 'text',
            value: defaultChannelType,
            onSelect: function (record) {
                $('#channelId').combobox('clear');
                $('#channelId').combobox('loadData', []);
                if (record.value == 1) {
                    $('#channelId').combobox({
                        url: ctx + "/admin/category/query.html",
                        valueField: 'cid',
                        textField: 'name'
                    });
                } else if (record.value == 2) {
                    $('#channelId').combobox({
                        url: ctx + "/admin/car_enterprise/query.html",
                        valueField: 'ceid',
                        textField: 'name'
                    });
                }
            }
        });

        var defaultChannelId = 1;
        $.ajax({
            url: ctx + "/admin/channel_view/query.html?channelType=" + defaultChannelType,
            async: false,
            dataType: "json",
            success: function (data, textStatus) {
                $('#channelId').combobox({
                    data: data,
                    valueField: 'channelId',
                    textField: 'channelName',
                    value: defaultChannelId
                });
            }
        });
    });

    function submitForm() {
        $('#addNewsForm').form('submit', {
            onSubmit: function () {
                var flag = $(this).form('validate');
                if (flag) {
                    App.showProgress(true, '操作提示', '提交中..');
                }
                return flag;
            },
            success: function (data) {
                App.showProgress(false);
                var dataObj = jQuery.parseJSON(data);
                if (dataObj.result == 'ok') {
                    $.messager.alert('操作提示', '发布成功!');
                    $('#addNewsForm').form('reset');
                    CKEDITOR.instances.addNewsContent.setData("");
                    $("#uploadingAddNewsCarouselImg").attr("src", '');
                    $("#uploadingAddNewsListImg").attr("src", '');
                } else {
                    $.messager.alert('操作提示', data.msg);
                }
            },
            onLoadError: function () {
                App.showProgress(false);
                $.messager.alert('操作提示', '由于网络或服务器太忙，提交失败，请重试！');
            }
        });
    }
    function clearForm() {
        $('#addNewsForm').form('reset');
        CKEDITOR.instances.addNewsContent.setData("");
        $("#uploadingAddNewsCarouselImg").attr("src", '');
        $("#uploadingAddNewsListImg").attr("src", '');
    }
</script>