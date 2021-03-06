/*
 * *****************************************************************************
 *
 *  Pentaho Data Integration
 *
 *  Copyright (C) 2017 by Pentaho : http://www.pentaho.com
 *
 *  *******************************************************************************
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 *  this file except in compliance with the License. You may obtain a copy of the
 *  License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 * *****************************************************************************
 *
 */

package org.pentaho.di.engine.configuration.impl.pentaho.scheduler;

import org.pentaho.di.base.AbstractMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.ui.spoon.Spoon;

import java.lang.reflect.Method;
import java.util.function.Supplier;

/**
 * Created by bmorrise on 8/22/17.
 */
public class SpoonUtil {

  public static Supplier<Spoon> spoonSupplier = Spoon::getInstance;

  public static String getRepositoryServiceBaseUrl() {
    String repoLocation = "http://localhost:8080/pentaho"; //$NON-NLS-1$

    try {
      Repository repository = spoonSupplier.get().getRepository();
      Method m = repository.getRepositoryMeta().getClass().getMethod( "getRepositoryLocation" );
      Object loc = m.invoke( repository.getRepositoryMeta() );
      m = loc.getClass().getMethod( "getUrl" );
      repoLocation = (String) m.invoke( loc );
    } catch ( Exception ex ) {
      // Ignore
    }
    return repoLocation;
  }

  public static String getUsername() {
    return spoonSupplier.get().getRepository().getUserInfo().getName();
  }

  public static String getPassword() {
    return spoonSupplier.get().getRepository().getUserInfo().getPassword();
  }

  public static String getFullPath( AbstractMeta meta ) {
    return meta.getRepositoryDirectory().getPath() + "/" + meta.getName() + "." + meta.getDefaultExtension();
  }

  public static boolean isConnected() {
    return spoonSupplier.get().getRepository() != null;
  }
}
