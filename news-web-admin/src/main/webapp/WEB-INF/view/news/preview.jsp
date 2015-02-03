<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="pj" uri="/WEB-INF/taglib/ctx.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="zh-CN" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="utf-8"/>
    <title>${previewNews.title }    </title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- <meta content="width=device-width, initial-scale=1" name="viewport"/> -->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <!-- BEGIN GLOBAL MANDATORY STYLES  -->
    <script type="text/javascript" src="<pj:ctx />/js/jquery.min.js"></script>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

    <style type="text/css">

        h1, h2, h3, h4, h5, h6 {
            font-weight: 900;
        }

        p {
            color: #444;
            font-size: 20px;
            font-family: "SimSun";
            letter-spacing: 1px;
        }

        small {
            color: #666;
            font-size: 15px;
            font-family: "SimSun";
        }

        .page-title {
            color: #000;
            display: block;
            font-family: "SimSun";
            font-size: 24px;
            font-weight: 800;
            letter-spacing: 0px;
            margin: 20px 0 15px;
            padding: 0;
        }

        .page-footer {
            margin-top: 12px;
            font-size: 13px;
            padding: 3px 8px;
            border-radius: 4px;
        }

        .original {
            background-color: rgb(222, 132, 131);
        }

        .reprint {
            background-color: rgb(160, 160, 160);
        }
    </style>
</head>

<body class="page-container" style="overflow:scroll;overflow-x:hidden">
<div class="container">
    <div class="row">
        <div class="col-xs-12">
            <h6 class="page-title">
                ${previewNews.title }
            </h6>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-6">
            <small>${previewNews.author }</small>
        </div>
        <div class="col-xs-6" style="text-align:right">
            <small>
                <fmt:formatDate value="${previewNews.publishTime }" pattern="yyyy-MM-dd"/>
            </small>
        </div>
    </div>
    <div class="row">
        <p style="border-bottom: 1px solid #AAA; padding: 3px 1px 12px 1px; margin-bottom: 25px"></p>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <c:choose>
                <c:when test="${not empty previewNews.carouselImgPath}">
                    <img class="img-responsive" src="${ctxImgPath }${previewNews.carouselImgPath }"/>
                </c:when>
                <c:otherwise>
                    <img class="img-responsive" src="${ctxImgPath }${previewNews.listImgPath }"/>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <p style="padding-top: 10px;">
                ${previewNews.content }
            </p>
        </div>
    </div>

    <div class="row">
        <div class="col-xs-12">
            <c:choose>
                <c:when test="${previewNews.channelType == 1 && previewNews.channelId == 2 }">
                    <p class="page-footer original">
                        版权声明：本文来源于网络实名认证用户，内容为作者个人观点，文责自负，不代表新华社发布汽车的立场和观点。如对本文内容有疑议，请致电：010-88051732。
                    </p>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${previewNews.fileFormat == 1}">
                            <p class="page-footer original">
                                版权声明：新华社发布汽车所刊载原创内容之知识产权为新华社新媒体中心及/或相关权利人专属所有或持有。未经许可，禁止转载、摘编、复制等任何使用。如对本文内容有疑议或需转载，请致电：010-88051732。
                            </p>
                        </c:when>
                        <c:otherwise>
                            <p class="page-footer reprint">
                                版权声明：本文来源于网络转载，内容为作者个人观点，不代表新华社发布汽车的立场和观点。如对本文内容有疑议，请致电：010-88051732。
                            </p>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>
            </p>
        </div>
    </div>
</div>
</body>
</html>