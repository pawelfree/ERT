package pl.pd.emir.entity.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidateCompleteness {
    
    public Class subjectClass();
    public String andGroup() default "";
    public String orGroup() default "";
    public boolean checkValuationReporting() default false;
    public boolean entry() default false;
}
