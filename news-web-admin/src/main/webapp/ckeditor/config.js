/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function (config) {
    config.language = 'zh-cn';

    config.extraPlugins = "autoformat";
    config.autoGrow_maxHeight = 980;
    config.autoGrow_minHeight = 580;
    // config.uiColor = '#AADC6E';

    // 图片上传配置
    config.filebrowserUploadUrl = ctx + '/upload.html?type=File';
    config.filebrowserImageUploadUrl = ctx + '/upload.html?type=Image';
    config.filebrowserFlashUploadUrl = ctx + '/upload.html?type=Flash';

    // 图片浏览配置
    config.filebrowserImageBrowseUrl = ctx + '/browerServer.html?type=image';

    config.autoUpdateElement = true;
    config.format_p = {element: 'p', attributes: {style: 'text-indent: 2em;'}};

    config.indentUnit = 'em';
    config.indentOffset = 2;

};
