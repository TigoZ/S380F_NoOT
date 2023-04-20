<html>
<head>
    <title>Edit Profile</title>
</head>
<body>
<h1>Edit Profile</h1>
<form:form modelAttribute="user" action="${pageContext.request.contextPath}/blog/profile/edit/${user.username}" method="post">
    <label for="email">Email:</label>
    <form:input type="email" path="email" id="email" />
    <br/>

    <label for="phoneNumber">Phone Number:</label>
    <form:input type="text" path="phoneNumber" id="phoneNumber" />
    <br/>

    <label for="description">Description:</label>
    <form:textarea path="description" id="description"></form:textarea>
    <br/>

    <button type="submit">Update</button>
</form:form>
</body>
</html>
