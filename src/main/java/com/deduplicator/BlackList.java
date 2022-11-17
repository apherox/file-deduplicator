package com.deduplicator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Provides list of directories which, if they are provided by the user to deduplicate, they will be omitted
 *
 * @author dimov
 */
public class BlackList {

	private static final Set<String> blackListedDirectories = new HashSet<>(
			Arrays.asList("/var", "/sys", "/etc", "/afs", "boot", "/dev", "/usr", "lost+found", "media", "mnt", "opt",
					"/proc", "/root", "/run", "/sbin", "srv", "C:\\", "C:\\windows", "C:\\Program Files",
					"C:\\system32", "C:\\system"));

	/**
	 * Checks if a given directory name is blacklisted
	 *
	 * @param name of file
	 * @return true if a file's name is blacklisted
	 */
	public static boolean isBlackListed(String name) {
		return blackListedDirectories.contains(name);
	}
}
