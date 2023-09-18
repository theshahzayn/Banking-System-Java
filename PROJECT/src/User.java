public class User {
    protected static String name;
    protected static String userName;
    protected static String dateOBirth;
    private static String password;
    protected static double balance;

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String pass) {
       password = pass;
    }
}
