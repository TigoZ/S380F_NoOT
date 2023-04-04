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
            min-height: 80vh;
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
            width: 600px;
            height: 300px;
        }

        .input-box {
            width: 100%;
            display: flex;
            justify-content: center;
            padding-top: 7%;
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
        }

        .text {
            font-size: 20px;
        }

        #submit, #guest {
            width: 90px;
            font-size: 30px;
            margin-left: 7%;
        }

        #registration {
            width: 230px;
            font-size: 25px;
            margin-left: 2%;
        }

    </style>
</head>
<body>
<div class="login-box">

    <c:if test="${param.error != null}">
        <p id='error'>Login failed.</p>
    </c:if>

    <h2>PhotoBlog Login</h2>

    <div class="input-box">
        <form action="login" method="POST">
            <label for="username" class="text">Username:</label>
            <input type="text" id="username" name="username"/><br/><br/>
            <label for="password" class="text">Password:</label>
            <input type="password" id="password" name="password"/><br/><br/>

            <input type="checkbox" id="remember-me" name="remember-me"/>
            <label for="remember-me" class="text">Remember me</label><br/><br/>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" id="submit" class="text" value="Log In"/>
            <a href=" "><input type="button" id="guest" class="text"
                               value="Guest User"/></a><br/><br/>
            <a href="/registration"><input type="button" id="registration" class="text"
                                           value="Register Now"/></a>

        </form>
    </div>
</div>
</body>
</html>