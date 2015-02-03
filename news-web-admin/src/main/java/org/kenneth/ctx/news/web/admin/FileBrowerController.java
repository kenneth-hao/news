package org.kenneth.ctx.news.web.admin;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kenneth.ctx.news.utils.constant.ConfigPropKey;
import org.kenneth.ctx.news.utils.spring.CustomPropertyConfigurer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 浏览服务器的图片
 *
 * @author: quzishen
 * @class_type: FileBrowerController
 * @version: v1.0
 * @create_time：2010-8-24 下午03:54:04
 * @project_name:NormandyPosition
 * @description: <p>
 * <p/>
 * </p>
 */
@Controller
@RequestMapping("/browerServer")
public class FileBrowerController {
    protected final Logger logger = Logger
            .getLogger(FileBrowerController.class);

    @RequestMapping(method = RequestMethod.GET)
    public void processBrower(ModelMap modelMap, HttpServletRequest request,
                              HttpServletResponse response) {
        processBrowerPost(modelMap, request, response);
        return;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void processBrowerPost(ModelMap modelMap,
                                  HttpServletRequest request, HttpServletResponse response) {

        String typeStr = request.getParameter("type");
        String floderName = request.getParameter("fo");

        if (logger.isDebugEnabled()) {
            logger.debug("浏览文件，文件格式:" + typeStr);
        }

        // 定位到目标文件夹 ： 上传目录
        String imgpath = CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH);
        String category = CustomPropertyConfigurer.getProperty(ConfigPropKey.KEY_APP_IMG_PATH_NEWS);
        String realPath = imgpath + category;

        File folder = new File(realPath);
        if (!folder.exists()) {
            return;
        }

        // 存储子目录 ,路径需要从/freemarker开始
        List<String> subFolderSet = new ArrayList<String>();
        // 存储文件夹
        List<String> subFileerSet = new ArrayList<String>();

        File[] subFiles = folder.listFiles();
        if (null != subFiles && 0 < subFiles.length) {
            for (int i = 0; i < subFiles.length; i++) {
                File _file = subFiles[i];
                if (_file.isDirectory()) {
                    subFolderSet.add(_file.getName());
                } else {
                    subFileerSet.add(getFileName(_file.getName()));
                }
            }
        }

        String callback = request.getParameter("CKEditorFuncNum");
        PrintWriter out;

        response.setContentType("text/html");
        response.setCharacterEncoding("GB2312");
        try {
            out = response.getWriter();
            out.println("<script type='text/javascript'><!-- ");
            // 定义点击选择js
            out.println("function choose(obj){");
            out.println("window.opener.CKEDITOR.tools.callFunction(" + callback + ",obj)");
            out.println("window.close();");
            out.println("}");

            // 定义文件夹点击响应js
            out.println("function view(obj){window.location.href='browerServer.do?type=image&CKEditorFuncNum=" + callback + "&fo='+obj;}");
            out.println("// --></script>");

            // 这里显示一个返回顶级目录，也就是返回freemarker目录
            out.print("<div style='width:100%;float:left;word-break:break-all;' onclick =view('" + URLEncoder.encode("/freemarker/upload") + "')>");
            out.print("<span>[根目录]<span/>");
            out.print("</div>");

            // 如果是子文件夹，显示上级目录链接
            if (StringUtils.isNotBlank(floderName)) {
                String parent = folder.getParentFile().getPath();
                out.print("<div style='width:100%;float:left;word-break:break-all;' onclick =view('" + URLEncoder.encode(parent) + "')>");
                out.print("<span>[上级目录]" + parent + "<span/>");
                out.print("</div>");
                if (logger.isDebugEnabled()) {
                    logger.debug("发现上级目录:" + parent);
                }
            }

            // 如果是文件夹，则显示文件夹并且可以再次触发下级和上级目录
            if (0 < subFolderSet.size()) {
                Iterator<String> subFolderSetIndex = subFolderSet.iterator();
                while (subFolderSetIndex.hasNext()) {
                    String ftemp = subFolderSetIndex.next();
                    // 这里url传递的时候，文件分隔符会有问题，需要转义
                    out.print("<div style='width:100%;float:left;word-break:break-all;' onclick =view('" + URLEncoder.encode(ftemp) + "')>");
                    out.print("<span>[下级目录]" + ftemp + "</span>");
                    out.print("</div>");

                    if (logger.isDebugEnabled()) {
                        logger.debug("添加子目录：" + ftemp);
                    }
                }
            }

            // 如果是文件，则点击就选择文件到控件中
            if (0 < subFileerSet.size()) {
                Iterator<String> subFileerSetIndex = subFileerSet.iterator();
                while (subFileerSetIndex.hasNext()) {
                    String ftemp = subFileerSetIndex.next();
                    String f = folder.getPath();
                    String fileUrl = f + File.separator + ftemp;
                    fileUrl = StringUtils.replace(fileUrl, "//", "/");

                    out.print("<div style='width:150px;height:150px;float:left;word-break:break-all;padding:5px;background:#666699;margin:5px;'>");
                    out.print("<a href='javascript:void(0)' mce_href='javascript:void(0)' onclick=choose('" + fileUrl + "')><img style='border:none;width:145px;height:145px;' src='" + fileUrl + "' mce_src='" + fileUrl + "' title='" + fileUrl + "'/></a>");
                    out.print("</div>");

                    if (logger.isDebugEnabled()) {
                        logger.debug("添加文件：" + fileUrl);
                    }
                }
            }

            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取文件名称
     *
     * @param str
     * @return
     */
    private static String getFileName(String str) {
        int index = str.lastIndexOf("//");
        if (-1 != index) {
            return str.substring(index);
        } else {
            return str;
        }
    }
}
