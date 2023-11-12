/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.plugins.rocksdb;

import org.lealone.storage.StorageBuilder;

public class RocksdbStorageBuilder extends StorageBuilder {
    @Override
    public RocksdbStorage openStorage() {
        return new RocksdbStorage(config);
    }
}
