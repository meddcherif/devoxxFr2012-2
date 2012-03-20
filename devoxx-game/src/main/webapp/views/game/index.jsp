<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="welcome">
<spring:message code="game.index.welcome" text="Bienvenue" />
<c:choose>
	<c:when test="${username==null}"></c:when>
	<c:otherwise>${userName}!</c:otherwise>
</c:choose>
</div>

<p><spring:message code="game.index.ranking.label" arguments="${rank}§${nbUsers}" argumentSeparator="§" text="Vous êtes classé {0} sur {1}" />.</p>
<p><spring:message code="game.index.waitingQuestions.label" arguments="${waitingQuestions}" argumentSeparator="§" text="Vous avez {0} question(s) en attente." /></p>

<c:if test="${waitingQuestions > 0}">
    <div class="center-wrapper">
        <spring:url value=""></spring:url>
        <a id="play-btn" href="<c:url value='/play' />" data-role="button" data-inline="true" data-iconpos="top" data-icon="play">
            <spring:message code="game.index.playNow" text="Jouez Maintenant!" />
        </a>
    </div>
</c:if>
