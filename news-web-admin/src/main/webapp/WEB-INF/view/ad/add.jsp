<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/1/4
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/taglib/ctx.tld" prefix="pj" %>

<div style="padding:10px">
    <div class="easyui-panel" title="添加广告" style="width:600px; height: 900px">
        <div style="padding:10px 60px 20px 60px">
            <form id="addAdForm" method="post" action="<pj:ctx />/admin/ad/add.html" enctype="multipart/form-data">
                <table cellpadding="5">
                    <tr>
                        <td>广告标题:</td>
                        <td><input name="title" class="easyui-textbox" type="text" data-options="required:true"/></td>
                    </tr>
                    <tr>
                        <td valign="top">上传广告图片:</td>
                        <td>
                            <img id="addAdImgUploaded" title="已上传列表图" src="unknown.jpg" width="360" height="640"/><br/>
                            <input id="addAdImgUpload" name="uploadAdImg" class="f1 easyui-filebox" value=""
                                   data-options="required:true"/><br />
                            <small style="padding-left: 5px; color: #ff0000">注意: 推荐上传的图片大小: 1080 * 1920</small>
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
        $('#addAdImgUpload').filebox({
            buttonText: '选择图片',
            onChange: function (newValue, oldValue) {
                if (!newValue) {
                    return false;
                }
                //验证上传文件格式是否正确
                var pos = newValue.lastIndexOf(".");
                var lastname = newValue.substr(pos + 1);
                console.log(lastname);
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
                    $("#addAdImgUploaded").attr("src", objUrl);
                }
            }
        });
    });

    function submitForm() {
        $('#addAdForm').form('submit', {
            success: function (data) {
                var dataObj = jQuery.parseJSON(data);
                if (dataObj.result == 'ok') {
                    $.messager.alert('操作提示', '添加成功!');
                    $('#addAdForm').form('clear');
                    $("#addAdImgUploaded").attr("src", 'unknown.jpg');
                } else {
                    $.messager.alert('操作提示', data.msg);
                }
            }
        });
    }
    function clearForm() {
        $('#addAdForm').form('clear');
        $("#addAdImgUploaded").attr("src", 'unknown.jpg');
    }
</script>