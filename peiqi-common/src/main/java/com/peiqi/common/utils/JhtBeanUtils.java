package com.peiqi.common.utils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 实体操作
 * @author STILL
 *
 */
public class JhtBeanUtils {

	/**
	 * 以Map为数据源，实例化一个Java Bean
	 * @param targetClass
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static <T> T instanceFormMap(Class<T> targetClass, Map<String, ? extends Object> source)
			throws Exception {
		BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
		T target = targetClass.newInstance();
		BeanUtils.populate(target, source);
		return target;
	}
	
	
	/**
	 * 获取非空的属性名
	 * @param source
	 * @return
	 */
	public static String[] getNotNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue != null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
