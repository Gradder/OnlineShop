<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8" %>
<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="features_items"><!--features_items-->
    <h2 class="title text-center"><spring:message code="label.products"/></h2>

    <c:forEach var="product" items="${productList}">

        <div class="col-sm-4">
            <div class="product-image-wrapper">
                <div class="single-products parent-product">
                    <div class="productinfo text-center min-size image-fix-size">
                        <div class="border-for-image">
                            <img class="border-for-img"
                                 src="${pageContext.request.contextPath}/images/products/${product.imageLink}" alt=""/>
                        </div>
                        <h2>${product.price} ¤</h2>
                        <p>${product.name}</p>
                        <div class="for-cart">
                        <a href="#" class="btn btn-default add-to-cart"><img style="max-width:16px" src="${pageContext.request.contextPath}/images/content/cart.png" /> Add
                            to cart</a>
                         </div>
                    </div>
                    <div class="product-overlay">
                        <div class="overlay-content">
                            <h2>${product.price} ¤</h2>
                            <p>${product.name}</p>

                            <a href="cart/${product.id}/add" class="btn btn-default add-to-cart"><img style="max-width:16px" src="${pageContext.request.contextPath}/images/content/cart.png" /><spring:message code="label.addToCart"/></a>

                        </div>
                    </div>
                </div>
            </div>
        </div>

    </c:forEach>


</div>

<form action="${pageContext.request.contextPath}/welcome">
    <table class="products-pages">
        <tr>
            <script>
                for(var i = 1; i <= ${pages};i++){
                    document.write('<td><input class="btn btn-primary btn-hover btn-pages" type=submit name="page" value="'+i+'"/></td>');
                }
            </script>
        </tr>
    </table>
    <input type=hidden name="category" value="<% if (request.getParameter("category") == null){out.print("");}else{out.print(request.getParameter("category"));}%>"/>
</form>
<!--features_items-->