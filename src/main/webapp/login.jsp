<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <title>Login</title>
    <style>
        body, html {
            height: 100%;
            margin: 0;
        }
    </style>
</head>
<body>
 <div class="d-flex justify-content-center align-items-center h-100">
     <div class="col-3">
         <div class="card">
             <div class="card-header bg-primary text-white text-right">
                 <h3>Login</h3>
             </div>
             <div class="card-body">
                 <form action="/login" method="post">
                     <input type="text" name="phone" class="form-control" placeholder="Phone">
                     <input type="password" name="password" class="form-control my-2" placeholder="Password">
                     <div class="d-flex justify-content-end">
                         <button type="submit" class="btn btn-primary">login</button>
                     </div>
                 </form>
                 <a href="register.jsp">
                     Don't have an account? Create one now.
                 </a>
             </div>
         </div>
     </div>
 </div>
</body>
</html>
