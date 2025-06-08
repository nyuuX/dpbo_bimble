package notification;

import data.Registrasi;
import data.Schedule;
import java.util.Date;

class ScheduleNotification extends Notification {
    public ScheduleNotification(String notificationID, String message, String type,
                                Registrasi registration, String paymentStatus, Schedule schedule, Date date, String Subject) {
        super(notificationID, message, type, registration, paymentStatus, schedule, date, Subject);
    }

    @Override
    public String getNotificationDetails() {
        return "[ScheduleNotification]\n" +
                "Tanggal Ujian: " + getDate() + "\n" +
                "Mata Pelajaran: " + (getSchedule() != null ? getSubject() : "");
    }

    public boolean isScheduleAvailable() {
        return getSchedule() != null && getDate() != null;
    }

    public void updateTestSchedule(Date newDate) {
        setDate(newDate);
    }
}
