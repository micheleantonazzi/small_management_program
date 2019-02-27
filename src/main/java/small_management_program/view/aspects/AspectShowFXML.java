package small_management_program.view.aspects;

import org.aspectj.lang.annotation.After;

import org.aspectj.lang.annotation.Aspect;


@Aspect
public class AspectShowFXML {

    @After("execution(public void *..*.showStage*())")
    public void showFXML(){
        System.out.println("aspect");
    }
}
