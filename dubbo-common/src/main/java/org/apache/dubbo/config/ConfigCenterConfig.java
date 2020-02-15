/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.config;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.common.utils.UrlUtils;
import org.apache.dubbo.config.support.Parameter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.apache.dubbo.common.constants.CommonConstants.ANYHOST_VALUE;
import static org.apache.dubbo.common.constants.CommonConstants.CONFIG_CONFIGFILE_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.CONFIG_ENABLE_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.PATH_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.PROTOCOL_KEY;
import static org.apache.dubbo.config.Constants.CONFIG_APP_CONFIGFILE_KEY;
import static org.apache.dubbo.config.Constants.ZOOKEEPER_PROTOCOL;

/**
 * ConfigCenterConfig
 */
public class ConfigCenterConfig extends AbstractConfig {
    private AtomicBoolean inited = new AtomicBoolean(false);

    private String protocol;
    private String address;

    /* The config center cluster, it's real meaning may very on different Config Center products. */
    // 配置中心集群，它的实际意义可能很大程度上取决于不同的配置中心产品。
    private String cluster;

    /* The namespace of the config center, generally it's used for multi-tenant,
    but it's real meaning depends on the actual Config Center you use.
    */
    // 配置中心的名称空间，通常用于多租户，但它的实际意义取决于您使用的实际配置中心。
    private String namespace = CommonConstants.DUBBO;
    /* The group of the config center, generally it's used to identify an isolated space for a batch of config items,
    but it's real meaning depends on the actual Config Center you use.
    */
    // 配置中心组，通常用于为一批配置项标识一个独立的空间，但它的实际意义取决于您使用的实际配置中心。
    private String group = CommonConstants.DUBBO;
    private String username;
    private String password;
    private Long timeout = 3000L;

    // If the Config Center is given the highest priority, it will override all the other configurations
    // 如果配置中心被赋予最高优先级，它将覆盖所有其他配置
    private Boolean highestPriority = true;

    // Decide the behaviour when initial connection try fails, 'true' means interrupt the whole process once fail.
    // 确定初始连接尝试失败时的行为，“true”表示一旦失败就中断整个进程。
    private Boolean check = true;

    /* Used to specify the key that your properties file mapping to, most of the time you do not need to change this parameter.
    Notice that for Apollo, this parameter is meaningless, set the 'namespace' is enough.
    */
    // 用于指定属性文件映射到的键，大多数情况下不需要更改此参数。注意，对于Apollo，这个参数没有意义，设置“名称空间”就足够了。
    private String configFile = CommonConstants.DEFAULT_DUBBO_PROPERTIES;

    /* the .properties file under 'configFile' is global shared while .properties under this one is limited only to this application
    */
    // configFile下的.properties文件是全局共享的，而这个文件下的.properties文件仅限于这个应用程序
    private String appConfigFile;

    /* If the Config Center product you use have some special parameters that is not covered by this class, you can add it to here.
    For example, with XML:
      <dubbo:config-center>
           <dubbo:parameter key="{your key}" value="{your value}" />
      </dubbo:config-center>
     */
    // 如果您使用的配置中心产品有一些特殊的参数没有被这个类覆盖，您可以将它添加到这里。
    private Map<String, String> parameters;

    private Map<String, String> externalConfiguration;

    private Map<String, String> appExternalConfiguration;

    public ConfigCenterConfig() {
    }

    public URL toUrl() {
        Map<String, String> map = new HashMap<>();
        appendParameters(map, this);
        if (StringUtils.isEmpty(address)) {
            address = ANYHOST_VALUE;
        }
        map.put(PATH_KEY, ConfigCenterConfig.class.getSimpleName());
        // use 'zookeeper' as the default configcenter.
        if (StringUtils.isEmpty(map.get(PROTOCOL_KEY))) {
            map.put(PROTOCOL_KEY, ZOOKEEPER_PROTOCOL);
        }
        return UrlUtils.parseURL(address, map);
    }

    public boolean checkOrUpdateInited() {
        return inited.compareAndSet(false, true);
    }

    public Map<String, String> getExternalConfiguration() {
        return externalConfiguration;
    }

    public Map<String, String> getAppExternalConfiguration() {
        return appExternalConfiguration;
    }

    public void setExternalConfig(Map<String, String> externalConfiguration) {
        this.externalConfiguration = externalConfiguration;
    }

    public void setAppExternalConfig(Map<String, String> appExternalConfiguration) {
        this.appExternalConfiguration = appExternalConfiguration;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Parameter(excluded = true)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Boolean isCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    @Parameter(key = CONFIG_ENABLE_KEY)
    public Boolean isHighestPriority() {
        return highestPriority;
    }

    public void setHighestPriority(Boolean highestPriority) {
        this.highestPriority = highestPriority;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    @Parameter(key = CONFIG_CONFIGFILE_KEY)
    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    @Parameter(excluded = true, key = CONFIG_APP_CONFIGFILE_KEY)
    public String getAppConfigFile() {
        return appConfigFile;
    }

    public void setAppConfigFile(String appConfigFile) {
        this.appConfigFile = appConfigFile;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    @Parameter(excluded = true)
    public boolean isValid() {
        if (StringUtils.isEmpty(address)) {
            return false;
        }

        return address.contains("://") || StringUtils.isNotEmpty(protocol);
    }
}
