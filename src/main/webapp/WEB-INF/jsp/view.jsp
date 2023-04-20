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

        .nav_content {
            height: 80%;
        }

        #logout {
            position: relative;
            margin-top: 100%;
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

        .title {
            text-decoration: none;
            color: #000;
            display: block;
        }

        .content {
            margin: 0 400px;
            max-width: 900px;
            padding: 20px;
            font-size: 22px;
        }

        .return {
            text-decoration: none;
            float: right;
            font-size: 22px;
        }

        .image-container {
            max-width: 100%;
        }

        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.5);
        }

        .modal-content {
            position: relative;
            margin: 10% auto;
            padding: 20px;
            width: 80%;
            max-width: 800px;
            background-color: #fff;
            border: 1px solid #888;
        }

        .modal-image {
            display: block;
            max-width: 100%;
            max-height: 100%;
            margin: auto;
        }

        .close {
            position: absolute;
            top: 0;
            right: 5px;
            color: #aaa;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
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
            <a href="<c:url value='/blog/profile' />">My Profile</a>

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
    <h2>Photo Blog #${blogId}: <c:out value="${blog.subject}"/></h2>

    <security:authorize access="hasRole('ADMIN')">
        [<a href="<c:url value='/blog/edit/${blog.id}'/>">Edit</a>]
        [<a href="<c:url value='/blog/delete/${blog.id}'/>">Delete</a>]
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
            <a href="javascript:void(0);" onclick="openModal('${attachment.id}')"
               class="image-link">
                <c:out value="${attachment.name}"/><br>
                <div class="image-container">
                    <img src="<c:url value='/blog/${blogId}/image/${attachment.id}'/>"
                         alt="${attachment.name}" style="max-width: 100%;"/>
                </div>
            </a>
            <div id="modal-${attachment.id}" class="modal">
                <div class="modal-content">
                    <span class="close" onclick="closeModal('${attachment.id}')">&times;</span>
                    <img src="<c:url value='/blog/${blogId}/image/${attachment.id}'/>"
                         class="modal-image" alt="${attachment.name}"/>
                </div>
            </div>
        </c:forEach><br/><br/>

    </c:if>

    <security:authorize access="hasAnyRole('USER', 'ADMIN')">
        <form action="<c:url value='/blog/comment/${blogId}'/>" method="post">

            <input type="text" name="content" placeholder="add your comment"/>
            <input type="hidden" name="username" value="${blog.customerName}"/>
            <input type="hidden" name="blogId" value="${blogId}"/>
            <button type="submit">submit</button>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </security:authorize>

    <c:if test="${not empty blog.comments}">
        <h4>comment:</h4>
        <ul>
            <c:forEach items="${blog.comments}" var="comment">
                <li>${comment.user.username}: ${comment.content}</li>
                <span class="time-difference" data-time="${comment.createdAt}"></span>
                <security:authorize access="hasRole('ADMIN')">
                    <form action="<c:url value='/blog/comment/delete/${comment.id}'/>" method="post" style="display:inline;">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit" style="border:none; background:none; color:#f00; cursor:pointer;">Delete</button>
                    </form>
                </security:authorize>
            </c:forEach>
        </ul>
    </c:if>

</div>
<a href="<c:url value="/blog"/>" class="return">Return to Home page</a>
</body>

<script>
    function openModal(id) {
        var modal = document.getElementById('modal-' + id);
        modal.style.display = "block";
    }

    function closeModal(id) {
        var modal = document.getElementById('modal-' + id);
        modal.style.display = "none";
    }

    document.getElementById('logout').addEventListener('click', function (event) {
        event.preventDefault();

        var xhr = new XMLHttpRequest();
        var csrfToken = document.getElementById('csrfToken').value;

        xhr.open('POST', '${logoutUrl}', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.setRequestHeader('${_csrf.headerName}', csrfToken);

        xhr.onload = function () {
            if (xhr.status === 200 || xhr.status === 204) {
                // Redirect to the desired page after successful
                logout
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

    function timeDifference(current, previous) {
        var msPerMinute = 60 * 1000;
        var msPerHour = msPerMinute * 60;
        var msPerDay = msPerHour * 24;
        var msPerMonth = msPerDay * 30;
        var msPerYear = msPerDay * 365;

        var elapsed = current - previous;

        if (elapsed < msPerMinute) {
            return Math.round(elapsed / 1000) + ' seconds ago';
        } else if (elapsed < msPerHour) {
            return Math.round(elapsed / msPerMinute) + ' minutes ago';
        } else if (elapsed < msPerDay) {
            return Math.round(elapsed / msPerHour) + ' hours ago';
        } else if (elapsed < msPerMonth) {
            return Math.round(elapsed / msPerDay) + ' days ago';
        } else if (elapsed < msPerYear) {
            return Math.round(elapsed / msPerMonth) + ' months ago';
        } else {
            return Math.round(elapsed / msPerYear) + ' years ago';
        }
    }

    var timeDifferenceElements = document.getElementsByClassName('time-difference');
    var currentTime = new Date().getTime();

    for (var i = 0; i < timeDifferenceElements.length; i++) {
        var commentTime = new Date(timeDifferenceElements[i].getAttribute('data-time')).getTime();
        timeDifferenceElements[i].innerText = timeDifference(currentTime, commentTime);
    }

</script>

</html>

