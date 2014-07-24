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
	private ZipOutputStream zipOut; // ѹ��Zip
	private byte[] buf;
	private int readedBytes;
	private String basepath;

	public ZipUtils() {
	}

	/**
	 * ѹ��Ŀ¼
	 * 
	 * @param dir
	 *            ��Ҫѹ����Ŀ¼
	 */
	public void doZip(String dir) {
		// ��¼����Ŀ¼
		basepath = dir;
		File zipDir = new File(dir);
		// ѹ�������ɵ�zip�ļ���
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
	 * �ݹ�ѹ��
	 * 
	 * @param dir
	 * @param zipOut
	 * @throws IOException
	 */
	private void recursionPack(File dir, ZipOutputStream zipOut) throws IOException {
		FileInputStream fileIn;
		File[] files = dir.listFiles();
		if (files.length == 0) {// ���Ŀ¼Ϊ��,�򵥶�����֮.
			// ZipEntry��isDirectory()������,Ŀ¼��"/"��β.
			this.zipOut.putNextEntry(new ZipEntry(dir.getName() + "/"));
			this.zipOut.closeEntry();
		} else {// ���Ŀ¼��Ϊ��,��ֱ���Ŀ¼���ļ�.
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
	 * ��ѹ�ļ�
	 * 
	 * @param unZipfileName
	 */
	@SuppressWarnings("unchecked")
	public void unZip(String unZipfileName) {// unZipfileName��Ҫ��ѹ��zip�ļ���
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
					// ���ָ���ļ���Ŀ¼������,�򴴽�֮.
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