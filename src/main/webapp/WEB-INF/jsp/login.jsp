<!DOCTYPE html>
<html>
<head>
    <title>PhotoBlog Login</title>
    <style>
		* {
			padding: 0;
			margin: 0;
			box-sizing: border-box;
		}

		body {
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

		.login-box {
			display: flex;
			justify-content: center;
			align-content: stretch;
			flex-wrap: wrap;
		}

		.input-box {
			margin: 0px auto;
			padding: 1.25rem;
			border-radius: 0.25rem;
			border: 1px solid rgb(216, 221, 230);
			color: rgb(22, 50, 92);
			width: 100%;
			display: flex;
			justify-content: center;
		}

		#error {
			color: rgba(255, 0, 0, 0.52);
		}

		.login-box h2 {
			width: 100%;
			display: flex;
			justify-content: center;
			color: #101e2d;
			font-size: 50px;
			padding-bottom: 40px;
		}

		.text {
			font-size: 20px;
		}

		#submit {
			padding: 12px 24px;
			border-radius: 4px;
			border: 0px;
			font-size: 18px;
			font-family: SFS, Arial, sans-serif;
			color: white;
			background-color: rgb(45, 114, 237);
			cursor: pointer;
			width: 100%;
		}

		.main {
			overflow: auto;
			padding-bottom: 48px;
			width: 400px;
		}

		.text_input {
			border: 1px solid rgb(150, 148, 146);
			border-radius: 4px;
			background-color: rgb(255, 255, 255);
			font-family: SFS, Arial, sans-serif;
			box-sizing: border-box;
			appearance: none;
			font-size: 1.81rem;
			transition: all 0.1s ease 0s;
            width: 100%;
		}

		.text_username, .text_password {
			font-size: 20px;
			color: rgb(84, 105, 141);
			margin: 0px 0px 8px;
			line-height: inherit;
		}

		#line {
			width: 100%;
			background-color: rgba(128, 128, 128, 0.23);
			height: 1px;
		}

        a{
			text-decoration: none;
			transition: all 0.1s ease 0s;
        }

        .forgot_pw{
            float: left;
        }
        #registration{
            float: right;
        }

    </style>
</head>
<body>

<div class="main">
    <div class="login-box">

        <c:if test="${param.error != null}">
            <p id='error'>Login failed.</p>
        </c:if>

        <h2>PhotoBlog Login</h2>

        <div class="input-box">
            <form action="login" method="POST">
                <label for="username" class="text_username">Username:</label><br/>
                <input type="text" id="username" class="text_input" name="username"/><br/><br/>
                <label for="password" class="text_password">Password:</label><br/>
                <input type="password" id="password" class="text_input" name="password"/><br/><br/>

                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="submit" id="submit" class="text" value="Log In"/><br/><br/>

                <input type="checkbox" id="remember-me" name="remember-me"/>
                <label for="remember-me">Remember me</label><br/><br/>

                <div id="line"></div>
                <br/>

                <div class="bottom">
                    <a id="forgot_password_link" class="forgot_pw"
                       href="/forgot">Forgot password？</a>

                    <a href="/registration" id="registration" >Sign in</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>