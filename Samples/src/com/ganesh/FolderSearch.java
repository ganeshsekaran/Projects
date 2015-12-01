package com.ganesh;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class FolderSearch {

	public interface SearchStatus {
		public String[] getList();

		public void printFiles();
	}

	public static void main(String[] args) throws Exception {
		FolderSearch main = new FolderSearch();
		String baseFolder = "D:\\personal";
		SearchStatus status = main.searchFiles(baseFolder);

		String[] fileNames = status.getList();
		/*for (String name : fileNames) {
			System.out.println(name);
		}
*/
		System.out.println("Total Count : " + fileNames.length);
	}

	public SearchStatus searchFiles(String baseFolder) throws Exception {
		SearchStatusImpl status = new SearchStatusImpl();

		File file = new File(baseFolder);
		File[] files = file.listFiles();
		searchFiles(files, status);
		return status;
	}

	private void searchFiles(File[] files, SearchStatusImpl status)
			throws Exception {
		Runnable runnable = new FileSearch(files, status);
		Thread t = new Thread(runnable);
		t.start();
		t.join();
	}

	class FileSearch implements Runnable {

		private final File[] files;
		private final SearchStatusImpl status;

		FileSearch(File[] files, SearchStatusImpl status) {
			this.files = files;
			this.status = status;
		}

		public void run() {
			try {
				search();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void search() throws Exception {
			for (File file : files) {

				synchronized (status) {
					String fileName = file.getCanonicalPath();
					status.fileList.add(fileName);
					status.notify();
					System.out.println(fileName);
				}

				if (file.isDirectory()) {
					File[] newFiles = file.listFiles();
					searchFiles(newFiles, status);
				}
			}
		}
	}

	class ThreadPool {
		private final BlockingQueue<String> queue;
		private final ThreadRunnableBean[] threads;

		ThreadPool(int poolSize) {
			queue = new ArrayBlockingQueue<String>(poolSize);
			threads = new ThreadRunnableBean[poolSize];
			initThreads(poolSize);
		}

		private final void initThreads(int poolSize) {
			for (int i = 0; i < poolSize; i++) {
				ThreadRunner runner = new ThreadRunner(queue);
				Thread thread = new Thread(runner);
				threads[i] = new ThreadRunnableBean(thread, runner);
			}
		}
	}

	class ThreadRunner implements Runnable {
		private final BlockingQueue<String> queue;

		ThreadRunner(BlockingQueue<String> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			while (true) {
				search();
			}
		}

		private void search() {

		}
	}

	class ThreadRunnableBean {
		private final Thread thread;
		private final Runnable runnable;

		ThreadRunnableBean(Thread thread, Runnable runnable) {
			this.thread = thread;
			this.runnable = runnable;
		}

		Thread getThread() {
			return thread;
		}

		Runnable getRunnable() {
			return runnable;
		}
	}

	class SearchStatusImpl implements SearchStatus {

		private final List<String> fileList;
		
		SearchStatusImpl() {
			fileList = new ArrayList<String>();
		}

		@Override
		public String[] getList() {
			String[] files = new String[fileList.size()];
			synchronized (this) {
				fileList.toArray(files);
			}
			return files;
		}

		@Override
		public void printFiles() {

		}
	}
}
