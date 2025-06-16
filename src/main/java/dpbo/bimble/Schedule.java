package dpbo.bimble;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Schedule {
	private String subject;
	private Date date;
	private String startTime;
	private String endTime;
	private String room;
	private String hasilTes;

	public Schedule(String subject, Date date, String startTime, String endTime, String room) {
		this.subject = subject;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.room = room;
		 this.hasilTes = null;
	}

	public String getSubject() {
		return subject;
	}

	public Date getDate() {
		return date;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getRoom() {
		return room;
	}
	
	public String getHasilTes() {
        return hasilTes;
    }
	  public void setHasilTes(String hasilTes) {
	        this.hasilTes = hasilTes;
	    }

	public void setSchedule(Admin admin, String subject, Date date, String startTime, String endTime, String room) {
		if (admin == null) {
			System.out.println("Akses ditolak: hanya Admin yang bisa mengubah jadwal.");
			return;
		}
		this.subject = subject;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.room = room;
		System.out.println("Jadwal berhasil diperbarui.");
	}

	public void printSchedule() {
		System.out.println("=========== Schedule ===========");
		System.out.println("Subject : " + subject);
		System.out.println("Date    : " + date);
		System.out.println("Time    : " + startTime + " - " + endTime);
		System.out.println("Room    : " + room);
		 if (hasilTes != null) {
		        System.out.println("Hasil Tes: " + hasilTes);
		    }
		System.out.println("================================");
	}
}
