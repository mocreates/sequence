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
package io.github.mocreates.config;

import io.github.mocreates.util.Assert;

/**
 * SequenceConfig
 *
 * @author Qimiao Chen
 * @since 2022-10-15 15:08
 **/
public abstract class SequenceConfig {

    /**
     * 可以被设置为最接近项目启用前的某个时间点（unix 时间戳)
     */
    public long twepoch = 1665817757000L;

    /**
     * 机器位所占的bit位数
     */
    private long workerIdBits = 19L;

    /**
     * 数据标识位所占的bit位数
     */
    private long datacenterIdBits = 0L;

    /**
     * 毫秒内自增位数
     */
    private long sequenceBits = 3L;

    public long getTwepoch() {
        return twepoch;
    }

    public void setTwepoch(long twepoch) {
        this.twepoch = twepoch;
    }

    public long getWorkerIdBits() {
        return workerIdBits;
    }

    public void setWorkerIdBits(long workerIdBits) {
        this.workerIdBits = workerIdBits;
    }

    public long getDatacenterIdBits() {
        return datacenterIdBits;
    }

    public void setDatacenterIdBits(long datacenterIdBits) {
        this.datacenterIdBits = datacenterIdBits;
    }

    public long getSequenceBits() {
        return sequenceBits;
    }

    public void setSequenceBits(long sequenceBits) {
        this.sequenceBits = sequenceBits;
    }

    /**
     * 自检
     */
    public void selfCheck() {

        Assert.isTrue(workerIdBits >= 0, "workerIdBits must must greater or equal zero");

        Assert.isTrue(datacenterIdBits >= 0, "datacenterIdBits must greater or equal to zero");

        Assert.isTrue(sequenceBits >= 0, "sequenceBits must greater or equal to zero");

        Assert.isTrue((workerIdBits + datacenterIdBits + sequenceBits) == 22L,
                "The sum of 'workerIdBits', 'datacenterIdBits', 'sequenceBits' must be equal to 22");


    }
}
