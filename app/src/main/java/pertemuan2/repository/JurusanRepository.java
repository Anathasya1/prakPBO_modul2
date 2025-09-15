package pertemuan2.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pertemuan2.database.Database;
import pertemuan2.models.Jurusan;

public class JurusanRepository {
    private static final Connection conn;

    static {
        conn = Database.connect();
    }

    // create
    public static void addJurusan(Jurusan jurusan) {
        String sql = "INSERT INTO jurusan (kodeJurusan, namaJurusan) VALUES (?,?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, jurusan.kode);
            preparedStatement.setString(2, jurusan.nama);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // read all
    public static List<Jurusan> getAllJurusan() {
        List<Jurusan> result = new ArrayList<>();
        String sql = "SELECT kodeJurusan,namaJurusan FROM jurusan";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                result.add(new Jurusan(resultSet.getString("kodeJurusan"), resultSet.getString("namaJurusan")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    //read by kode
    public static Jurusan getJurusanByKode(String kode){
        String sql = "SELECT kodeJurusan,namaJurusan FROM jurusan WHERE kodeJurusan = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, kode);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Jurusan(resultSet.getString("kodeJurusan"), resultSet.getString("namaJurusan"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // update
    public static void updateJurusan(Jurusan jurusan) {
        String sql = "UPDATE jurusan SET namaJurusan = ? WHERE kodeJurusan = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, jurusan.nama);
            preparedStatement.setString(2, jurusan.kode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //delete
    public static void deleteJurusan(String kode){
        String sql = "DELETE FROM jurusan WHERE kodeJurusan = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1,kode);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
