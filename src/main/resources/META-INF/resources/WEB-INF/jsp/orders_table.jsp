<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="blocking" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="editing" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="deleting" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="show" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="formatDate" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="formatTime" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="edit" uri="http://www.springframework.org/tags/form" %>

<div class="container">
    <h3 class = "navbar-header"><spring:message code="label.orders"/></h3>
    <div class = "login-form">
    <table class = "table table-condensed orders-table">
        <tr>
            <th><spring:message code="label.user"/></th>
            <th><spring:message code="label.status"/></th>
            <th><spring:message code="label.edit"/></th>
        </tr>
    </table>
    </div>
    <div class = "login-form">
        <c:forEach var = "order" items = "${orders}">
            <editing:form action = "/orders/${order.id}/edit" method="get">
                <table class = "table table-condensed orders-table">
                    <tr>
                        <%--<edit:form action = "/orders/${order.id}/edit" method="get"--%>
                            <td>${order.user.username}</td>
                            <td>${order.status}</td>
                            <td>
                                <button class = "btn btn-default" type = "submit"><spring:message code="label.edit"/></button>

                            </td>
                        <%--</edit:form>--%>
                    </tr>
                </table>
            </editing:form>
        </c:forEach>
    </div>
</div>