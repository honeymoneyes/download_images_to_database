import util.ConnectionManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlobRunner {
    public static final String SET_IMAGE = "UPDATE aircraft SET image=? WHERE id=1";
    public static final String GET_IMAGE = "SELECT image FROM aircraft WHERE  id=1";

    public static void main(String[] args) throws SQLException, IOException {
//        saveImage();
        getImage();
    }

    private static void getImage() throws SQLException, IOException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_IMAGE);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                byte[] bytes = resultSet.getBytes("image");
                Files.write(Path.of("resources", "boeing_from_db.jpg"), bytes, StandardOpenOption.CREATE);
            }
        }
    }

    private static void saveImage() throws SQLException, IOException {
        Connection connection = ConnectionManager.open();
        PreparedStatement preparedStatement = connection.prepareStatement(SET_IMAGE);
        preparedStatement.setBytes(1, Files.readAllBytes(Path.of("resources", "boeing.jpg")));
        preparedStatement.executeUpdate();
    }
}
