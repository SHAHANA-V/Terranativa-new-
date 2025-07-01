package com.terranativa.servlet;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BookingServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String roomType = request.getParameter("roomType");
        int guests = Integer.parseInt(request.getParameter("guests"));
        String checkIn = request.getParameter("checkIn");
        String checkOut = request.getParameter("checkOut");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String specialRequest = request.getParameter("specialRequest");

        try {
            // Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Database Connection
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/terranativa_db?useSSL=false", "root", "Shahana@30");

            // Prepare insert statement
            String query = "INSERT INTO bookings (name, email, phone, room_type, guests, checkin_date, checkout_date, special_request) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, roomType);
            ps.setInt(5, guests);
            ps.setDate(6, java.sql.Date.valueOf(checkIn));
            ps.setDate(7, java.sql.Date.valueOf(checkOut));
            ps.setString(8, specialRequest);

            ps.executeUpdate();
            conn.close();

            // ✅ Calculate total nights
            LocalDate checkInDate = LocalDate.parse(checkIn);
            LocalDate checkOutDate = LocalDate.parse(checkOut);
            long totalNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

            // ✅ Calculate base price per night based on room type
            int basePricePerNight = switch (roomType.toLowerCase()) {
                case "standard" -> 2000;
                case "deluxe" -> 3000;
                case "suite" -> 5000;
                default -> 0;
            };

            // ✅ Add cost based on special request
            int extraCost = switch (specialRequest) {
                case "Early Check-in", "Late Check-out" -> 300;
                case "Extra Bed" -> 500;
                case "Airport Pickup" -> 800;
                default -> 0;
            };

            int totalCost = (int)(basePricePerNight * totalNights + extraCost);

            // Pass attributes to confirmation page
            request.setAttribute("name", name);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("roomType", roomType);
            request.setAttribute("guests", guests);
            request.setAttribute("checkIn", checkIn);
            request.setAttribute("checkOut", checkOut);
            request.setAttribute("specialRequest", specialRequest);
            request.setAttribute("totalCost", totalCost);
            request.setAttribute("totalNights", totalNights);

            RequestDispatcher dispatcher = request.getRequestDispatcher("confirmation.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}

