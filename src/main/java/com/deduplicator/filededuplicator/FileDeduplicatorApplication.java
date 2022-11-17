package com.deduplicator.filededuplicator;

import com.deduplicator.statics.Statistics;
import com.deduplicator.storage.InMemoryStorage;
import com.deduplicator.walker.FileDeduplicatorImpl;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Provides file deduplication capability given a directory
 */
@Command(name = "File Deduplicator",
		mixinStandardHelpOptions = true,
		version = "File Deduplicator 1.0",
		description = "Removes duplicate files in a given directory.")
public class FileDeduplicatorApplication implements Runnable {

	private static final Set<String> commands = new HashSet<>(
			Arrays.asList("q", "-i", "-V", "--input", "--version", "-h", "--help"));

	@Parameters(index = "0..*")
	String[] params;

	@Option(names = {"q", "quit"})
	boolean optQuit = false;

	@Option(names = {"-i", "--input"},
			description = "Directory to remove duplicates from",
			interactive = true,
			arity = "0")
	private Path directory;

	@Override
	public void run() {
		if (params != null && !commands.contains(params[0])) {
			System.out.println("Command not recognized");
			return;
		}
		if (optQuit) {
			System.exit(0);
		}

		if (directory == null) {
			String dir = System.console().readLine("Enter directory for deduplication: ");
			directory = Path.of(dir);
			System.out.printf("directory=%s%n", dir);
		} else {
			new FileDeduplicatorImpl(new InMemoryStorage()).deduplicate(directory);
			Statistics.printStatistics();
		}
	}

	public static void main(String[] args) {
		displayBanner();
		displayMenu();
		Scanner input = new Scanner(System.in).useDelimiter("\n");
		String shPrompt = "cli> ";
		System.out.print(shPrompt);
		while (true) {
			if (input.hasNext()) {
				String cmd = input.next();
				new CommandLine(new FileDeduplicatorApplication()).execute(cmd);
				System.out.print(shPrompt);
			}
		}
	}

	private static void displayMenu() {
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Options menu:");
		System.out.println("Type -h (--help) to show help");
		System.out.println("Type -V (--version) to show version");
		System.out.println("Type -i (--input) to provide input directory for deduplication");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println();
	}

	private static void displayBanner() {
		System.out.println("______ _ _       ______         _             _ _           _                   ");
		System.out.println("|  ___(_) |      |  _  \\       | |           | (_)         | |                 ");
		System.out.println("| |_   _| | ___  | | | |___  __| |_   _ _ __ | |_  ___ __ _| |_ ___  _ __       ");
		System.out.println("|  _| | | |/ _ \\ | | | / _ \\/ _` | | | | '_ \\| | |/ __/ _` | __/ _ \\| '__|  ");
		System.out.println("| |   | | |  __/ | |/ /  __/ (_| | |_| | |_) | | | (_| (_| | || (_) | |         ");
		System.out.println("\\_|   |_|_|\\___| |___/ \\___|\\__,_|\\__,_| .__/|_|_|\\___\\__,_|\\__\\___/|_|");
		System.out.println("                                       | |                                      ");
		System.out.println("                                       |_|                                      ");
		System.out.println();
		System.out.println();
	}

}
