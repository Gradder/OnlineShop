<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="blocking" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="container">
    <div class = "login-form">
        <h3 class = "navbar-header"><spring:message code="label.users"/></h3>
        <table class = "table table-condensed admin-table">
            <tr>
                <th><spring:message code="label.users.usernamefield"/></th>
                <th><spring:message code="label.role"/></th>
                <th><spring:message code="label.address"/></th>
                <th><spring:message code="label.firstName"/></th>
                <th><spring:message code="label.lastName"/></th>
                <th><spring:message code="label.phoneNumber"/></th>
                <th><spring:message code="label.blocking"/></th>
            </tr>
            <c:forEach var = "user" items = "${userList}">
                <tr>
                    <td>${user.username}</td>
                    <td>${user.role}</td>
                    <td>${user.address}</td>
                    <td>${user.firstName}</td>
                    <td>${user.secondName}</td>
                    <td>${user.phoneNumber}</td>
                    <td>
                        <blocking:form action = "users/${user.id}/block" method = "post">

                            <c:if test = "${user.role != 'ADMIN'}">
                                <c:choose>
                                    <c:when test="${user.isBlocked == 'false'}">

                                        <button type="submit" class="btn btn-default block-button"><spring:message code="label.block"/></button>
                                    </c:when>
                                    <c:otherwise>

                                        <button type="submit" class="btn btn-default block-button"><spring:message code="label.unblock"/></button>
                                    </c:otherwise>
                                </c:choose>
                                </button>
                            </c:if>

                        </blocking:form>

                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>