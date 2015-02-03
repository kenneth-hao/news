<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/1/4
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/taglib/ctx.tld" prefix="pj" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div style="padding:10px">
    <div style="padding:10px 60px 20px 60px">
        <form id="updateNewsForm" method="post" action="" enctype="multipart/form-data">
            <input name="nid" value="${updatingNews.nid}" type="hidden"/>
            <input name="valid" value="${updatingNews.valid}" type="hidden"/>
            <table cellpadding="5">
                <tr>
                    <td>新闻标题:</td>
                    <td><input name="title" class="easyui-textbox" type="text"
                               data-options="required:true,validType:['length[1,30]']" value="${updatingNews.title }"/>
                    </td>
                    <td align="right">发布人:</td>
                    <td><input name="author" class="easyui-textbox" type="text" data-options="required:true"
                               value="${updatingNews.author }"/></td>
                </tr>
                <tr>
                    <td>频道分类:</td>
                    <td>
                        <input id="updateNewsChannelType" name="channelType" data-options="required:true"
                               panelHeight="auto" style="width:100px"/>
                    </td>
                    <td align="right">稿件属性:</td>
                    <td>
                        <input id="updateNewsFileFormat" name="fileFormat" data-options="required:true"
                               panelHeight="auto" style="width:100px"/>
                    </td>
                </tr>
                <tr>
                    <td>所属频道:</td>
                    <td>
                        <input id="updateNewsChannelId" name="channelId" data-options="required:true" panelHeight="auto"
                               style="width:100px"/>
                    </td>
                    <td align="right">发布时间:</td>
                    <td>
                        <input id="updateNewsPublishTime" name="publishTime"
                               value="<fmt:formatDate value="${updatingNews.publishTime }" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               class="easyui-datetimebox" data-options="required:true" style="width:200px"/>
                    </td>
                </tr>
                <tr>
                    <td valign="top">上传轮播图:</td>
                    <td colspan="3">
                        <img id="uploadingUpdateNewsCarouselImg" title="已上传轮播图"
                             src="${ctxImgPath }/${updatingNews.carouselImgPath }" width="540" height="333"/><br/>
                        <input id="updateNewsCarouselImgFilebox" name="carouselImg" class="f1 easyui-filebox" value=""/>
                    </td>
                </tr>
                <tr>
                    <td valign="top">上传列表图:</td>
                    <td colspan="3">
                        <img id="uploadingUpdateNewsListImg" title="已上传列表图"
                             src="${ctxImgPath }/${updatingNews.listImgPath }" width="300" height="225"/><br/>
                        <input id="updateNewsListImgFilebox" name="listImg" class="f1 easyui-filebox" value=""/>
                    </td>
                </tr>
                <tr>
                    <td valign="top">新闻内容:</td>
                    <td colspan="3">
                        <textarea id="updateNewsContent" name="content">${updatingNews.content }</textarea>
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
<script>
    jQuery(function () {

        // Create editor instance
        var updateNewsContent = CKEDITOR.replace('updateNewsContent', {height: '380px', width: '880px'});

        $('#updateNewsCarouselImgFilebox').filebox({
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
                    $("#uploadingUpdateNewsCarouselImg").attr("src", objUrl);
                }
            }
        });

        $('#updateNewsListImgFilebox').filebox({
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
                    $("#uploadingUpdateNewsListImg").attr("src", objUrl);
                }
            }
        });

        var defaultFileFormat = ${updatingNews.fileFormat };
        var dataFileFormat = [{value: 1, text: '原创'}, {value: 2, text: '转载'}];
        $('#updateNewsFileFormat').combobox({
            data: dataFileFormat,
            valueField: 'value',
            textField: 'text',
            value: defaultFileFormat
        });

        var dataChannelType = [{value: 1, text: '常规频道'}, {value: 2, text: '车企频道'}];
        var defaultChannelType = ${updatingNews.channelType };

        $('#updateNewsChannelType').combobox({
            data: dataChannelType,
            valueField: 'value',
            textField: 'text',
            value: defaultChannelType,
            onSelect: function (newValue, oldValue) {
                $('#updateNewsChannelId').combobox('clear');
                $('#updateNewsChannelId').combobox('loadData', []);
                if (newValue.value != "") {
                    $('#updateNewsChannelId').combobox('reload', ctx + "/admin/channel_view/query.html?channelType=" + newValue.value);
                }
            }
        });

        var defaultChannelId = ${updatingNews.channelId };
        $.ajax({
            url: ctx + "/admin/channel_view/query.html?channelType=" + defaultChannelType,
            async: false,
            dataType: 'json',
            success: function (data, textStatus) {
                $('#updateNewsChannelId').combobox({
                    data: data,
                    valueField: 'channelId',
                    textField: 'channelName',
                    value: defaultChannelId
                });
            }
        });

    });


    function submitForm() {
        var isValid = $('#updateNewsForm').form('validate');
        if (isValid) {
            App.showProgress(true, '操作提示', '提交中..');
            CKEDITOR.instances.updateNewsContent.updateElement();
            var formData = new FormData($('#updateNewsForm')[0]);
            $.ajax({
                url: ctx + "/admin/news/update.html",
                type: 'post',
                async: false,
                data: formData,
                dataType: 'json',
                cache: false,
                contentType: false,
                processData: false,
                success: function (dataObj, textStatus) {
                    App.showProgress(false);
                    if (dataObj.result == 'ok') {
                        $.messager.alert('操作提示', '更新成功!');
                        $('#updateNewsForm').form('reset');
                        CKEDITOR.instances.updateNewsContent.setData("");
                        $('#uploadingUpdateNewsCarouselImg').attr("src", "");
                        $('#uploadingUpdateNewsListImg').attr("src", "");
                        var row = $('#newsList').datagrid('getSelected');
                        var rowIndex = $('#newsList').datagrid('getRowIndex', row);
                        $('#newsList').datagrid('updateRow', {index: rowIndex, row: dataObj.updatedNews});
                        $('#newsList').datagrid('refreshRow', rowIndex);
                        $('.operate_btn_edit').linkbutton();
                        $('.operate_btn_del').linkbutton();
                        $('#editNewsWindow').window('close');
                    } else {
                        $.messager.alert('操作提示', data.msg);
                    }
                },
                error: function () {
                    App.showProgress(false);
                    $.messager.alert('操作提示', '由于网络或服务器太忙，提交失败，请重试！');
                }
            });
        }

    }
    function clearForm() {
        $('#updateNewsForm').form('reset');
    }
</script>