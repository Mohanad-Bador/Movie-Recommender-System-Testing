package movierecommender.Errors;

public class AppError extends Exception {
    
    private String errorDetails;
    /**
     * Default constructor
     */
    public AppError() {
        super();
    }
    
    /**
     * Constructor with error message
     * 
     * @param message The error message
     */
    public AppError(String message) {
        super(message);
    }
    
    /**
     * Constructor with message, and additional details
     * 
     * @param message The error message
     * @param errorDetails Additional error details
     */
    public AppError(String message, String errorDetails) {
        super(message);
        this.errorDetails = errorDetails;
    }
    
    
    /**
     * Get the error details
     * 
     * @return The error details
     */
    public String getErrorDetails() {
        return errorDetails;
    }
}
