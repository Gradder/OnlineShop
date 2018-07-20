<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<header>
    <div class="header-middle"><!--header-middle-->
        <div class="container">
            <div class="row">
                <div class="col-sm-4">
                    <div class="logo pull-left">
                        <a href="/welcome"><img src="${pageContext.request.contextPath}/images/content/logo.png"/></a>
                    </div>
                    <div class="btn-group pull-right">
                        <div class="btn-group">
                         <%--   <button type="button" class="btn btn-default dropdown-toggle usa" data-toggle="dropdown">
                                <spring:message code="label.language"/>
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <li><a href="${pageContext.request.contextPath}?lang=en"><spring:message
                                        code="label.english"/></a></li>
                                <li><a href="${pageContext.request.contextPath}?lang=ru"><spring:message
                                        code="label.russian"/></a></li>
                            </ul>--%>
                             <div class="dropdown">
                                 <button class="dropbtn"><spring:message code="label.language"/></button>
                                 <div class="dropdown-content">
                                     <a href="${pageContext.request.contextPath}?lang=en"><spring:message code="label.english"/></a>
                                     <a href="${pageContext.request.contextPath}?lang=ru"><spring:message code="label.russian"/></a>
                                 </div>
                             </div>
                        </div>

                    </div>
                </div>
                <div class="col-sm-8">
                    <div class="shop-menu pull-right">
                        <ul class="nav navbar-nav">
                            <c:if test="${pageContext.request.remoteUser != null}">
                                <li><a href="/cart"><img
                                        src="${pageContext.request.contextPath}/images/content/cart.png"/>
                                    <spring:message code="label.cart"/></a></li>
                                <li><a href="/profile"><img
                                        src="${pageContext.request.contextPath}/images/content/profile.png"/>
                                    <spring:message code="label.profile"/></a></li>
                                <li><a href="/logout"><img
                                        src="${pageContext.request.contextPath}/images/content/logout.png"/>
                                    <spring:message code="label.logout"/></a></li>
                            </c:if>
                            <c:if test="${pageContext.request.remoteUser == null}">
                                <li><a href="login"><img
                                        src="${pageContext.request.contextPath}/images/content/login.png"/>
                                    <spring:message code="label.login"/></a></li>
                            </c:if>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div><!--/header-middle-->

    <div class="header-bottom"><!--header-bottom-->
        <div class="container">
            <div class="row">
                <div class="col-sm-9">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse"
                                data-target=".navbar-collapse">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                    </div>

                </div>
            </div>
        </div>
    </div><!--/header-bottom-->
</header>
<!--/header-->