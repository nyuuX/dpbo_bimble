package dpbo.bimble;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.HashMap;
	import java.util.Map;

	public class PaymentHistory {
	    // Menyimpan daftar pembayaran
	    private List<Payment> paymentList;

	    // Constructor
	    public PaymentHistory() {
	        paymentList = new ArrayList<>();
	    }

	    // Menambah Payment ke riwayat
	    public void addPayment(Payment payment) {
	        paymentList.add(payment);
	    }

	    // Menghapus Payment dari riwayat
	    public void removePayment(Payment payment) {
	        paymentList.remove(payment);
	    }

	    // Menampilkan semua riwayat pembayaran
	    public void printAllHistory() {
	        if (paymentList.isEmpty()) {
	            System.out.println("Belum ada pembayaran.");
	        } else {
	            System.out.println("=== Payment History ===");
	            for (Payment p : paymentList) {
	                p.printPaymentInfo();
	                System.out.println("------------------------");
	            }
	        }
	    }

	    // Mencari Payment berdasarkan ID
	    public Payment findPaymentById(String idPayment) {
	        for (Payment p : paymentList) {
	            if (p.getIdPayment().equals(idPayment)) {
	                return p;
	            }
	        }
	        return null; // Jika tidak ditemukan
	    }

	    // Mendapatkan semua Payment
	    public List<Payment> getAllPayments() {
	        return paymentList;
	    }
	}


