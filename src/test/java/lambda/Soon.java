package lambda;

public class Soon {
    private String name;

    public Soon() {}
    public Soon(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String world(String name) {
        return name + " world";
    }

    public static String hello(String name) {
        return "hello " + name;
    }
}
