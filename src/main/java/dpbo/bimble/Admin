package dpbo.bimble;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User {
	
		Auth auth = new Auth();
		List<User> userDatabase = auth.getALL();
		
		public Admin(String userId, String username, String password, String role) {
			super(userId, username, password, role);
			
		}
		
		public void viewRegistrations() {
			System.out.println("Menampilkan seluruh data siswa: ");
			for (User user : userDatabase) {
				if (user instanceof Student)
				System.out.println(user);
			}
		}
		
		public void confirmRegistrations() {
			for (User user : userDatabase) {
				if (user instanceof Student) {
					
				}
			}
		}
		
		public void confirmPayment(Payment payment) {
		    if (payment != null) {
		        payment.setStatus("Sukses");
		        System.out.println("Pembayaran dengan ID " + payment.getIdPayment() + " telah dikonfirmasi.");
		    } else {
		        System.out.println("Data pembayaran tidak ditemukan.");
		    }
		}
		

		
		public void addSchedule(Schedule schedule) {
			System.out.println("Jadwal berhasil ditambahkan.");
		}
		
		public void updateSchedule() {
			System.out.println("Jadwal berhasil diperbarui.");
		}
		
		public void removeSchedule() {
			
	    }

		@Override
		public void login() {
			System.out.println("Akun dengan username '" + getUsername() + "' telah berhasil login sebagai Admin");
		}

		@Override
		public void logout() {
	        System.out.println("Admin dengan username '" + getUsername() + "' telah logout");			
		}

		@Override
		public void register() {
	        System.out.println("Admin dengan username '" + getUsername() + "' telah berhasil melakukan registerasi");			
		}
		

			
		}

	
