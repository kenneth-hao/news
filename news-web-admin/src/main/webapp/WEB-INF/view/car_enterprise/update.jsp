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
    <div style="padding:10px 60px 20px 60px">
        <form id="updateCarEnterpriseForm" method="post" action="<pj:ctx />/admin/car_enterprise/update.html"
              enctype="multipart/form-data">
            <input name="ceid" value="${updatingCarEnterprise.ceid}" type="hidden"/>
            <table cellpadding="5">
                <tr>
                    <td>车企名称:</td>
                    <td><input name="name" value="${updatingCarEnterprise.name }" class="easyui-textbox" type="text"
                               data-options="required:true,validType:['length[1,6]']"/></td>
                </tr>
                <tr>
                    <td valign="top">上传车企图片:</td>
                    <td>
                        <img id="uploadedImg" src="${ctxImgPath }/${updatingCarEnterprise.brandImgPath }" title="已上传列表图"
                             src="unknown.jpg" width="120" height="120"/><br/>
                        <input id="updateCarEnterpriseUploadBrandImg" name="uploadBrandImg" class="f1 easyui-filebox"
                               value=""/>
                    </td>
                </tr>
                <tr>
                    <td>APP 下载地址:</td>
                    <td><input name="appurl" value="${updatingCarEnterprise.appurl }" class="easyui-textbox" type="text"
                               data-options=""/></td>
                </tr>
                <tr>
                    <td>排序标志位:</td>
                    <td><input name="seqno" class="easyui-numberspinner"
                               data-options="required:true, min:1, max:999, value: ${updatingCarEnterprise.seqno }"/>
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
        $('#updateCarEnterpriseUploadBrandImg').filebox({
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
                    $("#uploadedImg").attr("src", objUrl);
                }
            }
        });
    });

    function submitForm() {
        $('#updateCarEnterpriseForm').form('submit', {
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
                    $.messager.alert('操作提示', '编辑成功!');
                    $('#updateCarEnterpriseForm').form('reset');
                    $("#uploadedImg").attr("src", '');
                    var row = $('#carEnterpriseList').datagrid('getSelected');
                    var rowIndex = $('#carEnterpriseList').datagrid('getRowIndex', row);
                    $('#carEnterpriseList').datagrid('updateRow', {index: rowIndex, row: dataObj.updatedCarEnterprise});
                    $('#carEnterpriseList').datagrid('refreshRow', rowIndex);
                    $('.operate_btn_edit').linkbutton();
                    $('.operate_btn_del').linkbutton();
                    $('#editRowWindow').window('close');
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
        $('#updateCarEnterpriseForm').form('reset');
    }
</script>