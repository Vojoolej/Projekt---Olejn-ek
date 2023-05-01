
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.Arrays;

    public class db_conn extends FilmManager{
        private static final String url = "jdbc:sqlite:C:/Users/vojoo_3uc6r7g/Downloads/db/movie_db.db";
         static Connection connection = null;
        public static void connect() {

            try {
                //Class.forName("org.sqlite.JDBC");

                connection = DriverManager.getConnection(url);
                System.out.println("Connection to SQLite has been established.");
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public static Connection getConn(){
            return connection;
        }

        public static void disconnectConn(){
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public static void saveMovie() throws SQLException {
            for(Film film : filmList){
                if(film instanceof Movie){
                    Movie movie = (Movie) film;
                    String sql = "INSERT INTO movies (name, director, year, actors, ratings) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, movie.getName());
                    statement.setString(2, movie.getDirector());
                    statement.setInt(3, movie.getYear());
                    statement.setObject(4, Arrays.toString(movie.getActors().toArray()));
                    statement.setObject(5, Arrays.toString(movie.getRatings().toArray()));
                    statement.executeUpdate();}}

        }
        public static void saveCartoon() throws SQLException {
            for(Film film : filmList){
                if(film instanceof Cartoon){
                    Cartoon cartoon = (Cartoon) film;
                    String sql = "INSERT INTO cartoons (name, director, year, animators, ratings, recommended_age) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, cartoon.getName());
                    statement.setString(2, cartoon.getDirector());
                    statement.setInt(3, cartoon.getYear());
                    statement.setObject(4, Arrays.toString(cartoon.getAnimators().toArray()));
                    statement.setObject(5, Arrays.toString(cartoon.getRatings().toArray()));
                    statement.setInt(6, cartoon.getRecommendedAge());
                    statement.executeUpdate();}}

        }

        public static void loadMovies() throws SQLException {
            ArrayList<Movie> movies = new ArrayList<>();
            String sql = "SELECT * FROM movies";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String director = resultSet.getString("director");
                int year = resultSet.getInt("year");
               ArrayList<String> actors = (ArrayList<String>) resultSet.getArray("actors").getArray();

                ArrayList<Integer> ratings = (ArrayList<Integer>) resultSet.getArray("ratings").getArray();
                Film movie = new Movie(name, director, year, actors, ratings);
                filmList.add(movie);
            }


        }

        public static void loadCartoons() throws SQLException {
            ArrayList<Cartoon> cartoons = new ArrayList<>();
            String sql = "SELECT * FROM cartoons";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String director = resultSet.getString("director");
                int year = resultSet.getInt("year");
                ArrayList<String> animators = (ArrayList<String>) resultSet.getArray("animators").getArray();
                ArrayList<Integer> ratings = (ArrayList<Integer>) resultSet.getArray("ratings").getArray();
                int recommendedAge = resultSet.getInt("recommended_age");
                Film cartoon = new Cartoon(name, director, year, animators, ratings, recommendedAge);
                filmList.add(cartoon);
            }


        }


    }

