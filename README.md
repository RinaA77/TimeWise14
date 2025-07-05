TimeWise14
Aplikasi android pengingat berbasis waktu yang dikembangkan sebagai tugas UAS mata kuliah Mobile Programming beranggotakan 4 orang. Dan 14 sendiri merupakan kelompok kami. Aplikasi ini membantu pengguna membuat pengingat harian dengan notifikasi  satt waktu tiba. Aplikasi ini masih belum sempurna dan masih harus dikembangkan

✨ Fitur Utama

- Menambahkan pengingat harian dengan judul, deskripsi, dan waktu
- Menampilkan daftar pengingat secara real-time
- Menyimpan data secara lokal menggunakan Room Database
- Notifikasi otomatis saat waktu pengingat tiba


🧰 Pendukung

1. Bahasa: Java
2. Aplikasi : Android Studio
3. Database: Room (Jetpack)
4. Arsitektur: MVVM (Model-View-ViewModel)
5. Komponen lain: LiveData, ViewModel, AlarmManager, NotificationCompat
6. UI/UX: Material Design + RecyclerView

🖼️ Screenshot
1. Tampilan Awal saat pertama demo
<img width="959" alt="Tampilan awal" src="https://github.com/user-attachments/assets/8ae2c86c-3f3e-43be-97a5-59c1916d7d36" />

2. Daftar pengingat, kita dapat menambahkan dengan mengklik tanda plus (+)
<img width="959" alt="Daftar Pengingat" src="https://github.com/user-attachments/assets/1abd9f7b-4efb-48f8-8fd7-12fe84d29bec" />

3. Tambahkan Pengingat
<img width="957" alt="Tampah Pengingat" src="https://github.com/user-attachments/assets/1d933cb7-3b20-4e36-b4e7-0714efa52842" />

📂 Struktur Proyek (Singkat)

* com.example.timewise14
* Activity: SplashActivity, MainActivity, AddEditReminderActivity
* Data: Reminder.java, ReminderDao, ReminderDatabase
* Repository: ReminderRepository
* Viewmodel: ReminderViewModel
* Adapter: ReminderAdapter
* Broadcast: ReminderBroadcastReceiver

👨‍💻 Kontributor Kelompok

Alip Setiawan  – Penjelasan Splash & MainActivity
Rina Afiyanti – Form Add/Edit Reminder
Elma Alviana – Struktur Database
Duta Hardhia – ViewModel, Repository, Notifikasi
