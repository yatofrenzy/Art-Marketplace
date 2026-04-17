<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Learning Log | Add Entry</title>
        <link rel="icon" href="${pageContext.request.contextPath}/resources/book.png" type="image/png" /> 
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addentry.css" />
    </head>
    <body>
        <div class="page">
            <!-- Header -->
            <header class="header">
                <div class="logo">
                    <a href="topiclist.html">
                        <img src="${pageContext.request.contextPath}/resources/book.png" alt="LL" />
                    </a>
                    <h3>Learning Log</h3>
                </div>
                <div class="usersession">
                    <h3>Username</h3>
                    <a href="#" class="logout">Logout</a>
                </div>
            </header>

            <!-- Navigation -->
            <nav class="navbar">
                <ul>
                    <li><a href="entry?topicid=${param.topicid}">&lt; Back (Entry)</a></li>
                    <li><p>Edit/Add New Entry</p></li>
                    <li></li>
                </ul>
            </nav>

            <!-- Main Content -->
            <main class="content">
                <h2 class="topic-title">${topic.getName()}</h2>

                <div class="form-card">
                    <!--As servlets handles adding data in post request, /entry -->
                    <form action="entry" method="post">
                        <!--Create hidden parameter to capture topicid and action-->
                        <input type="hidden" name="action" value="add" />
                        <input type="hidden" name="topicid" value="${param.topicid}" />
                        <div class="form-row">
                            <label for="title">Title:</label>
                            <input type="text" name="title" placeholder="Title" />
                        </div>

                        <div class="form-row">
                            <label for="description">Description:</label>
                            <textarea
                                name="description"
                                rows="4"
                                placeholder="Description"
                                ></textarea>
                        </div>

                        <div class="form-row">
                            <label for="link">Link:</label>
                            <input type="url" name="link" placeholder="Link" />
                        </div>

                        <div class="form-row">
                            <label for="image">Image:</label>
                            <input type="file" name="image" />
                        </div>

                        <div class="form-actions">
                            <button type="submit">Save</button>
                        </div>
                    </form>
                </div>
            </main>

            <c:choose>
                <c:when test="${error}">
                    <p style="color: red; text-align: center;">
                        ${error}
                    </p>
                </c:when>
                <c:when test="${topic}">
                    <p style="color: green; text-align: center; ">
                        ${topic.getName()} >>
                        Created At: ${topic.getCreatedAt().toLocalDate()} >>
                        Updated At: ${topic.getUpdatedAt().toLocalDate()}
                    </p>
                </c:when>
            </c:choose>

            <!-- Footer -->
            <footer class="footer">
                <h3>© Learning Log</h3>
            </footer>
        </div>
    </body>
</html>
