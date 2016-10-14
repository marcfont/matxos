<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form id="form" action="${url}" method="post">

    <c:forEach items="${fields}" var="f">
        <input type="hidden" name="${f.key}" value="${f.value}"/>
    </c:forEach>

</form>

<script type="text/javascript">
    document.getElementById("form").submit();
</script>