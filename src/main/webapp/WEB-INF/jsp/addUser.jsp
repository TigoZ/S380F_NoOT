<!DOCTYPE html>
<html>
<head>
    <title>Customer Support</title>
    <style>
		* {
			padding: 10px;
			margin: 0;
		}

		.nav {
			height: 100%;
			width: 200px;
			position: fixed;
			top: 0;
			left: 0;
			overflow-x: hidden;
			padding-top: 20px;
			border-right: 1px solid rgb(176, 170, 170);
			float: left; /* 添加float:left属性 */
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
			margin: 0 0 0 220px;
			max-width: 800px;
			padding: 20px;
		}

    </style>
</head>
<body>

<div class="nav">
    <h2>Tickets</h2>
    <security:authorize access="hasRole('ADMIN')">
        <a href="<c:url value='/user' />">Manage User Accounts</a>
    </security:authorize>
    <a href="<c:url value='/ticket/create' />">Create a Ticket</a>
    <c:url var="logoutUrl" value="/logout"/>
    <a href="/logout" id="logout">Log out</a>
    <input type="hidden" id="csrfToken" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</div>

<div class="content">
    <h2>Create a User</h2>
    <form:form method="POST" modelAttribute="ticketUser">
        <form:label path="username">Username</form:label><br/>
        <form:input type="text" path="username"/><br/><br/>
        <form:label path="password">Password</form:label><br/>
        <form:input type="text" path="password"/><br/><br/>
        <form:label path="roles">Roles</form:label><br/>
        <form:checkbox path="roles" value="ROLE_USER"/>ROLE_USER
        <form:checkbox path="roles" value="ROLE_ADMIN"/>ROLE_ADMIN
        <br/><br/>
        <input type="submit" value="Add User"/>
    </form:form>
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
