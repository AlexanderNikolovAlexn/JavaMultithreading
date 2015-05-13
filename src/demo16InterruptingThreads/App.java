package demo16InterruptingThreads;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class App {

	public static void main(String[] args) {
		
		System.out.println("Starting.");
		
		ExecutorService exec = Executors.newCachedThreadPool();
		
		Future<?> fu = exec.submit(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				Random random = new Random();
				for (int i = 0; i < 1E8; i++) {
					if(Thread.currentThread().isInterrupted()){
						System.out.println("Interrupted!");
						break;
					}
					Math.sin(random.nextDouble());
				};
				
				return null;
			}
		});
		
		exec.shutdown();
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		exec.shutdownNow();
		//fu.cancel(true);
		
		try {
			exec.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Finished.");

	}

}
