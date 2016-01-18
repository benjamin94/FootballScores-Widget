package barqsoft.footballscores;

import android.app.Application;
import android.database.Cursor;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testDatabaseBenJustDoesStuff(){

        String toConvert = "hi";

        Cursor cursor = getContext().getContentResolver()
                .query(
                        DatabaseContract.scores_table.buildScoreWithDate(),
                        null,
                        null,
                        new String[]{"148110"},
                        null);

        cursor.moveToFirst();
    }
}
