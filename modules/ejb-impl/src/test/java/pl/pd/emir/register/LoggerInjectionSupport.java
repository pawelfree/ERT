/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.pd.emir.register;

import javax.inject.Inject;
import org.slf4j.Logger;

/**
 *
 * @author PawelDudek
 */
public class LoggerInjectionSupport {

    @Inject
    private Logger logger;

    public Logger getLogger() {
        return logger;
    }
}
