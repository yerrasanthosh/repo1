package com.vroozi.customerui.util;

import java.lang.reflect.Field;

/**
 * User: SURYA MANDADAPU
 * Date: 9/28/12
 * Time: 5:37 PM
 */
public class BeanUtil {


    public static <T> Object[] copyFieldsValuesToArray(T source) throws Exception{


        Class<?> clazz = source.getClass();
        String[] columns = new String[clazz.getFields().length];
        String[] values = new String[clazz.getFields().length];
        int i=0;
        for (Field field : clazz.getFields()) {
            Object value = field.get(source);

            if(value!=null){
                columns[i]= field.getName();
                values [i]= value.toString();
                i++;
            }
        }

        System.out.println("Columns::::::::::::: "+columns.length);
        System.out.println("values::::::::::::: "+values.length);
        return new Object[]{columns, values};

    }

    public static <T> void copyFields(T target, T source) throws Exception{
        Class<?> clazz = source.getClass();

        for (Field field : clazz.getFields()) {
            Object value = field.get(source);

            if(value!=null)
                field.set(target, value);
        }
    }
}
