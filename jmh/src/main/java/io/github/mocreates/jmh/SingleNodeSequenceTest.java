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
package io.github.mocreates.jmh;

import io.github.mocreates.Sequence;
import io.github.mocreates.config.DefaultSequenceConfig;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

/**
 * @author Qimiao Chen
 * @since 2022-10-15 17:39
 **/
@BenchmarkMode(Mode.Throughput)
@Threads(10)
@Warmup(iterations = 3, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@State(value = Scope.Benchmark)
@Fork(1)
@OutputTimeUnit(TimeUnit.SECONDS)
public class SingleNodeSequenceTest {

    private static final DefaultSequenceConfig SEQUENCE_CONFIG = new DefaultSequenceConfig();

    static {
        SEQUENCE_CONFIG.setSequenceBits(22);
        SEQUENCE_CONFIG.setWorkerIdBits(0);
        SEQUENCE_CONFIG.setDatacenterIdBits(0);
        SEQUENCE_CONFIG.setTwepoch(System.currentTimeMillis());

        SEQUENCE_CONFIG.setWorkerId(0L);
        SEQUENCE_CONFIG.setDatacenterId(0L);
    }

    private static final Sequence SEQUENCE = new Sequence(SEQUENCE_CONFIG);


    @Benchmark
    public void nextIdTest(Blackhole blackhole) {

        blackhole.consume(SEQUENCE.nextId());
    }
}
