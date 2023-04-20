<!DOCTYPE html>
<html>
<head>
    <title>Forgot Password</title>
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

		.reset-box h2 {
			width: 100%;
			display: flex;
			justify-content: center;
			color: #101e2d;
			font-size: 50px;
			padding-bottom: 40px;
			font-family: 'Arvo', serif;
		}

		.reset-box {
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

		.main {
			overflow: auto;
			padding-bottom: 48px;
			width: 400px;
		}

		a {
			text-decoration: none;
			transition: all 0.1s ease 0s;
			color: rgb(84, 105, 141);
		}

		a:active {
			color: rgb(84, 105, 141);
		}

		.text {
			font-size: 20px;
			color: rgb(84, 105, 141);
			margin: 0px 0px 8px;
			line-height: inherit;
		}

		.reset {
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

		.login {
			float: right;
			font-size: 20px;
		}
    </style>
</head>
<body>
<div class="main">
    <div class="reset-box">
        <h2>Reset Password</h2>
        <div class="input-box">
            <form id="resetPasswordForm" action="forgot_password" method="POST">
                <input type="hidden" id="csrfToken" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <label for="username" class="text">Username:</label><br/>
                <input type="text" id="username" name="username" required
                       class="text_input"><br><br/>
                <label for="newPassword" class="text">New Password:</label><br/>
                <input type="password" id="newPassword" name="newPassword" required
                       class="text_input"><br><br/>
                <input type="submit" value="Reset" class="reset"><br/><br/>
                <div class="bottom">
                    <a href="login" class="login">Login</a>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    function checkUsernameAndResetPassword(event) {
        event.preventDefault();

        const username = document.getElementById('username').value;
        const newPassword = document.getElementById('newPassword').value;
        const csrfToken = document.getElementById('csrfToken').value;

        fetch('/No_OT/checkUsernameAndResetPassword', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-CSRF-TOKEN': csrfToken
            },
            body: JSON.stringify({ username, newPassword })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('Successfully change the password！');
                    window.location.href = '/No_OT/login';
                } else {
                    alert('Can not find the username, please try again');
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    document.getElementById('resetPasswordForm').addEventListener('submit', checkUsernameAndResetPassword);

</script>
</body>

</html>
