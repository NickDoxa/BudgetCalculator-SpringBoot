<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <head>
        <title>Budget Calculator</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
        <link rel="stylesheet" href="../assets/css/main.css" />
        <noscript><link rel="stylesheet" href="../assets/css/noscript.css" /></noscript>
        <script src="../assets/js/jquery.min.js"></script>
    </head>
    <body>
        <div class="align-center style3 box" style="width:35%; margin:auto;">
            <h2>Budget Calculator</h2>
            <p>${errorText}</p>
            <form method="post" action="calculate">
                <label for="annual-income">Annual Income:</label>
                <input type="text" id="annual-income" name="annual-income"><br>
                <label for="monthly-rent">Monthly Rent/Mortgage:</label>
                <input type="text" id="monthly-rent" name="monthly-rent"><br>
                <label for="weekly-food">Weekly Food Expense:</label>
                <input type="text" id="weekly-food" name="weekly-food"><br>
                <label for="monthly-streaming">Monthly Streaming Services/Cable:</label>
                <input type="text" id="monthly-streaming" name="monthly-streaming"><br>
                <label for="monthly-car">Monthly Car Payment:</label>
                <input type="text" id="monthly-car" name="monthly-car"><br>
                <label for="monthly-insurance">Monthly Insurance (All active insurance costs):</label>
                <input type="text" id="monthly-insurance" name="monthly-insurance"><br>
                <label for="monthly-phone">Monthly Phone Bill:</label>
                <input type="text" id="monthly-phone" name="monthly-phone"><br>
                <label for="weekly-transport">Weekly Transportation/Gas:</label>
                <input type="text" id="weekly-transport" name="weekly-transport"><br>
                <input type="submit" value="Calculate Budget">
            </form>
        </div>
    </body>
</html>