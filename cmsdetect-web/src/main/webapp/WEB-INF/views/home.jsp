<%@ page session="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>CmsDetect</h1>

<form action="/" method="get">

    <div class="row">
        <div class="col-lg-6">
            <div class="input-group">
                <input type="url" name="url" value="<c:out value="${url}" />" class="form-control" />
            <span class="input-group-btn">
                <button class="btn btn-default" type="submit">Slå opp</button>
            </span>
            </div>
        </div>
    </div>

</form>


<h2>Resultat</h2>

<h3>CMS</h3>

<c:if test="${result != null}">
<div class="row">
    <c:forEach var="score" items="${result.cmsScores}">
        <div class="col-md-4">
            <div style="padding: 10pt; background: #eee; margin-bottom: 5pt;">
                <c:out value="${score.name}" /> (<c:out value="${score.scorePercent}" />%)
            </div>
        </div>
    </c:forEach>
</div>

<h3>Funksjonalitet</h3>

<div class="row">
<c:forEach var="score" items="${result.featureScores}">
    <div class="col-md-4">
        <div style="padding: 10pt; background: #eee; margin-bottom: 5pt;">
            <c:out value="${score.name}" />
        </div>
    </div>
</c:forEach>
</div>
</c:if>