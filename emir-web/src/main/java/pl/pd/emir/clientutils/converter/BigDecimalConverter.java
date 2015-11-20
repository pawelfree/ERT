package pl.pd.emir.clientutils.converter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import pl.pd.emir.commons.NumberFormatUtils;
import pl.pd.emir.resources.MultipleFilesResourceBundle;

public class BigDecimalConverter implements Converter {

    private static final Logger LOG = Logger.getLogger(BigDecimalConverter.class.getName());
    final ResourceBundle BUNDLE = MultipleFilesResourceBundle.getBundle("messages");
    public int maxFractionDigits = 5;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        BigDecimal result = null;
        if (Objects.isNull(value)) {
            return null;
        }
        try {
            String tempValue = value.replaceAll("[ \u00a0]+", "").trim();
            tempValue = tempValue.replaceAll(" ", "");
            tempValue = tempValue.replaceAll(",", ".");
            LOG.log(Level.FINE, "BigDecimalConverter:getAsObject:value:{0}", tempValue);
            result = new BigDecimal(tempValue);
        } catch (java.lang.NumberFormatException ex) {
            LOG.info("BigDecimalConverter:NumberFormatException");
            return null;
        } catch (ConverterException ex) {
            throw ex;
        } catch (Exception ex) {
            LOG.log(Level.INFO, "getAsObject: conversion error: '{''}'{0}", ex.getMessage());
            return null;
        }
        return result;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        LOG.log(Level.FINE, "getAsString: value='{''}'{0}", value);
        maxFractionDigits = getAnttribute(component);
        String result = "";
        if (value instanceof BigDecimal) {
            BigDecimal number = (BigDecimal) value;
            NumberFormatUtils.NUMBER_OF_FRACTION_DIGITS = getAnttribute(component);
            result = NumberFormatUtils.format(number);
        }
        return result;
    }

    private int getAnttribute(UIComponent component) {
        int ret;
        try {
            ret = Integer.parseInt((String) component.getAttributes().get("maxFractionDigits"));
        } catch (NullPointerException ex) {
            ret = 5;
        }
        return ret;
    }
}
