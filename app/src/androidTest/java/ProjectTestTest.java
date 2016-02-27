import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

/**
 * Created by cam on 2/24/16.
 */
public class ProjectTestTest extends TestCase {

    public int currentYear = 2016;

    @Override
    protected void setUp () throws Exception {
        super.setUp();
    }

    @SmallTest
    public void add_project_test() throws Exception {

        // check dates

        int input = 3000;

        assert(input >= currentYear);

    }

    @Override
    protected void tearDown () throws Exception{
        super.tearDown();
    }
}