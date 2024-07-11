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
    <div class="align-center box style3" style="width: 60%; margin:auto;">
      <h2>Budget Results:</h2>
      <br>
      <div class="style1 align-left">
        <h3><u>Total Taxes</u></h3>
        <p>With a total income of ${totalIncome} your federal income tax is ${totalTaxPercent}% which
        takes ${totalTaxAmount} and makes your take home income ${totalIncomeAfterTax}</p>
      </div>
      <br>
      <div class="style1 align-left">
        <h3><u>Total Expenses</u></h3>
        <p>This is how your expenses line up annually against your salary:</p>
        <ul>
          <li>Rent/Mortgage Price: ${annualRent}</li>
          <li>Food Spending: ${annualFood}</li>
          <li>Streaming/Cable Price: ${annualStreaming}</li>
          <li>Car Payment: ${annualCarPayment}</li>
          <li>Insurance Costs: ${annualInsurance}</li>
          <li>Phone Bill: ${annualPhone}</li>
          <li>Transportation Costs: ${annualTransport}</li>
        </ul>
      </div>
      <div class="style1 align-center">
        <h3><u>Remaining Income:</u></h3>
        <p>After paying all of your expenses and the federal income tax you will be left with
        ${totalIncomeRemainder}. It is suggested by most financial advisors to invest 20%
        of your income after taxes into savings. Many banks offer a High Yield Savings Account which gives
        around a 5% interest rate. ${investMessage}.</p><br>
        <p>Your recommended monthly saving deposit is: ${savingsDeposit}</p>
        <p>${lowRemainderMessage}</p>
      </div>
    </div>
  </body>
</html>
