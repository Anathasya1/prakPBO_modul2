package pertemuan2.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pertemuan2.database.Database;
import pertemuan2.models.Jurusan;
import pertemuan2.models.Mahasiswa;

public class MahasiswaRepository {
    private static final Connection conn;

    static {
        conn = Database.connect();
    }

    //create
    public static void addMahasiswa(Mahasiswa mhs){
        String sql = "INSERT INTO mahasiswa (nim,namaMhs,kodeJurusan) VALUES (?, ?, ?)";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, mhs.nim);
            preparedStatement.setString(2, mhs.nama);
            preparedStatement.setString(3, mhs.jurusan.kode);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //read all
    public static List<Mahasiswa> getAllMahasiswa(){
        List<Mahasiswa> result = new ArrayList<>();
        String sql = "SELECT m.nim, m.namamhs, m.kodejurusan, j.namajurusan From mahasiswa m JOIN jurusan j ON m.kodeJurusan = j.kodeJurusan";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                result.add(new Mahasiswa(resultSet.getString("nim"), resultSet.getString("namamhs"), new Jurusan(resultSet.getString("kodejurusan"), resultSet.getString("namajurusan"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //read by nim
    public static Mahasiswa getMahasiswaByNim (String nim){
        String sql = "SELECT m.nim, m.namaMhs, m.kodeJurusan, j.namaJurusan From mahasiswa m JOIN jurusan j ON m.kodeJurusan = j.kodeJurusan WHERE nim = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, nim);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Mahasiswa(resultSet.getString("nim"), resultSet.getString("namaMhs"), new Jurusan(resultSet.getString("kodeJurusan"), resultSet.getString("namaJurusan")));
            } 
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return null;
    }

    //update
    public static void updateMahasiswa(Mahasiswa mhs){
        String sql = "UPDATE mahasiswa SET namaMhs = ? WHERE nim = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, mhs.nama);
            preparedStatement.setString(2, mhs.nim);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //delete
    public static void deleteMahasiswa(String nim){
        String sql = "DELETE FROM mahasiswa WHERE nim = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, nim);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void inputNilai (String nim, String kodeMatkul, String nilai){
        String sql = "INSERT INTO indeksNilai (nim,kodeMatkul,nilai) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nim);
            stmt.setString(2, kodeMatkul);
            stmt.setString(3, nilai);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double hitungIP(String nim){
        double hasil = 0;
        String idxNil = "";
        int nilaiChar = 0;
        int sks = 0; 
        int totalSks = 0;
        
        String sql = "SELECT nil.kodeMatkul,nil.nim,nil.nilai,Mk.sks FROM indeksNilai nil JOIN matakuliah Mk ON nil.kodeMatkul = Mk.kodeMatkul WHERE nil.nim = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, nim);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                idxNil = resultSet.getString("nilai");
                sks = resultSet.getInt("sks");
                
                if (idxNil.equalsIgnoreCase("A")) {
                    nilaiChar = 4;
                } else if (idxNil.equalsIgnoreCase("B")) {
                    nilaiChar = 3;
                } else if (idxNil.equalsIgnoreCase("C")) {
                    nilaiChar = 2;
                } else if (idxNil.equalsIgnoreCase("D")) {
                    nilaiChar = 1;
                } else {
                    nilaiChar = 0;
                }
                totalSks += sks;
                hasil += sks * nilaiChar;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasil/totalSks;
    }
}
