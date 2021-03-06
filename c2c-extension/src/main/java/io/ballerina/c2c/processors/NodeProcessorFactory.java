/*
 * Copyright (c) 2020, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.ballerina.c2c.processors;

import io.ballerina.c2c.exceptions.KubernetesPluginException;
import io.ballerina.c2c.models.KubernetesContext;

/**
 * Annotation processor factory.
 */
public class NodeProcessorFactory {

    public static NodeProcessor getNodeProcessorInstance(String type) throws KubernetesPluginException {
        // set can process to true so that this value can be accessed from code generated method.
        KubernetesContext.getInstance().getDataHolder().setCanProcess(true);
        KubernetesAnnotation kubernetesAnnotation = KubernetesAnnotation.valueOf(type);
        switch (kubernetesAnnotation) {
            case Service:
                return new ServiceNodeProcessor();
            case HPA:
                return new HPANodeProcessor();
            case Deployment:
                return new DeploymentNodeProcessor();
            case Task:
            case Job:
                return new JobNodeProcessor();
            default:
                KubernetesContext.getInstance().getDataHolder().setCanProcess(false);
                throw new KubernetesPluginException("error while getting annotation processor for type: " + type);
        }
    }

    private enum KubernetesAnnotation {
        Service,
        HPA,
        Deployment,
        Task,
        Job
    }
}
