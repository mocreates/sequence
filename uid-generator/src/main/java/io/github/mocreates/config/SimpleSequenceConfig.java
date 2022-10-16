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

import java.net.InetAddress;

/**
 * SimpleSequenceConfig
 *
 * @author Qimiao Chen
 * @date 2022-10-15 15:48
 **/
public class SimpleSequenceConfig extends SequenceConfig {

    /**
     * 网络信息，可不指定
     */
    private InetAddress inetAddress;

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    @Override
    public void selfCheck() {
        super.selfCheck();
    }
}
