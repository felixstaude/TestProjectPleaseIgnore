package de.felixstaude.testprojectpleaseignore.sql;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class IslandDatabase {
    private SQLConnection sqlConnection = new SQLConnection();
    private Connection connection = sqlConnection.getConnection();
    public void initializeTable(){
        try(Statement statement = connection.createStatement()){
            String createIslandsTable  = "CREATE TABLE IF NOT EXISTS islands (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "owner VARCHAR(255) NOT NULL," +
                    "location VARCHAR(255) NOT NULL," +
                    "UNIQUE(location));";
            statement.executeUpdate(createIslandsTable);

            String createMembersTable = "CREATE TABLE IF NOT EXISTS island_members (" +
                    "island_id INT," +
                    "member VARCHAR(255)," +
                    "FOREIGN KEY (island_id) REFERENCES islands(id)," +
                    "UNIQUE(island_id, member));";
            statement.executeUpdate(createMembersTable);

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getIslandCount(){
        String query = "SELECT COUNT(*) AS count FROM islands;";
        int count = 0;

        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                count = resultSet.getInt("count");
            }
        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
        return count;
    }

    public boolean addIsland(UUID owner, String location) {
        String insertQuery = "INSERT INTO islands (owner, location) VALUES (?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, owner.toString());
            preparedStatement.setString(2, location);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeIsland(int islandId) {
        String deleteQuery = "DELETE FROM islands WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, islandId);
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasPlayerIsland(UUID player) {
        String query = "SELECT COUNT(*) AS count FROM islands WHERE owner = ?;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, player.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getPlayerIslandId(UUID player) {
        String query = "SELECT id FROM islands WHERE owner = ?;";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, player.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public String getIslandCoordinate(UUID playerUUID){
        String query = "SELECT location FROM islands WHERE owner = ?;";

        try(PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1, playerUUID.toString());
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                return resultSet.getString("location");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

}
