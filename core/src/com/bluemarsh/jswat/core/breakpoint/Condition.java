/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is JSwat. The Initial Developer of the Original
 * Software is Nathan L. Fiedler. Portions created by Nathan L. Fiedler
 * are Copyright (C) 2001-2009. All Rights Reserved.
 *
 * Contributor(s): Nathan L. Fiedler.
 *
 * $Id$
 */

package com.bluemarsh.jswat.core.breakpoint;

import com.sun.jdi.event.Event;

/**
 * Interface Condition defines a breakpoint conditional. Conditionals may
 * be added to breakpoints, in which case the breakpoint will not stop
 * the debuggee unless all of its conditions are satisfied.
 *
 * @author  Nathan Fiedler
 */
public interface Condition {

    /**
     * Return a string describing this condition.
     *
     * @return  string description.
     */
    String describe();

    /**
     * Returns true if this condition is satisfied.
     *
     * @param  bp     breakpoint instance.
     * @param  event  event that brought us here.
     * @return  true if satisfied, false otherwise.
     * @throws  ConditionException
     *          if the condition has a problem.
     */
    boolean isSatisfied(Breakpoint bp, Event event) throws ConditionException;

    /**
     * Indicates if this condition is one meant to be seen by the user.
     *
     * @return  true if this condition should be visible to the user.
     */
    boolean isVisible();
}
