package aisha.geolocationapp.MS_SQL;

/**
 *
 * @author Aisha David
 */
public enum SQLQuery {
    INSERT_METADATA_RECORD, GET_METADATA_RECORD, UPDATE_METADATA_RECORD,
    INSERT_EMERGENCY_RECORD, INSERT_USER_RECORD, GET_ALL_EMERGENCIES, COUNT;

    public static String getSQLQuery(SQLQuery sqlQuery) {
        switch (sqlQuery) {
            case INSERT_METADATA_RECORD:
                return "INSERT INTO `tb_metadata` (`user_name`, `password_hash`, `password_salt`, `user_role`, `p_hash_status`)"
                        + " VALUES ('%s', '%s', '%s', '%s', '%s');";
            case UPDATE_METADATA_RECORD:
                return "UPDATE `tb_metadata` SET `password_hash`= '%s', `password_salt`= '%s', `p_hash_status`= 'changed' "
                        + "WHERE `user_name` = '%s';";
            case GET_METADATA_RECORD:
                return "SELECT * FROM `tb_metadata` WHERE user_name = '%s';";
            case COUNT:
                return "SELECT COUNT(*) FROM `%s` WHERE %s LIKE '%s';";
            case INSERT_EMERGENCY_RECORD:
                return "INSERT INTO `tb_emergencies` (`emergency_id`, `emergency_type`, `emergency_location`, `emergency_status`) "
                        + "VALUES ('%s', '%s', '%s', 'unverified');";
            case GET_ALL_EMERGENCIES:
                return "SELECT * FROM `tb_emergencies` WHERE emergency_status = 'verified'";
            case INSERT_USER_RECORD:
                return "INSERT INTO `tb_users` (`user_name`,`user_id`,`user_username`,`user_contact`,`user_email`,`user_address`,`user_primaryLocation`)" +
                        "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')";
            default:
                return "";
        }
    }
}
