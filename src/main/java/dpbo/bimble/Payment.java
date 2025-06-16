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
	    private String idPembayaran;

	    public Payment(String idPembayaran, String idRegistrasi, int jumlah, int va, String metode, String status, Date tanggal, String idPayment) {
	        this.idPembayaran = idPembayaran;
	        this.idRegistrasi = idRegistrasi;
	        this.totalHarga = jumlah;
	        this.vaNum = va;
	        this.paymentMethod = metode;
	        this.status = status;
	        this.tanggalPembayaran = tanggal;
	        this.idPayment = idPayment;
	    }

	    
	    public String getIdPayment() {
	        return idPayment;
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
	    public String getIdPembayaran() {
	        return this.idPembayaran;
	    }
	    
	    @Override
	    public String toString() {
	        return "=== Payment Info ===\n" +
	               "ID Payment: " + idPayment + "\n" +
	               "ID Registrasi: " + idRegistrasi + "\n" +
	               "Total Harga: Rp" + totalHarga + "\n" +
	               "VA Number: " + vaNum + "\n" +
	               "Metode Pembayaran: " + paymentMethod + "\n" +
	               "Status: " + status + "\n" +
	               "Tanggal Pembayaran: " + tanggalPembayaran;
	    }

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


		public void setTanggalPembayaran(Date date) {
			// TODO Auto-generated method stub
			
		}
	}


