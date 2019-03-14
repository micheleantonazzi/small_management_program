package small_management_program.view.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.DeclareSoft;
import org.aspectj.lang.reflect.MethodSignature;
import small_management_program.controller.queries.Query;
import small_management_program.model.database.DatabaseException;
import small_management_program.view.AnnotationShowAlertSuccess;
import small_management_program.view.graphicutilities.GraphicUtilities;

import java.sql.SQLException;

@Aspect
public class AspectShowAlerts {

    //Test database connection
    @Around("(execution(* small_management_program.model.database.Database.testConnection(..)) &&" +
            "cflowbelow(execution(* small_management_program.view.stages.StageDatabaseController.testConnection()))) ||" +

            "(execution(* small_management_program.model.database.Database.setConnection(..)) &&" +
            "cflowbelow(execution(* small_management_program.view.stages.StageDatabaseController.connection())))")
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



    @Around("execution(* small_management_program.view.stages..*.StageGoal*())")
    public Object showAlertStageAction(ProceedingJoinPoint joinPoint){
        Object ret = new Object();
        try{
            ret = joinPoint.proceed();

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            if (signature.getMethod().getAnnotation(AnnotationShowAlertSuccess.class) != null)
                GraphicUtilities.getInstance().showAlertSuccess("Operazione riuscita",
                    "Operazione eseguita con successo.");
        }
        catch (SQLException ex){
            GraphicUtilities.getInstance().showAlertError("Operazione non riuscita",
                    ex.getMessage());
        }
        catch (DatabaseException ex){
            GraphicUtilities.getInstance().showAlertError(ex);
        }
        finally {
            return ret;
        }
    }
}
