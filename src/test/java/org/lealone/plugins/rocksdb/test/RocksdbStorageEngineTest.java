/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.plugins.rocksdb.test;

import java.io.File;

import org.junit.Test;
import org.lealone.db.PluginManager;
import org.lealone.plugins.rocksdb.RocksdbStorageEngine;
import org.lealone.storage.Storage;
import org.lealone.storage.StorageBuilder;
import org.lealone.storage.StorageEngine;
import org.lealone.storage.StorageMap;
import org.lealone.storage.fs.FileUtils;
import org.lealone.test.TestBase;

public class RocksdbStorageEngineTest extends TestBase {

    @Test
    public void run() {
        Storage storage = getStorage(RocksdbStorageEngine.NAME);
        // storage.openMap("test", null).remove();
        testMap(storage);
        testAsyncMap(storage);
    }

    private static void testMap(Storage storage) {
        StorageMap<String, Integer> map = storage.openMap("test", null);
        map.put("a", 100);
        map.put("b", 200);

        Integer v = map.get("a");
        System.out.println(v);

        map.cursor().forEachRemaining(k -> {
            System.out.println(map.get(k));
        });

        map.save();
    }

    private static void testAsyncMap(Storage storage) {
        StorageMap<String, Integer> map = storage.openMap("test", null);
        map.put("c", 300, ac -> {
            System.out.println("Async old value: " + ac.getResult());
        });
        map.put("d", 400, ac -> {
            System.out.println("Async old value: " + ac.getResult());
        });
        Integer v = map.get("c");
        System.out.println(v);

        map.cursor().forEachRemaining(k -> {
            System.out.println(map.get(k));
        });

        map.save();
    }

    private static Storage getStorage(String name) {
        String tmp = "./target/tmp";
        FileUtils.deleteRecursive(tmp, true);
        File tmpDir = new File(tmp); // 存放临时RocksDB动态库文件
        if (!tmpDir.exists())
            tmpDir.mkdir();
        System.setProperty("java.io.tmpdir", tmp);
        StorageEngine se = PluginManager.getPlugin(StorageEngine.class, name);
        se.init(null);
        StorageBuilder builder = se.getStorageBuilder();
        String dir = TEST_BASE_DIR;
        if (!new File(dir).exists())
            new File(dir).mkdirs();
        builder.storagePath(dir);
        Storage storage = builder.openStorage();
        return storage;
    }
}
