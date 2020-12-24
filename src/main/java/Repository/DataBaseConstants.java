package Repository;

public class DataBaseConstants {
    public static class TableNames {
        public static String PlayersTableName = "PLAYERS";
        public static String MatchesTableName = "MATCHES";
        public static String BugReportTable = "BUGS";
    }

    public static class FieldNames {

        public static String IdFieldName = "Id";

        //Player Table Contants
        public static String ChatIdFieldName = "ChatId";
        public static String UserNameFiledName = "UserName";

        //Matched Table Constatns
        public static String PlayerId1 = "PlayerId1";
        public static String PlayerId2 = "PlayerId2";
        public static String WinnerId = "WinnerId";

        //Bug Table Constants
        public static String Message = "Message";
        public static String UserId = "UserId";

    }
}
