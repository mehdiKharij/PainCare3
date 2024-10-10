<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.HashSet" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PainCare</title>
    
    <link rel="stylesheet" type="text/css" href="css/vendor.css">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@10/swiper-bundle.min.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
    <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css">

   

    <!-- Google Fonts ================================================== -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link
        href="https://fonts.googleapis.com/css2?family=Roboto+Condensed:ital,wght@0,300;0,700;1,300&family=Roboto:wght@300;400;700&display=swap"
        rel="stylesheet">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.15.0/font/bootstrap-icons.css">
     <link rel="icon" type="image" href="images/logoapp.png">
    <!-- script ================================================== -->
    <script src="js/modernizr.js"></script>
    <style>
    .responsive-image {
        width: 100%; 
        height: auto;}
  
        .navbar .navbar-brand img {
          width: 70px;
          height: auto;
          border-radius: 25px;
        }
        .search-container {
    display: flex;
    margin-right: 10px;
}

input[type="text"] {
    padding-left: 8px ;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 8px 0 0 8px;
    border-right: none;
    outline: none;
}

button {
    padding: 8px 12px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 0 8px 8px 0;
    background-color: #f1f1f1;
    cursor: pointer;
    outline: none;
}

button:hover {
    background-color: #ddd;
}
        </style>
</head>

<body data-bs-spy="scroll" data-bs-target="#header-nav" tabindex="0">
    <header>
        <img src="images\logoapp.png" alt="Site Icon" style="height: 30px; width: 30px;">
    </header>
	<nav class="navbar navbar-expand-lg bg-white navbar-light container-fluid py-3 position-fixed " style="font-family: Arial, Helvetica, sans-serif;background-color: white; box-shadow: 0px 5px 10px rgba(0, 0, 0, 0.1);">
        <div class="container" style="margin-top: -10px; margin-bottom: -10px; ">
            <a class="navbar-brand" href="home.jsp"><img src="images/logoapp.png" alt="logo"></a>
            <button class="navbar-toggler" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasNavbar"
                aria-controls="offcanvasNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasNavbar"
                aria-labelledby="offcanvasNavbarLabel">
                <div class="offcanvas-header">
                    <h5 class="offcanvas-title" id="offcanvasNavbarLabel">Menu</h5>
                    <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas"
                        aria-label="Close"></button>
                </div>
                <div class="offcanvas-body">
                    <ul class="navbar-nav align-items-center justify-content-end flex-grow-1 pe-3" >
                        <li class="nav-item">
                            <a class="nav-link text-uppercase   px-3" aria-current="page"
                                href="home.jsp">Accueil</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-uppercase  px-3" href="SuiviSymptomForm.jsp">Suivi</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-uppercase  px-3" href="blogServlet?action=view">Communauté</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-uppercase  px-3" href="calendrier.html">Calendrier</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-uppercase  px-3" href="about.jsp">A propos de nous</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-uppercase  px-3" href="addAlarm.jsp">Alarme</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-uppercase  px-3" href="${pageContext.request.contextPath}/userProfile?action=view">Mon Profil</a>
                        </li>
                        <li class="nav-item">
                           <a href="logoutServlet" ><button type="button" class="btn btn-primary ms-md-3"> log Out</button></a>
                        </li>                        
                        <li class="nav-item" style="margin-left: 20px;">
                            <a href="${pageContext.request.contextPath}/userProfile?action=delete">
                            <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
                                <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z"/>
                                <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z"/>
                            </svg>
                        </a>
                        </li>


                        
                    </ul>
                              
                      
                    </div>
                    
                </div>
            </div>
        </div>
    </nav>
    
    </div>


    <div class="text-center">
        <div class="container" style="margin-top: 120px; font-weight: 800;">
            <h1>Ajouter une alarme</h1>
        </div>
        <section id="hero" class="position-relative overflow-hidden d-flex align-items-center justify-content-center">
            <div class="container ">
                <div class="row align-items-center justify-content-center  ">
                    <div class="col-md-7">
                        <form action="alarmServlet" method="post" class="hero-form p-5" style="margin-top: 20px; background-color: #f5f5f5;">
                           <input type="hidden" name="action" value="add">
                            <div class="mb-3">
                                <label for="title" class="form-label mb-1" style="font-weight: bold; font-size: 20px;">Titre de l'alarm: </label>
                                <input type="text" name="title" class="form-control border-1" placeholder="Titre..." required>
                            </div>
                            <div class="mb-3">
                                <label for="time" class="form-label mb-1" style="font-weight: bold; font-size: 20px;">Time: </label>
                                <input type="time" id="time" class="form-control border-1" name="time" required>
                            </div>
                            
                            <div class="mb-3 mt-4">
                                <label for="repeatDays"  class="form-label mb-1"  style="font-weight: bold; font-size: 20px;" >Repeter:</label>
                                <label><input type="checkbox" name="day" value="Monday"> Lun</label>
        <label><input type="checkbox" name="repeatDays" value="Tuesday"> Mar</label>
        <label><input type="checkbox" name="repeatDays" value="Wednesday"> Mer</label>
        <label><input type="checkbox" name="repeatDays" value="Thursday"> Jeu</label>
        <label><input type="checkbox" name="repeatDays" value="Friday"> Ven</label>
        <label><input type="checkbox" name="repeatDays" value="Saturday"> Sam</label>
        <label><input type="checkbox" name="repeatDays" value="Sunday"> Dim</label>

        <br>
                            </div>
                            
                            <div style="margin: 30px">
                                <button type="submit" value="Add Alarm" class="btn btn-primary btn-lg">Ajouter alarme</button>
                            </div> 
                            
                        
                        </form>
                        
                    </div>
                </div>
            </div>
        </section>
    </div>
    
    

       
    <div> 
        <section id="footer">
            
            <div class="container">
                <footer class="d-flex align-items-center pt-4 text-center">
                    <div class="col-md-6 mx-auto" style="margin-top: 100px;">
                        <p>© 2024 PainCare - All rights reserved</p>
                    </div>
        
                </footer>
            </div>
        </section>
        </div> 

    <!-- script ================================================== -->
    <script src="js/jquery-1.11.0.min.js"></script>
    <script src="js/plugins.js"></script>
    <script src="js/script.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/swiper@10/swiper-bundle.min.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/iconify-icon@1.0.7/dist/iconify-icon.min.js"></script>


</body>

</html>
