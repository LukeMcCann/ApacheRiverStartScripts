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
package org.apache.river.test.impl.outrigger.leasing;

import java.util.logging.Level;

// java classes
import java.rmi.*;

// jini classes
import net.jini.core.lease.Lease;
import net.jini.core.entry.Entry;
import net.jini.space.JavaSpace;
import net.jini.core.event.EventRegistration;
import net.jini.core.event.RemoteEventListener;
import net.jini.core.transaction.TransactionException;

// Test harness specific classes
import org.apache.river.qa.harness.TestException;

// Shared classes
import org.apache.river.test.share.TestBase;
import org.apache.river.test.share.UninterestingEntry;

/**
 * Test that registers with a JavaSpace for notification and checks the
 * lease it gets back
 */
public class SpaceNotifyGrantTest extends LeaseGrantTestBase {

    /**
     * Run the test.  See LeaseGrantTestBase for details on the command
     * line arguments are parsed.
     * @see LeaseGrantTestBase#parse
     */
    public void run() throws Exception {
        specifyServices(new Class[] {JavaSpace.class});
        prep(0);
        final Entry entry = new UninterestingEntry();
        EventRegistration reg;

	reg = ((JavaSpace) services[0]).notify(
			entry,
			null,
			new LeasedSpaceListener(getConfig().getConfiguration()),
			durationRequest, null);
	reg = (EventRegistration) 
	    getConfig().prepare("test.outriggerEventRegistrationPreparer", 
				reg);
        resourceRequested();
        Lease lease = reg.getLease();
	lease = (Lease) getConfig().prepare("test.outriggerLeasePreparer", 
					    lease);
        addLease(lease, false);
        logRequest("notification", lease);

        if (!isAcceptable(lease)) {
            throw new TestException(
                    "Got back lease with a improper duration");
        }
    }
}
