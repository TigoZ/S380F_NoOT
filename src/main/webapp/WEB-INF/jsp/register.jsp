<!DOCTYPE html>
<html>
<head>
    <title>PhotoBlog Registration</title>
    <!-- Add your CSS styling here -->
    <style>
		/* Add your CSS styling here */
    </style>
</head>
<body>
<div class="register-box">

    <c:if test="${param.error != null}">
        <p id='error'>Registration failed.</p>
    </c:if>

    <h2>PhotoBlog Registration</h2>

    <div class="input-box">
        <form action="/user/register" method="POST">
            <label for="username" class="text">Username:</label>
            <input type="text" id="username" name="username" required/><br/><br/>
            <label for="password" class="text">Password:</label>
            <input type="password" id="password" name="password" required/><br/><br/>
            <label for="confirmPassword" class="text">Confirm Password:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required/><br/><br/>
            <label for="email" class="text">Email Address:</label>
            <input type="email" id="email" name="email" required/><br/><br/>
            <label for="phone" class="text">Phone Number:</label>
            <input type="tel" id="phone" name="phone" required/><br/><br/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="submit" id="submit" class="text" value="Register"/>
            <a href="/login"><input type="button" id="back" class="text" value="Back to Login"/></a>
        </form>
    </div>
</div>
</body>
</html>
