package BackEndC2.ClinicaOdontologica.exception;

public class BadRequestException extends Exception {
    
    public BadRequestException(String message){
        super(message);
    }
}
