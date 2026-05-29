import java.util.ArrayList;
import java.util.Locale;

public class GoDriveRentalSystem {
    private ArrayList<Kendaraan> daftarKendaraan;

    public GoDriveRentalSystem() {
        daftarKendaraan = new ArrayList<>();
    }

    public void tambahKendaraan(Kendaraan k) {
        daftarKendaraan.add(k);
        System.out.println("[INFO] Kendaraan berhasil ditambahkan: " + k.getNamaKendaraan() + " (" + k.getKodeKendaraan() + ")");
    }

    public void tampilkanDaftarKendaraan() {
        System.out.println("\n=== DAFTAR ARMADA GODRIVE ===");
        if (daftarKendaraan.isEmpty()) {
            System.out.println("Belum ada kendaraan terdaftar.");
            return;
        }
        for (int i = 0; i < daftarKendaraan.size(); i++) {
            System.out.print((i + 1) + ". ");
            daftarKendaraan.get(i).tampilInfo();
        }
        System.out.println();
    }

    public void sewaKendaraan(String kode, int lamaSewa, boolean isVIP) {
        Kendaraan target = findKendaraan(kode);

        if (target == null || !target.isTersedia()) {
            throw new KendaraanTidakTersediaException(
                "Kendaraan dengan kode " + kode + " gagal disewa. Alasan: Kendaraan sedang disewa atau tidak ditemukan!");
        }

        target.setTersedia(false);
        double biayaDasar = lamaSewa * target.getHargaSewaPerHari();
        double biayaTambahan = 0;
        double diskon = 0;

        System.out.println("\n=== TRANSAKSI SEWA GODRIVE ===");
        System.out.println("Kendaraan Berhasil Disewa!");
        System.out.printf("Unit           : %s (%s)%n", target.getNamaKendaraan(), target.getKodeKendaraan());
        System.out.printf("Lama Sewa      : %d hari%n", lamaSewa);
        System.out.printf(Locale.US, "Biaya Dasar Harian : Rp %,.0f%n", biayaDasar);

        if (target instanceof Mobil) {
            Mobil m = (Mobil) target;
            if (m.getJumlahKursi() > 5) {
                biayaTambahan = 50000;
                System.out.printf(Locale.US, "Tambahan Kursi (>5): Rp %,.0f%n", biayaTambahan);
            }
        } else if (target instanceof Motor) {
            Motor mo = (Motor) target;
            if (mo.getJenisTransmisi().equalsIgnoreCase("Matik")) {
                biayaTambahan = 10000 * lamaSewa;
                System.out.printf(Locale.US, "Tambahan Asuransi Matik: Rp %,.0f%n", biayaTambahan);
            }
        }

        double subtotal = biayaDasar + biayaTambahan;

        // Diskon VIP 10%
        if (isVIP) {
            diskon += subtotal * 0.10;
            System.out.printf(Locale.US, "Diskon Member VIP (10%%): -Rp %,.0f%n", subtotal * 0.10);
        }

        // Diskon lebih dari 7 hari: 5%
        if (lamaSewa > 7) {
            double diskonDurasi = subtotal * 0.05;
            diskon += diskonDurasi;
            System.out.printf(Locale.US, "Diskon Sewa >7 Hari (5%%): -Rp %,.0f%n", diskonDurasi);
        }

        double total = subtotal - diskon;
        System.out.println("----------------------------------------");
        System.out.printf(Locale.US, "TOTAL BIAYA AKHIR: Rp %,.0f%n%n", total);
    }

    public void kembalikanKendaraan(String kode) {
        Kendaraan target = findKendaraan(kode);
        if (target == null) {
            System.out.println("[ERROR] Kendaraan dengan kode " + kode + " tidak ditemukan.");
            return;
        }
        if (target.isTersedia()) {
            System.out.println("[INFO] Kendaraan " + target.getNamaKendaraan() + " (" + kode + ") tidak sedang disewa.");
            return;
        }
        target.setTersedia(true);
        System.out.println("[INFO] Kendaraan " + target.getNamaKendaraan() + " (" + kode + ") berhasil dikembalikan. Status: Tersedia.");
    }

    private Kendaraan findKendaraan(String kode) {
        for (Kendaraan k : daftarKendaraan) {
            if (k.getKodeKendaraan().equalsIgnoreCase(kode)) {
                return k;
            }
        }
        return null;
    }
}
