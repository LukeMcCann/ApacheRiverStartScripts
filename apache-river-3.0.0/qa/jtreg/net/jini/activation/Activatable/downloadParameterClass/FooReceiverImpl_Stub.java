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
// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

public final class FooReceiverImpl_Stub
    extends java.rmi.server.RemoteStub
    implements DownloadParameterClass. FooReceiver
{
    private static final long serialVersionUID = 2;
    
    private static java.lang.reflect.Method $method_receiveFoo_0;
    
    static {
	try {
	    $method_receiveFoo_0 = DownloadParameterClass. FooReceiver.class.getMethod("receiveFoo", new java.lang.Class[] {java.lang.Object.class});
	} catch (java.lang.NoSuchMethodException e) {
	    throw new java.lang.NoSuchMethodError(
		"stub class initialization failed");
	}
    }
    
    // constructors
    public FooReceiverImpl_Stub(java.rmi.server.RemoteRef ref) {
	super(ref);
    }
    
    // methods from remote interfaces
    
    // implementation of receiveFoo(Object)
    public void receiveFoo(java.lang.Object $param_Object_1)
	throws java.rmi.RemoteException
    {
	try {
	    ref.invoke(this, $method_receiveFoo_0, new java.lang.Object[] {$param_Object_1}, -1548895758515635945L);
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
}
