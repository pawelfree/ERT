package pl.pd.emir.client.utils;

import javax.faces.context.ExceptionHandler;

public class ExceptionHandlerFactory extends javax.faces.context.ExceptionHandlerFactory {

    private final javax.faces.context.ExceptionHandlerFactory parent;

    public ExceptionHandlerFactory(final javax.faces.context.ExceptionHandlerFactory prnt) {
        super();
        this.parent = prnt;
    }

    @Override
    public final ExceptionHandler getExceptionHandler() {
        return new AjaxExceptionHandler(this.parent.getExceptionHandler());
    }
}
