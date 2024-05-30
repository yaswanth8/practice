public class Singleton {

    private static final Singleton instance=new Singleton();

    private Singleton(){
        System.out.println("hello");
    }

    public static Singleton getInstance(){
        return  instance;
    }
}
