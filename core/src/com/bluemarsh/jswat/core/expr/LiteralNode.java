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
 * The Original Software is the JSwat Core Module. The Initial Developer of the
 * Software is Nathan L. Fiedler. Portions created by Nathan L. Fiedler
 * are Copyright (C) 2002-2010. All Rights Reserved.
 *
 * Contributor(s): Nathan L. Fiedler.
 *
 * $Id$
 */
package com.bluemarsh.jswat.core.expr;

import com.bluemarsh.jswat.parser.node.Token;
import com.bluemarsh.jswat.core.util.Types;

/**
 * Class LiteralNode represents a literal value, possibly a number,
 * string, boolean, or null value.
 *
 * @author  Nathan Fiedler
 */
class LiteralNode extends AbstractNode implements JoinableNode {

    /** Literal value of this literal node. */
    private Object literalValue;

    /**
     * Constructs a LiteralNode with the given literal value.
     *
     * @param  node  lexical token.
     * @param  lit   literal value.
     */
    LiteralNode(Token node, Object lit) {
        super(node);
        literalValue = lit;
    }

    @Override
    protected Object eval(EvaluationContext context)
            throws EvaluationException {

        return literalValue;
    }

    @Override
    protected String type(EvaluationContext context)
            throws EvaluationException {

        if (literalValue == null) {
            return null;
        } else {
            String type = Types.nameToJni(
                    literalValue.getClass().getName());
            String primitive = Types.wrapperToPrimitive(type);
            if (primitive.length() > 0) {
                type = primitive;
            }
            return type;
        }
    }
}
