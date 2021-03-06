/**
 * Get more info at : www.jrebirth.org  
 * Copyright JRebirth.org © 2011-2013 
 * Contact : sebastien.bordes@jrebirth.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.jrebirth.forge.addon.facets;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.jboss.forge.addon.dependencies.Dependency;
import org.jboss.forge.addon.dependencies.builder.DependencyBuilder;
import org.jboss.forge.addon.facets.AbstractFacet;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFacet;
import org.jboss.forge.addon.projects.dependencies.DependencyInstaller;
import org.jboss.forge.addon.projects.facets.DependencyFacet;

/**
 * 
 * 
 * @author Rajmahendra Hegde <rajmahendra@gmail.com>
 */
public abstract class AbstractJRebirthFacet extends AbstractFacet<Project> implements ProjectFacet {

    protected static final Dependency JREBORTH_DEPENDENCY =
            DependencyBuilder.create().setGroupId("").setArtifactId("").setVersion("");

    private final DependencyInstaller installer;

    @Inject
    public AbstractJRebirthFacet(final DependencyInstaller installer) {
        this.installer = installer;
    }

    abstract protected Map<Dependency, List<Dependency>> getRequiredDependencyOptions();

    @Override
    public boolean install()
    {
      //  findAndinstallDependencies();
        return true;
    }

    @Override
    public boolean isInstalled()
    {
        return false;
    }

    private void findAndinstallDependencies() {
        boolean isInstalled = false;
        DependencyFacet dependencyFacet = origin.getFacet(DependencyFacet.class);
        for (Entry<Dependency, List<Dependency>> group : getRequiredDependencyOptions().entrySet())
        {
            for (Dependency dependency : group.getValue())
            {
                if (dependencyFacet.hasEffectiveDependency(dependency))
                {
                    isInstalled = true;
                    break;
                }
            }
            if (!isInstalled)
            {
                installer.installManaged(origin, JREBORTH_DEPENDENCY);
                installer.install(origin, group.getKey());
            }
        }
    }

}
