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
    <c:choose>
        <c:when test="${fn:length(blogDatabase) == 0}">
            <i>There are no blogs in the system.</i>
        </c:when>
        <c:otherwise>
            <c:forEach items="${blogDatabase}" var="entry">
                Blog ${entry.id}:
                <a href="<c:url value="/blog/view/${entry.id}" />">
                    <c:out value="${entry.subject}"/></a>
                (customer: <c:out value="${entry.customerName}"/>)
                <security:authorize access="hasRole('ADMIN') or
                                principal=='${entry.customerName}'">
                    [<a href="<c:url value="/blog/edit/${entry.id}"/>">Edit</a>]
                </security:authorize>
                <security:authorize access="hasRole('ADMIN')">
                    [<a href="<c:url value="/blog/delete/${entry.id}"/>">Delete</a>]
                </security:authorize>
                <br/>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>

<script>
    document.getElementById('logout').addEventListener('click', function(event) {
        event.preventDefault();

        var xhr = new XMLHttpRequest();
        var csrfToken = document.getElementById('csrfToken').value;

        xhr.open('POST', '${logoutUrl}', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.setRequestHeader('${_csrf.headerName}', csrfToken);

        xhr.onload = function() {
            if (xhr.status === 200 || xhr.status === 204) {
                // Redirect to the desired page after successful logout
                window.location.href = '/No_OT';
            } else {
                // Handle error if needed
                console.error('Logout failed');
            }
        };

        xhr.onerror = function() {
            // Handle network errors
            console.error('Network error');
        };

        xhr.send('${_csrf.parameterName}=' + encodeURIComponent(csrfToken));
    });
</script>



</script>
</body>
</html>
