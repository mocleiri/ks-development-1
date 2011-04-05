/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.lpr.service.adapter.checker;

import org.kuali.student.enrollment.lpr.service.adapter.validation.LuiPersonRelationMissingParameterCheckerAdapter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.ComparisonInfo;
import org.kuali.student.common.dto.StateInfo;
import org.kuali.student.common.dto.TypeInfo;

import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;


import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;


import static org.junit.Assert.fail;

/**
 *
 * @author nwright
 */
public class LuiPersonRelationMissingParameterCheckerAdapterTest {

	public LuiPersonRelationMissingParameterCheckerAdapterTest() {
	}
	private LuiPersonRelationService service = new LuiPersonRelationMissingParameterCheckerAdapter();

	public LuiPersonRelationService getService() {
		return service;
	}

	public void setService(LuiPersonRelationService service) {
		this.service = service;
	}

	@BeforeClass
	public static void setUpClass()
			throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test all the methods
	 */
	@Test
	public void testAllMethods() throws Exception {
		System.out.println("testAllMethods");
		for (Method m : getMethodsToTest ()) {
			System.out.println("testing method.." + m.getName());
			Class<?>[] parameterTypes = m.getParameterTypes();
			Object[] parameterValues = new Object[parameterTypes.length];
			for (int i = 0; i < parameterTypes.length; i++) {
				fillParameterValues(parameterTypes, parameterValues, i);
				try {
					m.invoke(this.service, parameterValues);
					fail(m.getName() + " with " + i + " parameters did not throw an exception as expected");
				} catch (InvocationTargetException ex) {
					if (!ex.getTargetException().getClass().equals(MissingParameterException.class)) {
						fail(m.getName() + " with " + i + " parameters did not throw a MissingParameterException as expected but threw a " + ex.getTargetException());
					}
				}
			}
			fillParameterValues(parameterTypes, parameterValues, parameterValues.length);
			try {
				m.invoke(this.service, parameterValues);
			} catch (InvocationTargetException ex) {
				if (ex.getTargetException().getClass().equals(MissingParameterException.class)) {
					fail(m.getName() + " with all parameters still threw a MissingParameterException when it should not have");
				}
			}
		}
	}


	private List<Method> getMethodsToTest ()
	{
//      Method[] methods = LuiPersonRelationService.class.getDeclaredMethods();
//		TODO: Switch this when using for real, right now just want to test implemented ones
	  Method[] methods = service.getClass ().getDeclaredMethods();
	  List<Method> selectedMethods = new ArrayList<Method> (methods.length);
	  for (Method method : methods) {
		  if ( ! Modifier.isPublic(method.getModifiers())) {
			  continue;
		  }
		  selectedMethods.add(method);
	  }
	  return selectedMethods;
	}

	private void fillParameterValues(Class<?>[] parameterTypes, Object[] parameterValues, int maxToFill) {
		for (int i = 0; i < parameterTypes.length; i++) {
			if (i < maxToFill) {
				parameterValues[i] = getFillValue(parameterTypes[i]);
			} else {
				parameterValues[i] = null;
			}
		}
	}

	private Object getFillValue(Class<?> type) {
		if (type.equals(String.class)) {
			return "some string";
		}
		if (type.equals(List.class)) {
			return new ArrayList();
		}
		if (type.equals(int.class)) {
			return 1;
		}
		if (type.equals(Integer.class)) {
			return new Integer(2);
		}
		if (type.equals(Long.class)) {
			return new Long(3);
		}
		if (type.equals(Date.class)) {
			return new Date();
		}
		if (type.equals(Boolean.class)) {
			return Boolean.TRUE;
		}
		if (type.equals(LuiPersonRelationInfo.class)) {
			return new LuiPersonRelationInfo.Builder().build();
		}
		if (type.equals(ContextInfo.class)) {
			return new ContextInfo.Builder().build();
		}
		if (type.equals(ComparisonInfo.class)) {
			return new ComparisonInfo.Builder().build();
		}
		if (type.equals(StateInfo.class)) {
			return new StateInfo.Builder().build();
		}
		if (type.equals(TypeInfo.class)) {
			return new TypeInfo.Builder().build();
		}
		throw new IllegalArgumentException("unhandled type " + type.getName());
	}
}
