package movierecommender.Services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import movierecommender.Contracts.IRecommendationFileWriter;
import movierecommender.Entities.Recommendation;
import movierecommender.Errors.AppError;

public class RecommendationFileWriterService implements IRecommendationFileWriter {

    private File file;
    private FileWriter fileWriter;

    public RecommendationFileWriterService(File file, FileWriter fileWriter) {
        this.file = file;
        this.fileWriter = fileWriter;
    }

    @Override
    public void writeRecommendations(ArrayList<Recommendation> recommendations)
            throws AppError {
        // TODO Auto-generated method stub
        if (recommendations.isEmpty()) {
            throw new IllegalArgumentException("No Recommendations Available");
        }
        if (file.getName() == null || file.getName().isEmpty()) {
            throw new IllegalArgumentException("No Filename Specified");
        }
        try {

            // Write Recommendations to File

            StringBuilder recommendedMovies = new StringBuilder("");
            for (Recommendation recommendation : recommendations) {

                recommendedMovies.append(
                        recommendation.getUser().getUserName() + ", " + recommendation.getUser().getuserID() + "\n");

                for (int i = 0; i < recommendation.getRecommendedMovies().size(); i++) {
                    if (!(i == recommendation.getRecommendedMovies().size() - 1))
                        recommendedMovies.append(recommendation.getRecommendedMovies().get(i).getMovieName() + ", ");
                    else
                        recommendedMovies.append(recommendation.getRecommendedMovies().get(i).getMovieName() + "\n");

                }
            }
            fileWriter.write(recommendedMovies.toString());
            fileWriter.close();

            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            // Translating the IOException to an AppError
            throw new AppError("Error writing recommendations to file: " + e.getMessage());
        }
    }
}
