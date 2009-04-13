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
 * are Copyright (C) 2009. All Rights Reserved.
 *
 * Contributor(s): Nathan L. Fiedler.
 *
 * $Id$
 */

package com.bluemarsh.jswat.nbcore;

import com.bluemarsh.jswat.core.PlatformService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.Repository;
import org.openide.util.NbPreferences;

/**
 * Provides an implementation of PlatformService that operates within
 * the NetBeans Platform.
 *
 * @author Nathan Fiedler
 */
public class NetBeansPlatformService implements PlatformService {
    /** Map of locks for file names. */
    private Map<String, FileLock> locks;

    /**
     * Creates a new instance of NetBeansPlatformService.
     */
    public NetBeansPlatformService() {
        locks = new HashMap<String, FileLock>();
    }

    @Override
    public void deleteFile(String name) throws IOException {
        FileSystem fs = Repository.getDefault().getDefaultFileSystem();
        FileObject fo = fs.findResource(name);
        if (fo != null && fo.isData()) {
            fo.delete();
        }
    }

    @Override
    public Preferences getPreferences(Class clazz) {
        return NbPreferences.forModule(clazz);
    }

    @Override
    public InputStream readFile(String name) throws FileNotFoundException {
        FileSystem fs = Repository.getDefault().getDefaultFileSystem();
        FileObject fo = fs.findResource(name);
        if (fo != null && fo.isData()) {
            return fo.getInputStream();
        }
        throw new FileNotFoundException(name);
    }

    @Override
    public void releaseLock(String name) {
        FileLock lock = locks.remove(name);
        if (lock != null) {
            lock.releaseLock();
        }
    }

    @Override
    public OutputStream writeFile(String name) throws IOException {
        FileSystem fs = Repository.getDefault().getDefaultFileSystem();
        FileObject fo = fs.findResource(name);
        if (fo == null) {
            fo = fs.getRoot().createData(name);
        }
        FileLock lock = fo.lock();
        locks.put(name, lock);
        return fo.getOutputStream(lock);
    }
}