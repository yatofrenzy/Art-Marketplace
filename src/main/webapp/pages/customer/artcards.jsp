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
                        <div class="art-image">Art 1</div>
                        <div class="art-details">
                            <h3>Sunset Beauty</h3>
                            <p>A calming landscape artwork with warm evening colors.</p>
                            <div class="art-price">Rs. 5000</div>
                            <a href="#" class="action-btn primary-btn">View Details</a>
                        </div>
                    </div>

                    <div class="art-card">
                        <div class="art-image">Art 2</div>
                        <div class="art-details">
                            <h3>Dream Waves</h3>
                            <p>An abstract creation with soft motion and modern feel.</p>
                            <div class="art-price">Rs. 4200</div>
                            <a href="#" class="action-btn primary-btn">View Details</a>
                        </div>
                    </div>

                    <div class="art-card">
                        <div class="art-image">Art 3</div>
                        <div class="art-details">
                            <h3>Mountain View</h3>
                            <p>A detailed painting inspired by natural mountain scenery.</p>
                            <div class="art-price">Rs. 6200</div>
                            <a href="#" class="action-btn primary-btn">View Details</a>
                        </div>
                    </div>

                    <div class="art-card">
                        <div class="art-image">Art 4</div>
                        <div class="art-details">
                            <h3>City Lights</h3>
                            <p>A modern digital art piece showing bright night life.</p>
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