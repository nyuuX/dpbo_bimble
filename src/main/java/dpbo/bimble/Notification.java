package dpbo.bimble;

import java.util.Date;

public abstract class Notification {
    private String notificationID;
    private String message;
    private String type;
    private Registrasi registration;
    private String paymentStatus;
    private Schedule schedule;
    private Date date;
    private String Subject;
    private String room;
    private String time;

    public Notification(String notificationID, String message, String type,
                        Registrasi registration, String paymentStatus, Schedule schedule, Date date, String subject, String room, String time) {
        this.notificationID = notificationID;
        this.message = message;	
        this.type = type;
        this.registration = registration;
        this.paymentStatus = paymentStatus;
        this.schedule = schedule;
        this.date = date;
        this.Subject = subject;
        this.room = room;
        this.time = time;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(String notificationID) {
        this.notificationID = notificationID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Registrasi getRegistration() {
        return registration;
    }

    public void setRegistration(Registrasi registration) {
        this.registration = registration;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setTestSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    
    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public String getSubject() {
    	return Subject;
    }
    
    public void setSubject(String Subject) {
    	this.Subject = Subject;
    }

    public String getRoom() {
        return room;
    }
    
    public String getTime() {
        return time;
    }
    
    public void setTime(String time) {
        this.time = time;
    }

    
    public abstract String getNotificationDetails();
}
