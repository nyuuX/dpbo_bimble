package dpbo.bimble;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.HashMap;
	import java.util.Map;

	public class PaymentHistory {
	    private List<Payment> paymentList;

	    public PaymentHistory() {
	        paymentList = new ArrayList<>();
	    }

	    public void addPayment(Payment payment) {
	        paymentList.add(payment);
	    }

	    public void removePayment(Payment payment) {
	        paymentList.remove(payment);
	    }

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

	    public Payment findPaymentById(String idPayment) {
	        for (Payment p : paymentList) {
	            if (p.getIdPayment().equals(idPayment)) {
	                return p;
	            }
	        }
	        return null; 
	    }

	    public List<Payment> getAllPayments() {
	        return paymentList;
	    }
	}