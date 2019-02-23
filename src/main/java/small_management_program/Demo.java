package small_management_program;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class Demo {

    @Before("!within(small_management_program.Demo)")
    public void logEnter(JoinPoint joinPoint) {
        System.out.print("aspect");

    }
}