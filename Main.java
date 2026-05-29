import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static GoDriveRentalSystem system = new GoDriveRentalSystem();

    public static void main(String[] args) {
        // Data awal
        system.tambahKendaraan(new Mobil("MBL01", "Toyota Avanza", 350000, 7));
        system.tambahKendaraan(new Mobil("MBL02", "Daihatsu Sigra", 300000, 7));
        system.tambahKendaraan(new Mobil("MBL03", "Honda Brio", 280000, 5));
        system.tambahKendaraan(new Motor("MTR01", "Honda Vario", 80000, "Matik"));
        system.tambahKendaraan(new Motor("MTR02", "Yamaha NMAX", 100000, "Matik"));
        system.tambahKendaraan(new Motor("MTR03", "Kawasaki KLX", 90000, "Manual"));

        boolean running = true;
        while (running) {
            tampilMenu();
            System.out.print("Pilih menu: ");
            int pilihan;
            try {
                pilihan = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Input tidak valid. Masukkan angka 1-5.");
                continue;
            }

            switch (pilihan) {
                case 1 -> tambahKendaraan();
                case 2 -> system.tampilkanDaftarKendaraan();
                case 3 -> menuSewa();
                case 4 -> menuKembalikan();
                case 5 -> {
                    System.out.println("Terima kasih telah menggunakan GoDrive. Sampai jumpa!");
                    running = false;
                }
                default -> System.out.println("[ERROR] Pilihan tidak tersedia.");
            }
        }
        sc.close();
    }

    static void tampilMenu() {
        System.out.println("\n====== MENU GO DRIVE RENTAL SYSTEM ======");
        System.out.println("1. Tambah Kendaraan");
        System.out.println("2. Tampilkan Daftar Armada");
        System.out.println("3. Sewa Kendaraan");
        System.out.println("4. Kembalikan Kendaraan");
        System.out.println("5. Keluar");
    }

    static void tambahKendaraan() {
        System.out.print("Masukkan jenis kendaraan (mobil/motor): ");
        String jenis = sc.nextLine().trim().toLowerCase();

        System.out.print("Masukkan kode kendaraan: ");
        String kode = sc.nextLine().trim();

        System.out.print("Masukkan nama kendaraan: ");
        String nama = sc.nextLine().trim();

        System.out.print("Masukkan harga sewa per hari: ");
        double harga;
        try {
            harga = Double.parseDouble(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Harga tidak valid.");
            return;
        }

        if (jenis.equals("mobil")) {
            System.out.print("Masukkan kapasitas kursi: ");
            int kursi;
            try {
                kursi = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Jumlah kursi tidak valid.");
                return;
            }
            system.tambahKendaraan(new Mobil(kode, nama, harga, kursi));
        } else if (jenis.equals("motor")) {
            System.out.print("Masukkan jenis transmisi (Matik/Manual): ");
            String transmisi = sc.nextLine().trim();
            system.tambahKendaraan(new Motor(kode, nama, harga, transmisi));
        } else {
            System.out.println("[ERROR] Jenis kendaraan tidak dikenal. Masukkan 'mobil' atau 'motor'.");
        }
    }

    static void menuSewa() {
        System.out.print("Masukkan kode kendaraan yang ingin disewa: ");
        String kode = sc.nextLine().trim();

        System.out.print("Masukkan durasi sewa (dalam hari): ");
        int durasi;
        try {
            durasi = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Durasi tidak valid.");
            return;
        }

        System.out.print("Apakah Anda Member VIP? (y/n): ");
        String vipInput = sc.nextLine().trim().toLowerCase();
        boolean isVIP = vipInput.equals("y");

        system.sewaKendaraan(kode, durasi, isVIP);
    }

    static void menuKembalikan() {
        System.out.print("Masukkan kode kendaraan yang ingin dikembalikan: ");
        String kode = sc.nextLine().trim();
        system.kembalikanKendaraan(kode);
    }
}
