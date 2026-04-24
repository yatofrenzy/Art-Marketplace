<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Art Gallery</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login_register.css">
</head>
<body>

<div class="main-page">
    <%@ include file="/pages/common/navbar.jsp" %>

    <div class="page-content">
        <div class="page-card">
            <div class="page-header">
                <h1>Artwork Collection</h1>
                <p>Explore beautiful and unique artworks from our marketplace.</p>
            </div>

            <div class="page-body">
                <div class="art-grid">

                    <div class="art-card">
                        <img src="${pageContext.request.contextPath}/resources/images/cherryblossom.jpg" alt="cherryblossom" class="art-real-image">
                        <div class="art-details">
                            <h3>cherryblossom</h3>
                            <p>A calming cherryblossom artwork with warm colors.</p>
                            <div class="art-price">Rs. 5000</div>
                            <a href="#" class="action-btn primary-btn">View Details</a>
                        </div>
                    </div>

                    <div class="art-card">
                        <img src="${pageContext.request.contextPath}/resources/images/picaso 1.jpg" alt="picaso lady" class="art-real-image">
                        <div class="art-details">
                            <h3>picaso lady</h3>
                            <p>An abstract creation of picaso copy.</p>
                            <div class="art-price">Rs. 4200</div>
                            <a href="#" class="action-btn primary-btn">View Details</a>
                        </div>
                    </div>

                    <div class="art-card">
                        <img src="${pageContext.request.contextPath}/resources/images/picaso 2.jpg" alt="picaso devil" class="art-real-image">
                        <div class="art-details">
                            <h3>picaso devil</h3>
                            <p>A devil painting copy of picaso painting inspired by minator.</p>
                            <div class="art-price">Rs. 6200</div>
                            <a href="#" class="action-btn primary-btn">View Details</a>
                        </div>
                    </div>

                    <div class="art-card">
                        <img src="${pageContext.request.contextPath}/resources/images/wukong.jpg" alt="wukong" class="art-real-image">
                        <div class="art-details">
                            <h3>wukong</h3>
                            <p>An ancient painting of wukong facing against buddha.</p>
                            <div class="art-price">Rs. 7100</div>
                            <a href="#" class="action-btn primary-btn">View Details</a>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>