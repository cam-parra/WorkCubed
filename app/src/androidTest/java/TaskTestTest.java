import android.test.suitebuilder.annotation.SmallTest;

import com.github.workcubed.Task;

import junit.framework.TestCase;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cam on 2/24/16.
 */
public class TaskTestTest extends TestCase {

    @Override
    protected void setUp () throws Exception {
        super.setUp();
    }

//    @SmallTest
    public void testValidYear () throws ParseException{
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        Time time = new Time(10000000);
        Task task = new Task();
        task.add("WorkCubed", "Make the world a better place", time, time, 5, sqlDate, 6);
    }

    @SmallTest
    public void testMYSQLConnection () {

//        boolean result = Task.MYSQLConnect();

//        assertTrue(result);
    }

    @Override
    protected void tearDown () throws Exception {
        super.tearDown();
    }


}