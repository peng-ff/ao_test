/**
 * HelloEnglish - 英文问候程序
 * 展示英文书本图案和英文问候语
 */
public class HelloEnglish {
    
    // 英文书本ASCII艺术图案
    private static final String ENGLISH_ART = 
        "    ___________\n" +
        "   |  ENGLISH  |\n" +
        "   |  ~~~~~~~~ |\n" +
        "   |           |\n" +
        "   |   HELLO   |\n" +
        "   |___________|";
    
    // 问候语数组
    private static final String[] GREETINGS = {
        "Hello, English! 📚",
        "Welcome to English World! 🌍",
        "Good day! 🎯",
        "Greetings! 👋"
    };
    
    /**
     * 打印英文书本图案
     */
    public static void printEnglish() {
        System.out.println(ENGLISH_ART);
    }
    
    /**
     * 获取随机问候语
     */
    public static String getRandomGreeting() {
        int index = (int) (Math.random() * GREETINGS.length);
        return GREETINGS[index];
    }
    
    /**
     * 个性化问候
     * @param name 用户的名字
     */
    public static void greetEnglish(String name) {
        System.out.println("┌────────────────────────────────┐");
        System.out.println("│  Hello, " + name + "!  │");
        System.out.println("└────────────────────────────────┘");
        printEnglish();
        System.out.println("\n" + getRandomGreeting());
    }
    
    /**
     * 主函数
     */
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════╗");
        System.out.println("║   欢迎来到 HelloEnglish 程序   ║");
        System.out.println("╚════════════════════════════════╝\n");
        
        if (args.length > 0) {
            // 如果提供了参数，使用参数作为用户名字
            greetEnglish(args[0]);
        } else {
            // 默认问候
            printEnglish();
            System.out.println("\nHello, English! Welcome to the world of learning! 📚");
            System.out.println("\n提示: 运行时可以传入名字作为参数");
            System.out.println("例如: java HelloEnglish John");
        }
        
        System.out.println("\n════════════════════════════════");
    }
}
