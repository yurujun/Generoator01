package com.yzr.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


public class ZipUtils {
	private ZipFile zipFile;
	private ZipOutputStream zipOut; // 压缩Zip
	private byte[] buf;
	private int readedBytes;
	private String basepath;

	public ZipUtils() {
	}

	/**
	 * 压缩目录
	 * 
	 * @param dir
	 *            需要压缩的目录
	 */
	public void doZip(String dir) {
		// 记录传入目录
		basepath = dir;
		File zipDir = new File(dir);
		// 压缩后生成的zip文件名
		String fileName = zipDir.getName() + ".zip";
		try {
			this.zipOut = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipDir.getParent() + "/" + fileName)));
			recursionPack(zipDir, this.zipOut);
			this.zipOut.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * 递归压缩
	 * 
	 * @param dir
	 * @param zipOut
	 * @throws IOException
	 */
	private void recursionPack(File dir, ZipOutputStream zipOut) throws IOException {
		FileInputStream fileIn;
		File[] files = dir.listFiles();
		if (files.length == 0) {// 如果目录为空,则单独创建之.
			// ZipEntry的isDirectory()方法中,目录以"/"结尾.
			this.zipOut.putNextEntry(new ZipEntry(dir.getName() + "/"));
			this.zipOut.closeEntry();
		} else {// 如果目录不为空,则分别处理目录和文件.
			for (File fileName : files) {
				if (fileName.isDirectory()) {
					recursionPack(fileName, this.zipOut);
				} else {
					fileIn = new FileInputStream(fileName);
					String name = fileName.toString().substring(basepath.length() + 1);
					this.zipOut.putNextEntry(new ZipEntry(name));

					while ((this.readedBytes = fileIn.read(this.buf)) > 0) {
						this.zipOut.write(this.buf, 0, this.readedBytes);
					}
					fileIn.close();
					this.zipOut.closeEntry();
				}
			}
		}
	}

	/**
	 * 解压文件
	 * 
	 * @param unZipfileName
	 */
	@SuppressWarnings("unchecked")
	public void unZip(String unZipfileName) {// unZipfileName需要解压的zip文件名
		FileOutputStream fileOut;
		File file;
		InputStream inputStream;
		try {
			this.zipFile = new ZipFile(unZipfileName);

			for (Enumeration entries = this.zipFile.entries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				file = new File(entry.getName());
				if (entry.isDirectory()) {
					file.mkdirs();
				} else {
					// 如果指定文件的目录不存在,则创建之.
					File parent = file.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}
					inputStream = zipFile.getInputStream(entry);
					fileOut = new FileOutputStream(file);
					while ((this.readedBytes = inputStream.read(this.buf)) > 0) {
						fileOut.write(this.buf, 0, this.readedBytes);
					}
					fileOut.close();
					inputStream.close();
				}
			}
			this.zipFile.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}