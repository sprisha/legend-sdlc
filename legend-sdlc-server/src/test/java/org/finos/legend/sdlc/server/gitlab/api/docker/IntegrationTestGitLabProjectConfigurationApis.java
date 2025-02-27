// Copyright 2021 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.sdlc.server.gitlab.api.docker;

import org.finos.legend.sdlc.server.gitlab.GitLabConfiguration;
import org.finos.legend.sdlc.server.gitlab.api.GitLabPatchApi;
import org.finos.legend.sdlc.server.gitlab.api.GitLabProjectApi;
import org.finos.legend.sdlc.server.gitlab.api.GitLabProjectConfigurationApi;
import org.finos.legend.sdlc.server.gitlab.api.GitLabProjectConfigurationApiTestResource;
import org.finos.legend.sdlc.server.gitlab.api.GitLabRevisionApi;
import org.finos.legend.sdlc.server.gitlab.api.GitLabVersionApi;
import org.finos.legend.sdlc.server.gitlab.api.GitLabWorkspaceApi;
import org.finos.legend.sdlc.server.gitlab.auth.GitLabUserContext;
import org.finos.legend.sdlc.server.project.config.ProjectStructureConfiguration;
import org.gitlab4j.api.GitLabApiException;
import org.junit.BeforeClass;
import org.junit.Test;

public class IntegrationTestGitLabProjectConfigurationApis extends AbstractGitLabApiTest
{
    private static GitLabProjectConfigurationApiTestResource gitLabProjectConfigurationApiTestResource;

    @BeforeClass
    public static void setup() throws GitLabApiException
    {
        setUpProjectConfigurationApi();
    }

    @Test
    public void testUserAndGroupWorkspaceProjectConfiguration()
    {
        gitLabProjectConfigurationApiTestResource.runUserAndGroupWorkspaceProjectConfigurationTest();
    }

    @Test
    public void testUserAndGroupWorkspaceProjectConfigurationForPatchReleaseVersion()
    {
        gitLabProjectConfigurationApiTestResource.runUserAndGroupWorkspaceProjectConfigurationTestForPatchReleaseVersion();
    }

    @Test
    public void testProjectVersionProjectConfiguration()
    {
        gitLabProjectConfigurationApiTestResource.runProjectVersionProjectConfigurationTest();
    }

    /**
     * Authenticates with OAuth2 and instantiate the test resource.
     */
    private static void setUpProjectConfigurationApi()
    {
        GitLabUserContext gitLabMemberUserContext = prepareGitLabMemberUserContext();
        GitLabUserContext gitLabOwnerUserContext = prepareGitLabOwnerUserContext();
        GitLabConfiguration gitLabConfig = GitLabConfiguration.newGitLabConfiguration(null, null, null, null, null, null);
        ProjectStructureConfiguration projectStructureConfig = ProjectStructureConfiguration.emptyConfiguration();

        GitLabProjectApi gitLabProjectApi = new GitLabProjectApi(gitLabConfig, gitLabOwnerUserContext, projectStructureConfig, null, backgroundTaskProcessor, null);
        GitLabRevisionApi gitLabRevisionApi = new GitLabRevisionApi(gitLabConfig, gitLabMemberUserContext, backgroundTaskProcessor);
        GitLabWorkspaceApi gitLabWorkspaceApi = new GitLabWorkspaceApi(gitLabConfig, gitLabMemberUserContext, gitLabRevisionApi, backgroundTaskProcessor);
        GitLabProjectConfigurationApi gitLabProjectConfigurationApi = new GitLabProjectConfigurationApi(gitLabConfig, gitLabMemberUserContext, projectStructureConfig.getProjectStructureExtensionProvider(), backgroundTaskProcessor, null);
        GitLabPatchApi gitLabPatchApi = new GitLabPatchApi(gitLabConfig, gitLabMemberUserContext, backgroundTaskProcessor);
        GitLabVersionApi gitLabVersionApi = new GitLabVersionApi(gitLabConfig, gitLabMemberUserContext, backgroundTaskProcessor);

        gitLabProjectConfigurationApiTestResource = new GitLabProjectConfigurationApiTestResource(gitLabWorkspaceApi, gitLabProjectApi, gitLabProjectConfigurationApi, gitLabPatchApi, gitLabVersionApi, gitLabRevisionApi);
    }
}
