import java.util.Locale;

public class Motor extends Kendaraan {
    private String jenisTransmisi;

    public Motor(String kode, String nama, double harga, String jenisTransmisi) {
        super(kode, nama, harga);
        this.jenisTransmisi = jenisTransmisi;
    }

    public String getJenisTransmisi() { return jenisTransmisi; }
    public void setJenisTransmisi(String jenis) { this.jenisTransmisi = jenis; }

    @Override
    public void tampilInfo() {
        String status = isTersedia() ? "Tersedia" : "Tidak Tersedia";
        System.out.printf(Locale.US, "[MOTOR] Kode: %-6s | Nama: %-20s | Transmisi: %-6s | Tarif: Rp%,.0f/hari | Status: %s%n",
                getKodeKendaraan(), getNamaKendaraan(), jenisTransmisi, getHargaSewaPerHari(), status);
    }

    @Override
    public double hitungBiayaDasar(int lamaSewa) {
        double biaya = lamaSewa * getHargaSewaPerHari();
        if (jenisTransmisi.equalsIgnoreCase("Matik")) {
            biaya += 10000 * lamaSewa;
        }
        return biaya;
    }
}
