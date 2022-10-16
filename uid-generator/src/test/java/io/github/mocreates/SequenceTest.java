/*
 * Copyright 2022-2030 Qimiao.Chen.
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
package io.github.mocreates;

import io.github.mocreates.config.DefaultSequenceConfig;
import io.github.mocreates.config.SequenceConfig;
import io.github.mocreates.config.SimpleSequenceConfig;
import org.junit.Assert;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author Qimiao Chen
 * @since 2022-10-15 16:28
 **/

public class SequenceTest {

    @Test
    public void defaultSequenceTest() {
        DefaultSequenceConfig defaultSequenceConfig = new DefaultSequenceConfig();
        defaultSequenceConfig.setWorkerId(16L);
        Sequence sequence = new Sequence(defaultSequenceConfig);
        long id = sequence.nextId();
        Assert.assertTrue("Id must greater than zero", id > 0);
    }


    @Test
    public void defaultSequenceRepeatVerify() {
        DefaultSequenceConfig defaultSequenceConfig = new DefaultSequenceConfig();
        defaultSequenceConfig.setWorkerId(16L);
        Sequence sequence = new Sequence(defaultSequenceConfig);
        Set<Long> ids = new HashSet<>();
        int count = 1000;
        for (int i = 0; i < count; i++) {
            ids.add(sequence.nextId());
        }
        Assert.assertEquals("Duplicate ID", count, ids.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorWorkerIdDefaultSequenceConfig() {
        DefaultSequenceConfig defaultSequenceConfig = new DefaultSequenceConfig();
        defaultSequenceConfig.setWorkerId(100000000000000L);
        new Sequence(defaultSequenceConfig);
    }


    @Test(expected = IllegalArgumentException.class)
    public void errorBitsDefaultSequenceConfig() {
        DefaultSequenceConfig defaultSequenceConfig = new DefaultSequenceConfig();
        defaultSequenceConfig.setWorkerId(10);
        defaultSequenceConfig.setDatacenterId(10);
        defaultSequenceConfig.setSequenceBits(10);
        new Sequence(defaultSequenceConfig);
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorDatacenterIdDefaultSequenceConfig() {
        DefaultSequenceConfig defaultSequenceConfig = new DefaultSequenceConfig();
        defaultSequenceConfig.setDatacenterId(-1);
        new Sequence(defaultSequenceConfig);
    }

    @Test
    public void manyNodesDefaultSequenceRepeatVerify() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 100, 10,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        Set<Long> ids = Collections.newSetFromMap(new ConcurrentHashMap<>());
        int workerIdLoop = 100;
        final int nextIdLoop = 1000;

        CountDownLatch testThreadLatch = new CountDownLatch(workerIdLoop);
        CountDownLatch execThreadLatch = new CountDownLatch(workerIdLoop);
        for (int workerId = 0; workerId < workerIdLoop; workerId++) {
            DefaultSequenceConfig defaultSequenceConfig = new DefaultSequenceConfig();
            defaultSequenceConfig.setWorkerId(workerId);
            Sequence sequence = new Sequence(defaultSequenceConfig);
            executor.execute(() -> {
                try {
                    execThreadLatch.countDown();
                    execThreadLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < nextIdLoop; i++) {
                    long id = sequence.nextId();
                    ids.add(id);
                }
                testThreadLatch.countDown();
            });
        }

        testThreadLatch.await();
        Assert.assertEquals("Duplicate ID", workerIdLoop * nextIdLoop, ids.size());

    }

    @Test
    public void simpleSequenceTest() {
        SimpleSequenceConfig simpleSequenceConfig = new SimpleSequenceConfig();
        Sequence sequence = new Sequence(simpleSequenceConfig);
        long id = sequence.nextId();
        Assert.assertTrue("Id must greater than zero", id > 0);
    }


    @Test
    public void simpleSequenceTestRepeatVerify() {
        SimpleSequenceConfig simpleSequenceConfig = new SimpleSequenceConfig();
        Sequence sequence = new Sequence(simpleSequenceConfig);
        Set<Long> ids = new HashSet<>();
        int count = 1000;
        for (int i = 0; i < count; i++) {
            ids.add(sequence.nextId());
        }
        Assert.assertEquals("Duplicate ID", count, ids.size());
    }

    @Test
    public void simpleSpecifiedNetInfoSequenceTest() throws UnknownHostException {
        SequenceConfig localHostSequenceConfig = getLocalHostSequenceConfig();
        Sequence sequence = new Sequence(localHostSequenceConfig);
        long id = sequence.nextId();
        Assert.assertTrue("Id must greater than zero", id > 0);
    }


    @Test
    public void simpleSpecifiedNetInfoRepeatVerify() throws UnknownHostException {
        SequenceConfig localHostSequenceConfig = getLocalHostSequenceConfig();
        Sequence sequence = new Sequence(localHostSequenceConfig);
        Set<Long> ids = new HashSet<>();
        int count = 1000;
        for (int i = 0; i < count; i++) {
            ids.add(sequence.nextId());
        }
        Assert.assertEquals("Duplicate ID", count, ids.size());
    }

    private SequenceConfig getLocalHostSequenceConfig() throws UnknownHostException {
        SimpleSequenceConfig simpleSequenceConfig = new SimpleSequenceConfig();
        InetAddress localHost = InetAddress.getLocalHost();
        simpleSequenceConfig.setInetAddress(localHost);
        return simpleSequenceConfig;
    }
}
