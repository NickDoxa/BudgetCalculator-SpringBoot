package net.oasisgames.budgetcalculator.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

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
            double afterExpenses = calculateTotalAfterTax(new double[] {monthlyRentDouble,
            weeklyFoodDouble, monthlyStreamingDouble, carPaymentDouble, monthlyInsuranceDouble,
            monthlyPhoneDouble, weeklyTransportDouble}, annualIncomeDouble);
            if (afterExpenses == -1) {
                model.addAttribute("errorText",
                        "Internal error occurred while calculating total!");
                return "index";
            }
            model.addAttribute("result", "worked!");
            model.addAttribute("total", afterExpenses);
        } catch (NumberFormatException e) {
            model.addAttribute("errorText",
                    "One or more fields contains invalid characters or is empty!");
            return "index";
        }
        return "result";
    }

    private double calculateTotalAfterTax(double[] expenses, double income) {
        double tax = calculateFederalTax(income);
        double totalExpense = Arrays.stream(expenses).reduce(Double::sum).orElseGet(() -> -1);
        if (totalExpense == -1) return -1;
        return income - (income * tax) - totalExpense;
    }

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

}
