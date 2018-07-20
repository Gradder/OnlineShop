<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="formatDate" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="formatTime" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="editorder" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="editproducts" uri="http://www.springframework.org/tags/form" %>
<%--@elvariable id="order" type=""--%>

<jsp:include page="init.jsp"></jsp:include>
<jsp:include page="header.jsp"></jsp:include>

<div class = "container">
    <div>
        <h3><spring:message code="label.productsInOrder"/> № ${order.id}:</h3>
        <br>
        <table>
            <tr>
                <th><spring:message code="label.product"/></th>
                <th><spring:message code="label.quantity"/></th>
            </tr>
            <c:forEach items = "${productsInOrder}" var = "productInOrder">
                <tr>
                    <td>${productInOrder.product.name}</td>
                    <td style="text-align: center">${productInOrder.quantity}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <br>
    <h3><spring:message code="label.changeStatus"/>:</h3>
    <br>
    <div class = "login-form">
    <editorder:form action = "/orders/${order.id}/save" method = "post" modelAttribute = "order">
        <editorder:select path = "status" cssStyle = "width: 150px;">
            <editorder:option value = "NEW"></editorder:option>
            <editorder:option value = "PREPAID"></editorder:option>
            <editorder:option value = "DELIVERED"></editorder:option>
        </editorder:select>
        <button class = "btn btn-default"><spring:message code="label.save"/></button>
    </editorder:form>
    </div>
</div>