<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <title>Artworks</title>

    <!-- GOOGLE FONT -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap"
          rel="stylesheet">

    <!-- FONT AWESOME -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <!-- DASHBOARD CSS -->
    <link rel="stylesheet"
          type="text/css"
          href="${pageContext.request.contextPath}/css/dashboard.css?v=15">

</head>

<body>

<div class="dashboard">

    <!-- SIDEBAR -->
    <jsp:include page="/pages/common/sidebar.jsp">
        <jsp:param name="active" value="artworks"/>
    </jsp:include>


    <!-- MAIN CONTENT -->
    <main class="main-content">

        <!-- TOPBAR -->
        <div class="topbar">

            <h1>Artworks</h1>

            <button class="add-artwork-btn"
                    onclick="openArtworkModal()">

                <i class="fa-solid fa-plus"></i>
                Add Artwork

            </button>

        </div>


        <!-- SUCCESS MESSAGE -->
        <% if(request.getParameter("success") != null){ %>

            <div class="success-message">
                Artwork uploaded successfully.
            </div>

        <% } %>


        <!-- ERROR MESSAGE -->
        <% if(request.getParameter("error") != null){ %>

            <div class="error-message">
                Failed to upload artwork.
            </div>

        <% } %>



        <!-- SECTION TITLE -->
        <h3 class="section-title">Top Selling Products</h3>



        <!-- PRODUCT GRID -->
        <div class="product-grid">

            <!-- CARD 1 -->
            <div class="product-card">

                <img src="${pageContext.request.contextPath}/resources/images/Digital_Art/La Robotte.png"
                     alt="La Robotte">

                <h4>La Robotte</h4>

                <p>Rs 7100</p>

            </div>



            <!-- CARD 2 -->
            <div class="product-card">

                <img src="${pageContext.request.contextPath}/resources/images/Nature-Art/Echoes in the Blue Forest.jpg"
                     alt="Echoes in the Blue Forest">

                <h4>Echoes in the Blue Forest</h4>

                <p>Rs 6200</p>

            </div>



            <!-- CARD 3 -->
            <div class="product-card">

                <img src="${pageContext.request.contextPath}/resources/images/Paintings/fox watercolor.png"
                     alt="fox watercolor">

                <h4>fox watercolor</h4>

                <p>Rs 4200</p>

            </div>

        </div>

    </main>

</div>



<!-- ========================= -->
<!-- ADD ARTWORK MODAL -->
<!-- ========================= -->

<div id="artworkModal" class="artwork-modal">

    <div class="artwork-modal-content">

        <!-- MODAL HEADER -->
        <div class="modal-header">

            <h2>Add New Artwork</h2>

            <span class="close-modal"
                  onclick="closeArtworkModal()">

                &times;

            </span>

        </div>



        <!-- FORM -->
        <form action="${pageContext.request.contextPath}/addArtwork"
              method="post"
              enctype="multipart/form-data"
              class="artwork-form">


            <!-- TITLE -->
            <div class="form-group">

                <label>Artwork Title</label>

                <input type="text"
                       name="title"
                       placeholder="Enter artwork title"
                       required>

            </div>



            <!-- DESCRIPTION -->
            <div class="form-group">

                <label>Description</label>

                <textarea name="description"
                          placeholder="Artwork description"
                          required></textarea>

            </div>



            <!-- PRICE + CATEGORY -->
            <div class="form-row">

                <div class="form-group">

                    <label>Price</label>

                    <input type="number"
                           step="0.01"
                           name="price"
                           placeholder="Price"
                           required>

                </div>


                <div class="form-group">

                    <label>Category ID</label>

                    <input type="number"
                           name="categoryId"
                           placeholder="Category"
                           required>

                </div>

            </div>



            <!-- IMAGE -->
            <div class="form-group">

                <label>Artwork Image</label>

                <input type="file"
                       name="image"
                       accept="image/*"
                       required>

            </div>



            <!-- BUTTON -->
            <button type="submit" class="upload-btn">

                Upload Artwork

            </button>

        </form>

    </div>

</div>



<!-- ========================= -->
<!-- JAVASCRIPT -->
<!-- ========================= -->

<script>

function openArtworkModal() {

    document.getElementById("artworkModal")
            .style.display = "flex";
}


function closeArtworkModal() {

    document.getElementById("artworkModal")
            .style.display = "none";
}


/* CLOSE WHEN CLICKING OUTSIDE */

window.onclick = function(event) {

    const modal = document.getElementById("artworkModal");

    if (event.target === modal) {

        modal.style.display = "none";
    }
}

</script>

</body>
</html>