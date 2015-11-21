package pl.pd.emir.web.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpsHeaderFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpsHeaderFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("init HttpsHeaderFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        ((HttpServletResponse) response).setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");
        ((HttpServletResponse) response).setHeader("Expires", "0");
        filterChain.doFilter(request, response);
    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        LOGGER.debug("Destroying UploadFilter");
    }

}
