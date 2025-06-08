package dpbo.bimble;

public class InfoTest {
    private JadwalTest jadwalTest;
    private String hasilTes;

    public InfoTest(JadwalTest jadwalTest) {
        this.jadwalTest = jadwalTest;
        this.hasilTes = "";
    }

    public String getHasilTes() {
        return hasilTes;
    }

    public JadwalTest getJadwalTest() {
        return jadwalTest;
    }

    public void inputHasilTesOlehAdmin(String hasilTes, String role) {
        if (role.equalsIgnoreCase("admin")) {
            this.hasilTes = hasilTes;
            System.out.println("Hasil tes berhasil dimasukkan oleh admin.");
        } else {
            System.out.println("Anda tidak memiliki akses untuk memasukkan hasil tes.");
        }
    }

    public void tampilkanInfoTest() {
        System.out.println("Tanggal Tes: " + jadwalTest.getTanggalTest());
        System.out.println("Hasil Tes: " + (hasilTes.isEmpty() ? "Belum diinput oleh admin" : hasilTes));
    }

}
