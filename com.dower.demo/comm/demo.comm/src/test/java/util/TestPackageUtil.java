package util;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dower.demo.comm.basedao.IBaseDao;
import com.dower.demo.comm.util.PackageUtil;

public class TestPackageUtil {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetAllClassByInterface() {
		System.out.println(PackageUtil.getAllClassByInterface(IBaseDao.class));
	}

	@Test
	public void testGetClasses() {
			
		List names = PackageUtil.getClasses("com.ihandy.ucp.comm.util");
		for (Object object : names) {
			System.out.println(object.toString());
		}
	}

	@Test
	public void testFindAndAddClassesInPackageByFile() {
		
		//PackageUtil.findAndAddClassesInPackageByFile(packageName, packagePath, recursive, classes)
	}

}
