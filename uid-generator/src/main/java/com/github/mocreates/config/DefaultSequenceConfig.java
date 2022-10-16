/*
 * Copyright 2022-2030 Qimiao.Chen(https://github.com/chenqimiao)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.mocreates.config;

import com.github.mocreates.util.Assert;

/**
 * 默认的 SequenceConfig 配置方式
 * 显式指定 workId datacenterId
 *
 * @author Qimiao Chen
 * @date 2022-10-15 15:46
 **/
public class DefaultSequenceConfig extends SequenceConfig {

    private long workerId;

    private long datacenterId = 0L;

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public long getDatacenterId() {
        return datacenterId;
    }

    public void setDatacenterId(long datacenterId) {
        this.datacenterId = datacenterId;
    }

    @Override
    public void selfCheck() {
        super.selfCheck();
        Assert.isTrue(workerId >= 0, "worker Id must greater or equal to zero");
        Assert.isTrue(datacenterId >= 0, "datacenter Id must greater or equal to zero");
    }
}
