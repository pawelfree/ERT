package pl.pd.emir.client.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import javax.ejb.EJBAccessException;
import javax.ejb.EJBException;
import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.persistence.OptimisticLockException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AjaxExceptionHandler extends ExceptionHandlerWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AjaxExceptionHandler.class);

    private final javax.faces.context.ExceptionHandler wrapped;

    public final static String MESSAGE_DETAIL_KEY = "ERROR_DETAIL";
    private final static String ERROR_PAGE = "/pages/error/error.xhtml";
    private final static String SESSION_EXPIRED_PAGE = "/pages/error/expired.xhtml";
    private final static String OLE_PAGE = "/pages/error/ole.xhtml";
    private static final String ATTRIBUTE_ERROR_EXCEPTION = "javax.servlet.error.exception";
    private static final String ATTRIBUTE_ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
    private static final String ATTRIBUTE_ERROR_MESSAGE = "javax.servlet.error.message";
    private static final String ATTRIBUTE_ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    private static final String ATTRIBUTE_ERROR_STATUS_CODE = "javax.servlet.error.status_code";

    public AjaxExceptionHandler(final javax.faces.context.ExceptionHandler wrapp) {
        super();
        this.wrapped = wrapp;
    }

    @Override
    public final javax.faces.context.ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public final void handle() throws FacesException {

        for (final Iterator<ExceptionQueuedEvent> it = getUnhandledExceptionQueuedEvents().iterator(); it.hasNext();) {
            Throwable throwable = it.next().getContext().getException();
            throwable = getRootException(throwable);

            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ExternalContext externalContext = facesContext.getExternalContext();
            final HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
            try {
                LOGGER.info("{}: {}", throwable.getClass().getSimpleName(), throwable.getMessage());
                String message = "";
                String requestPage = ERROR_PAGE;
                if (throwable instanceof ViewExpiredException) {
                    LOGGER.info("{}: {}", throwable.getClass().getSimpleName(), throwable.getMessage());
                    requestPage = SESSION_EXPIRED_PAGE;
                    message = throwable.getLocalizedMessage();
                } else if (throwable instanceof EJBAccessException) {
                    LOGGER.info("{}: {}", throwable.getClass().getSimpleName(), throwable.getMessage());
                    message = "Brak uprawnień do wywołanej funkcji.\n " + throwable.getLocalizedMessage();
                } else if (throwable instanceof OptimisticLockException) {
                    LOGGER.info("{}: {}", throwable.getClass().getSimpleName(), throwable.getMessage());
                    requestPage = OLE_PAGE;
                } else {
                    LOGGER.info("{}: {}", throwable.getClass().getSimpleName(), throwable.getMessage());
                    message = throwable.getLocalizedMessage();
                }

                final StringWriter sw = new StringWriter();
                throwable.printStackTrace(new PrintWriter(sw));
                request.getSession().setAttribute(ATTRIBUTE_ERROR_EXCEPTION, sw.toString());
                request.getSession().setAttribute(ATTRIBUTE_ERROR_EXCEPTION_TYPE, throwable.getClass());
                request.getSession().setAttribute(ATTRIBUTE_ERROR_MESSAGE, message);
                request.getSession().setAttribute(ATTRIBUTE_ERROR_REQUEST_URI, request.getRequestURI());
                request.getSession().setAttribute(ATTRIBUTE_ERROR_STATUS_CODE, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                LOGGER.info("redirect to " + requestPage);
                doRedirect(facesContext, requestPage);

                facesContext.responseComplete();
            } finally {
                it.remove();
            }

        }
        getWrapped().handle();
    }

    @SuppressWarnings("ThrowableResultIgnored")
    private Throwable getRootException(Throwable throwable) {
        while ((throwable instanceof FacesException || throwable instanceof EJBException || throwable instanceof ELException)
                && throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        return throwable;
    }

    public void doRedirect(final FacesContext fc, final String page) throws FacesException {
        try {
            final ExternalContext ec = fc.getExternalContext();
            final ServletRequest request = (ServletRequest) ec.getRequest();
            if (ec.isResponseCommitted()) {
                LOGGER.error("Redirect not possible, response commited !!!");
                return;
            }
            final String partialAjax = request.getParameter("javax.faces.partial.ajax");
            if ("true".equals(partialAjax) && fc.getResponseWriter() == null && fc.getRenderKit() == null) {
                final UIViewRoot view = fc.getApplication().getViewHandler().createView(fc, "");
                fc.setViewRoot(view);
            }
            ec.redirect(ec.getRequestContextPath() + page);

        } catch (final IOException e) {
            LOGGER.error("Redirect to the specified page '" + page + "' failed");
            throw new FacesException(e);
        }
    }
}
