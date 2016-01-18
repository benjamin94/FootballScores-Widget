package barqsoft.footballscores.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;

/**
 * Created by benjamin.lize on 15/01/2016.
 */
public class BenWidgetService extends IntentService {

    private static final String TAG = BenWidgetService.class.getSimpleName();

    public BenWidgetService() {
        super(".widget.BenWidgetService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Cursor cursor = getContentResolver()
                .query(
                        DatabaseContract.BASE_CONTENT_URI,
                        null,
                        null,
                        null,
                        null);

        cursor.moveToLast();

        int homeIndex = cursor.getColumnIndex(DatabaseContract.scores_table.HOME_COL);
        int awayIndex = cursor.getColumnIndex(DatabaseContract.scores_table.AWAY_COL);
        int homeGoalsIndex = cursor.getColumnIndex(DatabaseContract.scores_table.HOME_GOALS_COL);
        int awayGoalsIndex = cursor.getColumnIndex(DatabaseContract.scores_table.AWAY_GOALS_COL);

        String homeName  = cursor.getString(homeIndex);
        String awayName  = cursor.getString(awayIndex);
        int homeGoals = cursor.getInt(homeGoalsIndex);
        int awayGoals = cursor.getInt(awayGoalsIndex);

        String scores = Utilies.getScores(homeGoals, awayGoals);
        int homeCrest = Utilies.getTeamCrestByTeamName(homeName);
        int awayCrest = Utilies.getTeamCrestByTeamName(awayName);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        RemoteViews remoteViews = new RemoteViews(
                getApplicationContext().getPackageName(),
                R.layout.widget_ben_layout);

        remoteViews.setTextViewText(R.id.widgetScoresTextView,scores);
        remoteViews.setImageViewResource(R.id.widgetHomeCrestImageView, homeCrest);
        remoteViews.setImageViewResource(R.id.widgetAwayCrestImageView,awayCrest);

        appWidgetManager.updateAppWidget(new ComponentName(this.getPackageName(),
                BenWidgetProvider.class.getName()), remoteViews);

        Log.v(TAG,"Widget Updated");
    }
}