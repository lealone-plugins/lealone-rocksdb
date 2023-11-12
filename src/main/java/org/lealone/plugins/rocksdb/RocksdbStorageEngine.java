/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.plugins.rocksdb;

import java.util.Map;

import org.lealone.db.DataHandler;
import org.lealone.storage.Storage;
import org.lealone.storage.StorageBuilder;
import org.lealone.storage.StorageEngineBase;
import org.lealone.storage.lob.LobStorage;
import org.rocksdb.RocksDB;

public class RocksdbStorageEngine extends StorageEngineBase {

    public static final String NAME = "RocksDB";

    public RocksdbStorageEngine() {
        super(NAME);
    }

    @Override
    public StorageBuilder getStorageBuilder() {
        return new RocksdbStorageBuilder();
    }

    @Override
    public LobStorage getLobStorage(DataHandler dataHandler, Storage storage) {
        return null;
    }

    @Override
    public void init(Map<String, String> config) {
        RocksDB.loadLibrary();
        super.init(config);
    }
}
