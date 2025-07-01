<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Booking Confirmation</title>
    <style>
        body { font-family: Arial; background: #f4f4f4; padding: 40px; }
        .box { background: white; padding: 20px; border-radius: 10px; max-width: 500px; margin: auto; box-shadow: 0 0 10px #aaa; }
    </style>
</head>
<body>
    <div class="box">
        <h2>Booking Summary</h2>
        <p><strong>Name:</strong> ${name}</p>
        <p><strong>Email:</strong> ${email}</p>
        <p><strong>Phone:</strong> ${phone}</p>
        <p><strong>Room Type:</strong> ${roomType}</p>
        <p><strong>Number of Guests:</strong> ${guests}</p>
        <p><strong>Check-In Date:</strong> ${checkIn}</p>
        <p><strong>Check-Out Date:</strong> ${checkOut}</p>
        <p><strong>Total Nights:</strong> ${totalNights}</p>
        <p><strong>Special Request:</strong> ${specialRequest}</p>
        <h3>Total Cost: ₹${totalCost}</h3>
        <a href="index.html">Back to Home</a>
    </div>
</body>
</html>
