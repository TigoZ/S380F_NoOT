<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:form="http://www.springframework.org/tags/form">
<head>
    <title>PhotoBlog Register</title>
</head>
<body>

<h2>Create a User</h2>
<form:form method="POST" modelAttribute="user">
    <form:label path="username">Username</form:label><br/>
    <form:input type="text" path="username"/><br/><br/>
    <form:label path="password">Password</form:label><br/>
    <form:input type="password" path="password"/><br/><br/>
    <form:label path="email">Email</form:label><br/>
    <form:input type="text" path="email"/><br/><br/>
    <form:label path="phoneNumber">PhoneNumber</form:label><br/>
    <form:input type="text" path="phoneNumber"/><br/><br/>
    <br/><br/>
    <input type="submit" value="Add User"/>
</form:form>
<p>
    Already have an account? <a href="/login">Login</a>
</p>
</body>
</html>
