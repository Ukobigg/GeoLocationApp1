package aisha.geolocationapp;

/**
 * Created by UkoDavid on 03/12/2017.
 */

public enum SQLQuery {

        INSERT_EMERGENCY_RECORD, INSERT_USER_RECORD, GET_ALL_EMERGENCIES, COUNT;

        public static String getSQLQuery(SQLQuery sqlQuery) {
            switch (sqlQuery) {

                case COUNT:
                    return "SELECT COUNT(*) FROM `%s` WHERE %s LIKE '%s';";
                case INSERT_EMERGENCY_RECORD:
                    return "INSERT INTO `employee` ('COLUMN_USERID', 'COLUMN_EMERGENCYTYPE', 'COLUMN_EMERGENCYSTATUS', 'COLUMN_EMERGENCYLOCATION', 'COLUMN_LATITUDE', 'COLUMN_LONGITUDE', 'COLUMN_DATETIME')"
                            + "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');";
                case GET_ALL_EMERGENCIES:
                    return "SELECT * FROM `employee` WHERE COLUMN_EMERGENCYSTATUS = '1'";
                case INSERT_USER_RECORD:
                    return "INSERT INTO `tb_users` (`user_name`,`user_id`,`user_username`,`user_contact`,`user_email`,`user_address`,`user_primaryLocation`)" +
                            "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')";
                default:
                    return "";
            }
        }

}
