<!DOCTYPE html>
<html>
<head>
    <title>PhotoBlog Login</title>
    <style>
		*{
			padding: 0;
			margin: 0;
			box-sizing: border-box;
		}

		body{
			display: flex;
			justify-content: center;
			align-items: center;
			width: 100%;
			min-height: 100vh;
			background-repeat: no-repeat;
			background-position: center;
			background-size: cover;
			overflow: hidden;
		}

		.login-box{
			display: flex;
			justify-content: center;
			align-content:space-around;
			flex-wrap: wrap;
			width: 600px;
			height: 300px;
		}

        #error{
            color: rgba(255, 0, 0, 0.52);
        }

		.login-box h2{
			width: 100%;
			display: flex;
			justify-content: center;
			color: #101e2d;
			font-size: 30px;
		}

    #submit, #remember-me{
        align-content: center;
        margin-left: 30%;
    }

    </style>
</head>
<body>
<div class="login-box">

    <c:if test="${param.error != null}">
        <p id = 'error'>Login failed.</p>
    </c:if>

    <h2>PhotoBlog Login</h2>

    <div class="input-box">
        <form action="login" method="POST">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username"/><br/><br/>
            <label for="password">Password:</label>
            <input type="password" id="password" name="password"/><br/><br/>
            <input type="checkbox" id="remember-me" name="remember-me"/>
            <label for="remember-me">Remember me</label><br/><br/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" id="submit" value="Log In"/>
        </form>
    </div>
</div>
</body>
</html>
