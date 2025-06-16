package dpbo.bimble;

import java.util.Date;

class ScheduleNotification extends Notification {
    public ScheduleNotification(String notificationID, String message, String type,
                                Registrasi registration, String paymentStatus, Schedule schedule, Date date, String Subject, String room, String time) {
        super(notificationID, message, type, registration, paymentStatus, schedule, date, Subject, room, time);
    }
    
    @Override
    public String getNotificationDetails() {
        return " Jatwal Ujian:\n" +
               "- Tanggal         : " + getDate() + "\n" +
               "- Waktu           : " + getTime() + "\n" +
               "- Mata Pelajaran  : " + getSubject() + "\n" +
               "- Ruangan         : " + getRoom();
    }
    
    public boolean isScheduleAvailable() {
        return getSchedule() != null && getDate() != null;
    }

    public void updateTestSchedule(Date newDate) {
        setDate(newDate);
    }
}
