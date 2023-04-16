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
			border-right: 1px solid rgba(222, 211, 211, 0.55);
			float: left;
			font-size: 30px;
		}


		.nav_content a {
			display: block;
			color: #000;
			padding: 16px;
			text-decoration: none;
			font-size: 22px;
			border-radius: 4px;
		}

		.nav_content a:hover {
			background-color: #979494c1;
			color: white;
		}

		.nav_content {
			height: 80%;
		}

		#logout {
			position: relative;
			margin-top: 140%;
		}

		.title {
			text-decoration: none;
			color: #000;
			display: block;
		}

		.content {
			margin: 0 400px;
			max-width: 900px;
			padding: 20px;
			font-size: 25px;
		}

		.return {
			text-decoration: none;
			float: right;
			font-size: 22px;
		}

    </style>
    <link href="https://fonts.googlefonts.cn/css?family=Modern+Antiqua" rel="stylesheet">
</head>
<body>

<div class="nav">
    <h2 style="font-family: 'Modern Antiqua', cursive;"><a href="/No_OT/blog" class="title">Photo
        Blog</a></h2>
    <div class="nav_content">
        <security:authorize access="hasRole('ADMIN')">
            <br/>
            <a href="<c:url value='/user' />">Manage User Accounts</a>
        </security:authorize>
        <security:authorize access="hasAnyRole('USER', 'ADMIN')">
            <a href="<c:url value='/blog/create' />">Create a Blog</a>
            <c:url var="logoutUrl" value="/logout"/>
            <a href="/logout" id="logout">Log out</a>
            <input type="hidden" id="csrfToken" name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
        </security:authorize>
        <security:authorize access="isAnonymous()">
            <br/><br/>
            <a href="<c:url value='/login' />">Log in</a>
        </security:authorize>
    </div>
</div>
<div class="content">
    <h2>Edit Blog #${blog.id}</h2>
    <form:form method="POST" enctype="multipart/form-data" modelAttribute="blogForm">
        <form:label path="subject">Subject</form:label><br/>
        <form:input type="text" path="subject"/><br/><br/>
        <form:label path="body">Body</form:label><br/>
        <form:textarea path="body" rows="5" cols="30"/><br/><br/>
        <b>Add more attachments</b><br/>
        <input type="file" name="attachments" multiple="multiple"/><br/><br/>
        <input type="submit" value="Save"/><br/><br/>
    </form:form>

</div>
<a href="<c:url value="/blog"/>" class="return">Return to Home page</a>
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
