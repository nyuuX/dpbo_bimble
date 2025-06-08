package dpbo.bimble;
	import java.util.Date;
	

	public class Payment implements Ipayment {
	    private String idPayment;
	    private String idRegistrasi;
	    private int totalHarga;
	    private int vaNum;
	    private String paymentMethod;
	    private String status;
	    private Date tanggalPembayaran;

	    // Constructor
	    public Payment(String idPayment, String idRegistrasi, int totalHarga, int vaNum, String paymentMethod, String status, Date tanggalPembayaran) {
	        this.idPayment = idPayment;
	        this.idRegistrasi = idRegistrasi;
	        this.totalHarga = totalHarga;
	        this.vaNum = vaNum;
	        this.paymentMethod = paymentMethod;
	        this.status = status;
	        this.tanggalPembayaran = tanggalPembayaran;
	    }

	    // Getter (opsional untuk akses data)
	    
	    public static String getIdPayment() {
	        return getIdPayment();
	    }

	    public String getIdRegistrasi() {
	        return idRegistrasi;
	    }

	    public int getTotalHarga() {
	        return totalHarga;
	    }

	    public int getVaNum() {
	        return vaNum;
	    }

	    public String getPaymentMethod() {
	        return paymentMethod;
	    }

	    public String getStatus() {
	        return status;
	    }

	    public Date getTanggalPembayaran() {
	        return tanggalPembayaran;
	    }
	    
	    public void setStatus(String status) {
	        this.status = status;
	    }

	    // Implementasi dari interface IPayment
	    public void printPaymentInfo() {
	        System.out.println("=== Payment Info ===");
	        System.out.println("ID Payment: " + idPayment);
	        System.out.println("ID Registrasi: " + idRegistrasi);
	        System.out.println("Total Harga: " + totalHarga);
	        System.out.println("VA Number: " + vaNum);
	        System.out.println("Metode Pembayaran: " + paymentMethod);
	        System.out.println("Status: " + status);
	        System.out.println("Tanggal Pembayaran: " + tanggalPembayaran);
	    }
	}


