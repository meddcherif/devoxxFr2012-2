<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<div class="center" id="allUsersContainer">
	<table class="jtable" id="allUsersTable">
		<tr>
			<th><spring:message code="admin.user.name" text="Username" /></th>
			<th><spring:message code="admin.user.fullname" text="Name" /></th>
			<th><spring:message code="admin.user.mail" text="Email" /></th>
			<th style="width: 65px;"><spring:message code="admin.label.enabled" text="Enabled" /></th>
			<th class="actions_col"><spring:message code="admin.label.action" text="Action" /></th>
		</tr>
		<c:forEach items="${allUserResponses}" var="userResponse">
			<tr>
				<td>${userResponse.username}</td>
				<td>${userResponse.userForname}</td>
				<td>${userResponse.userEmail}</td>
				<td>
				    <a href='<c:url value="/admin/user/${userResponse.userId}/update/enabled" />' title="Change enabled state">
				    <span class="ui-icon ${userResponse.enabled ? 'ui-icon-check' : 'ui-icon-close'} center"></span>
				    </a>
				</td>
				
				<td>
				<a href='<c:url value="/admin/user/${userResponse.userId}" />' class="ui-btn"
				title='<spring:message code="admin.btn.show" text="Show" />'>
				    <span class="ui-icon ui-icon-person"></span>
				</a>
				<a href='<c:url value="/admin/user/${userResponse.userId}/update" />' class="ui-btn"
                title='<spring:message code="admin.btn.modify" text="Modify" />'>
                    <span class="ui-icon ui-icon-pencil"></span>
                </a>
				<a href='<c:url value="/admin/user/${userResponse.userId}/delete" />' class="ui-btn"
	            title='<spring:message code="admin.btn.delete" text="Remove" />' onclick="return confirm('Are you sure ?');">
	               <span class="ui-icon ui-icon-closethick"></span>
	            </a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<a href='<c:url value="/admin/" />' class="ui-btn"><spring:message code="global.btn.back" text="Back" /></a>
</div>