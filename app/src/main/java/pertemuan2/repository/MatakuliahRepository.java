package pertemuan2.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pertemuan2.database.Database;
import pertemuan2.models.Matakuliah;

public class MatakuliahRepository {
    private static final Connection conn;
    
    static {
        conn = Database.connect();
    }

    //create
    public static void addMatakuliah(Matakuliah matkul){
        String sql = "INSERT INTO matakuliah (kodeMatkul,namaMatkul,sks) VALUES (?, ?, ?)";
        try{
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, matkul.kode);
            preparedStatement.setString(2, matkul.nama);
            preparedStatement.setInt(3, matkul.sks);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //read all
    public static List<Matakuliah> getAllMatakuliah(){
        List<Matakuliah> result = new ArrayList<>();
        String sql = "SELECT kodeMatkul,namaMatkul,sks From matakuliah";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                result.add(new Matakuliah(resultSet.getString("kodeMatkul"), resultSet.getString("namaMatkul"), resultSet.getInt("sks")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //read by kode
    public static Matakuliah getMatakuliahByKode (String kode){
        String sql = "SELECT kodeMatkul,namaMatkul,sks FROM matakuliah WHERE kodeMatkul = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, kode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Matakuliah(resultSet.getString("kodeMatkul"), resultSet.getString("namaMatkul"), resultSet.getInt("sks"));
            } 
        } catch (SQLException e) {
           e.printStackTrace();
        }
        return null;
    }

    //update
    public static void updateMatakuliah(String kodeJurusan, String kodeMatkul){
        String sql = "UPDATE matakuliah SET kodeJurusan = ? WHERE kodeMatkul = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, kodeJurusan);
            preparedStatement.setString(2, kodeMatkul);
            preparedStatement.executeUpdate();
            System.out.println("Matakuliah dengan kode " + kodeMatkul + " berhasil ditambahkan ke jurusan " + kodeJurusan);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //delete
    public static void deleteMatakuliah(String kode){
        String sql = "DELETE FROM matakuliah WHERE kodeMatkul = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, kode);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
