package dpbo.bimble;

import java.util.Date;

public class Schedule {
	private String subject;
	private Date date;
	private String startTime;
	private String endTime;
	private String room;

	public Schedule(String subject, Date date, String startTime, String endTime, String room) {
		this.subject = subject;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.room = room;
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

	public String tampilSchedule() {
		return "=========== Schedule ===========\n"
		         + "Subject : " + subject + "\n"
		         + "Date    : " + date + "\n"
		         + "Time    : " + startTime + " - " + endTime + "\n"
		         + "Room    : " + room + "\n"
		         + "================================";
		
	}
	
	public void printSchedule() {
	    System.out.println(tampilSchedule());
	}

}
