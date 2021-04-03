package aplicacao.doc;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class MissingServletRequestParameterExceptionHandler {
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public String handleMissingParameter() {
        return "Your custom result";
    }
}