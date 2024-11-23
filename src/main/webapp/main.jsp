<%@ page import="uz.pdp.entity.Category" %>
<%@ page import="uz.pdp.DB.DB" %>
<%@ page import="uz.pdp.entity.Product" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <title>Main</title>
    <style>
        body, html {
            height: 100%;
            margin: 0;
        }

        .sidebar {
            border-radius: 8px;
            width: 20%;
            background-color: #f8f9fa;
            border-right: 1px solid #ddd;
            padding: 20px;
            height: 100vh;
            margin-left: 20px;
            margin-top: 20px;
        }
        .col .btn.active {
            color: white;
            background-color: aqua;
        }

        .sidebar .btn.active {
            background-color: red;
            color: white;
            width: 100%;
            border-color: #0d6efd;
        }

        .content {
            width: 80%;
            padding: 20px;
            overflow-y: auto;
        }

        .row-container {
            display: flex;
            height: 100%;
        }

        .product-card {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 15px;
            text-align: center;
            background-color: #f8f9fa;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .product-card:hover {
            background-color: #e9ecef;
            cursor: pointer;
        }

        .product-img {
            max-width: 100%;
            height: auto;
            border-radius: 5px;
            object-fit: cover;
            max-height: 150px;
        }

        .product-name {
            margin: 10px 0 5px;
            font-size: 1.1rem;
            font-weight: bold;
        }

        .product-price {
            color: #28a745;
            font-size: 1rem;
            margin-bottom: 10px;
        }


    </style>
</head>
<body>
<%
    Cookie[] cookies = request.getCookies();
    boolean active = false;
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("user")) {
                String value = cookie.getValue();
                String key = "youneverfinduser";
                Base64.Encoder encoder = Base64.getEncoder();
                String string = encoder.encodeToString(key.getBytes());
                if (value.equals(string)) {
                    active = true;
                }
            }

        }
    }
%>


<%
    List<Product> products = DB.products;
    String categoryId = request.getParameter("categoryId");
    String productId = request.getParameter("productId");
    String categoryLength = request.getParameter("categorylength");
    if (categoryLength != null) {
        int categorySize = Integer.parseInt(categoryLength);
        if (categoryId != null && !categoryId.equals("0") && categorySize != products.size()) {
            int id = Integer.parseInt(categoryId);
            products = new ArrayList<>(DB.products.stream()
                    .filter(p -> p.getCategoryId() == id).toList());
        }
    } else if (categoryId != null && !categoryId.equals("0")) {
        int id = Integer.parseInt(categoryId);
        products = new ArrayList<>(DB.products.stream()
                .filter(p -> p.getCategoryId() == id).toList());
    }
    if (productId != null && !productId.isEmpty()) {
        int proid = Integer.parseInt(productId);
        Product product = DB.products.stream().filter(p -> p.getId() == proid).findFirst().orElseThrow();
        DB.map.put(product, DB.map.getOrDefault(product, 0) + 1);
    }


%>
<div class="row-container">
    <!-- Sidebar -->
    <div class="sidebar">
        <h4 style="margin-left: 75px">Categories</h4>
        <form action="/main.jsp" method="post">
            <input type="hidden" name="categoryId" value="0">
            <button name="category" value="All" class="btn btn-primary w-100 <%="All".equals(request.getParameter("category")) ? "active" : ""%>">All</button>
        </form>
        <%
            for (Category category: DB.CATEGORIES) {
        %>
        <form action="/main.jsp" method="post">
            <input type="hidden" name="categoryId" value="<%=category.getId()%>">
            <button name="category" value="<%=category.getName()%>" class="btn btn-primary w-100 <%=category.getName().equals(request.getParameter("category")) ? "active" : ""%>"><%=category.getName()%></button>
        </form>
    <%
        }
    %>
    </div>
    <!-- Main Content -->
    <div class="content">
        <h1>Products List</h1>
        <%
        if (!active) {
            %>
             <a href="/login.jsp">
                     <button class="btn btn-secondary mb-3">Login</button>
                                   </a>
        <%}%>
        <a href="cart.jsp">
            <button class="btn btn-primary mb-3">Cart <%=DB.map.size()%></button>
        </a>
        <% if (active) {%>
             <a href="myOrders.jsp">
                     <button class="btn btn-primary mb-3">MyOrders</button>
                                   </a>
        <%}%>

        <div class="row row-cols-4 g-4">
            <% for (Product product : products) { %>
            <div class="col">
                <form action="/main.jsp" method="post">
                    <div class="product-card">
                        <img src="${pageContext.request.contextPath}/images/<%=product.getPath()  %>" alt="<%=product.getName()%>" class="product-img img-fluid mb-2">
                        <h5 class="product-name"><%=product.getName()%></h5>
                        <p class="product-price"><%=product.getPrice()%> $</p>
                        <input type="hidden" name="productId" value="<%=product.getId()%>">
                        <input type="hidden" name="categoryId" value="<%=product.getCategoryId()%>">
                        <input type="hidden" name="categorylength" value="<%=products.size()%>">
                        <% if (DB.map.get(product) != null) { %>
                        <p>Added</p>
                        <%} else {%>
                        <button class="btn btn-dark w-100 <%=(product.getId() + "").equals(request.getParameter("selectedProduct")) ? "active" : ""%>">
                            Add to Cart
                        </button>
                        <%}%>
                    </div>
                </form>
            </div>
            <% } %>
        </div>

    </div>
</div>

</body>
</html>
