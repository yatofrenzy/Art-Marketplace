<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>500 - Server Error</title>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', Arial, sans-serif;
        }

        body {
            min-height: 100vh;
            background: linear-gradient(135deg, #6aa887, #a76588);
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .error-card {
            width: 540px;
            background: #ffffff;
            border-radius: 30px;
            padding: 50px;
            text-align: center;
            box-shadow: 0 25px 70px rgba(0,0,0,0.18);
        }

        .error-code {
            font-size: 105px;
            font-weight: 900;
            color: #ff6b6b;
            line-height: 1;
        }

        h1 {
            margin-top: 18px;
            font-size: 32px;
            color: #111827;
        }

        p {
            margin-top: 14px;
            color: #6b7280;
            line-height: 1.7;
            font-size: 15px;
        }

        .btn {
            display: inline-block;
            margin-top: 30px;
            padding: 14px 28px;
            border-radius: 14px;
            background: #111827;
            color: white;
            text-decoration: none;
            font-weight: 800;
        }
    </style>
</head>
<body>

<div class="error-card">
    <div class="error-code">500</div>
    <h1>Internal Server Error</h1>
    <p>
        Something went wrong while processing your request.
        Please try again later or return to the home page.
    </p>

    <a href="${pageContext.request.contextPath}/pages/customer/home.jsp" class="btn">
        Back to Home
    </a>
</div>

</body>
</html>