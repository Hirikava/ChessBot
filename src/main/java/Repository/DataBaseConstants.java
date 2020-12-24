package Repository;

public class DataBaseConstants {
    public static class TableNames {
        public static String PlayersTableName = "PLAYERS";
        public static String MatchesTableName = "MATCHES";
    }

    public static class FieldNames {
        //Player Table Contants
        public static String IdFieldName = "Id";
        public static String ChatIdFieldName = "ChatId";
        public static String UserNameFiledName = "UserName";

        //Matched Table Constatns
        public static String PlayerId1 = "PlayerId1";
        public static String PlayerId2 = "PlayerId2";
        public static String WinnerId = "WinnerId";

    }
}
