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
package org.apache.river.test.impl.end2end.e2etest;

import javax.security.auth.Subject;

import net.jini.core.constraint.ClientMinPrincipal;
import net.jini.core.constraint.ClientMinPrincipalType;
import net.jini.core.constraint.ClientMaxPrincipal;
import net.jini.core.constraint.ConstraintAlternatives;
import net.jini.core.constraint.ServerMinPrincipal;

public interface SubjectProvider {

    public Subject getClientSubject();

    public Subject getServerSubject();

    public Subject getSubject();

    public ClientMinPrincipal getClientMinPrincipal();

    public ClientMinPrincipalType getClientMinPrincipalType();

    public ClientMaxPrincipal getClientMaxPrincipal();

    public ConstraintAlternatives getConstraintAlternatives1();

    public ConstraintAlternatives getConstraintAlternatives2();

    public ConstraintAlternatives getServerMinPrincipal();

    public ServerMinPrincipal getServerMainPrincipal();

}
