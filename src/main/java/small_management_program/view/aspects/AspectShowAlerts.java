package small_management_program.view.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.DeclareSoft;
import small_management_program.view.graphicutilities.GraphicUtilities;

import java.sql.SQLException;

@Aspect
public class AspectShowAlerts {

    //Test database connection
    @Around("execution(* small_management_program.model.database.Database.testConnection(..)) &&" +
            "cflowbelow(execution(* small_management_program.view.stages.StageDatabaseController.testConnection()))")
    public Object showAlertError(ProceedingJoinPoint joinPoint){

        Object ret = new Object();
        try{
            ret = joinPoint.proceed();
            GraphicUtilities.getInstance().showAlertSuccess("Connessione riuscita",
                    "La connessione al database \u00E8 avvenuta con successo.");
        }
        catch (Throwable ex){
            GraphicUtilities.getInstance().showAlertError("Errore di connessione",
                    "Attenzione, impossibile stabilire una connessione con il database.");
        }
        finally {
            return ret;
        }
    }
}
