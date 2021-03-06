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

package org.apache.river.test.spec.discoveryservice.event;

import java.util.logging.Level;

import org.apache.river.test.spec.discoveryservice.AbstractBaseTest;

import org.apache.river.qa.harness.TestException;
import org.apache.river.qa.harness.QAConfig;
import org.apache.river.qa.harness.Test;

import org.apache.river.test.share.DiscoveryProtocolSimulator;
import org.apache.river.test.share.GroupsUtil;
import org.apache.river.test.share.LocatorsUtil;

import net.jini.discovery.LookupDiscoveryRegistration;

import net.jini.core.discovery.LookupLocator;
import net.jini.core.lookup.ServiceRegistrar;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This class verifies that the lookup discovery service operates in a manner
 * consistent with the specification. In particular, this class verifies
 * that the lookup discovery service can successfully employ both the
 * multicast and unicast discovery protocols on behalf of one or more clients
 * registered with that service to discover a number of pre-determined lookup
 * services and then, for each discovered lookup service, send to the 
 * appropriate registration listener, the appropriate remote event containing
 * the set of member locators with which the discovered lookup service was
 * configured.
 *
 * The environment in which this class expects to operate is as follows:
 * <p><ul>
 *   <li> one or more lookup services, each belonging to a finite set of
 *        member locators
 *   <li> one instance of the lookup discovery service
 *   <li> one or more registrations with the lookup discovery service
 *   <li> each registration with the lookup discovery service requests that
 *        some of the lookup services be discovered through only locator
 *        discovery, some through only locator discovery, and some through
 *        both group and locator discovery
 *   <li> each registration with the lookup discovery service will receive
 *        remote discovery events through an instance of RemoteEventListener
 * </ul><p>
 * 
 * If the lookup discovery service utility functions as specified, then
 * for each discovered lookup service, a <code>RemoteDiscoveryEvent</code>
 * instance indicating a discovered event will be sent to the listener of
 * each registration that requested discovery of the lookup service.
 * Additionally, each event received will accurately reflect the new set
 * of member groups.
 */
public class SetLocatorsReplaceSome extends AbstractBaseTest {

    protected volatile LookupLocator[] newLocatorsToDiscover = new LookupLocator[0];
    protected volatile Map locatorsMap = new HashMap(1);
    protected final HashSet proxiesReplaced = new HashSet(11);

    /** Performs actions necessary to prepare for execution of the 
     *  current test (refer to the description of this method in the
     *  parent class).
     *
     *  Retrieves additional configuration values. 
     */
    public Test construct(QAConfig config) throws Exception {
        super.construct(config);
        useDiscoveryList = getUseGroupAndLocDiscovery0();
        locatorsMap      = getModLocatorsDiscardMap(useDiscoveryList);
        discardType      = ACTIVE_DISCARDED; // want groups and locators
        return this;
    }//end construct

    /** Executes the current test by doing the following:
     * <p>
     *   <ul>
     *     <li> registers with the lookup discovery service, requesting
     *          the discovery of the the desired lookup services using the
     *          desired discovery protocol
     *     <li> verifies that the discovery process is working by waiting
     *          for the expected discovery events
     *     <li> verifies that the lookup discovery service utility under test
     *          sends the expected number of events - containing the expected
     *          set of member groups
     *   </ul>
     */
    public void run() throws Exception {
        setNewLocators(locatorsMap);
        logger.log(Level.FINE, "run()");
        for(int i=0;i<nRegistrations;i++) {
            logger.log(Level.FINE, 
                      "lookup discovery service registration_"+i+" --");
            doRegistration(getGroupsToDiscoverByIndex(i),
                           getLocatorsToDiscoverByIndex(i),
                           i, leaseDuration);
        }//end loop
        waitForDiscovery();
        setLocatorsDo(newLocatorsToDiscover);
        waitForDiscard(discardType);
    }//end run

    /** Common code, shared by this class and its sub-classes, that is 
     *  invoked by the run() method. This method constructs the new
     *  set of locators with which to re-configure the each registration for
     *  discovery.
     */
    void setNewLocators(Map locatorsMap) {
        ArrayList locatorsList = new ArrayList();
        int portBase = 5000;
        Iterator iter = getGenMap().keySet().iterator();
        for(int i=0;iter.hasNext();i++) {
            DiscoveryProtocolSimulator curGen = 
                                       (DiscoveryProtocolSimulator)iter.next();
            String[]         curGroups   = curGen.getMemberGroups();
            ServiceRegistrar lookupProxy = curGen.getLookupProxy();
            if( locatorsMap.containsKey(lookupProxy) ) {
                locatorsList.add(QAConfig.getConstrainedLocator("newHost",(portBase+i)));
                proxiesReplaced.add(lookupProxy);
            } else {
                locatorsList.add( curGen.getLookupLocator() );// no change here
	    }//endif
        }//end loop
        newLocatorsToDiscover = (LookupLocator[])(locatorsList).toArray
                                      (new LookupLocator[locatorsList.size()]);
    }//end setNewLocators

    /** Common code, shared by this class and its sub-classes, that is 
     *  invoked by the run() method. This method invokes the setLocators()
     *  method on each registration.
     */
    void setLocatorsDo(LookupLocator[] newLocators) throws Exception {
        Set eSet = getRegistrationMap().entrySet();
        Iterator iter = eSet.iterator();
        for(int j=0;iter.hasNext();j++) {
            Map.Entry pair = (Map.Entry)iter.next();
            LookupDiscoveryRegistration ldsReg =
                                    (LookupDiscoveryRegistration)pair.getKey();

            LDSEventListener regListener = (LDSEventListener)pair.getValue();
            RegistrationInfo regInfo = regListener.getRegInfo();
            int rID = regInfo.getHandback();
	    logger.log(Level.FINE, 
		       "  registration_"+rID
		       +" -- request discovery of new locators");
	    
	    if((newLocators != null)&&(newLocators.length <= 0)) {
		logger.log(Level.FINE, "   NO_LOCATORS");
	    } else {
		LocatorsUtil.displayLocatorSet(newLocators,
                                               "   newLocator",Level.FINE);
	    }//endif
	    setExpectedDiscardedMap(regInfo);
	    ldsReg.setLocators(newLocators);
        }//end loop(j)
    }//end setLocatorsDo

    void setExpectedDiscardedMap(RegistrationInfo regInfo) {
        Map locMap = getModLocatorsDiscardMap
                              ( getLocatorListToUseByIndex(regInfo.getHandback()) );
        Map expectedMap = getExpectedDiscardedMap(regInfo,discardType);
        Set kSet = expectedMap.keySet();
        Iterator iter = kSet.iterator();
        for(int j=0;iter.hasNext();j++) {
            ServiceRegistrar lookupProxy = (ServiceRegistrar)iter.next();
            if(    !locMap.containsKey(lookupProxy)
                || !proxiesReplaced.contains(lookupProxy)
                || ((regInfo.getLocatorsToDiscover()).length == 0) )
            {
                iter.remove();
	    }//endif
	}//end loop
    }//end setExpectedDiscardedMap

} //end class SetLocatorsReplaceSome

