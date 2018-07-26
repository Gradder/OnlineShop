<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="users" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="catalog" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="orders" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="col-sm-3 admin-v-left">
    <br>
    <br>
    <br>
    <div class="left-sidebar">
        <script>console.log(<spring:message code="label.users"/>)</script>

                    <users:form action = "/users" method="get">
                        <button class="orange admin-menu-button"><spring:message code="label.users"/></button>
                    </users:form>

                    <catalog:form action = "/catalog" method="get">
                        <button class="orange admin-menu-button"><spring:message code="label.products"/></button>
                    </catalog:form>

                    <orders:form action = "/orders" method="get">
                        <button class="orange admin-menu-button"><spring:message code="label.orders"/></button>
                    </orders:form>
    </div>
</div>