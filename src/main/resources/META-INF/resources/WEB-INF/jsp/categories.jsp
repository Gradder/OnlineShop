<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="col-sm-3">
    <div class="left-sidebar">
        <h2><spring:message code="label.categories"/></h2>
        <div class="panel-group category-products" id="accordian"><!--category-productsr-->
            <form action="${pageContext.request.contextPath}/welcome">

                <div class="panel panel-default">
                <div class="panel-heading">
                   <h4 class="panel-title">
                        <button type=submit class="category-button" name="category"><spring:message code="label.category.ALL"/></button>
                        </h4>
                    </div>
                </div>

            <c:forEach var="category" items="${categories}">

                            <script>
                                document.write('<div class="panel panel-default">');
                                    document.write('<div class="panel-heading">');
                                    document.write('<h4 class="panel-title">');
                                    document.write('<button type=submit class="category-button" name="category" value="${category}"><spring:message code="label.category.${category}"/></button>');
                                    document.write('</h4>');
                                    document.write('</div>');
                                    document.write('</div>');

                            </script>

            </c:forEach>
            </form>
        </div><!--/category-products-->
    </div>
</div>
