<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Learning Log | Entries</title>
        <link rel="icon" href="${pageContext.request.contextPath}/resources/book.png" type="image/png" /> 
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/entrylist.css" />
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
                    <li><a href="topic">&lt; Back (Topic)</a></li>
                    <li><p>Entries List</p></li>
                    <li><a href="entry?action=new&topicid=${topic.getId()}">+ Add Entry</a></li>
                </ul>
            </nav>

            <!-- Main Content -->
            <main class="content">
                <!-- Search -->
                <div class="search-bar">
                    <form action="" method="get">
                        <label for="search">Entry:</label>
                        <input type="text" id="search" placeholder="Search..." />
                        <button type="submit">Search</button>
                    </form>
                </div>

                <h2 class="topic-title">
                    <%-- Parse the LocalDate (which defaults to yyyy-MM-dd) into a temporary variable --%>
                    <fmt:parseDate value="${topic.getCreatedAt()}" pattern="yyyy-MM-dd" var="parsedDate" type="date" />

                    <%-- Format that temporary variable into your desired pattern --%>
                    <fmt:formatDate value="${parsedDate}" pattern="E d, yyyy" />
                    ${topic.getName()}
                    : <c:if test="${not empty entries}">
                        <c:out value="${entries.size()}"/>
                        <!--c:out and value is simply-->
                        <!--${entries.size()} -->
                    </c:if>
                    <!--Ternary operator for above c:if :-->
                    <!-- :${entries.size()>0?entries.size():""} -->                   
                </h2>

                <!-- Entries List -->
                <div class="entry-grid">
                    <c:forEach var="entry" items="${entries}">
                        <!-- Entry Card -->
                        <div class="entry-card">
                            <div class="entry-header">
                                <div>
                                    <h3>${entry.getTitle()}</h3>
                                    <p class="date">Date: ${entry.getUpdatedAt().toLocalDate()}</p>
                                </div>
                                <div class="photo">
                                    <img src="${entry.getImage()}" alt="Not Found"/>
                                </div>
                            </div>

                            <p class="entry-text">
                                ${entry.getDescription()}
                            </p>

                            <p class="link">
                                Link: <a href="${entry.getLink()}">${entry.getLink()}</a>
                            </p>

                            <div class="entry-actions">
                                <button>Edit</button>
                                <button class="danger">Delete</button>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </main>

            <!-- Footer -->
            <footer class="footer">
                <h3>© Learning Log</h3>
            </footer>
        </div>
    </body>
</html>
