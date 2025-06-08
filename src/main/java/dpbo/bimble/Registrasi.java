package dpbo.bimble;

import java.util.Date;

public class Registrasi {
	private static int i = 1;
	private String idRegistrasi;
	private String namaLengkap;
	private Date tanggalLahir;
	private String jenisKelamin;
	private String alamat;
	private String noHp;
	private String email;
	private String namaWali;
	private String noHpWali;
	private String metodePembayaran;
	private boolean konfirmasi;

	public Registrasi(String namaLengkap, Date tanggalLahir,
			String jenisKelamin, String alamat, String noHp, String email, String namaWali, String noHpWali,
			String metodePembayaran) {
		this.idRegistrasi = "BRI-"+i;
		i++;
		this.namaLengkap = namaLengkap;
		this.tanggalLahir = tanggalLahir;
		this.jenisKelamin = jenisKelamin;
		this.alamat = alamat;
		this.noHp = noHp;
		this.email = email;
		this.namaWali = namaWali;
		this.noHpWali = noHpWali;
		this.metodePembayaran = metodePembayaran;
		if (konfirmasi) {
			
		}
		this.konfirmasi = false;
	}

	public String getIdRegistrasi() {
		return idRegistrasi;
	}

	public void setIdRegistrasi(String idRegistrasi) {
		this.idRegistrasi = idRegistrasi;
	}

	public String getNamaLengkap() {
		return namaLengkap;
	}

	public void setNamaLengkap(String namaLengkap) {
		this.namaLengkap = namaLengkap;
	}

	public Date getTanggalLahir() {
		return tanggalLahir;
	}

	public void setTanggalLahir(Date tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}

	public String getJenisKelamin() {
		return jenisKelamin;
	}

	public void setJenisKelamin(String jenisKelamin) {
		this.jenisKelamin = jenisKelamin;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getNoHp() {
		return noHp;
	}

	public void setNoHp(String noHp) {
		this.noHp = noHp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNamaWali() {
		return namaWali;
	}

	public void setNamaWali(String namaWali) {
		this.namaWali = namaWali;
	}

	public String getNoHpWali() {
		return noHpWali;
	}

	public void setNoHpWali(String noHpWali) {
		this.noHpWali = noHpWali;
	}

	public String getMetodePembayaran() {
		return metodePembayaran;
	}

	public void setMetodePembayaran(String metodePembayaran) {
		this.metodePembayaran = metodePembayaran;
	}

	public boolean isKonfirmasi() {
		return konfirmasi;
	}

	public void setKonfirmasi(boolean konfirmasi) {
		this.konfirmasi = konfirmasi;
	}

	@Override
	public String toString() {
		return 	"==========================================\n" +
	            "ID Registrasi    : " + idRegistrasi + "\n" +
	            "Nama Lengkap     : " + namaLengkap + "\n" +
	            "Tanggal Lahir    : " + tanggalLahir + "\n" +
	            "Jenis Kelamin    : " + jenisKelamin + "\n" +
	            "Alamat           : " + alamat + "\n" +
	            "No. HP           : " + noHp + "\n" +
	            "Email            : " + email + "\n" +
	            "Nama Wali        : " + namaWali + "\n" +
	            "No. HP Wali      : " + noHpWali + "\n" +
	            "Metode Pembayaran: " + metodePembayaran + "\n" +
	            "Konfirmasi       : " + (konfirmasi ? "Sudah" : "Belum") + "\n" +
	            "==========================================";
	}

	public Object getStudent() {
		// TODO Auto-generated method stub
		return null;
	}

}
