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

public final class TestActivatableExport_Stub
    extends java.rmi.server.RemoteStub
    implements Echo
{
    private static final long serialVersionUID = 2;
    
    private static java.lang.reflect.Method $method_echo_0;
    private static java.lang.reflect.Method $method_shutdown_1;
    
    static {
	try {
	    $method_echo_0 = Echo.class.getMethod("echo", new java.lang.Class[] {int.class});
	    $method_shutdown_1 = Echo.class.getMethod("shutdown", new java.lang.Class[] {});
	} catch (java.lang.NoSuchMethodException e) {
	    throw new java.lang.NoSuchMethodError(
		"stub class initialization failed");
	}
    }
    
    // constructors
    public TestActivatableExport_Stub(java.rmi.server.RemoteRef ref) {
	super(ref);
    }
    
    // methods from remote interfaces
    
    // implementation of echo(int)
    public int echo(int $param_int_1)
	throws java.rmi.RemoteException
    {
	try {
	    Object $result = ref.invoke(this, $method_echo_0, new java.lang.Object[] {new java.lang.Integer($param_int_1)}, 3974643609339341598L);
	    return ((java.lang.Integer) $result).intValue();
	} catch (java.lang.RuntimeException e) {
	    throw e;
	} catch (java.rmi.RemoteException e) {
	    throw e;
	} catch (java.lang.Exception e) {
	    throw new java.rmi.UnexpectedException("undeclared checked exception", e);
	}
    }
    
    // implementation of shutdown()
    public void shutdown()
	throws java.lang.Exception
    {
	ref.invoke(this, $method_shutdown_1, null, -7207851917985848402L);
    }
}