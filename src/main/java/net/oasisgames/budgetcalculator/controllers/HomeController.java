package net.oasisgames.budgetcalculator.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * @author Nick
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("calculate")
    public String calculateRemainder(@RequestParam("annual-income") String annualIncome,
                                     @RequestParam("monthly-rent") String monthlyRent,
                                     @RequestParam("weekly-food") String weeklyFood,
                                     @RequestParam("monthly-streaming") String monthlyStreaming,
                                     @RequestParam("monthly-car") String carPayment,
                                     @RequestParam("monthly-insurance") String monthlyInsurance,
                                     @RequestParam("monthly-phone") String monthlyPhone,
                                     @RequestParam("weekly-transport") String weeklyTransport,
                                     Model model) {
        try {
            double annualIncomeDouble = Double.parseDouble(annualIncome);
            double monthlyRentDouble = Double.parseDouble(monthlyRent);
            double weeklyFoodDouble = Double.parseDouble(weeklyFood);
            double monthlyStreamingDouble = Double.parseDouble(monthlyStreaming);
            double carPaymentDouble = Double.parseDouble(carPayment);
            double monthlyInsuranceDouble = Double.parseDouble(monthlyInsurance);
            double monthlyPhoneDouble = Double.parseDouble(monthlyPhone);
            double weeklyTransportDouble = Double.parseDouble(weeklyTransport);
            double afterExpenses = calculateTotalRemainder(new double[] {monthlyRentDouble,
                            monthlyStreamingDouble, carPaymentDouble, monthlyInsuranceDouble,
                            monthlyPhoneDouble}, new double[] {weeklyFoodDouble,
                            weeklyTransportDouble}, annualIncomeDouble);
            if (afterExpenses == -1) {
                model.addAttribute("errorText",
                        "Internal error occurred while calculating total!");
                return "index";
            }
            if (afterExpenses < 0) {
                model.addAttribute("errorText",
                        "Your expenses add up to more than what your " +
                                "annual income covers. Try adjusting your budget!");
                return "index";
            }
            //SET RESULT PAGE ATTRIBUTES HERE
            setTotalTaxDiv(model, annualIncomeDouble);
            setTotalExpensesDiv(model, monthlyRentDouble, weeklyFoodDouble,
                    monthlyStreamingDouble, carPaymentDouble, monthlyInsuranceDouble,
                    monthlyPhoneDouble, weeklyTransportDouble);
            setRemainingIncomeDiv(model, afterExpenses, annualIncomeDouble);
        } catch (NumberFormatException e) {
            model.addAttribute("errorText",
                    "One or more fields contains invalid characters or is empty!");
            return "index";
        }
        return "result";
    }

    /**
     * Determines the remainder of the users income after expenses and taxes
     * @param monthly the users monthly expenses
     * @param weekly the users weekly expenses
     * @param income the users total annual income
     * @return the users remaining income
     */
    private double calculateTotalRemainder(double[] monthly, double[] weekly, double income) {
        double tax = calculateFederalTax(income);
        double totalExpense = calculateTotalExpenses(monthly, weekly);
        if (totalExpense == -1) return -1;
        double afterTax = income - (income * tax);
        return afterTax - totalExpense;
    }

    /**
     * Reduces all expenses down to one number after multiplying them to a yearly total
     * @param monthly array of all monthly expenses
     * @param weekly array of all weekly expenses
     * @return the total expenses
     */
    private double calculateTotalExpenses(double[] monthly, double[] weekly) {
        double totalMonth = Arrays.stream(monthly)
                .map(i -> i * 12)
                .reduce(Double::sum)
                .orElse(-1);
        double totalWeekly = Arrays.stream(weekly)
                .map(i -> i * 48)
                .reduce(Double::sum)
                .orElse(-1);
        return totalMonth + totalWeekly;
    }

    /**
     * Calculate the tax percentage of the users income
     * @param income the users annual income
     * @return the total federal tax percent represented as a decimal double
     */
    private double calculateFederalTax(double income) {
        double tax;
        if (income < 11600) tax = 0.10;
        else if (income > 11600 && income < 47150) tax = 0.12;
        else if (income > 47150 && income < 100525) tax = 0.22;
        else if (income > 100525 && income < 191950) tax = 0.24;
        else if (income > 191950 && income < 243725) tax = 0.32;
        else if (income > 243725 && income < 609350) tax = 0.35;
        else tax = 0.37;
        return tax;
    }

    /**
     * Formats a numeric string to USD currency
     * @param str string to format to currency (USD)
     * @return formatted string
     */
    private String formatStringToCurrency(String str) {
        DecimalFormat formatter = new DecimalFormat("$#,##0.00");
        BigDecimal deci = new BigDecimal(str);
        return formatter.format(deci);
    }

    /**
     * Sets the parameters of the Total Tax Div in result.jsp
     * @param model Spring Model
     * @param annualIncomeDouble users annual income
     */
    private void setTotalTaxDiv(Model model, double annualIncomeDouble) {
        model.addAttribute("totalIncome",
                formatStringToCurrency(annualIncomeDouble + ""));
        double tax = calculateFederalTax(annualIncomeDouble);
        String taxToString = (tax * 100) + "";
        model.addAttribute("totalTaxPercent", taxToString);
        model.addAttribute("tax", taxToString);
        double totalTax = annualIncomeDouble * tax;
        model.addAttribute("totalTaxAmount",
                formatStringToCurrency(totalTax + ""));
        double totalIncomeAfterTax = annualIncomeDouble - totalTax;
        model.addAttribute("totalIncomeAfterTax",
                formatStringToCurrency(totalIncomeAfterTax + ""));
    }

    /**
     * Sets the parameters of the Total Expenses Div in result.jsp
     * @param model Spring Model
     * @param rent monthly rent price
     * @param food weekly food cost
     * @param streaming monthly streaming cost
     * @param car monthly car payment
     * @param insurance monthly insurance bills
     * @param phone monthly phone bill
     * @param transport weekly transportation costs
     */
    private void setTotalExpensesDiv(Model model, double rent, double food,
                                     double streaming, double car, double insurance,
                                     double phone, double transport) {
        String annualRent = formatStringToCurrency((rent * 12) + "");
        String annualFood = formatStringToCurrency((food * 48) + "");
        String annualStreaming = formatStringToCurrency((streaming * 12) + "");
        String annualCar = formatStringToCurrency((car * 12) + "");
        String annualInsurance = formatStringToCurrency((insurance * 12) + "");
        String annualPhone = formatStringToCurrency((phone * 12) + "");
        String annualTransport = formatStringToCurrency((transport * 48) + "");
        model.addAttribute("annualRent", annualRent);
        model.addAttribute("annualFood", annualFood);
        model.addAttribute("annualStreaming", annualStreaming);
        model.addAttribute("annualCarPayment", annualCar);
        model.addAttribute("annualInsurance", annualInsurance);
        model.addAttribute("annualPhone", annualPhone);
        model.addAttribute("annualTransport", annualTransport);
    }

    /**
     * Sets the parameters of the Remaining Income Div in result.jsp
     * @param model Spring Model
     * @param remainder the users remaining income after taxes and expenses
     * @param income the users annual income
     */
    private void setRemainingIncomeDiv(Model model, double remainder, double income) {
        model.addAttribute("totalIncomeRemainder",
                formatStringToCurrency(remainder + ""));
        boolean highRemainder = remainder > income * 0.2;
        String investMessage = highRemainder ? "You have a high enough remainder that" +
                " you can invest 20% of your post-tax income into savings!" : "Your remainder is" +
                " not high enough to invest 20% of your post-tax income into savings!";
        model.addAttribute("investMessage", investMessage);
        String savingsDeposit = highRemainder ?
                formatStringToCurrency(((income * 0.2) / 12) + "") :
                formatStringToCurrency((remainder * 0.75) / 12 + "");
        model.addAttribute("savingsDeposit", savingsDeposit);
        setBudgetRatingDiv(model, remainder, income);
        if (highRemainder) return;
        model.addAttribute("lowRemainderMessage",
                "Since your remaining balance was below the 20% income threshold " +
                        "we estimated your monthly savings deposit using 75% of your remaining " +
                        "income!");
    }

    /**
     * Sets the parameters for the Budget Rating Div in result.jsp
     * @param model Spring Model
     * @param remainder the users remaining income after expenses and taxes
     * @param income the users annual income
     */
    private void setBudgetRatingDiv(Model model, double remainder, double income) {
        double percentRemainder = remainder / income;
        String rating;
        if (percentRemainder < 0.05) rating = "Brother your cooked...";
        else if (percentRemainder > 0.05 && percentRemainder < 0.1) rating = "Poor";
        else if (percentRemainder > 0.1 && percentRemainder < 0.2) rating = "Decent";
        else if (percentRemainder > 0.2 && percentRemainder < 0.3) rating = "Good";
        else rating = "Excellent";
        model.addAttribute("budgetRating", rating);
    }

}
