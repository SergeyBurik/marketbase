package com.marketbase.resources.tools;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipDirectory {
	public static void zipDirs(
			String destinationDir,
			String zipName,
			boolean deleteExisting,
			Predicate<File> filePredicate,
			List<String> sourceDirs) throws IOException {
		System.out.println(sourceDirs);

		File destinationDirFile = new File(destinationDir);
		File zipFile = new File(destinationDir + File.separatorChar + zipName);

		if (!destinationDirFile.exists()) {
			if (!destinationDirFile.mkdirs()) {
				throw new RuntimeException("cannot create directories ");
			}
		} else {
			boolean exists = zipFile.exists();
			if (exists && deleteExisting && !zipFile.delete()) {
				throw new RuntimeException("cannot delete existing zip file: " +
						zipFile.getAbsolutePath());
			} else if (exists && !deleteExisting) {
				System.out.println("Zip file already exists: " +
						zipFile.getAbsolutePath());
				return;
			}
		}

		createZip(zipFile, filePredicate, sourceDirs);
	}

	private static void createZip(File destination, Predicate<File> filePredicate,
								  List<String> sourceDirs) throws IOException {

		if (sourceDirs == null) {
			throw new RuntimeException("Source dirs are null");
		}


		try (ZipOutputStream out = new ZipOutputStream(
				new BufferedOutputStream(new
						FileOutputStream(destination)))) {

			for (String sourceDir : sourceDirs) {
				File sourceDirFile = new File(sourceDir);
				if (!sourceDirFile.exists()) {
					throw new RuntimeException("Source dir doesn't exists "
							+ sourceDirFile);
				}

				addDirRecursively(sourceDirFile.getName(),
						sourceDirFile.getAbsolutePath(),
						sourceDirFile,
						out, filePredicate);
			}
		}
	}

	private static String fileToRelativePath(File file, String baseDir) {
		return file.getAbsolutePath()
				.substring(baseDir.length() + 1);
	}

	private static void addDirRecursively(String baseDirName,
										  String baseDir,
										  File dirFile,
										  final ZipOutputStream out,
										  Predicate<File> filePredicate) throws IOException {


		File[] files = dirFile.listFiles();
		if (files != null) {
			for (File file : files) {
				if (!filePredicate.test(file)) {
					continue;
				}
				if (file.isDirectory()) {
					addDirRecursively(baseDirName, baseDir, file, out, filePredicate);
					continue;
				}

				ZipEntry zipEntry = new ZipEntry(baseDirName + File.separatorChar +
						fileToRelativePath(file, baseDir));
				BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
				zipEntry.setLastModifiedTime(attr.lastModifiedTime());
				zipEntry.setCreationTime(attr.creationTime());
				zipEntry.setLastAccessTime(attr.lastAccessTime());
				zipEntry.setTime(attr.lastModifiedTime().toMillis());

				out.putNextEntry(zipEntry);
				try (BufferedInputStream in = new BufferedInputStream(new
						FileInputStream(file))) {
					byte[] b = new byte[1024];
					int count;
					while ((count = in.read(b)) > 0) {
						out.write(b, 0, count);
					}
					out.closeEntry();
				}
			}
		}
	}
}