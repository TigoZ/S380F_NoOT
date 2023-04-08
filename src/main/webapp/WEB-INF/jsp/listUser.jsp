<!DOCTYPE html>
<html>
<head>
    <title>Photo Blog User Management</title>
    <style>
		* {
			padding: 10px;
			margin: 0;
		}

		.nav {
			height: 100%;
			width: 300px;
			position: fixed;
			top: 0;
			left: 0;
			overflow-x: hidden;
			padding-top: 20px;
			border-right: 1px solid rgb(176, 170, 170);
			float: left;
		}

		.nav a {
			display: block;
			color: #000;
			padding: 16px;
			text-decoration: none;
		}

		.nav a:hover {
			background-color: #979494c1;
			color: white;
		}


		.content {
			margin: auto;
			max-width: 800px;
			padding: 20px;
		}

    </style>
</head>
<body>

<div class="nav">
    <h2>Photo Blog</h2>
    <security:authorize access="hasRole('ADMIN')">
        <a href="<c:url value='/user' />">Manage User Accounts</a>
    </security:authorize>
    <security:authorize access="hasAnyRole('USER', 'ADMIN')">
        <a href="<c:url value='/blog/create' />">Create a Blog</a>
        <c:url var="logoutUrl" value="/logout"/>
        <a href="/logout" id="logout">Log out</a>
        <input type="hidden" id="csrfToken" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </security:authorize>
    <security:authorize access="isAnonymous()">
        <a href="<c:url value='/login' />">Log in</a>
    </security:authorize>
</div>

<div class="content">
    <a href="<c:url value="/blog" />">Return to Home page</a>

    <h2>Users</h2>

    <c:choose>
        <c:when test="${fn:length(blogUsers) == 0}">
            <i>There are no users in the system.</i>
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Roles</th>
                    <th>Email</th>
                    <th>phone_number</th>
                    <th>Action</th>
                </tr>
                <c:forEach items="${blogUsers}" var="user">
                    <tr>
                        <td>${user.username}</td>
                        <td>${fn:substringAfter(user.password, '{noop}')}</td>
                        <td>
                            <c:forEach items="${user.roles}" var="role" varStatus="status">
                                <c:if test="${!status.first}">, </c:if>
                                ${role.role}
                            </c:forEach>
                        </td>
                        <td>${user.email}</td>
                        <td>${user.phoneNumber}</td>
                        <td>
                            [<a href="<c:url value="/user/delete/${user.username}" />">Delete</a>]
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</div>
</body>

<script>
    document.getElementById('logout').addEventListener('click', function (event) {
        event.preventDefault();

        var xhr = new XMLHttpRequest();
        var csrfToken = document.getElementById('csrfToken').value;

        xhr.open('POST', '${logoutUrl}', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.setRequestHeader('${_csrf.headerName}', csrfToken);

        xhr.onload = function () {
            if (xhr.status === 200 || xhr.status === 204) {
                // Redirect to the desired page after successful logout
                window.location.href = '/No_OT';
            } else {
                // Handle error if needed
                console.error('Logout failed');
            }
        };

        xhr.onerror = function () {
            // Handle network errors
            console.error('Network error');
        };

        xhr.send('${_csrf.parameterName}=' + encodeURIComponent(csrfToken));
    });
</script>
</html>
