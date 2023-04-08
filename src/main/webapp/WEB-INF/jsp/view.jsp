<!DOCTYPE html>
<html>
<head>
    <title>Photo Blog</title>
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
    <h2>Photo Blog #${blogId}: <c:out value="${blog.subject}"/></h2>
    <security:authorize access="hasRole('ADMIN') or
                          principal=='${blog.customerName}'">
        [<a href="<c:url value="/blog/edit/${blog.id}"/>">Edit</a>]
    </security:authorize>
    <security:authorize access="hasRole('ADMIN')">
        [<a href="<c:url value="/blog/delete/${blog.id}"/>">Delete</a>]
    </security:authorize>
    <br/><br/>
    <i>User Name - <c:out value="${blog.customerName}"/></i><br/><br/>
    Photo Blog created: <fmt:formatDate value="${blog.createTime}"
                                        pattern="EEE, d MMM yyyy HH:mm:ss Z"/><br/>
    Photo Blog updated: <fmt:formatDate value="${blog.updateTime}"
                                        pattern="EEE, d MMM yyyy HH:mm:ss Z"/><br/><br/>
    Description: <c:out value="${blog.body}"/><br/><br/>

    <c:if test="${not empty blog.attachments}">
        Attachments:
        <c:forEach items="${blog.attachments}" var="attachment" varStatus="status">
            <c:if test="${not status.first}">, </c:if>
            <a href="<c:url value='/blog/${blogId}/attachment/${attachment.id}'/>">
                <c:out value="${attachment.name}"/><br>
                <img src="<c:url value='/blog/${blogId}/image/${attachment.id}'/>"
                     alt="${attachment.name}"/><br>
            </a>
            [<a href="<c:url value='/blog/${blogId}/delete/${attachment.id}'/>">Delete</a>]
        </c:forEach><br/><br/>
    </c:if>

    <a href="<c:url value="/blog" />">Return to Home page</a>

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
