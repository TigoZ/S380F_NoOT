<!DOCTYPE html>
<html>
<head>
    <title>PhotoBlog Register</title>
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

		.main {
			overflow: auto;
			padding-bottom: 48px;
			width: 400px;
		}

		.register-box h2 {
			width: 100%;
			display: flex;
			justify-content: center;
			color: #101e2d;
			font-size: 50px;
			padding-bottom: 40px;
			font-family: 'Arvo', serif;
		}

		.text {
			font-size: 20px;
			color: rgb(84, 105, 141);
			margin: 0px 0px 8px;
			line-height: inherit;
		}

		.register-box {
			display: flex;
			justify-content: center;
			align-content: stretch;
			flex-wrap: wrap;
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

		.add {
			width: 100%;
			padding: 12px 24px;
			border-radius: 4px;
			border: 0px;
			font-size: 18px;
			font-family: SFS, Arial, sans-serif;
			color: white;
			background-color: rgb(45, 114, 237);
			cursor: pointer;
		}

        .login{
            float: right;
            font-size: 20px;
        }

		a{
			text-decoration: none;
			transition: all 0.1s ease 0s;
			color: rgb(84, 105, 141);
		}
        a:active{
			color: rgb(84, 105, 141);
        }

    </style>
    <link href="https://fonts.googlefonts.cn/css?family=Arvo" rel="stylesheet">
</head>
<body>
<div class="main">
    <div class="register-box">

        <h2>Register</h2>

        <div class="input-box">
            <form:form method="POST" modelAttribute="user">
                <form:label path="username" class="text">Username</form:label><br/>
                <form:input type="text" class="text_input" path="username"/><br/><br/>
                <form:label path="password" class="text">Password</form:label><br/>
                <form:input type="text" class="text_input" path="password"/><br/><br/>
                <form:label path="email" class="text">Email</form:label><br/>
                <form:input type="text" class="text_input" path="email"/><br/><br/>
                <form:label path="phoneNumber" class="text">PhoneNumber</form:label><br/>
                <form:input type="text" class="text_input" path="phoneNumber"/><br/><br/>
                <br/>
                <input type="submit" class="add" value="Sign In"/><br/><br/>
                <div class="bottom">
                    <a href="login" class="login">Login</a>
                </div>
            </form:form>
        </div>
    </div>
</div>
</body>
</html>
