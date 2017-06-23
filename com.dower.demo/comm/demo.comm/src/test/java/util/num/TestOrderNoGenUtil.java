package util.num;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.util.num.OrderNoGenUtil;

public class TestOrderNoGenUtil {
	final static List t = new ArrayList();
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				String preOrderNo = "F011120150126";
				for (int i = 0; i < 10; i++) {
					String st = "tread1   " + OrderNoGenUtil.genOrderNo(preOrderNo);
					System.out.println(st);
					t.add(st);
				}
				// System.out.println(t.size());
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				String preOrderNo = "F021120150126";
				for (int i = 0; i < 10; i++) {

					String st = "tread2   " + OrderNoGenUtil.genOrderNo(preOrderNo);
					System.out.println(st);
					t.add(st);
				}

				// System.out.println(t.size());
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				String preOrderNo = "F031120150127";
				for (int i = 0; i < 10; i++) {

					String st = "tread3   " + OrderNoGenUtil.genOrderNo(preOrderNo);
					System.out.println(st);
					t.add(st);
				}

				// System.out.println(t.size());
			}
		}).start();
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGenOrderNo() {
		
		for (int i = 0; i < t.size(); i++) {
			System.out.println(t.get(i));
		}
		
	}

}
