public class Main {

    public static void main(String[] args) {
	 if (args[0] == "s") new Thread(new SServer()).start();
	 else new Thread(new CClient()).start();
    }
}
