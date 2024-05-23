package org.example.main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class Mainpage implements Initializable {

    @FXML
    Line line;
    @FXML
    Hyperlink Movie1;
    @FXML
    Hyperlink Movie2;
    @FXML
    Hyperlink Movie3;
    @FXML
    Hyperlink Movie4;
    @FXML
    public Label User;
    @FXML
    Button Rate;
    @FXML
    Hyperlink Movie5;
    @FXML
    Hyperlink Movie6;
    @FXML
    Hyperlink Movie7;
    @FXML
    Hyperlink Movie8;
    @FXML
    Hyperlink Movie9;
    @FXML
    Hyperlink Movie10;
    List<Integer> recommendations;
    public  List<Integer> generateRecommendations(int userId) {
        List<Integer> recommendedMovies = new ArrayList<>();
        Map<Integer, Double> userRatings = MovieRecommender.userItemMatrix.get(userId);
        if (userRatings != null) {
            Map<Integer, Double> similarities = new HashMap<>();
            for (Map.Entry<Integer, Map<Integer, Double>> entry : MovieRecommender.userItemMatrix.entrySet()) {
                int otherUserId = entry.getKey();
                if (otherUserId != userId) { // Exclude the current user
                    Map<Integer, Double> otherUserRatings = entry.getValue();
                    double similarity = calculateCosineSimilarity(userRatings, otherUserRatings);

                    similarities.put(otherUserId, similarity);
                }
            }
            List<Map.Entry<Integer, Double>> sortedSimilarities = new ArrayList<>(similarities.entrySet());
            sortedSimilarities.sort((s1, s2) -> Double.compare(s2.getValue(), s1.getValue())); // Sort by similarity in descending order

            for (Map.Entry<Integer, Double> similarityEntry : sortedSimilarities) {
                int otherUserId = similarityEntry.getKey();

                Map<Integer, Double> otherUserRatings = MovieRecommender.userItemMatrix.get(otherUserId);
                for (Map.Entry<Integer, Double> ratingEntry : otherUserRatings.entrySet()) {
                    int movieId = ratingEntry.getKey();
                    if (!userRatings.containsKey(movieId)) { // Exclude movies already rated by the user
                        recommendedMovies.add(movieId);
                    }
                    if (recommendedMovies.size() >= 10) { // Limit recommendations to 10 movies
                        break;
                    }
                }
                if (recommendedMovies.size() >= 10) {
                    break;
                }
            }
        }
        return recommendedMovies;
    }


    private double calculateCosineSimilarity(Map<Integer, Double> userRatings1, Map<Integer, Double> userRatings2) {
        // Compute dot product
        double dotProduct = 0;
        double norm1 = 0;
        double norm2 = 0;
        for (Map.Entry<Integer, Double> entry : userRatings1.entrySet()) {
            int movieId = entry.getKey();
            double rating1 = entry.getValue();
            double rating2 = userRatings2.getOrDefault(movieId, 0.0);
            dotProduct += rating1 * rating2;
            norm1 += Math.pow(rating1, 2);
        }
        for (double rating : userRatings2.values()) {
            norm2 += Math.pow(rating, 2);
        }
        norm1 = Math.sqrt(norm1);
        norm2 = Math.sqrt(norm2);

        // Avoid division by zero
        if (norm1 == 0 || norm2 == 0) {
            return 0.0;
        }

        // Compute cosine similarity
        return dotProduct / (norm1 * norm2);
    }

    public void rateMovie() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ratepage.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Rating");
        stage.setScene(scene);
        stage.show();

    }
    public void refresh() throws IOException {
        Stage stage1 = (Stage) User.getScene().getWindow();
        stage1.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainpage.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("MainPage");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MovieRecommender.loadRatingsFromCSV("src/main/dataset/merged_movies_small.csv");
        MainController.topRatedMovies=MovieRecommender.getTopRatedMovies(100);
        MainController.movieTitles2 = MovieRecommender.movieTitles;
        User.setText(MainController.UserNAME);
        // Add user movierates to user-item matrix
        MovieRecommender.userItemMatrix.put(MainController.UserID, new HashMap<>());
        for (int i = 0; i < MainController.ratedMovies.size(); i++) {
            int movieId = MainController.ratedMovies.get(i);
            double rating = MainController.movierates.get(i);
            MovieRecommender.userItemMatrix.get(MainController.UserID).put(movieId, rating);
        }

        if (MainController.RatedMovieCount>=10){
            List<Integer> recommendations;
            recommendations = generateRecommendations(MainController.UserID);
            int movieId1 = recommendations.get(0);
            System.out.println(recommendations.get(0));
            String movieTitle1 = MovieRecommender.movieTitles.get(movieId1);
            Movie1.setText(movieTitle1);
            System.out.println(recommendations.get(1));
            int movieId2 = recommendations.get(1);
            String movieTitle2 = MovieRecommender.movieTitles.get(movieId2);
            Movie2.setText(movieTitle2);
            System.out.println(recommendations.get(2));
            int movieId3 = recommendations.get(2);
            String movieTitle3 = MovieRecommender.movieTitles.get(movieId3);
            Movie3.setText(movieTitle3);
            System.out.println(recommendations.get(3));
            int movieId4 = recommendations.get(3);
            String movieTitle4 = MovieRecommender.movieTitles.get(movieId4);
            Movie4.setText(movieTitle4);
            System.out.println(recommendations.get(4));
            int movieId5 = recommendations.get(4);
            String movieTitle5 = MovieRecommender.movieTitles.get(movieId5);
            Movie5.setText(movieTitle5);
            System.out.println(recommendations.get(5));
            int movieId6 = recommendations.get(5);
            String movieTitle6 = MovieRecommender.movieTitles.get(movieId6);
            Movie6.setText(movieTitle6);
            System.out.println(recommendations.get(6));
            int movieId7 = recommendations.get(6);
            String movieTitle7 = MovieRecommender.movieTitles.get(movieId7);
            Movie7.setText(movieTitle7);
            System.out.println(recommendations.get(7));
            int movieId8 = recommendations.get(7);
            String movieTitle8 = MovieRecommender.movieTitles.get(movieId8);
            Movie8.setText(movieTitle8);
            System.out.println(recommendations.get(8));
            int movieId9 = recommendations.get(8);
            String movieTitle9 = MovieRecommender.movieTitles.get(movieId9);
            Movie9.setText(movieTitle9);
            System.out.println(recommendations.get(9));
            int movieId10 = recommendations.get(9);
            String movieTitle10 = MovieRecommender.movieTitles.get(movieId10);
            Movie10.setText(movieTitle10);
            recommendations.clear();

        }
        else {
            Movie1.setText("No Recommendation");
            Movie2.setText("No Recommendation");
            Movie3.setText("No Recommendation");
            Movie4.setText("No Recommendation");
            Movie5.setText("No Recommendation");
            Movie6.setText("No Recommendation");
            Movie7.setText("No Recommendation");
            Movie8.setText("No Recommendation");
            Movie9.setText("No Recommendation");
            Movie10.setText("No Recommendation");

        }

    }

}
