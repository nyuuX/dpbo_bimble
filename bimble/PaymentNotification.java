package dpbo.bimble;

import java.util.Date;

class PaymentNotification extends Notification {
    public PaymentNotification(String notificationID, String message, String type,
                               Registrasi registration, String paymentStatus,
                               Schedule schedule, Date date, String Subject) {
        super(notificationID, message, type, registration, paymentStatus, schedule, date, Subject); 
    }

    @Override
    public String getNotificationDetails() {
        return "[PaymentNotification]\n" +
               "Notification ID: " + getNotificationID() + "\n" +
               "Status Pembayaran: " + getPaymentStatus();
    }

    public boolean isPaymentCompleted() {
        return getPaymentStatus() != null && getPaymentStatus().equalsIgnoreCase("Completed");
    }
}
