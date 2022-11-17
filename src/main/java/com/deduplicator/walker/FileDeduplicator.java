package com.deduplicator.walker;

import java.nio.file.Path;

/**
 * @author dimov
 */
@FunctionalInterface
public interface FileDeduplicator {

	void deduplicate(Path path);

}
