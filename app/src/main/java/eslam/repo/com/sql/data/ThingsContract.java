package eslam.repo.com.sql.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class ThingsContract {

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "eslam.repo.com.sql";
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);

    public static class usersEntry implements BaseColumns {

        public static final String PATH_USERS = "users";

        public static final Uri USERS_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USERS).build();

        public static final String TABLE_NAME = "users";
        public static final String COLUMN_USER_ID = BaseColumns._ID;
        public static final String COLUMN_USER_NAME = "name";
        public static final String COLUMN_USER_PASS = "password";

    }

    public static class postEntry implements BaseColumns {

        public static final String PATH_POSTS = "users";

        public static final Uri USERS_CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POSTS).build();

        public static final String TABLE_NAME = "posts";

        public static final String COLUMN_POST_ID = BaseColumns._ID;
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_POST_TEXT = "post_text";
    }


}
