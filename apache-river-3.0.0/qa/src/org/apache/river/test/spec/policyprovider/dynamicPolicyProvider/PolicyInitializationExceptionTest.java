/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.river.test.spec.policyprovider.dynamicPolicyProvider;

import java.util.logging.Level;

// org.apache.river.qa.harness
import org.apache.river.qa.harness.TestException;

// java.util.logging
import java.util.logging.Logger;
import java.util.logging.Level;

// java.security
import java.security.Security;

// utility classes
import org.apache.river.test.spec.policyprovider.util.Item;
import org.apache.river.test.spec.policyprovider.util.Util;
import org.apache.river.test.spec.policyprovider.util.QABadPolicy;

/**
 * <b>Purpose</b><br><br>
 *
 * This test verifies that <code>PolicyInitializationException</code>
 * is thrown if DynamicPolicyProvider's constructor unable to construct the
 * base policy.
 *
 * <b>Test Description</b><br><br>
 *
 * This test resets
 * org.apache.river.temp.davis.security.policy.DynamicPolicyProvider.basePolicyClass
 * security property so that base policy class cannot be found or cannot be
 * constructed. Then this test tries to call DynamicPolicyProvider() constructor
 * and verifies that PolicyInitializationException is thrown.
 *
 *  <br><br>
 *
 * <b>Infrastructure</b><br><br>
 *
 * <ul><lh>This test requires the following infrastructure:</lh>
 *  <li> {@link Util#listClasses}</li>
 *  <li> {@link QABadPolicy}</li>
 * </ul>
 *
 * <b>Actions</b><br><br>
 * <ol>
 *    <li> reset ...DynamicPolicyProvider.basePolicyClass security property
 *         so that base policy class cannot be found.
 *    </li>
 *    <li> construct DynamicPolicyProvider object using non-argument
 *         constructor and verify that PolicyInitializationException is thrown.
 *    </li>
 *    <li> again reset ...DynamicPolicyProvider.basePolicyClass security
 *         property so that a class can be found but this class shoud not be
 *         instance of Policy interface:
 *      <ul>
 *        <li> Obtain any class name from {@link Util#listClasses}.
 *             This array contains some helper. All of these classes 
 *             are placed in the qa1.jar and qa1-policy-provider.jar
 *             and can be loaded using http,
 *             file based url by PreferredClassLoader or by default loader.
 *        </li>
 *      </ul>
 *    </li>
 *    <li> construct DynamicPolicyProvider object using non-argument
 *         constructor and verify that PolicyInitializationException is thrown.
 *    </li>
 *    <li> again reset ...PolicyFileProvider.basePolicyClass security property
 *         to {@link QABadPolicy} class that
 *         does not declare a public no-arg constructor.
 *    </li>
 *    <li> construct DynamicPolicyProvider object using non-argument
 *         constructor and verify that PolicyInitializationException is thrown.
 *    </li>
 * </ol>
 *
 */
public class PolicyInitializationExceptionTest
        extends DynamicPolicyProviderTestBase {

    /** String to format test status string */
    private static final String CALLName = "new DynamicPolicyProvider()";

    /** Name of security property for basePolicyClass */
    protected static final String basePolicyClassProperty =
            "net.jini.security.policy.DynamicPolicyProvider.basePolicyClass";

    /**
     * Run the test according <b>Test Description</b>
     */
    public void run() throws Exception {
        /*
         * Reset ...basePolicyClass security property
         * so that base policy class cannot be found
         * and try to create DynamicPolicyProvider.
         * We expect PolicyInitializationException
         * in this case.
         */
        setBasePolicyClassProp("Foo");
        createDynamicPolicyProviderPIE(CALLName);

        /*
         * Reset ...basePolicyClass security property
         * so that base class can be found
         * but this class is not the instance
         * of Policy interface,
         * and try to create DynamicPolicyProvider.
         * We expect PolicyInitializationException
         * in this case.
         */
        String className = Util.listClasses[0].name;

        /*
         * Verify that class can be loaded.
         */
        Class.forName(className);
        setBasePolicyClassProp(className);
        createDynamicPolicyProviderPIE(CALLName);

        /*
         * Reset ...basePolicyClass security property
         * to QABadPolicy class that
         * does not declare a public no-arg constructor
         * and try to create DynamicPolicyProvider
         * We expect PolicyInitializationException
         * in these cases.
         */
        className = Util.listPolicy[0].name;

        /*
         * Verify that class can be loaded.
         */
        Class.forName(className);
        setBasePolicyClassProp(className);
        createDynamicPolicyProviderPIE(CALLName);
    }

    /**
     * Reset basePolicyClassProperty to new value.
     *
     * @param name name of new basePolicyClassProperty.
     *
     * @throws TestException if failed
     *
     */
    private void setBasePolicyClassProp(String name) throws TestException {
        try {
            Security.setProperty(basePolicyClassProperty, name);
            logger.log(Level.FINE, "Reset basePolicyClassProperty to " + name);
        } catch (Exception e) {
            throw new TestException(e.toString());
        }
    }
}
